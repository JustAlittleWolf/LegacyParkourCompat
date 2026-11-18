# Independent verdict: 1.15.2 to 1.16 eye-fluid timing

## Verdict

**ACCEPT (scoped claim).** The reviewed supplemental claim is supported: 1.15.2 derives `wasUnderWater` from a direct eye-level fluid predicate using `getEyeY()` and a `+ 0.11111111F` surface allowance. In stable 1.16, the eye-fluid refresh uses `getEyeY() - 0.11111111F`; it copies the previous `fluidOnEyes` result into `wasEyeInWater` before clearing and refreshing that cache. The same formula, latch, and consumer remain through 1.16.5. This finding does not support the separate Y=256 stale-depth jump claim.

## Operation and gate check

- **1.15.2:** `Entity.updateWaterState` runs body-water update, `updateUnderWaterState`, then `updateSwimming` (`Entity.java:943-947`). `updateUnderWaterState` writes the direct `isUnderLiquid(WATER, true)` result (`:975-976`). `isUnderWater()` returns `wasUnderWater && isInWater()` (`:939-941`); swimming consumes it (`:949-954`). `isUnderLiquid` rejects boat passengers, conditionally rejects absent chunks, chooses the block at `getEyeY()`, and checks water plus `d < blockY + (fluidHeight + 0.11111111F)` (`:1040-1052`).
- **1.16.0:** `baseTick` refreshes body fluid state, calls `updateFluidOnEyes`, then `updateSwimming` (`:372-374`). At refresh start, `wasEyeInWater = isEyeInFluid(WATER)` reads the existing `fluidOnEyes`; `isEyeInFluid` is exactly `fluidOnEyes == tag` (`:1061`), and the cache is then cleared before the new eye-fluid value is computed (`:974-1000`). The tested height is `getEyeY() - 0.11111111F`; block position is constructed from that vector and the fluid surface test is strict `e > d`. `isUnderWater()` consumes `wasEyeInWater && isInWater()` (`:935-937`), so the eye component is prior-cache state while body water is current, and swimming uses that predicate later in the same `baseTick` (`:939-944`).
- **Vehicle/chunk distinctions:** 1.15.2's direct predicate returns false for a boat and checks chunk availability when its boolean argument is true. 1.16.0/1.16.1 instead skip the eye refresh when a non-submerged boat contains the offset sample point; 1.16.2+ use the boat's vertical interval against that point. In the inspected 1.16 sources, eye refresh has no explicit chunk-presence guard. These gates qualify where the predicate applies but do not invalidate the bounded eye-offset/cache claim. `isEyeInFluid` reads the cached `fluidOnEyes` tag; `isUnderWater` additionally gates it with the current body-water predicate.

## Exact source identity

All paths are under `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/Entity.java`; mapping family is Mojmap. Actual `Entity.java` SHA-256s, checked against the adjacent source manifest or hidden source map, are:

| Release | Entity.java SHA-256 | Provenance identity |
|---|---|---|
| 1.15.2 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `mojmap.provenance.json` `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`; source manifest `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16 | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | provenance `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`; source manifest `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | provenance `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; source manifest `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | provenance `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; source manifest `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.3 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | hidden `mojmap/.decompile-provenance.json` SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; source map pins this Entity hash and inputs `client.jar` `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`, `client.txt` `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` |
| 1.16.4 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | hidden `mojmap/.decompile-provenance.json` SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`; source map pins this Entity hash and inputs `client.jar` `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, `client.txt` `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` |
| 1.16.5 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | provenance `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`; source manifest `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |

The checked stable snapshots establish the offset/cache form in 1.16.0 and unchanged text through 1.16.5; `isEyeInFluid` still returns `fluidOnEyes == tag` at `Entity.java:1067` in 1.16.5. This does not locate an earlier prerelease introduction. 1.16.0 and 1.16.1 Entity files are byte-identical; 1.16.2 through 1.16.5 Entity files are byte-identical.

## Reviewed input identity

Input: `e00185913b8a2e7949558daed308c7794bf1cb03:evidence.md`. Raw blob bytes retrieved with `git cat-file blob` (5,728 bytes) have SHA-256 `4f59f8e28e4f9553f9d19ca2829a0c9b1c2458b48bbc533c8b0519d12f2c0a66`, matching the assigned identity.
