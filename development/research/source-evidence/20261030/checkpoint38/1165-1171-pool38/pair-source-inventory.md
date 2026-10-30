# Pair source inventory and independent audit status

Exact pair: Mojmap 1.16.5 → Mojmap 1.17.1. Canonical source root is the read-only `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

## Complete case-sensitive Java path inventory

`case-sensitive-java-inventory.tsv` enumerates the ordinal source-relative Java paths present in either exact tree and stores each endpoint SHA-256 plus one of `same`, `changed`, `added-1.17.1`, or `removed-after-1.16.5`. Counts independently derived from the exact files:

- 1.16.5: 3,523 Java files; 1.17.1: 4,142 Java files.
- Union: 4,206 case-sensitive paths.
- 1,041 paired paths have identical content hashes; 2,418 paired paths differ; 683 paths exist only in 1.17.1; 64 paths exist only in 1.16.5.
- Endpoint path/hash identity is not semantic movement coverage by itself. The complete changed path set still needs movement-scope triage and dependency closure.

`inventory-summary.json` contains compact counts and endpoint metadata. Both complete source manifests have the same number of rows as the actual Java files; every inventory hash matched its endpoint manifest (zero missing entries, zero mismatches). Both ready markers bind the observed source and artifact manifest hashes. The source identities are recorded in that JSON.

## Finding rechecks and limits

- **Collision-resolved position reconstruction (`Entity.move`):** endpoint delta is present. 1.16.5 uses translated AABB edges then `setLocationFromBoundingbox`; 1.17.1 uses `setPos(position + resolved displacement)` under the `lengthSqr() > 1.0E-7` gate. Prior independent 1.16.5→1.17 source audit accepted only the bounded source operation and a conditional arithmetic example, not a tick witness or pair closure. Exact 1.17.1 player route and endpoint comparison are included in the ordinary-tick worker slice.
- **Jump Boost addition precision (`LivingEntity.jumpFromGround`):** endpoint source change confirmed. At base `0.42F`, amplifier 0 gives old single-precision sum `0.5199999809265137` (`0x3F051EB8`) versus promoted double sum `0.5199999883770943` (`0x3FE0A3D704000000`). This is a numeric source-expression witness for the same effect/base values, not runtime parity evidence. Player jump dispatch/gates are included in the ordinary-tick slice.
- **Push impulse square-root precision (`Entity.push`):** endpoint expression changes `Mth.sqrt(f)` (single result) to `Math.sqrt(f)` (double result). The player path is through a nearby `RemotePlayer` on the client: `RemotePlayer.tick()` calls `super.tick()`, its virtual `aiStep()` calls `pushEntities()`, and `EntitySelector.pushableBy` admits the local player as a pushable player candidate on the client; `LivingEntity.doPush` calls `entity.push(this)`. For conditional player-center deltas `(dx,dz)=(0.25,0.5)`, the two 0.6-wide player boxes overlap in both axes and `absMax=0.5` passes the `0.01F` gate. With the remote player default `pushthrough=0`, the local-player X impulse is `0.01767767009562193` versus `0.01767766979308149` (same `0.05F` scale; difference `-3.0254044061650553E-10`). This is an arithmetic witness with a reachable source route and stated conditional geometry, not a recorded trajectory.
- **Fluid scan gate and active-height fluid queries:** prior exact endpoint closure/audit is bounded and source claims recheck. 1.16.5 uses a Y-sensitive missing-chunk scan gate and fixed `[0,256)` query admission; 1.17.1 uses an X/Z missing-chunk gate plus active-dimension query admission. Existing closure reports all built-in 1.17.1 dimension defaults at `minY=0`; no concrete custom dimension/value or movement witness was supplied. Preserve that limit. Fluid/contact provider and dependency closure is assigned separately.
- **Swimming entry WATER predicate:** 1.17.1 adds `getFluidState(blockPosition).is(FluidTags.WATER)` on the entry branch. Source route is present, but the concrete non-WATER feet fluid/tag precondition remains open in the prior audit; fluid/contact worker checks source dependencies. Do not promote to a concrete behavior finding without that evidence.

## Coverage boundaries

Checked source artifacts: endpoint ready/provenance/source/artifact manifests, all Java file path/content-hash rows, the prior exact pair closures/audits listed in the assignment, and direct endpoint source for the candidate operations above. Source workers are independently checking ordinary tick/effect/modifier readers, collision/support/shape/callback providers, and fluids/contact providers.

Not closed here: a complete ordered inventory of every movement terminal slice; every changed path's scope triage; block-shape/provider registration and callback closure; fluid/block resources/tags; all attribute/effect/equipment providers; external position/velocity response paths; exact patch introduction dates within the interval; and an independent complete-pair coverage audit. No whole-pair freeze or runtime-parity claim is made.
