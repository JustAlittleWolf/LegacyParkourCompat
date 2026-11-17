# Independent audit verdict: wall container face projection

Verdict: **ACCEPT**

Audited report: `build/source-residual-20261116/1.13.2--1.14.4/wall_container_face_projection/report.md`

Report SHA-256: `71d4792dba638747440a1ded871069fbb4d635bcf666da2af061627655e362bc`

No corrections required for the bounded nine-key claim.

## Evidence identity and hashes

The exact Feather Gen2 source-manifest hashes match both the assignment and each `ready.json` record:

- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- 1.14.4: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`

I recomputed the report's ten listed source-file hashes on each side (`Block`, `WallBlock`, `Blocks`, `ChestBlock`, `TrappedChestBlock`, `EnderChestBlock`, `HopperBlock`, `AnvilBlock`, `DaylightDetectorBlock`, and `EnchantingTableBlock`). All 20 match both the report and their respective manifest entries. The trapped-chest source bytes are identical across the endpoints, as reported.

## Predicate and state audit

For 1.13.2, the six relevant old `getFaceShape` implementations return `UNDEFINED` on every horizontal face: chest/trapped chest, ender chest, hopper, anvil, daylight detector, and enchanting table. Hopper's `BOWL` and the last two blocks' `SOLID` are only for UP or DOWN, not a queried horizontal side. `WallBlock.shouldConnectTo` therefore returns false through the generic solid-face arm. The explicit old connection-exception predicate is false for all nine keys (including the three anvil registry aliases); the separate pole/fence-gate arm is also false. The conclusion does not rely on exception status because `UNDEFINED` cannot satisfy that arm.

For 1.14.4, the wall's generic arm receives the face-full result from `Block.isFaceSolid` and separately checks wall-tag/fence-gate eligibility. None of these registered blocks is a wall or fence gate. I confirmed the provider shapes and their state selectors: chest and trapped-chest shapes all stop at 14/16 height; ender chest has the fixed 1..15 inset and 14/16 height; hopper shapes are facing-selected and leave each horizontal face incomplete; anvil unions leave vertical gaps or partial-width regions; daylight detector and enchanting table are 6/16- and 12/16-high slabs. Thus `Block.isFaceSolid`'s full-face test is false for N/E/S/W throughout the listed domains. The target attachment-exception clause cannot turn any of these false face results into a connection.

The declared domains in the report are consistent with the property declarations/builders and registrations: chest and trapped chest each have 4 horizontal facings × 3 chest types × 2 waterlogged values (24); ender chest has 4 × 2 (8); hopper has five facings × 2 enabled values (10); each anvil alias uses the four horizontal facings (4 each); daylight detector has 16 power values × 2 inverted values (32); enchanting table has no declared properties (1). The geometry claims hold over these domains because the respective getters either select the stated shape families or return a constant shape.

Chest pairing limits were checked directly in both `ChestBlock` sources as well as against the referenced provider report: shape selection reads `TYPE` and `FACING` through `getConnectedDirection`, not a block entity or collision context; connection updates require a matching horizontal chest, compatible non-single type, matching facing, and reciprocal connected direction. Pair variants preserve the 14/16 height ceiling, so pairing does not fill the missing horizontal-face projection.

## Bounded conclusion

Accept the reported result: for these nine keys, all declared states, and N/E/S/W, the wall-neighbor result is false at both endpoints. This verdict audits only that projection and its stated dependencies; it is not a complete wall census, pair freeze, intermediate-version boundary, or trajectory claim.
