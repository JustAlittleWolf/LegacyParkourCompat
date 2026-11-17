# Independent audit: plain TorchBlock shape provider

## Immutable input

- Audited report: `build/source-residual-20261116/1.13.2--1.14.4/torch_shape_provider/coverage.md`
- Report SHA-256 (exact file bytes): `4866c0abf0941be3372ca212fbbc8cf866ea68578a2e750a72fd4d3319c79eb`
- Pair: 1.13.2 to 1.14.4; mapping family: Ornithe Feather.

## Evidence checks

- Exact source readiness is confirmed by `decompiled_minecraft/1.13.2/ornithe-feather.ready.json` and `decompiled_minecraft/1.14.4/ornithe-feather.ready.json`. Each names `ornithe-feather.sources.sha256`; the manifest-file hashes recompute to `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`, respectively. Both ready markers identify the exact versions and Feather mapping artifacts.
- The source manifests pin plain `net/minecraft/block/TorchBlock.java` to `da11308e51f57ce95764f2ee645dde8d15607914a3a68bac9c0635ab3d1531e0` (1.13.2) and `8430a1058184d9e87596d5ba961ce092e6c77469c642f50e5c901db1a0588aab` (1.14.4); recomputed source-file hashes match.
- Both TorchBlock implementations construct their superclass from the supplied `Block.Properties`, declare no state-property additions, and return the same `Block.block(6.0, 0.0, 6.0, 10.0, 10.0, 10.0)` floor shape unconditionally. The 1.14.4 context parameter does not affect that result.
- Plain `torch` is registered with `new TorchBlock(...noCollision()...)` in both versions: 1.13.2 `Block.java:986`; 1.14.4 `Blocks.java:394-396`. The shared registration line is distinct from wall and redstone torch registrations, which are outside this audit.
- The collision route is real and movement-reachable: entity movement gathers world/block collision shapes (1.13.2 `Entity.java:580,612`; 1.14.4 `Entity.java:665-700`), and world collision enumeration asks the block state for its collision shape (1.13.2 `WorldView.java:135`; 1.14.4 `WorldView.java:170-174`). For this registered plain torch, `Block.getCollisionShape` returns empty whenever `hasCollision` is false (1.13.2 `Block.java:393-395`; 1.14.4 `Block.java:376-378`). In 1.14.4 the BlockState cache is initialized through this gate (`block/state/BlockState.java:410`), so it does not reintroduce the outline shape into movement collision.
- The `isCube` statement is correct: TorchBlock explicitly returns false in 1.13.2 (`TorchBlock.java:28-30`); the override is absent in 1.14.4 and `Block.isCube` defaults to false (`Block.java:434-435`).

## Verdict

**ACCEPT for the bounded plain-TorchBlock claim.** The report correctly concludes that the plain torch's outline provider is unchanged, and its registered `noCollision` property makes that nonempty outline irrelevant to movement collision in both releases. The cited `isCube` override removal preserves the effective false result. No plain-torch collision/shape-provider movement change is supported by this slice.

## Boundaries and gates

This verdict covers only plain `TorchBlock` for 1.13.2 to 1.14.4. It does not audit `WallTorchBlock`, `RedstoneWallTorchBlock`, other torch behavior, full pair coverage, or intermediate patch boundaries. The report correctly leaves those areas unexamined and does not claim pair freeze. No gate remains for this bounded claim.
