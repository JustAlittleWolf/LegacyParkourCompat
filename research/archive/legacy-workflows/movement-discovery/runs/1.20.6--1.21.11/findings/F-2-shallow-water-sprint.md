# F-2: 1.21.11 rejects sprint initiation in shallow water

- Older version A: 1.20.6.
- Newer version B: 1.21.11.
- Mechanic / coverage slice IDs: stage 1, slice 1.3 (sprint eligibility by water state); stage 3 dependency `DEP-TRAVEL`.
- Classification: changed behavior.
- Confidence: source-confirmed.
- Applicability: historical player behavior; grounded, water-contacting player whose eyes are not underwater.
- First changed release: unknown within (1.20.6, 1.21.11].
- Runtime validation: not performed.

## Paired evidence

- A manifest: `../run.md`, artifact A. Source `decompiled_minecraft/1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java`, `net.minecraft.client.player.LocalPlayer.aiStep()`, lines 693–703; SHA-256 `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa`. When grounded, the double-tap branch accepts `onGround()` even when not underwater; `canStartSprinting()` at lines 1020–1028 has no water/shallow-water condition. Direct sprint-key handling has a separate water guard at lines 704–706, so this finding concerns the double-tap route.
- A travel dependency: `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel()` water movement branch around lines 2115–2140; SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. The water branch uses sprint state to select `0.9F` instead of `getWaterSlowDown()`.
- B manifest: `../run.md`, artifact B. Source `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer.canStartSprinting()` and `isSprintingPossible(boolean)`, lines 1096–1108; SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. Sprint eligibility includes `!this.isInShallowWater()` unless the argument indicates flying.
- B water-state source: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, `Entity.isInShallowWater()`, lines 1473–1474; SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. It returns `isInWater() && !isUnderWater()`.
- B travel dependency: `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travelInWater()`, lines 2380–2382; SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. It uses the same sprint-conditioned `0.9F` water slowdown choice.

## Source-level difference

Given a grounded player in water whose eyes are not underwater, with sufficient forward input, no shift input, no active sprint, eligible food/mobility state, and a forward tap/release/tap sequence inside the timer window, A's grounded double-tap branch can start sprinting: `onGround()` satisfies its location gate, and the A eligibility helper has no shallow-water rejection. B's `canStartSprinting()` delegates to `isSprintingPossible(false)` for a walking player; that method rejects `isInShallowWater()`, so B does not start sprint through this route. Directly holding the sprint key is excluded because A's separate direct-key branch already rejects water unless underwater.

## Reachability and dependencies

`LocalPlayer.aiStep()` sets sprint state before inherited `LivingEntity` travel. Both versions' water travel branches use sprint state to select a different horizontal slowdown factor. The local player is on the normal player -> living travel path. Input thresholds, sprint-window setting, item use, sprint state from prior ticks, and the source of water contact remain inputs; this finding states conditions that satisfy both eligibility paths aside from the shallow-water gate.

## Consequence and uncertainty

The source proves that this grounded double-tap route can set sprint in A and is rejected by B while in shallow water. Since sprinting selects `0.9F` for horizontal water slowdown in both versions, the source predicts different horizontal velocity retention after the travel movement when the player has nonzero horizontal velocity. It does not prove a measured trajectory. The changed behavior is limited to this initiation path and state; other sprint routes and the complete water travel closure remain under review. No release introduction point inside the endpoint interval is established.

## Handoff

Independent delta: shallow-water sprint eligibility for the grounded double-tap route. Related finding IDs: F-1 (sprint activation timer); shared stage 1 sprint-gate dependencies remain open. Implementation and testing decisions are deferred.
