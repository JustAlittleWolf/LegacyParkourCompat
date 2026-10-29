# Independent review: DDA writer coverage matrix

## Verdict

**REVISE for one evidence-binding omission.** The source classifications, provider inventory table, cache-consumer subset, and four stated per-ID gaps are supported within the report's bounded source scope. The 147 entries in `artifact-hashes.json` all rehash correctly. However, the matrix uses the accepted default-provider closure as an adapter gate without binding its independent acceptance audit (`dda-default-provider-review/source-audit.md`) or that audit's hash manifest. The matrix therefore does not fully bind one acceptance status it relies on. Add an immutable correction/rebind or include those exact artifact hashes in a new matrix revision. This is a traceability issue; it does not change the matrix's source conclusions.

## Bound inputs

- Matrix report: `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/report.md`
  - SHA-256: `F9905E2F93B0C53C330EDCEA37742576BF370F00F4452CA1F0FDAC1CDD1507C2`
- Artifact manifest: `.native-source/20261028/collision-next/dda-writer-coverage-matrix-audit/artifact-hashes.json`
  - SHA-256: `F67C8D984A33CCB233F43CCF748EAA2A4604EAF9955CE6295482491EC33EDCCB`
- Sidecar `report.sha256` contains the same report and artifact-manifest digests.
- The closure referenced in the matrix: `.native-source/20261028/collision-next/dda-default-provider-shape-closure/closure.md`
  - SHA-256: `8FFC77A0C443F7B19AB5B49914CCF65E839F38CF75527055608866A7D0E8AEC2`
- Omitted acceptance audit: `.native-source/20261028/collision-next/dda-default-provider-review/source-audit.md`
  - SHA-256: `E86D90F88A7491DAE5296ACE2BD913127350346D8B9E98F7B25BDE9367C3D0B1`
- Omitted audit hash manifest: `.native-source/20261028/collision-next/dda-default-provider-review/hash-manifest.md`
  - SHA-256: `87464E3B4BA42D0B3E414786FA743C812B886D97F50237B97852EFA10A9213D2`

The omitted audit independently accepts the closure only for the default provider algorithm and virtual-call contract, while leaving complete ID/state conversion, constructors, writers, neighbor context, lifecycle, and parity open. The matrix uses precisely those remaining gates. The accepted audit's bounded scope is consistent with the matrix; it is missing only from the matrix's hash-bound artifact set.

## Verification findings

- Recomputed all 147 `artifact-hashes.json` entries from current files: **zero missing paths or digest mismatches**.
- Recomputed the report SHA-256 and the `report.sha256` sidecar's two expected values: both match the current report and artifact manifest.
- Matched the 36 inventoried writer/provider classes: 35 `updateShape` writer classes plus the `TorchBlock` direct ray-bound writer. The table includes all of them, plus its supplemental Redstone Wire row.
- Confirmed the exact outstanding registrations in canonical 1.8.9 `Block.java`: ID 70 `stone_pressure_plate`, ID 72 `wooden_pressure_plate`, ID 101 `iron_bars`, ID 146 `trapped_chest`, and ID 180 `red_sandstone_stairs`. They are correctly separated from the closed families: weighted plates 147/148; panes 102/160; chest 54; and the other listed stairs.
- The cache-consumer subset is correctly limited to Anvil 145 and Chest 54 by the accepted camera-tick evidence. The matrix does not extend Chest 54's result to trapped chest 146.
- Stem exclusion is correct: `StemBlock extends PlantBlock`, and old `PlantBlock.getCollisionShape` returns null. The ray-bound writer is therefore not an accepted ordinary movement-cache consumer.
- The matrix keeps ray-rejected Air/Fire/Liquid, null-collision providers, refresh-before-clip providers, and the MovingBlock ray-null override separate. It does not claim whole-operation parity or a complete current-state adapter.

## Limits

No product source/history, builds, tests, games, or runtime were inspected. This review accepts the matrix's bounded source classifications subject to the single missing hash binding above; it does not accept whole DDA parity or implementation readiness.
