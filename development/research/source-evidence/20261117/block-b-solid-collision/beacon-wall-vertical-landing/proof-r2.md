# Beacon–wall landing witness, corrected state audit

## Corrected verdict

The earlier `proof.md` got the geometry conditional on `NORTH` right, but its claim that a normally placed 1.13.2 cobblestone wall has `NORTH=true` was wrong. The cause is exact: 1.13, 1.13.1 and 1.13.2 put `Blocks.BEACON` in `Block.isExceptionForPlacement`, inherited by `Block.isExceptionForAttachment`, and `WallBlock.isExceptionForConnection` uses that result. The ordinary 1.13.x wall state therefore has no north arm. For the fixture as posed, the vertical landing clips at the wall only in 1.14 and later; at 1.13.2 the post misses the player and the one-tick fall does not yet reach the Beacon top.

**Updated disposition:** the exact-source evidence supports a version-dependent player collision result in this fixture, but this report does not assign formal pair acceptance. The north-arm landing is not a 1.13.2 collision; it is the later-version side of the candidate difference. Do not treat the 26.2 state as further change evidence: it confirms continuation of the later arm behavior only.

## Exact state by version

Coordinates use W=(0,64,0), the Beacon neighbor N=(0,64,-1), the wall only at W, no block above W and all other horizontal neighbors air. “Stored” below means the state returned by normal wall placement or by the equivalent neighbor-shape update, not an artificially forced property combination.

| Exact source | Beacon→north predicate | Resulting wall properties | Collision seen by the proposed player |
|---|---|---|---|
| 1.13, Feather | False | `UP=true`, `NORTH=false`, other sides false | Post only; player z-max 0.2 stays short of post z-min 0.25. The Beacon top is y=65; feet after dy=-0.3 are 65.35, so no collision this tick. |
| 1.13.1, Feather | False | `UP=true`, `NORTH=false`, other sides false | Same as 1.13. |
| 1.13.2, Feather | False | `UP=true`, `NORTH=false`, other sides false | Same as 1.13. |
| 1.14, Feather | True | `UP=true`, `NORTH=true`, other sides false | North collision arm intersects x/z and clips the downward move at feet y=65.5. |
| 1.14.4, Feather | True | `UP=true`, `NORTH=true`, other sides false | Same arm/collision witness. |
| 26.2, unobfuscated | True | `UP=true`, `NORTH=LOW`, other sides `NONE` | `LOW` north arm's collision top is still y=65.5 and clips the move there. |

For the 1.13.x `UP` result, all four connector booleans are false, so the exact formula `(!bl || bl2 || !bl3 || bl4) && (bl || !bl2 || bl3 || !bl4)` evaluates to true. For 1.14.x the same formula also evaluates true with only `bl` (north) true. The wall is therefore post-bearing in every release here; only the horizontal side property changes.

## Decisive source logic

### 1.13, 1.13.1 and 1.13.2

All three Feather `WallBlock.java` files have identical SHA-256 `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`. In each:

- `WallBlock.getPlacementState` reads `blockPos.north()` and passes that state plus its south-facing `FaceShape` into `shouldConnectTo` (1.13.2 lines 69–92; 1.13 and 1.13.1 have the corresponding body).
- `shouldConnectTo` returns `!isExceptionForConnection(block) && shape == FaceShape.SOLID || bl`; here the secondary `bl` is the thick/middle-pole exception and is false for Beacon (lines 51–54).
- `isExceptionForConnection` delegates to `Block.isExceptionForAttachment` (lines 57–65).
- `Block.isExceptionForPlacement` explicitly contains `block == Blocks.BEACON`; `isExceptionForAttachment` delegates to it (1.13.2 lines 241–256; equivalent explicit Beacon entries at 1.13 lines 257–272 and 1.13.1 lines 241–256). This makes the first conjunction false even though ordinary `Block.getFaceShape` defaults to `FaceShape.SOLID` (1.13.2 `Block.java` lines 383–385). `AirBlock.getFaceShape` returns `UNDEFINED`, so the other three sides are false too.
- The selected state writes `.set(NORTH, bl)` and all other direction bits from their matching predicates (WallBlock lines 86–92). With the north Beacon excluded, the concrete state is no horizontal arms, `UP=true`.

Collision geometry is not the disputed condition: for that no-arm state, `WallBlock.getCollisionShape` uses the post in its indexed shape. `PaneBlock.makeShapes` gives the post x/z `[4,12]/16`, and wall collision height 24/16. The player box x `[0.2,0.8]`, z `[-0.4,0.2]` does not overlap the post's z interval `[0.25,0.75]`. The Beacon is a full collision block, but its top is at y=65: a proposed movement from feet 65.65 by -0.3 ends at feet 65.35, still above that top.

### 1.14 and 1.14.4

In both Feather releases, `WallBlock.getPlacementState` passes `Block.isFaceSolid` for each neighbor face into `m_71853799` (1.14 `WallBlock.java` lines 48–78; 1.14.4 lines 48–78; in 1.14.4 the state face predicate is named `m_87223014`). The predicate is now `!isExceptionForAttachment(block) && faceSolid || bl2`. Neither 1.14 nor 1.14.4's `Block.isExceptionForAttachment` includes Beacon: it lists leaves, barrier, carved/lit pumpkin, melon and pumpkin (1.14 `Block.java` lines 249–255; 1.14.4 lines 260–266).

`Blocks.BEACON` is registered with `Block.Properties.of(Material.GLASS, ...)` in both releases. `BeaconBlock` does not override collision shape. `Block.Properties.hasCollision` defaults to true; inherited `Block.getShape` returns `VoxelShapes.block()`, and `getCollisionShape` returns the state shape when `hasCollision` is true. Consequently the Beacon's south face is full for `Block.isFaceSolid`, so the wall's north boolean is true. `Block.isSolid` is false because it additionally requires `Material.isSolidBlocking()` and `Material.GLASS` has that flag false; that predicate is not used by wall connection. This resolves the false lead: the Beacon's non-solid/material property does not prevent the 1.14 connection.

With only north true, normal placement stores `UP=true`, `NORTH=true`, other sides false. In both releases, the same pane-based wall collision inputs are used: `WallBlock` passes `(4,3,24,0,24)` to `makeShapes`, whose north arm is x `[5,11]/16`, z `[0,11]/16`, y `[0,24]/16`. It reaches height 1.5 blocks, so W's arm top is y=65.5.

### 26.2

`Blocks.BEACON` has no collision-disabling property and `BeaconBlock` does not override its shape. `BlockBehaviour` defaults `hasCollision=true`, `getShape` to `Shapes.block()`, `getCollisionShape` to that shape when collision is enabled, and `getBlockSupportShape` to the collision shape. `BlockState.isFaceSturdy` defaults to `SupportType.FULL`; `SupportType.FULL` checks for a full face of that support shape. `Block.isExceptionForConnection` does not list Beacon. Thus `WallBlock.getStateForPlacement`'s `connectsTo(..., northState.isFaceSturdy(... SOUTH), SOUTH)` yields true.

With empty space above W, `updateShape` computes an empty above collision face; `makeWallState(true, empty, northTestShape)` chooses `LOW` because `isCovered` is false. One north connection also satisfies `shouldRaisePost`'s corner test, so the resulting state has `UP=true`, `NORTH=LOW`, other sides `NONE`. `makeShapes(24,24)` uses collision y-top 24 for both the LOW and TALL arm variants. Thus the modern state retains the collision at 65.5; it does not establish where the change occurred between 1.14.4 and 26.2.

## Legal ordinary vertical path

The player has width 0.6 and feet-based bounds. At the proposed center `(x,z)=(0.5,-0.1)`, its horizontal box is x `[0.2,0.8]`, z `[-0.4,0.2]`: it intersects the north arm if and only if the normally stored north side exists, and clears the central post. The pre-witness approach described in `proof.md` remains valid: remain just outside W's z-bound while falling, then enter the footprint on a preceding tick while feet stay above 65.5, and on the next tick have only ordinary vertical movement about -0.3035. On the witness tick, 1.13.x has no arm and ends near 65.35; 1.14+ has the arm and resolves to 65.5. The Beacon top at 65.0 is not reached in the 1.13.x tick.

The 1.13.2 `Entity.move` step-up branch additionally requires a horizontal resolved/requested movement difference; both horizontal movements are zero, so it does not run. 1.14's gate requires a horizontal collision flag, also false. The 26.2 gate similarly requires x or z collision. This remains an ordinary vertical collision, not a step-up or an inferred horizontal response.

## Source publication identities

All exact release markers say `ready`; each source-manifest SHA-256 was checked against its marker. Feather mappings below use `ornithe-feather`; 26.2 is published unobfuscated source. The previously recorded `proof.md` includes the complete 1.13.2/1.14/26.2 decompiler details.

| Release | Mapping | Source-manifest SHA-256 | Relevant source file hashes (SHA-256) |
|---|---|---|---|
| 1.13 | Ornithe Feather | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `WallBlock` `aeb67adf…`; `Block` `e5be72b7…` |
| 1.13.1 | Ornithe Feather | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `WallBlock` `aeb67adf…`; `Block` `735030e8…` |
| 1.13.2 | Ornithe Feather | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `WallBlock` `aeb67adf…`; `Block` `735030e8…` |
| 1.14 | Ornithe Feather | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `WallBlock` `9e120e49…`; `Block` `0308b22d…` |
| 1.14.4 | Ornithe Feather | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` | `WallBlock` `033da2ce…`; `Block` `1f8ec362…` |
| 26.2 | Unobfuscated | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `WallBlock` `b2bcd69d…`; `Block` `cec6a05e…`; `Entity` `7afb9c12…` |

1.14.1–1.14.3 have no ready source publications in the shared tree and are not claimed here. No product implementation, product history, wiki inventory, build, test, gameplay launch or decompilation was used.

