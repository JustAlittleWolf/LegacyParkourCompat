# AutoJump detector entry and timer gates: 1.10.2 → 26.2

## Exact source identity and integrity

- Older source: Minecraft 1.10.2, Ornithe Feather Gen2 build.2 (`ornithe-feather`), `decompiled_minecraft/1.10.2/ornithe-feather/`. Provenance names `feather-gen2-1.10.2+build.2-mergedv2.jar` and `feather-gen2-1.10.2+build.2.tiny`; ready marker reports 1.10.2 / `ornithe-feather`.
- Newer source: Minecraft 26.2, `unobfuscated`, `decompiled_minecraft/26.2/unobfuscated/`; provenance and ready marker report 26.2 / `unobfuscated`.
- For both trees, SHA-256 of source manifest, artifact manifest, and movement diagnostics matched the ready-marker digests. SHA-256 of each source listed below matched its entry in the source manifest.
- 1.10.2 source-manifest SHA-256: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`.
- 26.2 source-manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- `LocalClientPlayerEntity.java` (1.10.2) source SHA-256: `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`.
- `GameOptions.java` (1.10.2) source SHA-256: `767ed017470a0909148017461668fa95a988f2f15582b4e2c6636b03502b4d43`.
- `KeyboardInput.java` (1.10.2) source SHA-256: `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`.
- `Input.java` (1.10.2) source SHA-256: `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e`.
- `Minecraft.java` (1.10.2 input installation) source SHA-256: `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9`.
- `LocalPlayer.java` (26.2) source SHA-256: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.
- `Options.java` (26.2) source SHA-256: `26bbc6e61b2c072105d0f5a5b2d7afea4baeed28140e03b4e6793ceb5f6be831`.
- `Player.java` (26.2; `isStayingOnGroundSurface` helper) source SHA-256: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- `KeyboardInput.java` (26.2) source SHA-256: `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`.
- `ClientInput.java` (26.2) source SHA-256: `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78`.
- `Vec2.java` (26.2) source SHA-256: `284ba455cf02047283a01af3d4c77929cfd9bbcc772b0677de503a3774bd4b51`.
- `ClientPacketListener.java` (26.2 input installation) source SHA-256: `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`.

## Bounded trace

### 1.10.2 (Ornithe Feather)

- `LocalClientPlayerEntity.move(double,double,double)` saves the previous X/Z, calls `super.move`, then invokes `autoJump((float)(x - oldX), (float)(z - oldZ))` (LocalClientPlayerEntity.java lines 811–817). Detection runs after movement and receives the resulting X/Z deltas cast to `float`.
- `autoJump` requires cached AutoJump enabled, `ticksToNextAutojump <= 0`, `onGround`, `!isSneaking()`, and `!isRiding()` (lines 823–826). Its movement-input gate accepts when either component of `input.getMovement()` is `!= 0.0F` (lines 826–827; `Input.getMovement()` returns the two stored components, Input.java lines 18–20).
- The vanilla local player receives `KeyboardInput` at Minecraft.java lines 1913 and 1946. `KeyboardInput.tick()` resets each movement component to zero and adjusts it only by key-press increments/decrements, so each raw axis is -1, 0, or +1 (KeyboardInput.java lines 14–42). Sneaking multiplies both by 0.3 (lines 45–48); the local-player item-use path may multiply them by 0.2 (LocalClientPlayerEntity.java lines 666–670). These multipliers preserve zero versus nonzero. Thus the old component predicate is evaluated over the vanilla keyboard-produced input domain, not arbitrary finite floats or NaNs.
- On detector success, `ticksToNextAutojump = 1` (LocalClientPlayerEntity.java lines 911–915).
- In the next local `tickMovement`, after `input.tick()`, a positive timer is decremented, `bl4` is set true, and `input.jumping` is set true (lines 661–677). That same `bl4` suppresses interpreting this synthetic press as the separate creative-flight double-tap input (`!bl4`, line 719). This records only the AutoJump origin marker's immediate consumer.
- The cached field is read by `isAutoJumpEnabled()` (lines 819–821). The cache initializes true (line 105) and `sendMovementToServer()` refreshes it from `minecraft.options.autoJump` at the end of its `isCamera()` branch (lines 168–229; assignment at 228). The option itself initializes true (GameOptions.java line 108); option loading can replace it from the saved `autoJump` value (lines 831–833).

### 26.2 (unobfuscated)

- `LocalPlayer.move(MoverType, Vec3)` saves prior X/Z, calls `super.move`, casts the resulting X/Z deltas to `float`, then calls `updateAutoJump(deltaX, deltaZ)` (LocalPlayer.java lines 985–994). Detection remains post-move.
- `updateAutoJump` delegates entry to `canAutoJump()` (lines 1005–1006). That predicate requires cached AutoJump enabled, `autoJumpTime <= 0`, `onGround()`, `!isStayingOnGroundSurface()`, `!isPassenger()`, `isMoving()`, and `getBlockJumpFactor() >= 1.0` (lines 1121–1129). The `Player.isStayingOnGroundSurface()` implementation returns `isShiftKeyDown()` (Player.java lines 300–302), so this is the current shift-key rejection expression.
- `isMoving()` is `input.getMoveVector().lengthSquared() > 0.0F` (LocalPlayer.java lines 1131–1133). The vanilla local player receives `KeyboardInput` at ClientPacketListener.java lines 535 and 1328. Its producer sets each key impulse to -1, 0, or +1, then forms a vector and normalizes it (KeyboardInput.java lines 13–37). `Vec2.normalized()` returns zero below magnitude `1.0E-4F`, otherwise it returns the normalized vector (Vec2.java lines 57–60). Because every nonzero raw keyboard pair has magnitude at least 1, the producer yields either zero or a finite nonzero vector. Consequently, for the vanilla keyboard producer, the old per-component nonzero test and current positive squared-length test agree on whether movement is active. Arbitrary finite tiny vectors and NaN values are outside this reachable producer domain and are not a source-backed difference in this bounded claim.
- On detector success, `autoJumpTime = 1` (LocalPlayer.java lines 1091–1095).
- In the next `aiStep`, after `input.tick()`, a positive timer is decremented, `wasAutoJump` is set true, and `input.makeJump()` is called (lines 777–793). `wasAutoJump` suppresses the synthetic press from the separate creative-flight jump-edge/double-tap path (`!wasAutoJump`, line 838), corresponding to old `bl4` suppression. The bounded comparison is the same immediate origin-marking role plus the input-state update (`input.jumping = true` in old code versus `input.makeJump()` in current code); it does not establish a newly added current-only suppression behavior.
- `isAutoJumpEnabled()` reads the cached field (LocalPlayer.java lines 996–998). The cache initializes true (line 152); `sendPosition()` refreshes it from `minecraft.options.autoJump().get()` at the end of its `isControlledCamera()` branch (lines 263–300; assignment at 299). `Options.autoJump` has endpoint default false (Options.java line 502), is returned through its accessor (lines 1223–1225), and is registered for option persistence (line 1553).

## Source-backed comparison and boundary

Both endpoints schedule the detector after movement, set a one-tick timer on success, and consume it during the following local input tick. Both cache the AutoJump option in the local-player camera movement-report path; both cached fields initialize true, and the entry gate reads that cache. The endpoint evidence establishes these facts only for 1.10.2 and 26.2.

The bounded endpoint facts are: (1) the vanilla option default is true in 1.10.2 and false in 26.2; this is an option-value endpoint fact, not a release cutover or a product conclusion; (2) the sneak exclusion is expressed in current code as `!isStayingOnGroundSurface()`, implemented as a shift-key check; (3) current entry additionally requires `getBlockJumpFactor() >= 1.0`; and (4) the source-level nonzero-input expressions differ, but agree over the verified vanilla keyboard input domain. The timer consumers both mark the AutoJump-origin synthetic jump and suppress it from the same separate creative-flight double-tap path; their input-state updates differ as described above. No intermediate release was examined, so introduction versions remain unknown within the endpoint interval. These facts do not establish runtime parity or effects of the added block-factor gate on particular surfaces.

## Deliberately unexamined

Only the vanilla keyboard producers and their local-player installation points were checked to close the nonzero-input reachability domain. No other input producer, input-device/mod replacement, detector geometry, collision shapes, pose-specific low-height/entity-fit gate, block jump-factor effects, or AutoJump trajectory behavior was inspected. Creative-flight code was inspected only at the immediate origin-suppression consumer of the timer marker. No 1.14.4 or 1.15 source was needed for this endpoint closure. No product files, Git state, backlog, wiki inventory, tests, builds, or game launches were accessed or changed.