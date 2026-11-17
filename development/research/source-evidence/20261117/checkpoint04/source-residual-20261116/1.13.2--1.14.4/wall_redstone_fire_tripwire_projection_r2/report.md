# Remaining wall face projection: redstone, fire, and tripwire keys

## Scope and evidence identity

This source-only slice closes only the following common-key rows named by `wall_remaining_face_shape_census/report.md` (SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`): `tripwire`, `tripwire_hook`, `vine`, `torch`, `redstone_wall_torch`, `lever`, `ladder`, `redstone_wire`, and `fire`. The parent census lists “torch/redstone_wall_torch”; the plain torch and redstone wall torch are joined separately below. `wall_torch` is used only as the redstone-wall-torch provider dependency.

Endpoints are Minecraft 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2. The source-manifest files match the assignment identities:

| Endpoint | Manifest SHA-256 |
|---|---|
| 1.13.2 `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14.4 `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256` | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc` |

The bytes of the census report were rehashed before use and match its assigned SHA. Current target/old sources were read-only; no source generation or shared-source writes were performed.

## Wall operation compared

1.13.2 `WallBlock.shouldConnectTo` (`WallBlock.java:51–55`) accepts `!isExceptionForConnection(block) && faceShape == SOLID`, or its separate `MIDDLE_POLE_THICK` / fence-gate pole case. The examined block keys are not fence gates, and their providers return `UNDEFINED` horizontally. `WallBlock.isExceptionForConnection` (`:57–65`) includes the shared attachment-exception predicate and explicit barrier, melon, pumpkin, frosted-ice, and TNT exclusions; none of these nine keys is in that list. Thus old N/E/S/W bits are false for all examined keys, independent of their state.

1.14.4 `WallBlock.m_71853799` (`WallBlock.java:48–52`) accepts `!isExceptionForAttachment(block) && faceSolid`, or a wall-tag/fence-gate arm. None of these keys is a wall or fence gate, and none is an attachment exception. The supplied `faceSolid` is `BlockState.m_87223014` (`state/BlockState.java:326–327`), which resolves through `Block.isFaceSolid` (`Block.java:402–408`) and target `Block.getCollisionShape` (`:376–378`). The table compares that result by each horizontal direction. The target boolean is false for all keys and all four directions.

Direction tuples below are always north, east, south, west. Old columns report the effective 1.13.2 FaceShape. Target columns report `isFaceSolid` / full collision-face projection.

| Common key | Provider inputs (state/neighbor/context) | Old FaceShape N/E/S/W | Target collision projection N/E/S/W | Target projection reason |
|---|---|---|---|---|
| `tripwire` | `getShape` reads only `ATTACHED` to choose attached/free outline; `POWERED`, `DISARMED`, `NORTH/EAST/SOUTH/WEST` do not affect this provider. `world`, `pos`, and target `CollisionContext` are unused. No neighboring state is read by the shape provider. | `UNDEFINED` ×4 | `false` ×4 | Both registrations call `noCollision()`, so target collision shape is empty regardless of the attached/free outline. |
| `tripwire_hook` | `getShape` reads `FACING` only; `POWERED` and `ATTACHED` are unused by the provider. `world`, `pos`, and target context are unused; no neighbor state is read. | `UNDEFINED` ×4 | `false` ×4 | Both registrations call `noCollision()`; empty collision shape. |
| `vine` | Provider reads `UP`, `NORTH`, `EAST`, `SOUTH`, `WEST` and unions the corresponding ceiling/wall outline strips. `world`, `pos`, context, and neighboring states are not read by `getShape`. | `UNDEFINED` ×4 | `false` ×4 | Both registrations call `noCollision()`; empty collision shape for every property combination. |
| `torch` | Plain torch has no state properties; provider returns its constant floor outline. No world/position/context or neighbor input. | `UNDEFINED` ×4 | `false` ×4 | Both registrations call `noCollision()`; empty collision shape. |
| `redstone_wall_torch` | Target direct provider delegates to `WallTorchBlock` and reads `FACING`; `LIT` is not used in shape selection. No world/position/context or neighbor input. Old FaceShape is inherited from `TorchBlock`. | `UNDEFINED` ×4 | `false` ×4 | Both registrations call `noCollision()`; empty collision shape despite the facing-selected outline. |
| `lever` | Outline provider reads `FACE` and `FACING`; `POWERED` is ignored. No world/position/context or neighbor input. | `UNDEFINED` ×4 | `false` ×4 | Both registrations call `noCollision()`; empty collision shape. |
| `ladder` | Collision/outline provider reads `FACING`; `WATERLOGGED` is ignored. No world/position/context or neighbor input. | `UNDEFINED` ×4 | Facing `NORTH`: `false,false,true,false`; `EAST`: `false,false,false,true`; `SOUTH`: `true,false,false,false`; `WEST`: `false,true,false,false` (tuple order N/E/S/W). | The 3/16-thick slab touches one horizontal outer boundary and spans the full 1×1 dimensions parallel to that face. The target projects the boundary slice and considers it full. |
| `redstone_wire` | Shape index reads the four directional connection properties (old Boolean arms; target `ConnectionSide != NONE`); target/old state `POWER` does not select shape. Provider does not read world, position, context, or neighboring states. | `UNDEFINED` ×4 | `false` ×4 | Both registrations call `noCollision()`; empty collision shape for every connection state. |
| `fire` | Provider returns empty directly and reads no state. `AGE`, `NORTH/EAST/SOUTH/WEST`, and `UP` do not affect it; no world/position/context or neighbor input. | `UNDEFINED` ×4 | `false` ×4 | Provider shape is empty in target; registration also calls `noCollision()`. |

The old face-shape methods are unconditional for the listed keys: `TripwireBlock.java:206–208`, `TripwireHookBlock.java:255–257`, `VineBlock.java:380–382`, `TorchBlock.java:67–69` (inherited by redstone wall torch), `LeverBlock.java:150–152`, `LadderBlock.java:126–128`, `RedstoneWireBlock.java:492–494`, and `FireBlock.java:340–342`. The old connection-exception lists were checked in addition to these methods; they exclude none of the keys. In particular, no exception-versus-face ordering can turn an `UNDEFINED` result into an old wall connection.

## Reused geometry evidence

Existing accepted provider reports are reused only for identical provider-geometry/input claims, not as proof of this wall projection:

- `ladder_shape_provider/coverage.md` (SHA-256 `be38c3d02c735b568d96614af13c44b91171734f20724b02f243673afcab213b`) establishes the same facing-selected thin ladder boxes and the waterlogged-independent shape getter.
- `lever_shape_provider/coverage.md` (SHA-256 `fdb5a4a8c6cf72efdeb5a1619298cf0f7d4be40497db689279142ec2df4895cb`) establishes FACE/FACING selection and registration-level empty collision shape.
- `torch_shape_provider/coverage.md` (SHA-256 `4866c0abf0941be3372ca212fbbcf8cf866ea68578a2e750a72fd4d3319c79eb`) establishes the plain torch's constant outline and empty collision shape.
- `vine_shape_provider/coverage.md` (SHA-256 `ed8fa71f194d8b02f33522d82f5749df2a45669393a1c9e8ee84ed7ec0d030b8`) establishes the five-property outline union and empty collision shape.
- `redstone_wire_shape_provider/REPORT.md` (SHA-256 `4b44b5855f59aed1a49772775b1839c20db34708563e062ead1ea1b8e7824980`) establishes the cardinal connection shape selection and registration-level empty collision shape.
- `wall_torch_shape_provider/report.md` (SHA-256 `2fb7646fd8891c5c42e261483592a5f3361e0cdf26a4068e2a1029ba9a50f530`) establishes the facing-selected wall torch geometry and redstone-wall-torch delegation; this report independently applies the registration collision gate to the assigned key.

Tripwire, tripwire hook, and fire projection conclusions above are checked directly against their paired provider and registration sources, rather than inferred from those other provider reports.

## Ladder projection correction evidence (r2)

The exact target path confirms that the outer-face slice is tested as a full unit face, without requiring the collision shape to be a full cube. In `Block.java:402–412`, `isFaceSolid` calls `isFaceFull`, which obtains the directional face shape and checks it with the full-block-shape cache. The cache loader at `Block.java:84–90` tests that slice against `VoxelShapes.block()` with `BooleanOp.NOT_EQUALS`. `VoxelShape.java:170–200` obtains and caches the directional face shape; `VoxelShapes.java:317–334` returns an empty shape only when the queried boundary does not reach coordinate 0 or 1, otherwise it constructs a `SliceShape` at the outer voxel. `SliceShape.java:6–32` gives the slice unit coordinates along its face-normal axis while retaining the delegate coordinates in the two parallel axes. Its `makeSlice` crop (lines 17–26) selects only the outermost voxel on the face-normal axis and retains the complete ranges of the other two axes. The 1.14.4 ladder boxes span 0–16 in those parallel axes and reach 0 or 16 on their facing-selected boundary (`LadderBlock.java:25–48`); consequently the face slice fills a full 1×1 face even though the slab is only 3/16 thick. `Block.java:402–408` therefore returns true for the direction opposite `FACING`.

1.13.2 `LadderBlock.java:126–128` returns `FaceShape.UNDEFINED` unconditionally, so its four old horizontal bits remain false. This comparison establishes a projection change only; it does not assert a movement trajectory or pair freeze.

Exact 1.14.4 Ornithe Feather Gen2 build 2 source hashes for the projection path:

| Relative source | SHA-256 |
|---|---|
| `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/LadderBlock.java` | `12dc5ef28f2db058969218573f5d605b94cab4cc36b67b2d46e82aaf39fc767f` |
| `net/minecraft/block/shape/VoxelShape.java` | `8efb68bf9d5efdb0ec48e401d1852e90458c7094f8fa0c8e9ae418f268c40fdd` |
| `net/minecraft/block/shape/VoxelShapes.java` | `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` |
| `net/minecraft/block/shape/SliceShape.java` | `cdc2794ebcf185410c9171ce72ce9a63a8d1f32c067496c8624125ef77861455` |
| `net/minecraft/block/shape/CroppedVoxelSet.java` | `d9a71b7c5c98329606e1744ee7544dfafaa403c2bb9250c670866ba4d83d2621` |
## Source hashes

All listed class hashes were recomputed from source bytes. The hashes for the shared `Block.java` and wall-predicate `WallBlock.java` match the endpoint manifest entries; the provider class hashes likewise match their endpoint entries.

| Relative source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/TripwireBlock.java` | `708a553f53db6ff187e93e02e0e7ffc8e34fc0c9abd38a0ffe7c18a488534d59` | `fea6cdd16b28ab7008ef4429fb50aeb4f2c3ec4c6f7de9ae54959ae5cfca9efd` |
| `net/minecraft/block/TripwireHookBlock.java` | `c9b234f97b9ca6f93a519c2775aa91e88756456d8bfd4bd7e5281bb411f82629` | `b7c4e272133e4a5173b3896a5b090907fad232c76291d72686ca102ca4bae3b7` |
| `net/minecraft/block/VineBlock.java` | `54638df0418f19e3e37e343acb17124cf5d1777d789c7dcc75ef64418e9f02ea` | `6032f7a16ae3bf2f1ce4d70401086075b9f679fb92a7f6f6de1a3cb7ff4543cd` |
| `net/minecraft/block/TorchBlock.java` | `da11308e51f57ce95764f2ee645dde8d15607914a3a68bac9c0635ab3d1531e0` | `8430a1058184d9e87596d5ba961ce092e6c77469c642f50e5c901db1a0588aab` |
| `net/minecraft/block/RedstoneWallTorchBlock.java` | `14551c617a070a3591bfc3fac571ade37bd7f2324c6360e3936ee792a8f9d8da` | `237ba36f03e040c4ff1531d46887bef719698fe07d580f375947cdf7c4243c16` |
| `net/minecraft/block/WallTorchBlock.java` | `f06b2a546ba8465627511969efc1203e654a1af3ccf61d27aed9b28b00c77462` | `ff6966b0f5236251c86776c156f5aa57fa214ef50a0a78509a126d916b92db5f` |
| `net/minecraft/block/LeverBlock.java` | `0e9d5a08921c8b0f96c060742a24666f3a8eb2e4b9f7d408a5d242a0edc1daed` | `53dda36db5316f18985497b72e2d09b3e7a72e74141c4dfbe22addc621560111` |
| `net/minecraft/block/LadderBlock.java` | `c89dea1d7648bf5908e9d925b31b1ad6ead25bea3784838ae3d0fb505cfb9ab5` | `12dc5ef28f2db058969218573f5d605b94cab4cc36b67b2d46e82aaf39fc767f` |
| `net/minecraft/block/RedstoneWireBlock.java` | `ba3ad2e45aad6455b4a30803bf4840f98151ac924eb8368755fde031fc5de497` | `d00795e4ff382340702901594f370d012ab3ae3ddaca2aed5b05fa32a827da94` |
| `net/minecraft/block/FireBlock.java` | `cd9da861e6594e8ac5ab93bd26e268c03e9dbd0cc790c2a8e4a8d062e2af6336` | `b4d5e6e5b096bab0e353ed780fb8876ed9e086dc570d8397a1a4384b56dc9643` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/state/BlockState.java` | `0496fda381628e90b3b3ff8ae376a5bae24cbd662366f34d70991252fab2fe70` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| `net/minecraft/block/Blocks.java` (target registrations) | — | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |

The 1.13.2 registrations are in `Block.java` (including tripwire, hook, and the older key registry); target registrations are in 1.14.4 `Blocks.java`. Both endpoint registration sites were inspected for `.noCollision()`; ladder alone retains collision, with the partial-shape result shown above.

## Bounded conclusion and remaining unknowns

For eight examined common keys, every old horizontal wall bit is false because the old provider returns `FaceShape.UNDEFINED`; the old exception checks do not add any of these blocks to the exception list. Their target horizontal projections also remain false because the target collision-face projection is empty. **Ladder is the sole delta:** old `FaceShape.UNDEFINED` yields four false bits, while each 1.14.4 facing projects a full outward/back face (NORTH→SOUTH, EAST→WEST, SOUTH→NORTH, WEST→EAST). Therefore each facing has one target horizontal wall bit where its old counterpart is false.

The only provider-neighbor inputs needed by the wall projection are the adjacent block's registered key/state and the outward horizontal direction; these providers themselves do not read neighboring block states. This slice does not expand into neighbor callbacks, placement/update state derivation, tripwire entity callbacks, vine growth/climbability, redstone power, fire support logic, or other movement/collision consumers. All intermediate-patch cutovers are unknown. It does not close remaining census rows, the overall wall crosswalk, or pair freeze.
