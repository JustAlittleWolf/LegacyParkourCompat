# Farmland suffocation causes a local-player escape impulse when embedded

## Pair and exact change boundary

The old side is Minecraft 1.16.1; the new side is 1.16.2. In 1.16.1, `Blocks.FARMLAND` does not set an `isSuffocating` provider; 1.16.2 adds `.isSuffocating(Blocks::always)` (`world/level/block/Blocks.java`, old 1.16.1 registration and new 1.16.2 registration). `FarmBlock` retains the same 15/16-high collision box. The new `LocalPlayer` response is also introduced in 1.16.2: four `moveTowardsClosestSpace` calls replace four `checkInBlock` calls in the `!noPhysics` portion of `aiStep`; this same helper is present through 1.16.5. The 1.16.1 consumer still uses the old `checkInBlock` response and the inherited predicate remains false for this partial collision shape. The changed provider registration and replacement `moveTowardsClosestSpace` response both first appear in 1.16.2 and remain in the 1.16.3–1.16.5 sources checked for persistence.

In 1.15.2, the inherited `Block.isSuffocating` requires a motion-blocking material and a full collision shape. In 1.16.1 the equivalent inherited default is the `BlockBehaviour.Properties.isSuffocating` predicate, dispatched through `BlockBehaviour.BlockStateBase.isSuffocating`; it uses the same motion-blocking and full-collision-shape conditions. Farmland has a 15/16-high collision shape, so both defaults return false for the same state and block position. 1.16.2's registered `Blocks::always` predicate returns true, without changing that collision shape.

## Movement consumer and concrete input

The local-player tick calls its escape probes before the later `super.aiStep` travel work. Old `LocalPlayer.blocked` checks every vertical block cell overlapped by the player bounding box and calls `Player.freeAt`, which delegates to `BlockState.isSuffocating`. New `LocalPlayer.suffocatesAt` asks `level.noBlockCollision` over the player's vertical span within a unit X/Z cell, filtering candidate shapes by `BlockState.isSuffocating`; `CollisionGetter.noBlockCollision` returns whether every selected collision shape is empty. New `moveTowardsClosestSpace` tests WEST, EAST, NORTH, SOUTH and writes a horizontal velocity component of `±0.1` when one of those sampled neighboring cells is clear.

Use farmland at `(0,64,0)`, air in every other block cell intersecting the player box and in every cell intersecting the four horizontal candidate columns, and a non-noPhysics local player with pre-tick bounding box `[0.2,64.8,0.2]–[0.8,66.6,0.8]`, position/feet Y `64.8`, and `deltaMovement=(0,0,0)`. The player's box overlaps the farmland collision shape by `0.1375` vertically and is inside its X/Z footprint. For each new probe, the X/Z samples are `(.29,.71)`, `(.29,.29)`, `(.71,.29)`, `(.71,.71)` and the queried block position is `(0,64,0)`. The farmland shape intersects each current-cell query and its new suffocation predicate accepts it. The four nearest clear directions in order are WEST, WEST, EAST, EAST; the strict comparison retains the first direction on each tie. These sequential calls leave the final horizontal velocity at `deltaMovement.x=+0.1`, with Y and Z preserved.

Under the same old state and bounding box, the old inherited predicate returns false for farmland; `blocked` finds no suffocating cell among the player's overlapped vertical cells, so all four probes leave the initial velocity unchanged. For the stated zero X input, the paired pre-travel velocity write is therefore `0` versus `+0.1` on X.

## Claim limit

This is a conditional source-level movement response for the exact sampled state and embedded pre-tick player box. It does not claim ordinary walking creates that box, does not claim which correction or other producer creates it, and does not predict a later position or trajectory. Soul Sand and Grass Path have matching suffocation-registration changes but are not independently claimed by this Farmland witness.

## Exact sources and fingerprints

- 1.16.1 `Blocks.java`: `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9`
- 1.16.2 `Blocks.java`: `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`
- 1.16.1 `LocalPlayer.java`: `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`
- 1.16.2 `LocalPlayer.java`: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
- 1.15.2 `Block.java` inherited predicate and `FarmBlock.java` shape are pinned in the corresponding Mojmap source manifest; 1.16.2 `FarmBlock.java`, `CollisionGetter.java`, `Player.java` and exact referenced `LocalPlayer` methods are likewise manifest-pinned.
