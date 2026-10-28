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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.jspecify.annotations.Nullable;

@Mixin(Player.class)
abstract class PlayerPoseUpdateMixin {
    @Unique
    private @Nullable EntityDimensions legacyparkourcompat$previousDimensions;

    @Unique
    private @Nullable AABB legacyparkourcompat$previousBox;

    @Unique
    private float legacyparkourcompat$previousEyeHeight;

    @Unique
    private boolean legacyparkourcompat$fitGateSeen;

    @Unique
    private boolean legacyparkourcompat$swimmingFitPassed;

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
        legacyparkourcompat$fitGateSeen = false;
        legacyparkourcompat$swimmingFitPassed = false;
    }

    @Inject(method = "canPlayerFitWithinBlocksAndEntitiesWhen", at = @At("RETURN"))
    private void legacyparkourcompat$recordFirstFit(Pose pose, CallbackInfoReturnable<Boolean> callback) {
        if (!legacyparkourcompat$fitGateSeen) {
            legacyparkourcompat$fitGateSeen = true;
            legacyparkourcompat$swimmingFitPassed = pose == Pose.SWIMMING && callback.getReturnValue();
        }
    }

    // 26.2/unobfuscated Player.updatePlayerPose (343-355) first calls
    // Player.canPlayerFitWithinBlocksAndEntitiesWhen(Pose.SWIMMING) (373-375). Record that original result so
    // historical resizing cannot bypass the native swimming/crawl clearance gate.
    // Player.getDesiredPose (359-369) identifies the request; only a retained desired pose reaches the old resize.
    // A CROUCHING or SWIMMING fallback stays native.
    @Inject(method = "updatePlayerPose", at = @At("TAIL"))
    private void legacyparkourcompat$updatePlayerPose(CallbackInfo callback) {
        EntityDimensions previousDimensions = legacyparkourcompat$previousDimensions;
        AABB previousBox = legacyparkourcompat$previousBox;
        float previousEyeHeight = legacyparkourcompat$previousEyeHeight;
        legacyparkourcompat$previousDimensions = null;
        legacyparkourcompat$previousBox = null;
        if (!legacyparkourcompat$fitGateSeen || !legacyparkourcompat$swimmingFitPassed) {
            return;
        }
        Player player = (Player)(Object)this;
        Pose desired = legacyparkourcompat$getDesiredPose();
        if (player.getPose() != desired) {
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
}
