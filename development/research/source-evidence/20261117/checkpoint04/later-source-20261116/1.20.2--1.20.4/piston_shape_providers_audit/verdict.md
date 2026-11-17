# Independent audit: 1.20.2 to 1.20.4 piston shape providers

Verdict: **ACCEPT** — bounded `NO_DIFF` claim accepted.

Audited report: `build/later-source-20261116/1.20.2--1.20.4/piston_shape_providers/coverage.md`
Report SHA-256: `7c0a67411850ff1f294ce0e3330496911da12f9e3fc3e5048da26e3ee4433118`

I independently checked the two exact Mojmap release identities and the ready/provenance/source/artifact sidecars cited in the report. The sidecars identify 1.20.2 and 1.20.4 with `mapping=mojmap`; their recorded digests agree with the report. I recomputed each cited Java path hash against the source tree and matching `mojmap.sources.sha256`; every listed path/hash pair matched, including byte-identical `PistonMovingBlockEntity.java`.

The inspected implementations support the report's bounded conclusion:

- `PistonBaseBlock.getShape` selects the same six directional extended shapes, or the full block when unextended. Defaults remain NORTH/false.
- `PistonHeadBlock` uses the same directional base-plate and short/long arm unions. `getShape` selects by `SHORT` and `FACING`; TYPE and collision context do not affect the shape.
- `MovingPistonBlock` returns an empty ordinary shape and delegates collision shape to a matching `PistonMovingBlockEntity`, otherwise empty.
- The byte-identical moving-piston entity method retains the reported base-shape exception, NOCLIP early return, synthetic source-piston head state and short predicate, extended-progress calculation, translation, and union. `getExtendedProgress` is unchanged.
- Piston, sticky-piston, piston-head, and moving-piston registrations/property chains match for the cited shape/collision behavior. `BlockBehaviour` retains the same default shape-to-collision dispatch and `hasCollision` default; `Block.box` retains sixteenth scaling.

No omitted difference was found within those providers, selectors, geometry helpers, direct registrations, and inherited dispatch. This acceptance does not establish piston actuation/displacement, ordinary-state reachability, a complete pair-wide provider inventory, pair freeze, or runtime parity; those remain outside the report's stated slice.
