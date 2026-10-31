# Old block shape slice: 1.20.4 → 1.20.5 (1.20.6 context)

## Disposition

No concrete collision/support/movement-consumed block-shape delta was found among the 1.20.4-era registered block classes examined. No canonical finding draft is warranted. The changed-source path dispositions and inventory hashes are in `paired-path-dispositions.csv`.

The 1.20.5 shape-hook refactor changes `BlockBehaviour` hooks from public to protected while preserving their bodies; `BlockBehaviour.BlockStateBase` continues delegating state shape calls to the block (`BlockBehaviour.java`, 1.20.4 lines 329–350, 540–671; 1.20.5 lines 305–322, 515–646). `BlockState.java` is a thin state subclass on both sides. All shared block and piston shape-hook bodies and `VoxelShape` field initializers in the changed paired path inventory were compared. The sole shape initializer refactor found is Hopper: `Hopper.INSIDE` moves to `HopperBlock.INSIDE` with the same box coordinates (1.20.4 `Hopper.java` lines 14–19; `HopperBlock.java` lines 38–55; 1.20.5 `HopperBlock.java` lines 38–57). `getShape` retains the same facing switch and shape return. Hopper's interaction provider is not consumed by player movement.

The old enchanting-table class was renamed: 1.20.4 `EnchantmentTableBlock` (`EnchantmentTableBlock.java`, lines 34–64) and 1.20.5 `EnchantingTableBlock` (`EnchantingTableBlock.java`, lines 31–61) use the same 0–12-high shape and return it. `Blocks.ENCHANTING_TABLE` remains registered under the same registry key (1.20.4 `Blocks.java` lines 2762–2770; 1.20.5 lines 2765–2773). `VaultBlock` and `HeavyCoreBlock` are post-1.20.4 additions and excluded. No `noCollision` registration change was found; observed registrations differences in `Blocks.java` do not alter player collision/support geometry.

The changed block-path inventory shows 1.20.5→1.20.6 source hashes identical for these path slices. In particular, `BlockBehaviour.java`, `BlockState.java`, `Block.java`, `Blocks.java`, `HopperBlock.java`, and `PistonBaseBlock.java` are unchanged across that context edge.

## Exact source identities and hashes

- 1.20.4 Mojmap ready identity: `versionId=1.20.4`; source manifest SHA-256 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; client SHA-256 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`.
- 1.20.5 Mojmap ready identity: `versionId=1.20.5`; source manifest SHA-256 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; client SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`.
- 1.20.6 Mojmap ready identity: `versionId=1.20.6`; source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`.

Ready markers: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.4,1.20.5,1.20.6}/mojmap.ready.json`. The source pair inventory used is `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv`; `paired-path-dispositions.csv` carries each checked path's three source hashes and status.

## Coverage and remaining dependency

Covered changed Java paths directly under `net/minecraft/world/level/block/`, all `block/piston/*.java`, `BlockBehaviour.java`, `BlockState.java`, `Block.java`, `Blocks.java`, and `block/entity/Hopper.java`; unchanged 1.20.4→1.20.5 paths are outside the changed-path inventory and therefore were not re-diffed. Existing registration was checked in `Blocks.java` for the renamed block and inspected static registrations; the source trees contain no JSON or resource files. Resource/tag evidence for neighbor/state gates (for example wall, fence, pane, redstone-wire connectivity) is unavailable in these source-only decompilations, so tag membership/resource-side gate changes remain open for pair closure. This report is a bounded shape slice only and makes no pair-freeze claim.
