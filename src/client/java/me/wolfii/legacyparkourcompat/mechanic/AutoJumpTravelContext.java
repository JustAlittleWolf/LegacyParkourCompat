package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpEntityPrefixHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/** Retains the observed start of the first grounded forward move during its AutoJump callback. */
final class AutoJumpTravelContext {
    private static final ThreadLocal<Travel> CURRENT = new ThreadLocal<>();

    private AutoJumpTravelContext() {}

    private static final class Travel {
        private final LocalPlayer player;
        private final Vec3 start;
        private final boolean clipped;
        private boolean consumed;
        private boolean inCallback;

        private Travel(LocalPlayer player, boolean clipped) {
            this.player = player;
            this.start = player.position();
            this.clipped = clipped;
        }
    }

    static void duringAirTravel(Entity entity, Vec3 input, VanillaCall action) {
        if (!(entity instanceof LocalPlayer player) || Minecraft.getInstance().player != player) {
            action.run();
            return;
        }
        boolean clipped = MovementRuntime.find(AutoJumpEntityPrefixHook.class, player)
            .map(AutoJumpEntityPrefixHook::observesClippedGroundedTravel).orElse(false);
        if (!clipped && !MovementRuntime.find(AutoJumpCollisionOrderHook.class, player)
            .map(AutoJumpCollisionOrderHook::observesGroundedTravel).orElse(false)) {
            action.run();
            return;
        }
        Travel prior = CURRENT.get();
        if (isFirstForwardTravel(player, input, clipped)) {
            CURRENT.set(new Travel(player, clipped));
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
        travel.inCallback = mover == MoverType.SELF && player.onGround()
            && requested.y < 0.0 && player.getY() == travel.start.y
            && (travel.clipped
                ? dx == 0.0F && actualX == 0.0 && requested.x == 0.0 && dz > 0.0F
                    && (float)((double)dz * dz) > 0.001F && (float)(player.getZ() - travel.start.z) == dz
                    && (float)requested.z > dz
                : dx > 0.0F && dz == 0.0F && (float)((double)dx * dx) > 0.001F
                    && (float)actualX == dx && (float)requested.x == dx
                    && requested.z == 0.0 && player.getZ() == travel.start.z);
        try {
            action.run();
        } finally {
            travel.inCallback = false;
        }
    }

    static @Nullable Vec3 callbackStart(Entity entity) {
        Travel travel = CURRENT.get();
        return travel != null && travel.player == entity && travel.inCallback ? travel.start : null;
    }

    private static boolean isFirstForwardTravel(LocalPlayer player, Vec3 input, boolean clipped) {
        Vec3 previous = player.getDeltaMovement();
        var speed = player.getEffect(MobEffects.SPEED);
        var keys = player.input.keyPresses;
        return player.onGround() && player.getPose() == Pose.STANDING && OrdinaryPlayerContext.matches(player)
            && !player.noPhysics && !player.isSpectator() && !player.isSprinting() && !player.isShiftKeyDown()
            && !player.isInWater() && !player.isInLava() && !player.getAbilities().flying
            && previous.x == 0.0 && previous.z == 0.0 && previous.y < 0.0
            && input.x == 0.0 && input.y == 0.0 && input.z > 0.0
            && keys.forward() && !keys.backward() && !keys.left() && !keys.right()
            && (clipped ? speed == null && player.getYRot() == 0.0F && !keys.jump()
                : speed != null && speed.getAmplifier() == 1)
            && !player.hasEffect(MobEffects.JUMP_BOOST);
    }
}
