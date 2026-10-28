# Pane aliases and 26.2 LocalPlayer reachability audit

## Verdict and bound inputs

**ACCEPT, bounded.** I independently checked the source-level pane alias chain for 1.12.2, 1.13.2 and 26.2, and checked the corrected 26.2 LocalPlayer helper binding against the canonical read-only decompiled sources. The correction at `pane-26-alias-reachability/correction.md` governs the reachability result; the earlier supplement's three-argument `Entity.moveTowardsClosestSpace` WEST/random-speed explanation is withdrawn and is not used here.

Bound inputs:

- Accepted geometry and collector finding: `collision-dependencies/pane-center-overlap/pane-center-overlap.md`.
- Independent collector audit: `collision-dependencies/pane-overlap-audit/audit.md`.
- Reachability correction: `collision-dependencies/pane-26-alias-reachability/correction.md`.
- Alias/reachability supplement: `collision-dependencies/pane-26-alias-reachability/supplement.md`.
- Independent supplement audit: `collision-dependencies/pane-26-alias-audit/audit.md`.

Primary source root inspected read-only: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

## Version aliases checked

| Version | Pane and state/shape source | Collector and movement consumer aliases | Source-level gate relevant to the accepted witness |
|---|---|---|---|
| 1.12.2, `ornithe-feather` | `net.minecraft.block.PaneBlock`; `PaneBlock.SHAPES[0]` is a `net.minecraft.util.math.Box` center post. Pane's `addCollisions` always offers index 0 and adds arms only for true direction properties. | `net.minecraft.world.World.getCollisions` → block `addCollisions`; `net.minecraft.entity.Entity.move`; `Box.intersectX`. | `Block.addCollision` retains a translated box iff `Box.intersects` is true; strict positive cross-axis overlap then qualifies for `Box.intersectX`, with no inset. With false arms, the center post is the only pane collision box.
| 1.13.2, `ornithe-feather` | `net.minecraft.block.IronBarsBlock` → `PaneBlock`; `PaneBlock.makeShapes` creates center post with `Block.block(7,0,7,9,16,9)` and ORs it into each direction mask, including mask 0. | `net.minecraft.world.WorldView.getCollisions`; `net.minecraft.block.shape.VoxelShapes`; `VoxelShape.calculateMaxDistance`; `SimpleIndexMerger`. | Swept query expands by `1e-7`; candidate predicate uses `VoxelShapes.overlap(..., AND)`. X clipping indexes the cross-axis maximum at `box.maxZ - 1e-7`, placing the witness below the occupied post Z interval.
| 26.2, `unobfuscated` | `net.minecraft.world.level.block.IronBarsBlock` → `CrossCollisionBlock`; `Block.column(2,0,16)` is the centered post, and false arms leave it alone. | `Entity.collideBoundingBox` → `CollisionGetter.getBlockCollisionsFromContext` → `BlockCollisions.computeNext`; `Shapes.joinIsNotEmpty` / `IndirectMerger`; axis path `Shapes.collide` → `VoxelShape.collideX`. | The pane is not the `Shapes.block()` identity. It takes the regular collector path: nonempty translated shape and `Shapes.joinIsNotEmpty(shape, entityShape, AND)` must pass. The 1e-7 coordinate merge coalesces the 6.1920929e-8 Z overlap, so the collector rejects the pane for this supplied swept box. If separately supplied to clipping, `VoxelShape.collideX` uses the same 1e-7 cross-axis inset.

These are the names and paths in the inspected exports. The 1.12.2 and 1.13.2 records corroborate the aliases in the accepted finding and audit; this task does not extend the original bounded comparison or establish an implementation cutoff.

Bounded source references (paths are relative to each exact export):

- **1.12.2:** `net/minecraft/block/PaneBlock.java` (`SHAPES`, defaults, `addCollisions`, 26–75); `Block.java` (`addCollision`, 376–383); `World.java` (`getCollisions`, 964–1014); `Entity.java` (`move`, 567–589); `util/math/Box.java` (`intersectX`, `intersects`, 185–203, 245–251).
- **1.13.2:** `net/minecraft/block/IronBarsBlock.java` (constructor/default state, 15–19); `block/PaneBlock.java` (`makeShapes`, `getCollisionShape`, 32–84); `world/WorldView.java` (`getCollisions`, 112–151, 154–178); `block/shape/VoxelShapes.java` (overlap/merge and offset calculation, 143–181, 194–205, 252–270); `block/shape/SimpleIndexMerger.java` (coalescing, 34–45); `block/shape/VoxelShape.java` (`calculateMaxDistance`, 184–243); `entity/Entity.java` (axis clipping, 578–589).
- **26.2:** `net/minecraft/world/level/block/IronBarsBlock.java` (false defaults, 30–35); `CrossCollisionBlock.java` (`makeShapes`, shape selection, 53–84); `Block.java` (`column`, 177–184); `world/level/CollisionGetter.java` (ordinary and suffocation iterators, 96–105, 126–136); `world/level/BlockCollisions.java` (constructor and collector gate, 41–61, 78–103); `world/phys/shapes/Shapes.java` (`joinIsNotEmpty`, `collide`, 175–244); `IndirectMerger.java` (merge tolerance, 14–62); `VoxelShape.java` (`collideX`, 252–310); `client/player/LocalPlayer.java` (private helper/predicate and four calls, 451–484, 795–799); `world/level/block/state/BlockBehaviour.java` (full-shape predicate, suffocation method/default, 900–902, 808–810, 1018); `world/entity/LivingEntity.java` (air travel and player cutoff, 2468–2496, 3065); `world/entity/PositionMoveRotation.java` (absolute packet components, 40–67); `client/multiplayer/ClientPacketListener.java` (packet application, 795–827).

## Corrected 26.2 LocalPlayer binding and suffocation predicate

`LocalPlayer.aiStep` at `LocalPlayer.java:795–799` invokes four two-argument calls. Java resolves each to the private `LocalPlayer.moveTowardsClosestSpace(double x, double z)` at `LocalPlayer.java:451–478`; it does not resolve them to the distinct three-argument `Entity.moveTowardsClosestSpace(double x, double y, double z)` at `Entity.java:2912–2941`.

The LocalPlayer overload's first operation is `if (this.suffocatesAt(pos))`. Its only movement writes are inside that branch. `suffocatesAt` builds a test AABB for the probed block column and calls `level().collidesWithSuffocatingBlock`. That call constructs `BlockCollisions` with `onlySuffocatingBlocks=true`; `BlockCollisions.computeNext` checks `blockState.isSuffocating(...)` before retrieving/collecting the collision shape. `BlockBehaviour.Properties`' default predicate at `BlockBehaviour.java:1018` is `state.blocksMotion() && state.isCollisionShapeFullBlock(level,pos)`. The false-connection pane has only the 2/16-wide center post, so its collision shape is not a full-block shape. Its default suffocation predicate is false even if `blocksMotion()` is true.

For the accepted center and `getBbWidth()==0.6F`, the source expression's `* 0.35` offset is `0.21000000834465027`. The calls probe cells `(-1,64,0)`, `(-1,64,-1)`, `(0,64,-1)`, and `(0,64,0)`. Given the finding's isolated neighborhood, the first three are air and the last is the pane. None satisfies `suffocatesAt`; thus none enters the direction loop or changes delta movement. The correction therefore removes the prior claim of a WEST/randomized-X rewrite for this witness.

## Packet-seeded path to the conditional collector

A clientbound player-position packet can carry the exact position and delta as doubles. With no relative flags, `PositionMoveRotation.calculateAbsolute` uses the supplied absolute position and supplied delta components. `ClientPacketListener.handleMovePlayer` applies it to a non-passenger with interpolation false, calling `Entity.setPos` and `Entity.setDeltaMovement`. This supports packet-assisted setup at `(0.13749,64.2,0.13750005)` and `(0.003125,0,0)`; it does not derive that state from ordinary input.

Under the correction's bounded ordinary-tick preconditions (loaded local player; non-passenger, non-spectator, `noPhysics == false`; airborne and dry; not fall-flying or flying; zero movement input; isolated pane and clear corner neighborhood), the corrected corner helper leaves the packet delta intact. `LivingEntity.aiStep` only zeros player horizontal movement below `9.0E-6`; the supplied squared horizontal magnitude is `9.765625E-6`. The airborne travel path reaches `handleRelativeFrictionAndCalculateMovement`, whose `move(MoverType.SELF, getDeltaMovement())` occurs before subsequent gravity and air drag. Therefore packet-seeded state can source-reach the exact positive-X movement into `Entity.collideBoundingBox` and its conditional block collector, assuming those preconditions and the finding's supplied movement.

**Input-only derivation remains open. Runtime execution and a live collision-call observation remain unproven.** This conclusion is source reachability conditional on a packet-seeded state, not proof that keyboard/input simulation creates it.

## Direct source identity check

The 26.2 export identifies itself as `26.2 / unobfuscated`. I recomputed these direct file hashes from the canonical source files; they match the hashes bound in the correction/supplement:

| File | SHA-256 |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6` |
| `net/minecraft/world/level/CollisionGetter.java` | `8E7D1A54D27E0F187667439539C59B8892F966F1B9C8107EB162C6D43F830F6C` |
| `net/minecraft/world/level/BlockCollisions.java` | `EB8A8F6F07B1D6384F17987818F056D41ADFD6B2AD120D26774BFA550F4FC51E` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9C7A103492D0714C90397DA88EB696912FF6A9CA1C005D984C4746D52637FD1E` |
| `net/minecraft/world/level/block/IronBarsBlock.java` | `B8D8467DB13454FC5CD24C95827172B8550D5CBA3D51930BA8B240B960606E54` |
| `net/minecraft/world/level/block/CrossCollisionBlock.java` | `DCD2DBBCD093A68DEE4EE282E5F3101C519D633F3F173DD33383BD4B548C1BA5` |
| `net/minecraft/world/entity/PositionMoveRotation.java` | `A5F0CF32266502DD2C9250D28DFC7BC85CFCF30247E91E3311018AF15BE319F8` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `9CB0CC8AFEBAE9F4E42F428A52719C645817D03893DC371E6711C7BD16EAC1B6` |

No implementation, backlog, wiki, product diff/history, tests, runtime, game/server launch, build, or source generation was inspected or performed.
