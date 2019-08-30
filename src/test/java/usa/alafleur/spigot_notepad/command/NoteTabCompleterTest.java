package usa.alafleur.spigot_notepad.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteTabCompleterTest {
    private static final List<String> ALL_COMMANDS = Arrays.asList("add", "list", "delete", "read");

    private CommandSender sender;
    private Command mockCommand;
    private NoteTabCompleter completer;

    @BeforeEach
    public void setup() {
        completer = new NoteTabCompleter();
        sender = new MockPlayer();
        mockCommand = new MockCommand();
    }

    @Test
    public void testCompleteAll() {
        List<String> list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "" });

        for (String s : list) {
            assertTrue(ALL_COMMANDS.contains(s));
        }

        for (String s : ALL_COMMANDS) {
            assertTrue(list.contains(s));
        }
    }

    @Test
    public void testCompleteAdd() {
        List<String> list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "a" });
        assertTrue(list.contains("add"));
        assertEquals(1, list.size());

        list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "ad" });
        assertTrue(list.contains("add"));
        assertEquals(1, list.size());
    }

    @Test
    public void testCompleteList() {
        List<String> list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "l" });
        assertTrue(list.contains("list"));
        assertEquals(1, list.size());

        list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "li" });
        assertTrue(list.contains("list"));
        assertEquals(1, list.size());

        list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "lis" });
        assertTrue(list.contains("list"));
        assertEquals(1, list.size());
    }

    @Test
    public void testCompleteRead() {
        List<String> list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "r" });
        assertTrue(list.contains("read"));
        assertEquals(1, list.size());

        list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "re" });
        assertTrue(list.contains("read"));
        assertEquals(1, list.size());

        list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "rea" });
        assertTrue(list.contains("read"));
        assertEquals(1, list.size());
    }

    @Test
    public void testCompleteDelete() {
        List<String> list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "d" });
        assertTrue(list.contains("delete"));
        assertEquals(1, list.size());

        list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "de" });
        assertTrue(list.contains("delete"));
        assertEquals(1, list.size());

        list = completer.onTabComplete(sender, mockCommand, "note", new String[] { "del" });
        assertTrue(list.contains("delete"));
        assertEquals(1, list.size());
    }
}