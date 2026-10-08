# MCPK finding snapshot r2: 1.17 swimming entry gate

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **corrected source-backed candidate; clean independent review pending**. This snapshot does not freeze the release pair and is not formally accepted.

## Supersession and review provenance

Supersedes commit `3de1093`, path `workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot.md`, blob `79c325aa090b49eb272115ba768b1fa790333cfe`, content SHA-256 `1ae1e0824ff433ec26651fdb7cd9c638bd45cb5ad1f858f4cd4706ed6f528884`.

Review basis: commit `4fa5f57f7e70df066dbf8b9106c103ba8d6acbf2`, merged with `main` at `fcfd4480bfa2af6fa4a94a4c5ace863406709321`. The technical review requested the player flying guard and call order. Its process note blocks formal acceptance pending a clean review; this r2 is not final or accepted.

## MCPK claim and provenance limit

The preserved MCPK [Version Differences catalog entry](https://www.mcpk.wiki/wiki/Version_Differences) attributes to 1.17 a water-at-block-position requirement for starting swimming. The lane's direct MCPK page fetch returned HTTP 403; wording and revision attribution come from the prior catalog and were not freshly verified against the live page.

## Source adjudication

`Entity.baseTick()` updates fluid state, then eye-fluid state, then calls `this.updateSwimming()` (`Entity.java:380–382` in 1.16.5; `:415–417` in 1.17.1). On the player path this virtual call dispatches to `Player.updateSwimming()`. In both endpoints, a flying player is forced to `setSwimming(false)`; only a non-flying player delegates to `super.updateSwimming()` (`Player.java:1420–1424` in 1.16.5; `:1442–1446` in 1.17.1). The entry/continuation comparison below therefore applies only to the non-flying player path.

Within `Entity.updateSwimming`, the already-swimming branch is unchanged at the two endpoints: swimming continues only while sprinting, in water, and not a passenger (`1.16.5:950–951`; `1.17.1:1057–1058`). That branch does not use the new block-position fluid test. For a non-flying player not already swimming, the 1.16.5 predicate is sprinting + underwater + not passenger; 1.17.1 adds `level.getFluidState(blockPosition).is(FluidTags.WATER)` (`1.16.5:949–954`; `1.17.1:1056–1064`). This tests the fluid tag at the player's block position, not whether every part of the bounding box is in source water.

This bounds the entry-predicate delta at the inspected endpoints. It does not establish the first 1.17 patch, pose dimensions, fluid-height boundary, or subsequent swimming movement math. Fluid production and non-player swimming remain outside the finding.

## Exact vanilla source identities

Canonical read-only ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`. Both endpoints are ready Mojmap trees; source and artifact manifest hashes match their ready metadata.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.5/mojmap` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` |
| `1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |

Exact source row SHA-256 values, relative to each endpoint's `mojmap/` directory:

- `1.16.5`: `world/entity/Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `world/entity/player/Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- `1.17.1`: `world/entity/Entity.java` `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; `world/entity/player/Player.java` `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`.