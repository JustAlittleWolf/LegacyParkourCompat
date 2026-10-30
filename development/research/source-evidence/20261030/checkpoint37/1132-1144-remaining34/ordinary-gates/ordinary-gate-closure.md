# Ordinary movement gate closure: 1.13.2 → 1.14.4

**Scope.** Exact Ornithe Feather Gen2 build 2 source comparison, limited to the five surfaced gates and direct dataflow needed to state their preconditions. The permitted pair audit identifies these endpoint source trees. This is source evidence, not whole-pair acceptance or runtime parity. Vanilla block-state production, non-player movement, and source generation were not inspected.

## Findings

### Climb jump boost gate

**Source fact.** In 1.13.2 `LivingEntity.travel` moves, then assigns vertical velocity 0.2 only for `collidingHorizontally && isClimbing()` (`LivingEntity.java:1575–1578`). In 1.14.4 the same post-move operation uses `(collidingHorizontally || jumping) && isClimbing()` (`LivingEntity.java:1846–1850`). The 1.14.4 local-player AI copies `input.jumping` to the entity field (`LocalClientPlayerEntity.java:603–607`); the shared living tick invokes local AI before jump and travel (`LivingEntity.java:2191–2227`; old ordering `:1890–1926`). Both `isClimbing()` implementations accept a non-spectator at a ladder; 1.13.2 reads the block at floored x/z and shape-bottom y (`LivingEntity.java:1046–1057`), while 1.14.4 reads the entity block position (`:1239–1252`).

**Precondition and meaning.** On the new path, if `jumping` is true and the post-move climbing predicate is true, the assignment may run even when horizontal collision is false. The old path requires horizontal collision. Therefore a jump-held ladder case without horizontal collision can choose the new 0.2 vertical post-move velocity while the old path leaves the moved velocity for subsequent processing. The source gate difference is established. A concrete collision-free ladder trajectory and resulting tick delta are not established here; the exact climb-position query and collision result remain witness dependencies. Scaffolding is a separate `isClimbing` difference and is outside this gate disposition.

### Water gravity threshold sign

**Source fact.** Old `LivingEntity.moveRelative` selects slow-falling `d` from entry `velocityY` (`1.13.2 LivingEntity.java:1480–1484`), and, in its water branch, moves, multiplies Y by 0.8, then tests the *post-move/post-damping* `velocityY <= 0.0` before the gravity special case (`:1617–1649`). New `LivingEntity.moveRelative(Vec3d)` caches `bl = velocityY <= 0.0` at entry (`1.14.4 LivingEntity.java:1786–1793`), and its water branch moves, scales by `(f, 0.8F, f)`, then uses cached `bl` in the gravity special case (`:1882–1921`). Player-specific swimming correction occurs before the base call on both sides (`PlayerEntity.java:1442–1465` old; `:1381–1406` new). The base vertical input can also change velocity: old `Entity.updateVelocity` adds `upwards` to `velocityY` (`Entity.java:1057–1074`), new adds the supplied vector (`:1062–1076`).

**Precondition and meaning.** A gate divergence requires disagreement between the base-call entry sign and the post-water-move/post-damping sign, with the latter also inside the gravity threshold window. With slow falling (`d = 0.01`), the special-case window is nonempty: post value near `d/16 = 0.000625` is positive, meets both absolute-value bounds, and fails only the old `<= 0` test; if entry `bl` is true, new instead assigns `-0.003`. This establishes the arithmetic condition, not its ordinary-player reachability. The player input/vertical-speed producer and exact water movement/collision witness were not closed; disposition remains **conditional, witness open**. The apparent sign flip cannot be inferred from the cached predicate alone.

### Dry swimming-pose keyboard slowdown

**Source fact.** In old keyboard input, forward/sideways axes are multiplied by 0.3 only when current sneak input is true (`1.13.2 KeyboardInput.java:44–49`); `mobTick` calls its no-argument `input.tick()` before later sprint checks (the local-player body begins at `:653`, with the sprint logic at `:723–745`). New local-player `mobTick` computes `bl4 = m_63723874() || m_99544176()` and passes it to `input.tick(bl4, isSpectator())` (`1.14.4 LocalClientPlayerEntity.java:625–631`). The local-player `m_99544176()` helper is swimming pose and not in water (`:596–598` is sneaking helper; `net/minecraft/entity/Entity.java:1818–1824` identifies the swimming-pose helper as pose==SWIMMING && !isInWater). New `KeyboardInput.tick` scales both axes by 0.3 when `!spectator && (sneaking || bl)` (`KeyboardInput.java:13–25`).

**Precondition and meaning.** Given a non-spectator already in `Pose.SWIMMING` while outside water, the next new keyboard tick scales movement axes even without sneak input; old keyboard input does not. The direct gate and one-tick input effect are source-established. Whether ordinary movement can enter that pose outside water is conditional on the separately reviewed constrained-pose fallback and is not reopened or proved here. Thus this is a conditional input-delta finding, not an independently closed ordinary-movement witness.

### Submerged sprint-forward threshold

**Source fact.** Old auto-start and sprint-key start both require `movementForward >= 0.8F` (`1.13.2 LocalClientPlayerEntity.java:723–745`). New calls `m_03985577()` in both gates (`:654–676`); the helper returns `input.m_49149051()` when submerged and otherwise checks `movementForward >= 0.8` (`:961–964`). New input helper is strict positive `movementForward > 1.0E-5F` (`1.14.4 Input.java:22–24`). New keyboard sneak scaling produces 0.3 from forward-only input while sneak is held (`KeyboardInput.java:18–25`), and the sprint-key branch does not exclude sneaking (`LocalClientPlayerEntity.java:669–676`).

**Precondition and meaning.** For a submerged player eligible for sprint start (sprint key held, not already sprinting, adequate hunger/flight eligibility, not using an item, not blind), sneak-plus-forward keyboard input yields 0.3: new passes the submerged positivity check and old fails 0.8. The threshold and input witness are source-closed; no fluid producer or movement trajectory is needed to establish the start-gate difference. Auto-start has its own double-tap/eligibility gates; the sprint-key path suffices for reachability.

### Camera flight with jump and sneak together

**Source fact.** When `abilities.flying && isCamera()`, old camera code first divides horizontal input by 0.3 and subtracts `getFlySpeed() * 3.0F` from double `velocityY` for sneak, then adds the same float product for jump (`1.13.2 LocalClientPlayerEntity.java:797–806`). New performs the same horizontal-input division, accumulates `j--` and `j++`, then calls the velocity-add helper only for nonzero `j` (`1.14.4 LocalClientPlayerEntity.java:728–742`).

**Precondition and meaning.** With both booleans true, new `j` is zero and skips the vertical vector addition. Old evaluates two sequential double updates using the same float operand; the real-number sum is zero. This source comparison alone does not prove whether binary64 rounding leaves a residual for any reachable incoming `velocityY`, nor whether such a residual changes the following movement. No arithmetic witness or camera-flight movement trace is closed, so retain as **source-level arithmetic candidate; effect open**. Do not claim a guaranteed vertical difference.

## Coverage state

- **Examined:** The five named gates; immediate local-player input/AI ordering; the direct keyboard and sprint-threshold helper; water vertical-input addition and post-damping threshold; both climb predicates; camera branch arithmetic.
- **Partial:** Ordinary climb-branch trajectory; sign-crossing reachability for the water threshold; ordinary entry to swimming pose outside water; exact rounding consequence of camera arithmetic.
- **Unexamined:** Full movement/collision witnesses for climb and water, producer paths for the constrained pose, and IEEE-754 value-by-value camera residual reachability. No claims are made about other pair differences.
- **Next bounded action:** Independently construct minimal ordinary-player witnesses for the three partial movement gates (climb, water sign window, dry swimming pose); for camera flight, close only the binary64 recurrence from the shared float operand over reachable camera vertical-velocity values and then determine whether the resulting delta reaches movement.

## Source hashes

SHA-256 hashes below are of the complete cited canonical source files at report time; exact line ranges are given above.

| Endpoint | File | SHA-256 |
| --- | --- | --- |
| 1.13.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| 1.14.4 | `net/minecraft/entity/living/LivingEntity.java` | `2cccf4331ce9e62013eeb8e96163e5e146e1291619eb998ce9867daa87c02b61` |
| 1.13.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerEntity.java` | `2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df` |
| 1.13.2 | `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.14.4 | `net/minecraft/entity/Entity.java` | `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55` |
| 1.13.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 1.14.4 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce` |
| 1.13.2 | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.14.4 | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526` |
| 1.14.4 | `net/minecraft/client/entity/living/player/Input.java` | `acb63d46fb6e6a1cb6b285a5d0702fb62575f7d54344ad1f0f9d51e8f671f33f` |
