# Early west, north and south AutoJump border fixtures

Ornithe Feather Gen2 build.2 `LocalClientPlayerEntity.move` calls AutoJump from the actual post-move displacement in 1.10 (813–816) and 1.11.2 (825–828). The checked release aliases share the relevant caller and probe. In each accepted fixture a grounded standing player moves forward from rest with Speed II and no Jump Boost on a pair of loaded Stone supports. The player remains strictly inside the eroded border, the loaded clearance cells are air, and no entity contributes a collision.

The `World.getCollisions` border predicate temporarily supplies Stone to the first exterior cell. A negative-normal face reflects cell coordinates as `-raw-1`; its first exterior raw cell is the border coordinate minus one. The standing footprint and both actual raised rays stay in the same tangential row, reaching the first exterior cell with positive margin. Exact 1.10 through 1.11 prune both vertical caps on the side column. Exact 1.11.1 and 1.11.2 prune only the upper cap; the lower cube touches the feet plane and contributes no collision. The first ray hit is therefore the next cube at feet height. The candidate cap query directly reads actual air, giving rise one and arming a one-tick timer.

| Reviewed artifact | SHA-256 |
| --- | --- |
| Alias source-check.md | a7cce5fee3afb2cb1c73096891dc61d8a42c40434e43ca7f19b116d378909e0d |
| Alias audit.md | 991516352e5f07e61d8e12616512b97acc00b6f19cc3325999a6b86060fb798c |
| Ground source-closure.md | abddc3182837fbbf72cb0af6ec888faf8991246707f9b65c8213750fa8494b21 |
| Ground audit.md | 6cfe77d66cc8413ede30d68360a5de61ef1af39877c5980fbffdf186a9eef39b |

The product uses the existing first-forward travel observation and one candidate scanner. West, north and south allow only the small cross-axis residue of an observed cardinal move and require both real rays to stay inside the admitted row. The source verdict concerns the fixed fixtures. Runtime parity remains unverified. [East fixture](early-east-border-acceptance.md)
