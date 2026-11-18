# 1.14 → 1.14.1 active Elytra versus climb slice

## Verdict and bound

No movement difference found in the assigned ordinary dry, already-active Elytra, on-foot slice at ladder/vine feet. The relevant `PlayerEntity.java` files are byte-identical. The `LivingEntity.java` files differ only at the unrelated method-name mapping `method_5879()` → `animateDamage()`; the active-gliding, climb-cleanup, startup, travel and tick-final bodies are identical. This is a bounded no-difference result, not full pair coverage.

Concrete bound: a living player on the logical client, no vehicle, not on ground, no water or lava, already has tracked fall-flying flag 7 set, and wears a usable Elytra in the chest slot. Assume the feet-position block is a normal ladder or vine. The profile/input used to select the historical source is outside this source slice.

## Exact source identities

Canonical read-only trees: `decompiled_minecraft/1.14/yarn/` and `decompiled_minecraft/1.14.1/yarn/`.

- 1.14: Yarn `1.14+build.21`; mapping artifact SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`; client artifact SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`.
- 1.14.1: Yarn `1.14.1+build.10`; mapping artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`; client artifact SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`.

Both manifests record the local canonical source pipeline and runtime as Eclipse Adoptium Java 25.0.3+9-LTS on Windows 11 amd64. The recorded `buildSrc.jar` SHA-256 is `3396837ae5b6ea385980a625ce89cd495d90286c660bf2789371c39c2715980d`; the recorded Vineflower 1.12.0 JAR SHA-256 is `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d` on both sides. SHA-256 hashes below were recalculated from the current source files and match the manifest `sources` entries:

| Exact source file | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| `net/minecraft/block/Blocks.java` | `62acdf407d1fa85095da0351d441b5ece10681b036505f7b9972bc21f77b0a0d` | `f5ba7169237468c68c085ecbf101e0e1c620013c00744a20dfae7c8af97c74dd` |

## Ordered path and distinct gates

1. **Startup / active update:** `LivingEntity.tick()` calls `super.tick()`, then two entity update methods, performs server-side equipment/effect work, and calls `tickMovement()` (1.14 and 1.14.1: `LivingEntity.java:1956–2023`). In `tickMovement()`, after AI and jump handling, the `travel` profiler section scales inputs, calls `initAi()`, captures the bounding box, then calls `travel(...)` (`:2127–2205`).
2. **Active flag validation before travel:** `initAi()` starts from tracked flag 7. If it is already true, airborne and unmounted, it checks chest-slot item identity and `ElytraItem.isUsable`; invalid eligibility clears only its local boolean. It writes the resulting flag only when `!world.isClient` (`LivingEntity.java:2216–2235`). Thus for the assigned already-active valid client state, the flag is not cancelled in this client-side validation step; the existing tracked true flag remains authoritative for the immediately following travel call. This gate and order match in both releases.
3. **A — dry travel authority:** `LivingEntity.travel()` first requires voluntary movement or logical-side movement, then enters its non-water and non-lava branch. Within that branch, `isFallFlying()` is tested before ordinary ground travel (`:1763–1775`). With flag 7 true, the Elytra glide branch wins even when the feet block is a ladder or vine. The pre-move `Vec3d lv5` is the current velocity; strictly `lv5.y > -0.5` assigns `fallDistance = 1.0F` before rotation math and `move(...)` (`:1774–1803`). At exactly `-0.5` or below, this assignment does not occur. Later glide writes update velocity and move; a client does not run collision damage or the `onGround` flag-clear branch (`:1804–1816`). This means ladder/vine climb cleanup is bypassed for this active dry glide.
4. **B — non-gliding climb cleanup / wet alternative:** The sibling non-gliding dry branch performs velocity update, then calls `method_18801(...)` before its move (`:1817–1827`). `method_18801()` first checks `isClimbing()`, resets `fallDistance` to `0.0F`, clamps horizontal input to ±0.15 and downward input to at least −0.15, with the sneaking/player/other-block exception (`:1921–1935`). Later, after the move, horizontal collision or jumping while climbing sets the local Y velocity to `0.2` (`:1823–1827`). `isClimbing()` checks the block state at `new BlockPos(this)` (feet position), treating ladder, vine and scaffolding as climbing blocks (the direct declarations identify ladder at `Blocks.java:474` and vine at `:753` in 1.14; corresponding 1.14.1 declarations are `:474` and `:749`). This cleanup is not reached in branch A. Water/lava take their separate travel branch before dry gliding (`:1772–1774`, `:1845–1859`); they are excluded from the ordinary dry case.
5. **Player wrapper and tick final:** `PlayerEntity.travel()` has an earlier swimming adjustment, then a separate ability-flight/no-vehicle branch. Ordinary on-foot non-creative travel falls through to `super.travel(arg)` and then movement-stat accounting; it does not clear flag 7 (`PlayerEntity.java:1361–1389`). At the end of `tickMovement()`, after travel and movement/animation updates, fall-flying increments `field_6239`; otherwise it resets it to zero (`LivingEntity.java:2089–2100`). This final counter and ordering match both releases.

## Comparison method / remaining scope

I compared the exact bounded methods and enclosing calls in both canonical Yarn trees, plus the ladder/vine declarations that establish the feet-state predicate. `git diff --no-index` over the whole `LivingEntity.java` pair showed only the single unrelated mapping rename noted above; the whole `PlayerEntity.java` pair is byte-identical. The bounded operation therefore has no finding. Other movement slices, startup dependencies beyond the stated callers, and full-pair inventory/independent audit remain unexamined and open.

