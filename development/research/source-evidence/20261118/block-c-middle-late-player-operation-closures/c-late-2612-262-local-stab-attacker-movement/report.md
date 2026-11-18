# C lane: 26.1.2 → 26.2 local `stabAttack` movement reachability

## Verdict

**No-action for ordinary local-player movement in this slice.** The `Player.stabAttack` implementation changes between these releases and contains attacker-side velocity/sprint writes, but neither vanilla local dispatch path invokes it on the logical client. A normal piercing-weapon attack sends a `STAB` packet; the server invokes `PiercingWeapon.attack`. The other ordinary entry, kinetic weapon use ticks, is explicitly server-side. Do not treat the conditional method-body delta as a local movement finding without a vanilla client call path.

This conclusion is limited to the ordinary vanilla `LocalPlayer` route to `Player.stabAttack` and its own client-side velocity/sprint response. It does not classify damage, target movement, non-player movement, server replay/replication, or geometry.

## Exact source and provenance

Primary source: local exact, decompiled, canonical `unobfuscated` trees in `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. No mapping lookup or source generation was used.

| Release | Provenance | Ready marker and source manifest | Identity checks |
|---|---|---|---|
| 26.1.2 | `unobfuscated.provenance.json` SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; exact `versionId=26.1.2`, `namespace=unobfuscated`, Vineflower `1.12.0`, Java `25.0.3+9-LTS` | `unobfuscated.ready.json` SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; source manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; artifact manifest `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`; movement diagnostics `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02` | ready marker identifies version `26.1.2`, mapping `unobfuscated`, client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; ready source/artifact/diagnostic hashes match the corresponding local files |
| 26.2 | `unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; exact `versionId=26.2`, `namespace=unobfuscated`, Vineflower `1.12.0`, Java `25.0.3+9-LTS` | `unobfuscated.ready.json` SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; artifact manifest `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`; movement diagnostics `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3` | ready marker identifies version `26.2`, mapping `unobfuscated`; ready source/artifact/diagnostic hashes match the corresponding local files |

For both sides, each Java file below was independently SHA-256 hashed and matched its path entry in that release's `unobfuscated.sources.sha256`. Ready-marker source-manifest, artifact-manifest, and movement-diagnostics digests also matched the corresponding files. The source trees report 6,882 and 7,055 files respectively.

| Source file (relative to each `unobfuscated/`) | 26.1.2 SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/Minecraft.java` | `8b072df57a6efb47bcb4f5b2ac1bb750ed8bfc7b9441b1894167a7b8c046b4b1` | `c79d698d2323cb97a7e7570fa736cc7a0b88eab7adab8577e1bc66ba021ccdf0` |
| `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `674fe62b7debec3fa582735ae1b86247a25211ec03ec19c9f331dd3c44502e37` | `35bbed576b007d610e1892c95dd6fc1ffaa7f216535709cc07bd422c2e039566` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/item/ItemStack.java` | `26ba4af70893fae5107105ed46ed28cd3d51c7b4aba22bc8810f01b82177e146` | `c9d29d21e59a75fdeccdc68f115585114e2e489eed3b25148e3bbc61ce0bd0dc` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `7e643834e2ab54bd1b1aeb79f72146ac4e1f6d30b34e7608091a9ab15494d3e7` | `2015d9e3c8d3e277bc2ef9e709ad4c991894df1deaa2eaca3a5a5dcc29fb51d2` |
| `net/minecraft/world/item/Item.java` | `a92a71ef9acd0a98e26914b61382e87f1ba098f779587d452f7373dfa8690ba4` | `215fb193bc9fc45702f55a19572ced3f03cc567a851c19050619255b2e59d01d` |
| `net/minecraft/world/item/component/PiercingWeapon.java` | `8c854c02d95a6208fc9306e49101cb26ec3e5747d84e74c26eefa6b1c161e355` | `5c5277fb61b820e4dfffd306a058f8b613594c632ceffce892049140c1779621` |
| `net/minecraft/world/item/component/KineticWeapon.java` | `91f43a4ecd0bbcd2f412dbc67df4221268eefe0dcc5fb88a30f737ff40d92ea0` | `066547ee8d79d75ee20ee4025a511c43954ee07a8c1ea483f5dbd0bbc29e2db5` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |

## Route trace and bounded movement behavior

### Client dispatch (both versions)

- `net/minecraft/client/Minecraft.java`: 26.1.2 `startAttack`, lines 1679–1684; 26.2 lines 1661–1667. For a held stack with `PIERCING_WEAPON`, the local attack path calls `gameMode.piercingAttack(piercingWeapon)`, swings, and returns from this branch.
- `net/minecraft/client/multiplayer/MultiPlayerGameMode.java`: 26.1.2 lines 515–521; 26.2 lines 520–526. `piercingAttack` sends `ServerboundPlayerActionPacket.Action.STAB`, then locally calls only `player.onAttack()`, `player.postPiercingAttack()`, and the sound method. It does not call `weapon.attack` or `player.stabAttack`.
- `net/minecraft/server/network/ServerGamePacketListenerImpl.java`: 26.1.2 lines 1273–1287 and 26.2 lines 1280–1295 dispatches `STAB` by reading the server player's main-hand component and calling `piercingWeapon.attack(this.player, EquipmentSlot.MAINHAND)`. This closes the route as server-side, which is excluded here.
- `net/minecraft/world/item/ItemStack.java`: both releases lines 1092–1104 call `kineticWeapon.damageEntities(...)` only under `kineticWeapon != null && !level.isClientSide()`; otherwise they call the item's ordinary `onUseTick`. `KineticWeapon.damageEntities` later calls `livingEntity.stabAttack` (26.1.2 lines 98–132; 26.2 lines 98–132), but that entry is gated off for the logical client.

### Conditional attacker writes inside the non-local method

The vanilla spear component is explicitly constructed with `dealsKnockback=true` and `dismounts=false` in `net/minecraft/world/item/Item.java` (26.1.2 lines 516–525; 26.2 lines 517–526). `PiercingWeapon.attack` passes that component flag directly to `attacker.stabAttack` (both `PiercingWeapon.java`, lines 69–89). Its default codec value is also true (`PiercingWeapon.java`, line 28).

- **26.1.2:** `Player.stabAttack` (lines 1193–1239) calls `causeExtraKnockback(target, 0.4F + this.getKnockback(target, damageSource), oldMovement)` when `dealsKnockback`. The inherited three-argument `LivingEntity.causeExtraKnockback` (lines 2688–2693) only writes the attacker's horizontal velocity when the target is a `LivingEntity` and the combined knockback is positive; then it multiplies the attacker's delta movement by `(0.6, 1.0, 0.6)`. The fixed `0.4F` makes the combined amount positive whenever the direct flag is true. This overload does not clear sprinting.
- **26.2:** `Player.stabAttack` (lines 1213–1260) makes two calls for `dealsKnockback`: fixed `0.4F` with `comesFromEffect=false`, then `getKnockback(...)` with `comesFromEffect=true`. The `Player.causeExtraKnockback` override (lines 1122–1155) gates each self-write on `knockbackAmount > 0.0F`; after target handling it multiplies the player's own horizontal delta by `0.6` and calls `setSprinting(false)`. Thus a local invocation would always execute the fixed positive call when the direct flag is true; the second call depends on the effective attack-knockback amount.
- `LivingEntity.getKnockback` (26.1.2 lines 1511–1515; 26.2 lines 1540–1545) starts from `getAttributeValue(Attributes.ATTACK_KNOCKBACK)`. On a client it returns that effective value divided by `2.0F`; enchantment modification is only used for `ServerLevel`. `Attributes.ATTACK_KNOCKBACK` has default base `0.0` (26.1.2 `Attributes.java` line 15; 26.2 line 18), and `LivingEntity.createLivingAttributes` includes it (lines 338 / 352). Therefore an unmodified vanilla local player's second 26.2 amount is zero and fails the positive-amount gate; an effective positive attribute would permit it. This attribute gate affects only the unreachable local call considered here.

The method-body change is source-confirmed, but the normal local route has no call to it in either version. No local attacker movement or sprint delta is therefore attributable to this operation for ordinary vanilla input in this pair. This is a bounded no-action disposition, not full-pair movement coverage or a runtime-parity claim.


