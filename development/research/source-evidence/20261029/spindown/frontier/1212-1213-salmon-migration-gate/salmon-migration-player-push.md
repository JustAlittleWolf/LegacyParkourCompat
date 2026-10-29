# 1.21.2 → 1.21.3 Salmon migration and local-player push gate

## Scope and input

This is a bounded source-only closure of the migration/load and client push dependencies identified in `.native-source/20261028/frontier-c/1212-1213-full-audit/audit-report.md` (SHA-256 `c267cdde242ee3fc48371329c22834d37e75f3955afb50c3be113b9fc169d090`). It does not close the pair, entity simulation, non-Java inputs, or runtime/trajectory behavior. Canonical sources were read-only; no build, tests, or launch were run.

## Source identity and selected hashes

Canonical sources are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- 1.21.2: `1.21.2/mojmap/.decompile-provenance.json` exists inside the mapping directory (SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`). Its embedded source map lists 5,654 Java paths. It pins version metadata `1.21.2.json` (`793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`), client JAR (`613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`), and Mojmap `client.txt` (`e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`). No separate release-parent readiness/sidecar files were present.
- 1.21.3: the mapping directory has no embedded `.decompile-provenance.json`; its release parent does have `mojmap.provenance.json` (`24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`), `mojmap.ready.json` (`72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02`), `mojmap.sources.sha256` (`d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`), and `artifacts.sha256` (`0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`). The ready record identifies 1.21.3 Mojmap and 5,655 source files, and pins the client hash `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`; the artifact list includes Mojmap mappings. The hashes below were recomputed and match their side's embedded or parent source map.

| Source | 1.21.2 SHA-256 | 1.21.3 SHA-256 |
|---|---|---|
| `net/minecraft/DetectedVersion.java` | `37ddb1036daeaf6d84d026deed7b3df65fd62e83f1dbbb96fe8ec8aed7fec760` | `f6abe13e569a6e5f837c28be583a4c5d6317cef46a465faabc26f94ece81d5a9` |
| `net/minecraft/SharedConstants.java` | `f8070c2721438d1cc2d2e490ad75c512f1ea7529c20c9e3d90923766ae94ec0d` | `6e2884dc931e3f522822a1e77a08557072d27ddace4a472e3251ce5b77342a81` |
| `net/minecraft/util/datafix/DataFixers.java` | `e77137893cf36c3ce9b8583f134d110939f5972f01e814f0c84d039b7cffcdc7` | `f17acb2c5aef9b27f8d08568a2dcceae98be3d203536cce1ba6dc26a8a2e4cc1` |
| `net/minecraft/util/datafix/fixes/EntitySalmonSizeFix.java` | absent | `0469bbd0578b22f7d3cbfe023bad8a12a8cc45e48e6a31fbb0889535046c1931` |
| `net/minecraft/world/entity/animal/Salmon.java` | `7f3650f86ed80d99da70f665166b9b8a09362afee33fa2e9767595e426403310` | `3f66d1abd27dafe159c3a75fade2b43252e5985f04c38e033d8077bbe36fee1b` |
| `net/minecraft/world/level/chunk/storage/EntityStorage.java` | `60bf44b0687a2f3f7560d8d67280f58be38f3a42aea840eb7119b410acebf4e3` | identical |
| `net/minecraft/world/level/chunk/storage/SimpleRegionStorage.java` | `0627e1fa9b7eb9593c5c19c1578a282140bbabd1c85462d66d393332401f843e` | identical |
| `net/minecraft/world/entity/EntityType.java` | `433a855503eff559bf0d9c009080b4ff1dec73c497a455a1d22d6812ab9e7656` | identical |
| `net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` | identical |
| `net/minecraft/world/entity/Entity.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` | identical |
| `net/minecraft/world/entity/EntitySelector.java` | `cbbc7b0bbdc0849d191008b165c6d1ebb0ca2d8da8cffd42e5cd89cc1e710a74` | identical |
| `net/minecraft/world/level/Level.java` | `ff4d57a3df0b850ba02dc798c6aa6734174790b9fd17673261f33f03d44821e1` | identical |
| `net/minecraft/world/level/entity/EntityTypeTest.java` | `6a85ac27f1427bd19f041ebc92b4e3a0002d7e01da4c276c0ca49f09a50001b6` | identical |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` | identical |
| `net/minecraft/network/syncher/SynchedEntityData.java` | `755998494338969b6d47b91c463057d9a009d6804e8d06b0ab30719634cc9234` | identical |

`ServerEntity.java` is also byte-identical (`c45400e92eb0ad334212bda63549050259ca1cab74dd9a50ec2f2738f86fa3a6`); `DataFixTypes.java` is identical (`1bb4bc4bc1bddc8c2a9e78e12ac1115d0d1fedf196929ff388900c00da7a4698`). Source maps, not source directory names alone, bind the selected Java bytes.

## Migration and load path

The stable data version is 4080 in 1.21.2 (`DetectedVersion.java:33`, `SharedConstants.java:15`) and 4082 in 1.21.3 (same locations). In 1.21.3, `DataFixers` adds schema 4081 and registers `EntitySalmonSizeFix` (`DataFixers.java:1368–1369`). The new fix targets the entity choice `minecraft:salmon` (`EntitySalmonSizeFix.java:7–9`); its body reads `type` with fallback string `medium`, leaves only `large` unchanged, and writes `medium` for all other values (`:12–16`). Thus an old saved `small` Salmon entity record is rewritten to `medium` when its entity chunk is upgraded from 4080 to the 1.21.3 current data version 4082.

The checked caller chain is server-persistence code: `ServerLevel` constructs `EntityStorage` with `DataFixTypes.ENTITY_CHUNK` (`ServerLevel.java:233–235`); `EntityStorage.loadEntities` calls `SimpleRegionStorage.upgradeChunkTag` before `EntityType.loadEntitiesRecursive` (`EntityStorage.java:43–68`); `SimpleRegionStorage.upgradeChunkTag` extracts the stored data version and calls `DataFixTypes.updateToCurrentVersion` (`SimpleRegionStorage.java:35–37`); `EntityType.loadEntityRecursive` delegates to `Entity.load`, which calls `readAdditionalSaveData` (`EntityType.java:1331–1345`, `Entity.java:1804–1873`). The migration applies to persisted entity chunks, not to every source of Salmon state.

## Client dimension and push route

`Salmon.Variant` uses scales `SMALL = 0.5F` and `MEDIUM = 1.0F`; `getDefaultDimensions` scales the superclass dimensions by the parsed variant (`Salmon.java:122–128, 131–151`). The two source versions differ at `Variant.byName`: unrecognized or absent strings default to `SMALL` in 1.21.2 and `MEDIUM` in 1.21.3 (`:150–151`). Both define synchronized Salmon data with a `MEDIUM` default and refresh dimensions when `DATA_TYPE` changes (`:62–76`). The client `handleSetEntityData` looks up the entity and calls `assignValues` (`ClientPacketListener.java:546–551`); `SynchedEntityData.assignValues` calls `onSyncedDataUpdated` for each accessor (`SynchedEntityData.java:110–117`), reaching Salmon's dimension refresh. `ServerEntity.sendPairingData` sends nondefault tracked values in the entity pairing (`ServerEntity.java:270–279`), but this is an external/server-side payload producer and is not a client operation delta.

The shared `LivingEntity.tick` path calls `pushEntities` (`LivingEntity.java:2803`). On a non-`ServerLevel` client, `pushEntities` queries `Level.getEntities(EntityTypeTest.forClass(Player.class), this.getBoundingBox(), EntitySelector.pushableBy(this))` and calls `doPush` for each result (`LivingEntity.java:2853–2857`); `doPush` invokes the candidate's `push(this)` (`:2904`). `EntitySelector.pushableBy` requires pushability and, on a client level, accepts only a local `Player` candidate; team collision rules can further reject it (`EntitySelector.java:27–49`). `LivingEntity.isPushable` requires the player alive, nonspectating, and not on a climbable (`LivingEntity.java:3029`). `Entity.push` skips same-vehicle and no-physics pairs, requires horizontal separation with `Mth.absMax(dx,dz) >= 0.01F`, then may add a horizontal impulse to the player (`Entity.java:1533–1560, 1568–1571`). Therefore a Salmon whose enlarged AABB newly overlaps the local player can enter this direct response route, subject to those gates.

The decompiled `EntityTypeTest.forClass` anonymous `tryCast` body is not a reliable literal rendering of its captured class test: it prints `$$0.isInstance($$0)` with `$$0` as the method's generic `B` parameter, shadowing the captured `Class<T>`. The call site requests `Player.class`, and `EntitySelector.pushableBy` independently restricts client candidates to the local player, but I do not claim that the rendered helper body proves the class-cast implementation. This source defect is bound by the identical file hash in both versions and remains a source-interpretation limitation.

## Applicability and disposition

The size migration is a server-side saved-entity data producer change, while `EntityStorage` is constructed only for `ServerLevel`. The client dimension, synchronization receiver, entity query caller, selectors and push math are identical for both endpoints. For equivalent valid received state, both clients interpret `small` as `SMALL` and `medium` as `MEDIUM`, with the same dimensions and push gates. The ordinary 1.21.3 entity-chunk migration instead changes what a newer server loads from a saved `small` record; the source evidence does not establish that this is an in-scope historical client response difference for equal received state.

The changed fallback remains a source difference for absent or unknown `type` strings, but no normal client network producer for such a string was established. The 1.21.3 entity-chunk fix normalizes saved legacy entity records; item bucket/custom-data loading is a distinct path and is not covered by that entity-chunk fix. Effective packet producers, malformed/custom payload reachability, bucket/data-pack inputs, exact `EntityTypeTest` decompilation semantics, and any runtime trajectory remain unresolved. This report therefore records a server migration plus a conditional client response route, not an independently applicable client movement finding or pair freeze.
