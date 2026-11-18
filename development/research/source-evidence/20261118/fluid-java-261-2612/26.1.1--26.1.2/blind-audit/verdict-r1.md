# Final draft review verdict r1

## Bound identities

- Source comparison: `26.1.1--26.1.2/source/comparison.md`, authorized and verified SHA-256 `d4e863c4aef0ac682f67f4f0a4e605486532a36b7cfcac69836a68e60de13a3f`.
- Independent frozen audit: `26.1.1--26.1.2/blind-audit/frozen.md`, SHA-256 `f8c583b81c688442a121065b6bbcaa729ad9bbfb4c3d4450b8a03463d2d66c17`.

## Verdict

The comparison's no-difference result agrees with the frozen review for the overlapping evidence: contact admission and stored height in `EntityFluidInteraction`, state update/current application in `Entity`, fluid flow dispatch through the reviewed `FluidState`/`FlowingFluid` methods, and first water/lava travel gates and movement math in `LivingEntity`. No movement difference contradicts the frozen verdict.

The comparison extends the closure beyond the frozen audit in two places: `LocalPlayer` dispatch and the water/lava fluid subclass implementations. Those dependencies appear in the comparison's consumed-file table, but were not independently examined or hashed in the frozen audit. The frozen evidence therefore supports the shared implementation path, while independent acceptance of the full ordinary-local-player path remains open until these closures are audited.

There is also a source-location discrepancy for the ordinary player's true `isPushedByFluid()` behavior: the comparison cites `Entity.java:3206`; the frozen audit cites `Player.java:1673-1675`. Both reports state the same boolean and the compared `Entity` and `Player` files are byte-identical across releases, so this is not evidence of a release difference. Resolve the effective implementation/citation before treating the push-call trace as fully closed.

## 26.1.2 publication and origin

Canonical local metadata identifies the prepared 26.1.2 source set as version `26.1.2`, namespace `unobfuscated`, and records its decompile/tool run and source-manifest digest. Its ready marker records a client SHA-256. The local provenance file omits the official client URL/hash and version-metadata hash, so these records do not independently authenticate the source set's official Mojang artifact origin. The comparison correctly leaves that origin check open.

## Acceptance boundary

Accept the bounded no-difference result for the shared, independently frozen slices. Withhold full local-player path acceptance pending the two missing dependency closures and the `isPushedByFluid` citation resolution. This verdict concerns source evidence only; it does not establish full-pair coverage, an exact intermediate cutover, implementation correctness, or runtime parity.