package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import usa.alafleur.spigot_notepad.model.Note;

import java.util.Arrays;
import java.util.List;

public class NoteCommand implements CommandExecutor {
    private static final List<String> ALIASES = Arrays.asList("add", "create", "list");
    private BoxStore boxStore;

    public NoteCommand(BoxStore store) {
        boxStore = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandString, String[] args) {
        if (args[0].equalsIgnoreCase("add")) {
            Box<Note> noteBox = boxStore.boxFor(Note.class);
            String content = extractContentFrom(args);
            noteBox.put(createNote(content));
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {

            return true;
        }

        return false;
    }

    private String extractContentFrom(String[] args) {
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            String s = args[i];
            builder.append(s);
            builder.append(" ");
        }

        return builder.toString().trim();
    }

    private Note createNote(String content) {
        Note note = new Note();
        note.setContent(content);
        note.setName("FooBarBizBaz");
        return note;
    }
}
