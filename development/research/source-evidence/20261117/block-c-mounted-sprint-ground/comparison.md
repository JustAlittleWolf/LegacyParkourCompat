# Mounted local-player sprint admission and jump handoff

## Scope and result

Static source comparison of canonical Mojmap decompilations for 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3 and 1.19.4. The terminal change is in the local-player sprint-admission predicate in `LocalPlayer.aiStep()`: 1.19.3 first accepts a grounded vehicle for the double-tap path, and 1.19.4 factors admission into a shared predicate that additionally restricts passenger sprinting to a locally controlled vehicle whose class opts in via `Entity.canSprint()`. The modern held-sprint-key path has no ground test in either endpoint; it shares the new vehicle eligibility restriction in 1.19.4.

This is a distinct player movement operation. The sprint flag is written on the player, survives `stopRiding()` without a clear in the inspected path, and is consumed by the later player jump: `LivingEntity.jumpFromGround()` adds a facing-direction horizontal impulse of `0.2F` when `isSprinting()` is true. No vehicle velocity behavior is included.

## Exact source identities

All files below are under `decompiled_minecraft/<version>/mojmap/`. The local `mojmap.ready.json` marker and `mojmap.provenance.json` identify each exact Minecraft version and Mojmap namespace. The source-manifest file SHA-256 values below match `sourceManifestSha256` in the corresponding ready marker. Each `LocalPlayer.java` hash matches its exact entry in `mojmap.sources.sha256`.

| Version | Provenance run | Client JAR SHA-256 | Source manifest SHA-256 | LocalPlayer.java SHA-256 |
|---|---|---|---|---|
| 1.18.2 | Mojmap batch 1.15.2, 1.16.5, 1.17.1, 1.18.2; Vineflower 1.12.0; created 2026-10-07 | `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| 1.19 | Mojmap single-version run; Vineflower 1.12.0; created 2026-10-10 | `ffd4fb3037d4110085b4d3d2dc47b760695528eb14292c3e5f7c758983c6d764` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.19.1 | Mojmap single-version run; Vineflower 1.12.0; created 2026-10-10 | `146d4018cfed50c5e77483940653091f186d2eea9ad42fc40939f5c72d6b7f52` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19.2 | Mojmap batch 1.19.2, 1.19.3, 1.19.4; Vineflower 1.12.0; created 2026-10-07 | `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19.3 | Same Mojmap batch as 1.19.2; created 2026-10-07 | `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |
| 1.19.4 | Same Mojmap batch as 1.19.2; created 2026-10-07 | `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |

The 1.18.2 provenance record does not contain its mapping-artifact/client-mapping hashes (`mappingArtifacts: null`); its exact version, Mojmap namespace, ready marker's client hash, and source manifest are present. This is a provenance limitation, not a missing source body. No online mapping data was used.

## Version comparison

### 1.18.2 through 1.19.2

`net/minecraft/client/player/LocalPlayer.java`, `aiStep()` (1.18.2 lines 642-706; 1.19 lines 725-790; 1.19.1 and 1.19.2 lines 731-796): the initial double-tap candidate requires `this.onGround || this.isUnderWater()`. `sprintTriggerTime` is decremented first; when the initial candidate otherwise passes, timer `<= 0` with sprint key up arms it to `7`, otherwise sprint is set. Common gates require no shift input, sufficient forward impulse (`forwardImpulse >= 0.8`, except underwater uses `input.hasForwardImpulse()`), not already sprinting, enough food or `mayfly`, not using an item, and no blindness. The separate key route sets sprint when the player is not already sprinting, not in water unless underwater, has enough impulse/food, is not using an item, has no blindness, and `keySprint.isDown()`.

Neither admission route checks `isPassenger()` or vehicle ground in these exact bodies. In particular, the double-tap ground source is the player's `onGround`, not the vehicle's.

### 1.19.3

`LocalPlayer.java`, `aiStep()` lines 652-717: the double-tap ground predicate adds `this.isPassenger() && this.getVehicle().isOnGround()` to the prior player-ground/underwater disjunction (lines 694-706). This allows a client passenger with forward impulse at least `0.8`, a grounded vehicle, and the other common gates to arm/complete double-tap sprint even while the player itself is not grounded. The key route at lines 709-717 remains separate and has no ground requirement.

The food gate changes to `hasEnoughFoodToStartSprinting()` (lines 1047-1049), which returns true for a passenger regardless of food level. The helper's impulse threshold remains `0.8` forward outside underwater state. In this version the common candidate does not require a vehicle sprint capability, so the vehicle-on-ground addition is not limited by `canSprint()` in this source.

### 1.19.4

`LocalPlayer.java`, `aiStep()` lines 645-718: the code calculates `canStartSprinting()` once and uses it for both the double-tap and held-key routes. The double-tap ground source is `vehicle.isOnGround()` for a passenger, otherwise player `onGround` (lines 685-694); underwater remains an alternate ground condition. The held-key route at lines 696-698 remains ungated by ground.

`canStartSprinting()` lines 1022-1029 requires: not already sprinting; enough impulse; enough food; not using an item; no blindness; if riding, `vehicleCanSprint(vehicle)`; and not fall-flying. `vehicleCanSprint()` lines 1032-1034 requires both `vehicle.canSprint()` and `vehicle.isControlledByLocalInstance()`. `hasEnoughFoodToStartSprinting()` lines 1041-1043 retains the passenger food exemption. `Entity.canSprint()` defaults false (Entity.java lines 3163-3165); the examined era rideable opt-in is `Camel.canSprint()` returning true (Camel.java lines 276-278). Thus the bounded ordinary client witness is a locally controlled, grounded camel with forward input: double-tap (or held sprint key) sets the local player's sprint flag; a non-opted-in vehicle does not pass the mounted admission predicate.

## Player-state handoff and movement consumer

`LocalPlayer.setSprinting(boolean)`, 1.18.2 lines 448-451 and 1.19.3 lines 449-452 (the same behavior in inspected releases), delegates to `super.setSprinting` and resets `sprintTime`; it does not special-case passengers. The sprint status is a player state, not vehicle state.

`Entity.removeVehicle()`, 1.19.4 lines 1798-1804, clears `vehicle` and removes the passenger; `Entity.stopRiding()` lines 1806-1808 calls it. Neither method writes sprint state. `Player.rideTick()`, 1.19.4 lines 476-489, stops riding when `wantsToStopRiding()` on the non-client path; it does not clear sprint. This establishes that dismounting itself does not consume/clear the player sprint flag in the inspected path. The independent client input/packet flow for requesting the dismount is outside this bounded slice; the server authoritative stop path and the client's player flag consumer were inspected.

`LocalPlayer.aiStep()` only offers the ordinary player jump path after `!isPassenger()` (1.19.4 lines 732-736 for the adjacent elytra guard; normal jump is dispatched through LivingEntity's jump handling). `LivingEntity.jumpFromGround()`, 1.18.2 lines 1981-1990 and 1.19.4 lines 1955-1966, first sets vertical jump power, then, if `isSprinting()`, adds `(-sin(yRot * PI/180) * 0.2F, 0, cos(yRot * PI/180) * 0.2F)` to delta movement, then sets `hasImpulse`. `Player.jumpFromGround()` delegates to that implementation (1.18.2 lines 1411-1418; 1.19.4 lines 1429-1436) and changes exhaustion only. Therefore a reachable held-forward mounted sprint followed by dismount and a grounded player jump can carry the sprint flag into a direct player horizontal jump impulse. This is a source-level reachable witness; no simulated trajectory is claimed.

## Coverage and exclusions

- Compared exact Mojmap local-player sprint admission, timer initialization/decrement, direct held sprint key, forward impulse and food gates for all six listed versions.
- Compared local-player sprint setter, player stop-riding paths, and `LivingEntity`/`Player` jump consumers for 1.18.2 and 1.19.4; intermediate versions showed no divergent handoff in the compared method bodies.
- Confirmed 1.19.4 default entity `canSprint()` false and camel opt-in. Vehicle motion/velocity production, client/server movement prediction parity, and full per-tick input networking are outside assignment.
- No tests, launches, implementation, wiki, product code, or Git history were read or used.

## Source references

- 1.18.2: `net/minecraft/client/player/LocalPlayer.java` (`aiStep`, `setSprinting`), `net/minecraft/world/entity/LivingEntity.java` (`jumpFromGround`), `net/minecraft/world/entity/player/Player.java` (`jumpFromGround`).
- 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4: `net/minecraft/client/player/LocalPlayer.java` (`aiStep`, `setSprinting`, sprint helpers), `net/minecraft/world/entity/Entity.java` (`stopRiding`, `removeVehicle`, `canSprint`), `net/minecraft/world/entity/player/Player.java` (`rideTick`, `jumpFromGround`), `net/minecraft/world/entity/LivingEntity.java` (`jumpFromGround`). 1.19.4 additionally: `net/minecraft/world/entity/animal/camel/Camel.java` (`canSprint`).