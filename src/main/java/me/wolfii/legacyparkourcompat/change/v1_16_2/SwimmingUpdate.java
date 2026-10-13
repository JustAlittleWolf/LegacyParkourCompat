package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SwimmingUpdateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Preserves the 1.16.5 swimming entry predicate without the later block-fluid gate.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.16.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   updateSwimming; lines [[949, 955]].
 *   Source SHA-256: f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666.
 * Candidate locators only: operation fidelity, independent review and profile applicability remain open.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class SwimmingUpdate implements SwimmingUpdateHook {
    @Override
    public boolean isSwimmingAfterUpdate(Player player, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_13)) {
            return false;
        }

        if (player.isSwimming()) {
            return player.isSprinting() && player.isInWater() && !player.isPassenger();
        }
        return player.isSprinting() && player.isUnderWater() && !player.isPassenger();
    }
}
