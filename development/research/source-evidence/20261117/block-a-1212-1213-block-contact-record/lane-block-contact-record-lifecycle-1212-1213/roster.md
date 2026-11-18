# Source roster — 1.21.2 → 1.21.3, Mojmap

## Execution and source roots

- Host/execution: Windows 11, PowerShell 7.6.9.
- Older: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.2/mojmap/`.
- Newer: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.3/mojmap/`.
- Mapping family: Mojmap on both sides. Read-only canonical local sources; no generation or network access performed.

## Exact source provenance

- 1.21.2 uses the legacy in-tree equivalent `.decompile-provenance.json` (source-map entries = 5,654). Its pinned Mojang version metadata input is `.../1.21.2.json` SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`; pinned client jar SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`; official client mappings SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`. Pipeline `lpc-decompile-v1`, Vineflower 1.12.0, Java 25.0.3+9-LTS. Provenance file SHA-256: `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`.
- 1.21.3 has ready marker `mojmap.ready.json` (`versionId=1.21.3`, `mapping=mojmap`, `status=ready`, 5,655 source files) and provenance `mojmap.provenance.json`. Ready marker SHA-256: `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`; provenance SHA-256: `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`. The ready marker's source-manifest SHA-256 is `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, verified against `mojmap.sources.sha256`. The ready marker specifies `versionMetadataId=1.21.3` and client SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`; provenance records the exact decompilation batch command for versions 1.21.1, 1.21.3, 1.21.4 and 1.21.5.

## Bounded files and live SHA-256

The 1.21.2 provenance source map and 1.21.3 canonical source manifest both contain these hashes; each was checked against the live file on both sides.

| Relative source path | SHA-256 (both versions) |
| --- | --- |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` | `426442149f89a28fc5973a52b8156aec7c18d76faf8b62eecae9f120b2fdba16` |
| `net/minecraft/world/level/BlockGetter.java` | `5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` |

The paired source files are byte-identical. The comparison records no difference for this bounded slice.