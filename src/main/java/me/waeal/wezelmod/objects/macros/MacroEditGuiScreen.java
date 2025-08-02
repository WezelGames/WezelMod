package me.waeal.wezelmod.objects.macros;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.ScrollComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.CenterConstraint;
import gg.essential.elementa.constraints.ConstantColorConstraint;
import gg.essential.elementa.constraints.PixelConstraint;
import gg.essential.elementa.constraints.SiblingConstraint;
import gg.essential.vigilance.gui.settings.ButtonComponent;
import gg.essential.vigilance.gui.settings.DropDownComponent;
import java.awt.*;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.GuiServices;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MacroEditGuiScreen extends WindowScreen {
    private final Macro macro;
    private MacroAction selectedKeycodeAction;
    private MacroRequirement selectedKeycodeRequirement;

    public MacroEditGuiScreen(Macro macro) {
        super(ElementaVersion.V7, true, false, false, -1);

        this.macro = macro;
    }

    @Override
    public void initScreen(int width, int height) {
        ScrollComponent actionList = GuiServices.prepWindow("Macro Editor", new PixelConstraint(getWindow().getHeight() - 70), getWindow(), true);

        getWindow().getChildren().get(0).onMouseClickConsumer(event -> {
            if (this.selectedKeycodeAction == null && this.selectedKeycodeRequirement == null)
                return;

            if (this.selectedKeycodeAction != null)
                this.selectedKeycodeAction.setKeyCode(-100 + event.getMouseButton());
            else
                this.selectedKeycodeRequirement.setKeyCode(-100 + event.getMouseButton());

            event.stopPropagation();
            getWindow().unfocus();
        });

        UIBlock requirementsHeader = (UIBlock) new UIBlock()
                .setColor(new ConstantColorConstraint(new Color(0, 0, 0, 170)))
                .setX(new PixelConstraint(0))
                .setY(new SiblingConstraint(5))
                .setWidth(new PixelConstraint(this.getWindow().getWidth() - 20))
                .setHeight(new PixelConstraint(30));
        actionList.addChild(requirementsHeader);

        DropDownComponent requirementDropDown = (DropDownComponent) new DropDownComponent(
                    macro.getRequirement().getTypeIndex(),
                    MacroRequirementType.getTypes(),
                    MacroRequirementType.getTypes().size())
                .setX(new PixelConstraint(10))
                .setY(new CenterConstraint());
        requirementDropDown.getSelectedIndex().onSetValue(newType -> {
            macro.getRequirement().setType(MacroRequirementType.getTypes().get(newType));
            initScreen(width, height);
        });
        requirementsHeader.addChild(requirementDropDown);

        switch (macro.getRequirement().getType()) {
            case MESSAGE_CONTAINS:
            case MESSAGE_EQUALS:
                UITextInput requirementsMessageText = (UITextInput) new UITextInput(macro.getRequirement().getMessage())
                        .setWidth(new PixelConstraint(getWindow().getWidth()-300))
                        .setY(new CenterConstraint())
                        .setX(new PixelConstraint(275));
                requirementsMessageText.onMouseClickConsumer(event -> {
                    event.stopPropagation();
                    getWindow().focus(requirementsMessageText);
                }).onFocusLost(component -> {
                    requirementsMessageText.setActive(false);
                    macro.getRequirement().setMessage(requirementsMessageText.getText());
                    MacroConfigManager.saveConfig(Main.macroConfig);
                    initScreen(width, height);
                    return null;
                }).onFocus(component -> {
                    requirementsMessageText.setActive(true);
                    return null;
                });
                requirementsHeader.addChild(requirementsMessageText);
                break;
            case POSITION:
            case ON_ENTER:
                float offset = getWindow().getWidth() - 55;
                UITextInput minZ = doubleInput(macro.getRequirement().getMinZ(), offset, "minZ");
                offset -= 35;
                UITextInput minY = doubleInput(macro.getRequirement().getMinY(), offset, "minY");
                offset -= 35;
                UITextInput minX = doubleInput(macro.getRequirement().getMinX(), offset, "minX");
                offset -= 35;
                UITextInput maxZ = doubleInput(macro.getRequirement().getMaxZ(), offset, "maxZ");
                offset -= 35;
                UITextInput maxY = doubleInput(macro.getRequirement().getMaxY(), offset, "maxY");
                offset -= 35;
                UITextInput maxX = doubleInput(macro.getRequirement().getMaxX(), offset, "maxX");
                minX.onFocusLost(component -> {
                    minX.setActive(false);
                    try {
                        macro.getRequirement().setMinX(Double.parseDouble(minX.getText()));
                        MacroConfigManager.saveConfig(Main.macroConfig);
                    } catch (NumberFormatException ignored) {}
                    initScreen(width, height);
                    return null;
                });
                minY.onFocusLost(component -> {
                    minY.setActive(false);
                    try {
                        macro.getRequirement().setMinY(Double.parseDouble(minY.getText()));
                        MacroConfigManager.saveConfig(Main.macroConfig);
                    } catch (NumberFormatException ignored) {}
                    initScreen(width, height);
                    return null;
                });
                minZ.onFocusLost(component -> {
                    minZ.setActive(false);
                    try {
                        macro.getRequirement().setMinZ(Double.parseDouble(minZ.getText()));
                        MacroConfigManager.saveConfig(Main.macroConfig);
                    } catch (NumberFormatException ignored) {}
                    initScreen(width, height);
                    return null;
                });
                maxX.onFocusLost(component -> {
                    maxX.setActive(false);
                    try {
                        macro.getRequirement().setMaxX(Double.parseDouble(maxX.getText()));
                        MacroConfigManager.saveConfig(Main.macroConfig);
                    } catch (NumberFormatException ignored) {}
                    initScreen(width, height);
                    return null;
                });
                maxY.onFocusLost(component -> {
                    maxY.setActive(false);
                    try {
                        macro.getRequirement().setMaxY(Double.parseDouble(maxY.getText()));
                        MacroConfigManager.saveConfig(Main.macroConfig);
                    } catch (NumberFormatException ignored) {}
                    initScreen(width, height);
                    return null;
                });
                maxZ.onFocusLost(component -> {
                    maxZ.setActive(false);
                    try {
                        macro.getRequirement().setMaxZ(Double.parseDouble(maxZ.getText()));
                        MacroConfigManager.saveConfig(Main.macroConfig);
                    } catch (NumberFormatException ignored) {}
                    initScreen(width, height);
                    return null;
                });
                requirementsHeader.addChild(minX);
                requirementsHeader.addChild(minY);
                requirementsHeader.addChild(minZ);
                requirementsHeader.addChild(maxX);
                requirementsHeader.addChild(maxY);
                requirementsHeader.addChild(maxZ);
                break;
            case KEY_UP:
            case KEY_DOWN:
                String value;
                if (macro.getRequirement().getKeyCode() >= 0)
                    value = Keyboard.getKeyName(macro.getRequirement().getKeyCode());
                else
                    value = Mouse.getButtonName(macro.getRequirement().getKeyCode() + 100);
                UITextInput keyCode = (UITextInput) new UITextInput(value)
                        .setWidth(new PixelConstraint(50))
                        .setY(new CenterConstraint())
                        .setX(new PixelConstraint(getWindow().getWidth()-75));
                keyCode.onMouseClickConsumer(event -> {
                    event.stopPropagation();
                    getWindow().focus(keyCode);
                }).onFocus(component -> {
                    keyCode.setActive(true);
                    selectedKeycodeRequirement = macro.getRequirement();
                    return null;
                }).onFocusLost(component -> {
                    keyCode.setActive(false);
                    MacroConfigManager.saveConfig(Main.macroConfig);
                    initScreen(width, height);
                    this.selectedKeycodeRequirement = null;
                    return null;
                }).onKeyTypeConsumer((c, i) -> {
                    macro.getRequirement().setKeyCode(i);
                    selectedKeycodeRequirement = null;
                    getWindow().unfocus();
                });
                requirementsHeader.addChild(keyCode);
                break;
        }

        for (MacroAction action : macro.getActions()) {

            UIBlock actionHeader = (UIBlock) new UIBlock()
                    .setColor(new ConstantColorConstraint(new Color(0, 0, 0, 130)))
                    .setX(new PixelConstraint(0))
                    .setY(new SiblingConstraint(5))
                    .setWidth(new PixelConstraint(this.getWindow().getWidth() - 20))
                    .setHeight(new PixelConstraint(30));
            actionList.addChild(actionHeader);

            ButtonComponent moveUpButton = (ButtonComponent) new ButtonComponent("Up", () -> {
                macro.moveActionUp(action);
                MacroConfigManager.saveConfig(Main.macroConfig);
                initScreen(width, height);
                return null;
            })
                    .setX(new PixelConstraint(10))
                    .setY(new CenterConstraint());
            actionHeader.addChild(moveUpButton);

            DropDownComponent actionDropDown = (DropDownComponent) new DropDownComponent(
                    action.getTypeIndex(),
                    MacroActionType.getTypes(),
                    MacroActionType.getTypes().size())
                    .setX(new PixelConstraint(40))
                    .setY(new CenterConstraint());
            actionDropDown.getSelectedIndex().onSetValue(newType -> {
                action.setType(MacroActionType.getTypes().get(newType));
                initScreen(width, height);
            });
            actionHeader.addChild(actionDropDown);

            ButtonComponent deleteActionButton = (ButtonComponent) new ButtonComponent("Delete", () -> null)
                    .setX(new PixelConstraint(135))
                    .setY(new CenterConstraint())
                    .onMouseClickConsumer(event -> {
                        macro.removeAction(action);
                        MacroConfigManager.saveConfig(Main.macroConfig);
                        event.stopPropagation();
                        initScreen(width, height);
                    });
            actionHeader.addChild(deleteActionButton);

            switch (action.getType()) {
                case COMMAND:
                    UITextInput commandText = (UITextInput) new UITextInput(action.getCommand())
                            .setWidth(new PixelConstraint(Math.min(getWindow().getWidth() - 170, 200)))
                            .setY(new CenterConstraint())
                            .setX(new PixelConstraint(Math.max(getWindow().getWidth() - 215, 170)));
                    commandText.onMouseClickConsumer(event -> {
                        event.stopPropagation();
                        getWindow().focus(commandText);
                    }).onFocusLost(component -> {
                        commandText.setActive(false);
                        action.setCommand(commandText.getText());
                        MacroConfigManager.saveConfig(Main.macroConfig);
                        initScreen(width, height);
                        return null;
                    }).onFocus(component -> {
                        commandText.setActive(true);
                        return null;
                    });
                    actionHeader.addChild(commandText);
                    break;
                case KEY_CLICK:
                case KEY_PRESS:
                case KEY_RELEASE:
                    String value;
                    if (action.getKeyCode() >= 0)
                        value = Keyboard.getKeyName(action.getKeyCode());
                    else
                        value = Mouse.getButtonName(action.getKeyCode() + 100);
                    UITextInput keyCode = (UITextInput) new UITextInput(value)
                            .setWidth(new PixelConstraint(50))
                            .setY(new CenterConstraint())
                            .setX(new PixelConstraint(getWindow().getWidth()-75));
                    keyCode.onMouseClickConsumer(event -> {
                        event.stopPropagation();
                        getWindow().focus(keyCode);
                    }).onFocus(component -> {
                        keyCode.setActive(true);
                        selectedKeycodeAction = action;
                        return null;
                    }).onFocusLost(component -> {
                        keyCode.setActive(false);
                        MacroConfigManager.saveConfig(Main.macroConfig);
                        initScreen(width, height);
                        this.selectedKeycodeAction = null;
                        return null;
                    }).onKeyTypeConsumer((c, i) -> {
                        action.setKeyCode(i);
                        selectedKeycodeAction = null;
                        getWindow().unfocus();
                    });
                    actionHeader.addChild(keyCode);
                    break;
                case WAIT_TIME:
                    UITextInput waitTimeText = (UITextInput) new UITextInput(action.getWaitTime() + "")
                            .setWidth(new PixelConstraint(Math.min(getWindow().getWidth() - 170, 200)))
                            .setY(new CenterConstraint())
                            .setX(new PixelConstraint(Math.max(getWindow().getWidth() - 215, 170)));
                    waitTimeText.onMouseClickConsumer(event -> {
                        event.stopPropagation();
                        getWindow().focus(waitTimeText);
                    }).onFocusLost(component -> {
                        waitTimeText.setActive(false);
                        try {
                            action.setWaitTime(Integer.parseInt(waitTimeText.getText()));
                            MacroConfigManager.saveConfig(Main.macroConfig);
                        } catch (NumberFormatException ignored) {}
                        initScreen(width, height);
                        return null;
                    }).onFocus(component -> {
                        waitTimeText.setActive(true);
                        return null;
                    });
                    actionHeader.addChild(waitTimeText);
                    break;
            }
        }

        // Add Category Button
        ButtonComponent addCategoryButton = (ButtonComponent) new ButtonComponent("Add Action", () -> null)
                .setX(new CenterConstraint())
                .setY(new SiblingConstraint(10));
        addCategoryButton.onMouseClick((a, b) -> {
            macro.addAction(new MacroAction());
            MacroConfigManager.saveConfig(Main.macroConfig);
            initScreen(width, height);
            return null;
        });
        getWindow().getChildren().get(0).addChild(addCategoryButton);
    }

    public UITextInput doubleInput(double initial, float x, String name) {
        UITextInput input = (UITextInput) new UITextInput(initial + "")
                .setWidth(new PixelConstraint(30))
                .setY(new CenterConstraint())
                .setX(new PixelConstraint(x));
        input.onMouseClickConsumer(event -> {
            event.stopPropagation();
            getWindow().focus(input);
        }).onFocus(component -> {
            input.setActive(true);
            return null;
        });

        UIText nameLabel = (UIText) new UIText(name + ":")
                .setX(new PixelConstraint(0))
                .setY(new PixelConstraint(-30))
                .setWidth(new PixelConstraint(15))
                .setHeight(new PixelConstraint(5))
                .setChildOf(input);

        return input;
    }
}
