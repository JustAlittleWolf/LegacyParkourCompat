# Independent review: look-at square-root precision candidate

## Verdict

**Revise before acceptance.** Commit `7beb2254261f0a7d7bba5c7632fbf80317a54012` implements the accepted precision delta for the proven 1.16.2–1.16.5 alias group and its client-player call site, but it lacks a lower preservation barrier. Under the resolver in neutral commit `2097ca669145bdc93da0ba7524f04010aa463512`, the `V1_16_2` implementation also resolves for every selection older than `V1_16_2` unless a closer `V1_16` implementation exists. Add a `V1_16` hook implementation that returns the native double-precision `Math.sqrt` result, thereby limiting this candidate delta to the proven group. No upper barrier is needed: `V1_17` is newer than the candidate change and the resolver does not apply this hook there.

This is a commit-bound technical review and source-bound alias check, not completion of the full paired-source freeze or pair audit.

## Candidate implementation review

- `LookAtHorizontalLength.horizontalLength` returns `(double) (float) Math.sqrt(value)`. This reproduces `Mth.sqrt(double)`'s float return followed by widening to the `double` local in `Entity.lookAt`, preserving the relevant rounding point.
- `EntityLookAtMixin` redirects `Math.sqrt(D)D` only when the receiver is identical to `Minecraft.getInstance().player`. Its registration is in the mixin JSON's client list. Other entities and the absent-hook path use the native `Math.sqrt` result.
- Exact 26.2 source at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated\net\minecraft\world\entity\Entity.java:3657-3668` has one `lookAt(Anchor, Vec3)` method. Its `Math.sqrt(xd * xd + zd * zd)` invocation at line 3662 matches the redirect target `Ljava/lang/Math;sqrt(D)D`. `Minecraft.player` is a `LocalPlayer` field in the same source tree, and the identity check gates precisely that client player object.
- Candidate `gradle.properties` sets Minecraft `26.2`; the checked 26.2 source therefore verifies the candidate's runtime method name and invocation owner/descriptor without relying on another release's signature.

## Exact source alias proof

Directly checked Mojmap `Entity.java#lookAt` and `Mth.sqrt(double)` in the canonical read-only source trees:

| Version | `Entity.lookAt` horizontal length | `Mth.sqrt(double)` | SHA-256 `Entity.java` | SHA-256 `Mth.java` |
| --- | --- | --- | --- | --- |
| 1.16.2 | `Mth.sqrt(d * d + f * f)` | returns `(float)Math.sqrt(d)` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.16.3 | same | same | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.16.4 | same | same | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 1.16.5 | same | same | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

The 1.16.2 and 1.16.5 parent `mojmap.sources.sha256` ledgers contain these hashes. The 1.16.3 and 1.16.4 embedded `.decompile-provenance.json` `sources` records also contain the same hashes. Thus the source behavior is closed across every patch grouped under `ParkourVersion.V1_16_2`. The source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.2,1.16.3,1.16.4,1.16.5}\mojmap`.

## Scope and verification

I inspected the sanitized candidate at the stated commit, its assigned finding, neutral resolver/version infrastructure, the named prior review, and only the requested exact source trees plus 26.2 signature source. I did not inspect product history, backlog, wiki, or another candidate. No source files were changed; no tests, build, launch, or decompile were run.
