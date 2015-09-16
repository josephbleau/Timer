package com.josephbleau.bukkit.timer.exception;

/**
 * This exception is thrown when the caller attempts to create a timer by a name which
 * is already registered.
 */
public class TimerWithNameExistsException extends Exception {
    public TimerWithNameExistsException() {
        super();
    }
}
