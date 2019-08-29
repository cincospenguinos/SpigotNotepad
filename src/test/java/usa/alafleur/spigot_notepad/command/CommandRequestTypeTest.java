package usa.alafleur.spigot_notepad.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandRequestTypeTest {

    @Test
    public void testAddAliases() {
        assertEquals(CommandRequestType.ADD_REQUEST, CommandRequestType.extractValue("add"));
        assertEquals(CommandRequestType.ADD_REQUEST, CommandRequestType.extractValue("create"));
        assertEquals(CommandRequestType.ADD_REQUEST, CommandRequestType.extractValue("new"));
    }

    @Test
    public void testListAliases() {
        assertEquals(CommandRequestType.LIST_REQUEST, CommandRequestType.extractValue("list"));
    }

    @Test
    public void testDeleteAliases() {
        assertEquals(CommandRequestType.DELETE_REQUEST, CommandRequestType.extractValue("delete"));
        assertEquals(CommandRequestType.DELETE_REQUEST, CommandRequestType.extractValue("remove"));
    }

    @Test
    public void testShowAliases() {
        assertEquals(CommandRequestType.SHOW_REQUEST, CommandRequestType.extractValue("show"));
    }

    @Test
    public void testInvalidType() {
        assertEquals(CommandRequestType.INVALID_REQUEST,
                CommandRequestType.extractValue("notavalidrequest"));
    }
}