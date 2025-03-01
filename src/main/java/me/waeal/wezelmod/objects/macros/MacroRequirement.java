package me.waeal.wezelmod.objects.macros;

import java.util.List;

public class MacroRequirement {
    private MacroRequirementType type;
    private double minX, maxX, minY, maxY, minZ, maxZ;
    private String messageText;
    private boolean metLastTime;

    public MacroRequirement(String category) {
        type = MacroRequirementType.valueOf(MacroConfigManager.getConfig().get(category, "type", MacroRequirementType.MESSAGE_CONTAINS.toString()).getString());
        minX = MacroConfigManager.getConfig().get(category, "minX", Double.MIN_VALUE).getDouble();
        minY = MacroConfigManager.getConfig().get(category, "minY", Double.MIN_VALUE).getDouble();
        minZ = MacroConfigManager.getConfig().get(category, "minZ", Double.MIN_VALUE).getDouble();
        maxX = MacroConfigManager.getConfig().get(category, "maxX", Double.MAX_VALUE).getDouble();
        maxY = MacroConfigManager.getConfig().get(category, "maxY", Double.MAX_VALUE).getDouble();
        maxZ = MacroConfigManager.getConfig().get(category, "maxZ", Double.MAX_VALUE).getDouble();
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
    
    public void save(String category) {
        MacroConfigManager.getConfig().get(category, "type", MacroRequirementType.MESSAGE_CONTAINS.toString()).set(type.toString());
        MacroConfigManager.getConfig().get(category, "minX", Double.MIN_VALUE).set(minX);
        MacroConfigManager.getConfig().get(category, "minY", Double.MIN_VALUE).set(minY);
        MacroConfigManager.getConfig().get(category, "minZ", Double.MIN_VALUE).set(minZ);
        MacroConfigManager.getConfig().get(category, "maxX", Double.MAX_VALUE).set(maxX);
        MacroConfigManager.getConfig().get(category, "maxY", Double.MAX_VALUE).set(maxY);
        MacroConfigManager.getConfig().get(category, "maxZ", Double.MAX_VALUE).set(maxZ);
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
}
