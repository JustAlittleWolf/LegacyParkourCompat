# State/property residual disposition: 1.20.4 → 1.20.5 (1.20.6 context)

## Inputs and method

- Exact sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.4,1.20.5,1.20.6}/mojmap` (Mojmap).
- Inventory: `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv`.
- Owned residual: 25 exact-case paths: `StateDefinition`, `StateHolder`, `BlockStateProperties`, `TreeGrower`, and 21 old enum/property value classes. Every owned path has a row in `dispositions.csv`.
- SHA-256 of each source file was recomputed and matches all three inventory hashes for all 25 paths. All 25 1.20.5 and 1.20.6 file hashes are identical; see `hash-validation.csv`.

## Findings

No concrete old-era player movement difference was found in this residual; no finding drafted.

`StateDefinition` switches generated per-state property maps from Guava `ImmutableMap` to fastutil `Reference2ObjectArrayMap`. Its sorted property names, possible-value iteration, per-state insertion order, and state enumeration loop remain the same. `StateHolder` uses that map for backing values and neighbor-map keys, widens `getValues()` from `ImmutableMap` to `Map`, and adjusts the property codec. The directly movement-consumed operations (`hasProperty`, `getValue`, `getOptionalValue`, `setValue`, `trySetValue`, neighbor population) keep the same lookup, cast, comparison and transition behavior. For vanilla `Property` keys, reference-key semantics match the old map's key equality because `Property` retains object identity equality. The state values and neighbor assignments are generated from the same property/value pairs. The vanilla `getValues()` consumers found in 1.20.5 read or iterate the map; no vanilla write consumer was found.

`BlockStateProperties` adds `VAULT_STATE` and `OMINOUS` for the new vault block mechanics. Existing property definitions remain. The 21 enum/property value classes retain their constant identities, serialized names, ordering, and associated values; the source diffs are constructor-parameter `final` annotations only, except `BlockSetType` and `WoodType`, whose codec helper calls also change. Those codec substitutions do not change the old-era enum values or movement predicates. `TreeGrower` only changes the codec helper used to resolve grower names; that is not a movement-state change.

## Coverage and dependencies

The residual's requested property identity and state-construction/lookup path is closed for this bounded slice. Relevant reachable use was checked in the exact 1.20.4/1.20.5 source around property state reads/writes and `StateHolder.getValues()` consumers; this is not a full tick-graph or pair audit. Unexamined dependencies remain covered by other pair lanes. This report does not claim pair freeze or independent audit.

### Key source anchors

- `net/minecraft/world/level/block/state/StateDefinition.java`: constructor state enumeration and `Factory` signature (1.20.4 lines 29–61, 157–159; 1.20.5 corresponding section).
- `net/minecraft/world/level/block/state/StateHolder.java`: backing map and methods noted above (1.20.4 lines 35–174; 1.20.5 corresponding section).
- `net/minecraft/world/level/block/state/properties/BlockStateProperties.java`: existing fields plus two additions (1.20.4 full field list; 1.20.5 trailing declarations).
- Full per-path disposition and evidence labels: `dispositions.csv`.
