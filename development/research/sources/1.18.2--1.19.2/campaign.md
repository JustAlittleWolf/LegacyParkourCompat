# Source record: 1.18.2 → 1.19.2 (campaign)

Historical report: [run.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.18.2--1.19.2/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap (`mojmap`) for both exact releases; both readiness records identify the exact release and Mojmap namespace, so source names align directly.
- Source preparation owner / command / log / readiness marker: source-owner publication via canonical root `build/movement-campaign-2026-10-07/`; exact `mojmap.ready.json` markers beside each namespace tree and source-owner command/log verified in the two exact Mojmap readiness records; neither command was run by this worker. A/B logs: A success excerpt `ready/1.18.2/mojmap.success.log`; B full log under the recorded source-owner staging root.
- Toolchain/decompiler/remapper versions and options: version catalog pins Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping-IO 0.9.1, Gson 2.14.0, ASM 9.10.1. Decompiler task forks Java 25, default heap 4G, with generic signatures and ASCII string characters enabled, synthetic members removed, four-space indent, Java runtime excluded, and allowed prefixes `net/minecraft` and `com/mojang`. Actual JDK/Gradle and exact command metadata are recorded per side below.

Artifact paths below are relative to repository root; generated artifacts remain in ignored shared build storage and are not tracked.

### A — 1.18.2

- Requested/resolved release: `1.18.2` / `1.18.2`; `versionMetadataId` exact match verified.
- Readiness marker: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap.ready.json`, status `ready`, SHA-256 `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/`; 4,236 source files.
- Source manifest: `mojmap.sources.sha256`; SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; verified against marker.
- Artifact manifest: `artifacts.sha256`; SHA-256 `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036`; verified against marker.
- Client jar: `artifacts/1.18.2/client.jar`; SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`; publisher SHA-1 `2e9a3e3107cca00d6bc9c97bf7d149cae163ef21`.
- CLI mode / mapping: `mojmap`; official Mojang mappings, `artifacts/1.18.2/client_mappings.txt`, SHA-256 `a2aa6ee1030bfef79e9b2e08e79de1637fdd7ecb5bf8891cf2e9a4b186042543`; publisher SHA-1 `a661c6a55a0600bd391bdbbd6827654c05b2109c`.
- Remapped jar: `artifacts/1.18.2/client-mojmap.jar`; SHA-256 `60a2016dd217b23df8a5ddbebac96fccb58ff747e961974696e0018f6ef12dba`.
- Movement diagnostics: `movement-diagnostics.txt`; SHA-256 `0bc8857ee048b0ede8e696f3b7b050006618b278936cf182b3464c6c99483ac8`; verified against marker. It reports exact decompile success and movement entry/body line anchors for `Entity`, `LivingEntity`, `Player`, `LocalPlayer`, jump, travel, `move` and `moveRelative`; no movement-body decompiler errors are reported.
- Success excerpt: `mojmap.success.log`; confirms requested 1.18.2, `Finished 1.18.2 using mojmap`, and `BUILD SUCCESSFUL` (full stdout not persisted by owner).
- Toolchain: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0. Command used exact release in a batch with 1.15.2, 1.16.5 and 1.17.1: `decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`.
- Cited source paths/hashes and cited resource entries/hashes: individual evidence hashes are recorded in coverage slices and findings as they are cited. Resource inventory remains open. Required external data/provenance: pending resource audit.

### B — 1.19.2

- Requested/resolved release: `1.19.2` / `1.19.2`; `versionMetadataId` exact match verified.
- Readiness marker: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap.ready.json`, status `ready`, SHA-256 `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/`; 4,480 source files.
- Source manifest: `mojmap.sources.sha256`; SHA-256 `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; verified against marker.
- Artifact manifest: `artifacts.sha256`; SHA-256 `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`; verified against marker.
- Client jar: `artifacts/1.19.2/client.jar`; SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`; publisher SHA-1 `055b30d860ead928cba3849ba920c88b6950b654`.
- CLI mode / mapping: `mojmap`; official Mojang mappings, `artifacts/1.19.2/client_mappings.txt`, SHA-256 `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76`; publisher SHA-1 `8e8c9be5dc27802caba47053d4fdea328f7f89bd`.
- Remapped jar: `artifacts/1.19.2/client-mojmap.jar`; SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`.
- Movement diagnostics: `movement-diagnostics.txt`; SHA-256 `ce308a5f2902c3d7f7c5e330935d130e5ce56302d03b7fa687bf14493400e44e`; verified against marker. It reports exact decompile success and movement entry/body line anchors for `Entity`, `LivingEntity`, `Player`, `LocalPlayer`, jump, travel, `move` and `moveRelative`; no movement-body decompiler errors are reported.
- Full Gradle log: `build/movement-campaign-2026-10-07/staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log`; confirms `Finished 1.19.2 using mojmap` and `BUILD SUCCESSFUL`. It contains remapper access-fix warnings for GUI `OptionInstance$ValueSet` classes; those are unrelated to the movement body anchors reviewed so far.
- Toolchain matches A: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0. Command used exact release in a batch with 1.19.3 and 1.19.4: `decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`.
- Cited source paths/hashes and cited resource entries/hashes: individual evidence hashes are recorded in coverage slices and findings as they are cited. Resource inventory remains open. Required external data/provenance: pending resource audit.
