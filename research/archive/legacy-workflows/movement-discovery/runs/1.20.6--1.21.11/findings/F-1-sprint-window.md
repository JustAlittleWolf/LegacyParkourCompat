# F-1: Double-tap sprint window is configurable in 1.21.11

- Older version A: 1.20.6.
- Newer version B: 1.21.11.
- Mechanic / coverage slice IDs: stage 1, slice 1.2 (local sprint activation).
- Classification: changed behavior (client option dependent).
- Confidence: source-confirmed.
- Applicability: historical player behavior; the default B option matches A's fixed window.
- First changed release: unknown within (1.20.6, 1.21.11].
- Runtime validation: not performed.

## Paired evidence

- A manifest: `../run.md`, artifact A. Source `decompiled_minecraft/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`, `net.minecraft.client.player.LocalPlayer.aiStep()`, lines 649–650 and 693–700; SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`. At line 698 the eligible first tap assigns `this.sprintTriggerTime = 7`; a later tap while the counter is positive takes the `setSprinting(true)` branch.
- B manifest: `../run.md`, artifact B. Source `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `net.minecraft.client.player.LocalPlayer.aiStep()`, lines 729–730 and 767–775; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. The corresponding first tap assigns `this.minecraft.options.sprintWindow().get()`; a positive remaining timer enables sprint on the next eligible input.
- B option source `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/Options.java`, `net.minecraft.client.Options.sprintWindow` initializer, lines 555–565; SHA-256 `69a91d007e53c77a3a0b8b8d2c82698d91b05270ba69c429e9393e8597f5331b`. The option's `IntRange` is 0–10 and its default is 7.

## Source-level difference

Under the same eligible forward-input and sprint-eligibility conditions, A uses a fixed seven-tick double-tap counter. B uses a client option that defaults to seven ticks and allows values from zero through ten. Setting B to zero prevents the forward double-tap branch from arming a positive counter; settings below or above seven shorten or lengthen the activation window relative to A. With B left at its default, this timer value matches A.

## Reachability and dependencies

The local player's `aiStep()` decrements `sprintTriggerTime` and evaluates the forward-input/start-sprint branch before normal player travel. An accepted sprint state changes player movement speed through the player movement path. This finding isolates the timer value. The surrounding eligibility and stop-sprinting conditions, including food, blindness/mobility restriction, water, item-use components, crouching, passenger and fall-flying conditions, remain open in stage 1/2/6 coverage.

## Consequence and uncertainty

The source proves that B exposes a configurable double-tap interval while A fixes it at seven. In B, the default interval is also seven. For any nondefault interval, the source predicts that a second eligible tap can activate sprint in a different tick window, changing when sprint movement-speed state begins. No trajectory was observed or runtime-tested. The release where this option first appeared is unknown within the endpoint interval.

## Handoff

Independent delta: configurable double-tap sprint activation window. Related finding IDs: none. Applicability depends on the B client option; implementation and testing decisions are deferred.
