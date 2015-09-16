import com.josephbleau.bukkit.timer.TimerManager;
import com.josephbleau.bukkit.timer.exception.InvalidTimeStringException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class TimerManagerTest {
    private TimerManager timerManager;

    private final long SECOND_TO_MS = 1000;
    private final long MINUTE_TO_MS = 60 * SECOND_TO_MS;
    private final long HOUR_TO_MS = 60 * MINUTE_TO_MS;
    private final long DAY_TO_MS = 24 * HOUR_TO_MS;

    @BeforeMethod
    public void beforeMethod() {
        timerManager = new TimerManager();
    }

    @Test
    public void parseTimeString_noTimeSymbols_parseAsSeconds() throws InvalidTimeStringException {
        // Given
        String timeString = "1234";

        // When
        long time = timerManager.parseTimeString(timeString);

        // Then
        Assert.assertEquals(time, 1234 * SECOND_TO_MS);
    }

    @Test
    public void parseTimeString_wellFormedDayTimeString_parseCorrectly() throws InvalidTimeStringException {
        // Given
        String timeString = "1d0h0m0s";

        // When
        long time = timerManager.parseTimeString(timeString);

        // Then
        Assert.assertEquals(time, DAY_TO_MS);
    }

    @Test
    public void parseTimeString_wellFormedMixedTimeString_parseCorrectly() throws InvalidTimeStringException {
        // Given
        String timeString = "10d5h300m1s";
        long expectedTime = (10 * DAY_TO_MS) + (5 * HOUR_TO_MS) + (300 * MINUTE_TO_MS) + (SECOND_TO_MS);

        // When
        long time = timerManager.parseTimeString(timeString);

        // Then
        Assert.assertEquals(time, expectedTime);
    }

    @Test
    public void parseTimeString_wellFormedMissingSomeSymbolsTimeString_parseCorrectly() throws InvalidTimeStringException {
        // Given
        String timeString = "10d3m";
        long expectedTime = (10 * DAY_TO_MS) + (3 * MINUTE_TO_MS);

        // When
        long time = timerManager.parseTimeString(timeString);

        // Then
        Assert.assertEquals(time, expectedTime);
    }

    @Test(expectedExceptions = InvalidTimeStringException.class)
    public void parseTimeString_malformedTimeStringNonNumeric_throwInvalidTimeStringException() throws InvalidTimeStringException {
        // Given
        String timeString = "0d0h0mss";

        // When
        long time = timerManager.parseTimeString(timeString);
    }

}
