# Blind source review: 1.20.6 → 1.21 → 1.21.1

## Evidence identity

Read-only exact Mojmap trees under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` were used. The 1.20.6 tree identifies `versionId=1.20.6`, `mapping=mojmap`; its ready marker binds client jar SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`, and the artifact manifest binds `client_mappings.txt` SHA-256 `27f4ef3a9362e9874e2c33edac981b6f485ad97f92c4e35b497525b646aae745`. For 1.21, the embedded `.decompile-provenance.json` inputs bind client jar `4b56bc9b44de7e88f9fbb52e716c18240da34469fcbafa5ed2e75b04d60617e8` and Mojang client mappings `127485d79248d4ae2955a7d061c3626d38853ecb054c4d008707732264b0aac1`. The 1.21.1 ready marker identifies `versionId=1.21.1`, `mapping=mojmap`; its artifact manifest binds client jar `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99` and client mappings `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec`.

Source SHA-256 values below were checked against the corresponding tree (and the 1.20.6 / 1.21.1 source manifests; 1.21 embedded per-source provenance). Relevant files for 1.21 and 1.21.1 are byte-identical, including `Entity.java` and `LivingEntity.java`.

| Source file | 1.20.6 SHA-256 | 1.21 and 1.21.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381` | `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6` | `9e2f9acd5bc6a8f3b5292d720db81464954d41c9afb635f1a7c6cb31d5a2bf84` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/EntityType.java` | `5de729295e0d4821db6e88272889291254b14e843baf82a3c2c7ec1588bb7c01` | `23aa8ff6462783b83a09f058381a0ebeb8afb066898806535e850e1f81ca1d0e` |
| `net/minecraft/world/item/enchantment/LevelBasedValue.java` | — | `f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3` |
| `net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java` | — | `c1e2edd5c870e1ecba364cbbe07ae2f12720e47681dd1dbf16ac081e392f4270` |
| `net/minecraft/world/level/block/SnowLayerBlock.java` | `a88203cf88219422fef10adfeab87cfe24df272c015de9de635f2dc7949f049b` | same |
| `net/minecraft/world/level/block/SlabBlock.java` | `2085c939554cbaaaf8eea24383a870081e45a70eea9108314a5e81ade47601c1` | same |
| `net/minecraft/world/phys/shapes/Shapes.java` | `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a` | same |

## Water movement acceleration passed to `moveRelative`

**Status: accepted source-confirmed movement difference.** The operation is inherited by `LocalPlayer` through `Player` / `LivingEntity`; it is in `LivingEntity.travel`, not a `LocalPlayer` override. The water branch requires `isControlledByLocalInstance()`, `isInWater()`, `isAffectedByFluids()`, and `!canStandOnFluid(fluidState)` (`LivingEntity.java`, 1.20.6 lines 2104–2117; 1.21/1.21.1 lines 2091–2104). It then starts `$$6=0.02F`; the call receives this float at 1.20.6 line 2135 and 1.21/1.21.1 line 2118. Sprinting and Dolphin’s Grace alter the later slowdown `$$5`, not this `$$6` argument.

In 1.20.6, `EnchantmentHelper.getDepthStrider` returns the integer enchantment level (helper lines 184–186); travel caps it at 3, halves it while airborne, then computes `$$6 += (getSpeed() - 0.02F) * level / 3.0F` (travel lines 2117–2129). Depth Strider’s registered definition is levels I–III (`Enchantments.java` lines 74–80). In 1.21, travel casts the water movement efficiency attribute to `float`, halves it while airborne, and computes `$$6 += (getSpeed() - 0.02F) * efficiency` (`LivingEntity.java` lines 2104–2111). The attribute has default `0.0`, range `[0,1]` (`Attributes.java` lines 101–102), is in living defaults (`LivingEntity.java` lines 304–323), and Depth Strider adds `LevelBasedValue.perLevel(0.33333334F)` (`Enchantments.java` lines 345–367; `LevelBasedValue.java` lines 126–137; `EnchantmentAttributeEffect.java` lines 35–37). Thus its vanilla level values are 0, `0.33333334F`, `0.6666667F`, and `1.0F`, clamped by the attribute range.

For a vanilla player at default `getSpeed()==0.1F` (`Player.createAttributes`, 1.21 `Player.java` lines 222–235), standing in water with Depth Strider II and no other movement-speed modifier, the two expressions differ by a binary32 ULP:

| Step | 1.20.6 old order | 1.21+ new order |
|---|---:|---:|
| level after ground gate | `2.0F` (`0x40000000`) | efficiency `0.6666667F` (`0x3F2AAAAB`) |
| `(0.1F - 0.02F)` | `0.08F` (`0x3DA3D70A`) | `0.08F` (`0x3DA3D70A`) |
| multiply | `0.16F` (`0x3E23D70A`) | `0.053333335F` (`0x3D5A740E`) |
| divide by 3 | `0.05333333F` (`0x3D5A740D`) | — |
| add to `0.02F` | `0.07333333F` (`0x3D962FC9`) | `0.07333334F` (`0x3D962FCA`) |

These are source-ordered IEEE-754 binary32 operations; no Minecraft runtime was executed. The differing final float is directly supplied to `moveRelative`, so this is an actual movement-input difference, not a claim about a later position/trajectory. For comparison, level I and III round to the same final acceleration at this speed; the level-II grounded witness suffices. The value comes from ordinary player defaults plus a vanilla enchantment, so no command-injected attribute is needed.

## `Entity.collide` step-up search

**Status: accepted source-confirmed movement difference.** The player path reaches `Entity.collide` from `Entity.move` (1.20.6 and 1.21 `Entity.java` lines 617–618). Normal collision computes `$$3`, then detects clipped components. Both versions gate step-up on positive `maxUpStep`, an on-ground/downward-collision condition, and horizontal clipping. A player’s default step-height attribute is `0.6` (`Attributes.java` lines 92–93); LivingEntity returns that value, or at least `1.0F` when it controls a Player passenger (`LivingEntity.java` lines 3500–3503).

The algorithms differ in search order and settling. In 1.20.6 (`Entity.java` lines 874–897), after normal collision, `$$8` tests the full requested X/Z movement while rising by `maxUpStep`; `$$9` tests a vertical-only rise against a horizontally expanded box; if capped, `$$10` tests horizontal movement from that capped rise and replaces `$$8` only for strictly greater horizontal distance. A winning candidate is then vertically settled against the original requested Y (`line 893`). In 1.21/1.21.1 (`Entity.java` lines 875–924), the method builds a broad candidate volume, collects entity, world-border and block collision shapes (lines 927–945), gathers every nonnegative shape Y coordinate relative to the base box, excludes the normal clipped Y and heights above `maxUpStep`, then unstable-sorts those heights ascending. It tries each height with Y-first and then X/Z shape collision and immediately returns the first whose horizontal distance beats normal collision (lines 894–900); there is no old full-step-vs-expanded-vertical comparison or final downward settle. If the initial requested Y was clipped downward, the new code first shifts the base box by the clipped Y and subtracts that offset from the returned vector (lines 882–898). `Shapes.collide` performs axis shape clipping (lines 204–213); the step candidate collision order is Y, then the lesser-magnitude horizontal axis, then the other (Entity.java lines 948–980).

**Vanilla reachable witness (equal player state and requested vector).** Use a standing Player centered at `(x,z)=(0.5,0.5)`, feet at `y=1`, on a full-block floor (`EntityType.PLAYER` is sized `0.6F × 1.8F`, `EntityType.java` lines 803–812). Put a three-layer snow block at `(1,1,0)` and a bottom slab at `(2,1,0)`, both on that floor; request `Vec3(2.5,0,0)`. Keep world border and entities away. The 1.21 collision shape for snow uses `SHAPE_BY_LAYER[LAYERS-1]`; layer 3 is a `Block.box(..., 6.0, ...)`, hence a `0.25` collision height (`SnowLayerBlock.java` lines 29–38, 67–74). The bottom slab provider returns `BOTTOM_AABB`, whose top is `8/16=0.5` (`SlabBlock.java` lines 32–33, 56–65). These are real vanilla block states and exact shape providers; collision shapes are translated by block position.

With the player’s X bounds `[0.2,0.8]`, normal movement clips at the snow face to X=`0.3`, so horizontal clipping opens the step branch. The old full-step trial rises `0.6` because neither obstacle overlaps the player’s initial X/Z footprint; at that height it clears both shapes, travels the full requested X=`2.5`, then settles back to net Y=`0` on the floor. Its expanded vertical-only alternative can rise only `0.25` due to the snow and cannot pass the slab, so its horizontal progress is smaller and does not replace the full-step candidate. The new collected surface heights include `0.25` (snow), then `0.5` (slab). At the first candidate `0.25`, the player clears the snow but reaches the slab and clips at X=`1.2`; this beats normal X=`0.3`, so the new loop returns immediately with vector `(1.2, 0.25, 0)`, without testing `0.5`. The returned movement vector therefore differs directly: old `(2.5,0,0)`, new `(1.2,0.25,0)`.

The witness is away from entity collisions and the world border, uses default step height, normal on-ground gating, and ordinary block collision. It is a direct one-call collision result; no repeated-tick trajectory or network-state producer claim is needed. The 1.21.1 `Entity.java` and shape-provider hashes match 1.21, so this remains the later endpoint behavior.
