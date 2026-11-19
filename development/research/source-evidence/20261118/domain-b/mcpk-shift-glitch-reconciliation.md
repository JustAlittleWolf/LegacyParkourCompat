# MCPK 1.16.2 shift-glitch comparison

The source lookup for the MCPK “unexpected fall partially fixed” shift-glitch hint identifies the existing `stepdown-candidate-intersection-gate` operation. Exact 1.16.1 Mojmap `Player.maybeBackOffFromEdge` (1010–1057) gates sneaking SELF and PLAYER moves on `onGround`. Exact 1.16.2 `Player.java:1010–1065` adds `!abilities.flying` and substitutes `isAboveGround()`. That predicate accepts ground contact, or a fall distance below the step height when the box projected down by `fallDistance - maxUpStep` intersects collision. The X, Z and diagonal 0.05 backoff loops retain their existing collision predicates. `Entity.move` calls the backoff before clipping at 1.16.1 `Entity.java:488–489` and 1.16.2 `Entity.java:504–505`.

The reviewed product disposition for `stepdown-candidate-intersection-gate` already covers the near-ground support path and the backoff query composition for default-player geometry. The source comparison supplies no independent second operation for this hint. The disposition is source reconciliation for the exact release pair. Runtime parity remains unverified.

The `/root/mcpk_shift_glitch_known_residual` check used the pinned ready/provenance records and these Mojmap SHA-256 values.

| Artifact | 1.16.1 | 1.16.2 |
| --- | --- | --- |
| Client | b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef | 19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d |
| Source manifest | 76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754 | 10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7 |
| Player.java | a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e | d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 |
| Entity.java | 27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576 | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
