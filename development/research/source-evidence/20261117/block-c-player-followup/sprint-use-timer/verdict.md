# Independent source audit verdict

**Verdict: ACCEPT for the bounded claim.** The comparison’s 1.15.2 → 1.16 claim is supported by the exact local Mojmap source exports: 1.16 adds a cached-sneak `sprintTriggerTime = 0` reset before the sprint admission checks. The item-use reset already exists on both sides and is separately gated by `!isPassenger()`.

## Reproduced source facts

- In 1.15.2 `LocalPlayer.aiStep` (`decompiled_minecraft/1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, lines 625–641), a positive `sprintTriggerTime` is decremented first. `bl2` snapshots `input.shiftKeyDown` before `input.tick(...)`. The active-item block scales both impulses by `0.2F` and clears the timer only under `isUsingItem() && !isPassenger()`. No later sneak-gated timer clear occurs in the full `aiStep`; double-tap admission follows at lines 657–671.
- In 1.16 `LocalPlayer.aiStep` (`decompiled_minecraft/1.16/mojmap/net/minecraft/client/player/LocalPlayer.java`, lines 652–672, 688–705), decrement and snapshot ordering remain. After `input.tick(...)`, the same non-passenger item-use block is present. Following the in-block checks, `if (bl2) sprintTriggerTime = 0` is added before the double-tap conditions.
- `KeyboardInput.tick` in both versions assigns `shiftKeyDown = options.keyShift.isDown()` (lines 13–26); therefore `bl2` is the cached value from before this input refresh. For example, if shift was down in the cached input and is released before this tick’s refresh, 1.16 still takes the reset branch even though refreshed `shiftKeyDown` is now false. This branch has no passenger or item-use condition. The same `bl2` also gates double-tap admission.
- The ordinary item-use witness is a non-passenger with a nonempty held stack actively using an item while the timer is positive. `LocalPlayer.startUsingItem` sets `startedUsingItem` for a nonempty stack when not already using; `isUsingItem()` returns that flag and `stopUsingItem()` clears it (1.15.2 lines 495–513; 1.16 lines 524–542). An ordinary bow-use path calls `player.startUsingItem(hand)` in each version (`world/item/BowItem.java`, 1.15.2 line 129; 1.16 line 117). Thus, e.g. with timer 2, active bow use and no passenger, the normal item-use branch decrements it to 1 and then clears it to 0 on both versions. The added 1.16 sneak branch independently clears a pending positive timer when cached sneak is true.
- Adjacent examined releases stabilize the observed source boundary: `LocalPlayer.java` is byte-identical across 1.15, 1.15.1 and 1.15.2 (SHA-256 `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` for 1.15/1.15.1; `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` for 1.15.2), and byte-identical across 1.16 and 1.16.1 (SHA-256 `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`). This establishes absence in the inspected 1.15 patch sources and presence in inspected 1.16 patch sources; it does not identify the exact intervening introduction commit or prove uninspected versions.

## Evidence identity

All evidence is canonical local `decompiled_minecraft/<version>/mojmap/` source. Ready markers report exact version IDs and `status: ready`; each cited source hash matches that version’s `mojmap.sources.sha256` entry. Relevant source-manifest identities from the ready markers:

| Release | `mojmap.sources.sha256` SHA-256 | `LocalPlayer.java` SHA-256 |
| --- | --- | --- |
| 1.15 | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15.1 | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15.2 | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| 1.16 | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| 1.16.1 | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |

The `Input.java` and `KeyboardInput.java` hashes are respectively `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` and `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` in each of the five inspected releases; both match their manifests. The comparison input bytes were verified as SHA-256 `adf72e79861226e52820dcaf3334b6d093c19e2b3845459e11d71c154c615519`.

## Limits

This audit accepts only the stated 1.15.2 → 1.16 source boundary and its item-use/sneak witnesses. It does not claim a full movement-pair coverage freeze, an exact patch/commit introduction point, runtime parity, or product implementation status. No wiki, product source, Git history, backlog, tests, game/runtime, or source generation was consulted or used.

**COMPLETED** — bound comparison SHA-256 `adf72e79861226e52820dcaf3334b6d093c19e2b3845459e11d71c154c615519`; source evidence is the five exact Mojmap exports and manifest identities listed above; scope and exclusions are limited as stated.
