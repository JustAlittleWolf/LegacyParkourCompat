# Independent source-only audit: ordinary movement gates

Pair: 1.13.2 → 1.14.4, Ornithe Feather Gen2 build 2. Scope is limited to the five claims in `ordinary-gate-closure.md`; this is not pair acceptance or runtime parity.

## Input integrity and evidence identity

The claim input sidecar SHA-256 is `2c70c46506dddef309ae52dbff41bf9c1bc7ecc519486f71c7c5734dc08e436c`, matching the input file. I independently computed the complete-file hashes listed below from the canonical `decompiled_minecraft/<version>/ornithe-feather` trees; all match the claim input's source-hash table.

| Version | Source file | SHA-256 |
|---|---|---|
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
| 1.13.2 | `net/minecraft/entity/living/player/PlayerAbilities.java` | `03c506732bb599c2a6d5f6c7d6f13175b9d27a0abfd9058811cf443f51af012d` |
| 1.14.4 | `net/minecraft/entity/living/player/PlayerAbilities.java` | `706f0011836698a9be661cda922d42c7143fdb385b7086ed79143ef3d68874ee` |

## Claim verdicts

### 1. Climb jump-boost gate — supported; trajectory remains open

The paired gates are correctly stated: 1.13.2 `LivingEntity.java:1575–1578` moves and assigns `velocityY = 0.2` only under `collidingHorizontally && isClimbing()`; 1.14.4 `:1846–1850` uses `(collidingHorizontally || jumping) && isClimbing()`. Local-player AI copies input jumping into the entity field at 1.14.4 `LocalClientPlayerEntity.java:603–607`, before jump/travel in `LivingEntity.java:2191–2227`; the old AI/travel order is `:1890–1926`. The ladder predicate accepts a nonspectator on a ladder on both sides (`LivingEntity.java:1046–1057` old; `:1239–1252` new). The narrow gate difference is established. A collision-free ladder witness and resulting full-tick velocity are not established; retaining those as open is correct.

### 2. Water gravity threshold sign — arithmetic supported; ordinary witness open; one argument-name correction

Old `LivingEntity.moveRelative` selects `d` from entry `velocityY` (`:1478–1484`), then water movement and Y damping precede a fresh `velocityY <= 0.0` test (`:1639–1649`). New caches entry `bl = y <= 0.0` (`:1786–1793`), then water move and `(f, 0.8F, f)` scaling precede use of `bl` and the same absolute-value bounds (`:1904–1921`). Player swimming correction precedes the base call on both sides (old `PlayerEntity.java:1442–1465`, new `:1381–1406`). For slow falling, entry `y <= 0` selects `d = 0.01`; post-damping `y = 0.000625` makes old's `<= 0` condition false, so old subtracts `d/16` and reaches zero. New has `bl == true`, satisfies `abs(y - 0.005) = 0.004375 >= 0.003` and `abs(y - d/16) = 0 < 0.003`, and sets `y = -0.003`. This proves the conditional arithmetic, not that ordinary player movement reaches it.

Correction to the input report's wording: old `Entity.updateVelocity` adds the parameter named `forwards` to Y (`Entity.java:1057–1074`, specifically `:1072`), not the parameter named `upwards`. At the old `LivingEntity.moveRelative` call, the second `moveRelative` argument is passed as `updateVelocity`'s `forwards`; the living tick supplies `verticalSpeed` in that second position. Thus the effective base vertical input reaches Y, but the report should not describe the old helper as adding its `upwards` argument. The vertical producer and water collision/movement witness remain open.

### 3. Dry swimming-pose keyboard slowdown — supported conditional input delta; pose reachability open

Old `KeyboardInput.tick()` scales both movement axes only when its newly sampled sneak key is held (`1.13.2 KeyboardInput.java:44–49`). New local-player `mobTick` computes `bl4` and passes it to `input.tick(bl4, isSpectator())` before sprint checks (`1.14.4 LocalClientPlayerEntity.java:625–631`); new keyboard input scales when nonspectator and `(sneaking || bl)` (`KeyboardInput.java:13–25`). `m_99544176()` is inherited from `Entity.java:1822–1824` and means pose equals `SWIMMING` while not in water. Therefore a nonspectator already in that pose while dry gets the 0.3 input scale without sneak in the new source, unlike old. The conditional input delta is established. The helper is declared on `Entity`, not `LocalClientPlayerEntity`; the report's cited Entity location makes its meaning clear. Ordinary entry into that pose while dry remains unproved, as the input report states.

### 4. Submerged sprint-forward threshold — start-gate difference supported; downstream persistence needs an explicit precondition

Old auto-start and sprint-key start both test `movementForward >= 0.8F` (`1.13.2 LocalClientPlayerEntity.java:723–745`). New uses `m_03985577()` at both sites (`:654–676`); it returns `input.m_49149051()` when submerged, else tests the 0.8 threshold (`:961–964`). The 1.14.4 input predicate is strict `movementForward > 1.0E-5F` (`Input.java:22–24`), and keyboard sneak scales forward-only input to `0.3F` (`KeyboardInput.java:20–25`). The sprint-key branch has no sneak exclusion (`LocalClientPlayerEntity.java:669–676`). So the changed start gate is source-established for the stated eligible input.

Qualification: “the sprint-key path suffices for reachability” should name the same-tick downstream cancellation gate. After a successful start, 1.14.4 may clear sprint at `LocalClientPlayerEntity.java:679–688`; when `isSwimming()` is false, horizontal collision must be absent (the submerged precondition already makes `isInWater() && !isSubmergedInWater()` false). If `isSwimming()` is true, held sneak prevents that branch's cancellation. Under either condition, the new positive-input test also makes `bl7` false. Thus a persistent same-tick sprinting state is source-supported with this downstream condition; the changed start check alone does not establish persistence in every eligible submerged state. Auto-start's separate gates are correctly not needed for the sprint-key threshold comparison.

### 5. Camera flight with jump and sneak together — candidate accurately left open

Both branches require `abilities.flying && isCamera()`. Old camera movement divides horizontal inputs by `0.3F`, subtracts a `float` product from double `velocityY`, then adds a `float` product (`1.13.2 LocalClientPlayerEntity.java:797–806`). New divides the same axes, accumulates `j--` and `j++`, then adds a vertical vector only if `j != 0` (`1.14.4 LocalClientPlayerEntity.java:728–742`). With both inputs, the new branch has `j == 0` and skips the update. Old does two sequential double updates; because each getter returns the same float-valued speed while the abilities value is unchanged (`PlayerAbilities.java:44–46` on both sides), rounding could leave a residual. No value-by-value recurrence or following-movement trace was supplied or constructed. The input report correctly leaves the effect open and makes no guaranteed-difference claim.

## Audit disposition

All five source-level gate/arithmetic descriptions are supported with their stated witness limits. Two wording/closure refinements are needed: old `Entity.updateVelocity` adds its named `forwards` argument, and the submerged sprint claim should include the later same-tick cancellation precondition when describing persistent sprint state. No ordinary trajectory is established for climb, the water sign-crossing, or dry swimming-pose entry; no camera residual is established.
