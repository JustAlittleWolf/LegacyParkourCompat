# F008: Resolved displacement cutoff

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: entity movement update; S006
- Classification: changed behavior
- Confidence: candidate (A revision verified; original derived-artifact equivalence unproven; dependencies/reviewer open)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `Entity.java`::`move(MoverType,double,double,double)`, lines 578-599 and subsequent position update, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- A derived-artifact revision: `feather-r1-2026-10-07`; immutable snapshot `../../../build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; `artifact.sha256` SHA-256 `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`. Original source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` is unavailable; equivalence is unproven. All source and non-derived raw inputs verified identical; coordinator reports independent ops pass.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `Entity.java`::`move(MoverType,Vec3d)`, lines 463-468, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.

## Source-level difference

After sneak-edge preprocessing, B calls the collision solver but sets the shape and derives position only when the resolved vector squared length is greater than `1.0E-7`. A enters axis resolution for any nonzero requested component and later updates position from shape without this squared-length cutoff.

## Reachability and dependencies

All non-noClip Entity.move callers can reach this behavior; piston preprocessing and noClip have separate guards. The exact vector is produced by LivingEntity travel or external displacement. Full caller inventory remains open.

## Consequence and uncertainty

Very small resolved movement can update position/shape in A but be skipped in B. The source threshold is proven; practical reachable magnitudes and downstream callback consequences have not been tested.

## Handoff

Independent cutoff delta; first changed release unknown. Implementation/testing deferred.
