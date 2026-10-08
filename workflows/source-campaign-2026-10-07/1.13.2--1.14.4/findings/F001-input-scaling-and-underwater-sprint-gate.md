# F001: Input scaling and underwater sprint-forward cutoff

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: local input and sprint gate; S001
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `KeyboardInput.java`::`tick()`, lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; `LocalClientPlayerEntity.java`, sprint-forward consumers around lines 701, 726, 740, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `KeyboardInput.java`::`tick(boolean,boolean)`, lines 13-26, SHA-256 `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526`; `LocalClientPlayerEntity.java`::`m_03985577()`, lines 961-964, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.- Additional B dependencies: `Input.java`::`m_49149051()`, lines 22-24, SHA-256 `acb63d46fb6e6a1cb6b285a5d0702fb62575f7d54344ad1f0f9d51e8f671f33f`; `LocalClientPlayerEntity.java`::`m_63723874()`, lines 596-598; `Entity.java`::`m_00306336()`/`m_99544176()`, lines 1818-1823. Whole-file hashes are listed in the run manifest.

## Source-level difference

A applies the 0.3 input scale whenever sneaking. B calls `tick(bl4, spectator)` where `bl4 = m_63723874() || m_99544176()` and scales only when not spectator and actual sneaking or `bl4` is true. `m_63723874()` is true only when not flying/swimming and the sneaking pose fits, then returns actual sneak input or that standing pose does not fit; `m_99544176()` is true for swimming pose while out of water. Separately, B uses `movementForward > 1.0E-5F` for submerged sprint eligibility, while A uses `movementForward >= 0.8F`; B retains `>=0.8F` outside water.

## Reachability and dependencies

Keyboard input is sampled in local player `mobTick` and the resulting movementForward feeds local sprint initiation/timer checks. B supplies pose-related `bl` and spectator state to the input method. Full sprint eligibility (including food/blindness reads) and all update order are not closed; those producer systems remain excluded from emulation.

## Consequence and uncertainty

Source proves the changed scale and submerged threshold. The threshold may permit sprint initiation with a smaller positive submerged forward input than A; resulting speed/position is not observed here. `bl`'s exact upstream pose state and full gates need closure in INV-TICK/INV-STATE.

## Handoff

Independent delta description; related finding IDs: none. Applicability is limited to the local player and tested input conditions. First changed release is unknown. Implementation/testing deferred.