# 1.10.2 → 1.11 source coverage

## Exact inputs and inventory

- Older source root: `decompiled_minecraft/1.10.2/ornithe-feather/`.
  - Ready-marker SHA-256: `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d`.
  - Source-manifest SHA-256: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
  - Verified manifest rows and source files: 1,845; missing: 0; mismatched: 0.
- Newer source root: `decompiled_minecraft/1.11/ornithe-feather/`.
  - Ready-marker SHA-256: `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`.
  - Source-manifest SHA-256: `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.
  - Verified manifest rows and source files: 1,918; missing: 0; mismatched: 0.
- Manifest comparison: 1,842 shared paths, 762 changed shared files, 76 added files and 3 removed files. This is a full path/hash inventory, not a semantic review of all changed Java bodies.
- Relevant exact source hashes below match their source-manifest rows.

| Slice | 1.10.2 SHA-256 | 1.11 SHA-256 | Coverage |
| --- | --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | Movement resolver examined; changed operation is a candidate below. Other changed entity state/response logic remains partial. |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | Jump and `moveRelative` path checked; the sampled formulas/order match, with movement calls now tagged `MoverType.SELF`. Remaining lifecycle/response changes are not fully reviewed. |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` | `a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6` | `updatePlayerPose` checked and unchanged: same 0.6 width and 1.65 sneaking / 1.8 standing heights, with collision gate. Full player tick remains partial. |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | The local move override still forwards movement to the superclass and evaluates auto-jump from resulting displacement; new signature forwards `MoverType`. Other client-input/network consumers are not fully reviewed. |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | Entity-aware `getCollisions(Entity, Box)` scan checked: coordinate bounds, loaded-chunk gate, world-border/default-stone selection, and entity collision collection match in the compared method. State provider dependencies remain open. |
| `net/minecraft/block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` | Default collision collection still translates provider boxes and appends intersecting boxes; individual provider coverage is incomplete. |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `0185504476d365cf05c91365e692497f618a1e536fc311d09211b7caefe8aa7a` | Direct piston response differs; see candidate. Moving-state collision geometry and full resolver dependency remain open. |
| `net/minecraft/entity/MoverType.java` | Absent | `76a24c1a40b5127de62c30ada3495a9bfcd277e7f6039dc50ca6b35b4c062186` | New movement-source enum (`SELF`, `PLAYER`, `PISTON`, `SHULKER_BOX`, `SHULKER`); only the `SELF`/`PISTON` split was followed here. |

## Ordered player movement coverage

| Area | Status | Evidence and boundary |
| --- | --- | --- |
| Local player entry and regular input travel | Partial | `LocalClientPlayerEntity.move` forwards its typed movement to `Entity.move`; `LivingEntity.moveRelative` uses `SELF` for the sampled fall-flying, ground/climbing, lava, and water branches. The corresponding formulas and branch ordering in those sampled spans match. All input acquisition, full player/living tick order, and every travel consumer are not closed. |
| Player pose/dimensions | Bounded no-difference | `PlayerEntity.updatePlayerPose` in both releases selects the same sizes and calls the same collision boolean query before resizing. This does not close the changed generic `Entity.setSize` behavior for sleep or unrelated entity subclasses; bed-entry is excluded. |
| Movement collision resolution | Candidate and partial | `Entity.move` gains a mover-type gate for sneak edge safety, changes the down-offset from constant `1.0` to `stepHeight`, and skips each per-axis clipping loop when the requested component is exactly zero. The step-up retry remains recognizably the same two-path horizontal-distance comparison, but the full resolver diff and zero-component overlap behavior have not been independently closed. Player `stepHeight` is initialized to `0.6F` in both `LivingEntity` constructors. |
| Entity-aware world collision query | Bounded no-difference in method | The checked `World.getCollisions(entity, shape)` loop retains the same expanded block bounds, loaded-chunk checks, world-border predicate/default `STONE`, `BlockState.addCollisions` call, and entity collision append path. A full crosswalk of all providers and their states is not complete. |
| Block collision providers and callbacks | Partial/unexamined | 762 shared source files differ overall; many existing block/provider classes are among them, alongside additions and unrelated files. The complete era-existing provider registry, per-provider state domains, multipart order, callback and neighboring-block dependencies were not reconciled. New 1.11-only mechanics remain modern behavior for the 1.10.2 profile; additions have not all been individually dispositioned. |
| Fluid and climbing predicates | Unexamined beyond sampled `moveRelative` branches | Full old/new predicates, fluid state readers and their providers/callers remain open. |
| Effects, attributes and equipment | Unexamined | Full player-facing modifier/effect lifecycle and movement-speed readers are not closed for this pair. |
| External direct player responses | Partial | Moving-piston displacement is compared below. Full client packet responses and other direct player writers have not been traced. Shulker-only push behavior is a 1.11 feature and is outside 1.10.2-profile emulation. |
| Vehicle and non-player movement | Excluded | Boat, minecart, mob, projectile and vehicle physics are outside the assigned player scope, except where their source sends a direct player response (none accepted here). |
| Health, food production, damage, rendering and bed-entry eligibility | Excluded | Project scope exclusions. |

## Candidate observations

Two bounded source candidates are drafted separately:

- [`findings/sneak-edge-piston-mover-type.md`](findings/sneak-edge-piston-mover-type.md): crouch edge protection now applies only to `SELF`/`PLAYER`; a piston response is tagged `PISTON` and bypasses it. This is a direct local-player response candidate, not an accepted source finding.
- [`findings/moving-piston-client-response.md`](findings/moving-piston-client-response.md): old and new moving-block entity-push timing, swept shape selection, overlap arithmetic, and move source differ. Its state-shape and final clipping dependencies remain open; do not infer a full piston trajectory.

The exact pair boundary does not infer when these operations changed between any intermediate builds.

## Remaining source gates

1. Compare every changed in-scope method in `Entity`, `LivingEntity`, `PlayerEntity`, client input, and client packet response paths, closing caller order and gates.
2. Reconcile the complete 1.10.2/1.11 registry-backed collision provider inventory for old-era blocks, including exact state inputs, callbacks, multipart decomposition and entity filters. Keep new-only block mechanics outside the 1.10.2 emulation claim.
3. Close fluid, climbing, equipment/attribute, effect and external player-response dependency graphs.
4. Independently review these immutable claims and the complete pair inventory. This report is partial source coverage; it does not freeze the pair, establish implementation readiness, or establish runtime parity.
