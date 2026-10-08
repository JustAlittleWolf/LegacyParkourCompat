# F013: Simultaneous sneak and jump flight arithmetic

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: local flight vertical input; S010
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LocalClientPlayerEntity.java`::`mobTick`, lines 797-806, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; same method, lines 728-743, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.

## Source-level difference

Under `abilities.flying && isCamera()`, A handles sneak and jump in separate conditionals: sneaking subtracts `flySpeed * 3.0F` from double `velocityY`, then jumping adds the same float product. B increments/decrements integer `j` for those inputs and applies one vector update only when `j != 0`. With both inputs held, B leaves vertical velocity untouched; A executes two double assignments. Lateral input unscaling on sneak occurs in both.

## Reachability and dependencies

LocalClientPlayerEntity.mobTick snapshots current/previous jump and sneak state, updates flying ability, then runs this branch before superclass travel. Preconditions are flying ability, local camera player, simultaneous sneak+jump input. Fly speed comes from synchronized abilities.

## Consequence and uncertainty

Source proves different operation sequence and B's no-op at the combined-input guard. Floating-point roundoff could make A's subtract/add pair differ from the original value; no numeric witness or runtime trajectory is claimed. Exact fly-speed provenance and ability synchronization remain open in INV-EXTERNAL/INV-STATE.

## Handoff

Independent arithmetic candidate. Implementation handoff blocked by open state/ability provenance and absent independent reviewer acceptance. First changed release unknown.
