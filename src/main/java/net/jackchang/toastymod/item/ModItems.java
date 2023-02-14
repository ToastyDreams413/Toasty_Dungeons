package net.jackchang.toastymod.item;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.item.custom.CustomSword;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    // creates the deferred registry for items
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ToastyMod.MOD_ID);


    // items
    public static final RegistryObject<Item> TOASTY_MINERAL = ITEMS.register("toasty_mineral",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB)));
    public static final RegistryObject<Item> RAW_TOASTY_MINERAL = ITEMS.register("raw_toasty_mineral",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB)));


    // swords
    public static final RegistryObject<SwordItem> STARTER_SWORD = ITEMS.register("starter_sword",
            () -> new CustomSword(Tiers.STARTER, 5, 3.5f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB)));

    // bows
    public static final RegistryObject<BowItem> STARTER_BOW = ITEMS.register("starter_bow",
            () -> new BowItem(new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB)));

    public static class Tiers {
        public static final Tier STARTER = new ForgeTier(2, 800, 1.5f, 3, 350, null,
                () -> Ingredient.EMPTY);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
