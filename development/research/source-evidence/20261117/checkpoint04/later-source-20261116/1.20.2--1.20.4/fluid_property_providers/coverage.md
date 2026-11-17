# Water and lava movement property providers

## Bounded result

The examined water/lava fluid-state providers and resource tag payloads are equal between exact releases 1.20.2 and 1.20.4. No movement difference was found in this slice. This is not a full-pair freeze, an intermediate-patch boundary claim, or runtime evidence.

## Exact source identities

Both endpoints use the published Mojmap namespace. The source trees are `decompiled_minecraft/1.20.2/mojmap` and `decompiled_minecraft/1.20.4/mojmap`. Their root-sidecar identities are:

| Release | ready marker SHA-256 | provenance SHA-256 | source manifest SHA-256 | artifact manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.20.2 | `5FF3322F287C250EE914C7CA3840D053BFFC3C2C5F35E91B59314D948292483F` | `1DAD9F725F6CB25B6568700FA0294F83B66EA9633B0D62ED354D7BD8B45D4F05` | `E439FF7E1D3F1D31E01BE1EDD141F94D917A9DC7FA0DE5F69904544CBBE62887` | `EE67DA9733953B30AE4077C587184E59772B745D780ABD27A7B023780B459F54` |
| 1.20.4 | `6225B97979420CC743C8B7156A0CE512EF27D0DBF48BFFFBC40D3E1C8CB044D1` | `10914D9B7B3FC29DE066CA24F7D905E4D4F0A451A6C81729A2D1981ADDFC1385` | `FD3C8668483E5FF208C847474F6CBD3952A909C4AA602ED98B24E3D11A9602A1` | `EE3EFC771D264C0BC49D5472ABB9763A646F8D221841D0A61201ECD991CB3948` |

The ready files identify the exact release and `mojmap`, and bind the source and artifact manifest digests shown above. All cited source hashes below were checked against those source manifests and the files on disk.

## Paired source evidence

The following exact Java files have the same SHA-256 bytes in both endpoint source trees:

| File (relative to each Mojmap source root) | SHA-256 at both endpoints | Movement role inspected |
| --- | --- | --- |
| `net/minecraft/world/level/material/Fluid.java` | `50EA2DADFF56228AFF11147C6DA9FAB224C7241D3B54BC4025B4D38D3D470546` | Base fluid contract for flow, height, own-height, source and amount. |
| `net/minecraft/world/level/material/FluidState.java` | `0D6D1D24DEB58F056163ACC3AE8976D2C7B3394ACA2AE37FA43BC560C9D33B7C` | State delegation for type, source, amount, height, flow, and fluid-tag membership (notably lines 33–59, 91–105). |
| `net/minecraft/world/level/material/FlowingFluid.java` | `0F44B1AF25533DD8A1FBFEC13B279739341DB0C563568337A2825D3E46A1A5BB` | Horizontal neighbor-height accumulation and falling-fluid wall adjustment in `getFlow` (lines 54–103); source/flowing height and amount-derived own height (421–440). The vector is normalized after the same ordered horizontal scan and, for falling states, the same `-6.0` vertical adjustment when a side wall is present. |
| `net/minecraft/world/level/material/WaterFluid.java` | `3E10320356B11D415D8E4F3EA2B7FBB27E5BD8ABDC45D524AFB18B834E613E5F` | Water source/flowing identities, `LEVEL` amount and source predicates, flow spread distance/drop-off/tick delay, and tag-based replacement predicate (lines 28–151). |
| `net/minecraft/world/level/material/LavaFluid.java` | `9A409ECCB9B112D615E2204A1058658AFECABF1A18D79A5665F7D30BB79C9C74` | Lava identities, amount/source predicates, height cutoff and water-tag predicate, plus its dimension-selected spread constants (lines 27–28, 140–181, 225–253). These world-flow properties were checked as provider values; no world-tick reachability claim is made. |
| `net/minecraft/world/level/material/Fluids.java` | `913CB5D937AF92A7F90F7DC233A3D02C05F75E70A2A740300B2F26EEDCBED931` | Built-in registration of empty, flowing/source water, and flowing/source lava (lines 6–23). |
| `net/minecraft/tags/FluidTags.java` | `DF79262149BF0E684D877CA5D2BB87F02D8B66D55ED123D4C2E93BFA6462396F` | `WATER`/`LAVA` tag keys and default-namespace key creation (lines 7–16). |

`net/minecraft/world/level/block/LiquidBlock.java` has different full-file hashes: `83B0520E518596CCE0AC89654EFCD32B4AD00BB89F088E50B175BCF11728D762` (1.20.2) and `04384008E492D577C41D884A636417CCC32DBFF816FD15AF801766FCB2D8290D` (1.20.4). The exact diff adds only the 1.20.4 codec imports, codec field and `codec()` method; the shared movement-relevant constructor/state cache and `getFluidState` are unchanged. At 1.20.2 lines 42–54 and 84–87, and 1.20.4 lines 58–70 and 100–103, the constructor caches source state at level 0, flowing levels 1–7 using `getFlowing(8-level,false)`, and the falling level-8 state; `getFluidState` selects `stateCache[min(LEVEL,8)]`.

The direct vanilla water/lava block links are also identical in the compared registration excerpts: `Blocks.WATER` constructs `LiquidBlock(Fluids.WATER, ...)` (1.20.2 lines 337–351; 1.20.4 lines 340–354), and `Blocks.LAVA` constructs `LiquidBlock(Fluids.LAVA, ...)` (1.20.2 lines 352–367; 1.20.4 lines 355–370). The containing `Blocks.java` whole-file hashes differ because other registrations changed: `75E8E77ADAB4A07B02EF95619BE83A004F5F79810CF3F07157A713D714881253` and `5392C4349317E55C2989865BE38C17B94E6E0543E7017E6A2A64D79217E83643` respectively. The cited excerpt bytes were compared directly and match.

## Resource-backed membership

The isolated accepted packet at `build/source-resource-inputs-20261117/movement-gates/1.20.2--1.20.4_soul_speed_resource_tags/input-reference.json` (SHA-256 `d3e72d938e977e67a6ce24236d49df819c605d52f8c739bc1288f5fca09fe791`) contains the exact extracted fluid entries for each endpoint:

- `data/minecraft/tags/fluids/water.json`, 74 bytes, SHA-256 `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`: `minecraft:water`, `minecraft:flowing_water`.
- `data/minecraft/tags/fluids/lava.json`, 72 bytes, SHA-256 `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`: `minecraft:lava`, `minecraft:flowing_lava`.

The packet's independent audit is ACCEPT: `build/source-resource-inputs-20261117/movement-gates/audit/pinned-movement-resource-packets-audit-r1.md`, SHA-256 `C047FB9849B51713F78AB3934491B1EF6A62BC55DFEA12D86F7D606C83901070`. It binds the exact pinned JAR artifacts, manifest/provenance identities, archive entries, and copied payload hashes. Its stated limitation remains: downloaded JAR paths are not provenance-recorded paths; the repository's artifact SHA-256 pin and metadata SHA-1 were checked. This package-level membership report does not cover datapack overrides or a runtime registry.

## Coverage boundary

This report covers only these provider-side Java operations and the packaged fluid tag members. It does not claim that all client fluid travel, fluid-current application, fluid-world update timing, custom datapacks or registered runtime values are equivalent. It does not inspect intermediate versions, arbitrary fluid/tag overrides, all water/lava collision shapes, or the rest of the 1.20.2→1.20.4 movement pair. Independent audit is pending.