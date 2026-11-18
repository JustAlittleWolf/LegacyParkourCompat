# 1.21 → 1.21.1: player swim-pitch vertical correction

## Result

No source difference in the assigned operation. The exact Mojmap-decompiled `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` files are byte-identical between 1.21 and 1.21.1. The swimming-pitch correction therefore has the same predicate, lookup math, velocity update order, and inherited movement path in both releases. This closes only the requested operation slice; it is not a full pair inventory or independent coverage freeze.

## Exact behavior traced

- `Player.travel(Vec3)`, both releases, `net/minecraft/world/entity/player/Player.java:1456-1480`: first gate is `isSwimming() && !isPassenger()`. It samples `double lookY = getLookAngle().y` once, then selects `0.085` when `lookY < -0.2`, otherwise `0.06`. The update gate is `lookY <= 0.0 || jumping || !fluidAt(BlockPos.containing(x, y + 1.0 - 0.1, z))`; the fluid lookup uses current coordinates and tests `getFluidState().isEmpty()`. If admitted, it snapshots current `Vec3` delta movement, computes `(lookY - oldY) * factor`, adds that only to Y via `old.add(0.0, correction, 0.0)`, then calls `setDeltaMovement`. This occurs before the flying/passenger branch and its `super.travel(input)` call. The formula uses the pre-correction Y velocity; there is no repeated sample or clamp in this block.
- `Entity.getLookAngle()` (`Entity.java:2099-2100`) calls `calculateViewVector(getXRot(), getYRot())`. `getXRot()` returns the raw `xRot` field (`Entity.java:3359-3361`), not an interpolated view rotation. `calculateViewVector` converts pitch and negative yaw to radians using float expressions, then evaluates `cos(yaw), sin(yaw), cos(pitch), sin(pitch)` in that order and returns `(sinYaw*cosPitch, -sinPitch, cosYaw*cosPitch)` (`Entity.java:1530-1538`). Thus the sampled Y component is `-Mth.sin(rawPitch * (float)(PI / 180.0))`; yaw does not affect Y.
- `Mth` initializes a 65,536-element `float[] SIN` (`Mth.java:28,32-36`) with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. `sin(float)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`; `cos(float)` indexes the same table at `(int)(angle * 10430.378F + 16384.0F) & 65535` (`Mth.java:48-54`). The float multiplication/addition is cast to `int` before the bit-mask; the mask wraps indices to 16 bits.
- `super.travel(input)` here dispatches to `LivingEntity.travel(Vec3)`. For locally controlled, fluid-affected entities in water that cannot stand on the fluid, that inherited path adds input via `moveRelative`, calls `move(MoverType.SELF, currentDeltaMovement)`, then applies water drag and fluid-falling adjustment (`LivingEntity.java:2091-2136`). The swim-pitch Y correction has already changed the velocity consumed by that path. `Entity.moveRelative` delegates horizontal input rotation using yaw and adds it to velocity (`Entity.java:1350-1365`); it does not recompute the pitch correction.

## Pair evidence and provenance

- Mapping namespace: Mojmap on both sides. Sources were consumed read-only from `decompiled_minecraft/<version>/mojmap/`.
- 1.21 tree provenance: `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json`, SHA-256 `90246f277aefd2350e42e17245faa51928a949c8db4427f3be04a732527deb7c`. The manifest embeds pipeline/tool/input/source data. No separate 1.21 ready marker or external `*.sources.sha256` manifest is present in this tree.
- 1.21.1 readiness: `decompiled_minecraft/1.21.1/mojmap.ready.json`, SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; identifies version `1.21.1`, mapping `mojmap`, 5,363 source files, client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`, and source-manifest SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.
- 1.21.1 decompilation provenance: `decompiled_minecraft/1.21.1/mojmap.provenance.json`, SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdb2b3c726d710a977758d15b5578ce7a9`; records Mojmap, Vineflower 1.12.0 and Java 25.0.3. Artifact manifest: `decompiled_minecraft/1.21.1/artifacts.sha256`, SHA-256 `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`. External source list: `decompiled_minecraft/1.21.1/mojmap.sources.sha256`.

### Fingerprints of source files actually relied on

SHA-256 is the same for each file on both sides, confirming byte equality:

| Relative source | 1.21 and 1.21.1 SHA-256 |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/util/Mth.java` | `aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb` |

