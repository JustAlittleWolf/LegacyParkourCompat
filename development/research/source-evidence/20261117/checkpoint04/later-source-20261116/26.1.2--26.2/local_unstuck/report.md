# Local unstuck corner probes — bounded source report

## Verdict and scope

Endpoint comparison of the owned LocalPlayer corner/stuck probes and their local tick reachability found **no difference in the examined probe operation** from 26.1.2 to 26.2. This is a partial source slice, not a source acceptance or pair-freeze claim. The suffocating-block collision provider and its block/collision dependencies remain open. No exact intermediate onset is established.

## Endpoint identities and integrity

- **26.1.2, unobfuscated** — ready/provenance declare version `26.1.2`, namespace/mapping `unobfuscated`; Vineflower 1.12.0, Java 25.0.3+9-LTS. `unobfuscated.sources.sha256` SHA-256: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`. Root artifact manifest is named `artifacts.sha256` (the ready marker declares that name); SHA-256: `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`.
- **26.2, unobfuscated** — ready/provenance declare version `26.2`, namespace/mapping `unobfuscated`; Vineflower 1.12.0, Java 25.0.3+9-LTS. `unobfuscated.sources.sha256` SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Root artifact manifest is named `artifacts.sha256`; SHA-256: `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- Both ready markers' source/artifact hashes match the on-disk sidecars. The cited source files below were rehashed and match their entries in each source manifest.

## Ordered coverage

### Examined

1. **LocalPlayer local tick entry and superclass path.** `LocalPlayer.tick()` enters only when `connection.hasClientLoaded()`, then calls `super.tick()` (26.1.2 `LocalPlayer.java:227-249`; 26.2 same range). `AbstractClientPlayer.tick()` performs its avatar-state update then calls `super.tick()` (both `AbstractClientPlayer.java:44-46`). `Player.tick()` sets `noPhysics = isSpectator()` before calling `super.tick()` (26.1.2 `Player.java:231-259`; 26.2 `Player.java:232-260`). `LivingEntity.tick()` calls virtual `this.aiStep()` only when `!this.isRemoved()` (26.1.2 `LivingEntity.java:2738-2740`; 26.2 `:2804-2806`). This establishes the normal LocalPlayer tick path and the relevant outer gates.
2. **Corner probe caller.** `LocalPlayer.aiStep()` executes four calls under `if (!this.noPhysics)`, in order: `(x - width*0.35, z + width*0.35)`, `(x - width*0.35, z - width*0.35)`, `(x + width*0.35, z - width*0.35)`, `(x + width*0.35, z + width*0.35)` (both `LocalPlayer.java:767-800`). The calls occur before the remaining sprint/travel handling. The portal-effect `LevelLoadingScreen` check immediately above changed from `minecraft.screen` in 26.1.2 to `minecraft.gui.screen()` in 26.2; it encloses portal-effect work only and does not gate these probes.
3. **Probe body and direct player mutation.** `moveTowardsClosestSpace(double x, double z)` plus `suffocatesAt(BlockPos)` are unchanged at `LocalPlayer.java:451-484` in both endpoints. The method floors the supplied x/z and the player's y with `BlockPos.containing`; if the position suffocates, it tests directions in WEST, EAST, NORTH, SOUTH order. It computes each candidate edge distance using the corresponding x/z offset and direction sign, accepts only a strictly closer neighbor that does not suffocate, then sets only the selected horizontal velocity component to `0.1 * direction step`, preserving the other components from the old velocity. `suffocatesAt` forms the block-column AABB from that block's x/z bounds and the player's bounding-box minY/maxY, deflates it by `1.0E-7`, and calls `level().collidesWithSuffocatingBlock(this, testArea)`.
4. **Exact endpoint comparison.** The compared probe method bodies and four call statements are text-identical. Relevant tick reachability and the `!noPhysics` gate are unchanged. No probe-level difference is reported.

### Partial / dependency open

- `Level.collidesWithSuffocatingBlock` and the collision/block-state resources it consults are not audited here. The probe's suffocation decision therefore remains an open dependency; this report does not establish provider parity or complete local-unstuck parity.
- The LocalPlayer `aiStep()` chain's other operations are outside the assigned slice. The `screen()` accessor change above is noted only as neighboring context.

### Unexamined

- All other player tick, input, travel, collision, post-move, packet-response and player-contact slices, except the narrow superclass tick dispatch noted above.
- Any releases between 26.1.2 and 26.2; endpoint comparison only.
- Runtime behavior and implementation mapping.

## Relevant source identities

SHA-256 values below were recomputed from these files and matched their `unobfuscated.sources.sha256` entries.

| Endpoint | Relative file | SHA-256 |
|---|---|---|
| 26.1.2 | `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.2 | `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.1.2 | `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` |
| 26.2 | `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` |
| 26.1.2 | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.2 | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.1.2 | `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |

## Next gate

A bounded dependency review of `collidesWithSuffocatingBlock` and its relevant vanilla collision inputs is required before the local unstuck operation can be considered dependency-closed. Pair coverage remains partial pending the other assigned slices and independent audit.

