# 1.15.2 blocks Riptide use at one remaining durability

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/world/item/TridentItem.java`, `use`, lines 117–129.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/world/item/TridentItem.java`, `use`, lines 111–122.

Difference: With exactly one durability remaining, 1.14.4 permits starting a charged Riptide use; releasing it follows the shared impulse path, even though the server-side durability cost can break the Trident. 1.15.2 rejects starting use at that durability value, so the player cannot receive this impulse through that use. This is a player movement difference caused by item-use availability.

Movement meaning: Player item use -> `TridentItem.use` durability and environment checks -> charged release -> `player.push` and grounded vertical move.
