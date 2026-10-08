# F-BLOCK-CONTACT-TRAVERSE — swept player block contact

- Source status: source-confirmed; pair remains active and this finding has not been independently accepted or snapshotted.
- Pair: A 1.21.1 Mojmap -> B 1.21.3 Mojmap.
- Change boundary: unknown within (1.21.1, 1.21.3]; only these endpoints were compared.
- Scope: player movement through existing inside-block shapes.

## Paired source evidence

A: ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/entity/Entity.java, hash b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850. In move, lines 705-706 call tryCheckInsideBlocks() after position/collision processing. Lines 758-766 wrap checkInsideBlocks(). Lines 1001-1031 enumerate only block positions overlapped by the entity's current bounding box and call entityInside for those states.

B: ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/entity/Entity.java, hash a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9. move lines 619-701 no longer call the inside-block checker. applyEffectsFromBlocks lines 736-767 records the supplied movement, calls the path checker, then clears its per-tick movement/block sets. checkInsideBlocks lines 1038-1078 traverses block cells along each recorded from/to movement using BlockGetter.boxTraverseBlocks, obtains getEntityInsideCollisionShape, and invokes entityInside when the shape condition passes. LivingEntity.aiStep lines 2774-2777 invokes this after travel when server-side or locally controlled; hash 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52.

Both endpoints contain the same WebBlock.entityInside implementation at ../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap/net/minecraft/world/level/block/WebBlock.java and ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/level/block/WebBlock.java, file hash ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414, lines 26-33. It calls Entity.makeStuckInBlock with horizontal multiplier 0.25 and vertical multiplier 0.05 (or weaving-effect values). Entity.move reads and clears stuckSpeedMultiplier at A lines 611-615 and B lines 633-637. The B default inside-collision shape provider at ../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java is BlockBehaviour.getEntityInsideCollisionShape, returning Shapes.block() at lines 353-355; file hash 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133.

## Behavior and precondition

When the player's movement from/to path intersects an inside-block collision shape but the post-move bounding box no longer overlaps that block, A's final-box scan does not call the callback for that block. B's traversed-cell scan can call it during the post-travel pass. With the existing cobweb callback, B then writes the stuck multiplier that is consumed by the next player Entity.move, changing the next movement vector. The finding is conditional on that path geometry and movement magnitude; it does not claim every web entry changes timing or velocity.

## Dependency closure for this finding

- Closed: player tick path reaches LivingEntity.aiStep and its travel call; B's post-travel callback is guarded to include the locally controlled instance.
- Closed: A final-box callback path, B recorded path callback, exact shape gate, unchanged WebBlock callback, and next-move multiplier consumer.
- Pair-wide dependencies remain open: other block providers, resource/tag defaults, all travel branches, external impulses/corrections and full-pair independent audit. Those do not change the bounded callback difference but still block pair completion.
- Implementation handoff: blocked until a separate blind reviewer accepts an immutable finding snapshot under the campaign snapshot protocol.

## Evidence identity

- A source manifest SHA-256: 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48.
- B source manifest SHA-256: d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce.
- A artifact manifest SHA-256: 09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486.
- B artifact manifest SHA-256: 0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf.
- Artifact revision: not applicable; Feather derived-artifact revision covers six early Feather runs. This pair uses the published Mojmap sources and hashes above.
