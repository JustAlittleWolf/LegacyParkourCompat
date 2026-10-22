# Player collision axis resolution order

The closest-later representatives from 1.8.9 through 1.13.2 resolve movement in fixed Y, X, Z order, translating the box between axes:

- 1.8.9, 1.9.4, 1.10, 1.10.2, 1.11, 1.11.1, 1.11.2, and 1.12.2, Ornithe Feather: `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity.move`, respectively lines 439–459, 509–532, 514–537, 514–537, 523–557, 564–598, 564–598, and 567–601.
- 1.13.2, Ornithe Feather: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity.move(MoverType, double, double, double)`, lines 579–598.

The first representative of the next profile group, 1.14.1, Yarn, switches horizontal order in `decompiled_minecraft/1.14.1/yarn/net/minecraft/entity/Entity.java`, `Entity.method_17833(...)`, lines 688–718. The same order appears in 1.14.2 Yarn, lines 690–720; 1.14.3 Yarn, `Entity.method_20737(...)`, lines 701–731 and `Entity.calculateTangentialMotionVector(...)`, lines 734–765; 1.14 Ornithe Feather, `Entity.m_73670363(...)`, lines 689–719; and 1.14.4 Ornithe Feather, `Entity.m_79801352(...)`, lines 708–738 and `Entity.m_73670363(...)`, lines 741–770. Enclosing movement and step candidate gates were checked in each 1.14 source. Local-player `move` overrides delegate to `super.move` in the inspected local-player classes.

Difference: All inspected representatives through 1.13.2 clip nonzero Y, X, then Z, translating the box after each clipped component. Starting with 1.14, Y remains first and strict `Math.abs(x) < Math.abs(z)` selects the horizontal order: Z then X when true, otherwise X then Z. Equal magnitudes retain X-before-Z; exact-zero components are skipped. 1.14.3 routes multi-axis and axis-aligned movement through different collision helpers, but both keep the same order and guards. The 1.14 step-height candidate branch calls these same resolvers after requiring positive step height, grounded or downward-clipped state, and a horizontal collision.

Resolver scope: In 1.14.1 and 1.14.2 Yarn, Entity.method_17833 calls VoxelShapes.method_17945(Direction.Axis, BoundingBox, ViewableWorld, displacement, EntityContext, stream) for each selected axis (Entity.java lines 688–718 and 690–720, respectively). This finding treats that contextual collision operation as opaque and makes no claim about its downstream clipping implementation.

Movement meaning: Ordinary local-player travel passes its velocity to the inherited entity movement operation. When both horizontal components are nonzero and the player walks diagonally into a vanilla block corner, resolving the smaller component first can leave a different box for the second component and change clipped displacement. Equal-magnitude requests keep the old X-first order, so the new order alone does not predict a difference for every corner approach.

Applicability: The observed release boundary is 1.13.2 (fixed Y-X-Z) to 1.14 (magnitude-selected horizontal order); the closest-later V1_14 representative 1.14.1 confirms the newer behavior. Checked 1.14, 1.14.2, 1.14.3, and 1.14.4 sources confirm the newer behavior through the endpoint. Every supplied closest-later representative through 1.14.4 is covered: V1_8, V1_9, V1_10, V1_10_1, V1_11, V1_11_1, V1_11_2, V1_12, and V1_13 retain fixed order; V1_14 and V1_14_2 use the newer order. Other patch aliases within these groups were not inspected. No later profile is claimed by this finding.

The operation difference, its numeric tie/zero gates, step candidate gates, and representative applicability are source-verified. Full player input/tick coverage, collision-shape providers and registrations were not audited; no runtime trajectory was tested.

