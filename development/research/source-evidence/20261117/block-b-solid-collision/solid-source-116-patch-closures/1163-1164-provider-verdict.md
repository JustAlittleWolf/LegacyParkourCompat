# Independent source audit: 1.16.3–1.16.4 solid-provider routes

## Verdict

**No Java source delta found in the bounded solid collision-provider and caller route.** The 103 direct provider files independently counted as block-package declarations of `VoxelShape getShape(...)` or `getCollisionShape(...)` are all byte-identical across the pair and are included in the checked source set. The ordinary movement, step, support-shape and AutoJump routes examined also have identical source hashes. This does not establish equality of resource-defined tag membership: the decompile/cache evidence contains no asset or resource payload, and `WallBlock` provider state depends on `BlockTags.WALLS` and `BlockTags.WALL_POST_OVERRIDE`.

## Exact source identity and pair diff

Canonical read-only roots: `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/`. Their `.decompile-provenance.json` files record 3,516 and 3,523 sources. I recomputed the SHA-256 of each provenance file; they match `source-proof.json`'s manifest hashes (`513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`). Mojmap is the mapping identity for both.

I independently compared the provenance `sources` maps: **64 modified, 7 added, 0 removed**. The actual source files for all 64 modified entries and all 7 newer-only entries match their respective manifest SHA-256 values. None of the changed/added files intersects the 103 direct shape-provider files. The additions are social-screen/manager/list and server text-filter classes plus `CryptException`. Relevant looking modified classes include `EnderDragon` and `FireworkRocketEntity`, but neither is a solid-block provider or part of the ordinary solid collision enumeration route; their non-player/rocket behavior is outside this bounded operation.

The sibling proof file's actual SHA-256 is exactly the requested `8c4f665c6f876251eed6ec62fabd53b184712010c8ffe1ea5509b38d0fe3d8b7`. I independently validated all 122 listed route/provider source records against both current files and both provenance manifests: no mismatch, no unequal pair. I also independently enumerated the 103 direct `getShape`/`getCollisionShape` provider files and confirmed every path is in that record set and identical across releases.

## Caller and provider closure checked

- `LocalPlayer.move` (1.16.3 `LocalPlayer.java:863–868`) calls `super.move` and then `updateAutoJump`; the latter reads collision shapes for the player's current/raised blocks and queries collision shapes through `getCollisions` (`:874–971`). `LocalPlayer.java` is byte-identical across the pair.
- `Entity.move` (`Entity.java:485–509`) applies edge backoff and calls `collide`; `collide` selects the collision path and the step-assist route (`:668–727`), then applies ground/step callbacks in `move` (`:529–535`). `Entity.java` is byte-identical.
- `CollisionGetter.getBlockCollisions` delegates to `CollisionSpliterator`; the spliterator enumerates candidate blocks, queries `BlockState.getCollisionShape`, moves shapes into world coordinates and intersects them with the player box (`CollisionGetter.java:54–68`; `CollisionSpliterator.java:47–85`). Both classes are byte-identical.
- `BlockBehaviour` dispatches `BlockState` collision/support shape queries to each block implementation; collision fallback is gated by `hasCollision`, and the state wrapper dispatches `getBlockSupportShape` (`BlockBehaviour.java:231–282, 516–549`). This file is byte-identical. The 103 provider files, block registry and shape primitives are also unchanged.
- I additionally checked support-only dependencies not included in the sibling proof list: `SupportType.java`, `LeavesBlock.java`, `SnowLayerBlock.java`, `SoulSandBlock.java`, `Block.java` and `BlockTags.java`. Each actual file matches its manifest and has the same SHA-256 in both versions.
- `Options.java` and `Option.java` are file-level changes, so I checked the AutoJump input dependency directly: the `autoJump` default field, option read/write references and `Option.AUTO_JUMP` getter/setter are unchanged. The player-side AutoJump caller is unchanged.

## Resource/cache status and limit

For each version, all **36** manifest input artifacts are present in that version's `.minecraft-decompile-cache/` tree and their bytes match the provenance input SHA-256 values. This includes the exact client jar, Mojmap mapping file, version metadata and libraries. The source trees contain Java sources plus provenance only; the per-version caches have no assets/resource directory, asset index or resource archive, and the client jars contain no `data/minecraft/tags/blocks/` or `assets/minecraft/blockstates/` entries. `WallBlock.java` uses the two tag predicates listed above when resolving neighboring state. Therefore the reviewed evidence closes Java implementation/dependency changes but does not independently close tag-resource membership or effective state geometry for those tag-dependent cases.

No Git, build, decompile, tests or runtime were used.
