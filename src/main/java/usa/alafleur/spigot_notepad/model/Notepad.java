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

    public List<Note> getAllNotes() {
        List<Note> allNotes = new ArrayList<>();

        for (List<Note> n : notes.values()) {
            allNotes.addAll(n);
        }

        return allNotes;
    }

    public static Notepad fromNotesList(List<Note> notes) {
        long biggestId = -1;
        Notepad notepad = new Notepad();

        for (Note n : notes) {
            if (biggestId < n.getId()) {
                biggestId = n.getId() + 1;
            }

            notepad.add(n);
        }

        return notepad;
    }

    private boolean notesExistFor(UUID player) {
        return notes.containsKey(player);
    }
}
