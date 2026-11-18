# Independent 1.8.x Nether Brick Fence applicability audit

**Terminal verdict: ACCEPT** for substituting `NETHER_BRICK_FENCE` into the already accepted 1.8–1.8.9 falling-water upper-Y fixture. No blocker remains within this bounded claim. This audit covers provider applicability only; it adds no scan/provider inventory, new caller effect, runtime result, or broader material claim.

## Immutable inputs

- Initial narrow report: `bfd0332c9fcf33cce49b17c46284972d5ba0a553:nether-brick-fence.md`; raw blob SHA-256 `c33cf5d46dda93a23931ab008a0eded42eeed28013c86ff5f4e2d2d7cf401ab7`.
- Corrected provenance addendum: `d219f5e84405ad6a24e6b7c68f16bc655b9dabab:provenance-addendum.md`; raw blob SHA-256 `2937f21bf475879fd3d131fc85218e0c96187f10c3e9301d15a68d96e428efc0`.
- Accepted boundary witness reused for the fixture and original caller effect: `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76d:evidence.md`; raw blob SHA-256 `7f5f2c31a35121c1acdef172f069e8cd28c0fba4c6618562286c184af0b25e7c`.
- Accepted same-base 1.8 alias audit reused solely for the prior caller effect: `5a64a143033ca2d8b6d94d4e466af53182fac9f6:acceptance-addendum.md`; raw blob SHA-256 `47335f7f9c4fc2608124a57fee06f55eeedf2328cd8f0f872006833a04cb0e36`.

The specified hashes were recomputed from raw blob bytes. The corrected addendum fixes the initial report's sidecar-location and commit-ID concerns.

## Source and applicability trace

Read-only canonical source roots were `decompiled_minecraft/<release>/ornithe-feather` for 1.8–1.8.8 and the corresponding flat source tree for 1.8.9. For every release, its own provenance/ready source manifest pins `Block.java`, `FenceBlock.java`, `LiquidBlock.java`, and `material/Material.java`; all 40 file hashes match. Each 1.8–1.8.8 hidden `.decompile-provenance.json` pins that exact release's Feather gen2 build.2 mergedv2 mapping artifact. The 1.8.9 flat manifest and ready marker bind the source files; `artifacts.sha256` pins its mergedv2 JAR at `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` and Tiny mapping at `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63`.

The per-release source SHA-256 tuples below are ordered `Block.java`, `FenceBlock.java`, `LiquidBlock.java`, `Material.java`; each tuple is verified against that release's own pin:

- 1.8: `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814`, `2e75c087705278524a3a54acf3b56c4a476294357a11741640b457cea4e781e1`, `e4ef9385d025aa4672d4b6a55afc72803267d0721469bf036217ab30a7760fe3`, `a27bcc70e2712f150d8428773f8c2aafe98af9100a4fd05653057044d7d248a9`.
- 1.8.1: `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c`, `0a4b6623a3b5d59b521f082676b2a7400d40d696ee239da7f74c146487637271`, `36d345c4b0985902ed265602e391982cdc13bc447906e219d3efe568aed4676a`, `c05f393fe2b5e77b96dac85250751bed03cad2be6377fc5c10209904c1f185bf`.
- 1.8.2–1.8.9: `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`, `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9`, `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5`, `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`.

The source bytes distinguish 1.8 from 1.8.1; 1.8.2–1.8.9 share these four complete-file hashes. Exact registration/member continuity is confirmed in each `Block.java` and `Blocks.java`: block ID 113 / key `nether_brick_fence` constructs `FenceBlock(Material.STONE)` in 1.8, and `FenceBlock(Material.STONE, MapColor.NETHER)` thereafter; `Blocks.NETHER_BRICK_FENCE` resolves `nether_brick_fence`. Oak uses the same `FenceBlock` with `Material.WOOD`.

Across the pinned source sets, `FenceBlock` inherits base `Block.isFaceSolid`, which asks the neighbor material's `isSolid`; both `Material.STONE` and `Material.WOOD` return true. `LiquidBlock.getFlow`'s falling-level branch probes each horizontal side and then `side.up()` in short-circuit order. In the fixed fixture the same-height side is air and the above-side fence passes the inherited material predicate, matching oak's cue. It does not depend on the fence connection bits or collision geometry.

The fence constructor sets every connection property false by default. For that disconnected state, `FenceBlock.addCollisions` emits its center post beginning at local Y=0, so at fixture cell Y=65 the shape begins at world Y=65. The accepted fixture's player box ends near Y=64.401, leaving about 0.599 blocks of vertical clearance. The provider cue therefore applies without the above-side fence colliding with the fixture player.

## Scope boundary

This accepts only NETHER_BRICK_FENCE substitution in the already accepted fixed 1.8.x upper-Y fixture, including the pinned 1.8.0–1.8.9 source sets. The previous oak caller effect is reused from its accepted evidence; it was not rediscovered here. No extrapolation is made to other materials, other source spans, or runtime behavior.
