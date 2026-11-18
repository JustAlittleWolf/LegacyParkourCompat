# Frozen source roster — falling water at ordinary oak fence

Frozen before opening the supplied author report. Scope is limited to exact decompiled client player water-collision sampling and the horizontal flow-vector contribution caused by a falling-water face adjacent to an ordinary oak fence, including the immediately above/below neighbor probes and loaded-area gate. No other fluid or fence behavior is in this audit. No cross-version extrapolation is permitted.

## Source identities

All four source trees are published under the primary checkout `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`. Mapping family is Ornithe Feather, Gen2 build.2. Each `ready.json` identifies the exact client release and pinned map artifacts; each source manifest hash below is bound by `ready.json`. The nine-file roster per version was checked against that source manifest before review.

| Release | Mapping artifact | client.jar SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.8.9 | `feather-gen2-1.8.9+build.2` | `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` |
| 1.9.4 | `feather-gen2-1.9.4+build.2` | `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| 1.10.2 | `feather-gen2-1.10.2+build.2` | `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11.2 | `feather-gen2-1.11.2+build.2` | `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |

## Ordered paired review roster

For every exact release listed above, inspect these files and only the named responsibility in this audit:

1. `net/minecraft/entity/Entity.java` — `checkWaterCollisions` body, its loaded-area guard, bounds-to-block scan, liquid acceptance, neighbor probe ranges, direction accumulation and velocity write.
2. `net/minecraft/entity/living/LivingEntity.java` — player inheritance/tick call path reaching the entity water-collision check; include enclosing client/player reachability gates where present.
3. `net/minecraft/world/World.java` — block-state and loaded-area access used by that check, only as needed to close the gate and probe semantics.
4. `net/minecraft/block/FenceBlock.java` — ordinary fence collision shape and horizontal face extent used by liquid face tests.
5. `net/minecraft/block/LiquidBlock.java` — flowing-water shape, direction vector construction and horizontal face test.
6. `net/minecraft/block/LiquidSourceBlock.java` — source-water inheritance/override affecting whether the target is flowing water.
7. `net/minecraft/block/Block.java` — collision/shape default behavior and collision-face predicate consumed by fence/liquid checks.
8. `net/minecraft/block/material/Material.java` — liquid and movement-blocking identity predicates used by the scoped checks.
9. `net/minecraft/block/Blocks.java` — identity/registration proving ordinary oak fence and water classes selected by the mapped source.

Review each exact version independently. Compare only adjacent explicit releases (1.8.9→1.9.4, 1.9.4→1.10.2, 1.10.2→1.11.2) where a claim needs a comparison. Roster freeze does not assert a difference or close broader player tick coverage. Search hits, source display names and assumed equivalent behavior are not evidence.

## Exclusions fixed at freeze

No wiki, mod implementation, product Git history, other lane draft, broad movement inventory, other block families, rendering, server/non-player paths, or trajectory simulation. The report is not opened until this file is written and hashed.
## Frozen roster addendum — client call-chain closure

After the initial freeze, exact Feather paths showed that the named `LocalPlayer` concept is `LocalClientPlayerEntity`, not a class literally named `LocalPlayer`. Before opening the report, add these three files in each release to close the player route into the already-rostered `Entity.tick()` call site:

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` — local client player tick and delegation.
- `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` — client player tick/super-call bridge.
- `net/minecraft/entity/living/player/PlayerEntity.java` — player inheritance bridge to `LivingEntity` / `Entity`.

The frozen roster now consists of the original nine files plus these three paths, all per each of the four exact releases. This addendum is the only scope expansion.
