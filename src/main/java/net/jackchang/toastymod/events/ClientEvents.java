package net.jackchang.toastymod.events;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.GivePlayerShillingsC2SPacket;
import net.jackchang.toastymod.networking.packet.TestC2SPacket;
import net.jackchang.toastymod.util.KeyBinding;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = ToastyMod.MOD_ID, value = Dist.CLIENT)

    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.SPAWN_COW_KEY.consumeClick()) {
                ModMessages.sendToServer(new TestC2SPacket());
            }
            if (KeyBinding.GAIN_SHILLING_KEY.consumeClick()) {
                ModMessages.sendToServer(new GivePlayerShillingsC2SPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = ToastyMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SPAWN_COW_KEY);
            event.register(KeyBinding.GAIN_SHILLING_KEY);
        }
    }

}
