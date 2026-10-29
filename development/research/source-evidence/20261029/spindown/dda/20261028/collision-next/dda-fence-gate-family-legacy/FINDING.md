# DDA legacy fence and gate provider closure (1.8.9 → 26.2)

Status: bounded source closure for the listed fence/gate families. This closes provider semantics and identifier crosswalk inputs only; it does not claim full DDA traversal equivalence or modern-neighbor parity.

## Source identities

Canonical old source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`
Canonical current source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`

Exact files and SHA-256 digests are in `sources.sha256`. Hashes are over the canonical source files, not copied extracts.

## Old registration and metadata

Old `net/minecraft/block/Block.java` registers IDs/keys as follows:

| ID | old block key | family | old constructed type |
|---:|---|---|---|
| 85 | `fence` | regular fence | oak-colored `FenceBlock(Material.WOOD, OAK)` |
| 107 | `fence_gate` | gate | oak `FenceGateBlock` |
| 113 | `nether_brick_fence` | regular fence | `FenceBlock(Material.STONE, MapColor.NETHER)` |
| 183 | `spruce_fence_gate` | gate | spruce `FenceGateBlock` |
| 184 | `birch_fence_gate` | gate | birch `FenceGateBlock` |
| 185 | `jungle_fence_gate` | gate | jungle `FenceGateBlock` |
| 186 | `dark_oak_fence_gate` | gate | dark-oak `FenceGateBlock` |
| 187 | `acacia_fence_gate` | gate | acacia `FenceGateBlock` |
| 188 | `spruce_fence` | regular fence | wood `FenceBlock` |
| 189 | `birch_fence` | regular fence | wood `FenceBlock` |
| 190 | `jungle_fence` | regular fence | wood `FenceBlock` |
| 191 | `dark_oak_fence` | regular fence | wood `FenceBlock` |
| 192 | `acacia_fence` | regular fence | wood `FenceBlock` |

`Block.getStateFromMetadata` returns `defaultState` unless overridden. `FenceBlock` does not override it and has default NORTH/EAST/SOUTH/WEST=false, so the fence metadata nibble does not supply connectivity. Its virtual properties are resolved from the world when state is requested, but `addCollisions` itself recomputes all four neighbor tests from the live world. `getMetadataFromState` is inherited and rejects a nonempty property set; this reinforces that those connection booleans are not serialized metadata.

Gate metadata is a separate, fully decoded 4-bit layout. `Direction.byIdHorizontal(metadata)` wraps the low two bits to a horizontal direction (`abs(id % 4)`); metadata bit 2 sets OPEN and bit 3 sets POWERED. IN_WALL is omitted by decoding and defaults false, then `resolveVirtualProperties` may set it from exact neighboring cobblestone-wall blocks. Thus all sixteen nibble values decode to a horizontal facing plus OPEN/POWERED flags; no out-of-range nibble is possible after the masks/wrap. Gate shape provider behavior does not use POWERED or IN_WALL for collision.

## Legacy fence provider and mutable bounds

`FenceBlock.addCollisions` tests live neighbors in fixed order north, south, west, east through `shouldConnectTo`. It builds and clips the north/south arm first when either connects, then the west/east arm when either connects or when neither north nor south connects. The arms are 0.25 wide (`0.375F..0.625F`), extend to Y=1.5 for collision, and the north/south or west/east ends reach the block boundary only on connected sides. This composition emits the center post even with no neighbors and emits both crossing arms where the respective axes connect. The final `setShape` restores the connected horizontal bounds at Y=0..1 after temporary 1.5-high collision boxes. The base `addCollisions` clips one current mutable-bounds-derived box against the query and appends it on strict intersection.

Neighbor admission is legacy-specific and source-defined: BARRIER is always rejected; another `FenceBlock` connects only when its material is the same; every `FenceGateBlock` connects irrespective of its facing; otherwise connection requires solid-blocking material and `isCube()`, while pumpkin material is excluded. Do not replace this with the current tag/sturdy-face rule or current gate-axis check.

## Legacy gate provider and mutable bounds

`FenceGateBlock.getCollisionShape` returns null when OPEN; otherwise it returns one axis-aligned slab with a 0.25-wide center strip, full horizontal extent along the facing axis, and Y=0..1.5. The exact formula uses FACING axis, not POWERED/IN_WALL. This direct collision box is distinct from the gate's mutable outline bounds: `updateShape` sets an axis-aligned strip only 0..1 high. Base `rayTrace` first calls `updateShape(world,pos)`, then reads min/max bounds; therefore a fence or gate rayTrace refreshes mutable bounds from live neighbors/facing before ray intersection. Fence collision assembly also mutates the shared block instance bounds during movement queries, with a final height-1 reset. A later rayTrace will overwrite those bounds before using them; collision queries can still observe/mutate shared bounds in their own call order. This family closure does not assert a particular global provider visitation order.

## Current identifiers and provider divergence

The exact 26.2 `Blocks` registrations provide `OAK_FENCE`, `NETHER_BRICK_FENCE`, `SPRUCE_FENCE`, `BIRCH_FENCE`, `JUNGLE_FENCE`, `DARK_OAK_FENCE`, `ACACIA_FENCE`, and the corresponding seven current gate identifiers. `EntityBlockStateFix` retains flattened names keyed to the old numeric IDs: `fence`→85, `fence_gate`→107, `nether_brick_fence`→113, five gate variants→183–187, and five wood fence variants→188–192. `ItemStackTheFlatteningFix` separately explicitly maps old item aliases `fence.0`→`oak_fence` and `fence_gate.0`→`oak_fence_gate`; that item table is not treated as a block-state alias proof. For states, current registry constructors/keys plus the 26.2 legacy-name/ID table establish the relevant names and old IDs. Current state schema adds WATERLOGGED to fences; current fence shape uses state-stored directional booleans and a fixed central post/arms union, with collision arms extending to 1.5. Current connection updates use same wooden-fence tag class, directional fence-gate axis, and exception-or-sturdy-face rules. The legacy provider instead recomputes neighbors and admits any gate orientation or a legacy solid cube under the rules above.

Current gates preserve collision dimensions and OPEN gating: `SHAPE_COLLISION` is the centered 4/16 strip over a full 16/16 axis, Y=0..24/16; open gates return empty collision shape. POWERED does not change collision shape. Current IN_WALL changes outline/occlusion shapes only. Its wall predicate is `BlockTags.WALLS`, unlike legacy exact `Blocks.COBBLESTONE_WALL` in the same perpendicular-neighbor positions. Therefore ordinary old-state gate collision is directly representable from FACING/OPEN, while old `IN_WALL` and fence connection flags must not be inferred from modern neighbor-updated states if preserving the complete old operation.

## Application boundary

This closes the historical family provider as a canonical DDA hit-shape adapter input: a fence uses the old neighbor rule and emits its source-composed connected arms; a gate uses facing/open and emits its old direct collision slab or no hit box. It also closes current identifiers for the listed blocks. It does not implement ray traversal, prove chunk/loading visitation, establish that current block-state providers preserve old neighbor context, cover modern-only neighbor interactions, or resolve interaction with other cached mutable-bounds block families. Any consumer must feed the old neighbor admission from the received source-defined state/world context and keep the gate and fence operations distinct.
