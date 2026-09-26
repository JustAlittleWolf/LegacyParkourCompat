# 1.13.2 adds sneak-controlled downward motion in water

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 1.2 (local sneak input); 3.x (vertical velocity input)
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior in water present in both versions
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `LocalClientPlayerEntity.mobTick()V`, lines 649-812; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdabad795c70c9cf28854aff9823cc`. The checked input-to-super call path contains no water-and-sneak velocity write or `knockDownwards()` call. A whole-source search of the 1.12.2 Feather tree has no `knockDownwards` method/call.
- B: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `LocalClientPlayerEntity.mobTick()V`, lines 784-787; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`: `if (this.isInWater() && this.input.sneaking) { this.knockDownwards(); }`.
- B callee: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `knockDownwards()V`, lines 1466-1468; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`: subtracts `0.04F` from `velocityY`.
- Input provenance: A/B `KeyboardInput.tick()V` sets `input.sneaking` from the sneak key; both whole-file hashes match, as recorded in the run manifest. Water state is refreshed on the B Entity tick path before the living tick.

## Source-level difference

For a local player whose current input is sneaking while `isInWater()` is true, B subtracts `0.04F` from vertical velocity during the local player tick. A has no corresponding call or helper in the checked player/living source path. B applies this input repeatedly per tick while the condition remains true.

## Reachability and dependencies

The input flag is set by the common keyboard input implementation and consumed by `LocalClientPlayerEntity.mobTick()`. The condition uses the player's entity water-contact flag; 1.13.2 refreshes that flag through its water-state update. No server-side diving result is inferred.

## Consequence and uncertainty

Source proves a per-tick downward velocity write in B and its absence from A's corresponding path. It predicts increased downward motion while the player remains in water and sneaks; no trajectory was measured. The exact interaction with fluid buoyancy and fluid-flow velocity is still in stage 3/5 dependency closure.

## Handoff

Independent sneak-water vertical input. Related to water-jump and swimming findings, but changes velocity through a separate predicate and method. Release of introduction is unknown within the endpoint interval.
