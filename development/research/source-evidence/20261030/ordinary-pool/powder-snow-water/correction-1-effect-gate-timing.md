# Correction 1: PowderSnow effect registration and gate timing

This supplement corrects `.native-source/20261030/ordinary-pool/powder-snow-water/closure.md`, SHA-256 `c7b9be695a36824c5ffee9653785716d7e7cda372952494b8eaf5b2201bbdfb6`. Its old callback description remains unchanged; the exact old server-side ordering is:

- If the level is a `ServerLevel`, test entity-on-fire, mob-griefing-or-Player, and `mayInteract` to decide whether to destroy the block without drops.
- After that conditional attempt, still inside the `ServerLevel` branch, clear the entity's shared on-fire flag unconditionally, even when the block was not destroyed.

In 26.2, `PowderSnowBlock.entityInside` calls `effectApplier.runBefore(EXTINGUISH, consumer)` unconditionally. The consumer later checks `ServerLevel`, on-fire, mob-griefing-or-Player, and `mayInteract` when collector effects execute; those gates do not guard registration. The method then registers `FREEZE` and `EXTINGUISH`. The after-sweep collector runs the `EXTINGUISH` before-consumer, then applies `FREEZE`, then `EXTINGUISH`; destruction and fire/freeze state writes are therefore deferred. Exact 26.2 source: `PowderSnowBlock.java:87–102`, source SHA-256 `2b693be4a4d3cf2b1895096431c16d7ba3ae51b9cf4320773d384bdd146ba417`. Old 1.21.2/1.21.3 source: `PowderSnowBlock.java:77–88`, source SHA-256 `a018411167f5d8b60b131c3f6387dcd256b736ef64126bdcf33fd4457151654`.

This is a wording/phase correction only. It preserves the accepted conclusion that the historical callback performs its relevant entity/world writes inline and the modern typed effects do not.
