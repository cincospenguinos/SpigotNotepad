package usa.alafleur.spigot_notepad.model;

import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    private BoxStore store;

    @BeforeEach
    public void setUp() throws IOException {
        store = MyObjectBox.builder()
                .directory(new File("test/"))
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();
    }

    @Test
    public void testConstructor() {
        Note note = new Note();
    }
}