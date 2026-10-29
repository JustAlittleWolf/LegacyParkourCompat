# Low-ground collision providers: farmland ID 60 and soul sand ID 88

## Bounded result

This source closure compares the legacy movement-collision and ray-bound providers for farmland and soul sand with their current 26.2 registrations. It does not establish DDA visitation, first-hit order, player trajectory, or response parity.

The key collision distinction is concrete: legacy farmland movement collision is a full cube even though its mutable ray bounds stop at 15/16 height. Current farmland movement collision follows its 15/16-height block shape. Legacy and current soul sand movement collision both stop at 14/16 height, while their ray/outline bounds are full cubes.

## Identity and state mapping

- In 1.8.9, `net/minecraft/block/Block.java:957–958` constructs and registers numeric ID 60 as `farmland`; `:1024` registers ID 88 as `soul_sand`. The registrations instantiate `FarmlandBlock` and `SoulSandBlock`, respectively.
- Legacy `FarmlandBlock.getStateFromMetadata` at `FarmlandBlock.java:123–124` decodes moisture with `metadata & 7`; its state property is 0–7. The 26.2 `BlockStateData.java:3385–3392` rows 960–967 map all eight legacy farmland states to `minecraft:farmland` with the same `moisture` value. `BlockStateProperties.java:117` defines the current moisture range as 0–7; current `FarmlandBlock.java:32–33` uses that property.
- Legacy soul sand does not override the base metadata decoder/encoder (`Block.java:182–190`), so it has only the default state and metadata 0. The 26.2 legacy-state row `BlockStateData.java:3885` maps state 1408 to `minecraft:soul_sand` on both sides. `EntityBlockStateFix.java:110` records the legacy name at ID 88. Current `BlockItemIds.java:340` and `Blocks.java:2042–2052` register the explicit `soul_sand` key.
- Current farmland is explicitly registered as `BlockItemIds.FARMLAND` to `FarmlandBlock` in `Blocks.java:1312–1322`; `EntityBlockStateFix.java:82` records the `farmland` legacy name at ID 60. No same-name-only alias inference is used.

## Movement collision and ray/outline providers

### Farmland

- Legacy `FarmlandBlock` initializes shared bounds to `(0,0,0)..(1,15/16,1)` in its constructor (`FarmlandBlock.java:20–25`). It does not override `updateShape`; the inherited default provider does nothing (`Block.java:596–598`). The inherited default ray method calls `updateShape` before clipping against the shared bounds (`Block.java:456–460`), so the legacy ray box is the constructor's 15/16-height box for all moisture states.
- Legacy movement collision intentionally uses a different provider: `FarmlandBlock.getCollisionShape` returns a position-translated unit cube (`FarmlandBlock.java:29–32`), independent of the mutable ray bounds and moisture. This is a full-height old movement box.
- Current `FarmlandBlock` has a static 15/16-height column shape (`FarmlandBlock.java:33`) and returns it from `getShape` (`:83–85`). It does not override collision. `BlockBehaviour.Properties.hasCollision` defaults to true (`BlockBehaviour.java:985`); the registered properties do not call `noCollision`. Base `BlockBehaviour.getCollisionShape` therefore returns `state.getShape(...)` (`:323–328`). Current movement collision is consequently 15/16 height. The current outline shape and collision shape agree for this block. Relative to the old movement provider, current collision is 1/16 shorter at the top; this does not imply that old ray bounds were full height.

### Soul sand

- The base 1.8.9 `Block` constructor sets mutable ray bounds to a unit cube (`Block.java:198–206`, especially `:201`). `SoulSandBlock` does not change them and does not override `updateShape`, so inherited default ray clipping uses full-cube bounds (`Block.java:456–460,596–598`). Its base metadata decoder leaves its state at the default with metadata 0 (`Block.java:182–190`).
- Legacy movement collision is separately overridden by `SoulSandBlock.getCollisionShape`, which returns a position-translated box of height `1 - 0.125 = 14/16` (`SoulSandBlock.java:18–22`).
- Current `SoulSandBlock.getCollisionShape` returns `Block.column(16,0,14)`, also 14/16 high (`SoulSandBlock.java:14,26–29`). Its inherited `getShape` is full cube (`BlockBehaviour.java:323–324`), and `getVisualShape` explicitly returns full cube (`SoulSandBlock.java:37–39`). Thus current collision agrees with legacy movement collision, while current outline/ray shape stays full cube.

## Separate effects and limits

The geometry statements above do not equate collision with other block effects. Legacy soul sand's `onEntityCollision` multiplies horizontal velocity components by 0.4 (`SoulSandBlock.java:25–28`); current soul sand registration supplies `.speedFactor(0.4F)` (`Blocks.java:2049`). These are separate source paths, and this closure does not claim their movement-effect equivalence. Legacy farmland also has a fall callback/state-conversion path (`FarmlandBlock.java:59–69`), which is not part of the collision-provider comparison.

Source identity and selected-file SHA-256 values are recorded in `source-hashes.json`; every selected file was checked against the canonical ready source manifest.
