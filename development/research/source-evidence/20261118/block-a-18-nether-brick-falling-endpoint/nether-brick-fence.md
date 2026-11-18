# 1.8 NETHER_BRICK_FENCE cue check

## Verdict

**ACCEPT for the pinned 1.8.9 fixture substitution. REVISE for a line-wide 1.8.x readiness claim.** The exact 1.8.9 NETHER_BRICK_FENCE provider has the same horizontal side/above cue result as the accepted ordinary oak fence arrangement. The local canonical source tree lacks per-release provenance/readiness sidecars for 1.8.0–1.8.8, so their source bytes cannot be bound here to exact mapping pins. No provider inventory or runtime claim is made.

## Exact 1.8.9 source and cue

`decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` identifies `1.8.9`, `ornithe-feather`, Feather gen2 1.8.9 build.2 merged archive and Tiny mapping; its ready marker says `ready` and binds the source manifest SHA-256. `Block.java`, `FenceBlock.java`, `LiquidBlock.java`, and `Material.java` each match their local `.sources.sha256` entries; their source SHA-256 values are respectively `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`, `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9`, `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5`, and `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`.

`Block.java` registers block ID 113 at `nether_brick_fence` using `new FenceBlock(Material.STONE, MapColor.NETHER)` and internal key `netherFence` (around lines 1068–1071); `Blocks.java` assigns `NETHER_BRICK_FENCE` from `getOrThrow("nether_brick_fence")` (line 344). Oak is the same `FenceBlock` class registered with `Material.WOOD` (line 1019). Neither overrides `isFaceSolid`.

For falling water at `LEVEL >= 8`, `LiquidBlock.getFlow` checks horizontal neighbors in order and short-circuits `isFaceSolid(side, direction) || isFaceSolid(side.up(), direction)` (lines 150–154). Its predicate rejects same-liquid material and ICE, then delegates; `Block.isFaceSolid` reads the neighbor block material's `isSolid()`. `Material.STONE` and `Material.WOOD` both inherit `Material.isSolid() == true`. With the fixture's side cell air, side fails; the fence at the above-side cell succeeds for WEST exactly as oak does. The predicate does not inspect connection properties or collision geometry.

The default fence properties are disconnected. `FenceBlock.addCollisions` then emits a center post with local minimum Y 0 (lines 36–70); translated to cell Y 65, its lowest world Y is 65. This is above the fixture's uncontracted player-box maximum of about 64.401, so the above-side fence shape does not collide with the player.

## Release-source readiness limit

The four files above have identical complete-file SHA-256 values in local `1.8.2` through `1.8.9` source trees. The local `1.8.0` and `1.8.1` files differ. Only `1.8.9` currently has the provenance, ready, and source-manifest sidecars in `decompiled_minecraft`; the corresponding per-release sidecars are absent for `1.8.0` through `1.8.8`. The identical local source bytes support the cue comparison for 1.8.2–1.8.8, but do not independently prove their exact artifact pins/readiness. The 1.8.x closure therefore remains open until those pins are supplied or verified.

## Immutable witness identity check

The supplied boundary witness content is present and its raw Git blob SHA-256 is the requested `7f5f2c31a35121c1acdef172f069e8cd28c0fba4c6618562286c184af0b25e7c`. Its repository HEAD is `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76d`, while the assignment names `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76e` (last hex digit differs and that commit is absent). The alias-audit and wooden-fence-audit commits and cited blob hashes match the supplied identities. This report is based on the exact witness bytes, while retaining the commit-ID discrepancy as an audit-integrity blocker for reusing the supplied commit reference literally.