# 1.15 to 1.15.1: player swim pitch vertical adjustment

## Evidence identity and integrity

Exact Mojmap decompilations: published release IDs 1.15 and 1.15.1; both ready records say status: ready, mapping mojmap, and identify run prep-1.15-1.15.1-mojmap-2026-10-09-r1, using Vineflower 1.12.0 and Java 25. The records identify exact client SHA-256 values 41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b (1.15) and 6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358 (1.15.1). Ready records' source-manifest SHA-256 values were checked against the files: 251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340 and c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d, respectively.

The cited canonical files match their source-manifest entries on both sides. For each file below, the listed SHA-256 is identical in 1.15 and 1.15.1:

| File | SHA-256 |
|---|---|
| net/minecraft/world/entity/player/Player.java | 6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0 |
| net/minecraft/world/entity/LivingEntity.java | 139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79 |
| net/minecraft/world/entity/Entity.java | 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e |
| net/minecraft/util/Mth.java | a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4 |
| net/minecraft/world/phys/Vec3.java | 5c3c36231efe06c3c0bf70dcf2c2ec2f2b79089cb2dad530db1b3b7e1a5ec909 |

## Compared operation

Both versions have identical Player.travel(Vec3) at net/minecraft/world/entity/player/Player.java:1456-1484; the full Player.java bytes are identical. For isSwimming() && !isPassenger(), it reads g = this.getLookAngle().y as a double, then chooses h = g < -0.2 ? 0.085 : 0.06 (double literals). In the same order, it adjusts velocity only if g <= 0.0, or jumping, or the block state at (getX(), getY() + 1.0 - 0.1, getZ()) has a nonempty fluid state. Java short-circuiting means the fluid/block-state read is skipped when either earlier condition is true. If the condition passes, it reads the current delta movement and calls setDeltaMovement(vec32.add(0.0, (g - vec32.y) * h, 0.0)); the pitch-directed term is computed in double precision and added before the superclass travel call.

For this angle, Entity.getLookAngle() at Entity.java:1661-1662 calls calculateViewVector(this.xRot, this.yRot). At Entity.java:1219-1226, pitch radians are formed as float h = xRot * (float)(Math.PI / 180.0); m = Mth.sin(h) is float and the view-vector Y argument is -m. Vec3 stores that constructor argument in a double Y field, widening the float value; thus g is the widened float LUT result with its sign negated, not a newly evaluated double sine. Mth.java:12-16 initializes 65,536 floats with (float)Math.sin(ix * Math.PI * 2.0 / 65536.0). Mth.sin(float) at Mth.java:25-27 indexes it with (int)(f * 10430.378F) & 65535 (float multiplication, Java truncating cast to int, then mask). These helper files are byte-identical across the pair.

For a local, nonflying player swimming in water, Player.travel then calls super.travel(vec3) (Player.java:1482) in the nonflying branch. The inherited LivingEntity.travel runs under isEffectiveAi() || isControlledByLocalInstance() (LivingEntity.java:1831-1833), takes the water branch when in water and not a flying player (:1840-1841), performs moveRelative(g, vec3), then calls move(MoverType.SELF, this.getDeltaMovement()) (:1949-1950). That invokes the inherited Entity.move(MoverType, Vec3) implementation (Entity.java:450 onward), which consumes the movement vector to update the bounding box/location through its collision path. The subsequent water damping is setDeltaMovement(vec32.multiply(f, 0.8F, f)) (LivingEntity.java:1956); the vertical pitch delta therefore participates in this actual move before vertical damping. These inherited source bytes are also identical in both releases.

## Result

No 1.15 → 1.15.1 difference was found in this bounded operation: the pitch lookup, thresholds and coefficients, ordered short-circuit fluid gate, velocity write, superclass dispatch, water-path move, and subsequent damping are byte-identical. No finding is warranted for this operation on this pair.

