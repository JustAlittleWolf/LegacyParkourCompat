# Input-to-motion relative acceleration source comparison

## Scope and terminal result

Compared canonical `decompiled_minecraft/` source for 1.12.2, 1.13, 1.13.1, 1.13.2, 1.14, 1.14.1, 1.14.2, 1.14.3 and 1.14.4. This is a bounded comparison of keyboard-produced horizontal input, its reduction to movement acceleration, and yaw rotation. It does not compare sprint eligibility, sneak pose transitions, or other travel branches as independent mechanics.

**COMPLETED — exact provenance is verified for all nine listed releases.** The nested Yarn provenance manifests under 1.14.1–1.14.3 bind exact client, Yarn and Intermediary artifacts, decompiler/toolchain, runtime and generated source hashes; the relevant source file hashes match those manifests. The 1.14.1–1.14.3 helper and keyboard bodies show no change across those patches.

## Findings from exact-provenance sources

### Ordinary player witness

In 1.12.2–1.13.2, `KeyboardInput.tick()` constructs signed float sideways/forward components and preserves independent key booleans. A forward+left witness produces `(movementSideways, movementForward) = (1, 1)`; held sneak scales both components by `(float)(component * 0.3)` while leaving the directional booleans as key intent. `LocalClientPlayerEntity` copies those floats into `sidewaysSpeed`/`forwardSpeed`. In 1.13.2, `LivingEntity.tick()` calls `moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` (line 1926); `moveRelative(float forwards, float sideways, float upwards)` forwards those parameters to `Entity.updateVelocity(forwards, sideways, upwards, scale)` (line 1553 in the land branch). Thus ordinary horizontal input reaches the old three-float helper as `(sidewaysSpeed, verticalSpeed, forwardSpeed)`: sideways is first/X, vertical is second/Y, and forward is third/Z. The ordinary witness has zero in the **second/Y** coordinate. The helper performs diagonal normalization and yaw rotation.

In 1.14 and 1.14.4, `KeyboardInput.tick(bl, bl2)` first records key booleans, then uses ternaries to produce the same signed float axes. It applies 0.3 scaling when `!bl2 && (sneaking || bl)`. Player travel passes a `Vec3d` ordered as `(sidewaysSpeed, upwardSpeed, forwardSpeed)` to `LivingEntity.travel`, which calls `Entity.updateVelocity(float, Vec3d)`. The ordinary horizontal witness therefore has zero in the vector's second/Y coordinate, matching the legacy coordinate order. Sneak/slowdown input predicates remain distinct from the normalized vector. The helper result is added to existing velocity; it does not set velocity to the transformed input.

### Independent helper change, observed between 1.13.2 and 1.14

1.12.2, 1.13, 1.13.1 and 1.13.2 use `Entity.updateVelocity(float sideways, float forwards, float upwards, float scale)`. It computes `float squared = sideways*sideways + forwards*forwards + upwards*upwards`; only `!(squared < 1.0E-4F)` proceeds. It takes `MathHelper.sqrt(float)`, floors the resulting length to `1.0F`, computes a float `scale / length`, and multiplies each float axis by that float. It then evaluates yaw radians as `yaw * (float)(Math.PI / 180.0)`, computes float sine before float cosine, and adds `sideways*cos - upwards*sin`, `forwards`, and `upwards*cos + sideways*sin` to the double velocity fields. The ordinary horizontal player input path has a zero second/Y coordinate and active first/X and third/Z coordinates. The helper's formal parameter names do not change that call order: `moveRelative` passes `(sidewaysSpeed, verticalSpeed, forwardSpeed)` through to the first, second and third `updateVelocity` arguments.

1.14 and 1.14.4 use `Entity.updateVelocity(float scale, Vec3d input)` and a static helper. The helper obtains double squared length, returns `Vec3d.ZERO` when it is `< 1.0E-7`, and otherwise normalizes only when squared length is strictly `> 1.0`; equality at 1.0 leaves the input unchanged. It scales the vector before computing float yaw sine and cosine, then constructs a new double vector `(x*cos - z*sin, y, z*cos + x*sin)`. The result is added to velocity. For normal diagonal keyboard input, squared length 2 triggers normalization in both algorithms. For low-magnitude nonzero vectors, the cutoff differs; old squared-length `<1.0E-4F` versus new `<1.0E-7`. At the exact thresholds, both gates proceed because both comparisons are strict. Legacy intermediate vector scaling and rotated X/Z expressions are float; 1.14 vector coordinates and scale operations are double after the float scale argument is widened, while trigonometric values remain float. The changed helper is independent from key intent and keyboard sign construction.

The old and new horizontal yaw transforms have the same mathematical orientation: local sideways is the X component, local forward is the Z component; positive yaw applies `x*cos-z*sin` and `z*cos+x*sin`. The precision path differs because the old float axis expression is widened when added to double velocity, while the vector path multiplies/subtracts doubles using float sine/cosine coefficients.

### Keyboard intent and input-vector transforms

- 1.12.2–1.13.2 `KeyboardInput` sets `forward`, `backward`, `left`, `right`, `jumping`, `sneaking` independently of component arithmetic; left increments sideways and right decrements it. Opposing keys cancel through addition/subtraction. Sneak scales each float component after key construction.
- 1.14 and 1.14.4 keep directional/sneak booleans separate, assign `movementForward` and `movementSideways` with equality/ternary expressions, and apply the same 0.3 component scale under the additional `!bl2` gate. This comparison does not treat the boolean inputs or sprint checks as part of the normalization helper.
- The 1.14.1–1.14.3 Yarn `KeyboardInput` bodies hash identically to one another and match the 1.14/1.14.4 Feather body after mapping-name differences. Their nested exact provenance manifests and relevant source hashes are listed below. `Input` has an extra `movementForward > 1.0E-5F` predicate in 1.14.2/1.14.3, unlike 1.14.1; this is an input intent/predicate surface, not a difference in the keyboard vector transform.

## Slice coverage

- `Entity.updateVelocity` / movement-input helper and the associated vector normalization method: inspected in every listed release and exact-provenance confirmed for all nine releases.
- `KeyboardInput` and `Input`: inspected in every listed release and exact-provenance confirmed for all nine releases.
- Ordinary player data path: checked key construction and local-player input assignment in old/new source; checked `LivingEntity` calls into the helper. Sprinting thresholds/activation, sneak pose gates, item-use slowdown, flight transforms and server-side input packet handling are not accepted/closed by this narrow comparison.
- Mapping family: Feather is available for 1.12.2, 1.13, 1.13.1, 1.13.2 and 1.14; 1.14.1–1.14.3 have Yarn only; 1.14.4 has Feather (also Mojmap, not used). Cross-family symbol names are not used as proof of behavior; comparisons above rely on method bodies and call semantics.

## Exact source identities

All digests below are SHA-256 of the UTF-8 source file bytes or provenance JSON bytes in the canonical `decompiled_minecraft/` root. Mapping identities are taken from those per-version provenance manifests where present. `Entity.java` contains the acceleration/rotation helper; `Vec3d.java` contains normalization; `KeyboardInput.java` records key-to-axis behavior.

| Release | Mapping | Provenance JSON SHA-256 | `Entity.java` SHA-256 | `Vec3d.java` SHA-256 | `KeyboardInput.java` SHA-256 |
|---|---|---|---|---|---|
| 1.12.2 | Ornithe Feather Gen2 build.2 | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.13 | Ornithe Feather Gen2 `1.13+build.2` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.13.1 | Ornithe Feather Gen2 `1.13.1+build.2` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.13.2 | Ornithe Feather Gen2 build.2 | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `6941a43536e8aefaa435a4e5f1731b0fa26a9d96503d1f4d07de8950c297f3c5` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.14 | Ornithe Feather Gen2 `1.14+build.2` | `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` | `15e9d4cde45d857ca3117ccfe2d5d9071c08e7ee4a67b4053f7b006fe7a4bfa9` | `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b` | `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526` |
| 1.14.1 | Yarn `1.14.1+build.10` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `c56acd6b7b03e1a05d01687c490b5533c53c1b97ba637f7492a2d6d1744b55dc` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| 1.14.2 | Yarn `1.14.2+build.7` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` | `c56acd6b7b03e1a05d01687c490b5533c53c1b97ba637f7492a2d6d1744b55dc` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| 1.14.3 | Yarn `1.14.3+build.13` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` | `c56acd6b7b03e1a05d01687c490b5533c53c1b97ba637f7492a2d6d1744b55dc` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| 1.14.4 | Ornithe Feather Gen2 build.2 | `7167b135e0222fcf58ff5f36a36fbb604d5068f63a59165c8c4d18ce7d9b21c9` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` | `b69b990b768e52d6fac6b3ed82f60e7526e78d0837c868a61a4171ebca21040b` | `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526` |

For 1.12.2, 1.13, 1.13.1, 1.13.2, 1.14 and 1.14.4, the root `sources.sha256` manifest file digests for `Entity.java`, `Vec3d.java` and `KeyboardInput.java` match the listed source bytes. For 1.14.1–1.14.3, the provenance JSON is `decompiled_minecraft/<version>/yarn/.decompile-provenance.json`; the nested manifest digests above match those canonical bytes, and each manifest's `Entity.java`, `Vec3d.java`, `KeyboardInput.java` and `Input.java` hashes match the corresponding source files. These provenance files identify the exact prepared inputs.

## Exact source locations

Relative to each mapping source root:

- 1.12.2 `net/minecraft/entity/Entity.java`: `updateVelocity`, lines 1049–1067.
- 1.13 `net/minecraft/entity/Entity.java`: `updateVelocity`, lines 1056–1074.
- 1.13.1 and 1.13.2 `net/minecraft/entity/Entity.java`: `updateVelocity`, lines 1057–1075.
- 1.14 Feather `net/minecraft/entity/Entity.java`: `updateVelocity` and static vector helper, lines 1010–1025.
- 1.14.1 Yarn `net/minecraft/entity/Entity.java`: `updateVelocity` / `movementInputToVelocity`, lines 1009–1024.
- 1.13.2 ordinary call path: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, input-to-speed assignment, lines 638–640; `net/minecraft/entity/living/LivingEntity.java`, `moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)`, line 1926; `moveRelative` helper-to-`updateVelocity` call, line 1553. This confirms the vector `(sidewaysSpeed, verticalSpeed, forwardSpeed)` and Y as the zero coordinate.
- 1.14.1 ordinary call path: `net/minecraft/client/network/ClientPlayerEntity.java`, input-to-speed assignment, lines 592–594; `net/minecraft/entity/LivingEntity.java`, `travel(new Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed))`, line 2204. This confirms the same X/Y/Z order.
- 1.14.2 Yarn `net/minecraft/entity/Entity.java`: same helper, lines 1011–1026.
- 1.14.3 Yarn `net/minecraft/entity/Entity.java`: same helper, lines 1055–1070.
- 1.14.4 Feather `net/minecraft/entity/Entity.java`: `updateVelocity` and vector helper, lines 1062–1077.
- 1.12.2–1.13.2 `net/minecraft/client/entity/living/player/KeyboardInput.java`: `tick`, lines 12–50.
- 1.14 Feather / 1.14.4 Feather: `net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick`, lines 12–27.
- 1.14.1–1.14.3 Yarn: `net/minecraft/client/input/KeyboardInput.java`, `tick`, lines 12–27.
- Vector normalization dependency: `net/minecraft/util/math/Vec3d.java`, `normalize()` computes a double square root and divides each double component by that length. In the 1.14 movement helper this method is used only when squared length is greater than 1.

## Boundary and limitations

The helper change is present in exact 1.14.0 and remains present in 1.14.1–1.14.4. The supported boundary is introduced after 1.13.2 and present by 1.14.0; no claim is made about an earlier intermediate cutover. The 1.14.1–1.14.3 patch coverage is provenance-confirmed and shows no helper or keyboard-transform change across those releases. Source review does not establish runtime trajectory parity. No code, tests, decompilation, or game launch was performed.