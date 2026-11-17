# Independent projection review: extending Stone collision plate

Verdict: **ACCEPT**, narrowly bound to the 1.8.9-to-26.2 non-source, extending ordinary Stone collision projection in the supported positive-thickness phase below. This accepts only the collision-plate projection from native moving-block entity state. It does not accept piston tick, entity displacement, knockback, block-state, or lifecycle replay.

## Exact witness and projection

Fixture: a piston at owner coordinate `P` extends in direction `d`, pushing ordinary `Blocks.STONE` from `P + d`; the non-source moving-block entity is created at `P + 2d`. The entity has `movedState = Blocks.STONE`, `direction = d`, `extending = true`, and `source = false` / `isSourcePiston = false`. At the supported post-tick phase, 1.8.9 has `lastProgress = 0.5` and current progress `1.0`; 26.2 has `progressO = 0.5` and current `progress = 1.0`.

In 1.8.9, `MovingBlock.getCollisionShape(World, BlockPos, BlockState)` reads `getProgress(0.0F)`, hence the previous progress, transforms it to `q = 1 - lastProgress` for extension, then calls its overload. For an ordinary Stone unit box, that overload shortens the bound on the facing side by `q` while leaving the other side fixed. At `q = 0.5`, the resulting local plate is half a block thick at the owner position: east-facing `[0, 0.5]` on X, west-facing `[0.5, 1]` on X, with the analogous facing-side bound for Y or Z.

In 26.2, `MovingPistonBlock.getCollisionShape` dispatches to `PistonMovingBlockEntity.getCollisionShape`, whose default non-source path moves the full moved-state shape using current progress. The tick sets `progressO = progress` before advancing. Thus the native `progressO = 0.5`, `progress = 1.0` tuple carries the historical projection input and owner coordinate; a shape hook can construct the half-thickness historical Stone plate at that owner coordinate without changing the block state.

## Controls and exclusions

- Keep this verdict limited to `0 < progressO < 1` for the extending case. With the ordinary 0.5 tick increment, the reachable positive-thickness sample is `progressO = 0.5`, `progress = 1.0`.
- The zero-thickness plane at extension `progressO = 0` is a separate case and is not accepted by this verdict.
- Preserve the 26.2 native empty-shape result when its `NOCLIP` guard is active. The guard returns the source-head shape (empty for this non-source Stone case) when `progress < 1` and the thread-local no-clip direction matches piston movement. Do not replace that empty result with a collision plate.
- At terminal `progressO = progress = 1`, the old entity has been replaced by Stone; pass through the native full Stone shape. 26.2 may retain the client BE for five death ticks, but its full-progress native collision shape is the same full Stone geometry. Do not replay or alter replacement ticks.
- Do not change either release's entity movement path. 1.8.9 `MovingBlockEntity.tick` calls `moveEntities`; 26.2 `PistonMovingBlockEntity.tick` calls `moveCollidedEntities` and `moveStuckEntities`. Direct push, slime bounce/knockback, stuck-entity movement, and other tick side effects remain excluded.
- This projection verdict does not cover retraction, source pistons, other moved states, or general BE lifecycle parity.

## Exact source identities

SHA-256 values pin the inspected source-file bytes:

- 1.8.9 Feather `net/minecraft/block/MovingBlock.java`, `getCollisionShape` overloads: `700010B071738738F04E0508050590D8D625F01115FC4886FEF6E262F85C6E85`.
- 1.8.9 Feather `net/minecraft/block/entity/MovingBlockEntity.java`, progress accessors and `tick`: `05A5BFBF2556EAA621996BDA365B7BAB01F5B9060CF773E7B9949EBE2F39FFF6`.
- 1.8.9 Feather `net/minecraft/block/PistonBaseBlock.java`, `move` creation and owner coordinate: `3C96698A674714446F9FD0D6CC1C4D5EB72937D9A2F396516DDB2461C3EA4929`.
- 26.2 unobfuscated `net/minecraft/world/level/block/piston/MovingPistonBlock.java`, `getCollisionShape`: `9A8D9B03BE6866B1E5A2D237F8CB635B89C08E63A241DB2D32338448240102D1`.
- 26.2 unobfuscated `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`, progress, `tick`, and `getCollisionShape`: `706BAB1A13BA99BBD334AAD853B99F72DF58FCE5B6B67D30D41F902B63DD8E06`.
- 26.2 unobfuscated `net/minecraft/world/level/block/piston/PistonBaseBlock.java`, `moveBlocks` creation and owner coordinate: `5287608EAEDBD6402F4C6FAC93645CF595631B1D2B3DD6987789225045A64A91`.

The accepted evidence identity `moving-block-stone-sweep-witness-r2.md` SHA-256 `05a2b44304fe83008b9896d7bb5bcf23b51ea85e3a4f38ee5a94174d87c6ec91` is referenced as separate evidence; its bytes were not independently reviewed for this verdict. The published source directories lack `.decompile-provenance.json`, so these file hashes do not independently establish the Feather mapping artifact provenance.