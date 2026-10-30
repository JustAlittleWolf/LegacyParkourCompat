# Independent review: 1.8.9 anvil ID 145 projection

Verdict: accept the valid-metadata serialized-state projection and its facing-only old bounds inputs. It does not close mutable-bounds lifecycle or movement parity.

Reviewed candidate artifacts:

- `.native-source/20261030/collision-pool/anvil/FINDING.md`, SHA-256 `AC737707AA9286D6C63AA333FADAAD7A58E613D98E5BFB4CF878191A88264F23`.
- `.native-source/20261030/collision-pool/anvil/anvil-projection.tsv`, SHA-256 `A7696B15AE104091F5F29CBF66D07EB24629905F05E1B51C279FAA8CBFADD263`.
- Source ledger: `source-hashes.sha256`, SHA-256 `06E8379F6F2295543F8D805562FFD0BBD4401D0DB62964A4CE25B0FA2055A6B9`; reusable-input ledger: `evidence-hashes.sha256`, SHA-256 `F48203927E12AE445FEA4742F04B10E2DA55DAE8661B0DF267261623788D68FC`.

All canonical Java hashes and all reused input hashes in the ledgers matched. The canonical provenance identities match 1.8.9 `ornithe-feather` / Feather Gen2 build 2 and 26.2 `unobfuscated`.

The old decoder computes `Direction.byIdHorizontal(m & 3)` followed by `(m & 15) >> 2`; IDs 145 registers the anvil block. Metadata 0–11 yield valid damage 0–2 and facing SOUTH/WEST/NORTH/EAST by low bits; 12–15 request damage 3, outside the declared 0–2 property domain. The valid rows match the exact legacy aliases and 26.2 `BlockStateData` outputs at indices 2320–2331, including facing and variant block key. The filled 2332–2335 rows are properly labeled fallback rather than valid source tuples. The section route indexes `(oldId << 4) | metadata` and calls `BlockStateData.getTag`; ID 145 is absent from both `FIX` and `VIRTUAL`. Later rename processing leaves the three output names unchanged.

The current registry and codec preserve the horizontal facing, default to NORTH if absent, and have no damage property; damage tier is encoded in the registered block key. The current provider selects an axis-based composed silhouette and does not depend on position/context, unlike the old provider's axis-based full-height box. The report correctly distinguishes a tuple conversion from bounds parity and excludes invalid metadata as an applicability witness.

No open serialized-tuple or source gate remains for valid old metadata. Exact intermediate patch boundaries, old shared mutable-bounds lifecycle/scheduling, and runtime parity remain open and were not independently assessed here.
