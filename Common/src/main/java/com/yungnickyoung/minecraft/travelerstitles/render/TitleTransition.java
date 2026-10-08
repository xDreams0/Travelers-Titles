package com.yungnickyoung.minecraft.travelerstitles.render;

/**
 * Pure timing maths shared by biome and dimension title rendering.
 * No Minecraft state or rendering API is required, so we can test edge cases
 * without starting the game.
 */
public final class TitleTransition {
    private TitleTransition() {}

    public record Frame(int opacity, float offsetY, float scale) {}

    public static Frame sample(
        int remainingTicks,
        float partialTicks,
        int fadeInTicks,
        int displayTicks,
        int fadeOutTicks,
        boolean animateMovement
    ) {
        int fadeIn = Math.max(0, fadeInTicks);
        int display = Math.max(0, displayTicks);
        int fadeOut = Math.max(0, fadeOutTicks);

        float total = (float) fadeIn + display + fadeOut;
        float remaining = Math.max(0f, remainingTicks - clamp01(partialTicks));
        float elapsed = Math.max(0f, total - remaining);

        float enter = fadeIn == 0 ? 1f : clamp01(elapsed / fadeIn);
        float exitRemaining = fadeOut == 0 ? 1f : clamp01(remaining / fadeOut);

        // Keep the existing fade-only behaviour when motion is disabled.
        if (!animateMovement) {
            return new Frame(Math.round(255f * enter * exitRemaining), 0f, 1f);
        }

        // Smooth fades and a subtle whole-title movement: the thumbnail, label
        // and optional subtitle all receive the same transformation.
        float easedEnter = smoothstep(enter);
        float easedExitRemaining = smoothstep(exitRemaining);
        int opacity = Math.round(255f * easedEnter * easedExitRemaining);

        // On entry: from 8 pixels below and 94% size to the original position.
        // On exit: drift 6 pixels upward while gently shrinking.
        float exitProgress = 1f - exitRemaining;
        float easedExitMovement = exitProgress * exitProgress * exitProgress;
        float offsetY = 8f * (1f - easedEnter) - 6f * easedExitMovement;
        float scale = 0.94f + 0.06f * easedEnter - 0.04f * easedExitMovement;

        return new Frame(opacity, offsetY, scale);
    }

    private static float clamp01(float value) {
        return Math.max(0f, Math.min(1f, value));
    }

    private static float smoothstep(float value) {
        return value * value * (3f - 2f * value);
    }
}
