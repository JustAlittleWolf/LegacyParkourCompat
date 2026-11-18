# 26.1 → 26.1.1: Player swim-pitch vertical correction

## Verdict

No source difference was found for the direct swimming-pitch Y correction in an ordinary active local `Player.travel` call with no passenger. The pre-super correction, its guards and arithmetic, the pitch-to-view-vector path, the sine LUT, and the inherited `LivingEntity.travel` water route are byte-identical in the two published decompilations examined here. This is a checked no-difference slice, not a movement finding or proof of full-pair coverage.

## Exact source identity and integrity

Both sides are published unobfuscated decompilations in the canonical source tree:

- `decompiled_minecraft/26.1/unobfuscated/`
- `decompiled_minecraft/26.1.1/unobfuscated/`

The ready markers each report Vineflower / Java 25, 6,882 source files, and source bytes matching the canonical tree. The whole-tree file counts and summed byte sizes were checked locally: 26.1 = 6,882 / 32,992,042 bytes; 26.1.1 = 6,882 / 32,992,043 bytes.

| Evidence file | SHA-256 |
|---|---|
| `26.1/unobfuscated.ready.json` | `954126940593c1e1e4b987bfd7079452e0d6283c3d1d97a02693f0319f66f0f5` |
| `26.1.1/unobfuscated.ready.json` | `29540fe56da79d19a9b125a091cf89cdb45e4493e4c5898c677658dca1474802` |
| `26.1/unobfuscated.sources.sha256` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` |
| `26.1.1/unobfuscated.sources.sha256` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` |
| `26.1/artifacts.sha256` | `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0` |
| `26.1.1/artifacts.sha256` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` |
| `26.1/unobfuscated.provenance.json` | `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b` |
| `26.1.1/unobfuscated.provenance.json` | `9df9595443d56779d505511f5441bb360cca71aafcc570bc16ea8e61a1d847bf` |

The source manifest entries and directly recomputed file hashes agree for every relevant class on both sides:

| Source file (under each version's `unobfuscated/`) | SHA-256 on both sides |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/util/Mth.java` | `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33` |

Provenance identifies exact metadata/client inputs: 26.1 metadata SHA-256 `68e8d6e01e06e6a9a740a7ad474061f9303b004ceedb8224b085a58616390e49`, client SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`; 26.1.1 metadata SHA-256 `86b232149a2ae224b06443730fe32c46c306f035a1599e3477c5237df1c950ef`, client SHA-256 `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`. Both artifact manifests list those client digests. These are manifest/provenance claims; the raw client jars are not present in `.minecraft-decompile-cache/26.1` or `.minecraft-decompile-cache/26.1.1`, and the provenance's old campaign artifact directory is also absent. I did not redownload or regenerate them.

The ready markers' `outputPath` fields point to `build/movement-campaign-2026-10-07/ready/<version>/unobfuscated`, which is absent. The canonical source trees, ready markers, manifests and provenance files are present under `decompiled_minecraft/`. Their complete source counts/byte totals match the markers, each source-manifest SHA matches its ready marker, and the four relevant per-file hashes match their manifest entries. Thus the moved canonical source content is verifiable against the retained manifests, while the hidden original output and raw-cache artifacts are unavailable for reinspection.

## Compared behavior

### Player's direct correction and gates

In both releases, `Player.java:1381–1404` enters `super.travel(input)` immediately when `isPassenger()` is true. On the nonpassenger branch, it first checks `isSwimming()`. `Player.isSwimming()` (`Player.java:1664–1666`) is true only when abilities are not flying, the player is not a spectator, and `super.isSwimming()` is true. If swimming, it stores `getLookAngle().y` in a `double`; selects the `double` multiplier `0.085` for `lookAngleY < -0.2`, otherwise `0.06`; then applies the correction only if `lookAngleY <= 0.0`, `jumping` is true, or the fluid state at `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())` is nonempty. When admitted, it reads delta movement and adds `(lookAngleY - movement.y) * multiplier` to Y, leaving X and Z unchanged. The code then proceeds to its flying or ordinary `super.travel(input)` branch. Both versions have identical `Player.java` bytes and exact method text.

For an eligible local swimming call, this is a pre-super vertical velocity adjustment. Looking downward far enough chooses the stronger multiplier; looking upward does not trigger it unless jumping or the upper-block fluid check permits it. This describes the source gate and write, not a simulated trajectory.

### Authoritative view-angle conversion and LUT widths

`Entity.java:2512–2514` defines `getLookAngle()` as `calculateViewVector(getXRot(), getYRot())`. In `Entity.java:1879–1887`, both rotations and their radian products are `float`: `(float)(Math.PI / 180.0)` is multiplied by `xRot`, and by `-yRot`; the returned vector components remain float expressions and are widened by `Vec3`'s double-valued construction. Consequently `.y` is read as a `double` that contains the widened result of `-xSin`.

`Mth.java:34–37,49–55` uses a `float[65536]` sine table. Each entry is `(float)Math.sin(i / 10430.378350470453)`, with the division performed in double precision before the cast. `sin(double)` multiplies its double input by the double scale, casts the result to `long` (truncation toward zero), masks with `65535L`, casts the index to `int`, and returns a float. `cos(double)` performs the same path after adding double `16384.0`. The float radian arguments from `calculateViewVector` are widened to double at the `Mth` call boundary. These bodies are identical across the pair.

### Inherited movement reached after the correction

Neither `LocalPlayer` source declares a `travel` override. The player call resolves through `Player.travel` to `LivingEntity.travel` (`LivingEntity.java:2392+`), which first chooses fluid travel when `shouldTravelInFluid(level().getFluidState(blockPosition()))` is true, otherwise fall-flying or air travel. For the water branch, `travelInWater` (`LivingEntity.java:2440+`) chooses sprint slow-down or `getWaterSlowDown`, computes water-walker adjustments, optionally sets dolphin-grace slow-down, calls `moveRelative`, moves using current delta movement, applies `(slowDown, 0.8F, slowDown)`, and then applies fluid-falling adjustment. This inherited route has identical `LivingEntity.java` bytes in both versions; there is no paired downstream delta found in the caller path examined.

## Coverage limits

Compared: the complete `Player.travel` method and its local swim-pitch branch; `Entity.getLookAngle` and `calculateViewVector`; the `Mth` sine/cosine table definitions and methods; `LivingEntity.travel`, `shouldTravelInFluid`, and `travelInWater`; presence/absence of `LocalPlayer.travel`; relevant per-file manifest entries and hashes.

Not covered: the complete player tick graph, other travel branches and their dependencies, or a full 26.1→26.1.1 pair audit. No implementation, runtime, test or build work was performed.


