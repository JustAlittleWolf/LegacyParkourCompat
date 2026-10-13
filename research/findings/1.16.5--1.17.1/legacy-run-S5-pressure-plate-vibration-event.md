# Pressure plate transitions emit events accepted by the 1.17 sculk sensor

- **1.16.5**, mojmap: `world/level/block/BasePressurePlateBlock.java`.
- **1.17.1**, mojmap: `BasePressurePlateBlock.java`.

## Difference

1.17.1 adds game-event emissions to pressure-plate state transitions. 1.17.1 also contains a sculk sensor that maps those event types to vibration strengths and updates adjacent blocks when activated.

The source path could therefore add a redstone-driven external influence to player movement where a 1.17.1-only sensor is present.

The packaged `VIBRATIONS` tag and a concrete downstream movement mechanism remain dependencies, so that consequence is not promoted to a confirmed trajectory finding.

## Movement meaning

When a player enters the pressure-plate callback and the plate changes signal state, 1.17.1 calls the event dispatcher.

The 1.17.1-only sculk sensor is the relevant vanilla listener. `SculkSensorBlock` is absent from 1.16.5's registration and source inventory, so the receiver cannot be present in an 1.16.5-era map under this project's one-way scope.

Do not emulate the sensor or invent its effect in 1.16.5.

The source confirms the 1.17.1 callback emission, sensor event frequencies, sensor registration and neighbor update path.

Whether an activated sensor powers a concrete player-moving mechanism depends on the surrounding map and remains outside this finding's proof.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.16.5--1.17.1/findings/S5-pressure-plate-vibration-event.md).
