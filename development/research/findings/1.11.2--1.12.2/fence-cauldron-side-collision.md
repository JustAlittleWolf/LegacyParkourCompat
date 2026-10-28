# Fence adjacency to a cauldron has no collision difference

The earlier positive claim is disproved by the complete connection predicate. Exact Ornithe Feather sources for 1.11.2, 1.12 and 1.12.2 produce no connection to a neighboring cauldron.

In 1.11.2, `net.minecraft.block.FenceBlock.shouldConnectTo` requires solid-blocking material and a cube for a non-fence/non-gate neighbor; `CauldronBlock.isCube` returns false.

In 1.12 and 1.12.2, `FenceBlock.shouldConnectTo` asks for the horizontal face shape, but its static block helper is an exclusion predicate. `Block.isExceptionForAttachment` delegates to `Block.isExceptionForPlacement`, whose explicit CAULDRON entry excludes the cauldron even though `CauldronBlock.getFaceShape` returns SOLID. The separate MIDDLE_POLE/gate branch also fails for the cauldron.

The fence collision collector retains its center post and appends rails only for resolved directional connections. Consequently this neighbor contributes no rail in any of the checked versions. Cauldron collision components and the movement collector do not supply the claimed divergence. This disproves this particular boundary claim; it does not establish general fence or cauldron parity.

The accepted negative source closure and exact source references are retained in `development/research/source-evidence/20261028/fence-cauldron-negative.md`. The earlier affirmative draft remains in Git history.
