# Death velocity source audit verdict

Terminal: **COMPLETED**

Verdict: **Accept the 1.8.9-to-1.9 death-velocity arithmetic change as a directly reachable client player operation.** The comparison draft's producer-only/no-client-path disposition is incorrect.

## Bound inputs and source identity

- Comparison draft SHA-256: `1e68be9a1632c8b37cbcebc778199d9b6851976e00e18b3617d4b2e76ca58900` (verified before review).
- Exact pair: Minecraft 1.8.9 and 1.9.0, `ornithe-feather`; inspected 1.9.1, 1.9.2, 1.9.3, and 1.9.4 as bounded pair context.
- 1.8.9 and 1.9.4 roots have endpoint provenance, ready markers, and source manifests. The focal source hashes match `ornithe-feather.sources.sha256`; ready-marker source-manifest and client hashes agree with the draft's evidence identity.
- 1.9.0 through 1.9.3 each have a nested hidden `.decompile-provenance.json` in `decompiled_minecraft/<version>/ornithe-feather/`. These are full manifests: they record the exact client-jar URL and SHA-256, version-specific Feather Gen2 build.2 mapping jar URL and SHA-256, decompile pipeline/runtime, and per-source SHA-256 map. The four manifest hashes are respectively `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`, `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`, `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`, and `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`. PlayerEntity, LivingEntity, EntityVelocityS2CPacket, and ClientPlayNetworkHandler focal file hashes match those manifests at all four versions. The draft's statement that these versions lacked source manifests/provenance is false; standalone ready-marker files are absent, but exact source/mapping identity and focal-file integrity are present in the hidden manifests.

## Source ruling

- `PlayerEntity.die(DamageSource)`: 1.8.9 writes `velocityY = 0.1F`, then for non-null source computes X/Z from `-cos/sin((damagedSwingDir + yaw) * (float) Math.PI / 180.0F) * 0.1F`; null source sets X/Z to zero. In 1.9.0 and later checked versions, the non-null branch instead uses `* (float) (Math.PI / 180.0)`. The gates, order, Y write and null-source writes are otherwise the same. The two float constants are rounded by different operation orders, so the resulting client-side death velocity can differ.
- This method is directly reached on a client through death event 3, independently of client-side damage production: `LivingEntity.die` emits `world.doEntityEvent(this, (byte)3)`; `ServerWorld.doEntityEvent` sends `EntityEventS2CPacket` to tracking clients; `ClientPlayNetworkHandler.handleEntityEvent` calls `entity.doEvent(3)`; `LivingEntity.doEvent(3)` sets health to zero and calls virtual `this.die(DamageSource.GENERIC)`. For a `PlayerEntity`, virtual dispatch executes the changed override and writes the local velocity. `LivingEntity.takeDamage`'s client-side early return does not block this route: event 3 calls `die` directly, without going through `takeDamage`.
- The lethal player-melee server path also constructs `EntityVelocityS2CPacket` after `target.takeDamage` and restores saved server velocity afterward. The generic packet's clamp, multiply-by-8000, integer truncation, short encoding and `/8000.0` client application are unchanged across checked snapshots. This packet path is distinct from the local event-3-to-`die` response. Do not infer a realized trajectory delta from this source review alone, since later packet ordering may overwrite a local velocity write.

## Evidence locations

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/`:

- `entity/living/player/PlayerEntity.java`, `die`: 1.8.9 line 513; 1.9.0-1.9.2 line 483; 1.9.3-1.9.4 line 484.
- `entity/living/LivingEntity.java`, `die` event emission: 1.8.9 line 729; 1.9.4 line 832. `doEvent` event 3: 1.8.9 line 955; 1.9.4 line 1067.
- `server/world/ServerWorld.java`, `doEntityEvent`: 1.8.9 line 784; 1.9.4 line 922.
- `client/network/handler/ClientPlayNetworkHandler.java`, `handleEntityEvent`: 1.8.9 line 771; 1.9.4 line 836. The method body is unchanged across the checked endpoints.
- `network/packet/s2c/play/EntityVelocityS2CPacket.java` and `client/network/handler/ClientPlayNetworkHandler.java`, `handleEntityVelocity`: packet/application behavior is unchanged across the checked snapshots.

This is a source-operation acceptance only; it does not claim a measured trajectory or runtime parity result. No files in the product checkout were modified. No tests or game launches were run.