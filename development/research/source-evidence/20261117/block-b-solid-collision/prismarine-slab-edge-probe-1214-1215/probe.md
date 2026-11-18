# Source-only prismarine-slab backoff probe (1.21.4 → 1.21.5)

Scope: exact Mojmap source comparison of ordinary `Player.maybeBackOffFromEdge` / `canFallAtLeast`, testing a bottom prismarine-slab diagonal support projection. No product implementation, wiki, backlog, runtime, build, or game state was consulted or changed.

## Canonical source identity

Read-only source trees: `decompiled_minecraft/1.21.4/mojmap/` and `decompiled_minecraft/1.21.5/mojmap/`.

Both `mojmap.ready.json` files mark the exact releases and namespace ready. Each points to `mojmap.sources.sha256`; its actual SHA-256 equals the ready marker (`1.21.4`: `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; `1.21.5`: `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`). Provenance records the exact Mojmap batch run for `1.21.1,1.21.3,1.21.4,1.21.5`, Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, TinyRemapper 0.14.1, Mapping IO 0.9.1 and ASM 9.10.1. Version metadata IDs and exact client SHA-256s are present in each ready marker.

Checked source file hashes (SHA-256; source manifests independently contain the same paths/hashes):

| Source member | 1.21.4 | 1.21.5 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` |
| `net/minecraft/world/level/block/SlabBlock.java` | `fb9705bd4363ed8c1e318cbf020cb6956abce73c6f413a97be0013f98df70e77` | `d3666004285a0df44a3b20244d2f474e2feaea78e766b7da1317ca7bd70c8ec8` |
| `net/minecraft/world/level/BlockCollisions.java` | `5c90c246d367284b4fb9038d877e76831f38fdf36196466d3d1c2e0e15392f67` | `e60f4d3cf7537a5e3b8b3ad099a2a7ec167f972460fa01045fd2b2141b6ddaa0` |
| `net/minecraft/world/level/CollisionGetter.java` | `42301653b90eea59caf787a92699bd707d2454155b7aa19e4ff2de57beef774f` | `de8b38cdc04db6f981ab5d3c21cbd965f9abbd17fdcb393d706749f0f65ad920` |
| `net/minecraft/world/phys/shapes/Shapes.java` | `ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75` | `03cd390636f0253b881ddd36c70e61cca2956b02281d45df26c8038ebbdbf63b` |
| `net/minecraft/world/phys/shapes/IndirectMerger.java` | `2e8ae4378fe910dee9eae795add34c23939f716303571cca68af3d2cc0bfc694` | `2e8ae4378fe910dee9eae795add34c23939f716303571cca68af3d2cc0bfc694` |

## Paired Player source

- 1.21.4 `Player.java:1069-1130`: gates and three loops at 1069-1115; `isAboveGround` at 1120-1122; `canFallAtLeast` at 1124-1127.
- 1.21.5 `Player.java:1026-1084`: gates and three loops at 1026-1072; `isAboveGround` at 1077-1079; `canFallAtLeast` at 1081-1084.
- 1.21.4 `Entity.java:647` and 1.21.5 `Entity.java:655` call `maybeBackOffFromEdge` inside ordinary `Entity.move`.

The branch gates are unchanged: not flying; movement Y is not positive; mover is `SELF` or `PLAYER`; `isStayingOnGroundSurface()` (Player returns shift-key state); then `isAboveGround(maxUpStep)`. `onGround()` makes `isAboveGround` true directly. The loop order is X-only, Z-only, then joint X/Z in both versions; each subtracts signed `0.05`, with the `abs(delta) <= 0.05` zeroing rule.

The predicate changed in 1.21.5 from `canFallAtLeast(double dx, double dz, float down)` to `canFallAtLeast(double dx, double dz, double down)`. For 1.21.4 it passes an un-inset X/Z AABB and uses `minY - down - 1.0E-5F`; 1.21.5 constructs `minX + 1.0E-7 + dx`, `maxX - 1.0E-7 + dx`, analogous Z bounds, and `minY - down - 1.0E-7`, with maxY still equal to feet. Thus standing feet exactly at slab top Y=.5 still gives the query a positive vertical prism through the slab volume below; no airborne Y gap is needed.

`isAboveGround` can use actual `onGround()==true`; Player `isStayingOnGroundSurface` is shift key. A feasible supported state has feet Y=.5 on A. The query for A intersects slab interior over Y=[0,.5], and both X/Z support slabs have positive footprint overlap.

## Provider and registration closure

`Blocks.PRISMARINE_SLAB` registers `SlabBlock::new` in both releases (`Blocks.java:3076` / `3152`). The default BOTTOM slab shape is the half-height column: 1.21.4 `SlabBlock.java:35,63-68` (`Block.box(0,0,0,16,8,16)`); 1.21.5 `SlabBlock.java:35,61-64` (`Block.column(16,0,8)`). `BlockBehaviour.getCollisionShape` delegates to the state's shape in both versions. `BlockCollisions.computeNext` gets `CollisionContext.getCollisionShape`, and `CollisionGetter.noCollision(entity, aabb)` asks the block collisions then entity collisions and border collision. The slab shape is not the `Shapes.block()` singleton, so this reaches `Shapes.joinIsNotEmpty(slab, query, AND)` rather than the full-block AABB fast path.

Consequently the witness requires a loaded collision region including all candidate cells (including negative-coordinate padding cells), no additional collision shapes in the swept/query prisms, no other entity intersecting them, and a world border far away. `BlockCollisions` skips absent collision chunks; an unloaded chunk is not a valid witness.

## Joint-loop geometry and exact epsilon result

Use three bottom prismarine slabs only: A at `(0,0,0)`, B at `(-1,0,1)`, C at `(1,0,1)`. For an ordinary player (width `.6F`, half-width `.30000001192092896F`) at center `(0.25, 1.25)` and feet `y=.5`, initial bounds are X `[-.050000011920928955, .550000011920929]`, Z `[.949999988079071,1.550000011920929]`. A supports the X-only projection; B supports the Z-only projection; A/B are both cleared only after a joint shift near `dx=.45,dz=.1`. This is why the simple one-support diagonal probe is rejected: its Z-only loop moves/zeros the Z component before the joint loop.

A literal old-side overlap of `4-5e-8` does **not** distinguish these versions. In `Shapes.joinIsNotEmpty`, `IndirectMerger` merges coordinates within `1e-7`; when the overlap is no more than `1e-7`, the shared interval collapses to zero width and the AND shape is empty. Static index tracing yields the slab/query intersection threshold: old 1.21.4 intersects only when query max X exceeds C's min X by more than `1e-7`; 1.21.5 first shifts query max back `1e-7`, so it intersects only when the old overlap exceeds `2e-7`. Strict inequalities make exact `1e-7` and `2e-7` non-intersections on their respective sides.

There is a real distinguishable overlap interval: `1e-7 < delta <= 2e-7`, where old query intersects C and the inset query's remaining overlap is coalesced. For this witness set `dx=.45000015`: old projected maxX is approximately `1.000000161920929` (overlap `1.6192e-7`); 1.21.5 maxX becomes approximately `1.000000061920929` (remaining overlap `6.1921e-8`). A coordinate scan of the 1D merger gives: `delta=-1.5e-7,-0.75e-7,-0.5e-7,-0.25e-7,0,0.5e-7,1e-7` -> neither; `1.5e-7,2e-7` -> old contact / new no contact; `2.5e-7` -> both contact. The originally proposed `4e-8` overlap is in the neither-contact region for both releases.

With dz=.1, each axis-only loop remains blocked by A/B in both releases. Joint old query remains blocked by C and returns approximately `(.45000015,.1)`. Joint 1.21.5 query clears A/B/C, so the loop subtracts .05 from both. After the first decrement, A query z min is `.999999988079071`, leaving only `1.19e-8` overlap, below the shape-merger threshold; the query still clears. It decrements again and zeros dz, reaching approximately `(.35000015,0)`. At that point A overlaps robustly and stops the loop. The first output delta therefore differs conditionally by about `.10` on X and `.10` on Z. Exact Java floating-point subtraction determines final low bits.
## Reachability and triage

The large delta is not an ordinary default-speed control input. A source-valid map can configure it with a movement-speed attribute: `LocalPlayer.java:602,674-678` defines crouch as slow movement and scales input by `Attributes.SNEAKING_SPEED`; its default is 0.3 (`Attributes.java:75-77`). `Player.java:1492` reads movement speed from the attribute; `Attributes.MOVEMENT_SPEED` is syncable and ranges through 1024 (`Attributes.java:63-66`). Slab `BlockBehaviour.Properties` defaults to friction 0.6 (`BlockBehaviour.java:961`), making `LivingEntity.getFrictionInfluencedSpeed` equal to movement speed because `.21600002F / .6F^3 == 1` (`LivingEntity.java:2407-2409`). `Entity.moveRelative` normalizes a unit input, scales it by this speed and rotates it by player yaw, then adds it to delta movement (`Entity.java:1424-1438`); `LivingEntity.handleRelativeFrictionAndCalculateMovement` calls SELF move with that delta (`LivingEntity.java:2362-2365`). A command-configured speed near 1.537 with crouch input gives horizontal magnitude about .461, and a mild yaw produces components approximately `(.45000015,.1)`. No command or runtime check was executed. This establishes a source-level vanilla map path when such a static attribute is part of the map setup; it is not a claim about default player speed or runtime-tested parity. The initial position remains supported at y=.5 by A/B; shift, onGround, nonflying, nonpositive-Y and SELF gates are consistent.

Verdict: a conditional but source-reachable diagonal movement candidate under explicit map attribute setup. It should not be generalized to unmodified/default player movement.
