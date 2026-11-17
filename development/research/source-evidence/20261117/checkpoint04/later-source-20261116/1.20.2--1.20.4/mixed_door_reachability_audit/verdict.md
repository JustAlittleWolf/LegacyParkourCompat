# Independent source audit: mixed DoorBlock ordinary reachability

## Verdict

**ACCEPT for the bounded ordinary BlockItem placement conclusion**, bound to report SHA-256 86369F6EE3D4C17DF3EF1BED8F7CFD4B8BD695F1ADFDEAF4F11DBDF0B13BBFF6.

The report's identities and all ten cited endpoint Java-file hashes match the current root sidecars, ready markers, source manifests, and source bytes. Both inputs declare their exact release and Mojmap namespace. The actual sidecar form is version-root provenance JSON; the report does not assume a hidden mapping-local provenance path.

## Source trace

The paired DoorBlock.getStateForPlacement methods only return a lower-half state when the block above is replaceable. DoorBlock.setPlacedBy then installs the upper half from that same state, so a successful ordinary door-item placement creates the same DoorBlock instance in both halves. BlockItem.getPlacementState passes the returned state through canPlace; default mustSurvive is true, and canPlace requires state.canSurvive before setting the block.

DoorBlock.canSurvive calls the below state's isFaceSturdy with UP; BlockState's three-argument overload delegates to SupportType.FULL in both endpoints. SupportType.FULL checks whether the support shape's UP face is a full block. DoorBlock does not override getBlockSupportShape; BlockBehaviour delegates it to the collision shape, and collision shape delegates to the state shape. DoorBlock's horizontal collision shapes are only 3/16 thick, so their UP face is not full support. Placing a new lower door directly above an existing upper door therefore fails the survival gate. This trace is supported in both releases.

For additional placement-context confirmation, BlockPlaceContext.getClickedPos/canPlace are byte-identical across the endpoints and source-manifest-bound (SHA-256 c0ff47d483f04cb68c73dab6839a0ea9c300fc68deb6f6e07d266ca8e0e45702 in each tree). A nonreplaceable clicked door redirects the placement position to the hit face's adjacent cell; the top-face attempt is subject to the failed lower-door support gate above.

The paired DoorBlock.updateShape method change is also correctly stated: 1.20.2 requires the opposite-half neighbor to be this exact block instance; 1.20.4 accepts any DoorBlock and adopts the neighbor state with the current HALF. The ordinary placement route audited here does not construct the mixed opposite-half pair needed to reach that branch.

## Limits

This accepts only the stated ordinary BlockItem placement gate. Commands, world generation, data loading, other mods and arbitrary state injection remain unexamined. No ordinary-player movement finding, full registry/provider closure, pair freeze, or runtime parity is established. No backlog, wiki, product, Git-history, build, test, runtime, artifact-inspection, or source-generation work was performed.
