package net.jackchang.toastymod.custom_attributes.shillings;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerShillingsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerShillings> PLAYER_SHILLINGS = CapabilityManager.get(new CapabilityToken<PlayerShillings>() { });

    private PlayerShillings shillings = null;
    private final LazyOptional<PlayerShillings> optional = LazyOptional.of(this::createPlayerShillings);

    private PlayerShillings createPlayerShillings() {
        if (this.shillings == null) {
            this.shillings = new PlayerShillings();
        }

        return this.shillings;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_SHILLINGS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerShillings().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerShillings().loadNBTData(nbt);
    }
}
