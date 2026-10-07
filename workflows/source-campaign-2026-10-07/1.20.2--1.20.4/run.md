# Discovery: 1.20.2 to 1.20.4

- Status: active
- Scope: direct client player movement and every reachable vanilla influence on player movement state. Older A = 1.20.2; newer B = 1.20.4. This is a source-only discovery track; no runtime Java implementation is authorized here.
- Scope exclusions: health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation, including indirect sprint-gate effects; use vanilla state consumers only. Non-player physics is out of scope. Newer-only features remain modern-only and do not gain historical behavior.
- Provenance constraints: no Minecraft Wiki, MCPK, release-note mechanics, prior implementation, old mod diffs, old patch classes, or wiki-derived audit output. Exact-version decompiled Java and bytecode are the evidence.
- Repository revision and start date: 002137b227676caea77f6832b9f4c8d0b6200bff (`main` at worktree creation); 2026-10-07.
- Worktree / branch: `C:/Users/Wolfi/.codex/worktrees/movement-source-1-20-2-1-20-4/LegacyParkourCompat`; `feat/source-discovery-movement-source-1-20-2-1-20-4`.
- Selected naming namespace, CLI mode per side and alignment evidence: planned Mojmap / `mojmap` for both. Awaiting source-owner publication and exact readiness validation; no alignment claim yet.
- Source preparation command and log: source owner only; pending exact 1.20.2 and 1.20.4 Mojmap publication. Shared source root: `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`.
- Toolchain/decompiler/remapper versions and options: pending readiness records and artifact manifests.

## Artifact manifest

- A (1.20.2): source root, exact resolved ID, Mojang client jar identity/hash, `mojmap` CLI mode, official mappings coordinate/path/hash, remapped jar hash, source manifest/hash, decompiler diagnostics, and relevant dependency versions: pending validated readiness JSON.
- B (1.20.4): source root, exact resolved ID, Mojang client jar identity/hash, `mojmap` CLI mode, official mappings coordinate/path/hash, remapped jar hash, source manifest/hash, decompiler diagnostics, and relevant dependency versions: pending validated readiness JSON.
- Source/resource citations will use paths relative to these manifests. Original client-jar resources will be inspected locally and cited by entry name and SHA-256 when relevant.

## Correspondence and call order

Exact paths, descriptors, inheritance, callers, fields, and execution order are pending source availability. For each navigation stage, establish A-to-B correspondence from inspected members and call sites; do not treat matching names as proof. Record dependencies read or written for input, position, velocity, bounding box, pose, on-ground and collision/fluid flags, supporting block, movement attributes, sprint/jump timers, and equipment/effect state. Include the complete client input/tick/travel path and external velocity writers.

## Coverage ledger

Initial stage registrations only. Expand each stage into bounded method/data/resource slices after validating both exact source trees. No row is complete from directory presence or an unverified prior catalog.

- Slice S1 / stage 1 / local input sampling, tick order, movement input, sprint/jump/flight gates: pending
  - A/B evidence: pending validated source roots.
  - Dependencies: exact client input and local-player methods, their reachable callers/producers and state writers.
- Slice S2 / stage 2 / player state, pose/dimensions, resize/collision timing, player movement gates: pending
  - A/B evidence: pending validated source roots.
  - Dependencies: player superclass, pose/dimension definitions, state defaults/writers and collision/fluid consumers. Health/food mechanics excluded; inspect only any in-scope vanilla gate consumer as needed.
- Slice S3 / stage 3 / living travel, jump, acceleration, drag/gravity, velocity cutoffs and post-travel updates: pending
  - A/B evidence: pending validated source roots.
  - Dependencies: all called math/vector helpers, attributes and direct movement state consumers.
- Slice S4 / stage 4 / entity move, collision axes/step selection, support, callbacks and query timing: pending
  - A/B evidence: pending validated source roots.
  - Dependencies: AABB/voxel shapes, collision query producers, overrides, block callbacks and relevant field writers.
- Slice S5 / stage 5 / movement-producing block/fluid properties, shapes, callbacks, neighboring states and registrations: pending
  - A/B evidence: pending validated source trees and original client-jar resources.
  - Dependencies: all registered movement-relevant overrides, defaults, tags, shape/context helpers, fluid currents/heights, and source-backed absence checks. Modern-only blocks remain modern-only.
- Slice S6 / stage 6 / effects, enchantments, attributes, equipment and movement-relevant item data: pending
  - A/B evidence: pending validated source trees and original client-jar resources.
  - Dependencies: consumer-to-aggregation-to-registration/application/removal/equipment/tag closure; server-synchronized inputs identified separately. Explicitly disposition the required movement effects/enchantments and discover additional registrations. Health/food systems excluded.
- Slice S7 / stage 7 / external movement influences and cross-stage dependency closure: pending
  - A/B evidence: pending validated source trees.
  - Dependencies: incoming velocity/position updates, player knockback/push, explosions, piston displacement, mount transitions and launch-item paths; client versus externally supplied state distinguished.

## Dependency queue and blockers

- DEP-01; originating slices S1-S7; exact 1.20.2 and 1.20.4 Mojmap source roots, readiness JSON, source/artifact SHA-256 manifests, exact release IDs and relevant body diagnostics are not yet published. These are prerequisites for comparison. Next action: wait for source-owner publication; then read the JSON and verify all cited hashes and movement-method bodies before opening source. Status: pending, not a comparison blocker yet.
- DEP-02; originating slice S5-S6; original client-jar resources and referenced registries/tags/default data must be verified for movement data omitted by the Java saver. Resolve after validated artifacts are available.

## Finding index

- No findings confirmed yet. Zero findings does not imply equivalence.
- Discarded candidates: none yet.

## Resume checkpoint

- Last completed slice: none; workflow and repository inputs read, pair inventory not yet source-verified.
- Next bounded slice: verify both readiness JSON files against exact requested/resolved IDs, source/artifact manifests, hashes and diagnostics; then enumerate exact movement entrypoints for stage 1.
- Outstanding dependencies: DEP-01 source publication; DEP-02 original client-jar movement resources.
- Current assumptions requiring verification: both exact releases can be compared in Mojmap; no source-body damage affects relevant methods; all exact manifest entries refer to currently published bytes.

## Source audit closure

- Coverage counts by status: pending 7 stage registrations; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. The stage registrations are intentionally coarse and must be expanded to exhaustive per-stage method/dependency slices before closure.
- Explicit pending count: 7 coarse stage registrations plus 2 source/data dependencies; method-level coverage count is not yet established.
- Unresolved gaps and limits: all substantive comparison remains pending source publication. No equivalence claim.
- Evidence/hash/correspondence audit: not started; readiness records must be validated before use.
- Runtime validation: not performed (separate workflow).
