# Bounded verdict revision (audit addendum)

This addendum supersedes the input-path conclusion in `audit.md` while preserving that file unchanged. `audit.md` SHA-256 remains `B8305815133EFCD07F3EED22956399B59652B218256B0FFF25C64B3FFF775335`. The correction under audit remains `.native-source/20261030/autojump-witness31/report-correction.md`, SHA-256 `0D324A412834807BD085C4E559FF3575CEA305CF16E99500E982D6960ED8A3E3`.

## Revised input finding

I withdraw the audit's blanket statement that vanilla movement input is always zero throughout an existing pass-through inventory screen. The supported user input route must be included. A key press that closes an inventory screen is handled by its container screen, which returns true and prevents that one key event from directly updating bindings. But `Minecraft.openScreen(null)` / `setScreen(null)` calls `MouseHandler.grabMouse`; its `KeyBinding.setAll` / `KeyMapping.setAll` re-polls physically held keyboard keys (the method explicitly handles `KEYSYM`). When the sleep state is already present but an inventory screen is still open, the per-tick sleep-screen check has already skipped opening the bed screen. Closing the inventory during event polling therefore allows physically held keyboard movement bindings to be restored before that tick's local player update. That input is a legitimate vanilla binding path and is in scope. This also shows why `passEvents` and the inventory's consuming key handler do not close the whole reachability question.

Mouse-button-rebound movement does not provide the same bypass through vanilla container screens: both inventory container `mouseClicked` implementations return true even for an unsupported button, so `MouseHandler` does not forward it to `KeyBinding.set`. `setAll` also polls only `KEYSYM`, not mouse bindings. I do not use mouse remapping as an exclusion or as the counterexample.

## Gate that closes the sleeping timer path

The nonzero-input route does not reach the entity scan in the local sleeping pose. Both endpoint auto-jump methods first inspect the block containing the player's pose top and immediately return when that block's collision shape is nonempty:

- 1.13.2 `PlayerEntity.sleep` sets width/height to `0.2F` and positions the sleeping player at the bed block's X/Z plus an in-block offset and `bedY + 0.6875F` (`PlayerEntity.java:1266–1280`). The auto-jump top is therefore `bedY + 0.8875F`; `new BlockPos(x, top, z)` selects the bed's own block. `BedBlock.getShape` returns the nonempty `SHAPE` (`BedBlock.java:41,210–212`). `LocalClientPlayerEntity.autoJump` calls `blockState.getCollisionShape(...).isEmpty()` and exits when false (`LocalClientPlayerEntity.java:909–915`).
- 1.14.4 `LivingEntity.startSleeping` selects `Pose.SLEEPING`, calls `setPosToBed`, and zeros velocity (`LivingEntity.java:2712–2726`); `setPosToBed` uses `bedY + 0.6875F` (`2730`). The 0.2F pose top is likewise inside the bed's Y block; `BedBlock.getShape` returns an orientation-specific union containing the nonempty bed base (`BedBlock.java:47–55,192–200`). `LocalPlayer.updateAutoJump` performs the same nonempty-collision-shape early return at `LocalPlayer.java:884–889`.

This bed-block test is an unconditional earlier auto-jump gate for a valid sleeping pose over its bed, even if a held keyboard binding produces nonzero input and every entity-bound condition is arranged. It occurs before entity-query construction, so no old/new entity query result can set timer `1` while sleeping. Consequently the proposed sleeping-tick timer followed by wake-before-next-input path is not reachable: there is no sleeping-tick timer to carry. A timer armed before sleep is consumed by the first sleeping local input tick; if a wake packet instead arrives before that input tick, the player did not undergo the relevant sleeping-pose auto-jump call.

## Dying pose conclusion

The dying pose has no bed-block gate, and the query can be invoked after an inventory screen remains open. However the vanilla screen provides no in-scope movement-binding route while DYING: ordinary key presses are consumed by the container screen; closing it substitutes the death screen and calls release-all, while its click handler consumes clicks. If no screen is already open, the death screen is opened and releases bindings before entity ticking. In 1.13.2 server health/status packets are processed after entity ticking; in 1.14.4 queued packets run before the tick and the null-screen death-screen check. Local client damage does not provide an intervening death-pose tick. Thus no nonzero keyboard input reaches the dying-pose auto-jump scan through the checked vanilla input/UI routes. If some non-vanilla or otherwise out-of-scope source externally populates `Input`, the timer might differ while immobile, but a timer-triggered jump is still cleared by death immobility before jump physics and does not survive respawn.

## Verified source anchors and added hashes

Existing sources listed in `audit.md` are unchanged. Additional exact file identities:

- 1.13.2 Feather `net/minecraft/block/BedBlock.java`: SHA-256 `A274ED3D3DD8E4F4895FE1F3D1444425A9248E9CD41BC6E1F2677AF7DACBEC18`.
- 1.14.4 Mojmap `net/minecraft/world/level/block/BedBlock.java`: SHA-256 `01415D1783F2346375A282F84E203625F00FCDB377E5875A9F22C7DBD8A10C17`.
- 1.13.2 `Minecraft.openScreen`: screen-close-to-null path and mouse grab `Minecraft.java:643–668`; `MouseHandler.grabMouse` re-polls key bindings `MouseHandler.java:243–257`; `KeyBinding.setAll` limits this poll to `KEYSYM` `KeyBinding.java:46–51`.
- 1.14.4 corresponding `Minecraft.setScreen`: `Minecraft.java:729–755`; `MouseHandler.grabMouse`: `MouseHandler.java:249–264`; `KeyMapping.setAll`: `KeyMapping.java:47–52`.
- 1.13.2 `InventoryMenuScreen.keyPressed` closes on inventory/Escape then returns true `InventoryMenuScreen.java:523–543`; mouse handler returns true `:267–342`.
- 1.14.4 `AbstractContainerScreen.keyPressed` closes on inventory/Escape then returns true `AbstractContainerScreen.java:533–553`; mouse handler returns true `:271–350`.
