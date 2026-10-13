# Source record: 1.19.2 → 1.19.3 (campaign)

Historical report: [run.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.2--1.19.3/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap/Mojmap; exact releases and official mapping files verified from the published readiness/provenance manifests below
- Source preparation owner / command / log / readiness marker: shared source owner (worktree `3e2d`); command `gradlew.bat decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G --output-root=.../staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a --cache-directory=.../artifacts`; successful log `build/movement-campaign-2026-10-07/staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log`; exact readiness markers verified below
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; Java 25.0.3+9-LTS; Vineflower 1.12.0; TinyRemapper 0.14.1; Mapping-IO 0.9.1; ASM 9.10.1; Gson 2.14.0; heap 4G

### A — 1.19.2

- Exact requested/resolved release: 1.19.2 / 1.19.2; readiness `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap.ready.json`, SHA-256 `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c`; provenance JSON SHA-256 `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280`
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/` (4,480 Java files)
- Original client jar SHA-256: `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`
- CLI mode/namespace: `mojmap` / Mojmap official names
- Mapping coordinate/build/file/SHA-256: official Mojang 1.19.2 `client_mappings.txt`; `c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76`
- Bridge mapping: not used
- Remapped jar SHA-256: `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`
- Cited Java source paths/SHA-256: aggregate `mojmap.sources.sha256` is `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; cited body hashes below were checked against the manifest
- Cited resource entries/SHA-256: official `client.jar` movement tags were inspected; paired entry hashes are recorded in the source comparison section below
- Required external data and provenance: original `client.jar` SHA-256 `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`; mapped jar SHA-256 `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`; source manifest `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`; artifact manifest `d0197d78578241b8cdde1123a3dee64aa279341681a7edde9f867689f9bb3490`; diagnostic manifest `ce308a5f2902c3d7f7c5e330935d130e5ce56302d03b7fa687bf14493400e44e`; verified against readiness record

### B — 1.19.3

- Exact requested/resolved release: 1.19.3 / 1.19.3; readiness `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap.ready.json`, SHA-256 `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6`; provenance JSON SHA-256 `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`
- Source root: `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/` (4,618 Java files)
- Original client jar SHA-256: `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`
- CLI mode/namespace: `mojmap` / Mojmap official names
- Mapping coordinate/build/file/SHA-256: official Mojang 1.19.3 `client_mappings.txt`; `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b`
- Bridge mapping: not used
- Remapped jar SHA-256: `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`
- Cited Java source paths/SHA-256: aggregate `mojmap.sources.sha256` is `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; cited body hashes below were checked against the manifest
- Cited resource entries/SHA-256: official `client.jar` movement tags were inspected; paired entry hashes are recorded in the source comparison section below
- Required external data and provenance: original `client.jar` SHA-256 `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`; mapped jar SHA-256 `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`; source manifest `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; artifact manifest `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`; diagnostic manifest `95e8189acb6b384b21a584f28d5e8d23922996ea9f446e17e54734ccba840e60`; verified against readiness record
