# 1.11.2 → 1.12 moving-block client install and restoration order

## Binding and scope

This is a bounded source-only follow-up to `moving-block-provider-payload.md` (SHA-256 `46ef94ac4acb8ac3f0f05d675059f5dd4e8f4821c1c62633e6d78bd7ff9da694`). It compares only client-side moving-block state/block-entity installation and restoration, block-entity ticking, and the ordinary player collision query timing. It does not assess server movement packet responses, provider inventory, complete pair coverage, or freeze.

Exact inputs are `decompiled_minecraft/1.11.2/ornithe-feather/` → `decompiled_minecraft/1.12/ornithe-feather/`, as bound by pair coverage SHA-256 `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`; 1.11.2 ready marker `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` and source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12 mapping-local provenance `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. The current independent full-pair audit remains `PARTIAL` (SHA-256 `82145bec322ff77fecf967fa5cf0f440f11ecb4b07dbfd499f10b5ca227df80b`). Permitted response evidence reviewed for context: piston/portal response r2 SHA-256 `84f5160938a961bcd3de8bab9f066b9909ec1162bf4055de37ca6f53224d920b`; its accepted correction audit SHA-256 `127b73971637681565c1ce2d9a3be8ce8c6b70fdee7db3053cbc20a0812ad175`.

## Compared source identities

Paths are relative to each release's `ornithe-feather` root. SHA-256 digests are exact inspected source bytes.

| Source | 1.11.2 | 1.12 |
| --- | --- | --- |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | `633c59b54bdbe79a2430b59215033206c886249b154044ca95965b375ba3f8e6` |
| `net/minecraft/client/world/ClientWorld.java` | `996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f` | same |
| `net/minecraft/network/packet/s2c/play/WorldChunkS2CPacket.java` | `650dcea2255c0826493fa103f4ee0d8350888163661fe33ab7b44aa8998d3a86` | same |
| `net/minecraft/network/packet/s2c/play/BlockUpdateS2CPacket.java` | `9ad5b67f36b6055186896a30f3dc7516c6d078913d90579b9d34296029e0997d` | same |
| `net/minecraft/network/packet/s2c/play/BlocksUpdateS2CPacket.java` | `08a63335df4e4871854b565ed558f51cedac20f0a789a234d06bdefe8b08d94d` | same |
| `net/minecraft/network/packet/s2c/play/BlockEntityUpdateS2CPacket.java` | `980824a4c30f7bde2609b19fc68e2294a900af157adf8415c7e9f50408e53745` | same |
| `net/minecraft/world/chunk/WorldChunk.java` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` | same |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` | same |
| `net/minecraft/block/MovingBlock.java` | `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18` | `2bcea815334849934eaa3283eedad3a7a27f7b77503a8789aae1479af725df5c4` |
| `net/minecraft/block/PistonBaseBlock.java` | `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64` | `a0b929dec717a33355b291c6483430288a6a0edb9a4f3338b7d708b282898626` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |

The client packet-handler class and `World.java` have unrelated endpoint edits; the specific operations below were compared at their cited locations. The packet data classes, client world adapter, chunk implementation, and moving-block entity bytes are identical.

## Normal piston-event installation is synchronous and unchanged

- `ClientPlayNetworkHandler.handleBlockEvent` ensures execution on the client thread, then calls `world.addBlockEvent` (1.11.2: 1079–1082; 1.12: 1088–1091). `World.addBlockEvent` immediately dispatches the current state’s `doEvent` (1.11.2: 2553–2555; 1.12: 2564–2566); it does not defer the piston operation to a later world tick.
- `PistonBaseBlock.doEvent` runs the same client path at both endpoints (150–186). For extension, `move` writes each `MOVING_BLOCK` state and immediately calls `world.setBlockEntity` with a constructed `MovingBlockEntity` (1.11.2: 298–318; 1.12: 299–319). Retraction likewise writes the moving state then installs the entity (1.11.2/1.12: 178–186). There is no player tick or collision query interleaved between those statements on the client thread.
- Individual block and multi-block packets go through `ClientWorld.setBlockStateFromPacket` (1.11.2/1.12: 237 onward) into `World.setBlockState` and `WorldChunk.setBlockState`. When replacing a different old block-entity-provider block, the client removes its old entity; for a new provider it calls the provider factory. `MovingBlock.createBlockEntity` returns `null` in both versions (1.11.2/1.12: 37–45), so a state-only update cannot reconstruct an MBE. If the old and new states are both `MOVING_BLOCK`, the chunk setter's provider-removal branch is gated by block identity change (`WorldChunk.java:427–432`), so that branch does not replace an existing MBE.

## Chunk/NBT restoration admits a missing-MBE query

- `WorldChunkS2CPacket` serializes chunk block sections and separately appends NBT for each block entity in the included sections (identical constructor: 29–46). On receive, `handleWorldChunk` first updates section data, then iterates the NBT list and calls `world.getBlockEntity(pos)`; it calls `readNbt` only when that lookup is non-null (1.11.2: 626–647; 1.12: 637–658).
- Section installation in `WorldChunk.update` reads block-state/light arrays and clears block caches; it does not create block entities from the packet's NBT (identical source: 856–896). The following NBT loop is also a read-into-existing-object path, not an NBT factory. A fresh client chunk received while a server piston is moving therefore can contain a `MOVING_BLOCK` state and its serialized MBE NBT yet have no client MBE: `World.getBlockEntity` requests immediate creation, but `WorldChunk.createBlockEntity` reaches `MovingBlock.createBlockEntity`, which returns null (identical `WorldChunk.java:587–610`, `MovingBlock.java:37–45`). The handler silently skips that NBT because the lookup is null.
- The standalone block-entity update handler also does not install an MBE from payload: after lookup it accepts only its explicit block-entity type/object pairs, types 1 through 10 (1.11.2: 1029–1046; 1.12: 1037 onward), none for `MovingBlockEntity`. This confirms the chunk NBT path is not backed by a generic MBE update constructor in this client handler.
- If such a state is present, ordinary player `Entity.move` requests `World.getCollisions` before axis clipping (1.11.2: 564 onward; 1.12: 567 onward). The world query dispatches state collision callbacks (1.11.2: 960–1015; 1.12: 964–1019). `MovingBlock.addCollisions` looks up the MBE and emits moving-box collisions only if it exists (1.11.2: 129–140; 1.12: same lines). Thus a query can observe a moving-block state without payload and receive no dynamic moving-piston collision boxes. The MBE would tick through the ordinary world block-entity tick list if installed (`World.java`: 1313–1344 / 1317–1348); absence also means there is no MBE tick to advance or restore it.

## Bounded conclusion

The endpoint ordering for normal client piston block-event execution, state packet application, chunk section/NBT handling, entity lookup, and collision query is unchanged; no changed condition between 1.11.2 and 1.12 was found in this slice. The sources do expose a same-at-both-endpoints restoration gap: chunk NBT is only read into an existing MBE, while the moving-block factory returns null, so a newly received chunk during piston motion can leave the client with a moving block state and no MBE. That state can be queried by local player movement and contributes no dynamic collision box until a later state/event path changes it. This is a bounded lifecycle observation, not an accepted endpoint movement difference, server-response claim, or pair-coverage verdict.
