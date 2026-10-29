# Rebind supplement: farmland `onAdded` caller correction

Correction audited: `.native-source/20261028/frontier-c/1102-1112-farmland-position-response/farmland-landing-position-response-correction-1.md`, SHA-256 `08aa9bad4bc6a39cd4b896a99ae2d4ffae2e2cd0a7d6d5cebde823bd2dd8fefe`. Original independent audit preserved unchanged at `audit.md`, SHA-256 `FB2C2776DEFEA6B831E4916710B3C39E1B983ACEF374096D42B73EEBFA2DD54A`.

I independently verified the correction against `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`:

- `FarmlandBlock.java` has no `onAdded` override in 1.10.2 or 1.11. In 1.11.1 and 1.11.2, lines 105–109 call `super.onAdded`, test whether the block above is solid, and then call `setDirt(world, pos)`. The complete 1.11.1/1.11.2 source files are identical (SHA-256 `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8`).
- `WorldChunk.setBlockState`, lines 464–466, invokes `block.onAdded` only under `!this.world.isClient && block2 != block`. 1.11.1 and 1.11.2 files are identical (SHA-256 `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f`). The 1.10.2 source hash is `dcfcda41f28cc205f17a547c69efdf70aede8f4770a43101d20ead08e8ed86b5`; 1.11 uses the same `WorldChunk.java` hash as 1.11.1/1.11.2. All four actual file hashes match their release source-manifest entries.
- The helper body invoked by this added caller is the already audited `setDirt`: install dirt, obtain and move its collision shape, query intersecting entities, then set each entity's Y to `box.maxY`. The caller therefore adds a server-side entity lift when farmland is installed under a solid block in 1.11.1/1.11.2.

This correction does not affect the prior client-response conclusion. The client block-update handler installs the received state through `ClientWorld.setBlockStateFromPacket`; the client-side `WorldChunk` path does not call `onAdded`. A received player-position packet remains a separate path through the identical local-player position handler. This source audit establishes the callback gate and call path only; it makes no runtime or parity claim.
