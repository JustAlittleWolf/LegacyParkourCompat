# Source record: 1.10.2 → 1.11.2 (legacy-run)

Historical report: [run.md](../../archive/legacy-workflows/movement-discovery/runs/1.10.2--1.11.2/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Namespace alignment: Ornithe Feather, A `1.10.2+build.2`, B `1.11.2+build.2`.
- B command: `gradlew.bat decompileMinecraft --versions=1.11.2 --mappings=feather --no-daemon --gradle-user-home <worktree>/.gradle-home-movement-discovery`; success in 3m7s, 1,921 Java files. Full log not persisted.
- Toolchain: Gradle 9.7.1; Temurin 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; heap 4G.

### A — 1.10.2

- Source: `../../../../decompiled_minecraft/1.10.2/ornithe-feather/`.
- Client jar: `../../../../build/minecraft-decompile-cache/1.10.2/client.jar`; SHA-1 `dc8e75ac7274ff6af462b0dcec43c307de668e40`; SHA-256 `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9`.
- Mapping `net.ornithemc:feather-gen2:1.10.2+build.2`; Tiny SHA-256 `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`.
- Actual merged-v2 mapping jar SHA-256 `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`; actual remapped client jar SHA-256 `c42fe4366482d183f83ff62be5abfefead9d3350e7c9dd9243f8ac1a5b59ed90`.
- Owner manifest swapped these mapping/remapped hash assignments and reported `11d5e5…` for remapped jar. Hashes were verified at actual artifact paths; `11d5e5…` was not found. Discrepancy retained.

### B — 1.11.2

- Source: `../../../../decompiled_minecraft/1.11.2/ornithe-feather/` (1,921 Java files).
- Client jar SHA-1 `db5aa600f0b0bf508aaf579509b345c4e34087be`; SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- Version metadata SHA-256 `c98508dfc20ed365e6666c8f44879582df033bbde0e3499782381130f3dbaa1c`.
- Tiny mapping SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`; merged-v2 mapping jar SHA-256 `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`.
- Remapped jar SHA-256 `19200acf9fdd0395535cc8a880f6ab9f6db427131c6c11259f7a3e1b07845daf`.
- Remapper warned of invalid access and repaired 1 class/34 members. Full log/target list unavailable; relevant sources inspected and selected affected movement methods verified with `javap -c -p`.
