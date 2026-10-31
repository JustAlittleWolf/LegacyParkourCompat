# Independent source review: resolved-vector position cutoff

**Verdict: accepted for the bounded source finding and fixed-state ordinary-input witness.** This verdict is bound to candidate SHA-256 `30202252204D7FCB22F8F3B32C63DD87A5B94AB05FE5025162E96D0BF2C92164` at `.native-source/20261031/1132-1144-finish46/candidates/resolved-vector-position-cutoff.draft.md`. It accepts the resolver-result position-write difference and the stated one-tick witness under its explicit paired raw player/world assumptions. It does not establish prior reachability of the fixture start, pair-wide coverage, or runtime parity.

## Source identity and integrity

I independently read only the candidate, the two permitted reports, and the assigned exact source roots. The endpoint `Entity.java` files match the candidate's complete-file hashes:

| Release / mapping family | Exact source root and member | SHA-256 |
| --- | --- | --- |
| 1.13.2 / Ornithe Feather | `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.14.4 / Ornithe Feather | `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.14.4/ornithe-feather/net/minecraft/entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |

The 1.13.2 body at `Entity.java:578–599` resolves only when any requested component is nonzero, clips Y/X/Z, and applies each nonzero resolved horizontal component to the shape. The later `setPositionFromShape()` at `:676` is outside that conditional, so a small nonzero clipped remainder is committed. The 1.14.4 body at `Entity.java:463–468` resolves the vector, then moves the shape and updates position only if the resolved vector's `squaredDistanceToOrigin() > 1.0E-7`. The inequality is strict. For the witness residual of approximately `(0,0,9.9988E-5)`, squared length is approximately `9.9976E-9`; the 1.14.4 position update is skipped while the 1.13.2 update occurs.

The claimed intermediate stable checks also match the supplied sources: Yarn 1.14.0 `Entity.java:452–456`, 1.14.1 `:452–456`, 1.14.2 `:454–458`, and 1.14.3 `:461–465` each gate the bounding-box/position update on resolved `lengthSquared() > 1.0E-7`. The complete-file hashes independently match the candidate. These checked releases support the stated 1.13.2-to-1.14.0 endpoint boundary; they do not establish unexamined releases before 1.13.2.

The candidate identifies both endpoints as Ornithe Feather and binds the relevant decompiled files by path and hash. The supplied endpoint trees do not carry a local mapping-build provenance file, so this review confirms the exact trees and their declared mapping family, but cannot independently attest a Feather build coordinate from the permitted inputs. Yarn's exact source file hashes were independently checked; its provenance inputs identify the release-specific Yarn mapping artifacts (1.14.0 `1.14+build.18`, 1.14.1 `1.14.1+build.10`, 1.14.2 `1.14.2+build.7`, 1.14.3 `1.14.3+build.13`).

## Fixed-state witness review

The permitted witness report's keyboard, local-player, living-entity, player, block and registry source hashes match the supplied 1.13.2 and 1.14.4 files. Its cited path is coherent for a one-tick ordinary Forward input with the stated yaw, grounded state, zero prior velocity, no sprint/sneak/use/jump/effects, default speed, and autojump disabled. Under those assumptions both endpoints send a positive-Z SELF request of about `0.098`; the small float-rounding difference between endpoint requests does not affect the result because both exceed the stated wall gap by roughly three orders of magnitude. This review relies on the assignment's permission to treat equal raw logical inputs and fixed raw player/world state as paired initial conditions; it makes no claim about device plumbing or how ordinary prior play would produce the initial offset.

For the supplied standing box centered at `(0.5, 1, 0.6999)`, the base-block full-cube wall at `(0,1,1)` overlaps the player's X footprint and has a positive-Z face gap of about `9.9988E-5`. The two-block wall and ceiling in the witness suppress the step-up alternative under the stated geometry. The permitted collision-query closure supplies the matching loaded, interior, no-entity full-cube provider/query route. Thus clipping leaves the stated nonzero Z remainder in both endpoint resolvers, and only the later resolved-vector position-write gate changes the fixed-tick position result.

I did not inspect the audit report mentioned by the draft or witness report, because it was outside the permitted evidence. This verdict is an independent source review and does not rely on that report. The closure report itself explicitly bounds its result to these witnesses and leaves full collision-provider inventory open.

## Corrections needed

No correction is needed to the finding's movement claim, resolved-vector gate, fixed-state witness result, or cited endpoint/intermediate source line ranges. If the candidate is used where mapping publication identity must be independently auditable, add the exact Feather mapping build coordinates or provenance identities for the 1.13.2 and 1.14.4 source trees; the current permitted endpoint trees expose the family and exact file hashes but not those build coordinates. Keep the raw fixture-state and no-spawn-history qualification intact.
