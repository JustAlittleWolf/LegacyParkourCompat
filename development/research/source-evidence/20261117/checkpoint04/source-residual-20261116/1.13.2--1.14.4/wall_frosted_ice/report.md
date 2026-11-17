# Frosted ice wall-neighbor exception closure

## Scope and source identity

Source-only closure of the explicit FrostedIceBlock exception in `wall_generic_neighbor_inventory/report.md` (SHA-256 `1dc175827e5b8c7cbaecb20fc13dc2f55a8cf3450be8c76106ff855d56efcdbc`). Inputs: 1.13.2 Ornithe Feather Gen2 source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 Ornithe Feather Gen2 source manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. All listed source files matched their manifest entries.

| File | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/FrostedIceBlock.java` | `096940541255ab371d6f197fbf22f30b99da48ff1bde8be5c3be15ba00a299be` | `92c2cb87594561fcf9c089853440bc5dd2440f14de269d92d6ccdf96348c0432` |
| `net/minecraft/block/IceBlock.java` | `8c0a9bed03f026be289f183114c6bc71b7e321d6ab4e915921cd2f51e3ce0cec` | `031de8fc5c554d672e8d46c9de5628651f7653893c5bd1127ca8e3125e303aed` |
| Registration: old `Block.java`, target `Blocks.java` | `Block.java` hash above | `net/minecraft/block/Blocks.java`: `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |

## Comparison and result

- 1.13.2 registers the key `minecraft:frosted_ice` as a `FrostedIceBlock` in `Block.java:1606–1608`. Its `AGE` property is `Properties.AGE_3` and is added to the state definition (`FrostedIceBlock.java:14–20, 78–81`), giving legal ages 0 through 3.
- 1.14.4 registers the same key as `FrostedIceBlock` in `Blocks.java:1441–1443`; its same `AGE_3` property and state definition likewise give ages 0 through 3. No other exact `frost_ice`/frosted-ice registration key occurs in the paired block-registration sources.
- Old `WallBlock.shouldConnectTo` rejects the generic solid-face arm when `isExceptionForConnection` is true (`WallBlock.java:51–65`), and that exception explicitly includes `Blocks.FROSTED_ICE` (`:64`). The old directional `FaceShape` inherited by `FrostedIceBlock` is `SOLID`: `FrostedIceBlock` and its `IceBlock` superclass do not override `getFaceShape`, while `Block.getFaceShape` returns `FaceShape.SOLID` (`Block.java:382–385`). Thus the explicit exception dominates that solid shape and the old bit is false for every AGE state and horizontal direction.
- Target `WallBlock` accepts the generic arm when the attachment exception is false and the passed directional face-solid result is true (`WallBlock.java:48–51, 67–70, 91–103`). The target attachment exception list contains leaves, barrier, pumpkin variants and melon only (`Block.java:260–267`), so FrostedIceBlock is not excluded. It inherits the default full-block collision shape: `FrostedIceBlock` extends `IceBlock`, which extends `TransparentBlock`, which adds no shape override; `Block.getCollisionShape` delegates to `state.getShape`, and default `Block.getShape` returns `VoxelShapes.block()` (`IceBlock.java:20–23`, `TransparentBlock.java`, `Block.java:371–378`). `Block.isFaceSolid` tests that directional collision face and rejects only leaves (`Block.java:402–408`). Its collision shape does not depend on AGE, so each horizontal face is full for ages 0–3.

**Concrete arm-bit difference:** for common `minecraft:frosted_ice`, all four AGE states and each of the four horizontal neighbor directions change from old false to target true. The wall placement and neighbor-update calls store the result in the corresponding directional wall property (`WallBlock.java:81–90, 104–118` old; `:67–78, 91–105` target). This is a source-derived state-production difference, not a measured movement trajectory. This closes only the assigned frosted-ice residual and makes no pair-freeze claim.
