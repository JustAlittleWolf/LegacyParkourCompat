# Source-only movement discovery: 1.9.4 to 1.10.2

- Status: active
- Scope: direct client-player movement; older A = 1.9.4; newer B = 1.10.2
- Track: source-only discovery; no wiki/MCPK, release-note mechanics, or mod implementation consulted
- Repository base: `main` at `002137b227676caea77f6832b9f4c8d0b6200bff`; task branch `feat/source-discovery-movement-source-1-9-4-1-10-2`
- Start date: 2026-10-07
- Source-only constraint: do not read or change runtime implementation; do not launch tests, clients, TAS, Gym, servers or Docker

## Artifact manifest

Source publication is pending from the designated source owner. No source tree is treated as ready by directory presence or by the historical pair report. Required next action: consume the owner's validated ready JSON, verify requested and resolved IDs are exactly 1.9.4 and 1.10.2, verify per-side namespace and SHA-256 artifacts, and inspect diagnostics for every cited method body before using source evidence.

- Candidate aligned namespace: explicit Ornithe Feather on both releases. The pair predates Mojmap/native-unobfuscated official names; do not mix mapping families. Candidate mapping resolution is not yet evidence.
- A (1.9.4): exact client jar, mapping artifact/build, mapping file, remapped jar, source root, toolchain, decompiler log and hashes: pending validated source-owner record.
- B (1.10.2): exact client jar, mapping artifact/build, mapping file, remapped jar, source root, toolchain, decompiler log and hashes: pending validated source-owner record.
- Relevant source-body error/warning overlap and bytecode follow-up: pending source-owner diagnostics and method inventory.
- Resource provenance: pending; inspect only jar entries that become relevant through reachable movement dependencies.

## Correspondence and call order

No pair-specific correspondence is accepted yet. After source readiness, resolve exact classes, inheritance, descriptors, callers and state reads/writes for each stage. Preserve method-level call order and line anchors separately for A and B; class-name similarity alone is not correspondence.

## Coverage ledger

All seven required navigation stages are open. Each row must be split into bounded behavior slices as the call graph, producers, consumers, callbacks and dependencies are enumerated. Do not close a whole stage on unchanged top-level travel code.

- S1 — local input and tick ordering (input sampling, movement axes, sprint/jump/flight gates and timers, auto-jump, riding, tick sequencing): pending; sources not yet verified.
- S2 — player state and gates (pose/dimensions/resize, eye height in movement queries, swimming/crawling, abilities, edge sneak, item-use slowdown, air speed): pending; sources not yet verified. Hunger/food/health emulation is excluded; record only direct vanilla sprint-gate consumers as in-scope control flow.
- S3 — living movement integration (ground/air/water/lava/glide travel, acceleration, friction, gravity/drag, velocity cutoffs, jump and climb math, attributes/effects): pending; sources not yet verified.
- S4 — entity collision and movement pipeline (bounding boxes, collision query timing, axis order, step candidates/tie-breaks, support, callbacks, fluid push, entity-size effects): pending; sources not yet verified.
- S5 — historical blocks and fluids (shapes, registrations/defaults, neighboring-state dependencies, friction/speed/jump factors, callbacks and flow): pending; sources not yet verified. Modern-only blocks do not gain historical behavior.
- S6 — movement-affecting effects, attributes, enchantments and equipment (consumer-to-registration/data dependency closure; direct motion only): pending; sources/resources not yet verified.
- S7 — external movement inputs (velocity/position packets, knockback/push, explosions, pistons, mount transitions and launch items; player path only): pending; sources not yet verified.

Per bounded slice, record one of `pending`, `in-progress`, `compared-no-difference`, `findings`, `not-applicable`, or `blocked`; cite both-side paths, complete member ranges and hashes, dependency closure and rationale. Checked absence must follow inheritance, callers, registration or replacement paths. Preserve exact arithmetic, casts, comparisons and execution order.

## Dependency queue and blockers

- D0 — source publication: obtain and validate ready JSON for the exact Feather pair; verify exact IDs, mapping alignment, artifacts and method-body diagnostics. Until then all source comparisons remain pending.
- D1 — decompiler warning closure: for each reachable relevant member intersecting a reported remapper/decompiler repair or error, inspect mapped jar bytecode/descriptors or mark the slice blocked.
- D2 — resource closure: once movement consumers identify tags/defaults/equipment/effects, check matching versioned client-jar entries and hash cited resources; identify synchronized/external data boundaries.
- D3 — transitive movement-state writers/callers: enumerate after all navigation stages, revisit unchanged callers when a dependency changes.

## Finding index

No findings accepted in this fresh run yet. Add one `findings/<id>.md` per independently describable, source-confirmed delta; candidate findings must state unresolved dependencies. Prior pair reports are historical hints only and are not coverage evidence.

## Resume checkpoint

- Last completed slice: none; repository instructions, discovery contract, navigation stages, templates and decompiler implementation have been read.
- Next action: receive source-owner ready JSON; validate both endpoints/hashes/diagnostics; then begin S1 with full local-player input/tick call graph and producers.
- Outstanding dependencies: D0–D3.
- Assumptions requiring verification: explicit Feather exists and resolves both exact release IDs; source trees and relevant method bodies are intact; source-owner warnings do not intersect evidence without bytecode confirmation.

## Source audit closure

- Coverage counts: 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked; 7 stage rows pending; 0 bounded slices closed.
- Pending bounded-slice count: not yet enumerated; the dependency/callgraph inventory starts after source validation.
- Unresolved gaps: all comparison stages and source provenance remain open.
- Evidence/hash/correspondence audit: not started; no Minecraft source evidence has been accepted.
- Runtime validation: not performed (separate workflow).
