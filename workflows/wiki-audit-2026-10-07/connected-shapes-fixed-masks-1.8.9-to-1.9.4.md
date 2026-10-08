# Fixed neighbor-mask collision shapes: panes, fences, and walls

Snapshot ID: `wiki-connected-shapes-fixed-masks-1.8.9-1.9.4`

Status: source-confirmed bounded 16-mask shape comparison; neighbor-state predicates/providers and whole-family reachability remain open. This is not a complete pair audit or an exact snapshot-cutover claim.

## Wiki leads and retrieval provenance

The [Glass Pane](https://minecraft.wiki/w/Glass_Pane?oldid=2728132) and [Fence](https://minecraft.wiki/w/Fence?oldid=2939254) pages were opened on 2026-10-08; the retrieval log notes stale cached contents for both. [Hitbox](https://minecraft.wiki/w/Hitbox) was opened but no oldid was captured. Direct retrieval of [Iron Bars](https://minecraft.wiki/w/Iron_Bars) and [Wall](https://minecraft.wiki/w/Wall) failed or was blocked, with no page revision ID. The exact outcomes are in [wiki-page-fetch-log.md](wiki-page-fetch-log.md). Those pages only select leads; the shape results below come from exact-release sources.

## Compared evidence and artifact bindings

Sources are the `ornithe-feather` trees for exact releases 1.8.9 and 1.9.4. They are bound to canonical revised artifacts `feather-r1-2026-10-07`: 1.8.9 client JAR SHA-256 `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; 1.9.4 client JAR SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. The corresponding source-manifest SHA-256 values are `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The canonical verification record cited in [feather-r1-findings-snapshot.md](feather-r1-findings-snapshot.md) reports unchanged sources and inputs for these revised snapshots. The original derived JARs remain unavailable; equivalence of revised and original derived JARs is unproven.

| Version | Source file and bounded methods | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/block/PaneBlock.java`, `addCollisions()` lines 62-90 and `shouldConnectTo()` lines 134-141 | `f099cdc306c365b73632fc703f72b61bc6b304cb5df06c43234965f89e28de61` |
| 1.9.4 | `net/minecraft/block/PaneBlock.java`, `SHAPES` lines 27-43, `addCollisions()` lines 53-70, virtual connections lines 105-109 and `shouldConnectTo()` lines 133-140 | `e6e3dd856efb96146634ab8f56c915afbc6116fd51973c25229ef7aa2ce0e5c8` |
| 1.8.9 | `net/minecraft/block/FenceBlock.java`, `addCollisions()` lines 35-82 and `shouldConnectTo()` lines 128-136 | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| 1.9.4 | `net/minecraft/block/FenceBlock.java`, shape/collision constants lines 27-49, `addCollisions()` lines 58-75 and `shouldConnectTo()` lines 120-129 | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |
| 1.8.9 | `net/minecraft/block/WallBlock.java`, `updateShape()`/`getCollisionShape()` lines 67-110 and `shouldConnectTo()` lines 113-121 | `e1fca4e4dbb7736d17116e4dee92064d35f7bf135da374dae0615e89216aca5e` |
| 1.9.4 | `net/minecraft/block/WallBlock.java`, shape/collision constants lines 28-63, collision lookup lines 83-94, neighbor predicate lines 137-147 and virtual properties lines 177-184 | `af01294fe9391b77e54fcc30696f8b80754fc5b8f25f3e933aa3c94a2f101640` |
| 1.8.9 | `net/minecraft/util/math/Direction.java`, horizontal direction IDs lines 14-17 and `getIdHorizontal()` lines 44-46 | `ede4dc29795263853c048e569f24e74407255767c5e02349a90895ac5b08e544` |
| 1.9.4 | `net/minecraft/util/math/Direction.java`, same horizontal direction IDs/accessor | `10bd074fa46e27658e7109836fe5456fa1f2017a15401f28fc12900d942f02ef` |

Every cited source-file digest matches its version's immutable revised source-manifest row. Iron bars are registered as `PaneBlock` in both versions; see the registration identities already recorded in the unconnected iron-bars finding in [minecraft-wiki.md](minecraft-wiki.md). The generic PaneBlock shape matrix therefore applies to glass panes and iron bars when their resulting directional mask is held fixed.

## Fixed directional-mask results

Each case fixes the four booleans `N`, `E`, `S`, and `W` to the same values in both releases, then compares the union of collision AABBs expressed on a 1/16-block grid. This is a static derivation from the cited source constants and branch conditions; it is not game execution. The source direction-ID mapping was checked on both sides.

| Block family | Fixed masks compared | Equal geometry | Changed geometry | Result |
|---|---:|---:|---:|---|
| PaneBlock (glass panes and iron bars) | 16 | 7 | 9 | Changed for no connections; each single arm; and each adjacent two-arm corner. Equal for opposite pairs and all three-/four-arm masks. |
| FenceBlock | 16 | 16 | 0 | Fixed-mask collision union is equal, including the central post and 1.5-block arms. |
| WallBlock | 16 | 16 | 0 | Fixed-mask collision box is equal, including the narrowed straight N/S and E/W rails and their 1.5-block collision height. |

For PaneBlock, the changed masks are `none`, `N`, `E`, `S`, `W`, `NE`, `ES`, `SW`, and `WN`. 1.8.9's legacy routine emits crossing strips for `none`, while 1.9.4 uses only the central post; with one arm, the connected side's 1.8.9 half-arm stops at the center plane while the 1.9.4 central post extends the collision union by 1/16. Adjacent two-arm corners also gain the filled central corner. The seven equal masks are `NS`, `EW`, `NES`, `NEW`, `NSW`, `ESW`, and `NESW`.

FenceBlock's legacy routine builds overlapping axis boxes from the mask; 1.9.4 builds a central post plus directional arms. WallBlock changes from a dynamic box to precomputed state-indexed collision boxes, but the mask-conditioned union is identical across all 16 cases. This is shape geometry only; it does not declare the connection resolver or the whole neighbor-state behavior unchanged.

## Required follow-up and limits

The resulting masks are not independent of the world. PaneBlock changes its neighbor test from `Block.isOpaque()` in 1.8.9 to `block.defaultState().isCube()` in 1.9.4. FenceBlock and WallBlock change the solid-cube test from `Block.isCube()` to the neighbor `BlockState.isCube()`. Consequently, a fixed-mask equality does not prove that the same adjacent block state yields the same mask. The End Portal Frame/fence case is a separate bounded finding. Enumerate available neighbor block/state providers, each family's same-block/gate/glass exceptions, and relevant adjacent-provider effects before closing those connection inventories. Wall `UP` is derived separately and the cited 1.9.4 collision lookup indexes the four horizontal properties only; the `UP` shape-provider path remains to be checked with collision consumers.

This matrix covers only 1.8.9→1.9.4. It does not close later wall-shape revisions, other exact boundaries, block registrations across all versions, adjacent-block providers, or player collision-query interactions. Runtime validation was not performed.
