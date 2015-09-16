package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.TimerManager;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class AddHandler extends ActionHandler {

    private TimerManager timerManager;

    public AddHandler(Logger logger, TimerManager timerManager) {
        super(logger);
        this.timerManager = timerManager;
    }

    public boolean handle(CommandSender commandSender, String[] args) {
        if(args.length != 3) {
            return false;
        }

        logger.info("Adding timer: " + args[1]);

        return true;
    }
}
