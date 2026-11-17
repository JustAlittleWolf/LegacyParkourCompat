# Independent source audit verdict: fluid exit impulse

Candidate: `build/later-source-20261116/26.1.2--26.2/fluid_exit_impulse/report.md`
Candidate SHA-256: `093EA572F1D63CA35668F786CF3E33FC36827E5F1DFD6BEE91323BA123AD349E`
Endpoints: 26.1.2 unobfuscated → 26.2 unobfuscated

## Verdict

**Supported as a bounded no-difference finding.** The audited `jumpOutOfFluid` predicate and conditional write are source-identical at both endpoints. The water and lava branches each call it once after their movement and branch-specific velocity updates. `LivingEntity.travel` selects fluid travel with the same gate and branch order. The ordinary client route reaches `Player.travel`, which delegates to `LivingEntity.travel` in passenger, flying, and ordinary branches. No vanilla `Player.travel` override of the fluid helper exists at either endpoint. `travelInFluid` widens from private (26.1.2) to protected (26.2); this does not change the vanilla Player route. The report correctly limits the effect of its flying-branch post-write to Y.

The predicate reads current delta movement, checks `horizontalCollision`, then tests `isFree(movement.x, movement.y + 0.6F - this.getY() + oldY, movement.z)`. On success it writes `(movement.x, 0.3F, movement.z)`. The exact expression, float literal, guard order, and horizontal-component preservation match. Water calls occur after move, climbable adjustment, slowdown, and fluid-fall velocity write; lava calls occur after move, shallow/deep handling, and optional gravity. These conclusions are bounded to this operation and its vanilla-player route.

## Evidence integrity

The report SHA-256 matches the assigned immutable candidate identity. Both ready records declare the requested exact versions and `unobfuscated` mapping. Both provenance records declare the same namespace/mapping option and Vineflower 1.12.0. I independently rehashed each ready and provenance file and confirmed the report values.

For each endpoint, the ready-declared source-manifest, artifact-manifest, and diagnostics hashes match their sidecars. I checked every source-manifest entry against the decompiled tree: 26.1.2 has 6,882 files / 32,992,615 bytes and 26.2 has 7,055 files / 33,813,717 bytes, with no missing files or digest mismatches. The artifact sidecars contain 78 and 91 rows respectively; 26.1.2's ready marker declares 78, while 26.2's marker has no `artifactCount`. Each cited Java file's digest matches its source-manifest entry and the report:

- 26.1.2 `LivingEntity.java`: `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Player.java`: `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `LocalPlayer.java`: `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- 26.2 `LivingEntity.java`: `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Player.java`: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `LocalPlayer.java`: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.

The 26.2 provenance and success log identify their success evidence as a summary excerpt, not persisted full Gradle output; this limitation is explicit in the report and does not contradict the independently verified source trees or bounded source comparison.

## Scope and open dependencies

The report appropriately leaves the collision kernel that establishes `horizontalCollision`, `isFree` and its collision inputs, and fluid-height/property/tag/resource/effect resolution outside this verdict. It makes no claim about nearby fluid acceleration/drag, pair freeze, an exact introduction boundary, implementation, or runtime parity. No finding or disposition needs correction within the assigned slice.
