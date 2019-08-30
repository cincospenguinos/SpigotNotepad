package usa.alafleur.spigot_notepad;

import io.objectbox.BoxStore;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import usa.alafleur.spigot_notepad.command.NoteCommand;
import usa.alafleur.spigot_notepad.model.MyObjectBox;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    private static final String DB_FILE_NAME = ".spigot-notepad-db";
    private BoxStore store;

    @Override
    public void onEnable() {
        super.onEnable();

        PluginCommand command = this.getCommand("note");

        if (command != null) {
            store = MyObjectBox.builder().name(DB_FILE_NAME).build();
            command.setExecutor(new NoteCommand(store));
        } else {
            getLogger().log(Level.SEVERE,
                    "Plugin command came in null! Command will not be handled");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (store != null && !store.isClosed()) {
            store.close();
        }
    }
}
