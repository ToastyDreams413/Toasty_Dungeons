package net.jackchang.toastymod.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.jackchang.toastymod.custom_attributes.PlayerDataProvider;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.PlayerDataSyncS2CPacket;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class PartyCommands {

    public PartyCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("party").then(Commands.argument("option", StringArgumentType.string())
            .executes(ctx -> runCommand(ctx.getSource(), StringArgumentType.getString(ctx, "option"), null))
                .suggests((sourceCommandContext, suggestionsBuilder) -> {
                    suggestionsBuilder
                        .suggest("invite")
                        .suggest("accept")
                        .suggest("deny")
                        .suggest("kick")
                        .suggest("leader")
                        .suggest("disband")
                        .suggest("leave")
                        .suggest("list");
                        
                        return suggestionsBuilder.buildFuture();
                }).then(Commands.argument("target", EntityArgument.player())
                    .executes(ctx -> runCommand(ctx.getSource(), StringArgumentType.getString(ctx, "option"), EntityArgument.getPlayer(ctx, "target"))))));
    }

    private static int runCommand(CommandSourceStack context, String option, ServerPlayer target) throws  CommandSyntaxException {
        ServerPlayer player = context.getPlayer();

        ArrayList<String> optionsThatNeedATarget = new ArrayList<String>(Arrays.asList("invite", "accept", "deny", "kick", "leader"));
        if (target == null && optionsThatNeedATarget.contains(option)) {
            player.sendSystemMessage(Component.literal("The command /party " + option + " needs a target").withStyle(ChatFormatting.RED));

            return 1;
        }

        switch (option) {
			case "invite":
                if (player.getUUID() == target.getUUID()) {
                    player.sendSystemMessage(Component.literal("You cant have a party with just yourself D:").withStyle(ChatFormatting.RED));

                    return 1;
                }

                player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                    if (playerData.getInParty() && player.getUUID() != playerData.getPartyLeader()) {
                        player.sendSystemMessage(Component.literal("You have to be the party leader to invite someone").withStyle(ChatFormatting.RED));

                        return;
                    }

                    if (playerData.getPartyInvitesSent().contains(target.getUUID())) {
                        player.sendSystemMessage(Component.literal("You have already sent a party request to " + target.getName().getString()).withStyle(ChatFormatting.RED));

                        return;
                    }
                    
                    playerData.addPartyInviteSent(target.getUUID());
                    ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);

                    player.sendSystemMessage(Component.literal("Sent a party invite to " + target.getName().getString()).withStyle(ChatFormatting.GREEN));
                    target.sendSystemMessage(Component.literal(player.getName().getString() + " has invited you to their party. To accept type /party accept").withStyle(ChatFormatting.GREEN));
                });
                
                break;

			case "accept":
                if (player.getUUID() == target.getUUID()) {
                    player.sendSystemMessage(Component.literal("You cant have a party with just yourself and you weren't even invited D:").withStyle(ChatFormatting.RED));

                    return 1;
                }

                player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                    if (playerData.getInParty()) {
                        player.sendSystemMessage(Component.literal("You have to leave your party first before you can join another").withStyle(ChatFormatting.RED));

                        return;
                    }

                    target.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(targetData -> { 
                        if (!targetData.getPartyInvitesSent().contains(player.getUUID())) {
                            player.sendSystemMessage(Component.literal(target.getName().getString() + " did not invite you to their party").withStyle(ChatFormatting.RED));
    
                            return;
                        }
                        
                        targetData.removePartyInviteSent(player.getUUID());

                        // if first time creating party
                        if (!targetData.getInParty()) {
                            targetData.setInParty(true);
                            targetData.setPartyLeader(target.getUUID());
                            targetData.addAllPartyMembers(new ArrayList<UUID>(Arrays.asList(target.getUUID(), player.getUUID())));
                        } else {
                            targetData.addPartyMember(player.getUUID());
                        }

                        playerData.setInParty(true);
                        playerData.setPartyLeader(target.getUUID());
                        playerData.addAllPartyMembers(targetData.getPartyMembers());

                        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(targetData), target);
                        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);

                        target.sendSystemMessage(Component.literal(player.getName().getString() + " accepted your party invite").withStyle(ChatFormatting.GREEN));
                        player.sendSystemMessage(Component.literal("You accepted " + target.getName().getString() + "'s party invite").withStyle(ChatFormatting.GREEN));
                    });
                });
				
                break;

            case "deny":
                if (player.getUUID() == target.getUUID()) {
                    player.sendSystemMessage(Component.literal("You cant have a party with just yourself and why would you deny yourself from your own party?").withStyle(ChatFormatting.RED));

                    return 1;
                }

                target.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(targetData -> { 
                    if (!targetData.getPartyInvitesSent().contains(player.getUUID())) {
                        player.sendSystemMessage(Component.literal(target.getName().getString() + " did not invite you to their party").withStyle(ChatFormatting.RED));

                        return;
                    }

                    targetData.removePartyInviteSent(player.getUUID());
                    ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(targetData), target);


                    target.sendSystemMessage(Component.literal(player.getName().getString() + " denied your party invite").withStyle(ChatFormatting.GREEN));
                    player.sendSystemMessage(Component.literal("You denied " + target.getName().getString() + "'s party invite").withStyle(ChatFormatting.GREEN));
                });
        
                break;
            
            case "kick":
                if (player.getUUID() == target.getUUID()) {
                    player.sendSystemMessage(Component.literal("You cant kick yourself from a party but you can /party leave if not the party leader").withStyle(ChatFormatting.RED));

                    return 1;
                }

                player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                    if (playerData.getPartyLeader() != player.getUUID()) {
                        player.sendSystemMessage(Component.literal("You have to be the party leader to kick someone!").withStyle(ChatFormatting.RED));

                        return;
                    }
            
                    if (target.getUUID() == player.getUUID()) {
                        player.sendSystemMessage(Component.literal("You can't kick yourself").withStyle(ChatFormatting.RED));

                        return;
                    }

                    // disband party if only 2 members
                    if (playerData.getPartyMembers().size() == 2) {
                        disbandParty(context, player, target, 1);

                        return;
                    }

                    // sync with all party member's capabilities 
                    ServerLevel level = context.getLevel();
                    for (UUID partyMemberUUID : playerData.getPartyMembers()) 
                    { 
                        ServerPlayer partyMember = (ServerPlayer) level.getPlayerByUUID(partyMemberUUID);

                        if (partyMember.getUUID() != target.getUUID() && partyMember.getUUID() != player.getUUID()) {
                            partyMember.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(partyMemberData -> { 
                                partyMemberData.removePartyMember(target.getUUID());
    
                                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(partyMemberData), partyMember);
                            });
    
                            player.sendSystemMessage(Component.literal(target.getName().getString() + " has been kicked from the party").withStyle(ChatFormatting.GREEN));
                        }
                    }

                    target.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(targetData -> { 
                        // reset target's party capabilities
                        targetData.setInParty(false);
                        targetData.setPartyLeader(null);;
                        targetData.removeAllPartyMembers();
                        targetData.setSelectedChat("all");

                        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(targetData), target);

                        target.sendSystemMessage(Component.literal("You have been kicked from the party").withStyle(ChatFormatting.GREEN));
                    });

                    playerData.removePartyMember(target.getUUID());
                    ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);

                    player.sendSystemMessage(Component.literal("You have kicked " + target.getName().getString() + " from the party").withStyle(ChatFormatting.GREEN));
                });
			
                break;

			case "leader":
                player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                    if (playerData.getPartyLeader() != player.getUUID()) {
                        player.sendSystemMessage(Component.literal("Only party leaders can choose a new party leader").withStyle(ChatFormatting.RED));

                        return;
                    }

                    if (target.getUUID() == player.getUUID()) {
                        player.sendSystemMessage(Component.literal("Silly you, you are already the party leader").withStyle(ChatFormatting.RED));
                        
                        return;
                    }
                    
                    if (!playerData.getPartyMembers().contains(target.getUUID())) {
                        player.sendSystemMessage(Component.literal(target.getName().getString() + " is not in your party").withStyle(ChatFormatting.RED));
                        
                        return;
                    }

                    ServerLevel level = context.getLevel();
                    for (UUID partyMemberUUID : playerData.getPartyMembers()) 
                    { 
                        ServerPlayer partyMember = (ServerPlayer) level.getPlayerByUUID(partyMemberUUID);

                        if (partyMember.getUUID() != player.getUUID()) {
                            partyMember.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(partyMemberData -> { 
                                partyMemberData.setPartyLeader(target.getUUID());

                                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(partyMemberData), partyMember);
                            });

                            partyMember.sendSystemMessage(Component.literal(target.getName().getString() + " is now the party leader").withStyle(ChatFormatting.GREEN));
                        }
                    }

                
                    playerData.setPartyLeader(target.getUUID());
                    ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);

                    player.sendSystemMessage(Component.literal("You have transferred the party leader to " + target.getName().getString()).withStyle(ChatFormatting.GREEN));
                });

				break;

            case "disband":
                disbandParty(context, player, target, 0);
                
                break;

            case "leave":
                player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                    if (!playerData.getInParty()) {
                        player.sendSystemMessage(Component.literal("You cant leave a party if you aren't in one").withStyle(ChatFormatting.RED));

                        return;
                    }

                    if (playerData.getPartyLeader() == player.getUUID()) {
                        player.sendSystemMessage(Component.literal("You can't leave your own party").withStyle(ChatFormatting.RED));

                        return;
                    }

                    // disband party if only 2 members
                    if (playerData.getPartyMembers().size() == 2) {
                        disbandParty(context, player, target, 2);

                        return;
                    }

                    ServerLevel level = context.getLevel();
                    for (UUID partyMemberUUID : playerData.getPartyMembers()) 
                    {
                        ServerPlayer partyMember = (ServerPlayer) level.getPlayerByUUID(partyMemberUUID);

                        if (partyMember.getUUID() != player.getUUID()) {
                            partyMember.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(partyMemberData -> { 
                                partyMemberData.removePartyMember(player.getUUID());
    
                                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(partyMemberData), partyMember);
                            });
    
                            partyMember.sendSystemMessage(Component.literal(player.getName().getString() + " has left the party").withStyle(ChatFormatting.GREEN));
                        }
                    }

                    // reset party capabilities
                    playerData.setInParty(false);
                    playerData.setPartyLeader(null);
                    playerData.removeAllPartyMembers();
                    playerData.removeAllPartyInvitesSent();
                    playerData.setSelectedChat("all");

                    ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);

                    player.sendSystemMessage(Component.literal("You left the party").withStyle(ChatFormatting.GREEN));
                });

                break;

            case "list":
                player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                    if (!playerData.getInParty()) {
                        player.sendSystemMessage(Component.literal("You are not in a party").withStyle(ChatFormatting.RED));

                        return;
                    }

                    ServerLevel level = context.getLevel();
                    player.sendSystemMessage(Component.literal(playerData.getPartyMembers().size() + " Party Members: \n" + playerData.getPartyMembers().stream().map(partyMemberUUID -> level.getPlayerByUUID(partyMemberUUID).getName().getString())
                        .collect(Collectors.joining("\n"))).withStyle(ChatFormatting.GREEN));

                });

                break;

            // for debugging purposes TODO: delete when not neeed
            //   to use you need to add debug to optionsThatNeedATarget and suggest debug in the command
            /*case "debug":
                target.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(targetData -> {
                    player.sendSystemMessage(Component.literal(target.getName().getString() + " (" + target.getUUID().toString() + ")'s Party Capabilities:" + "\nIn party: " + targetData.getInParty() + "\nParty Leader: " + targetData.getPartyLeader() + "\nParty Members: " + targetData.getPartyMembers().toString() + "\nParty Invites Sent: " + targetData.getPartyInvitesSent().toString()).withStyle(ChatFormatting.GOLD));
                });

                break;*/

			default:
				break;
		}
        
        return 0;
    }

    /**
    * Disbands the party.
    * Called in the disband case and kick/leave cases if the party is made up of 2 members
    *
    * @param    context     the CommandSourceStack of the command it was invoked in
    * @param    player      the ServerPlayer of the player who invoked the command
    * @param    target      the ServerPlayer of the target of the command
    * @param    fromMethod  an int which changes the message sent to party members. 0 = disband case, 1 = kick case, 2 = leave case
    * @return               void
    */
    static private void disbandParty(CommandSourceStack context, ServerPlayer player, ServerPlayer target, int fromMethod) {     
        String partyMembersMessage;
        String playerMessage;
        switch (fromMethod) {
            // disband case
            case 0:
                partyMembersMessage = "The party you were in was disbanded";
                playerMessage = partyMembersMessage;
                break;

            // kick case
            case 1:
                partyMembersMessage = "You have been kicked from the party";
                playerMessage = "The party you were in was disbanded since you kicked the last member";
                break;

            // leave case
            case 2:
                partyMembersMessage = player.getName().getString() + " has left the party and the party was disbanded since the last member left";
                playerMessage = "You have left the party";
                break;

            default:
                partyMembersMessage = "";
                playerMessage = "";
                break;
        }
        
        player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {    
            if (playerData.getPartyLeader() != player.getUUID() && fromMethod != 2) {
                player.sendSystemMessage(Component.literal("Only party leaders can disband a party").withStyle(ChatFormatting.RED));

                return;
            }

            ServerLevel level = context.getLevel();
            for (UUID partyMemberUUID : playerData.getPartyMembers()) 
            {
                ServerPlayer partyMember = (ServerPlayer) level.getPlayerByUUID(partyMemberUUID);

                if (partyMember.getUUID() != player.getUUID()) {
                    // reset party capabilities
                    partyMember.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(partyMemberData -> { 
                        partyMemberData.setInParty(false);
                        partyMemberData.setPartyLeader(null);
                        partyMemberData.removeAllPartyMembers();
                        partyMemberData.removeAllPartyInvitesSent();
                        partyMemberData.setSelectedChat("all");

                        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(partyMemberData), partyMember);
                    });

                    partyMember.sendSystemMessage(Component.literal(partyMembersMessage).withStyle(ChatFormatting.GREEN));
                }
            }

            playerData.setInParty(false);
            playerData.setPartyLeader(null);
            playerData.removeAllPartyMembers();
            playerData.removeAllPartyInvitesSent();
            playerData.setSelectedChat("all");

            ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);

            player.sendSystemMessage(Component.literal(playerMessage).withStyle(ChatFormatting.GREEN));
        });
    }
}