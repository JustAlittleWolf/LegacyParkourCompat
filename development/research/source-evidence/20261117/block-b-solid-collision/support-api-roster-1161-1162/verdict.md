# Independent exact-source audit: support API and shape roster

**Bounded pair:** Minecraft 1.16.1 → 1.16.2, Mojmap, client player movement collision/support only.  
**Verdict:** Support predicate API and cache refactor is semantically equivalent for the vanilla 1.16.1/1.16.2 provider roster. Piston-head shape precomputation preserves every facing/short geometry. Fire and vine outline-shape caching does not change their collision or `noCollision` results. No movement-difference finding established in this bounded audit.

## Evidence identity

Both source trees have ready markers and provenance manifests for the same exact decompile invocation (`--versions=1.16.1,1.16.2 --mappings=mojmap`, Vineflower 1.12.0, Java 25.0.3+9-LTS; run dated 2026-10-07). The ready markers' source-manifest SHA-256 values match the files on disk:

| Release | source manifest SHA-256 | raw client SHA-256 | official mapping file SHA-256 |
|---|---|---|---|
| 1.16.1 | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef` | `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b` |
| 1.16.2 | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d` | `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f` |

Relevant decompiled-source hashes were recomputed and matched the corresponding `mojmap.sources.sha256` entries. Main evidence files: `Block.java` (1.16.1 `ca9a413b…896655`; 1.16.2 `2838aa4f…89e5b6`), `BlockBehaviour.java` (`1b524b5c…18ef7f`; `c62e2f07…2def8b`), `SupportType.java` (new in 1.16.2, `7a0fd4eb…d99d19`), `PistonHeadBlock.java` (`6160d18f…89f502`; `9854f1d4…db4bb`), `FireBlock.java` (`4bfb85b0…c630c6`; `b5816496…e551f9`), `BaseFireBlock.java` (`966086b7…1add40`; `16e2b7e9…d2e9a79`), `VineBlock.java` (`eee92552…a93fd`; `b9fc4bd0…dc7aedb`), and `CollisionSpliterator.java` (`19ab959c…94d297` on both).

## Rigid-support predicate and cache

1.16.1 `world/level/block/Block.java:211-216` computes:

`collisionFull && fullUpSupportFace || !joinIsNotEmpty(upSupportFace, RIGID_SUPPORT_SHAPE, ONLY_SECOND)`.

Its rigid ring is the unit face minus the centered 12×12 interior (`Block.java:66`). The second clause is exactly the 1.16.2 `SupportType.RIGID.isSupporting` calculation (`SupportType.java:29-37`), with the same box, operation and `!` polarity. 1.16.2 `Block.canSupportRigidBlock` forwards to `isFaceSturdy(..., Direction.UP, SupportType.RIGID)` (`Block.java:210-212`).

The old first clause cannot produce a false positive against the new ring predicate: `isFaceSturdy(..., Direction.UP)` means the support shape's UP face is full (`Block.java:228-234`), and a full face necessarily contains the smaller rigid ring. Thus the old OR term is redundant in Boolean terms, regardless of whether collision and support shapes match. When it is false, the old second clause and new RIGID operation are the same support-shape ring test. This proof is shape-provider agnostic when both sides evaluate the same support shape in the same context.

Cache behavior is also aligned for the vanilla provider roster. In 1.16.1 `BlockBehaviour.java:702-707,728-753`, cache states retain `FULL` face sturdiness plus full-collision status; in 1.16.2 `BlockBehaviour.java:703-715,723-775`, cache states add all `SupportType` × direction values and `isFaceSturdy` indexes that result. Both initialize only for `!hasDynamicShape()` (`BlockBehaviour.java:386-389` / `387-390`); dynamic states take the uncached provider path. Vanilla overrides of `getBlockSupportShape` are Leaves, SoulSand, and SnowLayer only, confirmed by bounded source search in both trees:

- Leaves return `Shapes.empty()` (`LeavesBlock.java:32-34`), despite their collision shape being nonempty; old full-collision/FULL-face term is false, and both versions reject the empty support ring.
- Soul sand returns `Shapes.block()` (`SoulSandBlock.java:30-35`) despite its collision shape stopping at 14/16 height; the old full-collision term is false, but both versions accept the full support ring.
- Snow's support shape is `SHAPE_BY_LAYER[LAYERS]`, while collision is `SHAPE_BY_LAYER[LAYERS - 1]` (`SnowLayerBlock.java:53-72`). At eight layers the support top is full but collision top is 14/16; both versions accept the ring via support shape. Lower layers are handled by the same ring containment test.

These mismatches directly exercise the supposed collision/support conflation and do not invalidate equivalence. All three overrides are state-derived and are not registered with `dynamicShape`; dynamic vanilla states bypass both versions' `BlockStateBase.Cache`. No vanilla provider overriding support shape was found that varies with world/position while remaining cacheable. This audit does not claim equivalence for third-party blocks that return context-dependent support shapes without marking themselves dynamic; that would violate the engine's cacheability assumption, not establish a vanilla pair difference.

`canSupportRigidBlock` has unchanged call sites in both releases: BaseRailBlock, BasePressurePlateBlock, and DiodeBlock. Their ordinary state/survival checks therefore retain the same Boolean support answer. `BasePressurePlateBlock` also separately checks `canSupportCenter`; that existing center rule, including the `UNSTABLE_BOTTOM_CENTER` gate, is unchanged. These are placement/survival predicates for blocks that can then participate in the player's world geometry; they do not alter collision shapes themselves.

## Piston-head shape cache

In 1.16.1 `PistonHeadBlock.java:31-48,55-100`, each `getShape` call chooses one base AABB by `FACING`, one arm AABB by the same `FACING` and `SHORT`, then returns `Shapes.or(base, arm)`. In 1.16.2 `PistonHeadBlock.java:31-73,85-88`, `makeShapes(false/true)` calculates that same `Shapes.or` pair for each `Direction.values()` ordinal and `getShape` selects the short/long table then the facing ordinal. The base/arm coordinates and union operand order are unchanged. Every enum case is present, with DOWN as the same default case. This removes repeated switch/union work while returning the same shape geometry for all 12 state combinations.

## Fire/Vine and `noCollision`

Fire and Vine switch their state-based outline `getShape` construction to precomputed maps. For Fire, the AABB coordinates are unchanged from 1.16.1 `BaseFireBlock.java:23-28` to 1.16.2 `FireBlock.java:42-46`; property checks select the same face strip, with only the union construction order changed. Fire's 1.16.2 map normalizes AGE to zero (`FireBlock.java:63-69,106-109`), and AGE did not participate in the 1.16.1 per-call shape (`FireBlock.java:66-89`). Vine's EAST/WEST and NORTH/SOUTH constant names are reversed between releases, with property-to-box selection changed correspondingly; both yield identical face strips for each property (`VineBlock.java:33-69` / `36-84`).

Both registered blocks still use `.noCollission()` in `Blocks.java` (Fire at 1.16.1 `457-459` / 1.16.2 `454-458`; Vine at `943-945` / `950-952`). The inherited `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when `hasCollision` is false (`BlockBehaviour.java:275-277` in both). Consequently `CollisionSpliterator`'s player collision scan receives an empty shape for Fire/Vine even though their outline shape changed representation. The `CollisionGetter.noCollision` and `CollisionSpliterator` implementations are source-hash identical across this pair. Player's step-up/auto-jump-style `noCollision` probes in `Player.java:1016-1064` still route through that same block-collision scan. Fire/Vine therefore do not block ordinary player movement before or after the cache refactor.

## Bounded exclusions and conclusion

This audit excludes bed/anchor relocation, moving-piston relocation/response, cauldrons, hoppers, chains and shulkers as assigned. It verifies static source behavior only; it does not establish runtime parity or all patch-level introduction boundaries. The reviewed support API/cache, piston-head lookup, Fire/Vine cached outline shapes and no-collision path establish no player-movement difference from 1.16.1 to 1.16.2 in the bounded vanilla source roster.
