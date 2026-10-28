# Independent audit: swept-AABB traversal contract

## Verdict

**REVISE** for one material formula statement in the source report. The source identities and the examined traversal, clipping, epsilon, cutoff, and 1.21.9 corner/iterator claims are otherwise supported within the bounded source scope. The report's general tie rule is too broad: exact minimum ties do not always select Z. This review does not establish callback reachability, a movement trajectory, runtime parity, or pair freeze.

Audited source report SHA-256: `5b7ed6ee05dda849182d8201ec90fab8a6b23feab3a860bbdfb73ed504c3b25f`.

## Source identity and hash verification

I independently checked the exact Mojmap source trees under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. For 1.21.4, 1.21.5, and 1.21.8, the release-parent `mojmap.sources.sha256` digest matches both the source identity table and `mojmap.ready.json`'s `sourceManifestSha256`. For 1.21.6, 1.21.7, and 1.21.9, the embedded `mojmap/.decompile-provenance.json` digest and source-entry counts match the identity table. All 42 selected files (seven per release) were rehashed live and matched both the applicable source manifest/provenance entry and the author's expected digest. The independent results are in `verified-source-hashes.csv`.

This verifies the published Java source trees and their recorded source manifests. I did not rehash source-generation JAR or mapping caches.

I also independently recomputed all 124 non-`NO_MATCH` line-range hashes from `method-hashes.csv`, `dependency-method-hashes.csv`, and `clip-direction-method-hashes.csv`; every range hash matched its listed digest.

## Independently checked source behavior

- `BlockGetter` 1.21.4–1.21.8 uses the strict squared-displacement cutoff `lengthSqr() < Mth.square(0.99999F)`; 1.21.9 uses `lengthSqr() < Mth.square(1.0E-5F)`. `Vec3.lengthSqr()` evaluates `x*x + y*y + z*z`; `Mth.square(float)` returns float multiplication, which is promoted for the comparison. The older 1.21.4–1.21.5 traversal uses the same DDA coordinate/time calculation; 1.21.6–1.21.8 retain the same voxel math and add the boolean visitor-stop contract. The old loop's post-increment guard allows 17 cell-clip bodies before the next selected-axis update breaks. The 1.21.9 helper has no fixed 16-step guard.
- Across the six versions, `Mth.floor`, `Mth.frac`, `Mth.sign`, `AABB.clip`, and its face clip helper support the report's stated arithmetic. For finite int-representable negative coordinates, floor is toward negative infinity and `frac(x) = x - lfloor(x)` remains in `[0,1)`. Thus `p=-1.25` gives floor `-2`, fraction `.75`; `p=-1.0` gives floor `-1`, fraction `0`.
- The old DDA per-axis expression is `sign(d)/d` for moving axes and `Double.MAX_VALUE` for a zero axis, multiplied by `1-frac(p)` for positive steps or `frac(p)` otherwise. It advances the selected coordinate before the clip test. The report's zero-axis caveat is correct: when the stationary coordinate is integral, `Double.MAX_VALUE * 0.0` is zero and can select a no-position-change step, but callback emission still depends on `AABB.clip`.
- The source comparison confirms `AABB.clip` is unchanged in all six releases: it starts with best `t=1`, visits entering faces in X/Y/Z order, requires axis delta outside `±1.0E-7`, accepts only strict `0<t<best`, and expands the two transverse bounds by `1.0E-7` with strict comparisons. Equal-time face hits do not replace the first accepted face. `BlockGetter`'s voxel-hit clamp separately uses float literal `1.0E-5F`, promoted to double.
- The 1.21.9 rewrite is correctly transcribed: it visits direction-ordered start-box cells, traces the selected corner, and visits unvisited endpoint-box cells; its `getFurthestCorner` signed-permutation branches and `BlockPos.betweenCornersInDirection` axis ordering were checked directly. `Direction.axisStepOrder` returns YZX only if `abs(dx) < abs(dz)`, otherwise YXZ, so an exact X/Z magnitude tie selects Y, X, Z. The report correctly distinguishes this rectangular-iterator order from DDA crossing selection.
- `AABB.collidedAlongVector` is unchanged from 1.21.4 through .8 and changes in .9 to inflate each axis by `boxSize*0.5 - 1.0E-7`; the start/end center containment and segment clip checks remain. This epsilon is separate from both the clip transverse tolerance and voxel interior clamp.

## Required correction: DDA tie selection

The report states: “Exact ties therefore choose Z over Y over X.” The strict branches at `BlockGetter.addCollisionsAlongTravel` do not define that total priority. The code is:

```text
if (tx < ty) {
    if (tx < tz) choose X; else choose Z;
} else if (ty < tz) choose Y;
else choose Z;
```

Consequently:

- `tx = ty < tz` selects **Y** (the outer `tx < ty` test is false, then `ty < tz` is true).
- `tx = tz < ty` selects **Z**.
- `ty = tz < tx` selects **Z**.
- `tx = ty = tz` selects **Z**.

For example, start `p=(0.75,0.75,0.25)` with `d=(1,1,1)` produces `tx=ty=0.25` and `tz=0.75`; the first selected axis is Y, not Z. The report's all-equal example `p=(0.25,0.25,0.25)`, `d=(1,1,1)` correctly selects Z, then Y, then X as the subsequent crossing times change. It should retain that worked example while replacing the generalized tie-priority sentence with the pair-specific rule above.

## Scope limits

The audit checks the primitive arithmetic and enumeration only. It accepts the report's limits on non-finite/out-of-range coordinates, pathological boxes, runtime scheduling, callback effects, and caller reachability. The accepted 1.21.3→1.21.4 caller bundle was not re-audited here.
