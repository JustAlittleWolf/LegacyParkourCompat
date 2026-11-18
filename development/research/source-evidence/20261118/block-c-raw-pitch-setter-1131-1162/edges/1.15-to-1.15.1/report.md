# 1.15 → 1.15.1 `Entity.turn` pitch trace

## Bounded result

For this assigned slice, `Entity.turn(double,double)`, its float pitch clamp, the previous-pitch update, and the live Elytra travel consumer are byte-identical in the exact 1.15 and 1.15.1 Mojmap source trees. This pair contributes no source difference for these operations. The result does not claim full-pair tick or inventory coverage.

## Exact source identity

Both roots report exact release IDs 1.15 and 1.15.1, `mojmap`, Vineflower, and the same `prep-1.15-1.15.1-mojmap-2026-10-09-r1` run. The source manifests are pinned by the ready sidecars and independently hash to:

- 1.15 `mojmap.sources.sha256`: `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`.
- 1.15.1 `mojmap.sources.sha256`: `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`.

The two source manifests list identical SHA-256 values for `net/minecraft/world/entity/Entity.java` (`191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`), `LivingEntity.java` (`139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79`), and `net/minecraft/util/Mth.java` (`a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`). Local SHA-256 checks of those six source files agree with the respective manifest entries. Ready-marker and provenance hashes also match the root sidecars: 1.15 ready `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31`, provenance `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`; 1.15.1 ready `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504`, provenance `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461`.

## Setter order, widths, and witnesses

In both versions, `net/minecraft/world/entity/Entity.java`, `Entity.turn`, lines 326–337, computes `f = e * 0.15` and `g = d * 0.15` as doubles. It then assigns current pitch as `(float)(this.xRot + f)`, current yaw as `(float)(this.yRot + g)`, clamps current pitch, assigns previous pitch as `(float)(this.xRotO + f)`, assigns previous yaw as `(float)(this.yRotO + g)`, and clamps previous pitch. Thus the current pitch is narrowed before its first clamp; `xRotO` is updated from its own prior value after both current angles have been assigned, then receives the second clamp. It is not copied from the newly clamped `xRot`. The optional vehicle passenger-turn callback runs only after these updates.

`net/minecraft/util/Mth.java`, lines 98–103, implements the clamp with `<` and `>` comparisons: values below the lower bound return `-90.0F`, values above the upper bound return `90.0F`, and otherwise the input is returned. There is no finiteness check.

An ordinary high finite setter witness is `e = 1000.0`, `xRot = 0.0F`: the double increment is `150.0`, the cast result is finite `150.0F`, and the first clamp yields `90.0F`. This input is supported by the public setter and the unbounded mouse-delta path: `MouseHandler.onMove`, lines 179–185, accumulates raw cursor differences while grabbed; `turnPlayer`, lines 192–223, applies sensitivity, then calls `player.turn(j, k * n)` without a pitch-delta cap. `Options.java:61` declares sensitivity as `double`; with sensitivity `0.5`, the mouse formula's float literals are promoted to double, so its multiplier is just over `1.0`, and a vertical delta of `1000` supplies an `e` just over `1000` when Y is not inverted. The resulting increment is just over `150`; its post-cast float remains finite and well above `90`, so the clamp yields `90.0F`. This is a finite float-after-cast case, not an overflow case.

Keep infinities separate from that ordinary finite witness. If a caller supplies positive infinity for `e`, the multiply, addition, and float narrowing remain positive infinity and the upper comparison returns `90.0F`; negative infinity returns `-90.0F`. The previous-pitch path performs the corresponding second clamp. NaN is not converted by this helper: both comparisons are false and `clamp` returns NaN. These are consequences of source arithmetic and branch ordering, not claims that ordinary mouse hardware emits non-finite values.

## Next active Elytra movement use

In both versions, `net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 1831–1871, enters the movement branch when effective AI or local-instance control is active, the water/lava conditions permit it, and `isFallFlying()` is true. It obtains `vec37 = this.getLookAngle()` and reads the live `this.xRot` again as `float l = this.xRot * (float)(Math.PI / 180.0)` at line 1849. `l` feeds `Mth.cos(l)` at 1853–1854, which changes the vertical-velocity adjustment at 1855, and the `l < 0.0F` dive/lift adjustment at 1861–1863. `Entity.getLookAngle()` at lines 1661–1662 also calls `calculateViewVector(this.xRot, this.yRot)`. This is the next active pitch-sensitive movement consumer; it uses current live pitch, not a partial-tick interpolation.

## Separate callback, render, and network paths

- **Input callback:** In both versions, `MouseHandler.onMove` accumulates cursor deltas only while the mouse is grabbed and invokes `turnPlayer`; `turnPlayer` applies sensitivity/smoothing and Y inversion before calling the current player’s public `turn`. This is the ordinary upstream path above.
- **Rendering:** `Entity.getViewXRot(float)` at lines 1211–1213 returns live `xRot` for partial tick `1.0F`, otherwise interpolates `xRotO` to `xRot`. `Camera.setup` reads `entity.getViewYRot(f)` and `entity.getViewXRot(f)` at `Camera.java:40` and applies those camera rotations. This is an interpolated camera/render read, not the live `LivingEntity.travel` read.
- **Network:** `ClientPacketListener.handleMoveEntity`, lines 624–640, filters out entities controlled by the local instance and applies remote rotations through `lerpTo`; it does not call `turn`. `handleMovePlayer`, lines 669–729, handles the server's local-player correction, resolves relative pitch against `player.xRot`, then calls `player.absMoveTo(...)` and acknowledges/sends movement. These network correction paths are separate from the mouse-to-`Entity.turn` setter and are unchanged across the pair (the 1.15.1 manifest lists the same `ClientPacketListener.java` digest `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30`).

The exact source references above are relative to `decompiled_minecraft/1.15/mojmap/` and `decompiled_minecraft/1.15.1/mojmap/`, mapping family `mojmap` (official Mojang mappings). No generation, product source, wiki, runtime, build, or Git inspection was performed.
