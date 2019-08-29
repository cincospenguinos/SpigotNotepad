package usa.alafleur.spigot_notepad.command;

import usa.alafleur.spigot_notepad.model.Note;

import java.util.Arrays;
import java.util.List;

/**
 * Extracts note from the command arguments handed to it.
 */
public class NoteExtraction {
    private static final String DEFAULT_NOTE_NAME = "NOTE";
    private static final List<String> NAME_SWITCHES = Arrays.asList("-name", "-n", "--name");

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
        if (arguments.length >= 3 && NAME_SWITCHES.contains(arguments[1].toLowerCase())) {
            return arguments[2];
        }

        return DEFAULT_NOTE_NAME;
    }
}
