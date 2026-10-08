# F004: Character-key movement bindings retain a pressed state across chat return in 1.9.4

- Older version A: 1.9.4, Ornithe Feather `net.ornithemc:feather-gen2:1.9.4+build.2`
- Newer version B: 1.10.2, Ornithe Feather `net.ornithemc:feather-gen2:1.10.2+build.2`
- Mechanic / coverage slice IDs: keyboard movement-input state; `LOCAL-KEYBOARD-INPUT-SAMPLING`, `INV-TICK`
- Classification: input-state behavior difference
- Confidence: source-confirmed, with exact revised-artifact bytecode confirmation of key polling
- Applicability: local camera player; non-Mac platform; active display; a movement binding assigned through Controls to a positive character code (`char + 256`); that character key remains held when ChatScreen closes and its release event is not processed before the next input sample
- First changed release: 1.10.2 within this paired comparison; releases outside the pair were not assessed
- Runtime validation: not performed

## Paired evidence

- A `KeyBinding.java` SHA-256 `29cf0668c652d8d830b3926c2be5d87bf3e8ddc43411dacbc1319c1a96eb4ecb`: `setAll()` lines 40-47 polls `Keyboard.isKeyDown(keyCode)` and catches `IndexOutOfBoundsException` without changing the prior pressed state if the poll throws. A `Minecraft.java` SHA-256 `6778bd1f0e8aa8a29fdce3bc5285cbd3ea56fa18ca8a43afc4d88439ab12133e` maps `eventKey == 0` to `eventCharacter + 256` at line 1499 and writes the state to `KeyBinding.set` at lines 1538-1541. A `ControlsOptionsScreen.java` SHA-256 `a3b878360e97a06e44cc352bb6a7f23afa86a3d7dc4aea594e65b4ae212fc5b2` permits `chr + 256` for positive character input (lines 93-105). A `ChatScreen.java` SHA-256 `2e7a96577f91e4e688b5cc4a598bae3656cede55d0c76a428b9d0d61d15c5cfe` closes to `openScreen(null)` on Escape/Enter (lines 58-75).
- B `KeyBinding.java` SHA-256 `208ee44f87b9cbc99b5fdfa5f99f026be68677f11c25f6dd02aebf3cce9321b`: `setAll()` lines 40-47 evaluates `keyCode < 256 && Keyboard.isKeyDown(keyCode)`, explicitly clearing character-code bindings. B `Minecraft.java` SHA-256 `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9` has the same event mapping/write order at lines 1488 and 1526-1530. B `ControlsOptionsScreen.java` SHA-256 `ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e` permits the same binding; B `ChatScreen.java` SHA-256 `cfd95fa9a2dbc32d52a8b3138353fcee3de1c93887f6cba87b4bb9e54474d099` has the same close path.
- Paired `Minecraft.openScreen` / `lockMouse` bodies are in the Minecraft hashes above. A non-null screen calls `KeyBinding.releaseAll()`, while chat's `openScreen(null)` follows the null branch to `lockMouse()`. On an active non-Mac display with focus lost, `lockMouse()` calls `setAll()` without a preceding `releaseAll()`.
- A/B `KeyboardInput.java` is identical, full-file SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`. Its `tick()` reads movement bindings into axes. A/B local-player `serverTickAi()` copies those axes only for the camera player: A `LocalClientPlayerEntity.java` SHA-256 `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` lines 586-597; B SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` lines 602-613. Movement defaults match; A/B `GameOptions.java` SHA-256 values are `1bcddb6d5c8aa3f32119a11c35a9c351b4bd6bf36aefc7aa0599c0ab329ad41e` and `767ed017470a0909148017461668fa95a988f2f15582b4e2c6636b03502b4d43`.
- Vanilla metadata selects `org.lwjgl.lwjgl:lwjgl:2.9.4-nightly-20150209` off macOS for both versions (A metadata SHA-256 `4b21379203f0d87df8cc2ab4dd3c3689f139772f1cdb37b40688252547e2d5be`; B `d0624badea6367233d47c830341952126cdb0f99b1e11fb60428dd530a0d4db5`); the 2.9.2 entry is macOS-only. The cached LWJGL jar SHA-256 is `833e721817f70d1445eec13d8ce5a86e12af70efac5014356302e2bbc68b3fe2`. `javap` confirms keyboard size 256 and indexed `ByteBuffer.get(int)` in `Keyboard.isKeyDown`. Exact revised Feather bytecode confirms A's poll/catch and B's >=256 false branch.
- Source manifests: A SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; B `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`. Revised immutable Feather jars: A SHA-256 `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`, revision JSON `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`; B jar `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`, revision JSON `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. Revision records confirm source-tree/raw-input identity, but original derived jars are unavailable, so identity to those jars is unproven.

## Source-level difference

While ChatScreen is open, a character keydown maps to a code >=256 and sets its movement KeyBinding pressed. If the key remains held when Escape or Enter closes chat, `openScreen(null)` reacquires focus. A's `setAll()` catches the LWJGL bounds exception and preserves the pressed state; B sets that code false. The next `KeyboardInput.tick()` therefore retains the A movement binding but clears it in B.

## Reachability and uncertainty

Controls supports the character binding; keyboard handling delivers events to the current screen before writing the key state. The chat close path does not reach the `releaseAll()` call, and the local camera player's input sampler consumes the differing state into movement axes. The matching release must not be processed before the next sample. Default movement bindings below 256 are unaffected. Runtime behavior was not tested; original derived-jar byte identity is unproven.

## Handoff

Review this exact paired finding independently before implementation. The broader source report remains active and other inventories are open.