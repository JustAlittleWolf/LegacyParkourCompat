# 1.21.0 / 1.21.1 fall-flying and swimming overlap proof

Status: COMPLETED for the next contiguous exact-version bucket. Frozen source-only addendum; no runtime, test, build or primary research write.

## Exact source authentication and alias

The canonical `decompiled_minecraft/1.21/mojmap/` source tree is exact Minecraft release ID `1.21` (1.21.0). It has no outer ready/manifest sidecars; nested `.decompile-provenance.json` SHA-256 is `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`. Its pinned inputs identify `1.21.json` (SHA-256 `cdd1a44db7b5e8787723c1f6c7c7dc3a4638d96a31ddc84aeb27d3f30efa802a`) and the exact client object URL `.../0e9a07b9bb3390602f977073aa12884a4ce12431/client.jar` (content SHA-256 `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8`). The nested provenance pins each source-file digest.

1.21.1 has outer Mojmap ready/provenance/manifest sidecars. Its provenance/ready marker names exact version `1.21.1`; computed `mojmap.sources.sha256` SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` matches the ready marker. For both versions I recomputed each canonical source hash below. The 1.21 values match nested per-source provenance; the 1.21.1 values match its source manifest; each pair is byte-identical:

| Source | SHA-256, both exact versions |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `cc73bb8fab95346aea10f7018ba7de6c723cde8a52a4ae40629b5370b6ff6d10` |

Thus 1.21.0 and 1.21.1 are digest-equivalent for the complete player selector, swimming and retained-flight state writers, local caller and failed-start packet writer.

## Direct overlap path in the identical source bodies

- `Entity.tick` updates fluid and eye-fluid state, then calls `updateSwimming` (1.21 source lines 440–442). `Entity.updateSwimming` (1232–1240) sets swimming from sprint + underwater + not passenger when entering; if swimming was already true it uses sprint + in-water + not passenger.
- `Player.updateSwimming` (1481–1486) blocks only ability flight; `Player.isSwimming` (1827–1829) filters ability flight and spectator. Neither condition excludes fall-flying. The entry branch additionally needs water at `blockPosition`.
- `LivingEntity.updateFallFlying` (2716–2741) clears shared flag 7 only server-side when the retained flight gate fails: it requires airborne, non-passenger, no Levitation and usable Elytra. Water is not a failure condition. This preserves a dry-started glider while entering water under those conditions.
- `Player.tryToStartFallFlying` (1529–1539) rejects starting in water; that is an entry gate, not a continuation writer. The local player sends `START_FALL_FLYING` only when its start helper returns true (LocalPlayer 741–745).
- LocalPlayer tick calls `super.tick()` before its input path (191–194). `Player.tick` calls `super.tick()` before `updatePlayerPose()` (276, 319); `Entity.tick` refreshes water/swimming state first. Therefore, on the water-entry tick the selector sees the updated swimming flag with retained fall-flying.
- `Player.updatePlayerPose` (401–429) requires fit for `Pose.SWIMMING`, then selects `FALL_FLYING` before `SLEEPING` before `SWIMMING`. It accepts that desired pose for a spectator/passenger or if it fits, otherwise falls back to crouching then swimming. `canPlayerFitWithinBlocksAndEntitiesWhen` (431–433) uses no-collision on the pose bounding box deflated by `1.0E-7`. Both fall-flying and swimming player dimensions are 0.6F × 0.6F and eye height 0.4F (dimensions table 139–144). With sleep excluded, no passenger/spectator/ability flight, no Levitation, sprinting/submerged state, block-position water and admitted fit, the selector writes `FALL_FLYING` for the overlap.
- The server command handler (ServerGamePacketListenerImpl 1432–1436) calls `tryToStartFallFlying` and calls `stopFallFlying` on a failed request. The ordinary water-entry tick does not send a new start command; this remains only a failed/duplicate-start packet caveat.

## Relation to adjacent releases

The frozen 1.21.2–1.21.3 report establishes the immediately following exact bucket with the same state and fit path. The present source proves 1.21.0 and 1.21.1 as an additional contiguous earlier bucket. 1.21.4's accepted selector evidence remains the later endpoint; no release-boundary change in this operation is evidenced across these 1.21 sources.

No runtime world trajectory, network timing, or collision-free fit was tested. Exact gameplay reachability remains conditional on the stated source gates and world geometry.