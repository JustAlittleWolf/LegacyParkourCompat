# 1.20.6 → 1.21.1 local-player tick and movement dependency comparison

## Bounded verdict

The exact source comparison confirms one reachable movement-math difference in the water-travel acceleration term: the Depth Strider level is divided by `3.0F` after multiplying the term in 1.20.6, while 1.21.1 multiplies by a per-level float-backed `WATER_MOVEMENT_EFFICIENCY` attribute. For a local player with Depth Strider II, this can change the `moveRelative` acceleration by one float ULP. The new built-in Depth Strider attribute effect is source-visible and maps level II to `0.6666667F`; no resource payload is needed for that built-in definition. This is a bounded source finding, not a trajectory or runtime-parity claim.

The ordinary local-player/input entry methods and most position/velocity methods checked are identical. The pair is not frozen: this report does not close collision/shape aggregation, block and fluid query providers, packaged resources/tags, unrelated direct packet responses, portal transfer state, or every external player-response path.

## Source identity

Canonical inputs are read-only `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21.1}/mojmap/`.

| Release | Parent `mojmap.provenance.json` SHA-256 | `mojmap.ready.json` SHA-256 | Source-manifest SHA-256 | Ready source count | Client JAR SHA-256 |
|---|---|---|---|---:|---|
| 1.20.6 | `ee53e67e7ab51069a0eaa5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | 5,329 | `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` |
| 1.21.1 | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` | 5,363 | `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99` |

The ready markers identify the exact release and `mojmap` mapping, and their source-manifest, artifact-manifest, and diagnostics digests match the corresponding parent files. Neither mapping root has an embedded `.decompile-provenance.json`; both release parents do contain the cited provenance and ready records. The manifests contain 5,329 and 5,363 rows. Comparing their path/hash entries yields 4,103 unchanged shared paths, 1,145 changed shared paths, 115 additions, and 81 removals. This is a manifest-to-manifest inventory; I rehashed each selected Java file below against its manifest, not every Java file in both trees.

### Selected source file identity

The following exact bytes were independently SHA-256 hashed, and every listed hash matches the matching release's `mojmap.sources.sha256` entry.

| Source | 1.20.6 SHA-256 | 1.21.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| `net/minecraft/client/player/Input.java` | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | identical |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | identical |
| `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `7e30d87c7a58b14d0052d2f9f7319d997b49ae7d025579cd762d28e845b2e82e` | `a9a19f556bb77fc218b5b2f4831eb6b285f4398c24fa2a3519009ca8dad2db7c` |
| `net/minecraft/world/item/enchantment/EnchantmentHelper.java` | `e0b4c410e0aa9499d67b38f57b34467628e882be960dce6958d4bc8ef717a6b6` | `9e2f9acd5bc6a8f3b5292d720db81464954d41c9afb635f1a7c6cb31d5a2bf84` |
| `net/minecraft/world/item/enchantment/Enchantments.java` | `26eeafdf667fa57b711fab3112aa9636f20a9a38cadfbff42740e66a14c38381` | `7848a2cd677aa0700434f85de37d8a0c47597741d2d1f26c8dc270f73d65b266` |
| `net/minecraft/world/item/enchantment/LevelBasedValue.java` | absent | `f9a0bed1d7693606f8657fadd4a59d95eea9955fcc036687e7a9dd08a8b360e3` |
| `net/minecraft/world/item/enchantment/effects/EnchantmentAttributeEffect.java` | absent | `c1e2edd5c870e1ecba364cbee07ae2f12720e47681dd1dbf16ac081e392f4270` |

## Ordered local tick and movement slices

### Local-player input and tick

- `LocalPlayer.tick()` and `sendPosition()` have identical method bodies. `Input.java` and `KeyboardInput.java` are byte-identical. `LocalPlayer.isMovingSlowly()`, `move()`, and `updateAutoJump()` are also identical.
- `LocalPlayer.aiStep()` retains the same sprint, jump, flight, water-down, vehicle-jump, and `super.aiStep()` ordering. Its input factor changes from `Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus(this), 0.0F, 1.0F)` (1.20.6 line 666) to `(float)this.getAttributeValue(Attributes.SNEAKING_SPEED)` (1.21.1 line 663). See the Swift Sneak comparison below.
- The same method also changes portal-local UI/cooldown handling from `handleNetherPortalClient()` to `handleConfusionTransitionEffect(...)` followed by `processPortalCooldown()`. This is a portal transition/transfer dependency, not ordinary input math; this source slice does not close the movement applicability or direct post-transfer state response.
- `Player.tick()` adds a 40-tick impulse-context grace countdown, and `Player.aiStep()` adds saturation recovery. These respectively feed fall-damage context and food production, which are outside the assigned movement claim. No other changed lines in those method bodies were found.

### Confirmed water movement arithmetic difference

The local `LocalPlayer` is locally controlled: its `isEffectiveAi()` returns `true` in both versions. `LivingEntity.travel(Vec3)` therefore reaches the `isControlledByLocalInstance()` branch for this player.

Both releases use the same water gates and branch order: `isInWater()`, `isAffectedByFluids()`, and `!canStandOnFluid(fluidState)`, then obtain `getWaterSlowDown()` or sprinting `0.9F`, initialize the movement acceleration to `0.02F`, adjust it from the Depth Strider factor, and call `moveRelative(acceleration, input)` before movement. The `Player.travel()` swimming pre-adjustment and flight branch are unchanged. The affected formulas are:

- **1.20.6 `LivingEntity.travel()` lines 2117–2128:** read the integer `EnchantmentHelper.getDepthStrider(this)`, cap it at 3, halve it when not on ground, then add `(getSpeed() - 0.02F) * level / 3.0F` to the input acceleration (and apply the analogous ordered operation to water slowdown).
- **1.21.1 lines 2104–2115:** read `(float)getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY)`, halve it when not on ground, then multiply each difference directly by that attribute value.
- The built-in 1.21.1 `DEPTH_STRIDER` definition (`Enchantments.java:346–365`) applies `Attributes.WATER_MOVEMENT_EFFICIENCY` with `LevelBasedValue.perLevel(0.33333334F)`. `Attributes.java:101–103` defines the attribute with default 0 and range [0, 1], marked syncable. `LevelBasedValue.Linear.calculate()` is float arithmetic; level II yields `0.33333334F + 0.33333334F * 1.0F = 0.6666667F`. `LivingEntity.tick()` calls `detectEquipmentUpdates()` only on the logical server; `ItemStack.forEachModifier()` and `EnchantmentHelper.forEachModifier()` define the equipment-to-attribute mapping there. The matching value visible to the local client is a received attribute-state dependency; that packet application is outside this assignment.

A reachable rounding witness is a non-sprinting local player in water, on ground, with Depth Strider II, default movement speed `0.1F`, and nonzero horizontal input. For the acceleration term, both start from `0.02F`:

```text
1.20.6: 0.02F + ((0.1F - 0.02F) * 2.0F / 3.0F) = 0.07333333045244217F
1.21.1: 0.02F + ((0.1F - 0.02F) * 0.6666667F)   = 0.07333333790302277F
```

Each listed operation rounds as IEEE-754 `float`. The different acceleration enters `moveRelative` directly and changes the next horizontal motion update. This finding identifies the exact local movement calculation; it does not claim a complete trajectory or close the surrounding collision/shape inputs.

### Checked equivalent attribute conversions

- `LocalPlayer.aiStep()` now consumes `SNEAKING_SPEED`; 1.21.1 defines default 0.3 and the built-in `SWIFT_SNEAK` effect adds 0.15 per enchantment level. The old helper also adds 0.15 per level to 0.3 and clamps [0, 1]. For standard levels I–III, casting the new attribute value to float yields the same values as the old expression: 0.45F, 0.6F, and 0.75F. The new equipment-to-attribute calculation is server-side and the value is syncable; exact incoming attribute payload/timing remains a separate state-response dependency. No local movement difference is established for the equivalent values. Extra new-version attribute modifiers are not old Swift Sneak inputs.
- The block-speed path is refactored. In 1.20.6 `LivingEntity.getBlockSpeedFactor()` returns 1.0F when the support block has `SOUL_SPEED_BLOCKS` and Soul Speed is equipped. In 1.21.1 it interpolates the vanilla factor toward 1.0F by `MOVEMENT_EFFICIENCY`; built-in Soul Speed has a location-change effect that sets this attribute to 1.0F on the tagged blocks. The new `onChangedBlock(ServerLevel, ...)` applies that location effect on the server. Exact client applicability/order consequently depends on the received attribute state and Soul Speed tag/provider inputs; this report does not infer them or close this gate.

### Other examined local movement roots

- Method bodies for `Player.travel()`, `Player.jumpFromGround()`, `Player.getSpeed()`, `LivingEntity.tick()`, `LivingEntity.aiStep()`, `LivingEntity.getJumpPower()`, `LivingEntity.setSprinting()`, `Entity.moveRelative()`, `Entity.setPos(double, double, double)`, `Entity.setDeltaMovement(Vec3)`, and `LocalPlayer.sendPosition()` match across the endpoints.
- `Entity.move()` differs in the name of the ground-support call (`setOnGroundWithKnownMovement` → `setOnGroundWithMovement`); the called support-state method bodies are the same. This is not a demonstrated math delta. Collision aggregation and block callbacks remain out of scope.
- Player position/velocity packet responses are not re-audited here. `ClientPacketListener.java` differs, so its response methods and incoming state applicability remain a separate source input.

## Finding draft

The specific water-movement source difference is drafted at `development/research/findings/1.20.6--1.21.1/water-movement-efficiency-rounding.md` inside this owned output directory. The exact intermediate patch boundary between 1.20.6 and 1.21.1 is unknown.

## Remaining coverage and exclusions

This comparison does not close the complete reachable player tick or pair. Unexamined or separately owned dependencies include: collision/shape collection and provider methods; block friction, support and movement-affecting-position providers; effective packaged block/fluid/enchantment tag or other resource contents; water/lava sample providers; portal transfer and its exact client response path; packet producer/receiver operations beyond this assignment; and remaining equipment/effect/modifier consumers. It also does not establish every intermediate stable patch boundary, runtime behavior or trajectory parity.

No tests, builds, game/server launches, source generation, or source edits were performed.
