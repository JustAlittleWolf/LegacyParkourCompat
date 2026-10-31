# Movement-support factors and block-touch responses: 1.20.6 → 1.21 → 1.21.1

Status: bounded source-only slice; no pair-freeze claim. Mapping family is Mojmap. Canonical sources are read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21,1.21.1}/mojmap`.

## Evidence identity

- 1.20.6 ready marker identifies Mojmap client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`; its source-manifest SHA-256 is `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`.
- 1.21 source tree embeds `.decompile-provenance.json`, binding the Mojang client SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`, Mojang client mappings SHA-256 `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`, and per-source hashes. The inspected 1.21 files matched the embedded source entries.
- 1.21.1 ready marker identifies Mojmap client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; its source-manifest SHA-256 is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.

The following movement-path source hashes were checked against those endpoint manifests or the 1.21 embedded source map. 1.21 and 1.21.1 files in this table are byte-identical.

All 23 source files named in the hash table and callback-difference inventory below were rehashed against the applicable endpoint manifest or embedded source map; every check matched.

| Source | 1.20.6 SHA-256 | 1.21 / 1.21.1 SHA-256 |
|---|---|---|
| `world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `world/level/block/Block.java` | `0c96f526470944baa74d212ca8bfb8b579fd6a1d7045498b8e062b4868bb8e09` | `6502b61ce4d667501b4e4889eff3aa337e3f66c0891232d5b679b181782acd0f` |
| `world/level/block/state/BlockBehaviour.java` | `5ccc577c6b257badf8bffab54181c45b6c57779c8545efe5c54a9766687e685b` | `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292` |
| `world/level/block/Blocks.java` | `684579bbcbe48b1f0984090a4ca044c0b2cee6d08c2dc1e449e2259389c5b129` | `538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d` |
| `data/tags/VanillaBlockTagsProvider.java` | `a0ab3c2a12f8e93ae7e6231069051712cf0f6fcaf3d06e86caa0f9b2d1af310e` | `819d133e6ee8f82485b3d84fcfcfa48e4ee2824431379b93de48c7dddc7b32f0` |
| `world/level/block/HoneyBlock.java` | `fd9c67c31c34833bf442e81ec0b1647e9f9e9042f01b9cd63127d2f832d5d8ff` | same |
| `world/level/block/SlimeBlock.java` | `62c1a543343cce23a47ff35ce51a4512637eda02c775db30bdefbe04d2673d46` | same |
| `world/level/block/BedBlock.java` | `3e7fa99ae0d73d6b4deec4d0425006d3439e3b84bcdb71367acb0ca6531230d5` | same |
| `world/level/block/WebBlock.java` | `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414` | same |
| `world/level/block/SweetBerryBushBlock.java` | `615b36436b64e20f6f33235aa2e24aa84a3d0e9b54ff27aa7d97821d8ffb6d3c` | same |
| `world/level/block/PowderSnowBlock.java` | `7a6d6ad2e6d6d719344de4bb4f4e4973387aa88e61757988afea25b46c176ad3` | same |
| `world/level/block/BubbleColumnBlock.java` | `39c4da51460f4c2c06927f15de65a9ee2ae2f8bee0a2c9289cea375ad5d53a7c` | same |

## Reachable block-factor consumers

The local player inherits the client-side `Entity` and `LivingEntity` movement routes. `LocalPlayer.isEffectiveAi()` returns true, so its client movement reaches these inherited consumers; it is not excluded by the default logical-side predicate. The player path reaches `Entity.move`, then `tryCheckInsideBlocks`, the supporting-block callbacks and post-collision horizontal speed-factor multiplication. Same valid current block states, support state, collision context and player movement state are sufficient inputs to compare these consumers.

### Surface friction

`LivingEntity.travel`'s ordinary ground/air branch samples `getBlockPosBelowThatAffectsMyMovement()`, reads `Block.getFriction()`, computes ground drag as `friction * 0.91F` (otherwise `0.91F`), and passes the same friction to `handleRelativeFrictionAndCalculateMovement`. The paired 1.20.6 and 1.21 branch bodies preserve this order and math; 1.21.1 `LivingEntity.java` is byte-identical to 1.21. `Block.getFriction()` returns its configured friction field in all three. `BlockBehaviour.Properties` defaults to `0.6F` and the setter stores the supplied float; these are unchanged. The vanilla registrations that override friction remain ICE, PACKED_ICE and FROSTED_ICE at `0.98F`, BLUE_ICE at `0.989F`, and SLIME_BLOCK at `0.8F`; the three source lists and values match. Other `getFriction()` consumers found in the source search were non-player or vehicle/item physics and are excluded.

### Block speed factor and Soul Speed

`Entity.getBlockSpeedFactor()` uses the block at the entity position except that WATER and BUBBLE_COLUMN use their current block factor directly; otherwise a factor of `1.0F` falls back to `getBlockPosBelowThatAffectsMyMovement()`. After inside-block handling, `Entity.move` multiplies horizontal delta by this factor. `Player.getBlockSpeedFactor()` returns `1.0F` while flying or fall-flying and otherwise delegates. These bodies and gates are unchanged in the compared releases.

At 1.20.6, `LivingEntity.getBlockSpeedFactor()` returns `1.0F` when `onSoulSpeedBlock()` and a positive Soul Speed enchantment are both true; at 1.21 it interpolates the native factor toward `1.0F` by `MOVEMENT_EFFICIENCY`. The default `SOUL_SPEED_BLOCKS` source membership remains SOUL_SAND and SOUL_SOIL. This is an attribute-backed movement input refactor; its same-effective-state and vanilla enchantment input path belong to the separate B6 dynamic-input closure and are not reclassified here as a new accepted claim.

The common vanilla speed-factor registrations are unchanged: SOUL_SAND remains `0.4F` and HONEY_BLOCK remains `0.4F`. Source search finds no other explicit `.speedFactor(...)` registrations in these endpoint block trees.

### Block jump factor and auto-jump

`Entity.getBlockJumpFactor()` reads the block factor both at `blockPosition()` and at `getBlockPosBelowThatAffectsMyMovement()`; it uses the latter only when the former equals `1.0`. `LivingEntity.getJumpPower` multiplies the jump-strength term by this factor before adding jump-boost power. `LocalPlayer.canAutoJump()` also requires a factor of at least `1.0`, alongside enabled/ready auto-jump, grounded, not staying on an unstable bottom, not a passenger and nonzero input. `updateAutoJump`'s obstacle collision sampling and height thresholds are the same in the inspected 1.20.6 and 1.21 method bodies; the 1.21.1 `LocalPlayer.java` is byte-identical to 1.21. `Entity.getBlockJumpFactor()` is the same source body at all three versions.

HONEY_BLOCK is the only common vanilla `.jumpFactor(...)` registration found; it remains `0.5F`. Its speed/jump property construction is unchanged. `UNSTABLE_BOTTOM_CENTER` remains the `FENCE_GATES` tag; this and the default `CLIMBABLE` list (ladders, vines, scaffolding, weeping/twisting vines and cave vines) are unchanged in `VanillaBlockTagsProvider` at both cutovers.

### Climbable, stuck-in-block and block-touch responses

`LivingEntity.onClimbable()` is unchanged: it rejects spectators, accepts current block states in `CLIMBABLE`, or an open trapdoor directly above a ladder with the matching facing. `handleOnClimbable` is unchanged: horizontal components clamp to ±0.15, downward speed is no less than -0.15, and a Player suppressing ladder slide stops downward movement except on scaffolding. These are movement gates, with tag/state dependencies closed above.

`Entity.makeStuckInBlock` resets fall distance and stores the supplied stuck-speed multiplier; `Player.makeStuckInBlock` skips that effect only while flying. `Entity.move` consumes the multiplier on the player displacement path. The source bodies and gates are unchanged. The movement-affecting vanilla callers checked are WebBlock and SweetBerryBushBlock (`makeStuckInBlock`), and PowderSnowBlock (same stuck vector plus its state/boots walk gate); all three source files are byte-identical across the endpoints. Powder snow walkability uses the powder-snow-walkable entity-type tag or leather boots for LivingEntity, with the same client-side predicate in both cutovers.

The same registered callback comparison includes HoneyBlock's slide/inside response, SlimeBlock and BedBlock fall-bounce callbacks, and BubbleColumnBlock's upward/downward response. These files are byte-identical across all three versions. Bubble columns call inherited `Entity.onAboveBubbleCol` / `onInsideBubbleColumn`; those current-delta updates are unchanged in `Entity.java`. `Block.updateEntityAfterFallOn` still clears Y velocity by multiplying by `(1.0, 0.0, 1.0)`; the SlimeBlock and BedBlock bounce overrides are unchanged.

The callback inventory searched the `world/level/block` source subtree for `stepOn`, `updateEntityAfterFallOn` and `entityInside` definitions (33 source files containing these callback names in 1.20.6; 1.21 adds a file for a modern block). Changed 1.20.6→1.21 source files in this callback inventory include `BaseFireBlock`, `CampfireBlock`, `EndPortalBlock`, `NetherPortalBlock`, `MagmaBlock`, `ButtonBlock`, `FrogspawnBlock`, `RedStoneOreBlock` and base `Block`. The examined non-portal callback changes do not write player position, velocity, pose, input or movement ability: fire/campfire/magma changes concern ignition/damage and the Frost Walker damage exclusion; Button/Frogspawn/RedStoneOre callbacks trigger their block/redstone state or effects. The base `Block.stepOn` and `updateEntityAfterFallOn` bodies remain the same despite other changes in the file. End/Nether portal callbacks have changed client-side portal-state handling; downstream transition/timer/position consequences are not closed by this slice and remain a portal-response dependency. No no-difference assertion is made for those consequences.

## Disposition and residuals

- No independent block-friction, block-jump-factor, climbable, stuck-vector, Honey/Slime/Bed bounce or BubbleColumn movement difference was found across 1.20.6→1.21→1.21.1.
- The 1.20.6→1.21 `LivingEntity.getBlockSpeedFactor` Soul Speed refactor is a movement consumer change whose effective attribute/equipment/tag inputs are owned by the separate dynamic-input closure; custom overlays and packet/effect production timing are not established here.
- End/Nether portal state downstream behavior remains partial. Collision shape providers, auto-jump obstacle queries, fluid refresh/current producers, entity/world tick interleavings and all direct external player responses remain other assigned dependencies. Vehicle, non-player physics, damage/attack, health/food and rendering are excluded.
- Source-only static review; no tests, build, game launch, network mapping lookup or source regeneration/decompilation was used.
