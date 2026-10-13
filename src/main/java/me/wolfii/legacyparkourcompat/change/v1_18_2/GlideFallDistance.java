package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.GlideFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 2074-2079.
 *   Source SHA-256: db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782.
 * 1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 2107-2111.
 *   Source SHA-256: fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77.
 * 1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java:
 *   class declaration / field initialization; lines 63-65.
 *   Source SHA-256: f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b.
 * 1.19.2/mojmap/net/minecraft/world/level/block/FarmBlock.java:
 *   fallOn; lines 89-98.
 *   Source SHA-256: ef03ebba83a5621c1f91bb4f777bd69f1ba3ab6afbc9ed04bcbbbba62cf90fbb.
 * 1.19.3/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkSlowFallDistance; lines 2147-2151.
 *   Source SHA-256: 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32.
 * 1.19.3/mojmap/net/minecraft/world/level/block/FarmBlock.java:
 *   fallOn; lines 89-98.
 *   Source SHA-256: ef03ebba83a5621c1f91bb4f777bd69f1ba3ab6afbc9ed04bcbbbba62cf90fbb.
 * 1.19.4/mojmap/net/minecraft/world/entity/Entity.java:
 *   checkSlowFallDistance; lines 2167-2170.
 *   Source SHA-256: 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines 2048-2051.
 *   Source SHA-256: c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class GlideFallDistance implements GlideFallDistanceHook {
    @Override
    public void beforeFallFlyingTravel(Player player, ParkourVersion selected) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return;
        }

        if (!player.onClimbable() && player.getDeltaMovement().y > -0.5) {
            player.fallDistance = 1.0F;
        }
    }
}
