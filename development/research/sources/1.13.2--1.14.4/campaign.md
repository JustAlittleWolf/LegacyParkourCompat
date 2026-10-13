# Source record: 1.13.2 → 1.14.4 (campaign)

Historical report: [run.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace, CLI mode per side and alignment evidence: Ornithe Feather `feather`, generated namespace `ornithe-feather`; exact mapping artifacts/builds and metadata IDs are readiness-verified.
- Toolchain/decompiler/remapper versions and options: Vineflower; Java 25. Repo pins Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1; BuildSrc options `ignoreConflicts=true`, `renameInvalidLocals=true`, `rebuildSourceFilenames=true`, `fixPackageAccess=true`. Provenance records Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, ASM 9.10.1, Mapping IO 0.9.1, Gson 2.14.0, Tiny Remapper 0.14.1; `--decompiler-heap=4G`.

At initial readiness validation, all listed source and artifact hashes matched (A: 2711 sources, 42 artifacts; B: 3250 sources, 40 artifacts). The source-owner integrity notice led to a canonical immutable A derived-artifact snapshot. Readiness/manifest hashes and cited mapping artifacts were rechecked; original derived-JAR equivalence remains unproven. Roots below are relative to this run folder.

### A — 1.13.2

- Exact requested/resolved/versionMetadataId: 1.13.2 / 1.13.2 / 1.13.2.
- Source root: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`.
- Client SHA-256: `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`.
- Mapping: `ornithe-feather`; `feather-gen2-1.13.2+build.2-mergedv2.jar`, `feather-gen2-1.13.2+build.2.tiny`; mapping jar SHA-256 `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`, tiny mapping SHA-256 `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`. Artifact manifest SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e` (42 entries).
- Source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 2711 files / 14,770,525 bytes.
- Diagnostics `movement-diagnostics.txt`, SHA-256 `a89e4e3cefe704b58339a1e342e4d8a0fffe7e49dba3ca18402647b69c398a45`; successful decompile/metadata ID plus method anchors for PlayerEntity.jump/moveRelative, LivingEntity.jump/moveRelative and their mobTick callers (review marker 10; not full coverage proof).
- Revised derived artifact: revision ID `feather-r1-2026-10-07`; immutable `client-ornithe-feather.jar` SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`, independently matched against `artifact.sha256` and `revision.json`. Original derived JAR SHA-256 `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e` is unavailable; no equivalence claim. Revision record says raw inputs/source tree identical (0 source-file differences); this worker reverified all 2711 source entries and all 41 non-derived raw-artifact entries. Verification command `gradlew.bat decompileMinecraft --versions=1.8.9,1.9.4,1.10.2,1.11.2,1.12.2,1.13.2 --mappings=feather --decompiler-heap=4G` (unique staging root and shared artifacts cache). Verification log `staging/verify-feather-1.8.9-to-1.13.2-ac481b5296a44bd2ac40d126187e8b8a/gradle.full.log` SHA-256 `33b732892a03ffac60026663f7266c20b637330db6d480b4260267861bebd40c`, reports success for the six-release batch and 1.13.2 Feather. Coordinator reports independent ops audit passed; original derived-JAR equivalence remains unproven.

### B — 1.14.4

- Exact requested/resolved/versionMetadataId: 1.14.4 / 1.14.4 / 1.14.4.
- Source root: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`.
- Client SHA-256: `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`.
- Derived mapped JAR SHA-256: `5e914421086acc2f244ca34bfd0df68e199dba5539a819a0eed1e4af31bb465c` (all 40 B artifact entries currently hash-match).
- Mapping: `ornithe-feather`; `feather-gen2-1.14.4+build.2-mergedv2.jar`, `feather-gen2-1.14.4+build.2.tiny`; mapping jar SHA-256 `3162806b9fb266d7e6d2c594be8d4cc6c91ddb09b4e55d1b566e5429edbd793f`, tiny mapping SHA-256 `60d4906621c873dadba96425d1a233349c9afa407d09bfc8371202af6a71f25c`. Artifact manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` (40 entries).
- Source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; 3250 files / 16,680,788 bytes.
- Diagnostics `ornithe-feather.movement-diagnostics.txt`, SHA-256 `8b5fba7cae7c0187671005b3261d184cbc2caecf9f8cbfc25b284bb0a89d128a`; reports successful decompile, metadata match and movement body review marker 10 for Entity/LivingEntity/PlayerEntity/ClientPlayerEntity.

Current integrity verification: A source tree matched all 2711/2711 manifest entries; 41/41 non-derived raw artifacts matched the original artifact manifest; the original derived `1.13.2/client-ornithe-feather.jar` is unavailable and the shared cache now has SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c` instead of original `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`. Immutable revision snapshot, `artifact.sha256`, `revision.json`, and verification log hash all match; B all 40/40 artifact entries match. The source owner makes no equivalence claim for the changed A derived JAR; this is a provenance limitation, not a hash mismatch in the revised snapshot or original source/raw inputs. Revision record SHA-256 is `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; snapshot sidecar SHA-256 is `4fa7f17f639f7cff0ff9db327b3c978b049cee0c96a6b40625601868692c7dd8`; original source manifest SHA-256 is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; original artifact manifest SHA-256 is `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`. Coordinator reports independent ops audit passed and this worker completed canonical consumer verification. Cite revision `feather-r1-2026-10-07` in each A-dependent finding; original derived-JAR equivalence remains unproven.

Exact source-owner commands from provenance (success logs are summary excerpts only): A `gradlew.bat decompileMinecraft --versions=1.13.2 --mappings=feather --decompiler-heap=4G`; B `gradlew.bat decompileMinecraft --versions=1.14.4 --mappings=feather,mojmap --decompiler-heap=4G`; each used its published staging output root and shared cache `build/movement-campaign-2026-10-07/artifacts`.

Cited Java files and whole-file SHA-256:

- `net/minecraft/client/entity/living/player/KeyboardInput.java`: A `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; B `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526`.
- `net/minecraft/client/entity/living/player/Input.java` (B): `acb63d46fb6e6a1cb6b285a5d0702fb62575f7d54344ad1f0f9d51e8f671f33f`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: A `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`; B `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.
- `net/minecraft/entity/living/LivingEntity.java`: A `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`; B `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61`.
- `net/minecraft/entity/Entity.java`: A `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; B `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.
- `net/minecraft/entity/living/player/PlayerEntity.java`: A `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; B `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df`.
- `net/minecraft/util/math/MathHelper.java` (B): `e39d5dfc69c17a9032be086d7d4d14cc63525962a0ff83615b1befdcca599843`.
- `net/minecraft/util/math/Vec3d.java`: A `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5`; B `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b`.
