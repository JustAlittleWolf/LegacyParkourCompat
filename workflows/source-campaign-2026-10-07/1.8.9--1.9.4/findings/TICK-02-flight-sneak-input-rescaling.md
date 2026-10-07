# TICK-02: sneaking input is rescaled during creative flight

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: keyboard sneak scale and local flight input; `TICK-02`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `KeyboardInput#tick()V`, lines 34-36, SHA-256 `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd`; paired `LocalClientPlayerEntity.java`, `LocalClientPlayerEntity#mobTick()V`, lines 596-603, SHA-256 `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `KeyboardInput#tick()V`, lines 46-48, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; paired `LocalClientPlayerEntity.java`, `LocalClientPlayerEntity#mobTick()V`, lines 715-725, SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`.

## Source-level difference

Both keyboard providers multiply sideways and forward input by `0.3` when sneaking. A's local creative-flight branch adjusts vertical velocity but leaves those scaled horizontal values as-is. B's branch, when the player is flying, is the camera, and the current input is sneaking, divides both horizontal values by `0.3` before applying the vertical fly-speed change. The exact sequence is multiply-and-cast in `KeyboardInput.tick`, then divide-and-cast in B's `LocalClientPlayerEntity.mobTick`; do not algebraically replace that sequence in an implementation.

## Reachability and dependencies

Keyboard input is sampled immediately before local movement gates in `mobTick`. The camera and `abilities.flying` guard selects creative flight, and the input values then reach the inherited `moveRelative(FF)V` travel path. A keeps the keyboard sneak scale for horizontal flight; B rescales it in that guarded path.

## Consequence and uncertainty

The source proves a different horizontal movement input supplied to flight travel while sneaking. It does not prove a trajectory or runtime result. No first changed release inside the interval is established.

## Handoff

Independent delta: sneak-axis input rescaling during local creative flight. Related flight toggle and fall-flight slices remain separate. Runtime validation is deferred.
