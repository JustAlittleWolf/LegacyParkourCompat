# F-ELYTRA-START: A jump press can start fall-flying before descent

- Older version A: exact Java Edition 1.14.4
- Newer version B: exact Java Edition 1.15.2
- Mechanic / coverage slice IDs: INV-TICK, INV-STATE, INV-EQUIPMENT; S-ELYTRA-START
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `ready/1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()`, lines 707-711 requires fresh jump input, airborne state, `getDeltaMovement().y < 0.0`, not fall-flying, and not ability-flying; with an Elytra whose `ElytraItem.isFlyEnabled` is true it sends `START_FALL_FLYING` without setting the local fall-flying flag there. SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`.
- B `ready/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java::LocalPlayer#aiStep()`, lines 715-719 requires fresh jump input, no ability-flight toggle that tick, not ability-flying, not a passenger, and not on a ladder; with an enabled Elytra it calls `tryToStartFallFlying()` before sending the same command. SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
- A `net/minecraft/world/entity/player/Player.java` has no `tryToStartFallFlying` helper; file SHA-256 `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`.
- B `net/minecraft/world/entity/player/Player.java::tryToStartFallFlying()`, lines 1578-1587 requires airborne, not already fall-flying, and not in water; for an enabled Elytra it calls `startFallFlying()`, which sets shared flag 7 at lines 1590-1592. SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`.
- In B, `LivingEntity#travel(Vec3)` selects its fall-flying branch when `isFallFlying()` is true (lines 1840-1843); `isFallFlying()` reads shared flag 7 (lines 2690-2692); SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
- Both endpoint artifact manifests: A `ready/1.14.4/mojmap.artifacts.sha256` SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; B `ready/1.15.2/artifacts.sha256` SHA-256 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`.

## Source-level difference

For an airborne player with an enabled Elytra who presses jump after releasing it, while still ascending or at zero vertical velocity, A does not send a start request because its gate requires Y velocity below zero. B has no descent requirement; provided the player is not riding, on a ladder, or in ability flight, its helper sets the fall-flying shared flag locally and then sends the request. B's helper additionally rejects water and an already active fall-flying state. These gates can change both whether the request is sent and the local player state before the server response.

## Reachability and dependencies

The path is client `LocalPlayer.aiStep` -> Elytra slot/enabled check -> B `Player.tryToStartFallFlying` -> shared flag 7 -> `LivingEntity.travel` fall-flying branch. This is a player-only local client behavior; server acceptance and later synchronization are external inputs and were not needed to establish the local transition. A's callsite only sends the command in the descending case.

## Consequence and uncertainty

The source proves the changed eligibility and B's local flag write, not a measured glide trajectory or server acceptance. The client-side difference is bounded to a fresh jump press with the specified airborne equipment/state; exact release introduction is unknown within the endpoint interval.

## Handoff

Preserve the selected historical start gate for its endpoint. If an implementation needs a cutover finer than the endpoint pair, inspect intervening exact releases before choosing it.
