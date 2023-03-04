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
        playerData = new PlayerData();
        playerData.setShillings(buf.readInt());
        playerData.setGorbs(buf.readInt());
        playerData.setRank(buf.readInt());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(playerData.getShillings());
        buf.writeInt(playerData.getGorbs());
        buf.writeInt(playerData.getRank());
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
