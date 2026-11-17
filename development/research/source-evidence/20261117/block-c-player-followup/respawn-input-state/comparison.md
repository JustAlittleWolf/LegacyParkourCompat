# Respawn input carryover source comparison

## Scope and source identity

Compared canonical `decompiled_minecraft/<version>/mojmap` sources for Minecraft 1.21.4, 1.21.5, 1.21.6, 1.21.7 and 1.21.8. Mapping family is Mojmap. The 1.21.4/1.21.5 provenance identifies a `decompileMinecraft` run with `mappingOption: mojmap`, batch `1.21.1,1.21.3,1.21.4,1.21.5`; 1.21.8 identifies a Mojmap `decompileMinecraft` run, batch `1.21.8,1.21.10,1.21.11`. Current hashes for the examined source members match their canonical source manifests. In 1.21.6 and 1.21.7, `mojmap/.decompile-provenance.json` records source-file hashes and the LPC decompiler pipeline but does not state a version ID, exact run, or mapping build. The directory labels and sources identify this lane as Mojmap, but those two run/mapping identities remain a source-acceptance gate.

Relevant SHA-256s (identical 1.21.6 and 1.21.7 files are listed once):

| Version | `ClientPacketListener.java` | `LocalPlayer.java` | `ClientInput.java` | `Input.java` |
|---|---|---|---|---|
| 1.21.4 | `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4` | `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611` | `0f2e4d03749ab7495e386af05aa77a0414b5d1c3ef76256db2b514f42cc6e3c4` | `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` |
| 1.21.5 | `284f34159a1baf30b6b540751ad2fa62344c179d5ed6a7bbcb5c888665779617` | `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | same as 1.21.4 |
| 1.21.6 / 1.21.7 | `a66556678a93c63c0ceb8953f25261b82bf3bb1971048fbc15b177c6dffea8cd` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` | `1c0c12af07da1f90651c3a7c4eb19bab6486b6391dcbfdd2926f06e3f36c2f06` |
| 1.21.8 | same as 1.21.6 | same as 1.21.6 | same as 1.21.6 | same as 1.21.4 |

## Operation: seed the new player's last-sent input after a respawn

**First stable boundary: 1.21.6.** The selected branch is gated by `ClientboundRespawnPacket.shouldKeep((byte)2)` in `ClientPacketListener.handleRespawn`.

- 1.21.4 `ClientPacketListener.java:1132-1164` and 1.21.5 `:1152-1184` create the replacement `LocalPlayer` with the previous player's `isShiftKeyDown()` and `isSprinting()` booleans. In `LocalPlayer.java:116-149`, those constructor booleans initialize `wasShiftKeyDown` and `wasSprinting`; `lastSentInput` instead initializes to `Input.EMPTY`.
- 1.21.6 `ClientPacketListener.java:1201-1233` and the same lines in 1.21.7/1.21.8 pass `getLastSentInput()` and `isSprinting()`. `LocalPlayer.java:124,147-153` stores that `Input` as `lastSentInput`; 1.21.7 and 1.21.8 have the same source hash and method body. 1.21.4 and 1.21.5 keep the former constructor and respawn path; no difference was found across those endpoints.
- The replacement object's `input` remains a newly initialized `ClientInput` (`LocalPlayer.java:120` in 1.21.4/5, `:127` in 1.21.6+); `ClientInput.keyPresses` starts at `Input.EMPTY` (`ClientInput.java:7` in each). The respawn method does not assign the old `input` object or its key state to the new player.

The 1.21.6+ copied `Input` is therefore specifically the **last-sent baseline**, not the new player's live movement input. `LocalPlayer.tick` compares that baseline with `input.keyPresses`, sends `ServerboundPlayerInputPacket` on inequality, then updates the baseline (`LocalPlayer.java:197-204` in 1.21.6+; the same compare/send exists at `:191-199` in 1.21.4/5). The packet carries `Input` (`ServerboundPlayerInputPacket.java:9-20`). No local movement method reads `lastSentInput`; the sole reader besides its getter is this packet comparison.

## Local movement consumers and outcome

Live local movement reads the replacement player's current `input`, not `lastSentInput`. `LocalPlayer.isShiftKeyDown` reads `input.keyPresses.shift` (1.21.4 `:593-595`; 1.21.6+ `:594-596`); `serverAiStep` copies current jump into `jumping` (1.21.4 `:607-613`; 1.21.6+ `:607-613`). `aiStep` uses current jump/shift for flying toggle, jump/fall-flying, swimming, and jump-edge/ride behavior (1.21.4 `:653-795`; 1.21.6+ `:693-815`). In both eras those consumers use the current `input`, while the respawn-created `ClientInput` is fresh. Thus the carryover change itself does not carry a held jump, shift, or directional input into local movement state.

In 1.21.4/5, `sendShiftKeyState` separately compares live shift against carried `wasShiftKeyDown` and emits `PRESS_SHIFT_KEY`/`RELEASE_SHIFT_KEY` only on a change (`LocalPlayer.java:273-280` in 1.21.4; `:269-276` in 1.21.5). This is outgoing command suppression state, not a local movement input consumer. That method and `wasShiftKeyDown` disappear in 1.21.6; shift is included in the seven-bit `Input` record and travels through the same last-sent-input comparison as other buttons.

**Server authority boundary:** `ServerGamePacketListenerImpl.handlePlayerInput` consumes the input packet. In 1.21.4/5 it calls `setLastClientInput` (1.21.4 `:373-376`; 1.21.5 `:386-389`). In 1.21.6+ it still stores that input and, after `hasClientLoaded()`, resets last-action time and applies `setShiftKeyDown(input.shift())` (`:390-396`; same hash in 1.21.7/8). This is server packet handling, not client-side movement simulation. The 1.21.6+ copied baseline can change whether a duplicate input packet is emitted; it does not itself update either local live input or server player state. Server state changes only if/when a packet reaches this handler.

## Coverage and gates

- Compared respawn keep-flag branch, `LocalPlayer` field/constructor/tick sender, `ClientInput` initialization, `Input` packet representation, direct local shift/jump consumers, packet codec/dispatch, and server input handler for all five endpoints. Relevant source bodies and hashes are intact against canonical manifests.
- Stable endpoints: 1.21.4 and 1.21.5 agree on the prior boolean carryover; 1.21.6, 1.21.7 and 1.21.8 agree on the `lastSentInput` carryover and sender path. First observed change is between 1.21.5 and 1.21.6; intermediate patch boundary is not inferred.
- **Gate:** exact publication/run and mapping-build identity for 1.21.6 and 1.21.7 is absent from their provenance JSON. Request provenance-owner confirmation before treating this claim as source-accepted. No wiki, product implementation, Git history, tests, or gameplay were used.

Terminal: COMPLETED (comparison complete; source-acceptance provenance gate remains open).