# Product review: respawn jump-strength base

**Verdict: ACCEPT.** The integrated change implements the accepted client respawn delta with the expected range, write boundary, and native fallback. No product defect was found in the reviewed diff.

## Immutable identities

- Candidate implementation reviewed: `d2bdc1976c259f24b084dcd793d2c29a21589780` (`.native-candidates/respawn-jump62`).
- Product integration reviewed: `71245cd3a49106252cf98423049c25fde6f1ed07` in isolated integration repository `.native-integrations/respawn-jump62`, based on `6e9780398a296d466d5dcc97e1b84fb65585f269`.
- Accepted source review: `development/research/source-evidence/20261031/checkpoint62/1206-1211/respawn-jump-independent-review.md` and alias review `development/research/source-evidence/20261031/checkpoint62/respawn-alias/review.md`; exact source hashes are recorded in `development/research/source-evidence/20261031/checkpoint62/respawn-alias/source-hashes.txt`.

## Findings

- The integration adds only the expected two versioned changes, one `RespawnJumpStrengthBaseHook`, and a small adapter block to the existing client respawn mixin. The mixin block is injected immediately after native `AttributeMap.assignBaseValues`; it reads the replacement local player and, only for the no-keep packet branch, resolves the hook and writes the returned base to `Attributes.JUMP_STRENGTH`. It does not copy or modify other attributes. Missing replacement player or jump-strength attribute fails loudly.
- The selected `0.42F` is passed to `OptionalDouble.of(double)`, preserving Java's widening of the float literal to its exact historical double value. The accepted Mojmap source review establishes that 1.20.5/1.20.6 omit attribute copying when `shouldKeep((byte)1)` is false, while 1.21/1.21.1 call `assignBaseValues` on that branch and use the same `0.42F` registered default. The 1.20.5 and 1.20.6 relevant source slices are identical; the 1.21 and 1.21.1 relevant slices are also identical.
- The hook's `@MovementChange` annotations allow automatic discovery and per-hook version resolution. The 1.20.2 empty-result anchor preserves the pre-1.20.5 partial behavior; the 1.20.5 implementation supplies the reset for profiles selecting 1.20.5 or later. This does not claim historical equality for earlier jump-power paths, whose attribute behavior is outside this delta. Native/current profiles remain untouched because `MovementRuntime.find` returns empty when historical movement does not apply.
- The change leaves all other movement-speed hooks and modifier handling untouched. No tests, game launches, or build were run as part of this source-level review.

## Scope

This review covers the product diff from `6e9780398a296d466d5dcc97e1b84fb65585f269` through `71245cd3a49106252cf98423049c25fde6f1ed07` against candidate `d2bdc1976c259f24b084dcd793d2c29a21589780` and the accepted exact-source reviews named above. Compilation and runtime movement parity remain separate verification evidence.
