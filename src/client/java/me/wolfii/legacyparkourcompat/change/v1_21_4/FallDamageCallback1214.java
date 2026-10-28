package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.FallDamageCallbackHook;
import net.minecraft.world.entity.Entity;

/**
 * 1.21.4 Mojmap net.minecraft.world.entity.Entity.move adds !isControlledByClient() to the
 * whole checkFallDamage call gate; net.minecraft.world.entity.player.Player.isControlledByClient
 * returns true. This skips both airborne accumulation and the grounded reset. The 1.21.3 Mojmap
 * Entity.move lacks this gate; 1.21.5 Mojmap Entity.move restores the client callback through
 * Entity.isLocalInstanceAuthoritative(). Preserve the complete 1.21.4 skip.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FallDamageCallback1214 implements FallDamageCallbackHook {
    @Override
    public boolean shouldCall(Entity entity) {
        return false;
    }
}
