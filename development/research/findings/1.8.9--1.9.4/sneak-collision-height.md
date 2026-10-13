# sneaking can resize the player collision box

1.8.9, Ornithe Feather gen2 build 2: `1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `tick()` lines 183-290; complete class size-writer inventory contains no sneaking-conditioned resize.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `protected void updatePlayerPose() {`, lines 285-309.

Difference: 1.8.9 has no sneaking-conditioned player dimension write: its source-wide `PlayerEntity` size writes handle reset, death, sleep, and wake.

The separate sneak eye-height reduction does not resize the box. 1.9.4 calls `updatePlayerPose()` at the end of each player tick; after higher-priority fall-flying and sleeping cases, ordinary sneaking selects width `0.6F` and height `1.65F`.

It builds the candidate box at the current minima and calls `setSize` only when the collision query finds no block collision.

This is a conditional collision-box height change, not a separate eye-height delta.

Movement meaning: Sneak state is read by the tick-end player pose writer.

The fit guard remains a stated condition, not an assumed result: the claim applies only when 1.9.4's candidate box is collision-free. 1.8.9 has no corresponding sneaking size writer. A successful 1.9.4 resize rebuilds the box at its existing minima with `0.6F` width and `1.65F` height; width is unchanged, and the separate width-growth move is not entered.

The support-edge probe in `Entity.move()` is a separate predicate and remains in `COLL-01`.

Fall-flight and sleeping dimensions are excluded.

The paired source proves that, when 1.9.4's candidate box has no block collisions, the tick-end sneaking pose update changes height from `1.8F` to `1.65F`, while 1.8.9 has no sneaking-conditioned dimension write and retains `1.8F`.

The collision-fit result depends on the queried world, including the pane geometry dependency cited above.

No particular fit result or future displacement is claimed.
