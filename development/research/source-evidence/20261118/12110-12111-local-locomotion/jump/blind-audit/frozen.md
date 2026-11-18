# Blind source audit: local ground jump, 1.21.10 → 1.21.11

## Scope and verdict

Exact Mojmap source comparison for ordinary dry, non-riding local-player ground-jump eligibility and input scheduling, the intrinsic jump delay, jump-power/effect calculation, vertical and sprint impulse writes, and handoff into travel. Sprint state is held equal; sprint/sneak trigger timers are outside the comparison. Fluids, rotation-dependent outcomes, Elytra/abilities branches, climbing, vehicles, pose and external responses, and support/collision-provider implementation are excluded.

**Verdict:** For the bounded ordinary dry, non-riding path with matching input, grounded state, jump attributes/effect, block-jump factor, prior velocity, and sprint state, the eligibility/timing and position/velocity calculations are equivalent in these endpoints. The sources differ in a non-kinematic bookkeeping write at the end of `LivingEntity.jumpFromGround`: 1.21.10 sets `hasImpulse = true`; 1.21.11 sets `needsSync = true`. That difference does not alter the jump velocity written by this method or the subsequent travel input. This endpoint comparison does not prove the exact intermediate patch where it changed.

## Source identity and integrity

Both exact-version trees are ready Mojmap decompilations. Their ready markers report matching `versionId` and `versionMetadataId`, Vineflower, Java 25, and 6,386 / 6,622 source files respectively. Provenance records the same exact decompilation run (`1.21.8,1.21.10,1.21.11`, `--mappings=mojmap`, Vineflower 1.12.0, mapping-io 0.9.1, Tiny Remapper 0.14.1, Java 25.0.3+9-LTS, Gradle 9.7.1); each success log says build successful and exact metadata ID verified. Each marker's source-manifest, artifact-manifest, and diagnostic digests match the SHA-256 of the corresponding files on disk. Artifact manifest has 257 entries per release.

| Release | Ready marker `clientSha256` | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
|---|---|---|---|---|
| 1.21.10 Mojmap | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `5642b893edbb01bc1dd57386e013c61572c20f75fd25542877bace1f1f457eb0` |
| 1.21.11 Mojmap | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `a97183dfdbeb5000aac0c66aaed9bb85aa2f2655594e5e4dece254a7c46a13aa` |

The compared body files are intact: each full-file SHA-256 below matches its entry in that release's `mojmap.sources.sha256`. These full-file hashes are the used-body integrity identities; source line numbers below identify the reviewed members.

| Relative Mojmap source | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/world/entity/player/Input.java` | `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` | `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` |

## Compared path

- `LocalPlayer.aiStep`: 1.21.10 `net/minecraft/client/player/LocalPlayer.java:697-713,750-771`; 1.21.11 same member at `:738-754,791-813`. Both snapshot the jump key, tick input, then apply auto-jump via `makeJump()` when its countdown is positive. The ordinary non-ability path passes the held jump state onward. Version changes in the ability/spectator, vehicle, and sprint-start gates are outside the assigned path.
- `Player.aiStep`: 1.21.10 `net/minecraft/world/entity/player/Player.java:455-466`; 1.21.11 `:452-463`. Both decrement `jumpTriggerTime` if positive, then call `super.aiStep()` in the same order. This double-tap/flying timer does not gate the ordinary survival jump path.
- `LivingEntity.aiStep`: 1.21.10 `net/minecraft/world/entity/LivingEntity.java:2751-2754,2794-2834`; 1.21.11 `:2877-2880,2920-2960` (relevant jump block at `:2932-2957`). Both decrement positive `noJumpDelay` before input/jump handling. With dry ordinary ground conditions, held `jumping` and `isAffectedByFluids()` enter the shared ground branch; `onGround()` and `noJumpDelay == 0` call `jumpFromGround()` and then set the delay to 10. If not jumping/affected by fluids, both reset this delay to zero. The travel section follows this block in both bodies.
- `LivingEntity.getJumpPower(float)` / `getJumpBoostPower` / `jumpFromGround`: 1.21.10 `net/minecraft/world/entity/LivingEntity.java:2222-2247`; 1.21.11 `:2256-2281`. Expression/order/types are identical: cast attribute value to `float`, multiply by `float` scale, multiply by block jump factor, then add jump-boost power; effect boost is `0.1F * (amplifier + 1.0F)` or `0.0F`. A power not greater than `1.0E-5F` skips all writes. Otherwise preserve X/Z and set Y to `Math.max(power, oldY)`; if sprinting, add the same `Vec3` horizontal impulse `(-sin(yawRadians) * 0.2, 0.0, cos(yawRadians) * 0.2)`; then set a bookkeeping flag. The first assignments and sprint impulse have identical order and numeric operations.
- `LivingEntity.aiStep` calls `jumpFromGround()` before the `travel` phase in both releases. The computed delta movement is therefore the velocity consumed by travel on that tick, subject to the held-equal and excluded inputs above.

## Bounded coverage and exclusions

Compared: client jump-key snapshot/auto-jump scheduling; player jump-trigger countdown; dry ground-jump gate and ten-tick intrinsic delay; base/effect power expression, threshold, vertical max and sprint horizontal impulse; call order into travel. `Input.java` hash is identical; `LocalPlayer.applyInput` routes the current key-press jump bit to `jumping` in both versions. No other divergence was found in these reviewed bounded operations.

Not examined as implementation candidates: fluid jumping and fluid thresholds; spectator/flying toggle; mayfly/ability cases; vehicles; Elytra; climbable/fall-flying; sprint and sneak timer behavior; yaw/rotation effects; collision/support queries and block-jump-factor provider; pose; external velocity responses; subsequent travel math beyond confirming it consumes the already-written velocity. These are excluded from this assignment or held as equal inputs.
