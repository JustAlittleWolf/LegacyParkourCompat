package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
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
        private boolean consumed;
        private boolean inCallback;

        private Travel(LocalPlayer player) {
            this.player = player;
            this.start = player.position();
        }
    }

    static void duringAirTravel(Entity entity, Vec3 input, VanillaCall action) {
        if (!(entity instanceof LocalPlayer player) || Minecraft.getInstance().player != player
            || !MovementRuntime.find(AutoJumpCollisionOrderHook.class, player)
                .map(AutoJumpCollisionOrderHook::observesGroundedTravel).orElse(false)) {
            action.run();
            return;
        }
        Travel prior = CURRENT.get();
        if (isFirstForwardTravel(player, input)) {
            CURRENT.set(new Travel(player));
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
        travel.inCallback = mover == MoverType.SELF && player.onGround() && dx > 0.0F && dz == 0.0F
            && (float)((double)dx * dx) > 0.001F && (float)actualX == dx && (float)requested.x == dx
            && requested.y < 0.0 && requested.z == 0.0 && player.getY() == travel.start.y
            && player.getZ() == travel.start.z;
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

    private static boolean isFirstForwardTravel(LocalPlayer player, Vec3 input) {
        Vec3 previous = player.getDeltaMovement();
        var speed = player.getEffect(MobEffects.SPEED);
        var keys = player.input.keyPresses;
        return player.onGround() && player.getPose() == Pose.STANDING && OrdinaryPlayerContext.matches(player)
            && !player.noPhysics && !player.isSpectator() && !player.isSprinting() && !player.isShiftKeyDown()
            && !player.isInWater() && !player.isInLava() && !player.getAbilities().flying
            && previous.x == 0.0 && previous.z == 0.0 && previous.y < 0.0
            && input.x == 0.0 && input.y == 0.0 && input.z > 0.0
            && keys.forward() && !keys.backward() && !keys.left() && !keys.right()
            && speed != null && speed.getAmplifier() == 1 && !player.hasEffect(MobEffects.JUMP_BOOST);
    }
}
