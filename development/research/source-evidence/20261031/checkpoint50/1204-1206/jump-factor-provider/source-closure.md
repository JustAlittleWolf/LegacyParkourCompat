# 1.20.4–1.20.5 block jump-factor source closure

Scope: exact Mojmap Java sources for the ordinary vanilla block-factor inputs to `LivingEntity.getJumpPower`, with 1.20.6 as context. This closes only the assigned dependency slice; it is not a full-pair coverage or freeze claim.

## Result

No 1.20.4→1.20.5 change was found in the ordinary vanilla block-factor provider or in the vanilla block properties that feed it. For a vanilla player with the 1.20.5 default jump-strength attribute (`0.42F`), the same sampled vanilla blocks and jump-effect state produce the same computed power as 1.20.4:

- 1.20.4: `0.42F * blockJumpFactor + jumpBoostPower`.
- 1.20.5: `(float) getAttributeValue(JUMP_STRENGTH) * 1.0F * blockJumpFactor + jumpBoostPower`; the registered default is `0.42F`, and `Player.createAttributes()` inherits it through `LivingEntity.createLivingAttributes()`.

The default multiplication by `1.0F` preserves the same float operand before multiplying by the block factor; the effect addition remains last and unchanged. This statement is limited to vanilla defaults and same effect/block inputs. Arbitrary attribute modifiers, modded block implementations, and server-produced state are outside this assignment.

`getBlockJumpFactor()` is textually unchanged: it reads the block at `blockPosition()`, then the block at `getBlockPosBelowThatAffectsMyMovement()`, and returns the below value only when the current block factor is exactly `1.0`; otherwise it returns the current value. `getBlockPosBelowThatAffectsMyMovement()` and its `getOnPos(0.500001F)` implementation are also textually unchanged between the pair.

The vanilla factor table reachable from 1.20.4-era registrations has only two outcomes: default `1.0F` for ordinary blocks, or Honey Block’s explicit `0.5F`. `BlockBehaviour.Properties.jumpFactor(float)` stores the property, the constructor copies it into the behavior field, and `Block.getJumpFactor()` returns that field. A repository-wide search of the assigned exact source trees found no `getJumpFactor()` override outside `Block` and no vanilla `.jumpFactor(...)` registration call other than Honey Block. No vanilla registration copies properties from Honey Block.

1.20.6 provides no further context change in these sources: every source file listed below has the same full-file SHA-256 in 1.20.5 and 1.20.6.

## Reachability and adjacent gates

`Player` does not override `getJumpPower()`; its `jumpFromGround()` delegates to `LivingEntity`. The only no-argument `getJumpPower()` override in the entity tree is `Rabbit`, outside player scope. `getJumpBoostPower()` has one implementation in `LivingEntity`; it is unchanged and returns the same jump-effect amplifier expression in both releases.

1.20.5 also adds a `getJumpPower() <= 1.0E-5F` guard in `LivingEntity.jumpFromGround()`. With the ordinary vanilla player default `0.42F` and the vanilla block factors above (`0.5F` or `1.0F`), the computed power is positive even without Jump Boost, so this guard does not suppress the player jump. The later change to sprint-horizontal movement in that method is not part of block-factor computation.

## Exact source evidence

All identities below are Mojmap sources under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`. Hashes are SHA-256 of the complete `.java` files and match the supplied `.native-source/20261030/1204-1206-pool44/whole-pair-inventory/three-way-java-path-inventory.csv`.

| Source and relevant members | 1.20.4 ref / SHA-256 | 1.20.5 ref / SHA-256 | 1.20.6 ref / SHA-256 |
|---|---|---|---|
| `net/minecraft/world/entity/Entity.java`: `getBlockPosBelowThatAffectsMyMovement`, `getOnPos(float)`, `getBlockJumpFactor` | 793–824 / `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` | 794–825 / `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | 794–825 / `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| `net/minecraft/world/entity/LivingEntity.java`: `getJumpPower`, `getJumpBoostPower`, `jumpFromGround`, `createLivingAttributes` | 2000–2017; attributes builder 280–288 / `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | 2057–2081; attributes builder 292–306 / `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | same file hash as 1.20.5 |
| `net/minecraft/world/level/block/Block.java`: `getJumpFactor` | 401–403 / `9a777d44c9c6b55677132fb5a56d46c9aa322e42faf956749ef80443817416c4` | 393–395 / `0c96f526470944baa74d212ca8bfb8b579fd6a1d7045498b8e062b4868bb8e09` | same file hash as 1.20.5 |
| `net/minecraft/world/level/block/state/BlockBehaviour.java`: property field, constructor copy, default, builder setter | 93, 100–112, 969, 1081–1084 / `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` | 95, 102–114, 947, 1059–1062 / `5ccc577c6b257badf8bffab54181c45b6c57779c8545efe5c54a9766687e685b` | same file hash as 1.20.5 |
| `net/minecraft/world/level/block/Blocks.java`: Honey Block registration and complete vanilla block registrations | 6584–6589 / `5392c4349317e55c2989865be38c17b94e6e0543e7017e6a2a64d79217e83643` | 6594–6599 / `684579bbcbe48b1f0984090a4ca044c0b2cee6d08c2dc1e449e2259389c5b129` | same file hash as 1.20.5 |
| `net/minecraft/world/entity/ai/attributes/Attributes.java`: new generic `JUMP_STRENGTH` default | old horse-specific `JUMP_STRENGTH` declaration, 24–26 / `287dcd477ccca2aebd24ad28db7b0e7a1e9dc9bbeb272d330cd15504b8669ff7` | generic registration default `0.42F`, 45–47 / `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | same file hash as 1.20.5 |
| `net/minecraft/world/entity/player/Player.java`: inherited attributes and jump delegation | `createAttributes` 204–209; `jumpFromGround` 1440–1441 / `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `createAttributes` 221–230; `jumpFromGround` 1462–1463 / `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | same file hash as 1.20.5 |

The inherited `getOnPos(0.500001F)` helper checks supporting-block state against `BlockTags.FENCES` and `BlockTags.WALLS`, and checks `FenceGateBlock` directly. These tags influence the lower sample position selected by the helper. Their data-driven tag memberships are not present in the supplied Java-only source inventory and were not compared here; the conclusion above is for the same sampled current/below block states. The provider’s other runtime inputs are the level’s block states at those positions and the player’s Jump effect/amplifier.

## Disposition

No canonical movement finding is warranted for the assigned ordinary vanilla block-factor slice. The modern generic attribute is not treated as a custom-attribute movement change; only its vanilla default is used above to answer the same-input player equivalence question. The data-driven fence/wall tag membership noted above is an external resource dependency if extending this check from equal sampled block states to a complete position-selection comparison.
