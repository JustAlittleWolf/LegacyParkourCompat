# Powder snow adds a player climb assist

- Older version A: `1.16.5`
- Newer version B: `1.17.1`
- Mechanic / coverage slice IDs: `S3-POWDER-SNOW-CLIMB`, `S5-POWDER-SNOW`
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: unknown within (1.16.5, 1.17.1]
- Runtime validation: not performed

## Paired evidence

- A: `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/entity/LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement()` around line 2067 sets Y to `0.2` only under `(horizontalCollision || jumping) && onClimbable()`; SHA-256 `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`.
- B: `decompiled_minecraft/1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, same method around lines 2155–2165 adds a powder snow branch; SHA-256 `33FD081AADB2B6FDC9EBF487DB6DA5B38C54F4B8676572790EE2203690D15E6F`.
- B `world/level/block/Blocks.java` registers `POWDER_SNOW` at lines 3496–3498; SHA-256 `87D72A113A3F8937A6A585EF917A4FD29CC5B335C00A858F6800E38F3C1BF7A8`.
- B `world/level/block/PowderSnowBlock.java`, `canEntityWalkOnPowderSnow()` lines 111–116; SHA-256 `4069B3EF70E2D0CE146BBFF79BE54B2BE99B7C7B32818C1EB45F11FD66F99AA3`. The player type is not in B's vanilla `powder_snow_walkable_mobs` tag; its leather-boots branch applies to `LivingEntity` feet equipment. Exact B client-jar entry `data/minecraft/tags/entity_types/powder_snow_walkable_mobs.json`, SHA-256 `A1D2F5C240C8D21446675AC242F898CC23EF3C343A2BC5FE125249192C9FBC3E`; values are rabbit, endermite, silverfish and fox.
- A `Blocks.java` has no `POWDER_SNOW` registration, the A source output has no `PowderSnowBlock.java`, and the A client jar has no powder-snow resource entries. A `Blocks.java` SHA-256 `3B39D5CC4CD22F146ED3195AA30CBB9FDFCA49F63783FABF9924A6CE7795FA12`.

## Source-level difference

B broadens the climb-assist predicate to `(horizontalCollision || jumping) && (onClimbable || feetBlockIsPowderSnow && canEntityWalkOnPowderSnow(entity))`. When true it returns a vector preserving X and Z while setting Y to `0.2`. For a player, the powder-snow predicate can be satisfied by wearing leather boots in the feet slot. A has no powder-snow block or branch.

## Reachability and dependencies

Player travel calls the shared living movement helper. The precondition is that the player's feet block is powder snow, the player is wearing leather boots, and either horizontal collision or jumping is active. The B helper checks entity type tags first, then recognizes leather boots for living entities. The cited B client-jar tag excludes players from its entity-type list; the equipment path is sufficient for the stated player case.

## Consequence and uncertainty

The source-proven B result is a vertical velocity component of `0.2` from this helper under the stated conditions. A lacks the block/state entirely. Powder snow was not a block/state an A-era map could contain, so this is recorded as modern-only and does not imply historical behavior for 1.16.5 maps. No trajectory was observed.

## Handoff

Independent modern-only mechanic: powder snow supports a climb assist for eligible entities. Keep it separate from the ordinary climbable-block rule and exclude it from A-era emulation.
