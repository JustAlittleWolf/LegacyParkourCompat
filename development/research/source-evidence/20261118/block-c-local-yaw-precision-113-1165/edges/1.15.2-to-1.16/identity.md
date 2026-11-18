# C — source identity sidecar for 1.15.2 → 1.16 Mojmap yaw report

This sidecar supplements `report.md` without changing its bytes. The release-root manifests do exist; the earlier report's statement that each Mojmap tree lacked `.decompile-provenance.json` was based on checking the nested source directory, while the published markers live at the version root (`decompiled_minecraft/<version>/mojmap.*`).

## Published source sets

Both version roots publish `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. The `.ready.json` files say `schema: 1`, `status: ready`, and name their exact `versionId` and `mapping`. Their recorded source-file counts match both the row count in the source manifest and the current `*.java` count under the nested `mojmap/` tree (3,332 for 1.15.2; 3,572 for 1.16).

| Identity field | 1.15.2 | 1.16 |
|---|---|---|
| Ready marker | `decompiled_minecraft/1.15.2/mojmap.ready.json` | `decompiled_minecraft/1.16/mojmap.ready.json` |
| Provenance | `decompiled_minecraft/1.15.2/mojmap.provenance.json` | `decompiled_minecraft/1.16/mojmap.provenance.json` |
| Version/mapping | `1.15.2` / `mojmap` | `1.16` / `mojmap` |
| Published source output path in marker | `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.15.2\mojmap` | `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\1.16\mojmap` |
| Source files / bytes | 3,332 / 17,543,871 | 3,572 / 19,220,152 |
| Source manifest SHA-256 | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| Artifact manifest SHA-256 | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` |
| Exact client / metadata ID | Client `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; metadata ID `1.15.2` | Client `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71`; metadata ID `1.16` |
| Provenance run | Batch `1.15.2, 1.16.5, 1.17.1, 1.18.2`; `mappingOption=mojmap`; Vineflower 1.12.0; Java 25.0.3+9-LTS; Gradle 9.7.1; created `2026-10-07T14:47:33.0201186Z` | Requested/resolved `1.16`; `mappingOption=mojmap`; Vineflower 1.12.0; Java 25.0.3+9-LTS; Gradle 9.7.1; created `2026-10-08T16:42:45.2697877Z` |

The sidecar bytes were checked against the markers: each `mojmap.sources.sha256` hash equals the marker's `sourceManifestSha256`, and each `artifacts.sha256` hash equals `artifactManifestSha256`. The 1.16 marker also carries `provenanceSha256`, which matches its provenance file. The 1.15.2 marker has no provenance-digest field; its provenance file is present and its current SHA-256 is recorded below.

## Release, client, and mapping artifact pins

| Artifact identity | 1.15.2 | 1.16 |
|---|---|---|
| Version metadata file SHA-256 | `e974511243e845b2427ab635eba5612481cb420a70c0630aa041eab3eeb590c5` (`1.15.2/version.json`) | `32ea7a259475ba6f2dc03f979bb2429115ba4137727f776dfd22cc55b1483297` (`.../1.16/version.json`) |
| Client JAR SHA-256 | `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` | `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` |
| Mojang client-mappings SHA-256 | `65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777` (`client_mappings.txt`) | `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d` (`client_mappings.txt`) |
| Remapped client JAR SHA-256 | `c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b` (`client-mojmap.jar`) | `3213f0827fa9215f41e74ca1dedaf104738e9c70ea1635769fb90f3996e4bf6c` (`client-mojmap.jar`) |

For 1.16, provenance additionally records the mapping file as 5,632,455 bytes, SHA-1 `9539840a7dbea682b305858fe138bd03ee7823cf`, plus the mapped-client path and digest. For 1.15.2, `mappingArtifacts` is null in the provenance JSON; the artifact manifest still gives SHA-256 pins for the mappings text and remapped client JAR. Neither provenance object includes a mapping-download URL. Both artifact manifests pin the version-manifest bytes at SHA-256 `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04`.

## Exact cited Java rows

For each cited Java file, the line's SHA-256 in the version-root `mojmap.sources.sha256` manifest was compared with `Get-FileHash -Algorithm SHA256` of the corresponding current file under `decompiled_minecraft/<version>/mojmap/`; the values match exactly:

| Source row | 1.15.2 SHA-256 | 1.16 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/client/MouseHandler.java` | `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681` |
| `net/minecraft/client/Options.java` | `5bf3f2ef925c282c8ac9c6df57b362013b295b6946b006ee72a008495c485634` | `efcaf563621e2c6ad2e547d5273492e7dbeb13a6e0b9c8639aa0e056a84435bb` |

The no-difference report's operation trace and source hashes therefore refer to the source rows under the exact ready-marked 1.15.2 and 1.16 Mojmap source sets. The initial report's provenance caveat is superseded by this sidecar; no product files, permanent research findings, or the original report were changed.

## Checked marker-file SHA-256 values

| File | SHA-256 |
|---|---|
| `1.15.2/mojmap.ready.json` | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` |
| `1.15.2/mojmap.provenance.json` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` |
| `1.15.2/mojmap.sources.sha256` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| `1.15.2/artifacts.sha256` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |
| `1.16/mojmap.ready.json` | `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2` |
| `1.16/mojmap.provenance.json` | `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` |
| `1.16/mojmap.sources.sha256` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| `1.16/artifacts.sha256` | `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` |
