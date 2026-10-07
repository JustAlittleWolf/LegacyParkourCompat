# Movement campaign integration register

Baseline: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`, 2026-10-07). Integration branch: `feat/movement-completeness-integration`.

This register tracks source discovery, independent coverage review, implementation reconciliation, source-versus-wiki disposition, and final integration separately. `pending` is open work, never a semantic no-difference result. Reports are not accepted until source provenance is verified, every scoped inventory is resolved or explicitly partial, and an independent reviewer records acceptance. Runtime parity is unverified by instruction.

## Integrated checkpoints and current evidence

- Workflow hardening and the final 26-pair endpoint roster are merged from `feat/movement-workflow-completeness` (source commits `40c34f5`, `2422192`; integration merge `2ef6c7a`).
- Removal of health, natural-regeneration, food, hunger/exhaustion hooks and their registrations is merged from `fix/remove-health-food-emulation` at `117ecaa6f30b92895b91e29b8a6c84c3ed26ddb4` (integration merge `26b6179`). Its requested source-admission build remains pending.
- Shared source preparation remains owned by the source owner. I verified the `26.2/unobfuscated` readiness JSON's exact version and namespace and matched the source-manifest SHA-256 (`a7ad74fc…efe894`), artifact-manifest SHA-256 (`ba9dc53a…f5c98ba`), movement-diagnostics SHA-256 (`ba6fd6c5…fe21d3`), native client JAR SHA-256 (`40896ee9…afa290`), and source hashes for `Entity`, `LivingEntity`, `Player`, and `LocalPlayer`. Seven movement source entries are recorded in the marker. This is provenance for static integration review, not discovery completion.
- The 26.2 bytecode audit confirmed descriptors for current Entity collision, fluid, piston/edge, and movement hooks; LivingEntity travel/jump/fluid hooks; Player edge/jump hooks; LocalPlayer input/sprint hooks; and `EntityFluidInteraction$Tracker.applyCurrentTo`. In particular, `Player.aiStep` invokes `Avatar.aiStep` exactly where the current Player mixin attaches its post-travel air-speed update. Other invocation counts, capture ordering and the full registration/profile inventory remain open.
- The initial collision-shape bridge only injected `BlockBehaviour.getCollisionShape`, which misses subclass overrides. The integration branch now dispatches after the virtual block query at `BlockBehaviour.BlockStateBase.getCollisionShape(BlockGetter, BlockPos, CollisionContext)`, so the player-context movement path retains cache bypass and all subclass results before a historical shape is selected. Static source and bytecode show `CollisionGetter` creates player contexts and `BlockCollisions` asks that context for the three-argument shape. This seam change awaits the final static review/build after source preparation is admitted.
- `1.8.9` and `1.9.4` Feather sources are generated, but readiness publication was still underway at handoff. Other exact endpoints remain queued serially. No pair is marked accepted based on source generation alone.
- At the latest coordination update, no source-only report was frozen. A schema-parsing defect in `check_completion.py` is being corrected by the workflow owner; two pair branches carry independent header fixes that must be reconciled to that canonical fix before checker output is treated as evidence.

| # | Pair | Source owner branch | Discovery | Coverage review | Implementation reconciliation | Source/wiki disposition | Integration |
|---:|---|---|---|---|---|---|---|
| 1 | 1.8.9 → 1.9.4 | `feat/source-discovery-movement-source-1-8-9-1-9-4` | pending | pending | pending | pending | pending |
| 2 | 1.9.4 → 1.10.2 | `feat/source-discovery-movement-source-1-9-4-1-10-2` | pending | pending | pending | pending | pending |
| 3 | 1.10.2 → 1.11.2 | `feat/source-discovery-movement-source-1-10-2-1-11-2` | pending | pending | pending | pending | pending |
| 4 | 1.11.2 → 1.12.2 | `feat/source-discovery-movement-source-1-11-2-1-12-2` | pending | pending | pending | pending | pending |
| 5 | 1.12.2 → 1.13.2 | `feat/source-discovery-movement-source-1-12-2-1-13-2` | pending | pending | pending | pending | pending |
| 6 | 1.13.2 → 1.14.4 | `feat/source-discovery-movement-source-1-13-2-1-14-4` | pending | pending | pending | pending | pending |
| 7 | 1.14.4 → 1.15.2 | `feat/source-discovery-movement-source-1-14-4-1-15-2` | pending | pending | pending | pending | pending |
| 8 | 1.15.2 → 1.16.5 | `feat/source-discovery-movement-source-1-15-2-1-16-5` | pending | pending | pending | pending | pending |
| 9 | 1.16.5 → 1.17.1 | `feat/source-discovery-movement-source-1-16-5-1-17-1` | pending | pending | pending | pending | pending |
| 10 | 1.17.1 → 1.18.2 | `feat/source-discovery-movement-source-1-17-1-1-18-2` | pending | pending | pending | pending | pending |
| 11 | 1.18.2 → 1.19.2 | `feat/source-discovery-movement-source-1-18-2-1-19-2` | pending | pending | pending | pending | pending |
| 12 | 1.19.2 → 1.19.3 | `feat/source-discovery-movement-source-1-19-2-1-19-3` | pending | pending | pending | pending | pending |
| 13 | 1.19.3 → 1.19.4 | `feat/source-discovery-movement-source-1-19-3-1-19-4` | pending | pending | pending | pending | pending |
| 14 | 1.19.4 → 1.20.1 | `feat/source-discovery-movement-source-1-19-4-1-20-1` | pending | pending | pending | pending | pending |
| 15 | 1.20.1 → 1.20.2 | `feat/source-discovery-movement-source-1-20-1-1-20-2` | pending | pending | pending | pending | pending |
| 16 | 1.20.2 → 1.20.4 | `feat/source-discovery-movement-source-1-20-2-1-20-4` | pending | pending | pending | pending | pending |
| 17 | 1.20.4 → 1.20.6 | `feat/source-discovery-movement-source-1-20-4-1-20-6` | pending | pending | pending | pending | pending |
| 18 | 1.20.6 → 1.21.1 | `feat/source-discovery-movement-source-1-20-6-1-21-1` | pending | pending | pending | pending | pending |
| 19 | 1.21.1 → 1.21.3 | `feat/source-discovery-movement-source-1-21-1-1-21-3` | pending | pending | pending | pending | pending |
| 20 | 1.21.3 → 1.21.4 | `feat/source-discovery-movement-source-1-21-3-1-21-4` | pending | pending | pending | pending | pending |
| 21 | 1.21.4 → 1.21.5 | `feat/source-discovery-movement-source-1-21-4-1-21-5` | pending | pending | pending | pending | pending |
| 22 | 1.21.5 → 1.21.8 | `feat/source-discovery-movement-source-1-21-5-1-21-8` | pending | pending | pending | pending | pending |
| 23 | 1.21.8 → 1.21.10 | `feat/source-discovery-movement-source-1-21-8-1-21-10` | pending | pending | pending | pending | pending |
| 24 | 1.21.10 → 1.21.11 | `feat/source-discovery-movement-source-1-21-10-1-21-11` | pending | pending | pending | pending | pending |
| 25 | 1.21.11 → 26.1.2 | `feat/source-discovery-movement-source-1-21-11-26-1-2` | pending | pending | pending | pending | pending |
| 26 | 26.1.2 → 26.2 | `feat/source-discovery-movement-source-26-1-2-26-2` | pending | pending | pending | pending | pending |

## Integration gates

- [ ] Each pair has a frozen source report with verified exact source IDs, namespace, readiness JSON, hashes, and method-body diagnostics.
- [ ] Each pair has an independent full-player-path coverage review; every routed miss has a disposition.
- [ ] Every finding has an implementation branch or an explicit evidence-backed exclusion/defer decision. Version boundaries are exact-source-confirmed; closest-applicable resolver semantics and independent mechanic keys are preserved.
- [ ] Source and each wiki audit are reconciled only after source report freeze; no wiki-only claim is attributed to source discovery.
- [ ] Preserve the current movement-only scope: no health, food, regeneration, hunger, saturation, exhaustion, damage, or combat emulation; no modern-only historical content or non-player physics.
- [ ] Verify native 26.2 bytecode injection contracts, selectors/descriptors/ordinals/slices/capture/order, registrations/profiles, block-shape interception and cache/subclass behavior, pose/resize live switching, and absence of duplicate/conflicting mechanic versions.
- [ ] Run only authorized static checks/build after source-preparation admission; all Gradle `Test` tasks disabled and `-x test`. No runtime parity claim without TAS validation; runtime remains explicitly unverified here.
- [ ] Merge current `main`, inspect post-baseline commits semantically, make final integration commit, and sync the verified integration branch into the clean parent checkout without touching `main`.
