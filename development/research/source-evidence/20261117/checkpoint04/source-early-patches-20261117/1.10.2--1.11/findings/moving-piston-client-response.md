# Moving piston player displacement source changes

1.10.2, Ornithe Feather: `net/minecraft/block/entity/MovingBlockEntity.java`, `moveEntities()` lines 91–149 and `tick()` lines 166–184. The block entity obtains the union of shapes at current and previous progress, selects intersecting non-`IGNORE` entities, sets slime velocity on the push axis, computes overlap from that union to the entity box plus `0.01`, and calls untyped `Entity.move`.

1.11, Ornithe Feather: `net/minecraft/block/entity/MovingBlockEntity.java`, `moveEntities(float)` lines 116–222 and `tick()` lines 252–267. It builds collision boxes from the state selected by `getStateForShape`, computes the progress delta and a directional movement area, derives push distance from each overlapping box, clamps it to that progress delta and adds `0.01`, then calls `Entity.move(MoverType.PISTON, ...)` under a thread-local moving-collision gate. Retraction of a source block can additionally displace an entity out of the source block.

Difference: the client-side moving-piston response changes its sampled shape and push region, per-tick amount/timing, overlap calculation, mover classification, and retract-source follow-up. The exact final player displacement depends on the moved block's collision boxes and `Entity.move` clipping.

Movement meaning: an eligible local player intersecting a client-ticked moving piston can receive a differently timed and sized piston movement request. This is a conditional source observation, not a proven net trajectory result. The moved-state/piston-head geometry, loaded-block-entity availability, and complete collision dependency have not been closed here.
