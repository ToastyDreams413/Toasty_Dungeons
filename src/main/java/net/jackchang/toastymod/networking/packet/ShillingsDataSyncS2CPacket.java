package net.jackchang.toastymod.networking.packet;

import net.jackchang.toastymod.client.ClientShillingsData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShillingsDataSyncS2CPacket {
    private final int shillings;

    public ShillingsDataSyncS2CPacket(int shillings) {
        this.shillings = shillings;
    }

    public ShillingsDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.shillings = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(shillings);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientShillingsData.set(shillings);
        });
        return true;
    }
}
