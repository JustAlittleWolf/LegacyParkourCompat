# Player.stabAttack client reachability — source-only dependency gate

Pair: Minecraft 26.1.2 → 26.2. Namespace: exact published `unobfuscated` sources at `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`. This is a bounded client reachability check for the Player impulse report, not a pair freeze, gameplay/runtime test, or conclusion about indirect authoritative server velocity responses.

## Source identities

- 26.1.2: provenance sidecar `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`, SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`, declares `versionId=26.1.2`, `namespace=unobfuscated`. Ready sidecar `decompiled_minecraft/26.1.2/unobfuscated.ready.json`, SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`, declares ready and source manifest `unobfuscated.sources.sha256`, SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.
- 26.2: provenance sidecar `decompiled_minecraft/26.2/unobfuscated.provenance.json`, SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`, declares `versionId=26.2`, `namespace=unobfuscated`. Ready sidecar `decompiled_minecraft/26.2/unobfuscated.ready.json`, SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, declares ready and source manifest `unobfuscated.sources.sha256`, SHA-256 `a7ad74fc712567eb87136afc1eaFCFD602B4088E9869F3596331FD34FAEFE894`.

The ready/source-manifest identities above were rechecked against the sidecars. Every cited Java-file digest below matched its entry in the corresponding source manifest and current file bytes. No source-tree-wide rehash or packaged artifact verification was performed.

## Paired source route

1. **Local client input and piercing weapon.** In both versions, `Minecraft.handleKeybinds`/its input loop consumes `keyAttack` and calls `startAttack` (26.1.2 around lines 2023–2036; 26.2 around 1942–1954). `startAttack` reads the local player's main-hand item, checks feature/attack gates, and when `PIERCING_WEAPON` is present calls `MultiPlayerGameMode.piercingAttack`. The LocalPlayer class extends AbstractClientPlayer, and AbstractClientPlayer extends Player, on both endpoints (`LocalPlayer.java:108`, `AbstractClientPlayer.java:19`).

   `MultiPlayerGameMode.piercingAttack` does not call `PiercingWeapon.attack` or `Player.stabAttack`: it first ensures the carried item was sent, then sends `ServerboundPlayerActionPacket(Action.STAB, ...)`; locally it calls `onAttack`, `postPiercingAttack`, and the sound path (26.1.2 lines 515–521; 26.2 lines 520–526). The local `startAttack` code also swings the main hand after this game-mode call. These local callbacks are not the `causeExtraKnockback` write in `Player.stabAttack`.

2. **Server packet consumer.** In both versions `ServerGamePacketListenerImpl.handlePlayerAction` first applies the same-thread handoff, then its `STAB` case rejects spectators, obtains the server player's main-hand item, rejects `cannotAttackWithItem(item, 5)`, and if it has `PIERCING_WEAPON`, calls `piercingWeapon.attack(this.player, MAINHAND)` (26.1.2 around lines 1273–1290; 26.2 around 1273–1292). This receiver is the server-side `ServerPlayer`; the local client sent a request and did not directly execute the attack operation.

   `PiercingWeapon.attack` queries target hits, then calls `attacker.stabAttack(...)` for each hit (26.1.2 `PiercingWeapon.java:79`; 26.2 `:79`). At this call site the `attacker` is the `ServerPlayer` passed by the packet consumer. Dynamic dispatch may reach `Player.stabAttack`, but it is running on the server entity. The `Player.stabAttack` method's change and attacker damping belong to the paired method-body evidence in the impulse report; this report closes only its local-client caller gate.

3. **Kinetic weapon use-tick path.** `LivingEntity.updatingUsingItem` calls `updateUsingItem` for a still-held use item (26.1.2 `LivingEntity.java:3370–3378`; 26.2 `:3436–3444`); `updateUsingItem` calls `ItemStack.onUseTick` (26.1.2 `:3415–3420`; 26.2 `:3481–3486`). In both versions `ItemStack.onUseTick` calls `KineticWeapon.damageEntities` only under `kineticWeapon != null && !level.isClientSide()`; on the client it instead takes `getItem().onUseTick` (26.1.2 and 26.2 `ItemStack.java:1092–1104`). The server-only kinetic branch calls `livingEntity.stabAttack` inside `KineticWeapon.damageEntities` (`KineticWeapon.java:131` in both). Thus this ordinary client-held-use route also does not invoke the stab scan locally.

4. **Call-site inventory.** A paired recursive text search for `stabAttack(` and `.stabAttack(` across both complete published Java source trees found the only invocation sites in `KineticWeapon.damageEntities` and `PiercingWeapon.attack`; the other matches are the `LivingEntity` and `Player` declarations. There is no additional direct LocalPlayer invocation site in these source trees. This inventory is limited to exact source call sites and does not imply completeness of movement writers outside this method route.

## Reachability verdict and limits

**Direct LocalPlayer reachability: not established; inspected source routes exclude direct execution in the two weapon mechanisms.** The kinetic weapon scan is guarded off on a client level. A normal client piercing-weapon attack sends a serverbound `STAB` action; its consumer calls `PiercingWeapon.attack` using the server's player, not the local `LocalPlayer`. Therefore the added Player attacker-side horizontal damping in `Player.stabAttack` is not shown here to run directly on the local client through these routes in either release.

This does not establish that the source change has no player movement consequence: the server-side player may be authoritatively updated and later synchronized to the client, a separate response/dependency path outside this bounded gate. It also does not close attack/event modifiers, all data-driven weapon configurations, target/contact geometry, or pair coverage. The 26.1.2→26.2 pair remains partial.

No build, test, game launch, runtime, source generation, wiki/backlog/product/history review, canonical finding, or product change was performed.

## Cited source file hashes

| Relative Java path | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/Minecraft.java` | `8b072df57a6efb47bcb4f5b2ac1bb750ed8bfc7b9441b1894167a7b8c046b4b1` | `c79d698d2323cb97a7e7570fa736cc7a0b88eab7adab8577e1bc66ba021ccdf0` |
| `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `674fe62b7debec3fa582735ae1b86247a25211ec03ec19c9f331dd3c44502e37` | `35bbed576b007d610e1892c95dd6fc1ffaa7f216535709cc07bd422c2e039566` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` | same |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `7e643834e2ab54bd1b1aeb79f72146ac4e1f6d30b34e7608091a9ab15494d3e7` | `2015d9e3c8d3e277bc2ef9e709ad4c991894df1deaa2eaca3a5a5dcc29fb51d2` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/item/ItemStack.java` | `26ba4af70893fae5107105ed46ed28cd3d51c7b4aba22bc8810f01b82177e146` | `c9d29d21e59a75fdeccdc68f115585114e2e489eed3b25148e3bbc61ce0bd0dc` |
| `net/minecraft/world/item/component/KineticWeapon.java` | `91f43a4ecd0bbcd2f412dbc67df4221268eefe0dcc5fb88a30f737ff40d92ea0` | `066547ee8d79d75ee20ee4025a511c43954ee07a8c1ea483f5dbd0bbc29e2db5` |
| `net/minecraft/world/item/component/PiercingWeapon.java` | `8c854c02d95a6208fc9306e49101cb26ec3e5747d84e74c26eefa6b1c161e355` | `5c5277fb61b820e4dfffd306a058f8b613594c632ceffce892049140c1779621` |