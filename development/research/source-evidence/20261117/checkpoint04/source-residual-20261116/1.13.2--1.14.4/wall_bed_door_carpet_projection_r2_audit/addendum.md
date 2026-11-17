# R2 binding addendum: wall bed/door/carpet projection

## Immutable identities

- Corrected report: `wall_bed_door_carpet_projection_r2/report.md`, SHA-256 `767d4b665e5283c6818bdabb2d6a201f21ff0931e232056ea216cc4cc1c2ba59` (recomputed; matches assignment).
- Prior independent verdict: `wall_bed_door_carpet_projection_audit/verdict.md`, SHA-256 `838280e824fa3c089046646196c0c9db2dd850e7e758b2f5762a6fe617348ddd` (recomputed).
- Door-provider reuse record: `door_shape_provider/coverage.md`, SHA-256 `18ba45c9b4c44e258cd044bab46cb8aab3a43383649c7575162a20084aea0df5` (recomputed; matches the corrected report citation).

## Bounded correction check

A line diff against the prior report (`wall_bed_door_carpet_projection/report.md`) shows exactly two changed paragraphs and no other changed lines:

1. The old 1.13.2 exception paragraph now identifies the full chain: `Block.isExceptionForPlacement` excludes piston, sticky piston and piston head; `WallBlock.isExceptionForConnection` additionally excludes barrier, melon block, pumpkin, carved pumpkin, lit pumpkin, frosted ice and TNT. This matches the prior audit's required correction. The audit states those added exceptions do not include beds, doors or carpets, so the assigned old horizontal bits and accepted projection results are unaffected.
2. The door-provider reuse citation now points to `door_shape_provider/coverage.md` with the exact hash verified above, replacing the stale `door_shape_provider/report.md` reference. The prior audit had noted that the latter path was absent and treated that historical reference as unverified.

## Disposition

The prior verdict accepted the assigned face-projection results, door direction table, key/state domains and bounded conclusion, subject only to the exception-chain correction and repair of the stale door-provider citation. Both requested textual changes are present in the corrected report, whose exact hash is bound above. Reuse that prior technical acceptance for the unchanged claims; this addendum does not perform a new source comparison or extend acceptance beyond the reviewed scope.
