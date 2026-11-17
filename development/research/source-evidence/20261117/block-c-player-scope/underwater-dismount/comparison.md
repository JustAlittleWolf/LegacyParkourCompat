# Underwater passenger dismount source check

## Scope and verdict

**Pair:** Minecraft 1.19.3 → 1.19.4, Mojmap exact decompiled sources. The 1.19.2 LocalPlayer input branch was checked as an adjacent bound. A 1.20 bound was unnecessary for this closed claim.

**Terminal status: COMPLETED.** Negative closure for a direct client-local-player underwater dismount transition in this pair. `LocalPlayer.aiStep` contains no underwater passenger dismount operation in either version. Its shift-plus-water branch calls `goDownInWater()` under `isInWater() && input.shiftKeyDown && isAffectedByFluids()` (1.19.3 `LocalPlayer.java:758-761`; 1.19.4 `LocalPlayer.java:739-742`; 1.19.2 `LocalPlayer.java:836-840`). This is the local downward-in-water response, not a passenger-state write. The branch and its gate are present at all three versions checked; no 1.19.3→1.19.4 dismount change was found here.

## Receiver, authority, and existing mount witness

- `LocalPlayer.tick` sends `ServerboundPlayerInputPacket(..., input.jumping, input.shiftKeyDown)` while `isPassenger()` (1.19.3 `LocalPlayer.java:188-200`; 1.19.4 `LocalPlayer.java:188-200`). It does not locally call `stopRiding` for shift or underwater state.
- The shared `Player.wantsToStopRiding()` predicate is `isShiftKeyDown()` (1.19.3 `Player.java:301-303`; 1.19.4 `Player.java:304-306`). `Player.rideTick()` executes `stopRiding()` and clears shift only under `!level.isClientSide && wantsToStopRiding() && isPassenger()` (1.19.3 `Player.java:472-485`; 1.19.4 `Player.java:475-489`). Thus the input requests the transition; the server-side player tick makes the dismount decision. This gate is unchanged across the pair.
- The client receiver is `ClientPacketListener.handleSetEntityPassengersPacket` (1.19.3 `ClientPacketListener.java:922-949`; 1.19.4 `ClientPacketListener.java:961-988`). It first witnesses whether the local player was already an indirect passenger via `hasIndirectPassenger(minecraft.player)`, ejects the vehicle's current passengers, and then invokes `startRiding(vehicle, true)` only for passenger IDs in the received packet. That prior-passenger witness is used to suppress the new-mount notification, not to decide a dismount. If the server-provided list omits the local player, the ejection applies the received dismounted state on the client; it is not a local underwater/shift prediction.
- After that packet-applied state change, ordinary `LocalPlayer.tick` chooses its passenger packet path or `sendPosition()` from `isPassenger()` (1.19.3 `LocalPlayer.java:188-201`; 1.19.4 `LocalPlayer.java:188-201`). Later local movement follows the updated state; it does not originate the dismount transition.

## Evidence identity and SHA-256

The provenance records identify versions 1.19.3 and 1.19.4 in the `mojmap` namespace and the exact batch `1.19.2,1.19.3,1.19.4`, decompiled by the recorded exact run. File hashes below were computed from the canonical source bytes and match the corresponding entries in each `mojmap.sources.sha256` manifest.

- `decompiled_minecraft/1.19.3/mojmap.provenance.json`: `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`
- `decompiled_minecraft/1.19.4/mojmap.provenance.json`: `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34`
- `1.19.3 LocalPlayer.java`: `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`
- `1.19.4 LocalPlayer.java`: `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`
- `1.19.3 Player.java`: `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`
- `1.19.4 Player.java`: `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`
- `1.19.3 ClientPacketListener.java`: `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`
- `1.19.4 ClientPacketListener.java`: `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715`
- `1.19.2 LocalPlayer.java` adjacent check: `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`

## Limitations

This closes only the requested direct client local-player passenger-state transition and the adjacent shift/water branch. It does not assess vehicle physics, fluid sampling, the server movement protocol beyond the input and passenger update points above, or other releases/cutover boundaries. No tests, launches, generation, or product files were involved.