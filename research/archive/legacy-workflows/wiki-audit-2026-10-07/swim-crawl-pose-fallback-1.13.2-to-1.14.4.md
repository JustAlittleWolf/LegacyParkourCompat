# Pose fallback enables an out-of-water crawl pose

Snapshot ID: `wiki-crawl-pose-fallback-1.13.2-1.14.4`

Status: source-confirmed bounded pose-selection finding for the exact 1.13.2→1.14.4 endpoint pair. The exact snapshot cutover is unknown. This does not close the full swim/crawl or per-tick movement inventory. Runtime validation was not performed.

## Wiki lead and access provenance

The audit catalog records the Crawling-page leads for 16w04a and 19w14a/b. The [Crawling page](https://minecraft.wiki/w/Crawling) direct fetch returned an internal error on 2026-10-08; the available official search result had no revision ID. That access outcome is preserved in [wiki-page-fetch-log.md](wiki-page-fetch-log.md). The [Swimming page](https://minecraft.wiki/w/Swimming?oldid=2726686) was opened at oldid `2726686`, with stale cached content, and remains a secondary lead. The exact-source comparison below establishes the released endpoint behavior; it does not prove the snapshot-level introduction described by a Wiki lead.

## Source and artifact identities

The source roots are `ready/1.13.2/ornithe-feather` and `ready/1.14.4/ornithe-feather`. Both ready records identify the expected version and `ready` status. The 1.13.2 source-manifest SHA-256 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the exact client input JAR SHA-256 is `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`. The immutable `feather-r1-2026-10-07` derived JAR for 1.13.2 is SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; its source-manifest is the same cited manifest. Equivalence to the unavailable original derived JAR is unproven.

The 1.14.4 Feather ready record binds client input JAR SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` and source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. No `feather-r1-2026-10-07` replacement snapshot exists for 1.14.4, so its cited source is bound to that exact ready output and input artifact rather than an r1 JAR.

| Version | Exact source paths, slices | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/entity/Entity.java`, tick-to-water/swim updater `m_03231680()` call lines 393-395 and body lines 947-959; swim-flag read/write lines 1790-1795 | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, `tick()` calls water-submersion update before `super.tick()` and pose update after it, lines 206-208 and 252-259; `updatePlayerPose()` lines 334-360; player `isSwimming()` gate lines 1818-1821 | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, sprint input and swim-pose stop path lines 722-758 | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.14.4 | `net/minecraft/entity/Entity.java`, `baseTick()` calls water/swim updater lines 339-358; updater lines 942-954; collision clearance helper `m_87715359(Pose)` lines 1628-1630; pose-sized AABB `m_16672198(Pose)` lines 2300-2305; swim-pose/crawl queries and flag access lines 1814-1827 | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerEntity.java`, pose-size map lines 108-118; player tick calls water-submersion update before `super.tick()` at lines 234-235 and pose update after it at lines 279-281; submersion producer lines 284-286; `updatePlayerPose()` lines 343-370; player swimming override lines 1412-1418 and 1762-1764 | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.14.4 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, swimming sprint-stop and jump-double-tap input branches lines 680-708 | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |

Each cited class digest matches its exact version's published source-manifest row.

## Bounded behavior

In 1.13.2, the entity tick runs its water/swimming update. The player override prevents the swimming flag while flying and otherwise delegates to the entity rule: a current swimmer remains swimming only while sprinting, in water, and not riding; a non-swimmer starts only while sprinting, submerged in water, and not riding. The player then chooses dimensions from fall-flight, sleep, actual swimming or spin attack, sneak, and standing. The swimming/spin box is `0.6 × 0.6`; resizing occurs only when the proposed box has no collisions. If that box is obstructed, the player retains its existing dimensions; this method has no fallback that writes a swimming pose solely because standing and sneaking are obstructed.

In 1.14.4, the entity swim-flag producer and player flying override retain those same gates. Pose selection is separate. The player pose map defines swimming as `0.6 × 0.6` and sneaking as `0.6 × 1.5`. `updatePlayerPose()` first requires the swimming-pose dimensions to be collision-clear. It then chooses a desired pose from fall-flight, sleeping, actual swimming, spin attack, sneaking, or standing. If the desired pose is obstructed, it tries sneaking; when that is also obstructed, it selects `Pose.SWIMMING`. The entity query `m_99544176()` identifies the swimming pose while not in water as crawling. Therefore a non-swimming player can enter the crawl-sized pose when the narrow space admits the `0.6 × 0.6` dimensions but rejects both the normal desired pose and the sneaking pose.

This distinguishes the water-driven swimming flag from the pose value. The shared water-swim flag formula is unchanged in the inspected endpoints; 1.14.4 adds collision-tested pose fallback capable of selecting the crawl pose outside water. The comparison is bounded to these callers, predicates, dimensions and state reads/writes, not to all input/travel behavior.

## Remaining swim/crawl inventory

This finding closes one pose-selection slice. Still open are exact dimension/eye-height downstream consumers, clearance and collision ordering across every pose, player input and sprint transitions through each tick, surface-swimming thresholds and submersion producers, travel/acceleration and pitch paths, crawling movement/step effects, the full pose producer-consumer graph, and the exact snapshot boundary. The separate accepted sprint-swimming and pending swimming-pitch findings remain bounded, not whole-swim coverage. Hunger/exhaustion production, aquatic damage, and non-player movement remain excluded.
