# F002: Pose selection and collision-aware resize

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: player pose/dimensions; S002
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `PlayerEntity.java`::`updatePlayerPose()`, lines 334-360, SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; same member, lines 343-370, and `getDimensionsForPose(Pose)` lines 109-117, SHA-256 `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.

## Source-level difference

A derives dimensions directly from fall-flying/sleeping/swimming-or-spin/sneaking/standing predicates; a requested box is applied only if collision-free, otherwise the existing size remains. Its sneak height is 1.65. B chooses a Pose, requires the swimming pose to fit before entering the selection, then tries the requested pose and falls back to sneaking or swimming. B's sneaking pose dimensions are set by the pose dimension provider and are 1.5 high in the cited release. A passes `null` as collision-query entity; B's pose fit calls the entity-aware `hasNoCollisions(this, box)` path.

## Reachability and dependencies

Player state predicates -> `updatePlayerPose` -> pose/dimensions -> collision, eye height and fluid checks. B pose/dimension mutation and world collision query/provider behavior need full paired closure. The entity-aware call site is proven; complete world-query semantics and all collision providers remain open.

## Consequence and uncertainty

Source proves distinct pose selection, fallback and collision-query context. Differences can alter player height and whether a transition succeeds in tight spaces; no trajectory or collision outcome was simulated. Verify all pose dimension mappings, base entity resize callback and collision query behavior before implementation decisions.

## Handoff

Independent delta description; related finding IDs: none. First changed release unknown. Implementation/testing deferred.
