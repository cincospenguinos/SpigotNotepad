package usa.alafleur.spigot_notepad.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usa.alafleur.spigot_notepad.model.Note;
import usa.alafleur.spigot_notepad.model.Notepad;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @BeforeEach
    public void setup() {
        File f = new File(Storage.STORAGE_FILE_NAME);

        if (f.exists()) {
            assertTrue(f.delete());
        }
    }

    @Test
    public void savesToFile() {
        File f = new File(Storage.STORAGE_FILE_NAME);
        assertFalse(f.exists());
        Storage.saveToFile(new Notepad());
        assertTrue(f.exists());
    }

    @Test
    public void restoresFromFile() {
        UUID player = new UUID(123L, 456L);
        Notepad notepad = new Notepad();
        notepad.add(new Note(player, "Hey there"));
        Storage.saveToFile(notepad);

        notepad = Storage.restoreFromFile();
        assertFalse(notepad.notesFor(player).isEmpty());
    }
}