# F-WATER-SPRINT-TRAVEL — 1.13.2 sprinting changes water drag and skips gravity

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: TRAVEL-MODIFIERS.
- Classification: changed behavior.
- Applicability: historical player behavior in water.
- Confidence: candidate (full-pair dependencies and independent review are open).
- First changed release: unknown within (1.12.2, 1.13.2].
- Runtime validation: not performed.
- Artifact manifests: A original artifact-manifest SHA-256 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; B fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e. Source trees were rehashed against their ready manifests. Revised snapshot revision feather-r1-2026-10-07: A fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87; B b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c. Original mapped jars unavailable; snapshots not proven identical or metadata-only.
- Evidence artifact records: `EA-FEATHER-R1-A` and `EA-FEATHER-R1-B` (see `run.md`, Artifact evidence identities).

## Paired evidence

- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative(FFF)V`, lines 1555-1584; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`. Water multiplier begins with base movement multiplier; with Depth Strider absent it resolves to 0.8F. Gravity applies `velocityY -= 0.02` when enabled.
- B evidence: corresponding member, lines 1617-1655; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`. Water multiplier begins at 0.9F when sprinting (before Depth Strider/Dolphin's Grace adjustments), and the gravity update is guarded by `!isSprinting()`.
- Preconditions/reachability: player movement in water with sprint already active; no Depth Strider or Dolphin's Grace for the quoted horizontal multiplier comparison; gravity enabled. Sprint eligibility differs separately in F-WATER-SPRINT.
## Source-level difference

- Difference: with the stated inputs, A multiplies X/Z velocity by 0.8F and applies water gravity; B multiplies X/Z by 0.9F and skips the water gravity update.
## Consequence and uncertainty

- Consequence and uncertainty: source proves the update branch and coefficients under the stated modifier exclusions; it predicts momentum retention but no trajectory was measured. Effect/enchantment registration and input-state closure remain open.

- Revised-artifact provenance: revision ID `feather-r1-2026-10-07`. A immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; A `revision.json` SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; A source manifest `ready/1.12.2/ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; A raw artifact manifest `ready/1.12.2/artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`.
- B immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; B `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; B source manifest `ready/1.13.2/ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; B raw artifact manifest `ready/1.13.2/artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.
- Both revision records state `originalDerivedArtifactAvailable=false`, source trees and raw inputs identical, and original-derived-artifact equivalence unproven. This worker verified the snapshots and manifests; independent ops audit passed on 2026-10-07.
## Handoff
