# Shapes.create candidate boundary check: ordinary collision-provider examples

Status: bounded negative reachability check; not a compatibility finding.

Question: Can an existing block collision provider concretely intersect the synthetic swept AABB whose max coordinates are `0.87500005`, while the 1.17.1 snapped value is `0.875`, such that the Shapes.create/findBits representation shift changes player collision?

Sources (read-only Mojmap):

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap\net\minecraft\world\level\block\ScaffoldingBlock.java` SHA-256 `3910D1C554A6937D61FBBF3027FC60DDA429E5AE46311F965B65E218BF1D069C`.
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap\net\minecraft\world\level\block\ScaffoldingBlock.java` SHA-256 `F99384C44EC3993CDD4361A0F62C56C11958D2EEE76787D08CF8ECBC1B5666D2`.
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap\net\minecraft\world\level\block\Blocks.java` SHA-256 `3B39D5CC4CD22F146ED3195AA30CBB9FDFCA49F63783FABF9924A6CE7795FA12`.
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap\net\minecraft\world\level\block\Blocks.java` SHA-256 `87D72A113A3F8937A6A585EF917A4FD29CC5B335C00A858F6800E38F3C1BF7A8`.

Scaffolding is not a witness for an entity box below its top. Both versions define the stable top plate with `Block.box(0,14,0,16,16,16)` at line 162 (1.16.5) / 164 (1.17.1), but `getCollisionShape` only returns `STABLE_SHAPE` at line 120/122 if `collisionContext.isAbove(Shapes.block(), blockPos, true)` and not descending. The other nonempty branch (lines 123-125 / 125-127) requires nonzero DISTANCE, BOTTOM, and `isAbove(BELOW_BLOCK,...)`, then returns only `UNSTABLE_SHAPE_BOTTOM`, defined at line 28/29 as the 0..2/16 bottom slab. Thus the apparent 14/16 endpoint cannot be treated as a reachable collision surface for the below-top synthetic query without a different proven context.

Buttons are also not a witness: both `Blocks.java` registrations mark STONE_BUTTON and OAK_BUTTON with `Properties...noCollission()` (1.16.5 lines 686-687 and 1175-1176; 1.17.1 lines 724-725 and 1247-1248). Their geometric AABB endpoints do not establish membership in the entity block-collision stream.

This check rejects those two tempting candidates only. It does not prove no other old block has an active collision-shape boundary at the synthetic endpoint, and it does not close the broader Shapes.create delta. The prior shape audit still stands as a conditional query-shape representation candidate; no concrete block collision outcome or resolved player displacement is established here.
