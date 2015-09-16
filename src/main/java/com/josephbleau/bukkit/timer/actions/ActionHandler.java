package com.josephbleau.bukkit.timer.actions;

import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public abstract class ActionHandler {
    protected Logger logger;

    public ActionHandler(Logger logger) {
        this.logger = logger;
    }

    public abstract boolean handle(CommandSender commandSender, String[] args);
}
