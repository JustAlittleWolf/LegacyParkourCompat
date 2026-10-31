# 1.20.4–1.20.5–1.20.6 resource/tag/state dependency slice

## Bounded disposition

No concrete 1.20.4→1.20.5 or 1.20.5→1.20.6 movement-tag payload difference is established from the available exact Java sources. The vanilla Java tag providers give the same defaults for the examined player-movement predicates in all three releases. No finding is proposed. The actual built-in and world-selected JSON datapack payloads remain an external evidence dependency; the permitted source set does not contain them.

## Exact source and inventory identities

All source trees are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.4,1.20.5,1.20.6}/mojmap`, official Mojang client mappings, ready for the named exact version, decompiled with Vineflower on Java 25. Ready-marker source manifest hashes are verified against the present `mojmap.sources.sha256` files:

| Release | Source files / bytes | Source manifest SHA-256 | Ready marker client SHA-256 |
| --- | ---: | --- | --- |
| 1.20.4 | 5,048 / 24,323,941 | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` |
| 1.20.5 | 5,328 / 25,448,860 | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` | `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` |
| 1.20.6 | 5,329 / 25,451,020 | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` |

The pair inventory is `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv` (5,391 path rows; SHA-256 `961e797371cada010cfd4abe628ec7b312c0e73b50eb7ff25a5ba1c86c204253`). Its hashes are the source file identities used for the per-path three-way comparison. The three source trees contain Java only (zero non-Java files).

## Java registration, loading and defaults checked

The inventory shows these relevant paths changed 1.20.4→1.20.5 and are byte-stable by the inventory hash 1.20.5→1.20.6: `net/minecraft/tags/BlockTags.java`, `TagLoader.java`, `TagManager.java`, `TagNetworkSerialization.java`; `net/minecraft/resources/RegistryDataLoader.java`, `RegistryOps.java`, `net/minecraft/server/ReloadableServerResources.java`; and data-generation paths `net/minecraft/data/tags/TagsProvider.java`, `VanillaBlockTagsProvider.java`, `UpdateOneTwentyOneBlockTagsProvider.java`, plus the added registry/tag sync collectors. These are Java definitions and loaders, not generated tag JSON. `TagManager` maps block/fluid registries to `tags/blocks` and `tags/fluids`; `TagLoader.load(ResourceManager)` reads layered JSON files under those directories, applies `replace`, and records source pack IDs. That establishes an external pack input path, not the contents of a particular pack.

Examined the tag constants, providers and direct ordinary-player/entity movement reads:

| Predicate input | Movement read sites | Vanilla Java default provider |
| --- | --- | --- |
| `BlockTags.CLIMBABLE` | `world/entity/Entity.java` climb predicate; `world/entity/LivingEntity.java` climb handling | `data/tags/VanillaBlockTagsProvider.java`; ladder, vines and scaffolding members are identical in all three sources |
| `BlockTags.SOUL_SPEED_BLOCKS` | `world/entity/LivingEntity.java` underfoot Soul Speed check | `VanillaBlockTagsProvider.java`; soul sand and soul soil in all three |
| `BlockTags.FENCES`, `WALLS` | `world/entity/Entity.java` step-up/fence-wall decision | `VanillaBlockTagsProvider.java`; definitions and membership expressions are unchanged across the three |
| `FluidTags.WATER`, `LAVA` | `world/entity/Entity.java`, `world/entity/LivingEntity.java`, `world/entity/player/Player.java`, and `world/level/block/state/BlockBehaviour.java` | `data/tags/FluidTagsProvider.java`; water/flowing water and lava/flowing lava respectively, unchanged across the three |

`BlockTags.PORTALS` is read by `LivingEntity.dismountVehicle`; `BlockTags.FIRE` is read by generic entity fire-state handling. These are adjacent entity reads, not accepted ordinary-player-movement dependencies in this slice. Entity step-sound tags (`INSIDE_STEP_SOUND_BLOCKS`, `COMBINATION_STEP_SOUND_BLOCKS`, `CRYSTAL_SOUND_BLOCKS`) are also read, but their effects are sound selection/rate limiting rather than player movement state. The 1.20.5 additions to `BlockTags.java` (badlands terracotta, netherite/tool-destruction tags, armadillo spawn, wind-charge and hopper tags) are not among the ordinary player movement reads checked here. The added tags do not by themselves establish a movement change.

The inventory also marks movement-adjacent Java definitions such as `BlockBehaviour.java`, `BlockState.java`, `StateDefinition.java`, `BlockStateProperties.java`, `FluidState.java`, and `core/registries/BuiltInRegistries.java` changed in 1.20.4→1.20.5 and same in 1.20.5→1.20.6. They define/runtime-consume Java state and registry structures; they do not supply the absent tag JSON payload. This slice does not classify unrelated code changes in those classes as findings.

## Open dependency and limits

The exact client jars are named and hashed in the ready/artifact manifests, but the referenced `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/artifacts` directory and its per-version client jars are absent in this environment. No exact official resource archive is therefore available here for inspection, and no network retrieval or decompilation was performed. Reopen only to inspect exact official release resource payloads if supplied/present: tag JSON values can be layered or replaced by world/resource packs, so Java constants and providers cannot prove an arbitrary world's resolved tag membership. The examined Java providers do establish the vanilla generator defaults stated above. Custom datapack/resource-pack membership remains unknown external input; modern-only tags remain out of the movement disposition unless a concrete player predicate consumes them.

This is a bounded resource/tag/state dependency disposition, not complete source-pair coverage or a pair-freeze claim.
