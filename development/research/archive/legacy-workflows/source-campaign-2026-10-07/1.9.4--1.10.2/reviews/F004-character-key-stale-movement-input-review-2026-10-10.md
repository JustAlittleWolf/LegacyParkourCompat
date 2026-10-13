# Independent blind snapshot review: F004 character-key stale movement input

- Verdict: **revision required**; this immutable snapshot is not accepted for implementation.
- Reviewer: independent source reviewer `review_character_key_input`, 2026-10-10.
- Exact pair: 1.9.4 / 1.10.2, Ornithe Feather gen2 build.2 per release.
- Finding: `findings/F004-character-key-stale-movement-input.md`.
- Author commit: `0202863998a8c6d9a3ff6555235f2add358eca1d`; author JustAlittleWolf; authored 2026-10-08T13:26:17+02:00.
- Finding Git blob: `c98e41d2fd8eda84eff15a06d6fb9cd273c0e34f`.
- Finding raw SHA-256: `2aa3ac152b00eb0b3eb19f29d133227d3968a8534dbf69e2ee583b502d650282`.
- Review checkout starts at that author commit on `fix/review-character-key-input-2026-10-10`.
- Blindness: only required repository/workflow guidance, assigned finding, exact paired sources and their readiness/artifact records were read. No implementation, wiki lane, unrelated report, or mixed coordinator document was read. No merge, build, tests or runtime validation occurred.
- Pair complete: no. Full-pair audit: not performed. Runtime parity: unproven.

## Decision basis

The paired polling delta is real: A `KeyBinding.setAll()` (40-47) polls every code and catches `IndexOutOfBoundsException` without assigning when the poll throws; B adds `keyCode < 256 && ...`, assigning false for character codes >=256. Revised immutable mapped-JAR bytecode independently confirms A offsets 28-42/catch45 and B offsets 36-57/false56. Both metadata files select LWJGL 2.9.4-nightly-20150209 outside macOS. That exact cached JAR's `Keyboard.isKeyDown(int)` invokes indexed `ByteBuffer.get(int)` at offset26; static initialization allocates a 256-byte buffer at offsets186-192. Thus A preserves an existing pressed value and B clears it for the cited positive-character codes.

However, the snapshot's required **producer witness is invalid**. A changed function plus a hypothetical preexisting true value does not prove reachable player movement divergence.

## Independent producer and consumer trace

1. Both `ControlsOptionsScreen.keyPressed` (93-105) permit `chr + 256` only when editing a selected binding, `key == 0`, `chr > 0`; `GameOptions.setKeyCode` A212-214/B219-221 writes the code, then Controls resets the lookup map. This establishes an eligible binding configuration, not a pressed-state writer.
2. Opening ChatScreen calls `Minecraft.openScreen(non-null)`, A761-797/B763-799. It unlocks focus and calls `KeyBinding.releaseAll()` A780/B782, then drains existing events. `KeyBinding.release` (94-97) clears pressed and clickCount. A prior gameplay keydown therefore cannot survive entry into chat.
3. `Minecraft.tick` first runs `screen.handleInputs()` A1383-1395/B1372-1384. Inherited `Screen.handleInputs` A367-379/B372-384 drains `Keyboard.next()` and calls the Screen keyboard handler. `Screen.handleKeyboard` A406-413/B411-418 calls `keyPressed` and `Minecraft.handleGuiKeyBindings`; it never calls `KeyBinding.set`. ChatScreen does not override handleInputs or handleKeyboard and does not set passEvents; Screen's passEvents field at58 defaults false and has no initializer/writer in these classes. `Minecraft.handleGuiKeyBindings` A2474-2487/B2477-2490 only handles fullscreen and screenshot actions. Ordinary character keydown in chat therefore does not set the movement binding pressed.
4. Only afterward does the client reach the separately cited `Minecraft.handleKeyboardEvents`, gated by `screen == null || screen.passEvents` A1413-1422/B1402-1411. Its mapping/writer A1498-1541/B1487-1530 is real but cannot process character events already drained by Screen.handleInputs. The finding omitted this earlier owner of the event queue.
5. Chat Escape/Enter closure is genuine: `ChatScreen.keyPressed`58-75 calls openScreen(null). With a world present, living player, active display, non-Mac platform and lost internal focus, `lockMouse` A1130-1143/B1120-1133 calls setAll. ChatScreen.removed47-50 does not clear bindings, and the null-screen branch avoids releaseAll. Nevertheless the chat-produced value entering this call is already false, so A preserving false and B writing false provide no divergence. Screen.handleInputs keeps draining through the old Screen receiver even after closure; remaining events in that same loop do not acquire the omitted movement-state writer.
6. In an actual no-screen keyboard handler, release processing A1555-1556/B1544-1545 sets the binding false before later world/entity work. Avoiding a release before the next input sample is necessary for any alternative witness, but cannot repair the missing chat keydown writer. A post-chat new keydown processed by Minecraft would set both versions true after setAll and likewise fails to produce the claimed difference.
7. Consumer eligibility is confirmed independently: KeyboardInput.tick13-50 reads KeyBinding.isPressed77-79 into axes/jump/sneak, and local-player input sampling is A649/B665. LocalClientPlayerEntity.serverTickAi A586-600/B602-616 copies movementSideways/movementForward/jumping for the camera player only. Minecraft assigns new KeyboardInput at A1917/1950 and B1913/1946. For a hypothetical divergent pressed value, this is an eligible direct player movement path; the assigned snapshot does not establish its producer.

An intermediate review hypothesis that Escape would reopen the pause menu was discarded after examining Screen.handleInputs. For the actual ChatScreen event route, the close event is consumed there, not by Minecraft's pauseGame branch. The decision rests on the missing key-state producer, not that discarded hypothesis.

## Boundary and open dependencies

- Exact source endpoints establish the polling change somewhere in **(1.9.4, 1.10.2]**. Only those two exact releases were inspected. The finding's first-changed-release statement does not establish an exact implementation boundary; introduction remains unknown within that interval.
- Open dependency F004-PRODUCER: supply a reachable vanilla state-writer/timing path that leaves a >=256 movement binding true immediately before guarded setAll without an intervening releaseAll. The asserted character-keydown-while-chat-open route fails. This review does not declare that no other witness exists.
- Open dependency F004-BOUNDARY: inspect intermediate exact releases, through the shared source owner, if a first changed release is required for implementation; endpoints alone are insufficient.
- Next action: source author must revise the producer proof or retract the movement finding, correct the boundary status, publish a new immutable finding snapshot and obtain fresh blind review. Preserve the current snapshot and this review. Implementation handoff remains blocked.

## Artifact bindings and provenance limit

All assigned finding source hashes matched actual read-only canonical files and their source-manifest entries. Source roots are `build/movement-campaign-2026-10-07/ready/<version>/ornithe-feather`; relative Java paths below are under `net/minecraft/client/`. Readiness records identify exact metadata IDs, corresponding release-specific Feather build.2 mappings, successful preparation and source manifests. Actual source/artifact manifests, metadata, client JARs, mappings, LWJGL and revised snapshot files were hashed independently. No damaged relevant source body was found.

Revision `feather-r1-2026-10-07` records rawInputsIdentical and sourceTreeIdentical, but originalDerivedArtifactAvailable=false. A's original recorded derived hash is `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a`; B's is `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`. Available revised JARs differ from those identities. Bytecode statements in this review bind only to the revised immutable JARs; original derived-JAR byte identity, or an assertion that changes are metadata-only, remains unproven. This provenance limitation is preserved separately from the concrete source reachability defect.

| File / artifact | 1.9.4 SHA-256 | 1.10.2 SHA-256 |
|---|---|---|
| options/KeyBinding.java | 29cf0668c652d8d830b3926c2be5d87bf3e8ddc43411dacbc1319c1a96eb4ecb | 208ee44f87b9cbc99b5fdfaf5f99f026be68677f11c25f6dd02aebf3cce9321b |
| options/GameOptions.java | 1bcddb6d5c8aa3f32119a11c35a9c351b4bd6bf36aefc7aa0599c0ab329ad41e | 767ed017470a0909148017461668fa95a988f2f15582b4e2c6636b03502b4d43 |
| Minecraft.java | 6778bd1f0e8aa8a29fdce3bc5285cbd3ea56fa18ca8a43afc4d88439ab12133e | 0979d770e8742a07bb8c54cf7fd3449a8c43a4f48910600ffabf75f1f67ba2d9 |
| gui/screen/menu/options/ControlsOptionsScreen.java | a3b878360e97a06e44cc352bb6a7f23afa86a3d7dc4aea594e65b4ae212fc5b2 | ddf26d661ea89ec9580cbd5432b18c2325e3864ae686269e3f339d83a47ffb2e |
| gui/screen/game/ChatScreen.java | 2e7a96577f91e4e688b5cc4a598bae3656cede55d0c76a428b9d0d61d15c5cfe | cfd95fa9a2dbc32d52a8b3138353fcee3de1c93887f6cba87b4bb9e54474d099 |
| gui/screen/Screen.java | d829d9ee005f1623eb08b2afe6890f27626c77f35d467c1966e589a66a2056c4 | 0cce54bfad02869726923530d7dbca186c5e130578dfe65d03c837f9d6085215 |
| entity/living/player/KeyboardInput.java | 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a | 7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a |
| entity/living/player/LocalClientPlayerEntity.java | 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d | a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443 |
| ready/{v}/ornithe-feather.sources.sha256 | c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19 | 91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71 |
| ready/{v}/artifacts.sha256 | 9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77 | 6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116 |
| artifacts/{v}/version.json | 4b21379203f0d87df8cc2ab4dd3c3689f139772f1cdb37b40688252547e2d5be | d0624badea6367233d47c830341952126cdb0f99b1e11fb60428dd530a0d4db5 |
| artifacts/{v}/client.jar | 23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908 | 7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9 |
| artifacts/yarn/feather-gen2-{v}+build.2-mergedv2.jar | 49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125 | 18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0 |
| artifacts/yarn/feather-gen2-{v}+build.2.tiny | 9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027 | 7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1 |
| revisions/derived-artifact-snapshots/feather-r1-2026-10-07/{v}/ornithe-feather/revision.json | df0a26fd4c65292530cdad638e6789cc26fcd47875fe1c3eb2daef6de7e3f915 | 79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856 |
| revisions/derived-artifact-snapshots/feather-r1-2026-10-07/{v}/ornithe-feather/client-ornithe-feather.jar | fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3 | 0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b |

LWJGL JAR (both sides): `833e721817f70d1445eec13d8ce5a86e12af70efac5014356302e2bbc68b3fe2` at `artifacts/<version>/libraries/org/lwjgl/lwjgl/lwjgl/2.9.4-nightly-20150209/lwjgl-2.9.4-nightly-20150209.jar`.

Static verification: finding commit/blob/raw identities and branch/root verified; narrow report whitespace/diff checks performed. Only this new review is owned. A preexisting Git-LFS world entry appeared modified when filters were disabled after the Git shell launcher failed; it was neither edited nor staged. No persistent processes were launched by this reviewer.
