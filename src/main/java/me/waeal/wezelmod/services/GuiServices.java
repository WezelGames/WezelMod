package me.waeal.wezelmod.services;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiServices {
    private static List<ItemStack> lastInv = new ArrayList<>();

    public static boolean updatedInv(GuiChest gui) {
        if (lastInv.equals(gui.inventorySlots.getInventory()))
            return false;
        lastInv = gui.inventorySlots.getInventory();
        return true;
    }

    public static boolean itemEquals(GuiChest gui, int i, int item) {
        return getItem(gui, i) == Item.getItemById(item);
    }

    public static boolean paneEquals(GuiChest gui, int i, int paneColor) {
        if (!itemEquals(gui, i, 160)) // glass pane
            return false;

        return getStack(gui, i).getItemDamage() == paneColor;
    }

    public static ItemStack getStack(GuiChest gui, int i) {
        return gui.inventorySlots.getInventory().get(i);
    }

    public static Item getItem(GuiChest gui, int i) {
        if (getStack(gui, i) != null)
            return getStack(gui, i).getItem();
        return null;
    }

    public static String getName(GuiChest gui) {
        return ((ContainerChest) gui.inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();
    }

    public static boolean isChest(GuiScreen gui) {
        return gui instanceof GuiChest;
    }

    public static void click(int slot) {
        click(slot, 2, 3);
    }

    public static void click(int slot, int click, int mode) {
        Minecraft.getMinecraft().playerController.windowClick(
                Minecraft.getMinecraft().thePlayer.openContainer.windowId,
                slot, click, mode, Minecraft.getMinecraft().thePlayer);
    }
}
