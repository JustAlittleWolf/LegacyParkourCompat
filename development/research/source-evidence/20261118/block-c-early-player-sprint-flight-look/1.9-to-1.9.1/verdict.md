# Independent audit verdict: 1.9 → 1.9.1 sprint, ability-flight reset, sneak-flight input

## Input and evidence identity

- Audited report: `c-early-19-191-sprint-flight-input/report.md`
- Audited report SHA-256: `b3f454307d47f01d176891d11e60b006cbe8b9c6a0412549fbeb7c8ea5f75f0a`
- Source roots: `decompiled_minecraft/1.9/ornithe-feather/` and `decompiled_minecraft/1.9.1/ornithe-feather/`.
- Local `.decompile-provenance.json` SHA-256s match the report: 1.9 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`; 1.9.1 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`. Their recorded Feather Gen2 build 2, Mojang client, and Feather metadata input hashes match the report. The manifests record the same `lpc-decompile-v1`/Vineflower 1.12.0 source pipeline.
- Independently recomputed hashes for all six cited source files match their local provenance manifests and the report's table. In each version, `Input.java` and `KeyboardInput.java` hashes are identical across the pair.

## Claim verdicts

### 1. Sprint timer and local transition dispatch — ACCEPT

The report's no-difference claim is supported. In both releases, `doubleTapSprintTime` and `sprintTimer` are uninitialized `int` fields, so Java's default initialization applies. `mobTick` increments `sprintTimer` first, then decrements a positive `doubleTapSprintTime` before sampling old input state and ticking fresh input. The item-use/non-riding branch scales both axes by `0.2F` and clears the double-tap timer. The ground double-tap path sets the timer to `7` or starts sprinting; the held sprint-key path starts it directly; an active sprint ends for forward input below `0.8F`, horizontal collision, or failed shared eligibility. Eligibility's internal predicates are outside this claim. The local `setSprinting` calls `super` before resetting `sprintTimer` to zero. In `tick`, the superclass tick is chunk-load gated and movement dispatch follows it on the non-riding path. `sendMovementToServer` sends START/STOP only when `isSprinting()` differs from `sentSprinting`, updates the cache after the send, then handles sneak and camera position/angle packets. The sprint flag implementation is identical in `Entity`; inherited `LivingEntity.setSprinting` removes an existing modifier and conditionally re-adds it in both versions.

Evidence: `LocalClientPlayerEntity.java` 1.9 lines 84–85, 141–167, 401–405, 599–605, 641–684; 1.9.1 lines 85–86, 142–168, 402–406, 600–605, 642–685, plus `sendMovementToServer` 1.9 lines 157–218 / 1.9.1 lines 158–219. `Entity.java` 1604–1610 and `LivingEntity.java` 1167–1176 / 1171–1181 respectively.

### 2. Ability-flight `fallDistance` reset — ACCEPT

The report correctly states the gate, sequence, and no-difference result. Both `PlayerEntity.moveRelative` implementations enter the special path only for `abilities.flying && !isRiding()`. They save vertical velocity as `double` and `speedInAir` as `float`, assign fly speed multiplied by sprint's integer factor, call `super.moveRelative`, multiply saved vertical velocity by `0.6`, restore `speedInAir`, reset `fallDistance` to `0.0F`, and clear flag 7. The ordinary/vehicle branch has no reset. In both versions, `LivingEntity` decays sideways/forward speeds by `0.98F`, calls `flyingTick`, then calls `moveRelative`.

Evidence: `PlayerEntity.java` 1.9 and 1.9.1 lines 1365–1381; `LivingEntity.java` 1.9 lines 1693–1699 and 1.9.1 lines 1697–1703.

### 3. Sneaking-flight input rescale — ACCEPT

The report correctly traces the keyboard and local-player operations with their types, gate, and order. In both releases `KeyboardInput.tick` builds float axes, sets the key booleans, then while sneaking multiplies each axis by unsuffixed double literal `0.3` and casts back to `float`. `LocalClientPlayerEntity.mobTick` snapshots the previous input booleans, refreshes input, applies optional item-use `0.2F` axis scaling (only when not riding), processes sprint and flight toggles, then only for `abilities.flying && isCamera()` divides each axis by unsuffixed `0.3` with a float cast when sneaking. Sneak vertical adjustment precedes jump vertical adjustment. `LivingEntity` subsequently decays axes by `0.98F` before `moveRelative`. The relevant bodies and operation ordering match across the pair.

Evidence: `KeyboardInput.java` 1–50 in both; `LocalClientPlayerEntity.java` 1.9 lines 641–650, 710–720 and 1.9.1 lines 642–651, 712–722; `LivingEntity.java` 1.9 lines 1693–1699 and 1.9.1 lines 1697–1703.

## Scope and result

All three claims are independently accepted as accurate bounded no-difference findings. The report correctly limits its conclusion to these operations and explicitly leaves the pair short of full tick/inventory coverage. The 1.9.1 Elytra sound addition is outside the claims and does not alter the reviewed operations. No additional finding or broader pair-coverage conclusion follows from this audit.
