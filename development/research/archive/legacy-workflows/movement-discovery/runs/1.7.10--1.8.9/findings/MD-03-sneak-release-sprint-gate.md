# MD-03: Releasing sneak changes the sprint-start gate

- Older version A: 1.7.10
- Newer version B: 1.8.9
- Mechanic / coverage slice IDs: stages 1.2 and 2.1
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.7.10, 1.8.9]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/client/entity/living/player/InputClientPlayerEntity.java`; `InputClientPlayerEntity.mobTick()V`; lines 141-171; SHA-256 `1E37EEAEBB22B759BFCAE11C8C08F24B37DFF7EBF5F5B17531F92E6CFBB48794`. A snapshots whether the old forward input is at least `0.8F`, ticks input, then checks the new forward value and old-forward snapshot to start or arm double-tap sprint. There is no old-sneak guard.
- A input scaling; `KeyboardInput.tick()V`; lines 28-34; SHA-256 `E5FC1D40329A80720D5FCCB60E97E94CA6CDD6F61C5CAB47D91A2A9CC2470BB6`. When sneaking, it scales stored forward input by `(float)(value * 0.3)`.
- B artifact B; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity.mobTick()V`; lines 535-564; SHA-256 `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053`. B snapshots both old jumping and old sneaking, ticks input, then the double-tap sprint gate additionally requires `!bl2` (the pre-tick sneaking value).
- B input scaling; `KeyboardInput.tick()V`; lines 28-34; SHA-256 `A5E5B2033322F8867CD845E4095CEA7A82888F6382CBFAFEFC559CF9BA3A76DD`. It retains the same 0.3 sneak input scale as A.

## Source-level difference

Precondition: the player was sneaking on the preceding local tick, is now releasing sneak while holding forward on ground, and the other sprint guards pass. A's old forward value was sneak-scaled below `0.8F`, so its old-forward transition guard is false; after the input tick restores forward to at least `0.8F`, A can arm or complete the double-tap sprint path. B also sees the restored forward value, but the saved old-sneak flag blocks that path for this tick. Its separate held-sprint-key path remains available.

The same threshold `0.8F`, input tick order, and sneak scaling are used on both sides; the additional saved-sneak predicate is the behavioral delta.

## Reachability and dependencies

Local client player tick -> inherited/client `mobTick()` -> snapshot previous input flags -> `KeyboardInput.tick()` samples current keys and sneak-scales axes -> sprint-start guards -> sprinting speed modifier and later `LivingEntity.moveRelative`. The predicate changes the sprint state and associated movement speed only when the stated transition and remaining gates occur. Hunger/flight permission, item-use slowdown, blindness and existing sprint timer are guard inputs; the server may synchronize sprint state.

## Consequence and uncertainty

Source-proven: after a preceding sneaking input, B blocks the double-tap sprint gate on the release tick; A does not. The held sprint-key branch is separate. Inference: this can delay or suppress sprint activation, changing subsequent movement acceleration. No trajectory was tested. The release introducing this predicate is unknown within `(1.7.10, 1.8.9]`.

## Handoff

Preserve the pre-tick sneak snapshot and its order relative to `input.tick()` when emulating B. Keep the double-tap gate separate from held-sprint-key logic. Release boundary remains unresolved; runtime validation is deferred.
