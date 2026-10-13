# Source record: 1.11.2 → 1.12.2 (campaign)

Historical report: [run.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.11.2--1.12.2/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Naming namespace: Ornithe Feather on both exact releases; release-specific mapping artifacts; same naming convention verified
- Source preparation owner / command / log / readiness marker: shared source owner (individual name not supplied); exact `--versions=1.11.2 --mappings=feather` and `--versions=1.12.2 --mappings=feather` runs; raw Gradle logs not retained; readiness marker at `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather.ready.json`
- Toolchain/decompiler/remapper versions/options: Java 25; Gradle 9.7.1; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; generic signatures, ASCII strings, synthetic removal, four-space indentation, available processor threads (minimum 1), Java runtime excluded, allowed prefixes `net/minecraft` and `com/mojang`

Shared source root `build/movement-campaign-2026-10-07/ready/` is physically in the primary checkout and read-only to this worker. Fresh read-only verification confirmed both exact readiness records, all listed source-file hashes and all raw-input artifact hashes. The original derived mapped JARs were replaced during a reproducibility rerun and are unavailable; the source owner published immutable revision `feather-r1-2026-10-07` snapshots. Their hashes and verification records were rechecked by this worker, and independent ops audit passed the revised Feather bundles. The new snapshots do not prove identity with the unavailable originals or that the changes are metadata-only; preserve that limitation and do not rewrite markers or waive original-hash mismatches.

### A — 1.11.2

- Requested/resolved: `1.11.2` / `1.11.2`; readiness `1.11.2/ornithe-feather.ready.json` confirms exact `versionId` and `versionMetadataId`.
- Source root: `1.11.2/ornithe-feather`.
- Original client SHA-256: `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- CLI mode/mapping: `feather`; `net.ornithemc:feather-gen2:1.11.2+build.2`.
- Mapping merged jar SHA-256: `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`; Tiny mapping SHA-256: `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`.
- Original remapped client SHA-256 (unavailable; recorded in the unchanged original artifact manifest): `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`.
- Revised mapped-artifact snapshot `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar` SHA-256: `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; revision record and checksum file agree; snapshot is read-only.
- Source manifest SHA-256: `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1,921/1,921 files present and matching.
- Artifact manifest SHA-256: `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` (unchanged original record; its derived-JAR entry names the unavailable original hash). Revised snapshot provenance is recorded under `feather-r1-2026-10-07`; independent ops audit passed.
- Movement diagnostics SHA-256: `8bb3b1169a21958a6570fc304a0a95d9c51cc3a2101869bcbd453c3588925cdc`; exact release success and required sources confirmed; jump and relative-movement anchors listed.

### B — 1.12.2

- Requested/resolved: `1.12.2` / `1.12.2`; readiness `1.12.2/ornithe-feather.ready.json` confirms exact `versionId` and `versionMetadataId`.
- Source root: `1.12.2/ornithe-feather`.
- Original client SHA-256: `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`.
- CLI mode/mapping: `feather`; `net.ornithemc:feather-gen2:1.12.2+build.2`.
- Mapping merged jar SHA-256: `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca`; Tiny mapping SHA-256: `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.
- Original remapped client SHA-256 (unavailable; recorded in the unchanged original artifact manifest): `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`.
- Revised mapped-artifact snapshot `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar` SHA-256: `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; revision record and checksum file agree; snapshot is read-only.
- Source manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 2,050/2,050 files present and matching.
- Artifact manifest SHA-256: `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` (unchanged original record; its derived-JAR entry names the unavailable original hash). Revised snapshot provenance is recorded under `feather-r1-2026-10-07`; independent ops audit passed.
- Movement diagnostics SHA-256: `1ae1a796ac7650bf218af02eb602e1b7f46df2950e57b14263c65d3b58dc71b3`; exact release success and required sources confirmed; jump and relative-movement anchors listed.

### Integrity update

- Fresh read-only verification at 2026-10-07 15:28 UTC confirmed the ready markers resolve exact IDs `1.11.2` and `1.12.2`, namespace `ornithe-feather`; the source-manifest hashes match the ready markers and all 1,921/2,050 listed source files match their entries.
- Rechecked 36/37 raw-input artifact entries against the original manifests; every entry matched. For each version, the revised read-only JAR snapshot hash matches both `artifact.sha256` and `revision.json`; the referenced verification-log hash also matches. Revision ID: `feather-r1-2026-10-07`.
- The original derived mapped JARs are unavailable. The revised snapshots are new derived artifacts and do not prove identity with those originals or that the hash change is metadata-only. Ready markers and original manifests remain unchanged.
- Independent ops audit passed the revised Feather bundles on 2026-10-07; this worker separately verified both exact pair endpoints as detailed above. `DEP-ARTIFACT-INTEGRITY` is closed for source-provenance use of revision `feather-r1-2026-10-07`. The original derived JARs remain unavailable and equivalence is unproven. Do not rewrite ready markers or waive original-hash mismatches.

### Artifact evidence identities

- Evidence artifact `EA-FEATHER-R1-1.11.2`: revision `feather-r1-2026-10-07`; immutable snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; checksum sidecar `.../artifact.sha256` records that artifact hash (sidecar SHA-256 `40131c4f05a8229d384ee7cb3680ec2c45eb66b7a10a25544dd7c9b71c7cc3eb`); evidence manifest `.../revision.json`, SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`; original artifact manifest `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; original derived JAR unavailable, expected SHA-256 `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`; revision records `sourceTreeIdentical=true` against source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, `rawInputsIdentical=true` against the original raw-input manifest, and no input differences; revised-to-original JAR equivalence unverified. Provenance limitation: a new derived JAR snapshot does not establish identity with the unavailable original or prove a metadata-only change. The worker verified the cited source files, snapshot bytes, sidecar and revision record; independent ops audit passed.
- Evidence artifact `EA-FEATHER-R1-1.12.2`: revision `feather-r1-2026-10-07`; immutable snapshot `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; checksum sidecar `.../artifact.sha256` records that artifact hash (sidecar SHA-256 `162170b94be0fa6393a515ea3beed10210b2c86a006366cf9d351eba136bae2d`); evidence manifest `.../revision.json`, SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; original artifact manifest `build/movement-campaign-2026-10-07/ready/1.12.2/artifacts.sha256`, SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`; original derived JAR unavailable, expected SHA-256 `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`; revision records `sourceTreeIdentical=true` against source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, `rawInputsIdentical=true` against the original raw-input manifest, and no input differences; revised-to-original JAR equivalence unverified. Provenance limitation: a new derived JAR snapshot does not establish identity with the unavailable original or prove a metadata-only change. The worker verified the cited source files, snapshot bytes, sidecar and revision record; independent ops audit passed.
