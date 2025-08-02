package me.waeal.wezelmod.objects.macros;

import java.util.HashMap;
import java.util.Map;

public class MacroConfig {
    private final Map<String, MacroCategory> categories = new HashMap<>();

    public MacroConfig() {
        for (String name : MacroConfigManager.getConfig().get("general", "names", new String[]{}).getStringList())
            addCategory(name, new MacroCategory(name));
    }

    public void interrupt() {
        for (MacroCategory category : categories.values())
            category.interrupt();
    }

    public Map<String, MacroCategory> getCategories() {
        return categories;
    }

    public void addCategory(String name, MacroCategory category) {
        categories.put(name, category);
    }

    public MacroCategory getCategory(String name) {
        return categories.get(name);
    }

    public void renameCategory(String oldName, String newName) {
        if (oldName.equals(newName))
            return;

        categories.put(newName, categories.get(oldName));
        categories.remove(oldName);
    }

    public void removeCategory(String name) {
        categories.remove(name);
    }

    public void save() {
        String[] categoryNames = new String[categories.size()];
        int i = 0;
        for (String name : categories.keySet()) {
            categoryNames[i] = name;
            getCategory(name).save(name);
            i++;
        }
        MacroConfigManager.getConfig().get("general", "names", new String[]{}).setValues(categoryNames);
    }
}