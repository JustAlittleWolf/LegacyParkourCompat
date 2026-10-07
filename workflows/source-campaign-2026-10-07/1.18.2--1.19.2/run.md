# Movement source comparison: 1.18.2 to 1.19.2

- Status: active
- Scope: source-only audit of direct client player movement; older A = 1.18.2; newer B = 1.19.2. Excludes health, regeneration, hunger, food, saturation, exhaustion, damage, and combat emulation; excludes non-player physics and newly historical behavior for modern-only blocks/features.
- Repository revision and start date: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main` at worktree creation); 2026-10-07.
- Track declaration: movement-source discovery only; no wiki/MCPK audit and no mod implementation inspection or reconciliation in this track.
- Selected naming namespace, CLI mode per side and alignment evidence: aligned Mojmap (`mojmap` CLI mode; output directory `mojmap`) for both exact releases. Not yet verified: both readiness manifests are unpublished. No source comparison claims until exact IDs, namespace, manifest hashes, and diagnostics are checked.
- Source preparation command and log: source-owner publication through shared root `build/movement-campaign-2026-10-07/ready/`; no command run by this worker. Awaiting exact release readiness and owner confirmation.
- Toolchain/decompiler/remapper versions and options: repository catalog pins Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping-IO 0.9.1, Gson 2.14.0, ASM 9.10.1; decompiler task forks Java 25, maximum heap default 4G, generic signatures and ASCII string characters enabled, synthetic members removed, four-space indent, Java runtime excluded, allowed prefixes `net/minecraft` and `com/mojang`. Actual source-owner run options and JDK runtime remain to be recorded from the readiness evidence.

## Artifact manifest

### A — 1.18.2

- Requested/resolved release: 1.18.2 / pending readiness JSON verification.
- Source root: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/` (expected; currently not published).
- Client jar identity/hash: pending.
- CLI mode / namespace: expected `mojmap` / Mojang official names; pending exact manifest.
- Mapping coordinate/build, mapping file/hash, bridge, mapped jar hash: pending.
- Source manifest and per-cited-source hashes: pending.
- Source diagnostics/log: pending.

### B — 1.19.2

- Requested/resolved release: 1.19.2 / pending readiness JSON verification.
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/` (expected; currently not published).
- Client jar identity/hash: pending.
- CLI mode / namespace: expected `mojmap` / Mojang official names; pending exact manifest.
- Mapping coordinate/build, mapping file/hash, bridge, mapped jar hash: pending.
- Source manifest and per-cited-source hashes: pending.
- Source diagnostics/log: pending.

## Correspondence and call order

- Pending exact-source publication and method inventory. Names and method correspondence will be recorded from exact source bodies, inheritance, callers, descriptors, and state reads/writes on both sides; no member-name assumptions are treated as evidence.
- Ordered stages are input/tick; player state and gates; living movement integration; entity movement/collision; movement-producing blocks/fluids; movement effects/enchantments/attributes/equipment; external movement influences and dependency closure. Full call chains, state writers, callback implementations, registration/data sources, and execution order remain open.

## Coverage ledger

- Slice `S1` / stage 1 / local input and tick ordering: pending — exact sources not published; input producers, local/super tick, and travel callers not yet inventoried.
- Slice `S2` / stage 2 / player-specific state and gates: pending — exact sources not published; pose/dimensions, flight, sprint/jump gates, movement fields, and non-excluded state consumers not yet inventoried.
- Slice `S3` / stage 3 / living movement integration: pending — exact sources not published; all reachable travel branches, thresholds, jump/sprint/flight math, pre/post-travel updates, and helpers not yet inventoried.
- Slice `S4` / stage 4 / entity movement and collision: pending — exact sources not published; move/axis/step/support/callback/query order, collision shapes, and state writers not yet inventoried.
- Slice `S5` / stage 5 / movement-producing blocks and fluids: pending — exact sources not published; registrations, overrides, shape providers, neighbor/state behavior, and fluid calculations not yet inventoried.
- Slice `S6` / stage 6 / movement effects, enchantments, attributes, and equipment: pending — exact sources/resources not published; consumers, producers, registration/data, predicates, slots, and synchronized input boundaries not yet inventoried.
- Slice `S7` / stage 7 / external movement influences and dependency closure: pending — exact sources not published; player velocity/position writers, packets, player knockback/push, piston movement, mount transitions, and launch items not yet inventoried.

## Dependency queue and blockers

- `D-SOURCES`: originating slices `S1`–`S7`; exact release source publications for 1.18.2 and 1.19.2, aligned namespace, validated readiness JSON, source/artifact SHA-256 manifests, and method diagnostics are missing. These are required for every source claim. Next action: wait for the source owner’s exact-release publication; verify IDs, namespace, manifest paths/hashes, and relevant method-body diagnostics before use. Current state: pending, not yet a comparison blocker because the owner has a queued publication path.
- `D-RESOURCES`: originating slice `S6` and any resource-dependent block/fluid slice; inspect matching client-jar resource entries and hashes only after the client-jar identity is verified. Class-source absence will not be treated as absence of data-driven entries.
- No conclusions are inferred from prior adjacent-pair reports; those reports are navigation aids only and are not used to declare any slice covered.

## Finding index

- No findings recorded yet; source comparison has not begun.

## Resume checkpoint

- Last completed slice: none; setup, workflow review, branch, base, scope, and decompiler catalog inventory are complete.
- Next bounded action: verify exact 1.18.2 and 1.19.2 `mojmap.ready.json`, source/artifact manifests, and `movement-diagnostics.txt`; establish paired source provenance and filename/member correspondence before stage 1 comparison.
- Outstanding dependencies: `D-SOURCES`; then source/resource dependencies discovered by complete stage navigation.
- Current assumptions requiring verification: Mojmap artifacts are available and namespace-aligned for both releases; expected folder names are exact; both published source bodies are suitable for all movement members. Do not infer any of these from the presence of source directories.

## Source audit closure

- Coverage counts by status: pending 7; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0.
- Unresolved gaps and limits: the comparison has not started because the source owner has not published either exact pair endpoint. No coverage slice is complete.
- Evidence/hash/correspondence audit: setup records only; no source evidence or movement equivalence claim is cited.
- Runtime validation: not performed (separate workflow).



