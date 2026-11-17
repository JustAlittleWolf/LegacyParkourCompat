# Independent source audit: spectator attack and local velocity

## Verdict

**COMPLETED — comparison accepted for its bounded claim.** The frozen comparison's disposition is supported: the 1.21.11-to-26.1/.1/.2 spectator attack route does not establish a client-local velocity mutation from lunge or piercing. In 1.21.11 the local callback reaches a `ServerLevel`-guarded effect; in the 26.x callbacks the replacement helper has the same logical-server-only gate. The new spectator-click route selects an entity for spectating and sends a packet. It does not write the local player's velocity. No historical local movement hook is supported by this bounded route.

The local spectator player's direct velocity responder checked here is also unchanged in behavior: `LocalPlayer.onGameModeChanged(SPECTATOR)` clears only the Y component through `setDeltaMovement(...with(Axis.Y, 0.0))` on both endpoint sides. Camera-following and spectator camera movement are outside this verdict. The finding is a no-difference disposition, not proof of complete movement-pair coverage.

## Immutable input

- Comparison: `spectator-lunge-scope/comparison.md`
- SHA-256 (raw file bytes): `f67dcc880dec42fd5302d4ff72173a2b2e7297c16970b65c7f7275f4ff6ede47`
- The digest was recomputed before review and matched the assigned identity.

## Exact local source evidence

Source set identities are confirmed by each exact version's `.provenance.json` and `.ready.json` (version ID and namespace agree); each ready marker's source-manifest digest matches the bytes of its `*.sources.sha256` file. Relevant files below were hashed locally and each digest was found in its version's source manifest. The 26.1, 26.1.1 and 26.1.2 digests match for each listed 26.x source file.

| Release / namespace | Source member | SHA-256 |
|---|---|---|
| 1.21.11 / mojmap | `client/Minecraft.java` | `e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59` |
| 1.21.11 / mojmap | `client/multiplayer/MultiPlayerGameMode.java` | `4d15d5c6b8c280d0e2c6de1b0f41bde83bc0944d400b7f89cabff24679e9a9c0` |
| 1.21.11 / mojmap | `client/renderer/GameRenderer.java` | `040ca51b0d4111cf085aca9b831618a947af2bcf08a2bd34d6ae6b72440162c5` |
| 1.21.11 / mojmap | `client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 1.21.11 / mojmap | `world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 / mojmap | `world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.11 / mojmap | `world/item/enchantment/EnchantmentHelper.java` | `38e944de3098847f62633a6f0747bc2e1dcf016694faebe47009fbe8af514a85` |
| 26.1, 26.1.1, 26.1.2 / unobfuscated | `client/Minecraft.java` | `8b072df57a6efb47bcb4f5b2ac1bb750ed8bfc7b9441b1894167a7b8c046b4b1` |
| 26.1, 26.1.1, 26.1.2 / unobfuscated | `client/multiplayer/MultiPlayerGameMode.java` | `674fe62b7debec3fa582735ae1b86247a25211ec03ec19c9f331dd3c44502e37` |
| 26.1, 26.1.1, 26.1.2 / unobfuscated | `client/renderer/GameRenderer.java` | `d0d926c9e48398bf25b47b9d2fcd13f05b4e9da1e67be0f8476f71cf0cc8e732` |
| 26.1, 26.1.1, 26.1.2 / unobfuscated | `client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.1, 26.1.1, 26.1.2 / unobfuscated | `world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |

### Callback and authority gates

- **1.21.11 attack dispatch:** `client/Minecraft.java:1628-1638` excludes spectator mode from `piercingAttack`; ordinary entity-hit dispatch passes `this.player` to `gameMode.attack`. `MultiPlayerGameMode.java:420-426` sends the attack packet but calls local `Player.attack` and resets the ticker only outside spectator mode. The piercing callback at `MultiPlayerGameMode.java:500-505` calls `onAttack()` and `lungeForwardMaybe()` on `minecraft.player`.
- **1.21.11 lunge:** `world/entity/player/Player.java:1571-1579` gates the base callback on enough food or `mayfly`. `world/entity/LivingEntity.java:1655-1658` invokes `doLungeEffects` only for `level() instanceof ServerLevel`. Thus the client call reaches no local velocity writer in the examined body, including the spectator/mayfly predicate case.
- **26.x attack dispatch:** `client/Minecraft.java:1663-1669` handles spectator first: on an entity hit it calls `gameMode.spectate(entity)` and returns. This prevents the held-item/piercing branch. `MultiPlayerGameMode.java:432-434` only sends `ServerboundSpectateEntityPacket`; its `piercingAttack()` at `:515-521` calls `postPiercingAttack()` on the local player when used outside that spectator-first branch.
- **26.x effect authority:** `world/entity/LivingEntity.java:1675-1679` calls `doPostPiercingAttackEffects` only for a `ServerLevel`. No local client velocity write occurs in that callback.
- **Target versus actor:** In 1.21.11 `GameRenderer.java:341-352` obtains the camera entity for the pick, while `Minecraft.startAttack()` dispatches the hit to `this.player`; 26.x `Minecraft.java:2941-2950` and `LocalPlayer.java:1242-1281` likewise use the camera entity for ray geometry. This changes target selection/camera handling, not the callback actor or its local velocity.

### Local spectator player velocity responder

`client/player/LocalPlayer.java` on both sides defines `onGameModeChanged(SPECTATOR)` to set the player's Y velocity to zero while preserving X/Z. It is a direct local response and was checked as in-scope movement behavior; the operation and gate are the same. The 26.x `applyInput()` and vertical flying input are gated by `isControlledCamera()`, which is `minecraft.getCameraEntity() == this` in all three 26.x trees. These camera-control details are recorded only to separate camera movement from a local attack response. The bounded route does not imply that camera-follow movement needs historical emulation.

## Coverage boundary and limits

Examined the attack-key dispatch, entity-hit attack actor, spectator branch, piercing callback, lunge / post-piercing effect gates, pick target source, and the direct local spectator-mode velocity responder. Examined 26.1, 26.1.1 and 26.1.2 for those route bodies; their cited client/game-mode/entity source files are byte-identical. The local spectator direct responder is present on both endpoints with the same behavior.

Not examined: the server packet handlers or server-side effect implementation; item or damage eligibility; replication; unrelated player movement operations; intermediate releases between the endpoints; or camera-only movement. Consequently this audit neither freezes full-pair coverage nor proves runtime parity.

## Terminal identity

- Status: **COMPLETED**
- Input comparison SHA-256: `f67dcc880dec42fd5302d4ff72173a2b2e7297c16970b65c7f7275f4ff6ede47`
- Verdict SHA-256: returned alongside this report; the terminal value hashes the final file bytes.