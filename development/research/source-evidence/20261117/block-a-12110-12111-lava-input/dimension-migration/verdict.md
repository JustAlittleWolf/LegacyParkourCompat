# Independent source dependency verdict

## Audited input

- Corrected report: `lane-lava-dim-migration-12110-12111/comparison.corrected-provenance.md`
- Expected and observed SHA-256: `6183ac3a8206ecdcf62690d5994ccbfe7045173967d5d1c96fad51ea9c55772e`
- Bytes decode as UTF-8 without BOM, use LF only, and end in LF.
- All 16 source-file hashes in the report match both the local source bytes and the relevant versioned `mojmap.sources.sha256` entry.
- Mojmap ready markers match the source manifest hashes; complete manifest checks also matched all 6,386 (1.21.10) and 6,622 (1.21.11) listed source files. Artifact-manifest hashes/counts and diagnostics hashes match their ready markers.

## Verdicts

1. **Old required `ultrawarm` input versus new `FAST_LAVA`: supported.** In 1.21.10 `DimensionType.DIRECT_CODEC` requires the boolean `ultrawarm` field, and the built-in Nether has `ultraWarm=true`. In 1.21.11 the dimension record and codec have no such field; `attributes` is optional and defaults to `EnvironmentAttributeMap.EMPTY`. `FAST_LAVA` is a non-positional, syncable boolean whose registry default is `false`. The stock Nether explicitly sets it to `true`. `EnvironmentAttributeSystem` uses the dimension map and returns the attribute default when no sampler exists. The report accurately distinguishes built-in preservation from custom authored dimensions lacking the new attribute.

2. **DataFixer / world-definition and unknown-key boundary: supported with the stated limitation.** I inspected both `DataFixers.addFixers` bodies and the report’s bounded searches across both `net/minecraft/util/datafix/` trees. They show no applicable `ultrawarm` to `FAST_LAVA` conversion. The newer `DimensionType` codec declares `attributes`, not `ultrawarm`; the optional empty default does not derive a value from the removed key. The report does not claim that unknown-key parsing is accepted or rejected: that behavior belongs to the external Mojang codec library and remains unresolved from this source set. Its negative claim is limited to the inspected vanilla codec/DataFixer route.

3. **Syncable dimension value reaches the client query: supported.** `FAST_LAVA` is syncable; `RegistryDataLoader.SYNCHRONIZED_REGISTRIES` includes `DIMENSION_TYPE` with `DimensionType.NETWORK_CODEC`; `EnvironmentAttributeMap.NETWORK_CODEC` filters to syncable attributes. `ServerConfigurationPacketListenerImpl` schedules registry synchronization, `SynchronizeRegistriesTask` packs the worldgen registries into registry-data packets, and `ClientConfigurationPacketListenerImpl` collects them into the frozen registries used by `ClientPacketListener`. `CommonPlayerSpawnInfo` carries the dimension-type holder; `ClientPacketListener` passes it to `ClientLevel`. `Level.dimensionType()` reads that holder, and `ClientLevel` builds its environment-attribute system from that level's dimension layer. `Entity.updateInWaterStateAndDoFluidPushing` queries `FAST_LAVA`; no `LocalPlayer` override was found.

The additional closure files also match their source-manifest entries: `SynchronizeRegistriesTask.java` `896384b86b97c10dffc6e55048579cd5e221eea78906cd642d11b6cdf13ae71a`, `CommonPlayerSpawnInfo.java` `55ee12eccb7c059eccab6f7d74deade0abc56885de99d8c7d05534fb68936aef`, `ClientConfigurationPacketListenerImpl.java` `84d22f0b65d05a3f17d9f10beda1159501957d82669da37aa4b1e934b04a6696`, `Level.java` `de329fdbeca632a1b63074e1735890b2624abfc57b7a579f43eef36caefef98d`, and `ClientboundRegistryDataPacket.java` `82ea49bf3b73e32b900abc164a52eca848f3bd7ce81bcdd5bc5d61a7c84dc118`.

## Overall disposition

**Accept the three bounded source-dependency claims.** Keep unknown-key behavior for legacy raw dimension JSON and third-party world conversion explicitly open; this verdict does not infer codec-library behavior or claim behavior for external resources. No implementation, pair-wide movement coverage, or runtime-parity verdict is made.
