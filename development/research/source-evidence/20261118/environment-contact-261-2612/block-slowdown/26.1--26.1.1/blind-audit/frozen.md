# Blind source audit: ordinary local movement contacts, 26.1 → 26.1.1

## Scope and verdict

This freezes only ordinary non-riding local-player contact behavior for honey-block sliding, cobweb stuck speed, and sweet-berry-bush stuck speed. The inspected behavior is unchanged between the exact releases. For each operation, the relevant producer, contact dispatcher, shared state writer, and next-move consumer have the same source bytes in both versions; under equal block/query results and player state, these bounded contracts are same-input equivalent. This does not freeze other movement areas or establish a full-pair audit, an intermediate patch boundary, implementation acceptance, or runtime parity.

## Exact source identity

Both canonical trees are `decompiled_minecraft/<version>/unobfuscated/` in the primary checkout, with published unobfuscated source (no mapping artifact). Their ready markers report `status=ready`, the exact respective version IDs, 6,882 sources, and Java 25 / Vineflower. Each ready marker's source-manifest and artifact-manifest SHA-256 match the actual files. Provenance identifies the exact input clients:

- `26.1`: client SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`; source manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`; artifact manifest SHA-256 `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0`.
- `26.1.1`: client SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`; source manifest SHA-256 `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`; artifact manifest SHA-256 `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81`.

Provenance records one exact batch, repository commit `fcd113c3fafcfde91d5cb5c8308b77292c601f9d`, with `requestedId` and `resolvedMetadataId` equal to each release. Per-source SHA-256 checks against each source manifest passed for `HoneyBlock.java`, `WebBlock.java`, `SweetBerryBushBlock.java`, `Entity.java`, `LivingEntity.java`, and `LocalPlayer.java`. Every listed source file has the same SHA-256 in the two trees, so the focused bodies below are byte-identical.

## Frozen contact contracts and order

All paths below are relative to the exact version tree above; cited line numbers are stable because the focused files are byte-identical.

### Honey block slide

`net/minecraft/world/level/block/HoneyBlock.java:64-124` dispatches `isSlidingDown` first, then achievement bookkeeping, `doSlideMovement`, and slide effects, before calling `super.entityInside`. `isSlidingDown` (`:89-107`) rejects grounded entities; entities above `posY + 0.9375 - 1.0E-7`; and entities whose `getOldDeltaY(vy) = vy / 0.98F + 0.08` is `>= -0.08`. It then requires horizontal edge contact: `abs(posX + 0.5 - entityX) + 1.0E-7 > 0.4375 + bbWidth / 2.0F` **or** the corresponding Z inequality. `doSlideMovement` (`:114-124`) further scales X/Z only when that same old-Y expression is `< -0.13`, using `-0.05 / getOldDeltaY(vy)`; in every accepted slide it sets Y to `(−0.05 − 0.08) * 0.98F` through `getNewDeltaY`, then resets fall distance. The float-suffixed constants and operation order are unchanged.

Reachable bounded witness, assuming the same honey collision/contact query: an unmounted local player with default width 0.6F, airborne at `y = blockY + 0.8`, centered in Z, and `x = blockX + 1.28` has `dx = 0.78`, overlapping the block horizontally while passing the edge predicate (`0.78 + 1e-7 > 0.4375 + 0.6F/2.0F`). At `vy = -0.22`, the old-Y expression is below `-0.13`; the callback therefore scales horizontal velocity by the prescribed factor, sets the prescribed Y velocity, and zeros fall distance. This witnesses the producer's conditional path without assuming any trajectory beyond the callback.

### Cobweb stuck speed

`net/minecraft/world/level/block/WebBlock.java:27-41` uses `(0.25, 0.05F, 0.25)` unless the entity is a `LivingEntity` with `MobEffects.WEAVING`, for which it uses `(0.5, 0.25, 0.5)`, then calls `makeStuckInBlock`. An ordinary local player is a living entity; the weaving predicate is the only alternate multiplier gate in this method.

### Sweet berry bush stuck speed

`net/minecraft/world/level/block/SweetBerryBushBlock.java:82-105` writes `(0.8F, 0.75, 0.8F)` through `makeStuckInBlock` exactly for `LivingEntity` instances other than foxes and bees; bush age does not gate this write. The server-only age and movement checks after it can cause damage and are excluded here.

### Shared producer, consumer, and tick order

`net/minecraft/world/entity/Entity.java:2876-2879` implements `makeStuckInBlock` as `resetFallDistance()` followed by storing `stuckSpeedMultiplier`. `Entity.java:721-728` consumes a non-negligible multiplier at the beginning of the next `move`: for non-piston moves it componentwise multiplies the requested delta, then clears the multiplier and zeroes delta movement. A normal local-player travel move uses `MoverType.SELF` (for example, `LivingEntity.java:2430-2435`, `2484-2485`, and `2623-2627`).

For local simulation, `LivingEntity.java:3072-3078` performs travel before `applyEffectsFromBlocks()`. `Entity.java:839-850` drains the movement list into block effects; `Entity.java:1195-1218` visits those movements in order; and `Entity.java:1252-1270` dispatches `state.entityInside(...)` on block intersection before `onInsideBlock`. Thus contact writes the honey velocity or the cobweb/berry multiplier after the travel move that reached the block; a stored stuck multiplier is consumed by the next applicable move. This audit assumes equal movement path, block states/geometry, contact-query result/order, and effect predicate inputs on both sides; those query values are dependencies, not claimed differences.

## Exclusions and limit

No finding is claimed for honey sound/particles/achievement, berry damage or growth, non-player or riding behavior, portal/fluid interactions, block geometry/query implementation, or unrelated movement. This is an independently frozen bounded source verdict only.