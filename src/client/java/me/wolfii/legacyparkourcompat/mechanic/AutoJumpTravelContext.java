package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpEntityPrefixHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpPartialCandidateHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/** Retains the observed start of an admitted forward move during its AutoJump callback. */
final class AutoJumpTravelContext {
    private static final ThreadLocal<Travel> CURRENT = new ThreadLocal<>();

    private AutoJumpTravelContext() {}

    private enum Route { GROUNDED, CLIPPED, LANDING }

    private static final class Travel {
        private final LocalPlayer player;
        private final Vec3 start;
        private final Route route;
        private boolean consumed;
        private boolean inCallback;

        private Travel(LocalPlayer player, Route route) {
            this.player = player;
            this.start = player.position();
            this.route = route;
        }
    }

    static void duringAirTravel(Entity entity, Vec3 input, VanillaCall action) {
        if (!(entity instanceof LocalPlayer player) || Minecraft.getInstance().player != player) {
            action.run();
            return;
        }
        boolean clipped = MovementRuntime.find(AutoJumpEntityPrefixHook.class, player)
            .map(AutoJumpEntityPrefixHook::observesClippedGroundedTravel).orElse(false);
        boolean landing = !clipped && MovementRuntime.find(AutoJumpPartialCandidateHook.class, player)
            .map(AutoJumpPartialCandidateHook::observesForwardLanding).orElse(false);
        if (!clipped && !landing && !MovementRuntime.find(AutoJumpCollisionOrderHook.class, player)
            .map(AutoJumpCollisionOrderHook::observesGroundedTravel).orElse(false)) {
            action.run();
            return;
        }
        Travel prior = CURRENT.get();
        Route route = clipped ? Route.CLIPPED : landing ? Route.LANDING : Route.GROUNDED;
        if (isForwardTravel(player, input, route)) {
            CURRENT.set(new Travel(player, route));
        } else {
            CURRENT.remove();
        }
        try {
            action.run();
        } finally {
            if (prior == null) CURRENT.remove();
            else CURRENT.set(prior);
        }
    }

    static void duringCallback(LocalPlayer player, MoverType mover, Vec3 requested,
                               float dx, float dz, VanillaCall action) {
        Travel travel = CURRENT.get();
        if (travel == null || travel.player != player || travel.consumed) {
            action.run();
            return;
        }
        travel.consumed = true;
        double actualX = player.getX() - travel.start.x;
        boolean unmodifiedEast = dx > 0.0F && dz == 0.0F && (float)((double)dx * dx) > 0.001F
            && (float)actualX == dx && (float)requested.x == dx && requested.z == 0.0
            && player.getZ() == travel.start.z;
        travel.inCallback = mover == MoverType.SELF && player.onGround() && requested.y < 0.0
            && switch (travel.route) {
                case GROUNDED -> player.getY() == travel.start.y
                    && (unmodifiedEast || unmodifiedCardinal(player, travel.start, requested, dx, dz));
                case CLIPPED -> player.getY() == travel.start.y && dx == 0.0F && actualX == 0.0
                    && requested.x == 0.0 && dz > 0.0F && (float)((double)dz * dz) > 0.001F
                    && (float)(player.getZ() - travel.start.z) == dz && (float)requested.z > dz;
                case LANDING -> unmodifiedEast && !player.horizontalCollision
                    && requested.y < player.getY() - travel.start.y && player.getY() < travel.start.y;
            };
        try {
            action.run();
        } finally {
            travel.inCallback = false;
        }
    }

    private static boolean unmodifiedCardinal(LocalPlayer player, Vec3 start, Vec3 requested, float dx, float dz) {
        float yaw = Mth.wrapDegrees(player.getYRot());
        Direction face = Direction.fromYRot(yaw);
        if (face == Direction.EAST || yaw != Mth.wrapDegrees(face.toYRot()) || player.horizontalCollision) {
            return false;
        }

        Direction.Axis normal = face.getAxis();
        boolean alongX = normal == Direction.Axis.X;
        Direction.Axis tangent = alongX ? Direction.Axis.Z : Direction.Axis.X;
        float normalMove = alongX ? dx : dz;
        Vec3 actual = player.position().subtract(start);
        // A cardinal yaw may leave a tiny cross-axis trigonometric residue.
        return normalMove * face.getAxisDirection().getStep() > 0.0F
            && (float)((double)normalMove * normalMove) > 0.001F
            && (float)actual.get(normal) == normalMove && (float)requested.get(normal) == normalMove
            && Math.abs(requested.get(tangent)) <= 1.0E-7 && Math.abs(actual.get(tangent)) <= 1.0E-7;
    }

    static @Nullable Vec3 callbackStart(Entity entity) {
        Travel travel = CURRENT.get();
        return travel != null && travel.player == entity && travel.inCallback ? travel.start : null;
    }

    private static boolean isForwardTravel(LocalPlayer player, Vec3 input, Route route) {
        Vec3 previous = player.getDeltaMovement();
        var speed = player.getEffect(MobEffects.SPEED);
        var keys = player.input.keyPresses;
        return player.onGround() == (route != Route.LANDING)
            && player.getPose() == Pose.STANDING && OrdinaryPlayerContext.matches(player)
            && !player.noPhysics && !player.isSpectator() && !player.isSprinting() && !player.isShiftKeyDown()
            && !player.isInWater() && !player.isInLava() && !player.getAbilities().flying
            && (route == Route.LANDING ? previous.x >= 0.0 : previous.x == 0.0)
            && previous.z == 0.0 && previous.y < 0.0
            && input.x == 0.0 && input.y == 0.0 && input.z > 0.0
            && keys.forward() && !keys.backward() && !keys.left() && !keys.right()
            && switch (route) {
                case GROUNDED -> speed != null && speed.getAmplifier() == 1;
                case CLIPPED -> speed == null && player.getYRot() == 0.0F && !keys.jump();
                case LANDING -> speed == null && player.getYRot() == -90.0F && !keys.jump();
            }
            && !player.hasEffect(MobEffects.JUMP_BOOST);
    }
}
