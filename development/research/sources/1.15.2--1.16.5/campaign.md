# Source record: 1.15.2 → 1.16.5 (campaign)

Historical report: [run.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.15.2--1.16.5/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap/Mojmap. Both ready records report exact requested/resolved metadata IDs and Mojmap; source and artifact manifests and cited Java files hash-match.
- Source preparation owner / command / log / readiness marker: shared source owner; both ready markers and provenance sidecars under ../../../build/movement-campaign-2026-10-07/ready. Exact command: .\gradlew.bat decompileMinecraft --versions=1.15.2,1.16.5,1.17.1,1.18.2 --mappings=mojmap --decompiler-heap=4G --output-root=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\staging\mojmap-1.15-to-1.18-f41b958fe6b84e6eb832e0c465a0e56d --cache-directory=D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts. Provenance SHA-256 A d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e; B b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d. Success sidecars are summary excerpts only (A b8b60b3ce9a7f746be9e2023bbd9afb7c85dc58cb8582b9ac5962fc2c8a6348d; B 0974f4cdada4d0549cb730901064681d11a0f2cfbf1fc61783993b9dc4488b07); complete raw Gradle streams were not retained.
- Toolchain/decompiler/remapper versions and options: Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; ASM 9.10.1; mapping-io 0.9.1; Gson 2.14.0; TinyRemapper 0.14.1; 4G heap, C:\Users\Wolfi\.gradle. Mojmap mapping inputs are the exact 1.15.2/client_mappings.txt and 1.16.5/client_mappings.txt entries; provenance records mappingArtifacts=null, so no mapping build coordinate is asserted.

Shared roots are read-only under ../../../build/movement-campaign-2026-10-07. Both ready records say Gradle decompileMinecraft succeeded and their metadata IDs equal the requested IDs. Source counts match trees: A 3,332; B 3,523. Movement diagnostics report no source-body damage; method-body review is still performed per slice.

### A — 1.15.2, Mojmap
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap
- Ready JSON: SHA-256 64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0
- Source manifest mojmap.sources.sha256: SHA-256 cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7
- Artifact manifest artifacts.sha256: SHA-256 208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406
- Movement diagnostics: SHA-256 1ab6c473dbbdf2c590a7a8870befbd404e634fe7c1b0ae6a796b7f5f9c63d4d5
- Client jar: 4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c
- Version JSON: e974511243e845b2427ab635eba5612481cb420a70c0630aa041eab3eeb590c5
- Official client mappings: 65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777
- Mojmap client jar: c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b
- Cited source hashes verified against the source manifest: LocalPlayer.java 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd; Input.java 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201; KeyboardInput.java 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7; Player.java 1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793; LivingEntity.java 46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54; Entity.java 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e; AABB.java 82324c0e6a3d69e80424656e6098b39ce41f0f17a5e6efbe6542fe8eaca24a18; VoxelShape.java 5faceea0e4ce9ad0f2a1a2c72a89d9a768ab877c8728ae2968fe34aa1e03d3ea; FlowingFluid.java e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf.

### B — 1.16.5, Mojmap
- Source root: ../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap
- Ready JSON: SHA-256 e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee
- Source manifest mojmap.sources.sha256: SHA-256 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b
- Artifact manifest artifacts.sha256: SHA-256 f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c
- Movement diagnostics: SHA-256 676ea98fca6e2b73e0db8214057397af8112b0b9d37b72ad7d9b1025bd77ec50
- Client jar: 00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263
- Version JSON: 3eeeab7b3165cc5263dc26ff8fe114fdb718b75a0244efead9d19635d090ba72
- Official client mappings: 7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c
- Mojmap client jar: 7c1a9f30983fa3c777d00116bdf3f3cef97c1c1aaf8294027052f5337fcde940
- Cited source hashes verified against the source manifest: LocalPlayer.java 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b; Input.java 367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201; KeyboardInput.java 746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7; Player.java d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960; LivingEntity.java b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88; Entity.java f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666; AABB.java 514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878; VoxelShape.java 6747647a5b94e69747340d432932b77ce5ad701c8f06e141e15b3dd68f8c3bde; FlowingFluid.java a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552.
