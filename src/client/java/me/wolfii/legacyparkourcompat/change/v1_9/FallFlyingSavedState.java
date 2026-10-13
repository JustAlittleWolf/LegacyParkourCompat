package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FallFlyingSavedStateHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.10.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void writeCustomNbt(NbtCompound nbt).
 *   public void readCustomNbt(NbtCompound nbt).
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void writeCustomNbt(NbtCompound nbt).
 *   public void readCustomNbt(NbtCompound nbt).
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-F005-fall-flying-state-persists-across-player-load is unknown within (1.9.4, 1.10.2]
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class FallFlyingSavedState implements FallFlyingSavedStateHook {
    @Override
    public boolean shouldPersistFallFlyingState(Player player, ParkourVersion selected) {
        return selected != ParkourVersion.V1_9;
    }

    @Override
    public void afterFallFlyingStateLoaded(Player player, ParkourVersion selected) {
        if (selected == ParkourVersion.V1_9) {
            ((EntityInvoker) player).legacyparkourcompat$setSharedFlag(7, false);
        }
    }
}
