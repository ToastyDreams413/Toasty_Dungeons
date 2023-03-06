package net.jackchang.toastymod.gui.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.jackchang.toastymod.ToastyMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class MenuScreen extends AbstractContainerScreen<MenuContainer> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ToastyMod.MOD_ID, "textures/gui/backpack.png");

    public MenuScreen(MenuContainer container, Inventory playerInventory, Component name) {
        super(container, playerInventory, name);

        this.imageWidth = 176;
        this.imageHeight = 150;

    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(matrixStack, this.leftPos, this.topPos, 0,0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title.getString(), 7,6,0x404040);
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(matrixStack, pMouseX, pMouseY);
    }
}
