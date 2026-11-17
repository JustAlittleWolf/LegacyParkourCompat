# Entity contact push: 1.21.11 → 26.1.2

## Verdict

**NO_DIFF within this bounded operation:** the source paths that select an overlapping entity for local-client contact push and add the resulting horizontal impulse to the local player's delta movement retain the same gates, ordering, and arithmetic. Names and source formatting changed; the behavior below did not. This is a scoped result, not a pair-coverage freeze.

## Source identity

Exact ready publications were checked against their per-source manifests, and each cited Java file's SHA-256 matched its manifest row.

| Endpoint | Mapping / source manifest SHA-256 | Artifact manifest SHA-256 | Pinned client jar SHA-256 | Provenance |
|---|---|---|---|---|
| 1.21.11 | Mojmap / `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `decompiled_minecraft/1.21.11/mojmap.provenance.json` |
| 26.1.2 | Unobfuscated / `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` |

The ready descriptors are `decompiled_minecraft/1.21.11/mojmap.ready.json` and `decompiled_minecraft/26.1.2/unobfuscated.ready.json`. Their source roots are `decompiled_minecraft/1.21.11/mojmap/` and `decompiled_minecraft/26.1.2/unobfuscated/`.

## Local-player contact path

1. `LivingEntity.aiStep` invokes `pushEntities()` after its travel/movement portion in both sources (1.21.11 `LivingEntity.java:3007`; 26.1.2 `LivingEntity.java:3106`). `pushEntities` queries `level().getPushableEntities(this, this.getBoundingBox())` and calls `doPush` for each returned entity (1.21.11 `LivingEntity.java:3066-3088`; 26.1.2 `LivingEntity.java:3165-3187`). The base `doPush` calls candidate `.push(this)` (1.21.11 `LivingEntity.java:3115-3117`; 26.1.2 `LivingEntity.java:3214-3216`).
2. In each client level, `getPushableEntities` returns only the current local player, and only when it exists, is not the pusher, intersects the pusher's bounding box, and passes `EntitySelector.pushableBy` (1.21.11 `ClientLevel.java:413-417`; 26.1.2 `ClientLevel.java:424-429`). `LocalPlayer.isLocalPlayer()` returns true (1.21.11 `LocalPlayer.java:359-362`; 26.1.2 `LocalPlayer.java:377-380`). The selector has the same server-or-local-player gate and the same pushability, spectator, team-collision-rule and alliance checks at 1.21.11 `EntitySelector.java:27-54` and 26.1.2 `EntitySelector.java:29-56`.
3. The local player inherits `LivingEntity.isPushable()`, which in both versions requires alive, non-spectator and not on a climbable (1.21.11 `LivingEntity.java:3202-3205`; 26.1.2 `LivingEntity.java:3309-3312`). `LivingEntity.push(Entity)` has the same sleeping guard then delegates to `Entity.push(Entity)` (1.21.11 `LivingEntity.java:2220-2225`; 26.1.2 `LivingEntity.java:2303-2308`).
4. `Entity.push(Entity)` has identical operation order and constants at 1.21.11 `Entity.java:1738-1767` and 26.1.2 `Entity.java:1789-1818`: reject same-vehicle and either-side `noPhysics`; compute horizontal displacement from this entity to the other; use `Mth.absMax`; require `>= 0.01F`; take square root; normalize; cap reciprocal magnitude at 1; scale each horizontal component by `0.05F`; apply negative impulse to this entity if it is not a vehicle and is pushable, then positive impulse to the other under the corresponding gate. For the call above, `this` is the local player, so the first write applies the local player's horizontal response. `Entity.push(double,double,double)` finite-checks each component, adds it to current delta movement, and sets `needsSync` in both versions (1.21.11 `Entity.java:1775-1779`; 26.1.2 `Entity.java:1826-1831`).

This finding is limited to the direct local-player entity-contact push path. It excludes inbound packet knockback/explosion, block collision, vehicle behavior and non-player response. It does not claim full pair coverage; unrelated movement operations and remaining coverage gates still require their own reports.

## Evidence source hashes

Hashes below are exact SHA-256 values verified against each endpoint's `*.sources.sha256`.

| Java source, relative to source root | 1.21.11 SHA-256 | 26.1.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| `net/minecraft/world/entity/EntitySelector.java` | `303f1b9df6a76d7a6b37b1a57e56aca52a487315bc55c956fb73db3a2f33990b` | `0735f67b48018b7fc12737e06085cdf194a138edb75f5f95b83c0a9451be589b` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2` | `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7` |
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/level/Level.java` | `de329fdbeca632a1b63074e1735890b2624abfc57b7a579f43eef36caefef98d` | `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01` |
| `net/minecraft/world/level/EntityGetter.java` | `1c370bf56b66774d6f5161a1844341be93399a8a61862e71a7106a96262b813d` | `e37ea40ed0f9ebb8234addc98a5e05271bfad2fe9ffce5a7babf3bfaaa700d29` |