# F-S1-FLIGHT-VEHICLE-GATE: Mayfly double-press flight toggle is gated while riding a non-jumpable vehicle

- Older version A: 1.21.10
- Newer version B: 1.21.11
- Mechanic / coverage slice IDs: `S1-flight-toggle-vehicle-gate` (parent bucket `S1-flight-ride`)
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.10, 1.21.11]
- Runtime validation: not performed

## Paired evidence

Sources are from the manifest-verified Mojmap pair: A client jar SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`, source manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; B client jar SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`, source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.

- A: `build/movement-campaign-2026-10-07/ready/1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java`; `net.minecraft.client.player.LocalPlayer.aiStep()V`; original lines 751-70; file SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`. The second-press condition is `else if (!this.isSwimming())`; it toggles `$$3.flying`, may call `jumpFromGround()` when now flying and grounded, calls `onUpdateAbilities()`, then clears `jumpTriggerTime`.
- B: `build/movement-campaign-2026-10-07/ready/1.21.11/mojmap/net/minecraft/client/player/LocalPlayer.java`; `net.minecraft.client.player.LocalPlayer.aiStep()V`; original lines 791-811; file SHA-256 `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`. The corresponding condition adds `(this.getVehicle() == null || this.jumpableVehicle() != null)` before the same toggle and side effects.
- Helper correspondence: A `LocalPlayer.jumpableVehicle()` lines 522-24 and B lines 563-65; A/B `LocalPlayer.java` hashes above. Both return the controlled vehicle only if it implements `PlayerRideableJumping` and its `canJump()` returns true.
- Call/reachability: A `ClientLevel.java` lines 321-60, SHA-256 `2a4bf7bac40707bf0d7d2feaa1f6564f5aff7aae1b923cf94270165425dc8ee3`; B lines 329-68, SHA-256 `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2`. `tickEntities` excludes passengers; `tickPassenger` ticks Players through `rideTick`. A `Entity.java` lines 2234-40, SHA-256 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`; B lines 2254-60, SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. `Entity.rideTick()` calls virtual `this.tick()`. A `LivingEntity.java` lines 2542-83, SHA-256 `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`; B lines 2610-50, SHA-256 `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`. `LivingEntity.tick()` calls `aiStep()` when the entity is not removed. `LocalPlayer.tick()` continues through `super.tick()` after its loaded gate.
- Consumer: A LocalPlayer.aiStep lines 791-803 and B lines 832-44 show the same camera-controlled vertical delta write while `Abilities.flying`; LocalPlayer.isControlledCamera() compares the current camera entity to `this` (A lines 664-66; B lines 705-07). Both expressions are in the same file hashes above.

## Source-level difference

With `mayfly` enabled, outside spectator mode, not swimming, after the first jump press starts the seven-tick trigger, a second jump press toggles flight in A even when the player is a passenger. In B, that toggle proceeds only if there is no vehicle or `jumpableVehicle()` is non-null. A player riding a non-jumpable vehicle therefore toggles `Abilities.flying` in A but not B for the same input sequence. The condition is an additive gate; the jump timer and remaining toggle operations are otherwise unchanged in the paired body.

## Reachability and dependencies

The passenger path is reachable: `ClientLevel.tickEntities` filters out passenger entities, then its `tickPassenger` path calls `rideTick()` for Player entities. `LocalPlayer.rideTick()` delegates through `LivingEntity.rideTick()` to `Entity.rideTick()`, which invokes virtual `tick()`. The local player tick calls the superclass tick after its loaded gate; `LivingEntity.tick()` reaches `aiStep()`. The new guard therefore is not dead code on the passenger path. This finding does not emulate the vehicle's motion or input handling.

`Abilities.flying` is the state written by the toggle and sent through the existing `onUpdateAbilities()` path. The same aiStep later adds vertical player delta from jump/shift input when flying and the local player is the controlled camera. The state can remain set across a later dismount until another existing condition changes it, so a dismount before grounding can expose the different flight input state.

## Consequence and uncertainty

Source proves that the A/B guards produce different local `Abilities.flying` values for the stated precondition and that this value gates the local camera-controlled vertical velocity write. The report predicts that a later airborne dismount can therefore enter different player-side flight-input behavior. It does not claim a measured trajectory or a change to vehicle physics. The exact release where this guard first appeared is unknown within the endpoint interval.

## Handoff

Independent source delta: mayfly double-press toggling while mounted is restricted in B to no vehicle or a jumpable vehicle. Related findings: none. Applicability is limited to the stated player-ability/input state and vehicle class; implementation and testing decisions are deferred. Finding-specific independent blind source review is pending; no accepted immutable snapshot or implementation handoff exists.
