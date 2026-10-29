# Independent review: trapped chest ID 146 source closure

## Verdict

**ACCEPT, bounded to the stated source-coverage claims.** The inspected canonical sources support the finding that 1.8.9 numeric block ID 146 is a distinct `ChestBlock(1)` singleton from ID 54, that its own mutable bounds are rewritten before base ray clipping, and that the shared base collision path feeds ordinary entity movement collision collection. The current 26.2 trapped-chest registration and inherited collision shape provider are also supported. This review does not establish DDA visitation equivalence, current/old shape parity, or a movement adapter.

## Exact reviewed inputs

- `FINDING.md` SHA-256: `17797220C2393BB817F752F9438A94C388A76D5F7FB35B2AE827D4FBACF73924`
- `source-hashes.json` SHA-256: `128A556C880A58938397C96FFF807A20B6F9C61B3A90D8AF48148CF5FFE01575`
- The ledger has 11 selected source entries. I independently recomputed each file digest and compared it with the canonical release manifest: 11/11 matched. The six listed source-manifest/readiness/provenance identity digests also matched their canonical files.

## Findings checked

- Old `Block.java` registers ID 54 as `new ChestBlock(0)` and ID 146 as `new ChestBlock(1)`, so they are separate object instances. Old `ChestBlock` stores `type` per instance. Its `updateShape` implementation probes north, south, west, east in an `if/else-if` chain and qualifies neighbors by object identity (`neighborBlock == this`). Thus ID 146 connects only to another ID-146 singleton; the priority order is as stated.
- The old constructor initializes mutable instance bounds. `updateShape` has no `type` branch and writes the selected bounds. No `ChestBlock` override of the base ray or collision-shape methods was found. Base `Block.rayTrace` invokes `updateShape` before clipping; a miss therefore does not undo the instance-field write.
- Base `Block.addCollisions` obtains its collision box from those same bounds. `World.getCollisions` calls that provider, and `Entity` consumes the resulting collision list during ordinary movement and step-up collection. The stated movement-consumer link follows from the exact old source.
- Current `ItemIdFix` maps numeric ID 146 to `minecraft:trapped_chest`; `BlockItemIds` contains that key. `Blocks` registers a `TrappedChestBlock` using ordinary collision-enabled properties. `TrappedChestBlock` extends `ChestBlock`; current chest shape selection uses the state `TYPE` and same-block connection predicate, while `BlockBehaviour` uses the state shape as collision shape when collision is enabled. The listed current state rows show the cited cardinal-facing/single forms only.

## Scope limits

The current provider's different representation is not evidence of geometric parity. This review does not prove old-versus-current provider equality, DDA visitation, cache replay correctness, or exhaustive metadata migration. The finding correctly keeps those claims open.