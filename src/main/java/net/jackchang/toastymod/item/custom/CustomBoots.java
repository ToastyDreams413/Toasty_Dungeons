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

public class CustomBoots extends ArmorItem {

    private String description;

    public CustomBoots(ArmorMaterial armorMaterial, Properties properties, String description) {
        super(armorMaterial, EquipmentSlot.FEET, properties);
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        if (Screen.hasShiftDown()) {
            components.add(Component.literal(this.description).withStyle(ChatFormatting.AQUA));
        }
        else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}