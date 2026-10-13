# Independent review: sprint air-speed early-release boundary

## Decision

**ACCEPT** — the memo's bounded source conclusion and exact-source evidence are verified for the releases it names. This decision covers the sprint-conditioned ordinary airborne-speed producer, its ordinary-air consumer, and the producer/consumer tick order. It does not freeze any release pair or establish runtime parity.

## Immutable inputs and preserved history

- Review base: `847e7d101780d7fb39aae523b932c08f6fb6eb19`.
- Reviewed memo: [`boundary-memos/sprint-air-speed-early-release-boundary-2026-10-09.md`](../boundary-memos/sprint-air-speed-early-release-boundary-2026-10-09.md), SHA-256 `42f3584c4372afe80269c4f47ada16ff7c570fcbbff7a2664c0a422389f6427c`.
- The memo preserves prior identities `memo1c4317af04f70a596642369a402ce38ccb5492da` and `independentsourceACCEPTee586d98cbd02df932f6f076daa56a9d2176557b`. This review does not revise, supersede, or invalidate either predecessor.
- Reviewed source publications read-only from `build/movement-campaign-2026-10-07/ready/`; no decompilation, implementation, wiki/MCPK, tests, builds, game launches, or runtime checks were used.

## Evidence checks

The memo names 23 exact releases, from 1.8.9 through 1.18.2. For all 23 rows, I independently checked the ready marker's version, mapping, status, and manifest references; the marker SHA-256; the complete source-manifest SHA-256 against both the memo and marker; the artifact-manifest SHA-256 against the marker; and both cited source-file SHA-256 values against the source bytes and their manifest entries. All 23 rows matched. The memo's class paths and source line references resolve in those publications.

Mappings are `ornithe-feather` for 1.8.9–1.14.4 and `mojmap` for 1.15–1.18.2. The artifact-manifest paths and verified SHA-256 values are:

| Release | Mapping | Artifact manifest | SHA-256 |
|---|---|---|---|
| 1.8.9 | ornithe-feather | `artifacts.sha256` | `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446` |
| 1.9.4 | ornithe-feather | `artifacts.sha256` | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` |
| 1.10.2 | ornithe-feather | `artifacts.sha256` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` |
| 1.11 | ornithe-feather | `artifacts.sha256` | `ae874615f961d67e402c978ff01cd3f26f61145e6b3c21ef318c1ab71f7794fd` |
| 1.11.1 | ornithe-feather | `artifacts.sha256` | `ebbf181f60d7bc922df56826f528d1ea8a064783ac0f0f2b576e73db1fc2a958` |
| 1.11.2 | ornithe-feather | `artifacts.sha256` | `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` |
| 1.12.2 | ornithe-feather | `artifacts.sha256` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` |
| 1.13 | ornithe-feather | `ornithe-feather.artifacts.sha256` | `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24` |
| 1.13.1 | ornithe-feather | `ornithe-feather.artifacts.sha256` | `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c` |
| 1.13.2 | ornithe-feather | `artifacts.sha256` | `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` |
| 1.14 | ornithe-feather | `ornithe-feather.artifacts.sha256` | `2fdf423e2fbe0d20ec961cc42cd69b3107caeed88edf3e097ee588d29868f5df` |
| 1.14.4 | ornithe-feather | `ornithe-feather.artifacts.sha256` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` |
| 1.15 | mojmap | `artifacts.sha256` | `acc29974470e4f4864c695e1f1d084e746874663c47a365ca1fa41ff9fcc0cae` |
| 1.15.1 | mojmap | `artifacts.sha256` | `d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb` |
| 1.15.2 | mojmap | `artifacts.sha256` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |
| 1.16 | mojmap | `artifacts.sha256` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` |
| 1.16.1 | mojmap | `artifacts.sha256` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| 1.16.2 | mojmap | `artifacts.sha256` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |
| 1.16.5 | mojmap | `artifacts.sha256` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` |
| 1.17.1 | mojmap | `artifacts.sha256` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |
| 1.18 | mojmap | `artifacts.sha256` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` |
| 1.18.1 | mojmap | `artifacts.sha256` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` |
| 1.18.2 | mojmap | `artifacts.sha256` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` |

The producer expression and airborne consumer line were read at every cited range. The arithmetic claim is correct: through 1.18.1, the increment is an unsuffixed double expression followed by an explicit float conversion (`flyingSpeed * 0.3` in the earlier field form; `+ 0.005999999865889549` in the later form). In 1.18.2, `flyingSpeed += 0.006F` uses a float-suffixed increment and compound-assignment conversion. The consumer remains the ordinary airborne branch (`speedInAir` in the earlier sources, then `flyingSpeed`) in each sampled release.

The ordering claim also holds across all 23 releases: each Player override calls `super.mobTick()` or `super.aiStep()` before writing the sprint-conditioned air speed. In every exact LivingEntity file, the corresponding superclass tick method contains its movement call before returning (`moveRelative` in the older form; `travel` in the newer form), whose ordinary airborne branch reads that stored value:

| Release | LivingEntity method | Movement call line |
|---|---|---:|
| 1.8.9 | `mobTick()` 1385 | `moveRelative` 1450 |
| 1.9.4 | `mobTick()` 1645 | `moveRelative` 1711 |
| 1.10.2 | `mobTick()` 1681 | `moveRelative` 1747 |
| 1.11 | `mobTick()` 1735 | `moveRelative` 1801 |
| 1.11.1 | `mobTick()` 1737 | `moveRelative` 1803 |
| 1.11.2 | `mobTick()` 1737 | `moveRelative` 1803 |
| 1.12.2 | `mobTick()` 1781 | `moveRelative` 1847 |
| 1.13 | `mobTick()` 1833 | `moveRelative` 1907 |
| 1.13.1 | `mobTick()` 1852 | `moveRelative` 1926 |
| 1.13.2 | `mobTick()` 1852 | `moveRelative` 1926 |
| 1.14 | `mobTick()` 2143 | `moveRelative` 2220 |
| 1.14.4 | `mobTick()` 2150 | `moveRelative` 2227 |
| 1.15 | `aiStep()` 2207 | `travel` 2287 |
| 1.15.1 | `aiStep()` 2207 | `travel` 2287 |
| 1.15.2 | `aiStep()` 2207 | `travel` 2287 |
| 1.16 | `aiStep()` 2366 | `travel` 2457 |
| 1.16.1 | `aiStep()` 2366 | `travel` 2457 |
| 1.16.2 | `aiStep()` 2368 | `travel` 2459 |
| 1.16.5 | `aiStep()` 2368 | `travel` 2459 |
| 1.17.1 | `aiStep()` 2455 | `travel` 2546 |
| 1.18 | `aiStep()` 2457 | `travel` 2548 |
| 1.18.1 | `aiStep()` 2457 | `travel` 2548 |
| 1.18.2 | `aiStep()` 2461 | `travel` 2552 |

Thus the write prepares the value for the next eligible ordinary-air movement call. This timing conclusion excludes the separately identified temporary abilities-flight override, as the memo states.

The memo's 14 unsampled exact releases were checked against the ready-publication directory list and are absent: `1.9`, `1.9.1`, `1.9.2`, `1.9.3`, `1.10`, `1.10.1`, `1.12`, `1.12.1`, `1.14.1`, `1.14.2`, `1.14.3`, `1.16.3`, `1.16.4`, and `1.17`. The memo correctly reports no behavior for them. Its result is limited to the listed samples; it does not establish the first changed release across an unsampled interval.

## Resumable state and next action

This bounded memo is accepted as source evidence for its sampled air-speed boundary. The separate release-pair discovery remains open: continue its remaining tick/inventory slices and dependencies under the discovery workflow, and keep pair completion and runtime validation separate. Preserve the two predecessor identities above unchanged. No implementation or pair-wide status update follows from this review alone.
