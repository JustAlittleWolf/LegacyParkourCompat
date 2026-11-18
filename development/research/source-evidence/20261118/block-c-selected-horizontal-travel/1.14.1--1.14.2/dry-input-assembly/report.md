# Dry input assembly: 1.14.1 → 1.14.2

## Assignment and verdict

Bounded source comparison of ordinary dry, unmounted, nonsprinting player horizontal input with standing/crouching pose supplied; input axes through keyboard assembly, input fields and client consumption/damping to the vector passed to `travel`. Swimming/crawling-pose behavior, travel arithmetic after vector construction, sprint/timer decisions and other assigned exclusions were not reviewed as findings. **No difference found in the assigned horizontal input assembly.** No canonical difference draft is applicable.

## Immutable source identity

Both exact Yarn decompilation trees were consumed read-only. The `.decompile-provenance.json` manifest SHA-256 is `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` for `decompiled_minecraft/1.14.1/yarn` and `786546F99684423CC2563BE45C9C3334DFCC2C7DB58DDB74BA39CE403BD0869A` for `decompiled_minecraft/1.14.2/yarn`, matching the supplied pins. Their pinned mapping inputs identify Yarn `1.14.1+build.10` and `1.14.2+build.7`, respectively, with matching intermediary artifacts; pinned version metadata URLs are for each exact release. Each source file below was checked against its manifest `sources` entry using SHA-256 on the actual bytes.

| Relative source | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/input/KeyboardInput.java` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| `net/minecraft/client/input/Input.java` | `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351` | `0a14d641cea95ecd6a836a2aecf0a7627dbf236460160a263a85b574a0216e4e` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` |
| `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |

## Paired evidence

- `client/input/KeyboardInput.java`, `tick(boolean, boolean)`, lines 13–25: the whole file is byte-identical. It reads forward/back/left/right booleans, assigns each axis to `0.0F` for matching key states or `+1/-1` otherwise, reads jump/sneak, then multiplies both axes by `(float)(axis * 0.3)` when `!bl2 && (sneaking || bl)`. The caller supplies `bl = isInSneakingPose() || shouldLeaveSwimmingPose()` and `bl2 = isSpectator()` in `ClientPlayerEntity.tickMovement`; for the assigned ordinary dry standing/crouching non-spectator path, the same gate and slowdown apply on both sides. No diagonal normalization occurs in this keyboard method.
- `client/input/Input.java`, fields and `getMovementInput()`, lines 5–20: both versions retain the same public sideways/forward float fields and booleans and return `new Vec2f(movementSideways, movementForward)`. 1.14.2 adds `method_20622()` at lines 22–24 (`movementForward > 1.0E-5F`); its caller is in changed sprint decision logic, outside this assignment.
- `client/network/ClientPlayerEntity.java`, `tickMovement()`, lines 607–624: both call `input.tick(bl4, isSpectator())`, then apply the same `0.2F` multiplication to both input axes when using an item and unmounted. The 1.14.1 pre-tick `movementForward >= 0.8F` local and 1.14.2 predicate-backed sprint checks differ around this code, but belong to excluded sprint decisions; they do not change the assigned key-axis assembly or item-use damping. This damping is conditional and therefore absent unless item use is active.
- `LivingEntity.java`, AI tick, 1.14.1 lines 2200–2204 and 1.14.2 lines 2205–2210: after existing input speed handling and `initAi()`, both construct `new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed)` and pass it to `travel`. `ClientPlayerEntity.tickNewAi()`, lines 589–594, copies `input.movementSideways` and `input.movementForward` into those speed fields for the camera player in both releases. This closes the assigned consumer-to-vector handoff; travel's subsequent vector arithmetic is outside scope.

## Coverage and gates

Examined both releases' complete `KeyboardInput` and `Input` classes, the relevant client player AI field copy and input tick/use-item damping, and the `LivingEntity` handoff vector. The ordinary key conversion, crouch/pose-boolean slowdown, input storage/readback, conditional item-use damping, and horizontal vector components supplied to travel match. No finding. Exact change-introduction boundary beyond this endpoint pair was not inspected. Full-pair source coverage, dependency closure outside this bounded slice, independent audit, integration acceptance, build and runtime parity remain open to their respective owners.
