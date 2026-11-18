# Independent audit: Mojmap 1.16.1 → 1.16.2 local travel handoff

## Verdict

**Accept the frozen report’s bounded no-difference claim** for ordinary, no-effect, on-foot local-player input/pose/ownership through travel dispatch and direct post-travel writes/callback ordering. The compared code confirms no residual movement delta in this slice. This does not establish full-pair coverage or runtime parity.

## Exact inputs and provenance audit

- Frozen input: `c-middle-1161-1162-local-travel-handoff/report.md`, SHA-256 `7a597b0a615bb4307b4be61321780badadd5ff1517363eca5fbffebf34be7a75`.
- Read-only source roots: `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`.
- Each root’s `mojmap.ready.json` names the exact version and `mojmap`, and its `sourceManifestSha256` matches the local `mojmap.sources.sha256`: 1.16.1 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; 1.16.2 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.
- Each `mojmap.provenance.json` independently names the exact paired run (`1.16.1,1.16.2`, Mojmap, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Gradle 9.7.1, Java 25). All seven compared Java source hashes in the frozen report were recomputed and match their entries in the local source manifests and source bytes.
- Provenance limitation: both ready-marker `outputPath` values point to the absent `build/movement-campaign-2026-10-07/ready/...` staging tree. The versioned canonical source trees and their manifests are present and hash-consistent with the markers; the absent staging path prevents rechecking that original output directory itself.

## Independent source trace

- `Input.java` and `KeyboardInput.java` are byte-identical across the pair (source hashes `367c3a9b…359201` and `746ea654…88396f7`). The ordinary input impulse derivation and slow-input multiplier therefore match exactly.
- `LocalPlayer.aiStep()` (`LocalPlayer.java:652+` / `:627+`) has the same order for crouch/pose eligibility, input refresh, item-use scaling, sprint/jump/flying decisions, and controlled-camera transfer of `leftImpulse`, `forwardImpulse`, and `jumping` into `xxa`, `zza`, and `jumping`. Its only method-body changes are the four calls under `!noPhysics`: 1.16.1 `checkInBlock(...)` becomes 1.16.2 `moveTowardsClosestSpace(...)`. This is the excluded suffocation response; after omitting those four calls, the bodies compare equal. `isControlledCamera()` is identical (`:647` / `:622`), as are `isLocalPlayer()` and the compared pose/dimension gates.
- The handoff remains `LocalPlayer` → `Player.aiStep()` (`Player.java:483+`) → `LivingEntity.aiStep()` (`LivingEntity.java:2366+` / `:2368+`). In the ordinary non-fluid path, the `LivingEntity.travel()` ownership entry gate (`:1912-1915` / `:1914-1917`) and ground branch operation sequence are identical. `Player.travel()` (`Player.java:1380+` / `:1387+`) is byte-for-byte identical as a method body. `LivingEntity.aiStep()`, `LivingEntity.travel()`, and `handleRelativeFrictionAndCalculateMovement()` also compare identical.
- `Entity.move()` (`Entity.java:469+` / `:485+`) has the same collision resolution, position/box updates, collision and on-ground flags, fall callback, blocked-velocity zeroing, `updateEntityAfterFallOn`, `stepOn`, and subsequent movement bookkeeping order. Its only body change in this comparison is removal of `isTouchingLava = false`, the excluded fluid-state change. `LocalPlayer.move()` is unchanged.
- `Entity.setPos()` (`Entity.java:321-325` / `:331-333`) is the only direct-write refactor in the bounded path. The new `EntityDimensions.makeBoundingBox(double,double,double)` (`EntityDimensions.java:21-25`) performs the same float casts/locals and constructs `AABB(d-g, e, f-g, d+g, e+h, f+g)` in the same argument order as the old inline code. No movement delta follows.

## Boundaries retained

This verdict covers only ordinary, no-effect, on-foot local control and the stated travel/write handoff. Fluids, edge backoff, other effects, entity interactions, corrections/packet behavior, mounted travel, non-ground branches, and full-pair inventory remain unreviewed or excluded as in the frozen report. No product files or source trees were changed; no generation, build, tests, or launch were performed.