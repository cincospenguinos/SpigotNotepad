package usa.alafleur.spigot_notepad.command;

import usa.alafleur.spigot_notepad.model.Note;

/**
 * Extracts note from the command arguments handed to it.
 */
public class NoteExtraction {
    private static final String DEFAULT_NOTE_NAME = "NOTE";
    private String[] arguments;

    public NoteExtraction(String[] _arguments) {
        arguments = _arguments;
    }

    public Note buildNote() {
        Note note = new Note();
        note.setName(extractName());

        return note;
    }

    private String extractName() {
        if (arguments.length >= 3 && arguments[1].equalsIgnoreCase("-name")) {
            return arguments[2];
        }

        return DEFAULT_NOTE_NAME;
    }
}
