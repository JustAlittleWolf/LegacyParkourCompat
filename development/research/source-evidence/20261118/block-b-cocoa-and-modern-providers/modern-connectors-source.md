# COMPLETED — endpoint source inventory: ordinary dry solid connectors

Scope: 1.21.11 Mojmap to 26.1 published unobfuscated sources; ordinary local collision-shape providers for fixed walls, fences, fence gates, iron bars, and glass panes. Static source only. No movement finding was established by this bounded endpoint comparison, so there are no canonical difference drafts.

## Source identity and integrity

- `1.21.11/mojmap`: ready marker status `ready`; exact version ID `1.21.11`; source manifest `mojmap.sources.sha256` SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`; version provenance `mojmap.provenance.json` SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`.
- `26.1/unobfuscated`: ready marker status `ready`; exact version ID `26.1`; source manifest `unobfuscated.sources.sha256` SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`; version provenance `unobfuscated.provenance.json` SHA-256 `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b`.
- The source-manifest digests match the assigned identities and each ready marker binds the same digest. Each source member named below was checked against its corresponding manifest entry. This establishes published source integrity, not historical behavior outside the bounded slices.

## Paired endpoint evidence and bounded equivalence

Paths below are relative to each version/mapping root. Line references are one-based.

- **Fixed wall states and post/arms:** `net/minecraft/world/level/block/WallBlock.java`, 1.21.11 lines 35–97, 105–129, 154–231; 26.1 lines 35–97, 105–129, 154–266. The six properties are UP, four `WallSide` values, and WATERLOGGED; defaults and property list correspond. Both versions build separate outline and collision shape functions over the state space, with post and directional wall boxes combined in the same iteration, and route ordinary collision queries to the collision function. Placement/update connection order, wall-test/covered logic, and post raising have the same gates and operation ordering in the inspected endpoint bodies.
- **Fence arms:** `net/minecraft/world/level/block/FenceBlock.java`, 1.21.11 lines 29–111; 26.1 lines 29–125, paired with `CrossCollisionBlock.java`, 1.21.11 lines 23–74; 26.1 lines 23–83. Four horizontal booleans and WATERLOGGED are the legal fence state dimensions. Both use the same post width/height, wall width/height, and 24-high collision dimensions; arm assembly unions the same rotated `boxZ` arms for enabled properties. Connection predicates retain face-sturdy, same-fence wood-family, and aligned-gate branches in the same order. The concrete fence registration remains `FenceBlock::new` at `Blocks.java` 1.21.11 lines 2108–2112 and 4209–4224 / 26.1 lines 2120–2124 and 4223–4238 for oak/dark-oak/pale-oak representatives.
- **Iron bars and clear/stained panes:** `IronBarsBlock.java`, 1.21.11 lines 26–97; 26.1 lines 26–110, paired with `CrossCollisionBlock.java` above. Both have the same four arm booleans and WATERLOGGED property, the same 2-wide post/arms, 16-high outline and collision, and the same attachment predicate and direction ordering. `StainedGlassPaneBlock.java` 1.21.11 lines 8–30 / 26.1 lines 8–30 inherits the same provider and defaults. `Blocks.java` registers iron bars at 2419–2421 / 2432–2434, glass pane through `IronBarsBlock::new` at 2441–2446 / 2454–2459, and stained panes through `StainedGlassPaneBlock` at 3120–3199 / 3134–3213.
- **Fence gates:** `FenceGateBlock.java`, 1.21.11 lines 44–100, 124–147, 198–203; 26.1 lines 44–107, 131–154, 209–214. State dimensions are horizontal facing, OPEN, POWERED, IN_WALL, with matching defaults. Direction-axis lookup, closed collision shape, and OPEN-to-empty collision gate match. IN_WALL changes outline/occlusion providers, not ordinary collision. Representative oak gate constructors are at `Blocks.java` 1.21.11 lines 2529–2533 / 26.1 lines 2546–2550.
- **Ordinary local state collision retrieval and cache:** `net/minecraft/world/level/block/state/BlockBehaviour.java`, 1.21.11 lines 308–325, 643–656, 895–926; 26.1 lines 329–346, 672–685, 916–947. The context-free state overload returns cached collision shape when a cache exists, otherwise delegates with `CollisionContext.empty()`; the contextual overload dispatches through the block with the supplied state, getter and position. The base block fallback still returns its outline shape when `hasCollision`, else empty. Cache construction still computes using `EmptyBlockGetter.INSTANCE`, `BlockPos.ZERO`, and empty context, and checks offsets/large shapes. `BlockGetter` 1.21.11 lines 32, 104–107 / 26.1 lines 32, 108–111 confirms ordinary local retrieval from the requested block state at that position; the floor-height consumer is outside this assignment.

### Witness / bounded equivalence

For a fixed legal fence/bar state, evaluating either endpoint provider follows `post = column(postWidth, 0, postHeight)`, then visits the four horizontal direction/property entries and unions `rotateHorizontal(boxZ(wallWidth, wallBottom, wallTop, 0, 8))` once for each true arm. `WATERLOGGED` is explicitly excluded from shape enumeration. Ordinary collision selects the separately precomputed collision function; fence uses 24 high dimensions and bars use 16 high dimensions. For a gate, equal facing axis and `OPEN=false` selects the same oriented `column(16,4,0,24)` collision shape; `OPEN=true` selects `Shapes.empty()`. This is a method-level witness for provider equivalence, not a player trajectory or full collision-engine proof.

## Unresolved gates and exclusions

- Exact intermediate patch-state and registration joins between these endpoints remain uninspected; introduction/cutover claims are open.
- Full registry-content enumeration, all registration/property initializer joins, and independent full-pair coverage audit remain open. The representative constructor sites establish concrete providers for the assigned families only.
- No claim is made about neighbor-state production as a whole, support shapes, AutoJump, generic world collision traversal, fluids or callback effects, movement-engine callers, runtime parity, or logical side gates.
- Pane geometry is inherited from `IronBarsBlock`; only the subclass/default-state path and representative registration constructors were traced. Other pane constructor registrations were inventoried as registrations but not individually audited for all settings.

## Checked member digests

The following SHA-256 values are source members verified against the corresponding source manifest:

| Relative source member | 1.21.11 Mojmap | 26.1 unobfuscated |
|---|---|---|
| `net/minecraft/world/level/block/WallBlock.java` | `30f65426c97e2507c13e55edbd078ab86f12d24a1496adb80e5d3cd44b2880f4` | `4dc61223dce4023deb3a7986983db677ccf4c89a53778692bacb301927b9b345` |
| `net/minecraft/world/level/block/FenceBlock.java` | `5cd60ac6466efbd64db7971ee2f6fcd519d12794be084afe40d1c4e085065326` | `be5e1aa19a07d68457ff21968fb4e264669588fb6e83a86a6f51e76833f17f93` |
| `net/minecraft/world/level/block/IronBarsBlock.java` | `09c0275dec572ad800acf4878ff113ef4ce697a2b8ed830851c5c9a3cfa6759e` | `b8d8467db13454fc5cd24c95827172b8550d5cba3d51930ba8b240b960606e54` |
| `net/minecraft/world/level/block/FenceGateBlock.java` | `9f82118f3202e696688b6a83e0fd3cb649169ea1aa25cfe7cf86302a8e40c2ce` | `e9e51b00bf59a66951741e0f0ef28d5d92815df9591e4f229fdb838588d105df` |
| `net/minecraft/world/level/block/CrossCollisionBlock.java` | `5ab90620c172821dd0080599262ab786ab63862c54651bbc396d8b137b1db46d` | `e6cea1c67ebf69f726e3dca7781cf83249a20990c339e4d053f12ec21f1fa05c` |
| `net/minecraft/world/level/block/StainedGlassPaneBlock.java` | `17c9aa18495f34d84c60e313df407f5c920fdd71c56ddc92d125f39d4c677787` | `dfaee99dba568ab4b0c791db1e4733fda0205ab53a82e1e039481bf8696492de` |
| `net/minecraft/world/level/block/Blocks.java` | `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `net/minecraft/world/level/block/state/BlockState.java` | `a3eec38ae13ae1e1305960fa13e34d8843c1ae9480c75d6ca1450d590ba9bb7b` | `df5b24701ee69145a78901ee0e370e063b63b3e74a0aae32c89b2ae45bc6d205` |
| `net/minecraft/world/level/BlockGetter.java` | `fd70107833086272078645cb01eee992528cfae269e6cac9c07fba622e2e12c9` | `623a44206db93d8fb2eefc9353345689cc35e0c1742b5d61789eab2b098b687d` |
