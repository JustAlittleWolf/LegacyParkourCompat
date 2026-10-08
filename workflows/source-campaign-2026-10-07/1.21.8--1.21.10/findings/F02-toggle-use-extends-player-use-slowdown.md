# F02: Toggle-use mode can keep local player use slowdown active after mouse release

- Older version A: `1.21.8`
- Newer version B: `1.21.10`
- Mechanic / coverage slice IDs: S1.2, S1.6
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior, conditional on enabling `toggleUse`, controlling the local player camera, being in an active item-use action, and not riding a passenger vehicle
- First changed release: unknown within (`1.21.8`, `1.21.10`]
- Runtime validation: not performed

## Paired evidence

All source paths below are relative to the matching `ready/<version>/mojmap` root in the run artifact manifest.

- A: `net/minecraft/client/Options.java`, `keyUse` declaration line 494; SHA-256 `24B6E50A9376761ADFC935113DA78B58D11A88376097F9A6A5947B520CD3A3C3`. `keyUse` is an ordinary `KeyMapping`.
- A: `net/minecraft/client/gui/screens/options/controls/ControlsScreen.java`, `options(Options)` lines 14–16; SHA-256 `1C718BA8CBD5A28FC4709AB58E52C8731F82B209F7E649D9E7EA567F4A2D107B`. Its complete controls-option array contains crouch/sprint toggles, auto-jump and operator-items options; it has no use-toggle option.
- B: `net/minecraft/client/Options.java`, `toggleUse` declaration lines 492–494, `keyUse` declaration line 530, accessor lines 1090–1092 and serialization line 1267; SHA-256 `791DF2C2FD5B37C6E8C3D7775EF7797A20A5839DB00445B383CBC37662E7CEC8`. `toggleUse` defaults to `false`; `keyUse` is a `ToggleKeyMapping` supplied with `this.toggleUse::get`.
- B: `net/minecraft/client/gui/screens/options/controls/ControlsScreen.java`, `options(Options)` lines 14–17; SHA-256 `E3531BB471440179FF38D46A776CE76F3D9915E09055F1EEC90A9E5E14CED4B6`. The option array exposes both `toggleUse()` and `toggleAttack()`.
- A: `net/minecraft/client/MouseHandler.java`, mouse-button dispatch lines 140–146; SHA-256 `581910DE8A6C0BBBF876F60CF0A05D5A9B50C7E0FF5009D637AAF39E59EA39A3`. It calls `KeyMapping.set(mouseKey, down)` for both press and release.
- B: same class, dispatch lines 141–146; SHA-256 `191FB18604F18EFC57B9604813845CF6172498DF864FFB486CE59E80003A6E00`; mouse events likewise call `KeyMapping.set(mouseKey, down)`.
- A: `net/minecraft/client/Minecraft.java`, `handleKeybinds()` lines 1911–1940; SHA-256 `8508BCB2BBE4D48B3628BF4C87F87F719D07B0F60E7811A8BC6322513E55F973`. While using an item, it calls `releaseUsingItem` when `keyUse.isDown()` is false.
- B: same class/member, lines 1935–1970; SHA-256 `AE782D427FF3F2B0A15BD58FE447BC3A07B216FAED243A7618420F816516BE50`; it has the corresponding release guard and starts use while `keyUse.isDown()` when the player is not already using an item.
- A: `net/minecraft/client/ToggleKeyMapping.java`, `setDown(boolean)` lines 14–23; SHA-256 `7830BE5BCE2146E9F8D69E79CA6F0F94617711B2F0F142CA826DD22AE23B4100`. The old use mapping does not use this class; its own `Options.keyUse` declaration is `KeyMapping` above.
- B: `net/minecraft/client/ToggleKeyMapping.java`, `setDown(boolean)` lines 26–35; SHA-256 `C580F39E7D4202C7C29B11E5E2132D447BEFA36D7A5ADA8E898739A90F744CD7`. With toggle enabled, a press flips the down-state; a release (`false`) does not call the superclass setter and leaves that state unchanged.
- A: `net/minecraft/client/player/LocalPlayer.java`, `applyInput()` lines 608–620 and `modifyInput(Vec2)` lines 623–639; SHA-256 `53F2A71A886B9C71853AFE36F2DF857F80A9BF1CD6EC4CA8604CCD7E238D89EE`. Under controlled-camera input, `modifyInput` multiplies the movement vector by `0.2F` while `isUsingItem()` and not a passenger.
- B: same class/members, `applyInput()` lines 612–624 and `modifyInput(Vec2)` lines 627–643; SHA-256 `9C1DF00E2F8379B2C19577A3691FEE28071D8925210BE3D3DF928B5352E367E1`. The same `0.2F` scaling and guards apply.
- A: `net/minecraft/world/entity/LivingEntity.java`, `aiStep()` line 2764 calls `applyInput()`; SHA-256 `609F0197A0B4551AB42279E452C11CDD256135B1D467C2A95950B0E9FD7DDEF8`.
- B: same class/member, `aiStep()` line 2794 calls `applyInput()`; SHA-256 `B8B49D60769203F7BD5AFE4A1BFFCDCDBEC30BE28960324CDC43A2DF85A6EB66`.

## Source-level difference

Under the same precondition that the local player is using an item, in A releasing the use mouse button sends `down=false` to an ordinary `KeyMapping`; `Minecraft.handleKeybinds` then releases item use. In B, if the user enabled the default-off `toggleUse` option, the same mouse release reaches `ToggleKeyMapping.setDown(false)`, which leaves the key state unchanged. While the player remains in an active use action, the release guard therefore does not call `releaseUsingItem` on that tick.

The player movement transform in `LocalPlayer` is unchanged. While the player remains in that action and is not a passenger, it multiplies a nonzero local movement input by `0.2F`. This mechanic is opt-in; with the default `toggleUse=false`, B's mapping follows held-key behavior like A.

## Reachability and dependencies

Mouse press/release -> `MouseHandler` -> `KeyMapping.set` -> B's `keyUse` toggle state -> `Minecraft.handleKeybinds` use/release decision -> local player's active-use state -> `LocalPlayer.applyInput` / `modifyInput` -> `LivingEntity.aiStep` travel input and movement.

`Minecraft.tick` handles key bindings before it calls the client level entity tick on both sides (A lines 1740–1762; B lines 1769–1785). The local-player input path runs when that player is ticked, controls the camera, has nonzero input, and is not a passenger for the use slowdown. No movement resource, tag, attribute, effect or server-supplied value selects the new toggle state. Item-specific use completion and any server-driven stop remain external conditions; the finding only claims the source-controlled release decision and local movement multiplier while use remains active.

## Consequence and uncertainty

Source-proven: with the option enabled, physical release does not clear B's use mapping; while an item-use action remains active, the matching release guard differs from A. During that state, local movement input is scaled by `0.2F` under the stated local-player guards.

Predicted only: releasing use before an action would otherwise end can produce additional ticks of movement slowdown in B. No trajectory or runtime reproduction was measured. The first release containing this option is unknown within the pair's interval because intermediate releases were not audited.

## Handoff

Independent delta: B adds a default-off `toggleUse` option that can preserve local item-use slowdown after mouse release. Applicability requires the option enabled, an active item-use action, controlled-camera local-player input, and no passenger state. Related finding IDs: none. Release boundary: unknown within (`1.21.8`, `1.21.10`]. Implementation and runtime decisions are deferred.
