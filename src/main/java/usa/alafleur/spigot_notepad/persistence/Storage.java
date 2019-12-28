package usa.alafleur.spigot_notepad.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import usa.alafleur.spigot_notepad.model.Note;
import usa.alafleur.spigot_notepad.model.Notepad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Storage {
    public static final String STORAGE_FILE_NAME = ".spigot-notepad-storage.json";

    public static void saveToFile(Notepad notepad) {
        String json = toJson(notepad);

        try {
            FileWriter writer = new FileWriter(new File(STORAGE_FILE_NAME));
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Notepad restoreFromFile() {
        File f = new File(STORAGE_FILE_NAME);

        if (f.exists()) {
            try {
                Scanner s = new Scanner(f);
                String json = s.nextLine();
                s.close();

                return fromJson(json);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return new Notepad();
    }

    private static String toJson(Notepad notepad) {
        List<Note> allNotes = notepad.getAllNotes();
        Gson gson = new Gson();
        return gson.toJson(allNotes);
    }

    private static Notepad fromJson(String json) {
        Gson gson = new Gson();
        List<Note> notes = gson.fromJson(json, new TypeToken<List<Note>>(){}.getType());
        return Notepad.fromNotesList(notes);
    }
}
