package me.wolfii.legacyparkourcompat.mixin.network;

import me.wolfii.legacyparkourcompat.mechanic.network.LegacyEntityRotationState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/** Access required to preserve LivingEntity.aiStep's direct old rotation writes. */
@Mixin(Entity.class)
public abstract class EntityLegacyRotationMixin implements LegacyEntityRotationState {
    @Shadow protected float yRot;
    @Shadow protected float xRot;

    @Override
    public void legacySetRotations(float yRot, float xRot) {
        this.yRot = yRot;
        this.xRot = xRot;
    }

    @Override
    public void legacyFinishRotations(float yRot, float xRot) {
        // 1.16 Entity.setRot only applies % 360. Current Entity.setRot clamps pitch through setXRot.
        this.yRot = yRot % 360.0F;
        this.xRot = xRot % 360.0F;
    }
}
