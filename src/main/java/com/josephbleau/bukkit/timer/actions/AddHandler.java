package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.InvalidTimeStringException;
import com.josephbleau.bukkit.timer.exception.TimerWithNameExistsException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.logging.Logger;

/**
 * Handle /timer add [timer name] [time string].
 *
 * Add a new timer by the given name and with a total running time of the given time string.
 */
public class AddHandler extends ActionHandler {

    public AddHandler(Logger logger, Configuration configuration, TimerManager timerManager) {
        super(logger, configuration, timerManager);
    }

    private final String permissionsName = "add";

    public boolean handle(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission(getFullPermissionsName())) {
            return false;
        }

        if(args.length != 3) {
            return false;
        }

        String timerName = args[1];
        String timeString = args[2];

        try {
            getTimerManager().createTimer(timerName, timeString, true);
        } catch (TimerWithNameExistsException e) {
            commandSender.sendMessage("A timer by the name '"+timerName+"' already exists.");
            return false;
        } catch (InvalidTimeStringException e) {
            commandSender.sendMessage("The time string '"+timeString+"' is not well-formed.");
            return false;
        }

        commandSender.sendMessage("A timer by the name of '"+timerName+"' was created.");
        return true;
    }

    @Override
    protected String getActionPermissionsName() {
        return permissionsName;
    }
}
