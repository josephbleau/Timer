package com.josephbleau.bukkit.timer.exception;

import com.josephbleau.bukkit.timer.TimerManager;

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
