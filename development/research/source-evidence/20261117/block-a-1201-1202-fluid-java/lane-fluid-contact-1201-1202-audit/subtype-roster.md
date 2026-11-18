# Frozen subtype roster — ordinary water/lava flow dispatch, 1.20.1 → 1.20.2

Scope is only the concrete source/flowing water and lava type edge to the shared current-vector implementation, through `FluidState.getFlow` and `FlowingFluid.getFlow`. Canonical source roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.1/mojmap/` and `.../1.20.2/mojmap/`, Mojmap.

Release/export identity is inherited from the already verified [pre-claim roster addendum](source-roster-addendum.md), SHA-256 `cd8c5bf193416dc8bc35764b12a1a8cbe3cc740508aa425ea440b76e1346e75b`, and the corrected claim SHA-256 `74afdce1697062534c51aad9b6c8a9efff5d6b8ae8ff5e6519d6315cbf57b488`. That addendum binds the version-root ready/provenance markers, Mojmap namespace and source-manifest identities. Each file below was rehashed locally and its digest matched its entry in the corresponding exact version's `mojmap.sources.sha256`.

| Source relative to each `mojmap/` root | 1.20.1 SHA-256 | 1.20.2 SHA-256 | Subtype edge inspected |
|---|---|---|---|
| `net/minecraft/world/level/material/WaterFluid.java` | `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` | `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` | `WaterFluid extends FlowingFluid`; nested `Flowing` and `Source` subclasses |
| `net/minecraft/world/level/material/LavaFluid.java` | `9a409eccb9b112d615e2204a1058658afecabf1a18d79a5665f7d30bb79c9c74` | `9a409eccb9b112d615e2204a1058658afecabf1a18d79a5665f7d30bb79c9c74` | `LavaFluid extends FlowingFluid`; nested `Flowing` and `Source` subclasses |
| `net/minecraft/world/level/material/Fluids.java` | `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931` | `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931` | Registry bindings for source/still and flowing instances |
| `net/minecraft/world/level/material/FluidState.java` | `0d6d1d24deB58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` | `0d6d1d24deB58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` | `getFlow` virtual dispatch from the state fluid type |
| `net/minecraft/world/level/material/FlowingFluid.java` | `ba1dff6ccb28b62760c74d66832d8fdbe7a1cdc37b9c5a0ff476bd200215c904` | `0f44b1af25533dd8a1fbfec13b279739341db0c563568337a2825d3e46a1a5bb` | Shared `getFlow`, same-fluid predicate and solid-face helper; whole file differs across pair |

Relevant members are `WaterFluid.java` lines 28, 30–36, 94–95, 123–151; `LavaFluid.java` lines 27, 30–37, 150–151, 225–269; `Fluids.java` lines 8–11; `FluidState.java` lines 91–92; `FlowingFluid.java` lines 54–103 and 105–115. This roster does not include resource tag data; effective water/lava tag membership remains separate and unresolved.