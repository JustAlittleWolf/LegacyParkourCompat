# Independent blind audit verdict: local unstuck probes

Candidate report SHA-256: `0B0E615D11D39AF16E319B4A0E820669C194A4027A31D206A1431ADBCCD65A38`

Verdict: **Agree with the bounded no-difference conclusion.** I independently checked the exact endpoint identity/manifests, the cited `LocalPlayer.java` source hashes, the probe bodies, their ordered caller sites, and the local tick path. The source-visible behavior in the examined corner-probe operation is unchanged from 26.1.2 to 26.2. This verdict applies only to the candidate revision and bounded operation; it is not source acceptance or pair freeze.

## Evidence identity and integrity

Both `unobfuscated.ready.json` markers declare the expected exact version and `unobfuscated` mapping. They declare the source-manifest and artifact-manifest filenames and hashes recorded in the candidate; both declared manifest SHA-256 values match the files on disk. The provenance sidecars identify Vineflower 1.12.0 and Java 25.0.3+9-LTS for both endpoints. The sidecar filenames present on disk are `unobfuscated.provenance.json` (without a leading dot).

The cited LocalPlayer source file hashes match both the candidate and each endpoint's source manifest:

- 26.1.2 `net/minecraft/client/player/LocalPlayer.java`: `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`
- 26.2 `net/minecraft/client/player/LocalPlayer.java`: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`

The audited source roots are `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`.

## Audited operation and reachability

`LocalPlayer.moveTowardsClosestSpace(double x, double z)` and `suffocatesAt(BlockPos)` at `LocalPlayer.java:451-484` are text-identical at both endpoints. The implementation floors the requested x/z and player y into a `BlockPos`; only if that position suffocates does it inspect WEST, EAST, NORTH, SOUTH in that order. For each direction it computes the axis distance to the corresponding edge, and accepts a neighbor only when the distance is strictly less than the current best and the neighbor does not suffocate. If a direction wins, it changes only its horizontal velocity component to `0.1 * direction step`, preserving the old y component and the other horizontal component. The suffocation query builds a block-column AABB spanning the player's bounding-box minY/maxY, deflates it by `1.0E-7`, then calls `level().collidesWithSuffocatingBlock(this, testArea)`. These body details and ordering match in both files.

The probe caller is unchanged at `LocalPlayer.java:795-800`: `aiStep()` checks `!this.noPhysics`, then calls the four corner probes in the reported order using `getBbWidth() * 0.35`. The portal screen check above it changed from `minecraft.screen` to `minecraft.gui.screen()`, but it encloses portal effects only; it neither gates nor changes the probe calls. Probe placement relative to the following sprint handling is unchanged.

The local tick route is present at both endpoints: `LocalPlayer.tick()` calls `super.tick()` only under `connection.hasClientLoaded()`; `AbstractClientPlayer.tick()` calls `super.tick()` after avatar-state update; `Player.tick()` assigns `noPhysics = isSpectator()` before its `super.tick()`; `LivingEntity.tick()` invokes virtual `aiStep()` under `!isRemoved()`. The relevant statements are unchanged in effect. Therefore the reported `!noPhysics` gate is reachable on a normally ticking loaded local player and skips these probes for a spectator.

## Dependency boundary

The report correctly leaves `Level.collidesWithSuffocatingBlock` and its collision/block-state inputs unaudited. Since that call determines the suffocation predicate used by the probes, this verdict does not establish dependency closure or full local-unstuck parity. Pair source coverage remains partial, and the collision dependency remains open. No intermediate release boundary or runtime parity is established.