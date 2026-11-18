# Independent C audit: 1.21.8 → 1.21.9

## Frozen C coverage digest (pre-comparison)

Scope: ordinary dry, nonvehicle, non-fall-flying local-player horizontal acceleration, terrain friction, speed input and input scaling through the horizontal vector passed to the requested self-move. Include keyboard vector construction/normalization, local-input modifiers, player speed accessor, ground-vs-air speed selection, friction-influenced speed, yaw transform and trigonometric lookup table, plus post-move horizontal friction. Exclude vertical gravity/jump, sprint-state lifecycle, vehicles, collision resolution, turn/input-device plumbing, swimming/flying-special modes, and the separately owned 1.21.10→1.21.11 boundary.

Inventory, checked on both exact source trees:

1. Local-player tick/AI dispatch, `LocalPlayer.applyInput`, `modifyInput`, `modifyInputSpeedForSquareMovement`; `ClientInput`, `KeyboardInput.tick`.
2. `LivingEntity.aiStep` input zeroing and travel dispatch; dry `travelInAir`, `handleRelativeFrictionAndCalculateMovement`, `getFrictionInfluencedSpeed`, `handleOnClimbable`, `shouldDiscardFriction`.
3. `Player.travel`, `getSpeed`, `getFlyingSpeed`, player movement/sneaking attributes, and the sprint speed input as a fixed current state (sprint lifecycle itself excluded).
4. `Entity.moveRelative`, `getInputVector`, support-block position lookup (`getOnPos`, `Mth.floor`, `BlockPos.atY`), delta-motion add/get/set helpers, and virtual `LocalPlayer.move` forwarding of the requested vector.
5. `Mth.sin/cos` and `SIN` table construction; `Vec2` normalization/length/scaling and `Vec3` addition, normalization/scaling helpers used by the path.
6. Ground friction source: `travelInAir` reads the below-block `Block.getFriction`; `BlockBehaviour.Properties` default, setter and all block friction property writes checked.

No difference conclusion is part of this frozen inventory. Dependency closure and source identities follow.

## Source identity and integrity

Both sides are exact Mojmap decompilations. On 1.21.8, sources are under the version-root layout `decompiled_minecraft/1.21.8/mojmap/`; `mojmap.ready.json` identifies version `1.21.8`, mapping `mojmap`, 6,105 source files, source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`, and client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`. The root-level source manifest currently hashes to that digest. The root-level provenance file is `mojmap.provenance.json` (SHA-256 `90fcb3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa`), ready marker `mojmap.ready.json` (`2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`), artifact manifest `artifacts.sha256` (`c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`), and diagnostics `movement-diagnostics.txt` (`c954da48013372c7b423bf47597b447d32607b2c39b00e3cf353b1d5fcc95806`). The pinned 1.21.8 client mappings artifact `1.21.8/client_mappings.txt` is SHA-256 `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`.

On 1.21.9, the source root is `decompiled_minecraft/1.21.9/mojmap/` with the legacy mapping-root provenance file embedded at `.decompile-provenance.json` (SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`). Its recorded inputs pin the 1.21.9 client JAR to `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`, Mojang client mappings (`client.txt`) to `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`, and exact version metadata `1.21.9.json` to `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`. The embedded provenance map’s SHA-256 entries match the current files below.

Each listed code file was SHA-256 checked against its side’s source manifest (1.21.8) or embedded provenance source map (1.21.9):

| Relative source file | 1.21.8 | 1.21.9 |
|---|---|---|
| `net/minecraft/core/BlockPos.java` | `a01661d6880dab86f2adb8c8bc203484f63bc0e10eaa14e36525e7123a2d4d15` | `f2597185a8cfd7bf20ab5c6e29039977a35ca0b3083154815c21fabb49ef58f4` |
| `net/minecraft/client/player/ClientInput.java` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| `net/minecraft/client/player/KeyboardInput.java` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` |
| `net/minecraft/util/Mth.java` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` |
| `net/minecraft/world/phys/Vec2.java` | `b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe` | `b637ef6899de5d8892e19273b114ffaf0397761e509170a56d52dd068f54bffe` |
| `net/minecraft/world/phys/Vec3.java` | `c7d5659c545445e329c05ef58cec2ce44853dc5f15673ab7d4b913c78aa33ad6` | `f1601867b42544c1461fd7f315b5ec7eb108fc23f2b755f923a609f5466cd154` |
| `net/minecraft/world/level/block/Block.java` | `d66c7efd4afb3e52f8d239a1b5fcd00a5c24874d75b61b5b16b794faf200f2c2` | `4563cbfb224c213cac1e24baa01a79c91eb4ab79ffc327acff280bd55142db90` |
| `net/minecraft/world/level/block/Blocks.java` | `5f929198d7e975bdf82582e7448be45601b9a0e140d435a1a6013e1b6d10ca9f` | `f94e222b2d37a3d9fae6f6d1f83dfa449b05ff847ea366edc5db89b2fe89b139` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `15418b0026919ef5ab97780a6ce8844677dc82d36cc4433a58f379b837d5cc50` | `195711b3651969f0202296bc4522b1dbc67d190ad21fa73b00e97b5040528620` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` |

## Exact source members

Paths are relative to each exact Mojmap source root above; listed line numbers identify member starts.

| Path through the operation | 1.21.8 member | 1.21.9 member |
|---|---|---|
| Local input preparation | `LocalPlayer.java:197 tick, 683 aiStep, 702 input.tick, 608 applyInput, 623 modifyInput, 641 square correction; KeyboardInput.java:23` | `LocalPlayer.java:195 tick, 687 aiStep, 706 input.tick, 612 applyInput, 627 modifyInput, 645 square correction; KeyboardInput.java:23` |
| AI/travel dispatch and dry horizontal mechanics | `LivingEntity.java:2512 tick, 2721 aiStep, 2858 applyInput, 2245 travel, 2276 travelInAir, 2446 relative-friction move, 2490 friction-influenced speed` | `LivingEntity.java:2542 tick, 2751 aiStep, 2888 applyInput, 2275 travel, 2306 travelInAir, 2476 relative-friction move, 2520 friction-influenced speed` |
| Player speed source and travel delegation | `Player.java:252 createAttributes, 1438 travel, 1482 getSpeed, 2045 getFlyingSpeed` | `Player.java:213 createAttributes, 1295 travel, 1339 getSpeed, 1867 getFlyingSpeed` |
| Input rotation/LUT and requested move state | `Entity.java:1530 moveRelative, 1535 getInputVector, 3543 getDeltaMovement, 3547 setDeltaMovement`; `Mth.java:32 SIN, 48 sin, 52 cos` | `Entity.java:1585 moveRelative, 1590 getInputVector, 3610 getDeltaMovement, 3614 setDeltaMovement`; `Mth.java:32 SIN, 48 sin, 52 cos` |
| Support friction lookup and coefficient source | `Entity.java:922 getBlockPosBelowThatAffectsMyMovement, 930 getOnPos(float)`; `BlockPos.java:242 atY`; `Mth.java:65 floor`; `Block.java:418 getFriction`; `BlockBehaviour.java:507 getBlock, 959 default friction, 1070 Properties.friction` | `Entity.java:935 getBlockPosBelowThatAffectsMyMovement, 943 getOnPos(float)`; `BlockPos.java:243 atY`; `Mth.java:65 floor`; `Block.java:457 getFriction`; `BlockBehaviour.java:510 getBlock, 965 default friction, 1076 Properties.friction` |
| Local-player requested-move forwarding | `LocalPlayer.java:916 move` | `LocalPlayer.java:906 move` |
## Trace and comparison verdict

**Verdict: no C-scope horizontal acceleration/friction/input-transform difference found in 1.21.8→1.21.9.**

Reachable local path: `LocalPlayer.tick` → inherited tick/AI dispatch → `LocalPlayer.aiStep` refreshes input and calls `super.aiStep` → `LivingEntity.aiStep` calls `applyInput`, zeros x/z only under the same immobile gate, constructs `(xxa, yya, zza)`, and under the same simulation/AI gate calls `travel`. For the assigned ordinary dry, nonvehicle, non-fall-flying case, `Player.travel` delegates to `LivingEntity.travel`, which selects `travelInAir`.

- Input preparation is identical: `KeyboardInput.tick` forms side/forward impulses and normalizes the `Vec2`; `LocalPlayer.applyInput` passes the move vector through `modifyInput`, retaining the same `0.98F`, item-use `0.2F`, sneaking-speed attribute scale, and square-movement correction order. `Vec2.normalized` retains its `1.0E-4F` threshold and division order.
- Ground/air selection and speed are identical: `travelInAir` uses below-block friction only when `onGround`, otherwise `1.0F`; `getFrictionInfluencedSpeed` retains `getSpeed() * (0.21600002F / (friction * friction * friction))` on ground and `getFlyingSpeed()` in air. `Player.getSpeed` remains a float cast of `Attributes.MOVEMENT_SPEED`; Player's default movement-speed attribute remains `0.1F`. Player's current-state sprint speed branches in `getFlyingSpeed` retain `0.025999999F`/`0.02F`; the `0.3F ADD_MULTIPLIED_TOTAL` sprint attribute modifier and `setSprinting` application are unchanged. Sprint start/stop lifecycle remains excluded.
- Terrain friction lookup is identical: the movement support position still uses `getOnPos(0.500001F)` with the same support-block/fence/wall handling; `Block.getFriction` still returns the block property. The generic property default remains `0.6F`; the complete `Blocks.java` friction assignment inventory has the same values in both versions (`0.98F`, `0.8F`, `0.98F`, `0.98F`, `0.989F`). No friction assignment or getter difference was found.
- Acceleration and heading math are identical: `handleRelativeFrictionAndCalculateMovement` still applies `moveRelative(getFrictionInfluencedSpeed(friction), input)` before the requested self-move. `getInputVector` retains its `< 1.0E-7` zero cutoff, `lengthSqr() > 1.0` normalization gate, scale order, float yaw conversion, and x/z rotation expression. `Mth.sin/cos` still index the same 65,536-entry table using `10430.378F` and cosine offset `16384.0F`; the table construction expression is unchanged. `Vec2.scale/lengthSquared`, `Vec3.add/normalize/scale/multiply`, `Mth.sqrt/floor`, `BlockPos.atY`, `Entity.getDeltaMovement/setDeltaMovement`, and the block-property `getBlock` helper used by this path are unchanged.
- Requested move is preserved: `LocalPlayer.move` calls `super.move(type, requestedVec)` with the same arguments before post-move bookkeeping. 1.21.9 changes walk-distance bookkeeping to `addWalkedDistance`; it does not rewrite the requested vector. After movement, the horizontal damping remains friction×`0.91F` (or no damping when `shouldDiscardFriction` is true); that state accessor and horizontal expressions are unchanged.

The compared method bodies are exact-text identical for `KeyboardInput.tick`, `LocalPlayer.applyInput/modifyInput/modifyInputSpeedForSquareMovement/distanceToUnitSquare`, `Player.travel/getSpeed/getFlyingSpeed/createAttributes`, `LivingEntity.travel/handleRelativeFrictionAndCalculateMovement/handleOnClimbable/getFrictionInfluencedSpeed/shouldDiscardFriction/setSprinting`, `Entity.moveRelative/getInputVector/getBlockPosBelowThatAffectsMyMovement/getOnPos(float)/getDeltaMovement/setDeltaMovement`, `Vec2.normalized/length/scale/lengthSquared`, `Vec3.add/normalize/scale/multiply/lengthSqr`, `Block.getFriction`, `BlockBehaviour.Properties.friction(float)`, the `MOVEMENT_SPEED` and `SNEAKING_SPEED` declarations, and `Mth.sin/cos/sqrt/floor` plus the `SIN` initializer, and `BlockPos.atY`.

Observed adjacent changes outside this slice: 1.21.9 changes LocalPlayer's sprint trigger window from a fixed `7` to the sprint-window option, changes a vertical-only `isClientSide` access in `travelInAir`, and changes walk-distance bookkeeping. The `LivingEntity.aiStep` client/server AI guard also changes field access to the equivalent `isClientSide()` call; the input-zeroing and travel gates remain in the same order. None changes the assigned horizontal request, input scaling, friction coefficient, speed selection, rotation LUT, or horizontal damping. Collision resolution and vertical outcomes were not assessed.

No implementation, product edits, runtime launches, tests or builds were performed.
## Phase-two comparison of the frozen source report

Frozen input checked before and after this comparison: `c-ground-air-accel-1218-1219-source/report.md`, SHA-256 `13345c0854a6650bc4be9063a7522d662bf4507ec364be4da6725ed2167b937c`.

The source report agrees with this independent audit on the overlapping downstream slice: dry `Player.travel`/`LivingEntity.travelInAir` dispatch, ground-vs-air friction scaling, relative acceleration order, yaw transform, delta-velocity addition, and the `SELF` move request have no 1.21.8→1.21.9 difference. My verdict additionally checks the local input preparation and helper transformations that the source report explicitly leaves upstream of its assigned field vector; those are also unchanged.

Caveats for reconciliation:

- The source report describes `0.02F` as an “inherited” `getFlyingSpeed()` result. The relevant runtime method is `Player.getFlyingSpeed()`; its ordinary non-flying, non-passenger, non-sprinting branch returns `0.02F`, while its active-sprint branch returns `0.025999999F`. My independent comparison checked this override and both values; its code is identical across the pair. This wording does not change the no-difference verdict, but should be corrected if the report is reused verbatim.
- The report’s new `Player → Avatar → LivingEntity` inheritance claim is outside the method-body comparison recorded here. I did not independently authenticate the `Avatar` declaration or absence of an `Avatar.travel` override in this phase; treat that specific claim as author-reported, not as covered by this audit.
