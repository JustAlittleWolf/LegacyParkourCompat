# Movement source campaign: 1.19.4 to 1.20.1

- Status: active
- Scope: direct client-player movement; older A = 1.19.4; newer B = 1.20.1.
- Track: source-only discovery; no wiki comparison and no mod implementation inspection.
- Repository base revision: 002137b227676caea77f6832b9f4c8d0b6200bff (main, verified).
- Dedicated branch: feat/source-discovery-movement-source-1-19-4-1-20-1.
- Start date: 2026-10-07 (Europe/Vienna).
- Source request: exact 1.19.4/feather and 1.20.1/feather publications requested from the source owner; both are absent from the currently published ready catalog.
- Source preparation command/log and toolchain: pending source-owner publication; this worker will not invoke the shared decompiler.
- Release taxonomy: adjacent content-update endpoints 1.19.4 -> 1.20.1; no release-note evidence used.

## Artifact manifest

Source-owner protocol root: build/movement-campaign-2026-10-07/. The checked-in checkout currently contains ready markers for 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12.2, 1.13.2 (Feather) and 26.2 (unobfuscated). Exact pair readiness is not established by folder presence and remains pending.

### A — 1.19.4

- Requested/resolved release: pending exact ready JSON verification.
- Namespace/CLI mode: Feather / feather, requested; alignment pending.
- Source root, client jar identity/hash, source/artifact manifest hashes, mapping coordinate/build/hash, remapped jar hash, logs and method diagnostics: pending.

### B — 1.20.1

- Requested/resolved release: pending exact ready JSON verification.
- Namespace/CLI mode: Feather / feather, requested; alignment pending.
- Source root, client jar identity/hash, source/artifact manifest hashes, mapping coordinate/build/hash, remapped jar hash, logs and method diagnostics: pending.

No source code, Minecraft jar, or generated artifact has been copied into the worktree or committed.

## Correspondence and call order

Pending the exact published pair. The inventory will resolve each role to exact class/member descriptors, inheritance, callers, dependencies, and read/write state on both sides before assigning correspondence. No names or behavior are presumed equivalent from neighboring reports.

## Coverage ledger

Every row is open until paired methods, reachable callers/writers and dependency closure have been inspected. Work proceeds in source-navigation order.

- I1 / Stage 1: local input, client-player tick order, input sampling, sprint/jump/flight/auto-jump/riding gates — pending source pair.
- I2 / Stage 2: player state, pose/dimensions/resize, eye height, flight and movement-relevant state — pending source pair.
- I3 / Stage 3: living travel dispatch, ground/air/fluid/climbing/glide physics, jump, velocity thresholds and post-travel — pending source pair.
- I4 / Stage 4: entity move/collision, axis/step candidates, edge/support queries, callbacks, AABB/shapes and collision-query timing — pending source pair.
- I5 / Stage 5: block/fluid registrations, shapes/providers, neighbors, friction/speed/jump factors, movement callbacks — pending source pair.
- I6 / Stage 6: in-scope effects, attributes, enchantments and equipment consumer-to-registration/data chains — pending source pair. Health, regeneration, hunger, food, saturation, exhaustion, damage and combat emulation are excluded.
- I7 / Stage 7: client-consumed external velocity/position, knockback/push, explosion, piston displacement, mount transitions and launch-item inputs — pending source pair.
- Cross-stage closure: enumerate and revisit changed callees, state writers, callback implementations, registries/resources and newly reachable dependencies — pending pair plus I1–I7 inventories.

The movement math audit will preserve source operation order, casts, suffixes, float/double boundaries and historical quirks. Modern-only blocks/features will not be assigned old behavior; non-player physics are out of scope.

## Dependency queue and blockers

- D0 — source owner: publish/confirm exact 1.19.4/feather and 1.20.1/feather ready JSON, source/artifact SHA-256 manifests, exact IDs/namespaces, successful decompiler provenance and method-body diagnostics. The source owner exclusively writes shared sources. No source-level comparison can close until both markers and cited files are validated.
- D1 — after D0: inspect relevant decompiler body diagnostics and queue any damaged body or missing artifact with exact member and side.
- D2 — after I5/I6: inspect matched client-jar resources/tags/defaults referenced by movement consumers; establish version-matched provenance and hashes.
- No game/TAS/gym/server/Docker actions, Gradle build, tests, source generation, wiki browsing, release-notes evidence, mod implementation inspection or runtime validation are part of this track.

## Finding index

No source-confirmed findings yet. This is not a no-difference conclusion; the exact pair is not yet ready.

## Resume checkpoint

- Last completed slice: project and workflow instructions read; dedicated worktree branch created from verified main commit.
- Next bounded slice: validate exact Ready JSON for A/B, hashes/manifests and method diagnostics, then inventory Stage 1 input/tick entry points on both source trees.
- Outstanding dependencies: D0–D2.
- Current assumptions requiring verification: both releases can be decompiled in aligned Feather namespace; each ready marker names the requested exact release and source/artifact hashes.

## Source audit closure

- Coverage counts: 0 terminal; 7 stage-level rows pending source publication; cross-stage closure pending.
- Unresolved gaps: exact endpoint source publication and provenance; all method inventories, correspondences, resource dependencies and mechanics comparison.
- Evidence/hash/correspondence audit: no source evidence accepted yet; do not promote any row from directory presence or a prior report.
- Runtime validation: not performed (separate workflow).
