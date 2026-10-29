# Mojmap-pair correction supplement: 1.14.4 → 1.15 auto-jump

This supplement is bound to the earlier `closure.md` SHA-256 `82b60d6b1007782c822014f6050fa426bd1e00f01885a14a9f886cdfd74973d6`. It does not modify that file. All comparison claims below use only the published Mojmap source pair identified here; no behavioral claim depends on the unprovenanced 1.14.4 Yarn staging tree. The Yarn staging identity limitation remains historical context for the initial pass.

The earlier draft's statement that the 1.15 auto-jump gate reads the shared entity shift flag is corrected. For a `LocalPlayer`, `Player.isStayingOnGroundSurface()` calls virtual `isShiftKeyDown()`, and `LocalPlayer` overrides that method to read `input.shiftKeyDown` directly. The exact local input path closes this predicate.

## Paired evidence identity

| Side | Published source root | Sidecar identity | Source evidence |
|---|---|---|---|
| 1.14.4 | `decompiled_minecraft/1.14.4/mojmap` | `mojmap.provenance.json`: namespace `mojmap`, requested/batch release 1.14.4, exact client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; `mojmap.ready.json`: 3,250 Java files / 17,080,207 bytes, source manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `net/minecraft/client/player/LocalPlayer.java` SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.15 | `decompiled_minecraft/1.15/mojmap` | `mojmap.provenance.json`: requested release 1.15, batch 1.15/1.15.1, client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`; `mojmap.ready.json`: 3,329 Java files / 17,522,461 bytes, source manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `net/minecraft/client/player/LocalPlayer.java` SHA-256 `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |

Recomputed source-manifest file SHA-256 values match the ready markers for both sides. Sidecar SHA-256 values: 1.14.4 provenance `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`, ready `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043`; 1.15 provenance `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`, ready `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31`.

## Reachability through the local player tick

- In both versions, `Minecraft.tick()` calls `level.tickEntities()` while the world is active and unpaused: 1.14.4 `client/Minecraft.java:1237-1309`; 1.15 `client/Minecraft.java:1240-1311`. The 1.14.4 `MultiPlayerLevel.tickEntities()` dispatches regular entities through its player-aware tick path (`client/multiplayer/MultiPlayerLevel.java:110-170`); 1.15 does so in `ClientLevel.tickEntities()` / `tickNonPassenger()` (`client/multiplayer/ClientLevel.java:120-180`). Passenger entities follow their vehicle tick path.
- `LocalPlayer.tick()` checks that its current chunk is available, then calls `super.tick()` in both versions (1.14.4 `client/player/LocalPlayer.java:178-180`; 1.15 `:179-181`). `LivingEntity.tick()` reaches virtual `aiStep()` (1.14.4 `world/entity/LivingEntity.java:1975-2042`; 1.15 `:2024-2103`). Each `LocalPlayer.aiStep()` refreshes `Input` before delegating to inherited AI: 1.14.4 `client/player/LocalPlayer.java:618-629,772`; 1.15 `:624-635,778`. Inherited `LivingEntity.aiStep()` then calls virtual `travel(...)` (1.14.4 `world/entity/LivingEntity.java:2146-2223`; 1.15 `:2207-2287`).
- Normal player travel invokes movement. The local override calls `super.move(...)` and then `updateAutoJump((float)(newX-oldX), (float)(newZ-oldZ))`: 1.14.4 `client/player/LocalPlayer.java:844-850`; 1.15 `:850-856`. The gate therefore sees `onGround` after the superclass movement call. Collision/move processing that produces that value remains outside this closure.
- In each version, local AI decrements positive `autoJumpTime` and sets `input.jumping = true` before inherited travel (1.14.4 `LocalPlayer.java:637-642`; 1.15 `:643-648`). The scan sets the timer to `1` when it accepts a rise (`1.14.4 LocalPlayer.java:946-950`; 1.15 `:950-954`).

## Exact gate comparison

The 1.14.4 body nests checks in this order (`LocalPlayer.updateAutoJump`, lines 856-861): `isAutoJumpEnabled()`, `autoJumpTime <= 0`, `onGround`, `!isSneaking()`, `!isPassenger()`, then a nonzero `input.getMoveVector()` test. In 1.15, `updateAutoJump()` delegates to `canAutoJump()`; its short-circuit order is enabled, timer, on-ground, `!isStayingOnGroundSurface()`, `!isPassenger()`, `isMoving()`, and finally `getBlockJumpFactor() >= 1.0` (`LocalPlayer.java:862-875, 962-975`).

The sneak-related predicate occupies the same gate position in both versions and reads current local input on both sides:

- 1.14.4 `LocalPlayer.isSneaking()` delegates to `isTryingToSneak()`, which returns `input != null && input.sneakKeyDown` (`LocalPlayer.java:583-590`). `aiStep()` calls `input.tick(...)` before travel (`:625-630`); `KeyboardInput.tick()` fills `sneakKeyDown` from `options.keySneak.isDown()` (`KeyboardInput.java:13-24`).
- 1.15 `Player.isStayingOnGroundSurface()` returns `isShiftKeyDown()` (`world/entity/player/Player.java:294-296`). The local override returns `input != null && input.shiftKeyDown` (`client/player/LocalPlayer.java:590-593`); `aiStep()` calls `input.tick(...)` before travel (`:632-636`), and `KeyboardInput.tick()` fills `shiftKeyDown` from `options.keyShift.isDown()` (`KeyboardInput.java:13-25`).

Thus the local-player source path is input-key state in both versions; the method and field symbols change. The gate adds one additional conjunct in 1.15: the current block jump factor must be at least `1.0`. The `isMoving()` extraction is equivalent to the older inline test: either component of `input.getMoveVector()` differs from `0.0F` (`LocalPlayer.java:859-861` vs `:972-975`). The passenger check remains `!isPassenger()` in both Mojmap trees.

Both versions cache `options.autoJump` in `autoJumpEnabled` inside the controlled-camera branch of `sendPosition()`, which runs after `super.tick()` for a non-passenger local player (1.14.4 `LocalPlayer.java:178-190,217-255`; 1.15 `:179-191,218-255`). The movement-time gate therefore reads the same cached field arrangement in the two sources.

## Coordinate and arithmetic comparison

The measured horizontal displacement and subsequent detection math retain their source-level structure. Both local `move()` overrides save X/Z before `super.move`, cast the resulting coordinate differences to `float`, and pass them to `updateAutoJump`. Both methods use the current vector as a direction fallback when squared horizontal displacement is `<= 0.001F`; rotate its scaled components by yaw; normalize with fast inverse square root; reject a forward dot product `< -0.15F`; use reach `max(speed * 7.0F, 1.0F / inverseSqrt)`; expand the swept AABB by width; offset edge lines by `0.51F`; and use an obstacle limit of `1.2F`, augmented under jump effect by `(amplifier + 1) * 0.75F`. Both only arm the timer for a measured rise `> 0.5F` and `<=` that limit.

The Y operands differ:

- 1.14.4 start/end candidate points use `getBoundingBox().minY`: `new Vec3(this.x, minY, this.z)` and `new Vec3(this.x + f, minY, this.z + g)` (`LocalPlayer.java:861-862`). 1.15 uses `position()` and `position().add(f, 0.0, g)` (`LocalPlayer.java:864-866`).
- Both obtain the first block probe at player X/Z and bounding-box `maxY` (1.14.4 `LocalPlayer.java:884-889`; 1.15 `:888-893`).
- Both the tall-obstacle early return and final rise subtraction use 1.14.4 bounding-box `minY` (`LocalPlayer.java:927-930,946-949`) versus 1.15 `getY()` (`LocalPlayer.java:929-934,950-953`).

Accordingly, candidate point and height-difference source operands move from AABB bottom to entity position Y; the top block probe remains AABB maxY. This documents changed operands, not a proven numeric difference: pose/dimension and bounding-box synchronization conditions have not been closed here.

## Remaining bounded dependencies

- 1.15 `Entity.getBlockJumpFactor()` reads the block at `new BlockPos(this)` and the movement-support position; it uses the support block's jump factor only when the current block factor equals `1.0` (`world/entity/Entity.java:584-602`). The support helper samples `boundingBox.minY - 0.5000001`. Block-property producers, complete factor registration inventories, and old-side block applicability are not audited here.
- The scan reads `CollisionContext.of(this)`, `getCollisionShape(...)`, `level.getCollisions(...)`, flattens shapes to AABBs, intersects the lateral edge segments, and performs additional vertical collision-shape probes (1.14.4 `LocalPlayer.java:883-939`; 1.15 `:887-942`). Shape-provider aggregation and collision iteration are outside this closure.
- The block jump-factor provider inventory, `onGround` production inside `Entity.move`, pose/bounding-box transitions, and trajectory effects remain unclosed. No runtime inference follows from this source comparison.

## Corrected conclusion

For the exact published Mojmap pair, enabled/timer/on-ground/passenger checks remain; the nonzero movement test is unchanged in substance. The old `!isSneaking()` predicate is replaced by `!isStayingOnGroundSurface()`, which for `LocalPlayer` also reads the current local sneak/shift input through its override. The one additional gate is `getBlockJumpFactor() >= 1.0`. Auto-jump scan reference Y changes from AABB minY to entity position Y in the candidate points and rise subtractions; the block-top probe still uses bounding-box maxY. These findings close only the stated gate, reference-point, and immediate tick/input paths.
