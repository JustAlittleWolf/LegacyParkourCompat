# Swept AABB and voxel traversal source contract

## Scope and source identity

This is a source-only comparison of Mojmap 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8 and 1.21.9 for the block traversal and AABB intersection primitives used by movement-segment callbacks. It closes the primitive’s arithmetic and enumeration rules for ordinary finite coordinates whose floored block positions are int-representable. It does not claim movement trajectory parity or full pair coverage.

The assigned starting source leads were `.native-source/20261028/frontier-c/G-effects/report.md` (SHA-256 `78b87c1084421409efe4be848c8d0d4db7b6db6333048591982b3484d603c94c`) and `G-full-inventory/full-inventory.md` (SHA-256 `bea6efff3d5db9449df255334fee67bf042d7a1b04986e596c153d3e6f1c0ed6`; method transition table `key-movement-method-transitions.csv` SHA-256 `36b71a4ee4fa0b9a2c61666979ff141c5ad9e32ee780a79741ae19e2233ab020`). The accepted caller dependency reused without redoing is `1213-1214-block-segment-callback-closure/report.md` (SHA-256 `11c1e42f76911b4f527e3dc360e24834d81ea15374a12f6e41fc90a25a5b0c50`), independently audited at `1213-1214-block-segment-callback-audit/audit.md` (SHA-256 `66772dbc839f21fa0243b5f3e87f40be2726bd35916a53fadda62c64fcd0651f`).

Canonical sources are read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. For 1.21.4, 1.21.5 and 1.21.8, the release-parent ready marker’s `sourceManifestSha256` matches the actual release-parent `mojmap.sources.sha256` hash. For 1.21.6, 1.21.7 and 1.21.9, the source tree instead binds through its embedded `.decompile-provenance.json`. Exact manifest/provenance digests, source counts, and the SHA-256 of each inspected source file are recorded in `source-identity.csv` and `source-file-hashes.csv`. I independently rehashed seven relevant Java files per release against their exact source manifest or embedded source map: all 42 checks match. The recorded input identities in these files were not re-hashed from JAR/mapping caches.

I checked both each release parent and mapping directory with hidden entries included; no `.*mojmap.previous-*` snapshot directory was present for the six releases.

The inspected classes are `BlockGetter`, `AABB`, `BlockPos`, `Direction`, `Mth`, `Vec3`, and `Vec3i`. `method-hashes.csv`, `dependency-method-hashes.csv`, and `clip-direction-method-hashes.csv` list line ranges and normalized method-body SHA-256 values. Method-body hashes use UTF-8 source text with line endings joined as LF; `source-file-hashes.csv` holds hashes of the actual canonical files.

Key source locations: 1.21.4 `BlockGetter.boxTraverseBlocks` 172–189 and `addCollisionsAlongTravel` 191–247; 1.21.5 `forEachBlockIntersectedBetween` 172–190 and helper 192–253; 1.21.6–.8 `forEachBlockIntersectedBetween` 172–199 and helper 201–262; 1.21.9 `forEachBlockIntersectedBetween` 171–205, helper 207–267 and `getFurthestCorner` 269–281. `AABB.clip(double...)` is at 287–299 in 1.21.4, 288–300 in 1.21.5, and 292–304 in 1.21.6–.9. `AABB.clipPoint` is at 363–388, 364–389, and 368–393 respectively; `collidedAlongVector` is at 390–406, 391–407, and 395–411 respectively. `BlockPos.betweenClosed(AABB)` is at 358–362 through 1.21.8 and 359–363 in 1.21.9; its integer iterator is 398–420 through 1.21.8 and 399–421 in 1.21.9. The new 1.21.9 `betweenCornersInDirection` overloads are at 491–573; `Direction.axisStepOrder(Vec3)` is at 375–377.

## Boundary and algorithm summary

The line-segment plane clip helper is stable through all six releases. `AABB.clip(double...)` and `AABB.clipPoint(...)` have the same normalized body hashes in every version. `AABB.collidedAlongVector(...)` also matches from 1.21.4 through 1.21.8; 1.21.9 changes its inflation by a fixed epsilon, described below.

`BlockGetter` changes its traversal API in 1.21.5 and its cancellation contract in 1.21.6. The voxel math in `addCollisionsAlongTravel` is unchanged across 1.21.6, .7 and .8. 1.21.9 rewrites the sweep, its short-motion threshold, its start/end visitation and its box-corner parameterization. Thus the earliest checked geometric cutoff change is 1.21.9; the earlier API/cancellation boundary changes are separately identified here.

### 1.21.4–1.21.8 traversal

1.21.4 `BlockGetter.boxTraverseBlocks(from, to, box)` computes `d = to - from`. If `d.lengthSqr() < Mth.square(0.99999F)`, it returns only `BlockPos.betweenClosed(box)`. Otherwise, it creates an insertion-ordered position set, sweeps the box’s minimum corner from `box.min - d` to `box.min`, then appends every destination-box position. In the DDA loop, `if (step++ > 16) break` is evaluated after the selected axis coordinate/time are updated and before clipping that cell. Thus at most 17 loop bodies reach the clip, and the next selected-axis update breaks before clipping; destination cells are appended after that bound.

1.21.5 replaces the iterable result with `forEachBlockIntersectedBetween` and a `BlockStepVisitor`. It retains the same strict squared-distance cutoff and the same DDA and destination-box geometry. In this visitor form, the counter advances after each selected axis step, before the `AABB.clip` check; clipped path-cell groups get that counter, and nonduplicate destination-box cells get `counter + 1`. 1.21.6 changes the visitor to return a boolean; a false result stops traversal immediately. The `forEachBlockIntersectedBetween` and `addCollisionsAlongTravel` normalized method hashes are identical in 1.21.6, .7 and .8, so there is no further arithmetic/order change in those methods in that interval.

For the old DDA, let `p` be the start corner, `q` the end corner, `d=q-p`, and `f_i=Mth.frac(p_i)`. `Vec3.subtract` subtracts each coordinate independently; `Vec3.lengthSqr` evaluates `x*x + y*y + z*z` in Java expression order. The cutoff compares that squared result directly; it does not take a square root. `Mth.floor(double)` truncates to `int` and subtracts one when the original value is below that truncated value. `Mth.frac(double)` is `value - Mth.lfloor(value)`, so its result is nonnegative for negative non-integral coordinates too. Per axis `i`, source computes:

```text
s_i       = sign(d_i)
deltaT_i  = (s_i == 0) ? Double.MAX_VALUE : s_i / d_i
nextT_i   = deltaT_i * ((s_i > 0) ? 1 - f_i : f_i)
```

For a moving axis `deltaT_i = 1 / abs(d_i)`. At each loop, source compares `nextT` with strict `<`: if X is strictly less than Y, choose X only when X is also strictly less than Z, otherwise choose Z; otherwise choose Y only when Y is strictly less than Z, otherwise choose Z. Exact ties therefore choose Z over Y over X. The chosen integer coordinate advances by `s_i`, and that axis’s `nextT_i` advances by `deltaT_i`.

After an axis step, the helper clips the segment against that unit voxel with `AABB.clip`. If a clip point exists, it clamps each component to `[cell + 1.0E-5F, cell + 1 - 1.0E-5F]`, floors the clamped component plus the corresponding AABB size, and emits the inclusive X/Y/Z product of cells from the current voxel through those far indices. The `1.0E-5F` bounds are float literals promoted to double; this clamp and inclusive range are unchanged at the 1.21.9 traversal callsite.

### 1.21.9 traversal

`forEachBlockIntersectedBetween` now uses the strict test `d.lengthSqr() < Mth.square(1.0E-5F)`. The short branch visits the destination AABB’s `betweenClosed` cells at step zero. In the swept branch it first visits all floored cells in the start AABB (`box.move(d.scale(-1.0))`) in direction-oriented order. It then sweeps one selected AABB corner from `corner(box,d)-d` to `corner(box,d)`, adds cells intersected by that line expanded by the box dimensions, and finally visits any not-yet-seen cells of the destination AABB. The destination group is labeled `acceptedClipCount + 1`; the DDA count advances only when a unit-cell clip exists. Unlike the older helper, this method has no fixed 16-step guard.

The selected corner is the literal `getFurthestCorner(d)` result, with signs `s_i = (d_i >= 0 ? +1 : -1)`:

```text
if |dx| <= |dy| and |dx| <= |dz|: c = (-sx, -sz,  sy)
else if |dy| <= |dz|:              c = ( sz, -sy, -sx)
else:                              c = (-sy,  sx, -sz)
```

The exact signed permutation matters: do not replace it with an intuitively chosen componentwise corner. The corner point is computed componentwise as `AABB.getCenter() + (AABB size * 0.5 * c)`, where `getCenter` uses `Mth.lerp(0.5, min, max)` and `lerp` is `start + 0.5 * (end - start)`.

For each clipped unit cell with cell coordinate `b` and clip point `h`, 1.21.9 computes `g_i = clamp(h_i, b_i + 1.0E-5F, b_i + 1 - 1.0E-5F)`, then `far_i = floor(g_i - size_i * c_i)`. It visits the inclusive `BlockPos.betweenCornersInDirection(b, far, d)` range, deduplicated against prior positions. For a unit box and `d=(2,0,0)`, the source’s zero-sign convention and Y-minimum tie branch select `c=(+1,-1,-1)`. For `d=(2,2,2)`, the X-minimum tie branch selects `c=(-1,-1,+1)`; for `d=(-2,-2,-2)` it selects `c=(+1,+1,-1)`. These are direct evaluations of the literal method, not claims about an idealized swept-box implementation.

The `.9` initial/final AABB enumeration uses `BlockPos.betweenCornersInDirection`. It floors min and max corners inclusively, starts at the low end for a nonnegative direction component and high end for a negative component, and iterates a rectangular integer range. `Direction.axisStepOrder(d)` returns YZX only when `abs(dx) < abs(dz)`, and YXZ otherwise; an exact X/Z magnitude tie therefore chooses Y, X, Z. Within that order the iterator nests the third axis fastest, then second, then first. A zero component counts as nonnegative for the starting corner/direction. This ordering is distinct from the DDA’s Z-over-Y-over-X `nextT` tie rule.

### Negative coordinates, zero axes and exact ties

For a finite start coordinate `p=-1.25`, the source floor is `-2` and fractional part is `0.75`; a negative-moving axis therefore has its next plane `0.75` units away, not `0.25`. At `p=-1.0`, floor is `-1` and fraction is zero, so a negative-moving axis has `nextT=0` and advances into block `-2` immediately. These follow directly from `Mth.floor`, `Mth.lfloor` and `Mth.frac`, whose relevant method bodies hash identically across all six releases.

As a positive-coordinate tie example, `p=(0.25,0.25,0.25)`, `d=(1,1,1)` gives all three `deltaT=1` and `nextT=0.75`; old and new DDA select Z, then Y, then X at that same crossing parameter. With `p=(-0.25,-0.25,-0.25)`, `d=(-1,-1,-1)`, floor is `(-1,-1,-1)`, each fraction is `0.75`, and the same Z/Y/X tie order applies while stepping into negative cells. AABB clipping’s transverse-coordinate epsilon permits exact edge/corner crossings within its specified `1.0E-7` tolerance.

When `d_i == 0`, the DDA sets `deltaT_i` to `Double.MAX_VALUE`. If that coordinate is exactly integral, `nextT_i` is `Double.MAX_VALUE * 0.0 == 0.0`, so the strict comparator can select a no-position-change step once before that axis’s next time becomes `Double.MAX_VALUE`. The loop’s actual emission/count behavior still depends on whether the current unit cube clips the segment; it is not correct to infer a callback from that no-op axis selection alone. At nonintegral stationary coordinates the initial time is exactly the source expression `Double.MAX_VALUE * frac(p_i)`; for ordinary fractions it exceeds the unit-segment parameter, while extremely small floating values retain the direct IEEE double result.

`BlockPos.betweenClosed(AABB)` is byte-for-byte method-identical across the six sources: it floors both the minimum and maximum coordinates, and its inclusive integer iterator visits X fastest, then Y, then Z. Consequently a maximum exactly on an integer plane includes the high-side voxel in the candidate range; that enumeration is not itself an open-volume intersection test.

## AABB clip and swept-shape epsilon

`AABB.clip` starts its best parameter at `1.0`. It tests entering faces in X, Y, Z order; positive components greater than `1.0E-7` test the minimum face, negative components below `-1.0E-7` test the maximum face. `clipPoint` computes `t=(plane-start)/axisDelta`, then the other two coordinates, and accepts only `0 < t < currentBest`. Both transverse coordinates must satisfy strict bounds expanded by `1.0E-7` on each side. Equal-time face hits do not replace the earlier face because the update also requires `t < currentBest`. `AABB.clip(double...)` and `clipPoint` have identical normalized hashes across the six releases.

`AABB.collidedAlongVector` is a separate swept-shape predicate used by the callback path. In 1.21.4–1.21.8 it expands each candidate shape by exactly half the moving box size on each axis. In 1.21.9 each expansion is `size * 0.5 - 1.0E-7`; for ordinary player dimensions this shrinks the expanded shape by `1.0E-7` at both opposite faces per axis. It then returns true if the start or end center is contained (`min` inclusive, `max` exclusive), or if the center segment clips the expanded shape. This epsilon is separate from `AABB.clipPoint`’s transverse-face tolerance and from the voxel traversal’s `1.0E-5F` interior clamp.

## Bounded findings and dependencies

- Exact traversal math is unchanged from 1.21.6 through 1.21.8; 1.21.6’s visitor cancellation is part of the contract.
- 1.21.9 changes the short-path cutoff by five orders of magnitude in displacement length (from approximately one block to approximately `1.0E-5` blocks), adds explicit start-box traversal, changes the swept-corner formulation, removes the old fixed iteration guard, and adjusts `collidedAlongVector` by `1.0E-7` per expanded face.
- The mathematical contract above is source-derived for finite, ordinary coordinates whose block floors fit the source’s `int` positions. NaN/infinite coordinates, overflow outside int-representable block coordinates, pathological negative-size AABBs, and floating underflow/overflow outside ordinary world ranges are not claimed.
- This report does not establish which caller paths execute in a particular runtime tick, block registry contents, callback effects, or movement parity. Caller reachability and callback ordering are separate closures; the accepted 1.21.3→1.21.4 caller bundle is reused as a dependency and was not repeated here.

