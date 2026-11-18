# 1.16.2→1.16.3 swimmer pitch/vertical trace

## Result

The requested player-override path exists in `Player.travel(Vec3)` in both releases, but the inspected 1.16.2→1.16.3 boundary contains no source change in that path or its pitch lookup and water-travel dependencies. The exact `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` source bytes have matching SHA-256 values across the pair. This is a checked no-difference for these slices, not evidence of a new 1.16.3 change.

## Exact local evidence identity

All sources are Mojmap. Older source is the canonical read-only `decompiled_minecraft/1.16.2/mojmap/` tree. Its ready marker is `decompiled_minecraft/1.16.2/mojmap.ready.json` (SHA-256 `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`); it reports version `1.16.2`, `mojmap`, ready status, 3,516 source files, source-manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`, and raw client SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`. The source manifest itself has that SHA-256. The accompanying `mojmap.provenance.json` SHA-256 is `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`.

Newer source is `decompiled_minecraft/1.16.3/mojmap/`, with hidden `.decompile-provenance.json` SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. Its provenance source records contain the exact source hashes listed below and pin these inputs: cached `version.json.pin.json` SHA-256 `44646026fadecdeee66424097b41ed7d26aac00ad3ee993cd93d33244572fbb6` (metadata payload `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`); `client.jar.pin.json` SHA-256 `a6aa829174b3061724cd848fdd2af040afce499c7969759a9a7622dc4aff8044` (client payload `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`); and `client_mappings.txt.pin.json` SHA-256 `8c658957e07fa4247b6328912f52043f5d3e5371531fae8c2a76de419f7246b1` (mapping payload `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`). No source generation or cache mutation was performed.

The four paired source-file SHA-256 values (matching the older source manifest and newer hidden provenance records) are:

| Source | SHA-256, both releases |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

## Trace from the player override

`Player.java:1387–1417`, both releases: `Player.travel(Vec3)` first saves position. Before either `super.travel(vec3)` call, its own swimming branch requires `isSwimming() && !isPassenger()`. It reads `g = getLookAngle().y`, chooses `h = g < -0.2 ? 0.085 : 0.06`, and applies the direct vertical correction only when `g <= 0.0`, `jumping`, or the block at `(x, y + 1.0 - 0.1, z)` has nonempty fluid state. The write is `setDeltaMovement(vec32.add(0.0, (g - vec32.y) * h, 0.0))`. This is a pre-super player movement response driven by look-Y; it does not replace block state. The flying branch is separate, and otherwise the method invokes `super.travel(vec3)` and then movement statistics.

`Entity.java:1700–1702` supplies `getLookAngle()` from current `xRot` and `yRot` via `calculateViewVector`, rather than the partial-tick `getViewVector(float)` path. `Entity.java:1243–1251` converts pitch and negative yaw to `float` radians using `(float)(Math.PI / 180.0)`, calls `Mth.cos`/`Mth.sin`, and returns `(k * l, -m, j * l)`. Thus the swimmer's `g` is the Y component `-Mth.sin(pitchRadians)` from this float computation.

`Mth.java:12–16, 25–31` builds one 65,536-entry `float[] SIN` with `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`. `sin(float)` indexes it with `(int)(f * 10430.378F) & 65535`; `cos(float)` offsets by `16384.0F` before the same cast and mask. The lookup and the enclosing Entity math have identical source bytes in both releases.

## Actual `super.travel` water branch and separate later adjustment

`LivingEntity.java:1914–1957`, both releases: the invoked superclass method enters its local-control/AI gate, sets gravity `d = 0.08` (or `0.01` with slow falling when vertical velocity is nonpositive), and enters water travel only for `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState.getType())`. It derives water slowdown and movement acceleration, caps Depth Strider at `3.0F` and halves it off ground, applies Depth Strider/Dolphin's Grace adjustments, calls `moveRelative(g, vec3)`, then `move(MoverType.SELF, getDeltaMovement())`. After the move it handles the horizontal-collision/climbable response, multiplies velocity by `(f, 0.8F, f)`, then calls `getFluidFallingAdjustedMovement(d, bl, velocity)`. The caller therefore runs after the initial water movement and damping, not in place of Player's look-Y correction.

`LivingEntity.java:2074–2087` is a separate helper used at that later point: only when gravity is enabled and the entity is not sprinting, it either returns a new vector with `y = -0.003` for its `bl`/absolute-difference threshold case or uses `y = vec3.y - d / 16.0`; otherwise it returns the input vector unchanged. `bl` was captured from the pre-branch condition `getDeltaMovement().y <= 0.0`. This passive fluid-falling adjustment has no pitch/look input. It is source-identical across this pair and must not be conflated with the player override.

## Scope boundary

Checked: `Player.travel`, its `Entity.getLookAngle`/`calculateViewVector`/Mth float LUT dependencies, and `LivingEntity.travel` water movement through the called fluid-falling helper. These source slices have no 1.16.2→1.16.3 delta. No broader player-tick inventory, intermediate patch boundary, runtime parity, or implementation claim is made.
