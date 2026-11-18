# 1.18.0 → 1.18.1 ability flight / glide clear

## Result

No source difference in the assigned operation. The exact Mojmap source files that govern local flight input, the active Elytra travel branch, the player flight override, and the post-move shared-flag write are byte-identical in the 1.18 and 1.18.1 publications. This closes only this bounded operation; it is not a full-pair tick-graph audit.

## Trace and comparison

The 1.18.0 release is published under source directory `decompiled_minecraft/1.18/mojmap/` (`versionId` and `requestedVersionId` are both `1.18`); the newer release is `decompiled_minecraft/1.18.1/mojmap/`.

For a local player with `abilities.mayfly` and `abilities.flying`, airborne, dry, not a passenger, and already carrying shared flag 7, `LocalPlayer.aiStep` reaches `super.aiStep()`; its post-super ability-flight shutdown requires `onGround` (`LocalPlayer.java:725-752, 808-812`). In `LivingEntity.aiStep`, `updateFallFlying()` runs immediately before `travel(...)` (`LivingEntity.java:2546-2548`). On the stated preconditions, the active flag, airborne/non-passenger state and enabled chest Elytra pass its gate (`LivingEntity.java:2586-2610`; `ElytraItem.java:21-23`), so the local client retains flag 7 through the start of travel.

`Player.travel` takes the `abilities.flying && !isPassenger()` branch, saves vertical velocity and flying speed, calls `super.travel`, restores horizontal velocity with vertical velocity multiplied by `0.6`, restores flying speed, then calls `resetFallDistance()` and independently writes `setSharedFlag(7, false)` (`Player.java:1424-1438`). Within `super.travel`, `LivingEntity.travel` sees flag 7 and follows the Elytra branch: it applies glide acceleration/drag, performs the real `move(MoverType.SELF, ...)`, and only clears flag 7 there when `onGround && !level.isClientSide` (`LivingEntity.java:2070-2112`). That latter guard does not clear the flag on the stated airborne client path; the subsequent `Player.travel` write does. `Entity.move` resolves and applies displacement before returning to the two post-move writes (`Entity.java:541-580`). Fall-distance reset and glide-state clear are separate operations, in that order, after the superclass travel returns.

These same gates, operation order, direct flag write, and fall-distance reset exist unchanged in both releases. This is a no-difference result, not an implementation-boundary claim.

## Exact source identities

Both published source sets declare Mojmap, use Vineflower 1.12.0 / Java 25, and have 4,203 source files. Provenance run ID: `prep-1.18-1.18.1-mojmap-2026-10-09-r1`.

| Release path | Release metadata SHA-256 | Official client mapping SHA-256 | Source manifest SHA-256 | Provenance file SHA-256 | Ready file SHA-256 |
|---|---|---|---|---|---|
| `decompiled_minecraft/1.18/` (1.18.0) | `40e076b63d116d21b8eaf7f9d3e09651e4bbc5853cde47edc13b4d5ea0b6631b` | `b4c7d514328050fa09ac04818d21e4be0b58e75c6b0847e56ce09f6637b8877b` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5` |
| `decompiled_minecraft/1.18.1/` | `b854990db7e1948661c0f0359e3b2dbf32610a7a61b4efa4d5471399bcf68fd1` | `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` |

The ready sidecars report `status: ready`; their provenance hashes match the provenance-file hashes above, and each declared source-manifest hash matches the actual manifest file hash. Direct SHA-256 checks matched the manifest entries for every cited source below.

| Mojmap source file (relative to each release's `mojmap/`) | SHA-256 in both releases |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| `net/minecraft/world/item/ElytraItem.java` | `f6859e9d245a2b34c4e2c24af5bf6a2bb956f05ba8eaf3dbef74ba543ea0993c` |

## Bounded coverage

Examined on both sides: local mayfly toggle and Elytra-start suppression/order; the active flag-7 revalidation gate; the dry airborne glide branch through the real move; the server-only grounded clear inside the glide branch; the creative/local player flight override through its post-super writes; and the movement method ordering for the actual displacement. All five corresponding source files are byte-identical, and the operation’s relevant bodies and guards match.

Unexamined: unrelated movement branches, block/collision-provider dependencies outside the ordinary dry airborne path, and the rest of the release-pair movement inventory. No next action is needed for this assigned slice; broader coverage remains open.
