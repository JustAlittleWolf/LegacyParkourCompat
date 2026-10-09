# Campaign status snapshot — 2026-10-09

Status/provenance audit at local `main` `3467cc398a89bb86bfafa6eedb315e0a29e040de`; the status branch merged it at `dcabe4aaf5e006d23116abf3f65bde9bb28396cb`. The cited `285244f893d381604ce7bc7cb6f96874ad95f05a` exists but is older. The queue records an earlier `mainVerified` value (`cb11257…`) and is treated as a dated snapshot, not a live roster.

## Stage status

| Stage | Current count/status | Evidence boundary |
|---|---:|---|
| full-pair source freeze | 1/26 accepted | Only 1.17.1--1.18.2 has an accepted full-pair source freeze. |
| independent full-pair coverage audit | 1/26 accepted | Same single interval; distinct gate from finding acceptance and implementation. |
| accepted finding backlog | 47 cumulative ledger records | Baseline/current ledger count; no defensible new-resume increment or deduplicated whole-campaign unique-finding count. |
| new resume implementation dispositions | 2 final no-code technical ACCEPTs | F001 sprint collision and F002 Elytra cosine; bounded to reviewed evidence, no Java change required. |
| new resume code candidates | 1/2 integrated with successful build; F3 REQUEST CHANGES pending | F005 technical review ACCEPT, integration recorded, and disabled-test build successful (18 actionable tasks). F3 R2 review REQUEST CHANGES; correction, new review and build pending. |
| integrated code batch ledger | 23 cumulative records | Heterogeneous ledger rows, not a count of implemented mechanics; no campaign-wide implementation numerator claimed. |
| new resume source checkpoints | 2 noted; both partial | 1.21.4--1.21.5 fresh input/tick checkpoint; 1.21.11--26.1.2 S4.6 mounted-fluid witness. |
| runtime parity | 0/26 documented complete; overall unverified | No runtime result is inferred from accepted evidence, code review, integration or builds. |
| active worker roster | unknown; soft target about 15 | Current policy excludes the coordinator and unrelated chats from the occupancy target; no current live roster evidence was used. |

The accepted-findings ledger has 47 cumulative records and the integrated-code-batch ledger has 23 cumulative rows. These heterogeneous ledger counts do not include a defensible new-resume delta or a campaign-wide implemented-mechanics total; F005’s newly integrated build is reported separately below.

## Resume-specific progress

- **F001 and F002:** two final bounded no-code technical ACCEPTs; no Java change was identified for the reviewed boundaries.
- **F005:** corrected source witness and exact 1.18.2 boundary are accepted. Independent implementation review accepted code `b4f2cc3` at tip `f73d176e`; review commit `7cb12f7`, report blob `873037c5…` binds the verdict. Integration and build are recorded in `workflows/fix-implementation/runs/F005-fall-reset-integration-2026-10-09.md` (record commit `c8200d3`): `BUILD SUCCESSFUL`, 18 actionable tasks with all Test tasks disabled. Runtime remains unverified.
- **F3:** R2 source finding is accepted, but the implementation technical review returned **REQUEST CHANGES** on mounted-player applicability (review merge `8713e344`). The correction author is queued; a new review and build remain pending.
- **Source checkpoints:** the fresh 1.21.4–1.21.5 input/tick slice and 1.21.11–26.1.2 S4.6 mounted-fluid witness remain partial. F012 has a bounded source-boundary ACCEPT and does not change pair freeze.

## All 26 exact-source intervals

| Interval | Resume branch @ recorded head | Verified pair status |
|---|---|---|
| 1.8.9--1.9.4 | `feat/source-discovery-movement-source-1-8-9-1-9-4-solid-resume` @ `690577535f5f` | partial/open |
| 1.9.4--1.10.2 | `feat/source-discovery-movement-source-1-9-4-1-10-2-resume3` @ `8d4deeedacd0` | partial/open |
| 1.10.2--1.11.2 | `feat/source-discovery-movement-source-1-10-2-1-11-2` @ `645656e32b40` | partial/open |
| 1.11.2--1.12.2 | `feat/source-discovery-movement-source-1-11-2-1-12-2` @ `f4d235379ea4` | partial/open |
| 1.12.2--1.13.2 | `feat/source-discovery-movement-source-1-12-2-1-13-2` @ `2853716b2031` | partial/open |
| 1.13.2--1.14.4 | `feat/source-movement-1-13-2-1-14-4-resume6` @ `c7fda155df85` | partial/open |
| 1.14.4--1.15.2 | `feat/source-discovery-movement-source-1-14-4-1-15-2` @ `11e4c0b7310e` | partial/open |
| 1.15.2--1.16.5 | `feat/source-discovery-1-15-2-1-16-5-resume` @ `f5636dd2fb01` | partial/open |
| 1.16.5--1.17.1 | `feat/source-discovery-movement-source-1-16-5-1-17-1-audit-oct8` @ `aca4dfb24d5f` | partial/open |
| 1.17.1--1.18.2 | `feat/source-1-17-1-18-2-fresh-oct8` @ `df418e06b957` | full-pair source freeze accepted; independent coverage audit accepted |
| 1.18.2--1.19.2 | `feat/source-discovery-movement-source-1-18-2-1-19-2` @ `ee009fe6d26b` | partial/open |
| 1.19.2--1.19.3 | `feat/source-discovery-movement-1-19-2-1-19-3-resume` @ `0b2c91f8ad29` | partial/open |
| 1.19.3--1.19.4 | `feat/source-discovery-1-19-3-1-19-4-resume-2026-10-08` @ `65053957149e` | partial/open |
| 1.19.4--1.20.1 | `feat/source-discovery-1-19-4-1-20-1-continuation` @ `eb6e73602c4c` | partial/open |
| 1.20.1--1.20.2 | `feat/source-discovery-1-20-1-1-20-2-resume-2026-10-08` @ `7a9fb8ab571a` | partial/open |
| 1.20.2--1.20.4 | `feat/source-discovery-1-20-2-1-20-4-resume-2026-10-08` @ `cbcd477f1e1e` | partial/open |
| 1.20.4--1.20.6 | `feat/source-discovery-1-20-4-1-20-6-resume-2026-10-08` @ `f14b8d63b79d` | partial/open |
| 1.20.6--1.21.1 | `feat/source-discovery-1-20-6-1-21-1-resume-2026-10-08` @ `9b58e85bdf70` | partial/open |
| 1.21.1--1.21.3 | `feat/source-discovery-1-21-1-1-21-3-resume-2026-10-08` @ `864d42197b90` | partial/open |
| 1.21.3--1.21.4 | `feat/source-discovery-movement-source-1-21-3-1-21-4-resume-2026-10-08` @ `5a52807348c0` | partial/open |
| 1.21.4--1.21.5 | `feat/source-discovery-movement-source-1-21-4-1-21-5-resume` @ `1d027c706dd2` | partial/open |
| 1.21.5--1.21.8 | `feat/source-discovery-movement-source-1-21-5-1-21-8-resume-2026-10-08` @ `ec84e77c333b` | partial/open |
| 1.21.8--1.21.10 | `feat/source-discovery-movement-1-21-8-1-21-10-resume-2026-10-08` @ `2642f9401ab5` | partial/open |
| 1.21.10--1.21.11 | `feat/source-discovery-1-21-10-1-21-11-resume-2026-10-08` @ `bacbfd252724` | partial/open |
| 1.21.11--26.1.2 | `feat/source-1-21-11-26-1-2-resume-oct8` @ `ea6ca59bd217` | partial/open |
| 26.1.2--26.2 | `feat/source-26-1-2-26-2-resume-oct8` @ `7455dc2dc987` | partial/open |

Only 1.17.1–1.18.2 is counted complete for source freeze and independent pair audit. Its immutable audit event is the corrected ACCEPT in commit `e0e36eff36a92be82327bf2a8140f18141d7cc21`, bound to frozen `run.md` blob `53bef7aae76bef026d5f5d19bc111635ddc6265d` (raw SHA-256 `70610a914b440ab651163f1ccd708000f4261da24727f7a4614eca473095b3f5`). The original audit commit `7872eb6457f051df3575f10299ed1aaf1c13eddb` is preserved as REQUEST CHANGES; the corrected event supersedes that verdict without changing the source evidence. The resume TSV and imported branch head predate the correction, so this snapshot reconciles that stale `partial` label against `run.md` and the exact audit binding.

The other 25 intervals were partial before this resume. The current main record contains no additional accepted full-pair freeze or independent full-pair audit among them. New bounded findings, accepted source slices, no-code dispositions, and code candidates do not close those pair-wide gates.

## Provenance and limits

- Interval names, source-owner branches and recorded heads: `workflows/branch-consolidation-2026-10-08/source-resume-heads.tsv` (26 data rows).
- Current scheduling snapshot: `workflows/orchestration-2026-10-09/queue.json`; historical `active` task fields are not a current worker count. **Active roster count: unknown; coordinator to refresh.** Current policy targets about 15 active campaign workers, excluding the coordinator and unrelated chats; this is a soft occupancy target, not a hard maximum. See `workflows/orchestration/session-policy-2026-10-09.md`.
- Integration decisions and current review events: `workflows/orchestration-2026-10-09/integration.md`, including the F3 R2 REQUEST CHANGES, F005 implementation technical ACCEPT, and F012 source-boundary ACCEPT.
- Prior accepted backlog: `workflows/accepted-findings-backlog-2026-10-08/accepted-findings.jsonl` (47 records); integration records: `integrated-code-batches.jsonl` (23 rows). These are not additive resume-progress totals.
- Runtime parity: unverified. No build, tests, game/TAS/Gym/server launch, Docker restart or push was performed for this snapshot.
