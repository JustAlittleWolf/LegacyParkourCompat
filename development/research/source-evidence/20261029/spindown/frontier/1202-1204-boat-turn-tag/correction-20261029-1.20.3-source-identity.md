# Correction supplement: bind the 1.20.3 boat-turn gate

This supplement corrects the source-identity section of `report.md`; it does not edit or replace that report, does not resolve packaged resource membership, and makes no trajectory or parity claim.

## Report byte identities

- Assignment-time report identity supplied by the independent auditor: SHA-256 `b170f86b83a14b07ae7be268ba439dcd1f22bbc6526b2c48d3da10078cd33897`.
- `report.md` bytes present immediately before this supplement was created: SHA-256 `4a7ba4b23a8e08aa9d530497ccb8582d37ec2dee9081b910ae91484a63cc2851`.

The latter is the current report file hash. Both report identities are recorded here so the correction is bound to the assignment-time and presently preserved report states.

## 1.20.3 exact source identity

The 1.20.3 mapping-root record is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.3/mojmap/.decompile-provenance.json`, SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`. It contains the decompiler input identities and per-source SHA-256 map. The recorded inputs bind the staged source to:

- Version metadata URL `https://piston-meta.mojang.com/v1/packages/86c3a686b59ee1bcd70febee4de962ec3e1a5d87/1.20.3.json`, downloaded metadata SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`.
- Client JAR URL `https://piston-data.mojang.com/v1/objects/b178a327a96f2cf1c9f98a45e5588d654a3e4369/client.jar`; the metadata's object identifier is `b178a327a96f2cf1c9f98a45e5588d654a3e4369`, and the decompile input SHA-256 is `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`.
- Client mappings URL `https://piston-data.mojang.com/v1/objects/be76ecc174ea25580bdc9bf335481a5192d9f3b7/client.txt`; the metadata's object identifier is `be76ecc174ea25580bdc9bf335481a5192d9f3b7`, and the mapping input SHA-256 is `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f` (the same bytes recorded for 1.20.4's mapping artifact).

The record identifies runtime `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`, pipeline `lpc-decompile-v1` with serial threads, and tool identities: Vineflower 1.12.0 JAR SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`; Tiny Remapper 0.14.1 JAR SHA-256 `c4d3b3e27089a5b96d21eb5184a75898c6e5062ebca4fee5f92db34c389b8952`; Mapping IO 0.9.1 JAR SHA-256 `aedc94b25a2fe7c09c898144ce33a1ef396680e984b07525d34916922d6ee3f4`; buildSrc JAR SHA-256 `3396837ae5b6ea385980a625ce89cd495d90286c660bf2789371c39c2715980d`.

### Relevant source hashes

Each expected hash below is in the 1.20.3 mapping-root provenance `sources` object. Each actual file hash was recomputed from the corresponding Mojmap source file and matched that record.

| Source file | Lines used in this trace | SHA-256 |
|---|---:|---|
| `net/minecraft/client/multiplayer/ClientLevel.java` | 279–290 | `2bc6147b758ba25fea89e1c7c8e28000cf323f1616b01b62af461cbce230e308` |
| `net/minecraft/client/player/LocalPlayer.java` | 846–853 | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/world/entity/LivingEntity.java` | 2741–2746 | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/Entity.java` | 1828–1845 | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/player/Player.java` | 177 | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/vehicle/Boat.java` | 665–675 | `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109` |
| `net/minecraft/tags/EntityTypeTags.java` | 27 | `3805f34f55ae616145b2c10b4f6e6c78223096248f4a45779f698cc9f8482d97` |

## Parent-marker correction

The version parent `decompiled_minecraft/1.20.3/` contains only the `mojmap/` directory; it has no version-root `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, or `artifacts.sha256`. The mapping root `decompiled_minecraft/1.20.3/mojmap/` contains `.decompile-provenance.json` in addition to `com/` and `net/`; it does not contain the 1.20.2/1.20.4 campaign ready/source/artifact sidecars. Thus the earlier report's unqualified wording that 1.20.3 had “no `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, or `artifacts.sha256`” missed the hidden mapping-root `.decompile-provenance.json`. That file is sufficient here to bind the seven selected source files and their inputs; it is not a campaign ready marker or packaged-resource archive.

## Corrected boundary verdict

The exact, identity-bound 1.20.2 `Boat.positionRider` has no tag gate and always rotates the passenger after positioning. The exact, identity-bound 1.20.3 source places passenger `YRot`, `YHeadRot`, clamp, and the full-boat Animal adjustment inside `if (!passenger.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS))`. The identity-bound 1.20.4 source has the same gate and body. Therefore 1.20.3 is the earliest checked release with the tag gate, and the observed boundary in this three-release sequence is 1.20.2 → 1.20.3. The loaded tag's exact packaged membership for `EntityType.PLAYER` remains unverified: the client JAR payload is identified by the record but was not available as local bytes. No membership inference is made from the 1.21 update data-generation provider.
