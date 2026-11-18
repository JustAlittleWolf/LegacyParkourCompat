# Independent source audit verdict

**Verdict: ACCEPTED — no 1.13 → 1.13.1 delta in the bounded active-glide/ladder-or-vine-feet slice.** The reviewed claim is limited to the pre-move write and branch order for an ordinary, dry local player already fall-flying with a usable chest-slot Elytra. It does not establish full-pair coverage or guarantee the post-move/end-of-tick `fallDistance` value.

## Bound inputs

- Frozen claim: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-113-1131-alias-gate/report.md`, SHA-256 `d5a3e7747425b86c31f96f2c91debbde1f4c8072b3f77331e346d5f395c17fad`.
- Exact source roots: `decompiled_minecraft/1.13/ornithe-feather` and `decompiled_minecraft/1.13.1/ornithe-feather`.
- Both ready manifests identify the exact release and `ornithe-feather`; both provenance records share run `prep-1.13-1.13.1-feather-2026-10-09-r1`. Resolved coordinates are `net.ornithemc:feather-gen2:1.13+build.2` and `net.ornithemc:feather-gen2:1.13.1+build.2` respectively. Provenance records identify client-jar SHA-256 values `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` and `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` respectively, matching the frozen report.
- Source-manifest SHA-256 values are `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` (1.13) and `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` (1.13.1). Each equals both the ready/provenance recorded digest and the digest of the local manifest file. Provenance-file SHA-256 values are `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` and `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`.

Relevant local source SHA-256 values match the frozen report:

| Source | 1.13 | 1.13.1 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |

## Independent trace

- **Client/server and tick route:** `LivingEntity.tickMovement` calls `flyingTick()` and then `moveRelative(...)` (1.13 `LivingEntity.java:1901–1908`; 1.13.1 `:1920–1927`). `LocalClientPlayerEntity.isLocallyControlled()` returns true (1.13 `LocalClientPlayerEntity.java:485–487`; 1.13.1 `:489–491`), satisfying the local-controlled side of `moveRelative`'s outer authority gate (1.13 `LivingEntity.java:1459–1461`; 1.13.1 `:1478–1480`). `flyingTick()` reads flag 7, retains it only for airborne, non-riding entities with a chest Elytra passing `ElytraItem.canFly`, but only calls `setFlag(7, bl)` under `!world.isClient` (1.13 `LivingEntity.java:1919–1937`; 1.13.1 `:1938–1956`). Thus this bounded local-client case consumes an already-active flag; it does not rely on client `flyingTick()` activating or clearing it.
- **Glide/climb branch order and write:** In both releases, the dry path reaches `if (this.isFallFlying())` before the alternate non-glide branch. The glide branch checks the double-valued `velocityY > -0.5` and writes the float literal `fallDistance = 1.0F` before glide velocity calculations and `move(SELF, ...)` (1.13 `LivingEntity.java:1467–1472`; 1.13.1 `:1486–1491`). At exactly `-0.5` that condition is false. The `isClimbing()` clamp and `fallDistance = 0.0F` occur only in the `else` branch (1.13 `:1518–1554`; 1.13.1 `:1537–1573`); a ladder/vine under the feet does not route an already-gliding player into it. The predicate itself checks `floor(x)`, `floor(shape.minY)`, and `floor(z)`, returning true for ladder/vine blocks at that position (1.13 `LivingEntity.java:1041–1053`; 1.13.1 `:1046–1058`).
- **Immediate post-write movement:** The glide branch immediately calls `move(SELF, velocityX, velocityY, velocityZ)` (1.13 `LivingEntity.java:1501–1504`; 1.13.1 `:1520–1523`). `Entity.move` then invokes `checkFallDamage` (both `Entity.java:696`); `checkFallDamage` clears fall distance on landing or adds the resolved downward displacement as `(float)(fallDistance - dy)` when `dy < 0` (1.13 `Entity.java:881–890`; 1.13.1 `:882–891`). The 1.0F is therefore a pre-move write, not a guaranteed final value. These movement and post-move rules match across the pair.

## Excluded neighboring claims

The local jump-to-start request is outside this already-active-glide claim: both `LocalClientPlayerEntity` versions gate it on jump, airborne state, `velocityY < 0.0`, not already gliding, and not ability-flying, then chest Elytra plus `canFly`; neither adds an `isClimbing()` exclusion (1.13 `:773–780`; 1.13.1 `:777–784`). The separate ability-flying `PlayerEntity.moveRelative` override clears fall distance/flag 7 only in that ability-flying branch (1.13 `PlayerEntity.java:1434–1458`; 1.13.1 `:1441–1465`), excluded by the ordinary-player precondition. This audit makes no activation verdict, no post-move final-value claim, and no A/B scenario claim.

