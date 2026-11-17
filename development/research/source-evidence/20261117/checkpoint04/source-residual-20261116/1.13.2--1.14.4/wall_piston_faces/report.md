# Wall neighbor piston-face closure

## Scope and source identity

This closes only the open piston, sticky-piston and piston-head rows in `wall_generic_neighbor_inventory/report.md`. Exact inputs are 1.13.2 Ornithe Feather Gen2 build 2 and 1.14.4 Ornithe Feather Gen2 build 2. The SHA-256 values of `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256` and `decompiled_minecraft/1.14.4/ornithe-feather.sources.sha256` are respectively `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`; both ready markers declare the same versions, mapping and manifest digests. The input inventory hash is `1dc175827e5b8c7cbaecb20fc13dc2f55a8cf3450be8c76106ff855d56efcdbc`.

Paired source-file SHA-256 values (old / target):

| File | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/PistonBaseBlock.java` | `1722edaf96c15af587b4e169b62c5af1d352800e82a2070e1f4af86bdde4cb31` | `37409c9f2390642103155dcec3e922e0d387e9d0bbca069fb334277d5d84a058` |
| `net/minecraft/block/PistonHeadBlock.java` | `d0de8ab39c2d350054d7ee9ed66f0e1695f2b7d1d78324b9eabd955361b58e5f` | `348885e6949b4aa754496af1e641ba07de9dcc1402ce0b88b4cde8f57febfd5b` |
| registration source (`Block.java` old, `Blocks.java` target) | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` (`Blocks.java`) | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` (`Blocks.java`) |

## Common registered keys and state crosswalk

Both registries contain `piston`, `sticky_piston`, and `piston_head`. The old registrations are `Block.java:919,933–934`; target registrations are `Blocks.java:266,283–284`. In both versions, `piston` and `sticky_piston` are instances of the same `PistonBaseBlock` class (constructor's sticky flag differs), and `piston_head` is `PistonHeadBlock`.

The paired state definitions agree: piston bases use `FACING × EXTENDED` (`PistonBaseBlock.java`, old lines 362–363 and target lines 359–360); piston heads use `FACING × TYPE × SHORT` (`PistonHeadBlock.java`, old lines 169–170 and target lines 159–160). These properties are not merely default-state artifacts: base placement picks a facing and piston operation changes `EXTENDED`; head creation during extension sets facing/type, and `MovingBlockEntity` can set `SHORT` in both releases (old line 342; target line 350). Every directional result below therefore applies across normal/sticky piston key and, for the head, both type and short variants.

## Predicate and face results

The old wall generic arm requires a directional `FaceShape.SOLID` and rejects all three piston blocks in `Block.isExceptionForAttachment` (old `Block.java:255–256`); thus the old wall-neighbor result is false for every piston state and direction. The old piston-base face override (`PistonBaseBlock.java:367–368`) returns `UNDEFINED` on the face opposite `FACING` only while extended, and `SOLID` on other faces; the old head override (`PistonHeadBlock.java:174–175`) reports `SOLID` only on `FACING`. Those directional values do not change the result because the exception gate rejects the block first.

In 1.14.4, these three keys are not in `Block.isExceptionForAttachment` (`Block.java:260–267`) and are absent from the fixed 14-entry target `minecraft:walls` tag payload (SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`, reused from the assigned inventory's resource extraction). Thus the wall-tag arm is false and the generic wall result follows target `Block.isFaceSolid` (`Block.java:402–403`): it is true exactly when the directional face of the state's collision shape is a full block face (none of these blocks is in `BlockTags.LEAVES`). Target `Block.getCollisionShape` uses the state shape when collision is enabled (`Block.java:376–377`); state face values are cached by direction (`BlockState.java:410–416`).

| Block/state | Old wall bit | Target full collision faces | Difference |
|---|---|---|---|
| Piston / sticky piston, `EXTENDED=false`, any facing | false (exception) | all six directions; full-cube collision shape | Target true on all six directions |
| Piston / sticky piston, `EXTENDED=true`, facing `d` | false (exception) | only `d.opposite()`; directional base shape is a full cube with the 4/16 slab removed at `d` | Target true on `d.opposite()` |
| Piston head, any facing `d`, `TYPE`, `SHORT` | false (exception) | only `d`; the 4/16 head plate covers that full outer face, while the arm is narrower than a full face | Target true on `d` |

For extended bases the target shape constants are axis-aligned 12/16 slabs (`PistonBaseBlock.java:31–36` old and target; target selector lines 52–70). The five other directional projections are partial or empty; the opposite-facing projection is full. For heads, old and target both build the same face plate plus narrow arm from `FACING` and `SHORT` (`PistonHeadBlock.java:24–41, 50–88` old; target lines 24–41, 50–93). Only the facing-side head plate spans the complete face. `TYPE` does not enter the shape selection. Consequently, these results are independent of `SHORT` and type.

The target differs for every state of each registered key: unextended base produces six additional connection directions; extended base produces one; head produces one. For a wall placed or updated horizontally beside such a state, these are changes to the persisted horizontal neighbor bit and therefore to the wall shape. Vertical directions in the table document the full directional predicate, though walls query horizontal neighbors only. This is source-derived state/shape impact, not a measured player trajectory.

## Coverage and remaining blockers

Closed: three common registered IDs; class crosswalk; matched state properties and relevant state reachability; both endpoints' attachment-exception behavior; exact old directional face overrides; target collision-face outcome across all direction/facing/type/short variants; no shape override outside the paired piston classes was needed for this slice.

No blocker remains within the assigned piston-family slice. This does not close other old-exception families, crosswalk target `WALLS` membership, or establish complete pair coverage/freeze. No product, wiki, backlog or Git history was consulted or modified.
