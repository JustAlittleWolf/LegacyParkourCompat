# Addendum: 1.13.2 structure-feature caller reachability

This evidence-only addendum is attached to author report SHA-256 `577bae5780ba1e637739219319f1a11a53ea2916c62bcbe05677c737705b0e1c` and independent verdict SHA-256 `8578d88544b7561952a136d0c9f6338ebf2c1f0136865e8ff0ada9351b36c7cf`. It addresses only the requested source binding for forwarding the exact `WorldGenRegion` object into structure-feature placement. It does not change the raw-write comparison or its movement-relevance limit.

## Forwarded world object

The endpoint's reachable decoration chain is:

1. `DecorateChunkTask.run` constructs `new WorldGenRegion(...)` and passes it to `generator.decorate(region)`; `AbstractChunkGenerator.decorate(WorldGenRegion)` forwards that `region` as `world` to `biome.placeFeatures(...)`. These methods and their pinned hashes are in the original report.
2. `Biome.placeFeatures` calls each `configuredFeature.place(world, ...)`, forwarding the same `WorldAccess` reference. The biome source configures actual structure entries such as `Feature.VILLAGE`, `Feature.MINESHAFT`, `Feature.STRONGHOLD`, and `Feature.WITCH_HUT` with `NOPE_DECORATOR` (`Biome.java` lines 312–359); `NOPE_DECORATOR` is a `NopeDecorator` instance (line 158).
3. `ConfiguredFeature.place` forwards that same `world` to `this.decorator.place(world, ...)` (lines 25–29). `NopeDecorator.place` forwards it to `feature.place(world, ...)` (lines 11–21). `Feature.place` is the `WorldAccess`-accepting method contract (line 161); `StructureFeature.place` overrides it and uses `world` for `StructureStart.postProcess` (the latter method and hash are included in the original report's pinned sources).

Thus this is a concrete 1.13.2 source route that passes the `WorldGenRegion` into a configured structure feature and then through the structure feature's placement entry point. The raw `StructurePiece`→`WorldGenRegion.setBlockState` evidence in the original report applies on this caller route. This establishes caller reachability only; it does not establish a specific stale provider state, structure instance witness, or player movement difference.

## Exact source identity bindings

All four file digests below were checked against the endpoint's `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256` manifest and the source bytes.

| Source file | Anchors | SHA-256 |
|---|---|---|
| `net/minecraft/world/biome/Biome.java` | `NOPE_DECORATOR` declaration 158; structure configured features 312–359; `placeFeatures` 481–495 | `4126ed24002f6797f11b307f8787b716dced2ae98c17797425d1ce483f26c996` |
| `net/minecraft/world/gen/feature/ConfiguredFeature.java` | `place` 25–29 | `5aca1702cda51aaa00a14b511ec453e2c7edd59d41772285c0d77155a69837a5` |
| `net/minecraft/world/gen/feature/decorator/NopeDecorator.java` | `place` 11–21 | `a0f8b36d55a94b7742df87be7ec6bb689f0c584a9a1c2d88ced1b71cac05c6d0` |
| `net/minecraft/world/gen/feature/Feature.java` | structure feature fields 50–63; abstract `place(WorldAccess, ...)` 161 | `61bc91bbb0a859eae11edfa7f7ba7631146d2c16a188a340bb424e6ef37cd589` |

**Disposition:** caller-world forwarding is source-bound. Original report's technical scope and conclusion remain unchanged: raw write refresh bypass confirmed, movement-relevant stale-state witness unproven.
