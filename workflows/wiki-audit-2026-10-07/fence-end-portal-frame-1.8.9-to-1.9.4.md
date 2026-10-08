# Finding snapshot: fence connection to end portal frames

Snapshot ID: `wiki-fence-end-portal-frame-1.8.9-1.9.4`

Status: source-confirmed bounded finding; independent Wiki-lane review pending; runtime validation not performed.

## Candidate and boundary

The [Minecraft Wiki Hitbox page](https://minecraft.wiki/w/Hitbox) is a lead for historical fence collision-shape changes. This exact-release comparison identifies one bounded neighbor-state difference for a block present in both versions: a fence next to an End Portal Frame. It establishes a net source difference in `(1.8.9, 1.9.4]`; it does not identify the first snapshot containing the change or adjudicate all fence states.

## Finding

For an End Portal Frame adjacent to a fence, 1.8.9 treats the neighbor as a cube for the fence connection predicate, while 1.9.4 does not. In 1.8.9, `EndPortalFrameBlock` uses `Material.STONE` and does not override `Block.isCube()`, whose inherited implementation returns `true`. `Material.STONE` is solid-blocking in both releases. The 1.8.9 `FenceBlock.shouldConnectTo()` therefore returns true for this neighbor (the pumpkin exclusion does not apply), and `addCollisions()` includes the matching cardinal arm.

In 1.9.4, `EndPortalFrameBlock.isCube(BlockState)` explicitly returns `false`. The resolved block state's `isCube()` delegates to that block method. `FenceBlock.shouldConnectTo()` consequently returns false for an End Portal Frame neighbor, so the direction flag is false and the fence does not add the arm on that side. This changes the fence's player collision geometry next to this state. The finding is specific to this source-level connection path; it does not claim that every fence state or the central post changed.

## Exact source evidence and identities

All source paths are rooted at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\<version>\ornithe-feather`. Each listed source hash was recomputed from the ready tree and matched the corresponding row in that version's source manifest. The ready JSON's source-manifest and artifact-manifest hashes also matched the recomputed manifest-file hashes; neither manifest was changed.

| Version | Source path and checked range | SHA-256 |
|---|---|---|
| 1.8.9 | `net/minecraft/block/FenceBlock.java`, `addCollisions()` 36-81 and `shouldConnectTo()` 128-136 | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| 1.8.9 | `net/minecraft/block/EndPortalFrameBlock.java`, constructor/material 23-25; no `isCube()` override | `c2c876aefe34d001ff0e3eebddd0df01ee85b0bf499e205eb1eb95ec44857b9e` |
| 1.8.9 | `net/minecraft/block/Block.java`, inherited `isCube()` 245-247 | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| 1.8.9 | `net/minecraft/block/material/Material.java`, `STONE` 5-8, `blocksMovement()` 68-70, `isSolidBlocking()` 100-101 | `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2` |
| 1.9.4 | `net/minecraft/block/FenceBlock.java`, collision boxes and `addCollisions()` 45-76; `shouldConnectTo()` 120-130 | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |
| 1.9.4 | `net/minecraft/block/EndPortalFrameBlock.java`, constructor/material 33-35 and `isCube(BlockState)` 108-111 | `1c6495a6d6c5d777eb643983c7e7b8151bb5b99ef1a4a3b991655792a0ad58eb` |
| 1.9.4 | `net/minecraft/block/state/StateDefinition.java`, `BlockStateImpl.isCube()` 267-270 delegates to `this.block.isCube(this)` | `10ba661985c87801e1bb7e941399498e89fb67e1bd9ead2869c00d39ffff6493` |
| 1.9.4 | `net/minecraft/block/material/Material.java`, `STONE` 7-10, `blocksMovement()` 70-72, `isSolidBlocking()` 102-103 | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` |

The 1.8.9 source-manifest SHA-256 is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; its artifact-manifest SHA-256 is `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`. The 1.9.4 source-manifest SHA-256 is `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248a37a27a1b4d77`; its artifact-manifest SHA-256 is `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.

The source manifests establish the hashes for the Java files; artifact-manifest verification establishes the ready bundle's input identity. The Feather-derived JARs for 1.8.9 and 1.9.4 are not used to make a bytecode claim here. Where revised Feather artifacts are discussed elsewhere in this audit, equivalence to the unavailable original derived JARs remains **unproven**.

## Applicability and exclusions

The source-level consequence is a change to a fence's neighboring collision arm when its side touches an End Portal Frame. It concerns player collision with a block shape. It does not claim a change to block states, End Portal Frame collision itself, non-player movement, or every fence connection case. Runtime validation was not performed.
