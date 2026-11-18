# Source review: passive vertical air integration, 1.21.6 to 1.21.7

## Evidence identity

- 1.21.6 Mojmap manifest: `decompiled_minecraft/1.21.6/mojmap/.decompile-provenance.json`; SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`. Client artifact `https://piston-data.mojang.com/v1/objects/740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar` (SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`); published Mojmap file `https://piston-data.mojang.com/v1/objects/848855615bc81e3db1c85e69b6afb150807a1261/client.txt` (SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`).
- 1.21.7 Mojmap manifest: `decompiled_minecraft/1.21.7/mojmap/.decompile-provenance.json`; SHA-256 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`. Client artifact `https://piston-data.mojang.com/v1/objects/a2db1ea98c37b2d00c83f6867fb8bb581a593e07/client.jar` (SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`); published Mojmap file `https://piston-data.mojang.com/v1/objects/8d83af626cae1865deaf55fbf96934be4886fd45/client.txt` (SHA-256 `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`).
- Both manifest hashes match the assigned identities. For every source file below, actual SHA-256 matched its version manifest. Each paired source file is byte-identical across these releases.

## Bounded comparison and coverage

Reviewed `LivingEntity.aiStep`, `Player.travel`, `LivingEntity.travel` and `travelInAir`, the gravity attribute defaults and effective-gravity path, ordinary fall-distance update/reset, `Entity.move` position/collision bookkeeping, and `LocalPlayer.tick`/`move` override. The compared files and source hashes are:

| Mojmap source file | SHA-256 (both releases) |
|---|---|
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/world/entity/ai/attributes/Attributes.java` | `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020` |

Exact paired line ranges (the displayed bodies are identical at the same ranges in 1.21.6 and 1.21.7):

- `LivingEntity.java:2737-2762`: reads delta movement and zeroes Y only when `abs(vY) < 0.003`, then writes the deadzoned vector. This occurs before travel in the same `aiStep`.
- `LivingEntity.java:2804-2819`: after jump handling, ordinary travel is called only under `canSimulateMovement() && isEffectiveAi()`; passenger control is a separate branch.
- `Player.java:1438-1461`: nonpassenger, nonswimming, nonflying Player delegates to `LivingEntity.travel`; vehicle, swimming and ability flight branches are outside this assignment.
- `LivingEntity.java:2235-2254, 2276-2299`: default gravity comes from `Attributes.GRAVITY`; `travel` gates fluids and fall-flying before the air path. In ordinary air travel the method gets movement-affecting support position and friction, computes the movement vector, takes its Y, then applies levitation or gravity/chunk fallback, followed by the friction decision. With effects excluded and an available client chunk, vertical output is `(movementY - effectiveGravity) * 0.98F` for Player (not a `FlyingAnimal`); `shouldDiscardFriction()` bypasses multiplication. If client chunk is unavailable, output is `-0.1` above minimum build height and `0.0` at/below it. No reassociation or cast/order change occurs. `LivingEntity.getEffectiveGravity` at 2240-2243 selects `min(gravity, 0.01)` only for downward/nonpositive velocity with Slow Falling; that effect is excluded here.
- `Attributes.java:44-46` and `LivingEntity.java:165-167, 317-330, 2235-2243`: gravity attribute default is `0.08`; LivingEntity default base gravity constant is `0.08` and the living attribute supplier includes `GRAVITY`. Attribute modifiers are outside the bounded ordinary default path.
- `Entity.java:663-750, 1304-1317, 1327-1354, 2674-2682`: `move` is where requested displacement is collision-resolved and position is written; authoritative-local movement calls fall bookkeeping with resolved `movement.y`. In dry ordinary falling, negative resolved Y adds `-(float)resolvedY` to float `fallDistance`, and on-ground resets it. Gravity application uses `getGravity` and adds `(0,-gravity,0)` when nonzero. `resetFallDistance` writes `0.0`. Collision/support providers and block responses are boundary exclusions.
- `LocalPlayer.java:197-222, 916-924`: the local tick delegates to `super.tick`; the move override delegates to `Entity.move` and then updates horizontal auto-jump/walk distance.

## Reachability boundary and verdict

The ordinary air travel path demonstrably writes the new Y velocity. However, within the reviewed local-player call chain, `LivingEntity.travelInAir` and its `aiStep` caller contain no `move(MoverType, Vec3)` call; the `LocalPlayer.move` override only delegates if invoked. Thus this bounded slice does not establish which reachable local caller consumes the resulting Y velocity into position. I found no paired source difference in the assigned slices: the listed files are byte-identical across 1.21.6 and 1.21.7, so this is a bounded no-difference result, not a complete pair freeze or a claim that the full position-consumer dependency is closed.

Excluded: status effects (including levitation/slow falling), equipment and their modifiers, fluids, blocks/support/collision providers, powder snow/climbing, jump initiation, glide/flying/passenger paths, horizontal movement, rotation, network presentation, non-player entities, and all other tick areas. No independent difference or canonical finding draft applies to this bounded slice.

