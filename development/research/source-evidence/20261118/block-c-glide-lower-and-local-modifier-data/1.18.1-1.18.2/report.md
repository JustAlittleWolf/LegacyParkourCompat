# Elytra glide lower-gate source check: 1.18.1 → 1.18.2

## Scope and result

Exact pair: Minecraft 1.18.1 → 1.18.2, Mojmap. Bounded operation: already-active, ordinary local player Elytra travel; pre-move fall-distance write, feet-climbable handling and same-tick writers; separately bound activation and fall-flight tick counter.

**Confirmed boundary:** Both `LivingEntity.travel` implementations write `fallDistance = 1.0F` before calculating or applying the Elytra movement iff the pre-move vertical component is strictly greater than `-0.5`. This lower gate and the ordinary, feet-state climbable predicate/handler exist unchanged in both releases. The Elytra branch is mutually exclusive with ordinary climb handling in `travel`, so ladder/vine `handleOnClimbable` does not reset this write during the same travel branch. A difference intervenes after the write in 1.18.2: `Entity.move` can reset nonzero fall distance before position application if the collision-adjusted movement is at least one block in squared length and a ray hits a `FALL_DAMAGE_RESETTING` block. 1.18.1 has no such ray reset.

## Ordered movement and climb behavior

- **1.18.1 `LivingEntity.java`, `travel(Vec3)`, lines 2005–2111:** after the slow-falling prelude and liquid/lava branches, the `else if (this.isFallFlying())` branch reads `Vec3 $$13 = this.getDeltaMovement()` (2071), tests `$$13.y > -0.5` (2072), then assigns `this.fallDistance = 1.0F` (2073). It next reads look/rotation and computes the glide vector; it finally sets drag-adjusted delta movement and calls `move(MoverType.SELF, ...)` (2098–2100). Strictly equal `y == -0.5` does not write 1.0F. `y` is the Vec3 double component; the stored value is float literal `1.0F`.
- **1.18.2 `LivingEntity.java`, `travel(Vec3)`, lines 2009–2115:** same test and write at lines 2076–2077, before glide math and the call to `move` at 2102–2104. Thus the ordered lower-gate write itself is not a 1.18.1→1.18.2 difference.
- **Ordinary climb path, both releases:** `travel` reaches `handleRelativeFrictionAndCalculateMovement` only in its final `else` (1.18.1 lines 2113–2138; 1.18.2 lines 2117–2142), which is mutually exclusive with `isFallFlying()`. That helper applies `handleOnClimbable` before its move call (1.18.1 lines 2157–2167; 1.18.2 lines 2161–2171). `handleOnClimbable` resets fall distance, clamps horizontal velocity to ±0.15, floors vertical velocity at -0.15, and suppresses negative vertical movement for a crouching Player except on scaffolding (1.18.1 lines 2185–2199; corresponding 1.18.2 lines 2189–2203). After movement, if horizontal collision or jumping while climbable, it sets resulting Y velocity to 0.2.
- **Feet-state predicate and tag contents:** `LivingEntity.onClimbable` (1.18.1 lines 1429–1447; 1.18.2 lines 1433–1451) reads `getFeetBlockState()`, returns true for `BlockTags.CLIMBABLE`, or for a trapdoor usable as a ladder. `BlockTagsProvider` explicitly includes `Blocks.LADDER` and `Blocks.VINE` in `CLIMBABLE` in both versions (1.18.1 lines 566–577; 1.18.2 lines 564–575). Therefore this sampled ladder/vine feet predicate and ordinary handling also hold at 1.18.1.
- **Same-tick 1.18.2 overwrite:** its `Entity.move` (lines 543–575) collision-adjusts the requested vector, then, before `setPos`, checks `fallDistance != 0.0F && collidedDelta.lengthSqr() >= 1.0`; it clips from current position to position plus that delta with `ClipContext.Block.FALLDAMAGE_RESETTING` and `ClipContext.Fluid.WATER`, and resets fall distance on a non-MISS hit. `BlockTagsProvider` defines `FALL_DAMAGE_RESETTING` as all `CLIMBABLE` blocks plus sweet berry bushes and cobwebs (line 576); `ClipContext` makes that block shape a full cube (1.18.2 line 51). Consequently a glide tick that first writes 1.0F can clear it here if this exact movement/raycast gate hits. The block tag and ray-reset code are absent in 1.18.1.
- **Following movement write:** in both versions, `Entity.move` calls `checkFallDamage` after applying collision movement and establishing on-ground state (1.18.1 lines 579, 969–982; 1.18.2 lines 593, 988–1000). A downward non-grounded displacement adds its magnitude to fall distance; landing resets it. This is after the glide write and, in 1.18.2, after the new ray reset. This is ordinary movement accumulation/landing, not an Elytra-specific overwrite.

## Upstream activation and later counter

The local-player jump edge starts flying only when not abilities-flying, not a passenger and not on a climbable block; it checks an enabled chest Elytra then `tryToStartFallFlying` and sends `START_FALL_FLYING` (`LocalPlayer.aiStep`, 1.18.1 lines 647–750; 1.18.2 lines 642–745). `Player.tryToStartFallFlying` additionally requires airborne, not already fall-flying, not in water and no levitation; it checks `Items.ELYTRA` and `ElytraItem.isFlyEnabled`, then sets shared flag 7 (`Player.java`, 1.18.1 lines 1542–1557; 1.18.2 lines 1555–1570). The requested lower-gate result conditions on this flag already being active and the regular travel branch being reached.

`LivingEntity.aiStep` calls `updateFallFlying()` immediately before `travel(...)` (1.18.1 lines 2543–2549; 1.18.2 lines 2547–2553). That updater preserves active flight only while airborne, not riding, not levitating and wearing an enabled chest Elytra; otherwise it clears the flag server-side (1.18.1 lines 2586–2612; 1.18.2 lines 2590–2616). Separately, `fallFlyTicks` increments at tick end while flying and resets otherwise (1.18.1 lines 2327–2331; 1.18.2 lines 2331–2335). The `fallFlyTicks + 1` `% 10` check in `updateFallFlying` is only server-side Elytra wear/event cadence: wear occurs at every second 10-tick interval (1.18.1 lines 2592–2603; 1.18.2 lines 2596–2607). It does not gate local-client glide movement.

## Exact source identity and provenance

Sources are read-only canonical output under `decompiled_minecraft/<version>/mojmap/`. The version-adjacent provenance artifacts identify Mojmap and Java 25.0.3 / Gradle 9.7.1 / Vineflower 1.12.0.

- 1.18.1: run ID `prep-1.18-1.18.1-mojmap-2026-10-09-r1`; client SHA-256 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`; `mojmap.provenance.json` SHA-256 `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`; source manifest `mojmap.sources.sha256` SHA-256 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`; ready marker source-manifest identity agrees.
- 1.18.2: adjacent `mojmap.provenance.json` identifies version `1.18.2`, mapping `mojmap`, and the same toolchain; `mojmap.ready.json` client SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`; provenance SHA-256 `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9`; source manifest `mojmap.sources.sha256` SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; ready marker source-manifest identity agrees.

Per-source hashes below are verified against both the files and their adjacent SHA-256 source manifests:

| Exact source | 1.18.1 SHA-256 | 1.18.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/entity/Entity.java` | `69417675eb8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `99c2d18bcd23243afb8f95c5c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/data/tags/BlockTagsProvider.java` | `c2d004e2aa3f23e24836b776ebae3c6df5a6bef91d94a4b3e6d199175669f640` | `a55aa9c25311ebf01feee2e2f1444c9ca6fe45b20963408c0fd3a8b1c05b3513` |
| `net/minecraft/world/level/ClipContext.java` | `7e41ac5791588d6fd84e84728996e8d7d8d2e995e54891c9646329e3f5dc87cd` | `c26cca8b2a2f185463b3785218518174831ea5a06268b04d9dd5c3a5287ac4ff` |

## Boundary disposition

Closed for this bounded operation: the pre-move `y > -0.5`/`1.0F` write and ordinary climbable feet handling are present at 1.18.1 and unchanged through 1.18.2. Newly introduced in 1.18.2 within this bounded tick is the post-write collision-path `FALL_DAMAGE_RESETTING` ray reset in `Entity.move`. No claim is made about intermediate 1.18.x patch introduction beyond this endpoint interval, non-local entities, or runtime trajectory parity.
