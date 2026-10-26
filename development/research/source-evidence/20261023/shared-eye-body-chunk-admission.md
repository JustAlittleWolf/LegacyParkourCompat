# Ordinary-player eye/body fluid preflight audit

**Audit verdict:** The supplied missing-neighbor-chunk witness is source-valid for a non-riding crouched local player with the stated water and chunk state. It proves that the newer shared body/eye preflight clears both results. It does not establish that eye sampling must remain a separately resolved operation. Narrow any broad claim from this witness that requires a distinct eye-eligibility operation; preserve the independent source fact that the old eye sample has separate point-sampling semantics.

## Cold path

For a crouched player centered at X=15.4 with the ordinary 0.6-block player width, the bounding box is approximately X=15.1..15.7. With water at the player’s body and eye cells in chunk 0 and chunk 1 absent:

- In 1.21.11, `Entity.baseTick` runs the body update before `updateFluidOnEyes` (`Entity.java:504-506`). The body scan first calls `touchingUnloadedChunk`; that expands the box by 1 and asks `LevelReader.hasChunksAt` (`Entity.java:3509-3514,3576-3582`; `LevelReader.java:197-210`). `hasChunksAt` dynamically dispatches `ClientLevel.hasChunk`, whose client override always returns true (`ClientLevel.java:392-394`). Therefore the guard does not reject the scan because chunk 1 is absent. The body scan deflates the player box and visits X=15 (`Entity.java:3514-3532`), so loaded chunk 0 water still contributes. The independent eye update snapshots the previous WATER eye result, clears tags, samples `BlockPos.containing(getX(), getEyeY(), getZ())`, and adds tags only when block Y plus fluid height is strictly greater than eye Y (`Entity.java:1524-1534`). That point lies in chunk 0, so the eyes are wet when the supplied eye cell contains water above eye height.
- In 26.1, `Entity.baseTick` snapshots `wasEyeInWater` and calls one shared `updateFluidInteraction` (`Entity.java:523-525`), which updates body state and eye membership from `EntityFluidInteraction` (`Entity.java:1558-1561,1651-1653`). `Entity.getFluidInteractionBox` deflates the body box by 0.001 and, for a non-rider, returns that box (`Entity.java:4015-4023`). The shared scanner maps its X bounds to x0=15 and x1=15, then expands preflight bounds to x=14..16 (`EntityFluidInteraction.java:32-42`). `hasFluidAndLoaded` converts those bounds to chunk coordinates and calls `getChunk(..., FULL, false)`; any null chunk returns false (`EntityFluidInteraction.java:91-118`). On the client, `ClientChunkCache.getChunk` returns null for an absent chunk when `loadOrGenerate` is false (`ClientChunkCache.java:69-78`), and `Level.getChunk` forwards that result (`Level.java:202-208`). Thus absent chunk 1 rejects the shared scan before it reads chunk 0’s water. Trackers were reset first (`EntityFluidInteraction.java:32-34`), so body height and eye membership both remain dry.

The tick-level consumer is also reachable. Both `Player.tick` implementations call `updateIsUnderwater()` before `super.tick()` (`Player.java:265-266` in 1.21.11; `Player.java:258-259` in 26.1). That method caches `isEyeInFluid(WATER)` in `wasUnderwater` (`Player.java:313-316`; `Player.java:303-306`). `LocalPlayer.isUnderWater()` returns that cache directly (`LocalPlayer.java:1137-1139`; 26.1 `LocalPlayer.java:1176-1179`). In 1.21.11 the cache is true from the prior wet-eye result and the body scan is wet; the shallow-water predicate is body WATER and not underwater (`Entity.java:1469-1474`). A forward+sprint input with sufficient food and no mobility/item-use restriction reaches `canStartSprinting`; crouching is `isMovingSlowly`, and the underwater cache permits that crouch exception (`LocalPlayer.java:648-650,1096-1109`). With the new shared scan dry, the next tick’s cache is false and the crouch exception fails (`26.1 Player.java:303-306`; `LocalPlayer.java:687-689,1141-1148`). This is a source-inferred input-gate divergence, not a runtime observation.

## Scope decision

The witness demonstrates loss of both body WATER and eye WATER due to the shared preflight’s expanded neighbor-chunk check and early return. Since the body result is lost at the same gate, it cannot establish that a separate eye availability operation is necessary: a historical body scan allowed through the relevant availability condition could also recover this eye result. The old point sample and strict `fluidTop > eyeY` test remain distinct from the new scanned-volume eye test, which marks eyes inside when `eyeY >= fluidBottom && eyeY <= fluidTop` (`EntityFluidInteraction.java:44-46,52-70`). Keep that as a separate source-semantic question; this cold audit did not find an eye-only witness with the shared preflight passing.

## Exact source identity and hashes

Sources were read-only from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- **1.21.11, Mojmap:** provenance `1.21.11/mojmap.provenance.json` records versionId 1.21.11, namespace Mojmap, the exact `decompileMinecraft` run for 1.21.8/1.21.10/1.21.11, Vineflower 1.12.0 and Java 25.0.3+9-LTS. Ready marker records source-manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` and client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.
- **26.1, published unobfuscated:** provenance `26.1/unobfuscated.provenance.json` records requested and resolved metadata ID 26.1, client SHA-1 `191771837687b766537a8c4607cb6fad79c533a1`, original client SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`, and no remapping artifact because this is the published unobfuscated namespace. Ready marker records source-manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`.
- **26.1.2 persistence:** provenance identifies exact versionId 26.1.2 and a separate exact decompile run; its ready marker records source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`. Relevant source hashes match 26.1 exactly for `EntityFluidInteraction.java` (`5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`), `Entity.java` (`8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`), `Player.java` (`44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`), `LocalPlayer.java` (`433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`) and `ClientChunkCache.java` (`1a35ecd0c18d989bed9ef883028e197e29f98aadc78d909727722faba9a912a5`). The shared preflight and the tick consumer therefore persist through 26.1.2.

SHA-256 of the compared relevant source files, also present in their respective source manifests:

| Source | SHA-256 |
| --- | --- |
| 1.21.11 Mojmap `world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 Mojmap `client/multiplayer/ClientLevel.java` | `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2` |
| 1.21.11 Mojmap `world/level/LevelReader.java` | `562cd72343484c0e48f55dfe1ff50e5bb10516135d601aff264867fac9d9ff09` |
| 1.21.11 Mojmap `world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 Mojmap `client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 26.1 unobfuscated `world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.1 unobfuscated `world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1 unobfuscated `world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1 unobfuscated `client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.1 unobfuscated `client/multiplayer/ClientChunkCache.java` | `1a35ecd0c18d989bed9ef883028e197e29f98aadc78d909727722faba9a912a5` |

## Gate

This is a bounded source-only audit. It establishes the ordinary-player witness and 26.1.2 persistence. It does not establish runtime parity, a patch introduction boundary, or an eye-only witness that isolates the old point sampler from body availability.
