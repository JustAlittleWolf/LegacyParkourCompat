# Independent blind source audit: local pitch update, 1.13 → 1.13.1

## Verdict

**No source difference in the audited pitch update; this pair does not support a pitch-setter finding.** In both canonical Feather trees, the method corresponding to ordinary local camera turning is named `Entity.updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)` (the requested `Entity.turn` label is not its Feather source name). The field widths, arithmetic, casts, write order, clamp overload and clamp order are identical. The finite overrange witness below confirms the path and its current-versus-previous behavior; it does not establish a version delta.

## Exact source evidence

- 1.13, Ornithe Feather (`net.ornithemc:feather-gen2:1.13+build.2`): `net/minecraft/entity/Entity.java`, fields at 118–121 and `updateLocalPlayerCamera` at 318–330; `net/minecraft/util/math/MathHelper.java`, float `clamp` at 89–95.
- 1.13.1, Ornithe Feather (`net.ornithemc:feather-gen2:1.13.1+build.2`): same relative files, fields at 118–121 and `updateLocalPlayerCamera` at 318–330; float `clamp` at 89–95.
- The camera method calls use the `float` `MathHelper.clamp(float, float, float)` overload. For each release, the local mouse handler calls `minecraft.player.updateLocalPlayerCamera(j, k * n)` when a player exists (`net/minecraft/client/MouseHandler.java:208–212`).

Both releases declare `yaw`, `pitch`, `lastYaw` and `lastPitch` as `float`; camera deltas and `d = cursorDeltaY * 0.15` are `double`. The method's pitch sequence in each release is byte-for-byte the same:

1. Compute the double pitch delta `d`.
2. Write current pitch as `(float)(this.pitch + d)`.
3. Clamp current pitch to `[-90.0F, 90.0F]`.
4. Write previous pitch as `(float)(this.lastPitch + d)`.
5. Clamp previous pitch to the same bounds.

Current yaw is written between steps 2 and 3; previous yaw is written between steps 4 and 5. No release changes these operations or their order. `MathHelper.clamp(float, float, float)` compares against the minimum, then the maximum, returning the bound when exceeded; it is also identical in the two trees.

## Finite witnesses and movement reachability

With finite state `pitch = 80.0F`, `lastPitch = -80.0F` and finite `cursorDeltaY = 1000.0`, both releases compute `d = 150.0`. The current addition produces `230.0F` and clamps to `90.0F`; the previous addition produces `70.0F` and stays `70.0F`. This demonstrates independent current-then-previous clamping. An extreme but still finite `cursorDeltaY = 1.0E40` makes `d` finite in double precision but casts the updated pitch to positive infinity; the float clamp returns `90.0F` in both versions. These are method-level numeric witnesses, not claims about a physical mouse device's maximum event delta.

The movement connection is direct and player-reachable: `PlayerEntity.moveRelative` delegates to `super.moveRelative` when the player is not in creative abilities flight, and `LivingEntity.moveRelative` enters its Elytra branch when local-side movement is allowed, the player is dry (water/lava gates pass), and `isFallFlying()` is true. In that branch the first current-pitch read is `this.getLookVector()` (`LivingEntity.java:1474` in 1.13; `:1493` in 1.13.1). `Entity.getLookVector()` passes current `this.pitch` and `this.yaw` to `getRotationVector` (`Entity.java:1690–1692` / `:1691–1693`); this does not interpolate `lastPitch`. The next direct read converts current pitch to radians (`LivingEntity.java:1475` / `:1494`). Thus the clamp can feed actual dry, active, nonpassenger Elytra movement, but the audited update/read behavior is unchanged across this pair.

## Readiness and immutable source identity

Canonical trees audited read-only: `decompiled_minecraft/1.13/ornithe-feather/` and `decompiled_minecraft/1.13.1/ornithe-feather/`. Both ready markers report `status: ready`, exact requested version and `mapping: ornithe-feather`. Their source-manifest SHA-256s match the ready markers; a read-only verification checked every manifest entry against canonical file bytes and matched the declared file counts and total source bytes (1.13: 2,702 files / 14,688,263 bytes; 1.13.1: 2,710 files / 14,768,136 bytes). The paired provenance files hash to the ready-record values and identify run `prep-1.13-1.13.1-feather-2026-10-09-r1`.

| Version | Ready source-manifest SHA-256 | Provenance SHA-256 | Relied source path | Source SHA-256 |
|---|---|---|---|---|
| 1.13 | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13 | same | same | `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| 1.13 | same | same | `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 1.13 | same | same | `net/minecraft/client/MouseHandler.java` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |
| 1.13 | same | same | `net/minecraft/util/math/MathHelper.java` | `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f` |
| 1.13.1 | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.1 | same | same | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.1 | same | same | `net/minecraft/entity/living/player/PlayerEntity.java` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| 1.13.1 | same | same | `net/minecraft/client/MouseHandler.java` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` |
| 1.13.1 | same | same | `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |

Audit is limited to the requested local pitch setter, its finite overrange/clamp semantics, and the first actual dry active Elytra movement read. It does not claim full pair coverage or audit later Elytra equations.

## Phase-two comparison (source report fixed by SHA-256)

Compared the independently frozen source report `c-raw-pitch-setter-113-1131-source/report.md` after confirming its bytes hash to `156e72afc3e6d54988bbbdd65f32ea17c41cbd0b4cae89684ad1599e95029ba1`. Its central disposition agrees with this audit: the Feather `updateLocalPlayerCamera` pitch writes and clamps are identical in 1.13 and 1.13.1, and no source finding is supported. Its alternate finite witness (`pitch = 89.0F`, `lastPitch = 88.0F`, `cursorDeltaY = 10.0`) also checks out: `d = 1.5`, current pitch clamps from `90.5F` to `90.0F`, and previous pitch becomes `89.5F`. I independently verified the report's claim that the complete `LivingEntity.moveRelative(float, float, float)` bodies are identical (192 lines in each canonical source).

One precision point for the assigned **first** Elytra pitch read: the report identifies the later direct expression `float j = this.pitch * (float)(Math.PI / 180.0)` (1.13: line 1475; 1.13.1: line 1494). The branch first calls `this.getLookVector()` (1.13: line 1474; 1.13.1: line 1493), which reads current `pitch` earlier because `Entity.getLookVector()` delegates to `getRotationVector(this.pitch, this.yaw)` (1.13: `Entity.java:1690–1692`; 1.13.1: `:1691–1693`). Both are real current-pitch movement reads; the latter is the first field read in that active dry fall-flying branch. This distinction does not change the no-difference verdict.