package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.ClientBorderLerpState;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/** Restores the elapsed-millisecond moving-border clock verified for 1.21.6–1.21.10. */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class ElapsedWorldBorderCollision implements WorldBorderCollisionHook {
    /*
     * 1.21.6–1.21.10 Mojmap WorldBorder.MovingBorderExtent#getSize/#getMinX/#getMinZ/#getMaxX/#getMaxZ/
     * #getCollisionShape and Entity#collectColliders use Util#getMillis elapsed time. 1.21.11 Mojmap
     * WorldBorder.MovingBorderExtent#calculateSize/#update and #getMinX(float)/#getMinZ(float)/
     * #getMaxX(float)/#getMaxZ(float)/#getCollisionShape, plus Entity#collectColliders, use tick-cached
     * size. 26.2 unobfuscated ClientPacketListener#handleInitializeBorder
     * and #handleSetBorderLerpSize pass packet lerp time to WorldBorder#lerpSizeBetween(... ticks, gameTime);
     * TimeArgument#parse maps seconds to 20 ticks, while 1.21.10 WorldBorderCommand#setSize maps seconds
     * to 1000 milliseconds. This is the source-established unit ratio, not a guessed conversion.
     */
    @Override
    public @Nullable Collision collision(WorldBorder border, Entity entity, AABB box) {
        return ClientBorderLerpState.collision(border, entity, box);
    }
}
