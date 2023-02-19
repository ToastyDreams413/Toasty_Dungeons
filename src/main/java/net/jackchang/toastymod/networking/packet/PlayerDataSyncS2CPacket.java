package net.jackchang.toastymod.networking.packet;

import net.jackchang.toastymod.client.ClientPlayerData;
import net.jackchang.toastymod.custom_attributes.PlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerDataSyncS2CPacket {
    private final PlayerData playerData;

    public PlayerDataSyncS2CPacket(PlayerData playerData) {
        this.playerData = playerData;
    }

    public PlayerDataSyncS2CPacket(FriendlyByteBuf buf) {
        playerData = new PlayerData(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(playerData.getShillings());
        buf.writeInt(playerData.getRank());
        buf.writeInt(playerData.getSelectedClassIndex());
        buf.writeInt(playerData.getTotalClasses());
        buf.writeInt(playerData.getMaxClasses());
        buf.writeInt(playerData.getPlayerXP());
        buf.writeInt(playerData.getPlayerLevel());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // this is on the client
            ClientPlayerData.set(playerData);
        });
        return true;
    }
}
