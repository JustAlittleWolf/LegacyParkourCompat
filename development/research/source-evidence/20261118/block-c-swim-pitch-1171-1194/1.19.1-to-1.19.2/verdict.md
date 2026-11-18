# Independent blind source audit: 1.19.1 → 1.19.2 swim-pitch vertical writer

## Frozen verdict

**No source difference in the scoped operation.** The ordinary nonpassenger player's swim-pitch vertical-velocity writer, its raw pitch/look-vector input, the sampled-above fluid gate, and its inherited movement path are byte-identical in the canonical 1.19.1 and 1.19.2 Mojmap source trees. This pair does not support a 1.19.1→1.19.2 movement delta for this operation.

This is a bounded operation audit, not a complete pair coverage audit and not runtime evidence.

## Exact source identity and readiness

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- 1.19.1 mapping: `mojmap`; provenance identifies exact version metadata `1.19.1`, Mojang official client mappings, Vineflower 1.12.0, and a successful exact run. `mojmap.ready.json` says `status: ready`, 4,480 source files / 21,921,512 source bytes, source-manifest SHA-256 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`, artifact-manifest SHA-256 `aae6327967e07f5cdd92ac9bfa6d9c606e1be9a96e24dd30acad07fd2425df41`, diagnostics SHA-256 `afd25c98efb5dbf3ae434f5c7be063a176f28c2ae42d37ec60db9979d5301163`, client SHA-256 `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52`.
- 1.19.2 mapping: `mojmap`; provenance identifies exact version metadata `1.19.2`, Mojang official client mappings, Vineflower 1.12.0, and a successful exact run. `mojmap.ready.json` says `status: ready`, 4,480 source files / 21,921,795 source bytes, source-manifest SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`, artifact-manifest SHA-256 `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`, diagnostics SHA-256 `ce308a5f2902c3d7f7c5e330935d130e5ce56302d03b7fa687bf14493400e44e`, client SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`.

The on-disk SHA-256 of each `mojmap.sources.sha256` and `artifacts.sha256` matches the corresponding ready marker and provenance value. Both provenance records name `mojmap.ready.json`; the ready markers name their canonical source and artifact manifests. The five scoped Java source files below were hashed from their canonical bytes, and their digest is identical across versions:

| Mojmap source file | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| `net/minecraft/util/Mth.java` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` |
| `net/minecraft/core/BlockPos.java` | `f520f3056b16c033f28db64171471aa165537fc8a1504278a24a4760b3fcf0ec` |

Source-manifest entries for Player, LivingEntity, Entity and Mth reproduce these file hashes on both sides. The source-manifest whole-file SHA differs between releases as expected because unrelated source files differ.

## Technical trace

All line references below are in both exact Mojmap trees; the corresponding source files have the equal SHA-256 values above.

1. **Player override and writer — `world/entity/player/Player.java:1464–1494`.** `Player.travel(Vec3)` saves starting x/y/z, then enters the writer only for `isSwimming() && !isPassenger()` (1468). It reads `double lookY = getLookAngle().y` (1469), chooses `0.085` if `lookY < -0.2`, otherwise `0.06` (1470), then writes only when `lookY <= 0.0 || jumping || !fluidState(sample).isEmpty()` (1471–1473). The sample expression is exactly `new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())`: additions/subtraction and the block lookup occur in that order. `BlockPos(double,double,double)` delegates to `Vec3i(double,double,double)`, which applies `Mth.floor` independently to x, y, z (`BlockPos.java:46–48`; `Vec3i.java:39–41`). If enabled, it obtains the existing velocity and calls `setDeltaMovement(old.add(0.0, (lookY - old.y) * factor, 0.0))` (1474–1475). The result is a delta-velocity adjustment; it is not a direct position write. There is no pitch cast in this writer.
2. **Raw pitch to look-vector Y — `world/entity/Entity.java:155–158`, `1322–1342`, `1841–1843`, `3055–3065`.** `xRot` is a `float`; `getXRot()` returns that field directly. `Player` does not override `getLookAngle`; inherited `Entity.getLookAngle()` passes raw `getXRot()` and `getYRot()` to `calculateViewVector`, with no old-rotation interpolation. The method multiplies each float rotation by `(float)(Math.PI / 180.0)`, negates the yaw radians, calls float `Mth.cos/sin` in order, and returns `new Vec3(yawSin * pitchCos, -pitchSin, yawCos * pitchCos)`. Thus this writer's lookY is the double-valued `Vec3.y` formed from the negated float LUT sine of the raw pitch radians.
3. **Float LUT/casts/mask — `util/Mth.java:28–32`, `44–50`.** `SIN` has 65,536 floats, initialized as `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `sin(float)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`; the float product is cast to int (truncating toward zero under Java conversion) and then masked with `65535`. `cos(float)` uses the same float scale and cast/mask after adding `16384.0F`. The float operations, cast placement and bit mask are exactly equal in both releases.
4. **Inherited real movement — `world/entity/LivingEntity.java:2042–2086`, `Player.java:1479–1493`, `world/entity/Entity.java:547–679`.** For an ordinary local player, `Player.travel` subsequently invokes `super.travel` (unless flying); the local/AI gate in `LivingEntity.travel` is `isEffectiveAi() || isControlledByLocalInstance()` (2043). In the water-and-fluid-affected, non-standable-on-fluid branch it applies relative input then calls `move(MoverType.SELF, getDeltaMovement())` (2051–2075), so the preceding Player Y-velocity write is the input to actual movement. It then applies water drag and fluid-falling adjustment (2076–2086). `Entity.move` handles collision and position advancement: no-physics adds the requested vector directly (548–550); otherwise it piston-limits when relevant, applies stuck/edge adjustment, collides, and changes position by the collided vector only when nonzero (552–579), before collision flags and later responses (581 onward). In the flying nonpassenger branch, Player saves the pre-super Y velocity and after `super.travel` replaces Y with that saved value times `0.6` (1479–1488); the swim writer still executes first but its Y result does not survive that later replacement. This ordering is unchanged across the pair.

## Exact source citations

- 1.19.1 Mojmap: `decompiled_minecraft/1.19.1/mojmap/net/minecraft/world/entity/player/Player.java:1464–1494`; `.../world/entity/Entity.java:1322–1342,1841–1843,3055–3065,547–679`; `.../world/entity/LivingEntity.java:2042–2086`; `.../util/Mth.java:28–50`; `.../core/BlockPos.java:46–48`; `.../core/Vec3i.java:39–41`.
- 1.19.2 Mojmap: the same relative files and line ranges under `decompiled_minecraft/1.19.2/mojmap/`; each relevant byte stream is identical to 1.19.1 as shown by the SHA-256 table.

Frozen before opening the assigned author artifact. Independent claim: **no difference for this operation in this exact pair**.


## Post-freeze comparison

The independent source trace was frozen before opening the assigned author report. Pre-comparison frozen verdict SHA-256: 24745edb1460b03831022fb1576990523d43e80098a643512a253345e5d01249. The author report at C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-swim-pitch-vertical-1191-1192-source/report.md hashes to its assigned SHA-256 d04f0879d3ffeab30c39a700eb31fac811535f8690e2d526baf0519c3779e261. Its bounded no-difference conclusion agrees with this independently derived verdict. The correction above changes only the file-count wording, not the source trace or verdict.

