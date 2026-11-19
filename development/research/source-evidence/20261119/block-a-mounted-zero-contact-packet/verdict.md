# Independent verdict: mounted zero-contact packet gate

**Verdict: accepted for the bounded receipt, tick-order, and zero-depth geometry claims.** The ordinary server-emission condition remains open; this does not establish that stock server movement correction naturally emits the specified coordinate while the player is mounted.

## Immutable input and exact source

Reviewed packet: `mounted-zero-contact-packet-gate/evidence.md`, SHA-256 `4f0b1f56d306d03b0ce1c150a2f5448abb5aa99274ee633d1ea22f191f481408`.

Canonical source: Minecraft 1.19.3 Mojmap, `decompiled_minecraft/1.19.3/mojmap`, ready marker says version `1.19.3`, mapping `mojmap`, status `ready`; source-manifest SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`.

Reviewed file hashes match the packet for `ClientPacketListener.java` (`c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`), `LocalPlayer.java` (`64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`), `Entity.java` (`8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`), `EntityDimensions.java` (`ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502`), `Player.java` (`02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`), `Boat.java` (`034e238686cc019c4285f68631586053c7fef2627cfba0e7220d29646270c253`), and `FlowingFluid.java` (`d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c`). The additional server-path source `ServerGamePacketListenerImpl.java` hashes to `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`.

## Independent checks

- `ClientPacketListener.handleMovePlayer` (592–646) removes the vehicle only when `requestDismountVehicle()` is true. With an absolute Y (Y not in relative arguments), the packet's Y becomes the local player's exact Y and `setPos` rebuilds the box. No passenger guard intervenes.
- `Entity.rideTick` (1704–1709) runs `tick()` before `vehicle.positionRider(this)`. The tick's `baseTick` reaches the water-contact scan (Entity.java 403–405, 428); `LocalPlayer.rideTick` calls `super.rideTick()` first and only updates boat input afterward (LocalPlayer.java 874–882). The packet-established box can therefore be scanned before the vehicle writes a new rider position.
- Standing dimensions are scalable `(0.6F, 1.8F)` (Player.java 126–128); the box's lower Y is the entity Y (EntityDimensions.java 21–25). `FlowingFluid.getOwnHeight` is `amount / 9.0F`, and with no same fluid above `getHeight` returns that height (FlowingFluid.java 429–440). For amount 7, float addition of block Y 10 gives the stated top `10.777777671813965`. With the stated packet Y and `deflate(0.001)`, the scan's lower edge equals that top. `updateFluidHeightAndDoFluidPushing` accepts `top >= minY`, marks contact, and accumulates `max(top - minY, 0)`, hence contact with zero depth (Entity.java 2826–2855). The half-open floor/ceil scan includes the claimed partial-water cell and excludes deeper cells as described.
- The Boat status predicate is separate: its underwater-status scan samples its own bounding-box band above `maxY` and accepts water when the sample top exceeds the scan Y (Boat.java 553–582); `isUnderWater()` checks the two underwater statuses (871–873). Thus true Boat underwater status is compatible with the assumed player-body cell layout, but its simultaneous runtime production is not established here.

## Ordinary server correction gate

In `ServerGamePacketListenerImpl.handleMovePlayer`, passengers take the branch at 860–862, which only preserves position and updates rotation/chunk tracking. The ordinary speed/collision correction setters are in the non-passenger path, including the rejection call at 953. Thus stock ordinary passenger movement handling does not provide the concrete exact-Y correction needed by this packet witness.

The packet flag is passed through by the teleport setter (ServerGamePacketListenerImpl.java 976–1004): `dismount(...)` explicitly sends `true`; the ordinary five-argument `teleport(...)` overload sends `false`; the packet constructor stores that flag (ClientboundPlayerPositionPacket.java 18–29). A pending teleport target can be resent at handleMovePlayer 842–852, but that only retransmits a previously established target and does not show an ordinary mounted correction originating at the required Y.

**Remaining condition:** retain the receipt as explicitly conditional. A non-dismount absolute-Y packet at the chosen coordinate must be emitted while the rider remains mounted, and the assumed player-water placement and Boat underwater predicate must persist until the next passenger ride tick. No server emission path satisfying that mounted ordinary-correction condition, Boat motion, or runtime occurrence is established by this audit.