# Source-only roster: 1.8.9 → 1.9 End-portal packets

## Assignment and method

- Host/execution environment: Windows 11, native PowerShell.
- Exact release pair: Minecraft 1.8.9 → 1.9 stable.
- Mapping family: Ornithe Feather on both sides.
- Bounded operations: ordinary player End-portal arrival at dimension 1, including server respawn/absolute-move packet writes and final End coordinates; client handling of absolute player position/velocity and any teleport acknowledgment.
- Sources: canonical shared `decompiled_minecraft/<version>/ornithe-feather/` trees, read-only. No wiki, product implementation, backlog or other research lanes were consulted.

## Exact source publication checks

- 1.8.9: `decompiled_minecraft/1.8.9/ornithe-feather.ready.json` reports `status=ready`, exact `versionId=1.8.9`, `mapping=ornithe-feather`, Feather `1.8.9+build.2` artifacts, Vineflower, Java 25, and 1,612 source files. Ready-marker SHA-256: `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`. Its source-manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and artifact-manifest SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` match the ready marker. All 1,612 source entries match the mapped files (0 mismatches). The source tree is the published ready output retained at the shared canonical path.
- 1.9: `decompiled_minecraft/1.9/ornithe-feather/.decompile-provenance.json` identifies release metadata `/1.9.json`, client artifact SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`, Feather `1.9+build.2-mergedv2.jar` SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`, and 1,819 source entries. All 1,819 provenance source hashes match files in the tree (0 mismatches), and all 34 recorded external-input hashes match their cached artifacts (0 mismatches). Provenance-manifest SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

## Ordered source coverage

1. Portal entry call reaches `ServerPlayerEntity.changeDimension(1)` from `EndPortalBlock.onEntityCollision` on the server; read only as the bounded ordinary-player route context.
2. Compared the entry branch of `ServerPlayerEntity.changeDimension(int)` and its ordered call into `PlayerManager.changeDimension(ServerPlayerEntity,int)` on both releases.
3. Compared `PlayerManager.changeDimension(ServerPlayerEntity,int)` packet order, target selection, fields, state writes, and the `changeDimension(Entity,int,ServerWorld,ServerWorld)` path used for the final End coordinates.
4. Compared `ServerPlayNetworkHandler.teleport(...)` on both releases to establish absolute-move packet fields, server position update order, and the 1.9 teleport ID.
5. Compared `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)` on both releases for relative-coordinate gates, velocity writes, position/angle application, and response packet order.

No unrelated portal routes, 1.9 patch aliases, inferred runtime scheduling, or other movement areas were examined. Details and source line references are in `comparison.md`; the two independently described differences are in the finding drafts.