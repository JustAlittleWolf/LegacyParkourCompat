# Cauldron and hopper collision/provider closure (1.8.9 → 26.2)

## Scope and evidence identity

This report closes the local block identity/state and collision/ray-shape provider behavior for old registry IDs 118 (cauldron) and 154 (hopper). It does not establish DDA visitation, first-hit order, end-to-end ray parity, or a player trajectory. Exact source bytes and canonical-manifest checks are in `source-hashes.json` (SHA-256 `67E8EDF79F5A32931B02A7220034E2A7CFA4F6D590C34631E991D5148F166A21`). The inputs are ready canonical exports: 1.8.9 Ornithe Feather (ready marker `FE0EC792349AE43C91D0A30F23660C94B2F5C8D31A29643F73ED0DE367110B95`; source-manifest `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`) and 26.2 unobfuscated (ready marker `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; source-manifest `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894`). All 16 selected Java sources matched their canonical source-manifest rows.

## Legacy identities and provider behavior

`Block.java:1076,1169` registers ID 118 as `cauldron` and ID 154 as `hopper`.

### Cauldron, ID 118

`CauldronBlock.LEVEL` is integer 0–3; `getStateFromMetadata` stores metadata directly in LEVEL and `getMetadataFromState` returns it (`CauldronBlock.java:27,198–205`). The collision provider does not read LEVEL. `addCollisions` submits five boxes through the base collision append path: a full-footprint floor from Y=0 to 5/16, then four walls of thickness 2/16 extending to full height (`CauldronBlock.java:36–48`). It calls `resetShape` after the four walls; that reset restores the shared bounds to the full unit cube (`:52–54`). The result is a multi-box movement collision shape, independent of level. No neighbor or block-entity input is read by this provider.

The old ray provider is different from movement collision. `CauldronBlock` has no `updateShape` override; it inherits `Block.updateShape`, which is empty (`Block.java:596–598`). The inherited `Block.rayTrace` calls that method and clips against the block instance's current min/max bounds (`Block.java:456–459`); the base constructor initializes those bounds to a unit cube (`:201`). Thus the old ray bounds are full-cube even though movement collision emits only the floor and side walls.

### Hopper, ID 154

The old default state is FACING=DOWN and ENABLED=true; valid FACING excludes UP (`HopperBlock.java:29–39`). Metadata reads `Direction.byId(metadata & 7)` and sets ENABLED false when bit 8 is set (`:161–190`); valid legacy metadata map to down/north/south/west/east, with either ENABLED value. The current `BlockStateData` rows at IDs 2464, 2466–2469, 2472, and 2474–2477 preserve those exact hopper names and facing/enabled properties (`BlockStateData.java:6908–6957`). The provider geometry itself does not use ENABLED, FACING, neighbors, or the hopper block entity.

`HopperBlock.updateShape` writes a full unit cube for ray clipping (`HopperBlock.java:44–46`). Its old `addCollisions` emits a 10/16-high full-footprint base plus four 2/16-thick full-height walls, then resets the bounds to the full unit cube before return (`:49–62`). This movement provider is state- and block-entity-independent, while its inherited ray provider clips against the full cube. The old class creates a hopper block entity (`:75–76`), but the collision and `updateShape` methods do not read it.

## 26.2 aliases and providers

The 26.2 registry IDs explicitly name `cauldron` and `hopper` (`references/BlockItemIds.java:561,680`). `Blocks.java:2572–2588` registers ordinary, water, lava, and powder-snow cauldrons; the filled variants share `AbstractCauldronBlock`. That superclass owns a static SHAPE and returns it from `getShape` (`AbstractCauldronBlock.java:27–47,70–76`); its interaction shape is a distinct inner shape (`:75–77`). `LayeredCauldronBlock` carries LEVEL 1–3, and lava cauldrons use their own subclass (`LayeredCauldronBlock.java:28–45; LavaCauldronBlock.java:17`). The level/state variants do not change the inherited collision shape. Current cauldron collision is enabled by its registration (no `noCollision` call; property default is true at `BlockBehaviour.java:985`); the base collision provider delegates to the state's `getShape` (`:323–328`). The current OUTLINE clip obtains `BlockStateBase.getShape` (`ClipContext.java:58,91`), so it uses the shared hollow cauldron shape rather than the legacy full-cube ray bounds. Its construction is a boolean difference from a full block, not the old five `addCollisions` passes (`AbstractCauldronBlock.java:29–47`). No neighbor or block-entity input is read by this shape provider.

The 26.2 data-fixer table retains legacy cauldron IDs 1888–1891 as `minecraft:cauldron` with LEVEL 0–3 (`BlockStateData.java:4939–4942`). The registered `CauldronRenameFix` converts level 0 to propertyless `cauldron`, and nonzero levels to `water_cauldron` while retaining the level (`DataFixers.java:983; CauldronRenameFix.java:16–21`). This reconciles the legacy level states with the modern split cauldron registrations; the geometry is common across those modern variants.

`Blocks.java:2926–2930` registers the current hopper as a collision-enabled `HopperBlock` (collision defaults true at `BlockBehaviour.java:985`, and the registration has no `noCollision` call). Its state FACING and ENABLED properties are the corresponding current properties (`HopperBlock.java:41–55`), and the state shape uses a spoutless shell plus a spout clipped to the unit block according to FACING (`:64–80`). The default collision provider delegates to this state-dependent shape (`BlockBehaviour.java:327–328`). The current interaction shape is separate (`HopperBlock.java:78–80`). Consequently, the current collision and OUTLINE providers use the facing-directed hopper shape, differing from the old movement base-plus-walls provider and old unit-cube ray bounds (`ClipContext.java:58,91`). The shape method takes state but does not read a block entity or neighboring block.

## Bounded conclusion and open limits

The aliases and provider inputs are source-backed for these two IDs. The providers are materially different in composition: old cauldron/hopper movement collision runs shared-bound AABB append passes and resets the shared bounds; current collision uses state VoxelShapes. Old ray clipping for both is against a full cube; current OUTLINE clipping uses `BlockStateBase.getShape` (`ClipContext.java:58,91`), exposing the hollow cauldron and state-directed hopper shape. This closes only these provider families. It does not prove a particular visited-cell sequence, hit result, or player movement consequence, and it does not generalize the legacy-to-current crosswalk beyond the recorded cauldron and hopper states.



