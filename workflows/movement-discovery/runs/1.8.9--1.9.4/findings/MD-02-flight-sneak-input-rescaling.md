# MD-02: Sneaking input is restored during creative flight

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: stage 1 input sampling and flight gates; stage 2 flight ability; stage 3 player movement-relative acceleration
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A input producer: `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `KeyboardInput.tick()` lines 13-41, SHA-256 `A5E5B2033322F8867CD845E4095CEA7A82888F6382CBFAFEFC559CF9BA3A76DD`. When sneaking it multiplies sideways and forward floats by `0.3` and casts each result to `float`.
- B input producer: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `KeyboardInput.tick()` lines 13-48, SHA-256 `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A`. Its same sneak scaling remains present.
- A flight consumer: `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `LocalClientPlayerEntity.mobTick()` lines 596-604, SHA-256 `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053`. Under `abilities.flying && isCamera()`, sneaking changes vertical velocity only; it does not restore the sideways/forward inputs.
- B flight consumer: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `LocalClientPlayerEntity.mobTick()` lines 715-725, SHA-256 `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`. Under the same guard, the sneaking branch first assigns `(float)(movementSideways / 0.3)` and `(float)(movementForward / 0.3)`, then applies the vertical fly-speed change.
- Input-to-player state, A: `LocalClientPlayerEntity.serverTickAi()` lines 470-485 copies input values into sideways/forward speed for the camera; source hash as above. `PlayerEntity.moveRelative(float,float)` lines 1279-1291 consumes those values in the flight-enabled path; `PlayerEntity.java` SHA-256 `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88`.
- Input-to-player state, B: `LocalClientPlayerEntity.serverTickAi()` lines 586-600 copies input values into sideways/forward speed for the camera; source hash as above. `PlayerEntity.moveRelative(float,float)` lines 1372-1384 consumes those values in the flight-enabled path; `PlayerEntity.java` SHA-256 `D658A0D95452D12BB7E347BFD802240EEECAF7F938E10DCD43E2640434387F85`.

## Source-level difference

For a camera-controlled player with flight enabled who sneaks, A passes the keyboard's sneaking-scaled horizontal input onward. B additionally divides each scaled input by the double literal `0.3` and casts the result back to `float` before copying it to movement speed fields. The multiply/cast and divide/cast sequence is preserved as decompiled; it is not treated as exact algebraic cancellation.

## Reachability and dependencies

`KeyboardInput.tick()` writes the current input. `LocalClientPlayerEntity.mobTick()` calls it before the flight/sneak branch. The locally controlled/camera path copies the resulting input into `sidewaysSpeed` and `forwardSpeed`; `LivingEntity.mobTick()` later dispatches to `PlayerEntity.moveRelative()`, whose flight path uses the movement inputs and fly speed. The A and B input producers otherwise retain the same sneak multiplier. Item-use slowdown is applied before the B division, so the two controls interact; the division does not erase an earlier `0.2F` item-use scaling.

## Consequence and uncertainty

Source proves that B supplies different horizontal input values to movement for this precondition. The predicted consequence is increased horizontal control while sneaking in flight. With simultaneous item-use slowdown, the different order also changes the resulting input; exact trajectories and rounding outcomes were not measured.

## Handoff

Independent delta: B's flight/sneak input rescaling, including its interaction with item-use slowdown. This is separate from ordinary ground sneaking and from the sprint timeout finding. No Elytra behavior is inferred from this creative-flight slice.
