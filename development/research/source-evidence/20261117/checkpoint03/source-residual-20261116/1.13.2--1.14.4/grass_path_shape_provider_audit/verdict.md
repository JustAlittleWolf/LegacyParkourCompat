# Independent audit verdict: GrassPath direct shape provider

## Immutable claim and evidence identity

Audited `build/source-residual-20261116/1.13.2--1.14.4/grass_path_shape_provider/result.md`, SHA-256 `a6caad3c938a5546e1b3780e8f6082f334aa889e737dccc0f7ded090681b5581`.

The exact source manifest hashes match the supplied identities and the current manifest files:

- 1.13.2 Feather: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`
- 1.14.4 Feather: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`

Both versions' readiness and verification records report these manifest identities and zero published source-file differences. I independently matched the manifest SHA-256 entries to the files used for this claim: `GrassPathBlock.java`, `FarmlandBlock.java`, `Block.java`, `Blocks.java`, `BlockState.java`, `WorldView.java`, `Entity.java`, and `LocalClientPlayerEntity.java` on each side.

## Verdict

**Accept the bounded conclusion: no direct GrassPath collision-shape difference is established for 1.13.2 versus 1.14.4, and no finding is warranted for this operation.**

The block join is adequately grounded in `minecraft:grass_path`: 1.13.2 `Block.java` creates and registers a `GrassPathBlock` (1599–1600), and `Blocks.java` resolves `GRASS_PATH` from that key (1098). In 1.14.4, `Blocks.java` registers `GRASS_PATH` directly under the same key (1428–1429). Both constructions use `Material.DIRT`, strength `0.65F`, and grass sounds. The class has no GrassPath-specific state-property declaration in either version.

Both `GrassPathBlock.java` files set `SHAPE = FarmlandBlock.SHAPE` (line 17), and both `FarmlandBlock.java` files define that shared shape as `Block.block(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)` (line 24 in 1.13.2; line 25 in 1.14.4). GrassPath's shape override returns this constant on both sides (1.13.2 lines 55–58; 1.14.4 lines 55–58), without consulting world, position, or context. Thus the returned provider shape is the same 1×15/16×1 box.

The ordinary collision route is reachable by the local client player: `LocalClientPlayerEntity.move` delegates to `super.move` in both versions (1.13.2 lines 871–875; 1.14.4 lines 846–850). Entity movement obtains world block collisions (`Entity.java`; 1.13.2 line 580 and subsequent axis resolution; 1.14.4 lines 657–700), and `WorldView` enumerates intersecting states and reads their collision shapes (1.13.2 lines 112–136; 1.14.4 lines 133–173). The 1.13.2 `BlockState` forwards shape and collision-shape requests to its block (205–210); the 1.14.4 state has cached and contextual collision-shape routes (197–202). The GrassPath override returns the same fixed shape across those requests.

## Wording correction and precise gates

The result's description that the 1.14.4 contextual collision overload “forwards the supplied context” is accurate for `BlockState` forwarding to the block, but incomplete for the base `Block` implementation: `Block.getCollisionShape(..., context)` calls `state.getSHape(world, pos)` (1.14.4 `Block.java` lines 376–378), and `BlockState.getSHape` uses `CollisionContext.empty()` (lines 189–195). `BlockState`'s no-context cached path is populated through the same empty-context collision computation (lines 197–202 and 410). This route detail does not change the audited result because GrassPath's shape override ignores the context and returns the same `SHAPE` constant.

This verdict is limited to direct collision/shape provision, its same-key construction join, and ordinary local-player collision reachability. It does not assess other movement operations, neighboring updates, state serialization, support/surface predicates, or full tick coverage. **Opacity and `isCube` operations remain explicitly excluded and unadjudicated.** No intermediate patch boundary or pair-wide coverage claim is made.
