package me.waeal.wezelmod.objects.macros;

import java.util.Arrays;
import java.util.List;

public enum MacroRequirementType {
    MESSAGE_CONTAINS("MESSAGE_CONTAINS"),
    MESSAGE_EQUALS("MESSAGE_EQUALS"),
    POSITION("POSITION"),
    ON_ENTER("ON_ENTER"),
    KEY_DOWN("KEY_DOWN"),
    KEY_UP("KEY_UP");

    private final String name;

    MacroRequirementType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<String> getTypes() {
        return Arrays.asList(
                MESSAGE_CONTAINS.toString(),
                MESSAGE_EQUALS.toString(),
                POSITION.toString(),
                ON_ENTER.toString(),
                KEY_DOWN.toString(),
                KEY_UP.toString()
        );
    }
}
