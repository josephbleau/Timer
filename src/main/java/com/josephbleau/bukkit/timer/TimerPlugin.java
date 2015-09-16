package com.josephbleau.bukkit.timer;

import org.bukkit.plugin.java.JavaPlugin;

public final class TimerPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Timer plugin is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Timer plugin was disabled.");
    }
}
