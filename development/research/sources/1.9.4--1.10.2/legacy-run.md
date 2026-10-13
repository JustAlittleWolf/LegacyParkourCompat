# Source record: 1.9.4 → 1.10.2 (legacy-run)

Historical report: [run.md](../../archive/legacy-workflows/movement-discovery/runs/1.9.4--1.10.2/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace, CLI mode per side and alignment evidence: explicit Ornithe Feather (`feather`) on both sides. A resolved to `1.9.4+build.2`; B resolved to `1.10.2+build.2`. This confirms an aligned family and exact endpoints.
- Source preparation commands and successful log evidence: A: `.\gradlew.bat --no-daemon decompileMinecraft --versions=1.9.4 --mappings=feather` (the [1.8.9 to 1.9.4 source preparation record](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/run.md#source-preparation-record)); B: `.\gradlew.bat decompileMinecraft --versions=1.10.2 --mappings=feather --console=plain` (`decompile-1.10.2-feather.log`, worktree root). Both task outputs reported exact requested/resolved releases, mappings, output paths and successful completion.
- Toolchain: Gradle 9.7.1; decompiler JVM Temurin 25.0.3+9-LTS; target client bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; default heap 4G and repository Vineflower options. Both remapper logs warned about access checks and recorded a repair (A 1 class/32 members; B 1 class/33 members). Inspect any relevant movement bytecode if a selected member intersects a warning.

### A — 1.9.4

- Source root: `decompiled_minecraft/1.9.4/ornithe-feather/`; 1,819 Java files.
- Client jar: `build/minecraft-decompile-cache/1.9.4/client.jar`; 8,736,083 bytes; SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`; Mojang SHA-1 `4a61c873be90bb1196d68dac7b29870408c56969`.
- CLI mode / namespace: explicit `feather` / Ornithe Feather.
- Mapping coordinate/build: `net.ornithemc:feather-gen2:1.9.4+build.2`, merged-v2 artifact SHA-256 `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`; extracted Tiny mapping SHA-256 `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`.
- Remapped jar: `build/minecraft-decompile-cache/1.9.4/client-ornithe-feather.jar`, SHA-256 `bf1ac5827656840275f45c62d26d7b0671575a8131dd0fd9c0268f733c5b0a96`.
- `version.json` SHA-256 `4b21379203f0d87df8cc2ab4dd3c3689f139772f1cdb37b40688252547e2d5be`.
- Owner-recorded initial source hashes: `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; `Input.java` `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009`; `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`.

### B — 1.10.2

- Source root: `decompiled_minecraft/1.10.2/ornithe-feather/` (shared ignored root through this worktree's verified junction).
- Client jar: `build/minecraft-decompile-cache/1.10.2/client.jar`; SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`; Mojang SHA-1 `dc8e75ac7274ff6af462b0dcec43c307de668e40`.
- CLI mode / namespace: explicit `feather` / Ornithe Feather.
- Mapping coordinate/build: `net.ornithemc:feather-gen2:1.10.2+build.2`, merged-v2 artifact `build/minecraft-decompile-cache/1.10.2/feather-gen2-1.10.2+build.2-mergedv2.jar`, SHA-256 `c42fe4366482d183f83ff62be5abfefead9d3350e7c9dd9243f8ac1a5b59ed90`.
- Remapped jar `build/minecraft-decompile-cache/1.10.2/client-ornithe-feather.jar`: SHA-256 `11d5e51f209ff559ef9e3f9b3192e1d26810bb3f83905c0e747b0bd0eb4c749d`.
- Successful log: `decompile-1.10.2-feather.log` (local/ignored); confirms exact ID, Feather build, output root, `Finished 1.10.2 using ornithe-feather`, and `BUILD SUCCESSFUL`. No Vineflower error count was reported.
- Indexed source hashes are recorded beside findings. Original client jar resources are available for bounded local inspection.
