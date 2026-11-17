# Independent audit verdict: SHORT piston-head producer

**Verdict: ACCEPT**

**Audited immutable report:** `build/source-early-patches-20261117/1.10.2--1.11/dependencies/short-piston-head-producer.md`  
**Report SHA-256:** `d8e8b413628e0f130c667ba2ee558dedc9b18b8d5a9ccf5dc5687ccf901a3363`

## Rationale

The report digest matches the assigned identity. The exact 1.10.2 and 1.11 Ornithe/Feather ready-marker and source-manifest hashes match the report, and the listed relevant source files match their recorded hashes in both releases.

The compared `PistonHeadBlock` sources both declare `SHORT`, include it in the state definition, default it to false, and leave it false when reconstructing from metadata. In 1.10.2, `getArmShape` uses facing only; the `MovingBlockEntity` source has no `addCollisions` implementation. In 1.11, `getArmShape` selects the short arm shape from `SHORT`. `MovingBlock.addCollisions` delegates to its local `MovingBlockEntity`; that method constructs a temporary default piston-head state for a source entity and sets `SHORT` using the reported expression `this.extending != 1.0F - this.progress < 0.25F` before delegating collision-shape calculation. The callback is guarded by `!(this.progress < 1.0) || !NOCLIP_DIR`; for ordinary entity-aware player collision queries, `NOCLIP_DIR` is false. The synthesized state is local to the callback.

The metadata and NBT methods do not serialize `SHORT` or `source`: piston-head metadata reconstructs from the false default, while moving-block-entity NBT stores moved block ID/metadata, facing, progress, and extending. Thus the transient SHORT producer is recalculated rather than restored as a persisted property.

The static local-client route is supported: `ClientPlayNetworkHandler.handleBlockEvent` calls `World.addBlockEvent`; that dispatches the current state's `doEvent`; the piston event body reaches `move` or creates the retracting source moving entity. The 1.11 extension move locally constructs a source moving-block entity. Entity movement calls `World.getCollisions(entity, box)`, which passes the entity through `BlockState.addCollisions` and `MovingBlock.addCollisions` to the moving block entity. This establishes possible reachability when the entity query includes the moving-block position.

## Boundary

Acceptance is limited to the producer, persistence, and static caller/query path above. It does not establish runtime timing, query overlap for a particular trajectory, collision clipping or outcome, a trajectory consequence, or complete 1.10.2→1.11 pair coverage.