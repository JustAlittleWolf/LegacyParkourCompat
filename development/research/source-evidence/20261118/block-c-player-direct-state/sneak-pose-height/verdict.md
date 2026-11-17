# Independent source audit r2: ordinary crouch cutover

Terminal: COMPLETED
Verdict: PASS for the narrow claim below

## Bound input

Corrected frozen comparison SHA-256: `ea5c8e256d0cf6d64962500aee5f3c659f020b1a6578fbfee65c43cdcaf7253f`

This audit binds to that exact comparison revision and the canonical source files listed below. It covers the ordinary crouch player dimensions and eye offset across 1.13.2 → 1.14.0. It does not audit the full movement tick or other movement operations. The prior `verdict.md` is preserved unchanged.

## Verdict

For an ordinary, non-flying crouch where the candidate dimensions fit, the exact sources establish the cutover:

- **1.13.2, Ornithe Feather:** `PlayerEntity.updatePlayerPose` chooses `0.6F × 1.65F` for `isSneaking()` after fall-flying, sleeping, swimming, and spin-attack checks, then applies the candidate only if `world.hasNoCollisions(null, box)` (`net/minecraft/entity/living/player/PlayerEntity.java`, lines 334–360). `getEyeHeight` returns `1.62F - 0.08F`, i.e. `1.54F`, for sneaking/height `1.65F` after its higher-priority sleeping and `0.4F` cases (lines 1859–1870).
- **1.14.0, Yarn:** the pose dimensions table maps `SNEAKING` to `0.6F × 1.5F` (`net/minecraft/entity/player/PlayerEntity.java`, lines 107–117). `updateSize` selects SNEAKING for `isSneaking() && !abilities.flying`, subject to the swimming-pose gate and fallback fit checks (lines 326–354). `getActiveEyeHeight` returns `1.27F` for SNEAKING (lines 1779–1789).

Thus the observed endpoint cutover is from 1.65F to 1.5F crouch height, and from 1.54F to 1.27F crouch eye offset. These are source facts for the ordinary crouch state under the stated fit conditions; this audit makes no trajectory prediction. Pose-fit queries and bounding-box application remain separate from this dimensions/eye-height claim, and this verdict establishes no block-shape change.

## Exact source identity

- 1.13.2 source: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. Outer provenance SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` identifies version `1.13.2`, namespace `ornithe-feather`, and Feather artifacts `1.13.2+build.2`.
- 1.14.0 source: `decompiled_minecraft/1.14/yarn/net/minecraft/entity/player/PlayerEntity.java`, SHA-256 `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319`. Nested `.decompile-provenance.json` SHA-256 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341` pins the 1.14 client input and Yarn `1.14+build.21`; its PlayerEntity source hash matches the canonical file.

The audited comparison and source trees were read-only. No product, wiki, Git, backlog, test, launch or generation work was performed.