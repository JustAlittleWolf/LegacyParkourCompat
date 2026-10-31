# Final independent candidate verdict

**Reviewed candidate commit:** `cd07e6aa391dc77c08ab6931aa4653518ef3668b`
**Prior candidate commit:** `7beb2254261f0a7d7bba5c7632fbf80317a54012`
**Neutral resolver commit:** `2097ca669145bdc93da0ba7524f04010aa463512`

## Verdict

**Accept as a source-level candidate for the stated look-at precision finding.** The final commit adds the required lower preservation boundary at `V1_16`, so the `V1_16_2` float-intermediate implementation no longer resolves for profiles older than the proven 1.16.2 patch group. The preservation hook returns native `Math.sqrt(double)` and explicitly avoids claiming older historical behavior. No upper boundary is needed: selections at `V1_17` or later cannot resolve the older `V1_16_2` implementation under the resolver's closest-emulates-at-or-after-selection rule.

The commit-bound diff from the prior candidate consists only of `src/client/java/me/wolfii/legacyparkourcompat/change/v1_16/LookAtHorizontalLengthPreservation.java`. Its public no-argument constructor, `@MovementChange(emulates = ParkourVersion.V1_16)`, shared hook implementation, and native-precision return are consistent with discovery and resolution semantics. Combined with the existing `V1_16_2` candidate implementation, resolution is as follows:

| Selected profile | Resolved horizontal-length hook |
| --- | --- |
| `V1_16` or older | `V1_16` native-preservation hook |
| `V1_16_2` (covering 1.16.2–1.16.5) | `V1_16_2` float-intermediate hook |
| `V1_17` or later | no candidate hook; native behavior |

The previous review's implementation checks remain valid: the float cast and widening reproduce `Mth.sqrt(double)`'s value flow, the redirect target is the 26.2 `Entity.lookAt(Anchor, Vec3)` call to `Math.sqrt(D)D`, and its client-player identity check is registered only in the client mixin list.

## Exact source-bound alias proof

For each source tree in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.2,1.16.3,1.16.4,1.16.5}\mojmap`, I directly checked `net/minecraft/world/entity/Entity.java#lookAt` and `net/minecraft/util/Mth.java#sqrt(double)`. All four copies have identical SHA-256 values for both files:

- `Entity.java`: `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`
- `Mth.java`: `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290`

At `Entity.java:2633`, every version computes `double g = Mth.sqrt(d * d + f * f)`. At `Mth.java:37–39`, every version defines `public static float sqrt(double d) { return (float)Math.sqrt(d); }`. The 1.16.2 and 1.16.5 parent `mojmap.sources.sha256` ledgers match these hashes; the 1.16.3 and 1.16.4 embedded `.decompile-provenance.json` source records match them too. This closes the exact-source alias check for every patch assigned to `ParkourVersion.V1_16_2`.

The boundary is intentionally conservative: this proof does not establish the historical look-at operation for versions before 1.16.2, and the `V1_16` hook preserves native precision there until such evidence is separately established.

## Scope

This is a technical candidate verdict, not the full paired-source freeze/audit and not runtime proof. No product checkout/history was inspected; no build, test, game launch, or decompile was run. No candidate or product source was modified during this review.
