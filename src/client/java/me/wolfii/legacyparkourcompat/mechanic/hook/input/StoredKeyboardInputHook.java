package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/**
 * Applies historical slowdown to the independent keyboard axes after key
 * polling. The producer contract applies to 1.16 through 1.21.4; consumers
 * preserve native fallback outside that range unless another resolved
 * producer covers the selected profile.
 */
@MechanicType("player.input.stored-keyboard-slowdown")
public interface StoredKeyboardInputHook extends VersionedMechanic {
    default boolean supports(Player player, ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_16)
            && selected.olderThanOrEqual(ParkourVersion.V1_21_4)
            && KeyboardInputSupport.supportsMovementFeatures(player, selected)
            && KeyboardInputSupport.swiftSneakLevel(player, selected).isPresent();
    }

    /**
     * Reconstruct the signed keyboard axes from the polled keys and apply the
     * selected release's slowdown order. {@code slow} must be sampled before
     * polling; item-use scaling is supplied after polling when its historical
     * gate ({@code usingItem && !passenger}) is true.
     */
    Vec2 apply(Input polledKeys, boolean slow, float sneakingSpeed, boolean itemUseSlowdown);
}
