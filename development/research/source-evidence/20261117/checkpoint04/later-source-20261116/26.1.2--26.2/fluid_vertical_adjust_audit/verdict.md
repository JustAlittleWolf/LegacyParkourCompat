# Independent source audit verdict: fluid vertical adjustment

Candidate: `build/later-source-20261116/26.1.2--26.2/fluid_vertical_adjust/report.md`

Candidate SHA-256: `D68AF9849F9060B41B1973999315FEBA77B0642F3C96E8706DEA5B7B1BB57AD5`

Verdict: **Accept the bounded no-difference conclusion.** This verdict applies only to `LivingEntity.getFluidFallingAdjustedMovement`, its immediate water/lava call sites, and the cited player reachability path for Minecraft 26.1.2 versus 26.2. It does not close the fluid movement area or pair coverage.

## Evidence identity audit

Both ready markers identify the exact requested release and `unobfuscated` mapping. Their declared source-manifest SHA-256 values match the root sidecars, with 6,882 rows for 26.1.2 and 7,055 for 26.2. The ready-declared artifact-manifest and movement-diagnostics hashes match their sidecars; artifact manifest row counts are 78 and 91. The cited `LivingEntity.java` and `Player.java` hashes match their respective source-manifest rows on both sides.

The marker provenance identifies Vineflower 1.12.0 and Java 25 for each endpoint. All 78 and 91 artifact paths declared by the manifests were absent from the respective ready output, cache, and staging roots inspected. Therefore the artifact payloads could not be rehashed, and this audit does not independently revalidate the upstream artifact contents or origin. That gap does not contradict the exact local source-file hashes and marker-to-sidecar checks reported above.

## Behavioral comparison

The method bodies are identical. In both releases, it first checks `baseGravity != 0.0 && !this.isSprinting()`. Within that gate, the ordered falling/tolerance predicate is `isFalling && Math.abs(movement.y - 0.005) >= 0.003 && Math.abs(movement.y - baseGravity / 16.0) < 0.003`. A match sets double `yd` to `-0.003`; otherwise `yd` is `movement.y - baseGravity / 16.0`. The method constructs `new Vec3(movement.x, yd, movement.z)` within the outer gate and returns the original `movement` object when the gate is false. Literal types, operation and predicate order, casts (none in this method), writes, and return behavior agree.

The immediate callers agree in relevant ordering: water applies the vertical `0.8F` slowdown before the helper and invokes `jumpOutOfFluid(oldY)` after setting the adjusted movement; shallow lava applies `(0.5, 0.8F, 0.5)` before the helper, while the non-shallow branch skips the helper. The surrounding fluid computations are outside this verdict.

Player reachability is established on both sides: `Player` extends `Avatar`, `Avatar` extends `LivingEntity`, and `Player.travel` delegates to `super.travel(input)` on passenger and ordinary branches (including the flying branch). `LivingEntity.travel` selects `travelInFluid` when `shouldTravelInFluid(...)` holds; that method dispatches water versus lava and reaches the helper through the cited callers. This establishes a reachable player path, not that every fluid or movement state takes it.

## Limits and remaining gates

This is not a review of effective gravity inputs, fluid selection and height/resource calculations, `jumpOutOfFluid`, exit impulses, fluid acceleration or drag, generic gravity, air travel, collision providers, or other tick paths. No exact introduction boundary, full-pair freeze, implementation acceptance, or runtime-parity claim follows. Preserve the source-coverage and independent full-pair audit gates.
