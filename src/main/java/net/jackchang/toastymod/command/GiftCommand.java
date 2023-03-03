package net.jackchang.toastymod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.jackchang.toastymod.custom_attributes.PlayerDataProvider;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.PlayerDataSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class GiftCommand {

    public GiftCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gift").then(Commands.argument("target", EntityArgument.player())
                .then(Commands.literal("shillings").then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((command) -> {
                    return giveShillings(command.getSource(), EntityArgument.getPlayer(command, "target"), IntegerArgumentType.getInteger(command, "amount"));
        })))));
    }

    private int giveShillings(CommandSourceStack source, ServerPlayer targetPlayer, int shillings) {
        ServerPlayer originalPlayer = source.getPlayer();

        // originalPlayer.sendSystemMessage(Component.literal("You are trying to send a shilling...").withStyle(ChatFormatting.AQUA));

        // give shillings to player
        targetPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
            playerData.increaseShillings(shillings);
            originalPlayer.sendSystemMessage(Component.literal("You have sent shillings to " + targetPlayer.getName().getString()).withStyle(ChatFormatting.GOLD));
            targetPlayer.sendSystemMessage(Component.literal("You gained " + shillings + " shillings from " + originalPlayer.getName().getString()).withStyle(ChatFormatting.GOLD));
            ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), targetPlayer);
        });

        return 1;
    }

}