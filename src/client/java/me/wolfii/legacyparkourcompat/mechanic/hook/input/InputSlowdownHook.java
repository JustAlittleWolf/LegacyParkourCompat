package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.UseEffects;
import net.minecraft.world.phys.Vec2;

/** Local input slowdown with distinct pre-poll and post-poll sampling. */
@MechanicType("player.input_slowdown")
public interface InputSlowdownHook extends VersionedMechanic {
    /** The resolver may expose this hook before its feature exists; retain native input there. */
    default boolean supports(ParkourVersion selected) {
        return !selected.olderThan(ParkourVersion.V1_14) && selected.olderThan(ParkourVersion.V1_15);
    }

    /** Preserve modern-only scale, sneak-speed and custom use-effect responses. */
    default boolean supports(Player player, ParkourVersion selected) {
        if (!supports(selected) || player.getAttributeValue(Attributes.SCALE) != 1.0
            || player.getAttributeValue(Attributes.SNEAKING_SPEED) != 0.3) {
            return false;
        }
        if (selected.olderThan(ParkourVersion.V1_13) && player.isSwimming()
            || selected.olderThan(ParkourVersion.V1_14) && player.getPose() == Pose.SWIMMING && !player.isSwimming()) {
            return false;
        }
        if (player.isUsingItem() && !player.isPassenger()) {
            UseEffects effects = player.getUseItem().getOrDefault(DataComponents.USE_EFFECTS, UseEffects.DEFAULT);
            return effects.speedMultiplier() == UseEffects.DEFAULT.speedMultiplier()
                && effects.canSprint() == UseEffects.DEFAULT.canSprint();
        }
        return true;
    }

    /**
     * Call immediately before polling local input, while isShiftKeyDown still
     * returns the previous poll's sneak intent. Keep the result for this tick.
     */
    boolean samplePoseSlowdown(Player player);

    /**
     * Call after polling input, before item-use slowdown. Do not recompute the
     * pose sample, flight state or collision predicates at this point.
     */
    boolean shouldSlowDown(Player player, boolean sampledPose);

    /** Scale each raw movement impulse once, before subsequent input multipliers. */
    float scaleImpulse(float impulse);

    /** Exact KeyboardInput.tick then LocalPlayer.aiStep item-use ordering in both 1.14 branches. */
    default Vec2 keyboardInput(Player player, boolean sampledPose, float left, float forward) {
        if (shouldSlowDown(player, sampledPose)) {
            left = scaleImpulse(left);
            forward = scaleImpulse(forward);
        }
        if (player.isUsingItem() && !player.isPassenger()) {
            left *= 0.2F;
            forward *= 0.2F;
        }
        return new Vec2(left, forward);
    }

    /** LivingEntity.aiStep damps movement copies, leaving the stored input unchanged. */
    default Vec2 travelInput(Vec2 input) {
        return new Vec2(input.x * 0.98F, input.y * 0.98F);
    }
}
