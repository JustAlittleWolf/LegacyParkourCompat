# 26.1.2--26.2: fluid vertical adjustment (source-only)

## Evidence identity

Exact endpoints: Minecraft 26.1.2 and 26.2; unobfuscated decompilation (Vineflower 1.12.0, Java 25). For both sides, root `unobfuscated.ready.json` and `.provenance.json` identify the requested version/mapping. `unobfuscated.sources.sha256` byte hash matches the ready declaration, and its row count matches ready (`6882` / `7055`). The ready-declared `artifacts.sha256` and `movement-diagnostics.txt` hashes also match their root sidecars. The artifact manifests contain 78 rows (26.1.2) and 91 rows (26.2), but all declared artifact payload paths were absent under the ready output, provenance cache, and staging roots; original artifact payloads could not be rehashed. This limits upstream artifact revalidation, not the local source-file checks below.

| Endpoint | `unobfuscated.sources.sha256` SHA-256 | `LivingEntity.java` SHA-256 | `Player.java` SHA-256 |
| --- | --- | --- | --- |
| 26.1.2 | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.2 | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |

Every cited Java file's actual SHA-256 matches its row in that endpoint's source manifest.

## Bounded slice

- `26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java`: `getFluidFallingAdjustedMovement` lines 2635–2648; fluid callers `travelInWater` 2467–2494 and `travelInLava` 2496–2512.
- `26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java`: `getFluidFallingAdjustedMovement` lines 2688–2701; fluid callers `travelInWater` 2516–2543 and `travelInLava` 2549–2565.
- Player reachability: `26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, `travel` 1381–1403; `26.2/.../Player.java`, `travel` 1402–1425. Player travel delegates to `super.travel(input)` on passenger and ordinary branches, reaching `LivingEntity.travel` and its fluid branch when selected.

## Comparison

No difference in the bounded adjustment method. Both versions gate adjustment on `baseGravity != 0.0 && !this.isSprinting()`. For `isFalling && Math.abs(movement.y - 0.005) >= 0.003 && Math.abs(movement.y - baseGravity / 16.0) < 0.003`, it sets double `yd` to `-0.003`; otherwise it sets `yd = movement.y - baseGravity / 16.0`. It returns a new `Vec3(movement.x, yd, movement.z)`. With the outer gate false, it returns the original `movement`. Predicate order, double literals/arithmetic, writes and return behavior match at these endpoints.

In water, the helper is called after movement/collision interaction and `movement.multiply(slowDown, 0.8F, slowDown)`. In lava it is called only on the shallow-fluid branch, after movement/collision interaction and `multiply(0.5, 0.8F, 0.5)`. These call sites establish ordinary player reachability but are not a review of the surrounding fluid calculations.

## Coverage and next gate

Examined: only the vertical falling/tiny-velocity adjustment body, its immediate water/lava call sites, and Player-to-LivingEntity travel delegation needed for reachability. Bounded result: no endpoint difference.

Partial: `S-TRAVEL-FLUID-ADJUST` remains partial. Effective `baseGravity` inputs (attributes/effects), movement-value provenance, fluid selection/height/resource calculation, `jumpOutOfFluid`, exit impulse, fluid acceleration/drag, generic gravity, air travel, input, collision providers and other tick paths remain outside this slice or unexamined. No inference is made about their equality or difference. No onset boundary, pair freeze, implementation or runtime-parity claim.

Next gate: source-owner review of remaining fluid adjustment dependencies, especially effective gravity and how its inputs enter the helper; retain the broader pair's separate coverage and audit gates.
