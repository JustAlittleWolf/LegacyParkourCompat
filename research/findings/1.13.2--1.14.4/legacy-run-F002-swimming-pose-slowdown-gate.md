# F002: Swimming state suppresses the pose-based sneak slowdown gate

1.14, ornithe-feather: `1.14/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `m_63723874`, lines 597.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `m_63723874`, lines 596–598.

Difference: At the exact 1.14 base, `m_63723874()` checks `!abilities.flying && canPose(SNEAKING)` before testing the sneak key or whether standing fits. In 1.14.4 it checks `!abilities.flying && !isSwimming() && canPose(SNEAKING)` before the same expression. The surrounding `m_99544176()` alternative remains true for the swimming pose outside water, so this added guard specifically suppresses the first gate while the player is swimming; it does not remove the outside-water swimming-pose path.

Movement meaning: The local player's `mobTick()` evaluates this predicate and passes it to `KeyboardInput.tick(...)`.

Original evidence: [archived report](../../archive/legacy-workflows/movement-discovery/runs/1.13.2--1.14.4/findings/F002-swimming-pose-slowdown-gate.md).
