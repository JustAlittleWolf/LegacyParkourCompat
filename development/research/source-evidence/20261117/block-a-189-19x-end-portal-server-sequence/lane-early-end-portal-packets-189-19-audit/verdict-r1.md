# Independent audit — corrected comparison r2

**Verdict: accepted for the revised permission-event claim.** The exact 1.9 `PlayerManager.changeDimension(ServerPlayerEntity,int)` sends `PlayerRespawnS2CPacket`, calls `updatePermissions(player)`, and only then removes/transfers the player; the final absolute move follows helper work and `onChangedDimension` (source lines 488–504). The public permission method computes the permission level and delegates to its private overload (480–486). The private overload gates on `player != null && player.networkHandler != null`, chooses event byte 24 / 28 / `24 + permissions` by permission level, and sends `EntityEventS2CPacket(player, b)` through the handler (675–687). For this connected-player transfer, the revised comparison accurately describes the event packet's gate and source call/send order: after Respawn and before removal, helper transfer and final move. The 1.8.9 transfer method has no corresponding permission update between Respawn and removal (435–458).

This verdict establishes source call order only; it makes no inference about runtime network arrival or interleaving.

## Bound inputs and hashes

- Revised author comparison: `comparison.corrected-r2.md` — SHA-256 `b1257e0de49dc5bde71328135b855c8559c21cb86738aaa25c1a77d5468de411`.
- Frozen independent source roster: `roster.md` — SHA-256 `3cb5349a5377eec87e08422ea89339435e82b3351d2eb9e53417b549d4eb99e1`.
- Prior independent verdict: `verdict.md` — SHA-256 `ce73e37b07dfe37f117076ce344fb0baece2875a8d9571d2a3542960293885aa`.
- Accepted unchanged End-transfer finding: `finding-end-portal-transfer.corrected-r1.md` — SHA-256 `d19f5768ff111ce0d28337e90a957bd35790ab844189cd4a594aff67a0e345ae`.
- Accepted unchanged client-ack finding: `finding-client-absolute-move-ack.md` — SHA-256 `0526e4ecacb312b861687bd50d86c783606b4cb220abe4a0dc9c42f31a377eb8`.
- Primary source checked: 1.9 `net/minecraft/server/PlayerManager.java` — SHA-256 `ec75fbd8978b92d18f16b649c65ed596ddce6a437fd22bb18fc0b86d2c937e86`.
