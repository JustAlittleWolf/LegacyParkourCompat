# MCPK finding snapshot r2: 1.17 Big Dripleaf player-relevant collision timing

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **corrected source-backed candidate; clean independent review pending**. This snapshot does not freeze the release pair and is not formally accepted.

## Supersession and review provenance

Supersedes commit `f4d5dcf`, path `workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot.md`, blob `3760d8ab5316a6e5ae9ccdb112af1bcf56820236`, content SHA-256 `ed0f578f848d7d7111db615680f58f66183bfa4db757356182f085d9c2c5ae4c`.

Review basis: commit `4fa5f57f7e70df066dbf8b9106c103ba8d6acbf2`, merged with `main` at `fcfd4480bfa2af6fa4a94a4c5ace863406709321`. That review technically accepted the bounded timing comparison, but recorded contamination in its preliminary search and blocks formal acceptance pending a clean review. This r2 is not final or accepted.

## MCPK claim and provenance limit

The preserved MCPK [Version Differences catalog entry](https://www.mcpk.wiki/wiki/Version_Differences) describes Big Dripleaf tilt as changing its collision shape and attributes an initial reduction after 20 ticks. The lane's direct MCPK page fetch returned HTTP 403; wording and revision attribution remain from the prior catalog and were not freshly checked against the live page. This finding checks only the bounded player-relevant collision sequence in vanilla source.

## Source adjudication

The 1.16.5 Mojmap `Blocks.java` has no `BIG_DRIPLEAF` registration; 1.17.1 registers it. This bounds presence to after 1.16.5 and by 1.17.1, not to a specific first 1.17 patch.

In 1.17.1, `entityInside` can trigger the tilt for a grounded entity only server-side, when tilt is `NONE`, the entity Y is strictly greater than `blockY + 0.6875F`, and there is no neighbor signal (`BigDripleafBlock.java:182–186, 218–220`). The callback accepts an `Entity`; this is a player-relevant path, not an exclusive player-only trigger.

Contact immediately sets `UNSTABLE` and schedules a block tick after 10 ticks. `NONE` and `UNSTABLE` share the leaf collision box from Y=11/16 to 15/16. At that scheduled transition, the block becomes `PARTIAL`, lowering the leaf collision top to 13/16, and schedules another 10 ticks; `FULL` then has an empty leaf collision shape (`BigDripleafBlock.java:45–63, 191–203, 222–230, 249–251`). Thus the first lower collision shape is scheduled after one 10-tick delay; the leaf collision becomes empty after the second delay. This conflicts with the cataloged page wording of 20 ticks before the first reduction. These are block-tick delays, not fixed wall-clock durations.

The collision override returns the leaf shape; the outline stem is not part of this collision-timing finding. Projectile activation and other triggers are outside its bounded claim. Block states and scheduled ticks remain vanilla; the feature must not be introduced into profiles predating its presence.

## Exact vanilla source identities

Canonical read-only ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`. Both endpoints are ready Mojmap trees; source and artifact manifest hashes match their ready metadata.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.5/mojmap` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` |
| `1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |

Exact source row SHA-256 values, relative to each endpoint's `mojmap/` directory:

- `1.16.5`: `world/level/block/Blocks.java` `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`.
- `1.17.1`: `world/level/block/Blocks.java` `87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8`; `world/level/block/BigDripleafBlock.java` `1e1315b0a53eb85a3365335131e7819c1b667a55e14343d839c5211c671fd59f`.