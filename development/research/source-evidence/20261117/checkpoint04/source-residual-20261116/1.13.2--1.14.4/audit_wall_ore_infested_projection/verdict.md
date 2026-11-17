# Independent verdict: OreBlock and InfestedBlock wall-face projection

Verdict: ACCEPT, bounded to the 13 registry-key wall-neighbor predicate rows in the reviewed packet.

Reviewed report: `build/source-residual-20261116/1.13.2--1.14.4/wall_ore_infested_projection/report.md`, SHA-256 `62c11dd3c8c92b4f96ad8c9b53fa0cbe140d511cc8a8cf4df2b7e0bd1c4ec9d7`.

I independently checked both ready source identities: 1.13.2 source manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, and 1.14.4 source manifest `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Every cited Java file digest in the report matches both the canonical file and its version source manifest. The cited unexamined TSV digest is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; filtering its exact rows yields seven OreBlock and six InfestedBlock keys, all singleton state products.

The registrations at both endpoints instantiate those classes without `.noCollision()`. OreBlock and InfestedBlock inherit Block's shape and face methods and add no state properties. For the old predicate, base `Block.getFaceShape` returns SOLID, while neither the attachment exception list nor WallBlock's additional exception keys match these 13 blocks; `WallBlock.shouldConnectTo` therefore returns true for each horizontal neighbor face. For the target predicate, default `hasCollision` is true, default Block collision shape is the registered state's full-block shape, and `Block.isFaceSolid` accepts that full face. Ore/infested keys are neither attachment exceptions, WALLS members nor fence gates. The hashed WALLS and LEAVES payloads match their report hashes and contain none of these keys.

Conclusion: no difference in these 13 keys' horizontal wall-connection bits (true at both endpoints). This acceptance covers only the bounded predicate projection. It does not establish player movement impact, other block/state providers, wall geometry parity or pair completion. No correction addendum is needed.
