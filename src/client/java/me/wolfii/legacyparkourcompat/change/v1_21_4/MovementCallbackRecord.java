package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.MovementCallbackRecordHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

/**
 * Mojmap 1.21.4 Entity.move does not append a callback movement record. The
 * local player reaches applyEffectsFromBlocks() after travel, where the
 * no-argument overload supplies oldPosition() to position(). Keep non-player
 * and logical-server movement on the native record path.
 * Source: 1.21.4/mojmap/net/minecraft/world/entity/Entity.java, move and
 * applyEffectsFromBlocks; 1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java, tick.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class MovementCallbackRecord implements MovementCallbackRecordHook {
    @Override
    public boolean suppressMovementRecord(Entity entity) {
        return entity instanceof LocalPlayer
            && entity.level().isClientSide()
            && !entity.isRemoved()
            && !entity.noPhysics;
    }
}
