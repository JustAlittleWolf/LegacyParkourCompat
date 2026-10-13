# STATE-03: sneaking can resize the player collision box

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Source snapshot revision: `feather-r1-2026-10-07`; A immutable mapped JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B immutable mapped JAR SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. The original derived JARs are unavailable; equivalence is unproven.
- Mechanic / coverage slice IDs: player pose/dimensions; `STATE-03`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player is sneaking, not sleeping/fall-flying, and B's 1.65-high candidate box has no block collisions at pose-update time
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity#tick()V` has no pose-size update; its only `setSize` writes are `resetPos()` line 417, `die()` line 515, sleep line 1108, and wake line 1163. None is sneaking-conditioned. `getEyeHeight()F`, lines 1640-1650, separately subtracts `0.08F` for sneaking. SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; end-of-`tick()V` pose call line 245; `PlayerEntity#updatePlayerPose()V`, lines 285-308, selects `0.6F` width and `1.65F` height for ordinary sneaking, creates the candidate box at the current minima, and calls `setSize` only when `world.getCollisions(box)` is false. `getEyeHeight()F`, lines 1740-1750, independently reduces eye height by `0.08F` for sneaking or height `1.65F`. SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.
- Fit-query dependency: B `World#getCollisions(Box)`, lines 1018-1045, enumerates candidate box cells and returns collision when a block state's `addCollisions` contributes any intersecting box; `World.java` SHA-256 `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`. Pane boxes are one such provider and are bounded in `COLL-02` snapshot `SNAP-COLL-02-PANE-02` (see run log); this finding makes the fit predicate explicit and claims no fit result for an unspecified world.
- Resize consumer: A/B `Entity#setSize(float,float)`, A line 200 and B line 259, rebuilds the box from its current minima using the new width/height; the width remains `0.6F`, so the width-growth movement branch is not selected. `Entity.java` SHA-256 A/B `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` / `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`.

## Source-level difference

A has no sneaking-conditioned player dimension write: its source-wide `PlayerEntity` size writes handle reset, death, sleep, and wake. The separate sneak eye-height reduction does not resize the box. B calls `updatePlayerPose()` at the end of each player tick; after higher-priority fall-flying and sleeping cases, ordinary sneaking selects width `0.6F` and height `1.65F`. It builds the candidate box at the current minima and calls `setSize` only when the collision query finds no block collision. The query's provider dependency is explicit: changed pane boxes can affect the guard near panes, so the finding does not claim the guard result for an unspecified world. B also uses the same `0.08F` eye-height reduction for ordinary sneaking. This is a conditional collision-box height change, not a separate eye-height delta.

## Reachability and dependencies

Sneak state is read by the tick-end player pose writer. The B fit query iterates the candidate box's block cells and invokes the block-state collision providers; the corrected `COLL-02` snapshot captures the changed PaneBlock provider and its normal player movement reachability. The fit guard remains a stated condition, not an assumed result: the claim applies only when B's candidate box is collision-free. A has no corresponding sneaking size writer. A successful B resize rebuilds the box at its existing minima with `0.6F` width and `1.65F` height; width is unchanged, and the separate width-growth move is not entered. The support-edge probe in `Entity.move()` is a separate predicate and remains in `COLL-01`. Fall-flight and sleeping dimensions are excluded.

## Consequence and uncertainty

The paired source proves that, when B's candidate box has no block collisions, the tick-end sneaking pose update changes height from `1.8F` to `1.65F`, while A has no sneaking-conditioned dimension write and retains `1.8F`. The collision-fit result depends on the queried world, including the pane geometry dependency cited above. No particular fit result or future displacement is claimed. Exact first changed release is unknown inside the endpoint interval.

## Handoff

Independent delta: B adds a tick-end collision-fit resize to a `1.65F` tall player box when sneaking. Runtime validation is deferred.
