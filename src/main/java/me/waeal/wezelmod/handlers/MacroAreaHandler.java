package me.waeal.wezelmod.handlers;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.objects.macros.Macro;
import me.waeal.wezelmod.objects.macros.MacroConfigManager;
import me.waeal.wezelmod.services.ESPServices;
import me.waeal.wezelmod.services.WezelServices;
import net.minecraft.util.BlockPos;

public class MacroAreaHandler {
    private Macro macro;
    private BlockPos pos1;
    private BlockPos pos2;

    public void setMacro(Macro macro) {
        this.macro = macro;
        pos1 = null;
        pos2 = null;
    }

    public void setPos(BlockPos pos) {
        if (macro == null)
            return;

        if (pos1 == null) {
            pos1 = pos;
        } else {
            pos2 = pos;
            setArea();
        }
    }

    public void setArea() {
        if (pos1.getX() < pos2.getX()) {
            macro.getRequirement().setMinX(pos1.getX());
            macro.getRequirement().setMaxX(pos2.getX() + 1);
        } else {
            macro.getRequirement().setMinX(pos2.getX());
            macro.getRequirement().setMaxX(pos1.getX() + 1);
        }
        if (pos1.getY() < pos2.getY()) {
            macro.getRequirement().setMinY(pos1.getY());
            macro.getRequirement().setMaxY(pos2.getY() + 1);
        } else {
            macro.getRequirement().setMinY(pos2.getY());
            macro.getRequirement().setMaxY(pos1.getY() + 1);
        }
        if (pos1.getZ() < pos2.getZ()) {
            macro.getRequirement().setMinZ(pos1.getZ());
            macro.getRequirement().setMaxZ(pos2.getZ() + 1);
        } else {
            macro.getRequirement().setMinZ(pos2.getZ());
            macro.getRequirement().setMaxZ(pos1.getZ() + 1);
        }

        WezelServices.openMacroEditGui(macro);
        setMacro(null);
        MacroConfigManager.saveConfig(Main.macroConfig);
    }

    public Macro getMacro() {
        return macro;
    }

    public BlockPos getPos1() {
        return pos1;
    }
}
