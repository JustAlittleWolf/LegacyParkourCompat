package me.wolfii.legacyparkourcompat.mixin.network;

import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientReceivedInterpolationHook;
import me.wolfii.legacyparkourcompat.mechanic.network.ClientPacketPositionState;
import me.wolfii.legacyparkourcompat.mechanic.network.LegacyPacketRotation;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientRelativeMoveTargetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.RelativePositionReconstructionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.ClientTeleportTargetHook;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

/** Thin packet seam: legacy target state is selected only for the registered LocalPlayer. */
@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMovementMixin {
    @Unique private static final ThreadLocal<long[]> LEGACY_RELATIVE_DELTA = ThreadLocal.withInitial(() -> new long[4]);
    @Unique private static final ThreadLocal<Entity> LEGACY_RELATIVE_ENTITY = new ThreadLocal<>();

    @Redirect(
        method = "handleTeleportEntity",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;setValuesFromPositionPacket(Lnet/minecraft/world/entity/PositionMoveRotation;Ljava/util/Set;Lnet/minecraft/world/entity/Entity;Z)Z"
        )
    )
    private boolean legacyparkourcompat$clearOldQueueBeforeModernTeleport(
        PositionMoveRotation change, Set<Relative> relatives, Entity entity, boolean interpolate
    ) {
        if (ClientPacketMovementPolicy.handlesLegacyPackets(entity)) {
            // The helper is reached after handleTeleportEntity's packet-thread guard.
            // Keep this relative/velocity correction native and discard only a stale
            // queued tracking target before its native interpolation is established.
            ((ClientPacketPositionState) entity).legacyClearInterpolation();
        }
        return legacyparkourcompat$invokePositionPacket(change, relatives, entity, interpolate);
    }

    @Invoker("setValuesFromPositionPacket")
    private static boolean legacyparkourcompat$invokePositionPacket(
        PositionMoveRotation change, Set<Relative> relatives, Entity entity, boolean interpolate
    ) {
        throw new AssertionError();
    }

    @Redirect(
        method = "handleEntityPositionSync",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isLocalInstanceAuthoritative()Z")
    )
    private boolean legacyparkourcompat$useHistoricalPlayerAuthorityForAbsoluteTarget(Entity entity) {
        if (ClientPacketMovementPolicy.acceptsServerMovement(entity)
            || (ClientPacketMovementPolicy.handlesLegacyPackets(entity)
                && MovementRuntime.find(ClientTeleportTargetHook.class, entity).isPresent())) {
            // In 1.16.1 a LocalPlayer was not controlled by a local instance, so this
            // packet queued a LivingEntity lerp. The resolved authority operation also
            // preserves that response through the current position-sync route.
            return false;
        }
        return entity.isLocalInstanceAuthoritative();
    }

    @Redirect(
        method = "handleEntityPositionSync",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;moveOrInterpolateTo(Lnet/minecraft/world/phys/Vec3;FF)V")
    )
    private void legacyparkourcompat$queueAbsolutePositionInsteadOfMove(
        Entity entity, Vec3 packetTarget, float yRot, float xRot
    ) {
        if (!legacyparkourcompat$queueAbsolute(entity, packetTarget, yRot, xRot)) {
            entity.moveOrInterpolateTo(packetTarget, yRot, xRot);
        }
    }

    @Redirect(
        method = "handleEntityPositionSync",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;snapTo(Lnet/minecraft/world/phys/Vec3;FF)V")
    )
    private void legacyparkourcompat$queueAbsolutePositionInsteadOfSnap(
        Entity entity, Vec3 packetTarget, float yRot, float xRot
    ) {
        if (!legacyparkourcompat$queueAbsolute(entity, packetTarget, yRot, xRot)) {
            entity.snapTo(packetTarget, yRot, xRot);
        }
    }

    @Unique
    private boolean legacyparkourcompat$queueAbsolute(Entity entity, Vec3 packetTarget, float yRot, float xRot) {
        if (!ClientPacketMovementPolicy.handlesLegacyPackets(entity)) {
            return false;
        }
        var targetHook = MovementRuntime.find(ClientTeleportTargetHook.class, entity).orElse(null);
        var interpolationHook = MovementRuntime.find(ClientReceivedInterpolationHook.class, entity).orElse(null);
        if (interpolationHook == null) {
            return false;
        }
        Vec3 target = targetHook == null ? packetTarget : targetHook.target(entity, packetTarget);
        return interpolationHook.queue(entity, target,
            LegacyPacketRotation.unpackAbsoluteAngle(yRot), LegacyPacketRotation.unpackAbsoluteAngle(xRot), 3);
    }

    @Inject(method = "handleMoveEntity", at = @At("HEAD"))
    private void legacyparkourcompat$clearRelativeDeltaAtStart(ClientboundMoveEntityPacket packet, CallbackInfo callbackInfo) {
        LEGACY_RELATIVE_DELTA.get()[3] = 0L;
        LEGACY_RELATIVE_ENTITY.remove();
    }

    @Redirect(
        method = "handleMoveEntity",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isLocalInstanceAuthoritative()Z")
    )
    private boolean legacyparkourcompat$useHistoricalPlayerAuthorityForRelativeTarget(Entity entity) {
        // This invocation follows PacketUtils.ensureRunningOnSameThread in the handler.
        LEGACY_RELATIVE_ENTITY.set(entity);
        if (ClientPacketMovementPolicy.acceptsServerMovement(entity)
            || (ClientPacketMovementPolicy.handlesLegacyPackets(entity)
                && MovementRuntime.find(ClientRelativeMoveTargetHook.class, entity).isPresent())) {
            // Old ClientPacketListener used !isControlledByLocalInstance(); that
            // predicate was false for LocalPlayer in 1.16.1. The selected authority
            // change also enables the audited 1.19.3 receiver policy.
            return false;
        }
        return entity.isLocalInstanceAuthoritative();
    }

    @Redirect(
        method = "handleMoveEntity",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/VecDeltaCodec;decode(JJJ)Lnet/minecraft/world/phys/Vec3;")
    )
    private Vec3 legacyparkourcompat$captureRelativeDelta(VecDeltaCodec codec, long xa, long ya, long za) {
        Entity entity = LEGACY_RELATIVE_ENTITY.get();
        Vec3 base = codec.getBase();
        Vec3 nativeTarget = MovementRuntime.find(RelativePositionReconstructionHook.class, entity)
            .flatMap(hook -> hook.decode(base, xa, ya, za))
            .orElseGet(() -> codec.decode(xa, ya, za));
        long[] delta = LEGACY_RELATIVE_DELTA.get();
        delta[0] = xa;
        delta[1] = ya;
        delta[2] = za;
        delta[3] = 1L;
        return nativeTarget;
    }

    @Redirect(
        method = "handleMoveEntity",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;moveOrInterpolateTo(Lnet/minecraft/world/phys/Vec3;)V")
    )
    private void legacyparkourcompat$queueRelativePosition(Entity entity, Vec3 nativeTarget) {
        if (legacyparkourcompat$queueRelative(entity, nativeTarget, entity.getYRot(), entity.getXRot())) {
            return;
        }
        entity.moveOrInterpolateTo(nativeTarget);
    }

    @Redirect(
        method = "handleMoveEntity",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;moveOrInterpolateTo(Lnet/minecraft/world/phys/Vec3;FF)V")
    )
    private void legacyparkourcompat$queueRelativePositionAndRotation(
        Entity entity, Vec3 nativeTarget, float yRot, float xRot
    ) {
        if (legacyparkourcompat$queueRelative(entity, nativeTarget, yRot, xRot)) {
            return;
        }
        entity.moveOrInterpolateTo(nativeTarget, yRot, xRot);
    }

    @Redirect(
        method = "handleMoveEntity",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;moveOrInterpolateTo(FF)V")
    )
    private void legacyparkourcompat$queueRotationOnly(Entity entity, float yRot, float xRot) {
        if (ClientPacketMovementPolicy.handlesLegacyPackets(entity)) {
            var interpolationHook = MovementRuntime.find(ClientReceivedInterpolationHook.class, entity).orElse(null);
            if (interpolationHook != null
                && interpolationHook.queue(entity, new Vec3(entity.getX(), entity.getY(), entity.getZ()), yRot, xRot, 3)) {
                return;
            }
        }
        entity.moveOrInterpolateTo(yRot, xRot);
    }

    @Unique
    private boolean legacyparkourcompat$queueRelative(Entity entity, Vec3 nativeTarget, float yRot, float xRot) {
        long[] delta = LEGACY_RELATIVE_DELTA.get();
        if (!ClientPacketMovementPolicy.handlesLegacyPackets(entity) || delta[3] == 0L) {
            return false;
        }
        var targetHook = MovementRuntime.find(ClientRelativeMoveTargetHook.class, entity).orElse(null);
        var interpolationHook = MovementRuntime.find(ClientReceivedInterpolationHook.class, entity).orElse(null);
        if (interpolationHook == null) {
            return false;
        }
        Vec3 target = targetHook == null
            ? nativeTarget
            : targetHook.target(entity, delta[0], delta[1], delta[2], nativeTarget);
        return interpolationHook.queue(entity, target, yRot, xRot, 3);
    }

    @Inject(method = "handleMoveEntity", at = @At("TAIL"))
    private void legacyparkourcompat$clearRelativeDeltaAtEnd(ClientboundMoveEntityPacket packet, CallbackInfo callbackInfo) {
        LEGACY_RELATIVE_DELTA.get()[3] = 0L;
        LEGACY_RELATIVE_ENTITY.remove();
    }
}
