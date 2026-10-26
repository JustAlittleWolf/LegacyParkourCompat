# Fence and wall neighbor connection boundary review

## Inputs and provenance

- Older endpoint: Minecraft 1.12.2, Ornithe Feather / `feather-gen2-1.12.2+build.2`; exact-run batch `1.10.2,1.11.2,1.12.2`; source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- Newer endpoint: Minecraft 1.13.2, Ornithe Feather / `feather-gen2-1.13.2+build.2`; exact-run batch `1.13.2`; source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.
- Both `ornithe-feather.verification.json` records say `verified-identical-source-and-input-artifacts`, with zero source file differences and source manifest hashes matching their ready markers. The recorded artifact difference is only a regenerated derived TinyRemapper jar; raw input artifacts and published source trees match.
- Verified source file SHA-256 values:
  - 1.12.2 `FenceBlock.java`: `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e`
  - 1.12.2 `WallBlock.java`: `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`
  - 1.12.2 `Block.java`: `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`
  - 1.13.2 `FenceBlock.java`: `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`
  - 1.13.2 `WallBlock.java`: `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf`
  - 1.13.2 `Block.java`: `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`

## Source verdict

The fence candidate is independently supported for common blocks present at both endpoints. In 1.12.2, `FenceBlock.shouldConnectTo` rejects the shared attachment exceptions, barrier, melon, pumpkins and lit pumpkin, then accepts a solid neighbor face (`FenceBlock.java:124-138`). Its resolver recalculates all four horizontal properties from the current world (`FenceBlock.java:163-167`), and both `getShape` and collision collection call that resolver (`FenceBlock.java:59-85`). In 1.13.2, the predicate is equivalent for the shared set but additionally excludes frosted ice and TNT; placement initializes all horizontal properties and `updateShape` updates the direction whose neighbor changed (`FenceBlock.java:46-61,76-107`). The connection properties are registered in `buildStateDefinition` (`FenceBlock.java:111-113`).

The wall candidate is independently supported. In 1.12.2, `WallBlock.shouldConnectTo` uses the same solid-face rule plus the middle-pole/fence-gate cases, and its connection exception list ends at lit pumpkin (`WallBlock.java:146-160`). `resolveVirtualProperties` recomputes four directions and `UP`, and shape/collision methods resolve from the current world (`WallBlock.java:83-102,190-201`). In 1.13.2, placement computes all four directions and `UP`; `updateShape` refreshes only the changed horizontal direction (while recomputing `UP`), and state registration includes those properties (`WallBlock.java:51-65,69-124`). Its exception list adds frosted ice and TNT for common block types.

The 1.13.2 predicates also list carved pumpkin. That block is not in the 1.12.2 endpoint, so it does not support an old-versus-new behavior claim for an identical block type. The common-block connection delta established here is specifically frosted ice and TNT. For each, the old block inherits `Block.getFaceShape`, whose default is `FaceShape.SOLID` (1.12.2 `Block.java:362-363`); neither `FrostedIceBlock`, its `IceBlock`/`TransparentBlock` parents, nor `TntBlock` overrides that method. In 1.13.2 the same default is `Block.java:383-384`; the corresponding inheritance chain also has no override.

## Player collision meaning and concrete geometry

This is a conditional geometry/state difference, not a claim about a fixed trajectory. The old fence collision collector includes a central post and adds a directional arm only when its resolved connection flag is true; for example, the north arm spans local Z `0.0..0.375` and X `0.375..0.625`, with collision height `1.5` (`1.12.2 FenceBlock.java:46-50,59-79`). With frosted ice or TNT north of the fence, that arm is included in 1.12.2 and omitted in 1.13.2. A player moving south with the bounding box centered on the fence's X axis can therefore contact the old arm before reaching the central post; the changed arm participates in the player's ordinary swept-box collision query. The old world collision scan passes its queried box to each block's `addCollisions` (`1.12.2 World.java:964-1019`), and inherited `Entity.move` consumes world collisions (`1.12.2 Entity.java:462 onward`). In 1.13.2 the registered direction properties select the pane/fence collision shape (`PaneBlock.java:29-35,82-100`); `WorldView.getBlockCollisions` fetches each block state's collision shape and filters it against the swept player box (`WorldView.java:112-150,154-177`), and `Entity.move` checks those collisions (`1.13.2 Entity.java:533 onward`).

For walls, the per-state collision shape changes with the same connection flags. A north-only versus no-north wall has a different collision shape (`1.12.2 WallBlock.java:28-62,90-103`; 1.13.2 `WallBlock.java:19-38`). A player box that overlaps the northern arm region while crossing beside the center post thus encounters the old arm and not the new one when the only northern neighbor is frosted ice or TNT. These examples establish a player-sized collision consequence conditional on an overlapping movement query; they do not establish an end-to-end trajectory or a specific tick outcome.

## Boundary and review gates

The endpoint evidence establishes the old behavior in 1.12.2 and the new behavior in 1.13.2. It does not establish the first patch release inside the interval; that exact cutover remains unverified. Source acceptance for these candidates is limited to the immutable prior drafts whose hashes were verified before review:

- Fence draft SHA-256: `ce434bd19f6e93e92641db232f477854619cb672b7d7241831617e047dec2ddc`.
- Wall draft SHA-256: `fe15f25bc6a2230f9dfe1efd37fff408817043f5032a42ff4cd345dfdd2a3153`.

This review supports the source-level neighbor-predicate claim at the specified endpoints. It is not a full pair freeze or a claim about intermediate releases.
