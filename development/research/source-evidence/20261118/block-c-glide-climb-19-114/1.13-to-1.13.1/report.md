# 1.13 → 1.13.1: active Elytra glide at ladder/vine feet

## Bounded result

No 1.13-to-1.13.1 behavior delta was found in the assigned slice. For an ordinary, dry, already-fall-flying local player with a usable Elytra in the chest slot, the glide branch precedes and bypasses the non-gliding climb cleanup in both releases. If the pre-move vertical velocity is strictly greater than `-0.5`, it writes `fallDistance = 1.0F` before glide velocity updates and movement. The `0.0F` climb reset exists only in the alternate, non-gliding branch, so that reset does not overwrite this pre-move write. At exactly `-0.5`, the threshold does not write `1.0F`. The subsequent `move(SELF, ...)` can still update fall distance: `checkFallDamage` resets it on landing, or adds `-dy` when the resolved displacement is downward (1.13 and 1.13.1 `Entity.java` lines 881–890). Thus `1.0F` is a pre-move write, not a guaranteed end-of-tick value; this later movement update is the overwrite boundary and is also unchanged across the pair.

This records only the assigned glide/climb slice. It is not full-pair coverage or an independent audit.

## Tick order and authority

In both releases, the living tick calls `flyingTick()` and then `moveRelative(...)` (`LivingEntity.java`: 1.13 lines 1901–1908; 1.13.1 lines 1920–1927). `flyingTick()` reads shared flag 7, keeps it true only when airborne, not riding, and wearing an Elytra for which `ElytraItem.canFly` succeeds; the only direct `setFlag(7, bl)` in that method is guarded by `!world.isClient` (1.13 lines 1919–1937; 1.13.1 lines 1938–1957). Thus this slice consumes an already-active flag on the local client; ordinary client travel does not set flag 7 in `flyingTick`.

The local-player tick requests `START_FALL_FLYING` when jump is pressed, the player is not in the controlling state represented by `bl`, is airborne, `velocityY < 0.0`, is not already gliding, and is not ability-flying; it then checks chest-slot Elytra plus `ElytraItem.canFly` and sends the packet. There is no `isClimbing()` exclusion in either release (1.13 `LocalClientPlayerEntity.java` lines 773–780; 1.13.1 lines 777–784). This start request is distinct from the assigned already-active-glide case.

The `moveRelative` glide path is entered from the `isFallFlying()` branch before the ordinary movement `else`. It first applies `if (velocityY > -0.5) fallDistance = 1.0F`, then computes Elytra motion, multiplies velocity by `0.99F`, `0.98F`, `0.99F`, and calls `move(SELF, ...)` (1.13 `LivingEntity.java` lines 1467–1517; 1.13.1 lines 1486–1536). The climb clamp/reset is inside the opposite branch: `isClimbing()` clamps horizontal velocity to ±`0.15F`, writes `fallDistance = 0.0F`, clamps downward `velocityY` to `-0.15`, then handles sneaking; afterward it moves and may set `velocityY = 0.2` on horizontal collision (1.13 lines 1518–1559; 1.13.1 lines 1537–1577). That code is not reached during the glide branch, even when the player’s foot block makes `isClimbing()` true.

For ladder/vine contact, `isClimbing()` samples `floor(x)`, `floor(shape.minY)`, `floor(z)`, returns false for spectator players, and recognizes Ladder or Vine at that position (with its existing trapdoor case) in both versions (1.13 lines 1041–1053; 1.13.1 lines 1046–1058). This predicate is not consulted by the glide branch.

The PlayerEntity override has the same relevant direct flag write in both versions: when ability-flying and not riding, it delegates to `super.moveRelative`, then clears fall distance and flag 7; the ordinary non-ability-flying path delegates without clearing flag 7 (1.13 lines 1434–1458; 1.13.1 lines 1441–1465). The assumed Elytra-gliding case takes this latter path.

## Exact local source identity

Canonical pair: `decompiled_minecraft/1.13/ornithe-feather` and `decompiled_minecraft/1.13.1/ornithe-feather` (Ornithe Feather, not an alias). Both ready manifests report `status: ready`, exact version IDs, Vineflower and Java 25.

| Release | Feather coordinate | Client SHA-256 | Source-manifest SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.13 | `net.ornithemc:feather-gen2:1.13+build.2` | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` |
| 1.13.1 | `net.ornithemc:feather-gen2:1.13.1+build.2` | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` |

The source-manifest file hashes above match the provenance manifest's recorded values. The exact relevant decompiled source file SHA-256 values are:

| Source file | 1.13 | 1.13.1 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |


