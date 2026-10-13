# F11: grounded slime-block damping uses a broader step callback gate in 1.15.2

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 508–518.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 499–501.

Difference: For a grounded, non-shift player on a slime block, 1.15.2 calls `stepOn` even when the player has flying ability enabled or is a passenger. 1.14.4 suppresses the callback in either state: flying makes `Player.makeStepSound()` false, and passengers fail the explicit passenger condition. Since `SlimeBlock.stepOn` writes horizontal delta movement, 1.15.2 applies the horizontal multiplier `0.4 + abs(vY) * 0.2` in these states while 1.14.4 does not. For ordinary grounded non-flying, non-passenger players, the callback gates and slime damping conditions correspond.

Movement meaning: Player movement -> `Entity.move` collision resolution -> selected support block -> `Block.stepOn` -> `SlimeBlock.stepOn` -> horizontal delta-movement write.

Original evidence: [archived report](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/findings/F11-slime-step-callback-gate.md).
