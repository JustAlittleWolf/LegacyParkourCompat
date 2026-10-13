# MD-07: Flight vertical input is computed through float fly speed

- Older version A: 1.7.10
- Newer version B: 1.8.9
- Mechanic / coverage slice IDs: stages 1.2 and 2.3
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.7.10, 1.8.9]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/client/entity/living/player/InputClientPlayerEntity.java`; `InputClientPlayerEntity.mobTick()V`; lines 197-205; SHA-256 `1E37EEAEBB22B759BFCAE11C8C08F24B37DFF7EBF5F5B17531F92E6CFBB48794`. If abilities are flying, sneaking subtracts the double literal `0.15` from `velocityY`, and jumping adds the same double literal.
- B artifact B; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity.mobTick()V`; lines 596-603; SHA-256 `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053`. For a flying camera player, vertical input adds/subtracts `abilities.getFlySpeed() * 3.0F`.
- Both `PlayerAbilities.java:11,44-49` set the default fly speed to `0.05F`; hashes are recorded in the run manifest. B's client player source and A's input player source hashes are also in the manifest.

## Source-level difference

When the player is flying and is the active camera, A directly applies the double literal `0.15` to vertical velocity. B derives the amount from the float-valued ability speed multiplied by the float literal `3.0F`. At the shared default `0.05F`, B's multiplication is evaluated in float precision and then promoted for the double velocity operation; A uses a double literal. Non-default ability speeds also change the B increment while A remains fixed at `0.15`.

## Reachability and dependencies

Local player `mobTick()` samples jump/sneak input and updates vertical velocity when flying; the input then flows into the subsequent living movement tick. B additionally requires `isCamera()`. The ability speed defaults to `0.05F` in both source versions and has a setter; its active value can be supplied by player ability state. MD-06 concerns the separate horizontal airborne input factor.

## Consequence and uncertainty

Source-proven: A uses fixed double `0.15`; B uses `flySpeed * 3.0F`, with float intermediate precision, and B is camera-gated. Inference: default values can differ at the least significant bits, while a non-default ability speed changes B's vertical input magnitude. No trajectory was measured. The additional camera predicate's behavior outside the local-camera case remains under review in the run checkpoint.

## Handoff

Preserve B's ability lookup, float multiplication, and camera guard in their original order. Do not replace them with a double constant. Runtime validation is deferred.
