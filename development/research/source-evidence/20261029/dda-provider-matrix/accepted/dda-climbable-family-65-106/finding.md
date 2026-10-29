# Legacy climbable block provider and alias closure: IDs 65 and 106

## Scope and result

This source-only closure covers 1.8.9 registered Ladder (numeric block ID 65, registry key `ladder`) and Vine (ID 106, key `vine`) against the same-named 26.2 block keys. It classifies their movement-collision providers and state inputs. It makes no claim about DDA cell visitation, provider call order, old/new ray-hit equivalence, or a compatibility implementation.

The old movement collision provider includes Ladder geometry and excludes Vine geometry. In 26.2, both registered blocks set `noCollision()`, so their collision-shape result is empty independent of their separately supplied visible shape. This is an actual provider difference for this pair, not evidence about whether a particular historical DDA path calls collision-shape or ray-trace providers.

## Identity and current aliases

- 1.8.9 `Block.java` registers ID 65 as key `ladder` and constructs `LadderBlock` (line 965); ID 106 as key `vine` and constructs `VineBlock` (line 1046).
- 26.2 `BlockItemIds.java` defines `LADDER` from `ladder` (line 316) and `VINE` from `vine` (line 373). 26.2 `Blocks.java` registers those IDs with `LadderBlock::new` (1442–1446) and `VineBlock::new` (2429–2441). Thus the current registry aliases for these two old names are `minecraft:ladder` and `minecraft:vine`.
- This is an exact same-key mapping for these two entries. It does not assert equivalence of their shape, collision, or ray-trace behavior.

## Old 1.8.9 state and movement-collision providers

### Ladder (ID 65)

`LadderBlock` has only `FACING`, defaulting to NORTH (lines 17–22). Metadata is decoded by `Direction.byId`; vertical directions are normalized to NORTH (116–123), and encoded from that direction's ID (125–127). For valid horizontal states, the collision callback updates shared block bounds from `FACING`, then delegates to `Block.getCollisionShape` (26–28, 38–56). The bound thickness is `0.125F` and covers the full other two axes. `Block.getCollisionShape` constructs the world-positioned AABB from the block's mutable min/max bounds (Block.java 346–349). `Block.addCollisions` appends that AABB only when non-null and intersecting the query box (339–344). Therefore historical Ladder is a non-null, facing-dependent collision provider; the bound mutation is part of that provider operation.

### Vine (ID 106)

The old block state declares `UP`, `NORTH`, `EAST`, `SOUTH`, `WEST`, with all false by default (VineBlock.java 24–36). Its 4 metadata bits encode only the lateral flags: bit 0 SOUTH, bit 1 WEST, bit 2 NORTH, bit 3 EAST (334–357). `UP` is a virtual property derived from whether the block above blocks ambient light (39–42), not from those metadata bits.

Despite its bounds and outline updates, old Vine's `getCollisionShape` returns `null` unconditionally (127–129). The generic `Block.addCollisions` null check therefore excludes Vine from the ordinary movement collision list. Its `updateShape` does mutate the block's bounds using the side flags and, if there are no side flags, the block above (65–124); this is relevant to the distinct outline/ray-trace path, not evidence of collision geometry.

## Current 26.2 collision gate versus visible shape

`Blocks.java` registers Ladder with `forceSolidOff().noOcclusion()` but does not set `noCollision()` (1442–1446). `LadderBlock.getShape` returns a facing-indexed voxel shape (42–44). However, `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` whenever `hasCollision` is false, otherwise it uses the state's shape (327–329). The `BlockBehaviour.Properties` default is `hasCollision = true` (BlockBehaviour.java 982–986), and Ladder's listed current properties do not clear it; its collision provider is the facing shape. This differs from the old `.125F` AABB by shape representation/dimension, which is not compared here.

`Blocks.VINE` explicitly sets `noCollision()` (2432–2440). `VineBlock.getShape` builds a union from the horizontal-direction properties, with a full-block fallback when none are set (54–72), but the inherited collision gate returns empty due to `noCollision()`. The `UP` state property is not among the directional properties folded into that shape (34–38, 59–65). Thus modern visible-shape calculations, including the full-block fallback, must not be confused with Vine's empty collision provider.

## Separated ray-trace/outline path

Old Ladder explicitly calls `updateShape` from both `getCollisionShape` and `getOutlineShape` (25–35); the ray-trace path in `Block.rayTrace` also calls `updateShape` before intersecting the mutable bounds (456 onward). Old Vine updates bounds for its outline, but its collision method remains null. These facts describe distinct APIs. This report does not infer that the DDA collector uses either API, nor that current modern traversal asks for collision shape rather than outline shape.

## Hashes

See `sources.sha256` and `sources.sha256.json` for SHA-256 digests of every exact source file cited above.
