package me.wolfii.legacyparkourcompat.mechanic.fluid;

/** Shared historical point-query boundary math for player eye-water checks. */
public final class PlayerEyeWaterMath {
    private PlayerEyeWaterMath() {
    }

    /** Preserves the historical float addition before comparing the result to the eye's double Y. */
    public static boolean isSubmerged(double eyeY, int eyeBlockY, float fluidHeight) {
        return eyeY < eyeBlockY + (fluidHeight + 0.11111111F);
    }
}
