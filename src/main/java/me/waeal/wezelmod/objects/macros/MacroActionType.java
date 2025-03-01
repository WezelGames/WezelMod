package me.waeal.wezelmod.objects.macros;

import java.util.Arrays;
import java.util.List;

public enum MacroActionType {
    COMMAND("COMMAND"),
    KEY_CLICK("KEY_CLICK"),
    KEY_PRESS("KEY_PRESS"),
    KEY_RELEASE("KEY_RELEASE"),
    WAIT_TIME("WAIT_TIME");

    private final String name;

    MacroActionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<String> getTypes() {
        return Arrays.asList(
                COMMAND.toString(),
                KEY_CLICK.toString(),
                KEY_PRESS.toString(),
                KEY_RELEASE.toString(),
                WAIT_TIME.toString()
        );
    }
}
