package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

import java.util.logging.Logger;

/**
 * Base action handler for parsed commands.
 */
public abstract class ActionHandler {
    private Logger logger;
    private TimerManager timerManager;
    private Configuration configuration;

    private final String rootPermissionsName = "timer";

    public ActionHandler(Logger logger, Configuration configuration, TimerManager timerManager) {
        this.logger = logger;
        this.configuration = configuration;
        this.timerManager = timerManager;
    }

    public abstract boolean handle(CommandSender commandSender, String[] args);

    protected  Logger getLogger() {
        return logger;
    }

    protected TimerManager getTimerManager() {
        return timerManager;
    }

    protected String getRootPermissionsName() {
        return rootPermissionsName;
    }

    protected abstract String getActionPermissionsName();

    protected String getFullPermissionsName() {
        return getRootPermissionsName() + getActionPermissionsName();
    }
}
