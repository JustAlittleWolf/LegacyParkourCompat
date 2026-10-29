# Independent source audit: 1.8.9 default ray provider closure

## Verdict

**ACCEPT**, bound to closure revision SHA-256 `8FFC77A0C443F7B19AB5B49914CCF65E839F38CF75527055608866A7D0E8AEC2`.

The closure correctly establishes the old default provider's algorithm and virtual-call contract while explicitly leaving its complete effective inputs unresolved. Read “provider behavior is source-closed” narrowly: the default implementation and dispatch rules are inspected; a source-faithful result for every legacy cell is not closed. The closure says this directly in its result, input requirements, and final limitation. No source-backed reason was found to revise its bounded conclusion.

## Source audit

- **Dispatch scope:** In 1.8.9 `World.rayTrace`, a block state is fetched for each visited cell and the block's virtual `canRayTrace` is checked before virtual `rayTrace`. Therefore the inherited `Block.rayTrace` path applies to eligible cells whose runtime block does not override `rayTrace`; it does not mean every visited cell reaches that method. Air, fire, liquids under the camera flags, the custom provider families, and any other failed eligibility gate are separate branches. The accepted provider inventory and hit contract support this distinction. For the inherited path, `Block.rayTrace` calls virtual `updateShape(world,pos)` first, then clips using that object's six mutable bounds. The default `updateShape` is empty, so the result for blocks without a writer depends on the bounds left by construction or earlier calls.
- **Clip and first-hit semantics:** The old default clip translates both endpoints to local coordinates, tests planes in minX, maxX, minY, maxY, minZ, maxZ order, accepts candidates within inclusive face bounds, and replaces the selected point only when squared distance is strictly smaller. Thus equal-distance candidate ties retain the earlier plane. It returns null only when no plane candidate survives; the world visitor continues after that null and stops at its first non-null provider result. The update still runs on a miss. These facts support the closure's warning that both a shape and call order can affect which hit blocks later cells.
- **Bounds ownership and construction:** `Block` initializes the six instance fields to the unit cube in its constructor through `setShape`. A subclass constructor may replace that initial value; the registered block object then carries shared mutable bounds used for calls at different positions. A state-only/current outline box is not sufficient to represent this operation. The closure correctly treats its constructor-bound scan as illustrative rather than a complete registry-to-current mapping.
- **State and world dependencies:** Virtual `updateShape(WorldView,pos)` can read the cell state and world neighbors. Door's combined metadata reads the paired vertical cells; chest reads adjacent cells; the accepted writer inventory records other world/state-dependent writers. An exact input must therefore include old block identity and compatible legacy state, plus neighbor/paired-cell context where the invoked provider reads it. The accepted role map explicitly does not prove a lossless metadata-to-current-state conversion.
- **Bounds lifecycle:** The closure appropriately identifies a separate unresolved lifecycle beyond virtual `updateShape`. In canonical source, `BrewingStandBlock.addCollisions` changes bounds and its `resetShape` leaves a 1/8-high box; `EndPortalFrameBlock.addCollisions` finishes with its 13/16-high reset. Neither class overrides `updateShape`, so an inherited default ray call does not refresh those bounds before clipping. Their constructors inherit the base unit-cube initialization and do not call those reset methods. `CauldronBlock.addCollisions` also writes shared bounds but resets to a full cube. This confirms why current cell state alone cannot close all effective ray inputs. Other call sites and subclass constructors still require the exhaustive bounds/lifecycle audit specified by the closure.

## What is accepted, and what remains open

Accepted by this audit:

- the source-level default `Block.rayTrace` order, mutable-bound read, plane selection and null/hit result behavior;
- the distinction between visited cells, eligible cells, inherited default-provider cells and virtual override families;
- the conclusion that the documented 1.8.9 evidence is insufficient to provide complete legacy provider inputs for a faithful modern-cell adapter.

Not established by the closure or this review:

- an exhaustive 1.8.9 registered-ID/state to 26.2 identity and property conversion, including unsupported/current-only cases;
- all constructor-established bounds and all possible writes/resets to the shared fields, with their caller/order/lifecycle reachability;
- complete legacy neighbor, paired-cell, or block-entity reconstruction for each provider;
- source coverage for 1.8 or 1.8.1, or any runtime parity.

The closure records these as explicit gates, so they do not invalidate its accepted source-only result. No cache-writer/consumer proof or modern traversal comparison was re-audited here.

## Anchors and evidence hashes

Canonical 1.8.9 source anchors checked:

- `net/minecraft/block/Block.java:198-207,292-298,456-516,596-598` — base initialization, bound assignment, inherited ray algorithm and empty default updater.
- `net/minecraft/block/BrewingStandBlock.java:33-36,64-74` — inherited constructor initialization and non-restoring collision-bound lifecycle.
- `net/minecraft/block/EndPortalFrameBlock.java:23-24,34-49` — inherited initial bounds and final 13/16 reset after collision collection.
- `net/minecraft/block/CauldronBlock.java:30-54` — collision writes followed by full-cube reset.
- `net/minecraft/block/DoorBlock.java:77-84,222-235` and `ChestBlock.java:56-68` — examples of state/world-dependent provider inputs.
- Accepted `dda-provider-closure/full-ray-provider-inventory.md` and `dda-legacy-provider-closure/provider-and-hit-contract.md` — bounded visitor/override and hit contract evidence.

All closure, accepted-artifact, and cited source-file hashes listed in `hash-manifest.md` were recomputed and matched. Ready-marker hashes also match. The 26.2 ready marker contains the cited source-manifest digest; the source-manifest file itself was not present at the marker's recorded output path, so that digest was corroborated from ready metadata rather than independently re-hashed.
