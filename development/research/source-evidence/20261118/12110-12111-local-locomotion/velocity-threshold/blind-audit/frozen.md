# Frozen blind audit: ordinary local velocity threshold and reset writers

## Scope and result

Pair: Minecraft 1.21.10 → 1.21.11, exact Mojmap decompilations. Scope is the ordered velocity threshold/component snap gates on the ordinary dry, non-riding LocalPlayer tick, plus velocity reset writers from that point through the end of the tick. The local tick must be active (`LocalPlayer.tick` passes its `hasClientLoaded()` gate). This audit is source-only.

**Result:** The pre-travel snap gates and their ordering are unchanged. The player horizontal gate is the strict predicate `horizontalDistanceSqr() < 9.0E-6`; when true it zeros both X and Z. This is a squared horizontal-magnitude threshold, not two independent component thresholds. Independently, `abs(Y) < 0.003) zeros Y. Equality does not snap. For non-player LivingEntity, X and Z instead use independent `abs(component) < 0.003` gates; that branch is not the player path.

Both versions execute this write in `LivingEntity.aiStep` after equipment ticking and before `applyInput`, jump handling, and `travel`. Thus it snaps the velocity carried into travel, not the resulting velocity at the end of travel. For a bounded equivalence claim, assume finite velocity and finite vanilla movement inputs/attributes at tick entry; the ordinary dry path preserves that finite domain. Under that bound the reset setter accepts the same values in both releases and the snap result is identical.

One adjacent source change is recorded as a boundary: 1.21.10''s `Entity.setDeltaMovement(Vec3)` unconditionally assigns the vector; 1.21.11 assigns only if `Vec3.isFinite()`. A non-finite vector therefore distinguishes the setter and could prevent a component reset from being committed. No reachable non-finite witness was found within the bounded ordinary dry local path, so this does not falsify the finite-domain equivalence result. Do not generalize that result to arbitrary externally supplied, effect-produced, or corrupted velocity.

## Ordered path and later writers

1. `LocalPlayer.tick` runs only after `connection.hasClientLoaded()`, then calls its superclass tick chain. `AbstractClientPlayer.aiStep` and `Player.aiStep` reach `LivingEntity.aiStep`; the latter performs the snap above before travel.
2. Ordinary dry `LivingEntity.travel` performs the movement step. Gravity, drag, jump, effects, pose handling, and collision/shape providers or clip results are outside this audit''s comparison.
3. During travel, `Entity.move` can zero a stuck entity''s complete velocity when `stuckSpeedMultiplier.lengthSqr() > 1.0E-7`. After clipping, on a horizontal collision it zeros X and/or Z only where the requested component differs from the clipped component by `Mth.equal`; Y is preserved by that write. It then runs `updateEntityMovementAfterFallOn` when requested Y differs from clipped Y, and applies the horizontal block speed factor. These are travel/collision or block-response writers, not a second threshold snap. Their order and source are equivalent in the compared pair.
4. After travel returns, `LivingEntity.aiStep` applies block effects where authorized, updates animation, handles server-only freezing, then spin-attack and entity-push paths before returning. Effects and attack/entity pushes are excluded. `Player.aiStep` then performs its post-super work, including entity interaction pushes; `LocalPlayer.aiStep` handles its ability-flight state. Those impulses/flight paths are excluded.
5. `LivingEntity.tick` finishes its remaining rotation/head-turn bookkeeping; `LocalPlayer.tick` then sends movement/input packets. No further ordinary dry local velocity threshold snap occurs before tick end.

The inspected collision movement and post-travel call ordering match across the pair. No claim is made about the excluded paths or about other entity types.

## Exact input and source identity

The canonical source roots are `decompiled_minecraft/1.21.10/mojmap/` and `decompiled_minecraft/1.21.11/mojmap/`. Both ready markers say status `ready`, exact version IDs match their directory, and mapping namespace is `mojmap`. Provenance records show the exact 1.21.8, 1.21.10, 1.21.11 batch with mapping option `mojmap`, Vineflower 1.12.0 and Java 25. The ready source-manifest and artifact-manifest digests were recomputed and match the markers.

| Release | Client SHA-256 | Mojmap client SHA-256 | Official client mappings SHA-256 | Ready source-manifest SHA-256 | Ready-marker SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|---|---|
| 1.21.10 | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | `0885181c5e4c2f21dd2f95591dd095fa0176807edbe239ef77218e861f0751da` | `2a6f53f4c1fd048e8fa956e3a3fbbb0afc02d5bbc23e52a16b328aed61b9bf39` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0` | `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c` |
| 1.21.11 | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `7055b6a734a8f9f0d80229c2438ba12d10952a57c3795f32b8611020b34eb89a` | `517799a8485e107e932dc1bd27c002b2d0b9207eb2396b685bcfe6c3321a9fbd` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` |

The same artifact-manifest SHA-256 is recorded and verified on each side: `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`. Each ready marker reports 6,386 source files for 1.21.10 and 6,622 for 1.21.11, and movement-diagnostics review 9.

SHA-256 source file hashes (each actual file hash matches its line in that release''s `mojmap.sources.sha256`):

| Mojmap source file | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `052e8387ace3ee65ea7456af23b8e9197931e5d5faeda54589166dce60269296` | `afe84ab1491a17aeeccf895ca4ecb17e941b8e517c980e31281b3d463fc593bd` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| `net/minecraft/world/entity/Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |

Primary compared members: `LocalPlayer.tick`/`aiStep`; `AbstractClientPlayer.tick`/`aiStep`; `Player.tick`/`aiStep`; `LivingEntity.tick`/`aiStep`/`travel`; `Entity.move`/`setDeltaMovement`. The critical ranges are `LivingEntity.aiStep` (1.21.10 lines 2751–2791 and 2834–2856; 1.21.11 lines 2877–2917 and 2964–2989), `Entity.move` (1.21.10 lines 665–779; 1.21.11 lines 685–779), and `Entity.setDeltaMovement(Vec3)` (1.21.10 lines 3619–3621; 1.21.11 lines 3643–3647).

No product, Git, wiki, implementation, backlog, build, test, or game-launch material was consulted. No source was changed.

