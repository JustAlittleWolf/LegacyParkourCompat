# Independent source audit: wall crosswalk correction r3

**Verdict: ACCEPT — narrow metadata correction only.** This verdict covers the correction-r3 artifact set and its stated relationship to correction r2. It does not accept or close the wider wall crosswalk or 1.13.2–1.14.4 pair.

## Immutable inputs

The coverage artifact is named `coverage-r3-correction-r3.md` in the submitted directory (there is no file named `coverage.md`). Its bytes match the supplied SHA-256, and the submitted `SHA256SUMS.tsv` binds that exact filename and digest. The filename difference is recorded here; it does not make the input ambiguous.

| Input | SHA-256 |
|---|---|
| `wall_full_registry_owner_crosswalk_r3_correction_r3/coverage-r3-correction-r3.md` | `53d6c069b16676a20ca552618c475151e976ab9b3540e2caa9f35e296a1b086a` |
| `wall_full_registry_owner_crosswalk_r3_correction_r3/partial-row-closures-r3-correction-r3.tsv` | `d35e9e03dd92f29b3f0ec463ee447fbff6be1722febf288210748e986e33be70` |
| `wall_full_registry_owner_crosswalk_r3_correction_r3/remaining-unexamined-rows-r3-correction-r3.tsv` | `ebcc9aa9cd3a9b256a6588d62fb18707edc8f42357747df5ce340d06ef591e35` |
| `wall_full_registry_owner_crosswalk_r3_correction_r3/residual-dispositions-r3-correction-r3.tsv` | `1ffa701c110aa6897a1ba65d5e59ae83f0e8ee8918cf36772538202bad162704` |
| Bound correction-r2 independent verdict (`audit_wall_crosswalk_correction_r2/verdict.md`) | `bdfb1808ed8de808edfbd64cbbf024156653d10ec37d049258004da3a838d02a` |

I recomputed all listed hashes. The correction-r2 verdict is `REQUEST CHANGES` and limits the requested repair to the four slab state-domain metadata rows. Its bound correction-r2 artifacts also match the digests recorded in that verdict: coverage `e55899f68bdbe66155328ea97a19f20b0c0010728691bc42dc39d82dcf548246`, closure TSV `f50513c78620d10fc5327c2481371080f46407c37e6d7f023ceeb9d47b353fee`, open-row TSV `ebcc9aa9cd3a9b256a6588d62fb18707edc8f42357747df5ce340d06ef591e35`, and disposition TSV `1ffa701c110aa6897a1ba65d5e59ae83f0e8ee8918cf36772538202bad162704`.

## Correction scope check

I compared the correction-r3 closure TSV to the exact correction-r2 closure TSV by registry key and column. Both have 20 rows. There are exactly 12 changed cells, all confined to these four keys:

- `minecraft:brick_slab`
- `minecraft:purpur_slab`
- `minecraft:quartz_slab`
- `minecraft:stone_slab`

For each key, exactly these three fields change: old endpoint cardinality, target endpoint cardinality, and `registration_and_domain_basis`. Both endpoint cardinalities change from `HALF:2,WATERLOGGED:2; product=4` to `HALF:3,WATERLOGGED:2; product=6`. The basis field now explicitly binds `HALF={TOP,BOTTOM,DOUBLE} × WATERLOGGED={false,true}` and the exact registration row. This is the requested six-state domain. The remaining 16 closure rows are byte-for-byte equivalent at the parsed field level; no key or association changed.

The seven-row unexamined TSV and 361-row disposition TSV are byte-identical to correction r2. Their rows remain 354 `ACCEPTED_BOUNDED` plus 7 `UNEXAMINED_FOR_WALL_PREDICATE`. The seven open stair keys are exactly cobblestone, dark oak, dark prismarine, nether brick, prismarine brick, red sandstone, and stone brick stairs. With 215 accepted baseline keys and 20 partial closures, the reported total remains 215 + 354 + 20 + 7 = 596.

The correction-r3 report retains the historical r1 TSV overwrite/byte-diff limitation stated in correction r2. The correction is limited to crosswalk metadata; this audit did not redo source geometry/provider comparisons and does not infer pair completion.
