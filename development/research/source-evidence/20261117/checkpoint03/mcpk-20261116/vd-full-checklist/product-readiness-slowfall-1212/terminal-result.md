# Product correspondence: Slow Falling at V1_21_2 and later

Disposition: **V1_21_2 is covered by native fallback for the accepted `Math.min(getGravity(), 0.01)` expression and the `Y <= 0.0` plus Slow Falling gate. No conditional formula gap is identified for that selected profile.** This is a read-only product alias/provider check; source acceptance is unchanged and runtime parity is unverified.

## Inputs

- Prior product readiness report: `product-readiness-slowfall-lowgravity/readiness.md`, SHA-256 `E7DDBA7A0D7C5505B023AAEF1B1EFC2D657AD51F055411ED69314CBE4148B3B4` (verified against bytes).
- Exact-source follow-up: `slowfall-1212-source-followup/terminal-result.md`, SHA-256 `1EFC31AE3F565BFF1AE072AF5F27FA8EFCF5594BF55673053E30429D726A46D4` (verified against bytes). Its conclusion is used as supplied; no source was re-audited.

## Product trace

- `ParkourVersion.V1_21_2` aliases 1.21.2 and 1.21.3 (`src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java:97`). Later aliases are V1_21_4, V1_21_5, V1_21_6 (including 1.21.7–1.21.8), V1_21_9, V1_21_10, V1_21_11, and V26_1 (`ParkourVersion.java:98-104`).
- The only post-1.20.4 provider for this hook is `SlowFallingGravitySampling`, annotated `V1_21`; earlier fences are `V1_20_2` and `V1_12`. `ChangeResolver.resolve` keeps changes whose emulation version is at or after the selected version and chooses the closest such change (`src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java:21-32`). Therefore selecting V1_21_2 or any later historical alias cannot resolve the V1_21 provider (or earlier fences): this hook is absent.
- In `LivingEntityMixin`, the hook is called at the native `getEffectiveGravity()` expression inside `travelInAir`; when no hook resolves, the mixin returns the captured vanilla value (`src/client/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java:294-313`). `MovementRuntime.find` also returns empty when no selected profile hook is registered (`src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java:383-388`). Native fallback consequently retains both the formula and gate.
- The 1.21.2 follow-up closes the source continuity gap for the V1_21_2 alias: exact 1.21.2 formula and command-reachable 0.005 gravity are recorded there; the prior accepted packet covers 1.21.3. Thus the profile alias's two releases are covered for this expression and gate. This closes the pending V1_21_2 endpoint in the prior readiness report.
- Later aliases likewise select native behavior for this hook. The previously accepted source packet checks later endpoints 1.21.4, 1.21.11, and 26.2; this assignment does not extend source coverage to unexamined intermediate aliases (including 1.21.5–1.21.10 and 26.1). This is a source coverage limit, not evidence of a product formula gap. Native fallback at the product's 26.2 runtime is source-checked by the accepted packet.

## Result

**Close V1_21_2 in the product correspondence:** the selected profile resolves to no Slow Falling gravity sampling provider, and native `getEffectiveGravity()` supplies the accepted `Math.min(getGravity(), 0.01)` behavior under Slow Falling with sampled Y `<= 0.0`; at base gravity `0.005` it returns `0.005`. No code change is indicated. Broader later source-alias completeness remains bounded as described above. No tests, build, runtime, backlog, or Git operation was performed.
