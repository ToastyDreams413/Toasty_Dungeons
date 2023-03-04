package net.jackchang.toastymod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CustomChausses extends ArmorItem {

    private String description;

    public CustomChausses(ArmorMaterial armorMaterial, Properties properties, String description) {
        super(armorMaterial, EquipmentSlot.LEGS, properties);
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        components.add(Component.literal(this.description).withStyle(ChatFormatting.AQUA));

        super.appendHoverText(stack, level, components, flag);
    }
}
