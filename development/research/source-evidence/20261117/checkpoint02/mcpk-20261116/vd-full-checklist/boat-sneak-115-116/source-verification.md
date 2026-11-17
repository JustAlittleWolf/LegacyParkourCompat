# Boat sneak hint: source verification

## Assigned hint

The supplied local MCPK input identifies `== 1.16 (Nether Update) ==` and, under Movement, says at line 374: “Bugfix: The player can once again sneak normally on boats.” This report uses only that supplied page extract and exact local Mojmap source for 1.15.2, 1.16, 1.16.1, and 1.16.2.

## Source identity and integrity

- Mapping family: Mojmap. Version IDs in the canonical `mojmap.ready.json` files and their paired provenance records match the directories (`1.15.2`, `1.16`, `1.16.1`, `1.16.2`). Each ready marker's `sourceManifestSha256` equals the SHA-256 of its `mojmap.sources.sha256` file. Manifest digests: `1.15.2` `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; `1.16` `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; `1.16.1` `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; `1.16.2` `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.
- Every relevant source-file byte hash below was checked against its per-version source-manifest entry; all matched. `Player.java` SHA-256: `1.15.2` `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; `1.16`/`1.16.1` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; `1.16.2` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`.
- `LocalPlayer.java` SHA-256: `1.15.2` `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; `1.16`/`1.16.1` `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; `1.16.2` `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`.
- `KeyboardInput.java` SHA-256 is identical in all four trees: `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7`.
- Supplied wiki file SHA-256: `4e8b9f6ed957883f56ee181dc1eec8cafa8dd7323e25f4c24871fb86c52476d3`.

## Exact-source comparison

- 1.15.2 Mojmap `net/minecraft/world/entity/player/Player.java`, `rideTick()`, lines 459–463: dismounts when `wantsToStopRiding() && isPassenger()`, but only inside `!level.isClientSide`; then calls `stopRiding()` and `setShiftKeyDown(false)`. `wantsToStopRiding()` at lines 290–292 returns `isShiftKeyDown()`.
- 1.16 Mojmap `Player.java`, `rideTick()`, lines 452–456: the same shift/passenger gate calls `stopRiding()` and clears shift, now with no `!level.isClientSide` condition. Thus the local client player's own `rideTick()` reaches the dismount branch. `wantsToStopRiding()` at lines 283–285 still returns `isShiftKeyDown()`.
- 1.16.1 has the same `Player.java` bytes as 1.16. 1.16.2 has different `Player.java` bytes, but the `rideTick()` and `wantsToStopRiding()` gates at lines 452–456 and 283–285 are unchanged.
- The local shift predicate is input-backed in all endpoints: LocalPlayer.isShiftKeyDown() returns input != null && input.shiftKeyDown (1.15.2 lines 591–592; 1.16 and 1.16.1 lines 620–621; 1.16.2 lines 595–596).
- LocalPlayer delegates its `rideTick()` to `super.rideTick()` before its boat-specific handling: 1.15.2 lines 825–831; 1.16 lines 862–868; 1.16.2 lines 837–843. When still a passenger of a `Boat`, it forwards only left/right/up/down key booleans to `boat.setInput`. Boat steering and vehicle motion are not assessed here.
- The ordinary crouch-input slowdown is not the new boat behavior: 1.15.2 `LocalPlayer.aiStep()` calls `input.tick(isMovingSlowly())`; 1.16 computes `crouching` from the same pose/shift predicate immediately before that call. `KeyboardInput.tick(boolean)` in both sides multiplies only player `leftImpulse` and `forwardImpulse` by `0.3F` when slow movement is requested (lines 13–25). Boat-control booleans are separate. No boat-specific crouch movement coefficient or player travel arithmetic change is established by this path.

## Terminal verdict

**Source-supported, narrowly:** by 1.16.0, holding the shift/sneak key while the local player is a passenger causes the player-side ride tick to dismount locally and clear the shift state; in 1.15.2 that dismount branch is server-only. This applies to a boat passenger because `LocalPlayer.rideTick()` delegates to the same player gate. The targeted gate is unchanged in 1.16.1 and 1.16.2. The source does not support reading the hint as “continue crouch-sneaking while remaining aboard”; the player-side shift action ends the ride. The claim is not about boat movement physics.

