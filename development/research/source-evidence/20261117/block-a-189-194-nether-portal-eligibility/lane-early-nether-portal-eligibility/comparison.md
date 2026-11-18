# Nether portal contact eligibility — bounded source comparison

Pair: Minecraft 1.8.9 → 1.9.4, Ornithe Feather.
Coverage: `PortalBlock.onEntityCollision`; `Entity.onPortalCollision`, portal-tick gate, riding/passenger and portal-use predicates; `PlayerEntity.getMaxNetherPortalTime`/`getPortalCooldown`; locally controlled `LocalClientPlayerEntity` portal timer/cooldown. Endpoints only; no claim about the 1.9.0–1.9.3 introduction boundary.

## Source identity and verification

Both trees are the repository-local `decompiled_minecraft/<version>/ornithe-feather` publications. Their readiness markers say `status=ready`, mapping `ornithe-feather`; provenance records the exact paired `decompileMinecraft --versions=1.8.9,1.9.4 --mappings=feather --decompiler-heap=4G` run with Vineflower 1.12.0, Java 25. Both source-manifest files match the readiness marker's SHA-256; each listed relevant Java file matches its own manifest entry.

| Version | Ready source manifest SHA-256 | PortalBlock.java | Entity.java | PlayerEntity.java | LocalClientPlayerEntity.java |
|---|---|---|---|---|---|
| 1.8.9 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `bb9d2cba26df71c8383408fcccc93b849eebecd3d36924a63ee80cba8734867e` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| 1.9.4 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `8f6c4502a2b910ad8eaaefed19d162a3319e9afc6802c8ccb3e7e05b3987c598` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |

## Compared slices

- 1.8.9 `net/minecraft/block/PortalBlock.java`, `onEntityCollision`, lines 162–166: dispatches when `entity.vehicle == null && entity.rider == null`.
- 1.9.4 same path, lines 170–174: dispatches when `!entity.isRiding() && !entity.hasPassengers() && entity.canUsePortals()`.
- 1.8.9 `net/minecraft/entity/Entity.java`, `isRiding`, lines 1448–1450: `vehicle != null`. Its paired portal predicate checks `rider == null` for no passenger.
- 1.9.4 `net/minecraft/entity/Entity.java`, `isRiding`/`hasPassengers`, lines 1597–1603: mount non-null and passenger-list non-empty. `canUsePortals`, lines 1895–1897, defaults true. `PlayerEntity` has no override; the 1.9.4 Wither and EnderDragon overrides return false (Wither lines 563–566, Dragon lines 890–893), so that added predicate does not reject a player.
- 1.8.9 `net/minecraft/entity/Entity.java`, `onPortalCollision`, lines 1397–1418; 1.9.4 same member, lines 1538–1559. In both, active cooldown resets to the entity's portal cooldown; otherwise portal-shape bookkeeping is guarded by `!world.isClient`, then `inPortal` is set on both logical sides. The 1.9.4 defensive `new BlockPos(pos)` copy is server-side metadata only.
- Both `Entity.baseTick` portal progression blocks are guarded by `!world.isClient && world instanceof ServerWorld` (1.8.9 lines 261–297; 1.9.4 lines 323–358). Riding entities do not advance the server portal counter: 1.8.9 tests `vehicle == null`; 1.9.4 tests `!isRiding()`. 1.9.4 extracts cooldown decrement to `tickPortalCooldown`, still within the server-only block.
- Player overrides in both versions keep normal portal time at 80 ticks, invulnerable-player time differs (1.8.9 `getMaxNetherPortalTime`, lines 293–295: 0; 1.9.4 lines 312–314: 1), and player portal cooldown at 10 (1.8.9 lines 308–310; 1.9.4 lines 327–329). The invulnerable-time delta affects only server-side dimension-transfer progression above, not local contact presentation/cooldown or a movement writer.
- Locally controlled player `LocalClientPlayerEntity` has the same portal timer and cooldown procedure: 1.8.9 lines 500–533; 1.9.4 lines 610–643. `inPortal` advances float `portalTime` by `0.0125F` capped at 1; otherwise nausea can add `0.006666667F`, else timer decays by `0.05F` and floors at 0; cooldown decrements by one. The collision callback sets the local `inPortal` only when cooldown is not active. No movement position/velocity/box writer is reached by this contact timer.

## Result and boundary

No player-specific contact-eligibility difference is established for this endpoint pair: riding and passenger checks have equivalent player meaning, and the new portal-use predicate is true for players. No changed locally controlled portal contact timer/cooldown or direct movement writer was found. The changed invulnerable-player portal threshold is within a server-only dimension-transfer gate and is outside this lane's local-player movement result.

Unexamined by assignment: portal construction/shape and destination geometry, dimension-transfer and packet behavior, intermediate 1.9 patches, other portal consumers, and runtime parity. This is a scoped source comparison, not a full-pair tick-graph freeze or independent audit.