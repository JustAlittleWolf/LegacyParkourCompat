# 1.8.9 brewing stand provider and cache closure

## Evidence identity

Canonical read-only sources were verified from their ready markers:

- 1.8.9, mapping ornithe-feather, Feather Gen2 1.8.9+build.2, manifest SHA-256 9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004.
- 26.2, mapping unobfuscated, manifest SHA-256 a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894.

The ready markers, provenance files, source manifests, and every Java file used are hashed in source-hashes.sha256. Every cited Java hash was recomputed and matched its canonical source manifest entry.

## Old identity and state

1.8.9 Block.java registers numeric block ID 117 as brewing_stand with a BrewingStandBlock instance (Block.java:1075). Blocks.BREWING_STAND resolves that key (Blocks.java:348). The block defines Boolean properties has_bottle_0, has_bottle_1, and has_bottle_2, all false in the default state (BrewingStandBlock.java:29-35). getStateFromMetadata maps bits 0-2 to those values and getMetadataFromState writes only those bits (BrewingStandBlock.java:145-170). Thus metadata 0-7 represents the eight property combinations; values 8-15 alias the same state as 0-7 because the upper bits are unused. These properties do not select collision bounds.

26.2 registers Blocks.BREWING_STAND through BlockItemIds.BREWING_STAND, whose key is brewing_stand (Blocks.java:2567-2571; BlockItemIds.java:679). The current block uses the same three Boolean properties, with all false in its default state (BrewingStandBlock.java:35-47,102). This closes the bounded registry-key and state-property alias, not all gameplay behavior.

## 1.8.9 mutable bounds and collision writes

The old Block constructor initializes every block instance's bounds to the full unit cube (Block.java:201). BrewingStandBlock does not replace that initial bound in its constructor. Its addCollisions method is state-independent and performs two superclass calls:

1. It writes bounds [7/16,0,7/16]-[9/16,14/16,9/16], then calls super.addCollisions (BrewingStandBlock.java:64-66). The superclass constructs a world-space box from the block instance's current mutable fields and adds it only when the query box intersects (Block.java:339-348).
2. It calls resetShape, which writes [0,0,0]-[1,2/16,1], then calls the superclass again (BrewingStandBlock.java:67-73). On return, the shared BrewingStandBlock instance's cached bounds are the low full-footprint platform, regardless of whether either box was added.

World's block collision loop calls each state's block instance addCollisions (World.java:900-921); it does not use a separate immutable per-position shape. Since the brewing stand is one registry singleton, its mutable bounds are shared across all brewing-stand positions. BlockModelRenderer.render(model,state,...) also calls block.resetShape() before rendering (BlockModelRenderer.java:284), setting the same low platform bounds.

## Ray-trace observer behavior

Block.rayTrace first calls updateShape(world,pos) and then reads the instance fields minX through maxZ for plane intersections (Block.java:456-466). The base Block.updateShape implementation is empty (Block.java:596), and BrewingStandBlock does not override it. Therefore ray tracing neither recomputes nor resets brewing-stand bounds; it observes whichever shared bounds were left by the last writer/reset. Before the first collision call or render reset, that is the constructor's full unit cube. After addCollisions or the render reset, it is the low platform. Entity.rayTrace delegates to World.rayTrace with ignoreBlocksWithoutCollision=false (Entity.java:1029-1033), and World.rayTrace dispatches to Block.rayTrace for ray-traceable blocks (World.java:633-649). This proves an old cache-read path, but does not claim which camera tick reaches it or a specific ray-trace outcome.

## 26.2 shape and bounded comparison

The modern block's fixed shape is Shapes.or(Block.column(2,2,14), Block.column(14,0,2)); getShape returns that static shape for every state, level, position, and collision context (BrewingStandBlock.java:38,61-62). Block.column centers X/Z width and divides its arguments by 16 through Block.box (Block.java:160-184). The modern components are:

- center column: [7/16,2/16,7/16]-[9/16,14/16,9/16];
- low platform: [1/16,0,1/16]-[15/16,2/16,15/16].

The old collision union contains a center column from Y=0 to 14/16 plus a full-footprint low platform [0,0,0]-[1,2/16,1]. The modern provider therefore omits the old platform's outer 1/16 rim on all four horizontal sides. The old stem portion below 2/16 is covered by its old full-footprint platform, so it adds no further union difference. This is a concrete provider-geometry mismatch for block collision queries, not merely a mutable-cache representation difference.

The modern registration has default collision enabled (BlockBehaviour.Properties.hasCollision=true; BlockBehaviour.java:985) and does not call noCollision; noOcclusion only clears occlusion (Blocks.java:2567-2571; BlockBehaviour.java:1094-1102). The base collision provider returns the state's shape while collision is enabled (BlockBehaviour.java:327-328). BrewingStandBlock returns one static shape and ignores bottle-state properties and collision context. Modern collision geometry is immutable and property-independent; it does not reproduce the old per-instance cache values observed by Block.rayTrace.

## Adapter implications and limits

For 1.8.9 collision-shape mapping, use a provider for old ID 117 / minecraft:brewing_stand that yields the two old boxes in source order, rather than accepting current getCollisionShape as equivalent. The eight bit states are shape-equivalent within 1.8.9, but an adapter must preserve the old collision path's two addCollisions calls, including intersection filtering and final cache state if another old observer reads it. A modern immutable shape is enough only when the caller needs the collision set and can use the explicit old boxes; it cannot emulate old shared Block bounds read by rayTrace.

This closes the exact 1.8.9 block key/state, old mutable-bounds write/reset order, ray-trace read semantics, and 26.2 shape formula. It does not prove all ray-provider traversal/last-writer equivalence, camera-tick reachability, a runtime witness, current/old chunk residency equivalence, or broader Brewing Stand behavior.

## Source references

- 1.8.9 net/minecraft/block/Block.java: constructor bounds 201; ID/state encoding 124-134; setShape 292-296; addCollisions/getCollisionShape 339-348; rayTrace 456-466; default no-op updateShape 596; default resetShape 654.
- 1.8.9 net/minecraft/block/BrewingStandBlock.java: default state 29-35; collision writes/reset 64-73; metadata/properties 145-170.
- 1.8.9 net/minecraft/block/Blocks.java: alias 348; numeric ID/key at Block.java:1075.
- 1.8.9 net/minecraft/world/World.java: block collision dispatch 900-921; ray-trace block dispatch 633-649.
- 1.8.9 net/minecraft/entity/Entity.java: ray-trace flags/dispatch 1029-1033.
- 1.8.9 net/minecraft/client/render/block/BlockModelRenderer.java: shape reset before render 284.
- 26.2 net/minecraft/references/BlockItemIds.java: key 679; net/minecraft/world/level/block/Blocks.java: registration 2567-2571.
- 26.2 net/minecraft/world/level/block/BrewingStandBlock.java: properties/default state 35-47, static shape 38, state-independent shape 61-62, property builder 102.
- 26.2 net/minecraft/world/level/block/Block.java: box/column coordinate conversion 160-184.
- 26.2 net/minecraft/world/level/block/state/BlockBehaviour.java: collision provider 327-328, default collision-enabled property 985, noCollision/noOcclusion 1094-1102.
- 26.2 net/minecraft/world/level/block/state/properties/BlockStateProperties.java: matching property names 23-25.
