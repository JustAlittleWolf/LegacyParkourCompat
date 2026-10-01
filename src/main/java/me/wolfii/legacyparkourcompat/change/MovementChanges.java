package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.change.v1_10.FarmlandConversionV1_10;
import me.wolfii.legacyparkourcompat.change.v1_10.FarmlandFullCollision;
import me.wolfii.legacyparkourcompat.change.v1_10.PlayerExhaustionV1_10;
import me.wolfii.legacyparkourcompat.change.v1_10.SneakEdgeDistanceV1_10;
import me.wolfii.legacyparkourcompat.change.v1_10.SneakEdgeMoverV1_10;
import me.wolfii.legacyparkourcompat.change.v1_11.PistonMovementV1_11;
import me.wolfii.legacyparkourcompat.change.v1_21.GroundJumpVerticalVelocity;
import me.wolfii.legacyparkourcompat.change.v1_21_4.EdgeBackoffProbeBounds;
import me.wolfii.legacyparkourcompat.change.v1_21_4.KeyboardDiagonalInput;
import me.wolfii.legacyparkourcompat.change.v1_21_4.PowderSnowClimbBoost;
import me.wolfii.legacyparkourcompat.change.v1_21_4.ShallowWaterSprintEligibility;
import me.wolfii.legacyparkourcompat.change.v1_21_5.DoubleTapSprintWindow;
import me.wolfii.legacyparkourcompat.change.v1_9.NoAutoJump;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new NoAutoJump());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_13.SneakInputSlowdown_1_13());
        registry.register(new FarmlandFullCollision());
        registry.register(new SneakEdgeDistanceV1_10());
        registry.register(new SneakEdgeMoverV1_10());
        registry.register(new FarmlandConversionV1_10());
        registry.register(new PlayerExhaustionV1_10());
        registry.register(new PistonMovementV1_11());
        registry.register(new me.wolfii.legacyparkourcompat.change.WaterMovement_1_12_WaterJump());
        registry.register(new me.wolfii.legacyparkourcompat.change.WaterMovement_1_12_WaterSneak());
        registry.register(new me.wolfii.legacyparkourcompat.change.WaterMovement_1_12_WaterTravel());
        registry.register(new me.wolfii.legacyparkourcompat.change.WaterMovement_1_12_Swimming());
        registry.register(new me.wolfii.legacyparkourcompat.change.WaterMovement_1_12_WaterSprintGate());
        registry.register(new me.wolfii.legacyparkourcompat.change.WaterMovement_1_12_PlayerDimensions());
        registry.register(new GroundGlideMovement_1_12());
        registry.register(new GroundAcceleration_1_12());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges_SprintTrigger());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges_WaterDescent());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges_FluidJump());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges_LavaTravel());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges_FluidCurrentMinimum());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges_LavaCurrent());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_16.Pre1162MovementChanges_ClientUnstuck());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_16.Pre1162MovementChanges_SneakEdge());
        registry.register(new FlightActivationJump_1_20_2());
        registry.register(new PassengerCrouch_1_20());
        registry.register(new AttributeGravity_1_20_2());
        registry.register(new JumpBoostPrecision_1_19_4());
        registry.register(new EffectFallDistanceReset_1_19_4());
        registry.register(new EffectFallDistanceReset_1_20());
        registry.register(new EffectFallDistanceReset_1_20_2());
        registry.register(new DoubleTapSprintWindow());
        registry.register(new ShallowWaterSprintEligibility());
        registry.register(new KeyboardDiagonalInput());
        registry.register(new GroundJumpVerticalVelocity());
        registry.register(new EdgeBackoffProbeBounds());
        registry.register(new PowderSnowClimbBoost());
    }
}
