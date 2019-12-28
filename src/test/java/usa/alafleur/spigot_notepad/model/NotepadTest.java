package usa.alafleur.spigot_notepad.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotepadTest {
    @Test
    public void canSaveANote() {
        Notepad pad = new Notepad();
        UUID foo = new UUID(10000000000L, 100000000L);
        Note note = new Note(foo, "hello");
        pad.add(note);
        List<Note> notes = pad.notesFor(foo);

        assertEquals(notes.get(0).getContent(), note.getContent());
        assertEquals(notes.get(0), note);
    }

    @Test
    public void addingANoteAssignsAnId() {
        Notepad pad = new Notepad();
        UUID foo = new UUID(10000000000L, 100000000L);
        Note note = new Note(foo, "hello");

        assertEquals(note.getId(), 0);
        pad.add(note);
        assertNotEquals(note.getId(), 0);
    }

    @Test
    public void canDeleteANote() {
        Notepad pad = new Notepad();
        UUID foo = new UUID(10000000000L, 100000000L);
        Note note = new Note(foo, "hello");
        pad.add(note);

        pad.remove(foo, note.getId());
        assertEquals(pad.notesFor(foo).size(), 0);
    }
}