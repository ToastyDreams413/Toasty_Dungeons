package net.jackchang.toastymod.item.custom;

import net.jackchang.toastymod.data.ArmorData;
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
    private String name;
    private int def, atk;

    public CustomChausses(String name, ArmorMaterial armorMaterial, Properties properties, String description) {
        super(armorMaterial, EquipmentSlot.LEGS, properties);
        this.description = description;
        this.name = name;
        this.def = ArmorData.ARMOR_DEFENSE.get(name);
        this.atk = ArmorData.ARMOR_ATTACK.get(name);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        components.add(Component.literal(""));
        components.add(Component.literal(this.description).withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(""));
        // components.add(Component.literal("On Equip:"));
        components.add(Component.literal("+" + this.def + " Defense").withStyle(ChatFormatting.LIGHT_PURPLE));
        if (this.atk != 0) {
            components.add(Component.literal("+" + this.atk + " Attack").withStyle(ChatFormatting.LIGHT_PURPLE));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}
