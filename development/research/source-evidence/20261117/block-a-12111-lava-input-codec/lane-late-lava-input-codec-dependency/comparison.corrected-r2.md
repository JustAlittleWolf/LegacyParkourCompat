# DFU codec gate closure r2

Status: exact 1.21.11 DFU 9.0.19 artifact verified and bytecode inspection closes the unknown-legacy-key codec gate for the specified otherwise-valid JSON input.

## Verified first-party provenance and pins

- Downloaded official catalog URL: `https://piston-meta.mojang.com/mc/game/version_manifest_v2.json`; captured as `version_manifest_v2.json`, 279,726 bytes, SHA-256 `4be3f97bcbd4b803d5645eb5ac56aacb1b45a64d0493a5d45091c6fad1be9f41`.
- Catalog contained exactly one entry with ID `1.21.11`, type `release`, metadata URL `https://piston-meta.mojang.com/v1/packages/b50d9e4eb8449c3c960558628732ef911061b329/1.21.11.json`, SHA-1 `b50d9e4eb8449c3c960558628732ef911061b329`.
- Fetched exactly that listed metadata URL. `1.21.11.official.json` reports `id=1.21.11`; its SHA-1 is `b50d9e4eb8449c3c960558628732ef911061b329` as required by the catalog, and SHA-256 is `594226aace505267407318d1e994d49e99137efa80e68dffa02ff6ab977b501c`.
- The verified metadata contains exactly one descriptor `com.mojang:datafixerupper:9.0.19`: path `com/mojang/datafixerupper/9.0.19/datafixerupper-9.0.19.jar`, URL `https://libraries.minecraft.net/com/mojang/datafixerupper/9.0.19/datafixerupper-9.0.19.jar`, declared size `731440`, SHA-1 `4e91f9712fa1e83231d1501625381b0210a977da`.
- Fetched only that exact library ZIP into this scratch lane. Its measured size is `731440`; measured SHA-1 is `4e91f9712fa1e83231d1501625381b0210a977da`; measured SHA-256 is `9653a39cf5c4853cc98628d902dcb23ffeb2755122ecc563bde38c8f60c0b61e`. Both hashes and size match the verified release metadata and the repository-published artifact pin in `decompiled_minecraft/1.21.11/artifacts.sha256`.
- The repository artifact manifest SHA-256 is `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`. 1.21.11 source manifest SHA-256: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. 1.21.11 source provenance SHA-256: `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`.

## Bytecode path inspected

Inspected the verified ZIP's entries with `jar tf` and disassembled only selected classes with `javap -c -p`; no DFU code was executed.

For vanilla resource deserialization, the exact 1.21.11 `RegistryDataLoader` source creates `RegistryOps<JsonElement>` with `JsonOps.INSTANCE`; `RegistryOps` extends `DelegatingOps`, whose `compressMaps()` and `getMap()` delegate to the wrapped ops. Resource parsing calls the `Decoder.parse(ops, json)` supplied for dimension types, then `getOrThrow()` and registers the decoded object. This is direct codec parsing, not a DataFixer migration.

The exact DFU 9.0.19 bytecode establishes the relevant uncompressed path:

1. `com.mojang.serialization.JsonOps.<clinit>` constructs `INSTANCE` with boolean `false` and `COMPRESSED` with `true`; `JsonOps.compressMaps()` returns that stored flag. RegistryOps delegates this call, so `JsonOps.INSTANCE` takes the uncompressed branch.
2. `MapCodec$MapCodecCodec.decode` calls the map codec's `compressedDecode`. `MapDecoder.compressedDecode` checks `ops.compressMaps()`; for false it calls `ops.getMap(input)` and passes the resulting `MapLike` to that decoder.
3. `JsonOps.getMap(JsonElement)` rejects a non-`JsonObject`, otherwise wraps its `JsonObject` in `JsonOps$1`. `JsonOps$1.get(String)` calls `JsonObject.get(name)` for the requested name. Its `entries()` enumerates the object but the record decoder path does not call `entries()` to validate consumed keys.
4. `RecordCodecBuilder.create` calls `build(...).codec()`. `MapCodec.codec()` yields `MapCodec$MapCodecCodec`; `RecordCodecBuilder$2.decode` delegates to the grouped `MapDecoder`. Group composition in `RecordCodecBuilder$Instance$1` / `MapDecoder$5` invokes the component decoders. `FieldDecoder.decode` requests only its own declared field name with `MapLike.get(name)`, errors when that required field is missing, and parses the retrieved value otherwise. No inspected record/group step compares the input key set to the declared key set or rejects leftovers.

Therefore a top-level `"ultrawarm": true` key that is not present in 1.21.11's `DimensionType` codec is ignored by that decoder; it is not read, retained or mapped to another field. This conclusion is specifically for the ordinary `JsonOps.INSTANCE` object decode path; the exact `JsonOps.COMPRESSED` path is not implicated.

For absent attributes, the exact Minecraft 1.21.11 codec calls `EnvironmentAttributeMap.CODEC.optionalFieldOf("attributes", EnvironmentAttributeMap.EMPTY)`. In the exact DFU bytecode, `Codec.optionalFieldOf(name, default)` creates an `OptionalFieldCodec` then `xmap`s it through `Optional.orElse(default)`; `OptionalFieldCodec.decode` obtains `MapLike.get(name)` and returns `Optional.empty()` successfully when absent. Thus the absent member becomes `EnvironmentAttributeMap.EMPTY`. `EnvironmentAttributes.FAST_LAVA` is Boolean with registered default `false`; with no FAST_LAVA override its absent/default value is false. This says nothing about unrelated builtin dimension definitions or map existence.

## Exact conditional decode conclusion

For an otherwise valid dimension-type JSON object that supplies every required 1.21.11 input (and satisfies its range/constructor constraints), carries legacy `"ultrawarm": true`, and omits `attributes`, 1.21.11 vanilla direct JSON decoding succeeds: the unknown raw key is ignored, the missing optional attributes field defaults to `EnvironmentAttributeMap.EMPTY`, and absent FAST_LAVA resolves to false. Missing any required 1.21.11 field remains a decode error; malformed/non-object input also fails through its relevant JSON/codec gate. This does not establish a match to any target-map representation or automatic migration; direct registry parsing shown here does not invoke DataFixer migration.

## Evidence scope and digest

- Exact library: `datafixerupper-9.0.19.jar`, SHA-1 `4e91f9712fa1e83231d1501625381b0210a977da`, SHA-256 `9653a39cf5c4853cc98628d902dcb23ffeb2755122ecc563bde38c8f60c0b61e`, 731440 bytes.
- Official catalog capture SHA-256: `4be3f97bcbd4b803d5645eb5ac56aacb1b45a64d0493a5d45091c6fad1be9f41`.
- Verified exact 1.21.11 metadata capture SHA-256: `594226aace505267407318d1e994d49e99137efa80e68dffa02ff6ab977b501c`; its catalog-verified SHA-1 is `b50d9e4eb8449c3c960558628732ef911061b329`.
- Relevant exact Minecraft sources: `net/minecraft/resources/RegistryDataLoader.java`, `RegistryOps.java`, `DelegatingOps.java`, `net/minecraft/world/level/dimension/DimensionType.java`, `net/minecraft/world/attribute/EnvironmentAttributeMap.java`, `EnvironmentAttributes.java`, and `EnvironmentAttributeSystem.java` in 1.21.11 Mojmap; the prior `README.md` records individual source-file hashes and the 1.21.10 comparison.
- This file is additive; `README.md` and `comparison.corrected-r1.md` remain unchanged. All catalog, version metadata and library files are retained inside the assigned scratch directory. No product/source writes, builds, tests, game/runtime launches, or library code execution were performed.