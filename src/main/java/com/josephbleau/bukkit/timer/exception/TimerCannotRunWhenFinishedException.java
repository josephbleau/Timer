package com.josephbleau.bukkit.timer.exception;

/**
 * Thrown when the caller attempts to begin a timer which has already running.
 */
public class TimerCannotRunWhenFinishedException extends Exception {
    public TimerCannotRunWhenFinishedException(){
        super();
    }
}
