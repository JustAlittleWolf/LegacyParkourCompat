# Independent source audit: fall-flying player NBT load

**Terminal: COMPLETED**

**Verdict:** The central behavioral claim is supported. The checked stable endpoint changes between 1.9.4 and 1.10: `LivingEntity.writeCustomNbt` in 1.10 writes `FallFlying` from `isFallFlying()`, and `readCustomNbt` sets tracked flag 7 only when that key reads true. 1.9.4 has no `FallFlying` NBT handling in `LivingEntity`. The 1.10.1 and 1.10.2 bodies are byte-identical to 1.10; 1.11.2 retains the same persistence behavior. This establishes first presence among the inspected stable endpoints, not a development-build introduction date.

**Scope verdict:** The direct persistence path is server-side. `PlayerManager.load(ServerPlayerEntity)` calls `player.readNbt(...)`, which reaches inherited living-entity NBT handling. This does not make NBT rehydration a client movement operation. The separate client `handleEntityData` path updates synchronized data on an already-existing entity; it is received state, not local-player NBT construction. The branch consuming an already-true `isFallFlying()` state has the same gliding calculations and operation order in 1.9.4 and 1.10. Thus no same-state client movement delta is established by this NBT change. The draft appropriately makes no claim about a new local input-to-glide transition. In 1.11.2 the move invocation is typed as `move(MoverType.SELF, ...)`; the audited conclusion concerns persistence and the gliding calculations, not a full move-resolution equivalence proof for that later release.

**Provenance correction:** The claim that the 1.9.4, 1.10.2, and 1.11.2 mapping directories lack `.decompile-provenance.json` is accurate. However, their version roots do contain outer `ornithe-feather.provenance.json`, `artifacts.sha256`, and `ornithe-feather.sources.sha256` records. The audited `LivingEntity.java` digests match those source lists. These records provide local run/mapping/tool metadata and source-byte hashes, so describing the hashes as lacking any provenance overstates the limitation. The 1.10 and 1.10.1 directories instead contain `.decompile-provenance.json` inside each mapping directory; its `sources` entry matches the file digest. No conclusion here relies on regenerating sources.

## Immutable inputs

- Comparison SHA-256: `d41e4eb6e0a85d812e110787d725a851691c49b07b899788dfd7d317292517e2`
- 1.9.4 `LivingEntity.java`: `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`
- 1.10, 1.10.1, and 1.10.2 `LivingEntity.java`: `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`
- 1.11.2 `LivingEntity.java`: `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`

Reviewed source slices: `LivingEntity.writeCustomNbt`, `readCustomNbt`, `isFallFlying`, and the gliding branch of `moveRelative`; `PlayerManager.load`; and client `ClientPlayNetworkHandler.handleEntityData` for 1.9.4 and 1.10. Checked persistence and the move invocation through 1.11.2. This is a bounded claim audit, not complete tick-graph coverage or runtime parity evidence.
