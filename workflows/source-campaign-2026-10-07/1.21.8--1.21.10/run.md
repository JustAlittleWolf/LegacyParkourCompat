# Discovery: 1.21.8 to 1.21.10

- Status: active
- Track: source-only movement discovery; no Minecraft Wiki/MCPK evidence; no mod implementation inspection or reconciliation in this track.
- Scope: direct client-player movement; older A = `1.21.8`; newer B = `1.21.10`.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`), 2026-10-07.
- Work branch: `feat/source-discovery-movement-source-1-21-8-1-21-10`.
- Selected naming namespace: requested `mojmap` for both sides, pending source-owner publication and exact metadata verification.
- Source preparation: shared owner-managed publication under `build/movement-campaign-2026-10-07`; no source generation performed here.
- Toolchain/decompiler/remapper versions and options: pending validated readiness manifests and source diagnostics.

## Artifact manifest

Source pair is not yet published. The shared `ready/` tree currently has no `1.21.8` or `1.21.10` readiness JSON, source tree, or matching artifact directory. The shared `decompile.lock` is held by the source owner. No directory-presence substitute is treated as readiness. After publication, record exact requested/resolved IDs, client-jar SHA-256, mapping coordinate/build and hash, mapped-jar hash, source root/hash manifest, movement-method diagnostics, successful source-owner log reference, and decompiler/tool versions for both sides. Reject prefix substitution or missing/damaged relevant bodies. Hash each source/resource cited below before completing its slice.

## Correspondence and call order

Pair-specific classes, descriptors, inheritance, callers, replacements, source anchors and call order remain pending until both validated source trees are available. Build the correspondence from the local player entry through input, player/living state, travel, movement/collision, and data/resource dependencies. Record movement state read/written and tick order for every indexed role; do not infer correspondence from matching Mojmap names.

## Coverage ledger

Every row is pending. These stage envelopes must be decomposed into bounded method/resource slices after source inventory. None supports a no-difference conclusion yet.

- Slice S1 / stage 1 / local input and tick ordering (sampling, local/super tick, travel dispatch, input scaling, sprint/jump timers, auto-jump, flight toggles, unstuck and riding gates): pending; both-side evidence awaits source publication.
- Slice S2 / stage 2 / player state and gates (pose, dimensions/eye height, swimming/crawling, flight abilities, sprint gates, active-item state, edge sneaking, air speed and field lifecycle): pending; do not emulate hunger, food, saturation, exhaustion, health, regeneration, damage or combat state.
- Slice S3 / stage 3 / living movement integration (ground/air acceleration, friction, gravity/drag, velocity thresholds, jump and sprint-jump, climbing, water/lava, swimming/gliding, post-travel updates and movement attributes): pending.
- Slice S4 / stage 4 / entity movement and collision (bounding-box resize/query timing, axis order, step candidates/tie-breaking, edge/support probes, grounding, velocity response, fluids/push and block callback order): pending.
- Slice S5 / stage 5 / historical blocks and fluids (reachable shape/provider overrides, registration/defaults, neighboring-state behavior, collision/support, friction/speed/jump callbacks, fluids/flow/bubble columns and client-jar resources): pending; modern-only blocks/states do not gain old behavior.
- Slice S6 / stage 6 / effects, enchantments, attributes, equipment and movement item components (consumer-to-registration/data chains, formulas/operations/order/conditions/slots/tags, server-synchronized inputs): pending.
- Slice S7 / stage 7 / external movement-state writers (incoming velocity/position corrections, player knockback/push, explosions, piston displacement, mount transitions and launch items); independent non-player physics remains out of scope: pending.

## Dependency queue and blockers

- D1 / source owner / both exact `1.21.8` and `1.21.10` source trees plus readiness JSONs in aligned `mojmap`, with exact IDs, source/artifact SHA-256 manifests, per-method diagnostics and publication log. Required because every source comparison depends on integrity and method-body completeness. Next action: wait for source-owner publication; do not run the decompiler or modify shared sources.
- D2 / after D1 / inspect both client jars' relevant resource entries and referenced tags/defaults; resolve which inputs are client vanilla defaults, server-synchronized or externally supplied. Queue precise missing version-matched data if absent.

## Finding index

No findings confirmed yet. Zero is a current count, not a claim of equivalence. Record and disposition each candidate only after checking its reachable player path and dependency closure.

## Resume checkpoint

- Last completed slice: none; repository guidance, workflow contract, source-navigation stages, templates and decompiler implementation reviewed.
- Next bounded slice: verify both published readiness JSONs and their cited IDs, hashes, namespace and diagnostics; then start stage 1 filename inventory and resolve the local input/tick call graph.
- Outstanding dependencies: D1 source publication; D2 resource/data inventory after source correspondence.
- Current assumptions requiring verification: `mojmap` is available and aligned for both exact releases; neither requested ID is prefix-resolved to another release; all movement-relevant bodies are decompiled completely.

## Source audit closure

- Coverage counts by status: pending 7 stage envelopes; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. These envelopes are not yet the eventual slice count.
- Unresolved gaps and limits: comparison has not begun because exact sources are queued; stage envelopes must be split and fully traversed; all findings/no-differences require paired source/resource evidence and dependency closure.
- Evidence/hash/correspondence audit: not applicable until source publication; no evidence is claimed.
- Scope exclusions: health/regen/hunger/food/saturation/exhaustion/damage/combat emulation excluded even where they indirectly gate sprint; non-player physics, modern-only historical behavior, Minecraft Wiki/MCPK, release-note mechanics, and mod implementation inspection excluded.
- Runtime validation: not performed (separate workflow).
