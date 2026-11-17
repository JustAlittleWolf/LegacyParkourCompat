# Independent review: revised shulker opening push

## Verdict

**ACCEPT** for the revised opening-only finding at `build/mcpk-20261116/vd-full-checklist/shulker-push-cutover/findings/1.16.5--1.17/shulker-box-progress-push.md`, exact SHA-256 `461301D58D5FF9F34DD86EBBE17B64817FEC4AE2ECE24BD877C3DFE462171165`. The hash recomputed from the candidate bytes matches the dispatch. The requested repeated-displacement correction is present; the remaining statement stays bounded to opening and accurately distinguishes per-call source formulas from the source-derived unobstructed path totals.

## Exact source identity checked

- 1.16.5 Mojang-mapped sources: `decompiled_minecraft/1.16.5/mojmap.sources.sha256`, SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; the exact 1.16.5 `ShulkerBoxBlockEntity.java` bytes hash to `4aab41f71aacf9e1138a225e1253e202a303276ff21c7b4974b11a76d694c322`, matching the source manifest. The pinned `1.16.5/client.jar` and `client_mappings.txt` inputs are also recorded in `decompiled_minecraft/1.16.5/artifacts.sha256`.
- 1.17 Mojang-mapped sources: `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json`, SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`. Its input map identifies the 1.17 client jar and official client mappings; the provenance source map records `ShulkerBoxBlockEntity.java` hash `0f1b810c4977ad8f9c27b6abb97ae9d1af8d5445e1cf514d33042f74a9a59cb7` and `Shulker.java` hash `102331800abe50283910e0fd94334c1ee4d4094bcd471bad3426ed9a2bcf3816`. Current local bytes match both entries.

## Opening calculation

The 1.16.5 source increments `progress` by `0.1F` in `updateAnimation` and uses `0.5F * progress` to grow the box. `getTopBoundingBox` narrows that grown box to the moving face slab; `moveCollidedEntities` selects entities overlapping the current slab and computes the directional overlap from the slab's outer face to the entity's near bound, then adds `0.01` (`ShulkerBoxBlockEntity.java:61–75, 93–105, 107–152`). On the stated unobstructed path, first overlap is `0.05 + 0.01 = 0.06`. That lead leaves about `0.04` slab overlap at each later `0.05` growth step, so subsequent calls are about `0.04 + 0.01 = 0.05`; nine such calls total about `0.51` over ten opening ticks.

The 1.17 source also increments by `0.1F`, and its physical box still uses `0.5F * progress`. Its push query instead uses `Shulker.getProgressDeltaAabb(direction, progressOld, progress)` with the unhalved progress endpoints; that helper returns an axis size equal to the progress delta. The push call requests that size plus `0.01` (`ShulkerBoxBlockEntity.java:67–82, 100–125`; `Shulker.java:244–254`). Each `0.1F` opening step therefore yields about `0.11`, and the selected unobstructed player continues intersecting the successive sweep regions, producing about `1.1` over ten ticks. Ratio `1.1 / 0.51 ≈ 2.16`; all cumulative figures are source-derived scenario inferences, not literal constants.

## Reachability and scope

Both client level tick paths call `tickBlockEntities` (`1.16.5 ClientLevel.java:177`; `1.17 ClientLevel.java:170`). The 1.16.5 shulker block entity is `TickableBlockEntity`; 1.17 `ShulkerBoxBlock.getTicker` returns its ticker without a client-side exclusion (`ShulkerBoxBlock.java:69–70`). The movement query includes the player among entities in the sweep, excludes only `PushReaction.IGNORE`, and calls general `Entity.move(MoverType.SHULKER_BOX, ...)` (`1.16.5 ShulkerBoxBlockEntity.java:107–152`; `1.17 ShulkerBoxBlockEntity.java:104–125`). `Entity.move` applies collision-resolved position changes. Thus the direct response is reached on the logical client. Opening eligibility, closing behavior, and server-side producer behavior remain outside this candidate's stated claim.
