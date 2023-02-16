package net.jackchang.toastymod.events;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.command.GiftCommand;
import net.jackchang.toastymod.command.WarpCommands;
import net.jackchang.toastymod.custom_attributes.shillings.PlayerShillings;
import net.jackchang.toastymod.custom_attributes.shillings.PlayerShillingsProvider;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.ShillingsDataSyncS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = ToastyMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new WarpCommands(event.getDispatcher());
        new GiftCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerShillingsProvider.PLAYER_SHILLINGS).isPresent()) {
                event.addCapability(new ResourceLocation(ToastyMod.MOD_ID, "properties"), new PlayerShillingsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getEntity().sendSystemMessage(Component.literal("Great job clicking that respawn button."));

            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(PlayerShillingsProvider.PLAYER_SHILLINGS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerShillingsProvider.PLAYER_SHILLINGS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerShillings.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerShillingsProvider.PLAYER_SHILLINGS).ifPresent(shillings -> {
                    ModMessages.sendToPlayer(new ShillingsDataSyncS2CPacket(shillings.getShillings()), player);
                });
            }
        }
    }
}
