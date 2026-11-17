# Fluid horizontal exit impulse — bounded endpoint check

**Pair / lane:** 26.1.2 unobfuscated → 26.2 unobfuscated; source-only, endpoint comparison. This report covers only the water/lava horizontal-collision exit impulse and its vanilla-player reachability. The pair remains partial.

## Evidence identity and integrity

| Endpoint | Ready identity | Provenance identity | Root-sidecar SHA-256 checks |
|---|---|---|---|
| 26.1.2 | ready; version and metadata ID `26.1.2`; mapping `unobfuscated`; 6,882 source files / 32,992,615 bytes | namespace and exact-run mapping option `unobfuscated`; Vineflower 1.12.0; provenance says full Gradle output | ready `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; provenance `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; source manifest `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; artifact manifest `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`; diagnostics `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02` |
| 26.2 | ready; version and metadata ID `26.2`; mapping `unobfuscated`; 7,055 source files / 33,813,717 bytes | namespace and exact-run mapping option `unobfuscated`; Vineflower 1.12.0; provenance is a summary excerpt | ready `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; provenance `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; source manifest `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact manifest `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`; diagnostics `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3` |

The ready-declared source-manifest, artifact-manifest, and diagnostics hashes match the files on both endpoints. Artifact-manifest row counts are 78 and 91 respectively; the 26.2 ready record does not declare an `artifactCount`. The compared Java files also match their source-manifest entries:

- `26.1.2` `LivingEntity.java` `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; `Player.java` `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `LocalPlayer.java` `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
- `26.2` `LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `Player.java` `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.

## Ordered coverage

1. **Client player reachability.** `LocalPlayer.aiStep` delegates to `Player.aiStep` at 26.1.2 lines 914 and 26.2 lines 914; `Player.aiStep` delegates to `LivingEntity.aiStep` at lines 453 in both versions. The latter dispatches `this.travel(input)` at 26.1.2 lines 3070–3074 and 26.2 lines 3137–3141. The ordinary simulated-movement path therefore dynamically reaches `Player.travel`.
2. **Player-to-living travel.** `Player.travel` delegates to `super.travel(input)` in passenger, non-flying, and flying branches in both versions: 26.1.2 lines 1381–1404; 26.2 lines 1402–1425. On the flying branch it writes Y again after `super.travel`, so the exit impulse's Y value is overwritten there; horizontal X/Z are unaffected by that post-write. These branch and post-write rules are unchanged.
3. **Living travel gate.** `LivingEntity.travel` first tests `shouldTravelInFluid(level fluid at blockPosition)`, then falls through to fall-flying, otherwise air: 26.1.2 lines 2392–2400; 26.2 lines 2430–2438. `shouldTravelInFluid` has identical logic at 26.1.2 lines 2406–2408 and 26.2 lines 2444–2446: water-or-lava, fluids affect the entity, and it cannot stand on the supplied fluid state.
4. **Fluid branch order.** `travelInFluid` captures `isFalling` from delta Y, `oldY`, then effective gravity; water is selected first and calls `travelInWater` followed by ridden-water float handling, otherwise lava calls `travelInLava`. Ranges: 26.1.2 lines 2455–2465; 26.2 lines 2504–2514. In water, exit handling follows the move, optional climbable Y adjustment, slowdown multiplication, and fluid-fall-adjusted velocity write; it precedes ridden-water float handling (26.1.2 lines 2467–2494; 26.2 lines 2516–2543). In lava, it follows the move, shallow/deep-fluid velocity branch, and optional gravity write (26.1.2 lines 2496–2512; 26.2 lines 2549–2565).
5. **Impulse predicate and write.** `jumpOutOfFluid(oldY)` reads the current delta movement, then requires `horizontalCollision` and `isFree(movement.x, movement.y + 0.6F - this.getY() + oldY, movement.z)`. On success it writes `(movement.x, 0.3F, movement.z)`. Exact ranges: 26.1.2 lines 2514–2519; 26.2 lines 2567–2572. Both are source-identical, including predicate order, float literals, position/velocity reads, and preserved X/Z writes. Both water and lava call this same helper once after their preceding `move(MoverType.SELF, getDeltaMovement())`.

## Bounded verdict and dependency gate

**No difference found in the horizontal-collision exit impulse or the vanilla-player route into it.** The tested precondition is a water or lava travel tick selected by the unchanged fluid gate, followed by the shared branch's move producing `horizontalCollision` and a free candidate exit space. Under that precondition, both endpoints conditionally replace vertical delta with `0.3F`, preserving current horizontal delta.

One adjacent source-surface change: `travelInFluid` is `private` in 26.1.2 and `protected` in 26.2. The vanilla `Player` has no override in either endpoint, so this does not change the checked vanilla-player path; custom subclass dispatch is outside this bounded verdict.

The dependency gate remains open for pair acceptance: this check did not inspect the `Entity.move` collision kernel that sets `horizontalCollision`, the `isFree` implementation and world collision inputs, or fluid-height/property/tag/resource/effect resolution. Nearby fluid acceleration and drag were used only to establish call order; no finding or no-difference claim is made for them. This is not a full-pair freeze, a first-onset claim, or an implementation/runtime-parity claim.

