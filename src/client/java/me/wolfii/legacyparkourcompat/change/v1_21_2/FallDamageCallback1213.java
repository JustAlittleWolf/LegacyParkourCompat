package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.FallDamageCallbackHook;
import net.minecraft.world.entity.Entity;

/**
 * ParkourVersion.V1_21_2 aliases 1.21.2 and 1.21.3. In 1.21.3 Mojmap
 * net.minecraft.world.entity.Entity.move calls checkFallDamage for the controlled local client
 * entity. The 1.21.4 Mojmap Entity.move gate adds net.minecraft.world.entity.Entity.isControlledByClient;
 * keep this true-valued resolver fence for the shared 1.21.2/1.21.3 profile.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class FallDamageCallback1213 implements FallDamageCallbackHook {
    @Override
    public boolean shouldCall(Entity entity) {
        return true;
    }
}
