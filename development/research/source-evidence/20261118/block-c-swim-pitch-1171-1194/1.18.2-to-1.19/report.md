# 1.18.2–1.19 player swim pitch Y update

## Exact inputs and source integrity

Compared Minecraft **1.18.2** and **1.19**, both Mojmap sources, from the local canonical `decompiled_minecraft/` tree. The 1.18.2 ready marker identifies client SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` and source-manifest SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`. The 1.19 provenance identifies the Mojang client SHA-256 `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764`; its ready marker identifies source-manifest SHA-256 `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`. Each ready marker’s manifest digest matched the local manifest bytes. Every relied source file’s SHA-256 matched its entry in that manifest:

| Release | `Player.java` | `LivingEntity.java` | `Entity.java` |
|---|---|---|---|
| 1.18.2 | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` | `db4168d531caf18f22e3feFD073365E776DA4075CE01452BB9F7671D9B458782` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.19 | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |

The local provenance records Mojmap and Vineflower 1.12.0 on both sides. Exact source bodies were read in place; no source generation or network lookup was used.

## Compared path

- 1.18.2 `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1422–1453.
- 1.19 same file/member, lines 1459–1490.
- 1.18.2 `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`, line 2009 onward; 1.19 same member, line 2042 onward.
- 1.18.2 `net/minecraft/world/entity/Entity.java`, `getLookAngle()` line 1816 and `calculateViewVector(float,float)` line 1314; 1.19 same methods at lines 1841 and 1339.

Compared extracted method bodies byte-for-text: `Player.travel`, `LivingEntity.travel`, `Entity.getLookAngle`, and `Entity.calculateViewVector` are identical across the pair (including operation order and literals). The enclosing source files differ elsewhere; this result is limited to this path.

## Player’s pre-super update and sample order

Both Player implementations first snapshot X/Y/Z. They next evaluate `isSwimming()` and then `!isPassenger()`. On that gate, `getLookAngle().y` is assigned to a `double`; `getLookAngle()` uses current `getXRot()` and `getYRot()` without interpolation. `calculateViewVector` converts each float angle to radians in float arithmetic, computes `Mth.cos`/`Mth.sin` as floats, then constructs `Vec3($$5 * $$6, -$$7, $$4 * $$6)`. The selected `.y` is the vector’s direct Y component, not an array lookup. The float component is widened to double when placed in `Vec3`; that same double is read as `$$4` here.

The code then chooses a **double** coefficient: `0.085` only when `$$4 < -0.2`; otherwise `0.06` (including exactly `-0.2`). It next evaluates `$$4 <= 0.0 || this.jumping || !headFluidEmpty` left-to-right with Java short-circuiting. Thus nonpositive look-Y skips both `jumping` and the head block/fluid sample; positive look-Y samples `jumping`, and samples the head position only when not jumping. The position is built from current X/Z and `(getY() + 1.0) - 0.1`; the fluid state is tested for nonempty.

When that OR is true, it reads current delta movement and writes `oldDelta.add(0.0, ($$4 - oldDelta.y) * coefficient, 0.0)` back via `setDeltaMovement`. Subtraction, multiplication, and addition are double operations. This is a direct vertical-velocity adjustment before `super.travel($$0)`.

For the ordinary nonpassenger, nonflying path, Player then calls `LivingEntity.travel`. That inherited method enters its movement body only for `isEffectiveAi() || isControlledByLocalInstance()`. Its water branch additionally requires `isInWater()`, `isAffectedByFluids()`, and `!canStandOnFluid(blockFluidState)`. In that branch it performs `moveRelative`, moves by current delta, applies water damping, calls `getFluidFallingAdjustedMovement`, and writes the resulting movement. Therefore the Player pitch-derived Y impulse is the input velocity seen by inherited active water movement. The separate flying-player branch also calls `super`, but afterward rewrites Y from the velocity captured before that call; it is outside this ordinary path.

## Result

**No difference found in the assigned operation for 1.18.2 → 1.19.** The pre-super direct Y writer, its pitch-vector computation, coefficient selection, short-circuit sample order, and the inherited water-motion path are source-identical. This closes only this assigned slice, not complete pair coverage.
