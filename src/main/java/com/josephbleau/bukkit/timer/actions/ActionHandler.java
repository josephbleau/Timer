package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

/**
 * Base action handler for parsed commands.
 */
public abstract class ActionHandler {
    private Logger logger;
    private TimerManager timerManager;

    public ActionHandler(Logger logger, TimerManager timerManager) {
        this.logger = logger;
        this.timerManager = timerManager;
    }

    public abstract boolean handle(CommandSender commandSender, String[] args);

    public Logger getLogger() {
        return logger;
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }
}
