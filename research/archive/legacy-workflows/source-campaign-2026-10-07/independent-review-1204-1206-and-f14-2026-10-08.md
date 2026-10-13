# Blind source snapshot review — 2026-10-08

## Scope and provenance

Independent source-only review of six immutable 1.20.4→1.20.6 finding snapshots at pair checkpoint 175d90f4f52e937e3cd6d40c6ad8423b7e4be5c2, plus F-14 in the 1.21.4→1.21.5 checkpoint ebd2e6ea513d3b41a12307cfb2980b5206c5d211. The exact commit-bound finding blobs were read and their original blob bytes SHA-256 hashed. Exact canonical ready Mojmap source trees were inspected read-only; each cited file hash was checked against its source manifest.

No mod implementation, implementation review, wiki/MCPK, tests, build, runtime, Docker, or other pair report was inspected or changed. Source producer limits are retained: this review accepts conditional direct player consumers where the finding says the upstream value is external, but does not assert that vanilla supplies a non-default attribute value. No first-change release inside either endpoint interval is inferred.

Exact source publication identities:
- 1.20.4 Mojmap: source manifest fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1; artifact manifest ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948.
- 1.20.6 Mojmap: source manifest 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311; artifact manifest e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31.
- 1.21.4 Mojmap: source manifest f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0; artifact manifest 1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841.
- 1.21.5 Mojmap: source manifest 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9; artifact manifest d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656.

All four ready markers and manifest bytes match these identities. All source-file hashes cited by the six 1.20.4→1.20.6 snapshots match their source manifests. For F-14, every cited source hash matches except the 1.21.5 LivingEntity.java hash; see its verdict below. Original and mapped client-jar hashes in the F-14 finding match the artifact manifests. The original client jar was also inspected read-only for item resources; the relevant Elytra components are defined in source code instead.

## Verdict index

| Finding | Immutable identity | Verdict |
|---|---|---|
| MC1204-1206-01 | 1985fa829eb597acbc23685935c5f6929c078f78; SHA-256 eb15d0f72764cf783cb0ac260d28f8a31827f3bb407015dd66f1601ca3ad0fda | ACCEPT |
| MC1204-1206-02 | b168d90c05e813d6dff2ccd97090dc1602d9d418; SHA-256 9e073eb434e750f6babac4cd6881b2cf38b1e4aad37523db0f62657b82071315 | ACCEPT |
| MC1204-1206-03 | 467e5a80114239be1135b9ebc4bd2ee1d2b8bdc2; SHA-256 ad2013db24d087f36d443c1b83ad1c571846274d95790f5ad5fbba3891dc6e1d | ACCEPT |
| MC1204-1206-04 | 0251c08c91a20339c826a0f755078dc47f035f4b; SHA-256 405a295fd0ca7b7b00311f234745e748ff50acdd437ca9cc019bd4e746966918 | REQUEST CHANGES |
| MC1204-1206-05 | 8797e40a04bbcb2de46fa4a689b181afb8a1a4ad; SHA-256 a6296dcbedc454962625693587baf4d4a3a950f4453518178d6799bca92a26d4 | ACCEPT |
| MC1204-1206-06 | d6479a08528c346d9462c952cf8c13da8aaf4915; SHA-256 2d6264950d59ddddaceca004e4c82dd53ab7773f6e894cf97d4f5add254ac6a0 | ACCEPT |
| F-14 | checkpoint  ebd2e6ea513d3b41a12307cfb2980b5206c5d211; SHA-256 720e2c042ee79e5ba7869ab8649d8a6beec81ea0cba33848a980eeb423ee52d0 | REQUEST CHANGES |

The six 1.20.4→1.20.6 findings are at workflows/source-campaign-2026-10-07/1.20.4--1.20.6/findings/<ID>.md in their respective immutable commits. F-14 is bound to workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-14-sleep-fall-flying-pose-priority.md in checkpoint commit ebd2e6ea513d3b41a12307cfb2980b5206c5d211; it has no separate snapshot-log commit in that checkpoint.

## MC1204-1206-01 — ACCEPT

The source delta and reachable player path are correct. A LocalPlayer.aiStep toggles mayfly flight on the second jump press and updates abilities without calling jumpFromGround (lines 725–733). B performs the same toggle and, when the resulting flying flag is true and the player is grounded, calls jumpFromGround before updating abilities (lines 728–740). Player.jumpFromGround delegates to LivingEntity.jumpFromGround in both releases. This is a direct player movement call; its food/stat side effects are excluded from the finding.

The stated preconditions (mayfly, second jump-key press, not swimming, grounded, and toggling flight on) make the extra call reachable. The consequence remains conditional on B jump power passing the cutoff in MC1204-1206-02. Keep first introduction unknown within (1.20.4, 1.20.6].

## MC1204-1206-02 — ACCEPT

The paired ground-jump methods support the stated delta. A always writes jump power to vertical velocity and marks an impulse; sprint adds trigonometric components after multiplying by float literal 0.2F (LivingEntity.java:2008–2017). B skips velocity writes, the sprint impulse, and the impulse flag when power <= 1.0E-5F; otherwise it uses a double 0.2 in the Vec3 construction (LivingEntity.java:2069–2081). The paired Player overrides delegate to this method. The finding correctly leaves the precise movement consequence conditional and does not emulate the excluded food/stat production.

The new cutoff and arithmetic difference are direct player-response changes for the stated inputs. First introduction remains unknown within the endpoint pair.

## MC1204-1206-03 — ACCEPT

A uses the fixed 0.42F jump-power term and has only horse.jump_strength, not a generic player jump-strength input. B reads the syncable generic.jump_strength attribute (default 0.42F, range 0–32); Player inherits it through LivingEntity.createLivingAttributes. The client update handler applies received living-entity attribute base values and modifiers. At the default value, the added multiplication by 1.0F preserves the prior base formula; a supplied non-default value scales it. The finding explicitly keeps that upstream producer external and makes no vanilla-value claim.

The paths reach player ground jumping and the conditional consequence in MC1204-1206-02. This is an in-scope conditional player movement input, not an inferred effect/producer trajectory. First introduction remains unknown within the endpoint pair.

## MC1204-1206-04 — REQUEST CHANGES

The gravity attribute and Slow Falling difference are supported, as are the cited ordinary, water, lava, and fluid-falling branches. However, the finding omits another in-scope consumer of the same gravity local in the bounded LivingEntity.travel method: fall-flying movement.

In A, travel initializes the local gravity to 0.08 and the Fall Flying branch uses it in the vertical movement update (LivingEntity.java:2035–2041, 2099–2125). In B, the local comes from getGravity(), then the Fall Flying branch uses that value in the same glide acceleration (LivingEntity.java:2104–2110, 2168–2194). Therefore the conditional non-default gravity delta also changes player fall-flying travel. The report's stated list of affected player travel branches is incomplete.

Correction requested: include the fall-flying consumer in the finding's source-level difference/applicability and preserve its non-default-input condition. Keep FlyingAnimal and other non-player movement excluded; do not infer the external producer or measured trajectory. First introduction remains unknown within the endpoint pair.

## MC1204-1206-05 — ACCEPT

A LivingEntity sets inherited max-up-step to 0.6F in its constructor; its override preserves at least 1.0F for a controlling-player passenger. B's override reads syncable generic.step_height (default 0.6, range 0–10), with the same passenger minimum. Player inherits the attribute builder, and the client handler applies received attribute snapshots. Both Entity.collide methods use virtual maxUpStep for the same guarded step-candidate calculations (A/B Entity.java:880–893). The default remains equivalent; only a supplied non-default value changes the step-height input. The finding correctly leaves server-side production and collision outcomes open.

This is a direct player movement input under the stated condition. No broad step-candidate outcome is claimed. First introduction remains unknown within the endpoint pair.

## MC1204-1206-06 — ACCEPT

A LivingEntity.getScale returns 0.5 for babies and 1.0 otherwise; Player is not a baby and supplies its pose dimensions. B reads and sanitizes generic.scale (default 1.0, range 0.0625–16), inherits the attribute through the living builder, and applies it to the player's default pose dimensions. B refreshes dimensions after dirty attributes change, updating the active box and eye height. The client update handler applies synchronized values. The finding makes no claim about an upstream non-default producer or a particular collision result.

For an equivalent default player, dimensions remain the same; the conditional new input affects direct player dimensions/state. First introduction remains unknown within the endpoint pair.

## F-14 — REQUEST CHANGES (behavior reachable; repair source hash)

The central behavior is supported and concretely reachable through the real server bed-start/tick path; this is not merely a hypothetical contradictory state.

BedBlock.useWithoutItem returns on the client and invokes Player.startSleepInBed on its server path. ServerPlayer.startSleepInBed checks alive/sleeping, natural dimension, bed range/obstruction, night, and safety, but has no onGround or fall-flying rejection. The inherited Player.startSleepInBed delegates to LivingEntity.startSleeping. That method stops riding only if needed, sets SLEEPING pose and sleeping position, positions the player at bed Y + 0.6875, zeroes velocity, and does not clear shared fall-flying flag 7. Entity.setPos changes position/bounding box without assigning onGround (A Entity.java:399-402; B:407-410).

A concrete valid precondition set exists: use a safe, unobstructed bed at night in a natural dimension while airborne and within the server's bed range (A ServerPlayer.java:1113-1151, 1123-1130; B:1067-1105, 1123-1130); the bed callback runs server-side (A BedBlock.java:83-119; B:81-117). The sleep gates do not test onGround or fall-flying. Use a non-riding player without Levitation or creative flight and an undamaged chest Elytra. Both Items.java defaults define Elytra with GLIDER and chest EQUIPPABLE components (A:971-985, SHA-256 e872f8251d0b4f4a8b8ed1609dda56b57d7bc8ce35fc4477095334cbd6d3df0b; B:989-1004, SHA-256 d258c31e88d6e7208b7c196a7b7408172f7c54050e11442e54894a1d31f4f5d5). B DataComponents.java defines GLIDER at line 174 (SHA-256 a23368a7039ea5c0709d080c41387125e97685a3ced77622adbb10ccf2ef0ffe); B LivingEntity.canGlideUsing checks GLIDER, EQUIPPABLE slot and non-breaking state at lines 3622-3629. The player-specific canGlide wrapper excludes only active abilities.flying before delegating (A Player.java:1474-1475; B:1452-1453); the shared canGlide checks ground, riding, Levitation and equipment (A LivingEntity.java:2847-2859; B:2837-2849), not sleeping.

The next Player.tick calls super.tick before updatePlayerPose (A Player.java:256-329; B:271-344). LivingEntity.tick calls aiStep (A LivingEntity.java:2468-2469; B:2499-2500); aiStep runs updateFallFlying before the fall-flying travel branch (A:2769-2771, 2189-2197; B:2753-2755, 2213-2221). The bed collision-shape base is 9/16 high (A BedBlock.java:53, 203-213; B:56-60, 201-203); sleep positions the player's feet at bed Y + 0.6875. With open space above and a horizontal look, the zero-velocity first glide update uses the paired formula at A LivingEntity.java:2290-2312 and B:2322-2345; at default gravity 0.08, vertical displacement is less than the 0.125 bed clearance. Thus it does not establish ground contact before this tick's pose update. Both versions' pose methods first test the 0.6×0.6 swimming-pose dimensions (Player.java:434-465 / 449-480); in an open space the desired fall-flying/sleeping pose fits. The retained flag is therefore consumed by the pose selector on the real post-bed-start tick path.

At that update, 1.21.4 prioritizes isFallFlying over isSleeping in Player.updatePlayerPose; 1.21.5 getDesiredPose prioritizes isSleeping. Thus the one-tick pose/dimension/eye-height difference is reachable. It can be transient if later contact makes the server clear fall flying; no trajectory beyond this source path is asserted. It remains an in-scope player movement-state difference.

Correction requested: the finding's 1.21.5 LivingEntity.java SHA-256 is mistyped. The finding records a8aed863d4fdc515c751dd2878a8bbc9b13179228cb8dbb50edf1d19cd5404271, but both the canonical ready source and its source manifest record a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271. The other cited F-14 source hashes match their ready-manifest entries. Correct that binding in a replacement finding snapshot and request a fresh review of that replacement. F-14's behavior claim itself is source-supported.

## Pair status

The 1.20.4→1.20.6 pair remains PARTIAL; this review dispositions only six findings and does not complete the open inventories or a full-pair audit. The 1.21.4→1.21.5 pair also remains PARTIAL; this review does not freeze its source inventory. Unknown within-interval cutovers remain unknown. No implementation or runtime status is inferred.