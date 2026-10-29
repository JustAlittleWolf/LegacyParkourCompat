# Independent review: ordinary pressure plates, IDs 70 and 72

**Verdict: ACCEPT** for the bounded legacy registry/state and local ray/movement-provider claims in the reviewed finding. This does not accept DDA visitation, first-hit, cache-consumer, or whole-operation parity.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-ordinary-pressure-plates-70-72/FINDING.md`
- Finding SHA-256: `CCA97391F76E294827ECFCDD6CF1C50BE853823F3BC7C6C9E92AD6847F176C1C`
- Source ledger: `.native-source/20261028/collision-next/dda-ordinary-pressure-plates-70-72/source-hashes.json`
- Source ledger SHA-256: `1533913D7AAC972A98FDEE647AB9A9E1A39ADFF45F3A875FFCD8DD6C5D180C2E`

## Independent checks

- Recomputed all 13 selected Java file hashes from the canonical read-only source trees and compared each with both the report ledger and the exact source `.sources.sha256` row: **13/13 match, zero mismatches**.
- Recomputed the 1.8.9 and 26.2 ready-marker, provenance, and source-manifest hashes recorded in the ledger: **all six identity digests match**.
- Confirmed the exact 1.8.9 Block registration: ID 70 is `stone_pressure_plate` constructed with stone material and MOBS activation; ID 72 is `wooden_pressure_plate` with wood material and EVERYTHING activation (`Block.java:970–986`). They are distinct from weighted IDs 147/148.
- Confirmed the shared old `PressurePlateBlock` state decoder maps metadata `1` to `POWERED=true` and all other metadata to false; serialization is true→1/false→0 (`PressurePlateBlock.java:61–68`).
- Confirmed old ray bounds are written from the queried state before the default ray clip: powered bounds X/Z `[1/16,15/16]`, Y `[0,1/32]`; unpowered bounds X/Z `[1/16,15/16]`, Y `[0,1/16]` (`AbstractPressurePlateBlock.java:26–38`; `Block.java:456–465`). The default `Block.rayTrace` dispatches virtual `updateShape` once before clipping. The camera entity call uses `false,false,true`, and the old world path reaches the per-cell ray provider after its gates (`Entity.java:1029–1034`; `World.java:633–653`).
- Confirmed these plates' old movement collision provider returns `null`; `Block.addCollisions` adds only non-null boxes (`AbstractPressurePlateBlock.java:46–49`; `Block.java:339–344`). Thus the mutable ray bounds are not their own movement collision box.
- Confirmed current aliases `stone_pressure_plate` and `oak_pressure_plate` (`BlockItemIds.java:589,593`); both current registrations use `.noCollision()` (`Blocks.java:1793–1802,1809–1819`). The datafix map stores current-state tags first and legacy tags as the varargs after them, as verified in `BlockStateData.register` (`BlockStateData.java:686–700`); the four entries map metadata-derived state IDs 1120/1121 to stone POWERED false/true and 1152/1153 from legacy wooden to current oak POWERED false/true (`BlockStateData.java:3664–3665,3782–3783`).
- Confirmed current `BasePressurePlateBlock` chooses centered 14/16-wide shapes of height 0.5/16 when powered and 1/16 when unpowered; these match the old local ray bounds (`BasePressurePlateBlock.java:11–27`; `Block.column` in `Block.java:177–185`). Current `noCollision` disables the collision provider, which returns `Shapes.empty()` (`BlockBehaviour.java:327–328,1094–1097`).

## Limits retained

The accepted claims are only about IDs 70 and 72, their valid POWERED projections, and each block's local old/current providers. No inference is made from weighted plates; no DDA visitation, cache relevance, current-state reachability, or whole-ray parity is established.
