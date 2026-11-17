# Independent source acceptance verdict — revised boat dismount finding

**Verdict: ACCEPT**

Bound candidate: `build/mcpk-20261116/vd-full-checklist/boat-sneak-115-116/candidate-source-finding.md`, SHA-256 `36F4969C679C15EB0A73BBE8C0DEB1C3FBC40F18DDE58886EFB2A397864924E1` (recomputed). Reviewed prior evidence: `.../source-verification.md`, SHA-256 `FE03D5D93168DF939FD219656CA5B4A15917EFFEAFFFA956A7DEA74B7FB76647` (recomputed). The prior REQUEST_CHANGES verdict SHA-256 `C6A074C1B49C495686927E075F0F2401550F1E1E89E15C13C7A60B9D420F616A` also matches.

## Finding

The corrected setter description resolves the only issue raised by the previous review. The candidate now says `Player.rideTick()` calls `setShiftKeyDown(false)`, which writes the entity shared flag, while `LocalPlayer.isShiftKeyDown()` reads `input.shiftKeyDown`; it correctly avoids claiming that the setter clears the local input state.

The bounded movement claim is source-supported. In 1.15.2 Mojmap `Player.rideTick()` lines 459–463 gates `stopRiding()` and `setShiftKeyDown(false)` behind `!level.isClientSide`, with `wantsToStopRiding()` returning `isShiftKeyDown()` at lines 290–292. In 1.16.0 lines 452–456 the shift/passenger branch remains but no longer has the client-side exclusion; `wantsToStopRiding()` remains input-predicate-backed through `LocalPlayer.isShiftKeyDown()` at lines 620–621. `LocalPlayer.rideTick()` delegates to `super.rideTick()` (1.15.2 lines 825–831; 1.16.0/1.16.1 lines 862–868; 1.16.2 lines 837–843), and in the boat case checks/forwards boat input only after that delegation. `ClientLevel.tickPassenger()` ticks `Player` passengers by calling `rideTick()` (1.15.2 lines 185–200; 1.16.0 and 1.16.2 lines 204–219). In 1.16.0, `Entity.stopRiding()` calls `removeVehicle()` (lines 1638–1640), so this is a local player ride-state change. The sampled gate remains unchanged in 1.16.1 and 1.16.2.

The finding is limited to the player's local dismount response when shift is held while a passenger, including a boat passenger. It does not establish boat steering or vehicle motion changes, nor continued crouching while remaining aboard. The endpoint evidence does not pinpoint an intermediate patch boundary beyond the source difference between 1.15.2 and 1.16.0.

## Evidence identity

Canonical source roots are `decompiled_minecraft/{1.15.2,1.16,1.16.1,1.16.2}/mojmap`. For each version, the ready marker declares the expected exact version and `mojmap`; its `sourceManifestSha256` matches the recomputed SHA-256 of `mojmap.sources.sha256`. The four manifest digests are, respectively: `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`, `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`, and `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.

Recomputed `Player.java`, `LocalPlayer.java`, `ClientLevel.java`, and `Entity.java` hashes match their corresponding source-manifest entries in all four versions. `Player.java` bytes are identical between 1.16.0 and 1.16.1; the cited `rideTick()` gate was also read directly in 1.16.2. Relevant 1.16.0 hashes: `Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; `LocalPlayer.java` `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; `ClientLevel.java` `3bae536422595514893b85bcde09b59459c292c737dd487d69db49d28913da7b`; `Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`.