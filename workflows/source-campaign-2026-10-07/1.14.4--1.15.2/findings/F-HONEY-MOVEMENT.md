# F-HONEY-MOVEMENT: Honey Block adds modern-only player movement rules

- Older version A: Minecraft 1.14.4
- Newer version B: Minecraft 1.15.2
- Mechanic / coverage slice IDs: INV-WORLD-MOVEMENT, INV-COLLISION, INV-STATE; S-HONEY-BLOCK, S-LIVING-JUMP, S-LOCAL-PRETRAVEL
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: within (1.14.4, 1.15.2]
- Runtime validation: not performed

## Paired evidence

- A `net/minecraft/world/level/block/Blocks.java`, lines 1-2035, is the complete registry source and has no `HONEY_BLOCK` registration; A's block source tree also has no HoneyBlock class. SHA-256 `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`.
- B `net/minecraft/world/level/block/Blocks.java::Blocks.HONEY_BLOCK`, lines 2118-2123 registers `HoneyBlock` with `.speedFactor(0.4F).jumpFactor(0.5F)`; SHA-256 `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`.
- B `net/minecraft/world/level/block/HoneyBlock.java` collision shape lines 21-35, `entityInside` lines 49-57, slide predicate lines 60-77, and velocity/fall-distance writes lines 85-95; SHA-256 `40760aeb3c084f1143e87e1e057f18165492eb01b8fce0815dfdd0882cdc8a03`.
- B `LivingEntity.java::LivingEntity#getJumpPower()` lines 1799-1801 multiplies 0.42F by `getBlockJumpFactor`; SHA-256 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`. B `LocalPlayer.java::LocalPlayer#canAutoJump()` lines 962-970 requires block jump factor >= 1.0; SHA-256 `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`.

## Source-level difference

Honey Block was added in B and cannot occur in A-era vanilla maps. B assigns horizontal speed factor 0.4 and jump factor 0.5. Honey contact can slide a descending, airborne entity when its feet are below blockY + 0.9375 - 1e-7, its vertical velocity is below -0.08, and it is near an edge. For velocity y below -0.13, the block scales X/Z by `-0.05 / y`, sets Y to -0.05, and resets fallDistance; otherwise it sets Y to -0.05 and still resets fallDistance. The block-factor lookup also changes jump power and prevents local auto-jump when factor is below 1.0.

## Reachability and dependencies

Entity movement invokes `checkInsideBlocks()` after collision movement; HoneyBlock's `entityInside` callback is reached on contact. Its collision shape occupies the central 14/16 width and 15/16 height of its block cell. Jump factor reaches LivingEntity jump handling, and the local auto-jump gate reads the same factor. The registry/class absence in A bounds this to a modern-only block and does not justify adding it to historical behavior.

## Consequence and uncertainty

The source establishes the callback conditions and writes, and the factor consumers. It does not establish any particular trajectory or achievement/effect outcome. Honey slide damage on landing is not a movement delta and remains outside this finding.

## Handoff

Keep Honey Block mechanics attached to the release where the block exists. Do not emulate these rules for 1.14.4 maps.
