# Dry-land player travel branch comparison

**Scope.** Exact source comparison of the inherited dry-land travel branch and the immediate `Player.travel` wrapper for a non-passenger player. The bounded player case is ordinary land travel where `isSwimming()` is false, `abilities.flying` is false, the in-fluid travel gate is false, and the player is not fall-flying. Grounded versus airborne friction selection is included. Fluid travel, fall-flying, climbing-specific movement, collision resolution/step-up, post-move callbacks, input sampling, and other direct writers are not closed here. This is not a pair freeze or runtime-parity claim.

## Exact source identity

| Release | Namespace and source root | Ready marker / SHA-256 | Provenance / SHA-256 | Source manifest / SHA-256 | Artifact manifest / SHA-256 |
|---|---|---|---|---|---|
| 1.21.11 | Mojmap, `decompiled_minecraft/1.21.11/mojmap` | `decompiled_minecraft/1.21.11/mojmap.ready.json` / `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `decompiled_minecraft/1.21.11/mojmap.provenance.json` / `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `decompiled_minecraft/1.21.11/mojmap.sources.sha256` / `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `decompiled_minecraft/1.21.11/artifacts.sha256` / `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 26.1.2 | Published unobfuscated, `decompiled_minecraft/26.1.2/unobfuscated` | `decompiled_minecraft/26.1.2/unobfuscated.ready.json` / `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` / `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `decompiled_minecraft/26.1.2/unobfuscated.sources.sha256` / `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `decompiled_minecraft/26.1.2/artifacts.sha256` / `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |

Both ready markers declare the stated exact `versionId` and `mapping`; each ready marker's `sourceManifestSha256` equals the listed source-manifest digest. The provenance files independently declare matching version and namespace. The cited source-file SHA-256 values below match their corresponding source-manifest entries.

## Paired operations and result

| Operation | 1.21.11 Mojmap | 26.1.2 unobfuscated | Bounded result |
|---|---|---|---|
| Player wrapper | `world/entity/player/Player.java`, `travel(Vec3)`, lines 1360–1383; `getSpeed()`, 1404–1406 | `world/entity/player/Player.java`, `travel(Vec3)`, lines 1381–1404; `getSpeed()`, 1425–1427 | Same passenger dispatch; same swimming adjustment; same `abilities.flying` test, `super.travel` call, and restoration of Y movement to original Y times `0.6`; same movement-speed attribute cast to `float`. Under the bounded ordinary-land conditions, swimming and flying branches are not taken and wrapper calls `super.travel(input)` directly. |
| Travel branch selection | `world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2309–2317 | `world/entity/LivingEntity.java`, `travel(Vec3)`, lines 2392–2400 | Same priority: fluid predicate, then fall-flying, else `travelInAir(input)`. The assigned dry, non-fall-flying condition selects the same air branch. |
| Dry-land movement / friction | `LivingEntity.java`, `travelInAir(Vec3)`, lines 2343–2366 | `LivingEntity.java`, `travelInAir(Vec3)`, lines 2430–2453 | Same supporting-position selector; `onGround ? block friction : 1.0F`; `friction = blockFriction * 0.91F`; same horizontal relative movement helper; same levitation/gravity selection; same client missing-chunk vertical fallback; same friction-discard gate; same horizontal friction and `FlyingAnimal` versus `0.98F` vertical multiplier. No changed constant, type, order, branch, or write found in the compared bodies. |
| Relative movement and effective ground speed | `LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement`, lines 2534–2544; `getFrictionInfluencedSpeed`, 2578–2580; `Entity.java`, `moveRelative`/`getInputVector`, 1608–1623 | `LivingEntity.java`, `handleRelativeFrictionAndCalculateMovement`, lines 2623–2633; `getFrictionInfluencedSpeed`, 2667–2669; `Entity.java`, `moveRelative`/`getInputVector`, 1659–1674 | Same operation order: ground speed is `getSpeed() * (0.21600002F / (friction³))`, otherwise use `getFlyingSpeed()`; add yaw-rotated, normalized-if-length-squared-over-one input to delta movement; run the same climb helper; move as `SELF`; apply same conditional 0.2 vertical correction; return post-move delta for later air friction. The `Player.getSpeed()` override returns `(float)getAttributeValue(MOVEMENT_SPEED)` in both. |

The source methods express the same arithmetic and gates with renamed locals and formatting changes only. In a grounded ordinary player case, block friction enters the cubic divisor before yaw-rotated input is added and `0.91F` horizontal air friction is applied. In an airborne case, the base-speed selection uses `getFlyingSpeed()` and block friction is `1.0F`. These are source-path descriptions, not trajectory measurements.

## Evidence identities

| Release | Java source | SHA-256 |
|---|---|---|
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.11 | `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 26.1.2 | `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1.2 | `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.1.2 | `decompiled_minecraft/26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |

## Coverage boundary

This bounded slice has no movement difference identified. It does not close caller reachability for every player state, the fluid and fall-flying travel branches, climb-specific effects, block friction values or resource-backed state inputs, collision effects of `move`, other tick stages, or the full pair dependency inventory. Exact patch onset within this broad interval is not asserted.