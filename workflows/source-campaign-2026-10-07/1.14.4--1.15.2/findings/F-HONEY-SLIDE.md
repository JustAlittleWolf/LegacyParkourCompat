# F-HONEY-SLIDE: Honey contact redirects a descending player's motion

- Older version A: exact Java Edition 1.14.4
- Newer version B: exact Java Edition 1.15.2
- Mechanic / coverage slice IDs: INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE; S-HONEY-BLOCK
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `ready/1.14.4/mojmap/` source: the complete block registry `net/minecraft/world/level/block/Blocks.java`, lines 1-2035, has no Honey Block registration; SHA-256 `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`. A's source tree has no `HoneyBlock` class. The A `Blocks.java` artifact manifest entry is `1.14.4/Blocks.java` in the ready artifact manifest.
- B `ready/1.15.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java`: collision shape lines 21-35, `entityInside` lines 49-57, slide predicate lines 60-77, motion writes lines 85-95; SHA-256 `40760aeb3c084f1143e87e1e057f18165492eb01b8fce0815dfdd0882cdc8a03`.
- B `net/minecraft/world/level/block/Blocks.java::Blocks.HONEY_BLOCK`, lines 2118-2123, registers the block; SHA-256 `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`.
- A and B `net/minecraft/world/entity/Entity.java::checkInsideBlocks()` scan the moved player bounding box and dispatch `BlockState.entityInside`: A lines 810-832, B lines 791-813; hashes A `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`, B `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- A/B original client jars: `artifacts/1.14.4/client.jar` SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; `artifacts/1.15.2/client.jar` SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`.

## Source-level difference

Honey Block exists in B and not A. Its collision shape is inset by 1/16 horizontally and reaches 15/16 height. On contact, `entityInside` checks that the entity is airborne, its feet are at or below blockY + 0.9375 - 1e-7, vertical velocity is below -0.08, and it is near a block edge. When y velocity is below -0.13, B multiplies X/Z by `-0.05 / y` and sets Y to -0.05; otherwise it retains X/Z and sets Y to -0.05. Both paths reset fallDistance.

## Reachability and dependencies

Player collision movement reaches block contact callbacks through `Entity.move` -> `checkInsideBlocks`; the Honey Block callback accepts a player because it is an Entity. The effect applies only when the modern Honey Block is present and the slide predicate succeeds. It does not establish a historical map mechanic for A.

## Consequence and uncertainty

The source proves the predicate and velocity/fallDistance writes, not an observed trajectory. Landing fall damage and sound/particles are outside this movement finding. The exact release where Honey behavior first appeared is unknown between these endpoints.

## Handoff

Document this only as a 1.15.2-side modern mechanic; do not introduce Honey behavior into 1.14.4 emulation.
