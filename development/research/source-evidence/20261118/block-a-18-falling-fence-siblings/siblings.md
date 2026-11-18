# Exact 1.8 wood-fence sibling cue closure

## Verdict

**CLOSED, no code change.** For all five assigned ordinary wooden fence variants—`SPRUCE_FENCE`, `BIRCH_FENCE`, `JUNGLE_FENCE`, `ACACIA_FENCE`, and `DARK_OAK_FENCE`—the exact Ornithe Feather build.2 sources for each selectable 1.8 alias (1.8.0 through 1.8.9) provide the same falling-water horizontal side/above solidity cue and permit the accepted oak call-boundary fixture placement without player collision. This adds only the five registered variants to the already accepted oak provider result. It does not repeat or broaden the accepted oak fixture, evaluate other fence families, or claim gameplay/runtime parity.

## Registration and shared provider

For exact 1.8.0, `Block.java` registers all five names at lines 1124–1128 using the same provider class, `new FenceBlock(Material.WOOD)`. For exact 1.8.1, `Block.java` registers them at lines 1210–1249 using `new FenceBlock(Material.WOOD, PlanksBlock__Variant.<wood>.getColor())`. The second constructor only passes `MapColor` to the base constructor; `FenceBlock` retains the same collision method and has no `isFaceSolid` override. `Block.isFaceSolid` delegates to `getMaterial().isSolid()`. The material is the identical static `Material.WOOD` in every case; `Material.isSolid()` returns true by default. The variant map color does not enter either face-solid or collision logic.

The same registered aliases 1.8.2–1.8.9 have identical whole source files for `Block.java`, `FenceBlock.java`, `LiquidBlock.java`, and `Material.java`. Each 1.8.2–1.8.8 file was checked against its own exact release provenance source entry; the 1.8.9 source hashes match its ready source manifest. Thus the five registrations and shared provider behavior are unchanged across that group.

In each 1.8–1.8.9 `LiquidBlock.getFlow`, the falling `LEVEL >= 8` path asks `isFaceSolid(world, side, direction) || isFaceSolid(world, side.up(), direction)` in side-then-above short-circuit order. `LiquidBlock.isFaceSolid` rejects a neighbor of the liquid's own material and ICE, accepts UP, and otherwise calls `Block.isFaceSolid`. A dry wooden fence has `Material.WOOD`, distinct from WATER, and is not ICE; the selected horizontal face therefore reaches `Material.WOOD.isSolid() == true`. The test never reads fence shape, connection bits, outline/collision shape, `solidRender`, or `isCube`. All five sibling variants consequently satisfy the same above-side cue as the accepted oak fixture.

## Fixture placement and collision

Reuse the accepted fixed oak arrangement and call-boundary player state from `falling-current-boundary` unchanged, replacing only the oak block at `(-1,65,0)` with one of the five siblings. In that fixture, the player's contracted scan box has maximum Y `64.0`; the uncontracted player collision box has maximum Y approximately `64.401`, still below the fence's lower block boundary `Y=65`. `FenceBlock.addCollisions` builds only local shapes with minimum Y `0` (and maximum Y `1.5` for raised fence collision segments); translated to the fixture's fence cell these start at world Y `65`. Therefore none intersects the player, regardless of connection bits. `getFlow` only reads this placed block as a face-solidity cue and does not require collision overlap. No fluid state is introduced on the fence.

The inherited accepted oak witness already establishes the remaining conditions: exact falling-water level, queried side and above-side positions, scan/load conditions, and fixed player shape. This worker checked only the sibling provider substitution and its source equivalence.

## Exact source bindings

All paths are relative to `decompiled_minecraft/<release>/ornithe-feather/`.

- 1.8: `net/minecraft/block/Block.java` 282–284, 1124–1128; `net/minecraft/block/FenceBlock.java` 18–81; `net/minecraft/block/LiquidBlock.java` 74–83, 129–165; `net/minecraft/block/material/Material.java` 7, 53–55.
- 1.8.1: `net/minecraft/block/Block.java` 289–291, 1210–1251; `net/minecraft/block/FenceBlock.java` 20–85; `net/minecraft/block/LiquidBlock.java` 75–84, 130–166; `net/minecraft/block/material/Material.java` 7, 53–55.
- 1.8.2–1.8.8: for each exact release, the full `Block.java`, `FenceBlock.java`, `LiquidBlock.java`, and `Material.java` files are byte-identical to 1.8.9 and each matches its release's `.decompile-provenance.json` source hash.
- 1.8.9: `net/minecraft/block/Block.java`, `FenceBlock.java`, `LiquidBlock.java`, and `material/Material.java` match `ornithe-feather.sources.sha256`; the accepted fixture source identity is also recorded in `falling-current-boundary`.

Whole-file SHA-256 for the four mapped source files, in order Block / FenceBlock / LiquidBlock / Material:

- 1.8.0: `4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814` / `2e75c087705278524a3a54acf3b56c4a476294357a11741640b457cea4e781e1` / `e4ef9385d025aa4672d4b6a55afc72803267d0721469bf036217ab30a7760fe3` / `a27bcc70e2712f150d8428773f8c2aafe98af9100a4fd05653057044d7d248a9`.
- 1.8.1: `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c` / `0a4b6623a3b5d59b521f082676b2a7400d40d696ee239da7f74c146487637271` / `36d345c4b0985902ed265602e391982cdc13bc447906e219d3efe568aed4676a` / `c05f393fe2b5e77b96dac85250751bed03cad2be6377fc5c10209904c1f185bf`.
- 1.8.2–1.8.9 common quartet: `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` / `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` / `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` / `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`.

## Accepted dependency identity and limits

- Existing oak source witness: `falling-current-boundary`, commit `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76d`, `evidence.md` raw blob SHA-256 `7f5f2c31a35121c1acdef172f069e8cd28c0fba4c6618562286c184af0b25e7c` (verified by binary `git show`).
- Existing accepted 1.8 alias applicability addendum: `falling-current-18-alias-audit`, commit `5a64a143033ca2d8b6d94d4e466af53182fac9f6`, `acceptance-addendum.md` raw blob SHA-256 `47335f7f9c4fc2608124a57fee06f55eeedf2328cd8f0f872006833a04cb0e36` (verified by binary `git show`).
- The canonical finding `development/research/findings/1.11.2--1.12/falling-water-oak-fence-face.md` binds the named residual to the older oak-only provider claim; it is not used here to infer another material.

No product/history/wiki/backlog was read or changed. No source was generated or modified, and no build, test, runtime, game launch, or push was performed. Python was unavailable in this Windows environment; the two assigned reference blob hashes were instead verified from binary `git show` stdout streams, without text conversion.
