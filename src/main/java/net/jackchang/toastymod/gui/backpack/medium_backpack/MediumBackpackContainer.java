package net.jackchang.toastymod.gui.backpack.medium_backpack;

import net.jackchang.toastymod.gui.Containers;
import net.jackchang.toastymod.item.custom.LargeBackpackItem;
import net.jackchang.toastymod.item.custom.MediumBackpackItem;
import net.jackchang.toastymod.item.custom.SmallBackpackItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MediumBackpackContainer extends AbstractContainerMenu {
    private final IItemHandler itemHandler;
    private int blocked = -1;
    private int backpackRowSize = 0;
    private int rows = 0;

    public static MediumBackpackContainer fromNetwork(final int windowId, final Inventory playerInventory, FriendlyByteBuf data) {
        return new MediumBackpackContainer(windowId, playerInventory, new ItemStackHandler(18));
    }

    public MediumBackpackContainer(int id, Inventory playerInventory, IItemHandler handler) {
        super(Containers.MEDIUM_BACKPACK.get(), id);
        itemHandler = handler;

        // creating backpack
        backpackRowSize = 9;
        rows = 2;
        for (int i = 0; i < backpackRowSize * rows; ++i) {
            int x = 8 + 18 * (i % backpackRowSize);
            int y = 18 + 18 * (i / backpackRowSize);

            addSlot(new SlotItemHandler(this.itemHandler, i, x, y));
        }

        final int yOffset = (rows - 4) * 18;

        // player inventory
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + yOffset));
            }
        }

        // hotbar
        for (int x = 0; x < 9; ++x) {
            Slot slot = addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + yOffset) {
                @Override
                public boolean mayPickup(Player playerIn) {
                    return index != blocked;
                }
            });

        }

    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // handling shift clicking for quick moving
        Slot slot = this.getSlot(index);

        if (!slot.mayPickup(playerIn)) {
            return slot.getItem();
        }

        if (index == blocked || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        // can't put a backpack into a backpack
        if (!(slot.getItem().getItem() instanceof Item) || slot.getItem().getItem() instanceof SmallBackpackItem || slot.getItem().getItem() instanceof MediumBackpackItem || slot.getItem().getItem() instanceof LargeBackpackItem) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack newStack = stack.copy();
        int containerSlots = itemHandler.getSlots();
        if (index < containerSlots) {
            if (!this.moveItemStackTo(stack, containerSlots, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.setChanged();

        }
        else if (stack.getItem() instanceof Item) {
            if (!this.moveItemStackTo(stack, 0, backpackRowSize * rows, false)) {
                return ItemStack.EMPTY;
            }
        }
        if (!this.moveItemStackTo(stack, 0, backpackRowSize * rows, false)) {
            return ItemStack.EMPTY;
        }


        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        }
        else {
            slot.setChanged();
        }

        slot.onTake(playerIn, newStack);
        return newStack;
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId < 0 || slotId > slots.size()) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }

        Slot slot = slots.get(slotId);
        if (!canTake(slotId, slot, dragType, player, clickTypeIn)) {
            return;
        }

        super.clicked(slotId, dragType, clickTypeIn, player);
    }

    private static boolean isValidItem(ItemStack stack) {
        return stack.getItem() instanceof SmallBackpackItem || stack.getItem() instanceof MediumBackpackItem || stack.getItem() instanceof LargeBackpackItem;
    }

    private boolean canTake(int slotId, Slot slot, int button, Player player, ClickType clickType) {
        if (slotId == blocked || slotId <= itemHandler.getSlots() - 1 && isValidItem(this.getCarried())) {
            return false;
        }

        // hotbar swapping via number keys
        if (clickType == ClickType.SWAP) {
            int hotbarId = itemHandler.getSlots() + 27 + button;
            // block swapping with container
            if (blocked == hotbarId) {
                return false;
            }

            Slot hotbarSlot = getSlot(hotbarId);
            if (slotId <= itemHandler.getSlots() - 1) {
                return !isValidItem(slot.getItem()) && !isValidItem(hotbarSlot.getItem());
            }
        }

        return true;
    }

}
