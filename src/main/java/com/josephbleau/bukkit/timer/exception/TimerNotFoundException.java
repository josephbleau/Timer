package com.josephbleau.bukkit.timer.exception;

/**
 * Thrown when caller attempts to use timer that does not currently exist.
 */
public class TimerNotFoundException extends Exception{
    public TimerNotFoundException() {
        super();
    }
}
