package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerMovementEyeHeightHook;
import net.minecraft.world.entity.player.Player;

/**
 * Feather 1.12.2 PlayerEntity.getEyeHeight predates the swimming flag branch in Feather 1.13
 * PlayerEntity.getEyeHeight. Preserve native eye height for this later feature on older profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeBeforeSwimmingEyeHeight implements PlayerMovementEyeHeightHook {
    @Override
    public float eyeHeight(Player player, float vanilla) {
        return vanilla;
    }
}
