# 26.1.2–26.2 source-only water-travel slice

## Exact evidence identity

Both endpoints are ready exact `unobfuscated` Vineflower trees (Java 25). Ready-marker version/status/mapping agree: `26.1.2` / `ready` / `unobfuscated`; `26.2` / `ready` / `unobfuscated`. Each marker-declared source manifest exists and its SHA-256 equals the marker declaration; all listed Java source entries were rehashed and matched (6,882 / 6,882 and 7,055 / 7,055). Each marker-declared artifact manifest exists and its SHA-256 equals the marker declaration.

| Version | Ready marker | Provenance | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 26.1.2 | `unobfuscated.ready.json`; client SHA `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | `unobfuscated.provenance.json`: exact run, `unobfuscated`, Java 25.0.3+9-LTS, Vineflower 1.12.0, TinyRemapper 0.14.1 | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |
| 26.2 | `unobfuscated.ready.json`; ready marker has no client SHA field | `unobfuscated.provenance.json`: exact run, `unobfuscated`, Java 25.0.3+9-LTS, Vineflower 1.12.0, TinyRemapper 0.14.1 | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |

## Bounded coverage and result

**Examined:** ordinary `LivingEntity.travelInWater` horizontal speed/slowdown selection and order; its `moveRelative` call, movement call, and horizontal post-move drag; `Entity.moveRelative` / `getInputVector`; `Player.travel` delegation gates; `Entity.updateFluidInteraction` water-current application gate/scale. `LocalPlayer` has no declared `travel` override in the exact source; ordinary water travel resolves through inherited `Player`/`LivingEntity` methods.

**No owned-slice difference found.** In both versions water travel starts with sprint-dependent `slowDown` (`0.9F` vs `getWaterSlowDown()`) and `speed=0.02F`; halves `waterWalker` when airborne; for positive efficiency blends slowdown toward `0.54600006F` and speed toward `getSpeed()` in that order; Dolphin's Grace then sets slowdown to `0.96F`. It calls `moveRelative(speed,input)`, moves using current delta, then multiplies X/Z by slowdown and Y by `0.8F` before the later fluid-falling adjustment. The endpoint code and operation order match. The only local difference in this region is the post-move temporary name (`ladderMovement` in 26.1.2, `movement` in 26.2); the climbable vertical adjustment itself is outside this assignment.

`Entity.moveRelative` and `getInputVector` match: ignore squared input length below `1.0E-7`; normalize only above `1.0`; scale by float speed; rotate by yaw using float sine/cosine; add the resulting delta to velocity. The water-current consumption call also matches: when `isPushedByFluid()` and `inWater`, call `fluidInteraction.applyCurrentTo(FluidTags.WATER, this, 0.014)`.

`Player.travel` has the same passenger/super delegation and flying handling at both endpoints. It also has the same swimming vertical adjustment before `super.travel`; vertical adjustment was inspected only to bound the method and is not adjudicated here. No horizontal water-specific override appears in `Player.travel`.

## Partial and unexamined

**Partial:** the owned methods call into movement input, movement/collision, fluid-falling adjustment and fluid-current application; this report establishes their call-site ordering/parameters only. Water-movement-efficiency attribute semantics, `getWaterSlowDown` implementations and modifiers, Dolphin's Grace semantics, and effective fluid/effect/resource inputs remain open. Current-vector production and `FluidInteraction` internals were not examined. The audited tree does not establish all callers or tick-path dependencies.

**Unexamined/out of assignment:** fluid exit impulse and `jumpOutOfFluid`; vertical water movement adjustment; current-vector production and fluid-state aggregation; generic travel branch selection; player input production and collision internals; all other movement areas and intermediate releases. This is endpoint-only evidence, not full-pair coverage, acceptance, implementation, or runtime parity.

## Exact source references and Java-file hashes

All paths are relative to `decompiled_minecraft/<version>/unobfuscated/`.

| Version | Source/member/range | Java file SHA-256 |
|---|---|---|
| 26.1.2 | `net/minecraft/world/entity/LivingEntity.java`, `travelInWater`, 2467–2494 | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java`, `travelInWater`, 2516–2543 | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.1.2 | `net/minecraft/world/entity/Entity.java`, `moveRelative` / `getInputVector`, 1659–1674 | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.2 | `net/minecraft/world/entity/Entity.java`, `moveRelative` / `getInputVector`, 1746–1761 | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.1.2 | `net/minecraft/world/entity/Entity.java`, `updateFluidInteraction`, 1558–1581 | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.2 | `net/minecraft/world/entity/Entity.java`, `updateFluidInteraction`, 1645–1668 | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.1.2 | `net/minecraft/world/entity/player/Player.java`, `travel`, 1381–1404 | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.2 | `net/minecraft/world/entity/player/Player.java`, `travel`, 1402–1425 | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
