package usa.alafleur.spigot_notepad;

import io.objectbox.BoxStore;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import usa.alafleur.spigot_notepad.command.NoteCommand;
import usa.alafleur.spigot_notepad.command.NoteTabCompleter;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    private static final String DB_FILE_NAME = ".spigot-notepad-db";

    @Override
    public void onEnable() {
        super.onEnable();

        PluginCommand command = this.getCommand("note");


        if (command != null) {
//            store = MyObjectBox.builder().name(DB_FILE_NAME).build();
//            TabCompleter noteTabCompleter = new NoteTabCompleter();
//            CommandExecutor noteCommand = new NoteCommand(store);
//
//            command.setExecutor(noteCommand);
//            command.setTabCompleter(noteTabCompleter);
        } else {
            getLogger().log(Level.SEVERE,
                    "Plugin command came in null! Command will not be handled");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        // TODO: Save what we need as JSON or something--it's a lot simpler

//        if (store != null && !store.isClosed()) {
//            store.close();
//        }
    }
}
