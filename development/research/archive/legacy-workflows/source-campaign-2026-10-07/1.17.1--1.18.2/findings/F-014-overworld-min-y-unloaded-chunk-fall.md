# F-014: default Overworld minY changes unloaded-chunk fall velocity

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-PLAYER-UNLOADED-CHUNK, INV-TICK, INV-STATE, INV-EXTERNAL
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: client-side ordinary air travel in a default Overworld client level, without Levitation, at Y in (-64, 0], while the player's XZ chunk is absent
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

- A `DimensionType.DEFAULT_OVERWORLD` supplies minY=0, height=256, logicalHeight=256; B supplies minY=-64, height=384, logicalHeight=384. `DimensionType.java` SHA-256 A `2dc118f16b671d3b1b9736c10e960e199790f7930cfa18983f379beb8d00f27b`, B `0c1d2268dde59ef4cf7378fe3ba083dac669a6705f399a23c8138988c29c7681`.
- A `LevelReader#getMinBuildHeight` returns `dimensionType().minY()` and `hasChunkAt(BlockPos)` delegates to `hasChunkAt(x,z)`; B has the same behavior. `LevelReader.java` SHA-256 A `4d041cdbc702d532c5b9da800c34bd1c23f8d3b554ecbdd89decbb765a6b451a`, B `1a812cc6a9c7fae74ae233fdc19bd9653abbfc768a00d79621885e5fca56f25d`.
- In the ordinary-air branch of `LivingEntity#travel`, after the Levitation branch and before gravity, both versions check `level.isClientSide && !level.hasChunkAt(blockPos)`. A sets the vertical local component to `0.0` when `getY() <= getMinBuildHeight()`, otherwise `-0.1`; B performs the same test against its level minY. For -64 < Y <= 0 this produces A `0.0`, B `-0.1`. `LivingEntity.java` SHA-256 A `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`, B `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782`.
- The subsequent common vertical friction write multiplies by `0.98F` when friction is not discarded, yielding A `0.0` and B `-0.098`; when friction is discarded, it writes the branch value unchanged, yielding A `0.0` and B `-0.1`. `Level.java` SHA-256 A `730d3cc1f1c1c7054c102bb32d635d8f5a3657fff8073df863f0848ed1f9a0f8`, B `397a2fd9ed6745be2637ee9c8368a0425cd269d1b7a9a4b269fe2d1fbe786099`.

## Reachability and limits

The LocalPlayer ordinary tick reaches inherited `LivingEntity#aiStep` and `travel` when alive, not passenger-controlled, not in water/lava, not fall-flying or ability-flying, and with no Levitation effect. A player can fall below Y=0; `hasChunkAt(BlockPos)` is an XZ chunk-presence test, so an absent XZ chunk satisfies the client fallback independent of the block position's Y. Under these concrete states the source writes different player vertical velocity and the following move consumes it.

This candidate relies on the built-in default Overworld dimension type on the respective client versions. A server-synchronized dimension type with an equal minY on both sides removes this difference for those inputs. The finding concerns direct player air-travel response to dimension data; it does not claim generated terrain, a below-zero A-era block, void damage/death behavior, or server physics.

## Handoff

Source-confirmed player movement delta for the stated client-level and missing-XZ-chunk preconditions. No trajectory was simulated. Independent review pending.
