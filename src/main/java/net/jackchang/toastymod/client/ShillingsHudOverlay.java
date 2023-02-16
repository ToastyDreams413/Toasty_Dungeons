package net.jackchang.toastymod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ShillingsHudOverlay {
    public static final IGuiOverlay HUD_SHILLINGS = ((gui, poseStack, partialTick, width, height) -> {

        Minecraft.getInstance().font.draw(poseStack, "Shillings: " + ClientShillingsData.getPlayerShillings(), width / 2 - 90, height / 8 * 7, 16776960);

    });

}
