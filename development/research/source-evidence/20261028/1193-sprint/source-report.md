# Blind source comparison: 1.19.3 → 1.19.4 (Mojmap)

## Evidence and source-set validation

Canonical read-only inputs:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.19.3\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.19.4\mojmap`

Both releases are marked ready with `mapping: mojmap`, exact version IDs, Vineflower 1.12.0, Java 25, and one batch decompile provenance for 1.19.2/1.19.3/1.19.4. The parent-level ready markers and provenance files were read. Their referenced source manifest and artifact manifest digests match the files on disk. The source roots contain only `com` and `net` package trees; no hidden files or embedded snapshot/provenance files occur under either mapping root. The shared `decompiled_minecraft` parent has a `.decompile-writer.lock`; it was left untouched.

Ready-marker and source-manifest identity:

| Release | Ready marker SHA-256 | Source manifest SHA-256 | Ready count / validated Java files | Source bytes | Client JAR SHA-256 in marker |
| --- | --- | --- | ---: | ---: | --- |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | 4,618 / 4,618 | 22,430,993 | `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` |
| 1.19.4 | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | 4,740 / 4,740 | 23,021,785 | `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec` |

Every manifest-listed file exists and hashes correctly; byte totals and filesystem Java counts match the ready markers. The assignment's estimate of 4,742 files for 1.19.4 differs from its exact ready marker and verified manifest, both of which establish 4,740.

The complete manifest-level delta is 146 added, 24 removed, and 925 changed Java files. [`source-inventory.tsv`](source-inventory.tsv) lists all 1,095 entries with status, package domain, and old/new SHA-256 values. The artifact manifests each include the release client JAR, but the provenance's artifact-cache directory (`D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`) is absent. The mapping trees contain no non-Java resources, so packaged tag/resource contents could not be checked.

## Reachable local-player path checked

The ordinary path is `Minecraft.tick` → `ClientLevel.tickEntities` → `LocalPlayer.tick` → `LocalPlayer.aiStep` (input, sprint state, jump/fall-flying decisions) → `Player.aiStep` → `LivingEntity.aiStep` → `Player.travel` → `LivingEntity.travel` → `Entity.move`/collision. Pre-travel input is `Input.tick`; ground movement consumes the support block's friction and jump/speed factors; fluid, fall-flying and levitation branches use the same `LivingEntity.travel` entry. Position, box and collision processing flows through `Entity.move`, `collide`, `collideBoundingBox`, level/block collision queries, block-state shapes and `BlockBehaviour` offset behavior.

Direct responses checked include client position corrections (`ClientPacketListener.handleMovePlayer`), entity velocity writes and piston movement restrictions, player/entity knockback entry points, block fall/landing callbacks (including slime and honey), fluid/climbable handling, and existing block shape/collision callbacks. Existing piston, collision, fluid, climbable, honey/slime bounce and ordinary block-shape math showed no player-reachable arithmetic/gate change in the examined source hunks. Entity `BlockPos(double,...)` → `BlockPos.containing(...)` replacements preserve `Mth.floor` conversion; the 1.19.4 `maxUpStep()` path reads the same stored field for a player (the new `LivingEntity` override only raises a controlled mount's step height). The `BlockBehaviour` offset refactor retains the same XZ/XYZ seed-bit formulas; all `.offsetType(...)` call sites in both trees pass enum constants, with no state-dependent function caller.

## Confirmed movement-sensitive source differences

1. **Local sprint-start eligibility changed.** In 1.19.3, `LocalPlayer.aiStep` checks the input threshold, food, item use and blindness inline for auto/key sprint. In 1.19.4 both paths call `canStartSprinting`, which adds `!isFallFlying()` and permits sprinting as a passenger only when `vehicleCanSprint(vehicle)` is true; that helper requires both `Entity.canSprint()` and `isControlledByLocalInstance()`. The new base `Entity.canSprint()` is false and `Player.canSprint()` is true. The changed gate means ordinary vehicles that do not override `canSprint` no longer allow the passenger player to enter sprint, and a fall-flying player no longer starts sprint from local input. This changes the player's sprint flag and can affect later travel branches. Exact anchors: 1.19.3 `LocalPlayer.java:652-716`; 1.19.4 `LocalPlayer.java:645-697,1022-1034`; 1.19.4 `Entity.java:3163`; `Player.java:2107`; new `Camel.java:276` (camel is outside this ordinary-player comparison as a 1.20-gated feature).

2. **Airborne sprint acceleration now observes sprint state during the travel step.** In 1.19.3, `Player.aiStep` calls `super.aiStep()` first (which travels the player), then writes `flyingSpeed = 0.02F` or adds `0.006F` when sprinting. In 1.19.4 that cached field/update is removed: `LivingEntity.getFrictionInfluencedSpeed` calls virtual `getFlyingSpeed`, and `Player.getFlyingSpeed()` returns `0.02F` or `0.025999999F` from the current sprint flag. Outside creative flight, normal airborne movement on the tick sprint starts/stops therefore uses the new flag immediately; 1.19.3 uses the value written after the preceding travel step. The creative-flight branch still computes ability speed × 1/2 from the current state in both releases. Anchors: 1.19.3 `Player.java:496-516,1448-1472`; 1.19.4 `Player.java:499-516,1440-1463,2112-2118`; `LivingEntity.java:2197-2203`.

## Direct-response and dependency candidates left open

- The 1.19.3 `ClientboundPlayerPositionPacket` contains and serializes a `dismountVehicle` boolean; `ClientPacketListener.handleMovePlayer` calls `player.removeVehicle()` when it is set. The 1.19.4 packet removes that field and the client branch. `ServerGamePacketListenerImpl` also drops the `dismount(...)` send overload. The exact client correction response is therefore removed. Native 1.19.4 also updates passenger state through `ClientboundSetPassengersPacket`, so the effect on final/tick-level local-player state versus packet ordering needs an independent paired client/server audit before accepting this as a player-movement finding. Anchors: 1.19.3 packet lines 16-51,82-83; `ClientPacketListener.java:593-597`; 1.19.4 `ClientPacketListener.java:605+` (same position/velocity assignment without the dismount prelude); server listener old `:976-1003`, new `:978-995`.
- `LivingEntity.hurt` replaces its explicit DROWN/explosion exceptions with `DamageTypeTags.NO_IMPACT` / `IS_EXPLOSION` checks before knockback. `Entity`/`LivingEntity` also replace underwater-vehicle capability methods with `EntityTypeTags.DISMOUNTS_UNDERWATER`; the dismount call is server-gated. To close equivalence for pre-existing damage/vehicle types, inspect the tag resource definitions from the exact client artifacts. Those binaries are absent at the recorded cache path, so this remains an open resource dependency; damage calculation itself is excluded by project scope.
- Riding-specific changes (`travelRidden`, vehicle jump interfaces, `canSprint`, controlled-vehicle checks, and living-mount step height) were traced to vehicle movement or the player-as-rider path. Vehicle/non-player physics and 1.20-only camel behavior remain outside this ordinary local-player slice; a separate direct-rider-response audit would be required to close those branches.

## Coverage and closure

The full Java manifest inventory is classified by path/status/hash. Player tick, travel, collision, block callback and identified client response slices were compared at both releases. No tests, builds, launches or source regeneration were performed. Resource-bound tag membership, riding-response timing, and an independent blind audit remain open. This is a bounded worker report, not pair freeze or independent acceptance.
