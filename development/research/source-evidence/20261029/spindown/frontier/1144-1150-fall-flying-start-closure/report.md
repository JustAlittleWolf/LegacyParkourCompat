# 1.14.4 to 1.15 fall-flying start closure

## Evidence identity

Published exact trees were retraced read-only at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15\mojmap`. Both are Mojmap source trees. The release-parent sidecars were checked directly.

- 1.14.4: ready marker says 3,250 Java files / 17,080,207 bytes, client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; `mojmap.sources.sha256` is `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. The source manifest recomputed to this hash and all 3,250 entries matched current files with no missing/mismatched entries. `mojmap.verification.json` records 3,250 verified sources, zero source differences, identical regenerated/published source manifest, and zero raw input artifact differences. The artifacts sidecar pins `1.14.4/client_mappings.txt` at SHA-256 `2dd53a5e70ba493cf6e33c0fc52bbdf4c57f9429c7a13842565aa825fd44d910`. Provenance records Mojmap output, command `--versions=1.14.4 --mappings=feather,mojmap`, Vineflower 1.12.0 and shared tool versions; its `mappingArtifacts` field names Feather artifacts because this was a dual run.
- 1.15: ready marker says 3,329 Java files / 17,522,461 bytes, exact metadata ID 1.15, client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`; `mojmap.sources.sha256` is `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`. The source manifest recomputed to this hash and all 3,329 entries matched current files with no missing/mismatched entries. Provenance identifies official client mappings by SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a` (SHA-1 `580c412cfa5869318a3a02d6b018a83781bf3ae7`), official metadata URL/hash, Mojmap, Vineflower 1.12.0 and shared tool versions. Its ready marker and provenance agree on source identity; no separate 1.15 verification JSON is present.

Key source SHA-256 values:

| Mojmap source | SHA-256 |
|---|---|
| 1.14.4 `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.14.4 `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| 1.14.4 `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `912f528c03da34fa703646b6383c2b49ac3493efd7ba78cb8ff27aa7f693b67d` |
| 1.15 `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15 `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| 1.15 `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| 1.15 `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |

## Compared path and findings

### Local request producer and immediate client state

1.14.4 Mojmap, `net/minecraft/client/player/LocalPlayer.java`, `aiStep()` lines 625–630, 707–714: it snapshots prior `input.jumping` into `bl`, ticks input, and later sends `START_FALL_FLYING` only when `input.jumping && !bl && !onGround && deltaMovement.y < 0.0 && !isFallFlying() && !abilities.flying`, chest item is `Items.ELYTRA`, and `ElytraItem.isFlyEnabled(stack)` is true. It does not write the fall-flying flag on this local path. The request is made before `super.aiStep()` at line 772.

1.15 Mojmap, same file, `aiStep()` lines 632–635, 695–722, 778: it snapshots prior jump state, tracks a local `bl8` when the mayfly toggle updates abilities, and requests fall flying only when `input.jumping && !bl8 && !bl && !abilities.flying && !isPassenger()`, the chest stack passes the same Elytra identity/enabled predicate, and `tryToStartFallFlying()` returns true. There is no local descending-velocity predicate. This call occurs before `super.aiStep()` at line 778.

1.15 `net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying()` lines 1586–1596, tests `!onGround && !isFallFlying() && !isInWater()`, then repeats the chest-slot `Items.ELYTRA` and `ElytraItem.isFlyEnabled` check; success calls `startFallFlying()` and returns true. `startFallFlying()` lines 1598–1600 sets shared entity flag 7 true. Thus the local path both requests the packet and writes local fall-flying state before the parent `Player.aiStep()` delegates to `LivingEntity.aiStep()` / travel. 1.14.4 `Player.java` has `startFallFlying()` at line 1276 but no `tryToStartFallFlying()`; the client call site is the packet producer above.

**Gate/order delta:** the 1.15 local success path no longer waits for a descending Y velocity. It adds a passenger exclusion and an explicit not-in-water check (the latter inside the shared Player helper), and suppresses the elytra start attempt on a tick where the mayfly toggle just changed (`!bl8`). On success, the local flag is set before parent movement processing. In 1.14.4, the local gate requires `deltaMovement.y < 0.0`, has no passenger rejection, and only transmits a request; local flag change follows server acceptance/replication. For a rising or level airborne local player, the 1.15 gate can succeed if all other predicates pass, where the 1.14.4 local producer does not send a request. This is only a source-level path consequence; no trajectory is inferred.

The item identity and `ElytraItem.isFlyEnabled` predicates appear in both local producers and both server validation paths. Their implementation is outside this bounded report; this comparison establishes the same invoked predicate and order after the release-specific surrounding gates.

### Server packet consumer (eligibility, not client movement)

1.14.4 `net/minecraft/server/network/ServerGamePacketListenerImpl.java`, `handlePlayerCommand` case `START_FALL_FLYING` lines 1122–1128: server requires `!onGround`, `deltaMovement.y < 0.0`, `!isFallFlying()`, `!isInWater()`, plus Elytra item/enabled checks, then calls `startFallFlying()`.

1.15 same member, lines 1137–1140: invokes `player.tryToStartFallFlying()`; if false, calls `player.stopFallFlying()`. The helper’s server-side criteria omit the negative-Y check, mirroring the new player-level shared gate. The server packet handler is eligibility/state enforcement and is distinct from the local client prediction described above. It does not supply historical server physics.

### Inherited validity update, pose and equipment gates

`LivingEntity.aiStep()` calls `updateFallFlying()` immediately before `travel(...)` in both releases (1.14.4 lines 2217–2223; 1.15 lines 2282–2287). `updateFallFlying()` has the same conditions/order in both: start from shared flag 7; if flagged, require `!onGround && !isPassenger()`, chest Elytra identity and `ElytraItem.isFlyEnabled`; clear the local candidate on any failed check; on server only, damage Elytra when `(fallFlyTicks + 1) % 20 == 0`; finally, write flag 7 only on the logical server. The local client therefore does not clear a just-started flag in this inherited updater. These validity/equipment checks show no start-gate delta in the compared bodies.

Player pose selection also remains structurally the same: `Player.updatePlayerPose()` lines 344–369 (1.14.4) and 357–382 (1.15) is gated by `canEnterPose(Pose.SWIMMING)`; it prioritizes `Pose.FALL_FLYING` when `isFallFlying()`, then permits the selected pose if spectator, passenger, or `canEnterPose(pose)`, else falls back to crouching/sneaking or swimming. The rename `SNEAKING` → `CROUCHING` and changed 1.15 local passenger gate are visible, but the pose selection itself does not add a new fall-flying rule. Collision-based `canEnterPose` dependencies were not reopened here.

## Bounded disposition

Supported, reviewable candidate: 1.15 adds a player-level `tryToStartFallFlying()` used by both local input and server packet handling; for the local input path it replaces the 1.14.4 local descent-only request gate with an immediate shared-flag write under `!onGround`, `!isFallFlying`, `!isInWater`, Elytra, and enabled predicates, while adding passenger and mayfly-toggle guards at the caller. The inherited `LivingEntity.updateFallFlying()` validity gate and the Player pose selection do not show a material change in this slice.

This report does not close the full 1.14.4→1.15 movement pair. It does not trace external shared-flag network arrival beyond the local player callback, complete pose collision dependencies, or downstream Elytra travel math. It makes no trajectory claim and does not assign an intermediate patch boundary.
