# Big Dripleaf contact changes its collision shape

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S5-03-CONTACT-CALLBACK-INVENTORY`, `S4-01-ENTITY-COLLISION-BASELINE`
- Classification: modern-only contact-driven collision-shape change
- Confidence: source-confirmed
- Applicability: B-only Big Dripleaf block; not applicable to A-era maps
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- B `world/level/block/BigDripleafBlock.java`, SHA-256 `1E1315B0A53EB85A3365335131E7819C1B667A55E14343D839C5211C671FD59F`, declares `entityInside(...)`. On the server, when `TILT` is `NONE`, the entity is grounded, its Y coordinate is above the leaf threshold, and the block has no neighbor signal, the callback sets `TILT` to `UNSTABLE` and schedules the next transition.
- The same source defines state-dependent collision shapes: `NONE` and `UNSTABLE` use a leaf box from Y 11/16 to 15/16; `PARTIAL` uses Y 11/16 to 13/16; `FULL` uses an empty leaf shape. `getCollisionShape(...)` selects that leaf shape from `TILT`. The state machine advances `UNSTABLE` to `PARTIAL`, then `FULL`, then resets; a neighbor signal also resets it.
- B `world/level/block/Blocks.java` registers `BIG_DRIPLEAF`; its hash is recorded in the run's cited-source table. No `BigDripleafBlock.java` exists in A's canonical ready source tree.

## Source-level difference

A grounded player can trigger the B-only contact callback on an unpowered Big Dripleaf. The scheduled tilt progression lowers and then removes the leaf collision shape, which changes the surface that supports the player. The generic collision consumer is shared across versions; the provider and block are new in B.

## Scope and consequence

This is a confirmed movement-affecting mechanic in B, but the block is absent from A. Under the one-way compatibility scope, it requires no historical emulation for A-era maps. It is retained to document the B-only callback owner's movement consequence and does not close the broader collision or callback inventories.

## Handoff

Keep the remaining shared callback bodies and provider/resource inventory open. Do not add this modern-only block to A behavior.
