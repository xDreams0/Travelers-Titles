import com.yungnickyoung.minecraft.travelerstitles.render.TitleTransition;

/** Standalone assertions for animation timing; executed in GitHub Actions. */
public final class TitleTransitionSmokeTest {
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void near(float actual, float expected, String message) {
        check(Math.abs(actual - expected) <= 0.0001f,
            message + ": expected " + expected + ", got " + actual);
    }

    public static void main(String[] args) {
        // Normal timeline: 10 in + 50 held + 10 out.
        var start = TitleTransition.sample(70, 0f, 10, 50, 10, true);
        check(start.opacity() == 0, "first frame should be invisible");
        near(start.offsetY(), 8f, "start position");
        near(start.scale(), 0.94f, "start scale");

        var entering = TitleTransition.sample(65, 0f, 10, 50, 10, true);
        check(entering.opacity() > 0 && entering.opacity() < 255, "entry opacity");
        check(entering.offsetY() > 0 && entering.offsetY() < 8f, "entry slide");
        check(entering.scale() > 0.94f && entering.scale() < 1f, "entry zoom");

        var visible = TitleTransition.sample(60, 0f, 10, 50, 10, true);
        check(visible.opacity() == 255, "hold should be opaque");
        near(visible.offsetY(), 0f, "hold position");
        near(visible.scale(), 1f, "hold scale");

        var leaving = TitleTransition.sample(5, 0f, 10, 50, 10, true);
        check(leaving.opacity() > 0 && leaving.opacity() < 255, "exit opacity");
        check(leaving.offsetY() < 0f && leaving.offsetY() > -6f, "exit slide");
        check(leaving.scale() < 1f && leaving.scale() > 0.96f, "exit shrink");

        var end = TitleTransition.sample(0, 0f, 10, 50, 10, true);
        check(end.opacity() == 0, "end should be transparent");
        near(end.offsetY(), -6f, "end position");
        near(end.scale(), 0.96f, "end scale");

        // An accessible fade-only option must not alter geometry.
        var classic = TitleTransition.sample(65, 0f, 10, 50, 10, false);
        check(classic.opacity() == 128, "classic halfway opacity");
        near(classic.offsetY(), 0f, "classic position");
        near(classic.scale(), 1f, "classic scale");

        // Graceful behaviour for zero/invalid durations and partial ticks.
        var instant = TitleTransition.sample(20, 0f, 0, 20, 0, true);
        check(instant.opacity() == 255, "zero fades should show title instantly");
        near(instant.offsetY(), 0f, "instant position");
        near(instant.scale(), 1f, "instant scale");
        var badTimes = TitleTransition.sample(30, 10f, -5, 20, -10, true);
        check(badTimes.opacity() >= 0 && badTimes.opacity() <= 255, "bounded alpha");
        for (int remaining = 0; remaining <= 70; remaining++) {
            var frame = TitleTransition.sample(remaining, 0.5f, 10, 50, 10, true);
            check(frame.opacity() >= 0 && frame.opacity() <= 255, "alpha range");
            check(frame.offsetY() >= -6f && frame.offsetY() <= 8f, "slide range");
            check(frame.scale() >= 0.94f && frame.scale() <= 1f, "zoom range");
        }
        System.out.println("Title transition timing tests passed.");
    }
}
