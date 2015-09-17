package com.josephbleau.bukkit.timer;

import com.josephbleau.bukkit.timer.actions.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class TimerPlugin extends JavaPlugin {
    private static Map<String, ActionHandler> actionHandlers = new HashMap<String, ActionHandler>();

    private TimerManager timerManager = new TimerManager();
    private boolean stopTimerThread = false;

    private Thread timerUpdateThread = new Thread(new Runnable() {
        public void run() {
            while(!stopTimerThread) {
                timerManager.updateTimers();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    getLogger().severe("Error in timer update thread: " + e.getMessage());
                    return;
                }
            }
        }
    });

    @Override
    public void onEnable() {
        initializeActionHandlers();
        stopTimerThread = false;
        timerUpdateThread.start();
    }

    private void initializeActionHandlers() {
        actionHandlers.put("add", new AddHandler(getLogger(), timerManager));
        actionHandlers.put("list", new ListHandler(getLogger(), timerManager));
        actionHandlers.put("stop", new StopHandler(getLogger(), timerManager));
        actionHandlers.put("start", new StartHandler(getLogger(), timerManager));
        actionHandlers.put("show", new ShowHandler(getLogger(), timerManager, getServer()));
        actionHandlers.put("clear", new ClearHandler(getLogger(), timerManager));
    }

    @Override
    public void onDisable() {
        stopTimerThread = true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("timer")) {
            if(args.length > 0) {
                String action = args[0];
                return parseTimerAction(sender, action, args);
            }
        }

        return false;
    }

    private boolean parseTimerAction(CommandSender sender, String action, String[] args) {
        ActionHandler handler = actionHandlers.get(action);
        return handler != null && handler.handle(sender, args);
    }
}
