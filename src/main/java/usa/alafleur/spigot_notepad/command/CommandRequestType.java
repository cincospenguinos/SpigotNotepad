package usa.alafleur.spigot_notepad.command;

import java.util.Arrays;
import java.util.List;

public enum CommandRequestType {
    ADD_REQUEST, LIST_REQUEST, DELETE_REQUEST, SHOW_REQUEST, INVALID_REQUEST;

    public static final List<String> ADD_ALIASES = Arrays.asList("add", "create", "new", "a");
    public static final List<String> LIST_ALIASES = Arrays.asList("list", "l");
    public static final List<String> DELETE_ALIASES = Arrays.asList("delete", "remove", "d");
    public static final List<String> SHOW_ALIASES = Arrays.asList("show", "read", "r", "s");

    public static CommandRequestType extractValue(String alias) {
        if (ADD_ALIASES.contains(alias.toLowerCase())) {
            return ADD_REQUEST;
        }

        if (LIST_ALIASES.contains(alias.toLowerCase())) {
            return LIST_REQUEST;
        }

        if (DELETE_ALIASES.contains(alias.toLowerCase())) {
            return DELETE_REQUEST;
        }

        if (SHOW_ALIASES.contains(alias.toLowerCase())) {
            return SHOW_REQUEST;
        }

        return INVALID_REQUEST;
    }
}
