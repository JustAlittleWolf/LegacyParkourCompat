# Cocoa age-2 collision shape

1.10, 1.10.1 and 1.10.2, Feather Gen2 build 2: `net/minecraft/block/CocoaBlock.java`, `EAST_SHAPE` lines 21–25, `WEST_SHAPE` 26–30, `NORTH_SHAPE` 31–35, `SOUTH_SHAPE` 36–40; `getShape` lines 77–90. The CocoaBlock source is identical in all three versions. `net/minecraft/block/Block.java`, `getCollisionShape` lines 356–359 delegates to `BlockState.getShape` (`net/minecraft/block/state/BlockState.java`, lines 364–366).

1.11, Feather Gen2 build 2: `net/minecraft/block/CocoaBlock.java`, the same arrays and `getShape` lines 21–40 and 77–90. The CocoaBlock source is identical in 1.11.1 and 1.11.2 as well. `net/minecraft/block/Block.java`, `getCollisionShape` lines 367–370 delegates to `BlockState.getShape` (`net/minecraft/block/state/BlockState.java`, lines 369–371).

Difference: In 1.10, 1.10.1 and 1.10.2, each direction's age-2 box repeats its age-1 box. Starting in 1.11, age 2 uses a larger and lower box, while ages 0 and 1 remain unchanged. EAST changes from `[0.5625, 0.3125, 0.3125 → 0.9375, 0.75, 0.6875]` to `[0.4375, 0.1875, 0.25 → 0.9375, 0.75, 0.75]`; WEST from `[0.0625, 0.3125, 0.3125 → 0.4375, 0.75, 0.6875]` to `[0.0625, 0.1875, 0.25 → 0.5625, 0.75, 0.75]`; NORTH from `[0.3125, 0.3125, 0.0625 → 0.6875, 0.75, 0.4375]` to `[0.25, 0.1875, 0.0625 → 0.75, 0.75, 0.5625]`; SOUTH from `[0.3125, 0.3125, 0.5625 → 0.6875, 0.75, 0.9375]` to `[0.25, 0.1875, 0.4375 → 0.75, 0.75, 0.9375]`. The default block collision-shape dispatch returns this state-selected shape, so the changed boxes participate in player collision.

Movement meaning: A player intersecting a cocoa pod at age 2 can collide with the wider box and its lower face/edges in 1.11 and later, changing available movement space and collision resolution. This is an exact 1.11 introduction: the 1.11 source has the new arrays, and 1.11.1/1.11.2 retain them.

