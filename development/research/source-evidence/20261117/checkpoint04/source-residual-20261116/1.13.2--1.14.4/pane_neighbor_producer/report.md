# 1.13.2–1.14.4 pane neighbor producer closure

Status: bounded source-only closure of the open producer gate in `glass_pane_crosswalk/report.md`. This covers `IronBarsBlock` placement and horizontal neighbor updates, with representative neighbor classes/state categories chosen to compare the stored horizontal connection bits. It is not a full neighbor census or pair freeze.

## Exact source identity

- Older endpoint: 1.13.2 Ornithe Feather Gen2, source root `decompiled_minecraft/1.13.2/ornithe-feather`; ready source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Newer endpoint: 1.14.4 Ornithe Feather Gen2, source root `decompiled_minecraft/1.14.4/ornithe-feather`; ready source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.
- These source identities and the crosswalk's accepted Pane provider/query evidence are reused from `glass_pane_crosswalk/report.md` (SHA-256 `e1150fb5ddc471defc1a67871e4b734012f3e713758d2c32de9261b2227e51ca`). The manifest digests are the identities recorded there. Overlapping Java hashes were recomputed locally and match its ledger; additional cited file hashes are listed below.

## Producer path comparison

Both endpoint `IronBarsBlock.getPlacementState` methods read the four horizontal adjacent states, then write `NORTH`, `SOUTH`, `WEST`, `EAST` in that order from one neighbor result per bit; `WATERLOGGED` is written afterward from the fluid state. In 1.13.2 each result passes `neighborState.getFaceShape(world, neighborPos, oppositeDirection)` into `shouldConnectTo` (`IronBarsBlock.java:22–43`). In 1.14.4 the same directional calls and assignment order pass `neighborState.m_87223014(world, neighborPos, oppositeDirection)` (`IronBarsBlock.java:22–43`).

Both `updateShape` methods schedule a water tick first when the pane is waterlogged, then update only the property for a horizontal `dir` and delegate nonhorizontal directions to `super` (`IronBarsBlock.java:45–55` in both). The old horizontal result calls `getFaceShape`; the target calls `m_87223014` (`1.13.2 IronBarsBlock.java:46–54; 1.14.4 IronBarsBlock.java:45–55`). Thus each concrete classifier outcome below changes the corresponding stored directional bit on both initial placement and a horizontal neighbor update. Fluid state does not enter the classifier.

## Bounded neighbor outcomes

The old decision is `(!isExceptionForConnection(block) && faceShape == SOLID) || faceShape == MIDDLE_POLE_THIN` (`1.13.2 IronBarsBlock.java:77–96`). The target decision is `(!isExceptionForAttachment(block) && faceSolid) || block instanceof IronBarsBlock` (`1.14.4 IronBarsBlock.java:72–75`); `m_87223014` delegates to cached face solidity or `Block.isFaceSolid`, which rejects leaves-tagged states and then requires a full collision-shape face (`1.14.4 BlockState.java:326–328; Block.java:402–409`).

| Neighbor state/category facing the pane | 1.13.2 stored bit | 1.14.4 stored bit | Result |
|---|---:|---:|---|
| Ordinary default full-cube `Block`, not an exception | true: default `getFaceShape` is `SOLID`, not in old exception set | true: full collision face, not in new attachment exception set | Same |
| Leaves state | false: `LeavesBlock` is in `isExceptionForConnection`; its face result cannot satisfy the thin-pole alternative | false: leaves are rejected by face solidity and are also an attachment exception | Same |
| Iron bars or pane-class neighbor | true: inherited `IronBarsBlock.getFaceShape` reports `MIDDLE_POLE_THIN` on horizontal faces, which independently satisfies the old `||` | true: target explicitly connects any `IronBarsBlock` subclass | Same; the reused crosswalk establishes pane key/class mappings on both endpoints |
| Beacon state on a horizontal face | false: beacon is in `isExceptionForConnection`, and its inherited default face shape is `SOLID` | true: beacon is absent from the target attachment exception set; `BeaconBlock` has no collision-shape override; inherited `Block.getShape` is a full cube, so the inherited collision shape has a full face | **Different: target sets the pane's directional bit where old leaves it false** |
| Glowstone state on a horizontal face | false: glowstone is in `isExceptionForConnection`, with inherited default `SOLID` face shape | true: glowstone is absent from the target attachment exception set; 1.14.4 `Blocks.java:583–586` registers it as plain `Block`, whose inherited collision shape has a full face | **Different: target sets the pane's directional bit where old leaves it false** |

The two concrete differences are state-producer differences, not changes to pane query geometry: for example, a pane placed east of a beacon stores `EAST=true` in 1.14.4 but `EAST=false` in 1.13.2; a subsequent horizontal update against that beacon makes the same per-direction result. Through the already accepted provider crosswalk, the differing bit selects a different Pane shape index and therefore a different player collision shape. This is a reachable player placement/update path adjacent to an existing block. No trajectory outcome is inferred here.

The direct source shows additional old-only exceptions (`CAULDRON`, `GLOWSTONE`, `ICE`, `SEA_LANTERN`, piston variants, and others) that require variant-by-variant shape inspection before assigning outcomes. This report claims only the two full-cube examples above. The `FaceShape.MIDDLE_POLE_THIN` override for iron bars is covered to ensure the target class exception preserves pane-to-pane connection; other neighbors were not census-audited.

## Sources and hashes

All paths below are relative to the endpoint source root. SHA-256 values were computed from the exact local Java files. Hashes that overlap the crosswalk ledger were confirmed identical to its published ledger.

| Endpoint | Source path | SHA-256 |
|---|---|---|
| 1.13.2 | `net/minecraft/block/IronBarsBlock.java` | `df6d956bb2745241e59294056f31513151b976dbd9ceed3f5478fd72b66c68a0` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.13.2 | `net/minecraft/block/BeaconBlock.java` | `c8489ab810e21c2efd95e24acf8eb40e120e8d4f78248b886c65a87d901efdaf` |
| 1.13.2 | `net/minecraft/block/GlowstoneBlock.java` | `c03d8d8ff45e0a3b945ce52255ea58087da4a7c379031a50e96daa75c273199e` |
| 1.13.2 | `net/minecraft/block/LeavesBlock.java` | `8f209a4fcf08dbb4570df915795931f3f876abad8659ded4b17df6e535432006` |
| 1.14.4 | `net/minecraft/block/IronBarsBlock.java` | `10ccd6a2135cb05e74cdd4c440da29992dbc60655beedcb5f648f73bbc150541` |
| 1.14.4 | `net/minecraft/block/Block.java` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| 1.14.4 | `net/minecraft/block/state/BlockState.java` | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.14.4 | `net/minecraft/block/Blocks.java` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| 1.14.4 | `net/minecraft/block/BeaconBlock.java` | `7416e10a733c49b608758f5937ab6690cb12e5cd6ba385106c44f28fadef7d36` |
| 1.14.4 | `net/minecraft/block/LeavesBlock.java` | `7df0d5e941aa34ccd67ebcf9fec95af2af73cd157ae6e5441a30c8a0dd6aedcd` |

## Bounded conclusion

The placement and horizontal update bit-production order/gates are otherwise aligned. Pane-to-pane connectivity is preserved through the target `IronBarsBlock` class exception. At least two reachable neighbor classes (beacon and glowstone) produce a concrete true/false directional-bit difference because they are old connection exceptions but not target attachment exceptions, while supplying full horizontal faces. This closes the assigned pane-producer gate with bounded examples; it does not establish all neighbor-state parity, a release introduction point within the interval, full-pair coverage, implementation acceptance, or runtime parity.




