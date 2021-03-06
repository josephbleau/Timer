package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.TimerInvalidStateTransitionException;
import com.josephbleau.bukkit.timer.exception.TimerNotFoundException;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

/**
 * Handle /timer stop [timer name].
 *
 * Stop the timer by the given name.
 */
public class StopHandler extends ActionHandler {

    public StopHandler(Logger logger, TimerManager timerManager) {
        super(logger, timerManager);
    }

    @Override
    public boolean handle(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            return false;
        }

        String timerName = args[1];

        try {
            getTimerManager().stopTimer(timerName);
        } catch(TimerNotFoundException e) {
            commandSender.sendMessage("Timer by the name of '"+timerName+"' could not be found.");
            return false;
        } catch (TimerInvalidStateTransitionException e) {
            commandSender.sendMessage("Timer by the name of '"+timerName+"' is already stopped or is finished.");
            return false;
        }

        return true;
    }
}
