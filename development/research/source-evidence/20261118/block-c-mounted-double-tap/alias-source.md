# Mounted sprint double-tap: early alias applicability

## Result

For the accepted 1.19.2→1.19.3 witness, the independently checked 1.19, 1.19.1, and 1.19.2 Mojmap sources all retain the old dry ordinary double-tap gate: `onGround || isUnderWater()`. None admits a dry, off-ground player merely because the player is a passenger on a grounded vehicle. The added passenger-and-grounded-vehicle alternative is present in the checked 1.19.3 source only.

The witness therefore supports the exact 1.19.2→1.19.3 boundary for this gate. Its old-side behavior also matches the checked 1.18.2 predecessor. The observed source supports retaining the old gate through 1.19.2 and the added rider case at 1.19.3; it does not establish behavior after 1.19.3 or any unexamined patch boundary.

## Bound input identities

The source trees are the canonical local `decompiled_minecraft/<version>/mojmap/` trees. Each ready marker declared the following source-manifest digest; the digest was recomputed from the corresponding `mojmap.sources.sha256` file. Relevant source-file hashes below were recomputed from exact file bytes and matched the manifest entries.

| Exact release | Mojmap source-manifest SHA-256 | `LocalPlayer.java` SHA-256 |
|---|---|---|
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| 1.19 | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` |
| 1.19.1 | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19.2 | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` |
| 1.19.3 | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` |

The relevant `Input.java` SHA-256 values are `eb50a4e268ec5ff8423d2805499ca3c7bae33765cb44cfecef808e38fa6de3c3` at 1.18.2 and `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` at 1.19, 1.19.1, 1.19.2, and 1.19.3. The relevant `KeyboardInput.java` SHA-256 values are `281622f8481654035196a7bc1554d5251c1040518375e3ac6f6439e5ec894a75` at 1.18.2 and `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` at the four 1.19 releases checked.

The accepted witness and audit inputs were read from their named immutable r2 files and verified by raw SHA-256:

- `c-mounted-doubletap-source/comparison-r2.md`: `74d02adebef4b92fcaf19017c606a39c4e91ac8f29035550911577e626892bb6`.
- `c-mounted-doubletap-audit/verdict-r2.md`: `c290ac6e7b6d1cb8ba47e858937449072e2b0dc874a3a3c9763b8e0f38673852`.

## Exact source comparison

All references below are relative to each release's `mojmap/` root.

- **1.18.2 predecessor:** `net/minecraft/client/player/LocalPlayer.java`, `aiStep` lines 651–656 and 682–696. `$$2` snapshots `hasEnoughImpulseToStartSprinting()` before `input.tick`; the ordinary unheld-key route requires `(onGround || isUnderWater())`, `!$$2`, sufficient current impulse, not already sprinting, food above 6 or mayfly, no item use and no blindness. When the timer is not active and sprint is not held it writes `sprintTriggerTime = 7`; otherwise it sets sprinting. The helper at lines 1031–1034 uses `forwardImpulse >= 0.8` when dry and `input.hasForwardImpulse()` underwater. `KeyboardInput.java` lines 22–34 produces ±1 input and applies its slow-movement multiplier only when the slow-movement argument is true.
- **1.19:** `LocalPlayer.java`, `aiStep` lines 734, 740, and 766–780; helper lines 1115–1118. The ordinary route still requires `(onGround || isUnderWater())` and keeps the same prior-input snapshot, `0.8` dry threshold, timer write and other gates. `KeyboardInput.java` lines 21–34 retains ±1 key impulse and applies the passed slow-movement factor only when the slow-movement argument is true.
- **1.19.1 and 1.19.2:** their `LocalPlayer.java` bytes are identical (same SHA-256). `aiStep` lines 740, 746, and 772–786 and helper lines 1121–1124 retain the same old ordinary gate, previous-input snapshot order, dry threshold, timer semantics and food/item/blindness checks as 1.19. `KeyboardInput.java` is byte-identical to 1.19's input file.
- **1.19.3:** `LocalPlayer.java`, `aiStep` lines 661, 667 and 693–707 preserves the previous-input snapshot and current-input ordering, but changes the ordinary route's leading condition to `(onGround || isUnderWater() || isPassenger() && getVehicle().isOnGround())`. Its `hasEnoughFoodToStartSprinting` helper at lines 1047–1049 also admits passengers independently of food. The dry threshold remains `0.8` (helper lines 1042–1045).

## Witness conditions and conclusion

Use the r2 sequence with the player initially not sprinting, dry, off ground, riding a grounded vehicle, and with food strictly above 6; leave the sprint and shift keys up, avoid item use and blindness, and use forward impulse at least `0.8` on each forward tap. Keep the first tap's pre-input impulse below `0.8`, release for one tick, then tap again with the release tick's prior impulse below `0.8`. The exact timing matters because `aiStep` reads `$$2` before `input.tick`, decrements the timer first, and only then evaluates the ordinary route. At the second tap, the 1.19.3 timer is 5 and the route sets sprinting; on each old source the dry, off-ground player fails the leading ground/water gate, so the route neither arms nor starts sprinting.

The food condition is deliberately the witness's `foodLevel > 6`: it satisfies the 1.19/1.19.1/1.19.2 food predicate and the 1.19.3 predicate. Thus the result isolates the new mounted-ground gate rather than relying on 1.19.3's passenger food exemption. The underwater branch remains available on the old sources when `isUnderWater()` is true; this comparison establishes no rider exception for the specified dry state. The specified post-input impulse threshold also keeps the 1.19 change to slow-input scaling from changing the witness's eligibility.

For source preservation, 1.19, 1.19.1 and 1.19.2 must keep the predecessor dry gate exactly as shown; the 1.18.2 source confirms that this dry ground/underwater form predates the pair. The new rider condition is source-confirmed only at 1.19.3 within this bounded check. This is an applicability prerequisite, not full-pair coverage, implementation approval or runtime parity.