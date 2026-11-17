# Independent source audit: repeater/comparator wall predicate

Verdict: **ACCEPT**, limited to the `minecraft:repeater` and `minecraft:comparator` horizontal wall-neighbor predicate rows.

Audited immutable author packet SHA-256: `1d1e9bab09e0977fc1e049574feba0952969664ece2b1dc38d8390dab486b5e6` (`wall_redstone_diode_projection/report.md`). The referenced unexamined-key TSV SHA-256 matches `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`.

## Source identity and bindings

The ready markers bind 1.13.2 and 1.14.4 to Feather Gen2 build 2 manifests `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, respectively. I independently recalculated every Java digest in the report's source table against canonical source bytes and confirmed each corresponding source-sidecar manifest entry. All matched. The report file itself matches the audited SHA above.

## Findings checked

- Both endpoint registration paths contain the exact `repeater` and `comparator` keys/classes. Their `buildStateDefinition` methods and property declarations support the report's products: repeater `FACING(4) × DELAY(4) × LOCKED(2) × POWERED(2) = 64`; comparator `FACING(4) × MODE(2) × POWERED(2) = 16`. `HORIZONTAL_FACING` is the four values NORTH/EAST/SOUTH/WEST; `DELAY` is 1–4; comparator mode is exactly COMPARE/SUBTRACT. The 80 total registered key/state combinations are correct.
- In 1.13.2, both classes inherit `DiodeBlock.getFaceShape`; it returns `SOLID` only for DOWN and `UNDEFINED` for each horizontal face. The old wall predicate requires SOLID or its separate middle-pole/fence-gate branch. Thus both keys remain disconnected for every registered state and horizontal direction.
- In 1.14.4, both classes inherit the state-independent 0..16 × 0..2 × 0..16 diode shape. Their registrations do not set `noCollision`; `Block.Properties.hasCollision` defaults true. The base collision provider consequently returns this 2/16-high shape. `BlockState.m_87223014` routes to the cached/direct `Block.isFaceSolid` calculation; face solidity requires a full-block face slice. Each horizontal slice retains only the 2/16 height and is not full. Neither registration is a `WALLS` member nor a fence gate, so the alternate tag/gate branch cannot connect. This establishes false target wall bits across all states/directions.
- The target attachment-exception gate does not change the result because both the generic full-face input and the alternate tag/gate input are false.

The referenced `WALLS` membership packet and its independently accepted resource audit were reused for that exact tag fact; I did not claim a new resource extraction.

## Disposition

The report's no-difference result is supported for these two wall predicate rows. This accepts no player trajectory or movement consequence and does not freeze either source pair.
