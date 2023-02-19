package net.jackchang.toastymod.networking.packet;

import net.jackchang.toastymod.custom_attributes.PlayerDataProvider;
import net.jackchang.toastymod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GivePlayerShillingsC2SPacket {
    public int amount;
    public ServerPlayer targetPlayer;
    public GivePlayerShillingsC2SPacket() {
        amount = 1;
        //targetPlayer = null;
    }

    public GivePlayerShillingsC2SPacket(int amount) {
        this.amount = amount;
        //targetPlayer = null;
    }

    public GivePlayerShillingsC2SPacket(int amount, ServerPlayer targetPlayer) {
        this.amount = amount;
        //this.targetPlayer = targetPlayer;
        System.out.println("AMOUNT: " + this.amount);
    }

    public GivePlayerShillingsC2SPacket(FriendlyByteBuf buf) {
        amount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(amount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // this is on the server
            // if (getTargetPlayer() == null)
            //    setTargetPlayer(context.getSender());
            ServerPlayer targetPlayer = context.getSender();
            //ServerLevel level = getTargetPlayer().getLevel();

            targetPlayer.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                playerData.increaseShillings(amount);
                targetPlayer.sendSystemMessage(Component.literal("You gained " + amount + " shillings! You now have " + playerData.getShillings()
                + " total shillings.").withStyle(ChatFormatting.GOLD));
                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), targetPlayer);
            });
        });
        return true;
    }
}
