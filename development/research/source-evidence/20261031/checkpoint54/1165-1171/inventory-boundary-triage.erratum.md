# Erratum: source witness for the 1.17.1 swimming-entry gate

This erratum supersedes the sentence in `inventory-boundary-triage.md` that says a concrete vanilla fixture and independent audit are still required. The independent source audit now closes that source witness. It does not establish runtime trajectory parity.

The audited witness uses a dry bottom slab at block `(0,0,0)` with a standing local player at `(0.5,0.5,0.5)`, and a waterlogged wall sign at `(0,2,0)` attached to a solid side block. Player standing dimensions are 0.6 by 1.8 and standing eye height is 1.62. The slab has empty fluid when dry; the sign is no-collision, waterloggable, and returns source water when waterlogged. Exact `FluidTagsProvider.addTags()` includes `Fluids.WATER` in `FluidTags.WATER`.

With sprinting true and no passenger, the 1.17 fluid-contact scan samples the player AABB after a 0.001 deflate; its Y loop includes the waterlogged sign at y=2. The eye sample at `getEyeY() - 0.11111111F` also lies in that sign's WATER fluid. Therefore the 1.17 old entry gates can all be true while `blockPosition()` remains `(0,0,0)` and the dry slab's fluid is not WATER. In 1.17.1 the added feet-cell gate fails. `Player.updateSwimming()` delegates to the base operation when not flying, and the local-player travel path consumes the changed swimming state.

The independent trace and exact source anchors are in `.native-source/20261031/1165-1171-finish45/swim_gate_audit/erratum.md`; its SHA-256 is recorded in that report's adjacent `erratum.sha256` file. Treat the geometry as a static source witness under equal logical inputs, not an executed game or recorded trajectory.
