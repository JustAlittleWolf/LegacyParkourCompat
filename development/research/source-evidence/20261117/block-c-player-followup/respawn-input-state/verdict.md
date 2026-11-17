# COMPLETED — respawn input state audit

## Verdict

**Accept the comparison's behavioral claim.** Across the examined stable endpoints, the first observed change is 1.21.6: the respawn `shouldKeep((byte)2)` branch seeds the replacement `LocalPlayer`'s `lastSentInput` from the previous player's `getLastSentInput()`. It does not seed the replacement player's live movement input. The earlier first parameter carried into `wasShiftKeyDown` in 1.21.4/1.21.5; the sprint boolean remains a separate outgoing-command baseline after the change.

The phrase “first stable boundary” is accurate as the first observed release in this endpoint set. It does not establish the precise patch in which the change was introduced between 1.21.5 and 1.21.6.

## Independent source checks

- In 1.21.4/1.21.5, `handleRespawn` passes the previous player's `isShiftKeyDown()` and `isSprinting()` to the replacement constructor. The constructor stores these in `wasShiftKeyDown` and `wasSprinting`; `sendShiftKeyState` and `sendIsSprintingIfNeeded` consume them to suppress or send serverbound player-command packets. Neither field is a live movement input. The old `input` object is not passed to the new player.
- In 1.21.6/1.21.7/1.21.8, the keep branch passes `getLastSentInput()` and `isSprinting()`. The constructor stores the `Input` in `lastSentInput`; `tick` compares that baseline with `input.keyPresses`, sends `ServerboundPlayerInputPacket` on inequality, and updates the baseline. `getLastSentInput` is used by the respawn path. No local movement consumer reads this baseline.
- Every endpoint constructs a fresh `ClientInput`; its `keyPresses` starts at `Input.EMPTY`. Local movement consumers and the local `isShiftKeyDown()` override read the current `input`/`input.keyPresses` state. Thus this constructor carryover alone does not restore prior directional, jump, or shift input to local movement.
- The carryover can change whether an input packet is sent. Server-side state changes remain behind server packet handling: 1.21.4/1.21.5 `handlePlayerInput` stores the input; 1.21.6/1.21.8 also apply its shift bit after the loaded-player gate. The conclusion is limited to direct client-local movement state and does not claim network/server consequences are impossible.

## Evidence identity

The assigned comparison input matches SHA-256 `5f36e01dcd76b7fb929137232cafb816c69b20100c4557ea53dac7a6476f522b`.

Mojmap source member SHA-256s independently checked against the canonical source manifests or nested provenance source hashes:

| Release | `ClientPacketListener.java` | `LocalPlayer.java` | `ClientInput.java` | `world/entity/player/Input.java` | `ServerGamePacketListenerImpl.java` |
|---|---|---|---|---|---|
| 1.21.4 | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4` | `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` | `c5467cc282f361b5ab5540066385240f847d101c68afdef8655908f3fb3b2c8b` |
| 1.21.5 | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | same as 1.21.4 | `fdb8726f77618ebcfd4fe3973e1740d999c2fd3d220ca4a3ddba837f160e6d69` |
| 1.21.6 / 1.21.7 / 1.21.8 | `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | same as 1.21.4 | `100f83ae1f264dd26170147cf1319f78b7bf837d5b1b5603934b414cde63a16d` |

For 1.21.6 and 1.21.7, the nested `.decompile-provenance.json` files have SHA-256 `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` and `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`, respectively. Their nested `sources` hashes match the examined source files. They also pin exact client, mapping and release-metadata inputs: 1.21.6 client `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`, client mappings `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`, metadata `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`; 1.21.7 client `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`, client mappings `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`, metadata `b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a`.

The nested 1.21.6/1.21.7 provenance records do not declare a version ID, mapping namespace, or decompiler command/run. Their exact hashed inputs and source hashes are present; a publisher confirmation of the intended mapping/run association remains an administrative provenance gate if the acceptance process requires those explicit labels. This does not change the independently confirmed behavior in the intact, hash-matched source bodies.

## Terminal status

**COMPLETED.** Static source review only; no implementation, wiki, product history, tests, generation, or runtime work was performed.