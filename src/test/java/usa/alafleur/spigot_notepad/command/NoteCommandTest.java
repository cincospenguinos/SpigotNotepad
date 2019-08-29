package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usa.alafleur.spigot_notepad.model.MyObjectBox;
import usa.alafleur.spigot_notepad.model.Note;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandTest {
    private CommandSender sender;
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
        sender = new MockPlayer();
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
        assertTrue(success);
        assertFalse(noteBox.isEmpty());

        Note note = noteBox.get(1);
        assertEquals("Here's some content", note.getContent());
    }

    @Test
    public void testAddNoteWithAlias() {
        ((MockCommand) mockCommand).setValid(true);
        boolean success = submitCommand("note", new String[] { "create", "Here's", "some", "content" });
        assertTrue(success);
        assertFalse(noteBox.isEmpty());

        Note note = noteBox.get(1);
        assertEquals("Here's some content", note.getContent());
    }

    @Test
    public void testShowAllNotes() {
        createNote("content");

        boolean success = submitCommand("note", new String[] { "list" });
        assertTrue(success);
        assertEquals("1 - content", ((MockPlayer) sender).getReceivedMessage(),
                "Shows all messages in correct format");
    }

    @Test
    public void testDeleteNoteById() {
        createNote("Delete me please");
        assertFalse(store.boxFor(Note.class).isEmpty());

        boolean success = submitCommand("note", new String[] { "delete", "1" });
        assertTrue(success);
        assertTrue(store.boxFor(Note.class).isEmpty());
        assertEquals("Note successfully deleted", ((MockPlayer) sender).getReceivedMessage(),
                "Informs the user that deletion worked.");
    }

    @Test
    public void testDeleteNoteWithoutAnything() {
        assertFalse(submitCommand("note", new String[] { "delete" }));
    }

    @Test
    public void testInvalidDeleteRequest() {
        assertFalse(submitCommand("note", new String[] { "delete" }));
    }

    @Test
    public void testRequestCannotComeFromServer() {
        sender = new MockCommandSender();
        assertTrue(submitCommand("note", new String[] { "list" }));
        assertEquals("This command is only available to players", ((MockCommandSender) sender).getLastMessage(),
                "Non-player is informed that this note is only for players.");
    }

    @Test
    public void testShowCommandButNoNotes() {
        assertTrue(submitCommand("note", new String[] { "list" }));
        assertEquals("There are no messages to show", ((MockPlayer) sender).getReceivedMessage(),
                "Informs the player that there are no notes to show.");
    }

    @Test
    public void testNothingWithNote() {
        assertFalse(submitCommand("note", new String[0]));
    }

    @Test
    public void testShowCommandButWithOtherNotesForOtherPlayers() {
        UUID oldUUID = ((MockPlayer) sender).getUniqueId();
        createNote("This is a note");
        assertFalse(store.boxFor(Note.class).isEmpty());
        ((MockPlayer) sender).setUniqueId(new UUID(110101L, 10010001L));
        assertTrue(submitCommand("note", new String[] { "list" }));
        assertEquals("There are no messages to show", ((MockPlayer) sender).getReceivedMessage(),
                "Respects UUID when listing notes");

        ((MockPlayer) sender).setUniqueId(oldUUID);
        assertTrue(submitCommand("note", new String[] { "list" }));
        assertEquals("1 - This is a note", ((MockPlayer) sender).getReceivedMessage(),
                "Respects UUID when listing notes");
    }

    @Test
    public void testDeleteCommandButWithNoteBelongingToOtherPlayer() {
        createNote("This is a note");
        assertFalse(store.boxFor(Note.class).isEmpty());
        ((MockPlayer) sender).setUniqueId(new UUID(110101L, 10010001L));
        assertTrue(submitCommand("note", new String[] { "delete", "1" }));
        assertEquals("No note with ID 1 to delete", ((MockPlayer) sender).getReceivedMessage(),
                "Respects UUID when deleting notes");
    }

    @Test
    public void testShowNoteCommand() {
        createNote("Hey there");
        assertFalse(store.boxFor(Note.class).isEmpty());
        assertTrue(submitCommand("note", new String[] { "show", "1" }));
        assertEquals("Hey there", ((MockPlayer) sender).getReceivedMessage());
    }

    @Test
    public void testAttemptToShowCommandAndUseNonId() {
        assertFalse(submitCommand("note", new String[] { "show", "foo" }));
    }

    @Test
    public void testAttemptToDeleteCommandAndUseNonId() {
        assertFalse(submitCommand("note", new String[] { "delete", "foo" }));
    }

    private void createNote(String content) {
        Note note = new Note();
        note.setContent(content);
        note.setPlayerUUID(((MockPlayer) sender).getUniqueId());
        store.boxFor(Note.class).put(note);
    }

    private boolean submitCommand(String alias, String[] args) {
        NoteCommand noteCommand = new NoteCommand(store);
        return noteCommand.onCommand(sender, mockCommand, alias, args);
    }
}