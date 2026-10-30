# Attached firework hit gate and player boost route

## Assignment and conclusion

Compared the attached-firework route identified in `.native-source/20261030/1171-1182-finish43/route_open_world_entities/route-audit.md`, using only that source-only report and the exact Mojmap source trees it identifies. Scope here is the `Entity.getHandHoldingItemAngle(Items.FIREWORK_ROCKET)` anchor, `FireworkRocketEntity.tick` hit query and callbacks/persistence, and the direct attached-player velocity response.

**Disposition:** the hit result is not source-equivalent. In 1.18.2 the rocket hit query starts from a nonzero item-hand offset when attached to a fall-flying player; the 1.17.1, 1.18, and 1.18.1 query starts at the player origin. The same source-level hit algorithm receives different segment endpoints and can return a different block/entity hit. This is a changed non-player projectile hit test, which project scope excludes. The direct player boost itself is unchanged and runs before the hit query. For equal attached-player/rocket state on each tick, the client-side hit callbacks do not discard the rocket or clear its attachment, so the changed hit result alone does not change whether the next local boost executes. Server-side explosion/removal and received entity state are external event/state producers, not claimed equivalent under this equal-input comparison.

No trajectory, scheduling, packet, or runtime behavior is inferred.

## Exact source identities

All inputs are read-only Mojmap decompiled sources under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. Tree SHA-256 identities (from the supplied route audit):

| Release | Mojmap tree SHA-256 |
|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |

Relevant file SHA-256 values:

| Release | `Entity.java` | `FireworkRocketEntity.java` | `ProjectileUtil.java` | `Projectile.java` |
|---|---|---|---|---|
| 1.17.1 | `AB28E1FBA924771EC048140DFD293EE5A46A7DFE81F71A1A0B1AECC1927232DE` | `76B6596B47F9A42B6968578F1A2336F901267606678717B088AA8E259BA1CD68` | `A1D11AE47F5B647701E6B462CE36E1D9D87EF5B5EF8693A43473201D50187B56` | `B4B6DC80C7853A0ACFD97EFCEFC068646CAC6C9DEFF05EA01E2671E8FA59BD8B` |
| 1.18 | `69417675AE8E0A5BD88B7372BAEA1E73B1F5217154F39446C8AD5C92731437F1` | `2F4CBF8C80BA37858EFAFED13A9EDB6FF1CD3E6EAF05364604C6D475DBFBD96B` | `1F5199FA5A155D528C2D31F3B96F3EC36561CA522D76AE8F1A81BA1852ED28EA` | `6DAFA259657D5A002BCAACB26E95689CE49F0133547C7758D1E3444835673AA4` |
| 1.18.1 | `69417675AE8E0A5BD88B7372BAEA1E73B1F5217154F39446C8AD5C92731437F1` | `2F4CBF8C80BA37858EFAFED13A9EDB6FF1CD3E6EAF05364604C6D475DBFBD96B` | `1F5199FA5A155D528C2D31F3B96F3EC36561CA522D76AE8F1A81BA1852ED28EA` | `6DAFA259657D5A002BCAACB26E95689CE49F0133547C7758D1E3444835673AA4` |
| 1.18.2 | `2228FDACA5793171CBD94038306D571A6ADA78CA96F5734EFB4CADA5B744C10A` | `BBECE25FBBA0DB7536138627C88AED68BA2C62371943B5E685610A926A1DC3CF` | `1F5199FA5A155D528C2D31F3B96F3EC36561CA522D76AE8F1A81BA1852ED28EA` | `6DAFA259657D5A002BCAACB26E95689CE49F0133547C7758D1E3444835673AA4` |

## Call-path evidence

### Anchor and direct player response

- 1.17.1 `FireworkRocketEntity.tick`, `:111–129`; 1.18 and 1.18.1 `:111–127`: if attached entity is fall-flying, compute and add the same three velocity components in order: `look.x * 0.1 + (look.x * 1.5 - velocity.x) * 0.5`, then corresponding y and z components. Then set rocket position to attached entity `(x,y,z)` and copy its updated velocity.
- 1.18.2 `FireworkRocketEntity.tick`, `:111–131`: same fall-flying gate and same velocity expression/order. It then obtains `getHandHoldingItemAngle(Items.FIREWORK_ROCKET)` and positions the rocket at player `(x,y,z) + angle`; non-fall-flying attachment uses `Vec3.ZERO`.
- 1.18.2 `Entity.getHandHoldingItemAngle(Item)`, `:1820–1827`: non-`Player` returns `Vec3.ZERO`; for `Player`, choose opposite main arm only when offhand has the queried item and main hand does not, otherwise main arm. Return `calculateViewVector(0.0F, getYRot() + (right ? 80 : -80)).scale(0.5)`. This is nonzero for ordinary finite yaw and either arm. `FireworkRocketEntity` calls it only in the attached-player fall-flying branch relevant to boost.
- Across all four versions, the hit query is after that velocity write and anchor assignment (`ProjectileUtil.getHitResult` immediately follows the attached/non-attached branch). Therefore the immediate direct player velocity write precedes and does not depend on the hit result.

### Concrete source-reachable changed hit

Hold equal inputs with an attached fall-flying player at `(0.8, 0.5, 0.5)`, yaw `0`, main arm right, firework rocket in main hand only, and pre-boost x velocity `0.6` (other components can be zero). The boost expression with yaw-zero look x `0` writes x velocity `0.3` in both implementations. Take equal world collider input with one full cube at block `(1,0,0)` and air along the remaining segment.

- In 1.17.1/1.18/1.18.1, rocket position is the player origin, so the block clip runs from x `0.8` toward x `1.1` at y `0.5`, z `0.5` and reaches the cube at x `1`.
- In 1.18.2, `calculateViewVector(0,80).scale(0.5)` has x approximately `-0.4924`, so the query starts near x `0.3076` and ends near x `0.6076` with the same x velocity `0.3`; it does not reach that cube.

The result is a block hit in the earlier anchor case and a miss in the 1.18.2 anchor case, with equal player state before the tick, equal post-boost velocity, and equal world collision input. The example isolates the block clip; candidate entity filtering is not needed to establish the difference. This is a hit-query difference, not a trajectory claim.

### Hit-test and continuation gates

- `ProjectileUtil.getHitResult`, all versions `:22–40` (1.17.1) / `:22–38` (1.18+): get entity velocity and position; clip `position` to `position + deltaMovement` against collider blocks/no fluids; replace segment endpoint with block hit location; query entities using the expanded projectile box and the same predicate; an entity hit replaces the block/miss result. Body and operation order match across all four versions; only decompiler local names/line count differ.
- `ProjectileUtil.getEntityHitResult`, all versions `:43–77` (1.17.1) / `:41–75` (1.18+): same candidate enumeration, pick-radius expansion, segment clipping, inside-box handling, squared-distance comparison, same-root-vehicle handling, and nearest-candidate return. The downstream entity query can also differ because the projectile box follows the shifted rocket position, but no separate entity example is needed for the established block case.
- `Projectile.canHitEntity`, all versions `:172–177` (1.17.1) / `:170–175` (1.18+): same spectator/alive/pickable checks and owner/same-vehicle gate.
- `Projectile.onHit`, all versions `:138–149` (1.17.1) / `:136–147` (1.18+): dispatch entity/block result to corresponding callback and emit projectile-land game event for any non-miss; does not itself discard the projectile.
- `FireworkRocketEntity.onHitEntity`, `:179–184` (1.17.1), `:177–182` (1.18/1.18.1), `:181–186` (1.18.2): calls empty `Projectile.onHitEntity`; explosion only when `!level.isClientSide`.
- `FireworkRocketEntity.onHitBlock`, `:187–195` (1.17.1), `:185–193` (1.18/1.18.1), `:189–197` (1.18.2): invokes the struck block state's `entityInside`, then explodes only when not client-side and the rocket has explosion data, then invokes `Projectile.onHitBlock` (`BlockState.onProjectileHit`). `hasExplosion`, `:197–202` / `:195–200` / `:199–204`, only gates this server-side explosion on a nonempty `Fireworks/Explosions` list. The actual `explode` routine discards after event/damage, but its hit-callback call sites are server-gated.
- `FireworkRocketEntity.tick` has no local discard/attachment-clear after the query; its other `explode()` call is the server-only lifetime gate (`!level.isClientSide && life > lifetime`). Consequently a client hit by itself leaves the attached rocket available to the next tick. A block's `entityInside`/`onProjectileHit` implementation is world/block behavior invoked with the rocket, not a direct player response; those block-specific effects were not treated as movement behavior here.

## Scope result and boundary

The 1.18.2 hand anchor is a real, reachable change to a non-player projectile collision query. Its direct player boost formula, gates, and order are unchanged; it executes before query dispatch in every release. Client hit callbacks do not remove the attached rocket, so under equal received attachment/existence state each following client tick reaches the same direct boost response. Entity disappearance, server explosion/removal, block-specific projectile interaction, and event/packet ordering are excluded inputs/producers in this comparison. No additional movement finding is asserted for the hit-result change itself.
