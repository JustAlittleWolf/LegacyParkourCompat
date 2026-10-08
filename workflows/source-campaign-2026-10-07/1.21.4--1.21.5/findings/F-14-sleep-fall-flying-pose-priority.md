# F-14: Sleeping takes precedence over retained fall-flying pose in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S2-POSE, S2-DIMENSIONS, S2-EYE-HEIGHT, S2-STATE-WRITERS
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: A1
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap/`
- Evidence artifact SHA-256: source manifest `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap.sources.sha256` / `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.4/artifacts.sha256` / `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`
- Original derived-artifact availability and SHA-256 or expected hash: source and mapped client jar available; jar SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`
- Source/raw-input hash relation and verification reference: readiness marker points to exact release 1.21.4 and the source manifest hash above; cited source file hashes were rechecked against that manifest.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original publication used.
- Provenance limitations: none identified for the cited publication; see run artifact manifest for client and official mapping identities.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:
  - `net/minecraft/world/level/block/BedBlock.java`; `net.minecraft.world.level.block.BedBlock#useWithoutItem(BlockState, Level, BlockPos, Player, BlockHitResult)` lines 83-119; SHA-256 `6fa509ef173bb0d670294c66495ee5aa388a0fb2bbed07e21757ac44a0e56d20`.
  - `net/minecraft/server/level/ServerPlayer.java`; `ServerPlayer#startSleepInBed(BlockPos)` lines 1113-1157; SHA-256 `d4fd29d9bed9698797e14cf9883e53f04ff4735afb2fee2972085cd82bf0d8f2`.
  - `net/minecraft/world/entity/player/Player.java`; `Player#tick()` line 329, `Player#updatePlayerPose()` lines 434-461, and player pose dimension table lines 136-147; SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
  - `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#startSleeping(BlockPos)` lines 3395-3410, `#aiStep()` lines 2769-2771, `#canGlide()` lines 2847-2859, `#canGlideUsing(ItemStack, EquipmentSlot)` lines 3626-3633, `SLEEPING_DIMENSIONS` line 177, and `#getDimensions(Pose)` lines 3352-3358; SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
  - `net/minecraft/client/player/LocalPlayer.java`, `AbstractClientPlayer.java`, and `RemotePlayer.java`; `LocalPlayer#tick()` calls `super.tick()` lines 191-195; class inheritance lines 93 and 17; remote empty `updatePlayerPose()` lines 87-89; SHA-256 values `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`, `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c`, and `2bac53020610475374bfbcee937be72baba11fceeb1c64f88a09989f21fce0fe`.
  - `net/minecraft/world/entity/Entity.java`; `Entity#setPose(Pose)` lines 367-369, `#onSyncedDataUpdated(EntityDataAccessor)` and `#refreshDimensions()` lines 2934-2964; SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`.

### B artifact identity

- Evidence artifact record ID: B1
- Publication status: original-verified
- Revision ID (revised evidence only): not applicable
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/`
- Evidence artifact SHA-256: source manifest `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap.sources.sha256` / `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.5/artifacts.sha256` / `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`
- Original derived-artifact availability and SHA-256 or expected hash: source and mapped client jar available; jar SHA-256 `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`
- Source/raw-input hash relation and verification reference: readiness marker points to exact release 1.21.5 and the source manifest hash above; cited source file hashes were rechecked against that manifest.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original publication used.
- Provenance limitations: none identified for the cited publication; see run artifact manifest for client and official mapping identities.
- Relative source path; fully qualified class; member signature/descriptor; original line range; source hash:
  - `net/minecraft/world/level/block/BedBlock.java`; `net.minecraft.world.level.block.BedBlock#useWithoutItem(BlockState, Level, BlockPos, Player, BlockHitResult)` lines 81-117; SHA-256 `93231daf73d11a6b8d72e6f42f4dcf8b928e443d34708e0a4c2ee50c1c88d4dd`.
  - `net/minecraft/server/level/ServerPlayer.java`; `ServerPlayer#startSleepInBed(BlockPos)` lines 1067-1111; SHA-256 `81ec90de6bf1521f45f1552c60af1ebb1a83fbcfa2eefb41b99e25b99d2f0d1b`.
  - `net/minecraft/world/entity/player/Player.java`; `Player#tick()` line 344, `Player#updatePlayerPose()` lines 449-461, `Player#getDesiredPose()` lines 465-475, and player pose dimension table lines 137-148; SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`.
  - `net/minecraft/world/entity/LivingEntity.java`; `LivingEntity#startSleeping(BlockPos)` lines 3386-3401, `#aiStep()` lines 2753-2755, `#canGlide()` lines 2837-2849, `#canGlideUsing(ItemStack, EquipmentSlot)` lines 3622-3629, `SLEEPING_DIMENSIONS` line 176, and `#getDimensions(Pose)` lines 3343-3349; SHA-256 `a8aed863d4fdc515c751dd2878a8bbc9b13179228cb8dbb50edf1d19cd5404271`.
  - `net/minecraft/client/player/LocalPlayer.java`, `AbstractClientPlayer.java`, and `RemotePlayer.java`; `LocalPlayer#tick()` calls `super.tick()` lines 191-195; class inheritance lines 93 and 17; remote empty `updatePlayerPose()` lines 86-88; SHA-256 values `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`, `971bc296fe1feeb3fc54a2b78c0a0a6d0df2578fb79428c451f25be57c20a648`, and `db4508961b75904ebcefc64baae2a3f613be4c4136333152972d320e7b533e08`.
  - `net/minecraft/world/entity/Entity.java`; `Entity#setPose(Pose)` lines 375-377, `#onSyncedDataUpdated(EntityDataAccessor)` and `#refreshDimensions()` lines 2927-2957; SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.

## Source-level difference

Bed use is handled on the server: `BedBlock#useWithoutItem` returns immediately on the client, and the server-side `ServerPlayer#startSleepInBed` checks the sleep conditions and then calls `Player#startSleepInBed` -> `LivingEntity#startSleeping`. The paired server checks include range and obstruction but no grounded or fall-flying predicate.

`startSleeping` sets `Pose.SLEEPING`, moves the player to the bed, records the sleeping position, sets delta movement to `Vec3.ZERO`, and marks an impulse. It does not clear the fall-flying shared flag. During the following `LivingEntity.aiStep`, active fall flying calls `updateFallFlying`; on the server the flag is cleared only when `canGlide()` fails. A retained state therefore requires the player to remain airborne, not be a passenger, lack Levitation, and have an equipped, non-breaking item whose components permit gliding in that slot.

`Player.tick` calls `super.tick()` before `updatePlayerPose()`. Both versions first require the player to fit the swimming dimensions and then apply their desired-pose/fallback checks. A 1.21.4 chooses `FALL_FLYING` before it checks `isSleeping()`. In 1.21.5, `getDesiredPose()` checks `isSleeping()` before `isFallFlying()`. If both states remain active and the fit gates pass, A changes the pose back to `FALL_FLYING`; B keeps `SLEEPING`.

## Reachability and dependencies

Server bed interaction -> `ServerPlayer#startSleepInBed` -> `LivingEntity#startSleeping` -> sleeping pose and position, zero velocity, sleeping-position state; existing fall-flying shared flag -> server `LivingEntity.aiStep`/`canGlide` -> `Player.tick`/`updatePlayerPose` -> `Entity.onSyncedDataUpdated`/`refreshDimensions` -> player bounding box and eye height. The bed callback's client-side branch does not start sleep locally. On client, the server supplies synchronized sleeping/fall-flying state; the local `Player` pose update may consume it, while `RemotePlayer` overrides pose updating. The authoritative server pose decision is the relevant state transition.

The remaining movement dependencies include exact GLIDER/EQUIPPABLE item component producers and equipment applicability, all other player pose writers/transitions, and collision/fluid readers of the changed bounding box and eye height. Those remain open in the run inventory.

## Consequence and uncertainty

Player dimensions for `FALL_FLYING` are 0.6 x 0.6 with 0.4 eye height; sleeping dimensions are 0.2 x 0.2 with 0.2 eye height in both releases. `DATA_POSE` updates refresh dimensions and eye height. The player path does not run the position-fudge branch in `refreshDimensions`, which excludes `Player`; the player center remains at the bed-set position through that refresh.

The source proves that bed start zeroes velocity and that a later glide step can recompute velocity while fall flying remains active; it does not prove that either release produces a particular trajectory. The pose difference then selects different boxes and eye positions for subsequent movement, collision and eye-based fluid queries. The exact resulting movement depends on the server's next ground/contact state, equipment components, world collision and caller timing. The difference is transient if a later server glide validation clears fall flying before another pose update.

## Handoff

Independent delta: precedence of sleeping versus active fall-flying when updating player pose after a bed-start state retains fall flying. Related findings: F-06, F-12. Applicability requires the coexistence and fit conditions stated above. Exact first changed release within the pair is unknown. Implementation and runtime validation are deferred.
