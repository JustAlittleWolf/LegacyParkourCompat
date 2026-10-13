# F-18: Raid Omen completion can skip a later Speed-effect tick in 1.21.4

- **1.21.4**, mojmap: `LivingEntity#tickEffects`; line 191.
- **1.21.5**, mojmap: `MobEffectInstance#tickServer`; line 190.

## Difference

When a server player has a Raid Omen effect at duration 1 and a stored raid-omen position, 1.21.4's `RaidOmenMobEffect` returns false. `MobEffectInstance#tick` calls `LivingEntity.removeEffect` directly, removing Raid Omen from the active-effects map while `LivingEntity#tickEffects` is iterating it.

The effect tick then reaches duration zero and returns false; `tickEffects` attempts `Iterator.remove()` on the already-removed entry, which detects the map modification and throws `ConcurrentModificationException`.

The catch exits the whole effect loop before a later Speed entry can tick, leaving a duration-1 Speed modifier active for this movement step.

In 1.21.5, `tickServer` returns false to `LivingEntity#tickEffects`, which removes Raid Omen through the iterator.

The loop can continue to Speed, whose duration-1 tick expires it and removes its `MOVEMENT_SPEED` modifier.

Both releases perform this effect processing before the same entity tick reaches `aiStep`; the effect registration and movement attribute reader are otherwise paired.

## Movement meaning

`BadOmenMobEffect` produces Raid Omen and the stored position for a non-spectator ServerPlayer in a non-peaceful village when the raid is absent or below its max omen level.

At the final Raid Omen tick, a valid stored position causes the effect to create/extend the raid and self-remove.

The player must also have Speed with a one-tick duration, and Speed must be later than Raid Omen in this map's iteration order.

Speed is movement-relevant because it adds `0.2F` multiplicatively to `MOVEMENT_SPEED`; the expiration callback removes that modifier before 1.21.5's subsequent movement step.

Under those source-level preconditions, 1.21.4 can retain the Speed modifier through one additional player movement step, while 1.21.5 expires it before `aiStep`.

This finding predicts a movement-speed difference but establishes no measured trajectory.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-18-raid-omen-expiry-skips-later-speed-effect.md).
