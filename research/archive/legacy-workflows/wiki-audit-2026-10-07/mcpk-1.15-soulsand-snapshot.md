# MCPK finding snapshot: 1.15 SoulSand movement sampling

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-confirmed bounded finding; independent wiki-lane review pending**. This snapshot does not freeze either full release pair.

## MCPK claim and scope

The MCPK [Soulsand page](https://www.mcpk.wiki/wiki/Soulsand), last edited 2021-09-04 13:46, says that 1.15 changed slowdown to the inner surface, left a 0.3-block perimeter unaffected, extended slowdown to a block position above SoulSand (including slabs), and removed its dependence on slipperiness. The MCPK [Version Differences page](https://www.mcpk.wiki/wiki/Version_Differences), last edited 2026-04-17 12:51, summarizes the same 1.15 change. No other wiki or audit lane was used.

The checked endpoints are 1.14.4 and 1.15.2, both Mojmap. They confirm the mechanics across these endpoints; the exact first 1.15 patch is not independently established by this pair.

## Finding

SoulSand’s horizontal slowdown changes from an overlap callback to a single position-sampled block speed factor.

- In 1.14.4, `Entity.checkInsideBlocks` enumerates every block cell intersecting the entity bounding box after shrinking each side by `0.001`, then calls `entityInside` for each cell (`Entity.java:810–831`). `SoulsandBlock.entityInside` multiplies X and Z velocity by `0.4` (`SoulsandBlock.java:27–30`). Thus each intersected SoulSand cell applies its own factor; overlapping two cells applies `0.4` twice. SoulSand registration does not set a non-default `speedFactor` (`Blocks.java:577–579`).
- In 1.15.2, SoulSand is registered with `speedFactor(0.4F)` (`Blocks.java:601–604`); its `SoulsandBlock` no longer overrides `entityInside` (`SoulsandBlock.java:18–35`). After `checkInsideBlocks`, `Entity` multiplies X and Z by one value from `getBlockSpeedFactor`, leaving Y unchanged (`Entity.java:533–543`). The helper checks the entity’s current block first, then one block position below it; it returns one factor rather than enumerating overlapping cells (`Entity.java:584–601`).
- Both endpoints retain the same `0.875`-high SoulSand collision shape (`SoulsandBlock.java:16, 22–25`). The 1.15 behavior is therefore a movement-sampling change, not a collision-height change.

This confirms the wiki’s change in where and how slowdown is selected. Since the 1.15 probe uses the entity’s X/Z position rather than all horizontal bounding-box overlaps, a player can still have part of their 0.6-wide box over SoulSand after their center has crossed into the neighboring block; that fringe can move normally. The player type is `0.6 × 1.8` at the 1.15.2 endpoint (`EntityType.java:342–344`), giving the wiki’s `0.3` half-width perimeter interpretation. At a seam between two SoulSand blocks, the 1.14 callback can apply twice while the 1.15 position sample chooses at most one block factor.

The 1.15.2 below-position probe is `new BlockPos(x, boundingBox.minY - 0.5000001, z)` (`Entity.java:600–601`). That same probe supplies the ground friction block during `LivingEntity` travel (`LivingEntity.java:1886–1889`); 1.14.4 instead sampled `minY - 1.0` (`LivingEntity.java:1837–1840`). With SoulSand’s top at `y + 0.875`, the 1.14.4 friction sample while standing on it floors to the block below (`y - 0.125`), whereas the 1.15.2 sample floors back into the SoulSand cell (`y + 0.3749999`). This explains why an Ice or Slime block below SoulSand can affect the old friction sample but no longer does in the ordinary standing case. The source confirms the changed sample point; this example is a direct coordinate derivation from the source shape and lookup.

For a bottom slab on the next block row, standing feet are at `y + 1.5`; the 1.15.2 probe becomes `y + 0.9999999`, which floors to the SoulSand cell at `y`. This source path therefore explains the wiki’s slab example. The lower-bound inclusivity at exact floating-point boundaries is governed by the literal `-0.5000001`; no broader slab-shape claim is made here.

## Exact source identities

Canonical source root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`.

Both ready markers identify the exact release and `mojmap` namespace. Their source and artifact-manifest hashes were checked against the marker. Each cited source file hash below matches its row in that release’s source manifest.

| Release / namespace | Ready source manifest SHA-256 | Ready artifact manifest SHA-256 |
|---|---|---|
| `1.14.4/mojmap` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` |
| `1.15.2/mojmap` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |

Cited source file hashes (relative to the corresponding `mojmap/` directory):

- `1.14.4`: `net/minecraft/world/entity/Entity.java` — `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`; `LivingEntity.java` — `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`; `level/block/SoulsandBlock.java` — `3f2ce73aae23fb7e13cf001123343c6c750f00684b5331a5597ae33b011ef4e4`; `level/block/Blocks.java` — `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`.
- `1.15.2`: `net/minecraft/world/entity/Entity.java` — `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `LivingEntity.java` — `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `EntityType.java` — `9a3ac5b01ebf9cb783e5b7073ae227ee2f58dd96437936f6aa21134d72a8d818`; `level/block/SoulsandBlock.java` — `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`; `level/block/Blocks.java` — `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`.

The cited endpoints are Mojmap, so the 1.8.9–1.13.2 Feather derived-JAR limitation does not apply to this finding. It is a bounded source corroboration only; wider movement-catalog coverage and independent wiki-lane acceptance remain open.
