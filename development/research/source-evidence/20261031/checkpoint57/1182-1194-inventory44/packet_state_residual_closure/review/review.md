# Independent review: block acknowledgement position restoration

**Verdict: REVISE** for draft SHA-256 `5AA999435B260C4FE20EE40FFC4EEBE230841AA47D8594B24329F2A451F2B25B`, broad source-ledger SHA-256 `54518A6416A3B4426EA0C633522341EFB3C7B412F88DB48F6189F79CD524CF4E`, and ACK-route ledger SHA-256 `8E4F7B07775D71C705BCE961C333A870820341B8FB47BBCCDE224A4AD560DC8B`.

All 66 broad-ledger hashes and 24 ACK-route hashes match the exact local Mojmap files. The cited old and new restore members are correctly described, including the old action/result gate, first captured player position and sequence-only update for later predictions at the same block, server-state update, ACK sequence closing entries `<= ack`, reference-inequality state check, level/collision check, and direct `absMoveTo` write through `minecraft.player`. The 1.19.1–1.19.4 `ClientLevel` restore form and prediction-handler member are unchanged.

## Required route citation

The draft states that a pending client prediction is captured and later ACKed, but does not establish from source how a real local block-break path activates that prediction scope and reaches the overridden `ClientLevel.setBlock`.

Add the exact caller route from the provided ACK-route source ledger:

- 1.19 `MultiPlayerGameMode.continueDestroyBlock` lines 238–244 wraps `destroyBlock($$0)` in `startPrediction` when destroy progress completes.
- `startPrediction` lines 256–261 opens `BlockStatePredictionHandler.startPredicting`, executes the local action before sending the sequenced packet, and closes the prediction scope afterward.
- `MultiPlayerGameMode.destroyBlock` lines 103–125 reaches `ClientLevel.setBlock`, which captures pre-prediction state and local player position when the prediction handler is active.

The old 1.18.2 action route is likewise present: `continueDestroyBlock` lines 219–226 sends `STOP_DESTROY_BLOCK` through `sendBlockAction` before calling `destroyBlock`; `sendBlockAction` lines 452–456 captures `LocalPlayer.position()`. Cite those refs (the source hash ledger already has 1.18.2 `MultiPlayerGameMode.java`). This closes the action→capture and action→prediction gaps.

No further logical correction was found in the submitted candidate. The conditional statement that the two sides share the final authoritative block state and no extra server/state-production behavior is inferred is appropriate. No build, tests, launches, or external tools were used.
