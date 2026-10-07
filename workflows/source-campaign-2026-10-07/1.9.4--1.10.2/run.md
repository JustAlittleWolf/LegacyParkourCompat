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

All seven required navigation stages are open. The following is a **pre-source coverage plan**, not a claim that these are the complete reachable methods. Once exact source is ready, resolve members/callers and split any item whose full body or dependency closure exceeds a bounded slice. Add newly discovered work; never close a stage on unchanged top-level travel code.

### S1 — local input and tick ordering

- S1.1 input sampling and key/controller state: pending.
- S1.2 input axes, yaw-to-motion conversion and normalization: pending.
- S1.3 local tick, superclass tick and travel call order / previous-current flag capture: pending.
- S1.4 sprint start/stop gates, timers and state writers: pending.
- S1.5 jump input, jump timers/cooldowns and jump state writers: pending.
- S1.6 flight toggle, double-tap and unstuck paths: pending.
- S1.7 automatic jump and synthetic-input paths: pending.
- S1.8 riding input, jump charge and mount gates affecting the player: pending.

### S2 — player state and gates

- S2.1 pose selection and standing/crouching/swimming/gliding transition gates: pending.
- S2.2 dimensions, bounding-box resize and collision timing on pose change: pending.
- S2.3 eye height only where it changes movement/fluid queries: pending.
- S2.4 flight abilities/speed and other movement capability defaults/writers: pending.
- S2.5 swimming/crawling state and stored air-speed state: pending.
- S2.6 edge-sneak support probes and related movement gates: pending.
- S2.7 item-use movement slowdown and input scaling: pending.
- S2.8 direct vanilla sprint-gate consumers: pending; health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation are excluded.

### S3 — living movement integration

- S3.1 travel dispatch and branch-selection state: pending.
- S3.2 ground acceleration, friction and movement-speed derivation: pending.
- S3.3 air acceleration, sprint air control and relative-input math: pending.
- S3.4 jump power, sprint impulse and movement attribute/effect inputs: pending.
- S3.5 gravity, drag, vertical thresholds and negligible-velocity cutoffs: pending.
- S3.6 water and lava branch movement, depth/jump gates and post-travel updates: pending.
- S3.7 climbing clamps and climb-state producers: pending.
- S3.8 gliding/fall-flying travel math and transitions: pending.
- S3.9 motion callbacks/reset operations reached after travel: pending.

### S4 — entity movement and collision

- S4.1 entity position/velocity/bounding-box writers around movement: pending.
- S4.2 collision query timing, candidates and shape context: pending.
- S4.3 axis clipping order, tie-breaking and blocked-velocity response: pending.
- S4.4 step-up candidates, comparison and selected displacement: pending.
- S4.5 on-ground/support/vertical-collision state computation: pending.
- S4.6 block collision callbacks and movement callback order: pending.
- S4.7 fluid contact/push and their collision/query dependencies: pending.
- S4.8 player push/knockback effects from other entities (player integration only): pending.

### S5 — blocks and fluids

- S5.1 base state/block collision and outline-shape dispatch, registration and defaults: pending.
- S5.2 historical partial-block shape overrides and support effects: pending.
- S5.3 friction/speed/jump-factor registrations and consumers: pending.
- S5.4 contact/landing/climbing/collision callback overrides and state predicates: pending.
- S5.5 fluid height, flow vector and player push producers: pending.
- S5.6 neighbor-dependent block movement inputs, registries and checked absences: pending.

### S6 — effects, attributes, enchantments and equipment

- S6.1 movement effect consumers and amplification/timer formulas: pending.
- S6.2 movement attribute defaults, aggregation and operation order: pending.
- S6.3 enchantment consumers, level formulas, slots and applicability: pending.
- S6.4 equipment/item-use movement consumers and lifecycle: pending.
- S6.5 relevant registrations, tags and jar-resource/default dependency closure: pending.
- S6.6 modern-only mechanics and server/external-data boundaries: pending.

### S7 — external movement influences

- S7.1 incoming velocity and position correction handlers: pending.
- S7.2 player knockback, push and explosion integration: pending.
- S7.3 piston displacement and client/player movement path: pending.
- S7.4 mount/dismount and transition placement affecting the player: pending.
- S7.5 launch-item and external impulse writers: pending.
- S7.6 final cross-stage movement-state writer/caller closure and revisit: pending.

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

- Coverage counts: 0 compared-no-difference; 0 findings; 0 not-applicable; 0 blocked; 51 planned bounded slices pending across 7 stages; 0 slices closed.
- Pending bounded-slice count: 51 planned; this must be revised upward whenever source navigation exposes additional distinct methods, writers, consumers or dependencies.
- Unresolved gaps: all comparison stages and source provenance remain open.
- Evidence/hash/correspondence audit: not started; no Minecraft source evidence has been accepted.
- Runtime validation: not performed (separate workflow).
