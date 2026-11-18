# `leaves2` nonfalling `Material.LEAVES` provider check

## Result

No independent `leaves2` movement difference found for the bounded operation. Across all 24 assigned stable releases, block ID 161 remains `leaves2` backed by `Leaves2Block`; its metadata decodes only Acacia and Dark Oak variants, and the item path round-trips those species. `Leaves2Block` inherits the same `Material.LEAVES` behavior as Oak leaves. Its material blocks movement and is not replaceable; it has no movement or falling override. Therefore it is the same kind of horizontal-flow neighbor for the already accepted dry east-leaf `LiquidBlock.getFlow` case. Reuse the supplied Oak geometry and player query; this review adds no caller or operation.

## Exact inputs and integrity

Host/environment: Windows 11, PowerShell. Primary source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather/`.

Reviewed releases, in order: `1.8`, `1.8.1`–`1.8.9`, `1.9`, `1.9.1`–`1.9.4`, `1.10`, `1.10.1`, `1.10.2`, `1.11`, `1.11.1`, `1.11.2`, `1.12`, `1.12.1`, `1.12.2` (24 total). Each hidden mapping-local `.decompile-provenance.json` records its exact version's Feather Gen2 `+build.2` artifact for 17 releases. For the remaining seven, the version-root `ornithe-feather.ready.json` / `ornithe-feather.provenance.json` records the exact version, `ornithe-feather`, and matching `feather-gen2-<version>+build.2` JAR/Tiny artifacts; each ready marker's source-manifest SHA-256 was checked against the adjacent `ornithe-feather.sources.sha256`. All 24 relevant source-file bytes match their recorded manifest digest. The 17 manifest-bearing source digests were read from the `sources` object; for the seven ready trees, digests were checked in the source list.

| Release(s) | Exact mapping record |
|---|---|
| `1.8`–`1.8.8` | mapping-local `.decompile-provenance.json`, Feather Gen2 `<version>+build.2` |
| `1.8.9` | ready/provenance pair, Feather Gen2 `1.8.9+build.2` |
| `1.9`–`1.9.3` | mapping-local `.decompile-provenance.json`, Feather Gen2 `<version>+build.2` |
| `1.9.4` | ready/provenance pair, Feather Gen2 `1.9.4+build.2` |
| `1.10`, `1.10.1` | mapping-local `.decompile-provenance.json`, Feather Gen2 `<version>+build.2` |
| `1.10.2` | ready/provenance pair, Feather Gen2 `1.10.2+build.2` |
| `1.11`, `1.11.1`, `1.11.2` | ready/provenance pairs, matching Feather Gen2 `+build.2` JAR/Tiny |
| `1.12`, `1.12.1` | mapping-local `.decompile-provenance.json`, Feather Gen2 `<version>+build.2` |
| `1.12.2` | ready/provenance pair, Feather Gen2 `1.12.2+build.2` |

The digests below are SHA-256 of the exact source files and were cross-checked against each release's provenance/source manifest. Version groups indicate identical full-file bytes in that class; a source hash change by itself is not a behavior claim.

| Source file | Releases : SHA-256 |
|---|---|
| `block/Leaves2Block.java` | `1.8`–`1.8.1`: `c7fd47fc9c72a0924cf0ba914d3c2455999c472505b2717aa67a638c1ea7ede4`; `1.8.2`–`1.8.9`: `7c38aca6c5fb3902eb1724f5693cc6c3d976bea86f3833fac56a953b65e5e208`; `1.9`–`1.9.2`: `49821b06d59396225cea92871961c600ebf650be4da311dd9be5817f2154b8c0`; `1.9.3`–`1.10.2`: `b3b8d4a122ce74e61d5d7c21de6b63d64a9e85a324cbc9d267130ae95a310902`; `1.11`–`1.11.2`: `cdca3efac3484dce682c22e892b33212873d711773f132972d6528e8a0e57620`; `1.12`–`1.12.2`: `54be11551779ba540ef2374e831a7c49d37157e041b31602ccd0a0636b8d5bf4` |
| `block/AbstractLeavesBlock.java` | `1.8`: `f4903770cff736b76f7fab700fd4e9af7508f8f4381e94a7b66f3a55c4a3ed19`; `1.8.1`: `97a6b613867d49361db2af31830f4a73b774fdb00a213e6a2412188518ffd043`; `1.8.2`–`1.8.9`: `5f6d40d382f6622757437de1ce9bae916a6cfe9bfbbcf2b46817447e5074614e`; `1.9`–`1.9.2`: `46a0ffa3231a71b5dd850a1e35bacc8ba5a601b885a0fa4b140c0fe2c4e9c547`; `1.9.3`–`1.9.4`: `c301c1dc31e5dccae557607c7ec546741ec6b862fc25f5bf3d6ad73fea5198f6`; `1.10`–`1.10.2`: `efb1b4ebc5e36f859329b4207217b057782c65b2ac134f5bcffc9b4d35b3cec9`; `1.11`–`1.12.2`: `e57e9a22e6229305be3d6feca216c18f2298d465d476c3ed51bb43a4bcf13c3a` |
| `block/material/Material.java` | `1.8`: `a27bcc70e2712f150d8428773f8c2aafe98af9100a4fd05653057044d7d248a9`; `1.8.1`: `c05f393fe2b5e77b96dac85250751bed03cad2be6377fc5c10209904c1f185bf`; `1.8.2`–`1.8.9`: `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`; `1.9`–`1.9.4`: `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19`; `1.10`–`1.12.2`: `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` |
| `item/LeavesItem.java` | `1.8`–`1.8.8`: `6618befe3a5ccf8531fdf36a2ff5b82ff2a472d2f22fee85b92c97ece0564fd0`; `1.8.9`–`1.12.2`: `801dacf1361da20143c628261f1e6c80a3a30f4f530b892d6429dd938d162be4` |
| `block/Block.java`, `item/Item.java` | Both files' hashes were checked against all 24 exact source records; registration statements were confirmed in each release. Their full hash lists are omitted because unrelated registry edits change them across the interval. |

## Compared source behavior

Exact paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- `net/minecraft/block/Block.java`: registers numeric ID `161`, key `leaves2`, with a new `Leaves2Block` (1.8 line 1075; 1.12.2 line 1129). `Blocks.LEAVES2` resolves this key in both endpoints. `net/minecraft/item/Item.java` separately registers `new LeavesItem(Blocks.LEAVES2)` (1.8 line 309; 1.12.2 line 411). All 24 releases retain both registrations.
- `net/minecraft/block/Leaves2Block.java`: extends `AbstractLeavesBlock` (1.8 line 17; 1.12.2 line 19). The default variant is Acacia; `getVariant(metadata)` applies `(metadata & 3) + 4`, selecting enum IDs 4 (Acacia) and 5 (Dark Oak) (1.8 line 75; 1.12.2 line 82). `getStateFromMetadata` reads decay flags from bits 4 and 8; `getMetadataFromState` writes species as `variantId - 4` and the same decay flags. The formulas and Acacia/Dark Oak scope hold in every release.
- `net/minecraft/item/LeavesItem.java`: `getBlockMetadata(metadata)` returns `metadata | 4` (1.8 and 1.12.2 line 17), preserving the stack's low species bits while setting the non-decayable bit on placement. Item metadata 0/1 therefore places Acacia/Dark Oak, and state-to-metadata persists that species. `Block`'s world-state metadata serialization routes through `getMetadataFromState` / `getStateFromMetadata` (see 1.8 `Block.java` lines 82, 92; 1.12.2 lines 81, 91).
- `net/minecraft/block/AbstractLeavesBlock.java`: constructor passes `Material.LEAVES` to its superclass (1.8 line 26; 1.12.2 line 25). No `Leaves2Block` override changes material, `blocksMovement`, replaceability, or falling behavior; its direct methods concern variants and drops.
- `net/minecraft/block/material/Material.java`: `LEAVES` is an ordinary `new Material(...).setFlammable().setTranslucent().setDestroyOnPistonMove()` and does not call `setReplaceable()` (1.8 line 13; 1.12.2 line 15). Ordinary `Material.blocksMovement()` returns true (1.8 line 64; 1.12.2 line 72); `isReplaceable()` returns the unset `replaceable` field (1.8 line 92; 1.12.2 line 100). Thus inherited `Material.LEAVES.blocksMovement()` is true and `isReplaceable()` false in all assigned releases. `Material` implementation changes across early versions were checked at the declaration and default methods; no `LEAVES` exception appears.

The supplied accepted Oak-leaves finding establishes the dry horizontal-flow geometry and player-facing query. Applying the unchanged predicate to `leaves2` yields the same east-sample rejection; no distinct `leaves2` result follows.

## Scope and gaps

This is a source-only sibling check, not a complete pair/tick coverage audit. It does not re-audit the accepted Oak geometry/query, inspect other leaf registrations/species, or examine other liquid callers, wall-face flow, climbing, falling, or body-water behavior. No product, history, shared research, or decompiler source was changed; no tests, build, decompile, or runtime was run.
