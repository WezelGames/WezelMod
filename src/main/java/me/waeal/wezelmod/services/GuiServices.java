package me.waeal.wezelmod.services;

import gg.essential.elementa.components.ScrollComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.Window;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ConstantColorConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.SiblingConstraint;
import gg.essential.vigilance.gui.settings.ButtonComponent;
import java.util.ArrayList;
import java.util.List;
import me.waeal.wezelmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiServices {
    private static List<ItemStack> lastInv = new ArrayList<>();

    public static boolean isUpdatedInv(GuiChest gui) {
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

    public static ScrollComponent prepWindow(String name, PixelConstraint height, Window window, boolean back) {
        window.clearChildren();

        // Background Panel
        UIBlock background = (UIBlock) new UIBlock()
                .setColor(new ConstantColorConstraint(Main.settings.macroBackgroundColor))
                .setX(new PixelConstraint(0))
                .setY(new PixelConstraint(0))
                .setWidth(new PixelConstraint(window.getWidth()))
                .setHeight(new PixelConstraint(window.getHeight()));
        window.addChild(background);

        // BackButton
        if (back) {
            ButtonComponent backButton = (ButtonComponent) new ButtonComponent("Back", () -> null)
                    .setX(new PixelConstraint(10))
                    .setY(new PixelConstraint(10));
            backButton.onMouseClickConsumer(event -> WezelServices.openMacroNavGui());
            background.addChild(backButton);
        }

        // Title
        UIText title = (UIText) new UIText(name)
                .setX(new CenterConstraint())
                .setY(new PixelConstraint(10));
        background.addChild(title);

        // Scrollable Main body
        ScrollComponent scrollComponent = (ScrollComponent) new ScrollComponent()
                .setX(new PixelConstraint(10))
                .setY(new SiblingConstraint(10))
                .setWidth(new PixelConstraint(window.getWidth() - 20))
                .setHeight(height);
        background.addChild(scrollComponent);

        return scrollComponent;
    }
}
