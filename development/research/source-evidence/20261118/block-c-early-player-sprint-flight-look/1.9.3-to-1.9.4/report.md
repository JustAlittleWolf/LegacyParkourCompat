# 1.9.3 → 1.9.4: local sprint and ability-flight/input slice

## Result and boundary

Compared the ordinary local-player sprint timer and sprint transitions; keyboard sneak scaling and local ability-flight input/vertical adjustment; and the ability-flight `fallDistance` reset reached through player movement. These reviewed slices have no source difference between 1.9.3 and 1.9.4. This is a bounded slice result, not full-pair coverage or a movement-parity claim. No finding is asserted.

The review is restricted to client/local-player movement and direct player movement state. It does not cover food production or other non-movement producers, environmental block behavior, or unrelated player state.

## Source identity and integrity

Canonical local source roots: `decompiled_minecraft/1.9.3/ornithe-feather/` and `decompiled_minecraft/1.9.4/ornithe-feather/` (Ornithe Feather mapping family).

- **1.9.3:** `decompiled_minecraft/1.9.3/ornithe-feather/.decompile-provenance.json` records the exact 1.9.3 Mojang client input (`b6985b0d.../client.jar`, SHA-256 `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`), Feather metadata for 1.9.3 (SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`), and `feather-gen2-1.9.3+build.2-mergedv2.jar` (SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`). Provenance-file SHA-256: `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.
- **1.9.4:** `decompiled_minecraft/1.9.4/ornithe-feather.provenance.json` and its ready marker identify version `1.9.4`, mapping `ornithe-feather`, and Feather Gen2 build.2 inputs. The artifact manifest pins the client (`23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`), merged mapped archive (`49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`) and Tiny file (`9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`). The ready marker binds source-manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; the verification record reports all 1,819 source files identical to the published tree and all raw input artifacts identical. Provenance-file SHA-256: `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`.

For each listed source below, the observed file SHA-256 matched its release's local declared source-manifest entry. Identical hashes across releases mean byte-identical complete files; `PlayerEntity.java` is the one exception and was checked at the specific method as stated below.

| Source file | 1.9.3 SHA-256 | 1.9.4 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | same |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | same |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | same |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | same |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` |

The compared `PlayerEntity.moveRelative(float sideways, float forwards)` body (1.9.3 lines 1372–1390; 1.9.4 lines 1372–1390) has the same normalized UTF-8/LF body SHA-256: `352f16d2ceb93ea1f2ae5c71410cda46042bfc912d98ca70bd9bdeee72623072`.

## Compared operations

### Local sprint timer and transitions — no difference

`net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`:

- The integer `sprintTimer` is declared at line 87 (default Java zero); `mobTick()` increments it at line 605. `setSprinting(boolean)` calls `super.setSprinting` and then unconditionally sets `sprintTimer = 0` at lines 405–409, on both enabling and disabling.
- In the same `mobTick()`, positive `doubleTapSprintTime` decrements before current input is ticked (lines 604–609). Item use while not riding resets it to zero after `input.tick()` (lines 649–654); the grounded double-tap-forward path initializes it to 7 when expired and sprint is not held, otherwise enables sprint (lines 661–675). The held-sprint start and stop gates follow at lines 677–688.
- The complete local-player source file has the same SHA-256 on both releases, covering these paths and their surrounding gates.

### Sneaking input and ability-flight controls — no difference

`net/minecraft/client/entity/living/player/KeyboardInput.java`, `tick()`, lines 13–50, builds movement components as floats from key presses, sets the jump/sneak booleans, then when sneaking multiplies sideways and forward movement by `0.3` and casts each result to `float`.

In `LocalClientPlayerEntity.mobTick()`, lines 645–654, the previous jump/sneak state is saved and `input.tick()` runs before the item-use scaling. Later, lines 715–724, the exact gate is `abilities.flying && isCamera()`. If also sneaking, the code divides sideways then forward input by the double literal `0.3`, casting each quotient to `float`, then subtracts `abilities.getFlySpeed() * 3.0F` from `velocityY`. A subsequent independent `input.jumping` check adds the same fly-speed term to `velocityY`. The order, types, casts and gates match.

### Ability-flight `fallDistance` reset and tick phase — no difference

`net/minecraft/entity/living/player/PlayerEntity.java`, `moveRelative(float sideways, float forwards)`, lines 1372–1389, saves position, and enters the ability-flight branch exactly when `abilities.flying && !isRiding()`. It saves `velocityY` and `speedInAir`, sets air speed to `abilities.getFlySpeed() * (isSprinting() ? 2 : 1)`, calls `super.moveRelative(sideways, forwards)`, restores vertical velocity as saved `g * 0.6`, restores air speed, sets `fallDistance = 0.0F`, then clears flag 7. The paired method bodies have identical normalized hashes even though the enclosing `PlayerEntity.java` files differ elsewhere.

Tick phase is confirmed by the common call chain: `LivingEntity.tick()` invokes virtual `this.mobTick()` at line 1551; `LocalClientPlayerEntity.mobTick()` processes sprint and flight inputs before `super.mobTick()` at line 755; `LivingEntity.mobTick()` calls `serverTickAi()` in its AI phase and only then enters travel, attenuates sideways/forward speed by `0.98F`, calls `flyingTick()`, and dispatches virtual `this.moveRelative(...)` at lines 1645–1711. Thus the player ability-flight reset occurs in the travel movement operation after local input/vertical flight adjustment. The nearby Elytra glide branch in `LivingEntity.moveRelative()` has its own `fallDistance = 1.0F` condition; it is distinct from this ability-flight reset.

