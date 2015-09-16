package com.josephbleau.bukkit.timer.time;

public class SystemTimeProvider implements TimeProvider{

    public long getTimeInMilliseconds() {
        return System.currentTimeMillis();
    }
}
