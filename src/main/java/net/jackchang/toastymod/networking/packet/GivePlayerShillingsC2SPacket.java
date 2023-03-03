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

    public GivePlayerShillingsC2SPacket() {
        amount = 1;
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
            // on the server
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> {
                playerData.increaseShillings(amount);
                player.sendSystemMessage(Component.literal("You gained a shilling!").withStyle(ChatFormatting.GOLD));
                ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(playerData), player);
            });
        });
        return true;
    }
}
