# Moving-piston raw state, shape and player overlap dependencies (1.8.9 → 1.9)

## Bound source inputs

Pair: Minecraft `1.8.9` → `1.9`, Ornithe Feather. Exact roots are `decompiled_minecraft/1.8.9/ornithe-feather/` and `decompiled_minecraft/1.9/ornithe-feather/`. The 1.8.9 source-manifest SHA-256 is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; its provenance SHA-256 is `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`. The 1.9 mapping-local `.decompile-provenance.json` SHA-256 is `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

I recomputed the cited Java-file hashes from disk and checked them against the 1.8.9 root source sidecar and 1.9 mapping-local `sources` rows:

| Source file | 1.8.9 SHA-256 | 1.9 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `05a5bfbf2556eaa621996bda365b7bab01f5b9060cf773e7b9949ebe2f39fff6` | `7ad3426bb74c9e69b4f521d20e045b1dc1eafa9b725daf0e2c0b9b718bc2683c` |
| `net/minecraft/block/MovingBlock.java` | `700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85` | `a5b2584bc575dc888f1d32d9d4b683b91fc8b145ee079e66ff4722718fc8906f` |
| `net/minecraft/block/PistonBaseBlock.java` | `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929` | `d2b396408dc26daf9fba18ccac8cc1bfa4bc197042c2629e8df062a66d61f639` |
| `net/minecraft/block/piston/PistonMoveStructureResolver.java` | `fef3be5c0785c567e49b945d2a984f252615576d01220ccca9a0b90c929c3e7b` | `8794b5bbd07dfb048a968031d780e71b1eabad788b2966bf44eb7c726aa04568` |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` |
| `net/minecraft/block/state/StateDefinition.java` | `9449797527f8314614c514321e180c52608db499054dc3ffca897c5cc996cb50` | `6b7e70156f9f834dd3129d02f0a24ce78a91a8bf926ac0cb2aae06955799c0e3` |

## Exact state and producer binding

Both `MovingBlockEntity` classes store `movedState`, `facing`, `extending`, `source`, `progress` and `lastProgress`. Their constructor retains the supplied `BlockState`; `source` does not participate in either shape method or the entity query/response method. On ordinary moved blocks, `PistonBaseBlock.move` obtains the resolver's `toMove` positions, replaces each block with `MOVING_BLOCK`, and supplies a `MovingBlockEntity` with that block's state, facing and extension flag (1.8.9 `PistonBaseBlock.java:287–320`; 1.9 `:276–315`). The resolvers produce ordered position lists; state capture occurs in `PistonBaseBlock.move`, not in the resolver.

There is a source-level raw-state difference at that capture boundary: 1.8.9 passes `world.getBlockState(blockPos2)` directly (`PistonBaseBlock.java:313–319`); 1.9 first calls `resolveVirtualProperties(world, blockPos)` for each position and stores that result in `list2`, then supplies it to the moving entity (`:286–313`; dispatch is through `StateDefinition.BlockState.resolveVirtualProperties`, `StateDefinition.java:328–329`). Consequently, equivalent moved-state provider outputs are **not** established for state-dependent or neighbor-derived shapes by the moving-entity method comparison alone. This report records the differing input binding; it does not infer a movement consequence for a particular virtual-property provider.

Two additional producer routes are bounded separately. During piston retraction, both versions create a source moving entity at the piston base using the piston base state and `source=true` (1.8.9 `PistonBaseBlock.java:133–141`; 1.9 `:169–181`). During extension, both create a moving entity for the piston head. The head-arm geometry slice is frozen and is explicitly excluded here. Piston event production, network delivery/order, and an ordinary tick witness for an arbitrary moved-state family remain outside this report.

`MovingBlockEntity.readNbt/writeNbt` in each release persists the moved block ID and metadata, facing, `lastProgress`, and `extending`; read sets `progress=lastProgress` from that one saved float (1.8.9 `MovingBlockEntity.java:151–166`; 1.9 `:185–200`). The `source` flag is not serialized in these methods and is irrelevant to the shape/query routines. This is a persistence binding only; it does not assert which serialization/update route supplies a live client entity or whether every provider property round-trips through metadata.

## Shape and movement-query path

The moving-block wrapper is not itself the final collision shape. When the block entity exists, 1.8.9 `MovingBlock.getCollisionShape(world,pos,state)` gets the entity's `getProgress(0.0F)` (therefore `lastProgress`), applies the extension progress conversion, and calls the overload that asks the moved block's `getCollisionShape`; it returns one shifted `Box` or `null` (`MovingBlock.java:118–130, 162–197`). The 1.9 wrapper's collision-shape method returns the moving entity's `getShape(world,pos)` (`MovingBlock.java:131–134`). Its separate outline `getShape` fallback to a full cube when the block entity is absent is an outline/provider fallback; this report does not treat that branch as the local-player collision callback (`:137–140`).

The entity-level shape math differs as previously audited: in 1.8.9 the collision path samples one old provider box at the converted `lastProgress`; in 1.9 `MovingBlockEntity.getShape(world,pos)` samples the moved state's shape at current and previous progress, translates each along facing using `getAmountExtended`, and returns their coordinate-wise `Box.union` (`MovingBlockEntity.java:54–60, 75–88`). For 1.8.9 the provider box is already world-positioned before its facing bound is shifted. For the direct 1.9 entity-response query, the resulting local union is then moved by the moving entity's position before `world.getEntities(null, box)` (`:90–92`). The block collision collector separately intersects the wrapper's returned shape with the player's swept query; the exact scan and single Stone witness are reused below.

The direct overlap consumer is also bounded by accepted evidence: both entity-response paths call `World.getEntities(null, box)`, which can include a non-spectator local player whose box intersects the query box in a loaded chunk; 1.9 also skips `PistonMoveBehavior.IGNORE`, while ordinary `PlayerEntity` inherits `NORMAL`. The response then calls `Entity.move`, which enters the local-player collision clipping path for `noClip == false`. The changed response schedule/direction/amount and the exact client tick/query call chain have already been independently accepted; this report does not re-audit them.

## Reused accepted evidence and exact limits

- Moving-piston response report: `build/source-189-19-20261117/tick-state/piston-entity-response-r2.md`, SHA-256 `AA25A8EC6767B9B0B443C8CADBD7FC3B818B212EF5E897EA7AE20CA5F5A82B61`; bounded ACCEPT verdict `.../piston-entity-response-audit-r2/verdict.md`, SHA-256 `103EE71196F3D357053A6C1A6E1FBCFB7E3BA817F197F09610E2856D5364E7B8`.
- Stone full-cube moving-block sweep witness: `build/source-189-19-20261117/input-travel/moving-block-stone-sweep-witness-r2.md`, SHA-256 `05A2B44304FE83008B9896D7BB5BCF23B51EA85E3A4F38EE5A94174D87C6EC91`; bounded ACCEPT verdict `.../audit-moving-block-r2/verdict.md`, SHA-256 `37CD019F6D23BB0B83AAA94045594A71FAC3690E485E56D638F47B52B4E76063`.

The Stone witness already closes one ordinary local-player query: at `(progress,lastProgress)=(0.5F,0.0F)`, an eastward full-cube Stone moving-block state intersects the player's swept move query in 1.9, and the checked old scan skips that cell; its report accepts the combined scan-bound and emitted-union result for that one witness. It does not establish behavior for other moved-state providers or prove a general trajectory. The frozen pane connection and piston-head-arm geometry slices are excluded; no geometry claims are reopened or widened.

## Residuals and disposition

Closed by the reused packets for their exact scopes: raw 1.8.9/1.9 moving-entity shape formulas and progress input order; ordinary entity-query and direct-response consumer; and one exact full-cube Stone collision-query witness. The 1.9 pre-capture virtual-property resolution is a concrete state-input difference; its shape consequence remains provider-specific and is not generalized here.

Remaining source gates are: (1) identify the moved-state provider families whose `getCollisionShape` (1.8.9) versus `getShape` (1.9) results or virtual-property resolution can affect a local-player overlap, excluding frozen pane/head-arm slices and reusing already accepted provider rows; (2) bind any such provider shape to a concrete response-box/player-box overlap without redoing the accepted Stone witness; and (3) close the distinct client-side moving-block state/progress producer/serialization/order dependency if pair-wide applicability is needed. The accepted direct response result remains conditional on the stated query/overlap and selected player. No arbitrary moved-state, network ordering, map trajectory, post-clipping displacement, full-pair inventory, or pair freeze is claimed.
