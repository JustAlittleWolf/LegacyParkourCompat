# Correction: 1.13 slab registry aliases through 26.2

## Verdict

**ACCEPT**, within the bounded collision-provider and state-alias scope for the 19 slab registrations in 1.13. This correction supersedes only the same-name alias claim for `stone_slab` in the bound closure. The original closure and its independent review are preserved unchanged.

The original closure is `.native-source/20261028/collision-next/slab-1130-all-slab-state-aliases/closure.md`, SHA-256 `58CF22BD0E2EFEADD62EBBB678B7F9EAC8F957BAE1D3B087B68EA17E6D3BC30C`; its source manifest is SHA-256 `A7320D175756CC6CA3E42311EC5BFF36286F14924D797B8F746F61B1465AF98B`. Its independent review is `.native-source/20261028/collision-next/slab-1130-all-slab-state-aliases-review/review.md`, SHA-256 `B39C4C76A211E9389F30403776F5F31959AE94C6535E3E45FE30C6EA09691935`; its manifest is SHA-256 `F14B2824F179350CEB8291C839EFA390C18AE06CCF744D0C571AF40CCDECA5E9`.

## Corrected key mapping

The 1.13 `Block.init` registry has these 19 slab keys (`Block.java:1411-1413,1568-1583`). The 26.2 block-renaming fixer is the authority for old block identifiers: `DataFixers.java:752-764` registers the version-1802 `BlockRenameFix` from `minecraft:stone_slab` to `minecraft:smooth_stone_slab`. `BlockRenameFix.java:27-32,37-40,42-63` changes only the block name and retains the state properties/remainder. Searching the 26.2 data-fixer sources for these 19 registry names found no other relevant 1.13-to-current block-key rename. The `prismarine_bricks_slab` to `prismarine_brick_slab` entry at `DataFixers.java:619-644` is an earlier spelling change; 1.13 already registers the singular `prismarine_brick_slab` key.

| 1.13 registry key | 26.2 key after block data fixing |
|---|---|
| `oak_slab` | `oak_slab` |
| `spruce_slab` | `spruce_slab` |
| `birch_slab` | `birch_slab` |
| `jungle_slab` | `jungle_slab` |
| `acacia_slab` | `acacia_slab` |
| `dark_oak_slab` | `dark_oak_slab` |
| `stone_slab` | `smooth_stone_slab` |
| `sandstone_slab` | `sandstone_slab` |
| `petrified_oak_slab` | `petrified_oak_slab` |
| `cobblestone_slab` | `cobblestone_slab` |
| `brick_slab` | `brick_slab` |
| `stone_brick_slab` | `stone_brick_slab` |
| `nether_brick_slab` | `nether_brick_slab` |
| `quartz_slab` | `quartz_slab` |
| `red_sandstone_slab` | `red_sandstone_slab` |
| `purpur_slab` | `purpur_slab` |
| `prismarine_slab` | `prismarine_slab` |
| `prismarine_brick_slab` | `prismarine_brick_slab` |
| `dark_prismarine_slab` | `dark_prismarine_slab` |

The 26.2 registry has both `stone_slab` and `smooth_stone_slab` (`Blocks.java:3296-3305`). The migrated historical key resolves to `SMOOTH_STONE_SLAB`, constructed by `SlabBlock::new`; the current `STONE_SLAB` is a different block and is outside this 1.13 roster. Both current registrations use the same slab block class and state-based shape provider. The source-confirmed 1.13 and 26.2 slab providers use the same bottom/top/double bounds; `type` selects the shape and `waterlogged` does not alter collision geometry. The product alias table must therefore accept current `smooth_stone_slab` as old `stone_slab`, and must reject current `stone_slab` from the legacy slab-only branch.

The product correction is present in commit `a4a31b55702f21ee20da10211b332ce2a6be3b21`, with `CURRENT_TO_LEGACY_113_SLAB_KEYS` covering all 19 keys in `MovementRuntime.java:376-395`; it excludes modern `stone_slab` and maps `smooth_stone_slab` to old `stone_slab`. The 26.2 `SlabBlock` shape gate still checks the exact local geometry, state properties, and canonical-full identity before using the historical collection path. This does not claim equivalence for other block families or other block behavior.

## Canonical source identity

All paths in `source-hashes.sha256` were re-hashed against the read-only canonical roots. The ready markers identify 1.13 / `ornithe-feather` and 26.2 / `unobfuscated`. The old registry and slab provider hashes match those already bound to the original closure; the new 26.2 `DataFixers.java`, `BlockRenameFix.java`, `Blocks.java`, and `SlabBlock.java` hashes bind the alias correction and provider check.

No product runtime, build, or tests were run for this source correction.
