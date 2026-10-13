# MCPK finding snapshot r2: 1.15 slipperiness sample examples

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **corrected source-backed candidate; clean independent review pending**. This snapshot does not freeze the release pair and is not formally accepted.

## Supersession and review provenance

Supersedes commit `bb05061`, path `workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot.md`, blob `65a2936fb581f40a2c0ce1c4f86e0b682c9b39de`, content SHA-256 `6714aaef4e086592c04f062ee78fdf0ee0eea4678e432c93028f456051287883`.

Review basis: commit `4fa5f57f7e70df066dbf8b9106c103ba8d6acbf2`, merged with `main` at `fcfd4480bfa2af6fa4a94a4c5ace863406709321`. The technical review requested an exact-case source path for Soul Sand. Its process note blocks formal acceptance pending a clean review; this r2 is not final or accepted.

## MCPK claim and provenance limit

The preserved MCPK [Slipperiness catalog entry](https://www.mcpk.wiki/wiki/Slipperiness) says the sample point moved from one block below to 0.5 blocks below in 1.15 and gives slab, bed, and Soul Sand examples. The lane's direct MCPK page fetch returned HTTP 403; wording and revision attribution come from the prior catalog and were not freshly verified against the live page.

## Source adjudication

At 1.14.4, grounded player movement samples `new BlockPos(x, boundingBox.minY - 1.0, z)` before applying block friction (`LivingEntity.java:1837–1840`). At 1.15.2 it obtains `getBlockPosBelowThatAffectsMyMovement()` (`LivingEntity.java:1886–1889`), which constructs `new BlockPos(x, minY - 0.5000001, z)` (`Entity.java:600–602`). `BlockPos(double, double, double)` delegates to `Vec3i(double, double, double)`, which floors the coordinates (`BlockPos.java:41–43`; `Vec3i.java:23–24`).

- **Bottom half slab:** top is at block Y + 8/16. The new sample is Y − 0.0000001 and floors to the block below, so ice there still supplies slipperiness. The old sample at Y − 0.5 also selects below.
- **Bed:** collision top is Y + 9/16 (`BedBlock.java:49–57`). The new sample is Y + 0.0624999 and floors to the bed's own block position; the old sample is Y − 0.4375 and selects below.
- **Soul Sand:** collision top is Y + 14/16 (`SoulsandBlock.java:15–24`). The new sample is Y + 0.3749999 and floors to the Soul Sand block position; the old sample is Y − 0.125 and selects below.

The cited exact path is `net/minecraft/world/level/block/SoulsandBlock.java` (lowercase `s` after `Soul`), matching the ready-tree filename and source-manifest entry. A top slab has its top at Y + 1.0 and the new sample falls inside its own block position; the half-height example is therefore qualified to a bottom slab. These calculations resolve sample-position examples, not total movement trajectories.

## Exact vanilla source identities

Canonical read-only ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`. Both endpoints are ready Mojmap trees; source and artifact manifest hashes match their ready metadata.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.14.4/mojmap` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` |
| `1.15.2/mojmap` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |

Exact source row SHA-256 values, relative to each endpoint's `mojmap/` directory:

- `1.14.4`: `world/entity/LivingEntity.java` `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`.
- `1.15.2`: `world/entity/Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `world/entity/LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `world/level/block/BedBlock.java` `e2496c30e0ff922f5bb492f951e2389c1e1fbbbdcd7aed2d4d308574fa7a4b0f`; `world/level/block/SlabBlock.java` `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86`; `world/level/block/SoulsandBlock.java` `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`; `core/BlockPos.java` `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950`; `core/Vec3i.java` `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d`.