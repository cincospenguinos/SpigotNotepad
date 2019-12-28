package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import usa.alafleur.spigot_notepad.model.Note;
import usa.alafleur.spigot_notepad.model.Notepad;
import usa.alafleur.spigot_notepad.model.UUIDConverter;

import java.util.List;

public class NoteCommand implements CommandExecutor {
    private Notepad notepad;

    public NoteCommand(Notepad _notepad) {
        notepad = _notepad;
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
            notepad.add(newNote);
            player.sendMessage(ChatColor.ITALIC + "Note added successfully");
            return true;
        case SHOW_REQUEST:
            if (isValidDeleteOrShowRequest(args)) {
                long id = Long.parseLong(args[1]);

                if (!noteExistsOrBelongsToPlayer(id, player)) {
                    sender.sendMessage(ChatColor.ITALIC + "" + ChatColor.RED + "No note with ID " + id + " to show");
                } else {
                    for (Note n : notepad.notesFor(player.getUniqueId())) {
                        if (n.getId() == id) {
                            player.sendMessage(n.getContent());
                            break;
                        }
                    }
                }

                return true;
            }

            return false;
        case LIST_REQUEST:
            List<Note> notesToShow = notepad.notesFor(player.getUniqueId());

            if (notesToShow.isEmpty()) {
                player.sendMessage(ChatColor.ITALIC + "" + ChatColor.RED + "There are no messages to show");
                return true;
            }

            for (Note storedNote : notesToShow) {
                String content = storedNote.getContent();
                String substring = content.substring(0, Math.min(16, content.length()));
                sender.sendMessage(ChatColor.BOLD + "" + storedNote.getId() +
                        "" + ChatColor.RESET + "" + " - " + substring);
            }

            return true;
        case DELETE_REQUEST:
            if (isValidDeleteOrShowRequest(args)) {
                long id = Long.parseLong(args[1]);

                if (!noteExistsOrBelongsToPlayer(id, player)) {
                    sender.sendMessage(ChatColor.ITALIC + "" + ChatColor.RED + "No note with ID " + id + " to delete");
                } else {
                    notepad.remove(player.getUniqueId(), id);
                    sender.sendMessage(ChatColor.ITALIC + "Note successfully deleted");
                }
                return true;
            }
        default:
            return false;
        }
    }

    private boolean noteExistsOrBelongsToPlayer(long id, Player player) {
        for (Note n : notepad.notesFor(player.getUniqueId())) {
            if (n.getId() == id) {
                return true;
            }
        }

        return false;
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
//        String idString = new UUIDConverter().convertToDatabaseValue(player.getUniqueId());
//        return noteBox.query()
//                .equal(Note_.playerUUID, idString)
//                .build()
//                .find();
        return null;
    }
}
