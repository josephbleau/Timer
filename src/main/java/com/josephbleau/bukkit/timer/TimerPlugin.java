package com.josephbleau.bukkit.timer;

import org.bukkit.plugin.java.JavaPlugin;

public final class TimerPlugin extends JavaPlugin {
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
        getLogger().info("Timer plugin is enabled.");
        stopTimerThread = false;
        timerUpdateThread.start();
    }

    @Override
    public void onDisable() {
        getLogger().info("Timer plugin was disabled.");
        stopTimerThread = true;
    }
}
