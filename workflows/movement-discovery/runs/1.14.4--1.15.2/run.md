# Discovery: 1.14.4 to 1.15.2

- Status: active
- Scope: client player movement; older A = exact Java Edition 1.14.4; newer B = exact Java Edition 1.15.2.
- Repository revision and start date: `c133c2999b6673874e35bbdb26759548407f3e11`; 2026-09-26.
- Selected naming namespace and alignment: Mojang official names / Mojmap for both releases. Each exact release used its own official `client.txt` mapping artifact. This is the same naming family, not cross-application of one release's mapping to the other. Requested and resolved IDs match both sides.
- Source preparation: A owner command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.14.4 --mappings=mojmap`; owner checkpoint `29f6a2d` records `Finished 1.14.4 using mojmap`. B command `gradlew.bat -g .gradle-user-home decompileMinecraft --versions=1.15.2 --mappings=mojmap`; success transcript extract is `source-preparation-transcript-extract.txt`.
- Log limitation: neither owner's raw console log is retained. A's committed provenance record is checkpoint `29f6a2d`; B's successful-output transcription is labeled as an extract, not a raw log. Both report exact-version successful completion.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; decompiler JVM 25.0.3+9-LTS; target bytecode Java 8; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default decompiler heap 4G. Separate isolated Gradle user homes were used.

## Artifact manifest

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
- Source preparation extract: `source-preparation-transcript-extract.txt`; decompiler warnings and exact successful completion are recorded there. The task reported no decompilation error.

### Per-finding source/resource hashes

Record each cited source path and SHA-256 in its finding. Paired tag resources and their hashes are in `resources-paired-tags.md`. Do not commit generated source trees, client jars or mapping files.

## Correspondence and call order

The two sources use a shared Mojmap namespace. Resolve each compared role through exact class/member signatures, inheritance, callers and state order in the coverage ledger and individual findings. Same-name classes or methods alone do not establish correspondence. Source-level candidate paths are indexed in `source-inventory-b.md`.

## Coverage ledger

See [coverage-ledger.md](coverage-ledger.md) for the stage-by-stage audit, terminal rows, and open coverage gaps.

## Finding index

Findings are tracked one per file under `findings/`; add only after the bounded slice and dependency closure are source-confirmed.

## Resume checkpoint

- Completed: exact source provenance for both releases; input correspondence; paired block/fluid tags; friction, Soul Sand, Honey speed/jump/slide, portal dismount and Bee player-knockback findings.
- Next: close the open rows in `coverage-ledger.md`, beginning with player-state gates and living travel, then collision, blocks/fluids, effects/equipment and external updates.
- Assumptions: no gameplay trajectory was observed; findings are source-level. Server-synchronized state is identified as externally authoritative rather than locally computed.

## Source audit closure

- Coverage status: in progress; seven source-confirmed findings and a terminal input-refactor row are recorded. Other navigation stages have explicit open coverage gaps in `coverage-ledger.md`.
- Unresolved gaps: paired player-state, living-travel, collision-order, block/fluid consumer, effect/equipment and external-update audits.
- Evidence/hash/correspondence audit: exact original client jars and release-specific Mojmap mappings are recorded. Each finding records cited source hashes; paired tags and resource hashes are in `resources-paired-tags.md`.
- Runtime validation: not performed; gameplay trajectory validation is a separate workflow.
