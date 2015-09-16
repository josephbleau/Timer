package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.InvalidTimeStringException;
import com.josephbleau.bukkit.timer.exception.TimerWithNameExistsException;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class AddHandler extends ActionHandler {

    public AddHandler(Logger logger, TimerManager timerManager) {
        super(logger, timerManager);
    }

    public boolean handle(CommandSender commandSender, String[] args) {
        if(args.length != 3) {
            return false;
        }

        String timerName = args[1];
        String timeString = args[2];

        try {
            getTimerManager().createTimer(timerName, timeString, true);
        } catch (TimerWithNameExistsException e) {
            getLogger().warning("A timer by the name '"+timerName+"' already exists.");
            return false;
        } catch (InvalidTimeStringException e) {
            getLogger().warning("The time string '"+timeString+"' is not well-formed.");
            return false;
        }

        getLogger().info("A timer by the name of '"+timerName+"' was created.");
        return true;
    }
}
