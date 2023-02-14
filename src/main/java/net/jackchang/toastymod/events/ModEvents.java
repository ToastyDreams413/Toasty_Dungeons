package net.jackchang.toastymod.events;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.command.WarpCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = ToastyMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new WarpCommands(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
