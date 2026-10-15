package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SneakSpeedScalarHook;
import net.minecraft.util.Mth;

/**
 * Mojmap 1.19 and 1.20.6 LocalPlayer.aiStep / EnchantmentHelper.getSneakingSpeedBonus:
 * maximum eligible equipped level times float 0.15F, then float addition and
 * explicit clamp. Mojmap 1.21 LocalPlayer.aiStep replaces this with the effective
 * SNEAKING_SPEED attribute cast. The consumer supplies the legs-slot maximum;
 * custom modern attribute state remains outside the historical input path.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class SneakSpeedScalar implements SneakSpeedScalarHook {
    @Override
    public float scalar(int swiftSneakLevel, double attributeValue) {
        return Mth.clamp(0.3F + swiftSneakLevel * 0.15F, 0.0F, 1.0F);
    }
}
