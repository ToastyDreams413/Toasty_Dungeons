package net.jackchang.toastymod.custom_attributes.shillings;

import net.minecraft.nbt.CompoundTag;

public class PlayerShillings {

    private int shillings;
    private final int MIN_SHILLINGS = 0;
    private final int MAX_SHILLINGS = 999999999;

    public int getShillings() {
        return shillings;
    }

    public void addShillings(int add) {
        this.shillings = Math.min(shillings + add, MAX_SHILLINGS);
    }

    public void subShillings(int sub) {
        this.shillings = Math.max(shillings - sub, MIN_SHILLINGS);
    }

    public void copyFrom(PlayerShillings source) {
        this.shillings = source.shillings;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("shillings", shillings);
    }

    public void loadNBTData(CompoundTag nbt) {
        shillings = nbt.getInt("shillings");
    }

}
