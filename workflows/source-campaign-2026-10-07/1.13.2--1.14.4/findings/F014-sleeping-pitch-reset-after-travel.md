# F014: Sleeping pitch reset after travel

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: local-player tick dispatch and sleeping state; S013
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: local player while sleeping
- First changed release: unknown within (1.13.2,1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LivingEntity.java`::`tick`, lines 1689-1825, SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; local-player dispatch is `LocalClientPlayerEntity.java`::`tick`, lines 181-198, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `LivingEntity.java`::`tick`, lines 1979-2123, SHA-256 `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`; local-player dispatch is `LocalClientPlayerEntity.java`::`tick`, lines 178-196, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.

## Source-level difference

Both local-player `tick()` methods call their superclass when the player's chunk is loaded. `LivingEntity.tick()` calls `mobTick()` virtually, so the local player's input processing runs before `LocalClientPlayerEntity.mobTick()` delegates to `LivingEntity.mobTick()` for travel. In B, after that movement call returns and after walk/fall-flying bookkeeping, `LivingEntity.tick()` executes `if (this.isSleeping()) this.pitch = 0.0F;`. A has no corresponding client-side post-tick pitch reset. B also has a server-only sleeping cleanup in the `!world.isClient` block; that branch is not reachable in this local-client path.

## Reachability and dependencies

The differing write is reachable on the local client when the player is sleeping. It occurs after this tick's local movement processing. Whether the altered pitch is observable to the next movement-input direction after waking depends on the sleep/wake lifecycle and rotation handling, which have not been traced. No same-tick translation difference is claimed.

## Consequence and uncertainty

This is a source-proven player orientation state difference adjacent to movement, but its relevance to the movement-only scope remains unresolved. Do not treat it as a confirmed movement mechanic without tracing sleep/wake transitions and local input orientation use. No runtime result is claimed.

## Handoff

Candidate scope edge. Implementation handoff blocked by open tick/state dependencies and absent independent reviewer acceptance. First changed release unknown.
