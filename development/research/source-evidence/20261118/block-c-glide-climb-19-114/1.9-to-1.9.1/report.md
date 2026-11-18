# 1.9 → 1.9.1: Elytra glide versus climbing at ladder/vine feet

## Bounded result

For an ordinary dry, on-foot local player with a usable Elytra in the chest slot, the requested 1.9-to-1.9.1 movement path has no source delta. On both sides, a local jump request is gated on `!onGround`, `velocityY < 0.0`, not already fall-flying, and not ability-flying; the chest stack must be an Elytra for which `ElytraItem.canFly` succeeds. The server independently validates the start action, then owns the fall-flying flag. During travel, an already-set fall-flying flag selects gliding before the ordinary `isClimbing()` response, so the dry ladder/vine-feet case uses the glide route and bypasses climb clamps. With the flag clear, the same feet position uses the ordinary climb route.

The adjacent 1.9.1 client source adds playback of `ElytraOnPlayerSoundInstance` beside the same `START_FALL_FLYING` send. The 1.9 client already imports that sound type, but does not play it at this gate. This is an audio-only addition; it does not change the compared movement state or ordering.

## Exact source identities and integrity

Both inputs are the explicit stable IDs `1.9` and `1.9.1`, mapped with `ornithe-feather`; no moving alias was used. The canonical local roots are `decompiled_minecraft/1.9/ornithe-feather/` and `decompiled_minecraft/1.9.1/ornithe-feather/`.

| Input | Provenance manifest SHA-256 | Pinned client SHA-256 | Feather artifact SHA-256 | Feather metadata SHA-256 |
|---|---|---|---|---|
| 1.9 / ornithe-feather | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` |
| 1.9.1 / ornithe-feather | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` |

Both manifests identify the same pipeline (`lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`) and runtime (Eclipse Adoptium 25.0.3+9-LTS, Windows 11, amd64). The pinned Vineflower tool entry is `jar:vineflower-1.12.0.jar`, SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d` on both manifests. The exact URLs and all listed input hashes above were read from each local provenance manifest.

For every referenced Java file below, I computed the local file SHA-256 and confirmed equality with that tree's `.decompile-provenance.json` `sources` entry. SHA-256 values are recorded here per tree so the relevant source bodies can be rechecked:

| Relative source path | 1.9 source SHA-256 | 1.9.1 source SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` | `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` | same |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10` | same |

## Compared path and source facts

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, 1.9 line 703 and 1.9.1 line 704: the `input.jumping && !bl && !onGround && velocityY < 0.0 && !isFallFlying() && !abilities.flying` gate and chest-slot `Items.ELYTRA` / `ElytraItem.canFly` predicate are unchanged. Both send `START_FALL_FLYING`. 1.9.1 additionally plays the Elytra-on-player sound immediately after the send.
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, both line 846: `START_FALL_FLYING` is accepted only for `!onGround && velocityY < 0.0 && !isFallFlying() && !isInWater()`, then the server rechecks chest Elytra identity and `canFly` before `ServerPlayerEntity.setFlying()`. The handler body is byte-identical between these releases. The client request is not itself the authoritative flag write.
- `net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, lines 1040–1046 in both exact sources: `setFlying()` writes flag 7 true; `clearFlying()` writes flag 7 true and then false, in that order. This file is byte-identical across the pair.
- `net/minecraft/entity/living/LivingEntity.java`, 1.9 lines 1697–1723 and 1.9.1 lines 1701–1727: tick travel calls `flyingTick()` before `moveRelative()`. The updater retains the flag only while airborne and not riding with a usable chest Elytra; only `!world.isClient` writes `setFlag(7, bl)`. Both releases are identical in the updater. The server also damages the Elytra every twentieth flying tick under the same predicate; item durability production is outside this movement finding.
- `net/minecraft/entity/living/LivingEntity.java`, 1.9 lines 896–914 and 1289–1450; 1.9.1 lines 900–918 and 1293–1454: `isClimbing()` floors `x`, `getShape().minY`, and `z`, returns false for a spectator player, and checks the feet block for ladder/vine (or the matching open trapdoor case). `moveRelative()` first follows the fluid gates, then selects the `isFallFlying()` branch before its ordinary ground/air route. Thus, for this assignment's dry condition and an already-active flag, the glide branch wins even when `isClimbing()` at ladder/vine feet would return true.
- In that glide branch, before `move`, `velocityY > -0.5` writes `fallDistance = 1.0F`; then the unchanged look-vector and pitch math updates velocity, applies `0.99F` horizontal and `0.98F` vertical multipliers, and moves. A horizontal collision's Elytra damage is server-gated; on-ground flag clearing is also server-gated. The glide branch is source-identical across the pair.
- If the flag is clear, the normal route's `isClimbing()` response occurs before `move`: horizontal velocity clamps to `[-0.15F, 0.15F]`, `fallDistance` becomes `0.0F`, downward `velocityY` is floored at `-0.15`, and a sneaking player with negative `velocityY` is set to `0.0`. After moving, horizontal collision while climbing sets `velocityY = 0.2`; otherwise the same levitation or gravity handling and drag follow. Those operations and order are unchanged across this pair.
- `net/minecraft/entity/living/player/PlayerEntity.java`, lines 1366–1381 in both releases: the player's ability-flight special case is separate; when not ability-flying/on-foot it calls `super.moveRelative`. It does not insert a ladder/vine exception into the fall-flying branch. No movement difference was found here.

## Boundary

This closes only the requested adjacent-pair source comparison for ordinary dry, on-foot local chest-Elytra movement at ladder/vine feet, including the local start gate, server validation/authority path, flag refresh ordering, glide pre-move Y/fall-distance write, and normal climb response. Fluid handling, riding, spectators, durability, damage resolution, all other tick inputs, and later release boundaries were not audited. The 1.9.1 sound addition is noted as an adjacent non-movement change; no movement finding is proposed for this bounded slice.
