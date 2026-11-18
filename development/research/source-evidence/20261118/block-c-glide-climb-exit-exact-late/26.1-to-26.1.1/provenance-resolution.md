# Provenance resolution: 26.1 → 26.1.1 Elytra climb exit

## Verdict

**Accepted for the report’s bounded exact-source no-difference slice.** The prior audit withheld exact-pair acceptance because its inspected checkout lacked the provenance/readiness/source-manifest files. In the primary checkout, both canonical version directories contain those files. Each ready marker identifies its exact release and `unobfuscated` namespace; its source, artifact, and diagnostics pins match the current files. All 6,882 source entries per version independently match the source manifest, including byte-identical complete Java files used by the report. This resolves the provenance gate for this finding only. It does not freeze full-pair coverage, establish an intermediate cutover, or establish runtime parity.

## Source publication identity and verification

Canonical source roots inspected:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated\`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated\`

The provenance records identify one decompiler run at repository commit `fcd113c3fafcfde91d5cb5c8308b77292c601f9d`, batch `[26.1, 26.1.1]`, with exact requested/resolved metadata IDs `26.1` and `26.1.1`, respectively, and published unobfuscated sources (no mapping artifact). The ready records report `ready`, Vineflower, Java 25, 6,882 files each, and the respective byte totals 32,992,042 and 32,992,043. Their `outputPath` values point to the recorded `build/movement-campaign-2026-10-07/ready/<version>/unobfuscated` preparation output. The canonical trees currently present in this checkout independently match every entry in those pinned source manifests; the recorded source publication identity therefore binds the canonical bytes despite the preparation output path.

Manifest/pin checks:

| Literal file path | SHA-256 of file bytes | Result |
|---|---|---|
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated.provenance.json` | `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b` | version 26.1, namespace unobfuscated; client SHA recorded as `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`; metadata SHA `68e8d6e01e06e6a9a740a7ad474061f9303b004ceedb8224b085a58616390e49` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated.ready.json` | `954126940593c1e1e4b987bfd7079452e0d6283c3d1d97a02693f0319f66f0f5` | ready; its source-manifest, artifact-manifest, and diagnostics SHA fields match the corresponding file hashes below |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated.sources.sha256` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` | matches ready marker; 6,882/6,882 paths and hashes verified, total 32,992,042 bytes, zero missing/mismatched entries |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\artifacts.sha256` | `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0` | matches ready marker; 77 artifact entries recorded |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\movement-diagnostics.txt` | `6590c89a7d3baf27490ce024d49519ed0dd4ee4e0cc4369c3f419588b15312da` | matches ready marker |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated.provenance.json` | `9df9595443d56779d505511f5441bb360cca71aafcc570bc16ea8e61a1d847bf` | version 26.1.1, namespace unobfuscated; client SHA recorded as `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`; metadata SHA `86b232149a2ae224b06443730fe32c46c306f035a1599e3477c5237df1c950ef` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated.ready.json` | `29540fe56da79d19a9b125a091cf89cdb45e4493e4c5898c677658dca1474802` | ready; its source-manifest, artifact-manifest, and diagnostics SHA fields match the corresponding file hashes below |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated.sources.sha256` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` | matches ready marker; 6,882/6,882 paths and hashes verified, total 32,992,043 bytes, zero missing/mismatched entries |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\artifacts.sha256` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` | matches ready marker; 77 artifact entries recorded |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\movement-diagnostics.txt` | `38688a7bf9adf0c34a28264178b4361287d45c6ded444a7ad6f8e41ba9d745fa` | matches ready marker |

The source-manifest SHA is the SHA-256 of the literal manifest file. Each manifest line has `sha256 *relative/path`; I recomputed SHA-256 from every named file under that version’s canonical `unobfuscated` root and checked both the count and byte total against the ready marker. The artifact and diagnostics values above likewise match their ready marker fields. Provenance pins the original client artifact hash and exact metadata hash; those values agree with the ready records and the adjacent accepted source audits. I did not regenerate or modify source artifacts.

## Compared source inputs

These are direct file paths and SHA-256 values from the current canonical roots. Each version’s source-manifest entry was checked; corresponding files are byte-identical across the pair.

| Literal source path | SHA-256 |
|---|---|
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated\net\minecraft\client\player\LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated\net\minecraft\world\entity\player\Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated\net\minecraft\world\entity\LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated\net\minecraft\world\entity\Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1\unobfuscated\net\minecraft\server\network\ServerGamePacketListenerImpl.java` | `16d32046e8a1435723f4009e51d5b557fdff71419034554f8cf5cc63240636db` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated\net\minecraft\client\player\LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated\net\minecraft\world\entity\player\Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated\net\minecraft\world\entity\LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated\net\minecraft\world\entity\Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.1\unobfuscated\net\minecraft\server\network\ServerGamePacketListenerImpl.java` | `16d32046e8a1435723f4009e51d5b557fdff71419034554f8cf5cc63240636db` |

## Bounded claim disposition

The original report’s claim is accepted as a bounded no-difference result: the cited local input/start, eligibility, active-flag, pre-travel cap, dry travel/climb-exit, and server request/validation routes are supported by the exact source files above, whose complete bytes match across 26.1 and 26.1.1. The separate 1.21.11→26.1 and 26.1.1→26.1.2 audit records independently use the same canonical `decompiled_minecraft/<version>/unobfuscated/` roots and corroborate this source identity and route; they do not extend this verdict to full pair coverage.

The climb predicate remains conditional on effective vanilla state/tag inputs; the source-only evidence here does not establish tag membership for named blocks. This acceptance does not claim a trajectory, runtime parity, full 26.1→26.1.1 movement inventory, or the exact release where any behavior changed. Frozen report and prior audit bytes were read only; this verdict resolves the previously open provenance gate without rewriting either.

## Immutable inputs

| Literal input path | SHA-256 |
|---|---|
| `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-261-2611-actual-climb-exit\report.md` | `08912efbc602e9e67dd0c937e1b7959dd22d0636c277d73cbf169119f4d2519f` |
| `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-261-2611-actual-climb-exit-audit\verdict.md` | `7e5a884520a208bc91e8f26570b0e6498b6def92fe0157fef3d00bdc0870a393` |

