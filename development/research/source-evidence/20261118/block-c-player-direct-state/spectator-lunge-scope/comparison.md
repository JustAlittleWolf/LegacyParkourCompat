# Spectator attack and lunge route comparison

## Scope and disposition

Compared exact source `1.21.11` Mojmap with `26.1`, `26.1.1`, and `26.1.2` published unobfuscated source. The bounded route is the client attack key path, entity targeting through the camera entity, spectator handling, the spear/piercing callback, and the direct lunge effect authority.

**Disposition: no in-scope local receiver velocity delta is established.** The 1.21.11 callback named `lungeForwardMaybe` does not locally mutate player velocity: `LivingEntity.lungeForwardMaybe()` applies its effect only when `level()` is a `ServerLevel`; client `ClientLevel` does not satisfy that gate. In 26.x the client invokes `postPiercingAttack()` instead, whose body has the same explicit `ServerLevel` gate and only dispatches server-side post-attack enchantment effects. The distinct 26.x spectator click route is a modern client interaction/input route, while the effects that can alter authoritative movement remain server-side and outside the mod's historical client-movement scope. No historical client movement hook is indicated by this comparison.

This conclusion is limited to the bounded source route. It does not establish the effect's server implementation, item/damage eligibility, replication behavior, or every change between these endpoint releases.

## Exact source witnesses

### 1.21.11 Mojmap

- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/Minecraft.java:1600-1650`: `startAttack()` checks the piercing weapon path only when `!gameMode.isSpectator()`. Its entity-hit path calls `gameMode.attack(player, entity)`.
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java:420-426`: `attack()` sends the attack packet, then calls `player.attack()` and resets the ticker only when `localPlayerMode != SPECTATOR`.
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/multiplayer/MultiPlayerGameMode.java:500-506`: `piercingAttack()` sends the ordinary STAB player-action packet, then invokes local `player.onAttack()` and `player.lungeForwardMaybe()`.
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java:1571-1579`: player override gates `super.lungeForwardMaybe()` on `hasEnoughFoodToDoExhaustiveManoeuvres()`; that predicate reads food sufficiency or `mayfly`.
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java:1655-1657`: the base callback calls `EnchantmentHelper.doLungeEffects()` only under `level() instanceof ServerLevel`.
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java:198-201`: `doLungeEffects(ServerLevel, Entity)` iterates weapon enchantments and invokes their Lunge effects. This is a server-authority witness, not an analysis of their item eligibility or damage outcome.
- `decompiled_minecraft/1.21.11/mojmap/net/minecraft/client/renderer/GameRenderer.java:341-352`: picking obtains `minecraft.getCameraEntity()` and passes it to `minecraft.player.raycastHitResult(...)`. `LocalPlayer.java:1203-1241` uses that entity for item range and ray geometry. Thus the target ray follows the camera entity, while the attack callback actor remains `this.player` (see `Minecraft.java:1600-1650`).

### 26.x published unobfuscated

The following route bodies have identical SHA-256 hashes in all three exact release trees (`26.1`, `26.1.1`, `26.1.2`), so the cited 26.1.2 line ranges also apply to 26.1 and 26.1.1:

- `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/client/Minecraft.java:1646-1715`: `startAttack()` handles spectator mode first; an entity hit calls `gameMode.spectate(entity)` and returns. Non-spectator mode then checks the held item and dispatches a `PiercingWeapon` through `gameMode.piercingAttack()`.
- `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/client/multiplayer/MultiPlayerGameMode.java:425-434`: ordinary `attack()` sends the attack packet and invokes `player.attack()`; `spectate()` sends a spectate-entity packet. Neither method writes player velocity.
- `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/client/multiplayer/MultiPlayerGameMode.java:515-521`: `piercingAttack()` sends the STAB action, invokes local `player.onAttack()` and `player.postPiercingAttack()`, then plays the weapon sound. It no longer calls `lungeForwardMaybe()` in this client callback.
- `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java:1675-1678`: `postPiercingAttack()` invokes its effect helper only for `ServerLevel`.
- `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/client/Minecraft.java:2941-2950` obtains the camera entity and passes it to `player.raycastHitResult(...)`; `LocalPlayer.java:1242-1281` uses it for item-range/raycast geometry. This targeting is separate from the player object passed to `gameMode.attack()`.

The same cited bodies were checked in 26.1 and 26.1.1. `LocalPlayer.isControlledCamera()` in all three is `minecraft.getCameraEntity() == this` (`LocalPlayer.java:744-746`), confirming that local movement/input control is camera-selected; it does not redirect the attack actor to the camera entity.

## Coverage and limits

- Checked: client attack start path; spectator gate/selection; standard entity attack callback; piercing callback; direct lunge/post-piercing effect authority gate; camera-based target selection and controlled-camera predicate.
- Not checked: server packet handlers or server-side effect execution; item and damage eligibility; attack replication; movement-loop inventory outside this bounded route; intermediate patch boundaries.
- Exact release provenance manifests exist for all four source sets. Relevant source file digests matched the per-release `*.sources.sha256` manifests. Hashes are reported below to pin this comparison's evidence.

## Evidence identity

SHA-256 source hashes:

- 1.21.11 Mojmap: `Minecraft.java` `e41d192579972ea043cf39ad7f754e1279dca63aa6a576edceb4c9928d685d59`; `MultiPlayerGameMode.java` `4d15d5c6b8c280d0e2c6de1b0f41bde83bc0944d400b7f89cabff24679e9a9c0`; `LocalPlayer.java` `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`; `Player.java` `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; `LivingEntity.java` `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `EnchantmentHelper.java` `38e944de3098847f62633a6f0747bc2e1dcf016694faebe47009fbe8af514a85`.
- 26.1, 26.1.1, and 26.1.2 unobfuscated: each of these compared files has the same digest across the three releases and matched each release's source manifest: `Minecraft.java` `8b072df57a6efb47bcb4f5b2ac1bb750ed8bfc7b9441b1894167a7b8c046b4b1`; `MultiPlayerGameMode.java` `674fe62b7debec3fa582735ae1b86247a25211ec03ec19c9f331dd3c44502e37`; `LocalPlayer.java` `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`; `LivingEntity.java` `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`; and `GameRenderer.java` `d0d926c9e48398bf25b47b9d2fcd13f05b4e9da1e67be0f8476f71cf0cc8e732`.