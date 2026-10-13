# F-005: Long movement can reset fallDistance before later edge-backoff checks

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-FALL-RESET,T-EDGE-GATE
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A Entity#move 553-566 SHA ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de lacks this reset. B 562-575 SHA 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a clips when fallDistance!=0 and resolved movement length squared>=1, then resets on a non-MISS hit. ClipContext B block/fluid filter SHA c26cca8b2a2f185463b3785218518174831ea5a06268b04d9dd5c3a5287ac4ff; B fall_damage_resetting tag SHA bda5807a4d0edf5e0641bc63312f7ca83e5bb58f9c34c09ac01b7829b0d1a850; water tag SHA 698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18 is identical A/B. Player edge predicate reads fallDistance.

## Source-level difference

B adds a fallDistance writer after edge-backoff/collision; unchanged Player gate reads it later.

## Reachability and dependencies

Player movement -> Entity#move clip/reset -> later Player#isAboveGround. B clip-filter closure is resolved: ClipContext#Block.FALLDAMAGE_RESETTING checks the new block tag, ClipContext#Fluid.WATER checks the water tag present in both versions, and Entity#resetFallDistance writes zero on a non-MISS hit. The reset is after the current edge-backoff check and can feed a later Player gate; damage remains excluded.

## Consequence and uncertainty

A qualifying reset can change a later edge gate; the current move's gate runs first. The qualifying path is bounded by nonzero fallDistance, resolved movement length squared>=1, and a clip hit in the B reset block tag or water; exact player outcomes remain dependent on open collision-shape providers.

## Handoff

Source discovery only; implementation deferred.
