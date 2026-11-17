# Independent audit: 1.11.2 → 1.12 moving-block client installation order

Verdict: **ACCEPT** for the bounded report and claim below.

## Bound inputs

- Audited report: `dependencies/moving-block-client-install-order.md`, SHA-256 `e38acd2dc4a2927d0a099a0131894460a5eb637d675a215f7c404391396d33fe`.
- Exact source pair: `decompiled_minecraft/1.11.2/ornithe-feather/` → `decompiled_minecraft/1.12/ornithe-feather/`, as bound by the report. I checked its inspected source hashes against the current files; all listed hashes match. `MovingBlockEntity.java` at both endpoints is `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`.
- Context report: `dependencies/moving-block-provider-payload.md`, SHA-256 `46ef94ac4acb8ac3f0f05d675059f5dd4e8f4821c1c62633e6d78bd7ff9da694`; its relevant audit verdict is `dependencies/moving-block-provider-payload-audit/verdict.md`. Pair coverage identifies this source pair and remains partial.

## Audit result

The source supports the report's bounded conclusion. In both releases, the client-thread block-event handler calls `World.addBlockEvent`, which directly dispatches the current state's event. The piston producer writes `MOVING_BLOCK` and immediately installs the constructed `MovingBlockEntity` for its source and moved-block positions. These adjacent synchronous calls contain no intervening player tick or collision query. The compared operations and order match at both endpoints.

The report also correctly separates this normal producer path from a conditional state-only restoration path. Chunk packet handling applies section states, then looks up each NBT position and reads NBT only into a non-null existing block entity. Immediate lookup cannot construct a moving-block entity because `MovingBlock.createBlockEntity` returns null. Therefore, for a freshly received client chunk whose moving-block state has no existing entity, the NBT payload does not install one. In an ordinary player collision query, `MovingBlock.addCollisions` emits dynamic boxes only when the lookup returns a `MovingBlockEntity`; a moving-block state with no such entity emits no dynamic moving box. The full-block `getShape` fallback does not alter that `addCollisions` gate.

Relevant source paths were checked at both endpoints: `ClientPlayNetworkHandler.handleBlockEvent` and `handleWorldChunk`; `World.addBlockEvent`; `PistonBaseBlock.doEvent`/`move`; `WorldChunk.update`, `setBlockState`, `createBlockEntity`, and `getBlockEntity`; `MovingBlock.createBlockEntity` and `addCollisions`; plus `Entity.move` → `World.getCollisions`. Packet/chunk and block-entity sources cited as identical were byte-identical. The cited operations in the changed client-handler, piston, world, entity, and moving-block sources preserve the described gates and order.

No endpoint difference is established by this lifecycle slice. The absent-entity condition is a bounded client restoration observation, not a claim that the ordinary piston event path lacks its entity or that a particular trajectory changes. This verdict does not accept a movement difference, establish complete pair coverage, or freeze the pair.