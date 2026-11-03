# Lily Pad collision geometry

1.8.9, Feather Gen 2 build 2: `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/block/LilyPadBlock.java`, constructor lines 15–19, `addCollisions` lines 23–27, `getCollisionShape` lines 30–34; `Blocks.java` line 342.
1.9.4, Feather Gen 2 build 2: `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/block/LilyPadBlock.java`, `SHAPE` line 16, `addCollisions` lines 23–27, `getShape` lines 38–40; `Blocks.java` line 369.
Difference: 1.8.9 uses collision bounds `[0,0,0]–[1,1/64,1]`; 1.9.4 uses `[1/16,0,1/16]–[15/16,3/32,15/16]`. Both providers exclude only boats, so a normal player receives the pad collision box on both sides.
Movement meaning: with one pad at `(0,64,0)`, a normal player at feet position `(0.5,65,0.5)`, with X/Z box center `(0.5,0.5)`, moving straight down by `1`, and no other collision boxes, `Entity.move`/`Box.intersectY` leave the player's feet at `64 + 1/64` in 1.8.9 and `64 + 3/32` in 1.9.4, a `5/64` difference. This is a conditional source-level player collision witness.

