# Independent audit verdict — ResetPos block-state key/provider inventory

## Verdict

**ACCEPT**, limited to the source identity, exact-key/provider joins, owner grouping, and state-source index of the audited inventory. This is not a shape-parity or ResetPos equivalence result.

## Immutable inputs checked

- Author coverage: `resetpos_block_state_residual_matrix/coverage.md` — SHA-256 `C4AC654CBBE8A4958CC2B1388F05A2CE44E2FD320395DB6F60D2EB332F8CC127`.
- Exact-key matrix: `resetpos-common-key-provider-matrix.csv` — SHA-256 `B7294302CD98BC923F543608EE9F5935A24A02033AC6033828C339DC702A2974`.
- Owner groups: `resetpos-provider-owner-groups.csv` — SHA-256 `E5BB405A1939D78218E6C28EC8D5B2E968DCDE672E6DD530E0B8817C3D0704A7`.
- State source index: `state-domain-source-index.csv` — SHA-256 `42C24D1FD02B63C02C4D3548DFB9D0533C92C3BD8553CE130C1E4E08D07A1023`.
- Ready endpoint manifests: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Registry crosswalk: `build/source-drafts/registry-collision-crosswalk.md` — SHA-256 `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`.

## Checks

Both ready markers identify the expected versions and match their canonical `ornithe-feather.sources.sha256` files. The manifest files hash to the stated endpoint identities.

The author matrix has 184 rows and 184 unique exact keys. Its keys exactly equal the 184 `EXACT_KEY_ONLY` rows in the pinned registry crosswalk. All seven dispatch fields per key (old ID, old registration class, old effective `addCollisions` owner, old effective `getCollisionShape` owner, target class, and target effective collision route, plus pair status) match the crosswalk; zero row/field mismatches were found.

The three query buckets contain 76 state-independent default-provider keys, 39 specialized target routes within the 115-key base-owner join, and 69 other shared-key routes; these sum to 184. The owner-group CSV has 57 rows and its `rowCount` values sum to 184.

The state-source index contains 203 unique release/class entries. Every indexed source file exists; all 203 indexed file SHA-256 values match both canonical bytes and the corresponding endpoint source-manifest entries. Every numeric state-definition/property anchor is within the referenced file's line count. This confirms source binding and anchor bounds, not that the referenced declaration exhaustively describes inherited property values.

## Limits preserved

The packet correctly remains an inventory, not a completed per-state or context matrix. It leaves inherited state-property ranges, legal Cartesian products, shape intersections, world/neighbor/block-entity context, and query-result equivalence open. The 76 default-provider dispositions are reusable only within their accepted state-independent unit-cube provider claim. No movement difference or pair freeze is inferred.
