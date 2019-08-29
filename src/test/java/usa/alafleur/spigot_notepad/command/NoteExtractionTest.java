package usa.alafleur.spigot_notepad.command;

import org.junit.jupiter.api.Test;
import usa.alafleur.spigot_notepad.model.Note;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NoteExtractionTest {

    private UUID mockUUID = new UUID(5555L, 444L);

    @Test
    public void testExtractsContent() {
        NoteExtraction extraction = new NoteExtraction(new String[] { "add", "Hello", "there", "Joe"});
        Note note = extraction.buildNote(mockUUID);
        assertEquals(note.getContent(), "Hello there Joe");
    }

    @Test
    public void testExtractsUUID() {
        NoteExtraction extraction = new NoteExtraction(new String[] { "add", "Hello", "there", "Joe"});
        Note note = extraction.buildNote(mockUUID);
        assertEquals(note.getPlayerUUID(), mockUUID);
    }
}