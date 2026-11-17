# Independent mounted flight toggle source review

Terminal status: COMPLETED
Verdict: ACCEPTED for the bounded mounted flight toggle finding.

## Immutable inputs

- Draft: `mounted-flight-toggle-source/comparison.md`
- Draft SHA-256: `3fc506d99873fdc615c2fbb0e03131c1f66abf785e44cadbd53efd27cd8d6c1c` (recomputed; matches the assigned revision).
- Canonical evidence root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.
- Reviewed exact source SHA-256 values for `LocalPlayer.java`:
  - 1.21.9 Mojmap: `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`.
  - 1.21.10 Mojmap: `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`.
  - 1.21.11 Mojmap: `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477`.
  - 26.1.2 unobfuscated: `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`.
  - 26.2 unobfuscated: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.

## Findings verified

- In 1.21.9 and 1.21.10 `LocalPlayer.aiStep`, the flight path is gated by `mayfly`; spectator mode turns flying on through the separate `isAlwaysFlying()` branch. Otherwise a fresh jump edge (`!wasJumping`, jump pressed, and not auto-jump) begins/continues the seven-tick double-tap window. On the second qualifying edge, `!isSwimming()` is the only eligibility test before flipping `flying`; there is no passenger/vehicle test. The successful toggle conditionally calls `jumpFromGround()` when newly flying and on ground, updates abilities, and clears the trigger. 1.21.9 lines 751-772; 1.21.10 same method and source hash.
- In 1.21.11 lines 792-813, and 26.1.2 lines 831-852 / 26.2 lines 831-852, the equivalent branch additionally requires `(getVehicle() == null || jumpableVehicle() != null)`. This verifies the endpoint change between 1.21.10 and 1.21.11 and its continued presence in 26.1.2 and 26.2. It does not establish an intermediate release cutover.
- The vanilla boat witness is valid: 1.21.9 `AbstractBoat` extends `VehicleEntity` and does not implement `PlayerRideableJumping`; 1.21.11 `vehicle/boat/AbstractBoat` has the same declaration. `LocalPlayer.jumpableVehicle()` returns non-null only for a controlled vehicle implementing `PlayerRideableJumping` and passing `canJump()`. Thus a player with `mayfly == true`, not in spectator mode or swimming, and riding a boat passes the older second-press gate but fails the newer vehicle eligibility gate. The horse declaration in 1.21.9 `AbstractHorse` implements `PlayerRideableJumping`, supporting the stated counterexample class, subject to `canJump()`.
- The spectator and touch-ground flying-stop gates were checked separately. In 1.21.9, `MultiPlayerGameMode.isAlwaysFlying()` returns `localPlayerMode == GameType.SPECTATOR`; in 1.21.11 and 26.2, `isSpectator()` returns the same equality. The end-of-`aiStep` ground-stop branch changes from `!isAlwaysFlying()` to `!isSpectator()` (1.21.9 lines 835-838; 1.21.11 lines 876-879; 26.2 lines 915-918). These are semantically equivalent in the inspected sources and do not alter this finding's witness.
- The local vertical-input operation is retained: when `flying && isControlledCamera()`, jump adds one and shift subtracts one; nonzero input directly adds `input * getFlyingSpeed() * 3.0F` to player delta-movement Y (1.21.9 lines 791-803; 1.21.11 lines 832-844; 26.1.2 lines 871-883; 26.2 lines 871-883). `isControlledCamera()` compares the camera entity to the local player. This supports the draft's reachable local-player velocity statement; it does not imply vehicle physics changes.

## Source identity and limits

The 1.21.9 `mojmap/.decompile-provenance.json` records the source hash, 1.21.9 client JAR hash `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`, and Mojang `1.21.9.json` mapping input hash `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`. The 1.21.10 and 1.21.11 Mojmap provenance files identify their release IDs and exact decompile run; their source hashes match their source manifests. The 26.1.2 and 26.2 unobfuscated provenance files identify those release IDs and runs, and their source hashes also match their manifests. The 26.2 metadata records `mappingArtifacts: null` and a summary-excerpt success log, so this audit relies on its declared unobfuscated run plus the matching local source hash/manifest, not a separately recorded mapping artifact hash.

This verdict accepts only the bounded finding and its cited local witness/gates. It is not a full-pair tick-graph coverage audit, does not establish the first intermediate release containing the change, and includes no runtime or trajectory verification. No product sources, wiki material, backlog, Git history, tests, launches, or source generation were used.