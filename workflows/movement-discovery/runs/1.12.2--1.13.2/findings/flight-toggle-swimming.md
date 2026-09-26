# 1.13.2 prevents double-jump flight toggle while swimming

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: 1.2 (local flight-toggle gate); 2.x swim-state dependency
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player interaction with water
- First changed release: unknown within (1.12.2, 1.13.2]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `LocalClientPlayerEntity.mobTick()V`, lines 747-761; SHA-256 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdabad795c70c9cf28854aff9823cc`. When the player can fly, is not in spectator mode, and jump input transitions from false to true without an auto-jump, the second press toggles `abilities.flying`, syncs abilities, and resets the double-press timer.
- B: corresponding file/member, lines 760-775; SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`. The second-press branch adds `else if (!this.isSwimming())` around the toggle/sync/timer-reset writes.
- B swimming predicate: `decompiled_minecraft/1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `isSwimming()Z`, lines 1819-1821; SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`. The player predicate requires not flying, not spectator, and the inherited swimming flag. The corresponding 1.12.2 source has no swimming flag or predicate in Entity/PlayerEntity.

## Source-level difference

With flight permission, a prior jump press registered, a new jump press, and no auto-jump override, A toggles flight. B toggles flight only when `isSwimming()` is false. While swimming, B leaves the ability and double-press timer untouched by this branch. This is separate from the earlier flight-toggle setup press, which still assigns the timer.

## Reachability and dependencies

The local player processes jump input in `mobTick()`. B's swimming flag is set from sprint and water/submersion state, with PlayerEntity filtering for flight/spectator. Thus the new guard applies only during B swimming; it does not prevent flight-toggle behavior in all water contact or for spectators/flying players. Exact swimming-state transitions remain covered by the water sprint and stage 2 dependencies.

## Consequence and uncertainty

Source proves B suppresses the flight-state toggle and its sync/reset writes while swimming. It predicts different vertical flight movement after the attempted second jump. Ability synchronization is invoked in the existing flight path; server acceptance and resulting runtime trajectory are not assessed.

## Handoff

Flight-toggle gate while swimming. Related to the water sprint/swim-state finding, independently changes the flight ability path. Release of introduction is unknown within the endpoint interval.
