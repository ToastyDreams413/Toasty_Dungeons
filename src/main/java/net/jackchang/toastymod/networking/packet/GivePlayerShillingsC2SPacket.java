package net.jackchang.toastymod.networking.packet;

import net.jackchang.toastymod.custom_attributes.shillings.PlayerShillingsProvider;
import net.jackchang.toastymod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GivePlayerShillingsC2SPacket {
    public GivePlayerShillingsC2SPacket() {

    }

    public GivePlayerShillingsC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // this is on the server
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();


            player.getCapability(PlayerShillingsProvider.PLAYER_SHILLINGS).ifPresent(shillings -> {
                shillings.addShillings(1);
                player.sendSystemMessage(Component.literal("You gained 1 shilling! You now have " + shillings.getShillings()
                + " total shillings.").withStyle(ChatFormatting.GOLD));
                ModMessages.sendToPlayer(new ShillingsDataSyncS2CPacket(shillings.getShillings()), player);
            });
        });
        return true;
    }
}
