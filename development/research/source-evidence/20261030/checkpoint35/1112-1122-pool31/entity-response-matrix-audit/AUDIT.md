# Independent source-only audit: entity response matrix

## Scope and identity

Audited the entity response matrix report against the blind Java inventory matrix and exact canonical Feather Gen2 build 2 / Vineflower 1.12.0 sources for 1.11.2 and 1.12.2. Used the shared and source discovery workflows. This is a bounded review of the assigned entity/client entity/client-network/packet slice and its 17 stated sibling identities; it is not a pair-freeze claim.

- Report SHA-256: `3707a2b004f647516ad2ada9a9c370d3de1b7b676308d3a930f275d03dc91926` (matches the assigned identity).
- Blind matrix: `java-file-matrix.csv`, SHA-256 `f41e7591f77d44268dc58d7b42a496b7385ffeb79992568c2dac50785d0f948c`.
- Inventory summary: SHA-256 `c3090353a2f5b421a91bea59dd4513ed28b33638cdc189c284ec3e49c012ac89`.
- Source manifests: 1.11.2 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Both actual manifest hashes agree with their ready markers. Ready source counts and bytes equal the exact trees: 1,921 / 9,898,384 and 2,050 / 10,518,527.

## Inventory and hash result

The matrix contains 133 changed/add/remove paths in the assigned prefixes: 113 `hash_changed`, 20 `added`, and zero `removed` (105 `entity`, 3 `client_entity`, 5 `client_network`, 20 `packet`). The report lists exactly these 133 paths, with no duplicates or missing/extra paths; statuses and both-side hashes match the inventory matrix exactly.

Recomputed canonical file SHA-256 for every present side of all 133 primary rows and all 17 sibling rows. All match the report (0 mismatches). Independently checked every reported hash against the corresponding source-manifest entry (0 mismatches). The sibling table has five changed context files and 12 unchanged sources.

## Findings and correction

The report's dismount difference is real: `LivingEntity.dismountRider` changes its candidate box and the first candidate displacement from `+1.0` to `0.0` (1.11.2 `LivingEntity.java:1295,1304`; 1.12.2 `LivingEntity.java:1334,1341`). The caller description should be corrected: `Entity.stopRiding` does not call `dismountRider`. The call is in `LivingEntity.stopRiding` (1.11.2 `LivingEntity.java:1864-1868`; 1.12.2 `LivingEntity.java:1908-1912`), and it is guarded by `!this.world.isClient`. The response therefore runs on the logical server only. Keep it as a changed vanilla player response if the matrix tracks all direct player responses, but do not treat it as a client historical-physics candidate; the project invariant says servers do not replay historical movement.

No additional changed direct local-player position, velocity, or shape writer was found in the reviewed candidate paths. The new 1.12.2 `PlayerEntity.placeOnShoulder` path sets the spawned shoulder entity's position, not the player's. Client correction/velocity/respawn and movement-bearing packet sources listed as siblings remain byte-identical or have unchanged handler method bodies as reported. The remaining firework look-vector reachability/order question is correctly left partial and unresolved; this audit does not close it.

## Verdict

**Pass with one material caller/side-gate correction.** Path inventory and all reported hashes are verified. Correct the dismount caller and make its server-only applicability explicit. Firework yaw/tick ordering and any other full-pair coverage dependencies remain open; no pair freeze is asserted.
