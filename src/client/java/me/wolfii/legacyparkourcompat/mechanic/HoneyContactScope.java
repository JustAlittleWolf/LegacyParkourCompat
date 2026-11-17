package me.wolfii.legacyparkourcompat.mechanic;

import me.wolfii.legacyparkourcompat.mechanic.hook.block.HoneyContactTimingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;

import java.util.List;

/** Associates a move-local Honey scan with the same native post-travel movement record. */
public final class HoneyContactScope {
    private static final ThreadLocal<HoneyContactScope> CURRENT = new ThreadLocal<>();

    private final LocalPlayer player;
    private final HoneyContactTimingHook timing;
    private boolean inAirTravel;
    private Object coveredMovement;
    private boolean suppressingLateHoney;

    private HoneyContactScope(LocalPlayer player, HoneyContactTimingHook timing) {
        this.player = player;
        this.timing = timing;
    }

    public static void duringAiStep(LocalPlayer player, HoneyContactTimingHook timing, VanillaCall step) {
        HoneyContactScope previous = CURRENT.get();
        CURRENT.set(new HoneyContactScope(player, timing));
        try {
            step.run();
        } finally {
            if (previous == null) {
                CURRENT.remove();
            } else {
                CURRENT.set(previous);
            }
        }
    }

    public static void duringAirTravel(Entity entity, VanillaCall travel) {
        HoneyContactScope scope = current(entity);
        if (scope == null || !scope.supportsAirTravel()) {
            travel.run();
            return;
        }
        boolean previous = scope.inAirTravel;
        scope.inAirTravel = true;
        try {
            travel.run();
        } finally {
            scope.inAirTravel = previous;
        }
    }

    public static void afterSingleMove(Entity entity, Object movement) {
        HoneyContactScope scope = current(entity);
        if (scope != null && scope.inAirTravel && scope.coveredMovement == null) {
            MovementRuntime.find(InsideBlockContactHook.class, entity).ifPresent(contact -> {
                if (scope.timing.replay(scope.player, contact.inset(scope.player, 1.0E-7))) {
                    scope.coveredMovement = movement;
                }
            });
        }
    }

    public static void duringInsideScan(Entity entity, List<?> movements, VanillaCall scan) {
        HoneyContactScope scope = current(entity);
        if (scope == null || scope.coveredMovement == null || movements.size() != 1
            || movements.getFirst() != scope.coveredMovement) {
            scan.run();
            return;
        }
        scope.coveredMovement = null;
        boolean previous = scope.suppressingLateHoney;
        scope.suppressingLateHoney = true;
        try {
            scan.run();
        } finally {
            scope.suppressingLateHoney = previous;
        }
    }

    public static boolean suppressesLateHoney(Entity entity) {
        HoneyContactScope scope = current(entity);
        return scope != null && scope.suppressingLateHoney;
    }

    private static HoneyContactScope current(Entity entity) {
        HoneyContactScope scope = CURRENT.get();
        return scope != null && scope.player == entity ? scope : null;
    }

    private boolean supportsAirTravel() {
        return player.isAlive() && player.isLocalInstanceAuthoritative()
            && !player.noPhysics && !player.onGround() && !player.isPassenger()
            && !player.getAbilities().flying && !player.isFallFlying() && !player.isInWater() && !player.isInLava()
            && !player.isSwimming() && player.getPose() == Pose.STANDING && !player.isNoGravity()
            && !player.shouldDiscardFriction() && !player.hasEffect(MobEffects.SLOW_FALLING)
            && !player.hasEffect(MobEffects.LEVITATION);
    }
}
