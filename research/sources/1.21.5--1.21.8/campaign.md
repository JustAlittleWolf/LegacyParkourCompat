# Source record: 1.21.5 → 1.21.8 (campaign)

Historical report: [run.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace, CLI mode per side and alignment evidence: A and B are exact-release Mojmap / `mojmap`; pair alignment is confirmed by exact 1.21.5 and 1.21.8 marker version IDs and Mojmap source paths. Campaign policy selects Mojmap from 1.14.4 onward.
- Toolchain/decompiler/remapper versions and options: A and B verified in their provenance records below; both use Gradle 9.7.1, Java 25.0.3+9-LTS, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, ASM 9.10.1, Gson 2.14.0 and a 4G decompiler heap.

### A — 1.21.5

- Requested/resolved release and metadata ID: `1.21.5` / `1.21.5`, verified in `ready/1.21.5/mojmap.ready.json`. Namespace/CLI mode: Mojmap / `mojmap`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/`; 5,921 files, 27,311,391 bytes per ready record. Readiness JSON SHA-256: `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`.
- Client jar SHA-256: `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`; release-specific Mojang `1.21.5/client_mappings.txt` SHA-256: `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`; Mojmap remapped jar SHA-256: `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`; version metadata SHA-256: `4a9a484109ee5c3f430b685ddc719712e962e8d06c761cd58ec0a1e6fea2f18c`.
- Source manifest `ready/1.21.5/mojmap.sources.sha256` SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest `ready/1.21.5/artifacts.sha256` SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`; movement diagnostics `ready/1.21.5/movement-diagnostics.txt` SHA-256 `d43c0fdfc627492b043962c0379b744461ebbb6b2ffb24f326fe72a63a039755`. Each file hash matches the readiness JSON. Source manifest entries and on-disk hashes were verified for KeyboardInput, ClientInput, LocalPlayer, ClientPacketListener, AbstractClientPlayer, Player, LivingEntity, Entity, Input and Vec2.
- Preparation command: `.\gradlew.bat decompileMinecraft --versions=1.21.1,1.21.3,1.21.4,1.21.5 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`; exact owner run and full successful log: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.1-to-1.21.5-cd5a99cb1024417c9d370097c886a131/gradle.full.log`.
- Toolchain: Gradle 9.7.1, JDK 25.0.3+9-LTS, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, ASM 9.10.1 and Gson 2.14.0. The log reports one class access repair and zero members; repeated-processing notices are in non-movement PalettedContainer/CarvingMask lambdas; task succeeded. Targeted diagnostics include player/input/travel/move entry methods; no relevant-body damage is reported there. Slice owners still verify each cited body.
- Pair alignment uses exact release markers and the shared Mojmap namespace. Method correspondence and semantics are resolved slice by slice below.

### B — 1.21.8

- Requested/resolved release and metadata ID: `1.21.8` / `1.21.8`, verified in `ready/1.21.8/mojmap.ready.json`. Namespace/CLI mode: Mojmap / `mojmap`.
- Source root: `build/movement-campaign-2026-10-07/ready/1.21.8/mojmap/`; 6,105 files, 27,900,784 bytes per ready record. Readiness JSON SHA-256: `2e3bf302503eb127061cc605797b390f5f4a4b508f09350a59d78ced857ba7d7`.
- Client jar SHA-256: `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`; release-specific Mojang `1.21.8/client_mappings.txt` SHA-256: `dce035fedcc047ce0306c69892176b5eb9036bcc2845c87198163f12bb1d67ca`; version metadata SHA-256: `3bf5cdd444dfa89199fe4f8a92b6c79f4e4fa8977ff52029c3acd99378506fad` (all entries verified in the artifact manifest).
- Source manifest `ready/1.21.8/mojmap.sources.sha256` SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`; artifact manifest `ready/1.21.8/artifacts.sha256` SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; movement diagnostics `ready/1.21.8/movement-diagnostics.txt` SHA-256 `c954da48013372c7b423bf47597b447d32607b2c39b00e3cf353b1d5fcc95806`. Each hash matches the readiness JSON. Source-manifest entries and on-disk hashes verified for Player, LivingEntity, Entity, EntityDimensions, Pose, Attributes and EntityAttachments; input/tick source hashes are being verified per slice.
- Preparation command: `.\gradlew.bat decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap --decompiler-heap=4G --output-root=<shared staging root> --cache-directory=<shared artifacts root>`; exact owner run and successful log: `build/movement-campaign-2026-10-07/staging/mojmap-1.21.8-to-1.21.11-9a8c76419cd14660b88f9fc90225e127/gradle.full.log`.
- Targeted diagnostics report successful exact 1.21.8 decompilation, verified metadata ID and required Entity/LivingEntity/Player/LocalPlayer sources; indexed movement bodies include Entity.move, LivingEntity.jumpFromGround/travel/aiStep, Player.aiStep/travel and LocalPlayer.aiStep/move. Diagnostics are an index only; slice owners verify each cited body.

Pairwise source comparison is active. Both source-owner publications cite exact requested/resolved IDs and validated SHA-256 source/artifact manifests; movement diagnostics are indexes, with behavior established only by the slice evidence below.
