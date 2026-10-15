package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SneakSpeedScalarHook;

/**
 * Mojmap 1.18.2 KeyboardInput.tick(boolean) uses 0.3F directly. Mojmap 1.19
 * LocalPlayer.aiStep instead passes a Swift Sneak bonus to KeyboardInput.tick.
 * Older double-multiply keyboard producers independently retain their math.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class SneakSpeedScalar implements SneakSpeedScalarHook {
    @Override
    public float scalar(int swiftSneakLevel, double attributeValue) {
        return 0.3F;
    }
}
