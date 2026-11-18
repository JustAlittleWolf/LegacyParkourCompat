# Independent blind source audit: fluid movement, 1.21.6–1.21.8

## Verdicts

- **Body fluid contact, height, current, and current-scale operation:** **No source difference** on 1.21.6→1.21.7 and 1.21.7→1.21.8. Exact `Entity.java`, fluid helper and dimension accessor source files have identical bytes at all three endpoints. Vanilla WATER/LAVA tag and built-in dimension resource members are byte-identical between 1.21.6 and 1.21.7. The 1.21.7→1.21.8 raw-resource input gate remains open because the exact 1.21.8 client jar is not present locally.
- **Eye fluid sampling and temporal state:** **No source difference** on both edges. `Entity.java`, `Player.java`, and `LocalPlayer.java` are exact byte matches across all three releases. The default 1.21.7→1.21.8 raw input gate remains open as above.
- **Ordinary non-ability-flight water/lava travel:** **No source difference** on both edges. `Player.java` and `LivingEntity.java`, including gates, water and lava branches, threshold helper and falling adjustment, have identical bytes at all three releases. Raw 1.21.8 client/mapping/version inputs remain an external evidence gate; code comparison itself is complete for the frozen operation.

These are operation-level source verdicts, not runtime parity claims. There is no independent finding from this bounded comparison. Vanilla tag membership is established only for the inspected bundled defaults; custom datapack membership remains conditional.

## Frozen scope and evidence identity

The comparison follows only the frozen operation/caller roster at `frozen-roster.md` SHA-256 `D3A172F7F169DA08F8979EA09411F651AD09590D20ABCC83E99DB5E31FCF4FD8`, with append-only corrections/addenda:

- `roster-correction.md` — `5192181DAB36E523109DF73E630ABAB6BBB5F6E724904356BECDF0E9685E9EC2`.
- `roster-hash-completion.md` — `11126612EC8E4A4005D758EA336006FF4F7B466EF13250B739B101FC9FBD1439`.
- `height-dependency-addendum.md` — `9B3BE15A1722D10071AB7926372A1D0FED47B6925E33898F3702212E5D3B55A6`.
- `tag-dimension-resource-addendum.md` — `4C4341D615AA4D3F716C0ABFB340B8A8FEF880C69CD1DBF9A4FBFDCD5FDEB74F`.

On 1.21.6 and 1.21.7, the actual whole-file `.decompile-provenance.json` hashes match the assigned pins `56B036C61094D1D936882BDF5B2B59044512189CB33ADBB075880C727DA9B19E` and `45BF296D921B2239FD0AEDB2684E2C1CB9B7D7532B4106B8A2FD9B09309CA82D`. The corresponding local client jar, Mojmap text and version metadata bytes each match their `.pin.json` SHA-256 and provenance input. Those two versions' raw evidence is complete.

For 1.21.8, actual `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256` hashes are respectively `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`, `90fcb3707371f2cfc5414e0089cfa231f505821d7aca45211c7c816332bb4cfa`, `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`, and `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`. Marker claims exact `versionId=1.21.8`, raw client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`, and the artifact manifest lists client mapping SHA-256 `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`. The cache has no 1.21.8 `client.jar`, `client_mappings.txt` or `version.json`; accordingly those raw bytes cannot be recomputed, the 1.21.8 bundled tag/dimension resource members cannot be checked, and the marker's version ID is not an independent raw version metadata pin. Preserve these gates.

## Compared code and source hashes

All actual files below match the exact release's `.decompile-provenance.json` (1.21.6/1.21.7) or `mojmap.sources.sha256` (1.21.8). For every row, the 1.21.6, 1.21.7 and 1.21.8 actual SHA-256 is the same value shown:

| Frozen source file (relative to each `<version>/mojmap/`) | SHA-256 |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `fd3f64f3739a9aded48358bd76362abf6a1248b42a636035cc5ca988b3f8238d` |
| `net/minecraft/world/level/material/FluidState.java` | `1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61` |
| `net/minecraft/world/level/material/Fluids.java` | `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931` |
| `net/minecraft/world/level/material/Fluid.java` | `e98bdfd14b3092096c6ba763642c3724f852b7589edbf0052b2c8767a874bf94` |
| `net/minecraft/world/level/material/WaterFluid.java` | `9a829ec0d8083d6a3e73a98d40d6c674fd3431aafdb7d05a78cca1daff6e1674` |
| `net/minecraft/world/level/material/LavaFluid.java` | `04134f233b68e7a8856fbf410414eca8ee75f6acb565276d4c4859dc28a6308c` |
| `net/minecraft/tags/FluidTags.java` | `1960057695560fbedaba1c722fdb6ddc0c97a8f233726a15de0191fde242d08f` |
| `net/minecraft/data/tags/FluidTagsProvider.java` | `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7` |
| `net/minecraft/world/level/Level.java` | `47e9510cc54f8463faa0c63f389e9222cecf75372fc4ca059724d350bdb6be19` |
| `net/minecraft/world/level/biome/Biome.java` | `6e6936b604265e3f4985ca93ff8f1977da1eda64cf98c4df9e360729ea2991e5` |
| `net/minecraft/world/level/dimension/DimensionType.java` | `bcfe113b27408b48c0ed09308910306ce2f85338297623bf95a967f9b194bead` |
| `net/minecraft/world/level/dimension/BuiltinDimensionTypes.java` | `4971dde1dd0b3b15290b84ba3a4396b0e770d67e30f48e84a59fe82c64dac89c` |
| `net/minecraft/world/level/dimension/DimensionDefaults.java` | `1d8bf277044f8a9470c0fae96a9ab7e408de1df1a8ca907444f7ad4df545119d` |

### Body-water/lava contact, surface height and current

At `Entity.baseTick` (`Entity.java:472`, calls at 491–492), water/lava contact and eye sampling precede swimming updates. `updateInWaterStateAndDoFluidPushing` (`:1423`) clears `fluidHeight`, executes water first at `0.014`, then selects the lava scale from `dimensionType().ultraWarm()` (`0.007` versus `0.0023333333333333335`) and scans lava. The water helper clears contact for a dry non-underwater boat; otherwise a positive water scan resets fall distance and stores `wasTouchingWater`, with a splash only on new contact after first tick. Lava contact reads stored lava height and is false on first tick (`:1526`).

`updateFluidHeightAndDoFluidPushing` (`:3414`) returns false immediately for touching an unloaded chunk; otherwise it scans the bounding box deflated by `0.001`, with integer loop bounds from floor(min)/ceil(max). For matching tagged cells whose surface reaches the deflated box minimum, it marks contact, accumulates the maximum surface penetration, and—only when `isPushedByFluid()`—samples flow, scales each flow by penetration below `0.4`, adds it and counts it. The final nonzero sum is averaged by sample count, normalized for non-players only, scaled by the caller force, and receives the minimum-current floor `0.0045000000000000005` only when both prior horizontal velocity components are below `0.003` and scaled current length is smaller. It then adds to velocity and stores the fluid height. Player's `isPushedByFluid` is false only for ability flight (`Player.java:1783`), a frozen excluded transition/state.

`FluidState.getHeight` dispatches to fluid type; `FlowingFluid.getHeight` uses `1.0F` when the same fluid continues above or `getOwnHeight`, which is amount divided by `9.0F` (`FlowingFluid.java:434–439`). `FlowingFluid.getFlow` (`:54`) differences horizontal neighboring fluid heights, accounts for matching fluid beneath an unblocked neighbor, and applies downward flow on falling fluid next to solid faces before normalizing. Solid geometry/world arrangement was not reviewed; the method source itself is byte-identical. All body scan/current operands, call order, tags, scale constants and helper source bodies therefore match exactly across both edges.

### Eye sample and temporal state

`Entity.updateFluidOnEyes` (`Entity.java:1446`) first latches `wasEyeInWater` from the prior `fluidOnEyes`, clears the tag set, computes eye Y, suppresses sampling only for the intersecting dry boat condition, reads exactly the fluid state at `(x, eyeY, z)`, and adds all that state's tags only when `cellY + getHeight(...) > eyeY`. Thus the tag set is refreshed after the `wasEyeInWater` latch. `isEyeInFluid` reads that set (`:1522`); `isUnderWater` also requires current body-water contact (`:1394–1395`). `Player.tick` calls `updateIsUnderwater` before `super.tick` (`Player.java:281–308`); normal Player sets `wasUnderwater` from the current eye tags (`:377–379`), and LocalPlayer delegates then layers sound effects (`LocalPlayer.java:1113–1129`). `LocalPlayer.tick` enters `super.tick` only on its loaded path (`LocalPlayer.java:197–201`). This state/sample ordering and its boundaries are identical in all three exact files.

Bundled default `FluidTagsProvider` assigns the WATER tag to still+flowing water and LAVA to still+flowing lava. The corresponding `1.21.6/client.jar` and `1.21.7/client.jar` `data/minecraft/tags/fluid/{water,lava}.json` entry bytes have hashes `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` (water) and `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b` (lava) and are byte-identical. Custom datapack expansion/override is conditional and not inferred.

### Ordinary local-player fluid travel

`LocalPlayer` has no `travel` override in the reviewed class; its inherited `Player.travel` is the caller (`Player.java:1438`). The passenger path immediately delegates; otherwise swimming adds look-relative vertical correction, then ability flight has a separate branch. For ordinary non-ability flight, `Player.travel` delegates to `LivingEntity.travel`. Player's `isAffectedByFluids()` is `!abilities.flying` (`Player.java:1033`), while `LivingEntity.travel` checks body water-or-lava, affected-by-fluids and `!canStandOnFluid(block-position state)` before fall-flying/air branches (`LivingEntity.java:2245–2250`). Its `canStandOnFluid` default is false (`:2231`). The water branch (`:2301–2332`) uses sprint-dependent drag, water slowdown, movement-efficiency attribute halved off-ground, optional Dolphin's Grace drag, then input movement, collision/climbable response, horizontal drag with vertical factor `0.8F`, and fluid falling adjustment. The other branch is lava: input movement at `0.02F`, movement, the `getFluidHeight(LAVA) <= getFluidJumpThreshold()` gate selects `(0.5,0.8F,0.5)` drag plus falling adjustment or half scaling; nonzero gravity adds `-gravity/4`; the shared horizontal-collision step-up response follows. `getFluidJumpThreshold` is `0.0` below eye height `0.4`, else `0.4` (`Entity.java:3494`); the fall adjustment honors nonzero gravity and non-sprinting state (`LivingEntity.java:2458`). Branch order/gates/math are byte-identical. Sprint, equipment/attributes, flight transitions and collision geometry were not independently modeled.

## Resource defaults and limits

The exact 1.21.6 and 1.21.7 client jars contain byte-identical bundled defaults for all six frozen `water.json`, `lava.json`, and dimension-type members. In those dimension JSON files, `ultrawarm` is false in overworld, overworld caves and the End, and true in the Nether. The movement code uses only the `DimensionType.ultraWarm` value for the lava current force; the exact same selected value and force rule apply at both source edges. 1.21.8 resource bytes remain blocked because only its pinned artifact digests, not the raw client jar, are locally available.

The frozen biome source was checked by hash but no selected movement call in this slice reads a biome. The frozen Fluid/Water/Lava classes are direct dependencies of the flow/height dispatch; no fluid tick, block callback or flow-world geometry behavior is claimed outside those consumed helpers. There is no gameplay, build, or test evidence in this audit.
