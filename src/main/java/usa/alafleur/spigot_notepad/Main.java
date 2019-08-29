package usa.alafleur.spigot_notepad;

import io.objectbox.BoxStore;
import org.bukkit.plugin.java.JavaPlugin;
import usa.alafleur.spigot_notepad.command.NoteCommand;
import usa.alafleur.spigot_notepad.model.MyObjectBox;

public class Main extends JavaPlugin {

    private BoxStore store;

    @Override
    public void onEnable() {
        super.onEnable();

        store = MyObjectBox.builder().name("spigot-notepad-db").build();
        this.getCommand("note").setExecutor(new NoteCommand(store));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        store.close();
    }
}
