# Client world/network residual closure: 1.18.2–1.19.4

## Scope and integrity

This closes the `client-level/network` role from the exact case-sensitive changed-path roster: 73 edge/path rows and 34 unique paths over 1.18.2→1.19→1.19.1→1.19.2→1.19.3→1.19.4. The six exact Mojmap source manifests and every manifested file digest were verified by the parent inventory and independent coverage audit. Hashes for the movement-relevant client classes and added prediction sources are in `source-hashes.tsv`.

The remaining client network/package paths are chat-session/profile-key/reporting, server address/status/list, player-list presentation, or registry plumbing. No route from those members to local-player input, movement state, collision, travel, or block/fluid lookup was found. Their path roster remains in the parent `changed-path-roles.csv` under role `client-level/network`; this report does not duplicate that roster.

## Movement data and availability

- `ClientChunkCache` storage-range, lookup, replacement, and drop logic is unchanged through 1.19.3. The 1.19.2→1.19.3 source difference is registry identifier relocation in the plains fallback biome construction. 1.19.3→1.19.4 adds `replaceBiomes`, which updates a loaded chunk's biome container only. No block-state, fluid-state, collision-shape, or chunk-availability method changes in these edges.
- `ClientLevel.tickEntities`, `tickNonPassenger`, and passenger ticking preserve their gates and call order across the edges. The only change in the entity profiler wrapper is registry lookup plumbing. `ClientLevel` registry-access/biome lookup and seeded-sound signatures also change; these do not alter player movement under fixed block/fluid state.
- 1.19 adds `BlockStatePredictionHandler` and client hooks `setServerVerifiedBlockState`, `syncBlockState`, `setBlock`, and `handleBlockChangedAck`. `ClientPacketListener` routes block update packets through this prediction/reconciliation path. This is client block-state production/reconciliation; it can temporarily change the local world state, so it is excluded by the assigned equal fixed block/fluid state boundary. No separate movement math or collision provider changed in that path.
- The `handleLevelChunkWithLight`, chunk replacement, forgotten-chunk, light update, and block update handlers preserve their chunk/light plumbing; the 1.19 block-update change is the state-prediction call above. The client cache's new biome payload path does not replace block/fluid data.
- `handlePlayerAbilities` is text-identical across all six versions for `flying`, `mayfly`, `instabuild`, invulnerability, and walking/flying speed writes. Movement position/velocity correction and respawn entity-data copying were already bounded in the client-input/packet-state evidence and are not reopened here.

## Client interaction boundary

`MultiPlayerGameMode.useItem` continues to invoke `ItemStack.use` on the local player and applies any returned hand stack. In 1.19 this call is wrapped in `startPrediction`; the item callback and local stack replacement remain the relevant path. `useItemOn` similarly wraps item/block use in the prediction layer. Item-specific movement consumers (including Elytra) remain with the assigned item residual audit; this report does not close those item members. Creative inventory delivery adds a required-feature gate in 1.19.3; this is feature-gated item availability/state, not a change to movement math for an equal item state.

## Disposition

No independent movement candidate was found in the client chunk/world availability logic for equal fixed vanilla block/fluid states. Client prediction affects the state supplied to movement and is out of this fixed-state slice. This report closes only the assigned `client-level/network` source-role paths; it is not a full-pair freeze. Item-use applicability remains pending the item/inventory residual closure.
