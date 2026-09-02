package me.wolfii.legacyparkourcompat.recording;

/**
 * Shared helpers for turning Minecraft movement impulses into recording bits.
 */
public final class MovementAxes {
    private MovementAxes() {
    }

    public static int fromImpulses(
        float forwardImpulse,
        float leftImpulse,
        boolean jump,
        boolean sneak,
        boolean sprint,
        boolean use
    ) {
        return TickButtons.pack(
            forwardImpulse > 0.0F,
            forwardImpulse < 0.0F,
            leftImpulse > 0.0F,
            leftImpulse < 0.0F,
            jump,
            sneak,
            sprint,
            use
        );
    }
}
