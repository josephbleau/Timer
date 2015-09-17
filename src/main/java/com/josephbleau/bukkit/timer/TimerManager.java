package com.josephbleau.bukkit.timer;

import com.josephbleau.bukkit.timer.exception.InvalidTimeStringException;
import com.josephbleau.bukkit.timer.exception.TimerInvalidStateTransitionException;
import com.josephbleau.bukkit.timer.exception.TimerNotFoundException;
import com.josephbleau.bukkit.timer.exception.TimerWithNameExistsException;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {
    private Map<String, Timer> timers = new HashMap<String, Timer>();

    public TimerManager() {}

    /**
     * Run update logic for all currently running timers and notify any players registered to listen
     * to timer events.
     */
    public void updateTimers() {
        for (Map.Entry<String, Timer> entry : timers.entrySet()) {
            Timer timer = entry.getValue();

            if (timer.getState()  == TimerState.running) {
                timer.update();
            }
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
     * @return The created timer.
     */
    public Timer createTimer(String timerName, String timeString, boolean running) throws TimerWithNameExistsException, InvalidTimeStringException {
        if (timers.get(timerName) != null) {
            throw new TimerWithNameExistsException();
        }

        Timer timer = new Timer(parseTimeString(timeString), running);
        timers.put(timerName, timer);

        return timer;
    }

    /**
     * Remove a timer.
     * @param timerName Name of the timer to remove.
     * @throws TimerNotFoundException when no timer by the given name exists.
     */
    public void deleteTimer(String timerName) throws TimerNotFoundException {
        if (timers.get(timerName) == null) {
            throw new TimerNotFoundException();
        }

        timers.remove(timerName);
    }

    /**
     * Run the timer by the given name.
     * @param timerName Name of the timer that is being ran.
     * @throws TimerInvalidStateTransitionException when the timer is already running, or is finished.
     * @throws TimerNotFoundException when no timer by the given name exists.
     */
    public void runTimer(String timerName) throws TimerInvalidStateTransitionException, TimerNotFoundException {
        if (timers.get(timerName) != null) {
            if (timers.get(timerName).getState() != TimerState.notRunning) {
                throw new TimerInvalidStateTransitionException();
            }

            timers.get(timerName).run();
        } else {
            throw new TimerNotFoundException();
        }
    }

    /**
     * Stop a timer by the given name.
     * @param timerName Name of the timer that is being stopped.
     * @throws TimerInvalidStateTransitionException when the timer is already finished, or is not running.
     * @throws TimerNotFoundException when no timer by the given name exists.
     */
    public void stopTimer(String timerName) throws TimerInvalidStateTransitionException, TimerNotFoundException {
        if (timers.get(timerName) != null) {
            if (timers.get(timerName).getState() != TimerState.running) {
                throw new TimerInvalidStateTransitionException();
            }

            timers.get(timerName).stop();
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
    public long parseTimeString(String timeString) throws InvalidTimeStringException {
        if(timeString == null || timeString.isEmpty()) {
            throw new InvalidTimeStringException();
        }

        try {
            int d,h,m,s;

            d = timeString.lastIndexOf('d');
            h = timeString.lastIndexOf('h');
            m = timeString.lastIndexOf('m');
            s = timeString.lastIndexOf('s');

            // Default to seconds.
            boolean timeSymbolsNotFound = d == -1 && h == -1 && m == -1 && s == -1;
            if (timeSymbolsNotFound) {
                long time = Long.parseLong(timeString);
                return secondsToMilliseconds(time);
            }

            long time = 0;
            int currentPosition = 0;

            if (d != -1) {
                String dayString = timeString.substring(currentPosition, d);
                long days = Long.parseLong(dayString);
                time += daysToMilliseconds(days);
                currentPosition = d+1;
            }

            if (h != -1) {
                String hourString = timeString.substring(currentPosition, h);
                long hours = Long.parseLong(hourString);
                time += hoursToMilliseconds(hours);
                currentPosition = h+1;
            }

            if (m != -1) {
                String minuteString = timeString.substring(currentPosition, m);
                long minutes = Long.parseLong(minuteString);
                time += minutesToMilliseconds(minutes);
                currentPosition = m+1;
            }

            if (s != -1) {
                String secondString = timeString.substring(currentPosition, s);
                long seconds = Long.parseLong(secondString);
                time += secondsToMilliseconds(seconds);
                currentPosition = s+1;
            }

            if (currentPosition < timeString.length()-1) {
                throw new InvalidTimeStringException();
            }

            return time;
        } catch (NumberFormatException e) {
            throw new InvalidTimeStringException();
        }
    }

    /**
     * Converts the time remaining in a timer to a pretty-printed string that follows the format
     * of time strings used by {@link TimerManager#createTimer(String, String, boolean)}.
     * @param timer Timer whose remaining time will be pretty-printed.
     * @return Time string.
     */
    public String getPrettyTimeLeft(Timer timer) {
        long completeTime = timer.getTargetRunTime() - timer.getRunTime();

        int  d,h,m,s;

        d = (int) Math.floor(completeTime / daysToMilliseconds(1));
        completeTime -= daysToMilliseconds((long)d);
        h = (int) Math.floor(completeTime / hoursToMilliseconds(1));
        completeTime -= hoursToMilliseconds((long) h);
        m = (int) Math.floor(completeTime / minutesToMilliseconds(1));
        completeTime -= minutesToMilliseconds((long) m);
        s = (int) Math.floor(completeTime / secondsToMilliseconds(1));

        String timeString = "";

        if (d > 0) {
            timeString += d + "d";
        }

        if (h > 0) {
            timeString += h + "h";
        }

        if (m > 0) {
            timeString += m + "m";
        }

        if (s > 0) {
            timeString += s + "s";
        }

        return timeString;
    }

    /**
     * Converts the time remaining in a timer to a pretty-printed string that follows the format
     * of time strings used by {@link TimerManager#createTimer(String, String, boolean)}.
     * @param timerName Name of the timer whose remaining time will be pretty-printed.
     * @throws TimerNotFoundException when a timer by the given name cannot be found.
     * @return Time string.
     */
    public String getPrettyTimeLeft(String timerName) throws TimerNotFoundException {
        if (timers.get(timerName) == null) {
            throw new TimerNotFoundException();
        }

        return getPrettyTimeLeft(timers.get(timerName));
    }

    private long secondsToMilliseconds(long seconds) {
        return seconds * 1000;
    }

    private long minutesToMilliseconds(long minutes) {
        return secondsToMilliseconds(minutes * 60);
    }

    private long hoursToMilliseconds(long hours) {
        return minutesToMilliseconds(hours * 60);
    }

    private long daysToMilliseconds(long days) {
        return hoursToMilliseconds(days * 24);
    }

    public Map<String, Timer> getTimers() {
        return timers;
    }
}
