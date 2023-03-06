package net.jackchang.toastymod;

import com.mojang.logging.LogUtils;
import net.jackchang.toastymod.block.ModBlocks;
import net.jackchang.toastymod.gui.Containers;
import net.jackchang.toastymod.gui.backpack.large_backpack.LargeBackpackScreen;
import net.jackchang.toastymod.gui.backpack.medium_backpack.MediumBackpackScreen;
import net.jackchang.toastymod.gui.menu.MenuScreen;
import net.jackchang.toastymod.gui.backpack.small_backpack.SmallBackpackScreen;
import net.jackchang.toastymod.item.ModItems;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.util.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ToastyMod.MOD_ID)
public class ToastyMod {
    public static final String MOD_ID = "toastymod";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Very Important Comment
    public ToastyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        Containers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModItemProperties.addCustomItemProperties();
        MenuScreens.register(Containers.MENU.get(), MenuScreen::new);
        MenuScreens.register(Containers.SMALL_BACKPACK.get(), SmallBackpackScreen::new);
        MenuScreens.register(Containers.MEDIUM_BACKPACK.get(), MediumBackpackScreen::new);
        MenuScreens.register(Containers.LARGE_BACKPACK.get(), LargeBackpackScreen::new);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
