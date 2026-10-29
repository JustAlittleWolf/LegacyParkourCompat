# Partial-sweep traversal candidate review

## Verdict

**ACCEPT for the bounded 1.21.2/1.21.3 callback-scan traversal candidate.** The implementation follows the accepted source closure for the shared final query box, ordered live movement-list consumption and normal-path clearing, short/long candidate traversal, and normalized endpoint nudges. This review does not cover product mixin/callsite wiring or runtime parity.

## Bound inputs and hashes

- Candidate `.native-candidates/partial-sweep-traversal-1212-20261029`: exact `HEAD` `0d28943fe9f07a54eb5eef1fb59c1eb1bf35d5cc` (verified).
- Candidate implementation `EntityInsideBlocksScan.java`: SHA-256 `0bb3ba0a8a8b4f1a2e83cd1271ff49a4d08db0637aa7a918460cb5155402c93e`.
- Candidate hook `EntityInsideBlocksScanHook.java`: SHA-256 `e9da9453d7b2172d6ae42fa3324e1cab2beed791480998055b03d98a48de0629`.
- Accepted source report `.native-source/20261029/partial-sweep-1212-alias-closure/report.md`: SHA-256 `554fca52d724e8133ec090f8d1b28f3daea443c926682df21b62e133d0816ab4` (recomputed; matches the requested hash).
- Independent source audit `.native-source/20261029/partial-sweep-1212-alias-audit/audit.md`: SHA-256 `601e1ec94848d2651ceecb8249f08fe3b138c476e2103c5a7fc5410ca72aa34e` (recomputed; matches the requested hash).

## Review notes

- `scanMovements` calls `deflate(1.0E-5F)` once before the loop and passes that same query-box object for every movement. It consumes the supplied live list in order and clears it only after callbacks complete normally, matching the 1.21.2/1.21.3 lifecycle described in the accepted report and audit.
- `candidateCells` uses the short-displacement gate `delta.lengthSqr() < Mth.square(0.99999F)` and returns the ordered closed AABB cells directly in that case. For longer movement it collects traversal cells into `ObjectLinkedOpenHashSet`, then appends ordered AABB cells, storing immutable positions for the latter.
- The long traversal uses `epsilon = delta.normalize().scale(1.0E-7)` and endpoints `queryBox.min - delta - epsilon` and `queryBox.min + epsilon`, preserving the report's endpoint signs and operation order. The traversal helper retains the cited axis stepping, clipping, clamp, and ordered unique-cell behavior.
- The candidate introduces only the scan hook and its 1.21.2 provider. It contains no duplicate partial-shape collision predicate. The provider is annotated `@MovementChange(emulates = V1_21_2)` and implements the single `@MechanicType` hook; based on the resolver in the candidate, this resolves as the 1.21.2 behavior for selected 1.21.2/1.21.3 profiles and leaves later versions without an applicable old provider.
- The accepted source report and audit both explicitly preserve `oldPosition()` to current `position` as the no-argument scan input and do not claim a piston pre-push origin. The candidate adds no contrary piston-origin claim.

## Scope limits

This is a source-to-candidate review of the new scan helper and resolver annotation only. Product integration, mixin/callsite wiring, builds, tests, game launches, and runtime behavior were not reviewed or run.
