# Finding snapshot: unconnected iron-bars collision shape

Snapshot ID: `wiki-iron-bars-unconnected-shape-1.8.9-1.9.4`

Status: source-confirmed bounded collision finding; independent Wiki-lane review pending; no runtime validation performed.

## Candidate and boundary

The official [Glass Pane page](https://minecraft.wiki/w/Glass_Pane?oldid=2728132) is a lead for the pane-shape change; the Iron Bars page fetch was blocked by robots.txt on 2026-10-08 (recorded in [wiki-page-fetch-log.md](wiki-page-fetch-log.md)). Both exact releases register iron bars as a `PaneBlock`, so the pane collision implementation is shared. This snapshot confirms only the state with no connecting neighbors: 1.8.9 emits crossing X/Z strips, while 1.9.4 emits the central post. The difference is bounded to `(1.8.9, 1.9.4]` and does not close the full connected-state or neighbor-predicate comparison.

## Finding and dependency closure

In 1.8.9, the `iron_bars` registration constructs `new PaneBlock(Material.IRON, true)`. `PaneBlock#addCollisions()` computes its four cardinal connection flags from neighboring blocks. When all four are false, its X and Z branches each use the full-length narrow box, producing the two crossing strips.

In 1.9.4, `iron_bars` is likewise registered as `new PaneBlock(Material.IRON, true)`. `PaneBlock#addCollisions()` resolves the virtual connection properties, always adds `SHAPES[0]` (the central 2/16 × 2/16 post), then adds only arms selected by the north/east/south/west flags. With no flags set, only the central post is emitted.

The registration-to-implementation path is closed for this state in both releases: the registered iron-bars block is an instance of `PaneBlock`, and the same class provides the collision producer. This finding concerns the block collision volume used by player movement. It does not infer a block-state change or any visual-only consequence.

`shouldConnectTo()` also changes from the 1.8.9 opaque-block predicate to a 1.9.4 default-state `isCube()` predicate. That may alter which neighboring blocks set the flags, so connected states and their producer dependencies remain open; this snapshot does not enumerate them.

## Exact source evidence and provenance

Paths are rooted at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`. Each Java hash below matches its exact version's `ornithe-feather.sources.sha256` entry.

| Version | Source and ranges | SHA-256 | Manifest row |
|---|---|---|---:|
| 1.8.9 | `net/minecraft/block/Block.java`, `iron_bars` registration line 1040 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | 12 |
| 1.8.9 | `net/minecraft/block/PaneBlock.java`, `addCollisions()` lines 62-91; all-false connections take the two full strips at lines 75-90 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` | 123 |
| 1.9.4 | `net/minecraft/block/Block.java`, `iron_bars` registration line 950 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` | 14 |
| 1.9.4 | `net/minecraft/block/PaneBlock.java`, `SHAPES[0]` lines 25-26 and `addCollisions()` lines 52-70 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` | 137 |

## Applicability and limits

Applicability is an iron-bars block whose four resolved cardinal connection flags are false. The exact first 1.9 snapshot containing the shape change is not established by these release endpoints. Full connected-shape enumeration, the `shouldConnectTo()` neighbor-state delta, iron-bar source comparisons outside this release pair, and runtime collision validation remain open.
