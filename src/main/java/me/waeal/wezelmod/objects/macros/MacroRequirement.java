package me.waeal.wezelmod.objects.macros;

import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MacroRequirement {
    private MacroRequirementType type;
    private double minX, maxX, minY, maxY, minZ, maxZ;
    private String messageText;
    private int keyCode;
    private boolean metLastTime;

    public MacroRequirement(String category) {
        type = MacroRequirementType.valueOf(MacroConfigManager.getConfig().get(category, "type", MacroRequirementType.MESSAGE_CONTAINS.toString()).getString());
        minX = MacroConfigManager.getConfig().get(category, "minX", 0d).getDouble();
        minY = MacroConfigManager.getConfig().get(category, "minY", 0d).getDouble();
        minZ = MacroConfigManager.getConfig().get(category, "minZ", 0d).getDouble();
        maxX = MacroConfigManager.getConfig().get(category, "maxX", 0d).getDouble();
        maxY = MacroConfigManager.getConfig().get(category, "maxY", 0d).getDouble();
        maxZ = MacroConfigManager.getConfig().get(category, "maxZ", 0d).getDouble();
        keyCode = MacroConfigManager.getConfig().get(category, "keyCode", 0).getInt();
        messageText = MacroConfigManager.getConfig().get(category, "messageText", "MEOW").getString();
    }

    public MacroRequirement() {
        type = MacroRequirementType.MESSAGE_CONTAINS;
        messageText = "MEOW";
    }

    public boolean isMet(String message, double x, double y, double z) {
        switch (type) {
            case MESSAGE_CONTAINS:
                return message.toUpperCase().contains(messageText);
            case MESSAGE_EQUALS:
                return message.toUpperCase().equals(messageText);
            case POSITION:
                return minX <= x && x <= maxX
                        && minY <= y && y <= maxY
                        && minZ <= z && z <= maxZ;
            case ON_ENTER:
                if (!(minX <= x && x <= maxX
                        && minY <= y && y <= maxY
                        && minZ <= z && z <= maxZ)) {
                    metLastTime = false;
                } else {
                    if (metLastTime)
                        return false;
                    else
                        metLastTime = true;
                }
                return metLastTime;
            case KEY_DOWN:
                if (keyCode < 0) {
                    if (metLastTime && !Mouse.isButtonDown(keyCode + 100)) {
                        metLastTime = false;
                        return false;
                    }
                    if (!metLastTime && Mouse.isButtonDown(keyCode + 100)) {
                        metLastTime = true;
                        return true;
                    }
                } else {
                    if (metLastTime && !Keyboard.isKeyDown(keyCode)) {
                        metLastTime = false;
                        return false;
                    }
                    if (!metLastTime && Keyboard.isKeyDown(keyCode)) {
                        metLastTime = true;
                        return true;
                    }
                }
                return false;
            case KEY_UP:
                if (keyCode < 0) {
                    if (metLastTime && Mouse.isButtonDown(keyCode + 100)) {
                        metLastTime = false;
                        return false;
                    }
                    if (!metLastTime && !Mouse.isButtonDown(keyCode + 100)) {
                        metLastTime = true;
                        return true;
                    }
                } else {
                    if (metLastTime && Keyboard.isKeyDown(keyCode)) {
                        metLastTime = false;
                        return false;
                    }
                    if (!metLastTime && !Keyboard.isKeyDown(keyCode)) {
                        metLastTime = true;
                        return true;
                    }
                }
                return false;
        }
        return false;
    }

    public boolean isMet(double x, double y, double z) {
        if (type != MacroRequirementType.POSITION
                && type != MacroRequirementType.ON_ENTER)
            return false;

        return isMet(null, x, y, z);
    }

    public boolean isMet(String message) {
        if (type != MacroRequirementType.MESSAGE_EQUALS
                && type != MacroRequirementType.MESSAGE_CONTAINS)
            return false;

        return isMet(message, 0, 0, 0);
    }
    
    public boolean isMet() {
        if (type != MacroRequirementType.KEY_DOWN
                && type != MacroRequirementType.KEY_UP)
            return false;
        
        return isMet(null, 0, 0, 0);
    }
    
    public void save(String category) {
        MacroConfigManager.getConfig().get(category, "type", MacroRequirementType.MESSAGE_CONTAINS.toString()).set(type.toString());
        MacroConfigManager.getConfig().get(category, "minX", 0d).set(minX);
        MacroConfigManager.getConfig().get(category, "minY", 0d).set(minY);
        MacroConfigManager.getConfig().get(category, "minZ", 0d).set(minZ);
        MacroConfigManager.getConfig().get(category, "maxX", 0d).set(maxX);
        MacroConfigManager.getConfig().get(category, "maxY", 0d).set(maxY);
        MacroConfigManager.getConfig().get(category, "maxZ", 0d).set(maxZ);
        MacroConfigManager.getConfig().get(category, "keyCode", 0).set(keyCode);
        MacroConfigManager.getConfig().get(category, "messageText", "MEOW").set(messageText);
    }

    public int getTypeIndex() {
        List<String> types = MacroRequirementType.getTypes();

        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(type.toString()))
                return i;
        }

        return 0;
    }

    public void setType(String newType) {
        type = MacroRequirementType.valueOf(newType);
    }

    public MacroRequirementType getType() {
        return type;
    }

    public String getMessage() {
        return messageText;
    }

    public void setMessage(String text) {
        messageText = text.toUpperCase();
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
