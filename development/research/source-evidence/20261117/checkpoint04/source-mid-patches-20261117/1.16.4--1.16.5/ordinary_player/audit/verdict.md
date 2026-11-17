# Independent source audit: 1.16.4 → 1.16.5

**Verdict: ACCEPT for the bounded source comparison and partial coverage report.** This does not freeze the pair, establish runtime parity, or close resource/provider/fluid/callback dependencies.

## Bound input

- Report: `build/source-mid-patches-20261117/1.16.4--1.16.5/ordinary_player/coverage-partial.md`
- Report SHA-256: `2723F2F1340597D1F8A076656C7794FDC8D66F8A4E4210C8DC73418B4D8ED74E`
- Exact endpoints and input forms follow `build/source-inputs-115-116-20261117/README.md`; both source trees are Mojmap.
- 1.16.4 source manifest is `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json#sources`; the provenance bytes hash to `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`. Its pinned 1.16.4 metadata and client mapping identities match the queue contract.
- 1.16.5 uses `decompiled_minecraft/1.16.5/mojmap.sources.sha256`, hash `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, and `mojmap.provenance.json`, hash `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d`; the ready marker declares version `1.16.5`, Mojmap namespace and the matching source-manifest hash.

## Checks performed

- Parsed the 1.16.4 mapping-local hidden `sources` object and 1.16.5 root manifest. Both list 3,523 Java paths. Disk inventories also contain 3,523 Java paths each, with no path absent from its corresponding map.
- Compared the complete source maps: zero added paths, zero removed paths, and exactly four hash changes: `DetectedVersion.java`, `Direction.java`, `PoiSection.java`, and `BaseFireBlock.java`. Each listed hash was checked against both the source map and on-disk file bytes.
- Checked the report's selected movement-path source hashes against both bound maps and disk bytes. They match on both endpoints for `Minecraft`, `LocalPlayer`, `Player`, `LivingEntity`, `Entity`, `CollisionGetter`, `VoxelShape`, `BlockBehaviour`, `MobEffectInstance`, `ClientPacketListener`, and `ServerEntity`. The cited tick, player override, travel, movement, collision and velocity-receive anchors are present.
- The `DetectedVersion` change is release metadata. `Direction.Plane.getRandomAxis` selects from the horizontal plane's `{X, Z}` axes; its only source call is the new vertical-input branch in `BaseFireBlock.isPortal`.
- `BaseFireBlock.isPortal` in 1.16.4 always uses `direction.getCounterClockWise().getAxis()`. In 1.16.5 it retains that result for horizontal input and chooses a random horizontal axis only for vertical input. The direct item-use callers `FlintAndSteelItem.useOn` and `FireChargeItem.useOn` pass `UseOnContext.getHorizontalDirection`; that accessor returns `Direction.NORTH` when no player exists, otherwise the player's `getDirection`, which is `Direction.fromYRot` and therefore horizontal. The additional `DispenseItemBehavior` caller passes dispenser facing directly and is a non-player source. The report's narrow disposition of this difference is supported.
- `PoiSection` changes mismatch handling and POI record bookkeeping; the source bodies and method contexts support its non-player-movement disposition.

## Coverage limit

The report correctly retains **PARTIAL** coverage. Fluid contact/travel, complete shapes/providers/registrations, tag/resource membership, all player-response callbacks, full tick/dependency closure, and independent full-pair inventory audit remain open. No movement finding, runtime conclusion, or exact intermediate patch boundary is accepted here.