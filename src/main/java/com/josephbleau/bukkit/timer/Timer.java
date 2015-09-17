package com.josephbleau.bukkit.timer;

/**
 * Simple timer implementation. Counts down from a given target run time.
 */
public class Timer {
    private long lastUpdate;
    private long runTime;
    private long targetRunTime;
    private TimerState state;

    public Timer(long targetRunTime, boolean running) {
        if (running) {
            this.lastUpdate = System.currentTimeMillis();
            this.state = TimerState.running;
        } else {
            this.lastUpdate = -1;
            this.state = TimerState.notRunning;
        }

        this.runTime = 0;
        this.targetRunTime = targetRunTime;
    }

    /**
     * Start the countdown timer.
     */
    public void run() {
        state = TimerState.running;
        update();
    }

    /**
     * Stop the countdown timer.
     */
    public void stop() {
        if (state != TimerState.fininshed) {
            lastUpdate = -1;
            state = TimerState.notRunning;
        }
    }

    /**
     * Update the state of the countdown timer.
     * @return Current state of the timer.
     */
    public TimerState update() {
        if (state == TimerState.running) {
            if (lastUpdate != -1) {
                long currentTime = System.currentTimeMillis();
                long timeDelta = currentTime - lastUpdate;

                runTime += timeDelta;

                if (runTime >= targetRunTime) {
                    state = TimerState.fininshed;
                }
	        }
           
	        lastUpdate = System.currentTimeMillis();
        }

        return state;
    }

    public TimerState getState() {
        return state;
    }

    public long getRunTime() {
        return runTime;
    }

    public long getTargetRunTime() {
        return targetRunTime;
    }
}
