# F-008 — Rejected player-move correction refreshes grounded movement state

- Older version A: 1.19.2
- Newer version B: 1.19.3
- Mechanic / coverage slice IDs: on-foot client movement packet correction; S7-external-velocity, S7-rejected-move-fall-state
- Classification: changed behavior
- Confidence: source-confirmed conditional server player-state update; subsequent movement consequence depends on the grounded packet and current state
- Applicability: server receives a player movement packet that reaches the collision-rejection correction branch; packet claims on-ground and server player has positive fall distance; Soul Speed effect additionally requires applicable equipment and support block
- First changed release: unknown within (1.19.2, 1.19.3]
- Runtime validation: not performed
- Manifest artifact reference: `../run.md` Artifact manifest A/B sections; aligned `mojmap` source trees and official original client jars

## Paired evidence

- A `net/minecraft/server/network/ServerGamePacketListenerImpl.java`, SHA-256 `976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2`: `handleMovePlayer()` lines 838-1001. Its collision-rejection branch at lines 960-961 restores the old position with `teleport(...)` and exits the branch without running fall bookkeeping. The accepted movement branch does call `doCheckFallDamage` at line 949.
- B same path, SHA-256 `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`: `handleMovePlayer()` lines 831-1010. The corresponding rejected branch at lines 953-954 restores the old position, then calls `doCheckFallDamage(this.player.getY() - $$10, packet.isOnGround())`. Here `$$10` captured the pre-attempt player Y and `teleport` restores `$$8`, also that Y, so the bookkeeping receives a zero vertical delta and the client packet's ground flag.
- A/B `net/minecraft/server/level/ServerPlayer.java` hashes `8366c069afcae3c9fc866db51a80041a7a56bb3189b049116abd6828ade1f6a3` / `7df71c12ff99bec35b7c6b0e68fa24e5a5aa7293c6dd8f3342d433936ad35c7c`: `doCheckFallDamage()` lines 973-980 / 990-997 skips unloaded chunks, gets the support block, and explicitly invokes `super.checkFallDamage`. The separate empty `ServerPlayer.checkFallDamage` override does not intercept this explicit superclass call.
- A/B `net/minecraft/world/entity/LivingEntity.java` hashes `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` / `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`: `checkFallDamage()` lines 292-313 / 293-314 has identical behavior. For server-side `onGround=true` and positive `fallDistance`, it refreshes Soul Speed before calling the base entity handler. This is the existing movement-speed modifier path documented in S6-soul-speed-ground.
- A/B `net/minecraft/world/entity/Entity.java` hashes `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` / `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6548983667adc32`: base `checkFallDamage()` lines 991-1002 / 1000-1011 resets `fallDistance` when grounded, after the conditional landing-block callback.

## Source-level difference

The same player move rollback is followed by different work. B calls server player fall bookkeeping on the rejected branch; A does not. With the packet ground flag set, positive server `fallDistance`, and a loaded chunk, B refreshes/removes or adds the Soul Speed movement modifier against the restored support state and clears `fallDistance`. A preserves those values in this correction path. The helper bodies and Soul Speed implementation are paired and unchanged; the call-site reachability differs.

## Reachability and dependencies

`LocalPlayer.sendPosition()` emits position/rotation/status packets based on its movement and rotation thresholds. `ServerGamePacketListenerImpl.handleMovePlayer()` checks the received target, attempts `player.move(MoverType.PLAYER, ...)`, validates collision, and on rejection calls `teleport` to restore the saved coordinates. The B-only bookkeeping follows that restoration. `ServerPlayer.doCheckFallDamage` then reaches `LivingEntity.checkFallDamage` and `Entity.checkFallDamage`. The Soul Speed refresh consumes current support block and equipment state and can change the player movement-speed modifier. On-ground landing-block callbacks and resulting world changes remain conditional and are not required for the attribute/fall-distance difference.

## Consequence and uncertainty

Source-proven: for the stated branch conditions, B may change the server player's movement attribute through the existing Soul Speed refresh and resets the server player's `fallDistance`; A does neither from this rejected move. The call also opens the identical landing callback path. Any callback result depends on the restored block and external server world state. No actual correction, attribute synchronization, world update, or player trajectory is claimed.

## Handoff

This is a player-state writer reached from an externally supplied movement packet and correction path. It is separate from F-004's fall-flying travel value entering ordinary landing callbacks. Independent blind source review is pending; implementation and testing decisions are deferred.

**Inventories/slices:** INV-EXTERNAL, INV-STATE, INV-MODIFIERS, INV-TICK; S7-external-velocity, S7-rejected-move-fall-state, S6-soul-speed-ground. **Implementation disposition:** deferred until blind freeze.
