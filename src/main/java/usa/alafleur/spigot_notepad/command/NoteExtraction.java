package usa.alafleur.spigot_notepad.command;

import usa.alafleur.spigot_notepad.model.Note;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    public Note buildNote(UUID playerUUID) {
        Note note = new Note();
        note.setName(extractName());
        note.setContent(extractContent());
        note.setPlayerUUID(playerUUID);

        return note;
    }

    private String extractName() {
        if (hasNameIncluded()) {
            return arguments[2];
        }

        return DEFAULT_NOTE_NAME;
    }

    private String extractContent() {
        StringBuilder builder = new StringBuilder();

        int i = 1;

        if (hasNameIncluded()) {
            i = 3;
        }

        for (; i < arguments.length; i++) {
            String s = arguments[i];

            builder.append(s);
            builder.append(" ");
        }

        return builder.toString().trim();
    }

    private boolean hasNameIncluded() {
        return arguments.length >= 3 && NAME_SWITCHES.contains(arguments[1].toLowerCase());
    }
}
