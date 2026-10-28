package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jspecify.annotations.Nullable;

@Mixin(Player.class)
abstract class PlayerPoseUpdateMixin {
    @Unique
    private @Nullable EntityDimensions legacyparkourcompat$previousDimensions;

    @Unique
    private @Nullable AABB legacyparkourcompat$previousBox;

    @Unique
    private float legacyparkourcompat$previousEyeHeight;

    // Snapshot 26.2/unobfuscated Entity dimensions, eyeHeight and bounding box before Player.updatePlayerPose
    // (343-355) can refresh them through Entity.refreshDimensions (3393-3400). They are the old 1.13
    // PlayerEntity.updatePlayerPose (333-359) and Entity.setSize (285-300) inputs on the historical path.
    @Inject(method = "updatePlayerPose", at = @At("HEAD"))
    private void legacyparkourcompat$beginPoseUpdate(CallbackInfo callback) {
        Player player = (Player)(Object)this;
        EntityPoseDimensionsAccessor dimensions = (EntityPoseDimensionsAccessor)player;
        legacyparkourcompat$previousDimensions = dimensions.legacyparkourcompat$getDimensions();
        legacyparkourcompat$previousBox = player.getBoundingBox();
        legacyparkourcompat$previousEyeHeight = player.getEyeHeight();
    }

    // Historical 1.13 pose resizing uses a block-only, null-source fit query. Do not gate it on the
    // modern preliminary swimming fit or the pose retained after modern entity/border fallback.
    // Retain native handling for desired poses that did not exist in 1.13 and for modern crawling,
    // which uses SWIMMING without the actual swimming state.
    @Inject(method = "updatePlayerPose", at = @At("TAIL"))
    private void legacyparkourcompat$updatePlayerPose(CallbackInfo callback) {
        EntityDimensions previousDimensions = legacyparkourcompat$previousDimensions;
        AABB previousBox = legacyparkourcompat$previousBox;
        float previousEyeHeight = legacyparkourcompat$previousEyeHeight;
        legacyparkourcompat$previousDimensions = null;
        legacyparkourcompat$previousBox = null;
        Player player = (Player)(Object)this;
        Pose desired = legacyparkourcompat$getDesiredPose();
        if (!legacyparkourcompat$isHistoricalPose(desired)
            || (desired == Pose.SWIMMING && !player.isSwimming())) {
            return;
        }
        if (previousDimensions == null || previousBox == null) {
            throw new IllegalStateException("Player pose pre-state snapshot is missing");
        }
        MovementRuntime.find(PlayerPoseHook.class, player).ifPresent(hook -> hook.updatePose(
            player,
            previousDimensions,
            previousBox,
            previousEyeHeight
        ));
    }

    @org.spongepowered.asm.mixin.gen.Invoker("getDesiredPose")
    protected abstract Pose legacyparkourcompat$getDesiredPose();

    @Unique
    private static boolean legacyparkourcompat$isHistoricalPose(Pose pose) {
        return pose == Pose.STANDING || pose == Pose.FALL_FLYING || pose == Pose.SLEEPING
            || pose == Pose.SWIMMING || pose == Pose.SPIN_ATTACK || pose == Pose.CROUCHING;
    }
}
