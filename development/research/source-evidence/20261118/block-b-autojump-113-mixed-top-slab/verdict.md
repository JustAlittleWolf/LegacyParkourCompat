# Independent audit: mixed slab 1.13 alias source

## Verdicts

- **Raw block-ID/state alias: PASS for the bounded claim.** In 1.13.1 and 1.13.2, `minecraft:stone_slab` is the registered slab identity; its state exposes `type` and `waterlogged`, and `type=top,waterlogged=false` is valid. `SlabBlock.getShape` returns the top-half shape (8..16/16 of the cell) for TOP, and the inherited collision-shape provider delegates to the state shape. In 1.14, the block-state data fixer renames only the `Name` field from `minecraft:stone_slab` to `minecraft:smooth_stone_slab`, preserving the remainder/properties; `Blocks` registers that smooth slab separately from the new `minecraft:stone_slab`. 1.14.1 keeps the distinct registrations and state schema. 26.2 raw identifiers and registrations likewise distinguish `stone_slab` from `smooth_stone_slab`, and `SlabBlock` still has `type`/`waterlogged` with TOP shape. This supports the supplied dry-top alias question.
- **1.13.1/.2 exact AutoJump query/timer witness for x=.59→.69, Y8 support, z=1.1 and the stated cap blocks: NOT ESTABLISHED by this report.** The exact callback and conditional timer producer are source-proven, but the report explicitly does not recalculate the supplied coordinates or prove a geometric traversal. The alias evidence makes the block shape available to queried collision/headroom paths; it does not prove that this cell is among the exact query results for those coordinates, that a relevant shape intersects either probe, or that execution reaches the timer write. Treat the callback/timer fact as a conditional producer only for this witness.

This audit says nothing about original 1.13 or full-pair coverage/runtime parity.

## Immutable input and source identity

Audited source-only report: `mixed-slab-113-alias-source.md`, SHA-256 `98c8235f1ac8adf79ba40fed5c94a14d8f8114f4d8d4ae842e9fed8bc7323ce4` (matches the assigned identity).

Verified source roots and mapping identities from local ready/provenance metadata:

- 1.13.1 and 1.13.2: Ornithe Feather Gen2 build.2; each ready marker's source-manifest digest matches its adjacent manifest. Their relied-on `Block.java`, `SlabBlock.java`, `Properties.java`, and `LocalClientPlayerEntity.java` bytes match listed source hashes. The two releases' selected Block/SlabBlock/Properties/LocalClientPlayerEntity files are byte-identical; `LocalClientPlayerEntity.java` SHA-256 is `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- 1.14: Ornithe Feather Gen2 build.2; ready source-manifest and provenance links verify. `DataFixes.java` and `Blocks.java` match their source-manifest entries.
- 1.14.1: Yarn `1.14.1+build.10`; `Blocks.java`, `SlabBlock.java`, `SlabType.java`, and `ClientPlayerEntity.java` match `.decompile-provenance.json` source hashes; pinned Yarn and intermediary artifacts are present as stated in the audited report.
- 26.2: raw unobfuscated source; the ready marker's source-manifest digest matches. `BlockItemIds.java` and `SlabBlock.java` match listed source hashes. The report correctly does not infer an old-to-current state-ID mapping from these registrations.

The audited report discloses the absent ready-to-provenance digest links for 1.13.2 and 26.2. I confirm the source-manifest digest checks and the relevant source bytes; that bookkeeping limitation does not change the source-level alias verdict.

## Source trace

- 1.13.1 and 1.13.2 Feather `net/minecraft/block/Block.java:1555`: `stone_slab` is registered as a `SlabBlock`; no `smooth_stone_slab` registration appears in the old registration family. The relevant registration source SHA is `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- Both old Feather `net/minecraft/block/SlabBlock.java:25-59`: `HALF` uses shared slab type property, `WATERLOGGED` is included, default is bottom/dry, and TOP returns `TOP_SHAPE = Block.block(0,8,0,16,16,16)`. `Properties.java` names these `type` and `waterlogged`; `SlabType.java` serializes TOP as `top`. `Block.java`'s default `getCollisionShape(state,world,pos)` delegates to `state.getShape(world,pos)` when collision is enabled.
- 1.14 Feather `net/minecraft/util/datafix/DataFixes.java:452-476`: schema 1802 maps block `minecraft:stone_slab` to `minecraft:smooth_stone_slab` (and the matching item separately). The applied block-state fixer updates `Name` in the remainder record, leaving other properties carried through. `net/minecraft/block/Blocks.java:1313-1316` registers `stone_slab` and `smooth_stone_slab` as distinct slab instances.
- 1.14.1 Yarn `net/minecraft/block/Blocks.java:1355-1358` also has distinct registrations. Its `SlabBlock` and `Properties` preserve the `type`/`waterlogged` schema; enum `field_12679` is `top` and maps to the top-half shape.
- 26.2 unobfuscated `net/minecraft/references/BlockItemIds.java:264-265` defines separate raw IDs, and `net/minecraft/world/level/block/Blocks.java:3296-3305` separately registers both. `SlabBlock.java:33-63`, `BlockStateProperties.java`, and `SlabType.java` retain `type`, `waterlogged`, `top`, and the top-half shape.
- 1.13.1/.2 Feather `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:871-876` invokes `autoJump` after `super.move`. The body at `:882-980` has the `ticksToNextAutojump <= 0`, on-ground, non-sneaking, non-riding and input gates; it checks the two initial headroom collision shapes, builds a swept collision query, uses the collision boxes against two offset probe segments, checks the block above a candidate obstacle, and writes `ticksToNextAutojump = 1` only when measured rise `u` satisfies `u > 0.5F && u <= 1.2F`. The 1.13.1 and 1.13.2 complete client-player source files have identical SHA-256, so this exact method body is the same in both assigned releases.
- The 1.14 callback carries the corresponding method and adds a `CollisionContext`; 1.14.1 `ClientPlayerEntity.move` invokes `method_3148`, whose body contains the ordinary AutoJump path. These later callbacks corroborate availability only, not the requested old-coordinate timer witness.

## Remaining gate for a timer witness

To upgrade the coordinate-specific movement verdict, independently bind the candidate scene to the old method's runtime inputs: player's post-move `x/z`, `getShape().minY/maxY`, `dx/dz`, width/height, timer/onGround/input/facing gates, both initial headroom positions, collision boxes returned for the swept box, the two probe intersections, the candidate collision box's `maxY`, each relevant above-block shape query, and final `u`. Until that trace demonstrates `ticksToNextAutojump = 1`, retain the report's explicit geometry limitation.
