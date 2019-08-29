package usa.alafleur.spigot_notepad.command;

import org.junit.jupiter.api.Test;
import usa.alafleur.spigot_notepad.model.Note;

import static org.junit.jupiter.api.Assertions.*;

class NoteExtractionTest {

    @Test
    public void testExtractsName() {
        NoteExtraction extraction = new NoteExtraction(new String[] { "add", "-name", "TheName" });
        Note note = extraction.buildNote();
        assertEquals(note.getName(), "TheName");
    }

    @Test
    public void testExtractsNameWithShortSwitch() {
        NoteExtraction extraction = new NoteExtraction(new String[] { "add", "-n", "TheName" });
        Note note = extraction.buildNote();
        assertEquals(note.getName(), "TheName");
    }
}