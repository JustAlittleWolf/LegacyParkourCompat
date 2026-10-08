# F-03: mounted sprint carryover on a dismount tick

- Older version A: 1.19.3
- Newer version B: 1.19.4
- Mechanic / coverage slice IDs: INV-TICK / I-TICK-MOUNT-START; INV-STATE / I-TICK-MOUNT-START; INV-EXTERNAL / I-TICK-MOUNT-START
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: player sprinting on an existing grounded rideable, then dismounting before a local movement tick
- First changed release: unknown within (1.19.3, 1.19.4]
- Runtime validation: not performed

## Paired evidence

All paths are repository-relative; hashes match the verified per-version source manifests.

- A `build/movement-campaign-2026-10-07/ready/1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 693–717, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`: the mounted ground gate permits sprint initiation when the vehicle is grounded; the passenger food helper returns true; with the sprint key down and the other guards satisfied, the second start branch calls `setSprinting(true)`. While sprinting, its stop check retains sprint when forward impulse and food are sufficient and collision/water stop conditions are absent.
- B `.../1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `LocalPlayer#aiStep()V`, lines 685–710, same `LocalPlayer.java` SHA-256 `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58`: both start branches use `canStartSprinting()`, which requires `!isSprinting()` and, for a passenger, `vehicleCanSprint(vehicle)`. At lines 1022–1034, `vehicleCanSprint` requires `vehicle.canSprint()` and local control.
- A `.../1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, `Entity#removeVehicle()V`, lines 1784–1790, SHA-256 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`; B `.../1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, lines 1798–1804, SHA-256 `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`. Their bodies are identical (body SHA-256 `7f2ec012dc94d69759a91fda179d9b770b3e3bc095acabb27e88090099828d95`): the vehicle link and passenger membership are removed without clearing sprint. A/B `LocalPlayer#removeVehicle()V` also have identical bodies (body SHA-256 `4b4c4c8a6bce647b301104e1e9ba0a7d1fc841c511fb1d69337030d2fea78992`); they only clear `handsBusy` after delegating.
- B `.../1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `Entity#canSprint()Z`, lines 3163–3165, file SHA-256 as above: the default is false. Existing rideables inheriting this default cannot start a B mounted sprint. The B-only Camel override is a modern-only entity and is not part of this historical claim.
- A/B `LivingEntity#setSprinting(Z)V` hashes are `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` / `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165`; it applies/removes the sprint movement-speed modifier. The normal grounded movement consumer uses the resulting speed through `LivingEntity#getFrictionInfluencedSpeed()F`.

## Narrow reachable route

A local player starts sprinting while riding an existing grounded rideable in A, with the sprint key held and ordinary start guards satisfied. The player then releases the sprint key but continues forward with sufficient food; A's sprint stop branch retains the already-set sprint state. With the sprint key released, `sprintTriggerTime == 0`, and other sprint-stop guards false, a dismount applied before the next local tick leaves A sprinting through the identical `removeVehicle()` bodies.

On that same post-dismount tick, B cannot carry a mounted sprint because its passenger start gate was false for a rideable whose `canSprint()` is false. After dismount, B's ordinary double-tap branch is reachable, but with the sprint key still released and `sprintTriggerTime == 0` it only sets `sprintTriggerTime = 7`; it does not call `setSprinting(true)` before movement. The held-key start branch is also false. This explicitly excludes the reviewer's noted cases where a held sprint key or positive trigger timer lets B restart sprint before movement.

The sprint gate and stop checks run in `LocalPlayer#aiStep()` before the inherited player/entity movement path. For a grounded post-dismount movement tick with forward input, sufficient food, and no collision or water stop condition, A reaches movement with the sprint modifier active; B reaches it without the modifier. No position delta or later-tick trajectory is claimed. The double-tap timer B starts may affect a later tick, outside this bounded finding.

## Shift-dismount reachability and timing

The ordinary shift-key route is source-traced on both sides. A `LocalPlayer#tick()V` sends `ServerboundPlayerInputPacket` (A `LocalPlayer.java` lines 188–197, SHA-256 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`); `ServerGamePacketListenerImpl#handlePlayerInput(...)V` stores shift state and `Player#rideTick()V` calls `stopRiding()` on the server when the player remains a passenger (A lines 365–368 / 473–477; `Player.java` SHA-256 `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203`, `ServerGamePacketListenerImpl.java` SHA-256 `81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b`). `ServerPlayer#stopRiding()` sends `connection.dismount`; `ServerGamePacketListenerImpl#dismount(...)` emits a position correction with the dismount flag, and client `ClientPacketListener#handleMovePlayer(...)` calls `removeVehicle()` when it sees that flag (A `ServerPlayer.java` SHA-256 `7df71c12ff99bec35b7c6b0e68fa24e5a5aa7293c6dd8f3342d433936ad35c7c`; `ClientPacketListener.java` SHA-256 `c01c3c362e5eb7de780f4ca0f1b8964a49e40cfc166ae63cbe392cd71b6b1b9e`).

B `LocalPlayer#tick()` sends the same shift input; paired `handlePlayerInput` and `Player#rideTick` process it (B lines 367–370 / 476–480; `Player.java` SHA-256 `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`, `ServerGamePacketListenerImpl.java` SHA-256 `1e1b8dc23031bd5dfc4a3dd5453ec1e6ad3211a534a810927c8fa725fc271dc9`). B `ServerPlayer` has no `stopRiding()` override and uses `Entity#stopRiding()`; `ServerEntity#sendChanges()` broadcasts `ClientboundSetPassengersPacket`, then teleports a removed ServerPlayer. `ClientPacketListener#handleSetEntityPassengersPacket()` ejects existing passengers and applies the received passenger list (B `ServerPlayer.java` SHA-256 `5eeea9be89db11000daa95cada0a4120c5eda1014aae9e868fba0bee8de05606`, `ServerEntity.java` SHA-256 `a597bbd4b7639ac0ce91dcc6ea53af4ae734d872beea1cc215a9fbfb9ed67347`, `ClientPacketListener.java` SHA-256 `bcc74e52a32d20a3e993ebe4e08fffe8caca7481fad8f326dacdc93796b2b715`).

The claim is conditioned on the client applying a dismount before the specified local movement tick; no packet scheduling or server trajectory is asserted.

## Reachability and dependencies

A mounted sprint-start gate -> persistent sprint flag and speed modifier -> dismount clears the vehicle relation without clearing sprint -> next local tick evaluates sprint state before movement -> grounded speed consumer. The B vehicle eligibility gate blocks the mounted start, and the released-key/zero-trigger condition blocks B from restarting before movement on the bounded tick. Vehicle physics are excluded.

## Handoff

This revised source snapshot supersedes the original F-03 snapshot for review; the original commit remains immutable and is retained in the campaign log. Separate from F-01's airborne speed calculation. Applies only to a grounded post-dismount movement tick meeting the explicit released-key, zero-trigger, forward-input and sprint-retention conditions. Release introduction is unknown within the compared interval.
