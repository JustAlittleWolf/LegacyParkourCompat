# C: direct pitch precision, 1.12.2 to 26.2

## Frozen source identity (before method-body inspection)

Host/execution: Windows PowerShell. Canonical local decompiled sources only; no network, wiki, implementation, Git, generation, build, tests, or runtime use.

- Older: Minecraft 1.12.2, Ornithe Feather (`ornithe-feather`), provenance `decompiled_minecraft/1.12.2/ornithe-feather.provenance.json` SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; source index `decompiled_minecraft/1.12.2/ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- Newer: Minecraft 26.2, unobfuscated, provenance `decompiled_minecraft/26.2/unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; source index `decompiled_minecraft/26.2/unobfuscated.sources.sha256` SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

All listed Java-file hashes were checked against the source index and match. This manifest is frozen; any newly relied-on file must be separately identified and hashed before its body is read.

| Side | Source-relative path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.12.2 | `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `207a0ff4ee996916cd1fc73f47bfaae64f92719916cafed8395b1f2a8f797809` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.12.2 | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.12.2 | `net/minecraft/client/entity/living/player/Input.java` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| 1.12.2 | `net/minecraft/client/Mouse.java` | `b6c9132938c64f2b79138e87de60cee4b965e6c28d485798d7131ec2bf0fd058` |
| 1.12.2 | `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` |
| 1.12.2 | `net/minecraft/client/Minecraft.java` | `8af833a5d03a6f6516a7eff0ab463a96bfec64aac31fc80656f37d193b1c1310` |
| 1.12.2 | `net/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket.java` | `acc2ec0bf5f5ef64bff47b411a065ee3c41b07053c95de9a5ed19d2eb2f941a7` |
| 26.2 | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 | `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 | `net/minecraft/client/player/KeyboardInput.java` | `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39` |
| 26.2 | `net/minecraft/client/player/ClientInput.java` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| 26.2 | `net/minecraft/client/MouseHandler.java` | `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15` |
| 26.2 | `net/minecraft/client/Minecraft.java` | `c79d698d2323cb97a7e7570fa736cc7a0b88eab7adab8577e1bc66ba021ccdf0` |
| 26.2 | `net/minecraft/network/protocol/game/ServerboundMovePlayerPacket.java` | `3f24293dd796c9eb0e9efe726797e09280a1239232813c307c0bb4a84e1ab9c9` |

## Source trace



### Direct pitch operation and original callers

- 1.12.2 `GameRenderer.render(float,long)` samples `Mouse` and computes sensitivity and deltas into `float`s (`GameRenderer.java:841-867`). It calls `player.updateLocalPlayerCamera(h, i * j)` in both smooth and ordinary branches. The direct method is `Entity.updateLocalPlayerCamera(float yaw, float pitch)` (`Entity.java:307-318`). For pitch it evaluates `this.pitch - pitch * 0.15` with `0.15` as a `double`, casts the completed result to `float`, clamps to `[-90,90]`, then adds the realized change to `lastPitch`.
- 26.2 `MouseHandler.turnPlayer(double)` computes `xo`/`yo` as doubles and calls `player.turn(invertedX ? -xo : xo, invertedY ? -yo : yo)` (`MouseHandler.java:350-376`). The direct API is `Entity.turn(double xo, double yo)` (`Entity.java:489-500`). It casts `yo` to `float` before multiplying by `0.15F`, adds that float delta to current rotation, calls `setXRot`, then clamps current pitch; it separately increments and clamps `xRotO` by the same pre-clamp delta.

The device paths differ: 1.12.2 `Mouse.getDX/getDY` are integer deltas with float sensitivity and an optional smooth-camera interpolation branch; 26.2 uses accumulated double deltas and double smoothing/sensitivity operations. The witness below is an API-boundary comparison, not a replay of identical device samples. It sign-normalizes the effective pitch change because the old helper subtracts its pitch argument while the new helper adds `yo`.

For a finite, normal float witness `d = 0.1F`, common initial pitch `-0.48F`, no clamp boundary, and equivalent effective upward pitch change:

- old API argument `pitch = -d`: `(float)(-0.48F - (-d) * 0.15)` gives `-0.4650000035762787F`;
- new API argument `yo = +d`: `-0.48F + (float)d * 0.15F` gives `-0.4649999737739563F`.

The resulting current-pitch values differ by one float ULP. This isolates the direct operation's multiplication/rounding and accumulation width. Both values remain finite and inside the clamp. The old `lastPitch` update and modern `xRotO` update are different interpolation bookkeeping; the selected next-tick glide kernels below read current pitch directly, not those prior/interpolation values.

### Next active dry local nonpassenger glide movement

Assume the profile is already in fall-flying state, the local player is dry, not riding, not on a climbable, in a loaded/simulated world, and no collision branch diverts the request. This removes startup/item and unrelated environment gates while retaining the actual local tick-to-move path.

- 1.12.2 `LocalClientPlayerEntity.tick()` enters `super.tick()` (`LocalClientPlayerEntity.java:165-167`). Its `LivingEntity` AI step reaches `flyingTick()` and then `moveRelative(...)` (`LivingEntity.java:1822-1847`). The active Elytra branch of `moveRelative` uses current `pitch` to form radians, derives the look vector, computes lift/pitch terms, updates velocity and applies drag, then issues `move(MoverType.SELF, velocityX, velocityY, velocityZ)` (`LivingEntity.java:1425-1464`). `Entity.getLookVector()` uses current pitch/yaw (`Entity.java:1690+`).
- 26.2 `LocalPlayer.tick()` enters `super.tick()` (`LocalPlayer.java:227-230`); `LivingEntity.tick()` calls `aiStep()` (`LivingEntity.java:2765-2805`), and `LocalPlayer.aiStep()` calls `super.aiStep()` (`LocalPlayer.java:767-914`). The `LivingEntity` travel phase dispatches active fall flying to `travelFallFlying` (`LivingEntity.java:3126-3140`, `2430-2437`). On the selected dry, non-climbable path it runs `updateFallFlyingMovement` and issues `move(MoverType.SELF, getDeltaMovement())` (`LivingEntity.java:2581-2589`). The update reads current `getXRot()` for `leanAngle`, gets the current-pitch look angle, and uses pitch in the lift, conversion and steering terms (`LivingEntity.java:2602-2626`; `Entity.getLookAngle()` at `Entity.java:2579-2581`).

Thus each selected branch actual next movement request consumes the post-turn current-pitch value from that same version. Keep each version existing Elytra kernel fixed while comparing the two turn results within that version. The old and new Elytra kernels themselves differ substantially (old integrated branch in `moveRelative`; new dedicated `updateFallFlyingMovement`/`travelFallFlying`), so their cross-version movement-request difference cannot be attributed solely to turn precision. No particular trajectory or numeric displacement is claimed without fixing the remaining velocity/world inputs.

### Scope boundary

This establishes a source-level direct-pitch precision difference for 1.12.2 to 26.2, its ordinary client caller on each side, and its path into the next active dry local nonpassenger Elytra move request. It does not establish which intermediate release introduced the difference, characterize other rotation APIs, compare device sampling, prove runtime parity, or infer behavior outside the checked methods.