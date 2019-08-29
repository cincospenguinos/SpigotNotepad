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
        assertEquals(CommandRequestType.LIST_REQUEST, CommandRequestType.extractValue("show"));
    }

    @Test
    public void testInvalidType() {
        assertEquals(CommandRequestType.INVALID_REQUEST,
                CommandRequestType.extractValue("notavalidrequest"));
    }
}