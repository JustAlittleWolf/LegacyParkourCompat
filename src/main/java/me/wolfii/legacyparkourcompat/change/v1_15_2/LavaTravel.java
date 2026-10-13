package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.LavaTravelHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines [[1913, 1926]].
 *   Source SHA-256: 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54.
 * 1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines [[1959, 1978]].
 *   Source SHA-256: b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class LavaTravel implements LavaTravelHook {
    @Override
    public boolean isShallowForTravel(Player player, boolean vanillaShallow) {
        return false;
    }
}
