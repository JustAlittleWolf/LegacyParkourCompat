# Source record: 1.14.4 → 1.15.2 (legacy-run)

Historical report: [run.md](../../archive/legacy-workflows/movement-discovery/runs/1.14.4--1.15.2/run.md)

The following is copied from the historical report, without checking local artifact availability or renewing acceptance. Paths beginning with `ready/` name the old publication root; they are not automatically aliases for the canonical `decompiled_minecraft/` tree. Verify exact per-version mapping builds/files and cited source bytes before reuse.

- Selected naming namespace and alignment: Mojang official names / Mojmap for both releases. Each exact release used its own official `client.txt` mapping artifact. This is the same naming family, not cross-application of one release's mapping to the other. Requested and resolved IDs match both sides.
- Source preparation: A owner command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.14.4 --mappings=mojmap`; owner checkpoint `29f6a2d` records `Finished 1.14.4 using mojmap`. B command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`; success transcript extract is [Source preparation record](#source-preparation-record).
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; decompiler JVM 25.0.3+9-LTS; target bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default decompiler heap 4G. Separate isolated Gradle user homes were used.

### A — Minecraft 1.14.4

- Source root: `decompiled_minecraft/1.14.4/mojmap` (shared ignored source root).
- Exact requested/resolved release: `1.14.4` / `1.14.4`.
- Original client jar SHA-256: `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`; size 25,191,691 bytes. Client artifact object `8c325a0c5bd674dd747d6ebaa4c791fd363ad8a9`.
- Version metadata SHA-256: `615F466A39D2C19AE9B6E2401AA7BD07DBDA337F976BCCE383326B8D6BA4E532`.
- CLI mode/namespace: `mojmap`, Mojang official names. Mapping object `6073e4ba6949217eb708c4512be2ccc1850a603f` (`client.txt`); mapping file SHA-256 `2DD53A5E70BA493CF6E33C0FC52BBDF4C57F9429C7A13842565AA825FD44D910`.
- Remapped client jar SHA-256: `7781BDCC8E8D9173173731F2564866F17C07CB43FE602A9A9F14B2753DC34665`.
- Decompiled source count reported by the owner: 3,250 Java files. Independently checked anchor `net/minecraft/client/player/LocalPlayer.java` SHA-256 `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F`.
- Owner reported three remapper invalid-access warnings for `ClientPacketListener` / `MapRenderer$MapInstance` and an access repair for one class; Vineflower reported duplicate `ModelBakery` lambda processing. No warning was identified for the movement slices used below; inspect any newly discovered relevant damaged body before relying on it.
- Owner source generation/provenance checkpoint: `29f6a2d`; raw log was not retained.

### B — Minecraft 1.15.2

- Source root: `decompiled_minecraft/1.15.2/mojmap` (shared ignored source root).
- Exact requested/resolved release: `1.15.2` / `1.15.2`.
- Original client jar SHA-256: `4A73008A73F3824B7C711750A5A37556DF8614F193C0A531E292DAD159A73A7C`; size 15,531,492 bytes. Client artifact object `e3f78cd16f9eb9a52307ed96ebec64241cc5b32d`.
- Version metadata SHA-256: `E974511243E845B2427AB635EBA5612481CB420A70C0630AA041EAB3EEB590C5`.
- CLI mode/namespace: `mojmap`, Mojang official names. Mapping object `1fbf9f0bc9c326af859b3ccf71c2a8f5edc47ef8` (`client.txt`); mapping file SHA-256 `65AD295B6CF63821F5D8F961C128128475E6D39386358D22EB238A3CE6E31777`.
- Remapped client jar SHA-256: `9B78CB6363696CA878D18B3A51A2E5D4AC626C9E06A4FA91B0B827FA85C2961A`.
- Source preparation extract: [Source preparation record](#source-preparation-record); decompiler warnings and exact successful completion are recorded there. The task reported no decompilation error.

### Per-finding source/resource hashes

Record each cited source path and SHA-256 in its finding. Paired tag resources and their hashes are in [Paired block and fluid tags](#paired-block-and-fluid-tags). Do not commit generated source trees, client jars or mapping files.
