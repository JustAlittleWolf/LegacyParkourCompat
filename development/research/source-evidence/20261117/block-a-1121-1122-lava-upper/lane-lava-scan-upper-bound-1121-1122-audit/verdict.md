# Independent source audit verdict: lava scan upper bound, 1.12.1 → 1.12.2

**Verdict: no source difference in the bounded lava-material scan or its effective client read path.** The claim that 1.12.2 changed the scan’s Y upper bound is not supported by these exact published sources.

## Source identity and integrity

- Host/runtime: Windows 11 x64, PowerShell, Eclipse Adoptium Java 25.0.3 LTS recorded in the 1.12.1 provenance. No source generation, network access, build, tests, or runtime launch was performed.
- Older source: Minecraft 1.12.1, Ornithe Feather; `decompiled_minecraft/1.12.1/ornithe-feather/.decompile-provenance.json` SHA-256 `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8`. It pins Feather-gen2 1.12.1+build.2, client/version metadata, tooling and per-source hashes.
- Newer source: Minecraft 1.12.2, Ornithe Feather; version-root `ornithe-feather.provenance.json` SHA-256 `747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE`; `ornithe-feather.sources.sha256` SHA-256 `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA`. The ready marker declares 2,050 source files and binds that source-manifest digest.
- Programmatic SHA-256 checks passed for 34 versioned source-file copies (17 paths × 2 releases) in the core roster/dependency closure, plus 14 versioned copies in material/state addenda and 2 in the rounding-helper addendum, against their respective published source manifests. The comparison consumed published files read-only.
- Evidence artifact SHA-256: `source-roster.md` `BD8983F8314E96C16A7106DB06D2BBFB46C5E286604EE73E065B398BB59D0A75`; `source-roster-addendum.md` `0CAA11AEE5EC0A18AAAF3AE91AF065D0D459919ACD88A65369A0B117641E9716`; `source-roster-addendum-material.md` `1A3515A9BD618C891B45C2C43305DA0712E5AC3DA7B8FA6E2B57685252DC5726`; `source-roster-addendum-state.md` `1AD08B6F0748BA7197B23074DFCF36FF1C0C4FE6D39E82D0FFBBAC039A356445`; `source-roster-addendum-materialimpl.md` `DE2456877B28F7F316FA4E03ADBB23A0429A1FE11C7B83D48FDA8BFEE4756ABA`; `source-roster-addendum-y.md` `DDEFCC5CC29D2A1F0E6D8252794E6CEC0330D3FD50672599D9F13B01BAEC7840`. The assigned author roster SHA-256 was checked separately and matched the supplied identity `25ed784967a02cf7c41a2530a3fe91111e133534d4516992de1ff3404fe3edf0`; its contents and other author drafts were not read.

## Findings

### Y upper-bound operation — no difference

In both releases, `Entity.isInLava()` passes `getShape().grown(-0.1F, -0.4F, -0.1F)` and `Material.LAVA` to `World.containsMaterial(Box, Material)` (`Entity.java:1045–1047`). The entity source file hash is identical across the pair.

Both `World.containsMaterial` bodies (`World.java:1623–1645`) compute X/Y/Z minima with `MathHelper.floor` and maxima with `MathHelper.ceil`; nested loops run from each minimum while strictly less than its corresponding maximum. The Y interval is therefore `[floor(minY), ceil(maxY))`: a fractional maximum includes its containing block coordinate, while an exact integer maximum is excluded. The coordinate order, casts, bounds, loop conditions, material equality, early return and pooled-position release sequence match exactly in both versions. `MathHelper.ceil(double)` (`MathHelper.java:67–70`) also matches: cast to `int`, increment only when `x > i`. No changed upper bound or scan-order behavior is present in this pair.

### Reachable local-client path — unchanged at the audited call sites

`LocalClientPlayerEntity.tick()` calls `super.tick()` when its world chunk predicate passes (`LocalClientPlayerEntity.java:164 in 1.12.1 / :165 in 1.12.2`). The no-argument `World.isChunkLoaded(BlockPos)` delegates with `allowEmpty=true` (`World.java:183–190`); the client override returns `allowEmpty || !chunk.isEmpty()` (`ClientWorld.java:105–106`), so that tick gate admits an empty client chunk. During the local player tick, its input state is consumed and copied to the player movement/jump fields (`LocalClientPlayerEntity.java:630–635 in 1.12.1 / :631–636 in 1.12.2 copies movement/jump inputs; input sampling is at :693 / :698`). `LivingEntity.tick()` processes the jump predicate (`LivingEntity.java:1827–1834`) and then travel through `moveRelative` (`:1844–1848`); the lava-conditioned branches and locally-controlled gate in `moveRelative` (`:1425–1429`) are the same in both versions. The local player reports `isLocallyControlled() == true` (`LocalClientPlayerEntity.java:484–486 / :485–487`). These call-site excerpts are unchanged; the local-player file has version-specific unrelated source changes.

### Effective client block/material read — no allowEmpty short-circuit in scan

`World.containsMaterial` invokes `getBlockState` directly for every scanned coordinate; it does not call `isChunkLoaded`, `isAreaLoaded`, or inspect an `allowEmpty` flag. `World.getBlockState` returns `Blocks.AIR` outside Y `[0,256)` and otherwise obtains the chunk then calls its block-state getter (`World.java:680–687`). `World.getChunkAt` delegates to its chunk source; the client source is `ClientChunkCache`, whose `getChunk` returns the loaded chunk or its `empty` sentinel (`ClientChunkCache.java:56–58`). `EmptyChunk.getBlockState` returns `Blocks.AIR.defaultState()` (`EmptyChunk.java:40–42`). Thus a scan that reaches an unloaded client chunk reads AIR from the sentinel; the `allowEmpty=true` chunk-loaded predicate is only a separate boolean gate and does not make this material scan stop or force a different block read. `WorldChunk` also returns AIR for a missing section in ordinary non-debug worlds (`WorldChunk.java:361–383`). This chain is unchanged in the inspected pair.

`BlockState` inherits the `getMaterial()` contract from `BlockProperties`; `StateDefinition` delegates the access to its block, and `Block.getMaterial(BlockState)` returns the block material (`StateDefinition.java:224–226`, `Block.java:148–150`). `Material`, `LiquidBlock`, `Blocks`, and the fluid state classes in the frozen/addendum inventory were checked as dependencies; no scan-related difference was found.

## Scope and limit

This is an independent verdict for the exact 1.12.1/1.12.2 pair and this single lava scan operation/read path. It does not review product implementation or establish behavior at any other release boundary. No portal UI/screen behavior was used as evidence.
