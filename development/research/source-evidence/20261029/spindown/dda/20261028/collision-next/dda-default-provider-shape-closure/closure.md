# 1.8.9 default ray-provider and shape-input closure

## Scope and result

This is a source-only dependency closure for a historical per-cell ray callback. It inspects canonical 1.8.9 Feather Gen2 build.2 and current 26.2 unobfuscated source, plus the three explicitly accepted source artifacts and the accepted neutral provider/hit report. It does not inspect product or candidate trees, backlog, wiki material, tests, builds, or runtime. It does not repeat the accepted cache-writer/Anvil/Chest movement-consumer proof or the accepted alias map.

**Result: provider behavior is source-closed for 1.8.9, but the inputs needed to execute the default provider are not.** The default `Block.rayTrace` depends on the exact old registered `Block` object (including constructor-initialized bounds), virtual old `updateShape(WorldView,pos)` behavior, and an old-compatible world view at the cell and any positions it reads. A current state at one cell, even paired with its exact old numeric ID, is not enough where the provider consumes legacy metadata fields, paired halves, neighboring states, or block-entity context. An exhaustive lossless old-ID/state crosswalk plus the required neighborhood/context reconstruction could suffice; the accepted mapping evidence does not establish that full input.

This closes only source readiness for **1.8.9**. No 1.8 or 1.8.1 source endpoint is available in this evidence, so `V1_8` remains native.

## Old default provider and callback result semantics

Canonical `1.8.9/ornithe-feather/net/minecraft/block/Block.java` has these relevant operations:

- The `Block` constructor initializes its six `min/max` fields to the unit cube through `setShape` (`Block.java:198-207`; `292-298`). Each registry block is an object with these mutable instance bounds.
- Default `canRayTrace(state,allowLiquids)` delegates to `canRayTrace()`; the latter returns true (`356-362`). The camera-specific exclusions are documented in the accepted full provider inventory: air and fire reject; liquid rejects with camera `allowLiquids=false`; stairs delegate eligibility to their base block.
- Default `rayTrace(world,pos,start,end)` first makes the virtual call `updateShape(world,pos)`, then translates both endpoints to block-local coordinates and computes candidate intersections in minX, maxX, minY, maxY, minZ, maxZ order (`456-516`). It accepts inclusive face bounds and replaces the selected candidate only for a strict smaller squared distance. No intersection returns `null`; any intersection returns a `HitResult` for the selected face and point. The update occurs even on a miss.
- Default `updateShape` is empty (`596-597`). Overrides and virtual dispatch are what make the default provider state- and world-dependent.
- Default collision collection separately reads the same mutable `min/max` through `getCollisionShape`, then adds an intersecting box (`339-350`). This explains the accepted cache-consumer dependency; it does not make current collision geometry an equivalent ray provider.

For the camera's old world visitor, `null` from a provider means keep traversing; the first non-null hit stops traversal. A failed legacy eligibility gate is not provider `null`: the accepted old hit-contract records its remembered MISS rules for non-start cells. The camera's `ignoreBlocksWithoutCollision=false` short-circuits the optional collision-shape eligibility prefilter, so collision shape must not be used to suppress the old `canRayTrace`/provider call. The exact caller flags and DDA/MISS details remain bound to the accepted contract and inventory rather than re-proved here.

There are six non-default provider families in the accepted inventory: rails, doors, trapdoors, torches, stairs, and moving-piston blocks. Rails, doors, and trapdoors perform an explicit `updateShape` before delegating to default `rayTrace`, which calls it again. Torch selects orientation bounds before delegating. Stairs has its internal ordered sub-box ray pass and result selection. Moving blocks return `null` without delegating. These override semantics must be retained even when the default provider is otherwise used. The complete class/method list and source fingerprints are in the accepted `full-ray-provider-inventory.md` and `provider-and-hit-contract.md`.

## Required historical provider inputs

The default provider's input is the old block object plus the `WorldView` observed by its virtual shape update, not merely an abstract current outline box. The accepted writer inventory contains 35 world-aware shared-bound writers and is the source of truth for that set; grouped by the fields/context they require, the unresolved reconstruction work includes:

- Cell state: facing/axis, half, powered/pressed, age, layer count, slab type, extended state, portal axis, connected faces, and other per-block legacy state selectors. Concrete examples are `AnvilBlock.updateShape` (`AnvilBlock.java:67-73`, reads `FACING`), `StemBlock` (`AGE` in the accepted inventory), and `SnowLayerBlock` (legacy layer count).
- Neighbor state: chest adjacency and priority (`ChestBlock.java:56-68` reads north, south, west, east in that order); fences, panes, walls, vines, and tripwire connectivity; rail and stair neighbor-dependent state; and attachment/neighbor context for related blocks. The visitor must supply old-equivalent lookups with the same block identity and state at those positions.
- Multi-cell state: `DoorBlock.updateShape` calls `getCombinedMetadata` (`DoorBlock.java:77-84,222-235`), which reads the current cell and both vertical neighbors, reconstructing upper/lower, facing/open, and hinge/powered bits from legacy metadata. A one-cell current state cannot reproduce that call by itself.
- Provider-internal state and order: stairs reads `FACING` and `HALF` and evaluates its eight sub-boxes in its own strict-selection order (`StairsBlock.java:502-535`); rail/door/trapdoor provider overrides repeat their writer before the default path.
- Block identity and fixed bounds: blocks without a dynamic `updateShape` writer still reach default clipping. Old `Block` defaults to a unit cube, while constructors establish fixed per-block bounds. Direct constructor `setShape` sites found in this pass include `DeadBushBlock.java:20`, `DiodeBlock.java:20`, `DragonEggBlock.java:19`, `EnchantingTableBlock.java:21`, `EnderChestBlock.java:29`, `FarmlandBlock.java:25`, `LilyPadBlock.java:18`, `MushroomPlantBlock.java:13`, `NetherWartBlock.java:23`, `PlantBlock.java:26`, `RedstoneWireBlock.java:46`, `SaplingBlock.java:32`, `SignBlock.java:22`, `SugarCaneBlock.java:24`, `TallPlantBlock.java:29`, and `WheatBlock.java:21`; `FlowerPotBlock.java:29-33` calls its fixed `resetShape` at `41-45`. This is a source-scan aid, not a separately audited exhaustive registry-to-fixed-bounds table.
- Bounds written outside default `updateShape`: `BrewingStandBlock.addCollisions` (`64-69`) writes bounds for multiple collision boxes, then `resetShape` restores a `1/8`-high bound (`72-74`). `CauldronBlock.addCollisions` (`36-49`) writes the bottom and four walls, then resets to a full cube (`52-54`). `EndPortalFrameBlock.addCollisions` (`39-48`) writes the frame and optional `EYE` box, then resets to height `13/16` (`34-35`). These are not called by camera `World.rayTrace`; camera flags also skip its collision-shape prefilter. However, all write shared fields that default `Block.rayTrace` later reads, so these classes demonstrate why initial/reset and intervening bound-write history must be checked before treating default geometry as a pure function of the currently visited cell. In particular, `BrewingStandBlock` and `EndPortalFrameBlock` have no constructor call to `resetShape` in the inspected class, and their collision routines leave the reset bound after normal return. The accepted writer inventory covers virtual `updateShape` writers, not this wider mutable-bounds lifecycle.

An exact old ID identifies a registry object, but does not encode the old state selector by itself. A current state can supply that selector only after a lossless per-ID property/metadata conversion is established. The accepted `closure.md` under `dda-ray-alias-state-map` explicitly says its role rows do not establish this conversion for all custom providers and do not enumerate every default provider's fixed bounds or old-state mapping. It therefore does not close default-provider inputs.

## Current 26.2 shape and clip APIs

In canonical current source, `BlockGetter.clip` (`net/minecraft/world/level/BlockGetter.java:65-95`) visits cells using modern `traverseBlocks`, obtains a `ClipContext` block shape and fluid shape, clips both, chooses the nearer result, and may adjust a block hit's direction from the interaction shape. `ClipContext.getBlockShape` (`net/minecraft/world/level/ClipContext.java:48-54`) delegates through the selected shape getter; its block modes include collider, outline, and visual (`56-59`).

`BlockBehaviour.BlockStateBase.getShape` (`net/minecraft/world/level/block/state/BlockBehaviour.java:678-684`) dispatches through the current `BlockState` to `Block.getShape`; interaction and collision shapes dispatch through separate state/block APIs (`686-707`). Current state-driven `VoxelShape` clipping (`VoxelShape.java:163,181`) uses current AABB clipping. These APIs have no call equivalent to old `canRayTrace`, old `Block.rayTrace`, or old mutable per-block `min/max` updates. A chosen modern outline/collision/interaction shape cannot establish legacy provider result or stop semantics.

## Closure required before the input can be considered complete

The missing source artifact is a source-audited **full 1.8.9 registered-block provider-input crosswalk to 26.2**, including:

1. Every old numeric block ID/registry key, exact 26.2 counterpart or an explicit unsupported/current-only policy, and each old provider implementation selected by virtual dispatch.
2. A lossless old metadata/`BlockState` decoder-to-current-state conversion for all old identities, including block variants, plus explicit handling of old states not representable by a current `BlockState`.
3. The old provider's constructor/fixed six bounds and every virtual `updateShape` result, with helper/reset/constructor ordering resolved; state and world fields consumed by each writer; and old-equivalent neighbor/paired-cell/block-entity reads.
4. A scan of every old write/reset of the shared six bounds beyond the accepted `updateShape` inventory, including constructor initialization, custom collision-box routines, and external reset call sites; preserve any preceding-call/lifecycle state that the default provider can observe.
5. For each legacy identity, proof that its current-state mapping preserves `canRayTrace` eligibility and the old provider's `null` versus hit behavior (including repeated calls and internal multi-box selection), or a documented legacy implementation for that operation.
6. Independent audit against canonical source so no default-provider first hit can be skipped and no unmapped current state silently changes traversal continuation.

The crosswalk must cover all default providers because any camera-eligible cell may be the first non-null hit and stop later cells' writer calls. It should extend the accepted custom-provider identity rows rather than restate them. Keep the native fallback for any unsupported/unmapped state, and keep `V1_8` native pending exact sources.

## Evidence identities and limits

Canonical read-only roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/` (Feather Gen2 build.2; ready marker SHA-256 `FE0EC792349AE43C91D0A30F23660C94B2F5C8D31A29643F73ED0DE367110B95`; source manifest SHA-256 `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`) and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/` (ready marker SHA-256 `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; source manifest SHA-256 `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`).

| Evidence | SHA-256 |
|---|---|
| Accepted `dda-provider-closure/full-ray-provider-inventory.md` | `5B970E9685C73E044F51771CA5FE79F00E4D97984F44E17F82978CB6EF05914E` |
| Accepted `dda-provider-closure/product-reconciliation-rebind-r2.md` | `08C50FC52A0CD4A66DE34586FB21F4D28112ABFA5E65DEAA9FDC7CCBFF73F274` |
| Accepted `dda-ray-alias-state-map/closure.md` | `811DBAB2A71571C06AA43A9283373967F8D8F845443ABEEA2119E0CC8B43761B` |
| Accepted `dda-legacy-provider-closure/provider-and-hit-contract.md` | `20904785D6D067658FC433842146D364851822CAF7C7B8565CA5C8B761894A29` |
| 1.8.9 `Block.java` | `EA10F05106A3CF7189AEC85236A7ECF9C7106A717F37BED583B5EA4ADEFFA528` |
| 1.8.9 `DoorBlock.java` | `51B93FCB01758E1B809A503A9E63AAB6D781DBD9A0E4D3B95823168E8958128D` |
| 1.8.9 `ChestBlock.java` | `8072BC317398297D1327A3BE4EDD8A66381A1A1021A6C4A8F3FBEEBCA2891D51` |
| 1.8.9 `AnvilBlock.java` | `7E52C4A86C29548D8DC491FE7D4FF17A79C1DC9F426F538A6C19E161F99F013E` |
| 1.8.9 `StairsBlock.java` | `FADC964480227F6FFD5756471212B18D0172BFFBCD0866EE0C278454AB079409` |
| 1.8.9 `BrewingStandBlock.java` | `6763A56C3328BAA51FCC02ED539F7AD76F299F0DA892A5B3632186F1AF149170` |
| 1.8.9 `CauldronBlock.java` | `CBC1724EA6707CE7F22981867CB62D4931240499291D18BD5E45A81D7A33836A` |
| 1.8.9 `EndPortalFrameBlock.java` | `C2C876AEFE34D001FF0E3EEBDDD0DF01EE85B0BF499E205EB1EB95EC44857B9E` |
| 1.8.9 `FlowerPotBlock.java` | `DBAB0443AE1D820786F92D6C1E0A875C0D5D33CC16347B2760F7FEE405164F09` |
| 26.2 `BlockGetter.java` | `623A44206DB93D8FB2EEFC9353345689CC35E0C1742B5D61789EAB2B098B687D` |
| 26.2 `ClipContext.java` | `67F5569E00DAC4169F54C42C2D237B1488EA3C7C8E00889603FC748E3C24ECBC` |
| 26.2 `BlockBehaviour.java` | `9C7A103492D0714C90397DA88EB696912FF6A9CA1C005D984C4746D52637FD1E` |
| 26.2 `VoxelShape.java` | `419A46BBCC8B48E1C075127954EB243DCC60D10D4087BF256D895CF91A2FF854` |
| 26.2 `AABB.java` | `1FFDD0F6D26A4DD1E26407EA9A386EAB7903E43462E60C9AE5FDC50A0468F10B` |

No product, candidate, backlog, wiki, build, test, game/runtime, or source-generation material was read or used. The known fixed-bound class scan above is not the required independently audited full crosswalk.
