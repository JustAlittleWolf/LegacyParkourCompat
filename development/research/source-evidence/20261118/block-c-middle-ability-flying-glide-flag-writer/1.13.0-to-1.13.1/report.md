# 1.13 → 1.13.1: creative-flight toggle during Elytra glide

## Frozen source finding

**Result: no 1.13 → 1.13.1 behavioral difference found in this bounded local-player state slice.** In both releases, an airborne local player can lawfully toggle creative flight while glide flag 7 is set. On the following locally controlled movement path, the glide branch performs its real `move(SELF, vx, vy, vz)` first; the enclosing `PlayerEntity.moveRelative` then applies the creative-flight post-processing, including an unconditional clear of flag 7 and a separate reset of `fallDistance`. With dry air, no passenger, and a usable chest Elytra, the glide branch is reachable for that move while the flag is still set. Clearing it ends glide for the next travel call unless another actor sets the flag again.

This is a no-difference result for the stated operation and reachability slice only; it does not establish full-pair coverage or an exact patch introduction boundary.

## Exact source trace

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

| Operation | 1.13 | 1.13.1 | Trace |
|---|---|---|---|
| Lawful local toggle | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:756–770` | same class, `:760–774` | Under `abilities.canFly`, spectator mode forces flight on; otherwise, a jump double-tap toggles `abilities.flying` and calls `syncAbilities()` when the input/timer/swimming gates pass. The ordinary creative player uses the second branch. |
| Travel dispatch | `net/minecraft/entity/living/LivingEntity.java:1901–1908` | same class, `:1920–1926` | Travel calls `flyingTick()`, then virtual-dispatches `moveRelative(...)`; the player override receives this call. |
| Glide move | `net/minecraft/entity/living/LivingEntity.java:1467–1517` | same class, `:1486–1536` | For the stated dry case the fluid gates pass. With flag 7 true, the `isFallFlying()` branch adjusts glide velocity in source order, applies `0.99F / 0.98F / 0.99F` damping, then calls exactly one real `this.move(MoverType.SELF, this.velocityX, this.velocityY, this.velocityZ)`. Its server-only on-ground clear is not the local-client clear in question. |
| Post-move creative flight branch | `net/minecraft/entity/living/player/PlayerEntity.java:1435–1461` | same class, `:1442–1468` | When `abilities.flying && !isRiding()`, cache incoming `velocityY` and `speedInAir`; adjust air speed; call `super.moveRelative(...)` (which takes the glide branch above); then set `velocityY` to cached value `* 0.6`, restore `speedInAir`, set `fallDistance = 0.0F`, and unconditionally `setFlag(7, false)`. The clear therefore happens after the real glide move. |
| Flag meaning/writer | `net/minecraft/entity/living/LivingEntity.java:2295–2297`; `net/minecraft/entity/Entity.java:1838–1849` | same classes, `LivingEntity.java:2314–2316`; `Entity.java:1839–1850` | `isFallFlying()` reads shared flag bit 7. `setFlag(7, false)` clears only `1 << 7` from the shared-flags byte (`b & ~(1 << index)`), preserving other bits. The player override is the reached local writer; `flyingTick()` also writes the bit, but only when `!world.isClient`. |
| Fall-distance writes | `LivingEntity.java:1469–1472`; `PlayerEntity.java:1451–1455` | `LivingEntity.java:1488–1491`; `PlayerEntity.java:1458–1462` | Independently of flag clearing, the glide branch first sets `fallDistance = 1.0F` only if `velocityY > -0.5`; after the superclass movement returns, the creative-flight wrapper sets it to `0.0F` unconditionally. The latter reset is after any fall-distance work performed by the real move. |

The default usable Elytra path is guarded in `LivingEntity.flyingTick()` by flag 7, `!onGround`, `!isRiding()`, and chest-slot `Items.ELYTRA` plus `ElytraItem.canFly(itemStack)` (`1.13:1919–1937`; `1.13.1:1938–1956`). The local client does not run that method's flag writer because of its `!world.isClient` gate; its flag is read for the glide branch and then cleared by the player override. A fresh/default Elytra meets the usable-item predicate. The assignment's live-glide premise supplies the initial flag-7=true state.

## Pair identity and pinned evidence

Both source sets identify mapping namespace `ornithe-feather`, prepared by run `prep-1.13-1.13.1-feather-2026-10-09-r1`. Provenance records exact requested versions, Feather mapping coordinates `net.ornithemc:feather-gen2:1.13+build.2` and `...:1.13.1+build.2`, source manifest identities, raw client inputs, and tool versions. Canonical sources were consumed read-only; no generation or network lookup was performed.

| Evidence artifact | 1.13 SHA-256 | 1.13.1 SHA-256 |
|---|---|---|
| `ornithe-feather.provenance.json` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` |
| `ornithe-feather.ready.json` | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de` |
| `ornithe-feather.sources.sha256` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| `ornithe-feather.artifacts.sha256` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |

The `sources.sha256` manifests contain the hashes of each cited original Java file:

| Original source file (relative to version/mapping root) | 1.13 SHA-256 | 1.13.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |

The source provenance pins raw client JAR SHA-256 values `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` (1.13) and `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` (1.13.1); Feather-mapped client JAR hashes `4b91c330aaf6179f7384f70f91ebcdf72af2713cc3788fea1482c3529905a761` and `5e4305c91a3a46a929322180624ab2cf73f33e11f7ffac86ce77c882a524fcbf`; mapped Feather JAR SHA-256 values `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897` and `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`; and mapping Tiny SHA-256 values `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84` and `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14`. Pair manifests/provenance bind both to the same exact preparation run and shared cached version-manifest hash `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04`.
