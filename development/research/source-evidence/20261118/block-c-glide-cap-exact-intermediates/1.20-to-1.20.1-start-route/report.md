# 1.20 → 1.20.1 Elytra glide-start and first-move route

## Scope and disposition

Bounded source comparison for an ordinary dry local player with a default chest Elytra, considering the jump-to-glide request, fall-flying flag validation/cancellation, branch selection before the first glide movement, and climbable feet. Starts beyond this LocalPlayer input path, final glide ticks, and A/B contact or geometry are outside this report.

**Disposition: unverified pair; no source difference observed in the requested slices.** The 1.20 side is backed by an exact local client artifact and provenance. The adjacent `1.20.1/mojmap` directory exists, but has no `.decompile-provenance.json`; `.minecraft-decompile-cache/1.20.1` is absent. Its `LocalPlayer.java`, `Player.java`, and `LivingEntity.java` are byte-identical to the verified 1.20 files. These matching files do not establish that the 1.20.1 tree came from the exact 1.20.1 client, so the apparent no-difference result cannot close the pair. I did not substitute the 1.20.1 tree for the required 1.20 source.

## Release and source identity

- Older label: `1.20`, Mojmap. Canonical tree: `decompiled_minecraft/1.20/mojmap/`.
- Newer label: `1.20.1`, Mojmap. Canonical tree: `decompiled_minecraft/1.20.1/mojmap/`; **raw release/provenance identity missing locally**.
- The 1.20 `.decompile-provenance.json` pins exact raw client URL `https://piston-data.mojang.com/v1/objects/e575a48efda46cf88111ba05b624ef90c520eef1/client.jar`, SHA-256 `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`; Mojmap mapping URL ends in `/objects/a4cd9a97400f7ecfe4dba23e427549ebc5815d66/client.txt`, SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`. Exact 1.20 version metadata URL ends `/packages/9e7847f933f4e81e45dd1dfb24bab7d4324ca023/1.20.json`, SHA-256 `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`.
- Verified 1.20 source SHA-256: `client/player/LocalPlayer.java` `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2`; `world/entity/player/Player.java` `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac`; `world/entity/LivingEntity.java` `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f`.
- The same three whole-file SHA-256 values were computed from the 1.20.1 directory. The output tree has no provenance manifest, and its exact raw artifact could not be identified locally. Thus these are content identities only, not provenance claims for 1.20.1.

## Claims

### C1 — LocalPlayer request ordering and climbable-start gate: no observed difference

`LocalPlayer.aiStep`, 1.20 `LocalPlayer.java:718–745, 801`; candidate 1.20.1 file, same lines. After the abilities-flight toggle handling, the request requires `input.jumping && !$$10 && !$$0 && !abilities.flying && !isPassenger() && !onClimbable()`. It then checks the chest item is `Items.ELYTRA`, `ElytraItem.isFlyEnabled`, and `tryToStartFallFlying()` before sending `START_FALL_FLYING`. `tryToStartFallFlying()` sets shared flag 7 locally before that packet is sent. The client then assigns `wasFallFlying = isFallFlying()` and reaches `super.aiStep()` at line 801, so the newly active state can be used by that tick's downstream fall-flying update/travel path.

`LivingEntity.onClimbable`, 1.20 `LivingEntity.java:1478–1494` and candidate 1.20.1 same lines, returns true for `BlockTags.CLIMBABLE` at the feet after recording `lastClimbablePos` (spectators return false). Therefore the stated ladder/vine-feet case fails the request gate before the Elytra checks; the ordinary non-climbable feet case can pass this gate if the other listed predicates hold. The two available files are byte-identical, but the newer file provenance remains unverified.

### C2 — Start/active flag and updater cancellation order: no observed difference

`Player.java:1576–1595` in both available trees: `tryToStartFallFlying` requires airborne, not already fall-flying, not in water, and no Levitation; it repeats the chest Elytra/flight-enabled check, sets shared flag 7 true through `startFallFlying`, and returns true. `stopFallFlying` executes `setSharedFlag(7, true)` followed immediately by `setSharedFlag(7, false)`.

`LivingEntity.java:2610–2635` in both available trees: `updateFallFlying` reads shared flag 7 first. Only if it is set, the entity is airborne, not a passenger, and not under Levitation does it check for an enabled chest Elytra. Failure at either gate clears the local candidate flag; success keeps it set. Durability/event handling uses `fallFlyTicks + 1` and is server-only. Only the logical server writes the resolved flag back via `setSharedFlag(7, $$0)`. `isFallFlying` reads shared flag 7 at `LivingEntity.java:3032–3034` in both trees. No updater, flag, or cancellation-order difference was observed.

### C3 — First glide branch, climbable cleanup, and numeric cap: no observed difference

`LivingEntity.travel`, 1.20 `LivingEntity.java:2004–2112` and candidate 1.20.1 same lines: after the water/lava cases, the fall-flying branch is selected by `isFallFlying()` before the ordinary movement branch. The ordinary branch calls `handleRelativeFrictionAndCalculateMovement`; that helper performs `handleOnClimbable` before movement. The glide branch instead calculates a new velocity and calls `move(MoverType.SELF, ...)` without that climbable cleanup helper. For the requested feet-climbable precondition, the LocalPlayer start gate already rejects glide entry.

Within the glide branch, pitch radians are stored in `float`; look/velocity calculations and `Math.min(1.0, lookLength / 0.4)` use double expressions; the velocity is then multiplied by `0.99F, 0.98F, 0.99F` before the first `move`. In `LivingEntity.tick`, `aiStep()` completes at line 2278 before the fall-flying counter is incremented at lines 2348–2352. Since LocalPlayer sets flag 7 before its `super.aiStep()` call, the first glide move uses the glide branch with the pre-increment counter phase; on a fresh glide that counter is still zero during the move. The updater's `fallFlyTicks + 1` gate consequently observes phase one. This ordered phase, cap/math, and branch route match byte-for-byte in the available files. The evidence does not establish the exact 1.20.1 source origin.

## Exact source references

All paths are relative to the exact version directory shown below; all are Mojmap decompiler output.

- `1.20`: `net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 647–745 and `super.aiStep()` at 801; SHA-256 above.
- `1.20`: `net/minecraft/world/entity/player/Player.java`, `tryToStartFallFlying`, `startFallFlying`, `stopFallFlying`, lines 1576–1595; SHA-256 above.
- `1.20`: `net/minecraft/world/entity/LivingEntity.java`, `onClimbable`, lines 1478–1494; `tick` invokes `aiStep()` at 2278 and updates the fall-flying counter at 2348–2352; `travel`, lines 2004–2112; `updateFallFlying`, lines 2610–2635; `isFallFlying`, lines 3032–3034; SHA-256 above.
- `1.20.1`: same Mojmap relative files and methods/line ranges; computed whole-file hashes equal the corresponding 1.20 hashes. No provenance manifest or cached exact 1.20.1 raw client artifact was present.

## Coverage boundary / next action

The requested narrow 1.20 source route was inspected. To turn this into an exact adjacent-pair result, obtain the preparation owner's exact 1.20.1 Mojmap provenance manifest and pinned raw client/mapping identities, or have that owner prepare the exact source in the canonical tree. Then independently recheck the same paired methods. Do not infer an intermediate change boundary or freeze pair coverage from these identical files.
