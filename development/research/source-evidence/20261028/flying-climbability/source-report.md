# 1.21.3 → 1.21.4 Mojmap source comparison (blind worker)

## Evidence identity

Canonical read-only source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.3\mojmap` and `...\1.21.4\mojmap`.

- 1.21.3 `mojmap.ready.json`: status ready, `versionId=1.21.3`, `mapping=mojmap`, 5,655 Java sources; `mojmap.provenance.json` identifies Vineflower 1.12.0, Mapping IO 0.9.1, Java 25.0.3+9, ASM 9.10.1, Gradle 9.7.1, and Tiny Remapper 0.14.1. SHA-256 of `mojmap.sources.sha256`: `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` (matches ready marker).
- 1.21.4 `mojmap.ready.json`: status ready, `versionId=1.21.4`, `mapping=mojmap`, 5,744 Java sources; same tool versions and mapping family in provenance. SHA-256 of `mojmap.sources.sha256`: `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` (matches ready marker).
- Parsed the complete source manifests: 158 added paths, 69 removed paths, 638 modified paths (865 changed paths total). Both manifest counts match the actual enumerated source files. The required core source files exist. The per-file source SHA-256 values for the central movement files are included below.

## Ordered ordinary local-player tick graph

1. Client tick reaches `LocalPlayer.tick`: in 1.21.4 it decrements the client-load timeout, then gates the ordinary superclass tick, shift/input packet updates, player position packet, and ambient sounds on `hasClientLoaded`. This is new loading/packet gating. Its first 60 ticks can defer ordinary player simulation in newly joined client sessions; the timeout gate is therefore a reachable difference but is a session initialization boundary, not an ongoing movement procedure. Exact source: `client/player/LocalPlayer.java:191`, `world/entity/player/Player.java:2095-2111`, and `client/multiplayer/ClientPacketListener.java:2456-2463` (the client marks loaded once the level-ready manager reports ready).
2. `Player.tick` clears on-ground for spectators and now also passengers (`Player.java:256-260`). The local player can ride a vehicle, so this is a player state response; while riding, `LocalPlayer.aiStep` follows the passenger path rather than ordinary land travel.
3. `LivingEntity.aiStep` retains the inherited living-entity state/effect/jump/travel sequencing. The compared method bodies for `LivingEntity.aiStep`, `jumpFromGround`, and `travel` have no movement-code difference; the file edits are in air replenishment, damage/knockback bookkeeping, line of sight and unrelated methods. These shared methods were checked by comparing the full-file diffs around those bodies.
4. `LocalPlayer.aiStep` processes input and sprint decisions, jump/auto-jump/fall-flying decisions, then calls superclass AI/travel. 1.21.4 changes input handling and sprint gates (detailed below). Input packet state still derives from `input.keyPresses`.
5. Ordinary travel dispatches to inherited `LivingEntity.travel` and shared movement math, then `Entity.move` performs edge-backoff, collision resolution, position updates, collision/on-ground state updates, fall-damage handling gates, block movement response, movement emission and block speed-factor scaling.
6. `LocalPlayer.move` remains a thin `super.move` wrapper with auto-jump and walk-distance updates. It is unchanged between these versions. `Entity.move`, however, changes on-ground/collision bookkeeping and in-block callback traversal as below.
7. Tick consumers include player movement packet reporting and the following-tick on-ground/sprint/climb decisions. Direct external responses include passenger state, entity/block callbacks, server position correction and player abilities. The clientbound teleport handler's only relevant change is packet send ordering (accept teleport before reporting position), so no new local movement math was identified there.

## Source-confirmed movement differences

### Local input and sprint state

`LocalPlayer.java:643-680, 816-829, 1053-1063` and `KeyboardInput.java`:

- 1.21.3 calls `input.tick(isMovingSlowly(), sneakingSpeedAttribute)`; `KeyboardInput.tick(boolean,float)` samples keys and applies sneak scaling there. 1.21.4 changes `ClientInput.tick` and `KeyboardInput.tick` to parameterless methods, then in `LocalPlayer.aiStep` multiplies left/forward impulses by `0.2F` for item use (unless riding), resets sprint trigger time, and subsequently multiplies them by the sneaking-speed attribute if moving slowly. The source order is item-use scaling before sneak scaling; both multiplications can apply together. Input tutorial notification happens before those multiplications.
- New `shouldStopSprinting()` ends sprinting when fall-flying, blind, moving slowly, riding a non-camel, or using an item outside a passenger/underwater state. `canStartSprinting()` also now rejects moving-slowly outside water. This changes reachable sprint and movement speed behavior for local players; camel passenger exception and underwater item exception are explicit gates.
- `Player.onClimbable()` now returns false while abilities.flying, else delegates to `LivingEntity.onClimbable`. Existing call sites in `LivingEntity.travel`, jump handling, and player movement predicates use `onClimbable`; flying players on climbable blocks now take the non-climbable branch. This direct predicate response is movement-relevant.

### Shared entity movement state and block callbacks

`Entity.java:400-408, 587-596, 627-686, 756-770, 1050-1090`:

- `makeBoundingBox()` is now final and delegates to a new position-parameter overload. Shape at the supplied position still derives from current dimensions.
- In `move`, `horizontalCollision` is always recomputed. Vertical collision, verticalCollisionBelow, and `setOnGroundWithMovement` are now updated only when requested Y movement is nonzero or the entity is local/controlled (`isControlledByOrIsLocalPlayer`). A locally controlled player therefore continues to refresh on-ground state on zero-Y movement; other entities retain prior flags on a zero-Y move. For player collision state, the local-player gate matters and was checked against `Player.isControlledByClient` and `Entity.isControlledByOrIsLocalPlayer`.
- Fall damage now runs only when the old client/server control gate passes and `!isControlledByClient()`. Player fall-damage calculation remains excluded by project scope; the server still owns damage resolution. This is recorded as a source change, not an accepted movement finding.
- At end of movement, the path list is copied and cleared before callbacks. `checkInsideBlocks` now builds the entity AABB at each movement segment's destination and tests the segment from→to against that box; `collidedWithShapeMovingFrom` uses the source-position box and the segment delta. Previously it used the final current bounding box for all accumulated movement segments and a translated-box bottom-center calculation. This changes which non-full-block entity-inside collision callbacks can fire during multi-move ticks (e.g. web/cobweb, berry bush, powder snow or other callback blocks), and thus can change direct player movement responses such as slowdown/stuck movement. Exact callback-block dependencies are listed as open below.
- `movementThisTick` is snapshotted before clear; fire/lava detection is applied to accumulated `blocksInside` after the callback pass. Do not infer a trajectory result from source alone.

### Other player response and block/effect inputs

- `Player.tick` now clears on-ground while passenger. `Player.onClimbable` is new as above.
- `Blocks.java` changes existing flower effect durations: azure bluet blindness 8→11 seconds and cornflower jump boost 6→5 seconds; allium fire resistance 4→3, tulips weakness 9→7, oxeye regeneration 8→7, wither rose 8→7, lily-of-the-valley poison 12→11. The first two can affect local sprint gating and jump boost duration respectively; other changed flower effects do not change movement math in this pair. Values are the `FlowerBlock` constructor duration constants (ticks converted by that constructor); `LocalPlayer` reads blindness and jump effects in its sprint and auto-jump decisions. Exact definitions in `Blocks.java` around lines 963-1050.
- `MultifaceBlock` gains waterlogging at the base class and changes generic state update/placement behavior. Existing 1.21.3 Glow Lichen and Sculk Vein supplied waterlogging themselves; 1.21.4 moves their superclass arrangement while retaining their own water/fluid behavior. Their cached collision-shape calculation is unchanged. New 1.21.4 resin-clump registration is modern content and its collision shape is not a historical block delta for 1.21.3.
- A full diff sweep across changed block sources found no changed `getCollisionShape`, `getShape`, `stepOn`, `entityInside`, `updateEntityMovementAfterFallOn`, `fallOn`, friction/speed/jump-factor or ladder override bodies in existing changed block classes. Existing callback blocks still need to be paired as dependencies of the new `Entity.checkInsideBlocks` traversal.

## Compared and unexamined slices

Compared exact source bodies/diffs: `LocalPlayer`, `ClientInput`, `KeyboardInput`, `Player`, `LivingEntity`, `Entity`, `ClientPacketListener`, `MultiPlayerGameMode`; block registration/effects in `Blocks`; block behavior in `Block`, `BlockBehaviour`, `BlockTags`, `MultifaceBlock`, `MultifaceSpreadeableBlock`, `GlowLichenBlock`, `SculkVeinBlock`, and a changed-block source sweep for collision/inside/step/fall/speed hooks. `LocalPlayer.move`, `LivingEntity.aiStep`/`travel`/`jumpFromGround`, `Entity.moveRelative`, and the direct correction path were checked for unchanged movement bodies or relevant gates.

Partial/open: I did not complete an independent retrace of every changed Java path in the 865-path diff, all content/resource/tag data and data-driven block definitions, all existing block callback implementations reachable from `checkInsideBlocks`, attribute/effect providers/resources, or server-originated direct player velocity/position responses. Exact `LivingEntity` damage knockback was observed textually unchanged in numeric behavior but is excluded as damage calculation; no judgment is made about the boundaries on other input sources. Checksum enumeration establishes path counts and integrity, not semantic coverage.

Unexamined dependency gaps for the `Entity.checkInsideBlocks` finding: exact old/new `entityInside` effects and collision-shape reachability for cobweb, berry bush, powder snow, fire/lava, sweet berry, and any other callback blocks used by movement; `stuckSpeedMultiplier` producers/consumers and multi-move-per-tick callers; fluid state sampling/drag callbacks; data-driven tags defining eligible existing blocks. Close these before accepting a bounded finding. A separate blind reviewer must retrace the complete tick graph and terminal slices before pair freeze.

## Pair status

This is not a pair-freeze claim. The tick and dependency inventory remains partial and no independent coverage audit was performed. Candidate source findings for independent triage: local input/sprint gating, flying climbability response, and segment-relative block-inside callback traversal. Historical applicability across profiles requires separate bounded evidence; patch introduction is only bounded to 1.21.3→1.21.4.

## Central movement file SHA-256

| File | SHA-256 |
|---|---|
| 1.21.3 `client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| 1.21.4 `client/player/LocalPlayer.java` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` |
| 1.21.3 `world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.21.4 `world/entity/Entity.java` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` |
| 1.21.3 `world/entity/player/Player.java` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| 1.21.4 `world/entity/player/Player.java` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` |
| 1.21.3 `client/player/KeyboardInput.java` | `2a0994e8a6a2fdf9e76480227216db4a4252734fca8ca0aedd25dde77be75ba6` |
| 1.21.4 `client/player/KeyboardInput.java` | `c885bcc709e445c3ff91f03bf37acf9320e17fc9daf1bd2ba46ba20487f1d9ed` |
| 1.21.3 `world/level/block/Blocks.java` | `28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a` |
| 1.21.4 `world/level/block/Blocks.java` | `8e28c42de96dd9ebee370797cfb208f034eee2c38e3d00152ed6f9769390b238` |
| 1.21.3 `world/level/block/MultifaceBlock.java` | `5fc1a7a75a1a0cd9c8d84281fcc09ceb0a7032fa91f6573de4085b2b7250c6c9` |
| 1.21.4 `world/level/block/MultifaceBlock.java` | `41d611ec080b1d27ab2e598b422150fd183d3c8a840f074f1b1c92bb1a18b3cc` |
