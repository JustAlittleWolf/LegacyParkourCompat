# F-FLIGHT-TOGGLE — 1.13.2 blocks double-tap flight toggle while swimming

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: SPRINT-STATE.
- Classification: added mechanic.
- Applicability: modern-only mechanic (swimming flight gate).
- Confidence: candidate (full-pair dependencies and independent review are open).
- First changed release: unknown within (1.12.2, 1.13.2].
- Runtime validation: not performed.
- Artifact manifests: A original artifact-manifest SHA-256 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; B fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e. Source trees were rehashed against their ready manifests. Revised snapshot revision feather-r1-2026-10-07: A fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87; B b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c. Original mapped jars unavailable; snapshots not proven identical or metadata-only.

## Paired evidence

- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, mobTick lines 747-761; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`.
- B evidence: corresponding member lines 760-775; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
## Source-level difference

- Difference: when flight permission exists, the second jump edge toggles `abilities.flying` in A; B adds `else if (!isSwimming())` before toggling. The first press timer and spectator auto-flight route remain separate guards.
## Reachability and dependencies

- Reachability and dependencies: local-player input edge reaches the branch when not spectator, able to fly, and jumping transitions false-to-true without autojump. B swimming state producer is F-WATER-STATE.
## Consequence and uncertainty

- Consequence and uncertainty: source candidate only; no trajectory measured. Water state call order, reviewer audit and implementation reconciliation remain pending; revised snapshot provenance is verified, while original mapped-bytecode identity remains unproven.

- Revised-artifact provenance: revision ID `feather-r1-2026-10-07`. A immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; A `revision.json` SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; A source manifest `ready/1.12.2/ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; A raw artifact manifest `ready/1.12.2/artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`.
- B immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; B `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; B source manifest `ready/1.13.2/ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; B raw artifact manifest `ready/1.13.2/artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.
- Both revision records state `originalDerivedArtifactAvailable=false`, source trees and raw inputs identical, and original-derived-artifact equivalence unproven. This worker verified the snapshots and manifests; independent ops audit passed on 2026-10-07.
## Handoff
