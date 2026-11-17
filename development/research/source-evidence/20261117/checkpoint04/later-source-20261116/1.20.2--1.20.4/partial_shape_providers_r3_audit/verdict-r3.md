# Independent audit — 1.20.2 → 1.20.4 partial-shape providers r3

## Verdict

**ACCEPT** for `build/later-source-20261116/1.20.2--1.20.4/partial_shape_providers_revision_r3/coverage-r3.md`, SHA-256 `BB917FCA4F4344CD2181DBA39DB7569D2C5A2E798F4C70E5A2EB30A15E402403`.

## Audit scope and evidence

The prior r2 report is SHA-256 `1A4FFFC6F4CD68F7E176E0869DEBF9B386091EB4B416643A2D471B3E5E6B0189`; its independent ACCEPT verdict is `build/later-source-20261116/1.20.2--1.20.4/partial_shape_providers_r2_audit/verdict-r2.md`, SHA-256 `5A94E3A4D67FBB28A01F17275334E5A62DCE91E76FCD734DBA70D84DD451F96F`. That audit verified the endpoint identity bindings, source-file hashes, original provider comparisons and ladder collision correction. I reused those unchanged source findings.

I compared r2 with r3. The prior provider text is unchanged except for the expected refinement of the snow-tag limit: r3 distinguishes pinned archive membership from effective runtime membership after overrides or reloads. The new supplement binds the correct resource input reference, SHA-256 `505efc5f34050a4e1a45031eade9d54b1051ed2271839092c50216ab55da57f4`, and its independent ACCEPT audit, SHA-256 `DA29C1EA214430F9697C70037CE01DC02C8AE62BAECD632FD4336B27B1829B5E`. The input reference identifies the same r2 report and hash used to derive the exact resource allowlist.

The accepted resource audit verifies both Mojmap endpoint/source crosswalks, artifact pins and official metadata SHA-1 values, then checks only the four assigned archive entries. I confirmed the extracted files at both version paths are present with the reported sizes and hashes; each endpoint's paired payload hashes match:

- `data/minecraft/tags/blocks/snow_layer_cannot_survive_on.json`: 94 bytes, SHA-256 `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb`, values `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier`.
- `data/minecraft/tags/blocks/snow_layer_can_survive_on.json`: 97 bytes, SHA-256 `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a`, values `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud`.

The resource audit independently confirms these extracted bytes match the corresponding pinned client-archive entries, are byte-identical across releases, and have no `replace` field. The report accurately states the provenance limitation: source provenance binds the source publication but does not directly record the client JAR digest or artifact path. The stated artifact-pin and official-metadata checks are supported by the accepted resource audit.

## Limits

Acceptance covers the unchanged bounded provider comparisons and the added pinned-archive snow-tag membership evidence only. It does not establish data-pack overrides/reloads, effective membership in a runtime registry or world, ordinary state placement/reachability, player trajectory parity, remaining provider inventory, or pair-wide dependency closure. No pair freeze is implied.
