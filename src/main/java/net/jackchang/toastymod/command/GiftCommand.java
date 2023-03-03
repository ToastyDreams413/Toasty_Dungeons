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

import java.util.concurrent.atomic.AtomicBoolean;

public class GiftCommand {

    public GiftCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gift").then(Commands.argument("target", EntityArgument.player())
                .then(Commands.literal("shillings").then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((command) -> {
                    return giveShillings(command.getSource(), EntityArgument.getPlayer(command, "target"), IntegerArgumentType.getInteger(command, "amount"));
        })))));
    }

    private int giveShillings(CommandSourceStack source, ServerPlayer targetPlayer, int shillings) {
        ServerPlayer originalPlayer = source.getPlayer();
        AtomicBoolean hasEnough = new AtomicBoolean(true);

        if (originalPlayer.equals(targetPlayer)) {
            originalPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                if (playerData.getShillings() < shillings) {
                    originalPlayer.sendSystemMessage(Component.literal("It's pointless to gift shillings to yourself... and you don't even have that many shillings to gift").withStyle(ChatFormatting.RED));
                }
                else {
                    originalPlayer.sendSystemMessage(Component.literal("It's pointless to gift shillings to yourself...").withStyle(ChatFormatting.RED));
                }
            });
            return 1;
        }

        originalPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
            if (playerData.getShillings() < shillings) {
                originalPlayer.sendSystemMessage(Component.literal("You don't have that many shillings to gift!").withStyle(ChatFormatting.RED));
                hasEnough.set(false);
            }
        });

        // give shillings to the target player if the original player has enough
        if (hasEnough.get()) {
            originalPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                playerData.increaseShillings(-1 * shillings);
                originalPlayer.sendSystemMessage(Component.literal("You gave " + shillings + " shillings to " + targetPlayer.getName().getString()).withStyle(ChatFormatting.GOLD));
                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), originalPlayer);
            });
            targetPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                playerData.increaseShillings(shillings);
                targetPlayer.sendSystemMessage(Component.literal("You received " + shillings + " shillings from " + originalPlayer.getName().getString()).withStyle(ChatFormatting.GOLD));
                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), targetPlayer);
            });
        }

        return 1;
    }

}