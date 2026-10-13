# Bounded ladder shape and climbable-provider comparison: 1.14.4 to 1.16.5

Snapshot ID: `wiki-ladder-shapes-providers-1.14.4-1.16.5`

Status: source comparison recorded; independent Wiki-lane review pending. This closes only the four directional ladder shape constants and the feet-block climbability predicate across the listed exact release endpoints. It does not close ladder attachment/support predicates, later intervals, all climbable behavior, or the Wiki's older sneak-hold/half-block claims. No runtime validation was performed.

## Ready-source identities

The exact ready roots are `build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather`, `.../1.15.2/mojmap`, and `.../1.16.5/mojmap`. The 1.14.4 ready record says status ready and mapping `ornithe-feather`; the later records say ready and mapping `mojmap`. This is a mapping/namespace transition, not a version gap. Source-manifest SHA-256 values are respectively `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

| Endpoint | Exact source and range | SHA-256; source-manifest entry verified |
|---|---|---|
| 1.14.4 | `ornithe-feather/net/minecraft/block/LadderBlock.java`, directional 3/16-thick shape constants and `getShape()`, lines 23–47 | `12dc5ef28f2db058969218573f5d605b94cab4cc36b67b2d46e82aaf39fc767f` |
| 1.14.4 | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `isClimbing()` and `canClimbTrapdoor()`, lines 1239–1264 | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| 1.15.2 | `mojmap/net/minecraft/world/level/block/LadderBlock.java`, directional shape constants and `getShape()`, lines 23–48 | `36014d99536a74a5761cd11cfa3c1ab6ac6aa6947609808852679dddb5625108` |
| 1.15.2 | `mojmap/net/minecraft/world/entity/LivingEntity.java`, `onLadder()` and `trapdoorUsableAsLadder()`, lines 1226–1251 | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| 1.16.5 | `mojmap/net/minecraft/world/level/block/LadderBlock.java`, directional shape constants and `getShape()`, lines 23–48 | `d7f65336fe5cd9432d5e10b85115e3bccbf1cf545bbccd29f31624b5848530c3` |
| 1.16.5 | `mojmap/net/minecraft/world/entity/LivingEntity.java`, `onClimbable()` and `trapdoorUsableAsLadder()`, lines 1363–1395 | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.16.5 | `mojmap/net/minecraft/data/tags/BlockTagsProvider.java`, `CLIMBABLE` provider, lines 521–530 | `9f084139857f7fd2c78928d89596d8b224c5959fc374191d755e17dadca4c239` |
| 1.16.5 | `mojmap/net/minecraft/world/entity/Entity.java`, climbable-tag vertical-distance handling, lines 537–546 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |

Each listed file hash matches its exact version's source-manifest entry. The ready metadata identities and manifest hashes are retained above; no readiness marker or shared artifact was changed.

## Bounded findings

1. **1.14.4 to 1.15.2 — no difference in the inspected ladder shapes and feet-block predicate.** The four directional collision shapes remain 3/16-thick full-height planes. The predicate still accepts ladder, vine, and scaffolding blocks directly, or an open trapdoor directly above a ladder when both facing directions match. `LivingEntity` calls that predicate from the same three inspected movement consumers: climb-up on horizontal collision or jump, horizontal collision clamp, and climb travel adjustment. The class/method names change with namespace (`isClimbing` to `onLadder`), but the checked gates and branches are materially equivalent.

2. **1.15.2 to 1.16.5 — directional ladder shapes remain equal; climbability becomes tag-driven.** The four 3/16-thick planes remain the same. The feet-block predicate changes from explicit ladder/vine/scaffolding identity checks to `BlockTags.CLIMBABLE`, while preserving the separate open-trapdoor-over-matching-ladder case. The 1.16.5 generated provider adds weeping vines, weeping-vines plant, twisting vines, and twisting-vines plant to the climbable tag alongside ladder, vine, and scaffolding. Thus the inspected climbing predicate now admits those four vine block forms. Its three movement consumers still ask the common predicate for the climb-up, horizontal collision, and travel branches. `onClimbable()` also writes `lastClimbablePos` when either branch succeeds; that field is exposed and reset in the same class, but downstream state effects are outside this bounded comparison.

3. **Related consumer:** 1.16.5 `Entity` movement-distance accumulation now preserves vertical distance when the block beneath the entity is in `BlockTags.CLIMBABLE`; the inspected earlier 1.15.2 counterpart uses explicit climbable block checks. This affects a movement-derived distance counter, not the ladder collision shape. The result is not a finding about sound, animation, or gameplay state beyond that bounded accumulation.

The new tag entries are a source-level provider inventory for this ready 1.16.5 tag provider only. This report does not establish every runtime/data-pack tag override or all blocks/producers that can satisfy the tag. Later interval source trees and ladder attachment/support consumers remain open.
