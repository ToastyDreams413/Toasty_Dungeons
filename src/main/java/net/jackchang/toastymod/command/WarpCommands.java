package net.jackchang.toastymod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class WarpCommands {

    public WarpCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("lobby").executes((command) -> warpLobbyCommand(command.getSource())));
        dispatcher.register(Commands.literal("l").executes((command) -> warpLobbyCommand(command.getSource())));
        dispatcher.register(Commands.literal("warp").then(Commands.literal("lobby").executes((command) -> warpLobbyCommand(command.getSource()))));
        dispatcher.register(Commands.literal("dhub").executes((command) -> warpDungeonhubCommand(command.getSource())));
        dispatcher.register(Commands.literal("dungeonhub").executes((command) -> warpDungeonhubCommand(command.getSource())));
        dispatcher.register(Commands.literal("warp").then(Commands.literal("dungeonhub").executes((command) -> warpDungeonhubCommand(command.getSource()))));
    }

    private int warpLobbyCommand(CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayer();
        player.teleportTo(100, 100, 100);
        player.sendSystemMessage(Component.literal("Warping to the lobby...!"));
        return 1;
    }

    private int warpDungeonhubCommand(CommandSourceStack source) throws CommandSyntaxException {
        Player player = source.getPlayer();
        player.teleportTo(500, 100, 500);
        player.sendSystemMessage(Component.literal("Warping to the Dungeon Hub...!"));
        return 1;
    }

}