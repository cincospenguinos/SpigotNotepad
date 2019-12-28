package usa.alafleur.spigot_notepad.command;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usa.alafleur.spigot_notepad.model.Note;
import usa.alafleur.spigot_notepad.model.Notepad;

import java.io.File;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandTest {
    private CommandSender sender;
    private Command mockCommand;
    private Notepad notepad;

    @BeforeEach
    public void setUp() {
        notepad = new Notepad();
        sender = new MockPlayer();
        mockCommand = new MockCommand();
    }

    @AfterEach
    public void tearDown() {}

    @Test
    public void testValidAddNote() {
        ((MockCommand) mockCommand).setValid(true);
        boolean success = submitCommand(new String[] { "add", "Here's", "some", "content" });
        assertTrue(success);

        UUID playerUUID = ((Player)sender).getUniqueId();
        assertFalse(notepad.notesFor(playerUUID).isEmpty());
        assertPlayerReceivedMessage("Note added successfully");
    }

    @Test
    public void testAddNoteWithAlias() {
        ((MockCommand) mockCommand).setValid(true);
        boolean success = submitCommand(new String[] { "create", "Here's", "some", "content" });
        assertTrue(success);
    }

    @Test
    public void testShowAllNotes() {
        createNote("content");

        boolean success = submitCommand(new String[] { "list" });
        assertTrue(success);
        assertEquals("§l1§r - content", ((MockPlayer) sender).getReceivedMessage(),
                "Shows all messages in correct format");
    }

    @Test
    public void testDeleteNoteById() {
        createNote("Delete me please");

        boolean success = submitCommand(new String[] { "delete", "1" });
        assertTrue(success);
        assertTrue(notepad.notesFor(((Player)sender).getUniqueId()).isEmpty());
        assertPlayerReceivedMessage("Note successfully deleted", "Informs the user that deletion worked.");
    }

    @Test
    public void testDeleteNoteWithoutAnything() {
        assertFalse(submitCommand(new String[] { "delete" }));
    }

    @Test
    public void testInvalidDeleteRequest() {
        assertFalse(submitCommand(new String[] { "delete" }));
    }

    @Test
    public void testRequestCannotComeFromServer() {
        sender = new MockCommandSender();
        assertTrue(submitCommand(new String[] { "list" }));
        assertEquals("This command is only available to players", ((MockCommandSender) sender).getLastMessage(),
                "Non-player is informed that this note is only for players.");
    }

    @Test
    public void testShowCommandButNoNotes() {
        assertTrue(submitCommand(new String[] { "list" }));
        assertPlayerReceivedMessage("There are no messages to show",
                "Informs the player that there are no notes to show.");
    }

    @Test
    public void testNothingWithNote() {
        assertFalse(submitCommand(new String[0]));
    }

    @Test
    public void testShowCommandButWithOtherNotesForOtherPlayers() {
        UUID oldUUID = ((MockPlayer) sender).getUniqueId();
        createNote("This is a note");

        ((MockPlayer) sender).setUniqueId(new UUID(110101L, 10010001L));
        assertTrue(submitCommand(new String[] { "list" }));
        assertPlayerReceivedMessage("There are no messages to show", "Respects UUID when listing notes");

        ((MockPlayer) sender).setUniqueId(oldUUID);
        assertTrue(submitCommand(new String[] { "list" }));
        assertPlayerReceivedMessage("§l1§r - This is a note", "Respects UUID when listing notes");
    }

    @Test
    public void testDeleteCommandButWithNoteBelongingToOtherPlayer() {
        createNote("This is a note");
        ((MockPlayer) sender).setUniqueId(new UUID(110101L, 10010001L));
        assertTrue(submitCommand(new String[] { "delete", "1" }));
        assertPlayerReceivedMessage("No note with ID 1 to delete", "Respects UUID when deleting notes");
    }

    @Test
    public void testShowNoteCommand() {
        createNote("Hey there");
        assertTrue(submitCommand(new String[] { "show", "1" }));
        assertPlayerReceivedMessage("Hey there");
    }

    @Test
    public void testListCommandWithLongNote() {
        createNote("This is an extremely long note, with a lot of stuff inside of " +
                "it, and will not fit into a single line, so it must be truncated.");
        submitCommand(new String[] { "list" });
        assertPlayerReceivedMessage("§l1§r - This is an extre");
    }

    @Test
    public void testAttemptToShowCommandAndUseNonId() {
        assertFalse(submitCommand(new String[] { "show", "foo" }));
    }

    @Test
    public void testAttemptToDeleteCommandAndUseNonId() {
        assertFalse(submitCommand(new String[] { "delete", "foo" }));
    }

    private void createNote(String content) {
        Note note = new Note();
        note.setContent(content);
        note.setPlayerUUID(((MockPlayer) sender).getUniqueId());
        notepad.add(note);
    }

    private boolean submitCommand(String[] args) {
        NoteCommand noteCommand = new NoteCommand(notepad);
        return noteCommand.onCommand(sender, mockCommand, "note", args);
    }

    private void assertPlayerReceivedMessage(String message) {
        assertTrue(((MockPlayer) sender).getReceivedMessage().contains(message));
    }

    private void assertPlayerReceivedMessage(String message, String explanation) {
        assertTrue(((MockPlayer) sender).getReceivedMessage().contains(message), explanation);
    }
}