# Independent bounded audit verdict: Nether portal contact eligibility

## Verdict

**ACCEPT the bounded player contact and local-timer conclusion, with a source-provenance caveat.** The source bytes inspected match the relevant Java-file hashes listed in the frozen comparison, and the compared expressions support its player-specific eligibility conclusion. However, the canonical source directories contain no `.decompile-provenance.json` (or readiness marker at the mapping root), so the comparison's stated completed-run provenance cannot be independently confirmed from this checkout. Do not treat the provenance paragraph as verified by this audit.

This verdict is bound to:

- Frozen source roster: `source-roster.md`, SHA-256 `4353085651f4d2045dd9757288db7ce5b2b8203622d72eb8431e5120531b5a4f`.
- Candidate comparison: `lane-early-nether-portal-eligibility/comparison.md`, SHA-256 `491090e4413bbe23db77da009224b57e053558b19f77e6792c8044766e7b0a2d`.
- Exact releases/mapping: 1.8.9 → 1.9.4, Ornithe Feather. Terminal file SHA-256 values are in the frozen roster.

## Independently checked claims

1. **Body contact is reachable.** In each version, entity collision processing calls `checkBlockCollisions()`. That scans block positions overlapped by the entity shape (with 0.001 inset at each bound) and dispatches each block's `onEntityCollision`. Thus a player whose AABB overlaps a Nether portal block can reach `PortalBlock.onEntityCollision`; the callback is not dead code or limited to server-only calls.
2. **Vehicle/passenger eligibility is player-equivalent at these endpoints.** In 1.8.9 the portal callback requires `vehicle == null && rider == null`. `Entity.isRiding()` means `vehicle != null`; the second field is the current entity's direct passenger. In 1.9.4 the callback requires `!isRiding() && !hasPassengers()`, where those predicates mean a null mount and an empty passenger list. The normal player mount/passenger relations are the same eligibility states across the two representations. The different API shape does not establish a player contact difference.
3. **The added portal-use predicate admits players.** 1.9.4 `Entity.canUsePortals()` returns `true`. `PlayerEntity` extends `LivingEntity`, which extends `Entity`; the client player classes extend `PlayerEntity`. No player/living/client-player override exists in the exact source trees. The only 1.9.4 overrides found in entity sources are Wither and Ender Dragon, not players. Thus the new predicate does not make a player ineligible.
4. **Local contact/cooldown behavior is unchanged in the inspected routines.** In both releases, `Entity.onPortalCollision` refreshes an active `portalCooldown` from `getPortalCooldown()` and does not set `inPortal`; without an active cooldown, it sets `inPortal = true`. The shape-position bookkeeping is server-only. In both local-player `mobTick` procedures, `portalTime` advances by `0.0125F` up to `1.0F` when `inPortal`, then clears `inPortal`; absent contact, nausea can add `0.006666667F`, otherwise the timer decays by `0.05F` and floors at zero. Local `portalCooldown` decrements once per local-player tick. The local player cooldown override is 10 in both versions. Continuous portal contact while cooldown is positive refreshes it, so the callback does not set `inPortal` during that contact. These timer fields and branches do not write player position, velocity, or collision shape.
5. **Server progression differs only in the noted invulnerability threshold within this bounded gate.** Both `Entity.baseTick` blocks run portal progression only under `!world.isClient && world instanceof ServerWorld`. Both suppress `portalTime++` while the player is mounted, then use postfix `portalTime++ >= maxTime`. The player threshold is 80 for ordinary players in both versions; for invulnerable players it changes from 0 (1.8.9) to 1 (1.9.4). From `portalTime == 0`, the old gate compares 0 >= 0 and transfers on its first eligible portal tick; the new gate compares 0 >= 1 (false), increments to 1, then transfers on the next eligible tick when 1 >= 1. Both clamp `portalTime` back to the threshold before transfer. This is server-only dimension-transfer timing, not a local-player movement response. It does not establish the candidate contact-eligibility operation as a movement change.

## Exact source slices

Excerpts below are verbatim from the SHA-bound source files listed in `source-roster.md`.

### Portal contact dispatch

`1.8.9/.../PortalBlock.java:162-165`

```java
public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
    if (entity.vehicle == null && entity.rider == null) {
        entity.onPortalCollision(pos);
    }
}
```

`1.9.4/.../PortalBlock.java:170-173`

```java
public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
    if (!entity.isRiding() && !entity.hasPassengers() && entity.canUsePortals()) {
        entity.onPortalCollision(pos);
    }
}
```

### Entity portal relation predicates

`1.8.9/.../Entity.java:1448-1450`

```java
public boolean isRiding() {
    return this.vehicle != null;
}
```

`1.9.4/.../Entity.java:1597-1603, 1895-1897`

```java
public boolean isRiding() {
    return this.getMount() != null;
}

public boolean hasPassengers() {
    return !this.getPassengers().isEmpty();
}

public boolean canUsePortals() {
    return true;
}
```

The mount/passenger accessors bind to the current `mount` and passenger list: `1.9.4/.../Entity.java:2190-2192, 2260-2262`. Player hierarchy: `PlayerEntity extends LivingEntity` in both releases; `LivingEntity extends Entity` in 1.9.4 at `LivingEntity.java:76` (the 1.8.9 declaration is at line 58); `ClientPlayerEntity extends PlayerEntity` and `LocalClientPlayerEntity extends ClientPlayerEntity` in both.

### Collision reachability and callback effects

`1.8.9/.../Entity.java:648-660` and `1.9.4/.../Entity.java:737-751` show `checkBlockCollisions()` enumerating shape-overlapped block coordinates and calling `block.onEntityCollision(...)`. `Entity.java:607-609` and `:691-693`, respectively, call this check from entity movement/collision processing.

`1.8.9/.../Entity.java:1397-1417` and `1.9.4/.../Entity.java:1538-1558` implement the same cooldown branch and `inPortal` write. 1.9.4 copies `BlockPos` before the server-only portal-shape bookkeeping; the changed copy does not affect player movement state.

### Local client timer

`1.8.9/.../LocalClientPlayerEntity.java:500-533` and `1.9.4/.../LocalClientPlayerEntity.java:610-643` contain the matching timer, `inPortal` reset, float increments/clamps, decay, and cooldown decrement. `PlayerEntity.java:293-310` in 1.8.9 and `:312-329` in 1.9.4 provide the max-time and cooldown overrides.

### Server-only max-time progression

`1.8.9/.../Entity.java:261-294` and `1.9.4/.../Entity.java:323-356` show the logical-server gate and postfix counter comparison; `PlayerEntity.java:293-295` and `:312-314` show the threshold change from invulnerable 0 to 1. The separate local-player timer above is not this server integer counter.

## Bounded scope

This audit does not establish intermediate 1.9.0–1.9.3 introduction timing, full-pair tick-graph coverage, runtime parity, portal construction/shape, or dimension-transfer geometry. End portal transfer geometry was not examined. No product source, wiki lane, backlog, tests, build, runtime, source setup, Git history, or network was accessed.