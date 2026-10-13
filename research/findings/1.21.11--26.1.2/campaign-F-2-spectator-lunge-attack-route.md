# F-2: Spectator attack input reaches Lunge only in 1.21.11

- **1.21.11**, mojmap: `1.21.11/mojmap/net/minecraft/client/Minecraft.java`; lines 1605-1640.
- **26.1.2**, unobfuscated: `26.1.2/unobfuscated/net/minecraft/client/Minecraft.java`; lines 1660-1693.

## Difference

Use a vanilla Player in SPECTATOR mode with a main-hand vanilla spear carrying Lunge, food level at least 7, no vehicle, not fall-flying and outside water.

Retain the spear while entering spectator: the paired `ServerPlayerGameMode.changeGameModeForPlayer` source changes the game-mode/ability fields and does not clear inventory.

Aim at a vanilla attackable living target in the same world, inside both the old 3-block entity-interaction range and the new held-item attack range, and choose an ordinary target that does not trigger `skipAttackInteraction`; an enabled spear and attack-strength state that passes the identical `cannotAttackWithItem` predicate satisfy the input gates.

No health or damage outcome is needed: 1.21.11's Lunge call occurs after that sub-branch.

For this state, 1.21.11's vanilla attack input dispatches the generic interaction packet.

The server accepts the loaded-client/target/border/range guards and invokes `Player.attack`.

SPECTATOR supplies `mayfly=true`, so the Player-specific food gate passes; the server-side enchantment route reaches the Lunge effect.

With Lunge level `n`, the direct vector is evaluated in this order: obtain look angle; add the local `(0,0,1)` direction; multiply coordinates by `(1,0,1)`; scale by `n * 0.458F`; add to the Player's existing delta movement; set velocity synchronization markers and the 10-tick grace timer. 1.21.11 nonzero horizontal velocity response is produced for a nonvertical look direction.

For the equivalent 26.1.2 state and attack-button input, vanilla client handling takes the spectator branch, calls `spectate(target)` and returns before sending an attack packet.

Independently, 26.1.2's server `handleAttack` rejects `isSpectator()` before dispatching `Player.attack`. 26.1.2 therefore produces no Lunge Player delta-velocity write on this route.

The 26.1.2 Lunge food predicate itself would pass at food level at least 7 if its post-attack caller ran; the route guard is the isolated difference.

Existing food, inventory, target and pose/movement state are predicate inputs; no attack/damage result or non-player trajectory is compared.

## Movement meaning

The exact call path is 1.21.11 attack button -> `Minecraft` entity-hit branch -> `MultiPlayerGameMode.attack` packet send -> server `handleInteract` -> `Player.attack` -> server `LivingEntity.lungeForwardMaybe` -> main-hand Lunge effect -> direct `Player.addDeltaMovement`. 26.1.2's button branch and server handler each stop the same spectator route before `Player.attack` / `postPiercingAttack`.

The current game-mode writer and its mayfly output are paired; the item eligibility resources are paired as listed.

The target may be an ordinary attackable living entity; no entity motion producer is part of the candidate.

Candidate remains pending because the downstream spectator travel/position consumer and all other Lunge entry callers/effect-resource closures have not been exhaustively closed.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-2-spectator-lunge-attack-route.md).
