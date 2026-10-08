# Pressure plate transitions emit events accepted by the 1.17 sculk sensor

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S5-03-CONTACT-CALLBACK-INVENTORY`, `S5-05-RESOURCE-AND-REGISTRATION-CLOSURE`, `S7-EXTERNAL-STATE`
- Classification: new block-contact event path to a modern-only movement influence
- Confidence: candidate; exact packaged vibration-tag data is unavailable
- Applicability: B-only receiver; no historical behavior for A-era maps
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A `world/level/block/BasePressurePlateBlock.java`, `entityInside(...)` and `checkPressed(...)`, SHA-256 `03E9FD59C67530216FC5D81E2DE4AFB84A0CF3AE6644B0E14DD0F8D4BD75898F`, recomputes signal strength, updates neighboring blocks and plays sounds; it does not emit block-press or block-unpress game events.
- B `BasePressurePlateBlock.java`, `entityInside(...)` and `checkPressed(...)`, SHA-256 `56C2979B3BA0A461552B5E72CD2A75A037211A6A78BFD8A1A7285B1A8FEAFC39`, passes the overlapping entity into the callback. On a state transition, `checkPressed(...)` emits `GameEvent.BLOCK_PRESS` or `GameEvent.BLOCK_UNPRESS` with that entity (scheduled release checks pass `null`).
- B `world/level/block/SculkSensorBlock.java`, source SHA-256 `AADF9067FE0C4E9B62EC4E175DD59A982C2D997ABC5550A1ED1E1333F1A3AAE6`, assigns event frequencies 11 to `BLOCK_PRESS` and 10 to `BLOCK_UNPRESS`; its activation path updates neighboring blocks. B `Blocks.java` registers `SCULK_SENSOR`, SHA-256 `87D72A113A3F8937A6A585EF917A4FD29CC5B335C00A858F6800E38F3C1BF7A8`; A has no registration or `SculkSensorBlock` source.
- B `net/minecraft/data/tags/GameEventTagsProvider.java`, SHA-256 `C0E81E2882EEB514784D1130CC8260F32052151F36D4B94FBEEF35BF4739A636`, places both events in the `VIBRATIONS` tag. The exact generated tag entry from the version-matched client/server jar could not be checked because only the ready source trees/manifests are present; packaged runtime membership remains open.

## Source-level difference

B adds game-event emissions to pressure-plate state transitions. B also contains a sculk sensor that maps those event types to vibration strengths and updates adjacent blocks when activated. The source path could therefore add a redstone-driven external influence to player movement where a B-only sensor is present. The packaged `VIBRATIONS` tag and a concrete downstream movement mechanism remain dependencies, so that consequence is not promoted to a confirmed trajectory finding.

## Reachability and dependencies

When a player enters the pressure-plate callback and the plate changes signal state, B calls the event dispatcher. The B-only sculk sensor is the relevant vanilla listener. `SculkSensorBlock` is absent from A's registration and source inventory, so the receiver cannot be present in an A-era map under this project's one-way scope. Do not emulate the sensor or invent its effect in A.

## Consequence and uncertainty

The source confirms the B callback emission, sensor event frequencies, sensor registration and neighbor update path. Exact packaged event-tag membership remains unverified without version-matched jar/data. Whether an activated sensor powers a concrete player-moving mechanism depends on the surrounding map and remains outside this finding's proof.

## Handoff

Keep `S5-05` and the external-state dependency open until the exact resource data and downstream consumer path are checked. This modern-only path does not change the A-era pressure-plate behavior eligible for emulation.
