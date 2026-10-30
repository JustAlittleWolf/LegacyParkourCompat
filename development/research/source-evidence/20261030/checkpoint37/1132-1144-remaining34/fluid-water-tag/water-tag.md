# 1.13.2 → 1.14.4 water-tag membership check

## Verdict

The witness's flowing-water membership is supported by both endpoint **data-generator defaults**, but is not proven for the active runtime `FluidTags.WATER` collection by these source trees alone. Both `FluidTagsProvider.addTags()` implementations add `Fluids.WATER` and `Fluids.FLOWING_WATER` to `FluidTags.WATER` (1.13.2 and 1.14.4 `net/minecraft/data/tag/FluidTagsProvider.java:17–20`). The new lower-cell flowing-water tag condition is therefore a reasonable vanilla-generated-data assumption, but must stay explicitly conditional in the static witness. The source assignment of the upper source-water cell is independently used for `WaterFluid.is(...)` family matching; this check makes no claim that the upper cell's active tag membership is loaded.

## Why runtime membership remains open

- In both versions `FluidTags.WATER` is a named tag handle, not a hard-coded member set. `FluidTags` starts with an empty `TagCollection`, and `FluidTag.contains` delegates to the current collection after `reset` (`net/minecraft/tag/FluidTags.java:7–16, 22–37`).
- `TagManager` loads fluid tags from its resource manager (`tags/fluids`) and then installs that collection with `FluidTags.reset` (1.13.2 `TagManager.java:11–14, 35–43`; 1.14.4 `TagManager.java:19–25, 59–92`). `TagCollection.load` enumerates resource JSON (1.13.2 `TagCollection.java:82–103`; 1.14.4 `:76–83`) and builds the resulting tags.
- The client tag handler replaces its tag manager with the received packet's manager and, for non-local connections, resets `FluidTags` from that packet collection (1.13.2 `ClientPlayNetworkHandler.java:1411–1420`; 1.14.4 `:1468–1480`).
- The inspected canonical Java trees contain no `data/.../tags/fluids/water.json`. Thus the exact generated JSON artifact, runtime resource-pack/datapack stack, registry resolution, and (for remote play) received tag packet contents were unavailable. Source code proves the vanilla provider recipe, not that a specific loaded collection contains either fluid.

## Paired source identity

Canonical roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13.2\ornithe-feather` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\ornithe-feather`.

| Source, SHA-256 | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/tag/FluidTags.java` | `b9d4c2d93f4594f27e235e2d5973cd0c81d269597cbdc400a11333cc617247aa` | `d70f3a8b097827d4ffbb714490453a74a87f6b062709f3e05ceb466ac82cb967` |
| `net/minecraft/data/tag/FluidTagsProvider.java` | `10ff8ccdde75f60999c88f16844c78e4419f94fcae3b1716634202b8f2ba671a` | `10ff8ccdde75f60999c88f16844c78e4419f94fcae3b1716634202b8f2ba671a` |
| `net/minecraft/tag/TagManager.java` | `fbe18a52f638c2d6d38713034388a951bd9de8fb3acf647dd31acd9d3b3bf252` | `f5fbf8803e26575c73ce15e3585459caceccdb65edf0f05b61663a93726d4e79` |
| `net/minecraft/tag/TagCollection.java` | `696fa88d7564d90fdd0d2f03a37c9033ec76db1598f1bdb07293247f4cb463c0` | `99ea58cf07a1e5a0d41db59242f8058775e72503ef6a10f2ec903b94ea7dcefe` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11` | `6c840580285c7c7d4617e895edac82d4eebf8289dd4994e7b593cebeb49b43ef` |

This is a source-only dependency check. No product code, canonical source, generated resources, or runtime state was changed or exercised.
