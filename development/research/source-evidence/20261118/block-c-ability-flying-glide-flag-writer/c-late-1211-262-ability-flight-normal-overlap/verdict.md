# Independent audit verdict: ability-flight/glide overlap

## Verdict

**ACCEPTED** for the narrow claim: on the ordinary local-player route, an authorized ability-flight double-tap can toggle `abilities.flying` on while the synchronized fall-flying flag is already live; with no fluid-travel precedence, no passenger state, and no climbable travel branch, the same local tick performs glide movement in both releases. The 1.21.1 player post-travel branch clears shared flag 7 after `super.travel`; the 26.2 branch does not clear it there. This finding does not establish a duration for the 26.2 flag or a guarantee about when authoritative state may later change.

## Frozen input

- Report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-late-1211-262-ability-flight-normal-overlap/report.md`
- Full report SHA-256: `71ef39910cc182c1baf12e691bfa4d35be866f27081f6b191cf0964e7c01e68c`

## Canonical source provenance and content digests

Read-only primary checkout sources were checked in place. The exact version and namespace fields in provenance and ready markers agree with the report. Actual source-manifest file bytes match the ready-marker hashes; all three reviewed Java file hashes match their entries in those manifests.

- `decompiled_minecraft/1.21.1/mojmap/`: provenance `mojmap.provenance.json` SHA-256 `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; ready marker `mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; version `1.21.1`, namespace/mapping option `mojmap`; actual `mojmap.sources.sha256` SHA-256 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, matching ready marker.
- `decompiled_minecraft/26.2/unobfuscated/`: provenance `unobfuscated.provenance.json` SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; ready marker `unobfuscated.ready.json` SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; version `26.2`, namespace `unobfuscated`; actual `unobfuscated.sources.sha256` SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`, matching ready marker.

Per-file SHA-256 values, checked against manifest entries and the local files:

| Release / source file | SHA-256 |
|---|---|
| 1.21.1 `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| 1.21.1 `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.1 `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| 26.2 `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |

This checks canonical source publication identity and source content against the local manifests. It does not revalidate the historical raw client binaries or claim cross-toolchain reproducibility.

## Independent trace

- **Reachable input route:** In 1.21.1 `LocalPlayer.aiStep` lines 717–748, `mayfly` permits the ordinary non-spectator jump double-tap toggle; that branch does not test the already-live fall-flying flag and sets `$$11`, suppressing a new glide-start attempt on the toggle tick. In 26.2 `LocalPlayer.aiStep` lines 830–858, `mayfly` and a prior jump trigger permit the ordinary double-tap toggle; no flag-7 test or clear occurs, and `justToggledCreativeFlight` suppresses only new glide initiation. `super.aiStep()` follows in both files (1.21.1 line 804; 26.2 line 914), so the changed ability value reaches the same tick's living-entity travel path.
- **Live flag through update:** 1.21.1 `LivingEntity.aiStep` line 2668 calls `updateFallFlying` before travel. At lines 2716–2740 the method reads flag 7, keeps it valid for an airborne non-passenger with an enabled Elytra and no levitation, and writes the recalculated flag only when `!level().isClientSide`. In 26.2 `LivingEntity.aiStep` lines 3127–3129 calls `updateFallFlying` when the flag is already true; lines 3193–3198 put the validity clear behind `!level().isClientSide()`. Given the report's valid-Elytra conditions, this local update leaves flag 7 live.
- **Movement branch:** 1.21.1 `LivingEntity.travel` lines 2099–2152 checks water/lava handling before `isFallFlying`; the player override `Player.isAffectedByFluids` at lines 1065–1066 returns `!abilities.flying`. Under the stated no-fluid-precedence conditions, the live flag selects glide travel, whose source calls `move` at line 2177. In 26.2 `LivingEntity.travel` lines 2430–2436 checks `shouldTravelInFluid` before `isFallFlying`; `Player.isAffectedByFluids` lines 875–876 likewise returns `!abilities.flying`. `travelFallFlying` lines 2581–2594 calls `move` on the ordinary non-climbable branch. The player `canGlide` ability-flight veto at `Player.java` lines 1427–1429 does not cancel an already-live flag-selected travel branch.
- **Post-travel flag operation:** In 1.21.1 `Player.travel` lines 1468–1474 calls `super.travel`, then applies vertical-velocity handling, resets fall distance, and directly calls `setSharedFlag(7, false)`. In 26.2 `Player.travel` lines 1417–1422 calls `super.travel` and then adjusts Y velocity; there is no flag-7 clear in that branch. A separate climbable branch in 26.2 `LivingEntity.travelFallFlying` lines 2582–2584 calls `stopFallFlying`, which clears the flag; it is outside the accepted ordinary non-climbable case.

## Scope and limitations

This is an ordinary reachable local path, not a crafted state or custom event. The toggle and live flag overlap is available on the same tick under the source gates; no latency, ordering across later network updates, or minimum/maximum survival time is inferred. The accepted delta is the direct flag-7 operation. Fall-distance handling is separate: 1.21.1 `Player.travel` resets it after travel, while 26.2 `Player.aiStep` also resets it earlier when ability flight is active (`Player.java` lines 449–453); that is not part of this verdict. Server movement, server validation, damage, packets/timing guarantees, other input routes, passengers, and other fluid/climbable branches remain outside this bounded claim.