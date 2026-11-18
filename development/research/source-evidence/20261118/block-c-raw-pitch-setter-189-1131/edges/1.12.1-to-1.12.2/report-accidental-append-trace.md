# 1.12.1 → 1.12.2 pitch setters and active dry Elytra read

## Result

The relevant pitch writer, current/previous pitch ordering, local-player gate, and dry fall-flying movement body are unchanged between these exact mapped source trees. The operation therefore has no source difference at this pair. This is a bounded no-difference result for the requested slice, not a full-pair coverage or acceptance claim.

A readiness qualification remains: 1.12.2 has a ready marker tied to its source and artifact manifests. The direct 1.12.1 source tree has a matching exact-input `.decompile-provenance.json` whose per-file hashes verify, but it has no adjacent `ornithe-feather.ready.json` or `ornithe-feather.sources.sha256`. Do not treat the old `.ornithe-feather.previous-*` sibling as the canonical source. Source content and exact-input provenance are present; canonical ready status is absent.

## Exact source identities

- **1.12.1 / Ornithe Feather:** `decompiled_minecraft/1.12.1/ornithe-feather/`. Its `.decompile-provenance.json` pins the Feather mapping artifact `feather-gen2-1.12.1+build.2-mergedv2.jar` (SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`), Feather metadata URL ending in `/1.12.1` (SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`), exact Mojang client object (SHA-256 `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`), and 1.12.1 version metadata. The manifest itself hashes to `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.
- **1.12.2 / Ornithe Feather:** `decompiled_minecraft/1.12.2/ornithe-feather/`. Ready marker declares version `1.12.2`, mapping `ornithe-feather`, 2,050 source files and the exact client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`. Its source-manifest SHA-256 is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; artifact-manifest SHA-256 is `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`. Both on-disk manifest hashes match the ready marker. Mapping artifacts are the 1.12.2 Feather build.2 merged-v2 jar and Tiny mapping listed by its provenance.

The exact relevant file hashes (manifest value and current file hash agree on both sides) are:

| Source file | 1.12.1 SHA-256 | 1.12.2 SHA-256 | Byte-identical? |
| --- | --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | yes |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | yes |
| `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` | yes |

`LocalClientPlayerEntity.java` differs as a whole (1.12.1 `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1`; 1.12.2 `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc`). Its relevant `isLocallyControlled()` override is identical on both sides and returns `true` (1.12.1 lines 484–486; 1.12.2 lines 485–487). The local Elytra-start input block shifts by lines but its relevant gate is the same; the `LivingEntity` movement body containing active glide is byte-identical.

## Compared behavior

### Ordinary local camera update and setter widths

In `GameRenderer`, both smooth-camera and ordinary-camera paths call `player.updateLocalPlayerCamera(h, i * j)` with float locals (`GameRenderer.java:856–868`, unchanged). The method signature is `(float yaw, float pitch)`. In `Entity.updateLocalPlayerCamera` (`Entity.java:307–316`, unchanged), the prior current pitch is saved in a `float`; the new pitch is computed as `(float)(this.pitch - pitch * 0.15)` (the unsuffixed `0.15` makes the product double before the cast), then the **current** pitch is clamped to `[-90.0F, 90.0F]`. Only after that, `lastPitch` is updated by adding `(clamped current pitch - saved old current pitch)`. It does not clamp `lastPitch`. Both versions preserve this float/double boundary and current-before-previous ordering.

The direct position setter is also float-width: `Entity.setPositionAndAngles(double x, double y, double z, float yaw, float pitch)` assigns `this.pitch = pitch` verbatim (`Entity.java:1122–1135`), and does not update `lastPitch`. This path is distinct from the camera updater. The protected `setRotation(float yaw, float pitch)` applies `% 360.0F` (`Entity.java:293–296`); `updatePositionAndAngles` first clamps the float parameter to `[-90.0F, 90.0F]`, writes current pitch, then writes `lastPitch = this.pitch`, and later calls `setRotation(yaw, pitch)` (`Entity.java:1093–1116`). Thus a finite raw direct-setter witness such as `pitch = 120.0F` remains 120 in `setPositionAndAngles`; the network/update-position method instead stores 90 in both current and previous pitch for that input. No such raw overrange value is produced by the ordinary mouse-camera updater, whose current value is clamped.

A finite witness also makes the camera updater's unclamped previous-state order concrete: starting with `pitch=80.0F`, `lastPitch=85.0F`, and camera pitch delta `-1000.0F`, the float setter input is finite; the calculated current value clamps to `90.0F`, then `lastPitch` becomes `85.0F + (90.0F - 80.0F) = 95.0F`. This witnesses that clamping current pitch does not clamp the previous field. It is a method-level arithmetic witness, not a claim that those exact initial values are a normal gameplay trace.

### Earliest stored-pitch use in active dry local Elytra movement

`LivingEntity` movement calls `flyingTick()` and then `moveRelative(...)` in its travel phase (`LivingEntity.java:1841–1848`, identical files). The local player’s `isLocallyControlled()` returns true, satisfying the first movement gate. In `moveRelative` (`LivingEntity.java:1425–1464`), the dry path passes the water and lava exclusions, and active fall-flying enters the glide branch. The **first actual current-pitch read in that branch** is `this.getLookVector()` (`LivingEntity.java:1434`): `Entity.getLookVector()` passes the stored `this.pitch` directly to `getRotationVector(this.pitch, this.yaw)` (`Entity.java:1690–1692`), whose trigonometry uses that float pitch (`Entity.java:1239–1245`). The next explicit pitch read is `float i = this.pitch * (float) (Math.PI / 180.0)` at `LivingEntity.java:1435`; the resulting `i` feeds `cos(i)` and the descent/turn adjustments. This is a client movement branch (`world.isClient` only suppresses the wall-damage side effect after movement), with the local predicate true.

As a finite overrange movement witness for the raw setter, if a local player’s current pitch is set to `120.0F` through `setPositionAndAngles`, the earliest active dry-glide read consumes `120.0F` through `getLookVector()`; the following explicit angle conversion also sees 120 degrees. For comparison, the camera updater clamps an updated current pitch before this same consumer. This documents setter distinction and the reachable consumer, not a claim that the raw setter itself is the ordinary mouse-input route.

## Finding disposition

No 1.12.1 → 1.12.2 behavior delta is supported for this bounded operation: pitch setter widths/order and the active dry local Elytra consumer are byte-identical in `Entity.java` and `LivingEntity.java`; the local control override is text-identical. Retain the 1.12.1 ready-marker/readiness gap as an evidence gate for pair acceptance. Intermediate patch boundaries are not established by this endpoint comparison.

## Independent Lead C audit verdict

VERIFIED: I independently confirm the bounded no-difference verdict for 1.12.1 → 1.12.2. The ordinary camera updater retains the same float inputs, double `0.15` product, current-pitch clamp, and subsequent `lastPitch += newCurrent - oldCurrent` ordering. Its finite overrange result clamps current pitch; it does not clamp the previous field. The first active dry local Elytra pitch use is `getLookVector()` in `LivingEntity.moveRelative`, followed by the explicit pitch-to-radians read; both consume stored pitch without a new clamp. `Entity.java` and `LivingEntity.java` are byte-identical; the full local-player class differs, while its relevant local-control override remains text-identical.

The 1.12.1 source-preparation gate remains separate and open: the current nested tree and its exact-input provenance/per-file hashes support source-byte identity, but it has no adjacent ready marker or source-hash manifest. The retained `.ornithe-feather.previous-*` tree is not a substitute canonical publication. Matching source hashes establish no behavior delta in these methods; they do not close the 1.12.1 ready/publication gate or establish full-pair coverage/acceptance.
