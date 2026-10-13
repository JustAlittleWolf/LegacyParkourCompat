package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.LavaTravelHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * 1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
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
