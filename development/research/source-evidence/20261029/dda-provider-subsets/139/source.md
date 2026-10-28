# DDA provider closure: old block ID 139 cobblestone wall

## Source identity

Old source is Minecraft 1.8.9, ornithe-feather. Current source is Minecraft 26.2, unobfuscated. The ready markers and provenance/source manifests are included in sources.sha256. Their source-manifest digests match the ready markers, and every selected Java source hash matched its canonical manifest entry.

## Old ID, metadata and provider

In 1.8.9 Block.java registers ID 139 as the single cobblestone_wall WallBlock. Blocks.java exposes that registry key; there is no separate old mossy wall block key. WallBlock.Variant supplies NORMAL id 0 and MOSSY id 1. Variant.byId maps any negative or too-large ID to NORMAL, so metadata 0 and 2–15 yield NORMAL and metadata 1 yields MOSSY. The variant only changes wall material/name/drop identity; collision connections and box construction use the same WallBlock class.

WallBlock.getCollisionShape calls updateShape from the current world on every collision-provider query. It computes north, south, west and east using shouldConnectTo, starts with X/Z extents [0.25,0.75], expands the appropriate extent to the cell edge for each connected neighbor, then applies two straight-run special cases: north+south without west/east narrows X to [0.3125,0.6875]; west+east without north/south narrows Z to [0.3125,0.6875]. updateShape passes Y [0,1] to the mutable Block bounds setter, after which getCollisionShape overwrites the shared maxY with 1.5 and returns the superclass AABB. The result is one AABB, not a union of post and arms. The provider also leaves shared block bounds changed for later readers; this report does not prove a DDA visitor sequence or camera reachability.

Old shouldConnectTo rejects barriers, accepts the same WallBlock instance and any fence gate, and otherwise accepts a neighbor only when its material is solid-blocking, the block is a cube, and the material is not pumpkin. resolveVirtualProperties separately writes UP and four cardinal booleans, but the collision provider recomputes the side connections and does not use UP to construct its AABB.

## Current aliases, states and collision shape

26.2 registers distinct cobblestone_wall and mossy_cobblestone_wall keys, both as WallBlock. These are the natural key aliases for the old NORMAL and MOSSY variants; the old VARIANT property is not present in the current state schema. The current schema has UP, NORTH/EAST/SOUTH/WEST WallSide values NONE/LOW/TALL, and WATERLOGGED. Default state is UP=true, all four sides NONE, and WATERLOGGED=false.

26.2 WallBlock precomputes its collision-shape function with makeShapes(24,24), equivalent to heights 24/16 = 1.5. The central post is a 8/16-wide column from Y=0 to 1.5 when UP is true. Each connected side adds an arm 6/16 wide from the center toward the corresponding edge, also from Y=0 to 1.5. Because both low and tall height arguments are 24 for collision, LOW and TALL side values have the same collision geometry. The result is a union of component shapes stored by the state and returned through the normal collision provider; it does not mutate shared bounds. Both registrations inherit forceSolidOn from the cobblestone properties.

The local collision formulas agree for the bounded no-arm state: with four nonconnecting air neighbors, old updateShape leaves the central [0.25,0.75] X/Z post and maxY=1.5; the current state has all sides NONE and UP=true and returns the same post coordinates. This is only a local provider equality for that state/neighborhood, not a general alias equivalence.

For connected neighbors, the old single AABB and current component union do not generally match. For example, when all four horizontal neighbors connect, the old formula returns the full X/Z footprint [0,1] by [0,1] to Y=1.5. The current shape is a central post plus four 6/16-wide arms, leaving corner volumes empty. Even one north connection differs: the old AABB spans X [0.25,0.75], Z [0,0.75], while the current north arm is only X [0.3125,0.6875], Z [0,0.6875] outside the central post. Thus variant aliasing alone cannot recover the old collision provider for arbitrary wall states.

The neighbor admission rules also differ. Current WallBlock.connectsTo accepts any BlockTags.WALLS state, IronBarsBlock, selected direction-compatible fence gates, and non-exception neighbors whose queried face is sturdy. The old rule accepts the same old WallBlock and every fence gate without a direction test, otherwise using solid-blocking material plus isCube and one pumpkin exclusion. These predicates are not interchangeable. Waterlogging is ignored by the current cached collision-shape function, but this does not close old/current neighbor or connected-shape equivalence.

## Bounded conclusion

The evidence closes old ID 139 metadata-to-variant decoding, the two current key candidates, current state fields, and both releases' local collision formulas. A source-proven equal provider case exists for no connected horizontal neighbors (including four air neighbors). General wall-provider conversion remains open because the old provider creates one connection-dependent AABB while the current provider unions component arms, and because neighbor-connection gates differ. Keep any DDA adapter native for connected or otherwise unproved wall neighborhoods unless it independently establishes the old collision AABB and its current equivalent. This report does not establish traversal visitation, cache-observer reachability, or runtime parity.

## Source locations

- 1.8.9 Block.java: ID 139 registration at 1135; bounds setter at 292–298; AABB construction at 346–350.
- 1.8.9 WallBlock.java: variant/default state at 20–45; updateShape and two straight-run cases at 65–104; collision provider at 107–110; neighbor predicate at 113–123; virtual neighbor properties at 152–158; metadata fallback at 188–194.
- 26.2 Blocks.java: cobblestone and mossy wall registrations at 2699–2703.
- 26.2 WallBlock.java: properties/default state at 34–66; collision shape construction and state-based provider at 70–98; neighbor gates at 105–109; current state/property schema at 266–268.
- 26.2 Block.java: wall-connection exceptions at 252–260.
- 26.2 FenceGateBlock.java: direction-specific wall/gate admission at 209–211.
- 26.2 BlockStateProperties.java and WallSide.java: current property fields and NONE/LOW/TALL values.
- 26.2 BlockBehaviour.java: collision provider gate at 327–328; Blocks.java registrations use forceSolidOn.
