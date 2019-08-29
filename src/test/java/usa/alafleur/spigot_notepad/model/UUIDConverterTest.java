package usa.alafleur.spigot_notepad.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UUIDConverterTest {

    private UUIDConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new UUIDConverter();
    }

    @Test
    public void testConvertsUUIDToString() {
        UUID uuid = new UUID(123L, 456L);
        String databaseValue = converter.convertToDatabaseValue(uuid);
        assertEquals(uuid.toString(), databaseValue, "Converts to string");
    }

    @Test
    public void testConvertsStringToUUID() {
        UUID uuid = new UUID(123L, 456L);
        String databaseValue = uuid.toString();
        UUID other = converter.convertToEntityProperty(databaseValue);
        assertEquals(uuid, other, "Converts from string");
    }
}