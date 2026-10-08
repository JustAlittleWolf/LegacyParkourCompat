# Finding snapshot: sprint swimming entry and horizontal water drag

Snapshot ID: `wiki-sprint-swimming-1.12.2-1.13.2`

Status: source-confirmed bounded input/travel finding; independent Wiki-lane review pending; runtime validation not performed.

## Candidate and boundary

The [Minecraft Wiki Swimming page](https://minecraft.wiki/w/Swimming?oldid=2726686) records sprint-swimming in the 1.13 development history. The exact 1.12.2 and 1.13.2 source comparison confirms that a fully submerged local player can newly start sprinting from the normal sprint inputs, and that sprinting selects a different horizontal water drag factor in the water travel branch. It establishes the net difference in `(1.12.2, 1.13.2]`; the exact snapshot introduction is not pinned by these release trees.

## Finding

In 1.12.2, both local sprint-start paths require `onGround`: the double-tap path and the sprint-key path. The latter has no water alternative. The ordinary sprint-stop condition does not itself cancel an already active sprint solely because the player enters water, so this finding does not claim sprinting was impossible in every water state.

In 1.13.2, the double-tap start path allows `onGround || isSubmergedInWater()`. The sprint-key path allows sprinting outside water or while submerged: `!isInWater() || isSubmergedInWater()`. Both retain the forward-input threshold and other vanilla eligibility gates. When the player's pose is swimming, the sprint-stop branch treats low forward input and leaving water differently from the non-swimming stop branch. This confirms a new, reachable sprint-start path for a submerged player, not a blanket sprint rule for every partial-water state.

The submersion state is updated by `PlayerEntity#tick()` before it calls `super.tick()`; `PlayerEntity#updateWaterSubmersion()` assigns `isSubmergedInWater` from the water fluid tag. The local-client override delegates to that state update. `LocalClientPlayerEntity` consumes the resulting state in the sprint-input branch. On travel, `PlayerEntity#moveRelative()` first applies the swimming pitch correction, then delegates to `LivingEntity#moveRelative()`; its water branch updates velocity, moves, then applies horizontal friction.

For the water branch with no Depth Strider and no Dolphin's Grace, `g` remains `0.02F` for `updateVelocity()` in both releases. In 1.12.2 horizontal drag `f` is `getBaseMovementSpeedMultiplier()`, whose implementation is `0.8F`, even if a sprint flag is already set. In 1.13.2 the water branch chooses `f = 0.9F` when sprinting and otherwise the same `0.8F` base multiplier; after the movement call it multiplies X and Z velocity by `f`. Thus the source changes horizontal velocity retention on later ticks while sprinting, rather than the initial water input acceleration on that tick. Depth Strider and Dolphin's Grace have separate branches and are outside this bounded calculation.

## Exact source evidence and identities

All paths are rooted at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`. Each hash was recomputed from the ready tree and matched the named source-manifest row.

The 1.12.2 ready record's source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` matches the recomputed `ornithe-feather.sources.sha256`; the corresponding 1.13.2 SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` also matches. Both ready records identify their expected version and report `ready`.

| Version | Source and ranges | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, sprint start/stop gates lines 718-745 | `01a58e94d8c6ff98a8e3794227cdc76a5fcbbdad795c70c9cf28854aff9823cc` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java`, base movement multiplier lines 1421-1423; water travel branch lines 1555-1585 | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, player tick/pose lifecycle lines 245-315 | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, sprint start/stop gates lines 723-758; submersion accessor/update override lines 999-1021 | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java`, water travel branch lines 1617-1655 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, `tick()`/`updateWaterSubmersion()` lines 206-260; pose resize lines 334-361; `moveRelative()` pitch correction/delegation lines 1442-1452; `isSwimming()` guard lines 1818-1821 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |

The finding is limited to sprint eligibility and the ordinary water travel coefficient. The adjacent swim pose dimensions and swimming pitch-control change are documented separately. This snapshot does not close the entire swim/crawl transition sequence, surface swimming, all submersion thresholds, aquatic effects, hunger production, or any snapshot-level boundary.

## Applicability and exclusions

Applicability: local player movement, no flight or spectator mode, water, and the stated sprint input path. Vanilla sprint eligibility reads the food-level gate (`food > 6` or flight permission); this audit records that gate without modeling hunger or exhaustion. The coefficient comparison assumes no Depth Strider or Dolphin's Grace. No runtime validation was performed.
