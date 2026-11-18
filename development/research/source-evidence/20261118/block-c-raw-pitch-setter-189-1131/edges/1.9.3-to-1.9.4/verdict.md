# Lead C: direct pitch and active Elytra source audit

## Verdict

No 1.9.3 → 1.9.4 change is present in the ordinary direct local camera pitch update or in the earliest stored-pitch use during dry, active, nonpassenger Elytra movement. The proposed pitch-order/clamp boundary is not source-supported for this pair. The finite witness for a cross-version difference is therefore invalid: the exact paired operation is identical for all finite inputs (and, more generally, both decompiled bodies express the same Java operations).

## Exact source and provenance identity

Both sources are Ornithe Feather (`ornithe-feather`; Feather mappings), from `decompiled_minecraft/<version>/ornithe-feather/`.

- 1.9.3 is the legacy mapping-root tree. Its `.decompile-provenance.json` pins Mojang 1.9.3 client `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`, Feather metadata for 1.9.3, and `feather-gen2-1.9.3+build.2-mergedv2.jar` `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`. Each cited 1.9.3 Java source hash below matches that manifest.
- 1.9.4 is the version-root tree. `ornithe-feather.provenance.json` identifies exact version 1.9.4, Feather mapping option, `feather-gen2-1.9.4+build.2-mergedv2.jar` and `.tiny`; `artifacts.sha256` pins the merged jar to `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` and the Tiny file to `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`. The published exact source tree is checked by `ornithe-feather.sources.sha256`; `ornithe-feather.verification.json` records 1,819 sources verified identical to an independent exact 1.9.4 Feather decompile. Each cited 1.9.4 Java source hash below matches the source manifest.

Literal SHA-256 of the cited Java source bytes (verified against the root sidecars/manifests):

| Source | 1.9.3 | 1.9.4 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | same |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | same |
| `net/minecraft/client/render/GameRenderer.java` | `e08c2034c7fd4621a76ff5a4ba26dd80e065c895bc5c0a4120d4d36141203d6b` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |

`PlayerEntity.java` differs elsewhere, but its `moveRelative` override at lines 1372–1390 is identical in the compared releases: normal Elytra use (abilities flight false) calls `super.moveRelative`; the ability-flight override is gated by `abilities.flying && !isRiding()` and is not that path.

## Direct local turn pitch

`GameRenderer.java:836–855` passes the ordinary local mouse deltas to `player.updateLocalPlayerCamera`; both smooth-camera and direct branches call the same method in both releases. `Entity.java:286–297` is byte-identical in the pair. It snapshots old pitch in float `f`, computes `this.pitch - pitch * 0.15` using the double literal and then narrows the result to float, clamps that current pitch to `[-90.0F, 90.0F]`, and only then adds the clamped-current-pitch delta `(this.pitch - f)` to `lastPitch`. The method and operation order already exist in 1.9.3; neither cast placement nor clamp placement was introduced in 1.9.4.

For a finite illustration, with current pitch `80.0F` and passed pitch delta `500.0F`, both releases evaluate the double expression `80.0 - 500.0 * 0.15`, narrow to `5.0F`, clamp to `5.0F`, then update `lastPitch` by the float delta `5.0F - 80.0F`. This is the same trace on both sides, not a boundary witness.

## Earliest stored-pitch use in the reachable Elytra movement path

`LivingEntity.java:1706–1712` orders the movement tick as `flyingTick()` followed by `moveRelative(sidewaysSpeed, forwardSpeed)`. The active-flight gate in `flyingTick()` at `1718–1736` requires flag 7, not grounded, not riding, and a usable chest-slot Elytra; `isFallFlying()` at `2052–2054` reads flag 7. For a local player, `PlayerEntity.moveRelative` reaches the superclass path when creative/ability flight is false. In `LivingEntity.moveRelative` at `1302–1312`, the relevant branch requires local/logical movement control, dry conditions (unless the player has ability flight), and `isFallFlying()` true.

The first pitch-dependent read in that active branch is `this.getLookVector()` at line 1311. `LivingEntity.getLookVector()` at `1817–1819` calls `getRotationVector(1.0F)`, whose `tickDelta == 1.0F` branch at `1822–1825` immediately calls `getRotationVector(this.pitch, this.headYaw)`. The branch then explicitly reads stored pitch again at line 1312 (`this.pitch * (float)(Math.PI / 180.0)`) for the Elytra velocity math. `Entity.java:1114–1129` supplies the exact rotation-vector trig procedure. These cited methods and callers are source-identical across the pair; no differing pitch producer or earliest consumer is present here.

## Scope conclusion

Reject a 1.9.3 → 1.9.4 accepted finding for direct local turn pitch operation order/clamp or an Elytra pitch witness based on that alleged boundary. This bounded check does not establish patch introduction elsewhere in the 1.9.x interval and does not assert whole-pair movement coverage.

## Independent audit of the source report

I rehashed the author input before and after comparison; it remained `50eb80904e7bcc5f457f8c8d3f5dc5a66cfe85c8e686950556a9241ba3099cf6`. I independently confirm the direct setter math, its cast/clamp/previous-delta order, the finite trace (`89.0F`, `88.0F`, input `-20.0F` → current `90.0F`, previous `89.0F`), exact 1.9.3/1.9.4 no-difference result, and the active dry nonpassenger Elytra steering branch. The witness validly distinguishes clamp-before-previous-delta from a hypothetical previous-before-clamp sequence; it does not witness a 1.9.3/1.9.4 difference, because both versions execute the same sequence.

The author report also correctly narrows “earliest pitch read”: `getLookVector()` is the first stored-pitch read within the Elytra steering branch, not necessarily the earliest pitch-field read in the whole local player tick. When the local player is the camera, `LocalClientPlayerEntity.serverTickAi()` reads `this.pitch` to update `easedPitch` before travel; if the interpolation gate passes, `LivingEntity.mobTick()` reads it earlier still at the conditional `lerpPitch` update. Those reads are outside Elytra steering. This refines the wording of my prior result without changing its no-difference verdict.