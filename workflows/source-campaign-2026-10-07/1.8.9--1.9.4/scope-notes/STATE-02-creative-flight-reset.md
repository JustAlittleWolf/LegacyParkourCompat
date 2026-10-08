# STATE-02: creative-flight fall reset is outside movement scope

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: creative-flight movement wrapper; `STATE-02`
- Classification: not applicable by project scope
- Confidence: source-confirmed
- Applicability: player creative flight while not riding
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A: source manifest A; `build/movement-campaign-2026-10-07/ready/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity#moveRelative(FF)V`, lines 1279-1294; SHA-256 `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`.
- B: source manifest B; `build/movement-campaign-2026-10-07/ready/1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity#moveRelative(FF)V`, lines 1371-1390; SHA-256 `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.

## Source-level difference and scope disposition

Both versions enter this wrapper when abilities indicate flying and the player is not riding, save vertical velocity and `speedInAir`, update air speed from fly speed and sprint state, invoke living movement, then restore vertical velocity multiplied by `0.6` and restore `speedInAir`. B then sets `fallDistance` to `0.0F` and clears entity flag 7 (the fall-flying flag). A has neither write in this branch. The flag is the B-only fall-flight feature; `fallDistance` feeds fall-damage state, which this campaign excludes.

## Reachability and dependencies

The wrapper is a `PlayerEntity.moveRelative(FF)V` override reached by the player travel path. The additional writes occur after `super.moveRelative`, which can run the movement/collision path before the reset. Elytra entry is tracked as a modern-only exclusion in `TICK-03`. Fall-damage simulation is excluded by project scope.

## Consequence and uncertainty

The paired source proves the added post-travel state writes when creative flight is active and the player is not riding. Neither the B-only flag's fall-flight consumer nor the excluded fall-damage state is an in-scope 1.8.9 historical movement delta. No implementation handoff is eligible.

## Handoff

Scope note only: creative flight clears the damage-related fall distance and a B-only fall-flight flag after living movement. No movement implementation finding; runtime validation is not applicable.
