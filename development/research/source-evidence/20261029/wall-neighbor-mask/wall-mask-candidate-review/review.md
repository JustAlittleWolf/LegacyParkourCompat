# Wall neighbor mask candidate review

Verdict: REVISE

Candidate repository: `.native-candidates/wall-neighbor-mask-20261029`
Candidate commit: `e7676634c33ee4825be1e400cca8e254fcfaa49e`

## Bound inputs

| Input | SHA-256 |
| --- | --- |
| `development/research/findings/1.11.2--1.12.2/wall-neighbor-connectivity.md` | `00ace08a3db2cf0c0cce2db7e5e193690817367e611781b704776e3fe91fc651` |
| `.native-source/20261029/wall-state-selector-closure/supplement-r2.md` | `3fc036aafd62b848e4f4cc3e60d85c26ad1142fff5b7296a5706eafefcdb9406` |
| `.native-source/20261029/wall-state-selector-closure/supplement-r3.md` | `7bad1170df6e31864064e7f5e3b3d60d001cee31a973b48e19feee5302dd418d` |

## Review

The implementation matches the source-closed selector in r2/r3. It admits only the two mapped wall keys with `waterlogged=false`; each cardinal neighbor must be air or one of the 14 mapped stair keys with `waterlogged=false`. The admitted old 1.11.2 predicate result is false for air and stairs, so the returned north/east/south/west mask is complete and follows the source direction order. It does not read or convert the current wall side/UP values. Unmapped targets, waterlogged stairs, and other neighbor states return `Optional.empty()`, allowing the caller to retain whole-shape native fallback.

The change is annotated `V1_11_2`, has a public no-argument constructor, and implements one topic hook. The candidate resolver selects this version for profiles at or before 1.11.2 when it is the closest registered change, and excludes it for 1.12 and later. This agrees with the accepted finding's `(1.11.2, 1.12]` observed transition. No extra gate on current target side properties or `UP` is introduced.

One documentation correction is required before acceptance. The `WallNeighborMask` source comment cites 1.11.2/1.12.2 Ornithe Feather methods but describes the current selector gates only as “26.2 WallBlock” and “BlockStateData mappings.” The candidate instructions require exact versions, mappings, classes, and members in source comments. Add the 26.2 unobfuscated mapping family and the exact members used to justify the current wall/stair waterlogging defaults and legacy target mappings (as identified in the supplied supplements), while keeping the Java comment free of hashes.

Scope: candidate code and neutral resolver/annotation infrastructure only; accepted wall finding and the supplied r2/r3 supplements. No product implementation, product history, backlog, builds, tests, or runtime state were inspected.
