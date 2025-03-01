package me.waeal.wezelmod.objects.macros;

import java.util.ArrayList;
import java.util.List;

public class Macro {
    private final MacroRequirement requirement;
    private final List<MacroAction> actions = new ArrayList<>();
    private boolean enabled;
    private volatile boolean active = false;

    public Macro(String category) {
        enabled = MacroConfigManager.getConfig().get(category, "enabled", false).getBoolean();
        requirement = new MacroRequirement(category + ".requirements");
        for (int action = 0; action < MacroConfigManager.getConfig().get(category, "actions", "").getInt(); action++)
            addAction(new MacroAction(category + "." + action));
    }

    public Macro() {
        this.requirement = new MacroRequirement();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public MacroRequirement getRequirement() {
        return requirement;
    }

    public void addAction(MacroAction action) {
        actions.add(action);
    }

    public List<MacroAction> getActions() {
        return actions;
    }

    public void execute() {
        if (active)
            return;

        active = true;
        new Thread(() -> {
            try {
                for (MacroAction action : actions) {
                    action.perform();
                }
            } catch (Exception ignored) {
                // Interrupted
            } finally {
                active = false;
            }
        }).start();
    }

    public void save(String category) {
        MacroConfigManager.getConfig().get(category, "enabled", false).set(enabled);
        requirement.save(category + ".requirements");
        MacroConfigManager.getConfig().get(category, "actions", 0).set(actions.size());
        for (int i = 0; i < actions.size(); i++)
            actions.get(i).save(category + "." + i);
    }

    public void removeAction(MacroAction action) {
        actions.remove(action);
    }

    public void moveActionUp(MacroAction action) { 
        int lastIndex = actions.indexOf(action);
        if (lastIndex == 0)
            return;
        
        List<MacroAction> actionsCopy = new ArrayList<>(actions);
        actions.clear();
        
        for (int i = 0; i < actionsCopy.size(); i++) {
            if (i == lastIndex)
                continue;
            
            if (i == lastIndex - 1)
                actions.add(action);
            
            actions.add(actionsCopy.get(i));
        }
    }
}
