package com.josephbleau.bukkit.timer;

/**
 * This exception is thrown when the caller attempts to create a timer with
 * an invalid timer string. See {@link TimerManager#parseTimeString(String)}
 * for information regarding valid time strings.
 */
public class InvalidTimeStringException extends Exception {
    public InvalidTimeStringException() {
        super();
    }
}
