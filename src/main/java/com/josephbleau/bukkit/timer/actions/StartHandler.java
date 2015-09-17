package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.TimerInvalidStateTransitionException;
import com.josephbleau.bukkit.timer.exception.TimerNotFoundException;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class StartHandler extends ActionHandler {

    public StartHandler(Logger logger, TimerManager timerManager) {
        super(logger, timerManager);
    }

    @Override
    public boolean handle(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            return false;
        }

        String timerName = args[1];

        try {
            getTimerManager().runTimer(timerName);
        } catch (TimerNotFoundException e) {
            getLogger().info("Timer by the name of '" + timerName + "' could not be found.");
            return false;
        } catch (TimerInvalidStateTransitionException e) {
            getLogger().info("Timer by the name of '"+timerName+"' is already running or is finished.");
            return false;
        }

        return true;
    }
}
