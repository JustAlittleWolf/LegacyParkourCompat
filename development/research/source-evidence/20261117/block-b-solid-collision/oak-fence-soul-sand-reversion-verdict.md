# Independent source verdict: Soul Sand side connection for Oak Fence

**Verdict: CONFIRMED.** For a normal dry Oak Fence state with Soul Sand as its only horizontal neighbor and air in the other three loaded horizontal positions, the Soul Sand connection is true through 1.13.2, false in 1.14–1.15.2, and true again from 1.16 through 26.2. This verdict concerns fence block-state connectivity and its resulting ordinary collision shape. It does not assert a trajectory or runtime test.

## Exact behavior and state paths

- **1.13.2 (Ornithe Feather): true.** `FenceBlock.getPlacementState` (lines 74–93) calls the neighbor state's `getFaceShape` for the four horizontal sides; `updateShape` (lines 97–108) repeats the same predicate for horizontal neighbor updates. `SoulSandBlock` supplies a 16×14×16 collision `SHAPE` (lines 12–24) but does not override `getFaceShape`. `Block.getFaceShape` therefore returns `FaceShape.SOLID` (lines 382–385). Fence `shouldConnectTo` accepts this shape unless the block is an attachment exception (lines 46–55); Soul Sand is not such an exception. In the stated fixture, the Soul Sand-side property is true at placement and remains true on its horizontal update; air evaluates false on each of the other sides.
- **1.14 (Ornithe Feather) and 1.14.4 (Mojmap): false.** Placement and update now pass `Block.isFaceSolid` / `BlockState.isFaceSturdy` into the fence predicate (`FenceBlock`). In 1.14 Feather, `Block.isFaceSolid` calls `isFaceFull` on the neighbor collision shape (`Block.java:391–397`); in 1.14.4 Mojmap, `Block.isFaceSturdy` does the same (`Block.java:403–410`). Soul Sand collision remains `Block.block/box(0,0,0,16,14,16)` (`SoulSandBlock`). For a horizontal face, that face has only 14/16 of the block's vertical extent, so it is not a full face and the predicate is false. The three air faces remain false.
- **1.15, 1.15.1, 1.15.2 (Mojmap): false.** Each exact release uses the same collision-based `Block.isFaceSturdy` implementation and the same Soul Sand 16×14×16 collision shape; each `FenceBlock.getStateForPlacement` and horizontal `updateShape` feeds `isFaceSturdy` to `connectsTo`. For an ordinary Soul Sand state, the side input is false, and Soul Sand is neither a fence nor a direction-compatible fence gate, so `connectsTo` is false.
- **1.16 and 1.16.1 (Mojmap): true.** Collision stays 16×14×16, but each `SoulSandBlock.getBlockSupportShape` now returns `Shapes.block()`. `Block.isFaceSturdy` now tests `blockState.getBlockSupportShape(...).getFaceShape(direction)` (`Block.java:228–234`), not the collision shape. The horizontal support face is full, so `FenceBlock` placement and horizontal update set the Soul Sand-side connection true. Air remains false.
- **26.2 (unobfuscated): true.** Soul Sand still has a 16×14 collision shape (`SoulSandBlock.java:15, 26–28`) and separately returns `Shapes.block()` from `getBlockSupportShape` (lines 31–34). `BlockState.isFaceSturdy` defaults to `SupportType.FULL` (`BlockBehaviour.java:892–897`); `SupportType.FULL` reads `getBlockSupportShape` (lines 12–16). `FenceBlock` uses that sturdiness for placement (lines 90–94) and horizontal updates (lines 105–111), so its connection remains true.

## Valid ordinary state and collision geometry

Use the Oak Fence block's ordinary `getPlacementState` result with a dry placement context and loaded neighbors: put Soul Sand in exactly one cardinal adjacent block, air in the other three horizontal adjacent positions, and no water at the fence's own position. The implementations synchronously read those four neighbor states and set all four directional properties; the update path recalculates the changed horizontal property from the same neighbor state. A pre-existing equivalent fixture is valid if its fence state has precisely that one direction true, the other three false, and `WATERLOGGED=false`.

This is the ordinary one-arm fence collision state, not a custom shape. The historical `FenceBlock` constructors pass `(2, 2, 16, 16, 24)` to `CrossCollisionBlock`/`PaneBlock`. Its collision-shape builder uses a central 4×4 post and a 4-wide arm for every true direction; the collision arm is generated from y=0 through y=24 in local 0–16 block units (1.5 blocks high). Thus the fixture yields one normal rail arm toward Soul Sand. The connection flag affects the fence's vanilla collision geometry; fence state and block identity stay vanilla.

## Exact source identity and hashes

Sources are the existing local decompiler output. The mapping families are Ornithe Feather for 1.13.2/1.14, Mojmap for 1.14.4–1.16.1, and published unobfuscated source for 26.2. Hashes below are SHA-256 of the literal UTF-8 source files or `.sources.sha256` manifest bytes in this checkout. File hashes are uppercase hexadecimal; manifest hashes pin each version/mapping source inventory. The manifest entries contain the corresponding per-source-file digest.

| Release / mapping | Source manifest SHA-256 |
|---|---|
| 1.13.2 / ornithe-feather | `2C8CFB646BF622FB26AC0CA0CB5E02A354FB512E29F8B1010F1AAACD509E1211` |
| 1.14 / ornithe-feather | `68BC39E38BC501E7C9AF315DE4678263E1A64606B0B9780099187C7984CCBC98` |
| 1.14.4 / mojmap | `AF98406F3D4ED31494FBDCE1078D1F3DF3F712501878F9A0E9AD5879BDF23C3B` |
| 1.15 / mojmap | `251A3019CCC3FD067F66365F3545B69A983383A28B19A1038DEBF1791BE29340` |
| 1.15.1 / mojmap | `C8FDCE2F9432B72FC13C19A48F9AB3BE203CAC41A617139E81BAC3552245912D` |
| 1.15.2 / mojmap | `CB7FD93F8730D8B2813857744F8572060535C7DE44FCEE86222ACD1CEB6F43E7` |
| 1.16 / mojmap | `E3C23E94AC985C25DEC0C768AED17B443BA4823662CF1E152235C99756AE5D4A` |
| 1.16.1 / mojmap | `76FDD121070C2012F48CBDF5CF0C78490116AFC94117C30B0858EEDCD6AB9754` |
| 26.2 / unobfuscated | `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894` |

| Exact source file (relative to repository root) | SHA-256 |
|---|---|
| `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/SoulSandBlock.java` | `259645F4B6DBC7B71481756C8FCD804D7BD3DADB679D09F06284FFB9D145B5BE` |
| `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/Block.java` | `735030E8BB5FC7EAD2D6DBCB1B262A36A414B4349DAF197456337F460B444CD4` |
| `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/block/FenceBlock.java` | `ABEFBEDEA10306ABF602D4E379A4793B415CA7635231752CD9F18A80ACED5FC2` |
| `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/block/SoulSandBlock.java` | `EE44C129F10322D5406E78CE24A6E9BEFA42FC089653954B31E30EFB90D0C1BA` |
| `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/block/Block.java` | `0308B22D47062863EE36DB68B907D42C7A95E060D5841F4D6E9CF67EDA66FD6A` |
| `decompiled_minecraft/1.14/ornithe-feather/net/minecraft/block/FenceBlock.java` | `99BF66FC30039A136940B25A3D05C5E9035B857F26E4884F68C7B237F54EC8D7` |
| `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/level/block/SoulsandBlock.java` | `3F2CE73AAE23FB7E13CF001123343C6C750F00684B5331A5597AE33B011EF4E4` |
| `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/level/block/FenceBlock.java` | `D45B20C3B1280285B823A5A8BDAB8CFB526FD5CDA41C33D83FEE75DBB17F6F3E` |
| `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/level/block/Block.java` | `276022CFC5BC00437FE65A23BBCDC9C078026E68D88930EB3244581F6DBFCF5F` |
| `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/level/block/CrossCollisionBlock.java` | `5088EB166CA1CA36EB496AB120338D713C30BEC84FF5815EC266A52EA1444ECD` |
| `decompiled_minecraft/1.15/mojmap/net/minecraft/world/level/block/SoulsandBlock.java` | `355F0CC25E78DCE76CBC9C89D8BD8FF1956D4B53780CBA8E3FB5C23F485802CF` |
| `decompiled_minecraft/1.15/mojmap/net/minecraft/world/level/block/FenceBlock.java` | `81EDA679E834659DB079607B64A14D79284221B852F75AAD43655AE7E0B96AB5` |
| `decompiled_minecraft/1.15/mojmap/net/minecraft/world/level/block/Block.java` | `069FFC485F94C84EB63E528E608F91104229477B6AC266AA3D4103F836CAC2FE` |
| `decompiled_minecraft/1.15.1/mojmap/net/minecraft/world/level/block/SoulsandBlock.java` | `355F0CC25E78DCE76CBC9C89D8BD8FF1956D4B53780CBA8E3FB5C23F485802CF` |
| `decompiled_minecraft/1.15.1/mojmap/net/minecraft/world/level/block/FenceBlock.java` | `81EDA679E834659DB079607B64A14D79284221B852F75AAD43655AE7E0B96AB5` |
| `decompiled_minecraft/1.15.1/mojmap/net/minecraft/world/level/block/Block.java` | `A5819A4C676D2B7E08CCE7F80AE80A13726DD17B15E0B29EFDCE7E37EFA2BF0D` |
| `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/level/block/SoulsandBlock.java` | `355F0CC25E78DCE76CBC9C89D8BD8FF1956D4B53780CBA8E3FB5C23F485802CF` |
| `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/level/block/FenceBlock.java` | `81EDA679E834659DB079607B64A14D79284221B852F75AAD43655AE7E0B96AB5` |
| `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/level/block/Block.java` | `A5819A4C676D2B7E08CCE7F80AE80A13726DD17B15E0B29EFDCE7E37EFA2BF0D` |
| `decompiled_minecraft/1.16/mojmap/net/minecraft/world/level/block/SoulSandBlock.java` | `DA92572788C9416B511C003E50688D802E8248CCDB3E2D5EB155AA482549B095` |
| `decompiled_minecraft/1.16/mojmap/net/minecraft/world/level/block/FenceBlock.java` | `1337151F78048FCEF2356D128599DEC6E688A01B10E21E8A409F767796F93588` |
| `decompiled_minecraft/1.16/mojmap/net/minecraft/world/level/block/Block.java` | `CA9A413BF8198402EC9F2FEBB65C2E89EEF4386CB0F1F3D36650F4047D896655` |
| `decompiled_minecraft/1.16.1/mojmap/net/minecraft/world/level/block/SoulSandBlock.java` | `DA92572788C9416B511C003E50688D802E8248CCDB3E2D5EB155AA482549B095` |
| `decompiled_minecraft/1.16.1/mojmap/net/minecraft/world/level/block/FenceBlock.java` | `1337151F78048FCEF2356D128599DEC6E688A01B10E21E8A409F767796F93588` |
| `decompiled_minecraft/1.16.1/mojmap/net/minecraft/world/level/block/Block.java` | `CA9A413BF8198402EC9F2FEBB65C2E89EEF4386CB0F1F3D36650F4047D896655` |
| `decompiled_minecraft/1.16.1/mojmap/net/minecraft/world/level/block/CrossCollisionBlock.java` | `CEBB3DCBBB94AB7513E11430FA4969BC1FF0A96C733298940889FC749B7EC1E4` |
| `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/block/SoulSandBlock.java` | `6BE030C181DD96298C219E6001EB69F05468531C22CBAB0DF2B7A066DF74A9FC` |
| `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/block/FenceBlock.java` | `BE5E1AA19A07D68457FF21968FB4E264669588FB6E83A86A6F51E76833F17F93` |
| `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/block/Block.java` | `CEC6A05E644E4A7FEB8253CC4CA772A98F0E116FB098A1B7EE7302984AC7ECAB` |
| `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/block/SupportType.java` | `EA19A02F2559AFE4A8E4C0F6E12CD1480ABB0F62FE6853E37FA93771BD7DBEDC` |
| `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/level/block/CrossCollisionBlock.java` | `DCD2DBBCD093A68DEE4EE282E5F3101C519D633F3F173DD33383BD4B548C1BA5` |

## Scope limits

This is a bounded independent audit of the named Soul Sand/Fence predicate and specified fixture across the assigned endpoints. It does not establish the exact patch release where the 1.13.2-to-1.14 behavior first changed or a complete pair inventory. No runtime test or build was performed.