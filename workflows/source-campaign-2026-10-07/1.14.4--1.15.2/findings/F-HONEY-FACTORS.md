# F-HONEY-FACTORS: Honey Block adds horizontal speed and jump factors

- Older version A: exact Java Edition 1.14.4
- Newer version B: exact Java Edition 1.15.2
- Mechanic / coverage slice IDs: INV-WORLD-MOVEMENT, INV-COLLISION, INV-TICK; S-ENTITY-MOVE, S-LIVING-JUMP, S-LOCAL-PRETRAVEL, S-HONEY-BLOCK
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: unknown within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `net/minecraft/world/level/block/Blocks.java`, complete registry lines 1-2035, has no Honey Block registration; `net/minecraft/world/level/block/Block.java::Block.Properties` has no speed/jump factor fields or builders (friction field/default at lines 98-99, 851-852), SHA-256 Blocks `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`, Block `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f`.
- A `net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3)`, lines 533-550 has no post-contact horizontal factor application; SHA-256 `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`.
- B `net/minecraft/world/level/block/Blocks.java::Blocks.HONEY_BLOCK`, lines 2118-2123, sets `speedFactor(0.4F)` and `jumpFactor(0.5F)`; SHA-256 `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`.
- B `net/minecraft/world/entity/Entity.java::Entity#move(MoverType,Vec3)`, lines 533-544, multiplies X/Z delta movement by `getBlockSpeedFactor()` after `checkInsideBlocks`; helper lines 590-602 resolves current/support factors; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`.
- B `Player.java::getBlockSpeedFactor()`, lines 2027-2029, uses the factor unless ability-flying or fall-flying; SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`.
- B `LivingEntity.java::getJumpPower()`, lines 1799-1801, multiplies 0.42F by `getBlockJumpFactor`; SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`. `Entity.java::getBlockJumpFactor()` lines 584-588 uses current/support factors. B `LocalPlayer.java::canAutoJump()` lines 962-970 requires factor >= 1.0; SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.
- Exact A/B artifact manifests: A `ready/1.14.4/mojmap.artifacts.sha256` SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`; B `ready/1.15.2/artifacts.sha256` SHA-256 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`.

## Source-level difference

The B Honey Block has horizontal speed factor 0.4 and jump factor 0.5. The speed helper applies horizontal scaling after collision/contact in `Entity.move` unless a player is ability-flying or fall-flying. The jump helper scales base jump power before Jump Boost is added. The same block factor also suppresses local auto-jump when below 1.0. A has neither factor property nor the associated consumers. The Soul Sand factor change is separately recorded in F-SOUL-SAND-SPEED.

## Reachability and dependencies

For a player grounded on Honey Block, the support lookup can resolve the block and apply the horizontal factor; when jumping, `LivingEntity.jumpFromGround` consumes the jump factor. The local-player auto-jump gate reads the same jump factor. These are B-only block interactions and do not imply a 1.14.4 mechanic.

## Consequence and uncertainty

The source proves factors and consumers; it does not establish measured speed, jump height or trajectory. The exact first release is unknown within the endpoint interval. Other non-default factor registrations remain part of the open whole-pair inventory, although B's current source search found Honey as the sole non-default jump factor and Honey plus Soul Sand as speed-factor registrations.

## Handoff

Keep these factors attached to the release that contains Honey Block. No pre-1.15.2 boundary is inferred from the endpoints.
