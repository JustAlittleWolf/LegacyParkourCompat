# F-SWIM-POSE — 1.13.2 adds swimming pose and dimensions

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: POSE-DIMENSIONS.
- Classification: added mechanic.
- Applicability: modern-only mechanic.
- Confidence: candidate (full-pair dependencies and independent review are open).
- First changed release: unknown within (1.12.2, 1.13.2].
- Runtime validation: not performed.
- Artifact manifests: A original artifact-manifest SHA-256 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; B fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e. Source trees were rehashed against their ready manifests. Revised snapshot revision feather-r1-2026-10-07: A fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87; B b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c. Original mapped jars unavailable; snapshots not proven identical or metadata-only.

## Paired evidence

- A evidence: `net/minecraft/entity/living/player/PlayerEntity.java`, updatePlayerPose lines 291-315 and getEyeHeight lines 1756-1768; SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`.
- B evidence: same class, updatePlayerPose lines 334-361 and getEyeHeight lines 1859-1870; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
## Source-level difference

- Difference: B has swimming pose handling and assigns compact dimensions (width 0.6, height 0.6) after a world collision-fit check; A has no swimming pose branch. The B eye-height method returns 0.4F while swimming; A has no swimming predicate in that method. PlayerEntity.updateSwimming also suppresses swimming while flying in B. The spin-attack branch shares compact dimensions but is a separate modern-only trigger.
## Reachability and dependencies

- Reachability and dependencies: swimming transition is sourced in Entity base tick and state is consulted by PlayerEntity pose update. Dimension changes affect getShape, eye-height and collision consumers.
## Consequence and uncertainty

- Consequence and uncertainty: B can move through a different collision volume while swimming, subject to the fit query.
- Limits: other dimension writers and world collision query semantics remain to be inventoried. Do not treat this file as a closed collision finding.



- Revised-artifact provenance: revision ID `feather-r1-2026-10-07`. A immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; A `revision.json` SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; A source manifest `ready/1.12.2/ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; A raw artifact manifest `ready/1.12.2/artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`.
- B immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; B `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; B source manifest `ready/1.13.2/ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; B raw artifact manifest `ready/1.13.2/artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.
- Both revision records state `originalDerivedArtifactAvailable=false`, source trees and raw inputs identical, and original-derived-artifact equivalence unproven. This worker verified the snapshots and manifests; independent ops audit passed on 2026-10-07.
## Handoff
