# Player swim-pitch vertical correction: 1.20.2 → 1.20.3

## Source identity and byte checks

Windows PowerShell 7.6.9; canonical sources read-only under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- **1.20.2 Mojmap:** local `mojmap.ready.json` identifies release `1.20.2`, mapping `mojmap`, 4,904 source files, client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, and source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. The manifest's actual bytes match that pin. Its checked source entries match all six files below.
- **1.20.3 Mojmap:** `1.20.3/mojmap/.decompile-provenance.json` SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`. Its local input pins include client JAR `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, Mojang client mappings `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`, and exact `1.20.3.json` metadata `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`. The provenance `sources` entries match all six files below.

| Source path (both releases) | 1.20.2 SHA-256 | 1.20.3 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/util/Mth.java` | `8a16560d3dd84853a2caaa658d6a106ea2e7f4e04d5cd6ed4d8f76839cda9dce` | `f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075` |
| `net/minecraft/core/BlockPos.java` | `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc` | `a91e96745a49cdb2ec18be2ac7986071d25ed2de2a04c8f779ca41cfcccc29fc` |
| `net/minecraft/world/phys/Vec3.java` | `2ed588cbbb5bf85f847ba21242fafa2d627bc25213db7b983eaab58297d67fab` | `dc05337c94e64d5c137138ef98b7f10768a420af23bffb0812b68a8320c7c813` |

## Paired source result

**There is no change to the requested pre-`super.travel` swim-pitch Y correction between 1.20.2 and 1.20.3.** `Player.travel(Vec3)` has the same swim condition, predicate order, pitch-derived look-Y value, threshold constants, sampled block position, delta expression, and timing before `super.travel`:

- 1.20.2 `Player.java:1450–1474`; 1.20.3 `Player.java:1451–1471`.
- Both require `isSwimming() && !isPassenger()`. `Player.isSwimming()` additionally excludes flying and spectators (1.20.2 `Player.java:1871–1873`; same override in 1.20.3).
- Set `lookY = getLookAngle().y`; choose `factor = lookY < -0.2 ? 0.085 : 0.06`; apply the vertical adjustment when `lookY <= 0.0 || jumping || sampledBlockFluidIsNonempty`. If enabled, add `(lookY - currentVelocityY) * factor` to Y velocity only.
- For an upward look with jumping false, the sample is evaluated last because `||` short-circuits left-to-right. It calls `BlockPos.containing(getX(), getY() + 1.0 - 0.1, getZ())`, then `level().getBlockState(pos).getFluidState().isEmpty()`. Thus the sample is at player X/Z and Y + 0.9, with each double coordinate floored by `BlockPos.containing`/`Mth.floor`; any nonempty fluid passes. A downward/equal-horizontal look or jumping=true avoids that world lookup.

### Numeric path for `lookY`

`Entity.getLookAngle()` directly calls `calculateViewVector(getXRot(), getYRot())` (1.20.2 `Entity.java:1458–1465, 2004–2006`; 1.20.3 `Entity.java:1459–1466, 2005–2007`). Pitch is a `float`; the radians conversion is `pitch * (float)(Math.PI / 180.0)`, so the constant is rounded to float and the multiplication is float. For the Y component, the helper returns `-Mth.sin(pitchRadians)`.

Both versions initialize `Mth.SIN` as a `float[65536]`; each entry narrows `Math.sin(i * Math.PI * 2.0 / 65536.0)` to float. `Mth.sin(float)` indexes it with `(int)(radians * 10430.378F) & 65535`: the scale multiply is float, float-to-int truncates toward zero, then the mask selects a 16-bit table index. The selected float is negated for the look-vector Y and is widened to double by `new Vec3(double, double, double)` (constructor assignment is direct). The vertical correction arithmetic then uses doubles because lookY, velocity Y and the factor literals are doubles.

Evidence: `Entity.java` as above; `Mth.java` 1.20.2 `:25–30, 42–48`, 1.20.3 `:27–35, 47–53`; `Vec3.java:53–57` both. These paired expressions and table width/initialization/indexing are identical.

### Inherited water movement consuming the velocity

The prelude is followed by `super.travel`. On the ordinary non-flying path this reaches `LivingEntity.travel`; while locally controlled, its water branch first obtains fluid state at `blockPosition()`, then requires `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState)`. It applies input with `moveRelative`, moves by the current delta with `move(MoverType.SELF, getDeltaMovement())`, applies horizontal/vertical slowdown, fluid-falling adjustment, then collision recovery. This consumes the Y velocity after the Player correction. The water branch is the same in both versions (1.20.2 `LivingEntity.java:2038–2081`; 1.20.3 `:2035–2078`). Its `isInWater()` state is separate from the swim-pitch block's sampled fluid test.

The surrounding `Player.travel` changed outside this slice: 1.20.2 snapshots position and calls `checkMovementStatistics` after travel; 1.20.3 does not do so here. That does not alter the compared swim-pitch correction or its ordering before the superclass call.

## Coverage boundary

This is a bounded comparison of the local ordinary active player's pre-super swim-pitch vertical correction, the exact Entity/Mth look-vector numeric path, its short-circuit fluid sample, and the inherited water movement that consumes the adjusted velocity. It is a no-difference result for this operation, not a finding of a changed behavior and not a full-pair movement coverage audit.
