# Renewed independent review: sleeping position response

Verdict: ACCEPTED for the bounded direct player position-response difference under the stated paired-input assumptions. This verdict is bound to the exact candidate SHA-256 below. The prior client eligibility wording has been corrected; I find no remaining source or scope correction for this bounded claim.

## Immutable candidate and source identity

Candidate: `.native-source/20261031/1132-1144-finish46/candidates/sleep-position-response.draft.md`

Candidate SHA-256: `BE6FD49E3BFF04ED690FE1CC643EC96CAB1F5CC21CC8F6F94FBF1947908B41C8`

Read-only source publication endpoints: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.13.2/ornithe-feather` and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.14.4/ornithe-feather` (Ornithe Feather mapping namespace, exact version directories). The primary file hashes cited in the candidate match the endpoint files:

- 1.13.2 `ClientPlayNetworkHandler.java`: `4F65502FBEE6476CEF0838563F03A66F07A1A9ED8C63E580F5338BA5FE274F11`
- 1.13.2 `PlayerEntity.java`: `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633`
- 1.14.4 `LivingEntity.java`: `2CCCF4331CE9E62013EEB8E96163E5E146E1291619EB998CE9867DAA87C02B61`

## Verified source trace

- 1.13.2 `ClientPlayNetworkHandler.handlePlayerSleep` (816–819) resolves the packet player in the client world and calls `trySleep(packet.getPos())`. The packet's `getPlayer` resolves its entity ID through `world.getEntity` (39–41), so the direct path reaches the local player when the paired response targets that player's ID.
- In 1.13.2 `PlayerEntity.trySleep`, facing comes from the vanilla block state (1228–1229). The eligibility checks are inside `if (!this.world.isClient)` (1230 onward); on the client they are skipped, and execution continues to the position write. With the assumed loaded bed position, it computes float intermediates `0.5F + direction.getOffsetX/Z() * 0.4F` and writes those X/Z coordinates plus `0.6875F` Y (1269–1273). The center-position fallback applies only when the chunk is not loaded (1274–1275).
- In 1.14.4 `ClientPlayNetworkHandler.handleEntityData` (546–551) resolves the packet entity ID in the client world and updates its synced data. `SyncedData.update` invokes `entity.onDataValueChanged` (239–243). `LocalClientPlayerEntity` delegates its callback to `super` and inherits `PlayerEntity` through `ClientPlayerEntity`, which inherits `PlayerEntity` (`LocalClientPlayerEntity.java:79,517–518`; `ClientPlayerEntity.java:22`; `PlayerEntity.java:108`). `LivingEntity.onDataValueChanged` handles the sleeping-position attribute on the client and invokes the setter for a present position (2510–2515). The setter at 2734–2736 writes `(x + 0.5, y + 0.6875F, z + 0.5)`.

For the same local player, bed position/facing, and loaded chunk, the horizontal coordinate differs by the direction-derived `0.4F` offset on one axis in 1.13.2 versus block center in 1.14.4. This is a direct position response and supports no claim about ordinary post-sleep trajectory. The candidate correctly treats input/history as paired assumptions and does not claim server packet production or bed-entry eligibility. No intermediate patch boundary or pair-wide coverage is inferred.
