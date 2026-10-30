# Product integration review: fence stair collision query

## Verdict

**ACCEPT integration commit `6901662573f57e54dbec9b0e933f9e2dc8e28f00` for the bounded 1.11.2 fence-stair witness.** Runtime parity remains unverified. No new source research was performed.

## Candidate identity and registration

The integrated four Java files are byte-identical to corrected candidate commit `76c75df9638a4885b6b1a412b3d12b21e2438610`:

- `change/v1_10_1/FenceCollisionState.java` SHA-256 `E3BFFA5176581A279A47709BFA8B6C4BB00FAD910E01CB4F003BE2C129352118`
- `change/v1_11_2/FenceCollisionState.java` SHA-256 `E1F1FA2D727A866103F06E394DB1D20F9DBE44EBB3B7D678E6CCD06254412084`
- `mixin/CrossCollisionBlockMixin.java` SHA-256 `EA15FFEB7DFC2FBBEA74BC4E196334679C4D689515397B34233CBF7F971112EB`
- `mechanic/hook/block/FenceCollisionStateHook.java` SHA-256 `EB7E78CD56ADF0A50A3266EE796B96288DA04E35281C9B2540C6677102258D0C`

The integrated client mixin config adds exactly one client entry for `CrossCollisionBlockMixin`; it leaves the existing mixin registration intact. `MovementChangeDiscovery` is invoked by the client initializer and registers every implemented `@MechanicType` interface, so `FenceCollisionStateHook` needs no manual catalog entry. The two changes have public no-argument constructors and use only their `@MovementChange` version annotations.

## Resolver and accepted fixture

Product `ChangeResolver` selects the earliest registration whose emulated version is greater than or equal to the selected profile. Thus V1_11_2 uses the corrected stair-disconnection change; V1_10_1 uses the native no-op barrier; V1_12 and later receive no candidate hook and retain native behavior. This matches the reviewed bounded assignment.

For the accepted fixture (legacy straight stone/cobblestone stair south of a wood fence, air in the other three horizontal cells), the corrected ID whitelist recognizes the mapped current `cobblestone_stairs` state. The v1.11.2 hook maps that stair and all three air cells to disconnected and writes all four direction properties, yielding the center-only collision shape. The 26.2 CrossCollisionBlock field/method binding and player/client guard were verified in the initial review and are unchanged in these byte-identical files.

## Existing hook composition

The retained `BlockStateCollisionShapeMixin` runs after the block collision-shape call returns, so it observes the shape supplied by the new CrossCollisionBlock injection. On this fixture, the existing fence/wall restoration hook produces mask zero for the stair and air neighbors. The selected `FenceLegacySolidConnections` provider returns empty for that zero mask, and selected `NativeFenceGlassCollision` returns empty. The existing generic path therefore leaves the candidate’s center-only shape intact. The moving-piston assembly branch does not apply to a fence. No duplicate fence change overwrites this fixture.

This verifies composition for the accepted fixture only; the narrow mapping and whole-operation native fallback do not claim complete historical fence-neighbor equivalence.

No integration files were changed by this review. No build, tests, game launch, source generation, or backlog write was performed.
