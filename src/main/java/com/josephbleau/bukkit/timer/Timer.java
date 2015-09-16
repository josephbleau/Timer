package com.josephbleau.bukkit.timer;

public class Timer {
    private long lastUpdate;
    private long runTime;
    private long targetRunTime;
    private TimerState state;

    public Timer(long targetRunTime, boolean running) {
        if (running) {
            this.lastUpdate = System.currentTimeMillis();
            this.state = TimerState.RUNNING;
        } else {
            this.lastUpdate = -1;
            this.state = TimerState.NOT_RUNNING;
        }

        this.runTime = 0;
        this.targetRunTime = targetRunTime;
    }

    public void run() {
        state = TimerState.RUNNING;
        update();
    }

    public void stop() {
        if (state != TimerState.FINISHED) {
            lastUpdate = -1;
            state = TimerState.NOT_RUNNING;
        }
    }

    public TimerState update() {
        if (state == TimerState.RUNNING) {
            if (lastUpdate != -1) {
                long currentTime = System.currentTimeMillis();
                long timeDelta = currentTime - lastUpdate;

                runTime += timeDelta;

                if (runTime >= targetRunTime) {
                    state = TimerState.FINISHED;
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
