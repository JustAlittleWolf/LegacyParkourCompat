# F01: A shared physical key no longer selects only one movement mapping

- Older version A: `1.21.8`
- Newer version B: `1.21.10`
- Mechanic / coverage slice IDs: S1.1, S1.2
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior, conditional on the user assigning one physical key to both forward and backward
- First changed release: unknown within (`1.21.8`, `1.21.10`]
- Runtime validation: not performed

## Paired evidence

All paths below are relative to the matching `ready/<version>/mojmap` source root in the run artifact manifest.

- A: `net/minecraft/client/KeyMapping.java`, `net.minecraft.client.KeyMapping#set(InputConstants.Key, boolean)` lines 48–53 and `resetMapping()` lines 77–83; SHA-256 `D5475012D2849E7547E23D1584D5B099661A2CB1E60DAEBE6EC19CB28B779111`. `MAP` is `Map<InputConstants.Key, KeyMapping>` (line 16); `set` looks up one value with `MAP.get($$0)` and invokes `setDown` only on that value; reset populates it with `MAP.put($$0.key, $$0)`, so duplicate keys overwrite earlier entries.
- B: `net/minecraft/client/KeyMapping.java`, `net.minecraft.client.KeyMapping#set(InputConstants.Key, boolean)` lines 33–44, `resetMapping()` lines 78–84 and `registerMapping(InputConstants.Key, KeyMapping)` lines 182–184; SHA-256 `6AA1388D6348684DF6E85E3227B186839B4528FAE59A5595436538D7101858A9`. `MAP` is `Map<InputConstants.Key, List<KeyMapping>>` (line 21); `set` delegates to `forAllKeyMappings`, and reset registers each mapping in the list.
- A: `net/minecraft/client/gui/screens/options/controls/KeyBindsScreen.java`, `keyPressed(int, int, int)` lines 70–81; SHA-256 `37FB7AF767757BC73759BDAC2CD0E7577BED98D18D2566E172D16C381B5CF909`. The selected mapping is assigned the requested key with `setKey` and mapping state is rebuilt; there is no occupied-key rejection in this path.
- B: same path and member, lines 72–87; SHA-256 `EE97164EAC9A048A2D159B95CED1B1F998F6090B48BF7F99AB3753D75A5EDB42`; same assignment and rebuild behavior.
- A: `net/minecraft/client/KeyboardHandler.java`, `keyPress(long, int, int, int, int)` lines 459–460 and 491–497; SHA-256 `EA1D04D02829D0AFA75D376C9478D092971337FF14F49CF6E4A240572991DC74`. Keyboard press/release dispatches `KeyMapping.set(key, true/false)` when gameplay has focus.
- B: same class/member, event dispatch at lines 502, 525 and 556–559; SHA-256 `6CC5209BCF738DB31761E3B76F3B9A2C3CF5D7BEEA53B08B3FE719B0B86B0AB1`; gameplay key events likewise reach `KeyMapping.set`.
- A and B: `net/minecraft/client/player/KeyboardInput.java`, `tick()` lines 23–36 and `calculateImpulse(boolean, boolean)` lines 14–19; SHA-256 `D5CB0E93DF7F66755172D74E028225012EE33C6E4F0D510A1D8E25BA5497C0A3` on both sides. The method reads `keyUp.isDown()` and `keyDown.isDown()` into forward/backward input; `calculateImpulse` returns `0.0F` when its two booleans match, otherwise `1.0F` or `-1.0F`.
- A: `net/minecraft/client/player/LocalPlayer.java`, `tick()` lines 195–203, `aiStep()` lines 686–706 and inherited `applyInput()` override lines 611–619; SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`.
- B: same class/members, `tick()` lines 195–203, `aiStep()` lines 686–706 and `applyInput()` override lines 611–619; SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`.
- A: `net/minecraft/world/entity/LivingEntity.java`, `aiStep()` lines 2721–2818; SHA-256 `609F0197A0B4551AB42279E452C11CDD256135B1D467C2A95950B0E9FD7DDEF8`. It calls `applyInput`, builds the travel vector from `xxa`/`yya`/`zza`, then reaches `travel` under its movement guard.
- B: same class/member, `aiStep()` lines 2751–2849; SHA-256 `B8B49D60769203F7BD5AFE4A1BFFCDCDBEC30BE28960324CDC43A2DF85A6EB66`; corresponding input application and travel path.
- A: `net/minecraft/world/entity/Entity.java`, `moveRelative(float, Vec3)` lines 1530–1533; SHA-256 `C403E6176D27B5BFD6AAA0DEA3735FFA4FCF80DBAE58766661DD84453B7E9704`. It converts the input vector and adds the result to delta movement.
- B: same class/member, lines 1590–1593; SHA-256 `8361DBB86FE6C975D21F69D008377B6F191669BE751150C842517E9D0346FA18`; corresponding input vector is added to delta movement.

## Source-level difference

Under the same concrete precondition—both movement mappings are initially released, and the user assigns one physical key to both forward (`keyUp`) and backward (`keyDown`)—pressing that key updates only the single mapping stored for the key in A. The identity of that mapping depends on the `ALL` iteration/overwrite order and is deliberately not asserted. Consequently, exactly one of the two input booleans is down and the forward/backward impulse is `+1.0F` or `-1.0F`.

In B, the physical key maps to a list and the key event sets every mapping in it. Both booleans become down, so `calculateImpulse` returns `0.0F`. The `KeyboardInput` implementation is otherwise byte-for-byte identical in the verified paired source artifact.

## Reachability and dependencies

Client keyboard press/release -> `KeyboardHandler.keyPress` -> `KeyMapping.set` -> mapping value(s) -> `KeyboardInput.tick` reads forward/backward mapping state -> `LocalPlayer` input tick -> `LivingEntity.aiStep` calls `applyInput`, constructs movement input, and reaches travel -> movement-relative acceleration adds the transformed input to player delta movement.

The player input path is active when the local player is controlled and movement simulation is enabled. The finding concerns a user-configured duplicate key; it does not affect default bindings, where forward and backward use distinct keys. No resource, tag, attribute, effect, equipment, or server-supplied value participates in selecting or calculating these two key states. A's duplicate winner is not resolved here. The existing player velocity and other movement forces remain independent inputs to the later movement step.

## Consequence and uncertainty

Source-proven: for the stated precondition, A supplies a nonzero forward/backward input component while B supplies zero; that component reaches the player's movement travel path. `Entity.moveRelative` adds the resultant input acceleration to delta movement.

Predicted only: the player's resulting velocity and position can therefore diverge between versions under otherwise matching state. No trajectory or runtime reproduction was measured. The first release containing B's list-valued fan-out is unknown within the pair's interval because intermediate releases were not audited.

## Handoff

Independent delta: duplicate physical keyboard assignment to opposing player movement mappings produces a nonzero directional input in A and a zero forward/backward input component in B. Applicability requires the duplicate user binding and an active local-player movement path. Related finding IDs: none. Release boundary: unknown within (`1.21.8`, `1.21.10`]. Implementation and runtime decisions are deferred.
