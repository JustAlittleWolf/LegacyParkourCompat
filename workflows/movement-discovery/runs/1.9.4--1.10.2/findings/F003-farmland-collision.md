# F003: Farmland collision height reduced to its 15/16 shape

- Older version A: 1.9.4
- Newer version B: 1.10.2
- Mechanic / coverage slice IDs: S4b, S5a (world collision lookup; existing block collision shape)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior (same vanilla farmland block/state in both endpoints)
- First changed release: unknown within (1.9.4, 1.10.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `getShape` lines 32–34 returns `SHAPE = new Box(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0)` (line 22), but `getCollisionShape` lines 38–40 returns `FULL_BLOCK_SHAPE`. SHA-256 `9df445485ba7e1603c20b5dede847fe412558148acefe1eeddc9bb3b62b14c25`.
- B: `decompiled_minecraft/1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `getShape` lines 32–34 returns the same 15/16-high `SHAPE`; there is no `getCollisionShape` override. SHA-256 `63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f`. B inherits `Block.getCollisionShape` lines 357–358, which returns `state.getShape(world, pos)`. B `Block.java` SHA-256 `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`; A base `Block.java` SHA-256 `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` (A's base method is overridden by farmland).
- Both versions route block collision through `World.getCollisions` and `Block.addCollision`, then `Entity.move` clips Y, X and Z in the same order. A/B `World.java` SHA-256 values: `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` / `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`. A/B `Entity.java`: `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` / `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`.

## Source-level difference

For farmland in A, collision queries use a full `1.0`-high box even though `getShape` is `0.9375` high. In B the special collision override is removed, so collision queries use the `0.9375`-high shape. The block and its state existed at both endpoints; this is a collision behavior change, not a newly introduced block.

## Reachability and dependencies

Player movement calls `Entity.move`; the unchanged `World.getCollisions` scan gets each block state's collision shape and passes it to `Entity.move` for axis clipping. Under the same farmland state and neighboring world data, A supplies a full cube while B supplies a box ending at `y + 0.9375`. The one-sixteenth vertical difference can change the collision stop/support surface and consequently the player's resolved Y/on-ground state. Auto-jump scans also query collision boxes, so F001 may interact with this shape where farmland is in its scan path. World block states are supplied by the client world/server; this finding does not rewrite them.

## Consequence and uncertainty

Source proves the collision-box height differs by `0.0625` blocks. It predicts a possible one-sixteenth change in the player's resolved vertical position when that box constrains movement; downstream on-ground and jump consequences depend on the surrounding collision query and were not runtime-tested. The exact first changed release is unknown within the endpoint interval.

## Handoff

Historical block collision delta for farmland. Keep applicability limited to the vanilla farmland state and the collision shape; no runtime validation or implementation was performed.
