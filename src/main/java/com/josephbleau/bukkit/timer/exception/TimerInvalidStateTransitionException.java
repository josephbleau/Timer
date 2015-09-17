package com.josephbleau.bukkit.timer.exception;

/**
 * Thrown when the caller attempts to run a timer that is finished or already running or when the caller
 * attempts to stop a timer which is stopped or finished.
 */
public class TimerInvalidStateTransitionException extends Exception {
    public TimerInvalidStateTransitionException(){
        super();
    }
}
