# Legacy slab provider and alias closure

## Scope and result

This closes the old 1.8.9 registered slab families 43, 44, 125, 126, 181, and 182 for state decoding, migrated current block identity, and their collision-shape providers. It does not close DDA traversal, provider visitation order, arbitrary non-slab provider families, mutable-bound cache order, or runtime reachability.

The 1.8.9 state decoder uses `metadata & 7` for the variant and bit 8 for the single-slab half or double-slab seamless property. Thus every signed integer metadata input produces a valid masked variant and half/seamless value; there is no invalid metadata case for these six decoders. Variants whose enum lookup rejects an index fall back to variant zero.

## Old registered states to current 26.2 states

For compactness, each row gives old variant metadata 0 through 7 in order. “Bottom/top” applies to the single ID and “double” to the double ID. All entries in a family share the listed shape. The current waterlogged value is `false` for the migrated legacy state; it has no effect on the slab collision shape.

| Old ID | Old metadata / variant | Current block key and state |
|---|---|---|
| 43 `double_stone_slab` | 0 stone, seamless 0/1 | `smooth_stone_slab[type=double]` / `minecraft:smooth_stone` full block |
| 43 | 1 sandstone, seamless 0/1 | `sandstone_slab[type=double]` / `minecraft:smooth_sandstone` full block |
| 43 | 2 `wood_old`, seamless 0/1 | `petrified_oak_slab[type=double]` for either value |
| 43 | 3 cobblestone; 4 brick; 5 stone brick; 6 nether brick, seamless 0/1 | respectively `cobblestone_slab`, `brick_slab`, `stone_brick_slab`, `nether_brick_slab`, each `[type=double]` for either value |
| 43 | 7 quartz, seamless 0/1 | `quartz_slab[type=double]` / `minecraft:smooth_quartz` full block |
| 44 `stone_slab` | 0 stone; 1 sandstone; 2 `wood_old`; 3 cobblestone; 4 brick; 5 stone brick; 6 nether brick; 7 quartz | respectively `smooth_stone_slab`, `sandstone_slab`, `petrified_oak_slab`, `cobblestone_slab`, `brick_slab`, `stone_brick_slab`, `nether_brick_slab`, `quartz_slab`; each `[type=bottom]` or `[type=top]` from bit 8 |
| 125 `double_wooden_slab` | 0 oak; 1 spruce; 2 birch; 3 jungle; 4 acacia; 5 dark oak; 6/7 fallback oak | respectively `<species>_slab[type=double]`; bit 8 is ignored |
| 126 `wooden_slab` | same variants as 125 | respectively `<species>_slab[type=bottom]` or `[type=top]` from bit 8 |
| 181 `double_stone_slab2` | all low-three-bit values fall back to red sandstone; seamless 0/1 | `red_sandstone_slab[type=double]` / `minecraft:smooth_red_sandstone` full block |
| 182 `stone_slab2` | all low-three-bit values fall back to red sandstone | `red_sandstone_slab[type=bottom]` or `[type=top]` from bit 8 |

The `wood_old` alias is petrified oak, not oak. The old stone variant’s current `stone_slab` key is subsequently renamed to `smooth_stone_slab` by the 26.2 data fixer (`DataFixers.java:771`). This applies to both migrated single and double slab states that still carry the key after block-state flattening. Current `stone_slab` therefore must not be used as the legacy stone-slab identity by same-name matching.

The old wooden enum has only six values, and `PlanksBlock.Variant.byId` falls back to oak. The old red-sandstone slab enum has only variant zero and falls back to it for every other masked value. Stone slab variants cover 0–7, so their mask always indexes a valid entry.

## Collision provider comparison

In 1.8.9, `SlabBlock.updateShape` writes a full unit cube for every double slab. For a single slab it reads the block state at the supplied position and writes the bottom half `[0,0,0]–[1,0.5,1]` or top half `[0,0.5,0]–[1,1,1]`. `SlabBlock.addCollisions` calls `updateShape` before delegating to `Block.addCollisions`, whose provider constructs a box from the mutable bounds and adds it only when it intersects the requested query box. The slab `rayTrace` path also refreshes those bounds first. The shape result is independent of material, variant, and old double `seamless` bit, but provider calls mutate the old block instance’s shared bounds.

In 26.2, `SlabBlock` exposes `type=bottom|top|double` and `waterlogged`; its collision shape is respectively the matching half-column or full cube. The mapped full-block seamless outcomes are also full cubes. Current collision shapes therefore match the old shape for all listed migrated states. The old mutable per-block bound writes are not a current-provider property and must remain a separate lifecycle/observer concern.

All current slab registrations used by the mapping instantiate `SlabBlock` and do not set `.noCollision()`. `BlockBehaviour.Properties` defaults `hasCollision` to true, so their collision gate is enabled; waterlogging does not disable it.

Neither source says that these six providers alone are a complete old collision collector. No claim is made here about DDA visitation, chunk availability, other block families, collector filtering, or whether a historical observer must emulate old shared-bound side effects.

## Source references

All references point into the canonical, read-only decompilations whose ready markers and source manifests are recorded in `sources.sha256`.

| Evidence | 1.8.9 source | 26.2 source |
|---|---|---|
| IDs and registry names | `net/minecraft/block/Block.java:916-917,1108-1109,1220-1224` | Slab registrations: `net/minecraft/world/level/block/Blocks.java:3201-3368,4406-4408` |
| old double/single bounds, collision call order | `net/minecraft/block/SlabBlock.java:27-68`; base query intersection: `net/minecraft/block/Block.java:339` | half/full shape: `net/minecraft/world/level/block/SlabBlock.java:36-58` |
| stone decoder, enum and fallback | `net/minecraft/block/StoneSlabBlock.java:72-90,130-160` | flattening rows 688–719 in `net/minecraft/util/datafix/fixes/BlockStateData.java`; stone slab rename: `net/minecraft/util/datafix/DataFixers.java:771` |
| wood decoder and fallback | `net/minecraft/block/WoodenSlabBlock.java:56-83`; enum lookup in `net/minecraft/block/PlanksBlock.java` | flattening rows 2000–2029 in `net/minecraft/util/datafix/fixes/BlockStateData.java` |
| red sandstone decoder and fallback | `net/minecraft/block/RedSandstoneSlabBlock.java:62-91,118-151` | flattening rows 2896, 2904, 2912, and 2920 in `net/minecraft/util/datafix/fixes/BlockStateData.java` |
| current slab state, shape and collision gate | — | `net/minecraft/world/level/block/SlabBlock.java:35-58`; default collision gate: `net/minecraft/world/level/block/state/BlockBehaviour.java:985,328` |

## Source identity

`1.8.9/ornithe-feather` and `26.2/unobfuscated` both have `status=ready`. Their ready-marker `sourceManifestSha256` values match the canonical source-manifest files. `sources.sha256` records the exact hash for each selected Java source and the copied identity marker/provenance/manifest artifacts; the Java entries were checked against the canonical source manifests.
