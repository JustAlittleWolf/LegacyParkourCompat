# Correction supplement 1: farmland `onAdded` caller

Applies to: `farmland-landing-position-response.md`, SHA-256 `5e2ba2ae0d04f3d8c2d44055132c267dc864002583f79c694ab7ce6011e58912`. This supplement preserves that report unchanged.

## Added source path

In both 1.11.1 and 1.11.2, `FarmlandBlock.onAdded(World, BlockPos, BlockState)` first calls `super.onAdded`, then checks whether the block above is solid, and invokes `setDirt(world, pos)` when true (`net/minecraft/block/FarmlandBlock.java`, lines 104–109). The method and source file are byte-identical in these two releases (SHA-256 `09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8`). This is an additional replacement caller beyond the dry-out, landing and `neighborChanged` callers enumerated in the report.

The caller is server-side in this path: `WorldChunk.setBlockState` invokes the new block's `onAdded` only under `!this.world.isClient && block2 != block` (`net/minecraft/world/chunk/WorldChunk.java`, lines 464–466). The 1.11.1 and 1.11.2 files are byte-identical (SHA-256 `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f`). For farmland newly installed below a solid block, that server callback therefore reaches `setDirt`, whose dirt replacement and entity reposition sequence is the one documented in the report.

## Effect on conclusion

This adds a server-only production path for the 1.11.1 and 1.11.2 entity lift when farmland is added beneath a solid block. It does not change the equal-payload client conclusion: the `WorldChunk` gate suppresses this `onAdded` callback on the logical client, and the client block-update handler still only installs the received state. Equal block-state or player-position packet payloads therefore do not gain a direct, release-specific local-player reposition from this caller. A later position correction remains conditional. This supplement makes no trajectory/parity or pair-freeze claim.
