# Piston-head retraction witness correction

This supplement binds the immutable block-entity closure report at SHA-256 `40CA4E06976857F9FDC9ED4D2E72BF5364B7FA6E980BDBA57EDD57DE7FBD12E5` and its independent source audit at SHA-256 `50B3ACA791D39646125B476E7886A5F84B8A818BA4BA62B3DA6331D192447596`.

The report's horizontal player-box witness at retracting `progress = 0.8F` is withdrawn. Ordinary piston progress advances in 0.5 increments and the collision collector reads the stored progress field, so that fractional value is not established by the client tick route.

The same PistonHeadBlock difference remains a source-reachable collision candidate with a corrected witness at terminal `progress = 1.0F`. For an east-facing retracting source piston at `(0,0,0)`, `SHORT` is true and the head offset is zero. The old full arm spans X `[-0.25,0.75]`, Y/Z `[0.375,0.625]`; the new short arm spans X `[0,0.75]`, Y/Z `[0.375,0.625]`. A player box `[-0.70,0.80,0.40 → -0.10,2.60,1.00]` requesting `dy=-0.30` overlaps the old arm but lies wholly west of the new short arm and extended base. The old collision clips downward movement to `-0.175`; the new head/base adds no vertical clip. World entity-before-block-entity tick order allows a player collision query after progress first reaches 1.0 and before the moving entity is removed on the following tick. This supports endpoint collision response, not a complete trajectory or runtime parity claim.

The 17 block-entity route closures remain supported for the assigned source slice. Pair freeze, intermediate boundary and runtime parity remain open.
