# Levitation blocks local elytra glide initiation beginning in 1.16

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1578–1588.
1.16, mojmap: `1.16/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1513–1523; `1.16/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 750–754.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, lines 1520–1530.

Difference: In 1.15.2, `tryToStartFallFlying` checks airborne, not already gliding and outside water before checking for a usable chest Elytra; Levitation is not a gate. Stable 1.16 adds `!hasEffect(MobEffects.LEVITATION)` to that predicate. Its `LocalPlayer.aiStep` caller reaches the method from the jump-input path and sends `START_FALL_FLYING` only when the start succeeds. The same Levitation gate remains in 1.16.5.

Movement meaning: With jump input, the other local start gates satisfied, and a usable chest Elytra, Levitation prevents local glide initiation beginning in 1.16. The cited client path establishes initiation eligibility. Server handling of the start request and the ongoing server-side `updateFallFlying` continuation are separate; this finding makes no claim that continuation changed.
