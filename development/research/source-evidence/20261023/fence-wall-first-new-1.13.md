# First new fence and wall connection behavior: Minecraft 1.13

## Exact source identity

- Release: Minecraft `1.13` (the exact source version ID in the ready marker), Ornithe Feather namespace.
- Mapping coordinate: `net.ornithemc:feather-gen2:1.13+build.2`; mapping artifacts `feather-gen2-1.13+build.2-mergedv2.jar` and `feather-gen2-1.13+build.2.tiny`.
- Exact-run provenance: run `prep-1.13-1.13.1-feather-2026-10-09-r1`, batch `1.13,1.13.1`, exact requested version `1.13`; Vineflower 1.12.0, Java 25.0.3, Gradle 9.7.1. The exact-run command and raw artifact hashes are recorded in `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.13/ornithe-feather.provenance.json`.
- Source manifest: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.13/ornithe-feather.sources.sha256`, SHA-256 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. The SHA matches both `ornithe-feather.ready.json` and `ornithe-feather.provenance.json`.
- Source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.13/ornithe-feather/` (read-only).
- Actual file hashes verified against entries in that manifest:
  - `net/minecraft/block/FenceBlock.java`: `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`
  - `net/minecraft/block/WallBlock.java`: `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`
  - `net/minecraft/block/Block.java`: `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476`
  - `net/minecraft/block/FrostedIceBlock.java`: `096940541255ab371d6f197fbf22f30b99da48ff1bde8be5c3be15ba00a299be`
  - `net/minecraft/block/IceBlock.java`: `8c0a9bed03f026be289f183114c6bc71b7e321d6ab4e915921cd2f51e3ce0cec`
  - `net/minecraft/block/TransparentBlock.java`: `392cbb712f2e3cc70b23f40b917ed837958c37173a72f3a6aedb3403769c07f6`
  - `net/minecraft/block/TntBlock.java`: `43e398acc9d401a2ba8b20b0bc2cfd979a0662a41e3c29b2ef3d9aa75d513713`
  - `net/minecraft/block/PaneBlock.java`: `9422e3f2aa9843ecd041f5ced13d05a4c7472d697f7dfd838450e49969e5326c`
  - `net/minecraft/world/WorldView.java`: `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543`
  - `net/minecraft/entity/Entity.java`: `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`

## Exact boundary verdict

The first new behavior is present in the exact 1.13 source. The 1.13 `FenceBlock.java` and `WallBlock.java` hashes exactly equal their 1.13.2 hashes, independently confirming that these class bodies do not introduce the behavior only in the later patch release. The prior exact 1.12.2 Feather evidence has the old predicate; thus the endpoint boundary closes at the first 1.13 release (`1.12.2` old, `1.13` new), rather than remaining unknown within that gap.

In 1.13, `FenceBlock.shouldConnectTo(Block)` excludes `FROSTED_ICE` and `TNT` (`FenceBlock.java:52-61`), and `shouldConnectTo(BlockState, FaceShape)` applies that exclusion before accepting a solid face (`FenceBlock.java:46-50`). `getPlacementState` computes all four directional properties from current neighbor states (`FenceBlock.java:76-94`); `updateShape` refreshes the property for the changed horizontal neighbor (`FenceBlock.java:97-107`); the state definition registers those connection properties (`FenceBlock.java:110-113`).

In 1.13, `WallBlock.isExceptionForConnection` excludes the same two common blocks (`WallBlock.java:57-66`), while `shouldConnectTo` uses the solid-face and middle-pole/fence-gate branches (`WallBlock.java:51-55`). Placement computes four directions and `UP` (`WallBlock.java:69-93`); neighbor updates refresh the changed direction and recompute `UP` (`WallBlock.java:95-119`); state registration includes `UP` and all four directions (`WallBlock.java:121-124`). The 1.13 wall `UP` expression `(!N || E || !S || W) && (N || !E || S || !W)` is false exactly for the two straight-through patterns, so it is equivalent to 1.12.2's exclusion of those two patterns before OR-ing with “block above is not air.” This preserves the wall-top gate for the same flags and overhead block.

The common-block predicate consequence is established directly. In 1.12.2, both neighbor tests permit a solid face unless the block appears in the attachment/connection exceptions; neither 1.12.2 list names frosted ice or TNT. In 1.13 `Block.getFaceShape` still defaults to `FaceShape.SOLID` (`Block.java:399-400`), and neither `FrostedIceBlock`, its `IceBlock`/`TransparentBlock` ancestry, nor `TntBlock` overrides it. `Block.isExceptionForAttachment` remains a separate shared exclusion (`Block.java:271-273`). Therefore the new explicit exclusions are what switch these two existing neighbor types from connected in 1.12.2 to disconnected in 1.13. The 1.13 lists also contain carved pumpkin, but this is not an old/new behavior for a shared block type because 1.12.2 has no carved-pumpkin block.

## Player collision path at the first-new release

The direction flags feed collision geometry in 1.13. Fence and wall extend `PaneBlock`; its directional state index chooses the collision shape returned by `getCollisionShape` (`PaneBlock.java:29-35,82-100`). Wall additionally chooses its collision-shape array by the flags when `UP` is true (`WallBlock.java:22-28,36-39`). With only a north-side frosted-ice or TNT neighbor, the old behavior supplies a north arm and the 1.13 state does not. A player collision box overlapping the arm region while moving across its edge can therefore collide with that arm in 1.12.2 but not at the first 1.13 release.

This is supported as conditional geometry for a player-sized query, not as a specific trajectory prediction. In 1.13, `Entity.move` asks the world for collision shapes using the entity box and requested displacement, then clips movement along each axis against the returned shape (`Entity.java:578-598`). `WorldView.getBlockCollisions` gets each state's collision shape and tests overlap against the movement bounds (`WorldView.java:113-177`). These are the ordinary entity movement collision path; the source comparison establishes the shape input difference, while actual tick outcomes depend on trajectory and surrounding geometry.

## Reconciled history

The earlier immutable endpoint review is `source-review.md`, SHA-256 `cb4ad1dbc219ea80d9a7b7725df0f8674ef7cace1f0e843fc765b62b5d804246`. This file adds the exact 1.13 source identity and closes the first-new release boundary; it does not rewrite that prior review snapshot or claim a full-pair coverage freeze.
