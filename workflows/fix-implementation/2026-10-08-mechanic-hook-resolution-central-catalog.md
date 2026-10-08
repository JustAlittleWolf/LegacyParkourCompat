# Static movement-change catalog migration (2026-10-08)

Baseline: `d8f3956602da94bf0cf67753cc0a9f4665397729` (`main` at task-branch creation). The 25 version-package `MovementChanges` providers and Fabric custom entrypoint were replaced by 25 explicit methods in `MovementChangeCatalog`. There are 108 statically listed mechanic mappings versus 104 before the sprint-hook split; the four added mappings are two versions × two additional independent hooks. `ChangeResolver` and historical predicates are unchanged.

## Old-to-new registration census

Each row maps a removed provider’s historical change classes to the new mechanic keys. `BlockCollisionShape` retains the old V1_8 per-block variants, and `BlockLanding` retains the V26_1 bed/slime registry scan.

| Emulated version | Prior change classes → catalog mechanic keys |
|---|---|
| `V1_8` | `BoatRiderInput` → `player.boat.side_acceleration`, `player.boat.sneak_acceleration`; `CreativeFlightFallDistance` → `player.flight_fall_distance`; `CreativeFlightSneakInput` → `player.input.flight_sneak`; `EndPortalFramePlayerEjection` → `player.client_unstuck.suffocation`; `RideableJumpCharge` → `player.rideable_jump`; `SneakingDimensions` → `player.dimensions`, `player.pose-update`; `SprintDuration` → `player.sprint.state`, `player.sprint.tick`; `TruncatedMovementChunkLookup` → `player.movement_chunk_lookup`; `VelocityZeroThreshold` → `player.velocity.zero_threshold`; `FencePortalFrameConnection (BLOCK_IDS)` → `block.collision (per listed block id)`; `PaneCollisionShape (BLOCK_IDS)` → `block.collision (per listed block id)` |
| `V1_9` | `NoAutoJump` → `player.auto_jump`; `FallFlyingSavedState` → `player.fall_flying.saved_state`; `PushAwayVelocity` → `player.client_unstuck.push_away_velocity` |
| `V1_10` | `FarmlandFullCollision` → `block.collision` |
| `V1_10_1` | `FarmlandConversion` → `player.farmland.conversion_push`; `SneakEdgeDistance` → `player.sneak.edge.distance`; `SneakEdgeMover` → `player.sneak.edge.mover` |
| `V1_11` | `PistonMovement` → `player.piston.movement` |
| `V1_11_2` | `BedBounce` → `block.bounce`; `DismountPosition` → `player.dismount_position`; `FallFlyingLookAngle` → `player.fall_flying.look`; `SleepSafetyTransition` → `player.rest-safety`; `SleepSafetyQueryGate` → `player.rest-safety-query` |
| `V1_12` | `GroundAcceleration` → `player.ground.speed`; `SwimmingDimensions` → `player.dimensions`, `player.pose-update`; `SwimmingState` → `player.swim`; `VerticalPitchGlideLook` → `player.fall_flying.look`; `WaterJump` → `player.jump.water`, `player.jump.ground_gate`, `player.jump.liquid_gate`; `WaterSneak` → `player.water.sneak`; `WaterSprintGate` → `player.sprint.allowShallowWaterSprint`; `WaterTravel` → `player.travel.water.sprint_slowdown`, `player.travel.water.gravity`; `SprintInputStart` → `player.sprint.inputStart.shallowWater`, `player.sprint.inputStart.doubleTap`, `player.sprint.inputStart.key` |
| `V1_13` | `SwimmingPitch` → `player.swim.pitch_look`; `SneakingDimensions` → `player.dimensions`, `player.pose-update`; `SneakInputSlowdown` → `player.input.sneak-slowdown`; `SprintInputStart` → `player.sprint.inputStart.shallowWater`, `player.sprint.inputStart.doubleTap`, `player.sprint.inputStart.key` |
| `V1_14` | `ElytraJumpStart` → `player.elytra.jumpStart`; `GroundFrictionSupportCellChange` → `entity.supporting_block`; `NativePoseDimensions` → `player.dimensions`; `PortalDismountChange` → `PortalDismountBehavior`; `SneakInputSlowdown` → `player.input.sneak-slowdown`; `SoulSandOverlapChange` → `entity.movement.block_speed_factor`, `entity.movement.after_collision` |
| `V1_15_2` | `GroundFrictionSamplePoint` → `entity.supporting_block`; `SuffocationProbe` → `player.client_unstuck.suffocation`; `FluidCurrentMinimum` → `entity.fluid.current.minimum`; `FluidJump` → `player.jump.fluid`, `player.jump.lava_ground`; `LavaCurrent` → `entity.fluid.lava.current`; `LavaTravel` → `player.travel.lava`; `SprintTrigger` → `player.sprint.trigger`; `WaterDescent` → `player.water.descent` |
| `V1_16` | `SneakEdge` → `player.sneak.edge`; `SuffocationProbe` → `player.client_unstuck.suffocation` |
| `V1_16_2` | `SwimmingUpdate` → `player.swimming.update` |
| `V1_17` | `FloatJumpBoostAddition` → `player.jump.power` |
| `V1_17_1` | `SprintCollision` → `player.sprint.collision`; `BoatPassengerYawRefresh` → `player.boat_passenger_yaw_refresh` |
| `V1_18` | `ElytraLiftForce` → `player.travel.elytra_lift_force` |
| `V1_18_2` | `AirSpeed` → `player.air.speed`, `player.air.speed.update`; `AutoJumpVectorNormalization` → `player.auto_jump.inverse_sqrt`; `BoatPassengerFluidPush` → `player.boat_fluid_push`; `ElytraLiftForce` → `player.travel.elytra_lift_force`; `GlideFallDistance` → `player.fall_flying.fall_distance`; `InsideBlockContact` → `player.inside_block_contact`; `SneakEdge` → `player.sneak.edge` |
| `V1_19` | `BoatPassengerFluidPush` → `player.boat_fluid_push`; `SprintFallFlyingGate` → `player.sprint.fallFlyingForSprintGate`; `SprintStart` → `player.sprint.canStartSprinting`; `VehicleSprint` → `player.sprint.vehicleCanSprint` |
| `V1_19_4` | `DoubleJumpBoostAddition` → `player.jump.power`; `EffectFallDistanceReset` → `player.effect_fall_distance_reset`, `player.fall_distance.slow_falling`, `player.fall_distance.levitation`; `SprintJumpImpulse` → `player.jump.sprint_impulse`; `SprintStart` → `player.sprint.canStartSprinting`; `GroundFrictionSamplePoint (explicit override)` → `entity.supporting_block` |
| `V1_20` | `EffectFallDistanceReset` → `player.effect_fall_distance_reset`; `PassengerCrouch` → `player.crouch.passenger` |
| `V1_20_2` | `AttributeGravity` → `player.gravity`; `EffectFallDistanceReset` → `player.effect_fall_distance_reset`; `FlightActivationJump` → `player.flight.activation_jump`; `SprintJumpImpulse` → `player.jump.sprint_impulse` |
| `V1_21` | `GroundJumpVerticalVelocity` → `player.jump.vertical_velocity`; `InsideBlockContact` → `player.inside_block_contact` |
| `V1_21_4` | `EdgeBackoffProbeBounds` → `player.sneak.probe`; `GlideFallDistance` → `player.fall_flying.fall_distance`; `KeyboardDiagonalInput` → `player.input.keyboard_diagonal`; `PowderSnowClimbBoost` → `player.climb.powder_snow`; `ShallowWaterSprintEligibility` → `player.sprint.isInShallowWaterForSprintEligibility` |
| `V1_21_5` | `DoubleTapSprintWindow` → `player.sprint.window` |
| `V1_21_11` | `ShallowWaterCurrentCutoff` → `entity.fluid.current.shallow_water_cutoff` |
| `V26_1` | `CollisionRestitution` → `entity.restitution`; `BlockLanding (all bed/slime blocks)` → `block.landing (per block id)` |

## Sprint mechanic-key migration

- Removed `SprintInputStartBehavior` (`player.sprint.inputStart`).
- Added `ShallowWaterSprintStartBehavior` (`player.sprint.inputStart.shallowWater`), `DoubleTapSprintStartBehavior` (`player.sprint.inputStart.doubleTap`), and `SprintKeyStartBehavior` (`player.sprint.inputStart.key`).
- The V1_12 and V1_13 change classes register the same object under each of the three interfaces. `LocalPlayerMixin` dispatches each decision through its own hook and retains the vanilla fallback.

## Coverage counts

- Removed provider classes and Fabric custom entrypoint registrations: 25 and 25.
- Prior class-to-mechanic mappings: 104.
- Catalog class-to-mechanic mappings: 108.
- Keyed block variants remain enumerated by the existing V1_8 lists and V26_1 block registry scan.
