# 1.9.2 → 1.9.3: creative flight and a live glide flag

## Scope and result

Compared only the ordinary player `abilities.flying` branch in `PlayerEntity.moveRelative`, its call through `LivingEntity.travel`, the source ownership gate, and the `PlayerEntity`/`PlayerAbilities` NBT methods relevant to glide/flying state normalization. Both sides are exact local Ornithe Feather sources. No implementation, wiki, product, Git, runtime, or broader saved-state review was used.

**Result: no difference in the requested movement operation.** In both versions, when `abilities.flying` is true and the player is not riding, `PlayerEntity.moveRelative` calls `super.moveRelative(...)`, then resets `fallDistance` to `0.0F` and directly calls `setFlag(7, false)`, in that order. Thus a flag-7 glide state already live on entry is cleared by this player override after the superclass movement call, independently of whether `fallDistance` was nonzero. These are separate writes and should not be treated as one state normalization.

This is an ordinary player-level method, not client-only: it is on shared `PlayerEntity`, inherited by local and server player classes. The actual call is virtual `this.moveRelative(this.sidewaysSpeed, this.forwardSpeed)` from `LivingEntity.travel`; the superclass movement call is gated by `isLocallyControlled() || isLogicalSideForUpdatingMovement()`. The local client subclass supplies the local-player path. This source comparison does not make historical movement server-authoritative; it only identifies the vanilla call/ownership in these releases.

The PlayerEntity custom-NBT methods in both releases delegate to `super`, then read/write player fields and `abilities`; their own bodies do not read, write, or normalize flag 7. The called `PlayerAbilities.readNbt` also loads `flying` and `mayfly` independently, with no normalization between them in either release. Thus the examined NBT path does not normalize glide flag 7 or reconcile `flying` against `canFly`. This is a narrow note about these methods, not a conclusion about broader inherited entity save behavior. `fallDistance = 0.0F` is in the movement method, not in these NBT methods.

## Exact operation

| Step | 1.9.2 and 1.9.3, `PlayerEntity.moveRelative` |
|---|---|
| 1 | Save `x`, `y`, `z`. |
| 2 | Test `abilities.flying && !isRiding()`. |
| 3 | On the true branch, save `velocityY` and `speedInAir`. |
| 4 | Set `speedInAir = abilities.getFlySpeed() * (isSprinting() ? 2 : 1)`. |
| 5 | Call `super.moveRelative(sideways, forwards)`. |
| 6 | Set `velocityY = savedVelocityY * 0.6`. |
| 7 | Restore saved `speedInAir`. |
| 8 | Set `fallDistance = 0.0F`. |
| 9 | Set glide flag 7 false via `setFlag(7, false)`. |
| 10 | After either branch, call `tickNonRidingMovementRelatedStats(xDelta, yDelta, zDelta)`. |

The overlap is conditional on the stated entry values and not on a particular tick-phase or timing guarantee. If `abilities.flying` is false or the player is riding, this override's true branch and its two resets do not run.

## Exact source evidence

Mapping family: **Ornithe Feather** (local generated source under `decompiled_minecraft/<version>/ornithe-feather/`). The provenance manifests identify the following exact Feather builds and raw input pins:

- 1.9.2: `feather-gen2 1.9.2+build.2`, merged-v2 artifact SHA-256 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; Feather metadata SHA-256 `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`; client SHA-256 `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`.
- 1.9.3: `feather-gen2 1.9.3+build.2`, merged-v2 artifact SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; Feather metadata SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`; client SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`.

Source hashes below were independently checked against each tree's `.decompile-provenance.json` `sources` entry and match.

| Version | Source and member | Lines | Source SHA-256 |
|---|---|---:|---|
| 1.9.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float)` | 1365–1384 | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` |
| 1.9.3 | `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float)` | 1371–1390 | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| 1.9.2 | `net/minecraft/entity/living/LivingEntity.java`, `travel` call site | 1698–1706 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| 1.9.3 | `net/minecraft/entity/living/LivingEntity.java`, `travel` call site | 1707–1715 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.9.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, local flight input/tick context | 704–756 | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| 1.9.3 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, local flight input/tick context | 707–759 | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| 1.9.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, `readCustomNbt` / `writeCustomNbt` | 676–734 | same PlayerEntity source hash above |
| 1.9.3 | `net/minecraft/entity/living/player/PlayerEntity.java`, `readCustomNbt` / `writeCustomNbt` | 681–739 | same PlayerEntity source hash above |
| 1.9.2 | `net/minecraft/entity/living/player/PlayerAbilities.java`, `readNbt` / `writeNbt` | 14–41 | `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0` |
| 1.9.3 | `net/minecraft/entity/living/player/PlayerAbilities.java`, `readNbt` / `writeNbt` | 14–41 | same source hash (unchanged file) |

The local subclasses expose/toggle creative `abilities.flying` during their normal local tick handling. Their tick-input details are included only to anchor the ordinary local-player context; the finding itself does not depend on a specific activation timing.
