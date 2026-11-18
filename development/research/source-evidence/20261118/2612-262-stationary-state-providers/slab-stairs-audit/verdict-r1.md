# Additive-correction audit verdict — 26.1.2 → 26.2 slab/stair providers

## Bound input digests

| Input | SHA-256 |
|---|---|
| Original author claim, unchanged | 4f95e15278e3a55363e851a73c232ec77e5d12be7820f6a701dc2fb1b0fa2dac |
| Authorized additive correction r1 | 74948aeda0f79d5b90ac8694f71c673a92fa197e63573702ada125a80d32cb3e |
| Prior independent source roster | 0a5e9ca4233749b24b9d9262e19db3db9ee5ee68b481406851a398f11418673b |
| Prior source-roster erratum | 06f4859b3c8188b3b4eba4e986aaef755796aa1c8de70a3f591ab4b5807d3d42 |
| Prior independent matrix | 326e0f0556802fc8647162d40f1b71ea21166f5964f793ae9810ce6794b91651 |
| Prior verdict | e9a8cacea925ad8b1d6c184ced30ad626801693609e4c7b0163d17fbc017ef57 |
| 26.1.2 source manifest | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 |
| 26.2 source manifest | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 |
| 26.1.2 StairBlock.java | 2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b |
| 26.2 StairBlock.java | 2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b |

Each digest above was computed from local raw file bytes. The original claim remains byte-identical to its previously audited revision.

## r1 scope check and source result

Correction r1 is an additive note scoped to the inner stair occupied-set description. It preserves the original report unchanged and does not replace or change its source roster, pair, scope, provider selectors, cache claim, registered-family claim, or no-difference conclusion. No other claim changed.

The correction is source-correct for both stair halves. In StairBlock.java, OUTER has one upper quarter added to a full lower half. STRAIGHT unions OUTER with its 90-degree rotation, giving two adjacent upper quarters. INNER unions STRAIGHT with another 90-degree rotation of STRAIGHT. A quarter-turn maps the two-quarter adjacent half-footprint to a different adjacent half-footprint; the sets overlap in one quarter, leaving three distinct upper quarters. INVERT_Y creates the top-half maps, preserving three-quarter occupancy while moving the full slab to the upper half and the missing quarter to the lower half. Horizontal-facing rotations preserve the occupied-quarter count. The same byte-identical source is present in each compared profile.

## Separate per-family dispositions

| Family/slice | r1 disposition |
|---|---|
| SlabBlock dry TYPE→shape | Accept bounded no-difference; r1 does not change this claim. |
| StairBlock dry FACING×HALF×SHAPE→shape | Accept bounded no-difference with r1's corrected geometry description: INNER occupies three quarters, for either HALF. |
| Registered weathering copper slab/stair variants | Accept inheritance coverage from the prior independent source roster/matrix; r1 changes no registry or subclass claim. |
| Collision-shape cache/context dependency | Accept bounded independence from the prior independent source roster/matrix; r1 changes no cache claim. |

This remains a bounded endpoint audit, not a full-pair freeze, intermediate-version boundary finding, or runtime-parity result.

Encoding: UTF-8 without BOM, LF line endings.