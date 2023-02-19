package net.jackchang.toastymod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.GivePlayerShillingsC2SPacket;
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

        // give shillings to player
        ModMessages.sendToServer(new GivePlayerShillingsC2SPacket(shillings, targetPlayer));
        originalPlayer.sendSystemMessage(Component.literal("You have sent " + shillings + " shillings to someone else!"));
        targetPlayer.sendSystemMessage(Component.literal("You have received " + shillings + " shillings from someone else!"));
        return 1;
    }

}