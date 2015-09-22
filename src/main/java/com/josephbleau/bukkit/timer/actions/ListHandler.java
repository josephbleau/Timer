package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.Timer;
import com.josephbleau.bukkit.timer.TimerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Handle /timer list.
 *
 * Displays a list of all existing timers.
 */
public class ListHandler extends ActionHandler {

    public ListHandler(Logger logger, Configuration configuration, TimerManager timerManager) {
        super(logger, configuration, timerManager);
    }

    private final String permissionsName = "list";

    @Override
    public boolean handle(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission(getFullPermissionsName())) {
            return false;
        }

        if (args.length != 1) {
            return false;
        }

        commandSender.sendMessage("Timers:");
        Map<String, Timer> timers = getTimerManager().getTimers();

        if (timers.size() > 0) {
            for (Map.Entry<String, Timer> entry : timers.entrySet()) {
                String timerName = entry.getKey();
                Timer timer = entry.getValue();

                String infoString = "    " + timerName + ": " + timer.getState().toString() +
                        " " + getTimerManager().getPrettyTimeLeft(timer);

                commandSender.sendMessage(infoString);
            }
        } else {
            commandSender.sendMessage("    There are no timers.");
        }

        return true;
    }

    @Override
    protected String getActionPermissionsName() {
        return permissionsName;
    }
}
