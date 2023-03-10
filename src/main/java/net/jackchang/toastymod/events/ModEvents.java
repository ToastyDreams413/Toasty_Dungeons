package net.jackchang.toastymod.events;

import net.jackchang.toastymod.ToastyMod;
import net.jackchang.toastymod.command.AdminGiveCommand;
import net.jackchang.toastymod.command.ChatCommands;
import net.jackchang.toastymod.command.GiftCommand;
import net.jackchang.toastymod.command.PartyCommands;
import net.jackchang.toastymod.command.WarpCommands;
import net.jackchang.toastymod.custom_attributes.PlayerData;
import net.jackchang.toastymod.custom_attributes.PlayerDataProvider;
import net.jackchang.toastymod.data.ArmorData;
import net.jackchang.toastymod.data.SwordData;
import net.jackchang.toastymod.item.custom.CustomSword;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.PlayerDataSyncS2CPacket;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber(modid = ToastyMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new WarpCommands(event.getDispatcher());
        new GiftCommand(event.getDispatcher());
        new AdminGiveCommand(event.getDispatcher());
        new PartyCommands(event.getDispatcher());
        new ChatCommands(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerDataProvider.PLAYER_DATA).isPresent()) {
                event.addCapability(new ResourceLocation(ToastyMod.MOD_ID, "properties"), new PlayerDataProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getEntity().sendSystemMessage(Component.literal("Great job clicking that respawn button."));

            event.getOriginal().reviveCaps();

            event.getOriginal().getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            event.getOriginal().invalidateCaps();
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerData.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> {
                    if (player.getName().getString().equals("ToastyDreams")) {
                        data.setRank(10);
                    }
                    ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(data), player);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();

        player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(playerData -> { 
            if (playerData.getSelectedChat().equals("party")) {
                ServerLevel level = player.getLevel();
                for (UUID partyMemberUUID : playerData.getPartyMembers()) {
                    ServerPlayer partyMember = (ServerPlayer) level.getPlayerByUUID(partyMemberUUID);

                    partyMember.sendSystemMessage(Component.literal("Party <" + player.getName().getString() + "> " + event.getMessage()));
                }

                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Entity mob = event.getSource().getEntity();
            // player.sendSystemMessage(Component.literal("Player hurt!"));
            for (ItemStack armorPiece : player.getArmorSlots()) {
                String curArmor = armorPiece.getDisplayName().getString();
                curArmor = curArmor.substring(1, curArmor.length() - 1);
            }
            // player.hurt(event.getSource(), 2);
        }
    }

    @SubscribeEvent
    public static void onMeleeDamageMob(AttackEntityEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Entity target = event.getTarget();
            Item usedItem = player.getMainHandItem().getItem();
            int damageCalc = 0;
            AtomicInteger insideAtk = new AtomicInteger();
            player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> {
                insideAtk.set(data.getAtk());
            });
            damageCalc += insideAtk.get();
            for (ItemStack armorPiece : player.getArmorSlots()) {
                String curArmor = armorPiece.toString().substring(2);
                damageCalc += ArmorData.ARMOR_ATTACK.get(curArmor);
            }
            if (usedItem instanceof CustomSword) {
                damageCalc += SwordData.SWORD_DAMAGE.get(usedItem.toString());
            }
            player.sendSystemMessage(Component.literal("Your max damage: " + damageCalc));
            damageCalc *= player.getAttackStrengthScale(0);
            player.sendSystemMessage(Component.literal("Your real damage based on charging time: " + damageCalc));
            target.hurt(DamageSource.playerAttack(player), damageCalc);
        }
    }
}
