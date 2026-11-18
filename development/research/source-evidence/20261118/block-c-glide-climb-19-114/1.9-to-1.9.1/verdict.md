# Independent blind source audit: 1.9 → 1.9.1 Elytra and climbing gate

## Verdict

**Accepted, scoped to the stated movement slice.** The frozen claim that ordinary dry, on-foot local-player movement with an already-active Elytra flag at ladder/vine feet has no 1.9-to-1.9.1 movement delta is supported by the exact local Feather sources. The glide route precedes and bypasses the normal climb cleanup. The adjacent Elytra-start sound is a separate audio-only addition. This verdict does not freeze the complete 1.9/1.9.1 movement pair or establish runtime parity.

## Immutable input and source identity

Audited input: `c-glide-climb-19-191-alias-gate/report.md`, SHA-256 `4a9c3cc1bf678b8ba1803deb14ce58e0cf183b2d2b652114e3df4cb8802f2a6a`.

The explicit releases are `1.9` and `1.9.1`, both `ornithe-feather`; the canonical trees are `decompiled_minecraft/1.9/ornithe-feather/` and `decompiled_minecraft/1.9.1/ornithe-feather/`. No moving version alias is involved. Each tree's local `.decompile-provenance.json` SHA-256 matches the frozen input: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` (1.9) and `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` (1.9.1). Both record the same decompiler pipeline and runtime.

I independently hashed the local cached raw client, Feather artifact and Feather mapping metadata bytes. They match both each manifest and the cache pin sidecars:

| Input | Raw client | Feather artifact | Feather metadata |
|---|---|---|---|
| 1.9 | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371` |
| 1.9.1 | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4` |

For every Java file cited below, the local file SHA-256 equals its `sources` entry in the corresponding local provenance manifest. The cited source hashes are the ones recorded in the immutable input report; none disagreed on recheck.

## A — Local start request and authoritative flag

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, 1.9 line 703 and 1.9.1 line 704: both require `input.jumping && !bl && !onGround && velocityY < 0.0 && !isFallFlying() && !abilities.flying`, then an Elytra in `EquipmentSlot.CHEST` for which `ElytraItem.canFly` succeeds. Both send `START_FALL_FLYING`. The 1.9.1 sound call follows the send (separated below).
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`, both lines 846–854: both validate `!onGround`, `velocityY < 0.0`, `!isFallFlying()`, and `!isInWater()`, then independently validate chest-slot Elytra identity and `canFly` before calling `setFlying()`. The file is byte-identical across the pair (SHA-256 `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634` on both sides).
- `net/minecraft/server/entity/living/player/ServerPlayerEntity.java`, both lines 1040–1047: `setFlying()` sets flag 7 true; `clearFlying()` sets it true and then false. The file is byte-identical (SHA-256 `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10`).
- `net/minecraft/entity/living/LivingEntity.java`, 1.9 lines 1697–1723 and 1.9.1 lines 1701–1727: travel invokes `flyingTick()` before `moveRelative()`. The updater retains an active flag only while airborne and not riding with a usable chest Elytra; it writes flag 7 only on the logical server (`!world.isClient`). Thus the local movement consumes the server-authoritative, synchronized active flag rather than authoring it locally. The updater is unchanged across this pair.

## B — Active glide route versus ladder/vine feet

- `net/minecraft/entity/living/LivingEntity.java`, 1.9 lines 1289–1458 and 1.9.1 lines 1293–1462: for the assigned dry condition, `moveRelative()` reaches the `isFallFlying()` branch before the ordinary movement branch. The active-flag branch is identical across releases. Its exact threshold is `velocityY > -0.5`; only then it assigns `fallDistance = 1.0F`. This strict comparison and float assignment occur before look-vector math, velocity updates, multipliers, and `move(...)`.
- `isClimbing()` at 1.9 lines 896–908 and 1.9.1 lines 900–912 floors `x`, `getShape().minY`, and `z`, rejects a spectator player, then recognizes ladder/vine at that feet block (plus the matching open-trapdoor case). With the flag already active, the earlier glide branch runs without calling this climb response.
- When the flag is clear, the normal branch later invokes `isClimbing()` before `move(...)`: it clamps horizontal velocity to `[-0.15F, 0.15F]`, clears `fallDistance`, floors downward velocity at `-0.15`, and zeros negative vertical velocity for a sneaking player. After movement, horizontal collision while climbing sets vertical velocity to `0.2`. These gates, assignments, and their order are identical in both releases.
- `net/minecraft/entity/living/player/PlayerEntity.java`, both lines 1366–1384: the ability-flight exception is separate. An ordinary player with `abilities.flying == false` delegates to `super.moveRelative`, so it does not divert the assigned scenario.

The cited `LivingEntity.java` SHA-256 values are `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` (1.9) and `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` (1.9.1). `PlayerEntity.java` hashes are `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` and `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71`.

## Separate audio observation

`LocalClientPlayerEntity.java`, 1.9 line 706, sends the request without playing Elytra-on-player sound. At 1.9.1 line 707, the same send is followed by `soundManager.play(new ElytraOnPlayerSoundInstance(this))`. This is an adjacent audio addition, not a movement-state or operation-order delta for the audited claim.

## Boundary and procedure

This audit covers only the ordinary dry, on-foot local chest-Elytra slice at ladder/vine feet, including request and server validation, flag authority, active glide ordering/threshold/write, and the inactive climb cleanup comparison. Fluids, riding, spectators, item damage, damage calculation, other tick inputs, other operations, and later release boundaries remain outside this verdict. This was a read-only source audit: no product implementation, source generation, tests, build, game/TAS/Gym/server launch, or runtime parity claim was made.