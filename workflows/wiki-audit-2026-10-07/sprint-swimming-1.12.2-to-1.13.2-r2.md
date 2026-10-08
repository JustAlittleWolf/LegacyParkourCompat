# Corrected finding snapshot: sprint swimming and horizontal water drag

Snapshot ID: `wiki-sprint-swimming-1.12.2-1.13.2-r2`

Status: corrected source-confirmed bounded input/travel finding; the prior snapshot `wiki-sprint-swimming-1.12.2-1.13.2` was rejected by the independent Wiki-lane review and is preserved unchanged. Runtime validation was not performed.

## Supersession and correction

This immutable snapshot supersedes the claim binding in rejected snapshot commit `bb0c70e808818dc6b1155a016e4434d6867cbea6` (file SHA-256 `334d7608e28efd543d2ecb5f417ac2cc758f9e38688f422e438a46f03ffc802d`). The review commit is `5c94a3d106f010cd033d19c9aa48f47089987b30`, and its decision record is `workflows/wiki-audit-2026-10-07/reviews/independent-review-new-snapshots-2026-10-08.md`. The reviewer requested correction because the 1.12.2 `LocalClientPlayerEntity.java` digest was mistyped and because the two 1.12.2 sprint-start branches were incorrectly summarized as both requiring ground contact. The prior commit and its file are retained as historical rejected evidence; this file is a new immutable replacement, not an edit to that record.

## Finding and exact gates

The bounded comparison is 1.12.2 to 1.13.2; it does not identify an exact snapshot cutover. In 1.12.2, the double-tap sprint-start branch requires `onGround` (as well as its other eligibility gates). The direct sprint-key branch has no `onGround` predicate and no water exclusion. The ordinary sprint-stop branch checks low forward input, horizontal collision, and sprint eligibility, but does not stop solely because the player enters water. Thus 1.12.2 did not categorically forbid sprinting in water.

In 1.13.2, double-tap start accepts `onGround || isSubmergedInWater()`. The direct sprint-key branch accepts `!isInWater() || isSubmergedInWater()`: it allows a fully submerged player and excludes partial-water states. Its swim-pose sprint-stop branch also differs from the ordinary non-swimming stop branch. Both versions retain the forward-input threshold (`>= 0.8F`), food/flight eligibility (`food > 6.0F || canFly`), item-use exclusion, blindness exclusion, and the surrounding input conditions shown in source. This finding records the local-player sprint predicates; it does not model hunger or exhaustion production.

The 1.13.2 player tick updates water submersion before `super.tick()`. `updateWaterSubmersion()` derives the state from `FluidTags.WATER`, and the local-client override delegates to the player state update. The sprint-input branches consume this state. The submersion producer is included to establish the state dependency, not to claim every fluid/submersion transition is covered.

For ordinary water travel with no Depth Strider or Dolphin's Grace, the compared `g` input-acceleration term remains `0.02F`. In 1.12.2 the water branch uses `getBaseMovementSpeedMultiplier()` (`0.8F`) for horizontal friction, including when sprint is already active. In 1.13.2 the branch selects `f = 0.9F` while sprinting and otherwise the `0.8F` base; after movement it multiplies horizontal velocity by `f`. This is a later-tick horizontal-velocity-retention difference, not a change to that tick's initial water input-acceleration term. Enchantment and Dolphin's Grace branches are outside this calculation.

## Exact source identities

The source root is `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`. Ready records identify the expected versions and `ready` status. The 1.12.2 `ornithe-feather.sources.sha256` is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the 1.13.2 value is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. Both match their published ready records and source-manifest files.

| Version | Source and bounded method/ranges | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, sprint-start and stop gates, lines 718-745 | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java`, base movement multiplier lines 1421-1423; water travel lines 1555-1585 | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, player tick/pose lifecycle lines 245-315 | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, sprint-start/stop gates lines 723-758; submersion accessor/update override lines 999-1021 | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java`, water travel lines 1617-1655 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, tick and submersion producer lines 206-260; pose resize lines 334-361; `moveRelative()` pitch correction/delegation lines 1442-1452; `isSwimming()` guard lines 1818-1821 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |

The corrected 1.12.2 client source digest was recomputed directly from the ready source file and matches its source-manifest entry. Other cited file identities and both source-manifest identities are retained from the verified source record.

## Applicability and exclusions

This is a local-player input and ordinary water-travel comparison. The sprint predicates have additional common vanilla gates recorded above. The travel coefficient comparison assumes no Depth Strider or Dolphin's Grace. It excludes hunger/exhaustion production, aquatic damage, surface swimming, all other submersion thresholds, the full swim/crawl transition sequence, pose-clearance behavior, and exact snapshot-boundary dating. No runtime validation was performed.
