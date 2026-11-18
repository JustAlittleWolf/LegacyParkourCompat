# 1.17 → 1.17.1: active Elytra at climbable feet

## Bounded verdict

For an ordinary client-controlled player whose Elytra flag is already active, who is dry, airborne, not a passenger, has no Levitation, and has an enabled Elytra in the chest slot, the reviewed route is behaviorally the same in 1.17 and 1.17.1 when the feet block is a ladder or vine. No 1.17→1.17.1 difference was found in the flag-preservation gate, branch order, `fallDistance` threshold/write, or climbable cleanup reachability. This is only a verdict for this bounded route; it does not close pair coverage or establish an implementation cutover.

## Ordered source trace

Line numbers below refer to `decompiled_minecraft/<version>/mojmap/`.

1. **Flag carried into travel.** `LivingEntity.aiStep` damps `xxa` and `zza`, calls `updateFallFlying()`, then calls `travel(...)` (1.17: `LivingEntity.java:2537–2542`; 1.17.1: `:2541–2546`). In both versions, `updateFallFlying` begins with the existing shared bit 7. It retains `true` only while `!onGround && !isPassenger() && !hasEffect(LEVITATION)` and the chest-slot item is an enabled Elytra (1.17: `:2580–2605`; 1.17.1: `:2584–2609`). It has no ladder/vine or `onClimbable()` gate. On the logical client its final `setSharedFlag(7, bl)` is skipped by `if (!level.isClientSide)`, so a qualifying already-active bit remains set for the following call. `isFallFlying()` reads that bit directly (1.17: `:3009–3011`; 1.17.1: `:3013–3015`).

2. **Travel entry and ordered branch selection.** Both `LivingEntity.travel` implementations enter their body only for `isEffectiveAi() || isControlledByLocalInstance()` (1.17: `:1999–2000`; 1.17.1: `:2003–2004`). Before branch selection, `getDeltaMovement().y <= 0.0` sets the local `bl`; only if that is true *and* SLOW_FALLING is present does the method set `d = 0.01` and write `fallDistance = 0.0F` (1.17: `:2001–2006`; 1.17.1: `:2005–2010`). In the stated no-effect default, that write does not occur. The ordered chain tests water first, lava second, then `isFallFlying()` (1.17: `:2008–2064`; 1.17.1: `:2012–2068`). Under the dry, active-state assertions, water and lava fail and the Elytra branch is selected. `Player.travel` delegates to `super.travel` unless abilities flight applies; the stated ordinary player takes that delegation (1.17: `Player.java:1433–1447`; 1.17.1: `:1424–1438`).

3. **Pre-move threshold and write.** At the top of the selected branch, `vec36` captures the current delta movement. The strict test is `vec36.y > -0.5`; on true, it writes the exact float literal `this.fallDistance = 1.0F` before computing the look-angle glide vector, setting movement, and calling `move` (1.17: `LivingEntity.java:2064–2093`; 1.17.1: `:2068–2097`). Equality at `-0.5` does not perform this write. The condition uses the captured pre-glide vertical velocity. If SLOW_FALLING had performed the earlier zero write, a passing `>-0.5` test would replace it with `1.0F` here.

4. **Immediate movement-side update.** `move` resolves movement and calls `checkFallDamage` before returning (both `Entity.java:534–566`). Both versions' `Entity.checkFallDamage` then reset `fallDistance` to `0.0F` when `onGround` is true; otherwise, if resolved `d < 0.0`, they write `(float)(fallDistance - d)` (both `Entity.java:998–1010`). `LivingEntity.checkFallDamage` reaches this superclass logic after its client/server-gated prelude (both `LivingEntity.java:286–307`). Thus the `1.0F` write is pre-move, not an unconditional end-of-tick value: a grounded result clears it, a negative resolved Y move adds its magnitude with the shown float cast, and a non-grounded nonnegative resolved Y move leaves it unchanged. No source-only trajectory is asserted for a particular collision resolution.

5. **Climbable cleanup does not take this route.** `onClimbable()` reads the feet state and recognizes `BlockTags.CLIMBABLE` (so a tagged ladder/vine qualifies) in both versions (1.17: `LivingEntity.java:1423–1438`; 1.17.1: `:1427–1442`). The cleanup that writes `fallDistance = 0.0F` is `handleOnClimbable`, reached from `handleRelativeFrictionAndCalculateMovement` in the final ordinary travel `else` branch (1.17: `:2107–2135`, `:2151–2154`, `:2179–2193`; 1.17.1: `:2111–2139`, `:2155–2158`, `:2183–2197`). The preceding `else if (isFallFlying())` branch excludes that route, so ladder/vine cleanup does not override the Elytra branch's state. The `onGround`/fall-distance updates inside the subsequent `move` call still apply as described above.

The separate initial-start predicate `Player.tryToStartFallFlying` requires `!onGround`, not already fall-flying, not in water, no Levitation, and an enabled chest-slot Elytra (1.17: `Player.java:1551–1561`; 1.17.1: `:1542–1552`). It also has no climbable predicate, but the scenario here assumes the flag was already active, so the preservation path above is the operative gate.

## Start and final assertions

- **Start:** local client player; travel control gate true; shared bit 7 true from the preceding state; airborne, not passenger, no Levitation; dry; enabled chest Elytra; feet state tagged climbable; no ability flight. For the pre-move literal write, additionally require the captured vertical velocity to be strictly greater than `-0.5`. The comparison does not assume horizontal collision or a particular movement result.
- **Final:** the same active Elytra branch is entered in both versions. If the strict Y test passes, `1.0F` is written before `move`. The post-move fall distance follows `checkFallDamage`: clear on `onGround`; on the non-grounded negative-Y case use `(float)(oldFallDistance - resolvedDeltaY)`; otherwise retain it. `handleOnClimbable` is not reached on this branch. The exact final value is therefore conditional on resolved movement; source alone does not establish that value for every ladder/vine contact.

## A/B source and environment identity

| Side | Exact source and mapping identity | Provenance/environment evidence |
| --- | --- | --- |
| A | 1.17, Mojmap; client SHA-256 `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`; official `client.txt` SHA-256 `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471`; version metadata SHA-256 `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`. | Hidden source-local `.decompile-provenance.json`, SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`; records `lpc-decompile-v1`, Adoptium `25.0.3+9-LTS`, Windows 11 amd64, Vineflower 1.12.0 and tool/input hashes. The three reviewed source hashes below match its per-file entries. |
| B | 1.17.1, Mojmap; client SHA-256 `a49b4a56c5bbe15c9ed9fe53efa9a591f265a1f5ba7d6aa9739a58ea7a92b79d`; `client_mappings.txt` SHA-256 `2b28ded68f8602aaf2f35ef92dd10ee41ba2cf9723e29570155d60e1723848e9`; `client-mojmap.jar` SHA-256 `2a2be036174902e447865498741b8c59fa2e090d352d786a8507dccb7c23008c`. | Adjacent provenance records exact version and `mappingOption: mojmap`, Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, the exact run paths, and artifact/source-manifest identities. Ready-marker SHA-256 `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; source-manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; artifact-manifest SHA-256 `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`. Its ready metadata does not record OS/runtime vendor; no cross-environment reproducibility claim is made. |

Reviewed class SHA-256s (source file hashes; A values agree with hidden provenance, B values agree with the listed source manifest):

| Class | 1.17 | 1.17.1 |
| --- | --- | --- |
| `net/minecraft/world/entity/Entity.java` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `net/minecraft/world/entity/LivingEntity.java` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `net/minecraft/world/entity/player/Player.java` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |

## Limits

This compares only the named Elytra/climbable route in the exact 1.17→1.17.1 pair. It does not assess other tick slices, prove when any behavior was introduced within the interval, run a trajectory, or claim pair freeze, acceptance, implementation, or runtime parity. It does not propose a correction beyond the current bounded review.
