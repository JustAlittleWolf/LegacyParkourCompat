# Independent bounded source verdict: mounted sprint double-tap witness, r2

**Verdict: ACCEPTED for this bounded direct-player witness as stated.** I independently verified the correction and retraced the complete bounded conclusion against the exact 1.19.2 and 1.19.3 Mojmap source trees. The correction accurately distinguishes the previous-input snapshot from current forward input and removes the former false claim that 1.19.2 arms its timer on the first grounded tick under continuous forward input. This verdict does not revisit or renew the rejected held-sprint-key finding and does not imply full-pair source freeze.

## Immutable input binding

- Draft: `c-mounted-doubletap-source/comparison-r2.md`
- Raw SHA-256 verified from the exact file bytes before authoring this verdict: 74d02adebef4b92fcaf19017c606a39c4e91ac8f29035550911577e626892bb6

## Evidence identity and trace

The exact canonical trees are `decompiled_minecraft/1.19.2/mojmap` and `decompiled_minecraft/1.19.3/mojmap`. Their ready markers bind source-manifest SHA-256 values `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` (1.19.2) and `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` (1.19.3). Relevant source files and hashes independently checked in the preceding audit are unchanged: `client/player/LocalPlayer.java` (1.19.2 `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`; 1.19.3 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`), `world/entity/Entity.java` (1.19.2 `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6`; 1.19.3 `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32`), and `world/entity/LivingEntity.java` (1.19.2 `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77`; 1.19.3 `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`). The prior audit also verified the paired `ClientPacketListener.java` and player `Player.java` files referenced by the unchanged local-dismount portion.

`LocalPlayer.aiStep` computes `$$2` from the old `input.forwardImpulse` before `input.tick`; after input processing, the unheld-key double-tap branch requires `!$$2` plus current impulse at least `0.8`. With an initially low impulse, the first forward tap is admitted in 1.19.3 because its start gate includes passenger-on-ground-vehicle state, arms 7, and is not admitted by the 1.19.2 grounded-or-underwater gate. On release, the old-input snapshot is true, preventing that route as the timer drops to 6. On the next forward edge, the snapshot is false and the timer drops to 5 before the route sets sprint in 1.19.3. The separate held sprint-key route is unavailable under the stated key-up condition.

Under the stated continuous forward impulse through dismount and landing, 1.19.2's old-input snapshot remains true on the first grounded jump tick, so it does not arm the timer there; the r2 draft says this correctly. In 1.19.3 the already-set sprint flag survives the local remove-vehicle path, and its sprint-stop gate accepts any positive forward impulse through `input.hasForwardImpulse()`, while the specified food, dry state, and collision conditions keep the other stop gates closed. Both versions' ordinary downward collision writes `onGround`; both reach the grounded `jumpFromGround` call with `noJumpDelay == 0`. That method adds the same sprint-only horizontal vector after its vertical jump write, so the described difference in the first grounded jump's direct velocity write follows.

The cited `ClientPacketListener.handleMovePlayer` local `removeVehicle()` call and `Player`/`Entity.removeVehicle` chain exist on both sides and clear vehicle linkage without clearing sprint. The source claim is limited to that local write and direct player movement response; no vehicle physics, request origin/timing, server replay, or trajectory is inferred. The r2 timer-boundary paragraph also correctly scopes any old-version timer arming after a preceding below-threshold impulse as a different sequence.

## Bounds

This is an individual bounded source acceptance for the exact immutable r2 draft and the evidence identities above. It is not a complete pair-coverage audit, implementation approval, runtime parity result, or claim about vehicle physics.
