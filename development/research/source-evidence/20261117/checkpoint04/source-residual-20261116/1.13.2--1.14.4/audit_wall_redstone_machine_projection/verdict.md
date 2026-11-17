# Independent source audit: dispenser/dropper/spawner/observer wall predicate

Verdict: **ACCEPT**, bounded to the four registered neighbor-predicate rows in the assigned immutable report. No movement trajectory, general provider parity, or pair freeze is claimed.

## Bound identity

Author report: `build/source-residual-20261116/1.13.2--1.14.4/wall_redstone_machine_projection/report.md`  
SHA-256: `5e599d61b35324de8ddd4aa0bc138256036eaadaa0c6711fa00b945bb5795fa3`

Pinned source sets are the exact 1.13.2 and 1.14.4 Ornithe Feather Gen2 build 2 ready sets. Their `ornithe-feather.sources.sha256` hashes match the corresponding ready-marker `sourceManifestSha256` values: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The author report's cited canonical Java hashes were independently recalculated and matched both the files and their exact source-manifest entries.

## Audit findings

The four keys are `minecraft:dispenser`, `minecraft:dropper`, `minecraft:spawner`, and `minecraft:observer`. The crosswalk TSV (SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`) confirms those exact common keys/classes and state cardinalities. `spawner` is the actual registered identifier; there is no `mob_spawner` key.

At 1.13.2, `BlockState.getFaceShape` delegates to `Block.getFaceShape`, whose default is `FaceShape.SOLID`. `WallBlock.shouldConnectTo` accepts this face for each row: none is in the connection-exception list or is a fence gate. At 1.14.4, the wall calls the state face-solid query and accepts it unless the block is an attachment exception; the collision face is full because the base block has collision enabled by default and the base shape is a full cube. The four exact registrations do not call `.noCollision()`. `DropperBlock` inherits `DispenserBlock`; the spawner and dispenser derive from `BlockWithBlockEntity`, which delegates to `Block`; `ObserverBlock` inherits the base shape methods through `FacingBlock`. None is in the attachment exclusions or in the audited `WALLS` tag payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`, independently audited extraction SHA `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`, verdict SHA `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`).

The 37 registered key/state pairs are 12 dispenser, 12 dropper, 1 spawner, and 12 observer. The six-valued `FACING` property and two-valued boolean properties are source-bound in the report. Each queried horizontal face is connected at both endpoints: 148 true-to-true comparisons. No difference is present in this bounded slice.

## Scope limits

This accepts only the old/new wall-neighbor predicate projection for these four keys and their declared states. It does not establish their other collision consumers/callbacks or complete either pair's inventories and dependencies. The historical r1 crosswalk overwrite limitation remains applicable to the broader crosswalk; this verdict accepts only the exact rows and evidence bound above.
