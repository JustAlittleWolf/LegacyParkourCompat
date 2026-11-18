# Active Elytra glide versus climbable travel: 1.19.2 → 1.19.3

## Scope and verdict

Bounded source gate: an ordinary dry player with an Elytra in the chest slot, already carrying the fall-flying shared flag at tick entry, at ladder/vine feet. This checks the active-flight state maintenance before `LivingEntity.travel`, branch order selecting glide versus the generic/climbable path, and writers encountered up to the first glide `move`. Starting-flight eligibility, fall-distance behavior, block contact/collision production, and any resulting trajectory are not assessed here.

**Verdict for this slice:** no 1.19.2→1.19.3 change in the active-state cancellation predicates, client/server flag-write gate, or glide-before-generic/climbable branch order. For the stipulated dry client path, if the shared flag is still true when `travel` is reached, the glide branch has priority over the generic branch that calls climbable movement helpers. This conclusion conditions on the existing flag and observed `onGround` field; it does not infer how ladder/vine contact produced either value.

## Exact source identity and integrity

Both inputs are adjacent, ready-marked Vineflower trees under `decompiled_minecraft/`, namespace `mojmap`; release IDs and mapping namespace match their per-release `mojmap.provenance.json` and `mojmap.ready.json` files. These are exact-run outputs from one batch, generated 2026-10-07 with Java 25.0.3+9-LTS, Gradle 9.7.1, TinyRemapper 0.14.1, Vineflower 1.12.0, ASM 9.10.1, Gson 2.14.0, and Mapping IO 0.9.1. The provenance identifies the source roots as exact versions 1.19.2 and 1.19.3, mapping option `mojmap`, and client artifact SHA-256 values `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` (1.19.2) and `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` (1.19.3). Adjacent `artifacts.sha256` manifests pin each client mappings file and `client-mojmap.jar`: 1.19.2 `client_mappings.txt` SHA-256 `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76` and `client-mojmap.jar` SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`; 1.19.3 `client_mappings.txt` SHA-256 `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b` and `client-mojmap.jar` SHA-256 `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`. Ready markers bind those artifact manifests and source manifests. No online mappings or other discovery lanes were consulted.

Source-manifest SHA-256 (also recorded in each ready marker):
- 1.19.2 `mojmap.sources.sha256`: `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`.
- 1.19.3 `mojmap.sources.sha256`: `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`.

Exact checked source-file SHA-256 values match their source-manifest entries:
- 1.19.2 `net/minecraft/world/entity/LivingEntity.java`: `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`.
- 1.19.3 `net/minecraft/world/entity/LivingEntity.java`: `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.
- 1.19.2 `net/minecraft/world/entity/player/Player.java`: `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`.
- 1.19.3 `net/minecraft/world/entity/player/Player.java`: `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`.

## Compared path and ordered predicates

`LivingEntity.aiStep` in both versions performs `updateFallFlying()` and then calls `travel(new Vec3(...))` in that order: 1.19.2 `LivingEntity.java:2582-2589`; 1.19.3 `LivingEntity.java:2593-2600`.

The active updater is 1.19.2 `LivingEntity.java:2626-2650` and 1.19.3 `LivingEntity.java:2637-2661`; its predicates and ordering are unchanged:
1. Read local candidate `$$0 = getSharedFlag(7)`.
2. Preserve it only if `$$0` is true, `!onGround`, `!isPassenger()`, and `!hasEffect(LEVITATION)`.
3. Then require chest equipment to be Elytra and `ElytraItem.isFlyEnabled(stack)`; otherwise set the local candidate false.
4. Write shared flag 7 only under `!level.isClientSide`.

For the client movement being compared, that last gate matters: a failed updater predicate changes the local candidate but does not clear the client shared flag here. Given the stipulated true flag at tick entry and no intervening writer, `isFallFlying()` still reads that bit at travel. The server counterpart writes the candidate, but this task concerns the client movement path and does not claim server historical physics.

Both `Player.travel` implementations first handle swimming, then take the creative-flight wrapper only if `abilities.flying && !isPassenger()`; otherwise they call `super.travel`. The wrapper and its flag clear are 1.19.2 `Player.java:1464-1494` and 1.19.3 `Player.java:1448-1478`. Under the ordinary/non-creative assumption, the call proceeds to `LivingEntity.travel` without that wrapper's later flag clear.

For dry travel, the relevant outer branch order in 1.19.2 `LivingEntity.java:2042-2178` and 1.19.3 `LivingEntity.java:2052-2185` is the same: water handling; lava handling; `else if (isFallFlying())` glide; final generic movement branch. The glide branch performs its `move(MoverType.SELF, ...)` before its later grounded-server flag clear (1.19.2 lines 2135-2149; 1.19.3 lines 2142-2156). The final generic branch calls `handleRelativeFrictionAndCalculateMovement` (1.19.2 line 2154; 1.19.3 line 2161), which calls `handleOnClimbable` and climbable predicates (1.19.2 lines 2194-2204; 1.19.3 lines 2201-2211). Therefore, while the client shared flag remains true, this branch order selects glide and skips that generic climbable movement path for the tick. `onClimbable`'s feet-block predicate itself is unchanged: 1.19.2 `LivingEntity.java:1462-1477`; 1.19.3 `LivingEntity.java:1464-1479`. No contact/collision calculation was compared.

## State-writer boundary and excluded delta

Before the first glide move, the tick path's only direct shared-flag write is the server-gated `setSharedFlag(7, candidate)` at the end of `updateFallFlying`; it is reached after the ordered predicates above. The glide branch's grounded-server clear is after its first `move`, so it is beyond this report's cutoff. `Player.stopFallFlying()` still writes flag 7 true and then false in both versions (1.19.2 `Player.java:1618-1621`; 1.19.3 `Player.java:1597-1600`), but is not a call in the traced `aiStep → updateFallFlying → travel` sequence. Start eligibility (`Player.tryToStartFallFlying`) was deliberately not evaluated.

The glide body has a separate fall-distance delta: 1.19.2 `LivingEntity.java:2107-2112` conditionally assigns the field when vertical velocity exceeds its threshold; 1.19.3 `LivingEntity.java:2117-2119` calls `checkSlowFallDistance()` instead. Fall-distance semantics are explicitly outside this assignment and no claim about that change is made here.

## Limits

This is a paired bounded source trace, not full-pair tick coverage or an independent audit. It does not determine whether a ladder/vine feet position has `onGround` true, how collision/contact creates that value, or predict post-move motion. Intermediate patch boundaries are unknown. The assigned active-state and branch-order gates show no difference in these two exact releases.

