# F012: Local stuck-block escape probe

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `pushAwayFrom, mobTick`, lines 401–455, 717–721.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `addMessage, mobTick`, lines 388–447, 645–651.

Difference: 1.13.2 invokes `pushAwayFrom` at four points before sprint checks; its method returns immediately for noClip. 1.14.4 surrounds the four calls with `!noClip`. Those guards are equivalent at the call boundary. The recovery probe differs: 1.13.2 tests one BlockPos; when not swimming it uses `!doesNotSuffocate(pos)`, where `doesNotSuffocate` requires `shouldSuffocate(pos)` and a non-solid block above; when swimming it uses `!shouldSuffocate(pos)`. 1.13.2's neighbor candidates use `doesNotSuffocate` directly. 1.14.4 scans integer Y positions from floor of player box minY to ceil of maxY and reports collision if any `shouldSuffocate` check is false; neighbor candidates use the same full scan. 1.14.4's `shouldSuffocate` uses `!state.isViewBlocking(world,pos)`; 1.13.2 uses `!state.isSolid()`.

Movement meaning: LocalClientPlayerEntity.mobTick -> four push-away probes -> suffocation predicates for current/neighbor blocks -> direct velocity component assignment to ±0.1.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F012-local-stuck-block-escape-probe.md).
