# Independent audit verdict: planks/log collision keys

**Verdict: ACCEPT the bounded collision/provider claims; KEEP the state-migration gate open.**

## Bound identity

Reviewed `build/source-residual-20261116/1.12.2--1.13.2/planks_logs_collision_keys/report.md`, whose SHA-256 is `61511ba8ac01a9e3afad8a7e61fea8195825b3065f0f8234a91de24c1eb0d2c6` (verified from its bytes). The exact local Feather Gen2 manifest hashes also match both the report and ready markers:

- 1.12.2: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`
- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`

All eleven consulted Java source-file hashes printed in the report were independently recomputed and match. The only evidence-table correction is clerical: its “Ready marker SHA-256” cells contain marker paths, not SHA-256 values. The actual marker hashes are `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` (1.12.2) and `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` (1.13.2). This does not affect the source-manifest identities or reviewed behavior claims.

## Claims accepted within stated scope

The 1.12.2 registration and state-domain claims are supported: `Block.java` registers ID 5 as `planks` / `PlanksBlock` and ID 17 as `log` / `LogBlock`; `PlanksBlock.Variant` has six legal species values. `LogBlock` accepts the four species IDs and reads metadata axis bits 0, 4, 8, and 12 into Y, X, Z, and NONE. The inspected source thus establishes the legacy `NONE` state for metadata 12–15.

The target key and provider claims are also supported. 1.13.2 registers each scoped plank key as an ordinary `Block` and each scoped log key as `AbstractLogBlock`; planks have no species state property, and `AxisBlock` adds `Properties.AXIS` (X/Y/Z) with default Y. Neither relevant log class overrides the inherited collision or face provider. In both versions the scoped blocks therefore inherit a full unit-cube shape and `FaceShape.SOLID`. For 1.13.2, collision uses the state shape when `hasCollision` is true; that flag defaults true, and the scoped registrations do not call `.noCollision()`. Consequently, for ordinary block-state collision and face queries over these registered states, these aliases establish no collision-geometry/provider difference. This acceptance does not extend to `log2` / ID 162, other aliases, or pair-wide movement coverage.

## State-migration gate remains open

The report correctly does not infer the old-to-new state fixer. The inspected 1.13.2 block registrations and state definitions prove available target keys/properties, but not the source old-ID/metadata-to-target-key/property table. `DataFixes.java` delegates through fixer/schema classes whose per-block mapping is not shown in the inspected source; the local `util/datafix` tree contains no decompiled `C_*.java` implementation. In particular, no evidence here establishes the destination of old log `axis=NONE`, nor the serialized old planks/log variant names. The candidate `_wood` registrations do not close this gap. Keep this as an explicit open gate and do not fill in a mapping from inferred naming or expected behavior.
