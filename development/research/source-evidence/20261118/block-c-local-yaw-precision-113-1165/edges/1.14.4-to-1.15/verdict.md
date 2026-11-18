# Blind source audit: direct local yaw and dry ground/air use

## Verdict

**No source-confirmed yaw-specific movement difference** was found between exact 1.14.4 and 1.15 Mojmap sources in the assigned slice. `Entity.turn(double, double)` keeps the same parameter widths, expression order, float casts, current/previous yaw writes, pitch-only clamps and absence of a finite-value gate. The ordinary local-player dry ground/air travel path still passes the current `yRot` through `Entity.moveRelative` into the same input-vector trigonometry. The helper's access changes from `protected static` to `private static`, but the standard `LocalPlayer` path calls the public `moveRelative` method and has no movement behavior change from that visibility edit.

This is a bounded no-difference verdict for ordinary local yaw input and its first dry ground/air yaw use; it does not accept or claim full-pair coverage.

## Exact source identity and integrity

- **1.14.4 Mojmap:** `decompiled_minecraft/1.14.4/mojmap/`. `mojmap.ready.json` says `status=ready`, `versionId=1.14.4`, `mapping=mojmap`, source-manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`, client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. `mojmap.verification.json` reports `verified-identical-source-and-input-artifacts`, 3,250 source files verified, zero file differences, and the same published/regenerated source-manifest hash.
- **1.15 Mojmap:** `decompiled_minecraft/1.15/mojmap/`. `mojmap.ready.json` says `status=ready`, `versionId=1.15`, `mapping=mojmap`, source-manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`, client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`. Its provenance identifies the exact `1.15` source set in run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`.

Each hash below was independently recomputed from the cited file and matches the entry in that release's `mojmap.sources.sha256` manifest:

| Source | 1.14.4 SHA-256 | 1.15 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/client/MouseHandler.java` | `e0fea181777732b1850c16d2fe7f757dd21170269229ed5c727164bd2ce03b3a` | `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b` |
| `net/minecraft/util/SmoothDouble.java` | `3563a46989bd6d0522f673d43f4d6a7264ebb7a611d29c7b7172e2d96e08f913` | `3563a46989bd6d0522f673d43f4d6a7264ebb7a611d29c7b7172e2d96e08f913` |

## Compared behavior

- **`Entity.turn`:** 1.14.4 `Entity.java:319–331`; 1.15 `Entity.java:326–338`. Both accept two `double`s; compute `f = e * 0.15` then `g = d * 0.15`; update current pitch and yaw via `(float)(current + delta)`; clamp pitch only; then update previous pitch and yaw from their previous values using the same deltas and clamp previous pitch only. There is no yaw normalization and no explicit `Double.isFinite`/finite gate. Vehicle notification remains after these writes.
- **Ordinary mouse-to-player call:** 1.14.4 `MouseHandler.java:187–223`; 1.15 `MouseHandler.java:192–228`. Both gate on grabbed mouse plus active window, calculate the same sensitivity factor, use the same smooth/non-smooth branches, reset accumulated deltas, apply integer `invertYMouse`, and call `player.turn(j, k * n)` only when the player is non-null. `SmoothDouble.java:8–19` is byte-for-byte identical by SHA-256. This path has no explicit finite-value filter before `Entity.turn`.
- **First dry ground/air yaw movement use:** `LocalPlayer.aiStep` calls `super.aiStep` (1.14.4 `LocalPlayer.java:772`; 1.15 `LocalPlayer.java:778`). The inherited `LivingEntity.aiStep` calls `this.travel(new Vec3(this.xxa, this.yya, this.zza))` (1.14.4 `LivingEntity.java:2223`; 1.15 `LivingEntity.java:2287`). In the dry, non-fall-flying branch, both releases call `this.moveRelative(this.getFrictionInfluencedSpeed(v), vec3)` (1.14.4 `LivingEntity.java:1836–1842`; 1.15 `LivingEntity.java:1885–1891`). Both `Entity.moveRelative` methods pass `this.yRot` to `getInputVector`, then add the resulting vector to delta movement (1.14.4 `Entity.java:1080–1083`; 1.15 `Entity.java:1063–1066`). The helper's input threshold, normalization condition, trig argument casts, sine/cosine calls and component expressions match exactly at 1.14.4 `Entity.java:1085–1095` and 1.15 `Entity.java:1068–1078`.

## Adjacent dependency to keep separate

The surrounding dry travel branch does change its supporting-block position source: 1.14.4 constructs `BlockPos(this.x, this.getBoundingBox().minY - 1.0, this.z)` (`LivingEntity.java:1837`), while 1.15 calls `getBlockPosBelowThatAffectsMyMovement()` (`LivingEntity.java:1886`). Both then obtain block friction and pass it into `getFrictionInfluencedSpeed(v)`. This is a separate friction/speed dependency, not a change to yaw reading or yaw-to-input trigonometry; it was not adjudicated here.
## Phase-two comparison against frozen source report

Compared against `c-yaw-precision-1144-115-source/report.md`, whose SHA-256 is `fe5e8a7ed89de472f15f676231f3a39130dc2aed822f0d41ecde9ccd59a5f621` (matches the supplied pin). I independently rechecked the exact turn bodies, mouse caller, helper, ordinary `LivingEntity.travel` route, source manifests and Mth lookup expressions.

The report's bounded no-yaw-difference conclusion is independently supported. The two `Entity.turn` method bodies are text-identical: both use the same double arguments and deltas, the same current/previous yaw writes and pitch-only clamps, and no finite gate. The direct mouse caller has no explicit finite filter. The first dry non-fall-flying `moveRelative` use still passes current `yRot` to the unchanged input-vector math; the only helper change is visibility (`protected static` to `private static`). `Mth.sin` and `Mth.cos` expressions are identical at `Mth.java:25–31` in both releases, and the report's two Mth file hashes match their source-manifest entries. The separate supporting-block lookup change in dry travel remains outside the yaw verdict.

One integrity correction: the frozen report's 1.14.4 `LivingEntity.java` row has a SHA-256 typo. It prints `428762178a876efd4069086b6b7d51f571ea0401f44e7eff0927b7d26d9ef681`; the actual recomputed file hash and `mojmap.sources.sha256` entry are `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`. This typo does not change the inspected travel lines or the yaw verdict. No other compared claim changed my conclusion.