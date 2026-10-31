# Wall / Iron Bars connection residual — Mojmap 1.15.2 → 1.16.5

## Exact release boundary

Both connection predicate additions first appear in 1.16 and persist unchanged through 1.16.1–1.16.5. 1.15.2 contains neither. For each endpoint/intermediate release, `WallBlock.java` and `IronBarsBlock.java` hashes were checked against that release's own source map; all 14 checks pass. 1.16.3/.4 are validated against their embedded `.decompile-provenance.json` maps. Exact hashes are in `source-hashes.tsv`.

- Old 1.15.2 `WallBlock.connectsTo`: `block.is(WALLS) || (!exception && sturdy) || (FenceGate && connectsDirection)` (`WallBlock.java:60–64`).
- New 1.16 `WallBlock.connectsTo`: the same terms plus `block instanceof IronBarsBlock` (`WallBlock.java:130–134`); persists at the same method lines in .1–.5.
- Old 1.15.2 `IronBarsBlock.attachsTo`: `(!exception && sturdy) || block instanceof IronBarsBlock` (`IronBarsBlock.java:75–78`).
- New 1.16 `IronBarsBlock.attachsTo`: the old expression plus `block.is(BlockTags.WALLS)` (`IronBarsBlock.java:85–88`); persists at the same method lines in .1–.5.

## Conditional same-input state routes

1. **Wall side connected to an Iron Bars neighbor.** Supply an Iron Bars block state in one horizontal neighbor slot to the wall's `getStateForPlacement` / `updateShape` path. The old result is false exactly when all old disjuncts fail: the supplied state is not in the effective `WALLS` tag; either it is an `isExceptionForConnection` block or the supplied sturdy-face boolean is false; and, since the state is Iron Bars rather than a fence gate, the gate disjunct is false. On that same state/face/tag input, 1.16's added `instanceof IronBarsBlock` makes the side connected. This explicitly conditions on the complete old predicate; it does not claim that every Iron Bars state fails the old predicate or that tag data has a particular membership.

2. **Iron Bars side connected to an effective WALLS-tag neighbor.** Supply a neighbor state for which `blockState.is(BlockTags.WALLS)` is true, while the complete old attachment expression `(!isExceptionForConnection(block) && sturdyFace) || block instanceof IronBarsBlock` is false. Under that same neighbor state, sturdy result, and effective tag input, the new tag disjunct makes the 1.16 attachment true. The exact domain is tag membership plus old false result. No default tag payload or member list is assumed.

`WallBlock.getStateForPlacement`, `sideUpdate` and `updateShape` feed the connection predicate result into the directional properties (`WallBlock.java:67–90, 93–123` old; 1.16 `.getStateForPlacement`, `sideUpdate`, and `updateShape` at `:135–218`). `IronBarsBlock.getStateForPlacement` and horizontal `updateShape` feed `attachsTo` into NORTH/EAST/SOUTH/WEST (`IronBarsBlock.java:26–56` old; `:29–61` new). These are state-generation differences for equal supplied neighbor state/face/tag inputs; block placement and neighbor-notification production frequency are not part of the claim.

## Collision and local-player movement consequence

Fixed-state wall and Iron Bars collision providers retain the previously reviewed shape math: 1.15.2 wall uses 24-high cross arms plus optional post; 1.16.5 collision map uses the same 24-high collision arms for either LOW/TALL side, while the *direction mask* still selects which arms exist. Fixed-state Iron Bars/CrossCollisionBlock direction-arm geometry is likewise already closed in the provider audit. Thus a changed directional property is a changed sampled collision shape, not a new same-state shape formula. `Entity.move` requests block-collision shapes, and the resulting shapes constrain local-player displacement.

A concrete conditional wall→bars collision setup is feasible. Put a wall at `(0,64,0)` with an Iron Bars neighbor east at `(1,64,0)`. Hold the old-predicate-false conditions stated above; set the wall's other horizontal sides to NONE, keep other surrounding collision cells empty, and choose the new wall sample with its east side present. For an airborne standing LocalPlayer (`onGround=false`, so `Entity.move` takes no step alternative) at center `(1.5,64.2,0.87)` with width `0.6`, the pre-move box is approximately X `[1.2,1.8]`, Y `[64.2,66.0]`, Z `[0.57,1.17]`. Request `(-0.3,0,0)`. The new east wall arm occupies local X `[5/16,1]`, Z `[5/16,11/16]`, Y `[0,24/16]`; it is initially clear in X but the westward sweep reaches it, limiting X motion to about `-0.2`. In the old state there is no east arm; its post is inside X≤12/16, so even the full old move ends with the box at X≈`[0.9,1.5]` and does not meet the post. The Iron Bars' collision arms remain in their own center-Z strip `[7/16,9/16]`, disjoint from the player's Z minimum `0.57`; the other cells are assumed clear. Thus, under the state and scene conditions, the changed wall arm can alter the LocalPlayer's direct X displacement under `Entity.move`. This is conditional on the complete old-false predicate and supplied states; it proves neither that a world producer creates this exact layout nor how often it is sampled.

For the bars→WALLS route, a changed direction mask likewise selects the already paired fixed-state bar arm shape. The neighbor occupies the direction of that added arm, so a movement witness also requires that its own collision shape leave the swept player box clear. The source-only evidence fixes that condition as an explicit additional sampled-state premise; without a concrete effective tag/member state and its own collision shape, do not claim an unconditional bars→wall displacement example. Retain this as a conditional route rather than guessing tag payloads.

## Bounded verdict

- `WallBlock.connectsTo` → Iron Bars is a source-confirmed 1.16 route, conditional on the full old predicate being false. The resulting wall side mask can change a collision box used by LocalPlayer `Entity.move`; the conditional box above shows a direct collision response.
- `IronBarsBlock.attachsTo` → effective `BlockTags.WALLS` is a distinct source-confirmed 1.16 route, conditional on tag membership and the full old predicate being false. Its state-dependent movement consequence requires the tag-neighbor collision-clear input above.
- Fixed-state wall and Iron Bars provider closures remain closed; no method-wide/provider-wide reopening is warranted.
- This is not pair freeze, runtime trajectory, or proof of state producer frequency.

## Prior exact evidence reused

- `.native-source/20261030/1152-1165-finish43/wall-block-variants/report.md` contains fixed-state wall shape and collision dispatch audit.
- `.native-source/20261030/1152-1165-finish43/existing-block-provider-residual-inventory/report.md` contains the provider route inventory and exact source identities.
- `.native-source/20261030/1152-1165-finish43/block-suffocation-registrations-independent-review/report.md` preserves the correction to condition the complete old wall predicate and not assume tag membership.


## Erratum 01 — explicit movement witnesses and supplied-state gates (2026-10-31)

This addendum preserves the bounded source claim above and replaces its final two witness paragraphs where they were underspecified. Both examples are conditional same-input collision witnesses; neither asserts that an unverified tag payload or a world producer supplies the assumed state.

### Wall → Iron Bars witness: explicit neighbor state and movement mode

Keep the east Iron Bars neighbor at `(1,64,0)`. Its supplied horizontal state is exactly `WEST=true, NORTH=false, EAST=false, SOUTH=false`; other state properties are immaterial to this geometry. The wall is at `(0,64,0)`, its other sides are `NONE`, and surrounding collision cells are clear. The old `WallBlock.connectsTo` result is false under the old-predicate conditions already listed above: the effective `WALLS` tag check is false, the old `!exception && sturdy` disjunct is false (either exception is true or the supplied sturdy-face result is false), and the Iron-Bars neighbor cannot satisfy the fence-gate disjunct. The new `instanceof IronBarsBlock` term makes the wall EAST property true.

Use an ordinary standing LocalPlayer box centered at `(1.5,64.2,0.87)`, width `0.6`, height `1.8`, with `noPhysics=false`, `MoverType.SELF`, and `onGround=false`; request `(-0.3,0,0)`. Its initial box is X `[1.2,1.8]`, Y `[64.2,66.0]`, Z `[0.57,1.17]`. `CrossCollisionBlock.makeShapes` gives the wall east arm local X `[5/16,1]`, Z `[5/16,11/16]`, Y `[0,24/16]`; this arm intersects the westward swept box and clips X displacement to about `-0.2`. With the old EAST property absent, the wall post ends at local X `12/16`; the full requested move leaves player minX at `0.9`, so the post cannot be reached. The supplied Iron Bars state has only its WEST arm; its collision is within the center-Z strip `[7/16,9/16]`, disjoint from player minZ `0.57`, so it does not confound the wall-arm comparison. `Entity.move` bypasses the collision branch only when `noPhysics` is true; with the stated false value it performs collision. Its step alternative is gated by `onGround || (vertical collision && requested y < 0)`; airborne `onGround=false` and zero requested Y make that gate false, excluding a step alternative.

### Iron Bars → WALLS witness: redstone wire as a conditional tag member

Put Iron Bars at `(0,64,0)` with exactly `EAST=true` and `NORTH/SOUTH/WEST=false`; put REDSTONE_WIRE at `(1,64,0)` and air elsewhere. Assume only for this witness that the supplied wire block state is a member of the effective `BlockTags.WALLS` input. This is a conditional tag-membership premise, not a claim about default or shipped tag contents. On the wire's west face, the old `attachsTo` expression is false: `Block.isExceptionForConnection(Blocks.REDSTONE_WIRE)` is false because the enumerated exceptions are Leaves, Barrier, Carved Pumpkin, Jack o' Lantern, Melon, Pumpkin, or a SHULKER_BOX-tagged block; the sturdy-face result is false because the registered wire block uses `noCollission()` and the default block support shape is its collision shape; and the neighbor is not an `IronBarsBlock`. The new `block.is(BlockTags.WALLS)` term makes the bars EAST property true for this conditional input.

Take a standing LocalPlayer box centered at `(1.5,64.2,0.8)`, width `0.6`, height `1.8`, with `noPhysics=false`, `MoverType.SELF`, and `onGround=false`; request `(-0.3,0,0)`. Its initial X interval is `[1.2,1.8]` and Z interval `[0.5,1.1]`. With Iron Bars constructor parameters `(1,1,16,16,16)`, `CrossCollisionBlock.makeShapes` yields the EAST collision arm at local X `[7/16,1]`, Z `[7/16,9/16]`, Y `[0,1]`; the arm overlaps the box's Z interval and clips the westward displacement from `-0.3` to about `-0.2`. Before the added arm, the central core ends at X `9/16`, over `0.63` blocks from the starting player minX, so the same movement cannot reach it. REDSTONE_WIRE is registered with `noCollission()`, so the neighbor itself contributes no collision shape to this sweep. The all-other-cells-air premise removes other block collisions. `Entity.move` and its step-gate analysis are the same as in the preceding witness.

### Exact source identity for this addendum

The exact boundary source identities and each release's manifest validation remain in `source-hashes.tsv` (14/14 WallBlock/IronBarsBlock hashes match their own release maps; `.3/.4` use embedded `.decompile-provenance.json`). For the geometry, state and movement details cited in this addendum, these 1.16.5 Mojmap source hashes match that release's `mojmap.sources.sha256`:

| Source | SHA-256 |
|---|---|
| `net/minecraft/world/level/block/IronBarsBlock.java` | `e84392b278ceeaf02abb0c72c74e1011629d7c0add57f6bf0f6ec5b6006feaab` |
| `net/minecraft/world/level/block/CrossCollisionBlock.java` | `cebb3dcbbb94ab7513e11430fa4969bc1ff0a96c733298940889fc749b7ec1e4` |
| `net/minecraft/world/level/block/Blocks.java` | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` |
| `net/minecraft/world/level/block/Block.java` | `2838aa4fb4052b3bd78d77b3d232a6321d03bd2dfd52275ffacfe6915a89e5b6` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |

`LivingEntity.travel` calls `move(MoverType.SELF, ...)` in the ordinary walking path (1.16.5 `LivingEntity.java:2063–2065`); `LocalPlayer` inherits this player-body movement path. `Entity.move` collision and step conditions are at `Entity.java:485–505, 668–690`. The source identities above are 1.16.5 only for the collision geometry/witness mechanics; the 1.15.2→1.16 introduction boundary and persistence through 1.16.5 are established by the exact per-release `WallBlock` and `IronBarsBlock` identities in `source-hashes.tsv`.


## Erratum 02 — final movement-scope disposition (2026-10-31)

The `getStateForPlacement` / `updateShape` routes above produce vanilla block-state properties. Their changed Wall→Iron Bars and Iron Bars→WALLS connection predicates are excluded from movement-source coverage because this project preserves vanilla block states. The conditional displacement witnesses in Erratum 01 compare distinct generated states and therefore do not establish an eligible movement delta. Retain the 1.15.2→1.16 predicate boundary and complete old-predicate conditions only as source traceability. For an equal supplied vanilla block state, the Wall and Iron Bars collision providers have no version difference; the eligible fixed-state shape closure remains closed.
