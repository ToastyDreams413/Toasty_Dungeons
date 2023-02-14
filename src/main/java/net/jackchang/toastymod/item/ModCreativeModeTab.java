package net.jackchang.toastymod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TOASTY_TAB = new CreativeModeTab("toastytab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TOASTY_MINERAL.get());
        }
    };
}
