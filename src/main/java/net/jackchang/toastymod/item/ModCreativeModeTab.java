package net.jackchang.toastymod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TOASTY_MISCELLANEOUS = new CreativeModeTab("toastymiscellaneous") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TOASTY_MINERAL.get());
        }
    };

    public static final CreativeModeTab TOASTY_ARMORS = new CreativeModeTab("toastyarmor") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.STARTER_CHESTPLATE.get());
        }
    };

    public static final CreativeModeTab TOASTY_WEAPONS = new CreativeModeTab("toastyweapons") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.STARTER_SWORD.get());
        }
    };

    public static final CreativeModeTab TOASTY_ABILITIES = new CreativeModeTab("toastyabilities") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.STARTER_SPELL.get());
        }
    };
}
