# Climbable applicability: glide fall-distance write

**Terminal: COMPLETED** — bounded source applicability follow-up. **Conclusion: yes.** An already-active, ordinary dry fall-flight can enter the 1.18.2/1.19.2 `fallDistance = 1.0F` write with the player's feet block state ladder/vine-climbable, provided the flight remains active and airborne and vertical velocity is strictly greater than -0.5. Climbable geometry does not exclude this write. No claim is made about starting a glide while on a climbable.

## Scope and frozen input

The only accepted source-only prior verdict consulted was `glide-fall-distance-audit/verdict.md`, SHA-256 `b172d90f1343a78ea4bfda2b8b3559dcd5e03f0d791bf81a1a7988b374dcccf3`. Its accepted claim is the 1.19.2 → 1.19.3 Mojmap glide fall-distance change. I read only that verdict and the exact canonical Mojmap sources plus outer provenance/ready manifests for 1.18.2, 1.19.2, 1.19.3, 1.19.4 and 1.21.5. No product, wiki, Git, or backlog material was consulted. No source generation, code, tests, or launches were performed.

For each source version, the ready marker and provenance file are the corresponding `decompiled_minecraft/<version>/mojmap.ready.json` and `mojmap.provenance.json`; the source identities below are SHA-256 of the canonical `LivingEntity.java` and `Entity.java` bytes. Mapping family is Mojmap throughout.

| Version | `LivingEntity.java` SHA-256 | `Entity.java` SHA-256 | Ready marker SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.18.2 | `DB4168D531CAF18F22E3FEFD073365E776DA4075CE01452BB9F7671D9B458782` | `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A` | `D8057D47468C37880DE38D658E95070EC3B750305B7856189997604822480946` | `CE14D5DFC2D51CFFA7B1512878D908E37C930D414147BE24E69439A7F1061CA9` |
| 1.19.2 | `FEE2DF5155449098556A138D2530D06B7A35979E43C6AA0AE1FFFD6251853E77` | `759DE9CDED43BD882AFCF5B7023BCF804D92419ACB656B493F3B490C83EB18B6` | `90F5A351C60A1AAB160B640567B716BBC563E62D92E7E555DF55C4CE7952492C` | `C0C148D3C3A0092ADC59C29C25F2106F3A5398156A4F266882384804B3534280` |
| 1.19.3 | `2B8BEFDC406176E01672175451DD480B14DA0B52BFE11A982D368909C05E73DB` | `8C35BDEF3BF2DE7B70AC76AD767BA2C194305E0DDA2E03FDF6544983667ADC32` | `C1C6ABC850D52B0DAC399EF6AC68974FB15750CB3BDCED2F947FAD21C79289B6` | `F733352C574A2F70F9218F709DCAAF6B19A9D968D5FA19AAD4E25C2EED25A9FE` |
| 1.19.4 | `C8D91AF61F87AAA1666DE79696D7CD9D4A8212D05F873BDAF28D7BB2926BF165` | `3667FEE610CBC5F58012E3A8FB8D6C4849F649FB7FE5300595158112D8B4B58B` | `59F59AB516D41EB97E09D63256EE26A68C15102052BF7ACAFDBD5440B72547D0` | `0EFCFC6075852711CFF5D62C3177258CBA273C206C8A2D2642BDF3487C676B34` |
| 1.21.5 (dependency only) | `A8AED863D4FDC515C751DD2878A8BB9C13179228CB8DBB50EDF1D19CD5404271` | `FC177733E9CC4B2D5CF2562E5529D0F4E0B9C3690F081FBD58AA9119EB173DC4` | `5F65FB143209ABCC6E28E77A7150AECEFDBC9DE00B6EF3FC924E53BC0E1A667E` | `B0832A4E1507BD74AC300FAE2FCA78668964B21EF2D05BE8CFF01B9D1D60DA8E` |

## Reachability in 1.18.2 and 1.19.2

Both `LivingEntity.onClimbable()` methods read `getFeetBlockState()` and return true for `BlockTags.CLIMBABLE` (1.18.2 lines 1433–1447; 1.19.2 lines 1462–1476). Vines and ladders are represented through that climbable tag. This is the geometry condition in the question.

The fall-flight state validation is independent of that predicate. In 1.18.2 `updateFallFlying()` at lines 2590–2610, and in 1.19.2 at 2626–2646, an existing shared fall-flight flag remains eligible when the entity is airborne, not a passenger, has no Levitation, and has an enabled Elytra in the chest slot. Neither method calls `onClimbable()`. Their server-only flag write also does not add a climbable check. This establishes continuation of an already active flight; it does not establish how the flight was started.

`travel()` is reachable for effective AI or the locally controlled instance (1.18.2 line 2010; 1.19.2 line 2043). In dry conditions the water and lava branches do not take precedence. The existing `isFallFlying()` branch then writes `fallDistance = 1.0F` when the current movement vector's `y > -0.5` (1.18.2 lines 2074–2078; 1.19.2 lines 2107–2111). The glide branch does not require `!onClimbable()` and does not invoke the ordinary climbable movement helper that resets fall distance. Therefore the same tick state can satisfy both `getFeetBlockState().is(BlockTags.CLIMBABLE)` and the old glide write. The strict boundary remains `y == -0.5` excluded.

This is a source reachability result, not a statement that a ladder or vine causes the glide or that its collision geometry guarantees any trajectory. Other preconditions still apply: the player must enter this travel branch with flight active, be dry, and be in the local/effective travel path. The cited accepted 1.19.2→1.19.3 verdict separately establishes the locally controlled dry witness and excludes Slow Falling for its accepted scope.

## Later climbable checks, only as dependencies

In 1.19.3 and 1.19.4, the glide travel branch retains the `isFallFlying()` entry. The later operation is now `checkSlowFallDistance()` (1.19.3 `LivingEntity.java:2117–2119`; 1.19.4 `:2048–2050`), which caps only when vertical movement is above -0.5 **and** fall distance exceeds `1.0F`. It still has no climbable exclusion at the entry. The nearby `horizontalCollision && onClimbable()` handling inside the glide branch affects the subsequent velocity response, not whether the earlier glide fall-distance operation was entered. Likewise, `onClimbable()` used by normal climb movement is not a gate on this glide operation.

1.21.5 is the first checked dependency here with an explicit climbable gate in the fall-flight travel operation: `travelFallFlying()` at `LivingEntity.java:2301–2308` delegates to `travelInAir()` and calls `stopFallFlying()` when `onClimbable()` is true; only the `else` performs fall-flight movement. This later organization must not be projected backward onto 1.18.2/1.19.2. It also does not establish that 1.21.5 never changes fall distance on that tick: `updateFallFlying()` calls `checkSlowFallDistance()` before its server-side can-glide handling (`:2815–2821`), and ordinary climb handling has its own reset (`:2422+`). Those are separate operations.

## Bounded result

**Same-state applicability: established for 1.18.2 and 1.19.2.** Ordinary ongoing dry flight can enter the old strict `> -0.5` reset while the feet block state is climbable. Startup restrictions are outside the claim. 1.19.3/1.19.4 show the near-term cap form still lacks a climbable entry gate; 1.21.5 supplies a later explicit fall-flight travel gate solely as a dependency. This does not locate any intermediate patch boundary, establish full-pair coverage, or establish runtime parity.