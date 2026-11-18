# 1.16 → 1.16.1 ordinary-player swim pitch correction

## Result

No source difference in the requested operation or its direct inherited movement path. The full canonical Mojmap `Player.java`, `LivingEntity.java`, `Entity.java`, and `Vec3.java` files are byte-identical between the two ready source trees (SHA-256 values below). This establishes no change for the bounded slice; it does not locate an introduction boundary outside these releases.

## Inputs and immutable source identity

Both sides use exact release Mojmap source prepared by Vineflower. Readiness marker and source-manifest SHA-256 identities:

- 1.16: `decompiled_minecraft/1.16/mojmap.ready.json`; source-manifest SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; client SHA-256 `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71`.
- 1.16.1: `decompiled_minecraft/1.16.1/mojmap.ready.json`; source-manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; client SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`.

Canonical source-file SHA-256, identical on each side:

- `net/minecraft/world/entity/player/Player.java`: `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`
- `net/minecraft/world/entity/LivingEntity.java`: `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`
- `net/minecraft/world/entity/Entity.java`: `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`
- `net/minecraft/world/phys/Vec3.java`: `555519ac5fa0ce1a240f90e7db64d5dbc4b005012d91edebcf940009fcbf03f2`.

(The `Vec3` digest above is grouped for readability; its literal SHA-256 is `555519ac5fa0ce1a240f90e7db64d5dbc4b005012d91edebcf940009fcbf03f2`.)

## Compared behavior

In both `Player.java`, `travel(Vec3)` (lines 1380–1410), the pitch-directed correction runs first under `isSwimming() && !isPassenger()`. It evaluates `double g = this.getLookAngle().y` once, then selects `double h = g < -0.2 ? 0.085 : 0.06`. The correction is applied only when `g <= 0.0 || jumping || upper-fluid-state-not-empty`; Java short-circuit order means the jump read is skipped when `g <= 0`, and the upper block/fluid lookup is skipped when either earlier condition is true. The lookup samples `BlockPos(getX(), getY() + 1.0 - 0.1, getZ())`.

`Entity.getLookAngle()` (lines 1680–1682) passes the entity's float `xRot` then float `yRot` to `calculateViewVector(float,float)` (1237–1245). That method keeps angle conversion, sine/cosine and multiplication in `float` locals, constructs `new Vec3(k * l, -m, j * l)`, then `Vec3` stores constructor arguments as doubles. Thus the selected Y component is the float `-m` widened to double when passed to the `Vec3` constructor and read as `double`; assigning it to `g` introduces no narrowing cast. `h` is also double because the literals are unsuffixed. `(g - vec32.y) * h` is evaluated as double and passed through `Vec3.add(double,double,double)`; no float cast occurs in the correction.

When not flying, `Player.travel` calls `super.travel(vec3)`, which is `LivingEntity.travel`. In its water branch (lines 1912 onward), a controlled/effective entity in water, affected by fluids, and unable to stand on the current fluid first calls `moveRelative(g, vec3)`, then the actual inherited displacement `move(MoverType.SELF, getDeltaMovement())` (1944–1945). It then applies `vec32.multiply(f, 0.8F, f)` to the post-move velocity before fluid-falling adjustment (1951 onward). The swim-pitch correction therefore modifies Y velocity before that water branch consumes it for the SELF move. `Entity.move(MoverType,Vec3)` (line 469 onward) remains the collision-aware base move; no branch/order difference exists in the compared full file.

## Bounded coverage

Compared the complete paired `Player.java`, `LivingEntity.java`, `Entity.java`, and `Vec3.java` inputs, including the enclosing player travel method, look-vector production, inherited water travel call sequence, base move entry and vector addition operation. All four files are byte-identical across this pair. No difference found in this bounded ordinary nonpassenger slice. Fluid sampling internals, collision implementation dependencies, and the rest of the player tick graph were not audited by this assignment.