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

public class AdminGiveCommand {

    public AdminGiveCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("admin").then(Commands.literal("give").then(Commands.argument("target", EntityArgument.player())
                .then(Commands.literal("shillings").then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((command) -> adminGiveShillings(command.getSource(), EntityArgument.getPlayer(command, "target"), IntegerArgumentType.getInteger(command, "amount"))))))));
    }

    private int adminGiveShillings(CommandSourceStack source, ServerPlayer targetPlayer, int shillings) {
        ServerPlayer originalPlayer = source.getPlayer();
        AtomicBoolean hasPerms = new AtomicBoolean(true);

        originalPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
            if (playerData.getRank() < 10) {
                originalPlayer.sendSystemMessage(Component.literal("You don't have permission to run admin commands!").withStyle(ChatFormatting.RED));
                hasPerms.set(false);
            }
        });

        // give shillings to player
        if (hasPerms.get()) {
            targetPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                playerData.increaseShillings(shillings);
                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), targetPlayer);
            });
            if (originalPlayer.equals(targetPlayer)) {
                originalPlayer.sendSystemMessage(Component.literal("[ADMIN COMMAND] You gave " + shillings + " shillings to yourself").withStyle(ChatFormatting.DARK_RED));
            } else {
                originalPlayer.sendSystemMessage(Component.literal("[ADMIN COMMAND] You gave " + shillings + " shillings to " + targetPlayer.getName().getString()).withStyle(ChatFormatting.DARK_RED));
                targetPlayer.sendSystemMessage(Component.literal("[ADMIN COMMAND] " + originalPlayer.getName().getString() + " gave you " + shillings + " shillings").withStyle(ChatFormatting.DARK_RED));
            }
        }

        return 1;
    }

}