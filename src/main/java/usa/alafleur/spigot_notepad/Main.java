package usa.alafleur.spigot_notepad;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import usa.alafleur.spigot_notepad.command.NoteCommand;
import usa.alafleur.spigot_notepad.command.NoteTabCompleter;
import usa.alafleur.spigot_notepad.model.Notepad;
import usa.alafleur.spigot_notepad.persistence.Storage;

import java.util.logging.Level;

public class Main extends JavaPlugin {
    private Notepad notepad;

    @Override
    public void onEnable() {
        super.onEnable();

        PluginCommand command = this.getCommand("note");

        if (command != null) {
            notepad = Storage.restoreFromFile();
            TabCompleter noteTabCompleter = new NoteTabCompleter();
            CommandExecutor noteCommand = new NoteCommand(notepad);

            command.setExecutor(noteCommand);;
            command.setTabCompleter(noteTabCompleter);
        } else {
            getLogger().log(Level.SEVERE,
                    "Plugin command came in null! Command will not be handled");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        Storage.saveToFile(notepad);
    }
}
