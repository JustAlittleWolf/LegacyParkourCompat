# F001: Input scaling and underwater sprint-forward cutoff

- **1.13.2**, ornithe-feather: `KeyboardInput.java`; lines 13-50.
- **1.14.4**, ornithe-feather: `KeyboardInput.java`; lines 13-26.

## Difference

1.13.2 applies the 0.3 input scale whenever sneaking. 1.14.4 calls `tick(bl4, spectator)` where `bl4 = m_63723874() || m_99544176()` and scales only when not spectator and actual sneaking or `bl4` is true. `m_63723874()` is true only when not flying/swimming and the sneaking pose fits, then returns actual sneak input or that standing pose does not fit; `m_99544176()` is true for swimming pose while out of water.

Separately, 1.14.4 uses `movementForward > 1.0E-5F` for submerged sprint eligibility, while 1.13.2 uses `movementForward >= 0.8F`; 1.14.4 retains `>=0.8F` outside water.

## Movement meaning

Keyboard input is sampled in local player `mobTick` and the resulting movementForward feeds local sprint initiation/timer checks. 1.14.4 supplies pose-related `bl` and spectator state to the input method.

Source proves the changed scale and submerged threshold.

The threshold may permit sprint initiation with a smaller positive submerged forward input than 1.13.2; resulting speed/position is not observed here. `bl`'s exact upstream pose state and full gates need closure in INV-TICK/INV-STATE.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F001-input-scaling-and-underwater-sprint-gate.md).
