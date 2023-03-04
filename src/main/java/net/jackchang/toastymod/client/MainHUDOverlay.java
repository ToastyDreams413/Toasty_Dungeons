package net.jackchang.toastymod.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MainHUDOverlay {
    public static final IGuiOverlay HUD_SHILLINGS = ((gui, poseStack, partialTick, width, height) -> {

        Minecraft.getInstance().font.draw(poseStack, "Shillings: " + ClientPlayerData.getPlayerData().getShillings(), width / 2 - 90, height / 8 * 6.6f, 16776960);
        Minecraft.getInstance().font.draw(poseStack, "Gorbs: " + ClientPlayerData.getPlayerData().getGorbs(), width / 2 - 90, height / 8 * 6.9f, 13415451);

    });

}
