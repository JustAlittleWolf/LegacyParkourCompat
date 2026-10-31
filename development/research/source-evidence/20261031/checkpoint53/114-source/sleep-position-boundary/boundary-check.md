# Sleep-position offset boundary check

## Conclusion

The earliest inspected release that proves the synchronized sleeping-position callback writes the bed center is **1.14 (the 1.14.0 stable release)**. The behavior is also present in 1.14.1, 1.14.2, and 1.14.3, and at the Feather-mapped 1.14.4 endpoint. The exact-version metadata identifies root `1.14` as `versionId: 1.14`; the source tree is therefore the 1.14 release, conventionally referred to as 1.14.0. Combined with the accepted 1.13.2 endpoint, the first stable release in this transition with the new center offset is 1.14/1.14.0.

No alternate intermediate offset was found in the inspected 1.14–1.14.3 `LivingEntity` files. Each has the same client-only tracked-data callback and `setPositionInBed` center coordinates.

## Crosswalk

- **1.13.2, Ornithe Feather:** `ClientPlayNetworkHandler.handlePlayerSleep` (816–819) resolves the packet player and invokes `trySleep(packet.getPos())`. `PlayerEntity.trySleep` (1269–1275) uses, for a loaded chunk, float offsets `0.5F + direction.getOffsetX/Z() * 0.4F` and writes Y `pos.getY() + 0.6875F`. The center fallback is only for an unloaded chunk. The candidate/review pair the loaded bed case.
- **1.14 (1.14.0), Yarn:** `LivingEntity.onTrackedDataSet` (2487–2492) handles `SLEEPING_POSITION` on the logical client and applies a present sleeping position via `setPositionInBed`. That helper (2709–2711) writes `(x + 0.5, y + 0.6875F, z + 0.5)`; this is the earliest inspected stable witness.
- **1.14.1, Yarn:** same callback (2487–2492) and center helper (2709–2711).
- **1.14.2, Yarn:** same client callback at 2493–2498; center helper at 2715–2717.
- **1.14.3, Yarn:** same client callback at 2501–2506; center helper at 2723–2725.
- **1.14.4, Ornithe Feather:** endpoint mapping expresses the callback as `onDataValueChanged` (2510–2515), then helper `m_57357409` at 2734–2736 writes `(x + 0.5, y + 0.6875F, z + 0.5)`.

These source pairs establish a direct player position-response difference under paired local-player/sleep-position inputs. They do not establish server-side input production or bed-entry eligibility.

## Source identities and readiness

Endpoint Feather readiness/provenance and source-manifest records were checked directly. The 1.13.2 Feather ready marker reports `status: ready`, `versionId: 1.13.2`, mapping `ornithe-feather`, and names `ornithe-feather.sources.sha256`; its `sourceManifestSha256` is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The provenance marker identifies namespace `ornithe-feather`, version `1.13.2`, and mapping artifacts `feather-gen2-1.13.2+build.2-mergedv2.jar` and `.tiny`. Both relevant source hashes match entries in that manifest.

The 1.14.4 Feather ready marker reports `status: ready`, `versionId: 1.14.4`, mapping `ornithe-feather`; its `sourceManifestSha256` is `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Its provenance identifies namespace `ornithe-feather`, version `1.14.4`, and `feather-gen2-1.14.4+build.2-mergedv2.jar` plus `.tiny`. The `LivingEntity.java` hash matches the source manifest entry.

The 1.14 Yarn tree has `.decompile-provenance.json`; its `sources` map records the `LivingEntity.java` file hash below. The sibling exact-version Feather ready/provenance metadata identifies root `1.14` as version 1.14; it is version corroboration, while the Yarn provenance supplies the per-file identity. The 1.14.1–1.14.3 Yarn roots have no ready/provenance/source-manifest files at their version roots; their inspected `LivingEntity.java` hashes below identify the per-file witnesses without a readiness claim.

| Version / mapping | Source file | SHA-256 |
|---|---|---|
| 1.13.2 Ornithe Feather | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `4f65502fbee6476cef0838563f03a66f07a1a9ed8c63e580f5338ba5fe274f11` |
| 1.13.2 Ornithe Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.14 Yarn | `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` |
| 1.14.1 Yarn | `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| 1.14.2 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| 1.14.3 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| 1.14.4 Ornithe Feather | `net/minecraft/entity/living/LivingEntity.java` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |

The immutable reviewed candidate is `sleep-position-response.draft.md`, SHA-256 `BE6FD49E3BFF04ED690FE1CC643EC96CAB1F5CC21CC8F6F94FBF1947908B41C8`. Its independent review accepts the endpoint claim under paired-input assumptions; this boundary check does not alter that verdict.
