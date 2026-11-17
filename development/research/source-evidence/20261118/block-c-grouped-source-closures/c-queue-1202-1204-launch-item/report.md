# 1.20.2 → 1.20.4 launch-item direct player impulse coverage

Status: **COMPLETED** for the assigned bounded source slice. This is source discovery, not full-pair coverage freeze. No individual difference finding was created: the assigned direct impulse implementations are unchanged at these endpoints.

## Exact evidence

- Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` (read-only).
- Older: Minecraft `1.20.2`, Mojmap; ready marker `decompiled_minecraft/1.20.2/mojmap.ready.json` reports `ready`, version `1.20.2`, mapping `mojmap`. SHA-256 of `mojmap.sources.sha256`: `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` (matches the ready marker's `sourceManifestSha256`).
- Newer: Minecraft `1.20.4`, Mojmap; ready marker `decompiled_minecraft/1.20.4/mojmap.ready.json` reports `ready`, version `1.20.4`, mapping `mojmap`. SHA-256 of `mojmap.sources.sha256`: `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` (matches the ready marker's `sourceManifestSha256`).
- The complete `TridentItem.java`, `FireworkRocketItem.java`, and `FireworkRocketEntity.java` source hashes match between releases: respectively `e86934edeb81b85e65e02c4ae8b0ce76485c48e4cc7774ee80f5de3884713c57`, `4e9276f08a09a81ebdb3ee587f6ffb2561256e9cc2223536cb270b2ae7c408ba`, and `93b1aa2842c6aa83f3167745956726e85a128dc456c812d38ce3dd45145e347b`.

## Riptide trident

Both releases: `net/minecraft/world/item/TridentItem.java`, `releaseUsing` lines 58–104, `use` lines 108–118. After the 10-tick threshold, the Riptide level must be positive and the player must satisfy `isInWaterOrRain`. Only durability handling and ordinary thrown-trident creation are inside `!level.isClientSide`; the Riptide player response is outside that branch. Both caller paths reach it: client `MultiPlayerGameMode.releaseUsingItem` sends `RELEASE_USE_ITEM` and then calls the local player at `MultiPlayerGameMode.java:448–452` (1.20.2) / `:449–453` (1.20.4), and `ServerGamePacketListenerImpl` handles that action by calling the server player at `ServerGamePacketListenerImpl.java:1014` (1.20.2) / `:1031` (1.20.4). It awards the item stat, constructs a float direction from yaw/pitch, normalizes it with `Mth.sqrt`, scales by `3.0F * ((1.0F + level) / 4.0F)`, adds that vector via `push`, and starts the 20-tick auto-spin attack. If `onGround()` is true, it then performs `move(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0))`. This is a direct local actor response; the player velocity is not gated to the server-only thrown-trident producer. The relevant `Entity.push` body (`Entity.java:1428` in 1.20.2; 1.20.4 is one line later) is unchanged: add the supplied components to current delta movement and set `hasImpulse`. `Player.startAutoSpinAttack` is likewise unchanged (`Player.java:605`/`:606`); it sets attack ticks and only sets living flag 4 on the logical server. This path does not set a player pose.

Difference: none across the compared release endpoints in the item method or the inspected helper operations. The direction/scaling order, float casts, grounded predicate and lift constant all match.

## Firework rocket boost while fall-flying

Both releases: `net/minecraft/world/item/FireworkRocketItem.java`, `useOn` lines 42–55 and `use` lines 59–75; `net/minecraft/world/entity/projectile/FireworkRocketEntity.java`, attached-player constructor lines 64–68 and `tick` lines 98–145. Item use is accepted only when `Player.isFallFlying()` is true. The item-use producer creates the attached rocket and consumes the item only on the logical server. Its constructor records the living target's entity ID in synchronized data. In the rocket tick, the missing local reference is resolved from that side's level entity table. When the resolved attachment is fall-flying, the tick directly writes the attached living entity's velocity using its look vector and current velocity:

`old + (look * 0.1 + (look * 1.5 - old) * 0.5)` component-wise.

The expression uses double constants; it has no clamp in the inspected path. The rocket then follows the entity and copies its resulting delta movement. There is no side check around the attached-entity boost in `tick`. Thus the server-only item-use producer does not make the boost itself server-only: the synchronized attached rocket can run the same direct response against its resolved client-side entity, while the authoritative server rocket tick applies it against the server-side player. The player impulse is produced by the rocket tick from that side's current look and velocity, not by copying the unattached rocket's ordinary projectile acceleration into the player.

Difference: none across these endpoints. The full item and entity source files have identical hashes, including the attached-target gate, fall-flying predicate, expression order/constants, and direct player velocity write. The inspected `LivingEntity.isFallFlying` method is unchanged (`LivingEntity.java:3084` in 1.20.2; `:3086` in 1.20.4), returning shared flag 7.

## Other era-existing launch-item paths and closure

A bounded source search of 1.20.2's `world/item` sources for direct motion writers (`setDeltaMovement`, `push`, `jumpFromGround`, `startAutoSpinAttack`, and `move(MoverType, ...)`) found only the Riptide `TridentItem` path. Firework boost is the separate rocket-entity path described above. No other era-existing launch-item direct player impulse path was identified in this search. Modern-only launch items, generic projectile hits/knockback, explosion/damage response, gliding math, and general motion-packet handling were not studied.

Closed dependencies for this assigned slice: Riptide's item gates, its additive velocity helper, auto-spin response and grounded lift; firework item acceptance/server creation, synchronized attached-target resolution, fall-flying gate and direct attached-entity velocity write. Scope left open: all other player movement inventory for this pair, exact intermediate patch boundary, runtime parity, and excluded areas listed above. No product source, wiki inventory, Git history, tests, generation, or game/server launch was accessed.