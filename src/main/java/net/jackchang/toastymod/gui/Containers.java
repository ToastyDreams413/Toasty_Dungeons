package net.jackchang.toastymod.gui;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.gui.backpack.large_backpack.LargeBackpackContainer;
import net.jackchang.toastymod.gui.backpack.medium_backpack.MediumBackpackContainer;
import net.jackchang.toastymod.gui.menu.MenuContainer;
import net.jackchang.toastymod.gui.backpack.small_backpack.SmallBackpackContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Containers {

    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(
            ForgeRegistries.MENU_TYPES, ToastyMod.MOD_ID);

    public static final RegistryObject<MenuType<MenuContainer>> MENU =
            CONTAINER_TYPES.register("menu_container", () -> IForgeMenuType.create(MenuContainer::fromNetwork));

    public static final RegistryObject<MenuType<SmallBackpackContainer>> SMALL_BACKPACK =
            CONTAINER_TYPES.register("small_backpack_container", () -> IForgeMenuType.create(SmallBackpackContainer::fromNetwork));

    public static final RegistryObject<MenuType<MediumBackpackContainer>> MEDIUM_BACKPACK =
            CONTAINER_TYPES.register("medium_backpack_container", () -> IForgeMenuType.create(MediumBackpackContainer::fromNetwork));

    public static final RegistryObject<MenuType<LargeBackpackContainer>> LARGE_BACKPACK =
            CONTAINER_TYPES.register("large_backpack_container", () -> IForgeMenuType.create(LargeBackpackContainer::fromNetwork));

    public static void register(IEventBus eventBus) {
        CONTAINER_TYPES.register(eventBus);
    }

}
