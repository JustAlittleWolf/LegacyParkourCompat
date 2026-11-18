# Blind source audit: common dry-air gravity and drag

Pair: Minecraft 1.21.10 → 1.21.11, Mojmap.
Verdict: bounded equality. The ordinary direct local-player dry, non-riding route reaches the same gravity acquisition/gates, vertical update order, and horizontal/vertical drag after its first movement in both versions. The caller's dry-fluid gate is expressed through a new helper in 1.21.11, but it returns false for the stated dry input and selects the same air-travel branch. This is source acceptance for this bounded operation only; it does not establish full-pair coverage or an intermediate patch boundary.

## Exact source identity and consumed bytes

Both canonical trees were read-only. In each `mojmap.ready.json`, `status=ready`, `versionId` matched the exact directory and `mapping=mojmap`; the associated provenance reports the same version and namespace. The ready marker's source-manifest SHA-256 and artifact-manifest SHA-256 matched the actual manifest bytes. Each consumed Java file's SHA-256 matched its entry in that version's source manifest.

| Version | Client input SHA-256 from ready marker | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|
| 1.21.10 | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 1.21.11 | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |

Consumed source-file SHA-256 (relative to each version's `mojmap/`):

| Source | 1.21.10 | 1.21.11 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |

The source IDs were independently corroborated by the version roots' ready/provenance records and success/diagnostic markers. This audit consumed only those exact sources and metadata; no product implementation, Git history, wiki material, or network source was consulted.

## Reachable bounded path

- `LocalPlayer.aiStep` calls `super.aiStep` (1.21.10 `LocalPlayer.java:834`; 1.21.11 `LocalPlayer.java:875`). `LivingEntity.aiStep` reaches `travel(input)` when there is no controlling passenger and movement simulation / effective-AI gates pass (1.21.10 `LivingEntity.java:2845–2849`; 1.21.11 `LivingEntity.java:2971–2975`).
- For the requested non-riding, non-swimming, non-flying player case, `Player.travel` delegates to `super.travel` (1.21.10 `Player.java:1295–1318`; 1.21.11 `Player.java:1360–1383`). The swimming and ability-flight branches are outside this assignment.
- `LivingEntity.travel` dispatches fluid movement first, then fall-flying, otherwise air movement (1.21.10 `LivingEntity.java:2275–2283`; 1.21.11 `LivingEntity.java:2309–2318`). On the stated dry, non-fall-flying input the air branch is selected. In 1.21.10 the fluid condition is inline; in 1.21.11 it calls `shouldTravelInFluid`, whose predicate is the same water-or-lava / affected-by-fluids / cannot-stand-on-fluid condition. For dry input it is false.

## Bounded operation and equality evidence

Exact extracted method bodies were compared across releases. These paired queries returned equal source text: `Entity.getGravity`, `Entity.applyGravity`, `LivingEntity.getDefaultGravity`, `LivingEntity.getEffectiveGravity`, `LivingEntity.travelInAir`, `LivingEntity.handleRelativeFrictionAndCalculateMovement`, `LivingEntity.travel`, and `Player.travel`. `Entity.applyGravity` is recorded as an equal common helper; the direct `LivingEntity.travelInAir` path applies gravity using `getEffectiveGravity` after the move, rather than calling that helper.

For the direct air route, `LivingEntity.travelInAir` first obtains below-block friction when grounded (otherwise `1.0F`), derives horizontal drag as friction × `0.91F`, then calls `handleRelativeFrictionAndCalculateMovement`. That helper applies relative movement and performs the first `move(SELF, deltaMovement)` before returning the resulting velocity. The air method then captures that post-move vector and initializes vertical `y` from its Y component.

Vertical precedence and gates are equal in both versions:

1. If Levitation is present, add `(0.05 * (amplifier + 1) - postMoveY) * 0.2` to the captured post-move Y. This branch bypasses gravity.
2. Otherwise, on a logical server or when the client has the below-movement block's chunk, subtract `getEffectiveGravity()`.
3. Otherwise, for an unloaded client chunk, use `-0.1` when entity Y is above minimum build height; at or below minimum build height use `0.0`.

`Entity.getGravity` returns `0.0` under `isNoGravity`; otherwise it calls the override. `LivingEntity.getDefaultGravity` reads `Attributes.GRAVITY`. `LivingEntity.getEffectiveGravity` caps gravity with `Math.min(getGravity(), 0.01)` only when current delta Y is nonpositive and Slow Falling is active; otherwise it returns `getGravity()`. Since this is queried after the first move, the slow-falling sign check sees the current post-move entity delta. NoGravity therefore suppresses the gravity contribution through the same common getter; Levitation still has precedence over that contribution.

After selecting vertical Y, `LivingEntity.shouldDiscardFriction()` returns the same `discardFriction` field in both versions. If true, `travelInAir` writes the captured horizontal components and chosen Y unchanged. Otherwise it multiplies X/Z by horizontal drag and Y by `0.98F` for a player (the other `FlyingAnimal` multiplier does not apply). `travelInAir`, its movement helper, the friction-discard getter, both gravity getters, and the direct player travel wrapper are text-equal between the pinned source trees, so constants, casts, branch order, and assignments are unchanged in this bounded path.

## Boundary

This freeze excludes acceleration/input calculation, jump behavior, swimming/fluid travel, climbing, flight/fall-flying, rotation, sprint/sneak rules, riding, external velocity changes, collision geometry, and unrelated velocity snaps/resets. The below-block position and block friction are route inputs; their provider/geometry behavior was not audited. Unloaded-chunk behavior above is included because it gates the ordinary client's gravity application.