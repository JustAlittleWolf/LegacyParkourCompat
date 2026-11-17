# Fence glass collision: source-review supplement

## Bound claim and outcome

Independent source verdict: ACCEPT the bounded source claim that old fence connection rules reject plain and stained glass, while the 1.14+ rules use full-face sturdiness and omit glass from their exception table. The 1.13, 1.13.1, and 1.13.2 Feather `FenceBlock.java` files are byte-identical (SHA-256 `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`). This supplement records the exact witness through 1.14 and 1.14.4; it does not assert an intermediate patch cutover or broader behavior.

## Exact-source witness

- 1.13, 1.13.1, 1.13.2, Ornithe Feather: `net/minecraft/block/FenceBlock.java`, `shouldConnectTo` and four neighbor-state assignments (`1.13.2`: lines 46–54, 84–107). `shouldConnectTo` rejects a block when `Block.isExceptionForAttachment` is true. The three FenceBlock source hashes are all `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2`.
- 1.13, 1.13.1, 1.13.2, Ornithe Feather: `net/minecraft/block/Block.java`, `isExceptionForPlacement` / `isExceptionForAttachment` (`1.13.2`: lines 241–258). Plain `Blocks.GLASS` and `StainedGlassBlock` are in the placement exceptions inherited by attachment exceptions. The .1 and .2 Block files share SHA-256 `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4`; 1.13 uses `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` and has the same relevant exception predicates.
- 1.14, Ornithe Feather: `net/minecraft/block/FenceBlock.java`, `shouldConnectTo`, the four directional neighbor assignments and `updateShape` (lines 43–47, 74–92); `net/minecraft/block/Block.java`, `isExceptionForAttachment` (lines 249–256). Connectivity takes `Block.isFaceSolid` for each horizontal neighbor, and the exception list omits plain and stained glass. FenceBlock SHA-256 `99bf66fc30039a136940b25a3d05c5e9035b857f26e4884f68c7b237f54ec8d7`; Block SHA-256 `0308b22d47062863ee36db68b907d42c7a95e060d5841f4d6e9cf67eda66fd6a`.
- 1.14.4, Mojmap: `net/minecraft/world/level/block/FenceBlock.java`, `connectsTo`, `getStateForPlacement`, and `updateShape` (lines 43–50, 67–96); `Block.java`, `isExceptionForConnection` and `isFaceSturdy` (lines 259–266, 403–409); `CrossCollisionBlock.java`, `makeShapes` and `getCollisionShape` (lines 41–90). Four horizontal faces are evaluated symmetrically; sturdy glass is accepted because the connection-exception table omits it. The shared shape builder supplies the corresponding rail shape. SHA-256: FenceBlock `d45b20c3b1280285b823a5a8bdab8cfb526fd5cda41c33d83fee75dbb17f6f3e`; Block `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f`; CrossCollisionBlock `5088eb166ca1ca36eb496ab120338d713c30bec84ff5815ec266a52ea1444ecd`.

## Mapping and provenance check

Source paths identify the compared mapping families: Ornithe Feather for 1.13–1.14 and Mojmap for 1.14.4. The source directories are legacy outputs without `.decompile-provenance.json`, so their mapping artifact provenance is not independently pinned by a source manifest. The 1.14.4 local version metadata is pinned at SHA-256 `615f466a39d2c19ae9b6e2401aa7bd07dbda337f976bcce383326b8d6ba4e532`; it names the Mojang client mappings by SHA-1 `6073e4ba6949217eb708c4512be2ccc1850a603f`. File SHA-256 values above verify the checked-in-place source bytes, not their original mapping artifact chain.

## Existing product disposition (separate from source acceptance)

`src/client/java/me/wolfii/legacyparkourcompat/change/v1_13/FenceGlassCollision.java` is annotated `emulates = V1_13_1`. It trims the glass-facing connected rail from the collision shape for plain and stained glass, checking all four horizontal directions and the listed era fences. It preserves native block state and other connection decisions. This is consistent with the accepted source behavior. No product change, version-boundary expansion, runtime-parity claim, build, test, or game launch was performed here.
