# Independent source audit verdict: water travel (26.1.2–26.2)

**Audited report:** `build/later-source-20261116/26.1.2--26.2/water_travel/report.md`  
**Report SHA-256:** `D5548E9BC8F99BCFD7F178B57F8EDB1047DE47BB2FEAE226F93D4213AAB972D3`  
**Verdict scope:** the claims and bounded slices in this report only; not full-pair acceptance.

## Verdict

The owned-slice comparison is independently confirmed: no behavior change was found in `LivingEntity.travelInWater`, `Entity.moveRelative` / `getInputVector`, the water-current application gate and scale in `Entity.updateFluidInteraction`, or the bounded `Player.travel` delegation. The only local source difference in the reviewed water-travel body is the temporary local name (`ladderMovement` / `movement`). The reviewed water calculation order, constants, conditions, calls, and vector multipliers match. Player swimming adjustment and flying/passenger delegation also match. `LocalPlayer` declares no `travel` method at either endpoint.

This is a bounded verdict, not full-pair closure. The report identifies the relevant still-open dependencies: effective attribute/effect inputs and resources, fluid-current production and `FluidInteraction` internals, movement/collision and fluid-falling helpers, and broader travel/tick-path callers. Consequently the no-difference verdict does not accept or close those behaviors, intermediate releases, implementation, or runtime parity.

## Evidence audit

- The report hash matches the assigned immutable SHA-256.
- Both root-sidecar ready markers identify the expected exact version, `ready` status, and `unobfuscated` mapping. Their source-manifest digests and artifact-manifest digests match the marker declarations. I rehashed every source entry against the corresponding `decompiled_minecraft/<version>/unobfuscated` tree: 6,882/6,882 for 26.1.2 and 7,055/7,055 for 26.2; no missing, malformed, or mismatching entry. Manifest byte totals also match marker declarations.
- All six cited Java-file hashes (LivingEntity, Entity, Player for each endpoint) match the report.
- Artifact payload verification is unavailable: the ready markers' declared `outputPath` directories and the provenance-referenced campaign/cache locations are absent in this checkout. The sidecar artifact manifests themselves exist and hash correctly, but their listed client/library payloads could not be checked. The Java source trees and their complete source manifests remain available and verified, so this limits artifact provenance verification rather than the cited Java-body comparison.

## Reviewed source outcome

`LivingEntity.travelInWater` uses the same sprint-dependent slowdown, base speed, airborne water-walker halving, positive-efficiency blending order, Dolphin's Grace override, relative movement, self move, climbable vertical adjustment, X/Z slowdown and Y multiplier, fluid-falling adjustment, and fluid-exit call at both endpoints. The corresponding helper's small-input threshold, conditional input normalization, float yaw sine/cosine, rotation math, and velocity addition match. The water-current path uses the same `isPushedByFluid()` / `inWater` gates and `0.014` scale. `Player.travel` has the same passenger delegation, swimming vertical correction gates and expression, and flying/post-travel Y handling.

The claim that these compared slices have no owned-slice difference is supported. Preserve the report's partial status and explicit dependency gaps.
