# Discovery: 1.19.3 to 1.19.4

- Status: active
- Scope: source-only client player movement comparison; older A = 1.19.3; newer B = 1.19.4. Health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation are excluded; vanilla state consumers are inspected only where they directly gate movement. Non-player physics and historical behavior for modern-only blocks/features are excluded.
- Track declaration: source discovery only; no wiki/MCPK/parkourwiki research, no release-notes mechanics, and no current or historical mod implementation inspection. Decompiled exact-release sources and bytecode are the evidence base.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; task branch `feat/source-discovery-movement-source-1-19-3-1-19-4`; started 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: pending exact Feather source-owner handoff; comparison not started until validated readiness records and hashes are checked.
- Source preparation command and log: source-owner managed publication; awaiting exact command/log/artifact references. This worker will not write or regenerate shared sources.
- Toolchain/decompiler/remapper versions and options: pending readiness metadata.

## Artifact manifest

Exact releases requested: A `1.19.3`, B `1.19.4`; exact resolved IDs, source roots, jar identities/hashes, mapping coordinates/builds/files/hashes, mapped jar hashes, cited source/resource hashes, and toolchain data are pending source-owner publication and verification. The shared handoff root is `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07`. Readiness JSONs must explicitly identify exact release, namespace, manifest hashes, artifact hashes and movement-method diagnostics; directory presence alone is not evidence. Do not commit generated sources, jars, raw diffs, caches or logs.

## Correspondence and call order

Pending source readiness. Resolve actual classes, inheritance, method descriptors, callers, state reads/writes and ordered call chains independently on both sides. Do not infer correspondence from names alone. Record all correspondence and direct dependencies here before closing slices.

## Coverage ledger

Initial stage inventories are pending because exact source trees have not yet been verified. Replace each coarse inventory row with bounded method-level units, concrete A/B evidence, dependency closure, and a sourced disposition. Every movement-reachable method/caller/state writer/shape override must end as finding, compared-no-difference, out-of-scope or a precisely blocked dependency; pending or in-progress is partial at handoff.

- Slice I-1 / stage 1 / local input and full player tick ordering inventory: pending; source roots not yet verified.
- Slice I-2 / stage 2 / player-specific movement state, pose/dimensions, gates, abilities and timers inventory: pending; source roots not yet verified.
- Slice I-3 / stage 3 / living travel, jump, pre/post travel, velocity cutoffs, attributes/effects and operation order inventory: pending; source roots not yet verified.
- Slice I-4 / stage 4 / entity movement, collision queries/axes/steps/support/callbacks, fluid push and external velocity writers inventory: pending; source roots not yet verified.
- Slice I-5 / stage 5 / movement-relevant block shapes/properties/callback overrides, registrations, neighbors, fluids and resource data inventory: pending; source roots not yet verified.
- Slice I-6 / stage 6 / direct movement consumers of effects, enchantments, attributes, equipment and item-use state inventory: pending; source roots not yet verified.
- Slice I-7 / stage 7 / incoming corrections, knockback, mounts, pistons, launch items and final dependency-closure inventory: pending; source roots not yet verified.

## Dependency queue and blockers

- D-1; originating slice: all; exact `1.19.3` Feather readiness JSON plus SHA-256 source/artifact manifests and movement-method diagnostics; movement body integrity and exact release identity cannot be checked before receipt; next action: receive source-owner publication and validate all cited IDs/hashes and relevant diagnostics; status: pending (source-owner queue).
- D-2; originating slice: all; exact `1.19.4` Feather readiness JSON plus SHA-256 source/artifact manifests and movement-method diagnostics; movement body integrity and exact release identity cannot be checked before receipt; next action: receive source-owner publication and validate all cited IDs/hashes and relevant diagnostics; status: pending (source-owner queue).

## Finding index

No source comparison has started; zero confirmed findings recorded. This is not a claim of equivalence.

## Resume checkpoint

- Last completed slice: none; repository workflow, navigation sequence, templates and decompiler README read.
- Next bounded slice and exact files/members to open: first verify source-owner readiness for exact A/B Feather trees, manifests, hashes and diagnostics; then build the ordered class/method correspondence inventory before method comparisons.
- Outstanding dependencies: D-1 and D-2.
- Current assumptions requiring verification: Feather mapping family and exact mapping coordinates for both releases; source-owner marker correctness; no assumption that neighboring-release reports apply.

## Source audit closure

- Coverage counts by status: pending 7 coarse stage inventories; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. Explicit pending count: 7.
- Unresolved gaps and limits: exact release sources and artifacts have not yet been handed off or validated; no behavior has been compared. This run remains active while source preparation proceeds.
- Evidence/hash/correspondence audit: not started; readiness records and each relevant body/hash must be verified before use.
- Runtime validation: not performed; no tests, clients, TAS, gym, server or Docker were launched.
