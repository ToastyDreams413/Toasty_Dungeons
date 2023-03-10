package net.jackchang.toastymod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.jackchang.toastymod.custom_attributes.PlayerDataProvider;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.PlayerDataSyncS2CPacket;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ChatCommands {

    public ChatCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("chat").then(Commands.argument("option", StringArgumentType.string())
            .executes(ctx -> runCommand(ctx.getSource(), StringArgumentType.getString(ctx, "option"), null))
                .suggests((sourceCommandContext, suggestionsBuilder) -> {
                    suggestionsBuilder
                        .suggest("all")
                        .suggest("party");
                        
                        return suggestionsBuilder.buildFuture();
                })));
    }

    private static int runCommand(CommandSourceStack context, String option, ServerPlayer target) throws  CommandSyntaxException {
        ServerPlayer player = context.getPlayer();

        player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> { 
            if (playerData.getSelectedChat().equals(option)) {
                player.sendSystemMessage(Component.literal("You are already in the " + option + " chat").withStyle(ChatFormatting.RED));

                return;
            }

            if (option.equals("party") && !playerData.getInParty()) {
                player.sendSystemMessage(Component.literal("You cannot switch to the party chat since you are not in a party").withStyle(ChatFormatting.RED));

                return;
            }

            playerData.setSelectedChat(option);
            ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);

            player.sendSystemMessage(Component.literal("You have switched to the " + option + " chat").withStyle(ChatFormatting.GREEN));
        });

        return 0;
    }
}