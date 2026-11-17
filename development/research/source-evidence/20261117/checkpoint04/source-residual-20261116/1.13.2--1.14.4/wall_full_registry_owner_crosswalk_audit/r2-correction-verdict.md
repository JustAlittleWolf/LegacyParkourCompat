# R2 correction audit: wall registration crosswalk

## Verdict: ACCEPT, bounded to the requested correction

All 11 rows identified in the prior audit are now `UNEXAMINED_FOR_WALL_PREDICATE` and have no bounded packet scope. The status partition is 215 covered, 20 partial, and 361 unexamined, across 596 unique keys. The residual TSV contains exactly the 361 unexamined keys, with no omissions or extras.

The exact corrected key set is six leaves (`acacia_leaves`, `birch_leaves`, `dark_oak_leaves`, `jungle_leaves`, `oak_leaves`, `spruce_leaves`) plus `barrier`, `carved_pumpkin`, `jack_o_lantern`, `melon`, and `pumpkin` (all under `minecraft:`). The prior unsupported `wall_non_gate_neighbors` partial assignments are gone.

## R2 artifact identities

| Artifact | SHA-256 |
|---|---|
| `wall_full_registry_owner_crosswalk/coverage.md` | `a8285ea8c199dd0261e25202214ea401fdf1f54af4db836917c6e9cd5fe369d0` |
| `registered-key-dispatch-crosswalk.tsv` | `7b8d10690d85824b2605c701651b516fe82d654908e7d22c6e62eca08d30bdff` |
| `unexamined-wall-projection-keys.tsv` | `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366` |

The corrected report retains the previously audited endpoint manifest identities, registration-source hashes, wall dispatch-source hashes, and target `walls`/`leaves` resource hashes. The earlier full source-identity audit remains the basis for those per-row fields; this pass was limited to the requested status correction and did not repeat registration, inheritance, or source-hash verification. The prior r1 TSV was overwritten, so this pass cannot make a byte-for-byte comparison of every non-status row against that unavailable copy.

The corrected inventory remains source-only. It does not imply wall-predicate parity or geometry results for unexamined keys, expand nested tags, resolve old-side tag membership, or establish full pair closure. Its report continues to leave 91 state-property products unresolved and explicitly retains the alias/class-expression and resource/provider limitations and open pair-wide gates.

Correction audit procedure: `wall_full_registry_owner_crosswalk_audit/audit_r2_correction.py`.
