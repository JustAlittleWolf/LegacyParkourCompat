# Dry wooden fence sibling source check

Scope: spruce, birch, jungle, acacia and dark-oak ordinary fences only, at exact 1.11.2 and 1.12.0 Ornithe Feather gen2 build.2. This closes the other-fence-variant dependency of `falling-water-oak-fence-face.md`; oak's accepted boundary and caller trace are reused without retracing.

## Exact source identity

- **1.11.2:** `decompiled_minecraft/1.11.2/ornithe-feather`, readiness marker `ornithe-feather.ready.json` says `versionId=1.11.2`, `mapping=ornithe-feather`, status `ready`, and mapping jar `feather-gen2-1.11.2+build.2`. Marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; `ornithe-feather.sources.sha256` SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` (also the ready marker's source manifest digest).
- **1.12.0:** `decompiled_minecraft/1.12/ornithe-feather`, provenance pins `feather-gen2-1.12+build.2` and exact 1.12 client/version inputs. `.decompile-provenance.json` SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; each reviewed Java file hash below matches its source entry in that provenance manifest.
- Both are the canonical read-only sources. No generation or source-tree writes were performed.

## Sibling registration disposition

`net/minecraft/block/Block.java` registers each sibling as `new FenceBlock(Material.WOOD, PlanksBlock.Variant.<wood>.getColor())`, at IDs 188–192, in both releases. `net/minecraft/block/Blocks.java` resolves the matching named registry entries. The color argument is the only wood-specific constructor input; the shared `FenceBlock` class supplies the movement-relevant face behavior. Thus every named sibling has the same dry default state and material/face-cue path as the accepted oak witness. No other fence family is included.

In 1.11.2, the accepted oak boundary establishes that `LiquidBlock.isFaceSolid` reaches the base material-solidity predicate for a horizontal non-ice, non-same-material neighbor. Since all five siblings are registered with solid `Material.WOOD`, every sibling qualifies on that horizontal query just as oak does.

In 1.12.0, the exact `FenceBlock.getFaceShape` implementation returns `MIDDLE_POLE` for every horizontal face without consulting state, connection properties, material or color. `LiquidBlock.isFaceSolid` requires `FaceShape.SOLID` after its earlier exclusions, so all five dry siblings are rejected on a horizontal query exactly as oak is.

The reused exact caller in `LiquidBlock.getFlow` has the same `LEVEL >= 8` gate and horizontal iteration in both releases. For each direction it asks `isFaceSolid` of the neighbor and then the cell above that neighbor, using that same horizontal face. Therefore the sibling cue reaches the same falling-level branch; this closes sibling applicability for those two side tests. It does not establish full flow-scan equivalence or runtime trajectories.

## Source hashes

SHA-256 is over each source file's bytes. All entries listed in the 1.11.2 source manifest match current files; all 1.12.0 entries match `.decompile-provenance.json`.

| Source | 1.11.2 | 1.12.0 |
|---|---|---|
| `net/minecraft/block/LiquidBlock.java` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| `net/minecraft/block/FenceBlock.java` | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| `net/minecraft/block/Block.java` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| `net/minecraft/block/Blocks.java` | `69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| `net/minecraft/block/material/Material.java` | `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` | `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` |

## Per-variant terminal outcomes

- **SPRUCE_FENCE — COMPLETED:** registered as `FenceBlock(Material.WOOD, SPRUCE color)` at registry ID 188 in both releases; same accepted 1.11.2 cue and rejected 1.12.0 horizontal face.
- **BIRCH_FENCE — COMPLETED:** `FenceBlock(Material.WOOD, BIRCH color)`, ID 189 in both; same cue result.
- **JUNGLE_FENCE — COMPLETED:** `FenceBlock(Material.WOOD, JUNGLE color)`, ID 190 in both; same cue result.
- **DARK_OAK_FENCE — COMPLETED:** `FenceBlock(Material.WOOD, DARK_OAK color)`, ID 191 in both; same cue result.
- **ACACIA_FENCE — COMPLETED:** `FenceBlock(Material.WOOD, ACACIA color)`, ID 192 in both; same cue result.

No glass, masonry/nether fences, gates, modern variants, other movement operations, broad world-scan claims, or uninspected releases are covered.
