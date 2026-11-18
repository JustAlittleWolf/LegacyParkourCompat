# 26.1 → 26.1.1 Elytra start and climb exit

## Verdict

No source difference was found in the bounded Elytra start, pre-travel, climb diversion, or authority routes. The compared canonical source files are byte-identical across exact releases 26.1 and 26.1.1. This is a bounded no-difference report, not full-pair coverage or runtime parity evidence.

## Exact source identity

Both canonical roots are `decompiled_minecraft/<version>/unobfuscated/`. Their `unobfuscated.provenance.json` and `unobfuscated.ready.json` identify the exact requested and resolved release (26.1 or 26.1.1), namespace `unobfuscated`, and one preparation run: repository commit `fcd113c3fafcfde91d5cb5c8308b77292c601f9d`, batch `[26.1, 26.1.1]`, published unobfuscated (no mapping artifact). Each ready marker's source-manifest SHA-256 was verified against the actual `unobfuscated.sources.sha256` bytes.

| Release | Version metadata SHA-256 | Original client SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|
| 26.1 | `68e8d6e01e06e6a9a740a7ad474061f9303b004ceedb8224b085a58616390e49` | `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` |
| 26.1.1 | `86b232149a2ae224b06443730fe32c46c306f035a1599e3477c5237df1c950ef` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` |

Relevant whole-file hashes in both releases (also present in each corresponding source manifest):

- `net/minecraft/client/player/LocalPlayer.java`: `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`
- `net/minecraft/world/entity/player/Player.java`: `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`
- `net/minecraft/world/entity/LivingEntity.java`: `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`
- `net/minecraft/world/entity/Entity.java`: `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- `net/minecraft/server/network/ServerGamePacketListenerImpl.java`: `16d32046e8a1435723f4009e51d5b557fdff71419034554f8cf5cc63240636db`

## Compared routes and behavior

- **Local input, ordinary air versus feet on a climbable:** `LocalPlayer.aiStep` at `LocalPlayer.java:854–858` starts the attempt only while jump is pressed, it was not already pressed, creative flight was not just toggled, and `!onClimbable()`. It calls `tryToStartFallFlying()` and sends `START_FALL_FLYING` only on success. At ordinary feet in air, a valid default chest Elytra can start gliding. At ladder/vine feet when `onClimbable()` is true, this input route is suppressed. Leaving the climbable block permits the route on a fresh jump press; a continuously held jump does not satisfy `!wasJumping`.
- **Eligibility and active flag:** `Player.java:1407–1409,1442–1453` requires non-creative flight and, in `tryToStartFallFlying`, not already fall-flying, `canGlide()`, and not in water; `startFallFlying()` sets shared flag 7. `LivingEntity.java:3148–3160,3946–3953` requires airborne, not passenger, no Levitation, and an equipped, unbroken item with the GLIDER component in its designated slot. `canGlide` has no fall-distance or climbability gate. For the assigned default chest case, this is the ordinary eligibility path.
- **Pre-travel cap and first movement:** `LivingEntity.java:3060–3068` calls `updateFallFlying()` before travel when flag 7 is active. `updateFallFlying()` first invokes `checkFallDistanceAccumulation()`, whose exact rule at `Entity.java:2835–2838` caps `fallDistance` to `1.0` when vertical delta is greater than `-0.5` and fall distance is greater than `1.0`. The tick then chooses fluid travel first, else fall-flying travel when the flag is still set, else ordinary air (`LivingEntity.java:2392–2399`). In `travelFallFlying()` (`2528–2547`), if `onClimbable()` is true it performs `travelInAir(input)` first, then `stopFallFlying()` toggles flag 7 true and false; otherwise it applies Elytra movement. Thus a newly started, ordinary-air glide takes the glide branch on that tick; an already-active flag reaching a state that still counts as climbable takes one ordinary-air movement before the stop-flag sequence.
- **Climb predicate detail:** `LivingEntity.java:1689–1704` makes `onClimbable()` false for spectators; during fall flying it also returns false when the in-block state has `CAN_GLIDE_THROUGH`, before checking `CLIMBABLE` or the usable-trapdoor case. Therefore “ladder/vine feet” means a state for which this actual predicate remains true after that override; the block name alone does not bypass the ordered checks.
- **Authority:** The client-side start sets its local shared flag and sends `START_FALL_FLYING` (`LocalPlayer.java:854–856`). The server packet handler (`ServerGamePacketListenerImpl.java:1737–1740`) independently calls `tryToStartFallFlying()` and calls `stopFallFlying()` if it fails. `updateFallFlying()` only performs `canGlide()` validation and Elytra wear/event work under `!level().isClientSide()` (`LivingEntity.java:3126–3145`). The client input gate checks `!onClimbable`, but the server's `tryToStartFallFlying` eligibility method does not; if a start request is received while still climbable and otherwise eligible, the server can set the flag, then its movement route takes the ordinary-air-first/stop sequence above. This describes vanilla source behavior; it does not authorize replaying historical movement on a logical server.

## Bounds

Compared only these ordered player input, eligibility, active-flag, pre-travel, first movement/stop, and server request/validation routes for an ordinary on-foot local player with a default valid chest Elytra, contrasting ordinary air with ladder/vine feet. The full relevant Java files have equal hashes across the pair, so the checked member text is identical. I did not inspect other movement areas, test or launch the game, generate sources, use network or Git, or edit product/research files.
