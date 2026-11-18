# Independent raw-bound verdict: mounted double-tap alias applicability

**Verdict: ACCEPTED for the bounded source-applicability claim.** I independently checked the immutable comparison against exact canonical Mojmap sources for 1.18.2, 1.19, 1.19.1, 1.19.2 and 1.19.3. The ordinary dry double-tap route in 1.19, 1.19.1 and 1.19.2 requires `onGround || isUnderWater()`; it contains no passenger or vehicle-ground alternative. The additional `isPassenger() && getVehicle().isOnGround()` admission appears in the checked 1.19.3 route. The checked 1.18.2 source already has the same old ground-or-underwater form. This supports the specific 1.19.2/1.19.3 applicability boundary in the immutable claim; it establishes no later or unexamined patch behavior.

## Immutable bindings

- Comparison reviewed: `c-mounted-doubletap-alias-source/comparison.md`, raw SHA-256 `35249bf93650cfb6a7e9f8a149a6528e51904cd3444baacd4c6ba915a585626c` (recomputed from file bytes).
- Cited r2 author input: `c-mounted-doubletap-source/comparison-r2.md`, raw SHA-256 `74d02adebef4b92fcaf19017c606a39c4e91ac8f29035550911577e626892bb6` (recomputed).
- Cited r2 verdict: `c-mounted-doubletap-audit/verdict-r2.md`, raw SHA-256 `c290ac6e7b6d1cb8ba47e858937449072e2b0dc874a3a3c9763b8e0f38673852` (recomputed). Its accepted direct-player witness is consistent with this narrower alias check; this verdict does not broaden or renew claims outside the bound comparison.

All five `mojmap.ready.json` identities match SHA-256 recomputed from their `mojmap.sources.sha256` files:

| Release | Source-manifest SHA-256 |
|---|---|
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |

For each examined Java file, its raw SHA-256 also matches its entry in that release's source manifest. The relevant `LocalPlayer.java` hashes are: 1.18.2 `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; 1.19 `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805`; 1.19.1 and 1.19.2 `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef`; 1.19.3 `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479`.

The relevant `Input.java` and `KeyboardInput.java` hashes are `eb50a4e268ec5ff8423d2805499ca3c7bae33765cb44cfecef808e38fa6de3c3` and `281622f8481654035196a7bc1554d5251c1040518375e3ac6f6439e5ec894a75` at 1.18.2; respectively `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` and `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` at all four 1.19 releases. The 1.19.2/1.19.3 `LivingEntity.java` hashes are `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` and `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db`.

## Independent source trace

All cited lines below are in `net/minecraft/...` relative to each release's `mojmap/` root.

- **1.18.2 predecessor:** `client/player/LocalPlayer.java`, `aiStep` lines 651 and 682–696 snapshots sprint impulse before `input.tick`, then gates ordinary unheld double-tap on `(onGround || isUnderWater())`, a false prior-input snapshot, sufficient current impulse, not already sprinting, food above 6 or mayfly, no item use, and no blindness. With an unheld sprint key and inactive timer it arms 7 ticks. Lines 1031–1034 retain the dry `forwardImpulse >= 0.8` threshold and the underwater `hasForwardImpulse()` alternative.
- **1.19:** `LocalPlayer.java`, lines 734–780 and 1115–1118, keeps that same old admission condition, snapshot-before-input ordering, food and other eligibility checks, timer write, and dry threshold. No mounted-ground clause occurs in this route.
- **1.19.1 and 1.19.2:** `LocalPlayer.java` is byte-identical across these two releases. Lines 740/772–786 and 1121–1124 in each keep the same old admission condition, ordering, threshold, food and other eligibility checks, and timer behavior. The route has no rider exception.
- **1.19.3:** `LocalPlayer.java`, lines 661 and 693–707, still snapshots before `input.tick` and keeps the double-tap/timer structure, but the leading route predicate adds `isPassenger() && getVehicle().isOnGround()`. Its separate food helper (lines 1047–1049) now allows passengers regardless of food. The witness's food-above-6 condition satisfies both the old food gate and 1.19.3's gate, so it does not depend on that separate passenger food change.
- **Input and jump bounds:** the 1.18.2 `KeyboardInput.tick` emits ±1 impulse and scales it by `0.3F` only when its slow-movement argument is true. The four 1.19-release `KeyboardInput.tick` methods emit the same impulses and apply the supplied factor only under the same argument; the r2 witness's current dry forward impulse must still meet `0.8` after input processing. `Input.hasForwardImpulse()` is `forwardImpulse > 1.0E-5F` throughout. For the cited grounded-jump bound, 1.19.2 and 1.19.3 `LivingEntity.aiStep` both call `jumpFromGround()` when grounded and `noJumpDelay == 0`; both `jumpFromGround()` methods write vertical velocity first and append the same sprint-only horizontal vector `(-sin(yRot) * 0.2F, 0, cos(yRot) * 0.2F)`. The passenger extension therefore changes admission to the sprint-start route for the stated dry mounted state; it does not change those direct jump-write bounds.

## Scope

This is an independent audit of the immutable raw-bound alias comparison only. It supports preserving the old ordinary gate through 1.19.2 and recognizing the rider-and-grounded-vehicle gate at 1.19.3 under the stated dry witness conditions. It is not full-pair coverage, implementation approval, or runtime parity.
