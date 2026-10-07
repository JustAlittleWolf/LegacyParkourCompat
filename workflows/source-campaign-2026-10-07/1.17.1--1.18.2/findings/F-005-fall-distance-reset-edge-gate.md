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

A Entity#move 553-566 SHA ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de lacks reset. B 562-575 SHA 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a clips when fallDistance!=0 and resolved length squared>=1, reset on non-MISS. Player edge predicate reads fallDistance. B tag SHA bda5807a4d0edf5e0641bc63312f7ca83e5bb58f9c34c09ac01b7829b0d1a850.

## Source-level difference

B adds a fallDistance writer after edge-backoff/collision; unchanged Player gate reads it later.

## Reachability and dependencies

Player movement -> Entity#move clip/reset -> later Player#isAboveGround. Clip filter closure open; finding limited to movement state, damage excluded.

## Consequence and uncertainty

A qualifying reset may change a later edge gate; current move's gate ran first. Concrete path unproven.

## Handoff

Source discovery only; implementation deferred.
