# Independent audit: active glide and climb exit, 26.1.2 → 26.2

## Verdict

**Qualified acceptance.** The active-glide climb-exit ordering, fall-distance reset and pre-move climb clamp in the candidate report are confirmed for the stated dry local-player path. A state for which `onClimbable()` is true takes ordinary air travel first; only after that call returns does `stopFallFlying()` write shared flag 7 `true`, then `false`. Ordinary feet for which `onClimbable()` is false retain the active-glide route.

The report's wider “unchanged” movement characterization is not established for collision cases. The actual virtual `LocalPlayer.move` path reaches `Entity.move`; 26.2 adds a collision-gated `restituteMovementAfterCollisions(...)` call after the parent move. This audit does not inspect its geometry-dependent behavior or assert that it changes a particular climb witness. The source result should therefore remain conditional on that parent-move branch. No shape, surface, damage or trajectory review was performed.

The climb witness is tag-conditional: `LivingEntity.onClimbable()` checks active glide plus `CAN_GLIDE_THROUGH` first and returns false before checking `CLIMBABLE`. The audited exit requires the predicate to return true; no concrete block/tag membership was independently established here.

## Re-traced path

- `LivingEntity.onClimbable()` has the same ordering on both sides: spectator rejection; current in-block state; the active-glide/`CAN_GLIDE_THROUGH` early false; `CLIMBABLE` and usable trapdoor checks. 26.1.2 lines 1689–1706; 26.2 lines 1722–1739.
- `LocalPlayer.aiStep()` has the same jump-edge, flight-toggle, prior-jump, `!onClimbable()` and `tryToStartFallFlying()` gates, then snapshots `wasFallFlying`. `Player.tryToStartFallFlying()` and `startFallFlying()` have the same not-active / `canGlide()` / not-water checks and local flag set. LocalPlayer 26.1.2 and 26.2 lines 854–858; Player 26.1.2 lines 1442–1453; 26.2 lines 1463–1474. This confirms only the report's local-request gates; the requested path starts with glide already active.
- In `LivingEntity.tick()`, `updateFallFlying()` remains before travel. The helper's fall-distance accumulation is unconditional, while validation/clearing is inside the `!level().isClientSide()` branch. 26.1.2 lines 3060–3062 and 3126–3160; 26.2 lines 3127–3129 and 3193–3226. Thus that helper does not clear the active flag on this logical-client path merely for a climb predicate.
- Dry `LivingEntity.travel()` dispatch remains fluid, then `isFallFlying()`, then ordinary air travel. In `travelFallFlying()`, a true climb predicate calls `travelInAir(input)` before `stopFallFlying()`; otherwise it updates glide movement and calls `move`. 26.1.2 lines 2392–2400 and 2528–2547; 26.2 lines 2430–2438 and 2581–2600.
- In the nested ordinary-air helper, both versions call `moveRelative`, pass current delta through `handleOnClimbable`, then invoke virtual `move`; the climb/jump Y response is read afterward. `handleOnClimbable` calls `resetFallDistance()` before clamping X/Z to `[-0.15F, 0.15F]` and lower-bounding Y at `-0.15F`, with the same ladder-sneak/scaffolding gate. 26.1.2 lines 2623–2665; 26.2 lines 2676–2718. `resetFallDistance()` still only writes `fallDistance = 0.0`: Entity 26.1.2 lines 2841–2843; 26.2 lines 2908–2910.
- The virtual dispatch is present on both sides: `LocalPlayer.move()` calls `super.move(...)` and then its local bookkeeping; Entity's implementation is therefore reached before the ordinary-air post-move response and before the glide-stop writes. LocalPlayer 26.1.2 and 26.2 lines 986–994; Entity 26.1.2 lines 704–800; 26.2 lines 711–800. `Entity.move` retains the local-authority check for vertical collision and fall checking; `LocalPlayer.isLocalPlayer()` returns true, and `Player.isLocalClientAuthoritative()` delegates to it. Relevant refs: Entity 26.1.2 lines 755–770 and 3516–3523; 26.2 lines 762–778 and 3589–3596; Player 26.1.2 lines 1260–1270 and 26.2 lines 1281–1291; LocalPlayer 26.1.2 and 26.2 lines 378–380.
- Dependency gap in the candidate's unchanged-route claim: 26.2 `Entity.move()` adds `if (this.canSimulateMovement() && (movedVertically && this.verticalCollision || this.horizontalCollision)) this.restituteMovementAfterCollisions(...)` at lines 784–785. There is no corresponding call in 26.1.2's lines 781–786. It follows the same parent-move/authority section but is a new collision-conditioned movement operation. Its specific outcome is outside this audit's boundary.
- The adjacent 26.2 air-drag formula reduces to the legacy constants at registered defaults: `AIR_DRAG_MODIFIER` and `FRICTION_MODIFIER` each declare default `1.0` (`Attributes.java` lines 10–12 and 52–54); living attributes include them (`LivingEntity.java` lines 356–357); player attributes derive from living attributes (`Player.java` lines 206–220). `computeModifiedFriction(f,m)` is clamped `1 - (1-f)*m` (`LivingEntity.java` lines 514–516); with `m=1`, values `0.91F` and `0.98F` remain those values. `travelInAir()` uses those constants through the new attributes at 26.2 lines 2468–2497. This supports the default-attribute case only, not modified attributes.

## Boundaries

Exact pair only: 26.1.2 → 26.2. No intermediate patch boundary, startup attempt, later tick, custom glider behavior, tag membership for a concrete block, collision geometry, restitution body, damage, surface behavior, or runtime parity was audited. No tests, launches or builds were run.

## Immutable input and source identity

Candidate report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-2612-262-actual-climb-exit\report.md`  
SHA-256: `D4AB6D6AC4F2C34FC416BA469A9C5534BAA05228FE7501DA83AB646588299C76`

Canonical source directories read-only: `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`; mapping namespace `unobfuscated` on both. The adjacent ready markers name exact IDs `26.1.2` and `26.2`, mapping `unobfuscated`, and their manifest SHA-256 values match the files on disk:

- 26.1.2 `unobfuscated.sources.sha256`: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; ready marker SHA-256 `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; provenance SHA-256 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`.
- 26.2 `unobfuscated.sources.sha256`: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; ready marker SHA-256 `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070F`; provenance SHA-256 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`.

The examined source hashes match their manifest entries and were recomputed from the canonical source files:

| Release | Relative source | SHA-256 |
|---|---|---|
| 26.1.2 | `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.1.2 | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1.2 | `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.1.2 | `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 | `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `net/minecraft/world/entity/ai/attributes/Attributes.java` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |
| 26.2 | `net/minecraft/world/entity/ai/attributes/DefaultAttributes.java` | `0e8d97f9af11fa17e585c56b66670a13e3fc368da21fb9a032f5fd2b05ab8d6e` |