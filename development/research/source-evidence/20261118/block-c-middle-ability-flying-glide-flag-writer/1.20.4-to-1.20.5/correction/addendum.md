# Signed source report correction / addendum

**Binds original frozen report SHA-256:** `4f54e836291863c44ed5c0bdddc802ad0c90d4886895537d88434be7c9762fb9`  
**Binds independent audit SHA-256:** `6f5ec5d670ac1e9705eca2a2188560ea3383fa0262529c246f1408be2ace1ddb`

## Correction: grounded client can still consume the live glide flag

The original report's grounded-case sentence was too strong. `LivingEntity.updateFallFlying` computes a local `false` when the player is grounded, but its final `setSharedFlag(7, $$0)` is guarded by `!level().isClientSide`. On the local client, this computation does **not** clear the synchronized shared flag. If bit 7 remains true at that point, `LivingEntity.travel` can still select its `isFallFlying()` branch; that branch condition has no on-ground guard. The local player's `Player.travel` then clears bit 7 after `super.travel` and after resetting fall distance.

Exact order is present in both compared releases:

- **1.20.4 Mojmap:** `LivingEntity.aiStep`, `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:2593-2603`, invokes `updateFallFlying()` before virtual `travel(...)`. `updateFallFlying`, lines `2641-2665`, sets its local result false when grounded (`2643`, `2659-2661`) but only writes shared flag 7 on the logical server (`2663-2664`). `LivingEntity.travel`, lines `2099-2125`, chooses glide using `isFallFlying()` and moves before its server-only landing clear. `Player.travel`, `decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/player/Player.java:1463-1469`, calls `super.travel`, resets fall distance, then clears shared flag 7 for flying nonpassengers.
- **1.20.5 Mojmap:** `LivingEntity.aiStep`, `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/LivingEntity.java:2673-2683`, retains update-before-travel ordering. `updateFallFlying`, lines `2721-2745`, has the same grounded calculation and server-only write gate (`2723`, `2739-2744`). `LivingEntity.travel`, lines `2168-2194`, likewise selects glide from `isFallFlying()` without an on-ground guard. `Player.travel`, `decompiled_minecraft/1.20.5/mojmap/net/minecraft/world/entity/player/Player.java:1485-1491`, retains the same post-`super.travel`, post-fall-reset bit-7 clear.

Thus, **if** an old synchronized true bit is present on the local client while grounded and flight is enabled, the existing bit can still select one glide-branch movement before `Player.travel` clears it. That conditional describes source reachability from the stated state; this review does not establish how often ordinary client/server scheduling produces the grounded stale-bit state. For the airborne scoped state, the result remains the same: glide movement occurs before the local `Player.travel` clear. There is no 1.20.5 change to this clear scheduling or to the glide predicate.

## Separately confirmed 1.20.5 toggle delta

The local ability toggle remains a distinct release difference: `LocalPlayer.aiStep` in 1.20.5 (`decompiled_minecraft/1.20.5/mojmap/net/minecraft/client/player/LocalPlayer.java:728-739`) sets `abilities.flying` and immediately calls `jumpFromGround()` if flight became enabled while grounded, before `super.aiStep()` reaches the movement sequence above. The corresponding 1.20.4 toggle (`decompiled_minecraft/1.20.4/mojmap/net/minecraft/client/player/LocalPlayer.java:725-733`) has no such call. This is the grounded toggle's new movement impulse, not a change to flag clearing. No broader parity claim is made here.

## Evidence identities

The exact Mojmap source identity and manifests are unchanged from the original report. Hashes below are SHA-256; each relied-on Java file hash matches its corresponding `mojmap.sources.sha256` entry.

| Input | SHA-256 |
|---|---|
| 1.20.4 source manifest | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` |
| 1.20.5 source manifest | `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` |
| 1.20.4 `LivingEntity.java` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| 1.20.5 `LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.20.4 `Player.java` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| 1.20.5 `Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.20.4 `LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| 1.20.5 `LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |

The source input is sufficient for this correction; the ordinary-client scheduling frequency of a grounded stale synchronized bit remains unmeasured and is outside this bounded static review.
