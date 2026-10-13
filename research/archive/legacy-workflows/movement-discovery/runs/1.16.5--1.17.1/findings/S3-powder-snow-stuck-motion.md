# Powder snow writes the entity stuck-movement multiplier

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S3-02-POWDER-SNOW-STUCK-MOTION`, `S5-03-CONTACT-CALLBACK-INVENTORY`
- Classification: added block-contact movement effect
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A has no `PowderSnowBlock.java` and no Powder Snow registration in `Blocks.java`; the A `Blocks.java` source hash is recorded in the run manifest. A does have the generic `Entity.makeStuckInBlock(BlockState, Vec3)` at `world/entity/Entity.java`, lines 1991–1994, SHA-256 `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE2666`.
- B `world/level/block/PowderSnowBlock.java`, `entityInside(...)`, lines 54–57, SHA-256 `4069B3EF70E2D0CE146BBFF79BE54B2BE99B7C7B32818C1EB45F11FD66F99AA3`: for a `LivingEntity`, the method calls `makeStuckInBlock(blockState, new Vec3(0.9F, 1.5, 0.9))` when the entity's feet block is this powder-snow block.
- B `world/entity/Entity.java`, `makeStuckInBlock(...)`, lines 2150–2153, SHA-256 `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE`: it resets `fallDistance` to `0.0F` and stores the supplied vector in `stuckSpeedMultiplier`.
- A `Entity.move(...)`, lines 498–501, and B `Entity.move(...)`, lines 547–550, use the same consumer: when the stored multiplier's squared length exceeds `1.0E-7`, multiply the requested movement vector by it, reset the multiplier to `Vec3.ZERO`, and set delta movement to zero. Both versions' `Entity.java` hashes are recorded in the run manifest.
- A and B `Entity.checkInsideBlocks()` call the block state's `entityInside` callback for blocks intersecting the entity bounding box (A lines 818 onward; B lines 891 onward). The method is reached from each version's entity tick path (A line 568; B line 655).

## Source-level difference

B adds a Powder Snow callback that writes the existing stuck multiplier. For a player, the callback condition is met when its feet block is Powder Snow. On the next movement consumption, the requested X/Z displacement is multiplied by `0.9F` and Y by `1.5`; the same branch clears the stored multiplier and delta movement. The generic multiplier operation itself is present in A, but A has no Powder Snow block capable of supplying this multiplier.

## Reachability and dependencies

The player is a `LivingEntity`, so it takes the callback's feet-block condition. Entity tick dispatch visits `entityInside` for intersected blocks; the shared `Entity.move` consumer applies the stored value to movement. The block is registered only in B. This callback effect is separate from the Powder Snow climb-assist predicate documented in [S3-powder-snow-climb.md](S3-powder-snow-climb.md).

## Consequence and uncertainty

The source proves the callback arguments, fall-distance reset, multiplier storage, and movement-consumption order. It does not establish a full trajectory. Powder Snow did not exist in A, so this is modern-only and does not imply historical behavior for A-era maps.

## Handoff

Keep this contact-triggered movement effect separate from climb assistance and from the new collision shape. The remaining S5-03 callback bodies are still open.
