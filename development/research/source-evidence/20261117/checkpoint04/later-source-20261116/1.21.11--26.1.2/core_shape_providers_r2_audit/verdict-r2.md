# Independent audit: core shape providers r2

Verdict: **ACCEPT** for the r2 report SHA-256 `1DEA060FEEFE1812E68CC6D6A33D70EA32672948088B238BCDCE4B9123266393`.

## Review basis

The r1 report remains at `build/later-source-20261116/1.21.11--26.1.2/core_shape_providers/coverage.md`, SHA-256 `73684D58E3A36BF90457B784AC444C42F607FFB37865A430C052546B0E84309E`. Its independent source audit is `build/later-source-20261116/1.21.11--26.1.2/core_shape_providers_audit/verdict.md`, SHA-256 `DFD672756DA4D420964440C42A990F735EBDBF038C980034F2136EBF515E1241` (ACCEPT). That audit verified exact endpoint ready/provenance/source/artifact bindings and all 25 cited Java-file hash rows. R2 retains those Java observations unchanged and adds a bounded package-resource crosswalk.

The r2 resource references were independently rechecked:

- Input reference: `build/source-resource-inputs-20261117/movement-gates/1.21.11--26.1.2_snow_support_tags/input-reference.json`, SHA-256 `18B9A5DEB911956374F2D4086988E8DAF471F7AFEA8D96A62BB79EC1C67BAEDE`.
- Resource audit: `build/source-resource-inputs-20261117/movement-gates/1.21.11--26.1.2_snow_support_tags/audit/verdict.md`, SHA-256 `0EC3E96DCCD16FD30966FB512A0F6F739785BE50156CAFF8CDE6601E70C725C7` (ACCEPT).

The reference and audit bind four exact singular `data/minecraft/tags/block/` archive paths. The old and renamed negative tags each have 94 bytes, SHA-256 `282662C1139488DCC43E3BFE48A36A6C4A759E53CB6A4024454236B8F07384CB`, and values ice, packed ice, barrier. The old and renamed positive tags each have 97 bytes, SHA-256 `C7491AE1BAFAE440421E90A1387AC87F46F7E003F37315F4ACF5EA0D0EE8CD5A`, and values honey block, soul sand, mud. Corresponding payloads are byte-identical; all four entries have no `replace` field. The resource audit verified exact source constants/default namespace, endpoint ready/source-manifest/provenance bindings, artifact-manifest client SHA-256 pins, official metadata client SHA-1, archive entries, and extracted-file hashes.

## Limits

Acceptance covers the unchanged r1 source slice plus the added archive-membership crosswalk for these exact pinned client archives. Provenance sidecars do not record the client JAR hash or artifact path. This does not establish datapack overrides, reload state, world-effective membership, or ordinary player-world reachability. The report correctly leaves scaffolding support-state population/reachability and broader shape, tick, provider, pair, and runtime closure open. No pair freeze or runtime claim follows.
