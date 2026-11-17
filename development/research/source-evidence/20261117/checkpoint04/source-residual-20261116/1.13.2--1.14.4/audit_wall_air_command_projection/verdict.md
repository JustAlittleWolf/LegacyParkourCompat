# Independent source audit: air and command block wall projection

Verdict: **ACCEPT**, bounded to the six named wall-neighbor predicate rows.

Input report: `build/source-residual-20261116/1.13.2--1.14.4/wall_air_command_projection/report.md`, SHA-256 `c853f644ac948b97ae17b0c614f764eddd9e2ff43d2f4d854960436f600a3492`.

## Identity and registry selection

The ready markers and source-manifest files identify the pinned endpoints as 1.13.2 Feather Gen2 build 2 (`2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`) and 1.14.4 Feather Gen2 build 2 (`717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`). I verified all ten source-file hashes listed in the report against the endpoint source sidecars and canonical source bytes. I also verified the unexamined crosswalk digest `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366` and confirmed it contains exactly the six reported keys with matching AirBlock/CommandBlock owner rows.

The raw 1.14.4 `walls.json` payload is bound by the accepted resource extraction/audit (`wall_resource_membership/extraction-record.md` SHA-256 `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; `wall_resource_membership_audit/verdict-r2.md` SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`). Its exact payload SHA-256 is `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; the listed 14 values do not include any of the six keys. The fence-gate alternative is inapplicable to these AirBlock and CommandBlock registrations.

## Predicate verification

- Old BlockState delegates `getFaceShape` to the block. AirBlock returns `UNDEFINED`; generic Block returns `SOLID`. CommandBlock inherits the latter through BlockWithBlockEntity, which declares no face/collision-shape override. Its state builder adds six-way FACING and Boolean CONDITIONAL, giving 12 states per command key; both properties leave the inherited face shape unchanged.
- Old WallBlock accepts SOLID unless the block is in its explicit attachment/connection exception route. The three command keys are not in that set. AirBlock's `UNDEFINED` never enters the SOLID rule.
- New BlockState's face-solid query uses the per-state cached result or `Block.isFaceSolid`; cache construction for the relevant shapes invokes `Block.isFaceSolid`. That method tests collision-shape face fullness, excepting LEAVES. Target AirBlock returns an empty outline and all air registrations have noCollision. The target command registrations omit noCollision; collision defaults true and inherited Block shape is a full cube. None of the six keys is in the exact WALLS payload or the attachment exception set.

Thus the three air keys remain false in all four horizontal directions, and the three command keys remain true for all 12 registered states in all four directions. Counts are 12 air key/direction cases and 144 command key/state/direction cases. I found no source-evidence correction needed.

## Scope limit

This accepts only the six named provider/predicate rows. It makes no movement-trajectory claim and does not freeze either release pair. The historical r1 crosswalk TSV overwrite limitation remains attached to the r2 crosswalk correction; this audit uses the current r2 TSV only to select these six rows.
