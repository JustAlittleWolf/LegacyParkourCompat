# Whole-pair source coverage audit: 1.21.0 → 1.21.1

## Bounded verdict

**Recommend source-pair freeze for ordinary client player movement under equal movement-relevant received state (including relevant block-entity data) and equal effective resources.** The pinned whole-tree audit accounts for every Java path and identifies 17 changed paths. Rechecking their dispositions, the corrected ordinary-player terminal order, packaged movement resources, and both entity-backed collision providers finds no remaining concrete client movement delta under that boundary. This closes source coverage for the stated pair comparison; it does not establish runtime movement parity.

This conclusion excludes differences in server-produced inputs: the four reordered `onRemove` callbacks can alter authoritative neighbor/comparator work and later block states. It also assumes the same effective data-pack/resource stack and the same received world, entity, ability, attribute, effect, equipment, key, and other movement inputs. Differing generated-world states or received values are unequal inputs, not a local source-pair result.

## Pinned input integrity

I recomputed SHA-256 for every assigned primary report and review. These all match the requested pins:

| Evidence | SHA-256 |
|---|---|
| `.native-source/20261030/1210-1211-audit/source-audit.md` | `0c23a664552dd5fcfdfe12d715dfd3cfc25efcaba2ccef3eb12b3e3dc2f27b1d` |
| `.native-source/20261030/1210-1211-block-callbacks/closure.md` | `b7dbc5af7fcad4b3e8326ecbc2888c7ec8e72f593d153c1180f57394bacda5e0` |
| `.native-source/20261030/ordinary-pool/1210-1211-block-callbacks-review/audit.md` | `ea15f281abe0c4b0412f57eb22ff94dca4caf8709b072207b752c1d97ae7d2df` |
| `.native-source/20261030/ordinary-pool/1210-1211-resources/report.md` | `59b727aa2e74d6c71927cad0b634cbfac2689fda09ebcda1e0b751b149685ec0` |
| `.native-source/20261030/ordinary-pool/1210-1211-resources-review/review.md` | `bd1ffd84267b58121a7c1fd3b1c1a171c6b2628ff973ec4043b41b92c87595e4` |
| `.native-source/20261030/ordinary-pool/1210-1211-shulker-reachability/report.md` | `6e602b6af56f2d2ff78cee28f065e780d96f0ae9abf7be2dd65bdd8971ac3a6e` |
| `.native-source/20261030/ordinary-pool/1210-1211-shulker-reachability-review/review.md` | `be540921f5ca269d4d2e6361c61b9c2462172bd7ff1f0e8a5d37fc8e1bce83ec` |
| `.native-source/20261030/ordinary-pool/1210-1211-terminal-inventory/terminal-inventory.md` | `32902f4e8bf35ba375f9e3ae3d515e62e318cdc3dc1daca2ba0d76479678cd90` |
| `.native-source/20261030/ordinary-pool/1210-1211-terminal-inventory-review/review.md` | `dc9482ec21671704914d01045f37938d2c67c56dc324e173815a3640a19737e9` |
| `.native-source/20261030/ordinary-pool/1210-1211-terminal-inventory-review/follow-up-shulker-closure.md` | `678ece932dbc0ab3a159a5172fed62d7b8b9719fdec950db4eebca6f1a9bb03a` |

The follow-up supplement cites a 65-character SHA-256 for `correction-supplement.md`, which is malformed and cannot match any SHA-256. The file itself hashes to `1da3f3595d4dfb79e05c3805e751fef884ff90bb27259cba93b5bdaf71d83a18`. I did not treat the malformed pin as identity evidence; I read that supplement and directly rechecked its source anchors against the exact source trees. The defect is a citation typo, not a movement-source gate.

The source audit reports complete exact Mojmap source inventories for `decompiled_minecraft/1.21/` (Minecraft 1.21.0) and `decompiled_minecraft/1.21.1/`: 5,363 Java files and mapped paths per side, equal path sets, and exactly 17 changed Java paths. I did not repeat its 10,726-file hashing pass. I checked the changed-path dispositions and rehashed key terminal files against the canonical read-only source root `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

## Ordered movement-terminal coverage

The existing terminal inventory needs the reviewed corrections applied conceptually:

1. `Player.tick` samples `updateIsUnderwater` before `super.tick` (1.21.0 `Player.java:275`). The inherited tick/base-tick path precedes `LocalPlayer.aiStep`; local input is sampled at `LocalPlayer.java:664`, before `super.aiStep` at `:804` reaches living travel.
2. The ordinary travel/collision sequence reaches movement clipping, position/bounds and collision flags, block-contact/in-block callbacks, and later-tick consumers. Its selected `LocalPlayer`, `Player`, `LivingEntity`, `Entity`, `Level`, `BlockGetter`, `BlockState`, `BlockBehaviour`, and `AABB` source roots are byte-identical across the endpoints. I independently rehashed the principal roots (`LocalPlayer`, `Player`, `LivingEntity`, `Entity`, `Level`, `BlockGetter`, `BlockState`, `BlockBehaviour`, and `AABB`); all matched across versions.
3. Add the distinct post-travel player-pushing terminal: `LivingEntity.aiStep` calls `pushEntities` at line 2705; its client player-candidate path dispatches `doPush` at lines 2746–2749 and can reach `LocalPlayer.push`, writing local delta movement. `LivingEntity` and `LocalPlayer` are byte-identical across the pair.
4. Direct client corrections and world/state delivery are inputs to these unchanged consumers. With equal received movement-relevant state and equal event values, the consumer side is pair-equal; differences in server command outcomes, block-state production, or network-delivered values are outside this equal-input comparison.

The source audit's 17-path inventory and independent review provide the changed-code closure. Three paths are version/report/protocol metadata; eight are command/chat selector parsing or resolution; four are logical-server-only `onRemove` notification reorderings; two are `BlockEntity`/`LevelChunk` validation and lifecycle paths. `BlockEntity.fillCrashReportCategory` also extracts equivalent crash-report Name formatting. No changed path adds a local player movement operation under the stated input boundary.

## Changed block-entity validation and collision terminals

The 1.21.1 `BlockEntity`/`LevelChunk` validation is the one changed Java mechanism that can clean up an existing block entity during state application. The audit therefore has to include block shapes whose collision/support results consume block-entity state, rather than assuming all block-entity effects are irrelevant.

- **Shulker boxes:** the shape/support methods read `ShulkerBoxBlockEntity` animation/closed state. The pinned reachability report and independent review trace chunk load, incremental block updates, typed block-entity updates, and unload/reload, finding no legal vanilla client transition that leaves a stale or mismatched Shulker entity. Relevant animation data is part of equal movement-relevant received state. The `ShulkerBoxBlock` geometry and `ShulkerBoxBlockEntity` implementation are unchanged; the only block diff is its server-only removal callback order.
- **Moving pistons:** `MovingPistonBlock.getCollisionShape` (1.21.0 `MovingPistonBlock.java:109–117`) uses `PistonMovingBlockEntity.getCollisionShape`, so this is another movement terminal. I independently checked both source hashes: `MovingPistonBlock.java` is `936ac30844ca1e6234030ae9934aa5fee99f51da9fc507f0cf0600e46bbc26db` on both sides, and `PistonMovingBlockEntity.java` is `7fd3e97e09e07df9efee9f756181a97cc00374c51fa7011e1307620b4f4ac8bf` on both. `BlockEntityType.PISTON` is registered for `Blocks.MOVING_PISTON` alone (`BlockEntityType.java:97–99`); validity tests the registered block identity (`:300–302`). Legal client chunk construction creates the entity from the received moving-piston state and accepts only a type-matching tag; an incremental block-identity change removes/recreates the entity before the new-state path, while same-block property updates retain a valid piston entity. Typed block-entity updates do not create or replace entities. Thus the new guard has no identified legal vanilla piston transition that changes the collision result for equal received state/entity data.

The ordinary collision providers otherwise have unchanged Java implementations under the pinned whole-tree comparison. The default packaged movement-selection inputs are equal by uncompressed resource-entry contents; the only changed data entries are generated structure templates, which can matter only through differing resulting world state. Both resource report and independent review pins match.

## Freeze decision and limits

The corrected terminal inventory, full exact Java path/hash accounting, dispositions of all 17 changed paths, paired resource comparison, and bounded client reachability closures leave no concrete changed-code effect on ordinary client player movement under equal received movement-relevant state and equal effective resources. **Source pair freeze is supportable at this boundary.** Record server-produced differences, custom resource stacks, unequal world/BE/packet state, malformed or externally injected client state, generated-world differences, and profile/runtime execution as outside this verdict. No tests, builds, game launches, source generation, product code, wiki, history, or backlog data were consulted or changed.
