# F-S3-SOUL-SPEED: Soul Speed changes player movement on tagged support blocks

- Older version A: 1.15.2, Mojmap
- Newer version B: 1.16.5, Mojmap
- Mechanic / coverage slice IDs: LivingEntity ordinary ground travel and Entity block-speed factor; S3-GROUND-AIR, INV-MODIFIERS, INV-WORLD-MOVEMENT
- Classification: changed behavior; B-only equipment/enchantment applicability
- Confidence: source-confirmed
- Applicability: 1.16.5 players wearing Soul Speed-enchanted footwear and standing on a Soul Speed-tagged support block; no 1.15.2 counterpart
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.15.2/mojmap`; `net/minecraft/world/entity/Entity.java`, base `Entity#getBlockSpeedFactor` and movement application; SHA-256 `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`. `net/minecraft/world/entity/player/Player.java`, `Player#getBlockSpeedFactor`, lines 2027-2030; SHA-256 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`. `net/minecraft/world/level/block/Blocks.java`, Soul Sand registration; SHA-256 `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`. A's `Enchantments.java` contains no Soul Speed registration.
- B manifest artifact reference: `../../../build/movement-campaign-2026-10-07/ready/1.16.5/mojmap`; `net/minecraft/world/entity/Entity.java`, `Entity#move` block factor application around lines 576-577 and base factor; SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`. `net/minecraft/world/entity/LivingEntity.java`, `onSoulSpeedBlock`, `getBlockSpeedFactor`, `tryAddSoulSpeed`, `checkFallDamage` and `onChangedBlock`; SHA-256 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`. `net/minecraft/world/entity/player/Player.java`, `onSoulSpeedBlock`, `getBlockSpeedFactor`, and `getSpeed`; SHA-256 `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`. `net/minecraft/world/item/enchantment/Enchantments.java`; SHA-256 `c9d388097fd4bbf03609258486dc02d7d24bf18ab813f20f4f0db8d86e3dbdb8`. `SoulSpeedEnchantment.java`, registered for the feet equipment slot; SHA-256 `79e4cb0c3521a08432b8c9d9e175b3bb9d73367315c0af5b77ffc2ef2690a0a4`. `EnchantmentHelper.java`; SHA-256 `cb4ff36f66976365247cf4fa0e052182e7896aa5c363ccc7236be91e84a99eef`. `Blocks.java`; SHA-256 `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`.
- Resource evidence: B client artifact `../../../build/movement-campaign-2026-10-07/artifacts/1.16.5/client.jar`, SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`; archive entry `data/minecraft/tags/blocks/soul_speed_blocks.json`, SHA-256 `c86fbd7bcfa2b94c881f0ba9980a694b28150a5b0b628aec0cfedc552f68994f`. The tag includes `minecraft:soul_sand` and `minecraft:soul_soil`. The A 1.15.2 client artifact has no corresponding tag entry. `soul_soil` is B-only and outside the shared historical block set.

## Source-level difference

B adds a Soul Speed feet enchantment. For a living entity on a tagged support block with Soul Speed level greater than zero, `LivingEntity#getBlockSpeedFactor` returns `1.0F` instead of the support block's normal speed factor. `Entity#move` multiplies the x and z components of delta movement by that factor. On shared Soul Sand, registered with the normal factor `0.4F` at both endpoints, this removes that horizontal reduction for the eligible B player. The Player override disables the special path while flying or fall-flying.

B also calls `tryAddSoulSpeed` from fall-damage and block-change paths. When the feet block is non-air, Soul Speed level is positive, and the movement block is tagged, that method adds a movement-speed attribute modifier with amount `0.03F * (1 + level * 0.35F)`; the player `getSpeed()` consumes the movement-speed attribute. This is a separate additive route from the block-factor override. B's Player flight override on `onSoulSpeedBlock` and its block-factor override prevent applying the ordinary walking benefit while flying.

## Reachability and historical boundary

The source establishes a B-only player movement mechanic. Soul Speed is registered only in B, is an enchantment for feet equipment, and A has no matching enchantment or movement modifier. The shared Soul Sand block is a valid support in both versions; B's Soul Soil tag member is modern-only and is not used as evidence of historical compatibility. This snapshot records the exact version delta but does not claim the behavior as a 1.15.2 mechanic or make an implementation-eligibility decision. Cross-version switching is undefined behavior.

## Consequence and uncertainty

For an eligible non-flying B player on shared Soul Sand, the block-speed multiplier path changes from the normal `0.4F` factor to `1.0F`, and the Soul Speed attribute route can further affect movement speed. The source proves these movement inputs and formulas; net displacement still depends on input, prior velocity, collision, and later tick processing. No runtime trajectory is claimed. The exact first changed release inside the pair interval is not determined by these two endpoints.

## Handoff

Independent source delta: 1.16.5 introduces Soul Speed's support-block factor bypass and movement-speed attribute route for enchanted feet gear. Applicability is confined to B-era equipment and tagged support; no 1.15.2 implementation behavior is inferred. Related slice: S3-GROUND-AIR.
