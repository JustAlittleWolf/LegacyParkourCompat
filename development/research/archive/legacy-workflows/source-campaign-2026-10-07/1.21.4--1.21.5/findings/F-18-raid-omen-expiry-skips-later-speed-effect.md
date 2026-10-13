# F-18: Raid Omen completion can skip a later Speed-effect tick in 1.21.4

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S6-EFFECTS, S6-ATTRIBUTES, S1-LOCAL-TICK
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: server-side player with Raid Omen and Speed effects whose iteration order places Speed after Raid Omen on its final tick
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A `LivingEntity` stores active effects in `Maps.newHashMap()` (line 191). `LivingEntity#tickEffects` iterates its key set and catches `ConcurrentModificationException` around the entire loop (lines 780-805); `LivingEntity#baseTick` reaches `super.baseTick`, which dispatches the override back to `tickEffects` before `LivingEntity#tick` later calls `aiStep` (A lines 386-395, 2429-2470; `Entity#tick` lines 430-432; `Entity#baseTick` line 434 onward). SHA-256: LivingEntity `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`; Entity hash is in the paired source manifest.
- A `MobEffectInstance#tick` calls `LivingEntity.removeEffect(this.effect)` when a server periodic effect returns false, before the active-effects iterator advances (lines 212-230; SHA-256 `d00417282830e6b0bf92761ab765f607b1bab658085d6cb5fec16ef2ae7ecf98`). `RaidOmenMobEffect#shouldApplyEffectTickThisTick` returns true at duration 1; its player branch creates/extends the raid, clears the stored omen position and returns false (lines 15-30; SHA-256 `5bfea6d970811d60d907821d632c42aef5c0751e378541a2e773df19588354eb`). `BadOmenMobEffect` can produce this state by adding `RAID_OMEN` for 600 ticks and storing the player's block position (lines 20-26; SHA-256 `52abe71ce5ecfd8ce19fafbd56e0941db26a010c3afb22999d08bd8b29cd76ea`).
- A `MobEffects` registers Speed as a `MOVEMENT_SPEED` `ADD_MULTIPLIED_TOTAL` modifier of `0.2F` (lines 15-21; SHA-256 `35d97c1741ec1379b05f41b8452229870c87a75633d312cd0b20d7fd901b4d08`). Expiring/removing the effect removes its attribute modifier through the paired `LivingEntity#onEffectsRemoved` path (lines 1031-1055).
- B `LivingEntity` likewise stores active effects in `Maps.newHashMap()` (line 190). Its `tickEffects` uses `MobEffectInstance#tickServer`; a false result removes the current key through the same iterator, runs `onEffectsRemoved`, and continues the loop (lines 792-814). `LivingEntity#baseTick` still reaches this before `LivingEntity#tick` calls `aiStep` (B lines 374-383, 2460-2501; `Entity#tick` lines 438-440; `Entity#baseTick` line 442 onward). SHA-256: LivingEntity `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
- B `MobEffectInstance#tickServer` returns false when the periodic effect callback returns false, without directly mutating `LivingEntity.activeEffects` (lines 215-230; SHA-256 `8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36`). The paired `RaidOmenMobEffect` and `BadOmenMobEffect` bodies are byte-identical to A. B registers Speed as the same `0.2F` movement-speed modifier under the renamed `SPEED` holder (MobEffects lines 15-21; SHA-256 `1511be2a9bfd2bd17112ef03b26d645e0cfaf315019b1d906d9d34c54d4ee245`).

## Source-level difference

When a server player has a Raid Omen effect at duration 1 and a stored raid-omen position, A's `RaidOmenMobEffect` returns false. `MobEffectInstance#tick` calls `LivingEntity.removeEffect` directly, removing Raid Omen from the active-effects map while `LivingEntity#tickEffects` is iterating it. The effect tick then reaches duration zero and returns false; `tickEffects` attempts `Iterator.remove()` on the already-removed entry, which detects the map modification and throws `ConcurrentModificationException`. The catch exits the whole effect loop before a later Speed entry can tick, leaving a duration-1 Speed modifier active for this movement step.

In B, `tickServer` returns false to `LivingEntity#tickEffects`, which removes Raid Omen through the iterator. The loop can continue to Speed, whose duration-1 tick expires it and removes its `MOVEMENT_SPEED` modifier. Both releases perform this effect processing before the same entity tick reaches `aiStep`; the effect registration and movement attribute reader are otherwise paired.

## Reachability and dependencies

`BadOmenMobEffect` produces Raid Omen and the stored position for a non-spectator ServerPlayer in a non-peaceful village when the raid is absent or below its max omen level. At the final Raid Omen tick, a valid stored position causes the effect to create/extend the raid and self-remove. The player must also have Speed with a one-tick duration, and Speed must be later than Raid Omen in this map's iteration order. Speed is movement-relevant because it adds `0.2F` multiplicatively to `MOVEMENT_SPEED`; the expiration callback removes that modifier before B's subsequent movement step.

## Consequence and uncertainty

Under those source-level preconditions, A can retain the Speed modifier through one additional player movement step, while B expires it before `aiStep`. This finding predicts a movement-speed difference but establishes no measured trajectory. The ordering precondition depends on the runtime effect-map iteration order. Runtime validation was not performed.

## Snapshot source/artifact identity

- A source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; client jar SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`.
- B source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`; client jar SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.

## Handoff

Independent delta: A can leave a later Speed effect un-ticked when Raid Omen self-removes during active-effect iteration; B removes through the iterator and continues. Related slices: S6-EFFECTS, S6-ATTRIBUTES, S1-LOCAL-TICK. Pending independent source review; no implementation handoff.
