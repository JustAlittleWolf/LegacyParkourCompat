# Discovery: 1.21.3 to 1.21.4

- Status: active
- Scope: source-only client player movement; older A = exact 1.21.3; newer B = exact 1.21.4. Health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation are excluded; player-only consequences mediated by vanilla state consumers are in scope. Modern-only blocks/features and non-player physics are out of scope.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`); 2026-10-07. Task branch: `feat/source-discovery-movement-source-1-21-3-1-21-4`.
- Naming namespace and alignment: requested explicit `mojmap` for both exact releases; pending source-owner publication and readiness verification.
- Source campaign declaration: discovery docs only; no mod implementation, no wiki/MCPK, no release-note mechanics, and no runtime validation.
- Source preparation: shared source owner only; no local decompilation or shared-source writes. Readiness manifests not yet published for either release.
- Toolchain and decompiler versions/options: pending validated source manifests.

## Artifact manifest

A and B exact IDs are specified above. Source root, client jar, mapping artifact/build, remapped jar, source hashes, toolchain and successful decompiler log are pending each validated readiness JSON. Do not treat directory presence as readiness. No source body has been used as comparison evidence.

## Correspondence and call order

Pending exact source availability. Resolve the local player/input, player-state superclass, living movement, entity/collision, block/fluid, effect/enchantment/equipment, and external input chains on both sides. Do not infer correspondence from class names alone.

## Coverage ledger

- Stage 1 / local input and tick ordering: pending; both-side sources and full reachable call graph required.
- Stage 2 / player-specific state, pose/dimensions and gates: pending; include field writers and state consumers; exclude health/food rule emulation.
- Stage 3 / living movement integration: pending; compare complete relevant methods and dependencies, preserving exact numeric order and thresholds.
- Stage 4 / entity movement and collision: pending; include shapes, query timing, axis order, step/support/callback paths.
- Stage 5 / blocks and fluids: pending; enumerate movement-relevant overrides, registrations, defaults and neighbors; modern-only additions remain out of historical scope.
- Stage 6 / effects, enchantments, attributes and equipment: pending; close consumer-to-registration/resource paths and vanilla-state consumers.
- Stage 7 / external influences and dependency closure: pending; include player velocity/position writers and close all reachable dependencies.

## Dependency queue and blockers

- SOURCE-PAIR: exact 1.21.3 and 1.21.4 `mojmap` readiness JSON, artifact/source manifests, and movement-method diagnostics. Why: mandatory provenance and intact method bodies. Next action: wait for shared source owner publication; verify exact IDs, namespace, cited hashes and relevant bodies before comparison. Status: pending, not a comparison blocker yet.
- WORKTREE-GIT: worktree status initially failed under the default sandbox; dedicated branch creation succeeded through the approved elevated Git operation. Continue edits and commits only in the returned managed checkout.

## Finding index

No source-confirmed findings yet. No difference or equivalence is inferred from missing sources.

## Resume checkpoint

- Last completed slice: none; workflow and templates read, task branch created.
- Next: verify actual readiness JSONs for exact A/B, then start navigation stage 1 with filename inventories and source hashes.
- Outstanding dependencies: SOURCE-PAIR.
- Assumptions requiring verification: `mojmap` is available and successful for both exact releases; no source artifact is accepted until its manifest and method diagnostics are verified.

## Source audit closure

- Coverage counts: 0 terminal; 7 pending; 0 in-progress; 0 blocked.
- Unresolved gaps: all movement stages await exact validated sources; this run is explicitly open.
- Evidence/hash/correspondence audit: none claimed yet.
- Runtime validation: not performed (separate workflow).
