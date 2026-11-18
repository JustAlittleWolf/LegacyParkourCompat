# 1.15.2 to 1.16: ordinary horizontal input assembly

## Bounded result

No independent horizontal input assembly difference was found for an ordinary dry, local, unmounted, nonsprinting player. The keyboard axes, crouch slowdown passed to keyboard input, ordinary item-use damping, local-player horizontal input consumption, LivingEntity horizontal damping, and vector construction passed to `travel` are behaviorally unchanged for this path. No finding draft is warranted for this bounded slice.

## Exact source identity and integrity

Sources read read-only from `decompiled_minecraft/1.15.2/mojmap/` and `decompiled_minecraft/1.16/mojmap/`.

- 1.15.2 `mojmap.ready.json` SHA-256: `64A0E40B784D525CE50937C63E465FC4F564EFB1F7AF17F2BAC3C77A36907BE0`. It declares `versionId: 1.15.2`, `mapping: mojmap`, ready status, and 3,332 source files / 17,543,871 bytes. The version-root `mojmap.provenance.json` SHA-256 is `D4802D35EE2927A44D753871F184C3255C060EB94457A5C65A8BC121A087954E`; it declares version `1.15.2`, namespace `mojmap`, and exact-run mapping option `mojmap`. Its `mappingArtifacts` value is null, so this provenance file does not identify a separate mapping artifact.
- 1.16 `mojmap.ready.json` SHA-256: `5B6CB24F95E83B856C53F646D6477F2E46DAE2276737DEE56DD07C5BD745E4A2`. It declares `versionId: 1.16`, `mapping: mojmap`, ready status, and 3,572 source files / 19,220,152 bytes. Its version-root `mojmap.provenance.json` also declares version `1.16`, namespace `mojmap`, exact resolved version `1.16`, mapping option `mojmap`, and the client mapping artifact SHA-256 `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d`.
- The 1.15.2 source manifest SHA-256 is `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; the 1.16 source manifest SHA-256 is `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`. Both equal the hashes declared in their ready markers. Recursive source file counts and byte totals match both ready markers.
- All examined Java files match their entries in the corresponding source manifests. SHA-256 values (1.15.2 / 1.16): `KeyboardInput.java` `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` / same; `Input.java` `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` / same; `LocalPlayer.java` `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` / `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; `LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` / `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`.

## Compared slices and result

- `net/minecraft/client/player/KeyboardInput.java`, `tick(boolean)`, 1.15.2 and 1.16 lines 12–26: identical direction-key sampling, opposing-key cancellation, `+1.0F` / `-1.0F` impulses, jump/shift sampling, and conditional `0.3` scaling cast back to `float` for both horizontal impulses.
- `net/minecraft/client/player/LocalPlayer.java`, 1.15.2 lines 595–618 and 625–641; 1.16 lines 620–650 and 652–672: horizontal field writes in `serverAiStep` are identical (`xxa = input.leftImpulse`, `zza = input.forwardImpulse`, guarded by `isControlledCamera()`). Both `aiStep` methods capture earlier input flags, pass `isMovingSlowly()` into `input.tick`, then notify the tutorial, then multiply each horizontal impulse by `0.2F` iff `isUsingItem() && !isPassenger()`. The sprint timer write alongside this gate is outside this assignment.
- Crouching gate detail: 1.15.2 `isCrouching()` computes the flying/swimming/pose/shift condition when called (lines 595–599); 1.16 assigns `crouching` from that same condition immediately before `input.tick` (lines 662–666), and `isCrouching()` then reads the field (lines 624–627). At the point `isMovingSlowly()` is consumed by `input.tick`, both evaluate the same condition against the pre-tick input state. The post-`input.tick` getter timing differs, but this does not change the already-assembled horizontal impulses in this slice; downstream pose or geometry consumers are outside the assignment.
- `net/minecraft/world/entity/LivingEntity.java`, 1.15.2 lines 2253–2287 and 1.16 lines 2412–2457: after `serverAiStep`, both multiply `xxa` and `zza` by `0.98F` in the travel phase, then call `travel(new Vec3(xxa, yya, zza))`. The ordinary dry path reaches this shared sequence. Branch-specific travel math and `moveRelative` are outside scope.
- `net/minecraft/client/player/Input.java`, `getMoveVector()`, lines 18–20 in both releases: identical construction `new Vec2(leftImpulse, forwardImpulse)`. LocalPlayer's `isMoving()` reads that vector in both releases (1.15.2 lines 972–975; 1.16 lines 1009–1012); this consumer did not alter the assembled values.

## Reachability, dependencies, and limits

The path is reachable when the local player is the controlled camera, dry, unmounted, not sprinting, and not in another travel mode. Keyboard key state, pose admission, item use, and hand state are treated as inputs. The exact order of keyboard tick, slow scaling, item-use damping, local-player field consumption, LivingEntity `0.98F` damping, and `Vec3` construction was checked on both releases. The superclass AI path supplies the `serverAiStep` and travel call sequence.

Excluded from this slice: diagonal/non-keyboard input plumbing, sprint initiation/timers and sprint-speed effects, jump and vertical behavior, swimming/fluids, flying/Elytra, climbing, collision/support/stepping, pose/dimension consequences beyond the crouch input gate, travel-body math, equipment/effect producers, and block coefficients. These exclusions and this terminal slice do not establish pair-wide source coverage or independent audit completion. Remaining gate: pair coordinator's complete inventory, dependency closure, and independent whole-pair coverage audit.

Status: COMPLETED for the assigned bounded source comparison; no finding draft.
