# 1.16.1 → 1.16.2 direct `Entity.turn` pitch slice

## Scope and source readiness

This is a source-only comparison of the Mojmap `Entity.turn(double, double)` pitch write/clamp and the first pitch read in the live Elytra `LivingEntity.travel` branch. No caller/input-device plumbing, networking, teleport path, timing, rendering, build, tests, or game runtime was inspected or used.

The source trees exist at `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`; the relied-on `Entity.java`, `LivingEntity.java`, and `Mth.java` files are present. Each Mojmap tree root lists only `com/` and `net/`; neither has `.decompile-provenance.json`. Per `buildSrc/README.md`, trees without a manifest are legacy outputs reused with an explicit unverified-provenance warning. Thus the directory names establish the intended release/namespace labels but do not prove the source publication identity, Mojmap artifact/build, decompiler inputs, or generation history. This bounded comparison is reproducible against the local bytes below, but exact-source provenance remains unverified; no source generation or network lookup was performed.

## Paired source evidence

- 1.16.1, Mojmap: `net/minecraft/world/entity/Entity.java`, `turn`, lines 332–344; `net/minecraft/util/Mth.java`, `clamp(float,float,float)`, lines 94–99.
- 1.16.2, Mojmap: `net/minecraft/world/entity/Entity.java`, `turn`, lines 340–352; `net/minecraft/util/Mth.java`, `clamp(float,float,float)`, lines 94–99.

Both `turn` bodies are identical apart from shifted line numbers. Parameters `d` and `e` are `double`; pitch delta is first computed as `double f = e * 0.15`. The first current-pitch write is `this.xRot = (float)(this.xRot + f)`: the float field is promoted into the double sum, then the sum is narrowed to `float`. The next statement clamps that float via `Mth.clamp(float, float, float)` to `[-90.0F, 90.0F]` and writes the float result back to `xRot`. It then updates yaw, updates `xRotO` using the same double-then-float pattern and clamps `xRotO`; only after those writes does the optional vehicle callback run. There is no standalone pitch setter call in `turn`.

A finite >360° post-cast witness within this method is an ordinary local `Entity` with `xRot == 0.0F` and a direct `turn(0.0, 3000.0)` call: `f` is 450.0 (double), the first assignment stores `450.0F` (finite and above 360°), and the immediately following float clamp makes the live `xRot` equal `90.0F`. This is an intermediate source-level value, not the final pitch observable by later movement.

## First live Elytra movement pitch read

- 1.16.1: `net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1977–1984; `Entity.java`, `getLookAngle`, lines 1680–1682.
- 1.16.2: `net/minecraft/world/entity/LivingEntity.java`, `travel`, lines 1979–1986; `Entity.java`, `getLookAngle`, lines 1700–1702.

When the `isFallFlying()` branch is reached, `travel` first calls `getLookAngle()`. That method immediately passes the live `this.xRot` as the pitch argument to `calculateViewVector`; that is the first pitch read in this movement branch. `travel` next reads `this.xRot` directly to form `float j = this.xRot * (float)(Math.PI / 180.0)`. Both releases use this same call/read order and expression. Therefore a >360° value from the first `turn` assignment cannot reach either Elytra pitch read through this call path: `turn` clamps it before returning.

## Result and evidence identity

No difference was found in the bounded `turn` pitch arithmetic/clamp or first live Elytra movement pitch-read sequence. This result is limited to those examined methods and dependencies; it is not a complete 1.16.1→1.16.2 movement-pair coverage claim, and source acceptance cannot be bound to a verified source-publication identity until provenance is supplied.

SHA-256 of the relied-on local source files (exact file bytes):

- 1.16.1 `Entity.java`: `27E3BB01B5D03C09161891E717C16477899768333082E81EBFAAC4E525616576`
- 1.16.1 `LivingEntity.java`: `B6EA2B9B037B79FC3EA6E1F6BCD2C4C1D89D4A96315968A8F247641F6C335F26`
- 1.16.1 `Mth.java`: `0E4424FFA12923314C309AE13E012768271DFCACE4B5A84727F74E8170AFE290`
- 1.16.2 `Entity.java`: `F9A9A073FE3105A0AA53D0F21EC72E59084E8D21A14C1CD3BE75703865EE266`
- 1.16.2 `LivingEntity.java`: `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`
- 1.16.2 `Mth.java`: `0E4424FFA12923314C309AE13E012768271DFCACE4B5A84727F74E8170AFE290`
