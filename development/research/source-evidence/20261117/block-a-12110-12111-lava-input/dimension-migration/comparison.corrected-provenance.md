# 1.21.10 → 1.21.11: lava-current dimension input migration

Corrected provenance copy. The original comparison.md is preserved unchanged by this correction task.

Scope: exact Mojmap decompiled vanilla sources only; raw dimension state feeding the local-player lava-current operation. No wiki, product implementation, Git history, builds, tests, launches, or external/custom-world behavior assumptions.

## Evidence identity and integrity

- Older: Minecraft `1.21.10`, Mojmap; read-only source at `decompiled_minecraft/1.21.10/mojmap/`.
- Newer: Minecraft `1.21.11`, Mojmap; read-only source at `decompiled_minecraft/1.21.11/mojmap/`.
- Each exact source tree has sibling readiness and provenance evidence one directory above `mojmap/`: `decompiled_minecraft/1.21.10/mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`; and the corresponding `1.21.11` files. The ready markers declare the exact version and `mojmap` mapping, and their recorded SHA-256 for `mojmap.sources.sha256` matches the actual manifest file for each version (`4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` and `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`). The provenance records give the exact `decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap` run and tool versions. No regeneration attempted.
- I checked all 16 listed source files against their per-version `mojmap.sources.sha256` entries; each local file hash matches its manifest entry. The ready markers bind those source manifests to the declared exact versions/mapping.
- Local readiness/provenance record hashes: 1.21.10 `mojmap.ready.json` `2374D901074B72CD69F0229381D6A11EDEA7983613C538A2E2E18D9FC73DBEB0`, `mojmap.provenance.json` `9A1DB4C050C3CAA28A1313F89DAE9DA3B28FEAA5F8AFBA42CAB798B47D7AD77C`; 1.21.11 `mojmap.ready.json` `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B`, `mojmap.provenance.json` `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`. These are local hashes, not signatures.

| Version | Relative source | SHA-256 |
|---|---|---|
| 1.21.10 | `net/minecraft/world/level/dimension/DimensionType.java` | `BCFE113B27408B48C0ED09308910306CE2F85338297623BF95A967F9B194BEAD` |
| 1.21.10 | `net/minecraft/data/worldgen/DimensionTypes.java` | `C7B5C103321338501B11E2B4E065BC54F85D8EF2582822B79997877655B454C3` |
| 1.21.10 | `net/minecraft/world/entity/Entity.java` | `8361DBB86FE6C975D21F69D008377B6F191669BE751150C842517E9D0346FA18` |
| 1.21.10 | `net/minecraft/util/datafix/DataFixers.java` | `549EA3397AB6897F962A80DA4C2FC0D19AE99D4F71BE8A3B83D9BB4E9D94F20D` |
| 1.21.11 | `net/minecraft/world/level/dimension/DimensionType.java` | `FC67FD2E178BBB95A252028B346268EE4097F1B0734649C2557AE41F26C8EC23` |
| 1.21.11 | `net/minecraft/data/worldgen/DimensionTypes.java` | `532319FC7694DA6007767E3CE521E5E7EA3A7926C1E782EBA132CAF03CD202D3` |
| 1.21.11 | `net/minecraft/world/attribute/EnvironmentAttributes.java` | `A35798B671DC8C2467C0B3278A697AF846B184567B8493FC0D9C4141085E2A46` |
| 1.21.11 | `net/minecraft/world/attribute/EnvironmentAttributeSystem.java` | `A8AD8C05FEC7E241E2BA63AD377A3D2E363BE4B9BD5E8482B3D32552A3F4C542` |
| 1.21.11 | `net/minecraft/world/attribute/EnvironmentAttributeMap.java` | `5A851614620201FD26C9C8DE8D08958F26BB6D0FD5719DB2F8865FBE0AE0A41E` |
| 1.21.11 | `net/minecraft/world/entity/Entity.java` | `32314478C6036FA9F3F3CC409C61C622EEFC5A282D60E1011A1A33CC29CF18A3` |
| 1.21.11 | `net/minecraft/client/multiplayer/ClientLevel.java` | `E76D09DE5ACAD450062D5ACB98E76FF032ECB179FEF0F7AF434E4148F84183A2` |
| 1.21.11 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `F1EBDB54D717266C894C87381C98BAD101980D252DE5BAE6CA55C37AECE1732E` |
| 1.21.11 | `net/minecraft/core/RegistrySynchronization.java` | `7AC3BBED7C3F56BD13C9C8339F95DFC2AF9F879FD5EA9C50584848D8D653DC08` |
| 1.21.11 | `net/minecraft/resources/RegistryDataLoader.java` | `5A3B445E3747FDFE7D70BFE4CAD92DCA8F40C0EC802FB1E4244BF5B17AB151AE` |
| 1.21.11 | `net/minecraft/server/network/ServerConfigurationPacketListenerImpl.java` | `FEE1A112B534FE5F5CEB6B0B7F5B55D4A7B82DD03324E68E1FD1C10D173B7E38` |
| 1.21.11 | `net/minecraft/util/datafix/DataFixers.java` | `DEFD1869015EE086E33E70D214D0E396F948CD1916EA6FC359ABD4CB7B3C5AD2` |

## Findings

### Legacy input and new representation

In 1.21.10 `DimensionType` is a record with `boolean ultraWarm`; its direct codec requires `ultrawarm` (`DimensionType.java:21-30, 50-57`). Built-in Nether registers that flag `true` (`DimensionTypes.java:35-52`). The same-era entity lava push reads `level().dimensionType().ultraWarm()` and chooses `0.007` versus `0.0023333333333333335` (`Entity.java:1486-1487`).

In 1.21.11, `DimensionType` has no `ultraWarm` component or `ultrawarm` codec field. Its codec instead accepts `attributes` as optional with `EnvironmentAttributeMap.EMPTY` (`DimensionType.java:31-50, 77-103`; `EnvironmentAttributeMap.EMPTY` is defined at `world/attribute/EnvironmentAttributeMap.java:22`). `FAST_LAVA` is a non-positional, syncable boolean with registry default `false` (`EnvironmentAttributes.java:126-128`). Missing map entry therefore resolves to that false default: the environment system returns the attribute default if there is no sampler (`EnvironmentAttributeSystem.java:97-102`), and missing map entries add no dimension layer (`:67-69, 134-149`).

No exact-source codec/datafix operation maps old `ultrawarm` to new `attributes[gameplay/fast_lava]`. In particular, the optional `attributes` default is empty, not inferred from a legacy field. For an old manually-authored dimension JSON, vanilla 1.21.11 source therefore provides no semantic carry-over from its `ultrawarm` value; authoring `FAST_LAVA=true` in `attributes` is an explicit data edit. The decompiled Minecraft source does not include Mojang codec-library implementation, so this report does not assert whether an otherwise unknown JSON key is ignored or rejected by that library. It does establish that `ultrawarm` is no longer part of the dimension type's declared record/codec.

### DataFixer boundary

Within both exact-version `net/minecraft/util/datafix/` trees, case-insensitive searches for `ultrawarm`, `fast_lava`, `EnvironmentAttributes`, `DimensionType`, and `dimension_type` returned no matches. I inspected `DataFixers.addFixers` in each `DataFixers.java`; its registered fixers contain no applicable conversion. This supports only the bounded result that the inspected vanilla DataFixer registrations and source keys contain no old-boolean-to-new-attribute migration. It does not establish behavior of resource parsing, an external world converter, or any uninspected migration path.

### Stock built-in Nether and client availability

In 1.21.11 the built-in Nether dimension explicitly sets `EnvironmentAttributes.FAST_LAVA` to `true` (`data/worldgen/DimensionTypes.java:57-85`); stock Overworld/End attribute maps do not set it. Thus the vanilla-built-in Nether keeps the era behavior by explicit built-in attribute data, independently of old save `ultrawarm` migration. For an authored dimension with no `FAST_LAVA` entry the effective value is false by the default path above; no assumption is made about unknown external raw resources.

The new current/push caller is `Entity.updateInWaterStateAndDoFluidPushing` (`Entity.java:1501-1506`), which queries `level.environmentAttributes().getDimensionValue(FAST_LAVA)` and selects the historical current scale; `Entity.isInLava` remains a fluid-height predicate (`:1604-1605`). `LocalPlayer` extends `AbstractClientPlayer`; no LocalPlayer-specific override of the lava-current query was found, so the shared `Entity` path applies. `ClientLevel` constructs its `EnvironmentAttributeSystem` with `addDefaultLayers(this)` and returns that system (`ClientLevel.java:224-235, 242-247, 618-619`); `Level` retains the dimension-type holder (`:908-914`).

For remote play, `RegistryDataLoader.SYNCHRONIZED_REGISTRIES` includes `Registries.DIMENSION_TYPE` with `DimensionType.NETWORK_CODEC` (`RegistryDataLoader.java:137-160`); the registry network codec filters the dimension `attributes` map to syncable attributes, and `FAST_LAVA` is marked syncable (`DimensionType.java:50, 95`; `EnvironmentAttributeMap.java:23-34`). `RegistrySynchronization` packs each synchronized registry (`:22-34, 36-62`); server configuration schedules registry synchronization from the server's layered registries (`ServerConfigurationPacketListenerImpl.java:90-95`), and `ClientPacketListener` adopts the received frozen registry access (`:425-430`). The client level is built from that registry access (`ClientLevel.java:224`). So the built-in or server-defined FAST_LAVA value reaches the client dimension type through registry sync, rather than being recomputed from old ultrawarm state.

## Closed behavior and remaining blockers

- Closed for stock built-ins: old vanilla Nether's `ultrawarm=true` is replaced by explicit built-in Nether `FAST_LAVA=true`; the current query defaults false when the attribute is absent.
- Closed for source implementation: no 1.21.10→1.21.11 DataFixer migration from `ultrawarm` to `FAST_LAVA` was found; no dimension record/codec field preserves `ultrawarm` in 1.21.11.
- Provenance gate: closed for the inspected source files. Both ready markers bind the exact version/mapping and each of the 16 inspected file hashes matches its versioned source manifest; ready-marker manifest hashes match the actual manifest files.
- Deliberately unresolved: behavior of unknown `ultrawarm` JSON keys in Mojang's codec library; exact handling of arbitrary third-party raw dimension files/resources; external world-conversion tools. None can be claimed from this vanilla source set.

