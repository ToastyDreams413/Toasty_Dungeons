package net.jackchang.toastymod.networking.packet;

import net.jackchang.toastymod.client.ClientPlayerData;
import net.jackchang.toastymod.custom_attributes.PlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.UUID;
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

        // party
        Boolean inParty = buf.readBoolean();
        playerData.setInParty(inParty);
        if (inParty) {
            playerData.setPartyLeader(buf.readUUID());
            
            // decode all party members
            int partyMemberLength = buf.readInt();
            ArrayList<UUID> partyMembers = new ArrayList<UUID>();
            for (int i = 0; i < partyMemberLength; i++) {
                partyMembers.add(buf.readUUID());
            }

            playerData.setPartyMembers(partyMembers);

            // decode all party invites sent
            int partyInvitesSentLength = buf.readInt();
            ArrayList<UUID> partyInvitesSent = new ArrayList<UUID>();
            for (int i = 0; i < partyInvitesSentLength; i++) {
                partyInvitesSent.add(buf.readUUID());
            }

            playerData.setPartyInvitesSent(partyInvitesSent);
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(playerData.getShillings());
        buf.writeInt(playerData.getGorbs());
        buf.writeInt(playerData.getRank());

        // party
        Boolean inParty = playerData.getInParty();
        buf.writeBoolean(inParty);
        if (inParty) {
            buf.writeUUID(playerData.getPartyLeader());
            
            // encode all party members
            buf.writeInt(playerData.getPartyMembers().size());
            for (UUID partyMemberUUID : playerData.getPartyMembers()) {
                buf.writeUUID(partyMemberUUID);
            }

            // encode all party invites sent
            buf.writeInt(playerData.getPartyInvitesSent().size());
            for (UUID partyInviteSentUUID : playerData.getPartyInvitesSent()) {
                buf.writeUUID(partyInviteSentUUID);
            }
        }
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
