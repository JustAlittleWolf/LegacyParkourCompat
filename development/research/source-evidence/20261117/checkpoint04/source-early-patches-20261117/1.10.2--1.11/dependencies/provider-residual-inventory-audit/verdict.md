# Provider residual inventory audit: 1.10.2 → 1.11

**Verdict: ACCEPT (bounded CocoaBlock age-2 provider applicability and local-player query path).** Audited report: `build/source-early-patches-20261117/1.10.2--1.11/dependencies/provider-residual-inventory.md`, SHA-256 `2015E9E759EAF8C7F5DE5778C1D9C11398E09995DB29DF6C522150533C24E12F`.

## Source bindings checked

The report’s referenced provider inventory is SHA-256 `559cfc38964235b8c436cbda1e908637578580c0a766bb655e632db63eeffd96`; its independent bounded audit is SHA-256 `6ee8be91180ba236a1bf885a64cb96bdcc6613b5690ff8d75b50f3927adb7a01`. That audit verified the exact 1.10.2/1.11 readiness and source-manifest identities and the source-row bindings used here. I independently recomputed the eight report-listed files `CocoaBlock.java`, `Block.java`, `World.java`, and `Entity.java` at both endpoints; all eight SHA-256 values match the report.

The provider route is supported in both paired sources: `Entity.move` expands the entity box by the requested displacement and calls `World.getCollisions(this, query)`; `World.getCollisions` passes that same entity through `BlockState.addCollisions`; `StateDefinition` forwards to `Block.addCollisions`; and the inherited default callback obtains the state collision shape and appends its translated box only when it intersects the query. `Block.getCollisionShape` delegates to the state shape, while the state adapter delegates `getShape` to `CocoaBlock.getShape`. No Cocoa entity filter interrupts this path. The unchanged accepted provider inventory independently binds the local-player movement caller and excludes the already accepted provider slices from this audit.

## CocoaBlock claim

The exact paired CocoaBlock hashes are 1.10.2 `fc7589009c1a84c6e69408fc2ff17eec46202acd03e986555e83812204dd2234` and 1.11 `97b1ac16965a9a6af7e052de9108c17cccb9579ed08283e4c9813f288bd9cd78`; both match local source bytes. Both define `AGE` as 0–2 and dispatch by `FACING`. For the reported `AGE=2`, `FACING=NORTH` state, the source constants are 1.10.2 `[0.3125,0.3125,0.0625]–[0.6875,0.75,0.4375]` and 1.11 `[0.25,0.1875,0.0625]–[0.75,0.75,0.5625]`. This verifies the exact geometry difference used by the witness.

The described state is valid and supportable in each endpoint: a north-facing Cocoa at `(0,0,0)` checks the jungle-log block at `(0,0,-1)`. Source tick/growth paths preserve AGE within 0–2 and can advance a supported Cocoa to age 2. With the ordinary 0.6-by-1.8 entity dimensions bound in `Entity.java`, the report’s player/query example has X `[0.2,0.8]`, Y `[0,1.8]`, and northward swept Z `[0.5,1.7]`. The old shape’s maximum Z `0.4375` is outside that query; the 1.11 shape reaches `0.5625` and intersects it. Thus the claimed difference is collision-list membership for that local-player query, not an inferred movement outcome.

## Scope

ACCEPT is limited to the report’s bound Cocoa age-2 north-facing shape and conditional local-player collision-query witness. It does not establish that a particular `0.6` displacement is produced by an ordinary tick, a resolved axis-clipping/step-up result, a trajectory, exhaustive Cocoa state coverage, full provider closure, pair freeze, implementation eligibility, or runtime parity. The prior accepted provider, moving-piston, PistonHead, SnowLayer, stairs, Flower, rail, and other slices were not re-audited or broadened.
