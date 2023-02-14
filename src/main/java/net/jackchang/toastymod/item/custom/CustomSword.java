package net.jackchang.toastymod.item.custom;

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

public class CustomSword extends SwordItem {

    private String description;
    public CustomSword(Tier tier, int dmg, float attackSpeed, Properties properties, String description) {
        super(tier, dmg, attackSpeed, properties);
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
