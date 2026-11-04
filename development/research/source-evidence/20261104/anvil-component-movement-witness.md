# Bounded source-predicted Anvil movement witness

This is one source-derived displacement witness for the Anvil collision-provider delta recorded in `residual-common-collision-a.md`. It is limited to exact 1.12.2 and 1.13.2 local Feather sources. The geometry and movement trace are derived from source; no runtime execution or recording was performed.

## Exact source identities

- 1.12.2 Feather Gen2 build.2 ready tree, source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- 1.13.2 Feather Gen2 build.2 ready tree, source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- Anvil, Entity, Box, collector, and local-player source files cited below match their manifest file hashes in these trees.

## Preconditions

Use a loaded, otherwise empty client world with one ordinary, collision-enabled Anvil at block position `(0,0,0)`, facing east (`FACING` axis X). No entities, world-border proximity, liquids, cobweb, step-up, support/sneak probes, or neighboring collision blocks are present. Use a local player with the ordinary 0.6F width and 1.8F height (`Entity.java:174–179` old; `:186–191` new), positioned at `(-0.4, 0.3, -0.125)`, `onGround == false`, `noClip == false`, not sneaking/riding, and not in a vehicle. Request the ordinary `SELF` move `(dx,dy,dz)=(+0.5,+0.1,0)`.

`Entity.setPosition` centers the width around X/Z and puts the feet at Y (`Entity.java:298–304` old; `:309–315` new). With the source float width, the starting box is approximately X `[-0.7,-0.1]`, Y `[0.3,2.1]`, Z `[-0.425,0.175]`. It begins 0.1 blocks before the Anvil's west face while grazing its south-side edge. The positive vertical component keeps the player in an ascending airborne state. Its swept box still starts at Y `0.3`, so the component-separation argument below is unchanged. Vertical clipping does not occur before X because the player's starting X max is still west of the Anvil.

## Source trace and predicted displacement

Old Anvil shape for X-axis facing is one filled `Box`: X `[0,1]`, Y `[0,1]`, Z `[0.125,0.875]` (`AnvilBlock.java:31–32,92–95`). The old movement query expands the player box by the requested movement and calls `World.getCollisions` (`Entity.java:567–568`; `World.java:1017–1020`). Its scan bounds and traversal are in `World.java:964–1014`; for this block cell, line `:1001` dispatches `BlockState.addCollisions`, which reaches the generic translated-Box intersection (`Block.java:372–381`). The Anvil box is included because the swept player volume overlaps it in Y and Z. During positive-Y clipping the player's starting X interval is still disjoint from the Anvil, so Y remains `+0.1`; during positive-X clipping, `Box.intersectX` checks Y/Z overlap and limits movement to `anvil.minX - player.maxX` when that is smaller than the request (`Box.java:185–203`). The predicted old result is `(dx,dy) ≈ (0.1,0.1)` (the exact X value reflects `0.6F` width rounding).

New Anvil shape for the same X-axis state is `SHAPE_Z`, a union of four parts (`AnvilBlock.java:29–37,71–74`; `Block.block` divides its coordinates by 16 at `Block.java:136–138`):

- base: Y `[0,0.25]`, Z `[0.125,0.875]`;
- first leg: Y `[0.25,0.3125]`, Z `[0.25,0.75]`;
- second leg: Y `[0.3125,0.625]`, Z `[0.375,0.625]`;
- top: Y `[0.625,1]`, Z `[0.1875,0.8125]`.

The swept player box spans Y `[0.3,2.2]`, Z at most `0.175`. It misses the base vertically and misses every upper component in Z. Its gap to the top's minimum Z is `0.0125`, much larger than the collector's `1e-7` envelope epsilon. Consequently the new shape has no overlap with the swept box and is not a movement collision candidate (`WorldView.java:112–150,154–164`). With no other candidates, `VoxelShapes.calculateMaxOffset` leaves positive X movement unchanged (`Entity.java:578–599`; `VoxelShapes.java:194–205`). The predicted new result is `(dx,dy)=(0.5,0.1)`.

The candidate displacement delta is therefore about `+0.4` blocks along X for this one move. This is a source-predicted result, not measured parity evidence.

## Query gates and falsifier

- This is the ordinary block-candidate query inside `Entity.move`, not the alternate step query (`Entity.java:613` old, `:612` new). Since the player starts airborne and its positive Y request is not clipped, the step condition at old `:603–604` / new `:601–602` is false; the sneak-edge loops at old `:522–564` / new `:533–575` are also skipped.
- Local-player `move` calls `super.move` and then auto-jump (`LocalClientPlayerEntity.java:846–851` old; `:871–876` new). Its auto-jump requires `onGround` (`:857–860` old; `:882–885` new), so this airborne case does not invoke its separate clearance queries.
- Anvil has a non-full component union; the 1.13.2 movement collector's full-block shortcut is not implicated. The block is inside the world border and loaded; entity collision append contributes nothing in the empty setup.
- Falsify this prediction if exact source evaluation shows either (a) the old swept query does not include the Anvil `Box` or its Y/Z projections do not overlap, or (b) the new Anvil `SHAPE_Z` overlaps the swept player box / is emitted as a collision candidate and clips X below `0.5` under the stated empty-world conditions. Moving the player inward in Z, lowering the feet into the base, changing the Anvil facing, or enabling grounded/step behavior is a different case and does not falsify this bounded witness.

This witness supports only the stated ordinary-move case for 1.12.2 versus 1.13.2. It does not close other providers, other query modes, intermediate releases, runtime parity, or generic collector admission.
