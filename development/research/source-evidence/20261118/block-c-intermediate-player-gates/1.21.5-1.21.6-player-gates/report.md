# 1.21.5 → 1.21.6 player gates: bounded source comparison

## Scope and disposition

**Disposition:** No accepted historical local-player movement difference found in the assigned slices. The pair shows an outbound input synchronization change in `LocalPlayer`; it updates only the initial baseline and packet emission, not the local movement input or predicates. Treat that network state change as outside the movement-parity finding scope. This is source coverage, not a no-difference claim for the full release pair.

**Bound:** Exact Mojang-mapped 1.21.5 versus 1.21.6 player locomotion gates: LocalPlayer tick/input order, sprint initiation and continuation, crouch/pose selection, held-item-use checks, and effect-based jump/move checks. The 1.21.5–1.21.6 endpoint comparison narrows the prior 1.21.5–1.21.8 endpoint work only through 1.21.6. It does not locate a change later in that wider interval; 1.21.6–1.21.8 and the other pair inventories remain open.

**Excluded / not audited here:** rendering and sound-only additions, remote player behavior, vehicle physics, collision/AutoJump geometry and their shape dependencies, fluids/contact responses, server-side movement replay, and non-player locomotion. `canPlayerFitWithinBlocksAndEntitiesWhen` is recorded only as an unchanged crouch gate; its geometry is outside this assignment.

## Tick and input consumption

- `decompiled_minecraft/1.21.5/mojmap/net/minecraft/client/player/LocalPlayer.java:191-200, 269-277` and `decompiled_minecraft/1.21.6/mojmap/net/minecraft/client/player/LocalPlayer.java:197-205`: both call `super.tick()` before comparing `lastSentInput` with `input.keyPresses` and sending changed `ServerboundPlayerInputPacket` state. 1.21.5 initializes `lastSentInput = Input.EMPTY`, accepts the prior shift-key boolean in its constructor, and calls `sendShiftKeyState()` after `super.tick()`. 1.21.6 seeds `lastSentInput` from the constructor's `Input` argument and removes the separate shift-key command. `lastSentInput` gates outbound packets only; it is not read by local movement predicates. This changes whether an initial/shift state packet is sent to the server, not the LocalPlayer tick's input value.
- Both `LocalPlayer.aiStep()` bodies, `LocalPlayer.java:693-703`, snapshot jump, shift, and forward impulse and recompute `crouching` before `input.tick()`. `KeyboardInput.tick()` then refreshes key presses and its normalized move vector; its source is identical on both releases. `LocalPlayer.applyInput()` (`LocalPlayer.java:607-620`) uses that refreshed vector and jump flag when called later by `LivingEntity.aiStep()` (`LivingEntity.java:2712` in 1.21.5; `2764` in 1.21.6). The LocalPlayer and LivingEntity order is unchanged. AutoJump's `makeJump()` and associated geometry are not assessed.

## Sprint and crouch / pose gates

- The sprint trigger reset and initiation block is identical: `LocalPlayer.java:718-744` on both sides. Shift, local item use while not a passenger, or backward input clears `sprintTriggerTime`; `canStartSprinting()` then requires forward impulse, adequate food (passenger, food level `> 6.0F`, or may-fly), no item use, no blindness, a sprint-capable authoritative vehicle when riding, and the existing fall-flying / slow-move / water gates. The double-tap counter remains 7 ticks; explicit sprint input still starts sprint after the predicate succeeds.
- Continuation predicates are identical: `LocalPlayer.java:837-855`. Run sprint stops on blindness, disallowed vehicle, no forward impulse, insufficient food, a major horizontal collision, or being in water but not underwater. Swim sprint stops on blindness, disallowed vehicle, leaving water, missing forward impulse while airborne and not shifting, or insufficient food. The collision/contact subconditions are noted as gates only; their geometry and fluid semantics are outside this slice.
- The `crouching` assignment at `LocalPlayer.java:693-703` has the same flying, swimming, passenger, crouch-fit, shift, sleeping, and standing-fit checks. `LocalPlayer.isShiftKeyDown()` reads current client input and `isCrouching()` returns the assigned field (`LocalPlayer.java:593-605`). `Player.getDesiredPose()` retains the same shift-and-not-flying CROUCHING/otherwise STANDING expression (`Player.java:468-476` in 1.21.5; `478-486` in 1.21.6). No crouch-state reset or pose decision change was found.

## Effect-based jump/move predicates and inherited gate

- `LivingEntity.getJumpPower`, `getJumpBoostPower`, and `jumpFromGround` are textually unchanged (`LivingEntity.java:2160-2185` in 1.21.5; `2192-2217` in 1.21.6). Jump boost remains `0.1F * (amplifier + 1.0F)` added after jump-strength × multiplier × block-jump-factor; the result still gates at `<= 1.0E-5F`, writes `max(jumpPower, currentY)`, and adds the same sprint impulse.
- Slow-falling gravity and the jump effect checks remain unchanged (`LivingEntity.java:2208-2210, 2230-2235, 2725-2749` in 1.21.5; `2240-2242, 2282-2290, 2777-2800` in 1.21.6). The effect predicate order and arithmetic did not change. Other audited movement effects retained their source expressions in the matched movement methods.
- 1.21.6 adds `&& this.isEffectiveAi()` to LivingEntity's travel gate (`LivingEntity.java:2815-2819`; 1.21.5 counterpart `2763-2767` checks `canSimulateMovement()` alone). For `Player`, both `canSimulateMovement()` and `isEffectiveAi()` return the same `!level().isClientSide || isLocalPlayer()` expression (`Player.java:1321-1328` in 1.21.5; `1333-1340` in 1.21.6). Therefore the added term does not alter a local player's travel reachability; for remote client players both existing/added player predicates are false. This is not an accepted player movement difference.

## Source identity and hashes

Both sides use Mojang mappings (`mojmap`). On 1.21.5, `decompiled_minecraft/1.21.5/mojmap.ready.json` identifies release 1.21.5 and `mojmap`; its source-manifest SHA-256 matches the bytes of `mojmap.sources.sha256`. The sidecar `decompiled_minecraft/1.21.5/mojmap.provenance.json` records the exact run and ready marker. The source tree has no hidden `.decompile-provenance.json`; the adjacent ready marker and source manifest are the applicable publication evidence. On 1.21.6, the hidden `decompiled_minecraft/1.21.6/mojmap/.decompile-provenance.json` records exact client/mapping inputs and per-source hashes; no adjacent ready marker is present. Every listed Java file below matched its side's manifest entry.

| Exact source file, relative to its `decompiled_minecraft/<version>/mojmap/` root | 1.21.5 SHA-256 | 1.21.6 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/player/LocalPlayer.java` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/client/player/ClientInput.java` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| `net/minecraft/client/player/KeyboardInput.java` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |
| `net/minecraft/world/entity/player/Input.java` | `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` | `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` |
| `net/minecraft/world/entity/player/Player.java` | `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/LivingEntity.java` | `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/Entity.java` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |

1.21.5 source-manifest SHA-256: `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` (matches `mojmap.ready.json`). 1.21.6 hidden provenance-manifest SHA-256: `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`.

## Coverage state

Examined paired source slices: LocalPlayer construction, `tick`, input consumption, `applyInput`, sprint start/continue/stop, crouch state, desired pose, and local movement predicates; ClientInput, KeyboardInput and Input; Player's movement-authority / pose gates; LivingEntity jump, jump-boost, slow-falling and travel gates; Entity authority source used by the comparison. No accepted delta in the assigned player-state gates. Partial/unexamined: geometry and dependent block collision providers; fluid/contact paths; complete pair tick graph and independent audit; remaining 1.21.6–1.21.8 boundary. No tests, launches, source generation, network access, or product/history/wiki reads were performed; no Git changes were made.


