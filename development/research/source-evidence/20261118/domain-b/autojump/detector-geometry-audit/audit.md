# Independent detector-geometry projection audit

Verdict: **accepted within the stated bounded projection**. The specified AutoJump geometry constants and comparison/timer operations are retained between the exact 1.10.2 and 26.2 sources. This verdict establishes neither full detector parity nor full movement coverage.

## Immutable input and source identity

- Candidate SHA-256: `8E540E84EB92ECF28CD830DDBF4FF3CA2362A381E8445E095620701256525AC1` (matches the supplied `evidence.md`).
- 1.10.2 `ornithe-feather`: source `LocalClientPlayerEntity.java`, SHA-256 `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443`; manifest entry matches. Adjacent provenance/ready sidecars identify version 1.10.2, Feather gen2 build.2, Vineflower 1.12.0 / Java 25; the ready marker's source-manifest SHA-256 matches the actual manifest bytes.
- 26.2 `unobfuscated`: source `LocalPlayer.java`, SHA-256 `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6`; manifest entry matches. Adjacent provenance/ready sidecars identify version 26.2, unobfuscated, Vineflower 1.12.0 / Java 25; the ready marker's source-manifest SHA-256 matches the actual manifest bytes.

## Retraced operations

- `LocalClientPlayerEntity.autoJump(float,float)`, 1.10.2 lines 823-924; `LocalPlayer.updateAutoJump(float,float)`, 26.2 lines 1005-1097.
- Jump-height cap starts as float `1.2F`; with Jump Boost, both add `(amplifier + 1) * 0.75F` using float multiplication (old lines 857-860; current 1038-1041).
- Lookahead is `Math.max(speed * 7.0F, 1.0F / inverseLength)`; both operands/result path are float (old 862, with inverse length from 845; current 1043, from 1025). The unused float lookahead-step local is also `7.0F` in both methods.
- The extended segment endpoints are each shifted upward by `0.51F`, after construction of the inflated test box (old 867-869; current 1048-1050).
- The forward/movement dot product is cast to float and rejects only under the negated `< -0.15F` comparison (old 847-849; current 1027-1029).
- Obstacle height starts at `Float.MIN_VALUE`. On intersection, the sampled box maximum is cast to float; the sentinel is checked with `!=` before computing rise (old 880-884, 911-912; current 1059-1064, 1091-1092).
- Rise subtraction occurs in double precision before casting the result to float (old 912; current 1092). The over-height early-return check compares obstacle height against the relevant reference plus cap by subtracting reference first, then `> cap` (old 894-896; current 1073-1076).
- The final timer condition is the same ordered negated conjunction, `!(rise <= 0.5F) && !(rise > cap)`, and writes one tick (old 913-915; current 1093-1095). For finite non-NaN rises, it admits `rise > 0.5F && rise <= cap`. The negated form also differs from the positive-range rewrite for NaN; reachability is not claimed.

## Explicit limits

The older rise reference uses `getShape().minY`; the current reference uses entity `getY()` (old 828-829, 894, 912; current 1007-1008, 1074, 1092). Collision candidates are an old `List<Box>` iterated directly versus current `VoxelShape` candidates flattened via `toAabbs()` (old 876, 882-884; current 1057-1064). These inputs can alter sampled geometry/result and remain outside this verdict. Normalized movement/input construction and outer eligibility are likewise not accepted by this audit. No broad detector-parity or tick-coverage conclusion follows.
