package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import usa.alafleur.spigot_notepad.model.Note;
import usa.alafleur.spigot_notepad.model.Note_;

import java.util.List;

public class NoteCommand implements CommandExecutor {

    private Box<Note> noteBox;

    public NoteCommand(BoxStore store) {
        noteBox = store.boxFor(Note.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandString, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only available to players");
            return true;
        }

        String request = args[0];
        CommandRequestType requestType = CommandRequestType.extractValue(request);

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
        case DELETE_REQUEST:
            if (args.length == 2) {
                String name = args[1];
                List<Note> notes = noteBox.query().equal(Note_.name, name).build().find();
                noteBox.remove(notes.get(0).getId());

                sender.sendMessage("Note successfully deleted");
                return true;
            }
        default:
            return false;
        }
    }
}
