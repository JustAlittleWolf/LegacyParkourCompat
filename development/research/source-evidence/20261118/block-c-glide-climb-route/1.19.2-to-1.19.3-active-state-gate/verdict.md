# Independent source-gate audit: active Elytra glide versus climbable travel

## Verdict

**Accepted for the bounded active-state and travel-branch claim, with the client/server gate stated explicitly below.** The frozen report correctly finds no 1.19.2→1.19.3 change to `updateFallFlying`'s candidate predicates/write gate or to the glide-before-generic branch order. This does not establish the source of the stipulated flag, the contact/collision path, a post-move trajectory, an intermediate patch boundary, full-pair coverage, or runtime parity.

## Input identity and exact-source integrity

Frozen input: `report.md`, SHA-256 `46beb961fb7f98cb9a6889d4a5b16865188021ab7ea122d6596383d9668b6fa8` (matches the assigned identity).

The canonical local trees are `decompiled_minecraft/1.19.2/mojmap/` and `decompiled_minecraft/1.19.3/mojmap/`. Their ready markers and provenance agree on exact release IDs and `mojmap`; both are ready. The source-manifest hashes match their ready markers: 1.19.2 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; 1.19.3 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`. The artifact-manifest hashes also match their markers: 1.19.2 `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`; 1.19.3 `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`.

Both report-cited Java files match their manifest entries and their recorded file digests:

- 1.19.2 `LivingEntity.java` `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`; `Player.java` `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`.
- 1.19.3 `LivingEntity.java` `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`; `Player.java` `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`.

Ready/provenance client artifact IDs match the report: 1.19.2 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`; 1.19.3 `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`. The mapping and `client-mojmap.jar` digests in both artifact manifests also match the report. These are exact-version local provenance checks; no online mappings were used.

## Gate trace and causal bounds

The ordered call is `aiStep → updateFallFlying → travel` on each side: 1.19.2 `LivingEntity.java:2582-2589`; 1.19.3 `LivingEntity.java:2593-2600`. `isFallFlying()` reads shared flag 7 in 1.19.2 `LivingEntity.java:3048-3049` and 1.19.3 `LivingEntity.java:3063-3064`.

The updater's candidate is the prior shared flag, then requires `!onGround`, `!isPassenger()`, no Levitation, and enabled Elytra in the chest slot; the candidate is stored only under `!level.isClientSide`. Exact ranges: 1.19.2 `LivingEntity.java:2626-2650`; 1.19.3 `LivingEntity.java:2637-2662`. The predicates and ordering are unchanged. Causally, on the **client**, a true flag at entry remains true through this updater even if its local candidate becomes false, because this updater performs no client-side flag write. Thus, subject to no other intervening writer, the travel branch still sees the true flag. On the **logical server**, the candidate is written: an invalid gate (including `onGround`) clears flag 7 before travel; a valid active candidate remains true. This role-dependent write behavior is the same in both releases. This is a flag/update comparison, not a claim that historical physics runs on the server.

For an ordinary, non-swimming, dry player, `Player.travel` reaches `super.travel` unless the creative `abilities.flying && !isPassenger()` wrapper applies. That wrapper and its post-super flag clear are unchanged: 1.19.2 `Player.java:1464-1494`; 1.19.3 `Player.java:1448-1478`. Under the stipulated ordinary-player assumption, `LivingEntity.travel` tests water, lava, then `isFallFlying()` before its generic `else`: 1.19.2 `LivingEntity.java:2042-2178`; 1.19.3 `LivingEntity.java:2052-2185`. The glide branch calls its first `move(MoverType.SELF, ...)` at 1.19.2 line 2136 and 1.19.3 line 2143. Its `onGround && !level.isClientSide` flag clear is after that move (1.19.2 `2147-2149`; 1.19.3 `2154-2156`). The generic branch, skipped when the flag remains true, reaches `handleRelativeFrictionAndCalculateMovement`, which applies `handleOnClimbable` and then its climbable predicate (1.19.2 `2194-2204`; 1.19.3 `2201-2211`). The unchanged feet-block check is 1.19.2 `1462-1477` and 1.19.3 `1464-1479`. Contact production and whether a particular collision establishes `onGround` remain outside this trace.

## Separate fall-distance delta; activation remains excluded

This is a distinct operation from the accepted branch-order claim. In the 1.19.2 glide branch, `LivingEntity.java:2108-2111` assigns `fallDistance = 1.0F` whenever vertical velocity `y > -0.5`; it can therefore set a value below 1 upward to 1, or reduce a value above 1 to 1. In 1.19.3, the branch calls `checkSlowFallDistance()` (`LivingEntity.java:2117-2119`); `Entity.java:2147-2151` assigns 1 only when `deltaMovement.y() > -0.5` **and** the existing distance is `> 1.0F`. At or below 1 it leaves the field unchanged. The frozen report correctly excludes this difference from its active-state/branch verdict; it should not be read as a no-difference finding for fall-distance behavior.

Upstream activation is also outside the claim: 1.19.2 `Player.java:1602-1616` and 1.19.3 `Player.java:1581-1595` contain `tryToStartFallFlying` eligibility and the setter. The audit accepts only the stipulated already-true flag at tick entry and does not infer how it became true. The odd two-write `stopFallFlying` method is also not called in the traced sequence (1.19.2 `Player.java:1618-1621`; 1.19.3 `Player.java:1597-1600`).

## Limits

Static exact-source audit only. No tests, game launch, runtime comparison, or build was performed. No claim is made about the climbable tag's contact/collision production, a realized ladder/vine `onGround` value, upstream flight activation, fall-distance parity, trajectory after the first glide move, the full 1.19.2→1.19.3 tick graph, or the exact patch where any boundary changed.

Verdict content SHA-256 (UTF-8 content above this line): `3019eea991048dbe2f3bf51f554befc2e4dab649571f317575e86caf27595dee`.
