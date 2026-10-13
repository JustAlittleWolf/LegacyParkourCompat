package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AfterCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedUpdateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpInverseSqrtHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockBounceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockLandingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockSpeedFactorHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerFluidPushHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatPassengerYawRefreshHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.CollisionRestitutionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.DismountPositionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.ElytraLiftForceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingLookHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FallFlyingSavedStateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FarmlandEntityPushHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FlightActivationJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FlightFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.FlightSneakInputHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidCurrentMinimumHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionBoxHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.FluidInteractionFluidHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FluidJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.GlideFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GravityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.GroundFrictionBlockHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.GroundJumpGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GroundSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.InsideBlockContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpVerticalVelocityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.KeyboardDiagonalInputHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.LavaGroundJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.LavaTravelHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.LevitationFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.LiquidJumpGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.MovementChunkLookupHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.PassengerCrouchHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PistonMovementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerFallDistanceResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerRestSafetyHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerRestSafetyQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PositionPacketThresholdHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.PortalDismountHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.PowderSnowClimbHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.PushAwayVelocityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.RideableJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowLavaCurrentCutoffHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SlowFallingFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeMoverHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeProbeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SneakInputSlowdownHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SprintCollisionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintFallFlyingGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.SprintJumpImpulseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintKeyStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SprintStateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTickHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTriggerHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintWindowHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.StaleWaterDepthJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SuffocationProbeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SwimmingHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SwimmingUpdateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.VehicleSprintHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.VelocityZeroThresholdHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterDescentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterGravityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.WaterJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSneakHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSprintGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSprintSlowdownHook;
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
        registry.register(FlightFallDistanceHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.CreativeFlightFallDistance());
        registry.register(FlightSneakInputHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.CreativeFlightSneakInput());
        registry.register(SuffocationProbeHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.EndPortalFramePlayerEjection());
        for (String blockId : me.wolfii.legacyparkourcompat.change.v1_8.FencePortalFrameConnection.BLOCK_IDS) {
            registry.register(BlockCollisionShapeHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.FencePortalFrameConnection(blockId));
        }
        for (String blockId : me.wolfii.legacyparkourcompat.change.v1_8.PaneCollisionShape.BLOCK_IDS) {
            registry.register(BlockCollisionShapeHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.PaneCollisionShape(blockId));
        }
        registry.register(RideableJumpHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.RideableJumpCharge());
        var movementChangeSneakingDimensionsV1_8 = new me.wolfii.legacyparkourcompat.change.v1_8.SneakingDimensions();
        registry.register(PlayerDimensionsHook.class, ParkourVersion.V1_8, movementChangeSneakingDimensionsV1_8);
        registry.register(PlayerPoseHook.class, ParkourVersion.V1_8, movementChangeSneakingDimensionsV1_8);
        var movementChangeSprintDurationV1_8 = new me.wolfii.legacyparkourcompat.change.v1_8.SprintDuration();
        registry.register(SprintStateHook.class, ParkourVersion.V1_8, movementChangeSprintDurationV1_8);
        registry.register(SprintTickHook.class, ParkourVersion.V1_8, movementChangeSprintDurationV1_8);
        registry.register(MovementChunkLookupHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.TruncatedMovementChunkLookup());
        registry.register(VelocityZeroThresholdHook.class, ParkourVersion.V1_8, new me.wolfii.legacyparkourcompat.change.v1_8.VelocityZeroThreshold());
    }

    private static void registerV1_9(MovementChangeRegistry registry) {
        registry.register(AutoJumpHook.class, ParkourVersion.V1_9, new me.wolfii.legacyparkourcompat.change.v1_9.NoAutoJump());
        registry.register(FallFlyingSavedStateHook.class, ParkourVersion.V1_9, new me.wolfii.legacyparkourcompat.change.v1_9.FallFlyingSavedState());
        registry.register(PushAwayVelocityHook.class, ParkourVersion.V1_9, new me.wolfii.legacyparkourcompat.change.v1_9.PushAwayVelocity());
    }

    private static void registerV1_10(MovementChangeRegistry registry) {
        registry.register(BlockCollisionShapeHook.class, ParkourVersion.V1_10, new me.wolfii.legacyparkourcompat.change.v1_10.FarmlandFullCollision());
    }

    private static void registerV1_10_1(MovementChangeRegistry registry) {
        registry.register(FarmlandEntityPushHook.class, ParkourVersion.V1_10_1, new me.wolfii.legacyparkourcompat.change.v1_10_1.FarmlandConversion());
        registry.register(SneakEdgeDistanceHook.class, ParkourVersion.V1_10_1, new me.wolfii.legacyparkourcompat.change.v1_10_1.SneakEdgeDistance());
        registry.register(SneakEdgeMoverHook.class, ParkourVersion.V1_10_1, new me.wolfii.legacyparkourcompat.change.v1_10_1.SneakEdgeMover());
    }

    private static void registerV1_11(MovementChangeRegistry registry) {
        registry.register(PistonMovementHook.class, ParkourVersion.V1_11, new me.wolfii.legacyparkourcompat.change.v1_11.PistonMovement());
    }

    private static void registerV1_11_2(MovementChangeRegistry registry) {
        registry.register(BlockBounceHook.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.BedBounce());
        registry.register(DismountPositionHook.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.DismountPosition());
        registry.register(FallFlyingLookHook.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.FallFlyingLookAngle());
        registry.register(PlayerRestSafetyHook.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.SleepSafetyTransition());
        registry.register(PlayerRestSafetyQueryHook.class, ParkourVersion.V1_11_2, new me.wolfii.legacyparkourcompat.change.v1_11_2.SleepSafetyQueryGate());
    }

    private static void registerV1_12(MovementChangeRegistry registry) {
        registry.register(GroundSpeedHook.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.GroundAcceleration());
        var movementChangeSwimmingDimensionsV1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.SwimmingDimensions();
        registry.register(PlayerDimensionsHook.class, ParkourVersion.V1_12, movementChangeSwimmingDimensionsV1_12);
        registry.register(PlayerPoseHook.class, ParkourVersion.V1_12, movementChangeSwimmingDimensionsV1_12);
        registry.register(SwimmingHook.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.SwimmingState());
        registry.register(FallFlyingLookHook.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.VerticalPitchGlideLook());
        var movementChangeWaterJumpV1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.WaterJump();
        registry.register(WaterJumpHook.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(GroundJumpGateHook.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(LiquidJumpGateHook.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(StaleWaterDepthJumpHook.class, ParkourVersion.V1_12, movementChangeWaterJumpV1_12);
        registry.register(WaterSneakHook.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.WaterSneak());
        registry.register(WaterSprintGateHook.class, ParkourVersion.V1_12, new me.wolfii.legacyparkourcompat.change.v1_12.WaterSprintGate());
        me.wolfii.legacyparkourcompat.change.v1_12.SprintInputStart sprintInputStartv1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.SprintInputStart();
        registry.register(ShallowWaterSprintStartHook.class, ParkourVersion.V1_12, sprintInputStartv1_12);
        registry.register(DoubleTapSprintStartHook.class, ParkourVersion.V1_12, sprintInputStartv1_12);
        registry.register(SprintKeyStartHook.class, ParkourVersion.V1_12, sprintInputStartv1_12);
        var movementChangeWaterTravelV1_12 = new me.wolfii.legacyparkourcompat.change.v1_12.WaterTravel();
        registry.register(WaterSprintSlowdownHook.class, ParkourVersion.V1_12, movementChangeWaterTravelV1_12);
        registry.register(WaterGravityHook.class, ParkourVersion.V1_12, movementChangeWaterTravelV1_12);
    }

    private static void registerV1_13(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpHook.class, ParkourVersion.V1_13, new me.wolfii.legacyparkourcompat.change.v1_13.StaleWaterDepthJump());
        registry.register(SwimmingPitchHook.class, ParkourVersion.V1_13, new me.wolfii.legacyparkourcompat.change.v1_13.SwimmingPitch());
        var movementChangeSneakingDimensionsV1_13 = new me.wolfii.legacyparkourcompat.change.v1_13.SneakingDimensions();
        registry.register(PlayerDimensionsHook.class, ParkourVersion.V1_13, movementChangeSneakingDimensionsV1_13);
        registry.register(PlayerPoseHook.class, ParkourVersion.V1_13, movementChangeSneakingDimensionsV1_13);
        registry.register(SneakInputSlowdownHook.class, ParkourVersion.V1_13, new me.wolfii.legacyparkourcompat.change.v1_13.SneakInputSlowdown());
        me.wolfii.legacyparkourcompat.change.v1_13.SprintInputStart sprintInputStartv1_13 = new me.wolfii.legacyparkourcompat.change.v1_13.SprintInputStart();
        registry.register(ShallowWaterSprintStartHook.class, ParkourVersion.V1_13, sprintInputStartv1_13);
        registry.register(DoubleTapSprintStartHook.class, ParkourVersion.V1_13, sprintInputStartv1_13);
        registry.register(SprintKeyStartHook.class, ParkourVersion.V1_13, sprintInputStartv1_13);
    }

    private static void registerV1_14(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpHook.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.StaleWaterDepthJump());
        registry.register(FallFlyingStartHook.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.ElytraJumpStart());
        registry.register(GroundFrictionBlockHook.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.GroundFrictionSupportCellChange());
        registry.register(PlayerDimensionsHook.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.NativePoseDimensions());
        registry.register(PortalDismountHook.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.PortalDismountChange());
        registry.register(SneakInputSlowdownHook.class, ParkourVersion.V1_14, new me.wolfii.legacyparkourcompat.change.v1_14.SneakInputSlowdown());
        var movementChangeSoulSandOverlapChangeV1_14 = new me.wolfii.legacyparkourcompat.change.v1_14.SoulSandOverlapChange();
        registry.register(BlockSpeedFactorHook.class, ParkourVersion.V1_14, movementChangeSoulSandOverlapChangeV1_14);
        registry.register(AfterCollisionHook.class, ParkourVersion.V1_14, movementChangeSoulSandOverlapChangeV1_14);
    }

    private static void registerV1_15(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpHook.class, ParkourVersion.V1_15, new me.wolfii.legacyparkourcompat.change.v1_15.StaleWaterDepthJump());
    }

    private static void registerV1_15_2(MovementChangeRegistry registry) {
        registry.register(BlockCollisionShapeHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.RetractingPistonHeadCollisionShape());
        registry.register(StaleWaterDepthJumpHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.StaleWaterDepthJump());
        registry.register(SneakEdgeCollisionQueryHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.SneakEdgeCollisionQuery());
        registry.register(GroundFrictionBlockHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.GroundFrictionSamplePoint());
        registry.register(SuffocationProbeHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.SuffocationProbe());
        registry.register(FluidCurrentMinimumHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.FluidCurrentMinimum());
        var movementChangeFluidJumpV1_15_2 = new me.wolfii.legacyparkourcompat.change.v1_15_2.FluidJump();
        registry.register(FluidJumpHook.class, ParkourVersion.V1_15_2, movementChangeFluidJumpV1_15_2);
        registry.register(LavaGroundJumpHook.class, ParkourVersion.V1_15_2, movementChangeFluidJumpV1_15_2);
        registry.register(LavaCurrentHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.LavaCurrent());
        registry.register(LavaTravelHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.LavaTravel());
        registry.register(SprintTriggerHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.SprintTrigger());
        registry.register(WaterDescentHook.class, ParkourVersion.V1_15_2, new me.wolfii.legacyparkourcompat.change.v1_15_2.WaterDescent());
    }

    private static void registerV1_16(MovementChangeRegistry registry) {
        registry.register(StaleWaterDepthJumpHook.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.StaleWaterDepthJumpProtection());
        registry.register(SneakEdgeHook.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.SneakEdge());
        registry.register(SneakEdgeCollisionQueryHook.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.SneakEdgeCollisionQuery());
        registry.register(SuffocationProbeHook.class, ParkourVersion.V1_16, new me.wolfii.legacyparkourcompat.change.v1_16.SuffocationProbe());
    }

    private static void registerV1_16_2(MovementChangeRegistry registry) {
        registry.register(SwimmingUpdateHook.class, ParkourVersion.V1_16_2, new me.wolfii.legacyparkourcompat.change.v1_16_2.SwimmingUpdate());
    }

    private static void registerV1_17(MovementChangeRegistry registry) {
        registry.register(JumpPowerHook.class, ParkourVersion.V1_17, new me.wolfii.legacyparkourcompat.change.v1_17.FloatJumpBoostAddition());
    }

    private static void registerV1_17_1(MovementChangeRegistry registry) {
        registry.register(AutoJumpCollisionOrderHook.class, ParkourVersion.V1_17_1, new me.wolfii.legacyparkourcompat.change.v1_17_1.AutoJumpCollisionOrder());
        registry.register(SprintCollisionHook.class, ParkourVersion.V1_17_1, new me.wolfii.legacyparkourcompat.change.v1_17_1.SprintCollision());
        registry.register(BoatPassengerYawRefreshHook.class, ParkourVersion.V1_17_1, new me.wolfii.legacyparkourcompat.change.v1_17_1.BoatPassengerYawRefresh());
        registry.register(SneakEdgeCollisionQueryHook.class, ParkourVersion.V1_17_1, new me.wolfii.legacyparkourcompat.change.v1_17_1.SneakEdgeCollisionQuery());
    }

    private static void registerV1_18(MovementChangeRegistry registry) {
        registry.register(ElytraLiftForceHook.class, ParkourVersion.V1_18, new me.wolfii.legacyparkourcompat.change.v1_18.ElytraLiftForce());
        registry.register(PositionPacketThresholdHook.class, ParkourVersion.V1_18, new me.wolfii.legacyparkourcompat.change.v1_18.PositionPacketThreshold());
        registry.register(PlayerFallDistanceResetHook.class, ParkourVersion.V1_18, new me.wolfii.legacyparkourcompat.change.v1_18.FallDistanceReset());
        var movementChangeAirSpeedV1_18 = new me.wolfii.legacyparkourcompat.change.v1_18.DoubleSprintAirSpeed();
        registry.register(AirSpeedHook.class, ParkourVersion.V1_18, movementChangeAirSpeedV1_18);
        registry.register(AirSpeedUpdateHook.class, ParkourVersion.V1_18, movementChangeAirSpeedV1_18);
    }

    private static void registerV1_18_2(MovementChangeRegistry registry) {
        var movementChangeAirSpeedV1_18_2 = new me.wolfii.legacyparkourcompat.change.v1_18_2.AirSpeed();
        registry.register(AirSpeedHook.class, ParkourVersion.V1_18_2, movementChangeAirSpeedV1_18_2);
        registry.register(AirSpeedUpdateHook.class, ParkourVersion.V1_18_2, movementChangeAirSpeedV1_18_2);
        registry.register(AutoJumpInverseSqrtHook.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.AutoJumpVectorNormalization());
        registry.register(BoatPassengerFluidPushHook.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.BoatPassengerFluidPush());
        registry.register(ElytraLiftForceHook.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.ElytraLiftForce());
        registry.register(GlideFallDistanceHook.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.GlideFallDistance());
        registry.register(InsideBlockContactHook.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.InsideBlockContact());
        registry.register(SneakEdgeHook.class, ParkourVersion.V1_18_2, new me.wolfii.legacyparkourcompat.change.v1_18_2.SneakEdge());
    }

    private static void registerV1_19(MovementChangeRegistry registry) {
        registry.register(BoatPassengerFluidPushHook.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.BoatPassengerFluidPush());
        registry.register(SprintFallFlyingGateHook.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.SprintFallFlyingGate());
        registry.register(SprintStartHook.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.SprintStart());
        registry.register(VehicleSprintHook.class, ParkourVersion.V1_19, new me.wolfii.legacyparkourcompat.change.v1_19.VehicleSprint());
    }

    private static void registerV1_19_4(MovementChangeRegistry registry) {
        registry.register(
            GroundFrictionBlockHook.class,
            ParkourVersion.V1_19_4,
            new me.wolfii.legacyparkourcompat.change.v1_15_2.GroundFrictionSamplePoint()
        );
        registry.register(JumpPowerHook.class, ParkourVersion.V1_19_4, new me.wolfii.legacyparkourcompat.change.v1_19_4.DoubleJumpBoostAddition());
        var movementChangeEffectFallDistanceResetV1_19_4 = new me.wolfii.legacyparkourcompat.change.v1_19_4.EffectFallDistanceReset();
        registry.register(EffectFallDistanceResetHook.class, ParkourVersion.V1_19_4, movementChangeEffectFallDistanceResetV1_19_4);
        registry.register(SlowFallingFallDistanceHook.class, ParkourVersion.V1_19_4, movementChangeEffectFallDistanceResetV1_19_4);
        registry.register(LevitationFallDistanceHook.class, ParkourVersion.V1_19_4, movementChangeEffectFallDistanceResetV1_19_4);
        registry.register(SprintJumpImpulseHook.class, ParkourVersion.V1_19_4, new me.wolfii.legacyparkourcompat.change.v1_19_4.SprintJumpImpulse());
        registry.register(SprintStartHook.class, ParkourVersion.V1_19_4, new me.wolfii.legacyparkourcompat.change.v1_19_4.SprintStart());
    }

    private static void registerV1_20(MovementChangeRegistry registry) {
        registry.register(EffectFallDistanceResetHook.class, ParkourVersion.V1_20, new me.wolfii.legacyparkourcompat.change.v1_20.EffectFallDistanceReset());
        registry.register(PassengerCrouchHook.class, ParkourVersion.V1_20, new me.wolfii.legacyparkourcompat.change.v1_20.PassengerCrouch());
    }

    private static void registerV1_20_2(MovementChangeRegistry registry) {
        registry.register(GravityHook.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.AttributeGravity());
        registry.register(EffectFallDistanceResetHook.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.EffectFallDistanceReset());
        registry.register(FlightActivationJumpHook.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.FlightActivationJump());
        registry.register(SprintJumpImpulseHook.class, ParkourVersion.V1_20_2, new me.wolfii.legacyparkourcompat.change.v1_20_2.SprintJumpImpulse());
    }

    private static void registerV1_21(MovementChangeRegistry registry) {
        registry.register(JumpVerticalVelocityHook.class, ParkourVersion.V1_21, new me.wolfii.legacyparkourcompat.change.v1_21.GroundJumpVerticalVelocity());
        registry.register(InsideBlockContactHook.class, ParkourVersion.V1_21, new me.wolfii.legacyparkourcompat.change.v1_21.InsideBlockContact());
    }

    private static void registerV1_21_4(MovementChangeRegistry registry) {
        registry.register(SneakEdgeProbeHook.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.EdgeBackoffProbeBounds());
        registry.register(GlideFallDistanceHook.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.GlideFallDistance());
        registry.register(KeyboardDiagonalInputHook.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.KeyboardDiagonalInput());
        registry.register(PowderSnowClimbHook.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.PowderSnowClimbBoost());
        registry.register(ShallowWaterSprintHook.class, ParkourVersion.V1_21_4, new me.wolfii.legacyparkourcompat.change.v1_21_4.ShallowWaterSprintEligibility());
    }

    private static void registerV1_21_5(MovementChangeRegistry registry) {
        registry.register(SprintWindowHook.class, ParkourVersion.V1_21_5, new me.wolfii.legacyparkourcompat.change.v1_21_5.DoubleTapSprintWindow());
    }

    private static void registerV1_21_11(MovementChangeRegistry registry) {
        registry.register(ShallowWaterCurrentCutoffHook.class, ParkourVersion.V1_21_11, new me.wolfii.legacyparkourcompat.change.v1_21_11.ShallowWaterCurrentCutoff());
        registry.register(ShallowLavaCurrentCutoffHook.class, ParkourVersion.V1_21_11, new me.wolfii.legacyparkourcompat.change.v1_21_11.ShallowLavaCurrentCutoff());
        registry.register(FluidInteractionBoxHook.class, ParkourVersion.V1_21_11, new me.wolfii.legacyparkourcompat.change.v1_21_11.MountedFluidInteractionBox());
        registry.register(FluidInteractionFluidHook.class, ParkourVersion.V1_21_11, new me.wolfii.legacyparkourcompat.change.v1_21_11.MountedBoatWaterInteraction());
    }

    private static void registerV26_1(MovementChangeRegistry registry) {
        registry.register(CollisionRestitutionHook.class, ParkourVersion.V26_1, new me.wolfii.legacyparkourcompat.change.v26_1.CollisionRestitution());
        for (Block block : BuiltInRegistries.BLOCK) {
            boolean slime = block instanceof SlimeBlock;
            if (slime || block instanceof BedBlock) {
                Identifier id = BuiltInRegistries.BLOCK.getKey(block);
                if (id == null) {
                    throw new IllegalStateException("Registered bounce block has no identifier: " + block);
                }
                registry.register(BlockLandingHook.class, ParkourVersion.V26_1, new me.wolfii.legacyparkourcompat.change.v26_1.BlockLanding(id.toString(), slime));
            }
        }
    }

}
