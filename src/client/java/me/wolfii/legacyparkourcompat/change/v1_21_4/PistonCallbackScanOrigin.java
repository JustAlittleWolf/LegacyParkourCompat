package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.PistonCallbackScanOriginHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * Restores the piston scan origin supplied by the 1.21.4 no-argument callback.
 * Source: Mojmap 1.21.4 PistonMovingBlockEntity.moveEntityByPiston and
 * Entity.applyEffectsFromBlocks; Mojmap 26.2 PistonMovingBlockEntity.moveEntityByPiston.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class PistonCallbackScanOrigin implements PistonCallbackScanOriginHook {
    @Override
    public Vec3 scanOrigin(Entity entity, Vec3 currentOrigin) {
        if (entity instanceof LocalPlayer && entity.level().isClientSide()
            && !entity.isRemoved() && !entity.noPhysics) {
            return entity.oldPosition();
        }
        return currentOrigin;
    }
}
