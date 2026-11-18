# Independent source-boundary verdict: swim pitch vertical writer

**Verdict: accepted for the bounded 1.21.10 → 1.21.11 patch-release comparison.** The author report is bound to SHA-256 `5c46483dd693e98f67afc0a746c3da71e07575aa26f81071e8c649627deadd37`. The reported direct `Player.travel` gate/write is unchanged; the reached `Mth.sin` lookup changes the pitch-derived Y input for a valid ordinary swimming player. This establishes the endpoint difference only. It does not establish which intermediate patch introduced it, full-pair coverage, or runtime parity.

## Frozen source identities

Read-only Mojmap trees: `decompiled_minecraft/1.21.10/mojmap` and `decompiled_minecraft/1.21.11/mojmap`.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|
| 1.21.10 | `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0` | `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 1.21.11 | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |

Both ready markers say `ready`, identify the exact version and `mojmap`, and their declared source/artifact manifest hashes match the manifest bytes. They report 6,386 and 6,622 Java files. Provenance identifies Java `25.0.3+9-LTS`, Vineflower `1.12.0`, and the same pinned mapping/decompiler toolchain. For every relied-on Java file, its full source-manifest row matches the SHA-256 of the current file bytes:

| Java source | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `util/Mth.java` | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` | `1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c` |

## Audited path and numeric witness

In both releases, `Player.travel(Vec3)` first calls `super.travel` for passengers. Its ordinary nonpassenger swimming prelude is otherwise the same: read `double lookY = getLookAngle().y`; select `0.085` if `lookY < -0.2`, else `0.06`; then write `deltaMovement.add(0.0, (lookY - deltaY) * factor, 0.0)` when `lookY <= 0.0`, jumping, or the checked head fluid is nonempty. The write precedes inherited travel. Player `isSwimming()` in each version excludes flying and spectator players and then reads the shared swimming flag.

`Entity.getLookAngle()` and `calculateViewVector(float,float)` retain the same expressions in both versions. Pitch radians are computed as a `float`: `xRot * (float)(Math.PI / 180.0)`. The resulting Y component is `-Mth.sin(pitchRadians)`.

The dependency differs. In 1.21.10, `Mth` initializes each 65,536-entry `float[] SIN` slot with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`; `sin(float)` computes a float product `x * 10430.378F`, casts to `int` (truncation toward zero), then masks with `65535`. In 1.21.11, table entries use `(float)Math.sin(i / 10430.378350470453)`; `sin(double)` widens the already-rounded float radian input, multiplies in double by `10430.378350470453`, casts to `long` (truncation toward zero), masks with `65535L`, then casts to `int`. There is one applicable `sin` overload on each side. Both the index arithmetic and table initialization formula changed.

The report's legal witness independently checks out: `pitch = 51.0260009765625F` is within the `Entity.setXRot` clamp of `[-90, 90]`. Float radians are `0.8905717134475708F` on both sides. The 1.21.10 float product selects slot 9289 (`SIN` value `0.7774314880371094F`); the 1.21.11 double product selects slot 9288 (`0.7773711681365967F`). Thus `lookY` is respectively `-0.7774314880371094` and `-0.7773711681365967`. Both select factor `0.085` and satisfy the write gate. From initial Y velocity `0.0`, the pre-travel Y velocities become `-0.0660816764831543` and `-0.06607654929161072`.

This state is reachable with a nonpassenger player actively swimming underwater while sprinting, not flying or spectating; no jump or head fluid is needed because `lookY <= 0`. `Player.travel` then dispatches to `LivingEntity.travel`. With water and fluid effects enabled, the inherited water path calls `move(MoverType.SELF, getDeltaMovement())` before Y drag. Under an unobstructed position and horizontal/zero vertical travel input, the distinct pre-corrected Y velocity is therefore the Y movement supplied to the inherited move. The water path then multiplies Y by `0.8F`; because the active swimming witness is sprinting, `getFluidFallingAdjustedMovement` returns that value unchanged. The distinction is a real tick displacement input, not just a stored velocity difference.

## Boundary and scope

The source pair proves a behavior difference between exact releases 1.21.10 and 1.21.11, attributable on this path to the changed `Mth.sin` numeric procedure. The direct swim writer itself has no source change. Preserve the claim as this bounded operation-specific consequence; do not generalize the helper change to unrelated consumers without their own movement-path evidence. The precise introduction point inside the interval remains unknown. This verdict covers stable patch releases only and makes no prerelease claim.
