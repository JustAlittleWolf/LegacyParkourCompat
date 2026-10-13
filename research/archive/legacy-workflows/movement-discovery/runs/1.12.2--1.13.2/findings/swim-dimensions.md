# 1.13.2 adds a swim-height player box

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 2.1 (player dimensions and pose); 4.x collision clearance dependency
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: player movement while swimming in water present in both versions
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose()V`, lines 291-315; SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`. Its size choices are fall-flying `0.6 x 0.6`, sleeping `0.2 x 0.2`, sneaking `0.6 x 1.65`, or standing `0.6 x 1.8`; no swimming predicate selects a size.
- B: corresponding member, lines 334-361; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. Between sleeping and sneaking, `isSwimming() || isDoingSpinAttack()` selects `0.6 x 0.6`. For the swimming case, this can shrink the player height from its prior pose height.
- Both call `Entity.setSize(float,float)` after checking that a proposed box is collision-free; A uses `world.getCollisions(box)` and B uses `world.hasNoCollisions(null, box)`. The `setSize` bodies at A `Entity.java:274-291` and B `Entity.java:285-302` use the same width/height writes and rebuild the shape. Hashes: A Entity `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; B Entity `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- Tick order: A PlayerEntity.tick() calls `updatePlayerPose()` after `super.tick()` at lines 204-245; B does the same at lines 208-254. The local player's movement tick runs within the living superclass tick, so this size choice follows that tick's movement and affects the subsequent state/collision queries.

## Source-level difference

When B's local player is swimming and the collision check accepts the `0.6 x 0.6` box, B selects that size; A has no swimming size branch and retains the size selected by its fall-flying/sleeping/sneaking/standing priority. The body explicitly includes `isDoingSpinAttack()` as another B trigger; this finding is limited to the swimming trigger, and the spin-attack path remains separately scoped. The collision-clearance call differs across the versions and its exact equivalence is not assumed here.

## Reachability and dependencies

B `Entity.updateSwimming()` derives the swimming flag from sprint and water state; PlayerEntity filters swimming while flying or spectating. `PlayerEntity.tick()` applies pose size after the living tick. The resulting box feeds later movement and collision queries. Whether the box shrink succeeds depends on `world.hasNoCollisions` versus A `world.getCollisions`; resolve those helpers and shape behavior in stage 4 before claiming exact clearance equivalence.

## Consequence and uncertainty

Source proves the new B size selection and the different collision-clearance APIs. It predicts a shorter player collision box when the B swim pose is accepted, changing later support, contact and collision calculations. It does not establish the exact first-tick movement consequence or clearance equivalence. Spin-attack sizing and collision helper behavior remain open.

## Handoff

Swimming player dimensions. Related to the water-sprint/swim-state path and collision stage; distinct from water-driven velocity writes. Release of introduction is unknown within the endpoint interval.
