# Plain TorchBlock shape-provider slice

## Exact source identity

- 1.13.2 Feather source-manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (matches the assigned manifest identity). Its manifest pins `net/minecraft/block/TorchBlock.java` to `da11308e51f57ce95764f2ee645dde8d15607914a3a68bac9c0635ab3d1531e0`; recomputed file hash matches.
- 1.14.4 Feather source-manifest SHA-256: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc` (matches the assigned manifest identity). Its manifest pins `net/minecraft/block/TorchBlock.java` to `8430a1058184d9e87596d5ba961ce092e6c77469c642f50e5c901db1a0588aab`; recomputed file hash matches.
- Mapping family: Ornithe Feather on both sides.

## Bounded comparison

`1.13.2` `net/minecraft/block/TorchBlock.java:16,23-25` and `1.14.4` `net/minecraft/block/TorchBlock.java:17,24-26` both define `FLOOR_SHAPE` as `Block.block(6.0, 0.0, 6.0, 10.0, 10.0, 10.0)` and return it unconditionally from `getShape`. The method signature gains a `CollisionContext` parameter in 1.14.4, but the provider does not inspect it. The effective local shape is the same: x/z `[6/16, 10/16]`, y `[0, 10/16]`.

The plain torch has no state properties in either `TorchBlock` implementation. Its block registration is `1.13.2` `net/minecraft/block/Block.java:986` and `1.14.4` `net/minecraft/block/Blocks.java:394-396`; both instantiate `TorchBlock` with `.noCollision()`. In 1.13.2 `Block.java:393-395`, `getCollisionShape` returns empty when `hasCollision` is false. In 1.14.4 `Block.java:376-378` has the same gate, and `BlockState.java:410` computes the cached collision shape through that method. Therefore the provider's nonempty outline shape is not a movement collision shape for the registered vanilla plain torch in either release.

`isCube` is explicitly overridden to false in 1.13.2 `TorchBlock.java:28-30`; 1.14.4 removes that override, while `Block.java:434-435` now returns false by default. The effective predicate is unchanged. No plain-torch state/provider registration change was found in this slice.

## Ordinary player movement reachability

Player/entity movement reaches block collision collection (`1.13.2` `net/minecraft/entity/Entity.java:580,612`; `1.14.4` `Entity.java:700`). The world collision path obtains each block state's collision shape (`1.13.2` `net/minecraft/world/WorldView.java:170-177`; `1.14.4` `WorldView.java:133-172`). Because the registered plain torch is `noCollision`, this path sees an empty shape in both versions. The unchanged outline shape can affect selection/ray interaction, but that is outside this collision-provider slice and does not establish movement collision reachability.

## Verdict and limits

No difference in plain `TorchBlock` collision/shape provider behavior or state semantics is evidenced for 1.13.2 to 1.14.4. No movement change candidate from this bounded slice. This is not a pair-freeze claim. Floor and wall variants were not separately investigated; wall torch and redstone wall torch classes were intentionally excluded. Other torch behavior and broader collision-provider inventories remain unexamined.
