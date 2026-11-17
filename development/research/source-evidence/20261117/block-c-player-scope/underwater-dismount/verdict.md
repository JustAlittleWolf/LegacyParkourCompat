# Independent source audit: underwater dismount

**Terminal status: COMPLETED**

**Frozen input:** `underwater-dismount-source/comparison.md`, SHA-256 `0ee36208d98bf7647466fac4d089457125ca05cd52d17d198458108cb75e2701` (verified).

## Verdict

The narrow negative claim is confirmed: 1.19.3 and 1.19.4 have no direct client-local-player underwater passenger-dismount operation in `LocalPlayer`. The comparison draft is acceptable only with that narrow scope. A related server-side producer change exists, so its negative conclusion must not be broadened to “no dismount-related change in the pair.”

`LocalPlayer.tick` sends the same `ServerboundPlayerInputPacket` shift state while riding in both versions (`LocalPlayer.java:188-200`). The water branch in `LocalPlayer.aiStep` is also the same: `isInWater() && input.shiftKeyDown && isAffectedByFluids()` calls `goDownInWater()` (`1.19.3 LocalPlayer.java:758-761`; `1.19.4 LocalPlayer.java:739-742`). It does not call `stopRiding` or write passenger state. `ServerGamePacketListenerImpl.handlePlayerInput` stores that shift state in `Player` on both versions (`:365-368` / `:367-370`). `Player.wantsToStopRiding()` returns `isShiftKeyDown()`, and `Player.rideTick()` calls `stopRiding()` only when `!level.isClientSide && wantsToStopRiding() && isPassenger()`, then clears shift (`1.19.3 Player.java:301-303, 472-485`; `1.19.4 Player.java:304-306, 475-489`). This is server authority, not a local underwater dismount prediction.

The client receiver is equivalent: `ClientPacketListener.handleSetEntityPassengersPacket` resolves the vehicle, records whether the local player was already an indirect passenger, ejects the vehicle’s current passengers, then calls `startRiding` for IDs in the received list (`1.19.3 ClientPacketListener.java:922-948`; `1.19.4 ClientPacketListener.java:961-987`). Omitting the player therefore applies the server’s passenger-state update on the client. The prior-passenger value only suppresses a mount notification; it does not trigger dismount. `Entity.stopRiding()` delegates to `removeVehicle()` in both versions (`1.19.3 Entity.java:1796-1798`; `1.19.4 Entity.java:1806-1808`).

## Producer delta found

`ServerEntity.sendChanges` is the material adjacent difference. In 1.19.3, when its passenger list changes, it broadcasts `ClientboundSetPassengersPacket` and updates `lastPassengers` (`ServerEntity.java:77-82`). In 1.19.4 it broadcasts the same packet, then finds removed passengers and calls `connection.teleport(x, y, z, yRot, xRot)` for each removed `ServerPlayer`, before updating `lastPassengers` (`ServerEntity.java:80-90`; helper `changedPassengers` at `:207-209`). Thus the equivalent passenger-state packet and its client write did not gain local dismount logic, but the 1.19.4 server adds a position resynchronization for a dismounted server player. This applies to passenger removal generally, including a shift-triggered underwater dismount; the source does not gate it on fluid state.

This is not a fluid-sampling or boat-physics change. It is a server-side response after passenger-list change. Under the project invariant that historical movement must not run on logical servers, it does not establish a client movement emulation change. If the requested inventory includes all server-originated post-dismount position corrections, track this as a separate server/network behavior candidate rather than folding it into a client-local underwater dismount finding.

## Evidence identity

Exact local provenance is Mojmap, 1.19.3 and 1.19.4, from the recorded exact decompile batch `1.19.2,1.19.3,1.19.4`. Provenance SHA-256: 1.19.3 `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe`; 1.19.4 `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34`. Source file SHA-256 values below were independently recomputed and match the Mojmap source manifests:

- 1.19.3 `LocalPlayer.java` `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`; `Player.java` `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`; `ClientPacketListener.java` `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`; `ServerEntity.java` `4147f1e7a07771c42a6e68ec9927c715b4056d531c93d1aa3611e84ba50b54ca`; `Entity.java` `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`.
- 1.19.4 `LocalPlayer.java` `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`; `Player.java` `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; `ClientPacketListener.java` `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715`; `ServerEntity.java` `a597bbd4b7639ac0ce91dcc6ea53af4ae734d872beea1cc215a9fbfb9ed67347`; `Entity.java` `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`.

## Limits

This audit covers the assigned 1.19.3→1.19.4 player shift/underwater request, server `rideTick` dismount authority, passenger-list packet producer and receiver, and the adjacent server position correction. It does not assess vehicle physics, general fluid sampling or movement, other dismount causes beyond the shared passenger-removal producer, intermediate patch boundaries, or other releases. It does not establish runtime parity. Static source review only; no tests, launches, source generation, or product/research edits.