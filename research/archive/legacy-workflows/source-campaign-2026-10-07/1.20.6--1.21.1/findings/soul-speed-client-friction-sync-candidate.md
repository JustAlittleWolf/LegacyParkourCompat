# soul-speed-client-friction-sync-candidate: Soul Speed block-speed selection moves to a server-activated attribute

- Older version A: 1.20.6
- Newer version B: 1.21.1
- Mechanic / coverage slice IDs: L2, E2, X1
- Classification: changed behavior
- Confidence: candidate (source proves the client consumer switched to a server-activated, syncable attribute; exact packet arrival relative to the next local prediction tick is not validated)
- Applicability: historical player behavior
- First changed release: unknown within (1.20.6, 1.21.1]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact: `ready/1.20.6/mojmap`; `net/minecraft/world/entity/LivingEntity.java`, `getBlockSpeedFactor()`, lines 489-496, SHA-256 `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`. Its Soul Speed predicate checks the current Soul Speed block and `EnchantmentHelper.getEnchantmentLevel(SOUL_SPEED, this)` on every call. `net/minecraft/world/entity/player/Player.java`, `onSoulSpeedBlock()`, lines 975-977 and `getBlockSpeedFactor()`, lines 1978-1980, SHA-256 `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`; the Player overrides exclude ability flight/fall-flying, while the non-flying client player reaches the LivingEntity predicate.
- B manifest artifact: `ready/1.21.1/mojmap`; `net/minecraft/world/entity/LivingEntity.java`, `getBlockSpeedFactor()`, lines 485-488, SHA-256 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`. It reads `MOVEMENT_EFFICIENCY` and interpolates the base block speed factor toward `1.0F`. `net/minecraft/world/entity/player/Player.java`, `getBlockSpeedFactor()`, lines 1973-1975, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`, retains the flying/fall-flying bypass.
- B attribute/effect producer: `net/minecraft/world/entity/ai/attributes/Attributes.java`, lines 71-73, SHA-256 `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c`, declares movement efficiency default `0.0`, range `0..1`, syncable. `net/minecraft/world/item/enchantment/Enchantments.java`, Soul Speed registration lines 506-522, SHA-256 `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266`, attaches a location-changed `MOVEMENT_EFFICIENCY` modifier of `1.0F` when the entity is affected by a Soul Speed block. `net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java`, lines 39-57, SHA-256 `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270`, adds/removes the transient modifier.
- B effect activation/synchronization: `net/minecraft/world/entity/LivingEntity.java`, server-only block-position callback in `baseTick()` lines 433-439 and `onChangedBlock(ServerLevel,BlockPos)` lines 514-516, same B `LivingEntity.java` hash above. `net/minecraft/server/level/ServerEntity.java`, dirty sync lines 322-329, SHA-256 `0994216505c8df7cc67a7a068ae8e71d1f023dbef5dc1dc31360e3e5bdc2ce14`, sends syncable attribute changes; `net/minecraft/client/multiplayer/ClientPacketListener.java`, `handleUpdateAttributes`, lines 2062-2076, SHA-256 `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d`, applies received values on the client.
- Concrete tagged block: `Blocks.java`, lines 2282-2298, in both exact versions sets Soul Sand `speedFactor(0.4F)` (A SHA-256 `684579bbcbe48b1f0984090a4ca044c0b2cee6d08c2dc1e449e2259389c5b129`; B SHA-256 `538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d`). The Soul Speed blocks tag is the same in both jars: `data/minecraft/tags/blocks/soul_speed_blocks.json`, SHA-256 `8ba7eac6f7c74d25601ef4455b71e3947fdb8e51b8fafd55dddee48397b144b6`.
- Consumer: B `net/minecraft/world/entity/Entity.java`, `move(MoverType,Vec3)`, lines 706-707, SHA-256 `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`, reads `getBlockSpeedFactor()` after movement and multiplies horizontal delta movement by it. A corresponding `Entity#move` lines 705-706, SHA-256 `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`.

## Source-level difference

For a non-flying player with Soul Speed boots on a tagged block, A evaluates block membership and enchantment level locally and returns `1.0F` directly. B evaluates the current `MOVEMENT_EFFICIENCY` attribute. The base attribute is zero; Soul Speed raises it to `1.0F` through a location-changed effect whose movement callback requires `ServerLevel`. The client therefore consumes a server-produced attribute value, with the normal block factor used while that value is absent or stale.

## Reachability and dependencies

Local player tick -> `LivingEntity#travel` -> `Entity#move` -> `Player#getBlockSpeedFactor` -> `LivingEntity#getBlockSpeedFactor` -> horizontal delta-movement multiplier. On a transition onto Soul Sand with Soul Speed boots, A's current-block query returns `1.0F`; B's first local movement uses the currently available movement-efficiency attribute. B's server block-position callback activates the location effect, and tracked dirty attributes are sent to the client. The tag and Soul Sand `0.4F` base speed factor establish a concrete block where the multipliers differ if the attribute has not yet reached the local client. Flight and fall-flying are excluded by the unchanged Player override.

## Consequence and uncertainty

Source proves the client-side consumer changed from a direct local tag/enchantment check to a syncable attribute and that the effect producer is server-only. If a local move uses the default/stale value after entering Soul Sand, B multiplies horizontal velocity by the block's `0.4F` speed factor while A uses `1.0F`. The exact attribute-packet arrival order relative to local prediction is not established by this source pass, so the timing-specific client trajectory remains a candidate and needs independent source review. No position trace or gameplay result is claimed.

## Handoff

Independent delta candidate: determine whether B's Soul Speed attribute reaches the local prediction path before its first post-entry `Entity#move` block-speed multiplication. Related to the Depth Strider attribute migration only at the general modifier-system level; the behavior is independently scoped. Applicability excludes player flight/fall-flying. First changed release remains unknown within `(1.20.6, 1.21.1]`.