package me.waeal.wezelmod.objects.macros;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;

public class MacroAction {
    private MacroActionType type;

    private String command;
    private int keyCode;
    private int waitTime;

    public MacroAction(String category) {
        type = MacroActionType.valueOf(MacroConfigManager.getConfig().get(category, "type", MacroActionType.COMMAND.toString()).getString());
        command = MacroConfigManager.getConfig().get(category, "command", "pc MEOW").getString();
        keyCode = MacroConfigManager.getConfig().get(category, "keyCode", 0).getInt();
        waitTime = MacroConfigManager.getConfig().get(category, "waitTime", 0).getInt();
    }

    public MacroAction() {
        type = MacroActionType.COMMAND;
        command = "pc MEOW";
    }

    public void perform() throws InterruptedException {
        switch (type) {
            case COMMAND:
                if (ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/" + command) == 0)
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/" + command);
                break;
            case KEY_CLICK:
                KeyBinding.onTick(keyCode);
            case KEY_PRESS:
                KeyBinding.setKeyBindState(keyCode, true);
                break;
            case KEY_RELEASE:
                KeyBinding.setKeyBindState(keyCode, false);
                break;
            case WAIT_TIME:
                Thread.sleep(waitTime);
                break;
        }
    }

    public void save(String category) {
        MacroConfigManager.getConfig().get(category, "type", MacroActionType.COMMAND.toString()).set(type.toString());
        MacroConfigManager.getConfig().get(category, "command", "pc MEOW").set(command);
        MacroConfigManager.getConfig().get(category, "keyCode", 0).set(keyCode);
        MacroConfigManager.getConfig().get(category, "waitTime", 0).set(waitTime);
    }

    public int getTypeIndex() {
        List<String> types = MacroActionType.getTypes();

        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(type.toString()))
                return i;
        }

        return 0;
    }

    public MacroActionType getType() {
        return type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setType(String newType) {
        type = MacroActionType.valueOf(newType);
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getKeyCode() {
        return keyCode;
    }
}
