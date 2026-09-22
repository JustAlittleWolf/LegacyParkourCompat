package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.FrictionMovementBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.LivingEntityInvoker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/** 1.13 ground acceleration with the pre-1.14 float operation order. */
@MovementChange(emulates = ParkourVersion.V1_13)
public class LegacyGroundAcceleration implements FrictionMovementBehavior {
    private static final float[] SINE = new float[65536];

    static {
        for (int index = 0; index < SINE.length; index++) {
            SINE[index] = (float) Math.sin(index * Math.PI * 2.0 / 65536.0);
        }
    }

    private final Map<LivingEntity, Boolean> priorSprint = Collections.synchronizedMap(new WeakHashMap<>());
    private final float groundFactor;

    public LegacyGroundAcceleration() {
        this(0.16277137F);
    }

    protected LegacyGroundAcceleration(float groundFactor) {
        this.groundFactor = groundFactor;
    }

    @Override
    public Vec3 handleRelativeFrictionAndCalculateMovement(
        LivingEntity entity, Vec3 input, float friction, VanillaFn<Vec3> vanilla
    ) {
        float k = friction * 0.91F;
        float l = this.groundFactor / (k * k * k);
        boolean sprintedOnPreviousTick = this.priorSprint.getOrDefault(entity, false);
        float speed = entity.onGround() ? entity.getSpeed() * l : 0.02F;
        if (!entity.onGround() && sprintedOnPreviousTick) {
            speed = (float) (speed + 0.02F * 0.3);
        }
        this.priorSprint.put(entity, entity.isSprinting());
        float sideways = (float) input.x;
        float forward = (float) input.z;
        // Current input maps two held direction keys to the unit square before
        // travel. The old keyboard supplied both axes as 1.0, then aiStep
        // multiplied them by 0.98F and updateVelocity normalized the pair.
        if (Math.abs(sideways) > 0.7F && Math.abs(forward) > 0.7F) {
            sideways = Math.copySign(0.98F, sideways);
            forward = Math.copySign(0.98F, forward);
        }
        float magnitude = sideways * sideways + forward * forward;
        if (!(magnitude < 1.0E-4F)) {
            magnitude = (float) Math.sqrt(magnitude);
            if (magnitude < 1.0F) {
                magnitude = 1.0F;
            }
            magnitude = speed / magnitude;
            sideways *= magnitude;
            forward *= magnitude;
            float radians = entity.getYRot() * (float) Math.PI / 180.0F;
            float sin = sin(radians);
            float cos = cos(radians);
            Vec3 velocity = entity.getDeltaMovement();
            entity.setDeltaMovement(
                velocity.x + (sideways * cos - forward * sin),
                velocity.y,
                velocity.z + (forward * cos + sideways * sin)
            );
        }

        entity.setDeltaMovement(((LivingEntityInvoker) entity).lpc$handleOnClimbable(entity.getDeltaMovement()));
        entity.move(MoverType.SELF, entity.getDeltaMovement());
        Vec3 movement = entity.getDeltaMovement();
        if (entity.horizontalCollision && entity.onClimbable()) {
            movement = new Vec3(movement.x, 0.2, movement.z);
        }
        return movement;
    }

    static float sin(float radians) {
        return SINE[(int) (radians * 10430.378F) & 65535];
    }

    static float cos(float radians) {
        return SINE[(int) (radians * 10430.378F + 16384.0F) & 65535];
    }
}
