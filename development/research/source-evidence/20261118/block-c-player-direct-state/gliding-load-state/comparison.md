# Fall-flying player NBT load comparison

**Terminal: COMPLETED**

## Finding

The earliest checked stable release with a persisted fall-flying bit is **1.10**. In 1.9.4, `LivingEntity.writeCustomNbt` and `readCustomNbt` contain no `FallFlying` NBT handling. In 1.10, the writer appends `nbt.putBoolean("FallFlying", this.isFallFlying())` (line 460), and the reader conditionally calls `this.setFlag(7, true)` when `nbt.getBoolean("FallFlying")` is true (lines 494–496). There is no `else` that clears the flag. The same code is present in 1.10.1 and 1.10.2; 1.11.2 retains it (lines 468, 505–507). This bounds the first stable cutover to 1.10; it does not identify a pre-release introduction point.

The flag is written/read on `LivingEntity`, so it covers a player through inheritance. The concrete player-load path is server-side: `server/PlayerManager.java` calls `player.readNbt(...)` (1.9.4 line 284; 1.10 line 271; 1.11.2 line 263). Thus the new NBT read changes server-side player state on load. It is not a client local-player NBT initialization hook. Client network handlers apply synchronized entity data to an already present entity (`ClientPlayNetworkHandler.handleEntityData`, 1.9.4 lines 444–449 and 1.10 lines 447–452); that is a distinct received-state path. Do not equate either path with the local client's own construction/initialization.

**Scope decision:** the server-side NBT load/write itself is not an eligible historical client-movement delta under the project invariant that historical movement never runs on logical servers. It can affect later client movement only if the client receives a fall-flying flag; that is network-provided state and should be analyzed as such. With `isFallFlying()` held identical, the inspected 1.9.4 and 1.10–1.10.2 `LivingEntity.moveRelative` fall-flying branch has the same gliding math and operation order. 1.11.2 retains that branch; its `move` call uses the newer `MoverType.SELF` signature. No new local input-to-start-gliding behavior is claimed here.

## Evidence

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/` (Feather mapping family).

| Release | Fall-flying NBT source | Local file SHA-256 |
|---|---|---|
| 1.9.4 | `net/minecraft/entity/living/LivingEntity.java`: `readCustomNbt` begins at 460; no `FallFlying` NBT key/write/read in the class. `isFallFlying` reads tracked flag bit 7 at 2052–2054. | `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5` |
| 1.10 | `.../LivingEntity.java`: write at 460; read/set at 494–496; `moveRelative` begins at 1332. | `D40DD476B6B68C6CE45B4202823475DEB546ECDA2284DA330FF6724B33815E82` |
| 1.10.1 | Same lines and source bytes as 1.10. | `D40DD476B6B68C6CE45B4202823475DEB546ECDA2284DA330FF6724B33815E82` |
| 1.10.2 | Same lines and source bytes as 1.10. | `D40DD476B6B68C6CE45B4202823475DEB546ECDA2284DA330FF6724B33815E82` |
| 1.11.2 | `.../LivingEntity.java`: write at 468; read/set at 505–507; `moveRelative` begins at 1388. | `BB7DC6C9E423A9568D6433D51BBA12E7AEE4555FBF3FB3E2B87F618382279F2F` |

The 1.10 `LivingEntity.java` hash matches its canonical `.decompile-provenance.json` source entry. 1.10.1 also has a provenance manifest. The canonical 1.9.4, 1.10.2, and 1.11.2 mapping directories have no provenance manifest, so their hashes identify the inspected local source bytes but do not attest the original download/mapping inputs or full decompilation pipeline. No source generation, product/Git/wiki/backlog access, tests, or runtime checks were performed.

## Coverage and limits

Compared the named NBT writer/reader and flag predicate for 1.9.4, 1.10, 1.10.1, 1.10.2, then checked persistence and gliding-branch continuity in 1.11.2. Checked the `PlayerManager` load caller and client entity-data handler to distinguish server persistence from received client state. This was a bounded operation comparison, not a complete tick-graph or full-pair coverage audit. It does not establish what a specific server sends after loading a player, prove runtime parity, or locate the change inside the 1.9.4–1.10 development interval.