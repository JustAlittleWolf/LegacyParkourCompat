# 1.14 → 1.14.1: creative flight with active Elytra

## Verdict

No source difference was found in the assigned local-player movement slice. With an already-active Fall Flying flag, a dry airborne nonpassenger player whose creative flight ability is active enters `PlayerEntity.travel`'s ability-flight branch in both releases. The call to `super.travel` performs the active Elytra movement first. After it returns, the player branch retains the resulting X/Z velocity, replaces Y velocity with the pre-travel Y velocity multiplied by `0.6`, restores the fly-speed field, resets `fallDistance` to `0.0F`, and then clears entity flag 7. The fall-distance reset is a separate state write and precedes the direct flag clear in both versions.

For the stated airborne case, the `LivingEntity.travel` Elytra branch performs the movement, but its own flag-7 clear is guarded by `onGround && !world.isClient`; that clear does not apply. The later `PlayerEntity.travel` clear is therefore the relevant direct clear. Both branches and their order are identical across this pair.

This is a bounded no-difference result, not a finding and not full-pair coverage or freeze.

## Exact source evidence

Mapping family: Yarn on both sides, pinned separately for each exact release. Names in the source retain Yarn's unmapped `field_6174`; the adjacent `EquipmentSlot` source identifies its stored slot name as `"chest"`.

- **1.14, Yarn 1.14+build.21**
  - `net/minecraft/client/network/ClientPlayerEntity.java`, `tickMovement`, lines 697–700: the request to begin Elytra flight requires falling, not already Fall Flying, and `!abilities.flying`; it checks the chest slot for the Elytra item and usability. This is only the start request; the assigned case already has Elytra flight active.
  - `net/minecraft/entity/player/PlayerEntity.java`, `travel`, lines 1362–1392: `abilities.flying && !hasVehicle()` branch, `super.travel`, Y-velocity replacement, fly-speed restore, fall-distance reset, then `setFlag(7, false)`.
  - `net/minecraft/entity/LivingEntity.java`, `travel`, lines 1763–1941: Elytra movement branch, including `move`, with the later flag clear guarded by `onGround && !world.isClient`.
  - `net/minecraft/entity/EquipmentSlot.java`, line 8: `field_6174` has the serialized name `chest`.
- **1.14.1, Yarn 1.14.1+build.10**
  - The same members and ranges in the corresponding four source files.

The complete `PlayerEntity.java` source is byte-identical in the two trees. The extracted `LivingEntity.travel` bodies (lines 1763–1941) and the relevant `ClientPlayerEntity.tickMovement` excerpt (lines 651–711) compare equal. The full `LivingEntity.java` and `ClientPlayerEntity.java` files have unrelated differences outside the compared slices.

## Provenance and integrity

Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

Both Yarn trees have local `.decompile-provenance.json` manifests. Manifest source hashes were checked against every local Java source: 3,152/3,152 files matched for 1.14, and 3,155/3,155 matched for 1.14.1; no missing or mismatched source file was found. The local cache's exact client-jar pins agree with each manifest.

| Release | Mapping artifact SHA-256 | Client jar SHA-256 | Source directory |
| --- | --- | --- | --- |
| 1.14 | `yarn-1.14+build.21-v2.jar`: `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64` | `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | `decompiled_minecraft/1.14/yarn` |
| 1.14.1 | `yarn-1.14.1+build.10-v2.jar`: `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `decompiled_minecraft/1.14.1/yarn` |

## Coverage boundary

Examined the local-player Elytra-start gate relevant to active flight, chest-slot identity, `PlayerEntity.travel`, and the `LivingEntity.travel` fall-flying branch and flag-clear gate. The exact scenario is closed as a no-difference check. This assignment did not inventory other tick paths, collision and shape providers, external movement responses, or other pair operations; it does not close the full 1.14–1.14.1 pair.
