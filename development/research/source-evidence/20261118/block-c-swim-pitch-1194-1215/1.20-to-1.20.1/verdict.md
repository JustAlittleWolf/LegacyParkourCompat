# Frozen blind source audit: Player underwater swim pitch correction

## Verdict

**No source change in the audited operation from Minecraft 1.20 (1.20.0) to 1.20.1.** The whole decompiled Mojmap source files for `Player`, `Entity`, `LivingEntity`, `Mth`, and `LocalPlayer` have identical SHA-256 bytes on both sides. This closes the assigned swim-pitch correction slice as a no-difference result; it does not claim full-pair movement coverage or pair freeze.

## Exact source identity and readiness

- Older side: Minecraft release ID `1.20` (1.20.0), Mojmap, `decompiled_minecraft/1.20/mojmap/`.
  - The in-tree `.decompile-provenance.json` is present (SHA-256 `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`). It identifies the deterministic `lpc-decompile-v1` pipeline, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Java 25.0.3+9-LTS on Windows 11 amd64, and exact input/source hashes.
  - The manifest’s four player-path source hashes match the bytes present. Local cached release inputs were also checked against their `.pin.json` SHA-256s: `client.jar` `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`, official `client_mappings.txt` `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`, and `version.json` `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`; all local bytes match the pins.
- Newer side: Minecraft release ID `1.20.1`, Mojmap, `decompiled_minecraft/1.20.1/mojmap/`.
  - Recognized root sidecars are intact: `mojmap.provenance.json` SHA-256 `5aeb1df09671480aeddb921c919e92098e33d0a0bce47e22f82a865cb32400aa`; `mojmap.ready.json` SHA-256 `f8dc81374dc42521ee9d17a04ea0dc77f1c5384548cfc01c9621b3d84b670fed` marks exact version `1.20.1`, mapping `mojmap`, status `ready`, 4,786 sources, 69 artifacts, and client SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`.
  - The ready marker’s referenced sidecars match their recorded hashes: `mojmap.sources.sha256` `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`, `artifacts.sha256` `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`, and `movement-diagnostics.txt` `3eecf5b2c48cb99795874a36a8eb83cfc07866bc909a51c997dbfaac8083f4fd`. Every audited source below matches the source checksum manifest.
- The compared source files have these identical full-file SHA-256 hashes on both releases:

| Mojmap source | SHA-256 on both releases |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/util/Mth.java` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |
| `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |

The older provenance manifest and the newer ready/provenance/source/artifact/diagnostics sidecars, plus the five exact source byte identities above, are the frozen evidence set for this verdict.

## Reachable ordinary local-player path

The checked witness is an ordinary `LocalPlayer`, not a passenger, spectator, or flying player. It is sprinting in water, has `isInWater()` true, has swimming enabled by `updateSwimming`, is affected by fluids, cannot stand on the sampled fluid, and has a valid nonempty fluid state at the player-travel sample above. Choose an upward-looking pitch (`getLookAngle().y > 0`) and no jump input so the above-sample arm is reached. `LocalPlayer.isEffectiveAi()` returns true (`LocalPlayer.java:489-491`); with no controlling passenger, `Entity.isControlledByLocalInstance()` selects `isEffectiveAi()` (`Entity.java:2909-2915`), so inherited `LivingEntity.travel` passes its local-control gate.

On both versions, `Player.travel` first captures position, then tests `isSwimming() && !isPassenger()` and computes `lookY = getLookAngle().y`. It chooses factor `0.085` only for `lookY < -0.2`, otherwise `0.06`. In order, it applies the vertical correction when `lookY <= 0`, or jumping, or the state at `BlockPos.containing(x, y + 1.0 - 0.1, z)` has nonempty fluid. Java short-circuit order means the state sample is skipped for nonpositive `lookY` and for jumping; on the selected witness it is read and passes. The write is `deltaMovement + (0.0, (lookY - deltaMovement.y) * factor, 0.0)`. It occurs before `super.travel`, so it feeds the same tick’s inherited water travel. `Player.java:1446-1459`.

`Player.updateSwimming` only clears swimming for flying players; otherwise it delegates to `Entity.updateSwimming` (`Player.java:1475-1481`). The base selector retains an existing swim state only while sprinting, in water, and not a passenger; starting from non-swimming state it requires sprinting, underwater eyes, no passenger, and water at `blockPosition` (`Entity.java:1166-1173`). `Player.isSwimming` additionally excludes flying and spectator players (`Player.java:1867-1868`). `Entity.isInWater()` reads `wasTouchingWater` (`Entity.java:1137-1139`).

## Pitch math and inherited movement

`Entity.getLookAngle()` calls `calculateViewVector(getXRot(), getYRot())` directly, using current raw pitch and yaw rather than the partial-tick interpolating `getViewVector` (`Entity.java:1970-1972`; compare `getViewVector` / `getViewXRot` at 1436-1445). The shared view-vector code converts both degrees to `float` radians, then obtains sine/cosine through `Mth`; its vertical component is `-Mth.sin(pitchRadians)` (`Entity.java:1448-1456`). `Mth` initializes a 65,536-entry `float` sine LUT from `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` (`Mth.java:26-30`). `sin(float)` multiplies in float by `10430.378F`, casts the product to `int` (truncation toward zero), masks with `65535`, and reads that LUT; `cos(float)` uses the same scale plus `16384.0F` before the same cast/mask (`Mth.java:42-47`). These files and expressions are byte-identical across the pair.

After the Player correction, `LivingEntity.travel` checks `isControlledByLocalInstance`, reads the fluid at `blockPosition`, selects its water branch with `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluid)`, then applies sprint/depth-strider/Dolphin’s Grace speed choices, calls `moveRelative`, calls `move(MoverType.SELF, deltaMovement)`, applies water drag and fluid-falling adjustment, and may apply the horizontal-collision step-up (`LivingEntity.java:1996-2047`). `Player` and `LivingEntity` do not declare a `move` override in these source files; this dispatch reaches inherited `Entity.move`.

The actual `Entity.move` body is identical (`Entity.java:593-722`): except for `noPhysics`, it applies stuck and edge adjustments, resolves `collide`, writes the collision-resolved position, computes collision/on-ground state, checks fall damage and horizontal velocity clipping, runs the block-step and movement-emission handling, checks inside-block effects, applies block speed factor, handles fire state, and pops the profiler. No inherited movement behavior on this path changes between the releases.

## Coverage boundary

This verdict is only for the named Player swim-pitch correction and the exact inherited selector/math/move dependencies traced above. It records a no-difference slice. It does not cover other Player travel branches, other player movement operations, or the full 1.20-to-1.20.1 reachable movement inventory.

## Phase 2: independent comparison of the author artifact

**Reviewer:** `/root/c_swim_pitch_vertical_120_1201_blind_audit`  
**Review disposition:** Agree with the bounded no-difference verdict for `Player.travel` swim-pitch correction and its source-level pitch math / inherited travel consumer. The author’s four paired source hashes match this frozen audit, and the source identities/readiness claims agree. The author’s report is bound to the exact bytes below; no content from it was read before the initial freeze recorded above.

**Coverage note:** The author report states the nonpassenger swim path and identifies the applicable gates, but does not explicitly establish the ordinary local-player reachability witness or trace the effective local-AI control gate and valid above-sample state. The independent frozen section above does trace that witness. This is a detail gap in the author artifact, not a disagreement with its no-difference claim; the operation remains source-confirmed as unchanged.

**Literal author binding:** `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-swim-pitch-vertical-120-1201-source/report.md`  
**Author SHA-256:** `caee8e79a79bb65222e82645928ca7d0d27425826215dba554ac6c855e2d6cae`  
**Original frozen verdict SHA-256 (before this append):** `dde1ed16062566b5dc8cebbfe960c428e647d2c5336d54cf34db297f503157fb`
