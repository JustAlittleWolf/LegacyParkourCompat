# Independent bounded audit: 1.8.3 → 1.8.4 Vine, Portal, and resources

## Verdict and boundary

**Bounded same-input closure confirmed.** I independently retraced the Vine movement consumers, the changed server-side Vine producer, the PortalForcer position path through packet production and client application, default block registration, and packaged resources. Under equal effective client block/player movement state and default vanilla registrations, none of these creates a local 1.8.3 → 1.8.4 player-movement operation delta.

The PortalForcer coordinate change is explicitly excluded as server-produced exit-position payload production: the server sends the authoritative position through the same packet format and the unchanged client applies its received values. If a client-side direct response/application operation itself changed, that would remain in scope; that is not the case in the checked pair. This verdict does not extend to unequal server-received world or position inputs, custom registries/resources, intermediate versions, full-pair coverage, or runtime parity.

## Exact source identity and hashes

Canonical read-only roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.3/ornithe-feather/` and `.../1.8.4/ornithe-feather/`. Both trees contain `.decompile-provenance.json`; its SHA-256 is `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` (1.8.3) and `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` (1.8.4). The provenance inputs identify the client archive digests listed below; mapping input is `meta.ornithemc.net/v3/versions/feather/1.8.3` and `.../1.8.4` respectively, within the `ornithe-feather` mapping family.

| Paired source member | 1.8.3 SHA-256 | 1.8.4 SHA-256 | Check |
|---|---|---|---|
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | same | Equal; registers `portal` and `vine` constructors |
| `net/minecraft/block/Blocks.java` | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` | same | Equal; resolves `NETHER_PORTAL` and `VINE` |
| `net/minecraft/block/PortalBlock.java` | `bb9d2cba26df71c8383408fcccc93b849eebecd3d36924a63ee80cba8734867e` | same | Equal |
| `net/minecraft/block/VineBlock.java` | `110ed5fff140f9b01ce20f9a2fa0c670160cfd31d99bf06566660bca2f38ea07` | `c5052b5d6b70598c06ecb351fe2e4fcf5d2674bda6fb2ad1e9d0bf4aeab816b6` | Changed producer; tick writes remain under `!world.isClient` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | same | Equal movement consumer |
| `net/minecraft/server/world/PortalForcer.java` | `86d5701245e49e298d9819e98968c54a2e36f9af27c8a898a8361a70932015da` | `5fe634ce1537e1fbe6c615a47a23059b66b52f24b9c21f99d52b300f9c00ee44` | Changed server producer |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | same | Equal; dimension change server-gated |
| `net/minecraft/server/PlayerManager.java` | `e70d2028b79f487ce17bfb524e9cfa2dcb27543a4f62356bf42aec263a19701e` | `0498b44f26782daa8b82db0c0823cef615c6bd7e7234a43c32456e15647d9a5d` | Changed surrounding server path; invokes forcer then teleport |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `291a05b4702ff32e604dfa8e57dc5c8f90f63aa424afd1b37ddbb4a204569f58` | `c2de662af504d7128a855efd1b105097667f662a80919f569bdc922787d5197a` | Changed surrounding server path; emits position packet |
| `net/minecraft/network/packet/s2c/play/PlayerMoveS2CPacket.java` | `3c2c044610df527df20561b4ded36aca91c774b7d0520f9e4cfe657030bf1baf` | same | Equal packet member |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `7fa3d587325068eaa158526cb59e4850b704ac237938df6592d1a09148db40c3` | same | Equal receive/application member |

Cache identity was independently checked against provenance:

| Release | `version.json` SHA-256 (`id` checked) | `client.jar` SHA-256 |
|---|---|---|
| 1.8.3 | `4afafb220ce18063d3aec5f8bab178a5192d84ca2d63b358fee7d369aea96938` | `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` |
| 1.8.4 | `c7220eabc36645636c8597b167d9ca67d16580611ed2d7d8cd7a3c0aec904587` | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` |

## Closure trace and changed-edge disposition

- **Vine registration/resource edge:** equal `Block.java` registrations create the default `PortalBlock` and `VineBlock`; equal `Blocks.java` aliases resolve those defaults. `PortalBlock.java` is byte-identical. No data-driven resource chooses these defaults in the inspected packaged client archives.
- **Vine movement consumer:** `VineBlock.getCollisionShape` returns `null` in both releases. Equal `LivingEntity.isClimbing` reads the block identity at the floored player feet position and applies the existing player spectator gate; it does not inspect vine direction properties. The changed Vine tick alters server growth/property-write logic, with all writes under `if (!world.isClient)`. Disposition: server producer only; any later differing vine existence/property state is an unequal received block-state input, not a same-input local movement delta.
- **Portal transfer path:** 1.8.3 `PortalForcer.findNetherPortal` adds the forward-axis offset to `f` or `h` in the found-portal branch; 1.8.4 omits those additions. Both call `entity.setPositionAndAngles` with the computed coordinates. `Entity.changeDimension` is guarded by `!world.isClient`; `PlayerManager` calls the destination forcer and the server handler teleport; the handler sends `PlayerMoveS2CPacket`; identical client `handlePlayerMove` applies the packet. Disposition per assignment rule: exclude server exit-position payload production where identical packet values are applied identically. The changed numeric position is an unequal received position when it differs, while direct client-side response/application changes remain eligible and none were found here.
- **Packaged resources:** provenance-matched cached client archives were present and hashed before read-only inventory. Both contain 3,085 `assets/` entries with equal path sets. No asset path was added or removed; only `assets/minecraft/lang/en_US.lang` differs in contents. Both archives contain zero `data/` entries. The remaining asset delta is language presentation content and does not provide block registration, collision shape, or climb-predicate inputs. This closes only packaged default resources, not custom/modded registries/resources.

## Exact inputs and report identity

This audit is supplementary to `.native-source/20261030/ordinary-pool/183-184-same-input-closure/report.md` (SHA-256 `c0affec7df11b514b36c12f96322f756437713d656365b32946ef5d971b5910d`) and the prior bounded review/correction. I verified the canonical source member hashes above against the actual absolute paths and independently confirmed the cached resource inventories. I did not inspect product implementation, wiki/backlog/product history, or perform decompilation, download, build, test, or launch.

Next queue opportunity: no further work remains within Vine/Portal/default packaged-resource closure for this pair. A distinct adjacent-pair assignment or an explicitly scoped custom-resource/registry analysis would require fresh bounded source inputs.
