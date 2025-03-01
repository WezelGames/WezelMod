package me.waeal.wezelmod.objects.macros;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.*;
import gg.essential.elementa.components.input.AbstractTextInput;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.vigilance.gui.settings.ButtonComponent;
import java.awt.*;
import java.util.Map;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.GuiServices;
import me.waeal.wezelmod.services.WezelServices;

public class MacroNavGuiScreen extends WindowScreen {

    public MacroNavGuiScreen() {
        super(ElementaVersion.V7, true, false, false, -1);
    }

    @Override
    public void initScreen(int width, int height) {
        ScrollComponent categoryList = GuiServices.prepWindow("Macro Manager", new PixelConstraint(getWindow().getHeight() - 60), getWindow(), false);

        // Populate Categories
        for (Map.Entry<String, MacroCategory> entry : Main.macroConfig.getCategories().entrySet()) {
            String categoryName = entry.getKey();
            MacroCategory category = entry.getValue();

            UIBlock categoryHeader = (UIBlock) new UIBlock()
                    .setColor(new ConstantColorConstraint(new Color(0, 0, 0, 145)))
                    .setX(new PixelConstraint(0))
                    .setY(new SiblingConstraint(5))
                    .setWidth(new PixelConstraint(this.getWindow().getWidth() - 20))
                    .setHeight(new PixelConstraint(30));
            categoryHeader.onMouseClickConsumer(event -> {
                category.toggleExpanded();
                MacroConfigManager.saveConfig(Main.macroConfig);
                initScreen(width, height);
            });
            categoryList.addChild(categoryHeader);

            // Category Name Display
            AbstractTextInput categoryText = (UITextInput) new UITextInput(categoryName)
                    .setMinWidth(new PixelConstraint(50))
                    .setMaxWidth(new PixelConstraint(200))
                    .setX(new PixelConstraint(10))
                    .setY(new CenterConstraint());

            categoryText.onUpdate(newValue -> {
                    if (newValue.contains(" "))
                        categoryText.setText(newValue.replace(" ", ""));
                    return null;
                }).onMouseClickConsumer(event -> {
                    event.stopPropagation();
                    getWindow().focus(categoryText);
                }).onFocusLost(component -> {
                    categoryText.setActive(false);

                    if (categoryText.getText().isEmpty())
                        return null;

                    Main.macroConfig.renameCategory(categoryName, categoryText.getText());
                    MacroConfigManager.saveConfig(Main.macroConfig);
                    initScreen(width, height);
                    return null;

                }).onFocus(component -> {
                    categoryText.setActive(true);
                    return null;
                });
            categoryHeader.addChild(categoryText);

            ButtonComponent addMacroButton = (ButtonComponent) new ButtonComponent("Add", () -> null)
                    .setX(new PixelConstraint(this.getWindow().getWidth() - 200))
                    .setY(new CenterConstraint())
                    .onMouseClickConsumer(event -> {
                        category.addMacro("NewMacro", new Macro());
                        MacroConfigManager.saveConfig(Main.macroConfig);
                        event.stopPropagation();
                        if (!category.isExpanded())
                            category.toggleExpanded();
                        initScreen(width, height);
                    });
            categoryHeader.addChild(addMacroButton);

            ButtonComponent deleteCategoryButton = (ButtonComponent) new ButtonComponent("Delete", () -> null)
                    .setX(new PixelConstraint(this.getWindow().getWidth() - 150))
                    .setY(new CenterConstraint())
                    .onMouseClickConsumer(event -> {
                        Main.macroConfig.removeCategory(categoryName);
                        MacroConfigManager.saveConfig(Main.macroConfig);
                        event.stopPropagation();
                        initScreen(width, height);
                    });
            categoryHeader.addChild(deleteCategoryButton);

            // Expand Button - Add to background, not categoryHeader
            ButtonComponent enableButton = (ButtonComponent) new ButtonComponent(category.isEnabled() ? "ON" : "OFF", () -> null)
                    .setX(new PixelConstraint(this.getWindow().getWidth() - 80))
                    .setY(new CenterConstraint());
            enableButton.onMouseClickConsumer((event) -> {
                category.setEnabled(!category.isEnabled());
                MacroConfigManager.saveConfig(Main.macroConfig);
                enableButton.setText(category.isEnabled() ? "ON" : "OFF");
                event.stopPropagation();
            });
            categoryHeader.addChild(enableButton);

            // Add the expandable macros if category is expanded
            if (!category.isExpanded())
                continue;

            UIBlock macrosBlock = (UIBlock) new UIBlock()
                    .setColor(new ConstantColorConstraint(new Color(0, 0, 0, 60)))
                    .setX(new PixelConstraint(0))
                    .setY(new SiblingConstraint(0))
                    .setWidth(new PixelConstraint(this.getWindow().getWidth() - 20))
                    .setChildOf(categoryList);

            float macrosHeight = 5;

            for (String macroName : category.getMacros().keySet()) {
                Macro macro = category.getMacros().get(macroName);

                UIBlock macroEntry = (UIBlock) new UIBlock()
                        .setColor(new ConstantColorConstraint(new Color(0, 0, 0, 70)))
                        .setX(new PixelConstraint(10))
                        .setY(new PixelConstraint(macrosHeight))
                        .setWidth(new PixelConstraint(this.getWindow().getWidth() - 40))
                        .setHeight(new PixelConstraint(30))
                        .onMouseClickConsumer(event -> WezelServices.openMacroEditGui(macro));
                macrosBlock.addChild(macroEntry);

                // Macro Name
                AbstractTextInput macroText = (UITextInput) new UITextInput(macroName)
                        .setMinWidth(new PixelConstraint(50))
                        .setMaxWidth(new PixelConstraint(200))
                        .setX(new PixelConstraint(10))
                        .setY(new CenterConstraint());
                macroText.onUpdate(newValue -> {
                    if (newValue.contains(" "))
                        macroText.setText(newValue.replace(" ", ""));
                    return null;
                }).onMouseClickConsumer(event -> {
                    event.stopPropagation();
                    getWindow().focus(macroText);
                }).onFocusLost(component -> {
                    macroText.setActive(false);

                    if (macroText.getText().isEmpty())
                        return null;

                    category.renameMacro(macroName, macroText.getText());
                    MacroConfigManager.saveConfig(Main.macroConfig);
                    initScreen(width, height);
                    return null;

                }).onFocus(component -> {
                    macroText.setActive(true);
                    return null;
                });
                macroEntry.addChild(macroText);

                ButtonComponent deleteMacroButton = (ButtonComponent) new ButtonComponent("Delete", () -> null)
                        .setX(new PixelConstraint(this.getWindow().getWidth() - 150))
                        .setY(new CenterConstraint())
                        .onMouseClickConsumer(event -> {
                            category.removeMacro(macroName);
                            MacroConfigManager.saveConfig(Main.macroConfig);
                            event.stopPropagation();
                            initScreen(width, height);
                        });
                macroEntry.addChild(deleteMacroButton);

                // Toggle Macro Button
                ButtonComponent toggleButton = (ButtonComponent) new ButtonComponent(macro.isEnabled() ? "ON" : "OFF", () -> null)
                        .setX(new PixelConstraint(getWindow().getWidth() - 90))
                        .setY(new CenterConstraint());
                toggleButton.onMouseClickConsumer(event -> {
                    macro.setEnabled(!macro.isEnabled());
                    MacroConfigManager.saveConfig(Main.macroConfig);
                    toggleButton.setText(macro.isEnabled() ? "ON" : "OFF");
                    event.stopPropagation();
                });
                macroEntry.addChild(toggleButton);
                macrosHeight += 35;
            }
            macrosBlock.setHeight(new PixelConstraint(macrosHeight));
        }

        // Add Category Button
        ButtonComponent addCategoryButton = (ButtonComponent) new ButtonComponent("Add Category", () -> null)
                .setX(new CenterConstraint())
                .setY(new SiblingConstraint(10));
        addCategoryButton.onMouseClick((a, b) -> {
            Main.macroConfig.addCategory("NewCategory", new MacroCategory());
            MacroConfigManager.saveConfig(Main.macroConfig);
            initScreen(width, height);
            return null;
        });
        getWindow().getChildren().get(0).addChild(addCategoryButton);
    }
}