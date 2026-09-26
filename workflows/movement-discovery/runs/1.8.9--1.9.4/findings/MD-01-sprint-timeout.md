# MD-01: Local sprint timeout is removed

- Older version A: 1.8.9
- Newer version B: 1.9.4
- Mechanic / coverage slice IDs: stage 1 sprint start/stop and timer state; stage 2 sprint gate; stage 3 movement-speed consumer
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.8.9, 1.9.4]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity.setSprinting(boolean)` lines 349-352 and `LocalClientPlayerEntity.mobTick()` lines 488-494; SHA-256 `1762B116E6B06D682B7DAAA0FC8CCE39B0FF455B3DB8CF79DAB03AC74F6C4053`. Setting sprinting true sets `sprintTimer` to 600; each `mobTick` decrements a positive timer and calls `setSprinting(false)` when it reaches zero.
- B artifact B; `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`; `LocalClientPlayerEntity.setSprinting(boolean)` lines 406-409 and `LocalClientPlayerEntity.mobTick()` lines 604-608; SHA-256 `8AAF711948B7602C2E6C015A37E36ED06073D39727D999D80480B4910B704F5D`. Setting either sprint state resets the timer to 0; `mobTick` increments it and does not test the value. The remaining stop gate is lines 683-688: sprinting stops if forward input is below `0.8F`, horizontal collision is present, or the hunger/flying predicate is false.
- The full 1.8.9 and 1.9.4 Feather `net/minecraft` source inventories show no other `sprintTimer` reads or writes beyond those in the cited local-player class.
- Movement consumer, A: `../../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity.setSprinting(boolean)` lines 1026-1035 applies/removes the same `0.3F` movement-speed attribute modifier; SHA-256 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`. `PlayerEntity.getSpeed()` reads that attribute in `PlayerEntity.moveRelative(float,float)`; `PlayerEntity.java` SHA-256 `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88`.
- Movement consumer, B: `../../../../../decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `LivingEntity.setSprinting(boolean)` lines 1180-1189 applies/removes the same modifier; SHA-256 `BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5`. `PlayerEntity.getSpeed()` reads that attribute in `PlayerEntity.moveRelative(float,float)`; `PlayerEntity.java` SHA-256 `D658A0D95452D12BB7E347BFD802240EEECAF7F938E10DCD43E2640434387F85`.

## Source-level difference

When a local player starts sprinting and continues to meet the existing forward-input, collision, and hunger/flying gates, A forcibly clears sprint after 600 local `mobTick` calls. B has no elapsed-time stop; the same sustained sprint can remain active past 600 ticks. The 1.9.4 timer increment is not itself a duration cap, and the source-wide reference check found no consumer for it outside the local-player class.

## Reachability and dependencies

The camera's `LocalClientPlayerEntity.mobTick()` samples input and runs the sprint gates. Both versions route `setSprinting` to `LivingEntity.setSprinting`, which updates the sprint flag and movement-speed modifier. Ground movement reads that attribute through `PlayerEntity.getSpeed()` and `LivingEntity.moveRelative`. The modifier and consumer are otherwise identical in the paired methods.

## Consequence and uncertainty

Source proves that the A sprint flag and its speed modifier are removed at the 600-tick timeout, while B retains them until another stop predicate runs. The predicted consequence is a longer interval of sprint-speed movement under sustained eligible input. No trajectory was measured.

## Handoff

Independent delta: expiry of the local sprint state after 600 ticks. Keep separate from sprint-start input thresholds and from the unrelated `sprintTimer` counter change. Applicability is local player movement; release introduction remains unknown within the endpoint interval.
