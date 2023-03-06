package net.jackchang.toastymod.gui.menu;

import net.jackchang.toastymod.gui.Containers;
import net.jackchang.toastymod.item.ModCreativeModeTab;
import net.jackchang.toastymod.item.ModItems;
import net.jackchang.toastymod.item.custom.MenuItem;
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

public class MenuContainer extends AbstractContainerMenu {
    private final IItemHandler itemHandler;
    private int blocked = -1;
    private int satchelSegmentSize = 0;

    public static MenuContainer fromNetwork(final int windowId, final Inventory playerInventory, FriendlyByteBuf data) {
        return new MenuContainer(windowId, playerInventory, new ItemStackHandler(9));
    }

    public MenuContainer(int id, Inventory playerInventory, IItemHandler handler) {
        super(Containers.MENU.get(), id);
        itemHandler = handler;

        // Add backpack slots (3 rows of 9)
        satchelSegmentSize = 9;
        //satchel segment creation
        for (int i = 0; i < satchelSegmentSize; ++i) {
            int x = 8 + 18 * (i % satchelSegmentSize);
            int y = 18 + 18 * (i / satchelSegmentSize);

            Slot slot = addSlot(new SlotItemHandler(this.itemHandler, i, x, y));
            slot.set(new ItemStack(ModItems.STAT_CHECKER.get()));
        }



        final int rowCount = this.itemHandler.getSlots() / 8;
        final int yOffset = (rowCount - 4) * 18;

        /*
        // Player inventory
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + yOffset));
            }
        }

        // Hotbar
        for (int x = 0; x < 9; ++x) {
            Slot slot = addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + yOffset) {
                @Override
                public boolean mayPickup(Player playerIn) {
                    return index != blocked;
                }
            });

        }
         */



    }
    private static ItemStack getHeldItem(Player player) {
        // Determine which held item is a backpack (if either)
        if (isValidItem(player.getMainHandItem())) {
            return player.getMainHandItem();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // This method handles shift-clicking to transfer items quickly. This can easily crash the game if not coded
        // correctly. The first slots (index 0 to whatever) are usually the inventory block/item, while player slots
        // start after those.

        return ItemStack.EMPTY;

        /*
        Slot slot = this.getSlot(index);

        if (!slot.mayPickup(playerIn)) {
            return slot.getItem();
        }

        if (index == blocked || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        //Filtering what's allowed in the bag, see also far below at line 192
        if(!(slot.getItem().getItem() instanceof Item) || slot.getItem().getItem() instanceof MenuItem) {
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

            //Filtering what's allowed in the bag, see also far below at line 192
        } else if(stack.getItem() instanceof Item) {
            if (!this.moveItemStackTo(stack, 0, satchelSegmentSize, false)) {
                return ItemStack.EMPTY;
            }
        }
        if (!this.moveItemStackTo(stack, 0, satchelSegmentSize, false)) {
            return ItemStack.EMPTY;
        }


        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        slot.onTake(playerIn, newStack);
        return newStack;
         */

    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        System.out.println("Slot ID: " + slotId);
        return;

        /*
        if (slotId < 0 || slotId > slots.size()) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }

        Slot slot = slots.get(slotId);
        if (!canTake(slotId, slot, dragType, player, clickTypeIn)) {
            return;
        }

        super.clicked(slotId, dragType, clickTypeIn, player);
         */
    }

    private static boolean isValidItem(ItemStack stack) {
        // return stack.getItem() instanceof MenuItem;
        return false;
    }

    private boolean canTake(int slotId, Slot slot, int button, Player player, ClickType clickType) {

        return false;

        /*
        if (slotId == blocked || slotId <= itemHandler.getSlots() - 1 && isValidItem(this.getCarried())) {
            return false;
        }
        if(!filtered(slotId, this.getCarried().getItem())) {
            return false;
        }

        // Hotbar swapping via number keys
        if (clickType == ClickType.SWAP) {
            int hotbarId = itemHandler.getSlots() + 27 + button;
            // Block swapping with container
            if (blocked == hotbarId) {
                return false;
            }

            Slot hotbarSlot = getSlot(hotbarId);
            if (slotId <= itemHandler.getSlots() - 1) {
                return !isValidItem(slot.getItem()) && !isValidItem(hotbarSlot.getItem()) && filtered(slotId, hotbarSlot.getItem().getItem());
            }
        }

        return true;
        */

    }

    //For filtering what items are allowed in the Satchel
    private boolean filtered(int slotId, Item item) {
        return false;

        /*
        if(slotId < itemHandler.getSlots()) {
            if(slotId < satchelSegmentSize && !(item instanceof Item))
                return false;
        }
        return true;
         */

    }


}
