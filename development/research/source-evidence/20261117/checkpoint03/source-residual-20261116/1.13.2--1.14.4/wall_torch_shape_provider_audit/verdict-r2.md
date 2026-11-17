# Fresh audit: wall torch shape provider (r2)

Audited report SHA-256: `2fb7646fd8891c5c42e261483592a5f3361e0cdf26a4068e2a1029ba9a50f530`.

## Verdict

**PASS — correction confirmed; no source-confirmed movement delta in this bounded provider slice.** The corrected delegation statement is accurate: 1.13.2 `RedstoneWallTorchBlock.getShape` delegates to `Blocks.WALL_TORCH.getShape(state, world, pos)`; 1.14.4 delegates to `WallTorchBlock.m_68832158(state)` directly. Both paths select the same facing-indexed wall-torch shape map. Both registered blocks opt out of collision, and the ordinary player collision path requests collision shapes, which are empty for these blocks. This audit is limited to the wall torch provider and does not establish pair coverage.

## Evidence checked

- `decompiled_minecraft/1.13.2/ornithe-feather.verification.json` names version `1.13.2`, namespace `ornithe-feather`, status `verified-identical-source-and-input-artifacts`, and source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. `decompiled_minecraft/1.14.4/ornithe-feather.verification.json` records the same status and manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.
- Those hashes match `ornithe-feather.sources.sha256` in each version directory. The manifest entries for the torch classes match their on-disk SHA-256 values: 1.13.2 `WallTorchBlock.java` `f06b2a546ba8465627511969efc1203e654a1af3ccf61d27aed9b28b00c77462`, `RedstoneWallTorchBlock.java` `14551c617a070a3591bfc3fac571ade37bd7f2324c6360e3936ee792a8f9d8da`; 1.14.4 `WallTorchBlock.java` `ff6966b0f5236251c86776c156f5aa57fa214ef50a0a78509a126d916b92db5f`, `RedstoneWallTorchBlock.java` `237ba36f03e040c4ff1531d46887bef719698fe07d580f375947cdf7c4243c16`.
- 1.13.2 `RedstoneWallTorchBlock.java:34-35` calls `Blocks.WALL_TORCH.getShape(state, world, pos)`. 1.14.4 `RedstoneWallTorchBlock.java:35-36` calls `WallTorchBlock.m_68832158(state)`.
- The shape maps are identical in both `WallTorchBlock.java` files: `FACING = HorizontalFacingBlock.FACING`; NORTH `(5.5,3,11)-(10.5,13,16)`, SOUTH `(5.5,3,0)-(10.5,13,5)`, WEST `(11,3,5.5)-(16,13,10.5)`, EAST `(0,3,5.5)-(5,13,10.5)`. Both default facing to NORTH. In 1.13.2 `getShape` returns `SHAPES.get(state.get(FACING))`; in 1.14.4 it calls the static helper, which performs that same lookup.
- Registry wiring confirms concrete owners. In 1.13.2 `Block.java:987` registers `wall_torch` as `WallTorchBlock` with `noCollision()`; `Block.java:1063-1064` registers `redstone_wall_torch` as `RedstoneWallTorchBlock` with `noCollision()`. In 1.14.4 `Blocks.java:397-399` and `549-553` register the corresponding owners and both properties include `noCollision()`.
- Collision reachability is as reported: 1.13.2 `Block.java:393-394` returns `VoxelShapes.empty()` when `hasCollision` is false; `Properties.noCollision()` sets that flag false at `1943-1944`; ordinary movement obtains world collisions at `Entity.java:580` (and again at `612`). 1.14.4 `Block.java:376-377` applies the same false-flag empty-shape gate; `Entity.java:700` includes `world.getBlockCollisions(...)`, whose `WorldView.java:133-172` implementation gathers collision shapes from block states. Thus the nonempty outline shape is not an ordinary movement obstacle for these registered blocks.

## Scope note

No wiki, product implementation, product history, backlog, tests, build, game launch, or source generation was consulted or performed. No source finding is proposed by this provider audit.
