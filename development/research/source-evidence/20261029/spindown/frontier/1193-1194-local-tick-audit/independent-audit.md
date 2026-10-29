# Independent audit: 1.19.3 to 1.19.4 LocalPlayer mount and dismount slice

## Input and scope

Audited input: `.native-source/20261028/frontier-c/1193-1194-local-tick/coverage.md`, SHA-256 `83a60500ae47dd098b3191f23da9bee02f5db4fb6ab14c3f340323b9bdf4e695` (recomputed from disk). This review independently retraces the canonical sources for the mount-control jump/boat callbacks and player dismount/correction sequence. It does not accept claims about unrelated tick changes, certify complete pair coverage, establish a runtime result, or prove movement parity.

## Source identity

Canonical read-only roots are:

- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.19.3/mojmap`
- `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.19.4/mojmap`

Neither mapping root contains `.decompile-provenance.json`; identity is bound by the release-parent records, which I checked in addition to the mapping roots. Both releases have parent `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, `artifacts.sha256`, and `movement-diagnostics.txt`.

| Release | Ready SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Diagnostics SHA-256 | Source count / bytes |
|---|---|---|---|---|---|---|
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652` | `95e8189acb6b384b21a584f28d5e8d23922996ea9f446e17e54734ccba840e60` | 4,618 / 22,430,993 |
| 1.19.4 | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72` | `61b7a69e1804684d0fc4414d5482d7233de5a7bfe64a19fb5a92571a7a69bcef` | 4,740 / 23,021,785 |

Both ready records identify the exact named version and Mojmap. Their declared source-manifest, artifact-manifest and diagnostics hashes match the corresponding files. A fresh recursive count/byte sum of each mapping root's Java sources matches its ready record. Each tabled source hash in the input report matches the corresponding source-manifest entry. I additionally hashed `PlayerRideableJumping.java`, `AbstractHorse.java`, and `Camel.java` in both releases against those manifests; all six hashes matched. Artifact-manifest entries are declarations only here; the original raw client/mapping artifacts were not independently rehashed.

## Mount-control findings

The changed `LocalPlayer.jumpableVehicle()` uses `getVehicle()` and `canJump(this)` in 1.19.3 (`LocalPlayer.java:542–543`), then uses `getControlledVehicle()` and `canJump()` in 1.19.4 (`:535–536`). `Entity.getControlledVehicle()` in 1.19.4 returns the current vehicle only when that vehicle reports this entity as its controlling passenger (`Entity.java:2821–2822`). The server's `START_RIDING_JUMP` path likewise switches from the old vehicle/player-predicate form to the controlled-vehicle/no-argument predicate form. This narrows input to the controlling passenger; it is not a changed gate for a source-present rideable whose legacy predicate already requires the same controller.

For the checked implementations, `AbstractHorse.canJump(Player)` in 1.19.3 and `canJump()` in 1.19.4 both reduce to `isSaddled()`. The old Camel predicate additionally checks that its controlling passenger is the player; the new LocalPlayer controlled-vehicle selection supplies that identity check before calling the Camel's no-argument movement predicate. These predicates support the input report's bounded no-new-gate conclusion for these implementations. This is not a claim about every possible modded vehicle.

The `LocalPlayer.rideTick()` boat callback changes from checking `getVehicle() instanceof Boat` in 1.19.3 (`:877–880`) to checking `getControlledVehicle() instanceof Boat` in 1.19.4 (`:858–861`). This can suppress input forwarding and the related `handsBusy` assignment for a non-controlling boat passenger. The callback changes boat input state; boat physics are outside this slice. No direct local-player movement or pose delta follows from the inspected player-side callback.

## Dismount and correction findings

In 1.19.3, `ClientboundPlayerPositionPacket` carries and serializes a `dismountVehicle` boolean; `ClientPacketListener.handleMovePlayer()` calls `LocalPlayer.removeVehicle()` when that flag is set before applying the correction (`ClientPacketListener.java` at `handleMovePlayer`, `ClientboundPlayerPositionPacket.java:16–39, 48–55, 82–83`). `ServerGamePacketListenerImpl.dismount(...)` constructs that flagged correction. `ServerPlayer.stopRiding()` and `dismountTo(...)` invoke the server connection's dismount path.

In 1.19.4, the position packet and its client handler no longer carry or test that flag. `ServerPlayer.dismountTo(...)` removes the vehicle and sets the supplied server position. Separately, `ServerEntity.sendChanges()` detects a passenger-list change, sends `ClientboundSetPassengersPacket`, then sends an absolute `connection.teleport(...)` for a removed `ServerPlayer` (`ServerEntity.java:82–89`). The client passenger-list handler applies the new passenger set, while the position handler applies the received absolute/relative position and rotation. This confirms the changed protocol and producer ordering described in the input report.

The old and new paths therefore differ in where detach is conveyed and when the correction is emitted. The inspected sources establish the packet content and order, but not that an intervening local movement tick is or is not reachable between callbacks, nor a runtime trajectory. For equivalent supplied server coordinates and rotations, both client correction handlers apply those supplied values; the source review does not establish a changed movement value. This producer/order boundary remains a qualification, not a full parity conclusion.

## Verdict

**Accept, bounded** for the mount-control and dismount/correction source observations above. Source identity and selected hashes are verified. The input report's no-new-mount-gate conclusion is supported for the inspected horse and camel implementations; the boat change is a vehicle-input callback. The dismount/correction protocol changed as reported, with server-side producer timing and any intervening tick left unresolved. No pair freeze, implementation readiness, or runtime parity is claimed.
