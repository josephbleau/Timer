package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.TimerNotFoundException;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

/**
 * Handle /timer clear [timer name].
 *
 * Remove the timer by the given name.
 */
public class ClearHandler extends ActionHandler {
    public ClearHandler(Logger logger, TimerManager timerManager) {
        super(logger, timerManager);
    }

    @Override
    public boolean handle(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            return false;
        }

        String timerName = args[1];

        try {
            getTimerManager().deleteTimer(timerName);
        } catch (TimerNotFoundException e) {
            commandSender.sendMessage("Timer by the name of '" + timerName + "' could not be found.");
            return false;
        }

        commandSender.sendMessage("Timer by the name of '" + timerName + "' has been removed.");
        return true;
    }
}
