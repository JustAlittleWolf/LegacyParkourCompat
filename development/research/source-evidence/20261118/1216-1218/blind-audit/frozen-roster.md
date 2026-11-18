# Frozen blind audit roster — fluid movement 1.21.6→1.21.7→1.21.8

Status: frozen before reading any compared source bodies or author/adjacent audit output.
Host/shell: Windows / PowerShell. Primary checkout: `D:\Javastuff\LegacyParkourCompat`.

## Bounded operation and caller

Audit only direct local-player fluid movement across adjacent edges 1.21.6→1.21.7 and 1.21.7→1.21.8:

1. Entity body-water/lava contact scan: contact booleans, sampled surface height, current preflight/accumulation/scaling, and state writes consumed by local player movement.
2. Entity eye-in-fluid sampling and temporal contact state consumed by player movement.
3. Ordinary non-ability-flight water/lava player travel branch, including its stable reachable preconditions and any directly called fluid-current helper.

Caller/source roster per exact release (`decompiled_minecraft/<version>/mojmap/`): `net/minecraft/client/player/LocalPlayer.java`, `net/minecraft/world/entity/player/Player.java`, `net/minecraft/world/entity/LivingEntity.java`, `net/minecraft/world/entity/Entity.java`, `net/minecraft/world/level/material/FlowingFluid.java`, `net/minecraft/world/level/material/FluidState.java`, `net/minecraft/world/level/material/Fluids.java`, `net/minecraft/tags/FluidTags.java`, `net/minecraft/data/tags/FluidTagsProvider.java`, `net/minecraft/world/level/Level.java`, `net/minecraft/world/level/biome/Biome.java`, `net/minecraft/world/level/dimension/DimensionType.java`, and `net/minecraft/world/level/dimension/BuiltinDimensionTypes.java` if present. Read only slices and dependencies needed by the stated calls. Vanilla tag/dimension defaults are in scope; datapack-defined membership is conditional. Explicitly excluded: portals, bubble columns, block callbacks, equipment, ability/sprint transitions, solid geometry, non-player fluid physics, broad lifecycle inventory.

## Exact evidence roster

Each version uses the canonical local decompile `decompiled_minecraft/<version>/mojmap/`. Exact source members are frozen by these path/hash pairs (SHA-256), as recorded in the release source manifest; recheck actual file hashes after source review. Rows are shared across 1.21.6, 1.21.7, and 1.21.8 unless otherwise marked:

- `net/minecraft/client/player/LocalPlayer.java` — `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee`
- `net/minecraft/world/entity/Entity.java` — `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`
- `net/minecraft/world/entity/LivingEntity.java` — `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`
- `net/minecraft/world/entity/player/Player.java` — `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`
- `net/minecraft/world/level/material/FlowingFluid.java` — `fd3f64f3739a9aded48358bd76362abf6a1248b42a636035cc5ca988b3f8238d`
- `net/minecraft/world/level/material/FluidState.java` — `1bfc76676cc7cf136220c05f3506ae4d931cfa33ce0833d307b28048a5d28c61`
- `net/minecraft/world/level/material/Fluids.java` — verify against release manifest before consumption.
- `net/minecraft/tags/FluidTags.java` — `1960057695560fbedaba1c722fdb6ddc0c97a8f233726a15de0191fde242d08f`
- `net/minecraft/data/tags/FluidTagsProvider.java` — `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7`
- `net/minecraft/world/level/Level.java` — `47e9510cc54f8463faa0c63f389e9222cecf75372fc4ca059724d350bdb6be19`
- `net/minecraft/world/level/biome/Biome.java` — `6e6936b604265e3f4985ca93ff8f1977da1eda64cf98c4df9e360729ea2991e5`
- `net/minecraft/world/level/dimension/DimensionType.java` — `bcfe113b27408b48c0ed09308910306ce2f85338297623bf95a967f9b194bead`
- `net/minecraft/world/level/dimension/BuiltinDimensionTypes.java` — include only if reachable defaults require it; verify against release manifest before consumption.

Release source pins and raw inputs:

- 1.21.6: `decompiled_minecraft/1.21.6/mojmap/.decompile-provenance.json`, whole-file expected SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`. Its local raw client input is `client.jar` SHA-256 `9592df106???`; its Mojmap text input is `client.txt` SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` (from provenance; exact value to validate against local pin files).
- 1.21.7: `decompiled_minecraft/1.21.7/mojmap/.decompile-provenance.json`, whole-file expected SHA-256 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`. Its local raw client input is `client.jar` SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`; Mojmap text input `client.txt` SHA-256 `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`.
- 1.21.8: version-level evidence at `decompiled_minecraft/1.21.8/mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. Expected source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; artifact-manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; ready-marker pinned raw client SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`; artifact entries include raw `1.21.8/client.jar` with that hash and `1.21.8/client_mappings.txt` SHA-256 `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca` plus mapped jar `1.21.8/client-mojmap.jar` SHA-256 `f6f0339631f43b38ce5fe7511603cde17aa4c804ad627d933835f228003adb85`.

Local raw-pin proof files to verify after freeze: `.minecraft-decompile-cache/1.21.6/{client.jar.pin.json,client_mappings.txt.pin.json,version.json.pin.json}` and same for 1.21.7. For 1.21.8, verify the ready marker/provenance/manifests above; report any unavailable independent raw metadata pin as an explicit source-evidence gate.

## Audit method and gates

Compare enclosing callers and exact branch gates/order, casts, numeric operations, reads and writes. Trace only the narrowly called `FlowingFluid`, tag, and dimension defaults required to establish behavior. Record each edge/operation separately as difference, no difference, or blocked/partial. Establish actual source-body hashes against the exact manifest before citing or accepting a claim. No wiki, product, Git/history, research/backlog, online mappings, external network, implementation, tests, builds, source generation, or gameplay launch.

Frozen roster created before compared source-body review. Freeze artifact itself is immutable; any later scope change requires a separate addendum, not editing this file.
