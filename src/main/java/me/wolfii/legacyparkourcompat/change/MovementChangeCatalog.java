package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.AfterCollisionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedUpdateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpInverseSqrtBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockCollisionShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockLandingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockSpeedFactorBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerFluidPushBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerYawRefreshBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatSideAccelerationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatSneakAccelerationBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.CollisionRestitutionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.DismountPositionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.DoubleTapSprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.EffectFallDistanceResetBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ElytraLiftForceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingSavedStateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FarmlandEntityPushBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightActivationJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightSneakInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidCurrentMinimumBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GlideFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GravityBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundFrictionBlockBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundJumpGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.InsideBlockContactBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpPowerBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpVerticalVelocityBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.KeyboardDiagonalInputBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaCurrentBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaGroundJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaTravelBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LevitationFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LiquidJumpGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementChunkLookupBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PassengerCrouchBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PistonMovementBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerFallDistanceResetBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerRestSafetyBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerRestSafetyQueryBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PositionPacketThresholdBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PortalDismountBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PowderSnowClimbBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PushAwayVelocityBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.RideableJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowLavaCurrentCutoffBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowWaterCurrentCutoffBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowWaterSprintBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowWaterSprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SlowFallingFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeCollisionQueryBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeMoverBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeProbeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakInputSlowdownBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintCollisionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintFallFlyingGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintJumpImpulseBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintKeyStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintStateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTickBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTriggerBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintWindowBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.StaleWaterDepthJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SuffocationProbeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingPitchBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingUpdateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.VehicleSprintBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.VelocityZeroThresholdBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterDescentBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterGravityBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSneakBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSprintGateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSprintSlowdownBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;

/** Statically linked catalog of built-in historical movement changes. */
public final class MovementChangeCatalog {
    private MovementChangeCatalog() {
    }

    public static void register(MovementChangeRegistry registry) {
        registerV1_8(registry);
        registerV1_9(registry);
        registerV1_10(registry);
        registerV1_10_1(registry);
        registerV1_11(registry);
        registerV1_11_2(registry);
        registerV1_12(registry);
        registerV1_13(registry);
        registerV1_14(registry);
        registerV1_15(registry);
        registerV1_15_2(registry);
        registerV1_16(registry);
        registerV1_16_2(registry);
        registerV1_17(registry);
        registerV1_17_1(registry);
        registerV1_18(registry);
        registerV1_18_2(registry);
        registerV1_19(registry);
        registerV1_19_4(registry);
        registerV1_20(registry);
        registerV1_20_2(registry);
        registerV1_21(registry);
        registerV1_21_4(registry);
        registerV1_21_5(registry);
        registerV1_21_11(registry);
        registerV26_1(registry);
    }

    private static void registerV1_8(MovementChangeRegistry registry) {
        var movementChangeBoatRiderInputV1_8 = new me.wolfii.legacyparkourcompat.change.v1_8.BoatRiderInput();
        registry.register(BoatSideAccelerationBehavior.class, ParkourVersion.V1_8, movementChangeBoatRiderInputV1_8);
        registry.register(BoatSneakAccelerationBehavior.class, ParkourVersion.V1_8, movementChangeBoatRiderInputV1_8);
        registry.register(FlightFallDistanceBehavior.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.CreativeFlightFallDistance());
        registry.register(FlightSneakInputBehavior.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.CreativeFlightSneakInput());
        registry.register(SuffocationProbeBehavior.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.EndPortalFramePlayerEjection());
        for (String blockId : me.wolfii.legacyparkourcompat.change.v1_8.FencePortalFrameConnection.BLOCK_IDS) {
            registry.register(BlockCollisionShape.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.FencePortalFrameConnection(blockId));
        }
        for (String blockId : me.wolfii.legacyparkourcompat.change.v1_8.PaneCollisionShape.BLOCK_IDS) {
            registry.register(BlockCollisionShape.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.PaneCollisionShape(blockId));
        }
        registry.register(RideableJumpBehavior.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.RideableJumpCharge());
        var movementChangeSneakingDimensionsV1_8 = new me.wolfii.legacyparkourcompat.change.v1_8.SneakingDimensions();
        registry.register(PlayerDimensionsBehavior.class, ParkourVersion.V1_8, movementChangeSneakingDimensionsV1_8);
        registry.register(PlayerPoseBehavior.class, ParkourVersion.V1_8, movementChangeSneakingDimensionsV1_8);
        var movementChangeSprintDurationV1_8 = new me.wolfii.legacyparkourcompat.change.v1_8.SprintDuration();
        registry.register(SprintStateBehavior.class, ParkourVersion.V1_8, movementChangeSprintDurationV1_8);
        registry.register(SprintTickBehavior.class, ParkourVersion.V1_8, movementChangeSprintDurationV1_8);
        registry.register(MovementChunkLookupBehavior.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.TruncatedMovementChunkLookup());
        registry.register(VelocityZeroThresholdBehavior.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.VelocityZeroThreshold());
    }

    private static void registerV1_9(MovementChangeRegistry registry) {
        registry.register(AutoJumpBehavior.class, ParkourVersion.V1_9, new me.wolfii.legacyparkourcompat.change.v1_9.NoAutoJump());
        registry.register(FallFlyingSavedStateBehavior.class, ParkourVersion.V1_9, new me.wolfii.legacyparkourcompat.change.v1_9.FallFlyingSavedState());
        registry.register(PushAwayVelocityBehavior.class, ParkourVersion.V1_9, new me.wolfii.legacyparkourcompat.change.v1_9.PushAwayVelocity());
    }

    private static void registerV1_10(MovementChangeRegistry registry) {
        registry.register(BlockCollisionShape.class, ParkourVersion.V1_10, new me.wolfii.legacyparkourcompat.change.v1_10.FarmlandFullCollision());
    }

    private static void registerV1_10_1(MovementChangeRegistry registry) {
        registry.register(FarmlandEntityPushBehavior.class, ParkourVersion.V1_10_1, new me.wolfii.legacyparkourcompat.change.v1_10_1.FarmlandConversion());
        registry.register(SneakEdgeDistanceBehavior.class, ParkourVersion.V1_10_1, new me.wolfii.legacyparkourcompat.change.v1_10_1.SneakEdgeDistance());
        registry.register(SneakEdgeMoverBehavior.class, ParkourVersion.V1_10_1, new me.wolfii.legacyparkourcompat.change.v1_10_1.SneakEdgeMover());
    }

    private static void registerV1_11(MovementChangeRegistry registry) {
        registry.register(PistonMovementBehavior.class, ParkourVersion.V1_11, new me.wolfii.legacyparkourcompat.change.v1_11.PistonMovement());
    }

    private static void registerV1_11_2(MovementChangeRegistry registry) {
        registry.register(BlockBounceBehavior.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.BedBounce());
        registry.register(DismountPositionBehavior.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.DismountPosition());
        registry.register(FallFlyingLookBehavior.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.FallFlyingLookAngle());
        registry.register(PlayerRestSafetyBehavior.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.SleepSafetyTransition());
        registry.register(PlayerRestSafetyQueryBehavior.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.SleepSafetyQueryGate());
    }

    private static void registerV1_12(MovementChangeRegistry registry) {
        registry.register(GroundSpeedBehavior.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.GroundAcceleration());
        var movementChangeSwimmingDimensionsV1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.SwimmingDimensions();
        registry.register(PlayerDimensionsBehavior.class, ParkourVersion.V1_12, movementChangeSwimmingDimensionsV1_12);
        registry.register(PlayerPoseBehavior.class, ParkourVersion.V1_12, movementChangeSwimmingDimensionsV1_12);
        registry.register(SwimmingBehavior.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.SwimmingState());
        registry.register(FallFlyingLookBehavior.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.VerticalPitchGlideLook());
        var movementChangeWaterJumpV1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.WaterJump();
        registry.register(WaterJumpBehavior.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(GroundJumpGateBehavior.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(LiquidJumpGateBehavior.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(StaleWaterDepthJumpBehavior.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(WaterSneakBehavior.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.WaterSneak());
        registry.register(WaterSprintGateBehavior.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.WaterSprintGate());
        me.wolfii.legacyparkourcompat.change.v1_12.SprintInputStart sprintInputStartv1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.SprintInputStart();
        registry.register(ShallowWaterSprintStartBehavior.class, ParkourVersion.V1_12, sprintInputStartv1_12);
        registry.register(DoubleTapSprintStartBehavior.class, ParkourVersion.V1_12, sprintInputStartv1_12);
        registry.register(SprintKeyStartBehavior.class, ParkourVersion.V1_12, sprintInputStartv1_12);
        var movementChangeWaterTravelV1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.WaterTravel();
        registry.register(WaterSprintSlowdownBehavior.class, ParkourVersion.V1_12, movementChangeWaterTravelV1_12);
        registry.register(WaterGravityBehavior.class, ParkourVersion.V1_12, movementChangeWaterTravelV1_12);
    }

    private static void registerV1_13(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpBehavior.class, ParkourVersion.V1_13, new me.wolfii.legacyparkourcompat.change.v1_13.StaleWaterDepthJump());
        registry.register(SwimmingPitchBehavior.class, ParkourVersion.V1_13, new me.wolfii.legacyparkourcompat.change.v1_13.SwimmingPitch());
        var movementChangeSneakingDimensionsV1_13 = new me.wolfii.legacyparkourcompat.change.v1_13.SneakingDimensions();
        registry.register(PlayerDimensionsBehavior.class, ParkourVersion.V1_13, movementChangeSneakingDimensionsV1_13);
        registry.register(PlayerPoseBehavior.class, ParkourVersion.V1_13, movementChangeSneakingDimensionsV1_13);
        registry.register(SneakInputSlowdownBehavior.class, ParkourVersion.V1_13, new me.wolfii.legacyparkourcompat.change.v1_13.SneakInputSlowdown());
        me.wolfii.legacyparkourcompat.change.v1_13.SprintInputStart sprintInputStartv1_13 = new me.wolfii.legacyparkourcompat.change.v1_13.SprintInputStart();
        registry.register(ShallowWaterSprintStartBehavior.class, ParkourVersion.V1_13, sprintInputStartv1_13);
        registry.register(DoubleTapSprintStartBehavior.class, ParkourVersion.V1_13, sprintInputStartv1_13);
        registry.register(SprintKeyStartBehavior.class, ParkourVersion.V1_13, sprintInputStartv1_13);
    }

    private static void registerV1_14(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpBehavior.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.StaleWaterDepthJump());
        registry.register(FallFlyingStartBehavior.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.ElytraJumpStart());
        registry.register(GroundFrictionBlockBehavior.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.GroundFrictionSupportCellChange());
        registry.register(PlayerDimensionsBehavior.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.NativePoseDimensions());
        registry.register(PortalDismountBehavior.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.PortalDismountChange());
        registry.register(SneakInputSlowdownBehavior.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.SneakInputSlowdown());
        var movementChangeSoulSandOverlapChangeV1_14 = new me.wolfii.legacyparkourcompat.change.v1_14.SoulSandOverlapChange();
        registry.register(BlockSpeedFactorBehavior.class, ParkourVersion.V1_14, movementChangeSoulSandOverlapChangeV1_14);
        registry.register(AfterCollisionBehavior.class, ParkourVersion.V1_14, movementChangeSoulSandOverlapChangeV1_14);
    }

    private static void registerV1_15(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpBehavior.class, ParkourVersion.V1_15, new me.wolfii.legacyparkourcompat.change.v1_15.StaleWaterDepthJump());
    }

    private static void registerV1_15_2(MovementChangeRegistry registry) {
        registry.register(SneakEdgeCollisionQueryBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.SneakEdgeCollisionQuery());
        registry.register(StaleWaterDepthJumpBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.StaleWaterDepthJump());
        registry.register(GroundFrictionBlockBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.GroundFrictionSamplePoint());
        registry.register(SuffocationProbeBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.SuffocationProbe());
        registry.register(FluidCurrentMinimumBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.FluidCurrentMinimum());
        var movementChangeFluidJumpV1_15_2 = new me.wolfii.legacyparkourcompat.change.v1_15_2.FluidJump();
        registry.register(FluidJumpBehavior.class, ParkourVersion.V1_15_2, movementChangeFluidJumpV1_15_2);
        registry.register(LavaGroundJumpBehavior.class, ParkourVersion.V1_15_2, movementChangeFluidJumpV1_15_2);
        registry.register(LavaCurrentBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.LavaCurrent());
        registry.register(LavaTravelBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.LavaTravel());
        registry.register(SprintTriggerBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.SprintTrigger());
        registry.register(WaterDescentBehavior.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.WaterDescent());
    }

    private static void registerV1_16(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpBehavior.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.StaleWaterDepthJumpProtection());
        registry.register(SneakEdgeBehavior.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.SneakEdge());
        registry.register(SneakEdgeCollisionQueryBehavior.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.SneakEdgeCollisionQuery());
        registry.register(SuffocationProbeBehavior.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.SuffocationProbe());
    }

    private static void registerV1_16_2(MovementChangeRegistry registry) {
        registry.register(SwimmingUpdateBehavior.class, ParkourVersion.V1_16_2, new me.wolfii.legacyparkourcompat.change.v1_16_2.SwimmingUpdate());
    }

    private static void registerV1_17(MovementChangeRegistry registry) {
        registry.register(JumpPowerBehavior.class, ParkourVersion.V1_17, new me.wolfii.legacyparkourcompat.change.v1_17.FloatJumpBoostAddition());
    }

    private static void registerV1_17_1(MovementChangeRegistry registry) {
        registry.register(SprintCollisionBehavior.class, ParkourVersion.V1_17_1, new me.wolfii.legacyparkourcompat.change.v1_17_1.SprintCollision());
        registry.register(BoatPassengerYawRefreshBehavior.class, ParkourVersion.V1_17_1, new me.wolfii.legacyparkourcompat.change.v1_17_1.BoatPassengerYawRefresh());
        registry.register(SneakEdgeCollisionQueryBehavior.class, ParkourVersion.V1_17_1, new me.wolfii.legacyparkourcompat.change.v1_17_1.SneakEdgeCollisionQuery());
    }

    private static void registerV1_18(MovementChangeRegistry registry) {
        registry.register(ElytraLiftForceBehavior.class, ParkourVersion.V1_18, new me.wolfii.legacyparkourcompat.change.v1_18.ElytraLiftForce());
        registry.register(PositionPacketThresholdBehavior.class, ParkourVersion.V1_18, new me.wolfii.legacyparkourcompat.change.v1_18.PositionPacketThreshold());
        registry.register(PlayerFallDistanceResetBehavior.class, ParkourVersion.V1_18, new me.wolfii.legacyparkourcompat.change.v1_18.FallDistanceReset());
        var movementChangeAirSpeedV1_18 = new me.wolfii.legacyparkourcompat.change.v1_18.DoubleSprintAirSpeed();
        registry.register(AirSpeedBehavior.class, ParkourVersion.V1_18, movementChangeAirSpeedV1_18);
        registry.register(AirSpeedUpdateBehavior.class, ParkourVersion.V1_18, movementChangeAirSpeedV1_18);
    }

    private static void registerV1_18_2(MovementChangeRegistry registry) {
        var movementChangeAirSpeedV1_18_2 = new me.wolfii.legacyparkourcompat.change.v1_18_2.AirSpeed();
        registry.register(AirSpeedBehavior.class, ParkourVersion.V1_18_2, movementChangeAirSpeedV1_18_2);
        registry.register(AirSpeedUpdateBehavior.class, ParkourVersion.V1_18_2, movementChangeAirSpeedV1_18_2);
        registry.register(AutoJumpInverseSqrtBehavior.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.AutoJumpVectorNormalization());
        registry.register(BoatPassengerFluidPushBehavior.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.BoatPassengerFluidPush());
        registry.register(ElytraLiftForceBehavior.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.ElytraLiftForce());
        registry.register(GlideFallDistanceBehavior.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.GlideFallDistance());
        registry.register(InsideBlockContactBehavior.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.InsideBlockContact());
        registry.register(SneakEdgeBehavior.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.SneakEdge());
    }

    private static void registerV1_19(MovementChangeRegistry registry) {
        registry.register(BoatPassengerFluidPushBehavior.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.BoatPassengerFluidPush());
        registry.register(SprintFallFlyingGateBehavior.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.SprintFallFlyingGate());
        registry.register(SprintStartBehavior.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.SprintStart());
        registry.register(VehicleSprintBehavior.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.VehicleSprint());
    }

    private static void registerV1_19_4(MovementChangeRegistry registry) {
        registry.register(
            GroundFrictionBlockBehavior.class,
            ParkourVersion.V1_19_4,
            new me.wolfii.legacyparkourcompat.change.v1_15_2.GroundFrictionSamplePoint()
        );
        registry.register(JumpPowerBehavior.class, ParkourVersion.V1_19_4, new me.wolfii.legacyparkourcompat.change.v1_19_4.DoubleJumpBoostAddition());
        var movementChangeEffectFallDistanceResetV1_19_4 = new me.wolfii.legacyparkourcompat.change.v1_19_4.EffectFallDistanceReset();
        registry.register(EffectFallDistanceResetBehavior.class, ParkourVersion.V1_19_4, movementChangeEffectFallDistanceResetV1_19_4);
        registry.register(SlowFallingFallDistanceBehavior.class, ParkourVersion.V1_19_4, movementChangeEffectFallDistanceResetV1_19_4);
        registry.register(LevitationFallDistanceBehavior.class, ParkourVersion.V1_19_4, movementChangeEffectFallDistanceResetV1_19_4);
        registry.register(SprintJumpImpulseBehavior.class, ParkourVersion.V1_19_4, new me.wolfii.legacyparkourcompat.change.v1_19_4.SprintJumpImpulse());
        registry.register(SprintStartBehavior.class, ParkourVersion.V1_19_4, new me.wolfii.legacyparkourcompat.change.v1_19_4.SprintStart());
    }

    private static void registerV1_20(MovementChangeRegistry registry) {
        registry.register(EffectFallDistanceResetBehavior.class, ParkourVersion.V1_20, new me.wolfii.legacyparkourcompat.change.v1_20.EffectFallDistanceReset());
        registry.register(PassengerCrouchBehavior.class, ParkourVersion.V1_20, new me.wolfii.legacyparkourcompat.change.v1_20.PassengerCrouch());
    }

    private static void registerV1_20_2(MovementChangeRegistry registry) {
        registry.register(GravityBehavior.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.AttributeGravity());
        registry.register(EffectFallDistanceResetBehavior.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.EffectFallDistanceReset());
        registry.register(FlightActivationJumpBehavior.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.FlightActivationJump());
        registry.register(SprintJumpImpulseBehavior.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.SprintJumpImpulse());
    }

    private static void registerV1_21(MovementChangeRegistry registry) {
        registry.register(JumpVerticalVelocityBehavior.class, ParkourVersion.V1_21, new me.wolfii.legacyparkourcompat.change.v1_21.GroundJumpVerticalVelocity());
        registry.register(InsideBlockContactBehavior.class, ParkourVersion.V1_21, new me.wolfii.legacyparkourcompat.change.v1_21.InsideBlockContact());
    }

    private static void registerV1_21_4(MovementChangeRegistry registry) {
        registry.register(SneakEdgeProbeBehavior.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.EdgeBackoffProbeBounds());
        registry.register(GlideFallDistanceBehavior.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.GlideFallDistance());
        registry.register(KeyboardDiagonalInputBehavior.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.KeyboardDiagonalInput());
        registry.register(PowderSnowClimbBehavior.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.PowderSnowClimbBoost());
        registry.register(ShallowWaterSprintBehavior.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.ShallowWaterSprintEligibility());
    }

    private static void registerV1_21_5(MovementChangeRegistry registry) {
        registry.register(SprintWindowBehavior.class, ParkourVersion.V1_21_5, new me.wolfii.legacyparkourcompat.change.v1_21_5.DoubleTapSprintWindow());
    }

    private static void registerV1_21_11(MovementChangeRegistry registry) {
        registry.register(ShallowWaterCurrentCutoffBehavior.class, ParkourVersion.V1_21_11, new me.wolfii.legacyparkourcompat.change.v1_21_11.ShallowWaterCurrentCutoff());
        registry.register(ShallowLavaCurrentCutoffBehavior.class, ParkourVersion.V1_21_11, new me.wolfii.legacyparkourcompat.change.v1_21_11.ShallowLavaCurrentCutoff());
    }

    private static void registerV26_1(MovementChangeRegistry registry) {
        registry.register(CollisionRestitutionBehavior.class, ParkourVersion.V26_1, new me.wolfii.legacyparkourcompat.change.v26_1.CollisionRestitution());
        for (Block block : BuiltInRegistries.BLOCK) {
            boolean slime = block instanceof SlimeBlock;
            if (slime || block instanceof BedBlock) {
                Identifier id = BuiltInRegistries.BLOCK.getKey(block);
                if (id == null) {
                    throw new IllegalStateException("Registered bounce block has no identifier: " + block);
                }
                registry.register(BlockLandingBehavior.class, ParkourVersion.V26_1, new me.wolfii.legacyparkourcompat.change.v26_1.BlockLanding(id.toString(), slime));
            }
        }
    }

}
