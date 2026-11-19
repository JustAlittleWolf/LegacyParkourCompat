# Earlier input acceleration applicability and exact transition

## Scope and result

This evidence closes the ordinary dry on-foot input-helper boundary from the previously established two-axis era through the first exact three-float release. It follows keyboard axes through the ordinary `LivingEntity` caller into `Entity.updateVelocity`; it does not cover sprint/sneak eligibility, water/lava/fall-flying branches, or runtime trajectory parity.

The two-axis helper is present in exact 1.8.9, 1.9.4, 1.10.2, and 1.11.2 Feather Gen2 sources. Exact 1.12.0 already has the three-float helper, and its `Entity.java` and `LivingEntity.java` bytes are identical in 1.12.1 and 1.12.2. The exact transition for this helper/call path is therefore between 1.11.2 and 1.12.0; 1.12.0 is the first inspected release with the three-float form. This adds an exact transition release, rather than extending the earlier “by 1.12.2” bound.

## Earlier two-axis controls

In 1.8.9, `LivingEntity.tick()` calls `moveRelative(this.sidewaysSpeed, this.forwardSpeed)` (line 1450), and the dry on-foot land branch calls `updateVelocity(sideways, forwards, k)` (line 1140). `Entity.updateVelocity(float sideways, float forwards, float scale)` (lines 844–860) treats sideways as local X and forwards as local Z: after yaw rotation, it adds `sideways*cos - forwards*sin` to X velocity and `forwards*cos + sideways*sin` to Z velocity. There is no Y input.

Exact 1.9.4, 1.10.2, and 1.11.2 retain that two-axis signature and ordinary caller order. Their corresponding `LivingEntity` dry land helper calls are lines 1370, 1400, and 1456; tick call sites are lines 1711, 1747, and 1803. The `Entity.updateVelocity` declarations are lines 947, 960, and 1028. Each helper computes float `sideways*sideways + forwards*forwards`, proceeds unless the sum is strictly `< 1.0E-4F`, takes the float square root, floors length to `1.0F`, computes `scale / length`, and scales both float components. It evaluates yaw sine before cosine, then adds the two rotated components to X/Z velocity. 1.8.9 computes its angle as `yaw * (float)Math.PI / 180.0F`; 1.9.4–1.11.2 use `yaw * (float)(Math.PI / 180.0)`.

Keyboard input constructs separate signed sideways and forward float axes; e.g. left increments sideways, right decrements it, and held sneak scales each component. This yields an ordinary diagonal witness `(sideways, forwards) = (1, 1)` before any sneak scaling. The same keyboard source bytes (`7be114…8654a`) occur in 1.9.4–1.12.2; 1.8.9 uses `a5e5b2…a76dd`.

## Exact transition and post-transition controls

1.12.0 `LivingEntity.tick()` calls `moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` (line 1847). `moveRelative(float forwards, float sideways, float upwards)` calls `updateVelocity(forwards, sideways, upwards, u)` in the dry land branch (line 1493). Because of that caller’s parameter order, the values arriving at `Entity.updateVelocity(float sideways, float forwards, float upwards, float scale)` are `(sidewaysSpeed, verticalSpeed, forwardSpeed)`, i.e. local X/Y/Z. Ordinary horizontal keyboard input has zero in the middle/Y slot. Thus the three-float helper adds a Y component as well as rotating local X/Z, while preserving the ordinary horizontal player witness through a zero Y value.

In exact 1.12.0, 1.12.1, and 1.12.2, `Entity.updateVelocity` (lines 1049–1067) computes float `sideways² + forwards² + upwards²`, uses the same strict `< 1.0E-4F` deadband, float square root, minimum length `1.0F`, and scale-before-rotation order. It scales all three float coordinates, evaluates sine then cosine, adds `sideways*cos - upwards*sin` to X velocity, adds `forwards` to Y velocity, and adds `upwards*cos + sideways*sin` to Z velocity. The ordinary player path supplies a zero `forwards` value at this helper, so Y receives zero from input acceleration.

The applicable difference is dimensional/call-contract expansion from two horizontal inputs to three local coordinates. The three-float helper preserves the same horizontal normalization and yaw expressions for zero Y input; it adds the middle-axis contribution for callers that supply it. The operation is invoked in the existing dry land branch for a locally controlled entity (or, in later sources, the logical-side movement-update gate), before movement and subsequent gravity/friction handling.

## Exact source identities

All file digests are SHA-256 of canonical UTF-8 source bytes. For 1.8.9–1.11.2 the source-file digests below were checked against each release’s `ornithe-feather.sources.sha256`; the displayed provenance JSON digest binds the exact run and Feather mapping build. For 1.12.0 and 1.12.1, each listed source digest was checked against the `sources` map in the hidden canonical `ornithe-feather/.decompile-provenance.json`; the sidecar pins the exact client, version metadata and mapping artifacts. For 1.12.2 the source digests match the canonical `ornithe-feather.sources.sha256` and its pinned provenance/source evidence.

| Release | Mapping identity | Provenance SHA-256 | `Entity.java` SHA-256 | `LivingEntity.java` SHA-256 | `KeyboardInput.java` SHA-256 |
|---|---|---|---|---|---|
| 1.8.9 | Feather Gen2 `1.8.9+build.2` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` | `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd` |
| 1.9.4 | Feather Gen2 `1.9.4+build.2` | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.10.2 | Feather Gen2 `1.10.2+build.2` | `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.11.2 | Feather Gen2 `1.11.2+build.2` | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.12.0 | Feather Gen2 `1.12+build.2` | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.12.1 | Feather Gen2 `1.12.1+build.2` | `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.12.2 | Feather Gen2 `1.12.2+build.2` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |

The 1.12.0 embedded sidecar pins Feather merged mapping JAR SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`, Feather version metadata SHA-256 `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`, and client JAR SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`. The 1.12.1 sidecar pins mapping JAR `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`, Feather version metadata `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`, and client JAR `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`. The 1.12.2 canonical artifact manifest pins client JAR `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, Feather merged mapping JAR `e48244030c53979793bdfbe48d7f1f3536f7e4b678ee5890a416198037cd46ca`, and Feather tiny mappings `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`.

## Source locations

- 1.11.2: `ornithe-feather/net/minecraft/entity/Entity.java`, `updateVelocity`, lines 1028–1044; `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative` declaration line 1388, land-helper call line 1456, ordinary tick call line 1803.
- 1.12.0, 1.12.1, 1.12.2: `ornithe-feather/net/minecraft/entity/Entity.java`, `updateVelocity`, lines 1049–1067; `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative` declaration line 1425, land-helper call line 1493, ordinary tick call line 1847.
- Keyboard axis construction is in `ornithe-feather/net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick`, lines 12–50.

This is exact-source applicability evidence. It does not assert runtime parity or an implementation decision.
