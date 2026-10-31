# Blind source-only audit: swimming feet-cell water gate

**Revision:** immutable after this file is hashed; SHA-256 is recorded in `SHA256SUMS.txt`.

## Verdict

The feet-cell water-tag predicate is absent from `Entity.updateSwimming` in 1.17 and 1.16.5 and is present in 1.17.1. The code-level transition and its conditional downstream `Player.travel` branch effect are proven. A concrete runtime-reachable state is **not closed** under the permitted inputs: the exact versioned fluid-tag payload that determines membership in `FluidTags.WATER` is absent from the Mojmap source roots. The geometric witness below is conditional on that membership; no runtime parity is claimed.

## Exact source inputs and identity

Mapping family: Mojmap. Compared releases: 1.17 and 1.17.1; 1.16.5 is a narrow historical check for the same method.

- Read-only source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17\mojmap`, `...\1.17.1\mojmap`, and `...\1.16.5\mojmap`.
- Prior source-only identity record: `.native-source/20261030/1165-1171-pool38/fluid_contact/source-identities.json`. It records the 1.16.5 source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` and 1.17.1 manifest SHA-256 `93270d229acfb751bf56daf1f7e1be26ce3dcff26b29e94aa157de621405a3463b`. The identity-record SHA-256 is `fdba6b11af712fb64957680696741b84930812ad8239f0c14d1a207fcd8673e1`. Its 1.16.5 and 1.17.1 `Entity.java` hashes match the direct hashes below.
- The permitted prior collision39 source report identifies the 1.17 source publication through `.decompile-provenance.json`, SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`; direct `Entity.java` SHA-256 is recorded below.
- Source tree itself has no provenance marker in 1.16.5 or 1.17.1; source identity above is taken from the permitted prior source-only record. No source generation or mutation was performed.

## Paired method evidence

- 1.17 Mojmap `net/minecraft/world/entity/Entity.java`, `updateSwimming()`, lines 1056–1062: if already swimming, writes `sprinting && isInWater && !passenger`; otherwise writes `sprinting && isUnderWater && !passenger`. No feet-cell fluid sample occurs in either arm.
- 1.17.1 Mojmap same path/member, lines 1056–1064: the already-swimming arm is unchanged; the false-to-true arm adds `level.getFluidState(blockPosition).is(FluidTags.WATER)` after the sprinting, underwater and non-passenger conditions.
- 1.16.5 Mojmap same path/member, lines 949–955: both arms match the 1.17 form; no feet-cell fluid sample.

Thus this gate is added between the concrete 1.17 and 1.17.1 endpoints, and is not present in 1.16.5. The branch is only an entry gate: a player whose shared swimming flag is already true uses the first arm and does not evaluate the new predicate.

## Sampling and tick ordering

1.17.1 Mojmap `Entity.java`:

- `baseTick()`, lines 395–417, calls `updateInWaterStateAndDoFluidPushing()`, then `updateFluidOnEyes()`, then `updateSwimming()` in that order.
- `isInWater()`, lines 1027–1029, returns `wasTouchingWater`. `updateInWaterStateAndDoWaterCurrentPushing()`, lines 1074–1087, sets that field from the water-tag fluid-height query.
- `updateFluidHeightAndDoFluidPushing(Tag,double)`, lines 2773–2811, scans the entity bounding box deflated by `0.001`; integer bounds are floor(min) to ceil(max), and a tagged fluid contributes when its surface `q + getHeight(...)` is at least the deflated box minimum Y. This is an overlap/height test over the bounding box, not a test of the single feet cell.
- `updateFluidOnEyes()`, lines 1090–1108, first copies the *previous* `fluidOnEyes` status into `wasEyeInWater`, then clears and recomputes `fluidOnEyes` from the fluid at `(getX(), getEyeY() - 0.11111111F, getZ())`; the fluid qualifies only when its tagged surface is above that sample height. `isEyeInFluid`, lines 1168–1170, compares `fluidOnEyes` to the tag. `isUnderWater()`, lines 1052–1054, is `wasEyeInWater && isInWater()`.
- `blockPosition()`, lines 2875–2878, returns the cached block position. `setPosRaw`, lines 2956–2964, updates it to `floor(x), floor(y), floor(z)`. Therefore the added query is the fluid tag in the block containing the entity position/feet, independent of the bounding-box and eye samples.
- `FluidTags.java`, lines 8–12, binds the WATER fluid tag. `FluidState.is(Tag)`, lines 101–102, checks the fluid type's tag membership. In `FlowingFluid.java`, lines 464–475, a source without same-type fluid above has `getHeight == getOwnHeight`; own height is amount/9. `WaterFluid.Source.getAmount`, lines 146–155, returns 8, so such a source surface is at `8/9` of the cell height. `WaterFluid.getTickDelay`, lines 103–111, returns 5; `LiquidBlock.onPlace`, lines 109–113, schedules its liquid tick at that delay when spreading is enabled.

The ordering matters: current body-water contact is refreshed before swimming is decided, while `wasEyeInWater` refers to the prior eye-fluid sample. The witness therefore requires the player to have sampled water at the eye on the preceding base tick.

## Conditional source-level geometric witness (tag reachability open)

Assume a survival LocalPlayer (not a spectator, not flying, not a passenger), standing pose, sprinting, shared swimming flag initially false, and loaded chunks. Put a solid floor block at `(0,5,0)`, leave `(0,6,0)` dry air, and place a water source at `(0,7,0)` under a solid ceiling at `(0,8,0)`. The player stands at `(x,y,z) = (0.5,6.0,0.5)`, so `blockPosition == (0,6,0)`. The setup can be made transiently by pouring a bucket at the underside of the ceiling: the bucket targets the adjacent air cell, and the placed source schedules its water update five ticks later. Assume the source was placed early enough for one base tick to sample its eye-water state and the subsequent comparison occurs before that scheduled flow update; no intervening block change occurs.

Conditional on the placed built-in water source satisfying `FluidState.is(FluidTags.WATER)`, source facts plus direct geometry:

- `Player.STANDING_DIMENSIONS` is `0.6 × 1.8` and `DEFAULT_EYE_HEIGHT` is `1.62` (`Player.java` lines 120–133). The deflated bounding box spans Y `[6.001, 7.799]`; its fluid scan includes water cell Y `7`. The source surface is `7 + 8/9`, which is above the box minimum, so the water-tag overlap query reports water. `isInWater()` is consequently true.
- Eye sample height is `6 + 1.62 - 0.11111111F`, approximately `7.5089`, inside water cell `(0,7,0)` and below its `7 + 8/9` surface. After the first eye recomputation and on the following base tick, `wasEyeInWater` is true; together with current `isInWater`, `isUnderWater()` is true.
- The cached feet position remains `(0,6,0)`, whose fluid state is empty. Thus the new predicate is false while sprinting, underwater and non-passenger are all true.

If the source fluid at `(0,7,0)` is a member of `FluidTags.WATER`, then at the next `updateSwimming()` with the flag initially false, 1.17 sets swimming true and 1.17.1 leaves it false. A newly placed source's scheduled five-tick delay provides a source-coded window for the two eye samples before its first scheduled fluid update, assuming the described placement/tick ordering. However, `FluidTags.java` only binds the named tag; the exact `data/minecraft/tags/fluids/water.json` membership is not present in the allowed decompiled Java roots. The prior permitted `fluid_contact/report.md` explicitly records exact tag/resource contents and runtime tag reachability as open (lines 33, 40, 46, 51). Therefore this audit cannot affirm that the coordinate witness is a reachable runtime state; that is the remaining blocker.

## Downstream local-player movement path

1.17.1 Mojmap call path:

- `LocalPlayer.tick()`, lines 190–203, enters `super.tick()` when its chunk is loaded.
- `LivingEntity.tick()`, lines 2218–2222 and 2257, calls `super.tick()` and then virtual `aiStep()`; inherited `Entity.baseTick()` performs the water and swimming updates first.
- `LocalPlayer.aiStep()`, lines 648–663 and 806–815, executes local input handling and calls `super.aiStep()`. `Player.aiStep()`, lines 489–507, calls `LivingEntity.aiStep()`. The LocalPlayer overrides `isEffectiveAi()` to true (lines 493–501), so `LivingEntity.aiStep()` invokes `serverAiStep()`; LocalPlayer copies `input.jumping` to `this.jumping` in `serverAiStep()`, lines 612–619.
- `LivingEntity.aiStep()`, lines 2513–2535, processes a local jump before travel; `jumpInLiquid`, lines 1991–1993, adds `0.04F` Y velocity. In the witness, fluid height is about `7 + 8/9 - 6.001`, greater than the `0.4` jump threshold (`Entity.getFluidJumpThreshold()`, lines 2849–2855); with jump input and `onGround`, the water-jump arm adds `0.04F`. `LivingEntity.aiStep()`, lines 2540–2547, then invokes virtual `travel(...)`. `Player.travel()`, lines 1408–1438, has a swimming-specific branch at lines 1413–1422 before ordinary/flying travel. With a horizontal look (`lookY == 0`) and no flight, 1.17 enters this branch and scales that positive Y increment by `1 - 0.06`, while 1.17.1 does not enter it. Thus the pre-`super.travel` Y velocity is different (the 1.17 branch applies `v + (0-v)*0.06` to the `0.04F` increment); later `LivingEntity.travel` receives different velocity while body water is true in both versions.
- `Player.isSwimming()`, lines 1801–1804, gates the shared swimming bit by not flying/not spectator. `Player.updatePlayerPose()`, lines 364–391, also uses `isSwimming()` when selecting swimming pose; it is called from `Player.tick()` at lines 288–290 after `super.tick()`.

## Facts versus inference and limits

**Sourced facts:** exact method contents and call order cited above; cached position flooring; eye sample point and one-tick previous-eye-state use; AABB fluid-height scan; source-fluid height; water-fluid scheduled update delay; LocalPlayer input-to-jump path, fluid jump, and Player/LocalPlayer travel dispatch.

**Geometric/tick inference:** the coordinate witness satisfies those source predicates as written. The transient-reachability argument assumes bucket placement under the ceiling happens before the relevant base tick, the scheduled liquid tick is not already due, and the player remains supported on the floor while the water source remains for the following eye sample. The bucket placement, fluid delay, geometry and movement branches are source-supported, but runtime tag membership is not. This audit did not execute a game or prove a full input/trajectory recording. The exact movement result after the differing pre-travel velocity write is not simulated.

No tests, build, game launch, source generation, product code, product history, research/backlog, wiki inventories, or runtime parity checks were used. Permitted prior source-only context read: pool38 fluid source identity and collision39 source reconciliation; no prior finding or implementation was used as a checklist.

## Mechanical source hash checks

SHA-256 values below were computed directly from the exact Mojmap source files with `Get-FileHash -Algorithm SHA256`; paths are relative to each version's `mojmap` root. They are repeated in `SHA256SUMS.txt` for independent mechanical verification.

| Release | Relative source | SHA-256 |
|---|---|---|
| 1.16.5 | `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.17 | `net/minecraft/world/entity/Entity.java` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` |
| 1.17.1 | `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| 1.17.1 | `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.17.1 | `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| 1.17.1 | `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` |
| 1.17.1 | `net/minecraft/tags/FluidTags.java` | `b328dac2531d48d462c14a58aa8e86e47510cbf20fd8002bc37a2cbdf1072bd7` |
| 1.17.1 | `net/minecraft/world/level/material/FluidState.java` | `0ee09bede0d44264009a810a6493897c0ce8ee9893fa275d3ec87d782a5ad754` |
| 1.17.1 | `net/minecraft/world/level/material/FlowingFluid.java` | `93fac0aa0b44dcd42a9e05783eede16e6e1e72cbc7cbf00d34f6e5a55b67f4c6` |
| 1.17.1 | `net/minecraft/world/level/material/WaterFluid.java` | `2e45a3d180d8f8978bcc2abefdc0c1475f3500cc6bbbaa88a1d6632e801ed672` |
| 1.17.1 | `net/minecraft/world/level/block/LiquidBlock.java` | `1a3757df70df8d1fb75544dfa486911c77a017726f8d89748ec185d9871c6483` |
| 1.17.1 | `net/minecraft/world/item/BucketItem.java` | `560d4d6be74bc7a1af36215b9f03b5553302319a8a22a41b02755f175fac01de` |

All source hashes listed above were directly computed and are cross-checked by `SHA256SUMS.txt`. The 1.16.5 and 1.17.1 Entity hashes also match the permitted source identity record. The 1.17 source file is cross-identified by the permitted provenance marker. No claim is made that this report validates every file in the full source tree or runtime.
