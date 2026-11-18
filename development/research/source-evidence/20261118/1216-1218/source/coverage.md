# Fluid exposure and ordinary water/lava travel coverage

Status: FAILED for full requested bounded contract; source-code slices are complete and show no difference, but 1.21.7→1.21.8 vanilla resource defaults could not be checked because the exact 1.21.8 client artifact is absent from its provenance-recorded staging cache. No finding draft: every scoped Java source file is byte-identical across all three releases.

## Frozen scope

The operation/caller contract and exact source roster were frozen in [scope-freeze.md](scope-freeze.md) before terminal method bodies were compared (SHA-256 `08152FCC8A085D5702A12D25B0CBE84A70A0E750C54766DFF2D12C47FDD4501D`). Scope: direct local-player body water/lava contact and height/current preflight, accumulation and scale; eye fluid sample and temporal state; ordinary non-ability-flight fluid travel; narrow FlowingFluid/FluidState and vanilla fluid tags/dimension defaults consumed by those calls. Datapack tag membership is conditional. Portals, bubble/block callbacks, equipment/ability/sprint transitions and solid geometry are excluded.

## Exact source and input identities

1.21.6 / Mojmap: provenance SHA-256 `56B036C61094D1D936882BDF5B2B59044512189CB33ADBB075880C727DA9B19E`. Raw `client.jar` SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`; raw `client.txt` Mojmap SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`; exact version metadata SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`. Each raw file is present locally and its bytes match its `.pin.json` / provenance identity.

1.21.7 / Mojmap: provenance SHA-256 `45BF296D921B2239FD0AEDB2684E2C1CB9B7D7532B4106B8A2FD9B09309CA82D`. Raw `client.jar` SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`; raw `client.txt` Mojmap SHA-256 `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`; exact version metadata SHA-256 `b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a`. Each raw file is present locally and its bytes match its `.pin.json` / provenance identity.

1.21.8 / Mojmap: ready marker SHA-256 `2E3BF302503EB127061CC605797B390F5F4A4B508F09350A59D78CED857BA7D7`; provenance SHA-256 `90FCB3707371F2CFC5414E0089CFA231F505821D7ACA45211C7C816332BB4CFA`; sources manifest SHA-256 `8FFB76CEA647A2BA4FE58E000678F751BEA2C40EA6358BAE492E962ED1D9D008`; artifacts manifest SHA-256 `C8210B15012DCC4E109C2F73015B4117ABEC1F8AB04FDD375DBC11419E9E246C`. Ready/provenance identify version `1.21.8`, Mojmap, and the exact run. Artifact manifest pins client jar SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f` and `client_mappings.txt` SHA-256 `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`. The artifact files are not present in the recorded staging cache, so I could not independently rehash/read the 1.21.8 client resources; the ready/artifact manifests remain the available exact pin evidence.

The actual SHA-256 hashes of all eight frozen Java files match their published source hashes in each release. All three releases have the same hash for each file:

| Source path | SHA-256, same in 1.21.6/1.21.7/1.21.8 |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `fd3f64f3739a9aded48358bd76362abf6a1248b42a636035cc5ca988b3f8238d` |
| `net/minecraft/world/level/material/FluidState.java` | `1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61` |
| `net/minecraft/tags/FluidTags.java` | `1960057695560fbedaba1c722fdb6ddc0c97a8f233726a15de0191fde242d08f` |
| `net/minecraft/world/level/dimension/DimensionType.java` | `bcfe113b27408b48c0ed09308910306ce2f85338297623bf95a967f9b194bead` |

## Compared operations

The Java bodies are byte-identical across the three pinned source trees, so neither adjacent edge changes these operations, gates, order, types, math or state writes:

- `Entity.baseTick` calls body fluid refresh, then eye-fluid refresh (`Entity.java:491-492`).
- `Entity.updateInWaterStateAndDoFluidPushing` clears heights, applies water current at `0.014`, and chooses lava push scale `0.007` when the dimension is ultra-warm, otherwise `0.0023333333333333335` (`1423-1437`). Water contact resets fall distance and updates `wasTouchingWater`; lava/current and the dimension check follow the same sequence.
- `Entity.updateFluidOnEyes` snapshots `wasEyeInWater` before clearing the tag set, samples the eye block's fluid height and adds its tags only when the surface is strictly above the eye (`1446-1457`). `isInWater` returns the `wasTouchingWater` latch and `isUnderWater` combines `wasEyeInWater` with it (`1377-1396`); `isEyeInFluid` and `isInLava` retain their same membership/first-tick gates (`1522-1527`). `Player.updateIsUnderwater` stores the current water-eye query (`Player.java:377-380`).
- `Entity.updateFluidHeightAndDoFluidPushing` keeps its unloaded-chunk gate, `0.001` box deflation, floor/ceil scan bounds, per-cell height comparison/max accumulation, current averaging, player-specific non-normalization, scale, `0.003` low-velocity test, `0.0045000000000000005` minimum, delta write and tag-height write (`3414-3478`).
- `LocalPlayer.tick` reaches `super.tick`; local-player `aiStep` reaches `super.aiStep`. `LivingEntity.aiStep` dispatches `this.travel(...)` in the ordinary simulation path (`LocalPlayer.java:197-202, 683-835`; `LivingEntity.java:2721-2823`).
- `Player.travel` delegates to ordinary superclass travel outside passenger/flight wrapper branches; `Player.isAffectedByFluids` is false only while abilities are flying (`Player.java:1438-1460, 1033-1035`). With ability flight excluded, `LivingEntity.travel` selects fluid travel only for body water/lava contact, fluid effect eligibility and failure of `canStandOnFluid`; otherwise it selects fall-flight or air travel (`LivingEntity.java:2245-2252`). `travelInFluid` water and lava arithmetic/order are identical (`2301-2352`); `getFluidFallingAdjustedMovement` has identical sprint and numeric gates (`2458-2471`).
- `FluidState` delegates height to the fluid type and tag checks to registry holders (`53-59,108-118,141-143`). `FlowingFluid.getFlow` and its adjacent-fluid/current effects are unchanged (`54-100`); the adjacent `affectsFlow` gate is at `101`. Height (`hasSameAbove ? 1.0F : ownHeight`) and amount conversion (`amount / 9.0F`) are unchanged (`434-440`). `FluidTags` still names the WATER/LAVA keys (`8-9`). `DimensionType.ultraWarm` access is unchanged.

## Vanilla resource defaults and edge closure

The exact local 1.21.6 and 1.21.7 raw jars both contain identical `data/minecraft/tags/fluid/water.json` membership (`minecraft:water`, `minecraft:flowing_water`), `lava.json` membership (`minecraft:lava`, `minecraft:flowing_lava`), and Nether dimension default (`ultrawarm: true`). Resource SHA-256s in both jars: water `DCFA69A748D03DBF788D8F7B0E5EB6C8DE6355A527FCAF74B9210FE4BE2A3004`; lava `71F50FB9092D78260BC7434731FC5FD426A44E5284A6AD084EC71CB725630C6B`; Nether dimension `26953E0426B058A44A4C1F2060A1A15823C03DD511F57DD7EF97DFA6327A3825`.

Examined and closed: all frozen Java slices on both edges; exact source hashes and raw client/mapping/version pins for 1.21.6→1.21.7; vanilla default tag membership and Nether ultra-warm setting for 1.21.6→1.21.7. No difference found in these slices.

Unexamined/open: exact 1.21.8 client resource entries for fluid tag default membership and dimension defaults. The 1.21.8 raw client SHA-256 is pinned by local manifests, but the raw artifact is absent from the provenance-recorded staging cache and was not otherwise available locally. The Java source equality does not prove resource equality, so 1.21.7→1.21.8 vanilla defaults are not claimed covered. Custom datapack membership remains conditional in every version.

No product code, history, wiki, research/backlog, agent outputs, network, tests or build were used.

