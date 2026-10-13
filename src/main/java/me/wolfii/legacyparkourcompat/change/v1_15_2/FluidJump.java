package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FluidJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.LavaGroundJumpHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines [[2265, 2279]].
 *   aiStep; lines [[2266, 2278]].
 *   Source SHA-256: 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
 * 1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep; lines [[2427, 2451]].
 *   Source SHA-256: b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class FluidJump implements FluidJumpHook, LavaGroundJumpHook {
    @Override
    public boolean mayJumpInFluid(Player player, boolean vanillaAffectedByFluids) {
        return true;
    }

    @Override
    public boolean isShallowLavaForGroundJump(Player player, boolean vanillaShallow) {
        return false;
    }
}
