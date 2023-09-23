package me.waeal.bootymod.services;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.*;

import java.awt.Color;

import gg.essential.elementa.constraints.ConstantColorConstraint;
import gg.essential.elementa.constraints.FillConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.vigilance.gui.settings.ButtonComponent;
import me.waeal.bootymod.objects.ItemComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.springframework.stereotype.Service;

@Service
public class ProfileServices {
    public void showInventory(PlayerControllerMP player) {
        Minecraft.getMinecraft().displayGuiScreen(new InventoryGui());
    }
}

class Gui extends WindowScreen {
    protected UIComponent info = new UIContainer().setX(new PixelConstraint(getWindow().getWidth() * 0.03f + 80)).setY(new PixelConstraint(getWindow().getHeight() * 0.08f + 5)).setWidth(new PixelConstraint(getWindow().getWidth() * 0.03f)).setHeight(new PixelConstraint(getWindow().getHeight() * 0.08f + 5));
    public Gui() {
        super(ElementaVersion.V3);

        drawMenu();
        drawInfo(getWindow().getWidth() * 0.03f + 80, getWindow().getHeight() * 0.08f + 5, getWindow().getWidth() * 0.03f, getWindow().getHeight() * 0.08f + 5);
    }

    protected void drawInfo(float x, float y, float width, float height) {

    }

    private void drawMenu() {
        UIComponent background = new UIContainer().setHeight(new FillConstraint()).setWidth(new FillConstraint()).setX(new PixelConstraint(0)).setY(new PixelConstraint(0)).setChildOf(getWindow());

        new GradientComponent(new Color(0xC75EC4), Color.BLACK, GradientComponent.GradientDirection.BOTTOM_TO_TOP).setHeight(new PixelConstraint(getWindow().getHeight())).setWidth(new PixelConstraint(getWindow().getWidth())).setChildOf(background);
        new UIRoundedRectangle(5f).setColor(new Color(0x1C1C1C)).setX(new PixelConstraint(getWindow().getWidth() * 0.02f)).setY(new PixelConstraint(getWindow().getHeight() * 0.02f)).setHeight(new PixelConstraint(getWindow().getHeight() * 0.96f)).setWidth(new PixelConstraint(getWindow().getWidth() * 0.96f)).setChildOf(background);
        new UIBlock().setColor(new Color(0x5C5C5C)).setX(new PixelConstraint(getWindow().getWidth() * 0.02f)).setY(new PixelConstraint(getWindow().getHeight() * 0.07f)).setHeight(new PixelConstraint(3)).setWidth(new PixelConstraint(getWindow().getWidth() * 0.96f)).setChildOf(background);
        UIComponent header = new UIText("Booty").setColor(new Color(0xC75EC4)).setX(new PixelConstraint(getWindow().getWidth() * 0.03f)).setY(new PixelConstraint(getWindow().getHeight() * 0.03f)).setChildOf(background);
        header.setTextScale(new PixelConstraint(getWindow().getHeight() * 0.04f / header.getHeight()));

        addMenuButton("Inventory", getWindow().getHeight() * 0.08f + 5, () -> Minecraft.getMinecraft().displayGuiScreen(new InventoryGui()), background);
        addMenuButton("Catacombs", getWindow().getHeight() * 0.08f + 28, () -> Minecraft.getMinecraft().displayGuiScreen(new CatacombsGui()), background);
        addMenuButton("Pets", getWindow().getHeight() * 0.08f + 51, () -> Minecraft.getMinecraft().displayGuiScreen(new PetsGui()), background);

        info.setChildOf(background);
    }

    private void addMenuButton(String text, float y, Runnable executable, UIComponent parent) {
        UIComponent btn = new ButtonComponent(text, () -> {executable.run();
        return null;}).setColor(new Color(0x5EC7C7)).setX(new PixelConstraint(getWindow().getWidth() * 0.03f)).setY(new PixelConstraint(y)).setChildOf(parent);
        btn.onMouseEnterRunnable(() -> {
            AnimatingConstraints anim = btn.makeAnimation();
            anim.setColorAnimation(Animations.IN_CIRCULAR, 0.5f, new ConstantColorConstraint(new Color(0x4BE6B6)));
            btn.animateTo(anim);
        });
        btn.onMouseLeaveRunnable(() -> {
            AnimatingConstraints anim = btn.makeAnimation();
            anim.setColorAnimation(Animations.IN_CIRCULAR, 0.5f, new ConstantColorConstraint(new Color(0x5EC7C7)));
            btn.animateTo(anim);
        });
    }
}

class InventoryGui extends Gui {
    @Override
    protected void drawInfo(float x, float y, float width, float height) {
        ItemStack test = new ItemStack(Item.getItemById(2), 2);
        test.setStackDisplayName("Test Sword");
        ItemComponent item = new ItemComponent(test);
        item.addTooltip();
        item.setX(new PixelConstraint(x)).setY(new PixelConstraint(y)).setHeight(new PixelConstraint(60)).setWidth(new PixelConstraint(60)).setChildOf(info);
    }
}

class CatacombsGui extends Gui {
}

class PetsGui extends Gui {
}

