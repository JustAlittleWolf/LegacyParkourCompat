# F-SWIM-LOOK — 1.13.2 swimming adds pitch-directed vertical velocity control

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: POSE-DIMENSIONS.
- Classification: added mechanic.
- Applicability: modern-only mechanic.
- Confidence: candidate (full-pair dependencies and independent review are open).
- First changed release: unknown within (1.12.2, 1.13.2].
- Runtime validation: not performed.
- Artifact manifests: A original artifact-manifest SHA-256 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; B fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e. Source trees were rehashed against their ready manifests. Revised snapshot revision feather-r1-2026-10-07: A fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87; B b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c. Original mapped jars unavailable; snapshots not proven identical or metadata-only.
- Evidence artifact records: `EA-FEATHER-R1-A` and `EA-FEATHER-R1-B` (see `run.md`, Artifact evidence identities).

## Paired evidence

- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(FFF)V`, lines 1386-1410; SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`. No swimming-look velocity adjustment precedes the superclass movement call.
- B evidence: corresponding member, lines 1442-1487; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. When swimming and not riding, B reads look-vector Y, selects 0.085 below -0.2 else 0.06, then conditionally adds `(lookY - velocityY) * factor` if lookY <= 0, jumping, or fluid exists at the sampled head position.
- Preconditions/reachability: virtual call from LivingEntity.mobTick executes on local player; B must be swimming and not riding. F-WATER-STATE establishes the new state producer, with revised snapshot provenance verified and original mapped-bytecode identity unproven.
## Source-level difference

- Difference: B applies a look-directed vertical adjustment absent from A's player override. It is separate from the local sneak-water descent impulse and water-height jump selection.
## Consequence and uncertainty

- Consequence and uncertainty: source proves conditional velocity write; no trajectory was measured. Exact fluid query, pose, and collision effects remain separate dependencies.

- Revised-artifact provenance: revision ID `feather-r1-2026-10-07`. A immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; A `revision.json` SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; A source manifest `ready/1.12.2/ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; A raw artifact manifest `ready/1.12.2/artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`.
- B immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; B `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; B source manifest `ready/1.13.2/ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; B raw artifact manifest `ready/1.13.2/artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.
- Both revision records state `originalDerivedArtifactAvailable=false`, source trees and raw inputs identical, and original-derived-artifact equivalence unproven. This worker verified the snapshots and manifests; independent ops audit passed on 2026-10-07.
## Handoff
