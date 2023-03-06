package net.jackchang.toastymod.gui.backpack.medium_backpack;

import net.jackchang.toastymod.ToastyMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.util.thread.SidedThreadGroups;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.UUID;

public class MediumBackpackManager extends SavedData {
    private static final String NAME = ToastyMod.MOD_ID + "_medium_backpack_data";

    private static final HashMap<UUID, MediumBackpackData> data = new HashMap<>();

    public static final MediumBackpackManager blankClient = new MediumBackpackManager();

    public HashMap<UUID, MediumBackpackData> getMap() { return data; }

    public static MediumBackpackManager get() {
        if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
            return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(MediumBackpackManager::load, MediumBackpackManager::new, NAME);
        else
            return blankClient;
    }

    public MediumBackpackData getOrCreateBackpack(UUID uuid) {
        return data.computeIfAbsent(uuid, id -> {
            setDirty();
            return new MediumBackpackData(id);
        });
    }

    public LazyOptional<IItemHandler> getCapability(UUID uuid) {
        if (data.containsKey(uuid))
            return data.get(uuid).getOptional();

        return LazyOptional.empty();
    }

    public LazyOptional<IItemHandler> getCapability(ItemStack stack) {
        if (stack.getOrCreateTag().contains("UUID")) {
            UUID uuid = stack.getTag().getUUID("UUID");
            if (data.containsKey(uuid))
                return data.get(uuid).getOptional();
        }

        return LazyOptional.empty();
    }

    public static MediumBackpackManager load(CompoundTag nbt) {
        if (nbt.contains("Backpacks")) {
            ListTag list = nbt.getList("Backpacks", Tag.TAG_COMPOUND);
            list.forEach((backpackNBT) -> MediumBackpackData.fromNBT((CompoundTag) backpackNBT).ifPresent((backpack) -> data.put(backpack.getUuid(), backpack)));
        }
        return new MediumBackpackManager();
    }

    @Override
    @Nonnull
    public CompoundTag save(CompoundTag compound) {
        ListTag backpacks = new ListTag();
        data.forEach(((uuid, backpackData) -> backpacks.add(backpackData.toNBT())));
        compound.put("Backpacks", backpacks);
        return compound;
    }
}
