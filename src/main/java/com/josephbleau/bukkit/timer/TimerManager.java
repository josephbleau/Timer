package com.josephbleau.bukkit.timer;

import com.josephbleau.bukkit.timer.exception.InvalidTimeStringException;
import com.josephbleau.bukkit.timer.exception.TimerCannotRunWhenFinishedException;
import com.josephbleau.bukkit.timer.exception.TimerNotFoundException;
import com.josephbleau.bukkit.timer.exception.TimerWithNameExistsException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TimerManager {
    private Map<String, Timer> timers = new HashMap<String, Timer>();

    public TimerManager() {

    }

    public void updateTimers() {
        Iterator it = timers.entrySet().iterator();
        while (it.hasNext()) {

        }
    }

    /**
     * Create a new timer by the given name.
     * @param timerName This name is how the timer will be referenced in-game.
     * @param timeString Time string (e.g. 0d0h0m1s, see {@link TimerManager#parseTimeString(String)} for more information on time strings.
     * @param running Specify whether or not the timer should begin running immediately. If this is set to false you may run the timer
     *                at a later point by calling {@link TimerManager#runTimer(String)}.
     * @throws TimerWithNameExistsException If a timer by the given timerName already exists this exception is thrown.
     * @throws InvalidTimeStringException If the timer string provided is malformed this exception is thrown.
     */
    public void createTimer(String timerName, String timeString, boolean running) throws TimerWithNameExistsException, InvalidTimeStringException {
        if (timers.get(timerName) != null) {
            throw new TimerWithNameExistsException();
        }

        Timer timer = new Timer(parseTimeString(timeString), running);
        timers.put(timerName, timer);
    }

    public void runTimer(String timerName) throws TimerCannotRunWhenFinishedException, TimerNotFoundException {
        if (timers.get(timerName) != null) {
            if (timers.get(timerName).getState() == TimerState.FINISHED) {
                throw new TimerCannotRunWhenFinishedException();
            }

            timers.get(timerName).run();
        } else {
            throw new TimerNotFoundException();
        }
    }

    /**
     * Parses time strings used to determine how long a timer will run.
     *
     * A valid time string might look like this: 0d0h0m10s although it must not conform specifically
     * to this format. In fact, you may include any number of numeric digits before any time symbol.
     * Additionally, you may optionally leave any time symbol out (although they must remain in that order).
     * If no time symbols are found we will assume the number is in seconds. If any part of the string is
     * malformed we will throw an {@link InvalidTimeStringException}.
     * @param timeString The formatted time string.
     * @return The target time in milliseconds.
     * @throws InvalidTimeStringException
     */
    private long parseTimeString(String timeString) throws InvalidTimeStringException {
        if(timeString == null || timeString.isEmpty()) {
            throw new InvalidTimeStringException();
        }

        try {
            int d,h,m,s = -1;

            d = timeString.lastIndexOf('d');
            h = timeString.lastIndexOf('h');
            m = timeString.lastIndexOf('m');
            s = timeString.lastIndexOf('s');

            // Default to seconds.
            boolean timeSymbolsNotFound = d == -1 && h == -1 && m == -1 && s == -1;
            if (timeSymbolsNotFound) {
                long time = Long.parseLong(timeString);
                return System.currentTimeMillis() + secondsToMilliseconds(time);
            }

            long time = 0;
            int currentPosition = 0;

            if (d != -1) {
                String dayString = timeString.substring(currentPosition, d);
                long days = Long.parseLong(dayString);
                time += daysToMilliseconds(days);
                currentPosition = d;
            }

            if (h != -1) {
                String hourString = timeString.substring(currentPosition, h);
                long hours = Long.parseLong(hourString);
                time += hoursToMilliSeconds(hours);
                currentPosition = h;
            }

            if (m != -1) {
                String minuteString = timeString.substring(currentPosition, m);
                long minutes = Long.parseLong(minuteString);
                time += minutesToMilliseconds(minutes);
                currentPosition = m;
            }

            if (s != -1) {
                String secondString = timeString.substring(currentPosition, s);
                long seconds = Long.parseLong(secondString);
                time += secondsToMilliseconds(seconds);
                currentPosition = s;
            }

            if (currentPosition < timeString.length()-1) {
                throw new InvalidTimeStringException();
            }

            return time;
        } catch (NumberFormatException e) {
            throw new InvalidTimeStringException();
        }
    }

    private long secondsToMilliseconds(long seconds) {
        return seconds * 1000;
    }

    private long minutesToMilliseconds(long minutes) {
        return secondsToMilliseconds(minutes * 60);
    }

    private long hoursToMilliSeconds(long hours) {
        return minutesToMilliseconds(hours * 60);
    }

    private long daysToMilliseconds(long days) {
        return hoursToMilliSeconds(days * 24);
    }
}
