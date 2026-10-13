# Source record: 1.11.2 → 1.12.2 (legacy-run)

Historical report: [run.md](../../archive/legacy-workflows/movement-discovery/runs/1.11.2--1.12.2/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace: Ornithe Feather on both releases, using release-specific artifacts.
- B source command: `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.12.2 --mappings=feather`; successful output confirmed requested/resolved 1.12.2, `Applying ornithe feather (1.12.2+build.2)`, `Finished 1.12.2 using ornithe-feather`, and `BUILD SUCCESSFUL`. Raw log not retained; command output is in task transcript.
- B toolchain: Gradle 9.7.1; decompiler JVM 25.0.3+9-LTS; target bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1 (repository version catalog).

### A — 1.11.2

- Requested/resolved: 1.11.2 / 1.11.2 (owner's successful log transcript).
- Source root: `decompiled_minecraft/1.11.2/ornithe-feather`.
- Client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; SHA-1 `db5aa600f0b0bf508aaf579509b345c4e34087be`.
- Mapping family/mode: Feather; coordinate `net.ornithemc:feather-gen2:1.11.2+build.2`; Tiny SHA-256 `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- Remapped jar SHA-256 `19200acf9fdd0395535cc8a880f6ab9f6db427131c6c11259f7a3e1b07845daf`.
- Owner reports remapper invalid-access warnings and fixes for 1 class / 34 members; relevance to movement sources is being checked.

### B — 1.12.2

- Requested/resolved: 1.12.2 / 1.12.2 (successful decompiler output).
- Source root: `decompiled_minecraft/1.12.2/ornithe-feather`; the worktree junction target was verified as `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.
- Original client URL object/SHA-1: `0f275bc1547d01fa5f56ba34bdc87d981ee12daf`; SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- Mapping family/mode: Feather; coordinate `net.ornithemc:feather-gen2:1.12.2+build.2`; cached merged Tiny mapping `build/minecraft-decompile-cache/yarn/feather-gen2-1.12.2+build.2.tiny`, SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.
- Remapped jar `build/minecraft-decompile-cache/1.12.2/client-ornithe-feather.jar`; SHA-256 `d9639e8ba8a34c1f9ad8186b80575bd68440bec29b750b42eb3220cf824429a`.
- Remapper emitted invalid-access warnings and fixed 1 class / 33 members. B glide methods were independently checked from mapped bytecode; warning targets/log remain unavailable for a full warning audit.

### Cross-task provenance

A provenance was relayed by the adjacent-version owner from `C:\Users\Wolfi\.codex\worktrees\movement-discovery-1-11\LegacyParkourCompat\workflows\movement-discovery\runs\1.10.2--1.11.2\run.md`. Do not regenerate 1.11.2. The owner reports exact successful completion in 3m7s, but no raw log or source-hash inventory. This run independently hashes each A source cited.
