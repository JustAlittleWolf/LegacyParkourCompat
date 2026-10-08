# F-SOUL-SAND-SPEED: Soul Sand applies a new horizontal velocity factor

- Older version A: Minecraft 1.14.4
- Newer version B: Minecraft 1.15.2
- Mechanic / coverage slice IDs: INV-COLLISION, INV-WORLD-MOVEMENT, INV-STATE; S-ENTITY-MOVE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: within (1.14.4, 1.15.2]
- Boundary resolution: the endpoint difference is confirmed; the earliest changed release inside the interval has not been established, and no intermediate-release source check is claimed.
- Runtime validation: not performed

## Paired evidence

- A `net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3)`, lines 533-550 performs `checkInsideBlocks()` and then proceeds to fluid/fire handling without block-speed scaling; SHA-256 `31c6be42d165102d3e6dc4295dfef6e8971fc3aea13f938a5b3a33b91d524a7`.
- B `net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3)`, lines 533-544 performs `checkInsideBlocks()` then `setDeltaMovement(getDeltaMovement().multiply(getBlockSpeedFactor(), 1.0, getBlockSpeedFactor()))`; `getBlockSpeedFactor()` lines 590-598 checks current and support blocks, with water/bubble special handling; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- A `net/minecraft/world/level/block/Blocks.java::Blocks.SOUL_SAND`, lines 577-579 registers Soul Sand without a speed factor; SHA-256 `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`.
- B `net/minecraft/world/level/block/Blocks.java::Blocks.SOUL_SAND`, lines 601-604 registers `.speedFactor(0.4F)`; SHA-256 `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`.
- A `net/minecraft/world/level/block/Block.java::Block.Properties` has friction only among these movement properties (default `0.6F`, lines 851-852); B adds `speedFactor` with default `1.0F` and accessor/builder (lines 717-723, 855-860, 915-918). Hashes: A `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f`; B `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d`.
- Player ordinary ground travel reaches `Entity.move(SELF, getDeltaMovement())` through A `LivingEntity.java` lines 1837-1842 and B lines 1886-1892; hashes A `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`, B `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.

## Source-level difference

For a player moving normally on Soul Sand, A retains the post-collision horizontal delta movement. B multiplies X and Z velocity by 0.4 after the block-contact callback; Y is unchanged. Soul Sand exists in both releases, so this changes an existing map block's player movement.

## Reachability and dependencies

Player `LivingEntity.travel` calls `Entity.move(SELF, deltaMovement)` in ordinary ground travel. `Player#getBlockSpeedFactor()` in B returns the superclass factor when neither ability flying nor fall-flying, and returns 1 otherwise (`Player.java` lines 2027-2029; SHA-256 `1ba2724c22163862b8f7fdfdea5a04a6e4db26a119d7e5360ba024724a34793`). The movement-delta conclusion therefore applies to a grounded/non-flying player whose current/support lookup resolves to Soul Sand. The same factor hook can affect other blocks; the complete registration inventory remains open in the run ledger.

## Consequence and uncertainty

The source proves the factor is applied to stored horizontal velocity after movement/callback processing. It does not prove a measured trajectory. Exact client/server consequences depend on their respective movement ticks and block position lookup.

## Handoff

Emulate the absent 1.14.4 factor only when the selected historical version requires it. Full non-default-factor consumer closure is assigned to S-ENTITY-MOVE and INV-WORLD-MOVEMENT.
