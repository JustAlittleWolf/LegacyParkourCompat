# F02: Toggle-use mode can keep local player use slowdown active after mouse release

- **1.21.8**, mojmap: `net/minecraft/client/Options.java`; line 494.
- **1.21.10**, mojmap: `net/minecraft/client/Options.java`; lines 492–494.

## Difference

Under the same precondition that the local player is using an item, in 1.21.8 releasing the use mouse button sends `down=false` to an ordinary `KeyMapping`; `Minecraft.handleKeybinds` then releases item use.

In 1.21.10, if the user enabled the default-off `toggleUse` option, the same mouse release reaches `ToggleKeyMapping.setDown(false)`, which leaves the key state unchanged.

While the player remains in an active use action, the release guard therefore does not call `releaseUsingItem` on that tick.

The player movement transform in `LocalPlayer` is unchanged.

While the player remains in that action and is not a passenger, it multiplies a nonzero local movement input by `0.2F`.

This mechanic is opt-in; with the default `toggleUse=false`, 1.21.10's mapping follows held-key behavior like 1.21.8.

## Movement meaning

Mouse press/release -> `MouseHandler` -> `KeyMapping.set` -> 1.21.10's `keyUse` toggle state -> `Minecraft.handleKeybinds` use/release decision -> local player's active-use state -> `LocalPlayer.applyInput` / `modifyInput` -> `LivingEntity.aiStep` travel input and movement.

`Minecraft.tick` handles key bindings before it calls the client level entity tick on both sides (1.21.8 lines 1740–1762; 1.21.10 lines 1769–1785).

The local-player input path runs when that player is ticked, controls the camera, has nonzero input, and is not a passenger for the use slowdown.

No movement resource, tag, attribute, effect or server-supplied value selects the new toggle state.

Item-specific use completion and any server-driven stop remain external conditions; the finding only claims the source-controlled release decision and local movement multiplier while use remains active.

Source-proven: with the option enabled, physical release does not clear 1.21.10's use mapping; while an item-use action remains active, the matching release guard differs from 1.21.8.

During that state, local movement input is scaled by `0.2F` under the stated local-player guards.

Predicted only: releasing use before an action would otherwise end can produce additional ticks of movement slowdown in 1.21.10.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.8--1.21.10/findings/F02-toggle-use-extends-player-use-slowdown.md).
