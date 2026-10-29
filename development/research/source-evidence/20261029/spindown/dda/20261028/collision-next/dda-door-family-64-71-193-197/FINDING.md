# 1.8.9 door provider and 26.2 state alias closure

Status: source-closed for legacy identity, per-state geometry formula, and mutable-bound call order. This closes only the door family provider facts listed here. It does not close traversal equivalence, first-hit behavior, a complete DDA provider registry, or runtime reachability.

## Canonical source readiness

The exact canonical roots were the ready 1.8.9 `ornithe-feather` and 26.2 `unobfuscated` trees recorded in `source-hashes.json`. Their ready markers report status `ready`, Vineflower decompilation, and matching source manifests. The report uses no product sources.

## Old registry identities

In 1.8.9 `net/minecraft/block/Block.java`, the registrations are:

| Old numeric ID | Old key | Material/constructor |
| ---: | --- | --- |
| 64 | `wooden_door` | `DoorBlock(Material.WOOD)`; oak/default door |
| 71 | `iron_door` | `DoorBlock(Material.IRON)` |
| 193 | `spruce_door` | `DoorBlock(Material.WOOD)` |
| 194 | `birch_door` | `DoorBlock(Material.WOOD)` |
| 195 | `jungle_door` | `DoorBlock(Material.WOOD)` |
| 196 | `acacia_door` | `DoorBlock(Material.WOOD)` |
| 197 | `dark_oak_door` | `DoorBlock(Material.WOOD)` |

`Blocks.java` resolves these registered names to its corresponding constants. The oak identity is the old `wooden_door` key, not a separately registered old `oak_door` key. Every listed key uses the same old `DoorBlock` implementation; material affects use behavior, not the box formula.

## Exact numeric-to-current alias rows

`BlockStateData.register(id, currentTag, legacyTags...)` stores the first tag as the upgraded tag in `MAP[id]`; `upgradeBlock(int)` returns that tag's name and `getTag(int)` returns the full tag. `BlockNameFlatteningFix` dispatches integer choices through this table. The numeric row is old block ID × 16 + metadata. The table gives these destination name families and state-property sets:

| Old ID | Numeric range | 26.2 block key | Source-backed state tuple family |
| ---: | ---: | --- | --- |
| 64 | 1024–1039 | `minecraft:oak_door` | facing, open, powered, hinge, half |
| 71 | 1136–1151 | `minecraft:iron_door` | facing, open, powered, hinge, half |
| 193 | 3088–3099 explicit; 3100–3103 default-filled | `minecraft:spruce_door` | facing, open, powered, hinge, half for explicit rows |
| 194 | 3104–3115 explicit; 3116–3119 default-filled | `minecraft:birch_door` | facing, open, powered, hinge, half for explicit rows |
| 195 | 3120–3131 explicit; 3132–3135 default-filled | `minecraft:jungle_door` | facing, open, powered, hinge, half for explicit rows |
| 196 | 3136–3147 explicit; 3148–3151 default-filled | `minecraft:acacia_door` | facing, open, powered, hinge, half for explicit rows |
| 197 | 3152–3163 explicit; 3164–3167 default-filled | `minecraft:dark_oak_door` | facing, open, powered, hinge, half for explicit rows |

The oak and iron ID ranges have all 16 rows; each species-door range has rows for metadata 0–11 only. `finalizeMaps()` fills missing indices from the first state registered for that 16-ID block group. The missing species rows 12–15 therefore resolve to that block's default row (east-facing, closed, lower-half), not a matching upper-half state. This is a source-proven limitation for noncanonical raw metadata. Old `DoorBlock.getStateFromMetadata` ignores upper-half bit 2, and `getMetadataFromState` drops it, so values 12–15 normalize to 8–11 after a decode/encode cycle; they are not canonical old state metadata. Do not claim exact flattening for those raw rows. For lower-half metadata 0–7, bits 0–1 select horizontal facing and bit 2 is open; bit 3 is clear. For upper-half metadata 8–11, bit 3 selects upper, bit 0 selects right hinge, and bit 1 selects powered. Old `getStateFromMetadata` sets upper-half facing/open to defaults; `getCombinedMetadata` takes facing/open from the lower half and hinge/powered from the upper half when deriving the occupied door state. The explicit upper-half rows include output facing/open/hinge/powered tuples even though an isolated upper block's raw metadata does not encode facing/open.

The current registry has corresponding door block constants (`OAK_DOOR`, `IRON_DOOR`, `SPRUCE_DOOR`, `BIRCH_DOOR`, `JUNGLE_DOOR`, `ACACIA_DOOR`, `DARK_OAK_DOOR`). The 26.2 `DoorBlock` exposes the same five properties `FACING`, `OPEN`, `HINGE`, `POWERED`, and `HALF`; `POWERED` does not participate in its shape formula.

## Collision and outline provider

Old `DoorBlock.getCollisionShape` and `getOutlineShape` both call `updateShape(world,pos)` and then the base implementation. `rayTrace` also calls `updateShape(world,pos)` before the base ray operation. Each `updateShape` recomputes a one-box shape from the current world view and writes the shared block bounds. It first resets bounds to `(0,0,0)–(1,2,1)` and then every branch replaces them with a height-one door leaf. Thus stale bounds from an earlier door orientation are not the intended result of any of these three public operations.

Let `t=3/16`. The final old block-local boxes are:

| Effective facing | Closed | Open, hinge left | Open, hinge right |
| --- | --- | --- | --- |
| east | `[0,0,0]–[t,1,1]` | `[0,0,0]–[1,1,t]` | `[0,0,1-t]–[1,1,1]` |
| south | `[0,0,0]–[1,1,t]` | `[1-t,0,0]–[1,1,1]` | `[0,0,0]–[t,1,1]` |
| west | `[1-t,0,0]–[1,1,1]` | `[0,0,1-t]–[1,1,1]` | `[0,0,0]–[1,1,t]` |
| north | `[0,0,1-t]–[1,1,1]` | `[0,0,0]–[t,1,1]` | `[1-t,0,0]–[1,1,1]` |

The 26.2 `DoorBlock.getShape` builds a 13/16-thick horizontal-facing shape. Closed orientation uses `FACING`; open orientation rotates `FACING` clockwise for left hinge and counterclockwise for right hinge. The resulting directional shapes are composed from this block's `VoxelShape` box; inherited `BlockBehaviour.getCollisionShape` returns the state's shape when the block has collision. This establishes the current provider entry point and formula. The source report does not claim pair recombination in current code or equivalence for malformed/unpaired blocks.

## Pair and context gates

The old shape provider is world-position dependent, not solely a function of the queried block state. `getCombinedMetadata(world,pos)` reads the queried block metadata, the block below, and the block above; the half bit selects which neighboring metadata supplies lower orientation/open bits and which supplies upper hinge/power bits. The old combination path does not first require both neighboring blocks to be the same door block. By contrast, old `resolveVirtualProperties` checks same-block identity when merging state properties, and 26.2 `DoorBlock.updateShape` only uses its neighbor to maintain/remove the two halves; `getShape` itself reads only the queried `BlockState` properties.

Therefore a source-grounded provider adapter must preserve the historical three-position metadata combination and call timing for the supported paired-door domain, or explicitly retain native behavior outside that domain. It must not claim malformed/non-door neighbor parity based on the current per-state shape formula. `POWERED` is a retained state bit but does not affect the box; it remains relevant to old neighbor/redstone behavior, which is outside this box-only closure.

## Scope boundary

Closed here: exact old IDs and keys; exact 26.2 destination keys and state rows for each ID × metadata; old bounds reset/write order and box dimensions; modern shape/collision provider entry points and formula inputs; old pair reads that parameterize the box.

Not established here: ray traversal visitation order, provider enumeration order, DDA first-hit semantics, admissible world/pair construction, runtime state injection, or whole-registry DDA compatibility.
