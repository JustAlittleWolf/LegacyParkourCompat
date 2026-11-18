# Blind audit freeze: local movement effects and attribute modifiers

Pair: exact Mojmap 1.21.10 → 1.21.11. Scope: ordinary ongoing local-player effect duration/expiry, client movement readers, and speed/jump/gravity attribute modifier application, removal, evaluation order and timing. This is a bounded equivalence audit, not full-pair coverage.

## Source identity and integrity

Both canonical trees are under D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/ and identify versionId exactly as 1.21.10 and 1.21.11, namespace mojmap, client artifacts, Java 25/Vineflower. The ready markers identify raw client SHA-256 e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482 (1.21.10) and 1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd (1.21.11). Both provenance records identify the exact batch 1.21.8,1.21.10,1.21.11, mapping option mojmap, Vineflower 1.12.0, Java 25.0.3+9-LTS, and the published Mojang mapping pipeline.

The 1.21.10 complete source manifest SHA-256 is 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1; the 1.21.11 manifest SHA-256 is 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555. I checked all 6,386 and 6,622 manifest entries respectively against the full bytes of their source files; every listed file exists and its SHA-256 matches. The following full-file digests identify the reviewed source bodies (paths relative to each mojmap/ root):

| Source | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| net/minecraft/world/effect/MobEffectInstance.java | 8b35c542a25090f7a45fa019a5263ea51cc1beb2d58605dd4ed65e805035bc36 | d786ece1cbc91c67907c422a9369bbbb9949af35a76dcc44ee8d1767c22c6367 |
| net/minecraft/world/effect/MobEffect.java | a59b16b493d0c1fe164dc09e28a00050f4bf47ad95df0ed67e205b46b10a0e66 | 1c4877780a246facd2e5ac392763c960580881d9ea90335d0841a5e982a12df7 |
| net/minecraft/world/effect/MobEffects.java | 59460ae5a2087122e88eb4e6f799466ed3ea313a04a9e9162a9abf76dea440ae | e27eef187ce134c3b64300fd7a4c7f691d553a172ec26ebac79797a030db7047 |
| net/minecraft/world/entity/LivingEntity.java | b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66 | 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8 |
| net/minecraft/world/entity/ai/attributes/AttributeInstance.java | c2d4f89e7ae2abbe856e7e1aa5bff2ca613073cbda282c1975636e479ba8e346 | 4b89cf81cf75b16ed743b6bd0a683154c481247230b56dfa37c11ccfe17004c1 |
| net/minecraft/world/entity/ai/attributes/AttributeMap.java | 06088d7aeedd65b468b807c3a500da7a3a16d787917812a55dd7b81ad60cd079 | c8dfebeefa367556ff706951cacea308c18da53fa28108c18f59789e6f1b60e2 |
| net/minecraft/world/entity/ai/attributes/Attribute.java | 16e9b68e7c7b04a8cc32f6bc76e042e61c3b49f6cdf27ad7f878b12f430c52db | 16e9b68e7c7b04a8cc32f6bc76e042e61c3b49f6cdf27ad7f878b12f430c52db |
| net/minecraft/world/entity/player/Player.java | af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82 | 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81 |
| net/minecraft/client/player/LocalPlayer.java | 9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1 | 948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477 |
| net/minecraft/client/player/AbstractClientPlayer.java | 052e8387ace3ee65ea7456af23b8e9197931e5d5faeda54589166dce60269296 | afe84ab1491a17aeeccf895ca4ecb17e941b8e517c980e31281b3d463fc593bd |
| net/minecraft/world/entity/Entity.java | 8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18 | 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3 |

These are full-file source digests from the complete manifests, not hashes of excerpts. The tree identity, marker identities and hashes were checked locally; no source was regenerated.

## Bounded trace and comparison

- LivingEntity.baseTick() reaches tickEffects() on the local client. tickEffects() calls MobEffectInstance.tickClient() for each active effect on both versions. Client ticking decrements finite duration by one and downgrades a hidden effect when appropriate; -1 remains infinite. It does not remove an expired instance from the client active-effect map or locally add/remove its attribute modifier. Effect removal and modifier changes arrive through the ordinary server synchronization path.
- On the server, MobEffectInstance.tickServer() checks remaining duration, evaluates a periodic effect using the current duration/amplifier, invokes it if due, decrements duration, promotes the hidden effect when due, and reports whether duration remains. LivingEntity.tickEffects() removes the effect when this returns false and calls onEffectsRemoved; both versions preserve this sequence. This bounds the one-tick expiry order for the ordinary synced local-player effect.
- LivingEntity.onEffectAdded, onEffectUpdated, and onEffectsRemoved apply/remove attribute modifiers only on the logical server; update removes the old effect modifier before creating the amplifier-scaled replacement and refreshes dirty attributes. The local player receives the effect state and synchronized attribute state through the existing packet flow. No client-local effect-duration branch mutates movement attributes.
- Speed and slowness in MobEffects both attach to MOVEMENT_SPEED using ADD_MULTIPLIED_TOTAL, with base amounts +0.2F and -0.15F; MobEffect.AttributeTemplate.create(amplifier) uses amount * (amplifier + 1). Those declarations and the template arithmetic match. No vanilla movement effect here attaches to JUMP_STRENGTH or GRAVITY; jump boost is read directly from the active effect amplifier.
- AttributeInstance.calculateValue() is the same ordered procedure: start at base; add ADD_VALUE amounts; snapshot that result and add each ADD_MULTIPLIED_BASE amount times the snapshot; then multiply successively by each ADD_MULTIPLIED_TOTAL factor; finally sanitize. Dirty caching, modifier ID replacement/removal, and dirty propagation retain the same order and behavior. The Mojmap API type for IDs changes from ResourceLocation to Identifier in 1.21.11; this is a namespace/type rename, not a math or gate change. AttributeMap remains the holder lookup into the same per-attribute instance behavior.
- Reachable local movement readers are unchanged in operation: Player.getSpeed() casts getAttributeValue(MOVEMENT_SPEED) to float; LivingEntity.getJumpPower(float) casts JUMP_STRENGTH to float, multiplies by the supplied float and block jump factor, then adds getJumpBoostPower(); that reader returns 0.1F * (amplifier + 1.0F) when JUMP_BOOST is present; LivingEntity.getDefaultGravity() returns getAttributeValue(GRAVITY), with Entity.getGravity() retaining the no-gravity gate. The LocalPlayer and AbstractClientPlayer speed consumers retain their casts/division/multiplication order. The compared 1.21.10 and 1.21.11 bodies for these readers are equivalent.
- A 1.21.11 LivingEntity change adds updateDirtyEffects() from updateDataBeforeSync() and removes the old dirty-visibility update from the end of the server effect loop. It only refreshes synchronized visibility/glow/particle state; it does not alter effect duration, attribute modifiers, or any scoped local movement reader. It is outside this audit's movement-effect delta claim.

## Verdict

No source difference found in the bounded ordinary local movement-effect duration/expiry, speed/jump/gravity modifier application/removal/evaluation, or movement-reader order between exact Mojmap 1.21.10 and 1.21.11. This closes only the bounded equivalence slices above. It does not establish complete pair coverage or runtime parity.

## Checked source member anchors

All paths below are under each version's mojmap/ root. Anchors are checked in both releases; line numbers differ where noted.

- MobEffectInstance.tickServer, tickClient, tickDownDuration, downgradeToHiddenEffect: net/minecraft/world/effect/MobEffectInstance.java around lines 214–270 (same member order).
- LivingEntity.tickEffects: net/minecraft/world/entity/LivingEntity.java, 1.21.10 lines 824–862; 1.21.11 lines 828–860. baseTick calls it at 481 / 482 respectively.
- LivingEntity.addEffect, forceAddEffect, removeEffect, onEffectAdded, onEffectUpdated, onEffectsRemoved: same path, around lines 985–1095 / 990–1100.
- MobEffect.addAttributeModifier, AttributeTemplate.create, addAttributeModifiers, removeAttributeModifiers: net/minecraft/world/effect/MobEffect.java around lines 134–205 in both.
- Speed/slowness/jump boost definitions: net/minecraft/world/effect/MobEffects.java around lines 15–60 in both.
- AttributeInstance.addModifier, removeModifier, getValue, calculateValue, setDirty: net/minecraft/world/entity/ai/attributes/AttributeInstance.java, around lines 70–170 in both.
- Player.getSpeed: net/minecraft/world/entity/player/Player.java, 1.21.10 lines 1339–1341; 1.21.11 lines 1404–1406.
- LivingEntity.getJumpPower(float), getJumpBoostPower, getDefaultGravity; Entity.getGravity: corresponding LivingEntity.java and Entity.java members around lines 2226–2268 / 2260–2301.