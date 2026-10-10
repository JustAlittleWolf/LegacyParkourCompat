# F004: Character-code key binding polling differs in 1.9.4 and 1.10.2

- Older version A: 1.9.4, Ornithe Feather `net.ornithemc:feather-gen2:1.9.4+build.2`
- Newer version B: 1.10.2, Ornithe Feather `net.ornithemc:feather-gen2:1.10.2+build.2`
- Mechanic / coverage slice IDs: keyboard movement-input state; `LOCAL-KEYBOARD-INPUT-SAMPLING`, `INV-TICK`
- Classification: paired input-state polling difference; player-movement applicability not established
- Confidence: source-confirmed polling difference; reachable movement-state producer unresolved
- Applicability: none established. The previously proposed ChatScreen-return path is withdrawn because screen entry clears bindings and screen event handling does not set them.
- First changed release: unknown within (1.9.4, 1.10.2]; only endpoint sources were inspected
- Runtime validation: not performed

## Paired source evidence

- A `KeyBinding.java` SHA-256 `29cf0668c652d8d830b3926c2be5d87bf3e8ddc43411dacbc1319c1a96eb4ecb`: `setAll()` lines 40-47 polls every configured code and catches `IndexOutOfBoundsException` without assigning a new pressed state when the poll throws.
- B `KeyBinding.java` SHA-256 `208ee44f87b9cbc99b5fdfa5f99f026be68677f11c25f6dd02aebf3cce9321b`: `setAll()` lines 40-47 adds `keyCode < 256 &&` before the poll, assigning false for character codes >=256.
- A/B `Minecraft.java` hashes are `6778bd1f0e8aa8a29fdce3bc5285cbd3ea56fa18ca8a43afc4d88439ab12133e` and `0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9`. Their keyboard event mapping/writer is A lines 1497-1541 and B lines 1487-1530. A maps `eventKey == 0` to `eventCharacter + 256`; in the no-screen event handler, a press sets the binding true and a release sets it false.
- A/B `Screen.java` hashes are `d829d9ee005f1623eb08b2afe6890f27626c77f35d467c1966e589a66a2056c4` and `0cce54bfad02869726923530d7dbca186c5e130578dfe65d03c837f9d6085215`. Their `handleInputs()` bodies (A 367-379; B 372-384) drain `Keyboard.next()` and invoke the screen handler. That handler calls `keyPressed` and `handleGuiKeyBindings`; it does not call `KeyBinding.set`.
- A/B `ChatScreen.java` hashes are `2e7a96577f91e4e688b5cc4a598bae3656cede55d0c76a428b9d0d61d15c5cfe` and `cfd95fa9a2dbc32d52a8b3138353fcee3de1c93887f6cba87b4bb9e54474d099`. The close path calls `openScreen(null)`; it does not produce a pressed movement binding.
- A/B `Minecraft.openScreen` (A 761-797; B 763-799) calls `KeyBinding.releaseAll()` when opening a non-null screen. `lockMouse()` calls `setAll()` on focus reacquisition only when the display is active and internal focus was false (A 1130-1143; B 1120-1133). Thus the proposed chat route enters `setAll()` with the prior binding cleared. During that tick the screen drains keyboard events before `Minecraft.handleKeyboardEvents`; any later no-screen keydown is processed after `setAll()` and sets the same true state in both versions. A release before sampling also removes the proposed stale state.
- A/B `KeyboardInput.java` is identical, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`. Its `tick()` reads movement bindings into axes. A/B `LocalClientPlayerEntity.java` hashes are `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` and `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`; `serverTickAi()` copies those axes for the camera player only. This confirms a possible consumer for a differing key state, not a producer for one.
- Exact revised Feather bytecode also confirms A's polling/catch and B's >=256 false branch. Those statements bind to revised immutable jars A `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3` and B `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`.

## Source manifests and provenance

- A source-manifest SHA-256: `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; artifact-manifest SHA-256: `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`.
- B source-manifest SHA-256: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; artifact-manifest SHA-256: `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`.
- Revised Feather revision JSON hashes: A `df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915`, B `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`. The revision records establish raw-input and source-tree identity; original derived-JAR byte identity remains unproven. Keep this provenance limitation separate from the producer reachability gap.
- The canonical campaign `version_manifest_v2.json` SHA-256 is `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04`; it lists the intervening stable release IDs `1.10` and `1.10.1`. Neither has a ready source marker in the shared cache. The exact first release containing the polling delta is therefore unresolved; do not call 1.10.2 the introduction boundary.

## Open dependency and handoff

- `F004-PRODUCER`: find a reachable paired source path that leaves a configured >=256 movement binding true immediately before A's guarded `setAll()` without an intervening `releaseAll()`, while B clears it. The chat-return route is not such a witness. If no such path exists, close the input-polling difference as lacking established player-movement applicability rather than retaining it as an emulatable movement finding.
- `F004-BOUNDARY`: inspect exact sources for 1.10 and 1.10.1, using source-owner-published readiness artifacts, to determine whether either is the first changed release. Do not decompile or populate the shared cache from this worker.
- Runtime behavior was not tested. This revision is not accepted for implementation; obtain a fresh independent blind review of its changed claim and evidence. The pair remains partial and the implementation handoff remains blocked.

This revision supersedes the finding text in snapshot `FS-1.9.4-1.10.2-2026-10-08-character-key-r1` (author snapshot commit `0202863998a8c6d9a3ff6555235f2add358eca1d`). Preserve that snapshot and its review as history.
