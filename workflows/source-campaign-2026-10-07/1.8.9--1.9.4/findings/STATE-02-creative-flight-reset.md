# STATE-02: creative flight clears fall state

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: creative-flight movement wrapper; `STATE-02`
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player creative flight while not riding
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity#moveRelative(FF)V`, lines 1279-1294; SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity#moveRelative(FF)V`, lines 1371-1390; SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.

## Source-level difference

Both versions enter this wrapper when abilities indicate flying and the player is not riding, save vertical velocity and `speedInAir`, update air speed from fly speed and sprint state, invoke living movement, then restore vertical velocity multiplied by `0.6` and restore `speedInAir`. B then sets `fallDistance` to `0.0F` and clears entity flag 7 (the fall-flying flag). A has neither write in this branch.

## Reachability and dependencies

The wrapper is a `PlayerEntity.moveRelative(FF)V` override reached by the player travel path. The additional writes occur after `super.moveRelative`, which can run the movement/collision path before the reset. Elytra entry, server flag writes, and tick-end pose sizing are separately tracked in `TICK-03` and `STATE-03`.

## Consequence and uncertainty

The paired source proves the added post-travel state reset when creative flight is active and the player is not riding. It does not assert a downstream trajectory or damage outcome. Exact first release is unknown inside the endpoint interval.

## Handoff

Independent delta: creative flight clears fall distance and flag 7 after living movement in 1.9.4. Runtime validation is deferred.
