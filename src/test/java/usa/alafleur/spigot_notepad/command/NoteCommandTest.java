package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usa.alafleur.spigot_notepad.model.MyObjectBox;
import usa.alafleur.spigot_notepad.model.Note;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandTest {

    private Player player;
    private Command mockCommand;
    private BoxStore store;
    private Box<Note> noteBox;

    @BeforeEach
    public void setUp() {
        store = MyObjectBox.builder()
                .directory(new File("test/"))
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();
        noteBox = store.boxFor(Note.class);
        player = new MockPlayer();
        mockCommand = new MockCommand();
    }

    @AfterEach
    public void tearDown() {
        if (store != null) {
            store.close();
            store.deleteAllFiles();
        }
    }

    @Test
    public void testValidAddNote() {
        ((MockCommand) mockCommand).setValid(true);
        boolean success = submitCommand("note", new String[] { "add", "Here's", "some", "content" });
        assertTrue(success, "Valid note creation with default name works");
        assertFalse(noteBox.isEmpty());

        Note note = noteBox.get(1);
        assertEquals("Here's some content", note.getContent());
    }

    @Test
    public void testAddNoteWithAlias() {
        ((MockCommand) mockCommand).setValid(true);
        boolean success = submitCommand("note", new String[] { "create", "Here's", "some", "content" });
        assertTrue(success, "Valid note creation with default name works");
        assertFalse(noteBox.isEmpty());

        Note note = noteBox.get(1);
        assertEquals("Here's some content", note.getContent());
    }

    @Test
    public void testAddNoteWithName() {
        ((MockCommand) mockCommand).setValid(true);
        boolean success = submitCommand("note", new String[] { "create", "-name", "TheName", "content" });
        assertTrue(success, "Valid note creation with default name works");
        assertFalse(noteBox.isEmpty());

        Note note = noteBox.get(1);
        assertEquals(note.getName(), "TheName");
        assertEquals(note.getContent(), "content");
    }

    @Test
    public void testShowAllNotes() {
        createNote("name", "content");

        boolean success = submitCommand("note", new String[] { "list" });
        assertTrue(success);
        assertEquals("name - content", ((MockPlayer) player).getReceivedMessage(),
                "Shows all messages in correct format");
    }

    @Test
    public void testShowAllNotesAliasWorks() {
        createNote("name", "content");

        boolean success = submitCommand("note", new String[] { "show" });
        assertTrue(success);
        assertEquals("name - content", ((MockPlayer) player).getReceivedMessage(),
                "Shows all messages in correct format");
    }

    private void createNote(String name, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setName(name);
        store.boxFor(Note.class).put(note);
    }

    private boolean submitCommand(String alias, String[] args) {
        NoteCommand noteCommand = new NoteCommand(store);
        return noteCommand.onCommand(player, mockCommand, alias, args);
    }
}