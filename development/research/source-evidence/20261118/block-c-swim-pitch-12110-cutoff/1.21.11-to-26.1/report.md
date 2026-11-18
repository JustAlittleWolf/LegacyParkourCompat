# Swim pitch vertical writer: 1.21.11 → 26.1 source boundary

## Verdict

**No change in this bounded operation.** For every legal pitch input to the voluntary swim writer, 1.21.11 and 26.1 produce the same look-Y value, evaluate the same writer gates, and apply the same delta to vertical velocity. The corresponding source pair does not establish a movement delta here. This is a bounded source result, not complete pair coverage or runtime parity.

## Exact source readiness and identity

The canonical published markers are `decompiled_minecraft/1.21.11/mojmap.ready.json` and `decompiled_minecraft/26.1/unobfuscated.ready.json`; each reports `status: ready` for the exact assigned release and mapping. Their provenance markers identify 1.21.11/Mojmap and 26.1/unobfuscated (26.1 is the selected official version directory; the source is published unobfuscated). The source-manifest and artifact-manifest bytes hash to the SHA-256 values embedded in each ready marker. Diagnostics also match the marker. The relied source files listed below each match the exact entry in the corresponding SHA-256 source manifest.

The recorded raw cache directories are absent, and no matching `client.jar` was found under this checkout's `build` tree. That is a raw-input-cache observation, not a source-readiness failure: both ready markers and source manifests are present and validate the decompiled files consumed here. No sources were generated or modified.

| Published evidence | 1.21.11 / mojmap | 26.1 / unobfuscated |
|---|---|---|
| provenance JSON SHA-256 | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b` |
| ready JSON SHA-256 | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `954126940593c1e1e4b987bfd7079452e0d6283c3d1d97a02693f0319f66f0f5` |
| source-manifest SHA-256 | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` |
| artifact-manifest SHA-256 | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0` |
| movement-diagnostics SHA-256 | `a97183dfdbeb5000aac0c66aaed9bb85aa2f2655594e5e4dece254a7c46a13aa` | `6590c89a7d3baf27490ce024d49519ed0dd4ee4e0cc4369c3f419588b15312da` |
| ready-marker client SHA-256 | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` |

## Source trace and comparison

The active operation is `Player.travel(Vec3)`. The client `LocalPlayer` has no `travel` declaration in either exact source file and inherits `Player.travel`.

- 1.21.11 Mojmap `net/minecraft/world/entity/player/Player.java`, lines 1359–1383: passengers immediately delegate to `super.travel`. Otherwise the swimming branch reads `double lookY = getLookAngle().y`, chooses `0.085` when `lookY < -0.2`, else `0.06`, then, only if `lookY <= 0.0 || jumping || upper-fluid-state-nonempty`, reads the movement vector and writes `movement.add(0.0, (lookY - movement.y) * multiplier, 0.0)`. The flying/non-flying `super.travel` call follows.
- 26.1 unobfuscated `net/minecraft/world/entity/player/Player.java`, lines 1380–1404: the same passenger bypass, swim read, thresholds, gate order, vector read, double arithmetic, `Vec3.add` write, and later `super.travel` branches. Changes in local names, formatting, and explicit parameter `final` do not alter the operation.

`getLookAngle()` uses the current pitch and yaw (`Entity.java`: 1.21.11 lines 2448–2450; 26.1 lines 2512–2514), then `calculateViewVector` computes:

1. `realXRot = xRot * (float)(Math.PI / 180.0)` — a float multiplication after the degree-to-radian constant is narrowed to float.
2. `xSin = Mth.sin(realXRot)` — float argument widened to the `double` parameter.
3. look-Y is `-xSin`, a float negation widened into the `double` Y component of `Vec3`.

The corresponding source is identical across releases (`Entity.java`: 1.21.11 lines 1828–1836; 26.1 lines 1879–1887). Both `Mth` files (1.21.11 lines 30–55; 26.1 lines 30–54) initialize the same `float[65536]` sine table with `(float)Math.sin(i / 10430.378350470453)`. Both calculate the index with the same expression: `(int)((long)(angle * 10430.378350470453) & 65535L)`. Thus the product is double; conversion to `long` truncates toward zero; masking is 16-bit (`65535L`); conversion to array index is `int`; the selected table value is `float`. The scale literal, table width, mask, casts, table-entry expression, and ordering are unchanged. Y uses only pitch's sine result; yaw-dependent sine/cosine terms feed X/Z, not Y. The entity rotation setter clamps legal pitch to `[-90, 90]` in both releases (`Entity.java`: 1.21.11 lines 3805–3813; 26.1 lines 3834–3842). Since every relevant operation and table input path is identical, there is no legal pitch input for which this pair changes look-Y.

## Downstream player movement

The swim writer stores a new `deltaMovement.y` before `super.travel(input)`. `LivingEntity.travel` selects the current movement branch (1.21.11 `LivingEntity.java` lines 2309–2317; 26.1 lines 2392–2400). On the water path, `travelInWater` calls `moveRelative`, then calls `move(MoverType.SELF, getDeltaMovement())` (1.21.11 lines 2380–2405; 26.1 lines 2467–2493). Therefore the vertical writer's value is carried into the real player move request; subsequent vanilla collision/fluid handling may change the resulting position or velocity, but those downstream behaviors are outside this bounded comparison. No difference was found in this source path that could alter the requested vertical movement.

## Reliance hashes

Each hash below is the exact file SHA-256 and equals that file's entry in the source manifest identified above.

| Relative source file | 1.21.11 / mojmap SHA-256 | 26.1 / unobfuscated SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/util/Mth.java` | `1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c` | `19d00e9745368c34692d0dfd7eddec07797790ac7f6e0bc579208b7a4b682d33` |
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |

No build, tests, gameplay/TAS/Gym/server launch, network access, decompilation, or Git operation was used.

