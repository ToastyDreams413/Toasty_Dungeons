package net.jackchang.toastymod.item.custom;

import net.jackchang.toastymod.custom_attributes.PlayerDataProvider;
import net.jackchang.toastymod.data.ArmorData;
import net.jackchang.toastymod.data.SwordData;
import net.jackchang.toastymod.networking.ModMessages;
import net.jackchang.toastymod.networking.packet.PlayerDataSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class StatCheckerItem extends Item {

    private int atk, def;

    public StatCheckerItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        Player player = Minecraft.getInstance().player;
        player.getCapability(PlayerDataProvider.PLAYER_DATA).ifPresent(data -> {
            atk = data.getAtk();
            def = data.getDef();
        });

        for (ItemStack armorPiece : player.getArmorSlots()) {
            String curArmor = armorPiece.toString().substring(2);
            atk += ArmorData.ARMOR_ATTACK.get(curArmor);
            def += ArmorData.ARMOR_DEFENSE.get(curArmor);
        }

        Item currentItem = player.getMainHandItem().getItem();

        components.clear();
        components.add(Component.literal(player.getName().getString()).withStyle(ChatFormatting.DARK_AQUA));
        components.add(Component.literal(" "));
        components.add(Component.literal("Attack: " + atk).withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("Defense: " + def).withStyle(ChatFormatting.LIGHT_PURPLE));

        super.appendHoverText(stack, level, components, flag);
    }

}
