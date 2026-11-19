# Independent source verdict: airborne fall-distance accumulation precision

## Verdict

**ACCEPT — continuous source applicability from stable 1.8 through 1.18.1, inclusive.** Every exact stable release in the assigned interval has the same post-subtraction float conversion in the ordinary entity writer. The adjacent 1.18.2 source instead converts the resolved Y displacement before subtraction. This verdict accepts the bounded writer difference and its applicability under the stated call preconditions; it does not accept full tick-graph coverage or runtime parity.

## Frozen inputs and independent checks

- Frozen comparison report: `source-evidence-r4.md`, SHA-256 `6530D47BB0E1BCE26E61504C4C5F3C654594E02DFF41443C923EF730BFA47FFB`.
- Paired source anchor: `development/research/source-evidence/20261118/block-c-fall-distance-1181-1182/source.md`, SHA-256 `491074D03E02CFE42C8D62268C4E3C3351D33984657F27CC518C746C54C478D0`.
- Movement verdict anchor: `development/research/source-evidence/20261118/block-c-fall-distance-1181-1182/movement-verdict.md`, SHA-256 `90148C4743763DB0505020831116504273C51F0897D13EDC814929D43659D2A6`.
- Independently checked all 45 exact `Entity.java` source files against canonical source hashes. For 22 outer-provenance trees, the ready marker's release and mapping identity and the source-manifest entry matched; for 23 nested-provenance trees, the actual source hash matched its nested provenance entry. No source digest or identity mismatch was found. Mapping families are Feather through 1.14, Yarn for 1.14.1–1.14.3, and Mojmap from 1.14.4 through 1.18.1.
- Independently checked the writer expression in each source file. All 45 retain a cast after subtraction; parameter names vary with mappings/decompiler output. The two 1.18.1-equivalent sources use the Mojmap local name `$$0`; earlier mapped sources use `dy`.

## Bounded source claim

The ordinary resolved-Y writer is `Entity.checkFallDamage` in the audited mappings. For a call with `onGround == false` and resolved Y `< 0.0`, every exact 1.8–1.18.1 writer performs the equivalent of `(float)(fallDistance - resolvedY)`: the existing float is promoted for double subtraction and the result is rounded once on assignment. In 1.18.2 the same negative-Y gate performs `fallDistance -= (float)resolvedY`, rounding the displacement before the subtraction. This is a real arithmetic distinction, though particular values can produce the same stored float.

The ground arm is prior to the downward arm: grounded movement resets fall distance; non-grounded nonnegative resolved Y does not take the accumulation write. At the 1.18.1/1.18.2 endpoints, `Entity.move` passes the resolved/collision-adjusted Y to this method and derives `onGround` from vertical collision plus a negative requested Y in both releases. The direct reset operation stores `0.0F` in both. The old 1.8 source likewise calls the inherited writer from `Entity.move`; `LocalClientPlayerEntity` extends `ClientPlayerEntity` → `PlayerEntity` → `LivingEntity` → `Entity`, and `LivingEntity.checkFallDamage` delegates to `super` after its own surrounding work. At the modern endpoint, `LocalPlayer.move` delegates to `super.move`, and `LivingEntity.checkFallDamage` delegates to `Entity.checkFallDamage`. These facts establish the ordinary local-player route to the writer, not parity of the enclosing travel/callback logic.

## Applicability and exclusions

The accepted applicability is per writer invocation with airborne state and a negative resolved Y displacement. It covers only this accumulator operation and its ordinary local-player reach. It does not assert a universal trajectory or same-tick position/velocity divergence, nor a specific downstream threshold consequence. It makes no claim about collision/backoff, block or support shape selection, reset-producing blocks, fluid gates or fluid resets, other callbacks, damage, vehicles, or non-player behavior. The whole movement pair is not audited or frozen here.