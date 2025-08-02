package me.waeal.wezelmod.objects.macros;

import java.util.HashMap;
import java.util.Map;

public class MacroCategory {
    private boolean expanded;
    private boolean enabled;
    private final Map<String, Macro> macros = new HashMap<>();

    public MacroCategory(String name) {
        expanded = MacroConfigManager.getConfig().get(name, "expanded", false).getBoolean();
        enabled = MacroConfigManager.getConfig().get(name, "enabled", false).getBoolean();
        for (String macro : MacroConfigManager.getConfig().get(name, "macroNames", new String[]{}).getStringList())
            addMacro(macro, new Macro(name + "." + macro));
    }

    public MacroCategory() {

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (!enabled)
            interrupt();
    }

    public void interrupt() {
        for (Macro macro : macros.values())
            macro.interrupt();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Map<String, Macro> getMacros() {
        return macros;
    }

    public Macro getMacro(String name) {
        return macros.get(name);
    }

    public void addMacro(String name, Macro macro) {
        macros.put(name, macro);
    }

    public void removeMacro(String name) {
        macros.remove(name);
    }

    public void save(String name) {
        String[] macroNames = new String[macros.size()];
        int i = 0;
        for (String macro : macros.keySet()) {
            macroNames[i] = macro;
            getMacro(macro).save(name + "." + macro);
            i++;
        }
        MacroConfigManager.getConfig().get(name, "expanded", false).set(expanded);
        MacroConfigManager.getConfig().get(name, "enabled", false).set(enabled);
        MacroConfigManager.getConfig().get(name, "macroNames", new String[]{}).set(macroNames);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void toggleExpanded() {
        expanded = !expanded;
    }

    public void renameMacro(String oldName, String newName) {
        if (oldName.equals(newName))
            return;

        macros.put(newName, getMacro(oldName));
        macros.remove(oldName);
    }
}
