# MCPK finding snapshot: 1.15 slipperiness sample examples

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-confirmed bounded finding; independent wiki-lane review pending**. This snapshot does not freeze the full release pair.

## MCPK claim

The MCPK [Slipperiness page](https://www.mcpk.wiki/wiki/Slipperiness), last edited 2021-09-04 13:06, says that 1.15 moves the sample point from one block below the player to 0.5 blocks below. It gives three examples: a half-height slab above ice remains affected by the ice, while a 9/16-high bed and 14/16-high Soul Sand no longer take slipperiness from the block below.

## Source adjudication

At the 1.14.4 endpoint, grounded player movement samples `new BlockPos(x, boundingBox.minY - 1.0, z)` before using that block’s friction (`LivingEntity.java:1837–1840`). At 1.15.2, movement uses `getBlockPosBelowThatAffectsMyMovement()` (`LivingEntity.java:1886–1889`), which samples `new BlockPos(x, boundingBox.minY - 0.5000001, z)` (`Entity.java:600–602`). The 1.15.2 block shapes produce the wiki examples as follows:

- **Bottom half slab:** its top is at block Y + 8/16. The new sample is block Y − 0.0000001, so it selects the block below the slab; an ice block there still supplies slipperiness. The old sample was block Y − 0.5 and also selected below. This confirms the wiki example for a bottom slab.
- **Bed:** the collision top is block Y + 9/16 (`BedBlock.java:49–57`). The new sample is block Y + 0.0624999, inside the bed’s block position, so the ice below is no longer sampled. Under the old offset, the sample was block Y − 0.4375, below the bed.
- **Soul Sand:** its collision top is block Y + 14/16 (`SoulSandBlock.java:16, 23–24`). The new sample is block Y + 0.3749999, inside the Soul Sand block position; the old sample was block Y − 0.125, below it. The underlying ice therefore stops supplying the sampled slipperiness.

The slab wording needs a state qualifier: a **top** slab has its top at block Y + 1.0, so the 1.15.2 sample is inside that same block position rather than below it. The page’s half-height geometry example matches a bottom slab; this snapshot does not generalize it to every slab placement. The finding verifies the sample-position examples, not total movement trajectories over these surfaces.

## Exact source identities

Canonical ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`.

Both endpoints are ready Mojmap trees. Their source and artifact manifest hashes identify the endpoints; cited Java file hashes match the corresponding source-manifest rows.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.14.4/mojmap` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` |
| `1.15.2/mojmap` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |

Cited Java file SHA-256 values, relative to each endpoint’s `mojmap/` directory:

- `1.14.4`: `net/minecraft/world/entity/LivingEntity.java` — `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`.
- `1.15.2`: `net/minecraft/world/entity/Entity.java` — `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `LivingEntity.java` — `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `level/block/BedBlock.java` — `e2496c30e0ff922f5bb492f951e2389c1e1fbbbdcd7aed2d4d308574fa7a4b0f`; `SlabBlock.java` — `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86`; `SoulSandBlock.java` — `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`.

This resolves the page’s bed, bottom-slab, and Soul Sand sample examples at the inspected endpoints. Exact earliest 1.15 patch and broader movement trajectories remain open.