# Independent source audit: remaining wall face-shape census

**Verdict: ACCEPT** for the bounded census framing and Beacon wall-bit result.

Audited report: `build/source-residual-20261116/1.13.2--1.14.4/wall_remaining_face_shape_census/report.md`  
Report SHA-256: `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`

## Evidence identity and census framing

The report bytes hash to the bound SHA-256. The ready markers identify 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2; the source manifest bytes hash to the assigned identities: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. Recomputed source hashes match their manifest entries for the cited 1.13.2 and 1.14.4 `Block.java`, `WallBlock.java`, and `BeaconBlock.java`, and registration `Blocks.java` files.

I independently enumerated direct `getFaceShape` declarations in the old `net/minecraft/block` source directory and found 65 distinct source files. The report describes the result with the correct boundary: this is a declaration-file census, including the base declaration and abstract/unregistered/state-dependent classes, not a common-registration or effective-inheritance census. It explicitly leaves registration aliases, inheritance, state domains, the four-face join and remaining per-key dependencies open, and does not assert global completeness or pair freeze.

## Beacon claim

The cited source path supports the reported all-direction result:

- 1.13.2 `WallBlock.shouldConnectTo` accepts the generic arm only when the block is not an exception and the neighbor face is `SOLID`; its other arm is the pole/fence-gate case. `Block.isExceptionForPlacement` explicitly includes `Blocks.BEACON`, and attachment exceptions include that placement list. The inherited `Block.getFaceShape` default is `SOLID`, so the exception suppresses the generic arm in every horizontal direction and Beacon does not satisfy the pole arm.
- Both endpoints register the common `minecraft:beacon` key as `BeaconBlock`. In 1.14.4 `WallBlock` evaluates the neighbor's directional full-collision-face result in its generic arm. Beacon is absent from the attachment-exception roster and the audited target `leaves` payload; its registration does not call `noCollision`. `BeaconBlock` adds no state properties, and the base default collision shape is a full cube with collision enabled. The target directional predicate therefore passes north, east, south and west for its sole registered state. The separate wall/gate arm does not weaken that result.
- Placement and horizontal `updateShape` call the predicate for each respective direction and store those results in the four directional properties. The report correctly describes a source-derived stored-state/shape consequence, not a measured player trajectory.

The default old face result, target collision default, and absence from the target leaves payload are supported by the exact cited files and accepted bounded evidence. No correction to the Beacon false-to-true conclusion is needed.

## Reused evidence limits

The cited accepted packets align with their stated slices: generic-neighbor inventory/audit for TNT and stained glass; pane producer/audit for pane directional-bit production; frosted ice; piston, sticky piston and piston-head faces; trapdoors and registered shulker boxes; six named full-face exception families plus the stated old-wall/target-`WALLS` overlap; and fence-gate bit production. Their verdicts do not close the remaining registration/state/face crosswalk. The resource extraction record is not treated as a source-provider report; its separate `verdict-r2.md` accepts only raw-client provenance and the leaves/walls payload membership claim. The report itself says no extraction was performed here.

**Corrections: none.** This verdict accepts only the report revision above and its bounded Beacon/census claims. The report's explicit remaining common-key unknowns, incomplete global crosswalk and no-freeze status remain open. No product or wiki material, backlog, Git history, build, test, game launch, network access, source generation or shared-source write was used.
