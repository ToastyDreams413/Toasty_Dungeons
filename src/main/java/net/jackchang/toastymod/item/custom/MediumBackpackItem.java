package net.jackchang.toastymod.item.custom;

import net.jackchang.toastymod.gui.backpack.medium_backpack.MediumBackpackContainer;
import net.jackchang.toastymod.gui.backpack.medium_backpack.MediumBackpackData;
import net.jackchang.toastymod.gui.backpack.medium_backpack.MediumBackpackManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class MediumBackpackItem extends Item {

    public MediumBackpackItem(Properties properties) {
        super(properties);
    }

    public int getInventorySize(ItemStack stack) {
        return 24;
    }

    public IItemHandler getInventory(ItemStack stack) {
        if(stack.isEmpty())
            return null;
        ItemStackHandler stackHandler = new ItemStackHandler(getInventorySize(stack));
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("inventory"));
        return stackHandler;
    }

    public static MediumBackpackData getData(ItemStack stack) {
        UUID uuid;
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("UUID")) {
            uuid = UUID.randomUUID();
            tag.putUUID("UUID", uuid);
        } else
            uuid = tag.getUUID("UUID");
        return MediumBackpackManager.get().getOrCreateBackpack(uuid);
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            ItemStack backpack = player.getItemInHand(hand);
            MediumBackpackData data = MediumBackpackItem.getData(backpack);
            NetworkHooks.openScreen(((ServerPlayer) player), new SimpleMenuProvider( (windowId, playerInventory, playerEntity) ->
                    new MediumBackpackContainer(windowId, playerInventory, data.getHandler()), backpack.getHoverName()));
        }

        return super.use(level, player, hand);
    }

}
