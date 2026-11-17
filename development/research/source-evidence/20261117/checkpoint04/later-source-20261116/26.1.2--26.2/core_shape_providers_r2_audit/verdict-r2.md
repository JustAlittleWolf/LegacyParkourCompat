# Independent audit — selected block shape providers r2, 26.1.2 → 26.2

## Verdict: ACCEPT

Reviewed immutable report: `build/later-source-20261116/26.1.2--26.2/core_shape_providers_revision_r2/coverage-r2.md`  
Report SHA-256: `5E666A2DFD21644CB323439E8984BB97A5D88D4A5C55C9B5FC2C53B5D0A02C74`

## Changed evidence and binding

The r2 payload is present at the stated path and its SHA-256 matches the requested report identity. The prior r1 report remains unchanged at SHA-256 `E18D57229563B8D1C40D5CAE28F4CBA04158D31AEA68EB5495DC6EC999E1F629`; its independent audit accepted the bounded selected-provider source claims and the same source identities/file rows. The r2 changes are confined to the snow-support tag discussion and the paired resource evidence/limits; the Java source inventory and bounded shape conclusions are retained.

The input reference at `build/source-resource-inputs-20261117/movement-gates/26.1.2--26.2_snow_support_tags/input-reference.json` hashes to `f5bbb93a10a0b7434ec62783aa9258e1d8ebcf536cec6f7bc147a0a5c475dd49`. It binds the unchanged r1 report and names the exact 26.1.2 and 26.2 source, ready, provenance, artifact-manifest, and metadata identities. The independent resource audit at `build/source-resource-inputs-20261117/movement-gates/26.1.2--26.2_snow_support_tags/audit/verdict.md` hashes to `BFE641E53EF552D1836418E8EF045F44EA0FEA5660BEEC312BAAAB4E5F43F3C2` and ACCEPTs the two exact singular-path entries per pinned client archive.

## Resource crosswalk verified

The report's paired payload table matches the audited input reference and verdict:

- `data/minecraft/tags/block/cannot_support_snow_layer.json`: 94 bytes, SHA-256 `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb`, values `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier`.
- `data/minecraft/tags/block/support_override_snow_layer.json`: 97 bytes, SHA-256 `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a`, values `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud`.

Both endpoint archives and extracted copies contain matching bytes for each paired entry, with no `replace` field. The input audit verifies each local JAR SHA-256 against the canonical artifact manifest pin and verifies its official metadata SHA-1. It also verifies `BlockTags` constants and default-namespace crosswalk. The report accurately notes that source provenance does not directly record the JAR digest/path.

## Limits

The added evidence closes only the two exact pinned-archive payloads and source-name crosswalk. It does not establish user/world data-pack overrides, reload state, effective-world membership, ordinary-state population/reachability, or pair-wide coverage. Those limits are stated in r2. No change is made to the previously audited selected shape-provider claims or their exclusions; this ACCEPT is only for the immutable r2 report and its added resource evidence.