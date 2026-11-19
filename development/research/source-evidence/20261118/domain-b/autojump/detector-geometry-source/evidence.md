# AutoJump detector geometry-source dependency

Status: COMPLETED for the bounded post-move detector projection; no product conclusion.

## Inputs and verified provenance

- Host/environment: Windows PowerShell; canonical source consumer, read-only.
- Older source: Minecraft 1.10.2, `ornithe-feather` (Feather gen2 build.2), `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, SHA-256 `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443`; member `autoJump(float,float)`, lines 823-924.
- Current source: Minecraft 26.2, `unobfuscated`, `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java`, SHA-256 `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6`; member `updateAutoJump(float,float)`, lines 1005-1097.
- Provenance is verified by version-level sidecars adjacent to each source directory: `<root>.provenance.json`, `<root>.ready.json`, and `<root>.sources.sha256`. The provenance records exact version/namespace and decompile command; the ready marker says `status: ready` and binds the source manifest by SHA-256; both listed member-file hashes match their source-manifest entries. For 1.10.2 the ready marker records 1,845 source files, source manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`, Feather gen2 build.2 jar/Tiny mapping names, Vineflower 1.12.0 and Java 25. For 26.2 the ready marker records 7,055 source files and source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; provenance identifies the exact 26.2 unobfuscated run, Vineflower 1.12.0 and Java 25. Both ready-marker manifest hashes were recomputed and match the sidecars. The earlier conclusion that these roots were unverified legacy trees was incorrect: the manifests are version-level sidecars, not files inside the source directories.

## Bounded comparison

Once the method's outer eligibility path reaches detector geometry, the checked detector constants and branch predicates are retained:

- Start at `1.2F`; add `(jumpBoostAmplifier + 1) * 0.75F` when Jump Boost is present (old 857-860; current 1038-1041).
- Lookahead distance is `Math.max(speed * 7.0F, 1.0F / inverseLength)` (old 862; current 1043). Both use float locals/expressions for this calculation.
- Shift both side-test segment endpoints by `0.51F` (old 868-869; current 1049-1050), then test either of the two side segments.
- Reject only when facing/movement dot product `< -0.15F` (old 849; current 1029).
- The detected obstacle height starts as `float s/obstacleHeight = Float.MIN_VALUE` and is considered present only when it remains unequal to that sentinel (old 880, 911; current 1059, 1091). The height difference is stored as a float after subtracting a double-valued vertical reference (old 912; current 1092).
- The final rise test is written in both sources as the exact negated conjunction `!(rise <= 0.5F) && !(rise > cap)`, then sets the one-tick timer (old 913-915; current 1093-1095). This has the expected `rise > 0.5F && rise <= cap` boundary for finite non-NaN rise values. Preserve the source's negated comparisons: for NaN, the negated form evaluates true while the positive finite-range form evaluates false. No claim is made that NaN is reachable here.

Thus the assigned constants/comparison projection is retained in these verified endpoints. This does not establish the complete historical detector result: the declared separate inputs can change which geometry is sampled and how its rise is measured.

## Explicitly separate, unresolved inputs

- The rise reference changes from `this.getShape().minY` in the older source (old 828-829, 894, 912) to `this.getY()` in current source (current `position()` at 1007 and comparisons at 1074, 1092). This is the separately owned old-minY/current-entity-Y input and is not reconciled here.
- Collision candidates change from `List<Box>` supplied by `world.getCollisions` and iterated directly (old 876, 882-884) to an iterable of `VoxelShape` flattened through `toAabbs()` (current 1057-1064). Exact effective shape streams/order are separately owned and are not audited here.
- Upstream eligibility differences are outside this bounded detector slice. No provider/catalog, broad tick, direction-normalization or other-mechanic audit was performed.

No unresolved member remains within the bounded constants/comparison projection; full geometry parity remains dependent on the separately owned position and effective-shape-stream inputs. This note is an isolated source-evidence artifact, not a permanent finding or product conclusion.
