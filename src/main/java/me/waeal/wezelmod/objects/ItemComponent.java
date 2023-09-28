package me.waeal.wezelmod.objects;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.state.BasicState;
import gg.essential.elementa.state.State;
import gg.essential.universal.UMatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemComponent extends UIComponent {
    private State<ItemStack> stack;

    public ItemComponent(ItemStack stack) {
        this.stack = new BasicState<>(stack);
    }

    @Override
    public void draw(@NotNull UMatrixStack matrix) {
        beforeDraw(matrix);
        super.draw(matrix);

        ItemStack item = stack.get();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(item, (int) getLeft(), (int) getTop());

        FontRenderer renderer = item.getItem().getFontRenderer(item);
        if (renderer == null)
            renderer = Minecraft.getMinecraft().fontRendererObj;

        Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(renderer, item, (int) getLeft(), (int) getTop(), null);

    }

    public void addTooltip() {
        this.onMouseEnter((a)-> {
            return null;
        });
    }
}