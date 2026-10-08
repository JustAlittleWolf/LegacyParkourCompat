# Independent re-review: corrected sprint-swimming snapshot — 2026-10-08

## Exact binding and decision

**Decision: ACCEPT this bounded corrected snapshot.** This decision binds only to owner snapshot commit `1e5b3a65cdb9946f1fe9553022950454038d9c68`, path `workflows/wiki-audit-2026-10-07/sprint-swimming-1.12.2-to-1.13.2-r2.md`, Git blob `d7118dc7b16a16df254e1b85836bd107690b955f` (5,902 bytes), raw-byte SHA-256 `62140b4d629831c430702bf7aff78fa87784beddd65dbfcb28b47dbb7a19fa782`. The raw digest was computed from the exact committed blob bytes.

The earlier snapshot remains rejected history: commit `bb0c70e808818dc6b1155a016e4434d6867cbea`, path `workflows/wiki-audit-2026-10-07/sprint-swimming-1.12.2-to-1.13.2.md`, raw SHA-256 `334d7608e28efd543d2ecb5f417ac2cc758f9e38688f422e438a46f03ffc802d`. It was not modified or reclassified. The other four prior ACCEPT decisions and the bed-walk OUT OF SCOPE disposition in `independent-review-new-snapshots-2026-10-08.md` are unchanged.

## Corrections verified

The corrected 1.12.2 `LocalClientPlayerEntity.java` digest is `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`; direct hashing of the exact ready source and its `ornithe-feather.sources.sha256` row agree. The other source identities cited by r2 also match the exact ready files: 1.12.2 `LivingEntity.java` `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`, `PlayerEntity.java` `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`; and 1.13.2 `LocalClientPlayerEntity.java` `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`, `LivingEntity.java` `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, `PlayerEntity.java` `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.

The path distinction is now accurate. In 1.12.2, the double-tap start branch requires `onGround`; its separate sprint-key branch does not require ground contact and has no water predicate. In 1.13.2, double-tap accepts `onGround || isSubmergedInWater()`, while the sprint-key branch requires `!isInWater() || isSubmergedInWater()`. Therefore the new submerged double-tap eligibility covers airborne submerged input; direct-key submerged start already existed in 1.12.2, while the 1.13.2 key gate excludes partial-water states. The changed swim-pose stop path is called out separately. The common forward threshold, food-or-flight condition, item-use exclusion, and blindness exclusion are retained.

The state dependency and order are preserved: 1.13.2 `PlayerEntity.tick()` calls `updateWaterSubmersion()` before `super.tick()`, and the producer reads `FluidTags.WATER`; the local-client override delegates to that producer before the sprint-input path. The exact client JAR SHA-256 is `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`; its default `data/minecraft/tags/fluids/water.json` raw SHA-256 is `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` and names `minecraft:water` and `minecraft:flowing_water`. This verifies the shipped vanilla default input, not arbitrary reloaded/custom tag contents.

The ordinary water-travel comparison is also correctly bounded: absent Depth Strider and Dolphin's Grace, both versions use `g = 0.02F`; 1.12.2 uses the 0.8 base horizontal multiplier, while 1.13.2 selects 0.9 while sprinting and applies it after movement. This is a later-tick horizontal-velocity-retention difference, not an initial input-acceleration difference.

## Scope and remaining limits

The corrected snapshot does not claim the first exact snapshot cutover. It does not claim whole swimming behavior, all submersion thresholds, surface swimming, the full swim/crawl transition sequence, pose-clearance behavior, or hunger/exhaustion production. Enchantment and Dolphin's Grace travel branches are excluded from the coefficient comparison. No runtime result is claimed. These limits are explicit and consistent with the source paths reviewed here.

This independent re-review covers only the exact r2 snapshot and the source dependencies needed to verify its bounded claims. It does not close the Wiki lane or either source pair. No implementation, MCPK material, normal source-pair discovery report, test, build, game, server, TAS, or runtime path was inspected or run.