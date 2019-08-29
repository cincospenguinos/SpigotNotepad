package usa.alafleur.spigot_notepad.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class MockCommand extends Command {

    private boolean isValid;

    public MockCommand() {
        super("", "", "", new ArrayList<String>());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
