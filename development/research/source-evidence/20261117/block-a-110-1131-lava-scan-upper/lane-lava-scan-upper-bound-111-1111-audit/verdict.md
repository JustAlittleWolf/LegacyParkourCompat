# Independent source audit — lava scan upper Y, 1.11 → 1.11.1

## Disposition

**ACCEPT the comparison’s no-difference claim for the assigned operations.** The scan bounds, ceil implementation, strict voxel loops, `isInLava` box shrink, and local-player travel consumer/gates match across the exact releases. The source scope is bounded to these operations and does not establish a change boundary outside 1.11 → 1.11.1.

## Independent checks

- Exact source sets: `decompiled_minecraft/1.11/ornithe-feather` and `decompiled_minecraft/1.11.1/ornithe-feather`. The ready markers say `ready`, with exact version IDs and `ornithe-feather`; their source-manifest SHA-256 values match the actual manifests. Provenance records report matching requested/resolved IDs, `feather`, Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0, and successful decompilation. Exact client hashes are 1.11 `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` and 1.11.1 `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`.
- Frozen before opening author artifacts: complete source manifests (1.11: 1,918 entries, SHA-256 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`; 1.11.1: 1,921 entries, SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`). Independent roster file SHA-256: `2291e4ec92189adc1c6b47f61e440b539a340ed07fc70357f62eab72c1b7dab0`.
- Author artifacts matched their assigned hashes: `comparison.md` `c929ecf316feb59842734a653e25de82046d4c6cec77b77ce38fcdaa5cda704c`; author `roster.md` `357b9e6c55815a84681776b8fdc5eb833265d494360f4232538b21e940997e49`.
- Independently verified each of the six source files per side in the author roster against the published source SHA-256 manifest; all match. I also checked `MathHelper.java`, a necessary dependency omitted from the author roster: its `floor(double)` and `ceil(double)` implementations are identical in both versions (source SHA-256 `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` on each side).

`Entity.isInLava()` passes `getShape().grown(-0.1F, -0.4F, -0.1F)` and `Material.LAVA` in both versions. `Box.grown` applies `min - delta` and `max + delta` in the same order; the float deltas are widened to `double`. `World.containsMaterial` computes `k = floor(minY)` and `l = ceil(maxY)` and iterates `p = k; p < l; p++`. Exact integral `maxY = N` therefore excludes row N; a value just above N includes it. The same result holds for negative coordinates because the exact `ceil(double)` helper returns the truncated integer when the input is integral and increments it when the input is greater than that integer. The X/Z bounds, nested X/Y/Z order, material equality check, early return, and pooled-position release also match. `getBlockState` has the same out-of-height check then chunk read; the query itself adds no loaded-area or loaded-chunk predicate.

`PlayerEntity.mobTick` delegates to `LivingEntity.mobTick`; both versions damp sideways/forward input by `0.98F`, adjust rotation speed, call `flyingTick`, then `moveRelative`. The player wrapper calls `super.moveRelative` on both flight/non-flight paths before movement-stat updates. The inherited movement gate and branch ordering match: locally controlled or server-authoritative entities proceed; water is tested before lava; player flight bypasses both checks. `LocalClientPlayerEntity.isLocallyControlled()` returns true in both versions, so the local client player reaches the query. In the lava branch both versions snapshot Y, update velocity with `0.02F`, move, halve X/Y/Z, subtract `0.02` from Y only without no-gravity, then apply the horizontal-collision/can-move condition and set Y to `0.3F`. The ordinary land branch’s chunk-loaded gravity guard follows its move and is outside the lava query/branch.

## Source locations

- `Entity.isInLava`: 1.11 lines 978–980; 1.11.1 lines 1037–1039.
- `World.containsMaterial`: 1.11 lines 1636–1658; 1.11.1 lines 1615–1637.
- `MathHelper.floor/ceil(double)`: both lines 40–43 and 67–70.
- `Box.grown`: both lines 132–140.
- `LivingEntity.moveRelative`: 1.11 lines 1386–1546; 1.11.1 lines 1388–1548. `LivingEntity.mobTick` travel call: 1.11 lines 1797–1802; 1.11.1 lines 1799–1804.
- `PlayerEntity.moveRelative`: 1.11 lines 1385–1403; 1.11.1 lines 1387–1405. `PlayerEntity.mobTick` delegates at lines 417–435 in both.
- `LocalClientPlayerEntity.isLocallyControlled`: both lines 468–470.

The author roster omission of `MathHelper.java` is closed by this independent check; include it in the evidence roster if the comparison is republished as a complete dependency inventory. No test, build, runtime, network, wiki, or product implementation was consulted.
