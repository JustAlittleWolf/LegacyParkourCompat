# Pane connection to an Ice block

Snapshot ID: `wiki-pane-ice-neighbor-1.8.9-1.9.4`

Status: source-confirmed single-provider witness for a changed connected-pane collision result; independent Wiki-lane review pending. This does not close the pane/bar provider inventory or the source pair.

## Result

An Ice block immediately east of a pane is not a connecting neighbor in 1.8.9, but is a connecting neighbor in 1.9.4. Both releases register Ice as block ID 79 and retain it as a vanilla block in scope.

In 1.8.9, `PaneBlock.shouldConnectTo(Block)` first tests `block.isOpaque()`. `IceBlock` extends `TransparentBlock`, whose `isSolidRender()` returns false. The `Block` constructor initializes `opaqueCube` from `isSolidRender()`, and `Block.isOpaque()` returns that field. Ice therefore fails the opaque test; it is not one of the later explicit pane/glass exceptions. With Ice as the pane's only adjacent block, the resulting pane mask has no connected directions.

In 1.9.4, the pane tests `block.defaultState().isCube()`. `Block.isCube(BlockState)` defaults to true. `IceBlock` still extends `TransparentBlock`; neither class overrides `isCube(BlockState)`. `TransparentBlock.isSolidRender(BlockState) == false` does not change the cube default. `StateDefinition.BlockStateImpl.isCube()` delegates to the block method. Ice consequently supplies an east connection in this case.

The fixed-mask comparison in [connected-shapes-fixed-masks-1.8.9-to-1.9.4.md](connected-shapes-fixed-masks-1.8.9-to-1.9.4.md) shows that the unconnected pane geometry and the east-only geometry differ between releases. Because the same registered adjacent Ice block produces different masks, this is a source-confirmed world-reachable pane collision-shape difference within `(1.8.9, 1.9.4]`, not only a predicate difference. The independent review has not yet accepted the finding.

## Exact source and artifact bindings

Sources are from the immutable revised `feather-r1-2026-10-07` artifacts. The corresponding client JAR SHA-256 values are 1.8.9 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` and 1.9.4 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. Source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`, respectively. The original derived JARs are unavailable, so revised/original derived-artifact equivalence remains unproven.

| Version | Exact source slice | SHA-256 |
|---|---|---|
| 1.8.9 | `Block.java`: Ice registration line 1010; `opaqueCube` initialization lines 198-204; `isOpaque()` lines 154-155 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `IceBlock.java`: extends `TransparentBlock`, constructor lines 17-19 | `dae1d397d40f5167af82fbde78174f259ade1415c96e4d34c8c31c72bdb40a3a` |
| 1.8.9 | `TransparentBlock.java`: `isSolidRender()` lines 22-25 | `10780460a36e5e73d61381cd91b05a988e30a67e71fc182875a52bdcf387d244` |
| 1.8.9 | `PaneBlock.java`: east-neighbor lookup in `addCollisions()` lines 62-66; `shouldConnectTo()` lines 134-141 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.9.4 | `Block.java`: Ice registration line 911; default `isCube(BlockState)` lines 223-225 | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.4 | `IceBlock.java`: extends `TransparentBlock`, constructor lines 20-25; no `isCube(BlockState)` override | `23502fdd9ae0d32687fdd9fed4bf272bede481ad00e371c6fc7d9a76f6d87ba4` |
| 1.9.4 | `TransparentBlock.java`: `isSolidRender(BlockState)` lines 22-25 | `b688c54180a7f67fad3a4a9a2e8e50d3b0d247c382800039f5d7c3d08ea0288b` |
| 1.9.4 | `PaneBlock.java`: virtual neighbor resolution lines 105-109; `shouldConnectTo()` lines 133-140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.9.4 | `StateDefinition.java`: resolved `BlockStateImpl.isCube()` delegation lines 267-270 | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |

No game, runtime, or collision test was run. Other pane providers, glass/pane exceptions, fence and wall provider states, and downstream player collision consumers remain outside this bounded witness.
