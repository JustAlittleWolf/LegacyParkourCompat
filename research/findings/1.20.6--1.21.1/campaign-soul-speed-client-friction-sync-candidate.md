# soul-speed-client-friction-sync-candidate: Soul Speed block-speed selection moves to a server-activated attribute

1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, move 706-707; move 705-706.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java`, onSoulSpeedBlock 489-496.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, getBlockSpeedFactor 485-488; baseTick 433-439.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/entity/player/Player.java`, onSoulSpeedBlock 975-977.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/player/Player.java`, getBlockSpeedFactor 1973-1975.
1.20.6, mojmap: `1.20.6/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java`, getEnchantmentLevel 150-166.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/ai/attributes/Attributes.java`, class declaration / field initialization 71-73.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/Enchantments.java`, bootstrap 506-522.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java`, onChangedBlock 39-57.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java`, handleUpdateAttributes 2062-2076.

Difference: For a non-flying player with Soul Speed boots on a tagged block, 1.20.6 evaluates block membership and enchantment level locally and returns `1.0F` directly. 1.21.1 evaluates the current `MOVEMENT_EFFICIENCY` attribute. The base attribute is zero; Soul Speed raises it to `1.0F` through a location-changed effect whose movement callback requires `ServerLevel`. The client therefore consumes a server-produced attribute value, with the normal block factor used while that value is absent or stale.

Movement meaning: Local player tick -> `LivingEntity#travel` -> `Entity#move` -> `Player#getBlockSpeedFactor` -> `LivingEntity#getBlockSpeedFactor` -> horizontal delta-movement multiplier. On a transition onto Soul Sand with Soul Speed boots, 1.20.6's current-block query returns `1.0F`; 1.21.1's first local movement uses the currently available movement-efficiency attribute. 1.21.1's server block-position callback activates the location effect, and tracked dirty attributes are sent to the client. The tag and Soul Sand `0.4F` base speed factor establish a concrete block where the multipliers differ if the attribute has not yet reached the local client. Flight and fall-flying are excluded by the unchanged Player override. Source proves the client-side consumer changed from a direct local tag/enchantment check to a syncable attribute and that the effect producer is server-only. If a local move uses the default/stale value after entering Soul Sand, 1.21.1 multiplies horizontal velocity by the block's `0.4F` speed factor while 1.20.6 uses `1.0F`. No position trace or gameplay result is claimed.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.20.6--1.21.1/findings/soul-speed-client-friction-sync-candidate.md).
