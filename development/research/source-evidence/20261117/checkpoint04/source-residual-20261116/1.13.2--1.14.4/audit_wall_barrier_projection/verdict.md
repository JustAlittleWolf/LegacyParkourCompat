# Independent source verdict: barrier wall neighbor projection

Verdict: ACCEPT, bounded claim only.

Reviewed packet: `build/source-residual-20261116/1.13.2--1.14.4/wall_barrier_face_projection/report.md`, SHA-256 `a85a3593e9c11117668fc5288328b546497ebcade800c280d7b81dd611ace8f0` (recomputed from exact bytes before review).

## Identity checks

The ready markers and source-manifest file hashes match the packet's endpoint identities: 1.13.2 Ornithe Feather Gen2 build.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 Ornithe Feather Gen2 build.2 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Independently recomputed hashes for both endpoint `WallBlock.java`, `Block.java`, `BarrierBlock.java`, and `Blocks.java` match their source manifest entries and the packet.

The 1.14.4 raw-client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` agrees with the target ready marker and the extraction record. Parsed extracted `walls.json` and `leaves.json`; neither includes `minecraft:barrier`. Payload hashes match the packet: walls `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`, leaves `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`.

## Source check

In 1.13.2, `BarrierBlock` declares no state properties and no face/collision override. Base `Block.getFaceShape` returns `FaceShape.SOLID`; base collision is enabled and `Block.getCollisionShape` uses the state's shape, whose default is the unit cube. `WallBlock.shouldConnectTo` permits the SOLID branch only when `isExceptionForConnection` is false, and that method explicitly includes `Blocks.BARRIER`. The middle-pole alternative cannot match: the barrier is not a `FenceGateBlock` and its inherited face shape is not a middle-pole shape. Result: false for each horizontal bit.

In 1.14.4, `BarrierBlock` likewise has no state properties or collision override; registration preserves default collision-enabled full-cube behavior. `Block.isFaceSolid` therefore returns true for the barrier (it is not a LEAVES member and the collision face is full). `WallBlock.m_71853799` then excludes it through `Block.isExceptionForAttachment`, which explicitly includes `Blocks.BARRIER`. The independent `WALLS` or matching fence-gate fallback is false: the barrier is absent from the exact WALLS resource and is not a fence gate. Result: false for each horizontal bit.

The report's conclusion is supported for the singleton legal barrier state and four horizontal neighbor directions. It closes only this predicate row. It does not establish downstream wall-shape parity, movement effect, applicability for edited/custom block registries, full provider inventory, or pair coverage/freeze.
