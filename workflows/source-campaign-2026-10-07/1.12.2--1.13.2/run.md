# Discovery: 1.12.2 to 1.13.2

- Status: active
- Scope: source-only client player movement; older A = 1.12.2; newer B = 1.13.2.
- Repository revision and start date: base 002137b227676caea77f6832b9f4c8d0b6200bff; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending validated readiness JSON; Feather is a candidate, not yet validated for this run.
- Source preparation command and log: source-owner handoff pending. This run does not own shared source preparation.
- Toolchain/decompiler/remapper versions and options: pending validated readiness JSON and artifact manifests.
- Track declaration: source-only discovery; no wiki/MCPK/parkour wiki, release notes, mod implementation, or runtime code used; runtime validation not performed.

## Artifact manifest

- A (1.12.2): exact ID requested; resolved ID, source root, client jar hash, CLI mode, mapping coordinate/build/path/hash, mapped jar hash, cited source/resource paths and hashes all pending validated readiness JSON.
- B (1.13.2): exact ID requested; resolved ID, source root, client jar hash, CLI mode, mapping coordinate/build/path/hash, mapped jar hash, cited source/resource paths and hashes all pending validated readiness JSON.
- Shared source owner is the sole decompiler writer. Do not read a source tree as evidence until its readiness JSON and relevant hashes/diagnostics have been verified.

## Correspondence and call order

Pending exact source IDs and hashes. Resolve each logical role independently on both sides, including class inheritance and member descriptors, caller chains, split/replacement paths, state reads/writes, and execution order. No guessed name correspondence is accepted.

## Coverage ledger

All slices remain pending until both exact source trees pass readiness validation. Required stages, with bounded slices to be added from their actual inventories:

1. Local input and tick ordering.
2. Player-specific state and gates.
3. Living movement integration.
4. Entity movement and collision.
5. Blocks and fluids that produce movement inputs.
6. Effects, enchantments, attributes and equipment.
7. External influences and dependency closure.

Track direct player movement only: input and full tick call graph; pre/post travel math and velocity thresholds; jump, sprint and flight; pose/dimensions/resize and collision query timing; collision axes, step/support/callbacks; historical block shapes/providers/registrations/neighbors; fluids; movement attributes/effects/equipment; and external velocity writers. Exclude health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation despite indirect sprint-gate effects. Do not extend historical behavior to modern-only blocks or features; non-player physics are out of scope. Preserve historical quirks and exact floating-point order.

Each eventual ledger row must name the bounded behavior, exact A/B member or checked absence, original line ranges, source/resource hashes, dependency closure and terminal rationale. Pending or in-progress rows keep this run partial.

## Dependency queue and blockers

- SRC-PAIR: obtain readiness JSON and referenced artifact/source manifests for exact releases 1.12.2 and 1.13.2. Verify requested/resolved IDs, namespace alignment, cited hashes and movement-method body diagnostics. Source-owner protocol: canonical shared root build/movement-campaign-2026-10-07; never generate, rewrite or clean shared artifacts here.
- GIT-META: dedicated branch feat/source-discovery-movement-source-1-12-2-1-13-2 created for the managed worktree. Git metadata is shared with the primary checkout; preserve any pre-existing changes.

## Finding index

No findings recorded yet; source comparison has not started.

## Resume checkpoint

- Last completed slice: none; global/project guidance and movement-discovery workflow/templates read; repository base verified as 002137b227676caea77f6832b9f4c8d0b6200bff.
- Next bounded slice: after both source trees are validated, enumerate and resolve paired input/tick entry methods before comparing stage 1.
- Outstanding dependencies: SRC-PAIR; all seven navigation stages and their discovered dependent callers, data and resources.
- Current assumptions requiring verification: Feather may provide the common namespace, but this is not accepted until the source-owner readiness JSON and artifact hashes confirm exact release alignment and body integrity.

## Source audit closure

- Coverage counts by status: 7 navigation stage groups open; no individual method slices admitted yet.
- Unresolved gaps and limits: source comparison cannot begin until validated 1.12.2 and 1.13.2 source handoff; detailed movement coverage remains open.
- Evidence/hash/correspondence audit: source evidence not yet admitted; only repository docs, workflow guidance and templates were consulted.
- Runtime validation: not performed (separate workflow).