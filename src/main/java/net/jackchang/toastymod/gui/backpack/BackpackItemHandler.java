package net.jackchang.toastymod.gui.backpack;

import net.jackchang.toastymod.gui.backpack.small_backpack.SmallBackpackManager;
import net.minecraftforge.items.ItemStackHandler;

public class BackpackItemHandler extends ItemStackHandler {
    public BackpackItemHandler(int size) {
        super(size);
    }

    @Override
    protected void onContentsChanged(int slot) {
        SmallBackpackManager.get().setDirty();
    }

}

