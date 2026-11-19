# Eye-fluid cutover: 1.15.2 to 1.16

## Claim checked

The 1.15.2 eye-water predicate samples at `getEyeY()` and tests the fluid surface with `+ 0.11111111F`. Starting in 1.16, the eye-fluid refresh samples at `getEyeY() - 0.11111111F`. The 1.16.0–1.16.5 implementations preserve that float offset and the copy-before-refresh latch used by `isUnderWater()`.

This is a separate eye-sample/predicate-timing change. It does not establish or explain the separately covered stale-depth jump at Y=256.

## Exact source evidence

Mapping: the canonical project `mojmap` decompilation for each named release. Evidence is `net/minecraft/world/entity/Entity.java`.

- **1.15.2**, `Entity.baseTick` line 363 calls `updateWaterState`; `updateWaterState` calls `updateInWaterState` then `updateUnderWaterState` then `updateSwimming` (lines 943–947); `updateUnderWaterState` stores the direct `isUnderLiquid(FluidTags.WATER, true)` result (line 976); `isUnderWater` returns `wasUnderWater && isInWater()` (lines 939–941). `isUnderLiquid(Tag<Fluid>, boolean)` sets `d = getEyeY()` (line 1045), probes the block at the eye position and uses `fluidState.is(tag) && d < blockPos.getY() + (fluidState.getHeight(...) + 0.11111111F)` (lines 1046–1052).
- **1.16.0**, `Entity.baseTick` updates body fluid state, calls `updateFluidOnEyes`, then `updateSwimming` in that order (lines 372–374). `updateFluidOnEyes` first copies the previous `isEyeInFluid(WATER)` result into `wasEyeInWater`, clears `fluidOnEyes`, then computes `d = getEyeY() - 0.11111111F` and refreshes `fluidOnEyes` from the fluid at that sample (lines 974–1000). `isUnderWater` consumes `wasEyeInWater && isInWater()` (lines 935–937). Thus the eye result consumed by the same-tick swimming update is the prior cached eye-fluid result, gated by the current body-water predicate.
- **1.16.1** has the same complete `Entity.java` bytes as 1.16.0.
- **1.16.2–1.16.5** have the same complete `Entity.java` bytes as one another. In those releases `baseTick` uses the same body-fluid → eye refresh → swimming order (lines 380–382); the latch copy, reset, and offset are at lines 981–984; `isUnderWater` is at lines 945–947. The boat exclusion changes from point containment in 1.16.0/1.16.1 to a vertical-range comparison in 1.16.2+, but it does not alter the requested sample formula or latch order.

The comparison establishes an interval boundary: these facts differ between 1.15.2 and 1.16.0 and remain unchanged through 1.16.5. It does not claim a more precise pre-1.16.0 introduction point.

## Source identity and hashes

All SHA-256 values below are lowercase hex. `Entity.java` hashes are the actual bytes read from the named canonical source path. For releases with `mojmap.sources.sha256`, its whole-file SHA-256 is listed as the source-manifest identity, and its `Entity.java` entry matches the actual file hash. For 1.16.3/.4 the sibling manifest files are absent; the hidden `.decompile-provenance.json` source map pins `Entity.java` and its hash. Its own SHA-256 is listed as the manifest identity.

| Release | Mapping | Entity.java SHA-256 | Source-manifest SHA-256 | Provenance/ready identity |
|---|---|---|---|---|
| 1.15.2 | mojmap | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | provenance `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`; ready `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` |
| 1.16 | mojmap | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | provenance `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`; ready `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` |
| 1.16.1 | mojmap | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | provenance `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; ready `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb` |
| 1.16.2 | mojmap | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | provenance `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; ready `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214` |
| 1.16.3 | mojmap | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | hidden source-map entry in `.decompile-provenance.json` | provenance/source-map SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` |
| 1.16.4 | mojmap | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | hidden source-map entry in `.decompile-provenance.json` | provenance/source-map SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` |
| 1.16.5 | mojmap | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | provenance `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`; ready `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` |

The 1.16.3 and 1.16.4 hidden source maps also identify their exact client jars/mapping inputs: 1.16.3 client input SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`, mapping input `client.txt` SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`; 1.16.4 client input `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, mapping input `client.txt` `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.