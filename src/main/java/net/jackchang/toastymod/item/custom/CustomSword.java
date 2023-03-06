package net.jackchang.toastymod.item.custom;

import net.jackchang.toastymod.data.SwordData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.lang.*;
import java.util.Optional;

public class CustomSword extends SwordItem {

    private String description;

    private String name;

    public CustomSword(String name, Tier tier, Properties properties, String description) {
        super(tier, SwordData.SWORD_DAMAGE.get(name), SwordData.calculateAttackSpeedFromCooldown(SwordData.SWORD_COOLDOWN.get(name)) - 4, properties);
        this.description = description;
        this.name = name;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {

        stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        components.add(Component.literal(" "));

        components.add(Component.literal(this.description).withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" "));
        components.add(Component.literal("Damage: " + SwordData.SWORD_DAMAGE.get(name)).withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("Full Charge Time: " + SwordData.SWORD_COOLDOWN.get(name) + " seconds").withStyle(ChatFormatting.LIGHT_PURPLE));

        super.appendHoverText(stack, level, components, flag);
    }

}
