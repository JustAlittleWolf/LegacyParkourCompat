# Autojump jump-factor gate source audit

Disposition: no code finding for historical-map compatibility; exclude the predicate difference because its only vanilla sub-1.0 producer is the 1.15 honey block, which has no 1.14.4-era state.

## Immutable prior claim

Prior permitted finding:
- Path: `development/research/findings/1.14.4--1.15.2/autojump-block-jump-factor-gate.md`
- SHA-256: `5720855e884d66828c5d195964c81a5ae4e68451b91ee6dcaf2b09cd2d0d5c73`

The claim that 1.15.2 adds a `getBlockJumpFactor() >= 1.0` autojump gate is source-accurate. The former movement witness (“standing over a block with a sub-1.0 jump factor”) is not representable with any vanilla 1.14.4 block state and therefore does not establish an eligible historical-map difference.

## Exact source identities

Read-only source root: `D:\\Javastuff\\LegacyParkourCompat\\decompiled_minecraft`.

- 1.14.4 Mojmap: ready source manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; ready marker identifies version 1.14.4 and client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`.
- 1.15 Mojmap: exact source manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; provenance run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`, version metadata SHA-256 `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785`, client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`.
- 1.15.1 Mojmap: exact source manifest SHA-256 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; same exact run as 1.15; client SHA-256 `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358`.
- 1.15.2 Mojmap: exact source manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; ready marker identifies version 1.15.2 and client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`.

The listed source manifests were read and their declared paths/hashes matched the canonical source files. Key source file hashes:
- 1.14.4 `LocalPlayer.java`: `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`
- 1.14.4 `Blocks.java`: `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`
- 1.15 `LocalPlayer.java`: `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823`
- 1.15 `Entity.java`: `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`
- 1.15 `Block.java`: `069ffc485f94c84eb63e528e608f91104229477b6ac266aa3d4103f836cac2fe`
- 1.15 `Blocks.java`: `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`
- 1.15.2 `LocalPlayer.java`: `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`

## Compared evidence

1.14.4, Mojmap:
- `net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump`, lines 856–959. The outer gate checks enabled auto-jump; the inner gate checks `autoJumpTime <= 0`, `onGround`, `!isSneaking()`, and `!isPassenger()`; then it checks the move vector before scanning.
- `net/minecraft/world/entity/Entity.java`, line 1812: `isSneaking()` returns shared flag 1.
- `net/minecraft/world/level/block/Block.java` and `Blocks.java`: no `jumpFactor` field, producer, or `jumpFactor(...)` assignment. `Entity.java` has no `getBlockJumpFactor`.
- `Blocks.java` has no honey block registration.

1.15 Mojmap (the exact first stable 1.15 release):
- `net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump` and `canAutoJump`, lines 862–976. The moved gate checks enabled auto-jump, timer, ground contact, `!isStayingOnGroundSurface()`, not a passenger, movement input, and finally `getBlockJumpFactor() >= 1.0).
- `net/minecraft/world/entity/player/Player.java`, lines 294–296: `isStayingOnGroundSurface()` returns `isShiftKeyDown()`. `Entity.java`, lines 1783–1784, maps that to shared flag 1, so it preserves the 1.14.4 player sneak flag gate.
- `net/minecraft/world/entity/Entity.java`, lines 584–588: the factor reads the block at `new BlockPos(this)` and the block at `getBlockPosBelowThatAffectsMyMovement()`; it returns the below factor only when the first factor equals 1.0. This equality and selection order are exact.
- `net/minecraft/world/level/block/Block.java`, lines 100, 722, 854–856, and 916–919: each block stores a jump factor, the getter returns it, and the builder default is `1.0F).
- The only `.jumpFactor(...)` assignment in the complete 1.15 Mojmap source tree is `net/minecraft/world/level/block/Blocks.java`, line 2121: `HONEY_BLOCK` receives `0.5F). Honey registration is at lines 2118–2123. This block is absent from the 1.14.4 registry source.
- `net/minecraft/world/entity/LivingEntity.java`, lines 1799–1801, scales jump power by this factor in 1.15. For all 1.14.4-era vanilla blocks that remain present in 1.15, the factor remains the builder default 1.0; this consumer therefore introduces no additional old-block witness.

1.15.1 and 1.15.2:
- Exact source inspection confirms the same `canAutoJump` factor gate and `Entity.getBlockJumpFactor` implementation. In 1.15.1 the `LocalPlayer.java`, `Entity.java`, and `Blocks.java` hashes equal 1.15. In 1.15.2, `Entity.java` and `Blocks.java` hashes equal 1.15; its `LocalPlayer.java` body retains the gate at lines 962–970.
- Thus the gate is present in the first stable 1.15, not merely inferred from the 1.14.4→1.15.2 endpoints.

## Movement conclusion

For a fixed 1.14.4 profile over any vanilla block state from that era, `getBlockJumpFactor()` as introduced in 1.15 can only resolve to 1.0: the current and support blocks each default to 1.0, and none has a factor override. The new predicate is therefore true whenever the preceding autojump conditions pass. Its only vanilla rejection case is a player on/in honey, whose 0.5 factor is a 1.15-only feature. Historical-map compatibility excludes modern-only blocks/features, so there is no eligible equal-era state witness and no compatibility code should be added for this gate.

Acceptance boundary for this item: exclude/no-code based on the representability evidence above. This is a bounded finding disposition; it does not claim the entire 1.14.4–1.15.2 player-movement pair is frozen.
