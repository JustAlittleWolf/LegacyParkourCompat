# Independent source audit: Coral block wall-neighbor predicate

Verdict: **ACCEPT**, bounded to the five specified CoralBlock registry keys and the wall-neighbor predicate at exact endpoints 1.13.2 and 1.14.4.

Reviewed packet `build/source-residual-20261116/1.13.2--1.14.4/wall_coral_cube_projection/report.md`, SHA-256 `a13e598b5f012e9c608665119a5193b70fbc1fe2e1f9b508f1263e691e1f19a8`.

## Identity and inventory verification

The 1.13.2 and 1.14.4 ready markers bind source-manifest SHA-256 values `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, matching the packet and local source-manifest files. The exact report SHA and the crosswalk TSV SHA `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366` match local bytes.

All report Java-file hashes match canonical source bytes and their endpoint manifests:

- 1.13.2 `Block.java` `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; `Blocks.java` `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`; `CoralBlock.java` `1da2beb5dba501839639d1a33b79f7f3cb5524865e5c9990a1690ce10c5322a2`; `WallBlock.java` `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`.
- 1.14.4 `Block.java` `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5`; `Blocks.java` `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8`; `CoralBlock.java` `817099b02117b7fc53695abae56dc7427b740dadd3a5bc41031b3e1201e1e61a`; `WallBlock.java` `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7`; `BlockTags.java` `dc59d07204b25e5d852d0384e849975367590a3cbc2b9a293396d0bf5637e52a`.

The crosswalk filter yields exactly five live `CoralBlock` keys (`tube`, `brain`, `bubble`, `fire`, `horn`); the five dead-coral-block keys are plain `Block` and outside this packet. CoralBlock declares no state-property additions, so each checked registration has only its singleton state.

## Predicate verification

In 1.13.2 `WallBlock.shouldConnectTo` takes the neighbor `BlockState.getFaceShape` and returns true for `FaceShape.SOLID` unless `isExceptionForConnection` applies, with a separate fence-gate pole route. `Block.getFaceShape` returns `SOLID`. The complete old placement/attachment exception lists do not include the five coral blocks. `CoralBlock` does not override face shape or shape, and each registered coral block has ordinary stone properties without `.noCollision()`. Thus the old wall-neighbor bit is true for all four horizontal directions.

In 1.14.4 the corresponding wall predicate is `!isExceptionForAttachment(block) && isFaceSolid(...) || wallTagOrFenceGate`. None of the five coral keys is an attachment exception or fence gate. The target registrations use ordinary stone properties without `.noCollision()`; `Block.Properties.hasCollision` defaults true, inherited `Block.getCollisionShape` delegates to the state's shape, and inherited `Block.getShape` is the full cube. Therefore `Block.isFaceSolid` returns true on each horizontal face. The exact extracted raw-client `data/minecraft/tags/blocks/walls.json` is hash-bound at `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; its 14 values exclude all five coral keys. This closes the separate tag branch for this scope.

Result: true→true for all five singleton keys and four horizontal directions at both endpoints. The author's bounded no-difference conclusion is supported.

## Limits

This accepts only the five coral-block wall-predicate rows. It makes no movement-trajectory, collision-consumer, all-provider, or pair-freeze claim. Preserve the historical crosswalk caveat: the previous r1 TSV was overwritten, so the r2 crosswalk correction audit could not byte-diff every unchanged r1 row. This review verified the five named source rows and does not repair that limitation.
