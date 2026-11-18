# C source slice: direct local pitch update, 1.13 → 1.13.1

**Disposition:** bounded no-difference coverage; no source finding for this operation.

## Exact source readiness and identity

Both canonical `ornithe-feather` outputs are marked `ready`, with matching exact `versionId` and namespace. Provenance records identify the same exact preparation run `prep-1.13-1.13.1-feather-2026-10-09-r1`, using Feather `1.13+build.2` and `1.13.1+build.2`, Vineflower 1.12.0, Java 25, and Gradle 9.7.1. The ready files' declared provenance, source-manifest, and artifact-manifest SHA-256 values match the actual files.

| Version | ready.json SHA-256 | provenance.json SHA-256 | source manifest SHA-256 | artifact manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.13 | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` |
| 1.13.1 | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |

## Compared operation and finite witness

In `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` is at line 318 in both releases. Its complete 14-line extracted method body is text-identical. It computes `d = cursorDeltaY * 0.15`, adds `d` to current `pitch` and narrows to `float`, clamps current pitch to `[-90, 90]`, then independently adds `d` to `lastPitch`, narrows to `float`, and clamps the previous pitch to the same bounds. The yaw writes and mounted-look callback are also unchanged.

Finite witness for the exact arithmetic: with current `pitch = 89.0F`, `lastPitch = 88.0F`, finite `cursorDeltaY = 10.0`, and any finite horizontal delta, `d` is `1.5`; current pitch becomes `90.5F` then clamps to `90.0F`, while previous pitch becomes and remains `89.5F`. Both releases produce these same writes and clamps.

The ordinary local mouse path calls this method as `minecraft.player.updateLocalPlayerCamera(j, k * n)` in `net/minecraft/client/MouseHandler.java:211` on both sides. The source hash for this call-site file is identical in both versions.

## Next actual Elytra movement read

`net/minecraft/entity/living/LivingEntity.java` has the same complete `moveRelative(float, float, float)` method body in both releases. The camera-updated current pitch is consumed in its fall-flying branch at `float j = this.pitch * (float) (Math.PI / 180.0)` (1.13: line 1475; 1.13.1: line 1494). This follows the `isFallFlying()` check, under the dry-fluid guards (`!isInWater()` and `!isInLava()`, except the player-ability flight allowances), and the locally controlled/logical movement gate at the method start.

This is an actual player movement path: the surrounding travel sequence calls `flyingTick()` and then `moveRelative(...)` (1.13.1: lines 1924–1926). `flyingTick()` retains flag 7 only when the entity is flagged, airborne, not riding, and has a flyable Elytra equipped (lines 1938–1955); `isFallFlying()` reads that flag (line 2314). Thus an active, nonpassenger, dry player can reach the pitch read on the next movement pass. For the witness above it reads current `pitch = 90.0F`; the previous-pitch write is not the value used by this Elytra expression. The entire consumer method, including this branch and its guards, is text-identical across the pair.

## Source hashes for cited files

Hashes below were checked against each version's `ornithe-feather.sources.sha256` entry and the current file bytes.

| Version | `Entity.java` | `LivingEntity.java` | `MouseHandler.java` |
| --- | --- | --- | --- |
| 1.13 | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |
| 1.13.1 | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |

This report covers only the assigned direct local pitch update and its next active dry Elytra consumer. It does not claim pair or tick-graph completion.
