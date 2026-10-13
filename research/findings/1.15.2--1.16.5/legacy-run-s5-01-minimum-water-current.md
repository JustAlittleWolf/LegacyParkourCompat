# S5-01: Weak water currents receive a minimum horizontal push

- **1.15.2**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s5-01-minimum-water-current.md); lines 2600–2666.
- **1.16.5**, mojmap: [source locations in preserved report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s5-01-minimum-water-current.md); lines 2641–2697.

## Difference

The shared scan, average, player normalization exception, and water strength remain. 1.16.5 adds the minimum-vector-length branch conditioned on low horizontal velocity; 1.15.2 has no corresponding adjustment.

## Movement meaning

Entity base tick invokes the water current update. 1.15.2 non-flying player in flowing water with nonzero averaged flow, both horizontal velocity components under 0.003, and a post-strength vector under the minimum reaches 1.16.5's boost.

Players do not normalize the average flow in either version, and ability flight suppresses fluid push in both versions.

1.16.5 may apply a larger horizontal velocity increment than 1.15.2 in the specified weak-current state.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.15.2--1.16.5/findings/s5-01-minimum-water-current.md).
