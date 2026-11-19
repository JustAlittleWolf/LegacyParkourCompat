# Independent source audit verdict: body-water admission alias

**Verdict: ACCEPT**

## Bounded claim

The concrete body-water admission alias is source-confirmed in canonical Mojmap client trees for exactly 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, and 1.20.6: for an ordinary loaded local player with the supplied standing box centered at x=15.7, z=8.5 and feet y=64, with the only matching fluid at source WATER (15,64,8) and AIR at (15,65,8), `updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014)` reaches that loaded sample and returns body contact even if chunk X=1 is absent. The claim is the admission prerequisite only.

## Evidence identity checked

The pinned source evidence is commit `229c52337a33189b4c6fe05b0dbab9a85e60ec21`, path `evidence.md`, raw committed-byte SHA-256 `626a57c519716204e16a3df1f468f502dc9b30417cd7a6cac75e7893780bcc1b`. The checkout copy has line-ending conversion; the committed blob's raw bytes reproduce the pinned digest.

I independently recomputed all six cited source-file SHA-256 values for each of the seven releases. Each agrees with the recorded exact-version source manifest. I also recomputed all seven provenance-file SHA-256 values; they agree with the evidence table. For the five root-manifest trees, `versionId` and `namespace` identify the listed release and Mojmap. The 1.20 and 1.20.3 hidden decompiler provenance source maps match all six reviewed source files; their exact metadata IDs are the ones recorded by the pinned evidence. Reviewed paths are `decompiled_minecraft/<release>/mojmap/net/minecraft/{world/entity/Entity.java,world/level/LevelReader.java,client/multiplayer/ClientLevel.java,world/level/material/FlowingFluid.java,world/level/material/WaterFluid.java,world/phys/AABB.java}`.

## Source trace and fixture arithmetic

For all seven releases, `Entity.updateInWaterStateAndDoWaterCurrentPushing` calls the body scan for `FluidTags.WATER` with `0.014`; on a true return it marks `wasTouchingWater`, resets fall distance, and clears fire (with the existing splash gate). The scan returns false early only when `touchingUnloadedChunk()` is true. That predicate inflates the entity AABB by 1.0, floors the minimum X/Z, ceils the maximum X/Z, and calls `level().hasChunksAt(minX,minZ,maxX,maxZ)`.

`LevelReader.hasChunksAt(int,int,int,int)` maps the block endpoints to chunk coordinates and loops both endpoint ranges inclusively. For the supplied 0.6-wide box, the padded X interval is [14.4,17.0], so the queried chunk-X endpoints are 0 and 1. `ClientLevel.hasChunk(int,int)` returns `true` in every inspected release (verified in all distinct source-hash groups). The virtual client query therefore admits the padding guard despite the stipulated absent physical chunk X=1. This establishes a client-side predicate result, not server behavior.

The scan then deflates the actual box by 0.001. Its X sample interval is [15.401,15.999], and Z is [8.201,8.799]; floor/ceil endpoints and strict `<` loops visit only block X=15 and Z=8. Its Y interval begins at 64.001 and ends at 65.799, so it visits Y=64 and Y=65. It reads the loaded source at (15,64,8), and the AIR at (15,65,8) cannot update the maximum.

With no same fluid above, `FlowingFluid.getHeight` uses the sample's own height. `WaterFluid.Source.getAmount` is 8, and `getOwnHeight` divides by `9.0F`. Thus `intY + floatHeight` is evaluated as float before widening to double: the surface is 64.888885498046875. The inclusive `surface >= deflatedBox.minY` check passes; the positive contribution is approximately 0.8878855, the return flag becomes true, and the stored maximum is this sample's depth. The optional stacked-water control is not used. The calculation does not substitute an above-water surface or a false final maximum.

## Review boundary

This ACCEPT covers only the exact fixture and seven named release proofs. It does not infer patch boundaries between or beyond inspected releases; it does not accept fluid impulse behavior, eye-fluid state, boats/vehicles, non-player entities, or a behavior change. No product source, Git history, backlog, wiki, tests, builds, launches, network, or decompilation were consulted or changed.
