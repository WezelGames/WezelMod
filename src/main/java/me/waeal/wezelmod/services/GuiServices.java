package me.waeal.wezelmod.services;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.springframework.stereotype.Service;

@Service
public class GuiServices {
    private List<ItemStack> lastInv = new ArrayList<>();

    public boolean updatedInv(GuiChest gui) {
        if (lastInv.equals(gui.inventorySlots.getInventory()))
            return false;
        lastInv = gui.inventorySlots.getInventory();
        return true;
    }

    public boolean itemEquals(GuiChest gui, int i, int item) {
        return getItem(gui, i) == Item.getItemById(item);
    }

    public boolean paneEquals(GuiChest gui, int i, int paneColor) {
        if (!itemEquals(gui, i, 160)) // glass pane
            return false;

        return getStack(gui, i).getItemDamage() == paneColor;
    }

    public ItemStack getStack(GuiChest gui, int i) {
        return gui.inventorySlots.getInventory().get(i);
    }

    public Item getItem(GuiChest gui, int i) {
        if (getStack(gui, i) != null)
            return getStack(gui, i).getItem();
        return null;
    }

    public String getName(GuiChest gui) {
        return ((ContainerChest) gui.inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();
    }

    public boolean isChest(GuiScreen gui) {
        return gui instanceof GuiChest;
    }

    public void click(int slot) {
        click(slot, 2, 3);
    }

    public void click(int slot, int click, int mode) {
        Minecraft.getMinecraft().playerController.windowClick(
                Minecraft.getMinecraft().thePlayer.openContainer.windowId,
                slot, click, mode, Minecraft.getMinecraft().thePlayer);
    }
}
