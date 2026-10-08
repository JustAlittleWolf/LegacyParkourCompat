# F-ELYTRA-LOOK-GUARD — 1.13.2 guards glide pitch correction against zero horizontal look

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: TRAVEL-MODIFIERS.
- Classification: changed behavior.
- Applicability: historical player behavior while fall-flying.
- Confidence: candidate (full-pair dependencies and independent review are open).
- First changed release: unknown within (1.12.2, 1.13.2].
- Runtime validation: not performed.
- Artifact manifests: A original artifact-manifest SHA-256 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; B fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e. Source trees were rehashed against their ready manifests. Revised snapshot revision feather-r1-2026-10-07: A fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87; B b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c. Original mapped jars unavailable; snapshots not proven identical or metadata-only.

## Paired evidence

- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, glide pitch correction lines 1434-1455; `Entity.getRotationVector(float,float)` lines 1239-1245; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` and `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`.
- B evidence: corresponding LivingEntity correction lines 1495-1518, Entity look-vector lines 1251-1258; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` and `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
## Source-level difference

- Difference: A executes the pitch-up correction when the pitch-derived value is negative; B additionally requires positive horizontal look length before dividing by it. The look-vector formulas themselves changed between versions, so the guard must be considered with the exact `MathHelper` float/trig source and camera pitch endpoint, not as an isolated algebraic simplification.
## Reachability and dependencies

- Reachability and dependencies: PlayerEntity camera pitch is clamped to [-90, 90] in both sources (A Entity lines 307-314; B lines 318-326); the local player's inherited fall-flying moveRelative reaches this branch when the Elytra movement state is active.
## Consequence and uncertainty

- Consequence and uncertainty: the exact trigonometric endpoint and branch proof still need source closure; revised snapshot provenance is verified, but original mapped-bytecode identity remains unproven. No trajectory was measured. Other gliding terms are F-ELYTRA-GLIDE-MATH.

- Revised-artifact provenance: revision ID `feather-r1-2026-10-07`. A immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; A `revision.json` SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; A source manifest `ready/1.12.2/ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; A raw artifact manifest `ready/1.12.2/artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`.
- B immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; B `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; B source manifest `ready/1.13.2/ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; B raw artifact manifest `ready/1.13.2/artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.
- Both revision records state `originalDerivedArtifactAvailable=false`, source trees and raw inputs identical, and original-derived-artifact equivalence unproven. This worker verified the snapshots and manifests; independent ops audit passed on 2026-10-07.
## Handoff
