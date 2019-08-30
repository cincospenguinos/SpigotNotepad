package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import usa.alafleur.spigot_notepad.model.Note;
import usa.alafleur.spigot_notepad.model.Note_;
import usa.alafleur.spigot_notepad.model.UUIDConverter;

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

        if (args.length == 0) {
            return false;
        }

        Player player = (Player) sender;

        String request = args[0];
        CommandRequestType requestType = CommandRequestType.extractValue(request);

        switch (requestType) {
        case ADD_REQUEST:
            Note newNote = new NoteExtraction(args).buildNote(player.getUniqueId());
            noteBox.put(newNote);
            player.sendMessage("Note added successfully");
            return true;
        case SHOW_REQUEST:
            if (isValidDeleteOrShowRequest(args)) {
                long id = Long.parseLong(args[1]);
                Note noteToShow = noteBox.get(id);

                if (!noteExistsOrBelongsToPlayer(noteToShow, player)) {
                    sender.sendMessage("No note with ID " + id + " to show");
                } else {
                    player.sendMessage(noteToShow.getContent());
                }

                return true;
            }

            return false;
        case LIST_REQUEST:
            List<Note> notesToShow = getNotesFromPlayer(player);

            if (notesToShow.isEmpty()) {
                player.sendMessage("There are no messages to show");
                return true;
            }

            for (Note storedNote : notesToShow) {
                String content = storedNote.getContent();
                String substring = content.substring(0, Math.min(16, content.length()));
                sender.sendMessage(storedNote.getId() + " - " + substring);
            }

            return true;
        case DELETE_REQUEST:
            if (isValidDeleteOrShowRequest(args)) {
                long id = Long.parseLong(args[1]);
                Note noteToDelete = noteBox.get(id);

                if (!noteExistsOrBelongsToPlayer(noteToDelete, player)) {
                    sender.sendMessage("No note with ID " + id + " to delete");
                } else {
                    noteBox.remove(id);
                    sender.sendMessage("Note successfully deleted");
                }
                return true;
            }
        default:
            return false;
        }
    }

    private boolean noteExistsOrBelongsToPlayer(Note note, Player player) {
        return note != null && note.belongsTo(player);
    }

    private boolean isValidDeleteOrShowRequest(String[] args) {
        try {
            if (args.length ==  2) {
                Long.parseLong(args[1]);
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return false;
    }

    private List<Note> getNotesFromPlayer(Player player) {
        String idString = new UUIDConverter().convertToDatabaseValue(player.getUniqueId());
        return noteBox.query()
                .equal(Note_.playerUUID, idString)
                .build()
                .find();
    }
}
