# DDA provider alias closure: daylight detectors (1.8.9 IDs 151 and 178)

## Bounded verdict

The old normal and inverted daylight detectors are source-closed to the 26.2 daylight-detector states for collision-provider geometry. Both old registrations use `DaylightDetectorBlock`; old metadata selects only `POWER` 0–15, while the registration constructor selects ordinary or inverted behavior. In 26.2, both old registrations map to `minecraft:daylight_detector` with the same `power` value and `inverted=false` or `true`, respectively. Every state returns the same full-width, 6/16-high box with the default collision gate enabled.

This closes only the block alias, state and collision-shape provider. It does not compare signal strength, block-entity ticking, ray traversal order, DDA observer reachability, or runtime parity.

## Old 1.8.9 source

Canonical identity is 1.8.9, `ornithe-feather` (Feather Gen2 build 2); the ready marker and provenance are included in `sources.sha256`. `Block.java:1157` registers numeric ID 151 as key `daylight_detector` with `new DaylightDetectorBlock(false)`. `Block.java:1211` registers ID 178 as `daylight_detector_inverted` with `new DaylightDetectorBlock(true)`. These are distinct registered block instances sharing the same class.

`DaylightDetectorBlock.java:22-42,123-137` defines only the integer `POWER` property in the block state. The constructor initializes bounds to `(0,0,0)–(1,0.375,1)` and sets `POWER=0`; the metadata decoder sets `POWER` directly to the metadata value and its encoder returns that value. Thus all 16 metadata values represent power 0–15, and inversion is registration-bound rather than encoded in metadata or a second state property.

The base `Block.addCollisions` (`Block.java:339-350`) obtains the collision box from `getCollisionShape` and tests it against the query; the base method makes a world-space box from the instance bounds. The detector has no collision-box override. Its own `updateShape` (`DaylightDetectorBlock.java:38-40`) writes the identical 0.375-height bounds initialized by its constructor. Base `Block.rayTrace` calls `updateShape` before reading the bounds (`Block.java:456-459`); for either detector this refresh is the same fixed box. Power and inverted registration do not change that geometry.

## Current 26.2 state and alias mapping

The current `Blocks` registry has one `DAYLIGHT_DETECTOR` registration at `Blocks.java:2901-2905`, using `BlockItemIds.DAYLIGHT_DETECTOR` (`BlockItemIds.java:567`) and `DaylightDetectorBlock::new`. `DaylightDetectorBlock.java:30-45,119-121` defines `POWER` 0–15 and Boolean `INVERTED`; the default state is `POWER=0, INVERTED=false`. Its shape method always returns static `SHAPE = Block.column(16,0,6)` regardless of state, world, position or collision context.

The 26.2 legacy-state table directly covers the numeric IDs and metadata values. For ID 151 (`151*16 + 0..15`), `BlockStateData.java:6890-6905` maps old tags named `minecraft:daylight_detector` with `POWER=n` to current tags named `minecraft:daylight_detector` with `inverted=false, power=n`. For ID 178 (`178*16 + 0..15`), `BlockStateData.java:7793-7819` maps old tags named `minecraft:daylight_detector_inverted` with `POWER=n` to current tags named `minecraft:daylight_detector` with `inverted=true, power=n`. `BlockStateData.register` records each legacy tag against its explicitly supplied current tag (`BlockStateData.java:686-704`). The `V704` schema maps the old inverted block key to the current common key (`V704.java:84-87`). This proves the alias and state conversion; do not infer it from the similar names alone.

`Blocks.java` supplies ordinary `BlockBehaviour.Properties.of()` and does not call `noCollision`; `BlockBehaviour.Properties.hasCollision` defaults to true (`BlockBehaviour.java:985`). The base collision provider returns the state shape when this flag is true (`BlockBehaviour.java:327-328`). `Block.column(16,0,6)` is `box(0,0,0,16,6,16)` and `Block.box` divides coordinates by 16 (`Block.java:159-184`), giving exactly `[0,0,0]–[1,0.375,1]`. That matches the old instance bounds exactly. The class returns the same shape for all power and inversion states, and the same collision shape is used for either alias.

## Scope and evidence identity

The report is limited to these two old IDs, their 16 metadata values, migrated current state aliases, and collision-box/shape provider behavior. It does not claim that lighting, signal, block-entity, or other world behavior matches.

All 11 selected Java sources were re-hashed against their canonical source manifests. The source paths, file hashes, ready-marker hashes, provenance hashes, and manifest hashes are recorded in `sources.sha256`; markers identify the exact version and mapping family for each side. No product code, backlog, build, tests, or runtime were accessed.
