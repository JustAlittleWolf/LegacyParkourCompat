# F-HONEY-FACTORS: Honey Block adds horizontal speed and jump factors

- **1.14.4**, mojmap: `net/minecraft/world/level/block/Blocks.java`; lines 1-2035.
- **1.15.2**, mojmap: `net/minecraft/world/level/block/Blocks.java`; lines 2118-2123.

## Difference

The 1.15.2 Honey Block has horizontal speed factor 0.4 and jump factor 0.5.

The speed helper applies horizontal scaling after collision/contact in `Entity.move` unless a player is ability-flying or fall-flying.

The jump helper scales base jump power before Jump Boost is added.

The same block factor also suppresses local auto-jump when below 1.0. 1.14.4 has neither factor property nor the associated consumers.

The Soul Sand factor change is separately recorded in F-SOUL-SAND-SPEED.

## Movement meaning

For a player grounded on Honey Block, the support lookup can resolve the block and apply the horizontal factor; when jumping, `LivingEntity.jumpFromGround` consumes the jump factor.

The local-player auto-jump gate reads the same jump factor.

These are 1.15.2-only block interactions and do not imply a 1.14.4 mechanic.

The source proves factors and consumers; it does not establish measured speed, jump height or trajectory.

Other non-default factor registrations remain part of the open whole-pair inventory, although 1.15.2's current source search found Honey as the sole non-default jump factor and Honey plus Soul Sand as speed-factor registrations.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-HONEY-FACTORS.md).
