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
    public static final List<String> ADD_ALIASES = Arrays.asList("add", "create");
    public static final List<String> LIST_ALIASES = Arrays.asList("list", "show");

    private BoxStore boxStore;

    public NoteCommand(BoxStore store) {
        boxStore = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandString, String[] args) {
        String request = args[0];

        if (isAddRequest(request)) {
            Box<Note> noteBox = boxStore.boxFor(Note.class);
            String content = extractContentFrom(args);
            noteBox.put(createNote(content));
            return true;
        }

        if (isListRequest(request)) {
            Box<Note> noteBox = boxStore.boxFor(Note.class);

            for (Note note : noteBox.getAll()) {
                sender.sendMessage(note.getName() + " - " + note.getContent());
            }

            return true;
        }

        return false;
    }

    private boolean isListRequest(String alias) {
        return LIST_ALIASES.contains(alias.toLowerCase());
    }

    private boolean isAddRequest(String alias) {
        return ADD_ALIASES.contains(alias.toLowerCase());
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
