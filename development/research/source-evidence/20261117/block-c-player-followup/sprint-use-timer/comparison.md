# Sprint double-tap timer reset: item use and sneak

## Comparison result

In Mojmap `LocalPlayer.aiStep`, the item-use reset is present in all six examined releases: 1.14.4, 1.15, 1.15.1, 1.15.2, 1.16, and 1.16.1. After the tick-start decrement of positive `sprintTriggerTime`, the method refreshes input, then executes `if (this.isUsingItem() && !this.isPassenger())`; its body scales both movement impulses by `0.2F` and writes `sprintTriggerTime = 0`. The no-passenger condition belongs to this item-use reset. The following double-tap sprint admission still checks `!isUsingItem()`.

The held-item witness is the player's use state, not merely holding an item: in each checked source, `startUsingItem(hand)` obtains `getItemInHand(hand)`, proceeds only when the stack is nonempty and the player is not already using an item, sets `startedUsingItem = true`, and `isUsingItem()` returns that flag. `stopUsingItem()` clears it. Thus the reset runs during active item use (and only while not a passenger), when started through the nonempty-hand path.

Sneak/sprint-key reset is absent in 1.14.4, 1.15, 1.15.1, and 1.15.2. In 1.16 and 1.16.1, `aiStep` snapshots `input.shiftKeyDown` into local `bl2` before calling `input.tick(...)`; after input refresh and the in-block checks, `if (bl2) this.sprintTriggerTime = 0` clears the timer. In 1.14.4 the analogous cached input is `input.sneakKeyDown`, but no timer write is made from it. In 1.15 through 1.15.2 the renamed field is `shiftKeyDown`, also without that write.

`KeyboardInput.tick` refreshes the sneak field from the configured key (`keySneak` in 1.14.4; `keyShift` in 1.15+). Since `bl2` is sampled before that refresh, the new 1.16 reset is specifically gated by the previously cached sneak input for this `aiStep`, not a fresh post-tick read. It is unconditional with respect to passenger or item-use state. The checked source establishes the observed boundary as absent in 1.15.2 and present in 1.16; it does not establish an exact introduction point inside that interval. Clearing a pending trigger removes the prior double-tap window; a later fresh qualifying press may begin a new window.

## Exact source locations

All paths are relative to `decompiled_minecraft/<version>/mojmap/`.

| Version | `LocalPlayer.java` evidence |
| --- | --- |
| 1.14.4 | `net/minecraft/client/player/LocalPlayer.java`: `aiStep` 618–635 (decrement, cached `sneakKeyDown`, item-use clear); 652–675 (double-tap conditions); `startUsingItem` 488–495, `isUsingItem` 498–500, `stopUsingItem` 503–506. |
| 1.15 | `net/minecraft/client/player/LocalPlayer.java`: `aiStep` 625–641 (cached `shiftKeyDown`, item-use clear); 657–680 (double-tap conditions); use-state methods 495–513. No `if (bl2)` timer write in the full method. |
| 1.15.1 | `net/minecraft/client/player/LocalPlayer.java`: `aiStep` 625–641 and 657–680; use-state methods 495–513. No `if (bl2)` timer write in the full method. |
| 1.15.2 | `net/minecraft/client/player/LocalPlayer.java`: `aiStep` 625–641 and 657–680; use-state methods 495–513. No `if (bl2)` timer write in the full method. |
| 1.16 | `net/minecraft/client/player/LocalPlayer.java`: `aiStep` 652–672 (decrement, cached `shiftKeyDown`, item-use clear); 688–702 (cached-sneak reset and double-tap conditions); use-state methods 524–542. |
| 1.16.1 | `net/minecraft/client/player/LocalPlayer.java`: `aiStep` 652–672 and 688–702; use-state methods 524–542. |

Input witnesses: `net/minecraft/client/player/Input.java` has `sneakKeyDown` in 1.14.4 (line 12) and `shiftKeyDown` in 1.15+ (line 12). `net/minecraft/client/player/KeyboardInput.java`, `tick`: 1.14.4 lines 13–25; 1.15.2, 1.16, and 1.16.1 lines 13–26. The 1.15/1.15.1 keyboard implementation is byte-identical to 1.15.2 for this source.

## Source identity and integrity

Namespace: Mojmap. The local `mojmap.provenance.json`, `mojmap.ready.json`, and `mojmap.sources.sha256` identify exact version exports. For 1.14.4 provenance records mapping option `feather,mojmap` and Feather Gen2 1.14.4 build 2 artifacts; 1.15/1.15.1 provenance records Mojmap on exact version client metadata; 1.15.2 and 1.16.1 provenance records Mojmap; 1.16 provenance records the exact Mojmap mapping artifact hash. For each examined release, the live `LocalPlayer.java` and `Input.java` SHA-256 values matched the corresponding entries in `mojmap.sources.sha256`.

| Release | LocalPlayer SHA-256 | `mojmap.sources.sha256` identity from ready marker |
| --- | --- | --- |
| 1.14.4 | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |
| 1.15 | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` |
| 1.15.1 | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` |
| 1.15.2 | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16 | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |
| 1.16.1 | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` |

Per-version `LocalPlayer.java` and `Input.java` hashes were cross-checked against their canonical source manifest entries. No product code, wiki material, Git history, tests, or runtime behavior was consulted.