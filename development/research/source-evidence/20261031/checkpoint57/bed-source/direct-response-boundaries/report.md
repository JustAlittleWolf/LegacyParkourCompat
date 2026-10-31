# Direct-response operation boundaries — Mojmap 1.15.2 through 1.16.5

## Scope

Exact-source boundary audit of the accepted bed-wake stand-up position algorithm and mounted client correction detach. Audited 1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, and 1.16.5 from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. This checks method forms and named helper routes only; it does not claim pair freeze, full tick coverage, or runtime parity.

Inputs include the accepted bed-wake pair witness and its route review, plus the accepted mounted-correction receiver review and canonical draft binding in sibling research folders. No findings, product code, wiki inputs, backlog, builds, tests, runtime, or decompiler were used or changed.

## Exact source identities and integrity

For 1.15.2, 1.16, 1.16.1, 1.16.2, and 1.16.5, each root `mojmap.ready.json` reports the exact `versionId`, `mapping=mojmap`, `status=ready`, and source count below. In each release the SHA-256 of root `mojmap.sources.sha256` equals the hash in its ready record. Every existing audited source file listed below matches its own entry in that release's source manifest; `DismountHelper.java` is absent in 1.15.2.

| Release | Ready identity | SHA-256 of `mojmap.sources.sha256` |
|---|---|---|
| 1.15.2 | `1.15.2 / mojmap / ready / 3332 files` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16 | `1.16 / mojmap / ready / 3572 files` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | `1.16.1 / mojmap / ready / 3572 files` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |
| 1.16.2 | `1.16.2 / mojmap / ready / 3516 files` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` |
| 1.16.5 | `1.16.5 / mojmap / ready / 3523 files` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |

For 1.16.3 and 1.16.4, each exact Mojmap root carries its authoritative `mojmap/.decompile-provenance.json`. The embedded provenance SHA-256 values are `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`, respectively. Both provenance records use the `lpc-decompile-v1` pipeline and identify their exact version metadata, client, and published client mapping input:

- 1.16.3 metadata `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; client `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; mapping `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- 1.16.4 metadata `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`; client `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; mapping `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.

For each of 1.16.3 and 1.16.4, all six inspected files (`BedBlock.java`, `LivingEntity.java`, `BlockGetter.java`, `DismountHelper.java`, `ClientPacketListener.java`, `Entity.java`) match their same-release embedded `sources` entry. The 1.16.3 and 1.16.4 `BedBlock`, `LivingEntity`, `BlockGetter`, `DismountHelper`, and `Entity` hashes also match their entries in the .2/.5 root manifests. The receiver source file is separately validated against its own embedded map for both releases.

Inspected source-file SHA-256 fingerprints, grouped where byte-identical:

| Source file | 1.15.2 | 1.16 and 1.16.1 | 1.16.2–1.16.5 |
|---|---|---|---|
| `world/level/block/BedBlock.java` | `e2496c30e0ff922f5bb492f951e2389c1e1fbbbdcd7aed2d4d308574fa7a4b0f` | `47fff9ab0073a8970ffd0a0db3120903b43e4246bf0f932687bdee9e6e6de063` | `32cff55596d10fb86bb8bd3fb9769df0df9826b8bd449fd4d70fb4d6f3868ced` |
| `world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `world/level/BlockGetter.java` | `7c144c5890245b8189faea94a56c628d469e4c142977645f753deac7f62f9545` | `0f81fc1750218ddefabb33a03120cf1ac3447640c1c97ff59850fd45fab38f44` | `847fe74a63e7c5a48c028cd40ddbd579ee616370bd3a19b03ff1ab4271175e2b` |
| `world/entity/vehicle/DismountHelper.java` | absent | `1eba299fa5aac4613ff1331dda00523e1bfa13cef0fe1dc42526909c08dfd3f1` | `c066d3d65a1dd25294159fef4081c341059e32afa49ce474cbcd8718fc1528e8` |
| `client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` (.0–.1) | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` (.2–.3); `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` (.4–.5) |
| `world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |

## Boundary 1: bed-wake stand-up position algorithm

**First audited release with the accepted new candidate-search/helper form: 1.16.2. It persists through 1.16.5.** The exact patch introduction is bounded to after 1.16.1 and no later than 1.16.2 in the inspected stable releases; no finer build boundary is claimed.

- 1.15.2 `BedBlock.findStandUpPosition(EntityType, LevelReader, BlockPos, int)` (`BedBlock.java:237–265`) scans an x/z 3×3 square for each of two bed positions, then calls `getStandingLocationAtOrBelow` (`:267–298`). That helper rejects current shapes above `0.4375`, searches down through empty shapes by at most two blocks, adds `2.0E-7` to support Y, enforces the vertical limit, and uses `noCollision`.
- 1.16 and 1.16.1 retain that integer-indexed candidate search (`BedBlock.java:227–254`) and the same support search (`:257 onward`). `LivingEntity.stopSleeping` still calls it with index `0` (`LivingEntity.java:3021`). The source files changed elsewhere, and `getStandingLocationAtOrBelow` became public in 1.16, but the reviewed candidate algorithm has not yet become the yaw-ordered/helper route.
- Starting in 1.16.2, `BedBlock.findStandUpPosition(EntityType, CollisionGetter, BlockPos, float)` (`BedBlock.java:233–244`) chooses yaw-dependent directions, branches for bunk beds, and searches ordered offsets through `findStandUpPositionAtOffset` (`:276–290`). That helper invokes `DismountHelper.findSafeDismountLocation`; the latter uses the new shape/supplier `BlockGetter.getBlockFloorHeight` route. `LivingEntity.stopSleeping` now passes `this.yRot` (`LivingEntity.java:3037`). This algorithm and helper route are present unchanged in 1.16.3, .4, and .5 (the corresponding source hashes are identical across .2–.5).

Thus the accepted 1.15.2-to-1.16.5 stand-up position witness is **not** evidence that the new algorithm existed in the 1.16.0 release: .0 and .1 still use the former grid search; the new form first appears in .2 among the audited releases. This boundary covers the paired candidate search and support/helper route together. It does not attribute the positional difference to the `BlockGetter` overload alone.

## Boundary 2: detach mounted local player on a correction

**First audited release with the mounted-correction detach gate: 1.16. It persists through 1.16.5.** Given the accepted 1.15.2 endpoint, introduction is bounded to after 1.15.2 and no later than 1.16; no finer boundary is established.

- In 1.15.2, `ClientPacketListener.handleMovePlayer` (`ClientPacketListener.java:669–727`) computes the corrected coordinates and old-coordinate fields, then applies position and velocity without a vehicle check or detach call. There is no `tickCount > 0 && getVehicle() != null` receiver gate.
- In 1.16 and 1.16.1, the same receiver adds `if (player.tickCount > 0 && player.getVehicle() != null) player.removeVehicle();` at `ClientPacketListener.java:726–727`; in 1.16.2–1.16.5 it is at `:714–715`. Each release has the same condition and operation. It runs after the `xOld/yOld/zOld` bookkeeping and before correction `setPosRaw`, previous-position, delta-movement, and rotation writes.
- The call reaches `Entity.removeVehicle` (1.16 `Entity.java:1630` onward; 1.16.2+ `:1650` onward), which clears the player's vehicle relationship. The direct client receiver therefore changes local passenger state under its own positive-tick and mounted gates. This claim does not require a server-side producer or packet-frequency premise.

## Conclusion

The bed-wake stand-up candidate search/helper algorithm boundary is 1.16.2 among checked releases, and that form persists through 1.16.5. Mounted correction detach first appears in the 1.16 release among checked releases, and persists through 1.16.5. The former remains bounded by the inspected .1/.2 pair; the latter remains bounded by the 1.15.2/.16 endpoint comparison. These method-level results do not close the wider 1.15.2–1.16.5 movement pair or establish runtime parity.
