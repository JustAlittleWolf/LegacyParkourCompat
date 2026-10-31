# Soul Sand and Grass Path suffocation trigger a local-player escape impulse

## Pair and exact boundary

The old endpoint is Minecraft 1.16.1 and the new endpoint is 1.16.2. The 1.16.1 `Blocks.SOUL_SAND` and `Blocks.GRASS_PATH` registrations have no `isSuffocating` override. In 1.16.2 both register `.isSuffocating(Blocks::always)`. Source registrations in 1.16.3, .4, and .5 retain the same provider. The old inherited default requires a motion-blocking material and a full collision shape; both block shapes are partial height, so the old result is false for the same sampled state.

The LocalPlayer consumer switch appears at the same 1.16.2 boundary: the four `!noPhysics` `checkInBlock` probes in `aiStep` are replaced by four `moveTowardsClosestSpace` probes. In the old call chain, `blocked` checks overlapping Y cells and `Player.freeAt` reads `BlockState.isSuffocating`. In the new chain, `suffocatesAt` filters block-collision shapes by that predicate, and `moveTowardsClosestSpace` writes a horizontal `±0.1` delta when a clear neighboring column is found.

## Same-input local-player witness

For either block independently, place its unchanged state at `(0,64,0)`; assume air in every other block cell intersecting the player box and in every cell intersecting the four horizontal candidate columns. Use a non-noPhysics local player with pre-tick bounding box `[0.2,64.8,0.2]–[0.8,66.6,0.8]`, position/feet Y `64.8`, and `deltaMovement=(0,0,0)`.

Both blocks span the full X/Z footprint. Soul Sand's collision shape reaches Y `64.875` (14/16 high), so the query overlaps it by `0.075`; Grass Path uses the FarmBlock shape, reaching `64.9375` (15/16 high), overlapping by `0.1375`. This remains positive after the new query's `1.0E-7` deflation. For each block, the four source-ordered probe fractions are `(.29,.71)`, `(.29,.29)`, `(.71,.29)`, `(.71,.71)`, all sampling `(0,64,0)`. The current-cell query intersects the partial shape and the new predicate accepts the state. The neighboring candidate columns are clear. The nearest clear directions are WEST, WEST, EAST, EAST because the strict distance comparison retains the first equal-distance direction. The sequential response leaves final `deltaMovement.x=+0.1`, with Y and Z preserved.

In 1.16.1, the same state returns false from the inherited suffocation predicate because neither partial collision shape is a full block. The old `blocked` probes therefore do not enter the response and leave the initial horizontal delta at zero. The same sampled input has a pre-travel X velocity difference of `0` versus `+0.1` at 1.16.2.

## Scope

This claim is conditional on the stated pre-tick player box, block state and surrounding air. It does not state how a client obtains the embedded box, assert ordinary-walking or server-producer reachability, or predict a later trajectory. Farmland is covered by the separate independently accepted finding in this directory; this draft's additional source claims are Soul Sand and Grass Path.

## Exact source fingerprints

- 1.16.1 `Blocks.java`: `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9`
- 1.16.2 `Blocks.java`: `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`
- 1.16.1 `LocalPlayer.java`: `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`
- 1.16.2 `LocalPlayer.java`: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
- 1.15.2 `Blocks.java`: `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`
- 1.15.2 `SoulsandBlock.java`: `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`
- 1.15.2 `GrassPathBlock.java`: `4e2477699c7d1d404515774146069fbd3c6d79e2c499c400ce03c4abce58918f`
- 1.16.2 `SoulSandBlock.java`: `da92572788c9416b511c003e50688d802e8248ccdb3e2d5eb155aa482549b095`
- 1.16.2 `GrassPathBlock.java`: `c94a5adc753ec4a4f9459c7cefcb8d9ff15fab2602ddaf88e48a5efa23fa72e8`
- 1.16.2 `CollisionGetter.java`: `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`
- 1.16.1 `BlockBehaviour.java`: `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f`
- 1.16.2 `BlockBehaviour.java`: `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b`
- 1.16.2 `BlockState.java`: `edd2d9dba0c10d035774f8b2dd7dcf00d112d7a2d67734fba1851a2d2f91bd36`
- 1.16.2 `Player.java`: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`
