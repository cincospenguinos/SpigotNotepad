package usa.alafleur.spigot_notepad.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoteTabCompleter implements TabCompleter {
    private static final List<String> ALL_COMMANDS = Arrays.asList("add", "list", "delete", "read");
    private static final List<String> ADD_LIST = Collections.singletonList("add");
    private static final List<String> READ_LIST = Collections.singletonList("read");
    private static final List<String> LIST_LIST = Collections.singletonList("list");
    private static final List<String> DELETE_LIST = Collections.singletonList("delete");

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0 || strings[0].isEmpty()) {
            return ALL_COMMANDS;
        }

        String prefix = strings[0];

        if (prefix.startsWith("a")) {
            return ADD_LIST;
        }

        if (prefix.startsWith("l")) {
            return LIST_LIST;
        }

        if (prefix.startsWith("r")) {
            return READ_LIST;
        }

        if (prefix.startsWith("d")) {
            return DELETE_LIST;
        }


        return ALL_COMMANDS;
    }
}
