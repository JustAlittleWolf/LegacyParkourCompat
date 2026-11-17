# Pinned snow-support tag resource audit

Verdict: **ACCEPT**

Reviewed input reference: `build/source-resource-inputs-20261117/movement-gates/26.1.2--26.2_snow_support_tags/input-reference.json`, SHA-256 `f5bbb93a10a0b7434ec62783aa9258e1d8ebcf536cec6f7bc147a0a5c475dd49`.

## Identity and provenance checks

The paired endpoints are ready `unobfuscated` source publications. Their `versionId`, mapping, and ready status match 26.1.2 and 26.2. The source-manifest digests in the ready markers match the cited source manifests; the version-root provenance records identify the same release and `unobfuscated` namespace. The `BlockTags.java` source hashes match both local source files and their manifest rows. Both files declare `CANNOT_SUPPORT_SNOW_LAYER = create("cannot_support_snow_layer")` and `SUPPORT_OVERRIDE_SNOW_LAYER = create("support_override_snow_layer")`; `create` uses `Identifier.withDefaultNamespace`, so these are the stated `minecraft:` tag identifiers.

Each local `artifacts.sha256` matches its recorded manifest digest and contains the stated `client.jar` SHA-256 pin. The inspected client JARs match those pins. The referenced official metadata files match their recorded SHA-256 values, identify the stated release, and declare client artifact URLs and SHA-1 values matching SHA-1 computed from each JAR. The 26.2 JAR is the local Fabric Loom cache copy named in the reference. Neither provenance sidecar directly records the client JAR digest or artifact path; the reference states this limit accurately.

## Allowlisted entries

For each endpoint, the two and only two listed entries were opened directly from the pinned archive and compared with the isolated extracted copies:

| Entry | Size | SHA-256 | Values | Result |
| --- | ---: | --- | --- | --- |
| `data/minecraft/tags/block/cannot_support_snow_layer.json` | 94 | `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb` | `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier` | Both archives and output copies match byte-for-byte; no `replace` field |
| `data/minecraft/tags/block/support_override_snow_layer.json` | 97 | `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a` | `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud` | Both archives and output copies match byte-for-byte; no `replace` field |

Both paths are the singular `tags/block/` form. Archive entry sizes, output sizes, computed SHA-256 values, parsed values, and absence of `replace` agree with the reference. Each counterpart has identical bytes across 26.1.2 and 26.2. The extraction method and paths are restricted to these two named entries per JAR.

## Limits

This accepts the stated pinned-archive payload membership and source crosswalk only. It does not establish user/world datapack overrides, reload state, effective runtime membership, ordinary-world reachability, or pair-wide movement coverage.
