# Cactus and sugar-cane provider closure (1.8.9 → 26.2)

## Scope

This report closes the listed blocks' source identities, age-state mappings, and local movement-collision and ray/outline provider geometry. It does not claim full DDA traversal, hit-order, player reachability, or trajectory parity. The exact 1.8.9 Feather Gen2 build.2 merged-v2 and 26.2 unobfuscated identities, ready/provenance/manifest hashes, and selected source hashes are in source-hashes.json; all 10 selected Java files were rehashed and matched their canonical source-manifest rows.

## Historical identities and state

In 1.8.9 Feather, Block.java:1012 registers ID 81 as cactus (CactusBlock), and Block.java:1014 registers ID 83 as reeds (SugarCaneBlock). Both classes define AGE from 0 through 15 and encode/decode it directly as metadata (CactusBlock.java:18,107–113; SugarCaneBlock.java:18,126–132). Their collision and bounds do not depend on AGE.

26.2 registers the cactus and sugar_cane block keys in Blocks.java:1986–1990,2005–2015, named by BlockItemIds.java:237,321. The explicit BlockStateData rows map old cactus AGE 0–15 to current minecraft:cactus AGE 0–15 (BlockStateData.java:3825–3840) and old minecraft:reeds AGE 0–15 to current minecraft:sugar_cane AGE 0–15 (:3842–3857). This is the declared legacy-state crosswalk; this report does not claim that every runtime state is produced through a particular data-fix call path.

## Movement-collision providers

| Block | 1.8.9 result | 26.2 result | Bounded comparison |
|---|---|---|---|
| Cactus (81) | CactusBlock.getCollisionShape returns the world-positioned box inset 1/16 on X/Z, from Y=0 through Y=15/16 (CactusBlock.java:52–55). | SHAPE_COLLISION = Block.column(14, 0, 15) is returned by getCollisionShape (CactusBlock.java:29–30,85–86; Block.java:177–185 defines centered 14/16 width and 0–15/16 height). | Same local movement-collision geometry for every AGE. |
| Reeds / sugar cane (83) | SugarCaneBlock.getCollisionShape returns null (SugarCaneBlock.java:91–93), so the legacy Block.addCollisions appends no box for it (Block.java:339–344). | Registration sets .noCollision() (Blocks.java:2005–2015); BlockBehaviour.Properties.noCollision clears hasCollision, and base getCollisionShape then returns Shapes.empty() (BlockBehaviour.java:327–328,1094–1097). | Both contribute no movement-collision box for every AGE. |

## Ray and outline bounds are separate

The legacy base Block constructor initializes mutable bounds to the unit cube (Block.java:201); its default updateShape is empty (:596–598), and default rayTrace calls that method before clipping against the instance bounds (:456–459). The distinct virtual getOutlineShape and getCollisionShape methods do not themselves define that inherited ray provider.

- Cactus: it does not replace the base mutable ray bounds, so the inherited 1.8.9 ray provider uses the full unit box. Its separate getOutlineShape is inset 1/16 in X/Z but reaches Y=1 (CactusBlock.java:58–60). In 26.2, its outline getShape is Block.column(14,0,16), a 14/16-wide centered shape from Y=0 to 1 (CactusBlock.java:29,90–91; Block.java:177–185). Thus the current outline bounds are narrower than the legacy inherited ray box; this is only a provider-geometry comparison, not a hit/reachability claim.
- Reeds / sugar cane: the legacy constructor sets mutable bounds to X/Z 1/8–7/8 and Y=0–1 (SugarCaneBlock.java:22–25); inherited updateShape does not change them, so those are the inherited ray-provider bounds. Current getShape returns Block.column(12,0,16), a centered 12/16-wide column from Y=0 to 1 (SugarCaneBlock.java:25,38–39; Block.java:177–185). The local dimensions match. Both versions still have no movement-collision box as described above.

Cactus damage is a separate entity-inside effect: old onEntityCollision calls takeDamage(CACTUS, 1) (CactusBlock.java:97–99), while 26.2 entityInside calls hurt(cactus, 1) (CactusBlock.java:126–136). This is not the collision-shape provider and is outside the movement-collision conclusion.

## Limits

- Closed here: IDs 81/83, AGE 0–15 mapping, current key aliases, and the individual collision/ray/outline provider geometry.
- Not closed here: actual camera query provider selection, DDA visitation and stop order, surrounding caller gates, or any resulting player trajectory.
- No product code, tests, builds, or runtime evidence were used.
