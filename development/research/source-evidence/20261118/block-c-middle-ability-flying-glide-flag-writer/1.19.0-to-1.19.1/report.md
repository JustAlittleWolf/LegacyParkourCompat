# 1.19 to 1.19.1: ability flight during Elytra glide

## Result

No source difference in the assigned operation. In both exact Mojmap releases, a local player who already has glide flag 7 set and starts ordinary ability flight while airborne, dry, and not riding still executes the Elytra travel branch for that movement. The player-level ability-flight path performs a real `super.travel` first and then directly clears shared flag 7. `resetFallDistance()` is a separate preceding state write; it is not the glide clear.

This is a bounded no-difference comparison, not a finding of changed behavior or a full-pair coverage claim.

## Reachability and operation

`LocalPlayer.aiStep` allows a player with `mayfly` to toggle `abilities.flying` on the jump-toggle path (or enables it for always-flying game modes), then records `wasFallFlying`. The Elytra-start path is separately guarded by `!abilities.flying`; it is irrelevant to this case because glide is already active. The scenario is reachable for a creative player with mayfly, ordinary chest-slot Elytra, and flag 7 already true. Sources: 1.19 `LocalPlayer.java:805-831`; 1.19.1 `LocalPlayer.java:811-837`.

On the local player's movement tick, `Player.travel` takes `abilities.flying && !isPassenger()`, saves the existing vertical velocity and flying speed, invokes `super.travel`, restores the prior flying speed, then does `resetFallDistance()` followed by `setSharedFlag(7, false)`. With flag 7 set and no fluid branch selected (ordinary dry air), `LivingEntity.travel` executes Elytra flight math and calls `move(MoverType.SELF, ...)` before returning. Thus the direct player-level clear is post-move. Its operation order is the same in both versions. Sources: 1.19 `Player.java:1459-1489`, `LivingEntity.java:2042-2136`; 1.19.1 `Player.java:1464-1494`, `LivingEntity.java:2042-2136`.

The separate `LivingEntity.travel` fall-flying clear is gated by `onGround && !level.isClientSide`, so it does not clear the local client's flag. `Entity.move` updates `onGround` and performs fall-distance accumulation/checking as part of real movement; neither release changes this path in the compared slice. Sources: 1.19 and 1.19.1 `LivingEntity.java:2135-2149`, `Entity.java:566-605`.

## Exact input identity and readiness

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`; both versions use `mojmap` (official Mojang mappings). No generation or online mapping lookup was performed.

- 1.19 ready marker `decompiled_minecraft/1.19/mojmap.ready.json`, SHA-256 `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88`; status `ready`, exact `versionId` `1.19`, mapping `mojmap`, Vineflower, Java 25. Source-manifest SHA-256 `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`; provenance JSON SHA-256 `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a`.
- 1.19.1 ready marker `decompiled_minecraft/1.19.1/mojmap.ready.json`, SHA-256 `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690`; status `ready`, exact `versionId` `1.19.1`, mapping `mojmap`, Vineflower, Java 25. Source-manifest SHA-256 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`; provenance JSON SHA-256 `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245`.

The provenance records identify exact Mojang client and official client-mapping artifacts, both SHA-256-pinned, and Vineflower 1.12.0. Their source manifests are the immutable source-set identities above. Readiness markers and provenance were checked in place; compared source files were read-only.

## Compared slices and file SHA-256

The tick/input/player travel and entity movement slices were directly compared; after aligning the shifted LocalPlayer and Player line numbers, each listed bounded body is byte-for-byte equal as decoded source text.

| Release | Relative source file | Compared members / lines | File SHA-256 |
|---|---|---|---|
| 1.19 | `net/minecraft/client/player/LocalPlayer.java` | `aiStep`: 725-829 | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.19.1 | `net/minecraft/client/player/LocalPlayer.java` | `aiStep`: 731-835 | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19 | `net/minecraft/world/entity/player/Player.java` | `travel`: 1459-1489 | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` |
| 1.19.1 | `net/minecraft/world/entity/player/Player.java` | `travel`: 1464-1494 | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| 1.19 | `net/minecraft/world/entity/LivingEntity.java` | `travel`: 2042-2149 (includes fall-flying branch) | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` |
| 1.19.1 | `net/minecraft/world/entity/LivingEntity.java` | `travel`: 2042-2149 (includes fall-flying branch) | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19 | `net/minecraft/world/entity/Entity.java` | `move`: 548-605 | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| 1.19.1 | `net/minecraft/world/entity/Entity.java` | `move`: 548-605 | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |


