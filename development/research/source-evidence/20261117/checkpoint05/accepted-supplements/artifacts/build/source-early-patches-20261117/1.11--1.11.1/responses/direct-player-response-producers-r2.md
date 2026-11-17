# 1.11 → 1.11.1 direct player response producers r2 correction

This bounded correction binds the original report `build/source-early-patches-20261117/1.11--1.11.1/responses/direct-player-response-producers.md`, SHA-256 `D3284ED0878E872E64DB750F43D480C9E9D137B0DC229A1F81E06C17F0120EB3`. It corrects the ordering statement only. The original report's exact pair identities, source-file hashes, packet construction, direct recipient, client receiver symbol, exclusions and pair-coverage limits remain bound unchanged.

## Corrected branch ordering

In 1.11.1 `ServerPlayNetworkHandler.handlePlayerMove(PlayerMoveC2SPacket)`, the sleeping-player branch at lines 416–420 occurs immediately after calculating `q = m*m + n*n + o*o`. It precedes and bypasses the `q - p > threshold` moved-too-quick check, which is now inside the `else` branch for a player who is not sleeping. When sleeping, `q > 1.0` directly calls `teleport(...)`; when sleeping and `q <= 1.0`, this branch skips the remaining non-sleeping movement processing. The new sleeping correction condition is therefore based on positional displacement alone and is not conditional on `q - p`, packet-rate count, the moved-too-quick threshold, or the singleplayer username exclusion.

In 1.11, there is no sleeping-player split at this location. Sleeping and non-sleeping players both proceed through the moved-too-quick check (`q - p > s * r`, subject to its existing teleportation-state, Elytra-rule, and singleplayer gates), then the remaining movement handling. Thus the new 1.11.1 sleeping branch adds the direct correction for `q > 1.0` and bypasses that existing check for sleeping players. This is a source response difference only; it does not claim historical server physics.

## Unchanged packet and applicability binding

For the new `q > 1.0` sleeping branch, `teleport(this.player.x, this.player.y, this.player.z, packet.getYaw(this.player.yaw), packet.getPitch(this.player.pitch))` reaches the same-version teleport overload with `Collections.emptySet()`. The unchanged overload updates the server-side requested teleport position and ID, then sends an absolute `PlayerMoveS2CPacket` through `this.player.networkHandler.sendPacket(...)` to that same player connection. The receiver symbol remains `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)`; receiver acceptance is separate and is not added by this correction.

The source-observed gate remains: this `ServerPlayerEntity` must be sleeping, the inbound packet's position relative to the recorded first-good position must yield strict squared displacement `q > 1.0`, and the request must reach this branch. The report remains bounded to the changed direct local-player response path. It does not close other server response producers, tick/provider dependencies, the changed-path ledger, or full-pair audit. No pair freeze, implementation readiness or runtime parity is asserted.
