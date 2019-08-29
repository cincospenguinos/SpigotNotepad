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
            String content = extractContentFrom(args);
            noteBox.put(createNote(content));
            return true;
        case LIST_REQUEST:
            for (Note note : noteBox.getAll()) {
                sender.sendMessage(note.getName() + " - " + note.getContent());
            }

            return true;
        default:
            return false;
        }
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
