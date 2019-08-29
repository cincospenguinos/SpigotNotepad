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
            noteBox.put(newNote);
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
}
