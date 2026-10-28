# 1.16 patch-alias closure: suffocation, Soul Speed, fluids

## Scope and method

Source-only review of exact Mojmap source trees for 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, and 1.16.5 in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Compared each adjacent patch in order, and checked the enclosing call paths plus relevant block predicate/support helper. No wiki, product implementation, history, tests, or runtime evidence was used. This closes only the assigned patch-alias intervals; it is not a release-pair coverage freeze.

## Per-version result

| Exact release | Ordinary LocalPlayer suffocation response / suffocating registrations | Soul Speed support lookup | Water/lava presence/current sampling |
|---|---|---|---|
| 1.16 | Older behavior | `getBlockStateOn()` | Touched-lava flag and water-first sampling with early return |
| 1.16.1 | Same as 1.16 | Same as 1.16 | Same as 1.16 |
| 1.16.2 | New behavior | New support-position lookup | New fluid-height-based lava presence; samples lava even when water is present |
| 1.16.3 | Same as 1.16.2 | Same as 1.16.2 | Same as 1.16.2 |
| 1.16.4 | Same as 1.16.2 | Same as 1.16.2 | Same as 1.16.2 |
| 1.16.5 | Same as 1.16.2 | Same as 1.16.2 | Same as 1.16.2 |

Adjacent-patch comparison: 1.16 → 1.16.1 has no byte changes in the five operation/dependency files hashed below. 1.16.1 → 1.16.2 contains all three assigned cutovers. For 1.16.2 → 1.16.3, 1.16.3 → 1.16.4, and 1.16.4 → 1.16.5, the assigned files are byte-identical; the 1.16.3 and 1.16.4 trees were inspected directly, not inferred from endpoint hashes.

## Operations and exact cutovers

### Ordinary player suffocation response and registrations

**Cutover: 1.16.1 → 1.16.2; patched alias applies to 1.16.2–1.16.5 in this inspected interval.**

In 1.16/1.16.1, `LocalPlayer.aiStep` calls `checkInBlock(x, y + 0.5, z)` at four width-offset points (`LocalPlayer.java:682–685` in 1.16.1). `checkInBlock` floors the supplied 3D point, calls `blocked` on its block and candidate neighbors, and sets horizontal velocity to `0.1` toward the selected side. `blocked` walks integer Y from `floor(boundingBox.minY)` to `< ceil(maxY)`, calling `freeAt` for each position (`LocalPlayer.java:414–478`).

Starting 1.16.2, the same four X/Z points call `moveTowardsClosestSpace` (`LocalPlayer.java:657–660`). That method derives the block position from `(x, this.getY(), z)`, tests the current and candidate neighboring columns via `suffocatesAt`, chooses the nearest clear horizontal direction in WEST/EAST/NORTH/SOUTH order, then writes `0.1` horizontal velocity. `suffocatesAt` tests a column-wide AABB spanning the player's bounding-box Y range, deflated by `1.0E-7`, through `noBlockCollision` filtered by `BlockState.isSuffocating` (`LocalPlayer.java:414–447`). The normal `!noPhysics` gate and per-corner invocation order remain.

`Blocks.java` 1.16.2 adds `.isSuffocating(Blocks::always)` to **FARMLAND** (around line 494), **SOUL_SAND** (around line 730), and **GRASS_PATH** (around line 1965); these registrations do not have those explicit predicates in 1.16.1. The `BlockBehaviour.Properties` default remains `material.blocksMotion() && fullCollisionShape`; the player query consumes the predicate directly. The registrations and response therefore share the same 1.16.2 applicability boundary.

### Soul Speed support lookup

**Cutover: 1.16.1 → 1.16.2; patched alias applies to 1.16.2–1.16.5 in this inspected interval.**

`LivingEntity.onSoulSpeedBlock` changes from `this.getBlockStateOn().is(BlockTags.SOUL_SPEED_BLOCKS)` (1.16.1 `LivingEntity.java:424–426`) to `this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(...)` (1.16.2 `LivingEntity.java:425–426`). The helper already exists in 1.16.1 and is unchanged in 1.16.2: it returns `new BlockPos(position.x, boundingBox.minY - 0.5000001, position.z)` (`Entity.java:612–614` then `627–629`). By comparison, `getBlockStateOn()` calls `getOnPos()`, whose Y is `floor(position.y - 0.2F)` (`Entity.java:579–584`, then `594–599`); the selected support Y can therefore differ for non-integer/partial-height positions. Other Soul Speed gates and effects were not part of this bounded alias check.

### Water/lava presence and current sampling

**Cutover: 1.16.1 → 1.16.2; patched alias applies to 1.16.2–1.16.5 in this inspected interval.**

In 1.16.1, `updateInWaterStateAndDoFluidPushing` clears `fluidHeight`, samples water through `updateInWaterStateAndDoWaterCurrentPushing`, then returns immediately when `isInWater()` is true; only otherwise does it sample lava (`Entity.java:947–956`). Lava presence is a separate `isTouchingLava` boolean, reset before `checkInsideBlocks`, set by `setInLava`, and returned by `isInLava` (`Entity.java:551–553, 1065–1070`).

In 1.16.2, the early return is removed: it always samples lava after water and returns `isInWater() || lavaSample` (`Entity.java:957–963`). The touching-lava field/setter is removed, and `isInLava` becomes `!firstTick && fluidHeight.getDouble(FluidTags.LAVA) > 0.0` (`Entity.java:1071–1073`). This changes the lava-presence source and allows same-update water and lava height/current samples. The `updateFluidHeightAndDoFluidPushing` sampling/current loop itself is textually unchanged across the cutover: it scans the deflated box, accepts matching fluid whose surface reaches the box minimum, records maximum height, scales low-height flow below `0.4`, averages flow, leaves Player flow unnormalized, applies the passed scale and the low-horizontal-speed minimum. `updateFluidOnEyes` also changes its non-underwater-boat exclusion from point containment to bounding-box Y overlap in 1.16.2 (`Entity.java:974–985` vs. `981–992`); it has the same later-patch alias.

## Source hashes and tree evidence

All five relevant file hashes below were checked against their release manifest row or embedded provenance source row, and matched for every listed release. Files are relative to `mojmap/`.

| Hash group | Releases | `LocalPlayer.java` | `LivingEntity.java` | `Entity.java` | `Blocks.java` | `BlockBehaviour.java` |
|---|---|---|---|---|---|---|
| Before cutover | 1.16, 1.16.1 | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9` | `1b524b5c45e3be4397dd680b9bd12c937497b9088e623428cf6387406018ef7f` |
| After cutover | 1.16.2–1.16.5 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` | `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` |

`1.16`, `1.16.1`, `1.16.2`, and `1.16.5` have Mojmap ready markers, provenance files, source manifests, and artifact manifests. Each ready marker's source-manifest SHA-256 matched the actual manifest; the five source rows above matched. Their pinned client/mapping SHA-256 values are preserved in the artifact manifests: 1.16 `client=00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71`, `client_mappings=53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d`; 1.16.1 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`, `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b`; 1.16.2 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`, `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`; 1.16.5 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`, `7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c`. The referenced campaign artifact cache is absent, so those artifact bytes could not be independently rehashed here. No `.previous-*` snapshot directories were present in the shared source root.

The 1.16.3 and 1.16.4 release parents contain only `mojmap/`; hidden inspection found `.decompile-provenance.json` inside each mapping directory. Each embedded `sources` map matches every relevant source file hash above. No ready marker, separate source manifest, or artifact manifest is present, so these records do **not** establish publication identity. The exact cached input pins are present and verified: 1.16.3 client `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`, Mojmap client mappings `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`, version metadata `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; 1.16.4 client `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, mappings `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, metadata `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`. The actual cached client, mapping, and version-metadata files matched their `.pin.json` SHA-256 values and the mapping SHA-1 declared by the exact version metadata. Embedded provenance file SHA-256s: 1.16.3 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; 1.16.4 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`.

## Bounded conclusion

For each assigned operation, the exact inspected source boundary is 1.16.2: old alias through 1.16.1, new alias beginning 1.16.2 and unchanged through 1.16.5. This statement is limited to the three assigned operation slices and does not freeze the broader 1.16 pair.
