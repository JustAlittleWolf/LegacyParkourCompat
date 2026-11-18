# Active Elytra glide on a climbable: 1.19.2 → 26.2 source route

## Scope and conclusion

This is an endpoint causal trace for an ordinary local player already actively fall-flying at the start of the relevant player tick, on foot, in dry air, wearing a default Elytra in the chest slot, with `onClimbable()` true at the feet (ladder/vine case). The player is not on ground, has no levitation or slow-falling effect, and movement is simulated locally. This establishes an already-active path only; it does not identify when the behavior changed or assert an introduction boundary.

**Conclusion:** In 1.19.2, after the active-glide gate survives, dry travel selects the Elytra branch regardless of climbability. That branch may set `fallDistance` to `1.0F` when vertical velocity is greater than `-0.5`, then applies glide velocity and makes the glide move. In 26.2, the active-glide tick first caps accumulated fall distance through `checkFallDistanceAccumulation()`. Dry travel then enters `travelFallFlying`; a climbable diverts it to ordinary air travel before the glide velocity update/move, though not before all movement: the ordinary air-travel move runs first. Its climbable handler resets `fallDistance` before that move. After that air travel returns, `stopFallFlying()` writes shared flag 7 true then false. Thus these source routes differ in selection, call order and fall-distance state around the climbable move.

## Exact source identity

Canonical published decompiler trees, read-only:

| Release / namespace | Source and input identity |
| --- | --- |
| 1.19.2 / Mojmap | Tree `decompiled_minecraft/1.19.2/mojmap`; provenance `decompiled_minecraft/1.19.2/mojmap.provenance.json` records exact version `1.19.2`, mapping option `mojmap`, Vineflower `1.12.0`, Java `25.0.3+9-LTS`, Gradle `9.7.1`, and publication from the recorded exact run. Ready marker source-manifest SHA-256: `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`. Exact client input SHA-256: `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db`; client Mojmap artifact SHA-256: `a257c4c97ceac50fbc069dc6051dff5f0263716547ede05e85c44d675ade592f`. |
| 26.2 / unobfuscated | Tree `decompiled_minecraft/26.2/unobfuscated`; provenance `decompiled_minecraft/26.2/unobfuscated.provenance.json` records exact version `26.2`, unobfuscated output, Vineflower `1.12.0`, Java `25`, Gradle `9.7.1`, and publication from the recorded exact run. Ready marker source-manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Exact client input SHA-256: `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`; no separate mapping artifact applies to unobfuscated output. |

The ready markers identify the exact version IDs and source-manifest hashes. Each source file hash below was checked against its `.sources.sha256` entry and re-hashed from the published bytes during this route. Line references use one-based lines in those exact files.

| Release | File (repository-relative) | SHA-256 |
| --- | --- | --- |
| 1.19.2 | `decompiled_minecraft/1.19.2/mojmap/net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |
| 1.19.2 | `decompiled_minecraft/1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 26.2 | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `decompiled_minecraft/26.2/unobfuscated/net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |

## Ordered causal route

### 1.19.2 Mojmap

1. `LivingEntity.aiStep` reaches the `travel` profiler section, multiplies `xxa`/`zza` by `0.98F`, calls `updateFallFlying()`, then calls `travel(new Vec3(xxa, yya, zza))` (`LivingEntity.java:2583–2589`).
2. `updateFallFlying()` reads shared flag 7. It accepts the active state only while flag 7 is true, not on ground, not a passenger, and without levitation; it then requires a chest-slot `Items.ELYTRA` with `ElytraItem.isFlyEnabled`. Otherwise its local boolean becomes false. The method writes flag 7 only on the logical server (`LivingEntity.java:2626–2650`). There is no climbable predicate in this update gate. Under this report's valid Elytra/on-foot/no-effect preconditions, the active bit is retained for client travel.
3. `travel(Vec3)` is entered only for effective AI or a locally controlled instance. The travel decision checks water, then lava, then `isFallFlying()` (`LivingEntity.java:2042–2107`). Under the dry precondition, the water/lava arms are skipped; active flag 7 chooses the glide arm. That arm has no `onClimbable()` test.
4. The glide arm reads current delta movement. If its `y > -0.5`, it assigns the float field `fallDistance = 1.0F` before calculating glide velocity (`LivingEntity.java:2107–2112`). It later writes multiplied glide velocity and calls `move(MoverType.SELF, ...)` (`LivingEntity.java:2131–2136`). No climbable-specific early exit precedes this first glide move. The server-only landing flag clear after movement is outside the stipulated airborne state (`LivingEntity.java:2147–2149`).

The local-player input path is not the source of an already-active bit in this trace: `LocalPlayer.aiStep` can send a `START_FALL_FLYING` command after a jump when its start predicates pass, then snapshots `wasFallFlying`; it does not directly set shared flag 7 there (`LocalPlayer.java:830–837`). It subsequently calls `super.aiStep()` (`LocalPlayer.java:893`), reaching the base update/travel route described above. Starting eligibility is excluded here.

### 26.2 unobfuscated

1. `LivingEntity.aiStep` reaches `travel`, checks `isFallFlying()`, and calls `updateFallFlying()` before constructing travel input or choosing travel (`LivingEntity.java:3125–3135`). With active flag 7, this reaches `checkFallDistanceAccumulation()` first (`LivingEntity.java:3193–3195`).
2. The inherited `Entity.checkFallDistanceAccumulation()` assigns double `fallDistance = 1.0` when `getDeltaMovement().y() > -0.5` and current `fallDistance > 1.0` (`Entity.java:2902–2906`). Unlike the old branch's unconditional assignment when `y > -0.5`, this write is also gated by the current accumulator exceeding 1.0. It runs before the travel branch.
3. On a local client, `updateFallFlying()` does not write flag 7: its `canGlide()` validation, flag clear, durability and glide game event are inside `!level().isClientSide()` (`LivingEntity.java:3193–3213`). The `canGlide()` predicate requires airborne, not passenger, and no levitation, and accepts a usable glider item from the equipment slots (`LivingEntity.java:3215–3227`). The assigned chest Elytra satisfies the intended ordinary case; climbability is not part of this update gate.
4. The travel dispatch checks `shouldTravelInFluid(...)` first, then `isFallFlying()`, then air (`LivingEntity.java:2430–2437`). Dry air selects `travelFallFlying(input)` while the active bit is true. At the start of that method, `onClimbable()` diverts to `travelInAir(input)` and calls `stopFallFlying()` afterward (`LivingEntity.java:2581–2595`). The climbable branch therefore prevents the glide velocity update and glide `move` from running, but it is not an early exit before any move: it calls `travelInAir`, whose ordinary air-travel move is the first move in this route.
5. `travelInAir` routes movement through `handleRelativeFrictionAndCalculateMovement`; that invokes `handleOnClimbable` before its first `move` (`LivingEntity.java:2676–2680`). `handleOnClimbable` resets fall distance when climbable, then clamps the delta (`LivingEntity.java:2703–2714`). Thus the previous pre-travel cap, if it wrote `1.0`, is reset to zero before this ordinary climbable move.
6. Once `travelInAir` returns, `stopFallFlying()` writes shared flag 7 as `true` followed by `false` (`LivingEntity.java:2597–2600`). In this route those are direct same-tick writes after the climbable ordinary move, not an update-gate check before travel.

The local-player `aiStep` start path checks `!onClimbable()` before sending `START_FALL_FLYING`, then snapshots `wasFallFlying` (`LocalPlayer.java:854–858`); it does not directly set shared flag 7. It subsequently calls `super.aiStep()` (`LocalPlayer.java:914`), reaching the base update/travel route described above. This concerns starting only and is not used to claim that the already-active state can be initiated on a climbable in either release.

## Bounds and unresolved claims

- Dry ordinary local-player tick, already-active fall-flying bit at tick entry, chest Elytra usable, on-foot, airborne and climbable feet are the only applicability assumptions used.
- Water/lava selection and effects are only bounded out by the dry/no-effect preconditions. Their internals are not compared.
- Elytra start eligibility, input timing, command processing, server validation, and the route that initially sets the active bit are excluded. The local input snapshots and command sends above are cited only to distinguish them from direct same-tick shared-flag writes.
- The source route does not establish final-tick claims beyond the described same-tick `stopFallFlying` writes; post-tick synchronization and packet timing are not traced.
- Geometry, contact/shape computation, ladder/vine predicates and collision internals are excluded. `onClimbable()` is treated as the stipulated true predicate.
- This endpoint comparison does not establish the release or patch where any behavior was introduced.

## Frozen report digest

Encoding contract: UTF-8 without BOM, LF line endings. Report SHA-256 is supplied by the terminal handoff after writing this file.



