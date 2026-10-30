# 1.9.4–1.10.2 bounded resource-to-player-physics inventory

Status: source-side closure complete for packaged-resource/registration dependencies in the assigned slice; raw archive content comparison unavailable because the exact client jars are absent. This report does not close full-pair source coverage.

## Evidence identity

Canonical read-only decompiled sources: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4\ornithe-feather` and `...\1.10.2\ornithe-feather`, namespace `ornithe-feather` / Feather Gen2 mappings. Both have `ready` markers and exact-run provenance dated 2026-10-07. Source manifest SHA-256: 1.9.4 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; 1.10.2 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. Mapping artifacts: `feather-gen2-1.9.4+build.2` and `feather-gen2-1.10.2+build.2` (mergedv2 jar + tiny), hashes recorded in each `artifacts.sha256`.

Recorded raw client hashes from ready markers/manifests: 1.9.4 `client.jar` = `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; 1.10.2 `client.jar` = `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`. These are recorded identities, not hashes recomputed from files in this checkout.

## Bounded source/resource dependency closure

- Both source trees contain Java sources only (`net/minecraft/...`); no packaged asset/resource files are present in the published decompilation tree. Their source manifests cover 1,819 and 1,845 Java files respectively.
- Block geometry enters player collision through Java `Block`/`BlockState` shape methods and block-specific overrides in `net/minecraft/block/` (e.g. `Block.java`, `AirBlock.java`, `BannerBlock.java`, `ButtonBlock.java`, `CakeBlock.java`, `FenceGateBlock.java`, `LiquidBlock.java`). For 1.10.2 the local player directly queries these state collision shapes in `client/entity/living/player/LocalClientPlayerEntity.java` (around the climb/ledge checks at lines 852–902). Resource-manager references are in client resource/render management; no inspected collision-shape provider or player movement path reads blockstate/model/texture resources. `Blocks.java` is the fixed Java block registry/initialization site in each version, with concrete shape behavior provided by the registered Java block classes.
- In these pre-1.13 versions the source model is fixed Java block registrations and Java shape/state logic, not data-driven blockstate/tag definitions. Client resource reloaders/manager serve presentation assets (models/textures/color maps); no evidence in this bounded source slice links those resources to collision geometry or movement physics. Visual/model bounds must not be treated as authoritative collision shapes.
- Tags are not a movement input in the inspected source/resource architecture. A complete claim about archive payload entries would require opening the exact client archives; that check was not possible here.

## Missing artifact and exact follow-up

Expected raw paths from provenance: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts\1.9.4\client.jar` and `...\1.10.2\client.jar`. The campaign artifacts directory and both paths are absent. The separate `.minecraft-decompile-cache` contains neither version's client jar. Request these exact two archives from the source preparation owner, verify their SHA-256 against the identities above, and inspect entries only to confirm resource payload/registration assumptions. Do not regenerate/decompile sources for this bounded task.

## Disposition

No resource-driven player-geometry dependency found in the reviewed source closure. Fixed Java registration and block shape source remain authoritative. Archive-level resource payload inventory is open pending the two exact raw jars. This is a terminal dependency inventory only, not full tick/inventory coverage or independent audit.
