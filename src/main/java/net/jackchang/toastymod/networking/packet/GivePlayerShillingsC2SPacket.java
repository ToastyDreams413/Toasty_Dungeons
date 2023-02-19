package net.jackchang.toastymod.networking.packet;

import net.jackchang.toastymod.custom_attributes.PlayerDataProvider;
import net.jackchang.toastymod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class GivePlayerShillingsC2SPacket {
    public int amount;
    public UUID targetPlayerUUID;

    public ServerPlayer targetPlayer;
    public boolean hasDifferentTarget;

    public GivePlayerShillingsC2SPacket() {
        amount = 1;
        //targetPlayer = null;
        hasDifferentTarget = false;
    }

    public GivePlayerShillingsC2SPacket(int amount, UUID targetPlayerUUID) {
        this.amount = amount;
        this.targetPlayerUUID = targetPlayerUUID;
        hasDifferentTarget = true;
    }

    public GivePlayerShillingsC2SPacket(FriendlyByteBuf buf) {
        hasDifferentTarget = buf.readBoolean();
        if (hasDifferentTarget) {
            targetPlayerUUID = buf.readUUID();
        }
        amount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(hasDifferentTarget);
        if (hasDifferentTarget) {
            buf.writeUUID(targetPlayerUUID);
        }
        buf.writeInt(amount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer originalPlayer = context.getSender();
            ServerLevel originalLevel = originalPlayer.getLevel();
            if (hasDifferentTarget) {
                originalPlayer.sendSystemMessage(Component.literal("You are finding a player..."));
                targetPlayer = (ServerPlayer) originalLevel.getPlayerByUUID(targetPlayerUUID);
            }
            else {
                targetPlayer = originalPlayer;
            }

            targetPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                playerData.increaseShillings(amount);
                if (hasDifferentTarget) {
                    originalPlayer.sendSystemMessage(Component.literal("You have sent shillings to " + targetPlayer.getName().getString()).withStyle(ChatFormatting.GOLD));
                    targetPlayer.sendSystemMessage(Component.literal("You gained " + amount + " shillings from " + originalPlayer.getName().getString()).withStyle(ChatFormatting.GOLD));
                }
                else {
                    originalPlayer.sendSystemMessage(Component.literal("You gained a shilling!").withStyle(ChatFormatting.GOLD));
                }
                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), targetPlayer);
            });
        });
        return true;
    }
}
