package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import usa.alafleur.spigot_notepad.model.Note;

public class NoteCommand implements CommandExecutor {

    private BoxStore boxStore;

    public NoteCommand(BoxStore store) {
        boxStore = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandString, String[] args) {
        String request = args[0];
        CommandRequestType requestType = CommandRequestType.extractValue(request);
        Box<Note> noteBox = boxStore.boxFor(Note.class);

        switch (requestType) {
        case ADD_REQUEST:
            Note newNote = new NoteExtraction(args).buildNote();
            String name = newNote.getName();
            String content = extractContentFrom(args);
            noteBox.put(createNote(name, content));
            return true;
        case LIST_REQUEST:
            for (Note storedNote : noteBox.getAll()) {
                sender.sendMessage(storedNote.getName() + " - " + storedNote.getContent());
            }

            return true;
        default:
            return false;
        }
    }

    private String extractContentFrom(String[] args) {
        StringBuilder builder = new StringBuilder();

        int i = 1;

        if (args[1].equalsIgnoreCase("-name")) {
            i = 3;
        }

        for (; i < args.length; i++) {
            String s = args[i];

            builder.append(s);
            builder.append(" ");
        }

        return builder.toString().trim();
    }

    private String extractNameFrom(String[] args) {
        if (args.length > 3 && args[1].equalsIgnoreCase("-name")) {
            return args[2];
        }

        return "FooBarBizBaz"; // TODO: Default name
    }

    private Note createNote(String name, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setName(name);
        return note;
    }
}
