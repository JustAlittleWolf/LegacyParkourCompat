# 1.14.1 → 1.14.2 dry horizontal input assembly audit

## Bounded verdict

No source-level change was found in the requested ordinary dry local-player horizontal input assembly: logical keyboard directions become the same independent `movementSideways` and `movementForward` values, keyboard slowdown is the same, the local camera player copies the same input fields into its movement-speed fields, item-use damping is the same, and the same damped horizontal fields are supplied to `travel`.

This verdict is limited to the requested path for ordinary standing/crouching, unmounted, nonsprinting movement on ground or in ordinary air. It does not establish whole-tick parity. Crawling/swimming pose behavior, sprint decisions, the body of `travel` and its later vector math, vertical movement, and other excluded systems are not assessed here.

## Immutable source identity

Both source trees are `decompiled_minecraft/<version>/yarn` and their `.decompile-provenance.json` hashes match the assigned identities. Manifest metadata pins the exact client and Yarn/intermediary inputs; actual hashes of every source file cited below match the corresponding manifest `sources` entry.

| Release | Yarn mapping | Provenance SHA-256 | Client jar SHA-256 | Yarn mapping artifact SHA-256 | Intermediary artifact SHA-256 | Version metadata SHA-256 |
|---|---|---|---|---|---|---|
| 1.14.1 | Yarn `1.14.1+build.10` | `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` |
| 1.14.2 | Yarn `1.14.2+build.7` | `786546F99684423CC2563BE45C9C3334DFCC2C7DB58DDB74BA39CE403BD0869A` | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `237aa8d499b71bb17122cbee40b8748efcd5ac9d8362c68c89e99ab0703e66b8` |

The exact intermediary input URLs are `https://maven.fabricmc.net/net/fabricmc/intermediary/1.14.1/intermediary-1.14.1-v2.jar` and `https://maven.fabricmc.net/net/fabricmc/intermediary/1.14.2/intermediary-1.14.2-v2.jar`.

## Paired source evidence

- `net/minecraft/client/input/KeyboardInput.java`, `tick(boolean, boolean)`, lines 13–25 in both releases; SHA-256 in both: `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0`.
  - Both read forward/back/left/right key states, assign zero when opposite booleans are equal, otherwise assign forward/left `+1` and back/right `-1`; read jump and sneak keys; then, when `!bl2 && (sneaking || bl)`, multiply each horizontal float by `0.3` and cast the double result back to float. The operation order and expression are byte-identical. The two axes are assembled independently here; no diagonal normalization occurs in this method.
- `net/minecraft/client/input/Input.java`, fields and base `tick(boolean, boolean)`, lines 5–16 in both releases. The relevant public float fields `movementSideways` and `movementForward` match, as does the no-op base `tick`. The 1.14.2 file adds `method_20622()` at lines 22–24, outside this ordinary dry input assembly. Whole-file SHA-256: 1.14.1 `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351`; 1.14.2 `0a14d641cea95ecd6a836a2aecf0a7627dbf236460160a263a85b574a0216e4e`.
- `net/minecraft/client/network/ClientPlayerEntity.java`, `tickNewAi()`, lines 589–599 in both; hashes: 1.14.1 `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933`, 1.14.2 `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947`. After `super.tickNewAi()`, the `isCamera()` gate copies input sideways/forward floats to `sidewaysSpeed`/`forwardSpeed`, and input jumping to `jumping`, identically in both releases.
- `ClientPlayerEntity.tickMovement()`, lines 613–625 in both releases: both snapshot prior jump/sneak input, compute the input slowdown argument, call `input.tick(...)`, notify the tutorial manager, and, if using an item while unmounted, multiply both input horizontal fields by `0.2F` and reset the sprint delay counter. The exact item-use gate, axis writes, multiplier, and order are the same.
- `net/minecraft/entity/LivingEntity.java`, `tickMovement()`: the common local-player path invokes `tickNewAi()` when movement is allowed; then, immediately before travel, multiplies `sidewaysSpeed` and `forwardSpeed` by `0.98F`, initializes AI state and bounding box, and calls `travel(new Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed))`. Call-site lines are 2198–2205 in 1.14.1 and 2204–2211 in 1.14.2. The cited lines and order match. The passed horizontal components are those damped fields; any normalization or use inside `travel` is outside this audit.

Actual source SHA-256 values equal provenance entries for the cited `KeyboardInput.java`, `Input.java`, `ClientPlayerEntity.java`, and `LivingEntity.java` on each side. Full values are recorded in the manifests at the exact paths above.

## Scope boundary

The input slowdown boolean comes from the client pose/sneak path. `ClientPlayerEntity.isInSneakingPose()` gained an additional `!isSwimming()` gate in 1.14.2; under this audit’s dry, non-swimming standing/crouching precondition that gate is true on both sides. Swimming and pose-specific outcomes are excluded. Sprint predicates and sprint-state changes are also excluded by assignment; this finding does not assess their behavior.
## Phase 2 independent audit

**Verdict: ACCEPT** the bounded author claim in report SHA-256 `87471A31B22D4CC136E9E7F67003F6A607D8602E9F5747340A49BD8B3DDA8B2A` (4,824 bytes). The report’s paired keyboard assembly, unchanged input fields and slowdown, conditional item-use damping, local camera field copy, and `LivingEntity` vector handoff agree with the blind audit and the checked exact-version source. Its extra 1.14.2 `Input.method_20622()` is correctly placed outside this nonsprinting dry path. The `getMovementInput()` note does not claim that this Vec2f is the `travel` argument; the report separately traces the actual speed-field vector. No correction is needed. Acceptance is limited to the assigned endpoint slice and does not close full-pair coverage, intermediate release boundaries, implementation, build, or runtime parity.