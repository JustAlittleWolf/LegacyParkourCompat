# MCPK clean snapshot re-review — 2026-10-08

Reviewer: clean independent MCPK/source pass. Review identity record: commit `93a01b8509b907e49f1ba990e5fabaf5d9b21f33`, path `workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-candidates-2026-10-08.md`. Snapshot source: commit `78683ba65928004ce8b7b6b9371359164a68d43b`.

## Dispositions

| Finding | Disposition | Review result |
|---|---|---|
| 1.16.1→1.16.2 sneak edge-backoff predicate | **REQUEST CHANGES** | The comparison of the outer gate and unchanged X-only, Z-only, then combined 0.05 backoff loops is accurate. The `isAboveGround()` explanation reverses the collision result: source uses `fallDistance < maxUpStep && !level.noCollision(entity, candidateBox)`, so the candidate box **does collide**. Correct the wording and retain the bounded source-defined fall-distance condition. |
| 1.17 Big Dripleaf collision timing | **ACCEPT** | Registration is absent in 1.16.5 and present in 1.17.1. A grounded entity entering the server callback can set `UNSTABLE`; that state keeps the 11/16–15/16 leaf shape and schedules `PARTIAL` after 10 block ticks. `PARTIAL` lowers the top to 13/16 and schedules `FULL` after another 10 ticks; `FULL` has an empty leaf collision shape. The finding correctly says this is entity-triggered and player-relevant, and records the conflict with the preserved catalog wording of a 20-tick initial reduction. No block state is introduced into an earlier profile. |
| 1.16.5→1.17.1 swimming entry gate | **ACCEPT** | `baseTick()` updates fluid state, updates eye-fluid state, then calls virtual `updateSwimming()`. `Player.updateSwimming()` clears swimming while flying at both endpoints and delegates otherwise. The already-swimming branch is unchanged; only non-flying, non-swimming entry gains the water-fluid tag check at `blockPosition()`. The stated boundary and unchanged flying guard are accurate. |
| 1.14.4→1.15.2 slipperiness sample examples | **REQUEST CHANGES** | The sampling formulas, floor conversion, bottom slab/bed/Soul Sand examples, and exact `SoulsandBlock.java` case are source-backed. However, the identity register gives the snapshot Git blob as `96ca89de5ef2b4e84e2fd591296ab620651ee7c7`; the actual blob at the registered commit:path is `96ca89de5ef2b4e84e2fd591296ab620651ee7c3`. The content SHA-256 matches. Correct the register's blob ID before formal acceptance. |

## Snapshot identities

All four files were read from the immutable snapshot commit. Their content SHA-256 values match the identity register. Three registered Git blob IDs also match; the slipperiness row is the mismatch noted above.

| Finding | Exact snapshot commit:path | Actual Git blob | Content SHA-256 |
|---|---|---|---|
| Edge-backoff | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r2.md` | `3ea28bac8e53ec5d6be2ee99d98001841102eb02` | `d2dae102961157b575d871918c5112243b2a89eb2b412885131facc77e28ad79` |
| Big Dripleaf | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot-r2.md` | `75cc259fe3559a9bc2ce0f8415ea26bc4b9d4b51` | `89c43e33dc7c5d9581950cf1f09e9b9d811e6d210899cee54eac01013f47f6ee` |
| Swimming | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot-r2.md` | `d166be4e626498950f1181c8bc034829968f1c7b` | `17284668382004ed3101a20c9f810d3b5cdb42721bc3efca8a7d5dfc641b3a32` |
| Slipperiness | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot-r2.md` | `96ca89de5ef2b4e84e2fd591296ab620651ee7c3` | `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c` |

## Source identities and evidence

The six cited source and artifact manifest pairs were recomputed from the canonical ready trees and match the snapshot values:

| Version / mapping | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.14.4/mojmap` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` |
| `1.15.2/mojmap` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |
| `1.16.1/mojmap` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| `1.16.2/mojmap` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |
| `1.16.5/mojmap` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` |
| `1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |

Every cited canonical source row was hashed at `ready/<version>/<namespace>/net/minecraft/<snapshot-relative-path>` and matched the SHA-256 recorded in its snapshot. This includes the lowercase filename `world/level/block/SoulsandBlock.java`. Relevant source checks confirmed the 1.15.2 `minY - 0.5000001` sample and floor conversion; 1.16.1/1.16.2 player gates, virtual call site, 0.05 probe loops, and `noCollision` consumer; Big Dripleaf registration, entity callback, delay map, state shapes and scheduled transitions; and both swimming call sequences, predicates, and player flying overrides.

## Limits and process

The preserved MCPK page fetch returned HTTP 403. This pass did not verify the live MCPK page wording or revision metadata; the catalog statements remain attributed to the preserved catalog. These snapshots name Mojmap ready trees for every endpoint. This review does not establish Feather-mapped source equivalence to an original mapped JAR and makes no such equivalence claim.

No broad pair coverage, implementation coverage, or runtime parity is established. I read only the required global guidance, project `README.md`, the named identity register, the four r2 snapshots, and their cited canonical source files and direct source dependencies. No unrelated workflow contents or mod implementation were read; no tests, builds, game/runtime tools, Docker, pushes, or messages to other chats were used.
