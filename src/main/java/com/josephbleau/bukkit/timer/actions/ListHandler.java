package com.josephbleau.bukkit.timer.actions;

import com.josephbleau.bukkit.timer.Timer;
import com.josephbleau.bukkit.timer.TimerManager;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.logging.Logger;

public class ListHandler extends ActionHandler {

    public ListHandler(Logger logger, TimerManager timerManager) {
        super(logger, timerManager);
    }

    @Override
    public boolean handle(CommandSender commandSender, String[] args) {
        if (args.length != 1) {
            return false;
        }

        commandSender.sendMessage("Timers:");
        Map<String, Timer> timers = getTimerManager().getTimers();

        for(Map.Entry<String, Timer> entry : timers.entrySet()) {
            String timerName = entry.getKey();
            Timer timer = entry.getValue();

            String infoString = "    " + timerName + ": " + timer.getState().toString() +
                                " " + getTimerManager().getPrettyTimeLeft(timer);

            commandSender.sendMessage(infoString);
        }

        return true;
    }
}
