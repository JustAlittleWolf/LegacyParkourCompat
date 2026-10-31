# Independent terminal disposition: 1.20.5 → 1.20.6

## Verdict

**All 13 exact-case changed/added Java paths in the supplied 1.20.5→1.20.6 roster are terminally dispositioned for the project's eligible local-player movement surface. No new eligible ordinary local-player movement delta was found in this endpoint.** This is an endpoint source audit, not a runtime parity claim and not a decision to freeze the overall 1.20.4→1.20.6 pair.

The pair inventory has 12 changed paths and one added path; there are no removed paths. I independently read each changed/added source member and followed movement-adjacent consumers where applicable. The source manifests and ready markers are release-level sidecars located beside, not inside, each `mojmap` directory. Release source-manifest SHA-256 values are 1.20.5 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484` and 1.20.6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. For every one of the 13 rows, I recomputed the present source file hash and matched it to the existing three-way inventory's version-specific hash cell; the added `V3808_2.java` is present only in 1.20.6 as recorded. The inventory used is `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv`, SHA-256 `961e797371cada010cfd4abe628ec7b312c0e73b50eb7ff25a5ba1c86c204253`.

## Exact path dispositions

| Path | Status | Terminal and movement boundary |
|---|---|---|
| `net/minecraft/DetectedVersion.java` | Changed | Only the fixed version name and data version change (`1.20.5`/3837 to `1.20.6`/3839). Version metadata is not an ordinary movement calculation or player-state response. |
| `net/minecraft/SharedConstants.java` | Changed | Only version/data constants change, including world version, version string and snapshot protocol value. The movement consumers in scope do not branch on these release identity constants. |
| `net/minecraft/client/multiplayer/chat/report/ReportEnvironment.java` | Changed | The report string changes the embedded client version from `1.20.5` to `1.20.6`; this is chat-report metadata only. |
| `net/minecraft/util/datafix/DataFixers.java` | Changed | Registers the 3808.2 schema and a Trader Llama body-armor data-fix, plus adjusted schema numbering. These are save-data/entity migration operations, not client local-player movement. The only new entity concerned is Trader Llama, whose vehicle/non-player physics is excluded. |
| `net/minecraft/util/datafix/fixes/ChestedHorsesInventoryZeroIndexingFix.java` | Changed | Extends the existing horse-like inventory-index migration to `minecraft:trader_llama`. It transforms saved non-player mount inventory indexes; it does not write or query player position, velocity, rotation, collision or movement attributes. |
| `net/minecraft/util/datafix/schemas/V3808_2.java` | Added | Registers the Trader Llama entity schema and its entity/item fields for the new migration version. Schema shape is serialization metadata for the non-player mount. No movement consumer exists in this type. |
| `net/minecraft/world/item/component/CustomData.java` | Changed | The only behavior change is in `loadInto(BlockEntity, Provider)`: failed `loadCustomOnly` application now logs, attempts to restore the prior custom-only tag, and returns false. Its `loadInto(Entity)` path is unchanged. BlockEntity callers are item block-entity-data application (`BlockItem.updateCustomBlockEntityTag`, gated by a server being present and permissions) and server clone handling; these are server/world-state producers, which project scope excludes from historical client movement. The entity-call route is unchanged. |
| `net/minecraft/world/level/BaseCommandBlock.java` | Changed | Custom command-block name parsing now uses the shared safe parser. For valid names it delegates to the same JSON component parser; malformed-name error handling affects only command-block display metadata. Command execution is server-side state production and outside historical client movement. |
| `net/minecraft/world/level/block/entity/BannerBlockEntity.java` | Changed | Replaces direct custom-name JSON parsing with `BlockEntity.parseCustomNameSafe`; valid name values are unchanged, malformed names are logged/discarded. Banner names do not feed local-player movement; the banner pattern/state operations are untouched. |
| `net/minecraft/world/level/block/entity/BaseContainerBlockEntity.java` | Changed | Same safe custom-name parser substitution. Valid names preserve the same parsed component; malformed input affects only container naming. Inventory and container block movement responses are untouched. |
| `net/minecraft/world/level/block/entity/BeaconBlockEntity.java` | Changed | Same safe custom-name parser substitution. Beacon tick/effect application, including movement-related effects, is unchanged in this file; only valid/malformed name metadata handling differs. |
| `net/minecraft/world/level/block/entity/BlockEntity.java` | Changed | Adds `parseCustomNameSafe`, which returns the same result from `Component.Serializer.fromJson` for valid names and logs/returns null on parse exceptions. Existing block-entity load/position/component methods are unchanged. Its only new callers in this roster are the four display-name readers above and `BaseCommandBlock`. |
| `net/minecraft/world/level/block/entity/EnchantingTableBlockEntity.java` | Changed | Same safe custom-name parser substitution. Enchanting-table visual/interaction fields do not produce a player movement response; the enchanting behavior is unchanged. |

## Consumer checks and limits

`CustomData.loadInto(BlockEntity)` is the only changed method among these 13 paths that mutates a gameplay state object. The client-local `BlockItem.updateCustomBlockEntityTag` call path is gated by `Level.getServer() != null`; the loader therefore runs as a server-side item-placement or command/data mutation. That source change can change server-side handling of malformed block-entity data, but server movement/state production is explicitly outside this compatibility scope. The separate entity custom-data load path remains byte-identical between the endpoints.

The data-fix rows are limited to horse/trader-llama save migration and its schema. Neither `HorseBodyArmorItemFix` nor `ChestedHorsesInventoryZeroIndexingFix` targets player inventory, attributes, position, velocity, or any local-player tick terminal. Trader Llama is a non-player vehicle entity, excluded by project scope.

The custom-name helper's normal-success path calls the same serializer with the same raw string and registry lookup. Its difference is exceptional malformed-text behavior (warn and discard instead of propagating); this changes metadata/error handling, not any player movement input or response. The beacon's movement-affecting effect application logic is unchanged.

No product/wiki/backlog/Git history was read. No product files or shared decompiled sources were changed; no builds, tests, source generation, game/server launch, or runtime check was performed. This report preserves the previous 1.20.4→1.20.5 audit as a separate artifact.

## Manifest and per-path hash verification

I recomputed the release sidecar manifest digests and confirmed they equal the corresponding ready-marker `sourceManifestSha256` values: 1.20.5 `162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484`; 1.20.6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`. The per-path ledger [source-hashes.tsv](source-hashes.tsv) records each changed or added path's inventory hash, independently recomputed SHA-256, and exact manifest membership. All 12 changed paths match the 1.20.5 and 1.20.6 manifests and their inventory columns. The added `V3808_2.java` matches the 1.20.6 inventory and manifest and is absent from the 1.20.5 tree as expected. Ledger SHA-256: `7755b65bc70a52540fee1d6f5cf0c6a42562c0d01cd466633f715a94ca3d9465`.

## CustomData caller boundary clarification

The changed `CustomData.loadInto(BlockEntity, Provider)` is reached by `BlockItem.updateCustomBlockEntityTag(Level, ...)`. That caller first retrieves `level.getServer()` and returns false immediately when it is null, before applying block-entity custom data. Thus the changed block-entity load/rollback behavior is server-side. `CustomData.loadInto(Entity)` remains unchanged. This confirms there is no client-local block-entity custom-data writer in this route.
