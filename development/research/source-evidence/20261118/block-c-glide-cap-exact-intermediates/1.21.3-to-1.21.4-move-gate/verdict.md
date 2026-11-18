# Independent exact-source audit verdict

## Bound input and result

Audited report: `c-glide-1213-1214-cap-route-exact/report.md`  
Input SHA-256: `9B1F305A38C514FA6A239CE7B8F446EEBE922F644CAC702AC3493B57782B6C67`  
Pair: Minecraft 1.21.3 → 1.21.4, Mojmap.  
Verdict: **The bounded movement claims are supported, with a required correction to the source-identity statement.** This accepts the route and `Entity.move` claims below, not full-pair coverage or runtime parity.

## Evidence identity

The six source-file hashes listed by the input report were recomputed from the primary checkout and match byte-for-byte. Each is also present in its version’s adjacent `mojmap.sources.sha256`. Those source-manifest SHA-256 values match `mojmap.ready.json`’s declared `sourceManifestSha256`:

- 1.21.3: `mojmap.sources.sha256` `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`; `artifacts.sha256` `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`.
- 1.21.4: `mojmap.sources.sha256` `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; `artifacts.sha256` `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`.

Both version directories also contain `mojmap.provenance.json` and `mojmap.ready.json`: for 1.21.3 their SHA-256 values are `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7` and `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`; for 1.21.4, `ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9` and `1c6617b7acf8bb357f77c6c6c276881c73d882bda1f93f6dd217bb54eb8aa4be`. The adjacent provenance records name exact version and `mojmap`; ready records name the exact release/mapping and client hash. Thus the input report is right that the files named `.decompile-provenance.json` are absent, but wrong that **no manifest, client-artifact, or mapping-artifact identity is available**. These adjacent records and pinned manifests provide those identities. Their raw client/mapping JAR bytes were not independently rehashed in this audit; the direct behavioral evidence is bound to the verified source-file bytes above.

The source bytes independently checked for this audit are:

| Version | Relative Mojmap source | SHA-256 |
|---|---|---|
| 1.21.3 | `net/minecraft/world/entity/player/Player.java` | `A803203E92AA4729D5F5C9B16085B6A43CE51D9907D309EB96736E9C7C1340DE` |
| 1.21.4 | `net/minecraft/world/entity/player/Player.java` | `C45F41B9784CE50A88A498E84A13EDCEF0A23E175233D726ED7F90940F19EBC1` |
| 1.21.3 | `net/minecraft/world/entity/LivingEntity.java` | `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52` |
| 1.21.4 | `net/minecraft/world/entity/LivingEntity.java` | `E62CE650AF0A5AC97E4D0A2BA7CAD4BA68A8D098525609F8B5B6976A8AB0AE30` |
| 1.21.3 | `net/minecraft/world/entity/Entity.java` | `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9` |
| 1.21.4 | `net/minecraft/world/entity/Entity.java` | `05F18EF2EC0413FC010230407C812A11553EB5123B68D21B5D7B2C0C175698AD` |

## Claim verdicts

1. **Active glide update and dry route: supported, no adjacent difference established.** In both `LivingEntity.aiStep`, `updateFallFlying()` runs before `travel(...)` (`1.21.3` lines 2755–2772; `1.21.4` lines 2766–2783). `updateFallFlying()` calls `checkSlowFallDistance()` first; its `canGlide()` cancellation branch is server-only (`1.21.3` lines 2814–2820; `1.21.4` lines 2825–2831). For a dry, already-active, ride-free local player, `travel()` selects `travelFallFlying()` before ordinary air travel (`1.21.3` lines 2178–2187; `1.21.4` lines 2189–2198); that route updates glide movement and invokes `move(SELF, ...)` directly (`1.21.3` lines 2268–2276; `1.21.4` lines 2279–2287). It does not pass through ordinary air travel’s `handleOnClimbable` call. The climbable predicate’s ordinary feet-block/tag rule is in `LivingEntity.onClimbable()` (`1.21.3` lines 1561–1568; `1.21.4` lines 1575–1582). `Player.onClimbable()` is newly overridden in 1.21.4 to suppress the predicate for abilities flight (`Player.java` lines 2171–2173), which does not affect the stated on-foot, non-abilities-flying witness. A visual ladder/vine overlap alone is not enough to establish the true predicate case.

2. **Slow-fall-distance cap: supported unchanged.** In `Entity.checkSlowFallDistance()`, both versions test `deltaMovement.y() > -0.5` and `fallDistance > 1.0F`, then assign `1.0F` (`1.21.3` lines 2484–2488; `1.21.4` lines 2512–2516). Its call remains before glide cancellation and before the tick’s travel move.

3. **Direct client-player accumulation in `Entity.move`: supported as a bounded state-write difference.** Both `move` bodies resolve collision into `$$3` and set collision/on-ground state before the fall-damage gate. In 1.21.3, the gate is `!level().isClientSide() || isControlledByLocalInstance()` and passes the resolved `$$3.y` to `checkFallDamage` (`Entity.java` lines 667–672). In 1.21.4, the gate adds `&& !isControlledByClient()` (`lines 679–683`); local-player vertical collision bookkeeping has its separate `isControlledByOrIsLocalPlayer()` guard (`lines 667–671`). `Player.isControlledByClient()` returns true in both releases (`Player.java`: 1.21.3 lines 2145–2148; 1.21.4 lines 2165–2168). `LocalPlayer.isEffectiveAi()` returns true in both, making the 1.21.3 local-instance gate reachable (`LocalPlayer.java`: 1.21.3 lines 478–480; 1.21.4 lines 483–485). The unchanged base `Entity.checkFallDamage` adds `-(float)$$0` to the `Entity.fallDistance` field only for a negative resolved vertical delta when not on ground (`1.21.3` lines 1216–1232; `1.21.4` lines 1237–1253). Therefore the local player’s `Entity.move` no longer performs that accumulation on the 1.21.4 client path. This is a direct move-time state-write conclusion only; it does not establish total tick fall-distance behavior after other writers, contacts, or subsequent ticks.

## Scope limits

This audit covers the reported dry, active, on-foot Elytra route and its cap plus the direct `Entity.move` fall-distance gate/state write. It does not analyze damage calculation or collision shapes, and does not establish runtime parity, endpoint behavior, server simulation, fluids, ridden movement, custom gliders, full-pair tick coverage, or an intermediate patch boundary.

