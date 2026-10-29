# Independent review: 1.8.9 chest ID 54 provider closure

**Verdict: ACCEPT**

Reviewed closure `dda-189-chest-54-current-provider/closure.md` at SHA-256 `4151BF920F01C46D6144720F04C06D7F1A35535A1048F69E5777A93ED08447A9` and its cited-source hash manifest. The closure hash and all listed canonical-source hashes match the files at the stated paths. The cited registry projection also matches its recorded SHA-256, and its ID 54 row identifies `chest` / `ChestBlock` while leaving cross-version state conversion unproven.

The 1.8.9 source supports the stated registration and decode: `Block.java:942` registers ID 54 with one `ChestBlock(0)` instance; the chest state has only `FACING`; and `getStateFromMetadata` uses `Direction.byId` then replaces vertical directions with NORTH. The reported aliases for stored metadata nibbles 0..15 follow the six-entry `ALL` direction array and `abs(id % ALL.length)`.

The historical mutable-bounds account is supported. `ChestBlock.updateShape` tests NORTH, SOUTH, WEST, EAST in short-circuit order using block-instance identity and writes the listed local bounds (the constructor supplies the no-neighbor bounds). `Block.setShape` stores the six values on the block object; `getCollisionShape` reads them without consulting its state argument; and `rayTrace` calls `updateShape` before reading those fields. This supports shared per-block-instance bounds and ray-trace refresh, without implying that collision-shape queries refresh them.

The current-source description is supported. `Blocks.CHEST` is registered with `BlockItemIds.CHEST`, whose key is `minecraft:chest`; its state contains `FACING`, `TYPE`, and `WATERLOGGED`, with NORTH/SINGLE/false defaults. `getShape` selects the single shape by `TYPE`, or a directional half shape using `TYPE` and `FACING`; the waterlogged property is not read there. The shape constructors and horizontal rotations support the stated 14/16 centered cross-section, 14/16 height, and 15/16 directional half extent. The default collision provider delegates to `state.getShape` when collision is enabled, and the chest has no override of that provider.

The equivalence boundary is appropriately limited. The shape-source correspondence is not an ID or metadata conversion: the old state has no chest type, old bounds depend on the first same-instance neighbor found by a fixed four-direction scan, and the current half shape depends on explicit `TYPE` plus `FACING`. Current neighbor callbacks validate compatible pair state and direction rather than reproducing the old scan. The closure does not claim general historical equivalence or infer old state from the current registry key.

No unsupported claim or correction was found in the reviewed scope.
