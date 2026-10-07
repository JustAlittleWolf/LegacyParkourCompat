# Movement campaign integration register

Baseline: `002137b227676caea77f6832b9f4c8d0b6200bff` (`main`, 2026-10-07). Integration branch: `feat/movement-completeness-integration`.

This register tracks source discovery, independent coverage review, implementation reconciliation, source-versus-wiki disposition, and final integration separately. `pending` is open work, never a semantic no-difference result. Reports are not accepted until source provenance is verified, every scoped inventory is resolved or explicitly partial, and an independent reviewer records acceptance. Runtime parity is unverified by instruction.

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
