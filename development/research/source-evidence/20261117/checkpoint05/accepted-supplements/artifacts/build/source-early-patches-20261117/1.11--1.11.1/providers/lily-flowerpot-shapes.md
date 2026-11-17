# 1.11 → 1.11.1 LilyPadBlock and FlowerPotBlock collision providers

## Exact source identity

Compared the read-only Ornithe Feather roots `decompiled_minecraft/1.11/ornithe-feather/` and `decompiled_minecraft/1.11.1/ornithe-feather/`. The 1.11 root publication markers are `ornithe-feather.ready.json` SHA-256 `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` and `ornithe-feather.sources.sha256` SHA-256 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`. The 1.11.1 root markers are `ornithe-feather.ready.json` SHA-256 `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf` and `ornithe-feather.sources.sha256` SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`. The source inventory rows bind 1,918 and 1,921 files respectively with no manifest mismatches per the supplied source-input reference.

Exact source SHA-256 identities:

| Source | 1.11 | 1.11.1 |
| --- | --- | --- |
| `net/minecraft/block/LilyPadBlock.java` | `91004173b4287d244c671ffe565ca0935d0c04983d598027f37f26d9c111136d` | `873b93062318b692cae5c071edb6489cbac6621bdda4b139f08df493b075fb9` |
| `net/minecraft/block/FlowerPotBlock.java` | `388f85170835de967f19adbdc823c3f36593d62787c1d2a099cabdbecc0558d4` | `d749da19156e319c25671fd75ea80dc25ea65cdf39b7f59e1fdc5178f5c563c4` |
| `net/minecraft/block/Block.java` (provider dispatch) | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` |
| `net/minecraft/world/World.java` (query dispatch) | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| `net/minecraft/entity/Entity.java` (movement caller) | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |

## Provider comparison

`LilyPadBlock.SHAPE` is the same fixed box in both releases: x/z `0.0625..0.9375`, y `0..0.09375`. Its `getShape` returns that box without consulting state, world or position. In 1.11, `addCollisions` emits the translated shape unless the queried entity is a `BoatEntity`. In 1.11.1 the method adds a `forceShape` parameter, but does not read it; the same boat exclusion and same box emission remain. `onEntityCollision` still calls the parent and breaks the lily pad only for a boat. Placement, survival checks and metadata behavior are unchanged in the compared class bodies.

`FlowerPotBlock.SHAPE` is also identical: x/z `0.3125..0.6875`, y `0..0.375`; `getShape` returns it for every state. `FlowerPotBlock` does not override `addCollisions` in either release, so the inherited `Block.addCollisions` path obtains `state.getCollisionShape(world, pos)` and emits its intersecting translated box. The matching `Block.addCollisions` implementations differ only by the added, unused `forceShape` argument in 1.11.1. The pot's `CONTENTS` and `LEGACY_DATA` state properties, including its block-entity-backed virtual contents, do not affect the fixed collision box. The `use` method's inventory call changes from `player.inventory.addItem` to `player.addItem`; this is inventory behavior and provides no collision-provider difference.

## Local-player query binding and bounds

Both `World.getCollisions` implementations iterate the queried block states and call their collision provider with the query `entity`; 1.11.1 passes `forceShape=false`. `Entity.move` calls this world query with `this` as the entity and its swept box (1.11 `Entity.java:523`, 1.11.1 `Entity.java:564`). This supplies the ordinary player-movement collision path and binds the LilyPad boat predicate to the queried entity. A player is not a `BoatEntity`, so the LilyPad provider emits its fixed box when intersected. The FlowerPot inherited provider likewise uses the fixed state collision box. This is source-level query reachability only; no trajectory or map occurrence is claimed.

## Disposition

No LilyPadBlock or FlowerPotBlock collision-shape/provider output delta was found in the examined scope. The changed `forceShape` signatures are ignored by both examined providers and the inherited FlowerPot path uses the same state collision box. Lily pad boat destruction, FlowerPot item use, and other non-collision behavior are not promoted to player-movement findings. This report covers only these two providers and the narrow dispatch/caller bindings above; the remaining changed provider, tick, item/effect and response paths and the exact-pair independent coverage audit remain open. No pair freeze or implementation readiness is inferred.
