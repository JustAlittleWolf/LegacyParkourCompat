package me.wolfii.legacyparkourcompat.change.v1_8_9.entity;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityPushHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/**
 * In 1.8.9, Ornithe Feather build.2, LivingEntity.mobTick only reached
 * pushAwayCollidingEntities behind !world.isClient. In 1.9.4, Ornithe Feather
 * build.2, LivingEntity.mobTick called LivingEntity.pushAwayCollidingEntities
 * client-side; PlayerEntity.push(Entity) forwarded to Entity.push(Entity),
 * which applied the inbound horizontal response to LocalClientPlayerEntity.
 * This restores the 1.8.9 client eligibility gate for that path.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class EntityPushResponse implements EntityPushHook {
    @Override
    public boolean suppressInboundPush(Entity receiver, Entity source) {
        return receiver instanceof LocalPlayer && source instanceof LivingEntity;
    }
}
