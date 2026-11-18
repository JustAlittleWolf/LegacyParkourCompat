# Independent source audit freeze: 1.14.3 → 1.14.4 ability-flight/glide clear

## Frozen before reading the source draft

Audit scope: local dry, airborne, nonpassenger player; fall-flying flag 7 active; default enabled chest Elytra; lawful Creative flight with `abilities.flying == true`; no collision during the movement. Static exact-source audit only. No runtime, game, tests, Gradle, network, source generation, product Git, backlog, wiki, or implementation inspection.

Canonical source trees inspected read-only:

- 1.14.3 Yarn: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.14.3/yarn/`.
  - Hidden `.decompile-provenance.json` SHA-256: `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`.
  - Provenance records the exact Mojang client `1.14.3` input SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`, metadata input SHA-256 `ebf2df95d9bd83c6687f287d5a6f5f41f74bc1b39159fcbb74ff6d1d19d8464c`, Yarn `1.14.3+build.13` mapping artifact SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`, and Yarn metadata pin `85ad2c9e3ff892f5a6d5b774b869ed6b6dbdd26de9a46d1a37b5c739e73c8766`.
  - Class hashes equal their frozen provenance entries: `PlayerEntity.java` `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963`; `LivingEntity.java` `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b`; `Entity.java` `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14`.
- 1.14.4 Mojmap: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.14.4/mojmap/` (published source tree; no staging tree used).
  - `mojmap.provenance.json` SHA-256: `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`.
  - `mojmap.sources.sha256` SHA-256: `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; published verification records `verified-identical-source-and-input-artifacts`, 3,250 source files verified, zero differences, and this same manifest digest.
  - Raw client `1.14.4` SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; metadata SHA-256 `615f466a39d2c19ae9b6e2401aa7bd07dbda337f976bcce383326b8d6ba4e532`.
  - The published source manifest hashes match the files: `Player.java` `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`; `LivingEntity.java` `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`; `Entity.java` `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`.

## Independent trace, frozen before draft review

- Normal tick travel dispatch is `LivingEntity.tickMovement` → virtual `this.travel(...)` in 1.14.3 (`LivingEntity.java:2214–2221`) and 1.14.4 (`LivingEntity.java:2217–2224`). In 1.14.4 `updateFallFlying()` runs just before travel. Under this client-side precondition it retains the active flag when the default chest Elytra is enabled, the player is airborne and not a passenger; it only writes the flag when `!level.isClientSide` (`LivingEntity.java:2235–2254`).
- The player override takes the ability-flight branch when `abilities.flying && !hasVehicle()/!isPassenger()`: 1.14.3 `PlayerEntity.travel(Vec3d)`, lines 1364–1392; 1.14.4 `Player.travel(Vec3)`, lines 1380–1408. It stores starting vertical velocity, temporarily sets flying speed, and calls `super.travel`.
- With flag 7 still active and local dry movement, `LivingEntity.travel` enters the Elytra branch in each release and calls the actual `move(SELF, velocity)` after computing and applying glide velocity (1.14.3 lines 1790–1839; 1.14.4 lines 1793–1842). `Entity.move` applies the actual box/position displacement and calls the fall updater (1.14.3 `Entity.java:441–489`, `fall` at 870–879; 1.14.4 `Entity.java:446–493`, `checkFallDamage` at 894–903). For the specified airborne state, an actually downward move increments `fallDistance` during this move stage.
- After `super.travel` and its move return, the player override restores the initial vertical component scaled by `0.6`, then sets `fallDistance = 0.0F`, then unconditionally clears flag 7 (1.14.3 `PlayerEntity.java:1382–1386`; 1.14.4 `Player.java:1398–1402`). These are distinct sequential writes; the fall-distance reset is not a consequence of clearing the fall-flying flag. `LivingEntity.travel`'s own flag clear is only for landing on a nonclient logical side, so it does not supersede the local player override here.
- The compared player travel bodies preserve this sequence and gate across the named releases. The 1.14.4 added pre-travel Elytra-state check does not write the local client flag in this scenario. Frozen finding hypothesis: no player movement behavior difference for this exact bounded scenario; a report claiming that one release lacks the local post-move flag clear or that the clear changes `fallDistance` is unsupported by these sources.

This source identity and trace were frozen before opening `c-mid-1143-1144-ability-flight-glide-clear-source/report.md`. Draft SHA supplied by the coordinator for post-freeze review: `aacaf2fcb3fac8fa6d79db097ffaf7c808c66da2f955bd4fa1f9b7afdb88a9bf`.

## Post-freeze independent review

Reviewed only after the frozen source record above was written. The source report SHA-256 is `aacaf2fcb3fac8fa6d79db097ffaf7c808c66da2f955bd4fa1f9b7afdb88a9bf`, matching the assigned immutable input. The frozen pre-review record SHA-256 was `98fcc8d668540671f5e566682bd2071d2e00ba22bd54d27f7af5c197757673cf`.

**Verdict: accept the report's no-difference conclusion for the bounded local-player operation.** Its main claim is independently supported by the frozen sources: both `Player.travel` overrides enter superclass travel before the wrapper restores Y as saved-Y × `0.6`, clears `fallDistance`, and then clears flag 7. During the requested airborne local-client move, both superclass Elytra branches call actual `move` before the player wrapper resumes. `Entity.move` invokes the fall updater, so any downward movement may update `fallDistance` in that inner phase; the later wrapper's explicit zero assignment is a separate post-move write. The superclass land-triggered flag clear is gated on landing plus nonclient side and is not the local path here.

I also checked the report's additional local-trigger claim after the freeze: the cited 1.14.3 `ClientPlayerEntity.java` SHA matches its hidden-provenance source hash (`28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae`), and 1.14.4 `LocalPlayer.java` SHA matches its published source manifest (`0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`). The lawful-flight toggle occurs before the Elytra-start check in each method; the start check requires `!abilities.flying`, but does not clear an already active flag. In 1.14.4 the pre-travel `updateFallFlying` only writes flag 7 when not client-side. These details support reachability of the report's already-active-flag scenario and do not introduce a client-side difference.

The report's conclusion is correctly bounded; it does not establish full-pair player-tick coverage or runtime parity. No source-backed finding is warranted for this operation in 1.14.3 → 1.14.4.
