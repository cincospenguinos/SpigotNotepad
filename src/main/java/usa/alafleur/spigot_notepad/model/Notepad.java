package usa.alafleur.spigot_notepad.model;

import java.util.*;

public class Notepad {
    private Map<UUID, List<Note>> notes;
    private long idCounter;

    public Notepad() {
        notes = new HashMap<>();
        idCounter = 1;
    }

    public void add(Note note) {
        UUID player = note.getPlayerUUID();

        if (!notesExistFor(player)) {
            notes.put(player, new ArrayList<>());
        }

        note.setId(idCounter++);
        notes.get(player).add(note);
    }

    public List<Note> notesFor(UUID player) {
        if (notesExistFor(player)) {
            return notes.get(player);
        }

        return new ArrayList<>();
    }

    public void remove(UUID player, long id) {
        if (notesExistFor(player)) {
            List<Note> notesForPlayer = notes.get(player);
            int index = -1;

            for (int i = 0; i < notesForPlayer.size(); i++) {
                if (notesForPlayer.get(i).getId() == id) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                notesForPlayer.remove(index);
                notes.put(player, notesForPlayer);
            }
        }
    }

    private boolean notesExistFor(UUID player) {
        return notes.containsKey(player);
    }
}
