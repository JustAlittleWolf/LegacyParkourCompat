# F-WALL-THREE-ARM-CORNER-GAPS — three-arm wall shape leaves a reachable collision corner

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: WALL-THREE-ARM-CORNER-GAPS, WORLD-COLLISIONS, ENTITY-MOVE-AXES.
- Classification: changed behavior
- Confidence: source-confirmed; independent finding review pending
- Applicability: historical player behavior
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Artifact provenance

- A source artifact: `EA-FEATHER-R1-A` in `run.md`; revised-derived revision `feather-r1-2026-10-07`; source tree `ready/1.12.2/ornithe-feather`; source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. The original mapped JAR is unavailable, so original derived-bytecode identity is unverified.
- B source artifact: `EA-FEATHER-R1-B` in `run.md`; revised-derived revision `feather-r1-2026-10-07`; source tree `ready/1.13.2/ornithe-feather`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The original mapped JAR is unavailable, so original derived-bytecode identity is unverified.
- Source evidence uses the exact ready trees independently matched to those manifests. See `run.md` for immutable artifact and raw-input verification records.

## Paired source evidence

- A `WallBlock.java`, shape table / collision height entries lines 28-62, `getShapeIndex` lines 105-124, and `resolveVirtualProperties` lines 190-197; SHA-256 `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`.
- B `WallBlock.java`, constructor, `getCollisionShape`, `getShapeIndex`, and placement/update state formulas, lines 22-39, 54-57, 69-122; SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`.
- B `PaneBlock.java`, constructor / `makeShapes` / collision-shape selection, lines 32-84; SHA-256 `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`. B `VoxelShapes.java`, Boolean `or` construction, lines 96-104 and 140 onward; SHA-256 `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`. B `Block.java`, sixteenth-coordinate scaling, lines 136-138; SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`.
- A `FenceGateBlock.java#getCollisionShape/getFaceShape`, lines 81-87 and 175-181, SHA-256 `38ae9e6aa1057a8e9b65ff78414d0d6a9a70cf2645f1ea42141ff2815ad6a477`; B same members lines 67-73 and 159-163, SHA-256 `e251cbd1b5e06c42e856a1d537e4ba043f6760b8f54ea1388039428aa7c05164`.
- A `Entity.java#move`, lines 462-796, SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; B same member lines 473-782, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`. Player dimensions are `0.6F` wide in both versions; A/B constructor file hashes are recorded under `WALL-SINGLE-ARM-COLLISION`.
- A `World.java#getCollisions`, lines 964-1015, SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; B `WorldView.java#getBlockCollisions`, lines 112-163, SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`. Each cited source digest matches the exact-version manifest.

## Source-level difference

For NORTH+EAST+SOUTH connected and WEST open, both neighbor formulas produce those three connection bits and `UP=true` when the block above is air. A's horizontal direction bits select shape index 13. Its collision Box is X[4,16] × Z[0,16] × Y[0,24] in sixteenth-block coordinates (the collision table raises the outline's 16/16 height to 24/16).

B's `UP=true` shape is the OR of the center X[4,12] × Z[4,12], north arm X[5,11] × Z[0,11], south arm X[5,11] × Z[5,16], and east arm X[5,16] × Z[5,11], all with Y[0,24]. This leaves the northwest corner cell region X[4,5] × Z[0,4] empty, while A's single Box fills it. Coordinates above are inclusive shape boundaries; collision requires positive-volume overlap.

## Reachability and player consequence

Place open fence gates immediately NORTH, EAST, and SOUTH of the wall, with each facing chosen so its face toward the wall is `MIDDLE_POLE`; leave WEST and the block above air. Both versions' wall helper accepts `MIDDLE_POLE` from a fence gate, and the open gate collision getters return empty shapes. With three connections and air above, the A virtual-property formula and B placement/update formula both give `UP=true`.

Put a 0.6-wide player at y=0 with center x=z=-0.3, so its initial X and Z maxima are both 0. Request movement `(dx,dy,dz)=(0.3,0,0.24)`, with sneaking and on-ground false. The shared resolver processes Y, then X, then Z. During X, the player only touches the wall's z=0 boundary, so A does not clip X. After X, its box overlaps A's wall in X; the positive Z request meets the wall at z=0 and clips to zero. B's swept player query reaches at most x=0.3000001 and z=0.2400001 after the collector's 1e-7 expansion. Those maxima remain below the B north-arm boundary x=5/16 and center boundary z=4/16, so none of the B wall's component shapes overlaps the query. The open neighboring gates contribute no collision. B therefore retains the requested positive Z displacement for this source-derived query.

This example is a geometric source derivation through the paired collector and axis resolver, not a measured trajectory. It is limited to the stated three-arm wall state, open-gate neighbors, and player query; no frequency claim is made.

## Handoff

- Independent delta description: A's three-arm wall collision uses a filled rectangular Box; B composes center and arms with Boolean OR and leaves reachable outer corner regions empty.
- Related finding IDs: `F-WALL-SINGLE-ARM-GAPS`, `F-COLLISION-REPRESENTATION`.
- Applicability constraints: the documented NORTH+EAST+SOUTH state with `UP=true`, and a player query confined to the uncovered corner region.
- Independent blind finding review: pending. No implementation handoff until a reviewer accepts this exact source snapshot.
