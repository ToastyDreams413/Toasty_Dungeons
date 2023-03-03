package net.jackchang.toastymod.item;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.item.custom.*;
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
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TOASTY_MISCELLANEOUS)));
    public static final RegistryObject<Item> RAW_TOASTY_MINERAL = ITEMS.register("raw_toasty_mineral",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TOASTY_MISCELLANEOUS)));
    public static final RegistryObject<Item> MENU = ITEMS.register("menu",
            () -> new MenuItem(new Item.Properties().tab(ModCreativeModeTab.TOASTY_MISCELLANEOUS).stacksTo(1)));


    // swords

    public static final RegistryObject<SwordItem> STARTER_SWORD = ITEMS.register("starter_sword",
            () -> new CustomSword(Tiers.STARTER, -3, -3f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "A starter sword. Honestly, it's pretty garbage."));

    public static final RegistryObject<SwordItem> DECAYING_DRUMSTICK = ITEMS.register("decaying_drumstick",
            () -> new CustomSword(Tiers.STARTER, -2, -3, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "A decaying drumstick that you obtained by defeating the Mother Hen. Pretty disgusting."));

    public static final RegistryObject<SwordItem> RUSTY_SWORD = ITEMS.register("rusty_sword",
            () -> new CustomSword(Tiers.STARTER, -1, -3, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "Once quite a potent sword. However, it's very rusty now."));

    public static final RegistryObject<SwordItem> ARACHNAS_FANG = ITEMS.register("arachnas_fang",
            () -> new CustomSword(Tiers.STARTER, -1, -2, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "A trophy from defeating Arachna. It's quite sharp."));

    public static final RegistryObject<SwordItem> FLIMSY_RAPIER = ITEMS.register("flimsy_rapier",
            () -> new CustomSword(Tiers.STARTER, 0, -1, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "A flimsy rapier."));

    public static final RegistryObject<SwordItem> CRACKED_STONE_HAMMER = ITEMS.register("cracked_stone_hammer",
            () -> new CustomSword(Tiers.STARTER, 6, -3.5f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "Legend says this was once one of the most powerful weapons in existence. Unfortunately, most of it was loss when it was cracked."));

    public static final RegistryObject<SwordItem> CUTLASS_OF_THE_SEAS = ITEMS.register("cutlass_of_the_seas",
            () -> new CustomSword(Tiers.STARTER, 1, 1, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "This mighty sword was once brandished by Captain Blackbeard himself."));

    public static final RegistryObject<SwordItem> CRYSTAL_JAVELIN = ITEMS.register("crystal_javelin",
            () -> new CustomSword(Tiers.STARTER, 5, 0, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "A javelin that can be thrown pretty far, and was once the main weapon of the Crystal Assassin."));

    public static final RegistryObject<SwordItem> SWORD_OF_ELECTRIFYING_POWER = ITEMS.register("sword_of_electrifying_power",
            () -> new CustomSword(Tiers.STARTER, 8, 3.5f, new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS),
                    "This dominant sword once was wielded by Zeus himself. Legend says one strike can defeat almost anything."));


    // bows

    public static final RegistryObject<BowItem> STARTER_BOW = ITEMS.register("starter_bow",
            () -> new CustomBow(new Item.Properties().tab(ModCreativeModeTab.TOASTY_WEAPONS), "A crappy bow."));

    // wands

    public static final RegistryObject<Item> HONEY_WAND = ITEMS.register("honey_wand",
            () -> new CustomWand(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "A wand made of honey. Suprisingly, it's somewhat effective."));

    public static final RegistryObject<Item> WAND_OF_DESPAIR = ITEMS.register("wand_of_despair",
            () -> new CustomWand(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "Just holding this wand makes you want to crawl back into bed under your covers."));



    // armors
    public static final RegistryObject<ArmorItem> STARTER_HELMET = ITEMS.register("starter_helmet",
            () -> new CustomHelmet(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "A very basic standard helmet."));
    public static final RegistryObject<ArmorItem> STARTER_CHESTPLATE = ITEMS.register("starter_chestplate",
            () -> new CustomChestplate(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "A very basic standard chestplate."));
    public static final RegistryObject<ArmorItem> STARTER_CHAUSSES = ITEMS.register("starter_chausses",
            () -> new CustomChausses(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "A very basic standard chausses."));
    public static final RegistryObject<ArmorItem> STARTER_SABATON = ITEMS.register("starter_sabaton",
            () -> new CustomSabaton(ModArmorMaterials.STARTER_HEAVY, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "A very basic standard sabaton."));

    public static final RegistryObject<ArmorItem> HELMET_OF_DREAD = ITEMS.register("helmet_of_dread",
            () -> new CustomHelmet(ModArmorMaterials.DREAD_SET, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "Helmet of pure dread. Powerful, but possesses a dark force."));
    public static final RegistryObject<ArmorItem> CHESTPLATE_OF_DREAD = ITEMS.register("chestplate_of_dread",
            () -> new CustomChestplate(ModArmorMaterials.DREAD_SET, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "Chestplate of pure dread. Powerful, but possesses a dark force."));
    public static final RegistryObject<ArmorItem> CHAUSSES_OF_DREAD = ITEMS.register("chausses_of_dread",
            () -> new CustomChausses(ModArmorMaterials.DREAD_SET, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "Chausses of pure dread. Powerful, but possesses a dark force."));
    public static final RegistryObject<ArmorItem> SABATON_OF_DREAD = ITEMS.register("sabaton_of_dread",
            () -> new CustomSabaton(ModArmorMaterials.DREAD_SET, new Item.Properties().tab(ModCreativeModeTab.TOASTY_ARMORS),
                    "Sabaton of pure dread. Powerful, but possesses a dark force."));

    // spells

    public static final RegistryObject<Item> STARTER_SPELL = ITEMS.register("starter_spell",
            () -> new CustomSpell(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "A pretty meh spell. Alright for beginners."));

    public static final RegistryObject<Item> SLITHERING_SPELL = ITEMS.register("slithering_spell",
            () -> new CustomSpell(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "A spell used by the snakes. Quite a powerful spell."));

    public static final RegistryObject<Item> ZEUS_LIGHTNING_BOLT = ITEMS.register("zeus_lightning_bolt",
            () -> new CustomSpell(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "Zeus' ultimate weapon. As stated by the Kairagi: Dancing Thunder, \"One strike should suffice.\""));

    // horns

    public static final RegistryObject<Item> STARTER_HORN = ITEMS.register("starter_horn",
            () -> new CustomHorn(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "A good horn for motivating your peers."));


    public static final RegistryObject<Item> CONCH_OF_THE_SEAS = ITEMS.register("conch_of_the_seas",
            () -> new CustomHorn(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "The ultimate horn used by Poseidon himself. Legend says that when used, nothing is impossible for affected entities."));

    // tomes

    public static final RegistryObject<Item> STARTER_TOME = ITEMS.register("starter_tome",
            () -> new CustomHorn(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "Doesn't heal much, but it helps."));

    public static final RegistryObject<Item> HONEY_TOME = ITEMS.register("honey_tome",
            () -> new CustomHorn(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "They say honey can help you heal."));

    public static final RegistryObject<Item> TOME_OF_DARKNESS = ITEMS.register("tome_of_darkness",
            () -> new CustomHorn(new Item.Properties().tab(ModCreativeModeTab.TOASTY_ABILITIES),
                    "A tome salvaged from the remains of The Despairing Dark. Unlike others, it doesn't heal much, but somehow does quite a bit of damage."));


    public static class Tiers {
        public static final Tier STARTER = new ForgeTier(2, 800, 1.5f, 3, 350, null,
                () -> Ingredient.EMPTY);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
