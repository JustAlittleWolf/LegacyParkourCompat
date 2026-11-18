# Independent source audit verdict: 1.9.2 → 1.9.3 ability-flying glide flag

## Verdict

**Accepted: the bounded ordinary local-player movement claim is source-supported; there is no 1.9.2-to-1.9.3 difference in this operation.** If a local player enters `PlayerEntity.moveRelative` with `abilities.flying == true`, `!isRiding()`, and entity flag 7 live, both versions execute the same flying override: save coordinates; save vertical velocity and air speed; set air speed from `getFlySpeed() * (isSprinting() ? 2 : 1)`; invoke `super.moveRelative`; restore vertical velocity as the saved value times `0.6`; restore air speed; set `fallDistance = 0.0F`; then directly call `setFlag(7, false)`. Thus the final player-level clear occurs after the superclass call and is unconditional within the flying/non-riding branch. It does not depend on the entry `fallDistance` value.

The ordinary local-player call path is real: `LivingEntity` invokes virtual `this.moveRelative(this.sidewaysSpeed, this.forwardSpeed)` in travel. `LocalClientPlayerEntity` extends `ClientPlayerEntity`, which extends `PlayerEntity`; the local class returns true from `isLocal()` and `isLocallyControlled()`. The superclass `LivingEntity.moveRelative` gate is `isLocallyControlled() || isLogicalSideForUpdatingMovement()`, so local movement passes it. `PlayerEntity`'s own post-super flag clear has no additional local/server gate. The report is right to distinguish the shared vanilla method ownership from the mod invariant that historical simulation belongs only on the logical client.

## Separation checks

- `fallDistance = 0.0F` and flag-7 clear are adjacent but distinct player-level writes in this order in both versions. The former is a float state reset; the latter clears a shared entity flag. Neither is a combined “normalization” operation.
- The superclass has its own glide-related ground/server flag write in its fall-flying branch. That path is separately gated by `onGround && !world.isClient`; it does not replace the player override's subsequent direct clear. For the ordinary client path, `world.isClient` makes that superclass write false even if grounded.
- In `PlayerAbilities.readNbt`, both versions load `flying` from `"flying"` and `canFly` from `"mayfly"` independently, with no reconciliation between them. `PlayerEntity.readCustomNbt` delegates to its superclass and later calls `abilities.readNbt`; the player method itself has no flag-7 write. Base `Entity` NBT writes/reads `FallDistance` as a separate field and does not serialize or restore shared flag 7. `LivingEntity.isFallFlying()` reads flag 7 directly in both releases (1.9.2 line 2043; 1.9.3 line 2053). This confirms the narrow examined NBT observation; it does not audit every inherited/entity-specific save path.

## Exact evidence and immutable inputs

The writer report input is frozen by SHA-256 `9fe71a3576cba85908946f975ad5b5b0c8e6a5cb4f81b1698ea3d88ee7a5bdc0` (`report.md`, as supplied). Exact local source family is Ornithe Feather under `decompiled_minecraft/<version>/ornithe-feather/`; both provenance manifests list the pinned `feather-gen2 <version>+build.2` merged-v2 artifact and source hashes below. I independently verified source bytes against each manifest's `sources` entry.

| Version | Source / member | Lines | SHA-256 |
|---|---|---:|---|
| 1.9.2 | `net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float,float)` | 1366–1384 | `1fc5107b9b7f5711105d521a28829f2466fe9f297e832f4d209e6ec422111aac` |
| 1.9.3 | same, `moveRelative(float,float)` | 1372–1390 | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| 1.9.2 | `net/minecraft/entity/living/LivingEntity.java`, virtual travel call and superclass gate/glide branch | 1293–1344, 1702 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| 1.9.3 | same | 1302–1353, 1711 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.9.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `isLocal`, `isLocallyControlled` | 303–305, 444–446 | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| 1.9.3 | same | 306–308, 447–449 | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| 1.9.2 | `net/minecraft/entity/Entity.java`, movement ownership and base NBT | 1202, 1260, 1287, 2237 | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9.3 | same | 1267+, 2254+ | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| both | `net/minecraft/entity/living/player/PlayerAbilities.java`, `readNbt` / `writeNbt` | 14–41 | `b0350ca7819f995e4ede358a9d71270cc7a073a9d8817ce218d363cca7ea0cb0` |

The 1.9.2 manifest pins Feather metadata `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`, merged-v2 artifact `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`, and client `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`. The 1.9.3 manifest pins Feather metadata `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`, merged-v2 artifact `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`, and client `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`.

## Audit boundary

This verdict accepts only the stated ordinary local-player movement behavior and the narrow adjacent NBT distinction. It is not a full pair inventory, runtime parity result, or audit of server/network/malformed-save paths. No product code, product history, wiki, network, source generation, build, test, or game launch was used.