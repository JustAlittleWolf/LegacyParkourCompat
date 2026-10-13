# MD-07: Flight vertical input is computed through float fly speed

- **1.7.10**, ornithe-feather: `1.7.10/ornithe-feather/net/minecraft/client/entity/living/player/InputClientPlayerEntity.java`; lines 197-205.
- **1.8.9**, ornithe-feather: `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; lines 596-603.

## Difference

When the player is flying and is the active camera, 1.7.10 directly applies the double literal `0.15` to vertical velocity. 1.8.9 derives the amount from the float-valued ability speed multiplied by the float literal `3.0F`.

At the shared default `0.05F`, 1.8.9's multiplication is evaluated in float precision and then promoted for the double velocity operation; 1.7.10 uses a double literal.

Non-default ability speeds also change the 1.8.9 increment while 1.7.10 remains fixed at `0.15`.

## Movement meaning

Local player `mobTick()` samples jump/sneak input and updates vertical velocity when flying; the input then flows into the subsequent living movement tick. 1.8.9 additionally requires `isCamera()`.

The ability speed defaults to `0.05F` in both source versions and has a setter; its active value can be supplied by player ability state.

MD-06 concerns the separate horizontal airborne input factor.

Source-proven: 1.7.10 uses fixed double `0.15`; 1.8.9 uses `flySpeed * 3.0F`, with float intermediate precision, and 1.8.9 is camera-gated.

Inference: default values can differ at the least significant bits, while a non-default ability speed changes 1.8.9's vertical input magnitude.

The additional camera predicate's behavior outside the local-camera case remains under review in the run checkpoint.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.7.10--1.8.9/findings/MD-07-flight-vertical-input-rounding.md).
