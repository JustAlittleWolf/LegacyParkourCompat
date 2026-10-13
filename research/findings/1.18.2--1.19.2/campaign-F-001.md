# F-001: Swift Sneak raises local crouch/crawl input scale

- **1.18.2**, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 601-604.
- **1.19.2**, mojmap: `1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java`; lines 690-693.

## Difference

1.18.2 has no Swift Sneak registry entry, and `KeyboardInput.tick(boolean)` always uses the fixed `0.3F` for its slow-input branch. 1.19.2 adds a legs-slot Swift Sneak enchantment and computes a clamped factor from its level before the same input branch.

Levels 1, 2 and 3 yield `0.45F`, `0.60F` and `0.75F`.

At level zero, 1.19.2 remains `0.3F`.

The slow-input predicate is `isCrouching() || isVisuallyCrawling()` in both releases.

The changed expression runs before local movement consumes the sampled impulses.

## Movement meaning

Local client tick -> `LocalPlayer.aiStep()` updates crouching state -> `isMovingSlowly()` -> `EnchantmentHelper.getSneakingSpeedBonus(this)` -> `KeyboardInput.tick()` writes `leftImpulse` and `forwardImpulse` -> the existing local movement consumers.

Swift Sneak's level is read from the equipped legs slot.

The vanilla `Entity.moveRelative()` / `getInputVector()` conversion has the same threshold, normalization and yaw operation order on both sides (1.18.2 `Entity.java` lines 1151-1166; 1.19.2 lines 1170-1185; source hashes in the run ledger).

This pair comparison does not establish that the mechanic first appeared in release 1.19.2.

Source-proven: while slow keyboard input is sampled and Swift Sneak is equipped, 1.19.2 writes larger lateral and forward input impulses than 1.18.2. 1.19.2's bundled ancient-city loot table contains a Swift Sneak enchanted-book entry; the 1.18.2 client jar has no matching table entry.

Predicted consequence: subsequent player movement can receive stronger horizontal input in those conditions.

Artifact revision `feather-r1-2026-10-07` does not apply: it revises derived Feather artifacts for 1.8.9-1.13.2; this finding uses the reverified Mojmap 1.19.2 source and artifacts.

The original Feather derived jars remain unavailable and their identity is unproven, but they are not a source dependency here.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.18.2--1.19.2/findings/F-001.md).
