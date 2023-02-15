package net.jackchang.toastymod.item;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.item.custom.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
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
    public static final RegistryObject<Item> MENU = ITEMS.register("menu",
            () -> new MenuItem(new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB).stacksTo(1)));


    // swords
    public static final RegistryObject<SwordItem> STARTER_SWORD = ITEMS.register("starter_sword",
            () -> new CustomSword(Tiers.STARTER, -3, -3f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A starter sword. Honestly, it's pretty garbage."));
    public static final RegistryObject<SwordItem> DECAYING_DRUMSTICK = ITEMS.register("decaying_drumstick",
            () -> new CustomSword(Tiers.STARTER, -2, -2f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A decaying drumstick that you obtained by defeating the Mother Hen. Pretty disgusting."));
    public static final RegistryObject<SwordItem> FLIMSY_RAPIER = ITEMS.register("flimsy_rapier",
            () -> new CustomSword(Tiers.STARTER, 0, -1f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A flimsy rapier."));
    public static final RegistryObject<SwordItem> CRYSTAL_JAVELIN = ITEMS.register("crystal_javelin",
            () -> new CustomSword(Tiers.STARTER, 5, 3f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A javelin that can be thrown pretty far, and was once the main weapon of the Crystal Assassin."));

    // bows
    public static final RegistryObject<BowItem> STARTER_BOW = ITEMS.register("starter_bow",
            () -> new CustomBow(new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB), "A crappy bow."));


    // armors
    public static final RegistryObject<ArmorItem> STARTER_HELMET = ITEMS.register("starter_helmet",
            () -> new CustomHelmet(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A very basic standard helmet."));
    public static final RegistryObject<ArmorItem> STARTER_CHESTPLATE = ITEMS.register("starter_chestplate",
            () -> new CustomChestplate(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A very basic standard chestplate."));
    public static final RegistryObject<ArmorItem> STARTER_CHAUSSES = ITEMS.register("starter_chausses",
            () -> new CustomLeggings(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A very basic standard chausses."));
    public static final RegistryObject<ArmorItem> STARTER_SABATON = ITEMS.register("starter_sabaton",
            () -> new CustomBoots(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_TAB),
                    "A very basic standard sabaton."));


    public static class Tiers {
        public static final Tier STARTER = new ForgeTier(2, 800, 1.5f, 3, 350, null,
                () -> Ingredient.EMPTY);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
