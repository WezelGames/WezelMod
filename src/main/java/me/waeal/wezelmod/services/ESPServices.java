package me.waeal.wezelmod.services;

import java.awt.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class ESPServices {
    public static void drawFilledAABB(AxisAlignedBB aabb, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        worldRenderer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);

        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex(); // NORTH-DOWN-WEST SIDES

        tessellator.draw();
        worldRenderer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);

        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex(); // EAST-UP-SOUTH SIDES

        tessellator.draw();
    }

    public static void drawOutlinedAABB(AxisAlignedBB aabb, Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);

        worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);

        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();

        tessellator.draw();
    }

    public static void drawEntityBox(Entity entity, float partialTicks, int espSetting, Color espColor) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - renderManager.viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - renderManager.viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - renderManager.viewerPosZ;

        AxisAlignedBB aabb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - entity.posX,
                                               entity.getEntityBoundingBox().minY - entity.posY,
                                               entity.getEntityBoundingBox().minZ - entity.posZ,
                                               entity.getEntityBoundingBox().maxX - entity.posX,
                                               entity.getEntityBoundingBox().maxY - entity.posY,
                                               entity.getEntityBoundingBox().maxZ - entity.posZ).offset(x, y, z);

        drawAABB(aabb, espSetting, espColor);
    }

    public static void drawBlockBox(BlockPos pos, int espSetting, Color espColor) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double x = pos.getX() - renderManager.viewerPosX;
        double y = pos.getY() - renderManager.viewerPosY;
        double z = pos.getZ() - renderManager.viewerPosZ;

        AxisAlignedBB aabb = new AxisAlignedBB(0, 0, 0, 1, 1, 1).offset(x, y, z);

        drawAABB(aabb, espSetting, espColor);
    }

    public static void drawArea(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, int espSetting, Color espColor) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double x = minX - renderManager.viewerPosX;
        double y = minY - renderManager.viewerPosY;
        double z = minZ - renderManager.viewerPosZ;

        AxisAlignedBB aabb = new AxisAlignedBB(0, 0, 0, maxX - minX, maxY - minY, maxZ - minZ).offset(x, y, z);

        drawAABB(aabb, espSetting, espColor);
    }

    private static void drawAABB(AxisAlignedBB aabb, int setting, Color color) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);

        if (setting == 1 || setting == 3) {
            GL11.glLineWidth(2f);
            drawOutlinedAABB(aabb, color);
        }
        if (setting > 1)
            drawFilledAABB(aabb, color);

        GL11.glDepthMask(true);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
}
